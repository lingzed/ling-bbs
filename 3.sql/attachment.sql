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

INSERT INTO ling_bbs.attachment (file_id, article_id, user_id, file_size, filename, download_count, filepath, filetype, download_points) VALUES ('28awi092sd', 'ababa', '9619980088', 119994, '塞尔达.png', 19, 'D:/ling-bbs-folder/web/attachment/2024-12/28awi092sd.png', 11, 2);
INSERT INTO ling_bbs.attachment (file_id, article_id, user_id, file_size, filename, download_count, filepath, filetype, download_points) VALUES ('56c1a4d6a14045a9904e93243a962fca', '000981283431340', '9876175182', 19272, '584_2025030164232458 - 副本.jpeg', 9, 'D:\\ling-bbs-folder\\web\\attachment\\2025-03\\56c1a4d6a14045a9904e93243a962fca.jpeg', 10, 2);
INSERT INTO ling_bbs.attachment (file_id, article_id, user_id, file_size, filename, download_count, filepath, filetype, download_points) VALUES ('ajdqwwojd', 'uukicbc29eqo', '9619980088', 340, '巫师3攻略.txt', 42, 'D:/ling-bbs-folder/web/attachment/2024-12/ajdqwwojd.txt', 0, 2);
INSERT INTO ling_bbs.attachment (file_id, article_id, user_id, file_size, filename, download_count, filepath, filetype, download_points) VALUES ('bd47629eeb8f4c6ea1d3caa6b5d32cfd', '000981283431340', '9876175182', 14827, '584_2025030164232998 - 副本.jpeg', 9, 'D:\\ling-bbs-folder\\web\\attachment\\2025-03\\bd47629eeb8f4c6ea1d3caa6b5d32cfd.jpeg', 10, 2);
INSERT INTO ling_bbs.attachment (file_id, article_id, user_id, file_size, filename, download_count, filepath, filetype, download_points) VALUES ('ddc22d5f1c6b4183bdf18a70bed6c2a6', '000981283431340', '9876175182', 5495, '认识AI模型.txt', 6, 'D:\\ling-bbs-folder\\web\\attachment\\2025-03\\ddc22d5f1c6b4183bdf18a70bed6c2a6.txt', 0, 1);
INSERT INTO ling_bbs.attachment (file_id, article_id, user_id, file_size, filename, download_count, filepath, filetype, download_points) VALUES ('wdpqowjdpq', 'uukicbc29eqo', '9619980088', 100701, '巫师3攻略.pdf', 42, 'D:/ling-bbs-folder/web/attachment/2024-12/wdpqowjdpq.pdf', 1, 3);
