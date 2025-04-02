package com.ling.entity.dto;

/**
 * 系统设置管理实体
 */
public class SysSettingManager {
    private SysSetting4Audit sysSetting4Audit;          // 审核设置
    private SysSetting4Comment sysSetting4Comment;      // 评论设置
    private SysSetting4Like sysSetting4Like;            // 点赞设置
    private SysSetting4Mail sysSetting4Mail;            // 邮件设置
    private SysSetting4Post sysSetting4Post;            // 发帖设置
    private SysSetting4Register sysSetting4Register;    // 注册设置

    public SysSetting4Audit getSysSetting4Audit() {
        return sysSetting4Audit;
    }

    public void setSysSetting4Audit(SysSetting4Audit sysSetting4Audit) {
        this.sysSetting4Audit = sysSetting4Audit;
    }

    public SysSetting4Comment getSysSetting4Comment() {
        return sysSetting4Comment;
    }

    public void setSysSetting4Comment(SysSetting4Comment sysSetting4Comment) {
        this.sysSetting4Comment = sysSetting4Comment;
    }

    public SysSetting4Like getSysSetting4Like() {
        return sysSetting4Like;
    }

    public void setSysSetting4Like(SysSetting4Like sysSetting4Like) {
        this.sysSetting4Like = sysSetting4Like;
    }

    public SysSetting4Mail getSysSetting4Mail() {
        return sysSetting4Mail;
    }

    public void setSysSetting4Mail(SysSetting4Mail sysSetting4Mail) {
        this.sysSetting4Mail = sysSetting4Mail;
    }

    public SysSetting4Post getSysSetting4Post() {
        return sysSetting4Post;
    }

    public void setSysSetting4Post(SysSetting4Post sysSetting4Post) {
        this.sysSetting4Post = sysSetting4Post;
    }

    public SysSetting4Register getSysSetting4Register() {
        return sysSetting4Register;
    }

    public void setSysSetting4Register(SysSetting4Register sysSetting4Register) {
        this.sysSetting4Register = sysSetting4Register;
    }
}
