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

INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (3, '9619980088', 1, 5, '2024-12-06 11:26:53', '2024-12-06 11:26:53');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (4, '9876175182', 1, 5, '2025-01-11 11:10:41', '2025-01-11 11:10:41');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (5, '7878970085', 1, 5, '2025-01-11 11:25:58', '2025-01-11 11:25:58');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (6, '2550515268', 1, 5, '2025-01-13 19:53:25', '2025-01-13 19:53:25');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (32, '9876175182', 2, -5, '2025-01-15 19:57:53', '2025-01-15 19:57:53');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (33, '9619980088', 3, 5, '2025-01-15 19:57:53', '2025-01-15 19:57:53');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (34, '9876175182', 2, -2, '2025-01-16 21:26:35', '2025-01-16 21:26:35');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (35, '9619980088', 3, 2, '2025-01-16 21:26:35', '2025-01-16 21:26:35');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (37, '9619980088', 4, 1, '2025-02-19 17:20:18', '2025-02-19 17:20:18');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (38, '9619980088', 4, 3, '2025-02-20 21:29:14', '2025-02-20 21:29:14');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (39, '9619980088', 4, 3, '2025-02-20 21:32:15', '2025-02-20 21:32:15');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (40, '9619980088', 4, 3, '2025-02-20 21:32:39', '2025-02-20 21:32:39');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (41, '9619980088', 4, 3, '2025-02-20 21:34:11', '2025-02-20 21:34:11');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (42, '9619980088', 4, 3, '2025-02-20 21:36:18', '2025-02-20 21:36:18');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (43, '9619980088', 4, 3, '2025-02-20 21:38:30', '2025-02-20 21:38:30');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (44, '9619980088', 4, 3, '2025-02-20 21:42:26', '2025-02-20 21:42:26');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (45, '9619980088', 4, 3, '2025-02-20 21:51:52', '2025-02-20 21:51:52');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (46, '9619980088', 4, 3, '2025-02-20 22:52:41', '2025-02-20 22:52:41');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (47, '9619980088', 4, 3, '2025-02-20 23:01:17', '2025-02-20 23:01:17');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (48, '9619980088', 4, 3, '2025-02-20 23:02:42', '2025-02-20 23:02:42');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (49, '9619980088', 4, 3, '2025-02-20 23:03:06', '2025-02-20 23:03:06');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (50, '9619980088', 4, 3, '2025-02-20 23:07:38', '2025-02-20 23:07:38');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (51, '7878970085', 4, 3, '2025-02-21 09:50:39', '2025-02-21 09:50:39');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (52, '7878970085', 4, 3, '2025-02-21 09:55:58', '2025-02-21 09:55:58');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (53, '9876175182', 4, 3, '2025-02-21 09:59:02', '2025-02-21 09:59:02');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (54, '7878970085', 4, 3, '2025-02-21 10:01:27', '2025-02-21 10:01:27');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (55, '9619980088', 4, 3, '2025-02-21 10:09:45', '2025-02-21 10:09:45');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (56, '9876175182', 4, 3, '2025-02-21 10:17:40', '2025-02-21 10:17:40');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (61, '0000000000', 2, -5, '2025-03-01 17:05:08', '2025-03-01 17:05:08');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (62, '9619980088', 3, 5, '2025-03-01 17:05:08', '2025-03-01 17:05:08');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (63, '0000000000', 2, -2, '2025-03-01 17:09:12', '2025-03-01 17:09:12');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (64, '9619980088', 3, 2, '2025-03-01 17:09:12', '2025-03-01 17:09:12');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (65, '9876175182', 5, 3, '2025-03-01 18:11:46', '2025-03-01 18:11:46');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (66, '0000000000', 5, 3, '2025-03-03 14:12:21', '2025-03-03 14:12:21');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (67, '0000000000', 5, 3, '2025-03-03 14:12:46', '2025-03-03 14:12:46');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (68, '0000000000', 5, 3, '2025-03-03 14:18:07', '2025-03-03 14:18:07');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (69, '0000000000', 5, 3, '2025-03-03 14:43:16', '2025-03-03 14:43:16');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (72, '9619980088', 2, -4, '2025-03-07 16:03:06', '2025-03-07 16:03:06');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (73, '9876175182', 3, 4, '2025-03-07 16:03:06', '2025-03-07 16:03:06');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (74, '2550515268', 2, -5, '2025-03-07 16:23:19', '2025-03-07 16:23:19');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (75, '9876175182', 3, 5, '2025-03-07 16:23:19', '2025-03-07 16:23:19');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (76, '1835487851', 1, 5, '2025-03-26 10:58:51', '2025-03-26 10:58:51');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (77, '1835487851', 2, -5, '2025-03-26 19:44:23', '2025-03-26 19:44:23');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (78, '9619980088', 3, 5, '2025-03-26 19:44:23', '2025-03-26 19:44:23');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (79, '1835487851', 4, 3, '2025-03-26 20:00:32', '2025-03-26 20:00:32');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (80, '1835487851', 4, 3, '2025-03-26 20:01:55', '2025-03-26 20:01:55');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (81, '1835487851', 4, 3, '2025-03-26 20:06:13', '2025-03-26 20:06:13');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (82, '9619980088', 4, 3, '2025-03-26 20:20:58', '2025-03-26 20:20:58');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (83, '1835487851', 4, 3, '2025-03-27 23:13:32', '2025-03-27 23:13:32');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (84, '1835487851', 4, 3, '2025-03-27 23:14:10', '2025-03-27 23:14:10');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (85, '1835487851', 4, 3, '2025-03-27 23:17:21', '2025-03-27 23:17:21');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (86, '9619980088', 5, 5, '2025-04-01 19:56:26', '2025-04-01 19:56:26');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (87, '1835487851', 5, 5, '2025-04-01 20:05:47', '2025-04-01 20:05:47');
INSERT INTO ling_bbs.user_points_record (record_id, user_id, operation_type, points, create_time, update_time) VALUES (88, '9619980088', 5, 5, '2025-04-02 23:38:20', '2025-04-02 23:38:20');
