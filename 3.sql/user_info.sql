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

INSERT INTO ling_bbs.user_info (user_id, avatar, nick_name, email, password, gender, description, join_time, last_login_time, last_login_ip, last_login_ip_address, total_integral, current_integral, status, create_time, update_time, version, is_admin) VALUES ('0000000000', null, 'admin', 'admin@test.com', '0192023a7bbd73250516f069df18b500', 2, '管理员', '2024-11-01', '2025-04-01 10:17:22', '0:0:0:0:0:0:0:1', '四川省', 112, 105, 1, '2025-01-11 11:53:30', '2025-04-01 10:17:22', 6, 1);
INSERT INTO ling_bbs.user_info (user_id, avatar, nick_name, email, password, gender, description, join_time, last_login_time, last_login_ip, last_login_ip_address, total_integral, current_integral, status, create_time, update_time, version, is_admin) VALUES ('0000000001', null, 'demo_admin', 'admin@demo.com', 'f90b2ad670f653f69d676e34838d4b6a', 2, '管理员', '2024-11-01', '2025-01-14 10:52:49', '0:0:0:0:0:0:0:1', '四川省', 0, 0, 1, '2025-01-14 10:53:05', '2025-01-14 10:53:07', 0, 1);
INSERT INTO ling_bbs.user_info (user_id, avatar, nick_name, email, password, gender, description, join_time, last_login_time, last_login_ip, last_login_ip_address, total_integral, current_integral, status, create_time, update_time, version, is_admin) VALUES ('1835487851', 'avatar/2025-03/31f311b3a680488eafce05a41d54e575.jpg', 'su7 Ultra', '3515606468@qq.com', '240a3eac959167a86396de3ed6275c10', 2, '沉默安济岛开普请客哦', '2025-03-26', '2025-04-01 20:02:07', '0:0:0:0:0:0:0:1', '四川省', 28, 23, 1, '2025-03-26 10:58:51', '2025-04-01 20:02:07', 9, 0);
INSERT INTO ling_bbs.user_info (user_id, avatar, nick_name, email, password, gender, description, join_time, last_login_time, last_login_ip, last_login_ip_address, total_integral, current_integral, status, create_time, update_time, version, is_admin) VALUES ('2550515268', null, 'user_wangyi1', 'test_demo_ling@163.com', 'c4aed4492a8061c5f89c69c87bc5531b', 1, null, '2025-01-13', '2025-03-07 16:23:11', '0:0:0:0:0:0:0:1', '四川省', 5, 0, 1, '2025-01-13 19:53:25', '2025-03-07 16:23:11', 3, 0);
INSERT INTO ling_bbs.user_info (user_id, avatar, nick_name, email, password, gender, description, join_time, last_login_time, last_login_ip, last_login_ip_address, total_integral, current_integral, status, create_time, update_time, version, is_admin) VALUES ('7878970085', null, 'user_aliyun', 'lingzed@aliyun.com', 'f11be3dc801b20c1df4ae20f553f5ce6', 1, null, '2025-01-11', '2025-02-21 10:00:11', '0:0:0:0:0:0:0:1', '四川省', 14, 14, 1, '2025-01-11 11:25:58', '2025-02-21 10:00:11', 4, 0);
INSERT INTO ling_bbs.user_info (user_id, avatar, nick_name, email, password, gender, description, join_time, last_login_time, last_login_ip, last_login_ip_address, total_integral, current_integral, status, create_time, update_time, version, is_admin) VALUES ('9619980088', 'avatar/2025-04/aa21ac441ca44890932c9d987a3d060e.jpg', 'ling', 'lingzed@163.com', '472a41b09f4654816ab428023181841c', 2, '你看我的简介是十个字', '2024-12-06', '2025-04-02 23:22:03', '0:0:0:0:0:0:0:1', '四川省', 82, 76, 1, '2024-12-06 11:26:53', '2025-04-02 23:22:03', 39, 0);
INSERT INTO ling_bbs.user_info (user_id, avatar, nick_name, email, password, gender, description, join_time, last_login_time, last_login_ip, last_login_ip_address, total_integral, current_integral, status, create_time, update_time, version, is_admin) VALUES ('9876175182', null, 'user_189', '15828460467@189.cn', '4ecf1e74e0431b3e3de4c9e64c15767f', 1, null, '2025-01-11', '2025-03-19 21:58:35', '0:0:0:0:0:0:0:1', '四川省', 23, 16, 1, '2025-01-11 11:10:41', '2025-03-19 21:58:35', 18, 0);
