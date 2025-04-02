create table sys_setting
(
    code         varchar(10)  not null comment '编码'
        primary key,
    json_content varchar(500) null comment '设置信息的json字符串',
    create_time  datetime     not null comment '创建时间',
    update_time  datetime     not null comment '修改时间'
)
    comment '系统设置信息表';

INSERT INTO ling_bbs.sys_setting (code, json_content, create_time, update_time) VALUES ('audit', '{"postAudit": true,"commentAudit": false}', '2024-11-18 22:41:36', '2024-11-18 22:41:51');
INSERT INTO ling_bbs.sys_setting (code, json_content, create_time, update_time) VALUES ('comment', '{"openComment": true,"commentPoints": 3,"commentNum": 50}', '2024-11-18 22:41:44', '2024-11-18 22:41:52');
INSERT INTO ling_bbs.sys_setting (code, json_content, create_time, update_time) VALUES ('like', '{"likeNum": 20}', '2024-11-18 22:41:47', '2024-11-18 22:41:53');
INSERT INTO ling_bbs.sys_setting (code, json_content, create_time, update_time) VALUES ('mail', '{"mailTitle": "邮箱验证码","mailContent": "【LingZed-bbs】 验证码：%s。您正在进行身份验证，请尽快完成验证码校验（5分钟内有效），请勿向他人提供此验证码。感谢您的支持！"}', '2024-11-18 22:41:48', '2024-11-18 22:41:54');
INSERT INTO ling_bbs.sys_setting (code, json_content, create_time, update_time) VALUES ('post', '{"postPoints": 5,"postNum": 5,"uploadImgNum": 50,"attachmentSize": 3}', '2024-11-18 22:41:49', '2024-11-18 22:41:55');
INSERT INTO ling_bbs.sys_setting (code, json_content, create_time, update_time) VALUES ('register', '{"welcomeInfo": "恭喜您成功注册！在这里，每一位成员都是我们宝贵的一部分。希望您能在社区中找到灵感，分享智慧，一起成长！"}', '2024-11-18 22:41:50', '2024-11-18 22:41:56');
