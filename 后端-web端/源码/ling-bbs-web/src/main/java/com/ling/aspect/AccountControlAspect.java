package com.ling.aspect;

import com.ling.annotation.AccessControl;
import com.ling.constant.Constant;
import com.ling.entity.dto.SessionUserinfo;
import com.ling.entity.dto.SysSettingManager;
import com.ling.entity.dto.query.ArticleQuery;
import com.ling.entity.dto.query.CommentQuery;
import com.ling.entity.dto.query.LikeRecordQuery;
import com.ling.entity.vo.Result;
import com.ling.enums.FrequencyLimitTypeEnum;
import com.ling.enums.ResponseCodeEnum;
import com.ling.exception.BusinessException;
import com.ling.service.ArticleService;
import com.ling.service.CommentService;
import com.ling.service.LikeRecordService;
import com.ling.utils.StrUtil;
import com.ling.utils.SysCacheUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 访问控制切面
 */
@Aspect
@Component
@Order(0)
public class AccountControlAspect {
    private Logger log = LoggerFactory.getLogger(AccountControlAspect.class);
    @Resource
    private HttpServletRequest request;
    @Resource
    private CommentService commentService;
    @Resource
    private LikeRecordService likeRecordService;
    @Resource
    private ArticleService articleService;
    private static final Map<FrequencyLimitTypeEnum, Function<String, Integer>> countFormDB = new HashMap<>();
    private static final Map<FrequencyLimitTypeEnum, Supplier<Integer>> countFormSys = new HashMap<>();

    static {
        countFormSys.put(FrequencyLimitTypeEnum.COMMENT_LIMIT,
                () -> SysCacheUtil.getSysSettingManager().getSysSetting4Comment().getCommentNum());
        countFormSys.put(FrequencyLimitTypeEnum.LIKE_LIMIT,
                () -> SysCacheUtil.getSysSettingManager().getSysSetting4Like().getLikeNum());
        countFormSys.put(FrequencyLimitTypeEnum.POST_LIMIT,
                () -> SysCacheUtil.getSysSettingManager().getSysSetting4Post().getPostNum());
        countFormSys.put(FrequencyLimitTypeEnum.UPLOAD_IMG_LIMIT,
                () -> SysCacheUtil.getSysSettingManager().getSysSetting4Post().getUploadImgNum());
    }

    {
        countFormDB.put(FrequencyLimitTypeEnum.COMMENT_LIMIT, userId -> {
            CommentQuery query = new CommentQuery();
            LocalDate now = LocalDate.now();
            query.setSenderId(userId);
            query.setStartPostTime(now);
            query.setEndPostTime(now);
            Long totalByCondition = commentService.findTotalByCondition(query);
            return totalByCondition.intValue();
        });
        countFormDB.put(FrequencyLimitTypeEnum.LIKE_LIMIT, userId -> {
            LikeRecordQuery query = new LikeRecordQuery();
            LocalDate now = LocalDate.now();
            query.setLikerId(userId);
            query.setStartLikeTime(now);
            query.setEndLikeTime(now.plusDays(1));
            Long totalByCondition = likeRecordService.findTotalByCondition(query);
            return totalByCondition.intValue();
        });
        countFormDB.put(FrequencyLimitTypeEnum.POST_LIMIT, userId -> {
            ArticleQuery query = new ArticleQuery();
            LocalDate now = LocalDate.now();
            query.setUserId(userId);
            query.setStartCreateTime(now);
            query.setEndCreateTime(now.plusDays(1));
            Long totalByCondition = articleService.findTotalByCondition(query);
            return totalByCondition.intValue();
        });
        countFormDB.put(FrequencyLimitTypeEnum.UPLOAD_IMG_LIMIT, userId -> 0);
    }

    @Pointcut("@annotation(com.ling.annotation.AccessControl)")
    public void pt() {

    }

    /**
     * 访问控制
     *
     * @param joinPoint
     */
    @Around("pt()")
    public Object accountControl(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        AccessControl accessControl = method.getAnnotation(AccessControl.class);

        HttpSession session = request.getSession();
        SessionUserinfo userinfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);

        checkLoginRequired(accessControl, userinfo);  // 登录接口限制校验

        String ymd = StrUtil.formatDate("yyy-MM-dd_");
        boolean recordFrequency = frequencyLimitation(accessControl, ymd);// 接口频次访问限制

        Object proceed = joinPoint.proceed();

        // 若Result状态码为200，则更新会话中的限制次数
        if (recordFrequency && Objects.equals(((Result) proceed).getCode(), 200)) {
            FrequencyLimitTypeEnum frequency = accessControl.frequency();
            String frequencyKey = ymd + frequency.getType();
            Integer count = (Integer) session.getAttribute(frequencyKey);
            session.setAttribute(frequencyKey, count + 1);
        }
        return proceed;
    }

    /**
     * 登录接口限制
     *
     * @param accessControl
     */
    public void checkLoginRequired(AccessControl accessControl, SessionUserinfo userinfo) {
        if (Objects.isNull(accessControl) || !accessControl.loginRequired()) return;
        if (Objects.isNull(userinfo))
            throw new BusinessException(ResponseCodeEnum.CODE_901);
    }

    /**
     * 接口频次限制
     *
     * @param accessControl
     * @param formatDate
     */
    public boolean frequencyLimitation(AccessControl accessControl, String formatDate) {
        if (Objects.isNull(accessControl) ||
                Objects.equals(FrequencyLimitTypeEnum.UNLIMITED, accessControl.frequency()))
            return false;
        FrequencyLimitTypeEnum frequency = accessControl.frequency();
        HttpSession session = request.getSession();
        SessionUserinfo userinfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);

        // 从session中获取已完成的次数
        String frequencyKey = formatDate + frequency.getType();
        Integer count = (Integer) session.getAttribute(frequencyKey);

        // 若为null，则从数据库中查询次数
        if (Objects.isNull(count)) {
            count = countFormDB.get(frequency).apply(userinfo.getUserId());
            session.setAttribute(frequencyKey, count);
        }

        // 判断是否超出次数限制
        Integer sysCount = countFormSys.get(frequency).get();
        if (count >= sysCount)
            throw new BusinessException(String.format(frequency.getErrMsg(), sysCount));
        return true;
    }
}
