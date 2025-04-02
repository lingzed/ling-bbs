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

INSERT INTO ling_bbs.attachment_download_record (download_record_id, article_id, downloader_id, download_time) VALUES (1, 'uukicbc29eqo', '9876175182', '2025-01-15 19:57:53');
INSERT INTO ling_bbs.attachment_download_record (download_record_id, article_id, downloader_id, download_time) VALUES (2, 'ababa', '9876175182', '2025-01-16 21:26:35');
INSERT INTO ling_bbs.attachment_download_record (download_record_id, article_id, downloader_id, download_time) VALUES (3, 'uukicbc29eqo', '0000000000', '2025-03-01 17:05:09');
INSERT INTO ling_bbs.attachment_download_record (download_record_id, article_id, downloader_id, download_time) VALUES (4, 'ababa', '0000000000', '2025-03-01 17:09:12');
INSERT INTO ling_bbs.attachment_download_record (download_record_id, article_id, downloader_id, download_time) VALUES (6, '000981283431340', '9619980088', '2025-03-07 16:03:06');
INSERT INTO ling_bbs.attachment_download_record (download_record_id, article_id, downloader_id, download_time) VALUES (7, '000981283431340', '2550515268', '2025-03-07 16:23:19');
INSERT INTO ling_bbs.attachment_download_record (download_record_id, article_id, downloader_id, download_time) VALUES (8, 'uukicbc29eqo', '1835487851', '2025-03-26 19:44:23');
