# 1 板块

**板块**是论坛内容的一种分类方式，用于组织和管理主题帖子。不同的领域可以设置为一个板块，例如汽车板块、游戏板块等。板块下可以进一步划分为不同的主题或子分类（具体分类视板块内容的复杂程度而定）。以**游戏板块**为例，可以包含以下主题分类：

- **游戏资讯**：发布最新公告、新闻、更新内容等。
- **游戏攻略**：分享游戏玩法技巧、任务流程、装备搭配等实用信息。
- **玩家交流**：讨论游戏趣事、晒成就、吐槽版本等。

板块和主题的设置目的是为用户提供有条理的内容组织结构，使他们能够快速找到感兴趣的内容并参与讨论。总之，通过科学的分类设计，论坛可以提升信息管理效率和用户的参与体验。

本项目前端中的板块样式：

![image-20241226215829787](assets/image-20241226215829787.png)

类似导航栏。

## 1.1 板块表

板块表结构如下：

```sql
create table forum_board
(
    board_id    int auto_increment comment '板块id',
    p_board_id  int               null comment '父级板块id',
    board_name  varchar(50)       null comment '板块名称',
    cover       varchar(50)       null comment '板块封面',
    description varchar(150)      null comment '板块描述',
    sort        int               null comment '排序',
    post_type   tinyint default 1 null comment '0: 只允许管理员发帖, 1: 允许任何人发帖',
    constraint forum_board_pk
        primary key (board_id)
)
    comment '论坛板块表';
```

板块存在层级，因此有父级id，根板块的pid为0，响应给前端的数据是树形结构。