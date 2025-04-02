-- 文章表
create table article
(
    article_id      varchar(15)       not null comment '文章id'
        primary key,
    board_id        int               null comment '板块id',
    board_name      varchar(50)       not null comment '板块名称',
    p_board_id      int               null comment '父级板块id',
    p_board_name    varchar(50)       null comment '父级板块名称',
    avatar          varchar(150)      null comment '头像',
    user_id         varchar(15)       not null comment '用户id',
    nick_name       varchar(20)       not null comment '昵称',
    user_id_address varchar(100)      not null comment '最后登录ip地址',
    title           varchar(150)      not null comment '文章标题',
    cover           varchar(100)      null comment '文章封面',
    content         text              null comment '内容',
    md_content      text              null comment 'markdown格式的内容',
    editor_type     tinyint           null comment '文章编辑器类型, 0: 富文本编辑器, 1: markdown编辑器',
    summary         varchar(200)      null comment '摘要',
    read_count      bigint  default 0 not null comment '浏览量',
    like_count      bigint  default 0 not null comment '点赞量',
    comment_count   bigint  default 0 not null comment '评论数量',
    top_type        tinyint default 0 not null comment '0: 未置顶, 1: 已置顶',
    attachment_type tinyint default 0 not null comment '0: 没有附件, 1: 有附件',
    status          tinyint default 1 not null comment '0: 已删除, 1: 待审核, 2: 已审核',
    create_time     datetime          not null comment '创建/发布时间',
    update_time     datetime          not null comment '编辑时间'
)
    comment '文章表';

create index article_idx_board_id
    on article (board_id);

create index article_idx_create_time
    on article (create_time);

create index article_idx_p_board_id
    on article (p_board_id);

create index article_idx_title
    on article (title);

create index article_idx_top_type
    on article (top_type);

create index article_idx_user_id
    on article (user_id);

-- 附件表
create table attachment
(
    file_id         varchar(150)  not null comment '文件id'
        primary key,
    article_id      varchar(15)   not null comment '文章id',
    user_id         varchar(15)   not null comment '用户id',
    file_size       bigint        not null comment '文件大小',
    filename        varchar(200)  not null comment '文件名',
    download_count  int default 0 null comment '下载次数',
    filepath        varchar(100)  not null comment '文件路径',
    filetype        tinyint       not null comment '文件类型',
    download_points int           not null comment '下载所需积分'
)
    comment '附件表';

create index attachment_article_id_index
    on attachment (article_id)
    comment '文章id索引';

create index attachment_user_id_index
    on attachment (user_id)
    comment '用户id索引';

-- 附件下载记录表
create table attachment_download_record
(
    download_record_id int auto_increment comment '下载记录id'
        primary key,
    article_id         varchar(15) not null comment '文章id',
    downloader_id      varchar(15) not null comment '下载人id',
    download_time      datetime    not null comment '下载时间',
    constraint attachment_download_record_pk
        unique (article_id, downloader_id)
)
    comment '附件下载记录表';

-- 评论表
create table comment
(
    comment_id        int auto_increment comment '评论id'
        primary key,
    p_comment_id      int               null comment '父级评论id',
    article_id        varchar(15)       not null comment '文章id',
    img_path          varchar(150)      null comment '图片路径',
    content           varchar(800)      null comment '评论内容',
    sender_avatar     varchar(150)      null comment '评论发送人头像',
    sender_id         varchar(15)       not null comment '评论发送人id',
    sender_nickname   varchar(50)       not null comment '评论发送人昵称',
    sender_ip_address varchar(150)      not null comment '评论发送人IP地址',
    receiver_id       varchar(15)       null comment '被回复人id',
    receiver_nickname varchar(15)       null comment '被回复人昵称',
    top_type          tinyint default 0 null comment '0: 未置顶, 1: 置顶',
    like_count        bigint  default 0 null comment '点赞量',
    status            tinyint default 1 null comment '0: 已删除, 1: 待审核, 2: 已审核',
    post_time         datetime          not null comment '发布时间'
)
    comment '评论表';

create index comments_article_id_index
    on comment (article_id);

create index comments_p_comment_id_index
    on comment (p_comment_id);

create index comments_post_time_index
    on comment (post_time);

create index comments_sender_id_index
    on comment (sender_id);

create index comments_status_index
    on comment (status);

create index comments_top_type_index
    on comment (top_type);

-- 文件使用记录
create table file_use_record
(
    record_id   varchar(50)  not null comment 'id',
    target_id   varchar(50)  null comment '目标id',
    uploader_id varchar(15)  not null comment '上传人id',
    filename    varchar(50)  null comment '文件名',
    filepath    varchar(150) not null comment '文件路径',
    status      tinyint      null comment '0: 未使用, 1: 已使用 '
)
    comment '文件使用记录';

-- 板块表
create index file_use_record_target_id_index
    on file_use_record (target_id);

create table forum_board
(
    board_id    int auto_increment comment '板块id'
        primary key,
    p_board_id  int               null comment '父级板块id',
    board_name  varchar(50)       null comment '板块名称',
    cover       varchar(50)       null comment '板块封面',
    description varchar(150)      null comment '板块描述',
    sort        int               null comment '排序',
    post_type   tinyint default 1 null comment '0: 只允许管理员发帖, 1: 允许任何人发帖'
)
    comment '论坛板块表';

-- 点赞记录表
create table like_record
(
    like_record_id   int auto_increment comment '记录id'
        primary key,
    target_id        varchar(15) not null comment '目标id, 文章或评论',
    target_author_id varchar(15) not null comment '目标作者id',
    liker_id         varchar(15) not null comment '点赞人id',
    like_type        tinyint     not null comment '点赞类型, 0: 文章点赞, 1: 评论点赞',
    like_time        datetime    not null comment '点赞时间',
    constraint like_recode_pk2
        unique (target_id, liker_id, like_type)
)
    comment '点赞记录表';

create index like_record_liker_id_like_type_target_id_index
    on like_record (liker_id, like_type, target_id);


-- 邮箱验证码
create table mail_code
(
    mail        varchar(150) not null comment '邮箱',
    code        varchar(5)   not null comment '邮箱验证码',
    create_time datetime     not null comment '创建时间',
    status      tinyint      not null comment '0: 未使用, 1: 已使用',
    update_time datetime     not null comment '修改时间',
    constraint mail_code_pk
        unique (mail, code)
)
    comment '邮箱验证码';

-- 系统设置信息表
create table sys_setting
(
    code         varchar(10)  not null comment '编码'
        primary key,
    json_content varchar(500) null comment '设置信息的json字符串',
    create_time  datetime     not null comment '创建时间',
    update_time  datetime     not null comment '修改时间'
)
    comment '系统设置信息表';

-- 用户信息
create table user_info
(
    user_id               varchar(15)   not null comment '用户id'
        primary key,
    avatar                varchar(150)  null comment '头像',
    nick_name             varchar(20)   null comment '昵称',
    email                 varchar(150)  null comment '邮箱',
    password              varchar(50)   null comment '密码',
    gender                tinyint       null comment '0: 女, 1: 男',
    description           varchar(200)  null comment '个人描述',
    join_time             date          null comment '加入时间',
    last_login_time       datetime      null comment '最后登录时间',
    last_login_ip         varchar(15)   null comment '最后登录ip',
    last_login_ip_address varchar(100)  null comment '最后登录ip地址',
    total_integral        int           null comment '总积分',
    current_integral      int           null comment '当前积分',
    status                tinyint       null comment '0: 禁用, 1: 正常',
    create_time           datetime      not null comment '创建时间',
    update_time           datetime      not null comment '修改时间',
    version               int default 0 not null comment '乐观锁',
    is_admin              tinyint       null comment '0: 普通用户, 1: 管理员',
    constraint user_info_pk_2
        unique (email, nick_name)
)
    comment '用户信息';

-- 用户消息
create table user_message
(
    message_id       int auto_increment comment '消息id'
        primary key,
    received_user_id varchar(15)   not null comment '接收人的user_id',
    article_id       varchar(15)   null comment '文章id',
    article_title    varchar(150)  null comment '文章标题',
    comment_id       int           null comment '评论id',
    sender_avatar    varchar(150)  null comment '发送人头像',
    send_user_id     varchar(15)   null comment '发送人的user_id',
    send_nick_name   varchar(20)   null comment '发送人的昵称',
    message_type     tinyint       null comment '消息类型，0：系统消息，1：评论，2：文章点赞，3：评论点赞4：附件下载',
    message_content  varchar(1000) null comment '消息内容',
    status           tinyint       not null comment '消息状态，0：未读，1：已读',
    create_time      datetime      not null comment '创建时间',
    update_time      datetime      not null comment '修改时间',
    constraint user_message_pk_2
        unique (article_id, comment_id, send_user_id, message_type)
)
    comment '用户消息';

create index user_message_message_type_index
    on user_message (message_type);

create index user_message_received_user_id_index
    on user_message (received_user_id);

create index user_message_status_index
    on user_message (status);

-- 用户积分记录表
create table user_points_record
(
    record_id      int auto_increment comment '记录id'
        primary key,
    user_id        varchar(15) not null comment '用户id',
    operation_type tinyint     not null comment '操作类型',
    points         int         not null comment '积分',
    create_time    datetime    not null comment '创建时间',
    update_time    datetime    not null comment '修改时间'
)
    comment '用户积分记录表';


