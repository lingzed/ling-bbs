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

INSERT INTO ling_bbs.like_record (like_record_id, target_id, target_author_id, liker_id, like_type, like_time) VALUES (8, '52nRPHv9Ul', '7878970085', '9619980088', 0, '2025-01-13 19:34:03');
INSERT INTO ling_bbs.like_record (like_record_id, target_id, target_author_id, liker_id, like_type, like_time) VALUES (9, 'uukicbc29eqo', '9619980088', '9619980088', 0, '2025-01-13 19:35:47');
INSERT INTO ling_bbs.like_record (like_record_id, target_id, target_author_id, liker_id, like_type, like_time) VALUES (10, 'abcdefg', '9876175182', '9619980088', 0, '2025-01-13 19:40:09');
INSERT INTO ling_bbs.like_record (like_record_id, target_id, target_author_id, liker_id, like_type, like_time) VALUES (11, '2', '9876175182', '9619980088', 1, '2025-01-21 15:13:50');
INSERT INTO ling_bbs.like_record (like_record_id, target_id, target_author_id, liker_id, like_type, like_time) VALUES (28, '1', '9619980088', '9619980088', 1, '2025-01-23 19:54:30');
INSERT INTO ling_bbs.like_record (like_record_id, target_id, target_author_id, liker_id, like_type, like_time) VALUES (34, '41', '9619980088', '9619980088', 1, '2025-02-20 10:15:51');
INSERT INTO ling_bbs.like_record (like_record_id, target_id, target_author_id, liker_id, like_type, like_time) VALUES (35, '39', '2550515268', '9619980088', 1, '2025-02-20 10:17:28');
INSERT INTO ling_bbs.like_record (like_record_id, target_id, target_author_id, liker_id, like_type, like_time) VALUES (38, '000981283431340', '9876175182', '9619980088', 0, '2025-03-26 16:18:24');
INSERT INTO ling_bbs.like_record (like_record_id, target_id, target_author_id, liker_id, like_type, like_time) VALUES (39, 'uukicbc29eqo', '9619980088', '1835487851', 0, '2025-03-26 19:49:30');
INSERT INTO ling_bbs.like_record (like_record_id, target_id, target_author_id, liker_id, like_type, like_time) VALUES (42, '41', '9619980088', '1835487851', 1, '2025-03-26 19:57:51');
INSERT INTO ling_bbs.like_record (like_record_id, target_id, target_author_id, liker_id, like_type, like_time) VALUES (43, '52nRPHv9Ul', '7878970085', '1835487851', 0, '2025-03-27 23:03:53');
INSERT INTO ling_bbs.like_record (like_record_id, target_id, target_author_id, liker_id, like_type, like_time) VALUES (44, '645476867433817', '0000000000', '1835487851', 0, '2025-03-27 23:08:52');
INSERT INTO ling_bbs.like_record (like_record_id, target_id, target_author_id, liker_id, like_type, like_time) VALUES (47, '68', '9619980088', '1835487851', 1, '2025-03-27 23:12:05');
