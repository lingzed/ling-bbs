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

INSERT INTO ling_bbs.forum_board (board_id, p_board_id, board_name, cover, description, sort, post_type) VALUES (1, 0, '游戏', null, '这是游戏板块', 1, 1);
INSERT INTO ling_bbs.forum_board (board_id, p_board_id, board_name, cover, description, sort, post_type) VALUES (2, 1, '游戏咨询', null, '最前沿的游戏咨询在这里', null, 1);
INSERT INTO ling_bbs.forum_board (board_id, p_board_id, board_name, cover, description, sort, post_type) VALUES (3, 1, '游戏攻略', null, '最好的游戏攻略', null, 1);
INSERT INTO ling_bbs.forum_board (board_id, p_board_id, board_name, cover, description, sort, post_type) VALUES (4, 0, '动漫', null, '动漫板块', 2, 1);
INSERT INTO ling_bbs.forum_board (board_id, p_board_id, board_name, cover, description, sort, post_type) VALUES (5, 4, '新番导视', null, '这里是新番宇宙的导航塔，为你精准捕捉每一帧高光，无论是霸权续作、黑马新番，还是小众宝藏，用最犀利的眼光带你预判 “必追清单”。', null, 1);
INSERT INTO ling_bbs.forum_board (board_id, p_board_id, board_name, cover, description, sort, post_type) VALUES (6, 0, '论坛管理', null, '论坛规则看这里', 4, 0);
INSERT INTO ling_bbs.forum_board (board_id, p_board_id, board_name, cover, description, sort, post_type) VALUES (7, 0, '美食', null, '舌尖上的世界', 3, 1);
INSERT INTO ling_bbs.forum_board (board_id, p_board_id, board_name, cover, description, sort, post_type) VALUES (8, 7, '美食制作', null, '不会做菜？手残党必看', null, 1);
INSERT INTO ling_bbs.forum_board (board_id, p_board_id, board_name, cover, description, sort, post_type) VALUES (9, 7, '美食测评', null, '做个吃货，品世间美味', null, 1);
INSERT INTO ling_bbs.forum_board (board_id, p_board_id, board_name, cover, description, sort, post_type) VALUES (10, 7, '美食记录', null, '记录一日三餐，给生活添一点幸福', null, 1);
INSERT INTO ling_bbs.forum_board (board_id, p_board_id, board_name, cover, description, sort, post_type) VALUES (11, 6, '论坛规则', null, '遵守论坛规则，建立友好的讨论环境', null, 0);
