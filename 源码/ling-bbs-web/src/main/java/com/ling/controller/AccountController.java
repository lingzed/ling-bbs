package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.annotation.RequestLogRecode;
import com.ling.annotation.ExcludeParamLog;
import com.ling.annotation.Validation;
import com.ling.commons.CommonMsg;
import com.ling.constant.RegexConstant;
import com.ling.entity.ImageCode;
import com.ling.constant.Constant;
import com.ling.entity.dto.SysSettingContainer;
import com.ling.entity.dto.UserInfoSessionDto;
import com.ling.entity.vo.Result;
import com.ling.exception.BusinessException;
import com.ling.service.MailCodeService;
import com.ling.service.UserInfoService;
import com.ling.utils.IPUtil;
import com.ling.utils.StrUtil;
import com.ling.utils.SysCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/account")
public class AccountController {

    private Logger log = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private MailCodeService mailCodeService;

    @Resource
    private UserInfoService userInfoService;

    /**
     * 获取验证码
     *
     * @param response 响应流
     * @param type     验证码类型
     * @throws IOException 异常
     */
    @GetMapping("/checkCode")
    @AccessControl
    @RequestLogRecode
    public void getCheckCode(@ExcludeParamLog HttpSession session,
                             @ExcludeParamLog HttpServletResponse response,
                             @RequestParam @Validation Integer type) throws IOException {
        ImageCode imageCode = new ImageCode(130, 38, 5, 10);    // 绘制图片
        String codeKey = type.equals(Constant.NUM_1) ? Constant.CHECK_CODE : Constant.CHECK_CODE_EMAIL; // 验证码类型
        String code = imageCode.getCode();      // 生成图片验证码
        session.setAttribute(codeKey, code);    // 将验证码存入session
        log.info("验证码{}: {}", codeKey, code);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        imageCode.write(response.getOutputStream());    // 将图片验证码写入到响应流
    }

    /**
     * 发送邮箱验证码
     *
     * @param mail      收件人邮箱
     * @param checkCode 用户输入的图片验证码
     * @param session   session
     * @return
     */
    @GetMapping("/sendmail")
    @AccessControl
    @RequestLogRecode
    public Result sendmail(@Validation(max = 150, regex = RegexConstant.REGEX_EMAIL) String mail,
                           @Validation String checkCode,
                           @ExcludeParamLog HttpSession session) {
        try {
            doCheckCode(session, checkCode, Constant.CHECK_CODE_EMAIL);
            mailCodeService.sendMailCode(mail);
            return Result.success();
        } finally {
            // 验证码校验完成后需要删除
            session.removeAttribute(Constant.CHECK_CODE_EMAIL);
        }
    }

    /**
     * 注册
     *
     * @param nickname  昵称
     * @param password  密码
     * @param mail      邮箱
     * @param checkCode 图片验证码
     * @param mailCode  邮箱验证码
     * @param session   session
     * @return
     */
    @GetMapping("/register")
    @AccessControl
    @RequestLogRecode
    public Result register(@Validation String nickname,
                           @Validation(regex = RegexConstant.REGEX_PWD_MEDIUM) String password,
                           @Validation(max = 150, regex = RegexConstant.REGEX_EMAIL) String mail,
                           @Validation String checkCode,
                           @Validation String mailCode,
                           @ExcludeParamLog HttpSession session) {
        try {
            doCheckCode(session, checkCode, Constant.CHECK_CODE);
            userInfoService.register(nickname, password, mail, mailCode);
            return Result.success();
        } finally {
            session.removeAttribute(Constant.CHECK_CODE);
        }
    }

    /**
     * 登录
     *
     * @param request
     * @param session
     * @param mail      邮箱
     * @param password  密码
     * @param checkCode 图片验证码
     * @return
     */
    @GetMapping("/login")
    @AccessControl
    @RequestLogRecode
    public Result login(@ExcludeParamLog HttpServletRequest request,
                        @ExcludeParamLog HttpSession session,
                        @Validation(max = 150, regex = RegexConstant.REGEX_EMAIL) String mail,
                        @Validation(min = 8, max = 32) String password,
                        @Validation String checkCode) {
        try {
            doCheckCode(session, checkCode, Constant.CHECK_CODE);
            String ip = IPUtil.getIP(request);
            UserInfoSessionDto userInfoSessionDto = userInfoService.login(ip, mail, password);
            session.setAttribute(Constant.USERINFO_SESSION_KEY, userInfoSessionDto);
            return Result.success();
        } finally {
            session.removeAttribute(Constant.CHECK_CODE);
        }
    }

    /**
     * 登出
     *
     * @param session
     * @return
     */
    @GetMapping("/logout")
    @AccessControl
    @RequestLogRecode
    public Result logout(@ExcludeParamLog HttpSession session) {
        session.invalidate();
        return Result.success();
    }

    /**
     * 获取用户信息
     *
     * @param session
     * @return
     */
    @GetMapping("/getUserInfo")
    @AccessControl
    @RequestLogRecode
    public Result<UserInfoSessionDto> getUserInfo(@ExcludeParamLog HttpSession session) {
        UserInfoSessionDto userInfo = (UserInfoSessionDto) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        return Result.success(userInfo);
    }

    /**
     * 获取评论开关状态
     *
     * @return
     */
    @GetMapping("/getOpenComment")
    @AccessControl
    @RequestLogRecode
    public Result<HashMap<String, Object>> getSysSetting4Comment() {
        SysSettingContainer sysSettingContainer = SysCacheUtil.getSysSettingContainer();
        HashMap<String, Object> res = new HashMap<>();
        res.put("openComment", sysSettingContainer.getSysSetting4Comment().isOpenComment());
        return Result.success(res);
    }

    @GetMapping("/resetPwd")
    @AccessControl
    @RequestLogRecode
    public Result resetPwd(@Validation(regex = RegexConstant.REGEX_PWD_MEDIUM) String password,
                           @Validation(max = 150, regex = RegexConstant.REGEX_EMAIL) String mail,
                           @Validation String checkCode,
                           @Validation String mailCode,
                           @ExcludeParamLog HttpSession session) {
        try {
            doCheckCode(session, checkCode, Constant.CHECK_CODE);
            userInfoService.resetPwd(mail, password, mailCode);
            return Result.success();
        } finally {
            session.removeAttribute(Constant.CHECK_CODE);
        }
    }

    private void doCheckCode(HttpSession session, String checkCode, String checkCodeKey) {
        String code = (String) session.getAttribute(checkCodeKey);
        log.info("生成的图片验证码: {}", code);
        if (StrUtil.isEmpty(code)) throw new BusinessException(CommonMsg.CHECK_CODE_EXPIRED);
        if (StrUtil.isEmpty(checkCode) || !checkCode.equalsIgnoreCase(code))
            throw new BusinessException(CommonMsg.CHECK_CODE_ERROR);
    }

    @GetMapping("/mailCheckExists")
    @AccessControl
    @RequestLogRecode
    public Result mailCheckExists(@Validation(max = 150, regex = RegexConstant.REGEX_EMAIL) String mail,
                                  @Validation(max = 2, min = 1) Integer env) {
        userInfoService.checkMailExists(mail, env);
        return Result.success();
    }
}
