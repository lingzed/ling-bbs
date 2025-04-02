package com.ling.enums;

/**
 * 设置项枚举
 */
public enum SysSettingItemEnum {
    AUDIT("audit", "com.ling.entity.dto.SysSetting4Audit", "sysSetting4Audit"),
    COMMENT("comment", "com.ling.entity.dto.SysSetting4Comment", "sysSetting4Comment"),
    LIKE("like", "com.ling.entity.dto.SysSetting4Like", "sysSetting4Like"),
    MAIL("mail", "com.ling.entity.dto.SysSetting4Mail", "sysSetting4Mail"),
    POST("post", "com.ling.entity.dto.SysSetting4Post", "sysSetting4Post"),
    REGISTER("register", "com.ling.entity.dto.SysSetting4Register", "sysSetting4Register");

    private String code;        // 设置项编码
    private String classname;   // 设置项类全类名
    private String field;       // 系统设置容器类的字段

    SysSettingItemEnum() {
    }

    SysSettingItemEnum(String code, String classname, String field) {
        this.code = code;
        this.classname = classname;
        this.field = field;
    }

    /**
     * 通过code获取枚举项
     *
     * @param code
     * @return
     */
    public static SysSettingItemEnum getItemByCode(String code) {
        for (SysSettingItemEnum item : SysSettingItemEnum.values()) {
            if (code.equals(item.getCode())) return item;
        }
        return null;
    }

    /**
     * 获取
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取
     *
     * @return classname
     */
    public String getClassname() {
        return classname;
    }

    /**
     * 获取
     *
     * @return desc
     */
    public String getField() {
        return field;
    }
}
