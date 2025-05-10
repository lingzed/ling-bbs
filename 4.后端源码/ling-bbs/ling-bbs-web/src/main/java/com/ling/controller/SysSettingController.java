package com.ling.controller;

import com.ling.annotation.AccessControl;
import com.ling.constant.Constant;
import com.ling.entity.dto.SysSettingManager;
import com.ling.entity.dto.session.SessionUserinfo;
import com.ling.entity.po.SysSetting;
import com.ling.entity.vo.Result;
import com.ling.enums.ResponseCodeEnum;
import com.ling.exception.BusinessException;
import com.ling.service.SysSettingService;
import com.ling.utils.SysCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * 系统设置控制器
 */
@RestController
@RequestMapping("/setting")
public class SysSettingController {
    private static final Logger log = LoggerFactory.getLogger(SysSettingController.class);
    @Resource
    private SysSettingService sysSettingService;

    @GetMapping("/refresh")
    @AccessControl(loginRequired = true)
    public Result<SysSettingManager> refreshHandle(HttpSession session) {
        SessionUserinfo userinfo = (SessionUserinfo) session.getAttribute(Constant.USERINFO_SESSION_KEY);
        if (!userinfo.getIsAdmin())
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        sysSettingService.refreshCache();
        SysSettingManager sysSettingManager = SysCacheUtil.getSysSettingManager();
        return Result.success(sysSettingManager);
    }

    /**
     * 获取评论开关状态
     *
     * @return
     */
    @GetMapping("/getOpenComment")
    @AccessControl(loginRequired = true)
    public Result<HashMap<String, Object>> getSysSetting4Comment() {
        SysSettingManager sysSettingManager = SysCacheUtil.getSysSettingManager();
        HashMap<String, Object> res = new HashMap<>();
        res.put("openComment", sysSettingManager.getSysSetting4Comment().isOpenComment());
        return Result.success(res);
    }

    @GetMapping
    public Result<SysSettingManager> get() {
        SysSettingManager sysSettingManager = SysCacheUtil.getSysSettingManager();
        return Result.success(sysSettingManager);
    }
}
