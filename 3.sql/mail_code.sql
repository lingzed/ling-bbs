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

INSERT INTO ling_bbs.mail_code (mail, code, create_time, status, update_time) VALUES ('15828460467@189.cn', 'yNjNx', '2025-01-11 11:09:14', 1, '2025-01-11 11:10:41');
INSERT INTO ling_bbs.mail_code (mail, code, create_time, status, update_time) VALUES ('3515606468@qq.com', 'lnu2z', '2025-03-26 11:12:49', 1, '2025-03-26 11:53:16');
INSERT INTO ling_bbs.mail_code (mail, code, create_time, status, update_time) VALUES ('3515606468@qq.com', 'NzUzT', '2025-03-26 11:52:28', 1, '2025-03-26 11:53:16');
INSERT INTO ling_bbs.mail_code (mail, code, create_time, status, update_time) VALUES ('3515606468@qq.com', 'yWlhU', '2025-03-26 10:53:57', 1, '2025-03-26 11:53:16');
INSERT INTO ling_bbs.mail_code (mail, code, create_time, status, update_time) VALUES ('lingzed@163.com', '6sfWJ', '2025-03-21 10:49:44', 1, '2025-03-21 10:50:02');
INSERT INTO ling_bbs.mail_code (mail, code, create_time, status, update_time) VALUES ('lingzed@163.com', 'B9Yay', '2024-11-16 11:06:37', 1, '2025-03-21 10:50:02');
INSERT INTO ling_bbs.mail_code (mail, code, create_time, status, update_time) VALUES ('lingzed@163.com', 'nLxax', '2024-12-06 11:18:15', 1, '2025-03-21 10:50:02');
INSERT INTO ling_bbs.mail_code (mail, code, create_time, status, update_time) VALUES ('lingzed@163.com', 'OL67F', '2025-03-21 10:45:14', 1, '2025-03-21 10:50:02');
INSERT INTO ling_bbs.mail_code (mail, code, create_time, status, update_time) VALUES ('lingzed@163.com', 'oYeSr', '2024-11-18 16:44:01', 1, '2025-03-21 10:50:02');
INSERT INTO ling_bbs.mail_code (mail, code, create_time, status, update_time) VALUES ('lingzed@163.com', 'P0qXu', '2025-03-21 10:41:56', 1, '2025-03-21 10:50:02');
INSERT INTO ling_bbs.mail_code (mail, code, create_time, status, update_time) VALUES ('lingzed@163.com', 'RktLf', '2024-11-16 10:58:51', 1, '2025-03-21 10:50:02');
INSERT INTO ling_bbs.mail_code (mail, code, create_time, status, update_time) VALUES ('lingzed@163.com', 'TEIsN', '2024-12-06 11:25:26', 1, '2025-03-21 10:50:02');
INSERT INTO ling_bbs.mail_code (mail, code, create_time, status, update_time) VALUES ('lingzed@163.com', 'tuyj9', '2024-11-16 11:05:36', 1, '2025-03-21 10:50:02');
INSERT INTO ling_bbs.mail_code (mail, code, create_time, status, update_time) VALUES ('lingzed@aliyun.com', 'VYDuK', '2025-01-11 11:25:02', 1, '2025-01-11 11:25:58');
INSERT INTO ling_bbs.mail_code (mail, code, create_time, status, update_time) VALUES ('test_demo_ling@163.com', 'hJXgQ', '2025-01-13 19:52:09', 1, '2025-01-13 19:53:25');
