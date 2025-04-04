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

INSERT INTO ling_bbs.article (article_id, board_id, board_name, p_board_id, p_board_name, avatar, user_id, nick_name, user_id_address, title, cover, content, md_content, editor_type, summary, read_count, like_count, comment_count, top_type, attachment_type, status, create_time, update_time) VALUES ('000981283431340', 2, '游戏咨询', 1, '游戏', null, '9876175182', 'user_189', '四川省', '波兰蠢驴新作《巫师4》最新咨询', null, '在最近《巫师4》的最新宣传片中，部分玩家却对希里的外貌提出了质疑，认为她看起来更老或&ldquo;变丑了&rdquo;，今日CDPR《巫师》制作团队成员Sebastian Kalemba在推特上发文，澄清了视频中希里&ldquo;并没有修改脸部造型&rdquo;，只是因为视频制作问题才看起来不太一样。
/web/file/read/article/2025-03/f38658ca2d7b44fd8a07a23c76a82a22.jpg
他表示：&ldquo;幕后视频中的希里造型与原版预告片中的相同，我们没有做过任何修改。只是幕后视频中大家看到的是原始素材，没有面部动画、灯光或虚拟镜头。虽然它仍然是在引擎中，但它是我们在制作电影化预告片之前展示的一个开发中的快照。这种变化是游戏开发过程中很自然的一部分。在这一点上，任何角色的外观都可能因媒介而异&mdash;&mdash;无论是在预告片、3D模型还是游戏中。&rdquo;
/web/file/read/article/2025-03/f70a5395a4464bb887646e7e60e8fcf5.jpg

', null, 0, '在最近《巫师4》的最新宣传片中，部分玩家却对希里的外貌提出了质疑，认为她看起来更老或&ldquo;变丑了&rdquo;', 20, 1, 0, 0, 1, 2, '2025-03-01 18:11:46', '2025-03-31 15:22:13');
INSERT INTO ling_bbs.article (article_id, board_id, board_name, p_board_id, p_board_name, avatar, user_id, nick_name, user_id_address, title, cover, content, md_content, editor_type, summary, read_count, like_count, comment_count, top_type, attachment_type, status, create_time, update_time) VALUES ('049091206880531', 5, '新番导视', 4, '动漫', 'avatar/2025-03/31f311b3a680488eafce05a41d54e575.jpg', '1835487851', 'su7 Ultra', '四川省', '测试文章2', null, '富文本测试内容2', 'markdown测试内容2', 1, '测试描述2', 0, 0, 0, 0, 0, 2, '2025-04-01 20:04:01', '2025-04-01 20:05:47');
INSERT INTO ling_bbs.article (article_id, board_id, board_name, p_board_id, p_board_name, avatar, user_id, nick_name, user_id_address, title, cover, content, md_content, editor_type, summary, read_count, like_count, comment_count, top_type, attachment_type, status, create_time, update_time) VALUES ('173568352430026', 7, '美食', 0, null, 'avatar/2025-04/aa21ac441ca44890932c9d987a3d060e.jpg', '9619980088', 'ling', '四川省', '《辣椒如何俘获中国人的胃》', 'cover/2025-03/576cee8ac4ab44a889445eb68b6ebbe2.jpg', '&lt;h1&gt;辣椒，豪赤&lt;/h1&gt;
都给我去吃辣椒', '#辣椒，豪赤
都给我去吃辣椒', 1, '辣椒的征服历史', 0, 0, 0, 0, 0, 1, '2025-03-03 13:40:35', '2025-03-31 19:39:49');
INSERT INTO ling_bbs.article (article_id, board_id, board_name, p_board_id, p_board_name, avatar, user_id, nick_name, user_id_address, title, cover, content, md_content, editor_type, summary, read_count, like_count, comment_count, top_type, attachment_type, status, create_time, update_time) VALUES ('249122878053080', 3, '游戏攻略', 1, '游戏', 'avatar/2025-03/31f311b3a680488eafce05a41d54e575.jpg', '1835487851', 'su7 Ultra', '四川省', '41231231', null, '哈哈哈哈', null, 0, '啊是巨大接收到i就是', 0, 0, 0, 0, 0, 1, '2025-03-27 22:48:52', '2025-03-27 22:48:52');
INSERT INTO ling_bbs.article (article_id, board_id, board_name, p_board_id, p_board_name, avatar, user_id, nick_name, user_id_address, title, cover, content, md_content, editor_type, summary, read_count, like_count, comment_count, top_type, attachment_type, status, create_time, update_time) VALUES ('315280559832117', 3, '游戏攻略', 1, '游戏', 'avatar/2025-03/31f311b3a680488eafce05a41d54e575.jpg', '1835487851', 'su7 Ultra', '四川省', '神作-塞尔达时之笛', 'cover/2025-03/8577d09604d648e09606634433e32376.jpg', '在游戏历史上出现过众多经典作品，但能被广泛用户认可为&ldquo;神作&rdquo;的寥寥无几，尤其是文化口味存在明显差异的东西方玩家，想要达成共识更是难上加难。

在上个世纪网络信息并不发达的环境下，游戏媒体的评价有着很强的权威性，本期我们回顾的，则是被众多媒体给出满分评价，对于玩家体验有着颠覆性突破的神作，也是全世界玩家公认的顶级神作&mdash;&mdash;《塞尔达传说 时之笛》（The Legend of Zelda Ocarina of Time）。
localhost:8091/web/file/read/article/2025-03/43322ef1219d494bb68ec76d4f1de81c.webp

时间回到上世纪九十年代中期，随着索尼PS和世嘉SS先后推出，家用游戏主机进入到全新的时代，3D多边形画面处理能力，以及远超以往卡带的大容量光盘，让开发者有了更多施展才华的空间。然而在之前十几年的游戏发展历史中，2D平面作品占据了绝对主流，无论是以街机平台为核心的动作射击玩法，还是在家用机平台得到长足发展的RPG玩法，卷轴式画面几乎是唯一的选项，无论横向还是俯视角，玩家只能在预设的视角体验，无法以较高的自由度探索游戏世界。

当年新的主机发售后，玩家和厂商对于3D游戏体验进行了广泛探索，然而受限于设计理念，硬件设备并没有完全发挥出3D特点。最典型的例子就是，PS和SS最初的手柄，仍然延续了经典的单独方向键+若干功能按键组合，这就导致玩家只能控制人物移动，不能自由选择视角，最多是在设计好的固定方向上进行简单的旋转缩放操作。再加上当年多边形处理能力有限，全3D场景建模粗糙锯齿明显，实际效果并不理想，厂商通过加入动画CG展现剧情，这也导致游戏中人物表现和CG有着明显差距，就像现在的网红&ldquo;照骗&rdquo;。

localhost:8091/web/file/read/article/2025-03/4051819cbb954d658dbc86959ec146e2.webp', '在游戏历史上出现过众多经典作品，但能被广泛用户认可为&ldquo;神作&rdquo;的寥寥无几，尤其是文化口味存在明显差异的东西方玩家，想要达成共识更是难上加难。

在上个世纪网络信息并不发达的环境下，游戏媒体的评价有着很强的权威性，本期我们回顾的，则是被众多媒体给出满分评价，对于玩家体验有着颠覆性突破的神作，也是全世界玩家公认的顶级神作&mdash;&mdash;《塞尔达传说 时之笛》（The Legend of Zelda Ocarina of Time）。
localhost:8091/web/file/read/article/2025-03/43322ef1219d494bb68ec76d4f1de81c.webp

时间回到上世纪九十年代中期，随着索尼PS和世嘉SS先后推出，家用游戏主机进入到全新的时代，3D多边形画面处理能力，以及远超以往卡带的大容量光盘，让开发者有了更多施展才华的空间。然而在之前十几年的游戏发展历史中，2D平面作品占据了绝对主流，无论是以街机平台为核心的动作射击玩法，还是在家用机平台得到长足发展的RPG玩法，卷轴式画面几乎是唯一的选项，无论横向还是俯视角，玩家只能在预设的视角体验，无法以较高的自由度探索游戏世界。

当年新的主机发售后，玩家和厂商对于3D游戏体验进行了广泛探索，然而受限于设计理念，硬件设备并没有完全发挥出3D特点。最典型的例子就是，PS和SS最初的手柄，仍然延续了经典的单独方向键+若干功能按键组合，这就导致玩家只能控制人物移动，不能自由选择视角，最多是在设计好的固定方向上进行简单的旋转缩放操作。再加上当年多边形处理能力有限，全3D场景建模粗糙锯齿明显，实际效果并不理想，厂商通过加入动画CG展现剧情，这也导致游戏中人物表现和CG有着明显差距，就像现在的网红&ldquo;照骗&rdquo;。

localhost:8091/web/file/read/article/2025-03/4051819cbb954d658dbc86959ec146e2.webp', 1, '关于时之笛的一些事情', 0, 0, 0, 0, 0, 1, '2025-03-26 17:01:24', '2025-03-26 17:06:42');
INSERT INTO ling_bbs.article (article_id, board_id, board_name, p_board_id, p_board_name, avatar, user_id, nick_name, user_id_address, title, cover, content, md_content, editor_type, summary, read_count, like_count, comment_count, top_type, attachment_type, status, create_time, update_time) VALUES ('341173206094698', 8, '美食制作', 7, '美食', 'avatar/2025-04/aa21ac441ca44890932c9d987a3d060e.jpg', '9619980088', 'ling', '四川省', '蒜苗炒腊肉', null, '今天教大家制作一道家常菜，蒜苗炒腊肉，后附图片', '', 0, '美食制作之蒜苗炒腊肉', 0, 0, 0, 0, 0, 1, '2025-03-08 11:38:04', '2025-03-08 11:47:20');
INSERT INTO ling_bbs.article (article_id, board_id, board_name, p_board_id, p_board_name, avatar, user_id, nick_name, user_id_address, title, cover, content, md_content, editor_type, summary, read_count, like_count, comment_count, top_type, attachment_type, status, create_time, update_time) VALUES ('354240285003942', 3, '游戏攻略', 1, '游戏', 'avatar/2025-04/aa21ac441ca44890932c9d987a3d060e.jpg', '9619980088', 'ling', '四川省', '测试文章1', null, '内容1', null, 0, '描述1', 0, 0, 0, 0, 0, 0, '2025-04-01 19:54:56', '2025-04-01 20:09:05');
INSERT INTO ling_bbs.article (article_id, board_id, board_name, p_board_id, p_board_name, avatar, user_id, nick_name, user_id_address, title, cover, content, md_content, editor_type, summary, read_count, like_count, comment_count, top_type, attachment_type, status, create_time, update_time) VALUES ('486621472277387', 3, '游戏攻略', 1, '游戏', 'avatar/2025-03/31f311b3a680488eafce05a41d54e575.jpg', '1835487851', 'su7 Ultra', '四川省', '1231232', null, '12312321312', null, 0, '123123123', 0, 0, 0, 0, 0, 1, '2025-03-27 22:48:02', '2025-03-27 22:48:02');
INSERT INTO ling_bbs.article (article_id, board_id, board_name, p_board_id, p_board_name, avatar, user_id, nick_name, user_id_address, title, cover, content, md_content, editor_type, summary, read_count, like_count, comment_count, top_type, attachment_type, status, create_time, update_time) VALUES ('52nRPHv9Ul', 5, '新番导视', 4, '动漫', null, '7878970085', 'user_aliyun', '四川省', '京东埃及动物', null, '这是HTML内容', null, 0, '法老的猫', 20, 2, 20, 0, 0, 2, '2025-01-11 11:31:55', '2025-04-02 16:12:13');
INSERT INTO ling_bbs.article (article_id, board_id, board_name, p_board_id, p_board_name, avatar, user_id, nick_name, user_id_address, title, cover, content, md_content, editor_type, summary, read_count, like_count, comment_count, top_type, attachment_type, status, create_time, update_time) VALUES ('645476867433817', 11, '论坛规则', 6, '论坛管理', null, '0000000000', 'admin', '四川省', '社区规则', null, '第一条（制定依据）  
根据《中华人民共和国网络安全法》第24条、《网络信息内容生态治理规定》第6条，结合本社区实际情况制定本规范。  

第二条（适用范围）  
适用于所有注册用户（含游客模式访问者），包括但不限于：  
&radic; 图文内容发布  
&radic; 直播/短视频互动  
&radic; 私信及群组通讯', null, 0, '社区规章制度，请严格遵守', 3, 1, 0, 0, 0, 2, '2025-03-03 14:43:16', '2025-03-03 14:43:16');
INSERT INTO ling_bbs.article (article_id, board_id, board_name, p_board_id, p_board_name, avatar, user_id, nick_name, user_id_address, title, cover, content, md_content, editor_type, summary, read_count, like_count, comment_count, top_type, attachment_type, status, create_time, update_time) VALUES ('961422669144116', 1, '游戏', 0, null, 'avatar/2025-04/aa21ac441ca44890932c9d987a3d060e.jpg', '9619980088', 'ling', '四川省', '测试文章1_v1', '拉按司岙少东家', '内容1_v1', '内容1_v1', 1, '摘要1_v1', 0, 0, 0, 0, 0, 2, '2025-03-13 11:24:56', '2025-04-02 23:38:20');
INSERT INTO ling_bbs.article (article_id, board_id, board_name, p_board_id, p_board_name, avatar, user_id, nick_name, user_id_address, title, cover, content, md_content, editor_type, summary, read_count, like_count, comment_count, top_type, attachment_type, status, create_time, update_time) VALUES ('ababa', 2, '游戏咨询', 1, '游戏', 'avatar/2025-04/aa21ac441ca44890932c9d987a3d060e.jpg', '9619980088', 'ling', '四川省', 'Switch2新消息', null, '这是HTML内容', '这是md内容', 1, '2025年Switch2全新消息', 0, 0, 6, 0, 1, 1, '2025-01-08 12:02:59', '2025-01-08 12:03:00');
INSERT INTO ling_bbs.article (article_id, board_id, board_name, p_board_id, p_board_name, avatar, user_id, nick_name, user_id_address, title, cover, content, md_content, editor_type, summary, read_count, like_count, comment_count, top_type, attachment_type, status, create_time, update_time) VALUES ('abcdefg', 8, '美食制作', 7, '美食', null, '9876175182', 'user_189', '四川省', '担担面制作', null, '这是HTML内容', '这是md内容', 1, '今天教大学制作担担面', 3, 1, 9, 0, 0, 2, '2025-01-08 12:08:36', '2025-01-08 12:08:38');
INSERT INTO ling_bbs.article (article_id, board_id, board_name, p_board_id, p_board_name, avatar, user_id, nick_name, user_id_address, title, cover, content, md_content, editor_type, summary, read_count, like_count, comment_count, top_type, attachment_type, status, create_time, update_time) VALUES ('uukicbc29eqo', 3, '游戏攻略', 1, '游戏', 'avatar/2025-04/aa21ac441ca44890932c9d987a3d060e.jpg', '9619980088', 'ling', '四川省', '巫师3攻略1', null, '这是html内容', null, 0, '巫师3良心攻略', 13, 2, 29, 1, 1, 2, '2025-01-06 16:38:14', '2025-03-07 21:31:53');
