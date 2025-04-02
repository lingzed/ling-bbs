package com.ling.constant;

/**
 * 常量类
 */
public class Constant {
    // 字符串常量
    public static final String UNKNOWN = "未知";
    public static final String ARTICLE_LIKE_MESSAGE_CONTENT = "点赞了您的文章%s";
    public static final String COMMENT_LIKE_MESSAGE_CONTENT = "点赞了您的评论%s";
    public static final String DOWNLOAD_MESSAGE_CONTENT = "下载了您的文章%s的附件";
    public static final String ARTICLE_COMMENT_MESSAGE_CONTENT = "评论了您的文章%s";
    public static final String BACK_COMMENT_MESSAGE_CONTENT = "回复了您的评论%s";
    public static final String DEL_ARTICLE_MESSAGE_CONTENT = "管理员删除了您的文章【%s】";
    public static final String AUDIT_ARTICLE_MESSAGE_CONTENT = "您的文章%s已审核";
    public static final String ZIP_FILENAME = "附件%s.zip";

    // session常量
    public static final String USERINFO_SESSION_KEY = "USERINFO_SESSION_KEY";   // 用户信息session-key
    public static final String ADMIN_SESSION_KEY = "ADMIN_SESSION_KEY";   // 管理员信息session-key

    // 图片验证码常量
    public static final String CHECK_CODE = "CHECK_CODE";               // 图片验证码key
    public static final String CHECK_CODE_EMAIL = "CHECK_CODE_EMAIL";   // 发送邮箱的图片验证码key

    // 数字常量
    public static final int NUM_0 = 0;
    public static final int NUM_1 = 1;
    public static final int NUM_2 = 2;
    public static final int NUM_3 = 3;
    public static final int NUM_5 = 5;
    public static final int NUM_10 = 10;
    public static final int NUM_15 = 15;
    public static final int NUM_NEG_1 = -1;

    // 时间常量 (以毫秒为单位)
    public static final long MILLIS_1 = 1000L;               // 1毫秒
    public static final long MIN_1_TO_MILLIS = 60000L;     // 1分钟毫秒值
    public static final long MIN_2_TO_MILLIS = 120000L;     // 2分钟毫秒值
    public static final long MIN_3_TO_MILLIS = 180000L;     // 3分钟毫秒值
    public static final long MIN_5_TO_MILLIS = 300000L;     // 5分钟毫秒值
    public static final long MIN_10_TO_MILLIS = 600000;    // 10分钟毫秒值
    public static final long MIN_15_TO_MILLIS = 900000L;    // 15分钟毫秒值

    // 小时和天的时间常量
    public static final long HOUR_1_TO_MILLIS = 3600000L;   // 1小时毫秒值
    public static final long DAY_1_TO_MILLIS = 86400000L;   // 1天毫秒值
    public static final long WEEK_1_TO_MILLIS = 604800000L; // 1周毫秒值

    // 字节大小常量
    public static final long BYTE = 1L;             // 字节
    public static final long KB = 1024L;            // 千字节
    public static final long MB = 1048576L;         // 兆字节
    public static final long GB = 1073741824L;      // 吉字节
    public static final long TB = 1099511627776L;   // 太字节

    // 文件扩展名常量
    public static final String FILE_EXTENSION_TXT = ".txt";   // 文本文件扩展名
    public static final String FILE_EXTENSION_PDF = ".pdf";   // PDF文件扩展名
    public static final String FILE_EXTENSION_JPG = ".jpg";   // 图片文件扩展名
    public static final String FILE_EXTENSION_PNG = ".png";   // 图片文件扩展名

    // 文件路径常量
    public static final String RESOURCE_DIR_ARTICLE = "article";      // 资源目录-文图
    public static final String RESOURCE_DIR_ATTACHMENT = "attachment";      // 资源目录-附件

    // 请求/响应头常量
    public static final String IMAGE_ID_HEADER = "image-id";    // 图片id
    public static final String CACHE_CONTROL = "Cache-Control";    // 缓存控制
    public static final String CONTENT_LENGTH = "Content-Length";    // 内容大小

    public static final String WEB_SERVER_NAME = "LingBBS-Web-Server";    // web端服务器名称
    public static final String WEB_CLIENT_NAME = "LingBBS-Web-Client";    // web端客户端名称
    public static final String ADMIN_SERVER_NAME = "LingBBS-Admin-Server";    // admin端服务器名称
    public static final String ADMIN_CLIENT_NAME = "LingBBS-Admin-Client";    // admin端客户端名称

    private Constant() {

    }
}
