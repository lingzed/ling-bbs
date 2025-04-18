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

create index file_use_record_target_id_index
    on file_use_record (target_id);

INSERT INTO ling_bbs.file_use_record (record_id, target_id, uploader_id, filename, filepath, status) VALUES ('64a4a683f352466b91749c772e01dfb5', null, '0000000000', '设计logo.png', 'D:\\ling-bbs-folder\\web\\article\\2025-02\\64a4a683f352466b91749c772e01dfb5.png', 0);
INSERT INTO ling_bbs.file_use_record (record_id, target_id, uploader_id, filename, filepath, status) VALUES ('f38658ca2d7b44fd8a07a23c76a82a22', '000981283431340', '9876175182', '584_2025030164232998.jpg', 'D:\\ling-bbs-folder\\web\\article\\2025-03\\f38658ca2d7b44fd8a07a23c76a82a22.jpg', 1);
INSERT INTO ling_bbs.file_use_record (record_id, target_id, uploader_id, filename, filepath, status) VALUES ('f38658ca2d7b44fd8a07a23c76a82a22', '000981283431340', '9876175182', '584_2025030164232458.jpg', 'D:\\ling-bbs-folder\\web\\article\\2025-03\\f70a5395a4464bb887646e7e60e8fcf5.jpg', 1);
INSERT INTO ling_bbs.file_use_record (record_id, target_id, uploader_id, filename, filepath, status) VALUES ('43322ef1219d494bb68ec76d4f1de81c', null, '1835487851', '96dda144ad345982b0020b1152bc02a1caef8479.webp', 'D:\\ling-bbs-folder\\web\\article\\2025-03\\43322ef1219d494bb68ec76d4f1de81c.webp', 0);
INSERT INTO ling_bbs.file_use_record (record_id, target_id, uploader_id, filename, filepath, status) VALUES ('43322ef1219d494bb68ec76d4f1de81c', null, '1835487851', 'c8ea15ce36d3d53985fdafc665cfda5c342ab043.webp', 'D:\\ling-bbs-folder\\web\\article\\2025-03\\4051819cbb954d658dbc86959ec146e2.webp', 0);
