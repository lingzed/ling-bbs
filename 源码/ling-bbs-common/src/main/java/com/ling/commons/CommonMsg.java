package com.ling.commons;

public class CommonMsg {
    // 请求消息
    public static final String REQUEST_SUCCESS = "请求成功";
    public static final String REQUEST_FAIL = "请求失败";
    public static final String REQUEST_NOT_FOUND = "请求地址不存在";
    public static final String REQUEST_PARAM_ERROR = "请求参数错误";
    public static final String REQUEST_TIMEOUT = "请求超时";

    // 邮箱消息
    public static final String MAIL_EXISTS = "邮箱已存在";
    public static final String MAIL_SEND_FAIL = "邮件发送失败";
    public static final String MAIL_NOT_EXISTS = "邮箱不存在";

    // 业务消息
    public static final String BUSINESS_ERROR = "业务异常";
    public static final String SERVER_ERROR = "服务器返回错误，请联系管理员";
    public static final String REQUEST_ALREADY_EXISTS = "信息已存在";

    // 认证相关消息
    public static final String INVALID_TOKEN = "无效的token";
    public static final String SESSION_EXPIRED = "会话已过期，请重新登录";
    public static final String TOKEN_EXPIRED = "token已过期，请重新登录";
    public static final String USERNAME_OR_PASSWORD_ERROR = "用户名或密码错误";
    public static final String USER_DISABLED = "用户被禁用";

    // 文件相关消息
    public static final String FILE_TOO_LARGE = "文件过大";
    public static final String INVALID_FILE_FORMAT = "无效的文件格式";
    public static final String FILE_NOT_FOUND = "文件不存在";
    public static final String FILE_UPLOAD_SUCCESS = "文件上传成功";
    public static final String FILE_UPLOAD_FAIL = "文件上传失败";
    public static final String FILE_DOWNLOAD_SUCCESS = "文件下载成功";
    public static final String FILE_DOWNLOAD_FAIL = "文件下载失败";
    public static final String UNSUPPORTED_FILE_FORMAT = "不支持的文件格式";
    public static final String UNSUPPORTED_IMAGE_FORMAT = "不支持的图片格式";
    public static final String FILE_READ_FAIL = "文件读取失败";

    // 权限相关消息
    public static final String PERMISSION_DENIED = "权限不足";
    public static final String ACCESS_DENIED = "访问被拒绝";

    // 成功/失败消息
    public static final String OPERATION_SUCCESS = "操作成功";
    public static final String OPERATION_FAIL = "操作失败";
    public static final String QUERY_SUCCESS = "查询成功";
    public static final String QUERY_FAIL = "查询失败";
    public static final String ADD_SUCCESS = "添加成功";
    public static final String ADD_FAIL = "添加失败";
    public static final String EDIT_SUCCESS = "编辑成功";
    public static final String EDIT_FAIL = "编辑失败";
    public static final String DELETE_SUCCESS = "删除成功";
    public static final String DELETE_FAIL = "删除失败";
    public static final String UPLOAD_SUCCESS = "上传成功";
    public static final String UPLOAD_FAIL = "上传失败";
    public static final String POINTS_OPERATION_FAIL = "积分操作失败";
    public static final String REFRESH_CACHE_FAIL = "刷新缓存失败";
    public static final String FETCH_FAIL = "获取失败";
    public static final String LOGIN_FAIL = "登录失败";
    public static final String RESET_PWD_FAIL = "重置密码失败";

    // 验证码消息
    public static final String CHECK_CODE_ERROR = "图片验证码错误";
    public static final String CHECK_CODE_EXPIRED = "图片验证码已过期";
    public static final String MAIL_CHECK_CODE_ERROR = "邮箱验证码错误";
    public static final String MAIL_CHECK_CODE_EXPIRED = "邮箱验证码已过期";

    public static final String ARTICLE_NOT_EXISTS = "文章不存在";
    public static final String COMMENT_NOT_EXISTS = "评论不存在";
    public static final String REPLY_COMMENT_NOT_EXISTS = "回复的评论不存在";
    public static final String ONLY_LIKE_AUDITED_ARTICLE = "只能点赞已审核文章";
    public static final String ONLY_LIKE_AUDITED_COMMENT = "只能点赞已审核评论";
    public static final String INSUFFICIENT_POINTS = "积分不足";
    public static final String ONLY_AUTHOR_TOP_COMMENT = "只有作者才能置顶评论";
    public static final String UNOPENED_COMMENT = "未开启评论";
}
