-- 用户表
CREATE TABLE `user`
(
    `id`       bigint       NOT NULL PRIMARY KEY comment '主键',
    `username` varchar(64)  not null comment '用户名',
    `password` varchar(512) not null comment '密码',
    `enabled`  tinyint(4)   not null comment '是否启用',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment ='用户表';

-- 数据源表
CREATE TABLE `datasource`
(
    `id`          bigint      NOT NULL PRIMARY KEY comment '主键',
    `name`        varchar(64) not null comment '名称',
    `type`        tinyint(4) comment '类型',
    `description` varchar(255) comment '描述',
    `plugin_name` varchar(64) not null comment '插件名称',
    `content`     longtext    not null comment '数据源内容',
    `create_time` datetime    DEFAULT NULL,
    `create_by`   varchar(32) DEFAULT NULL,
    `update_time` datetime    DEFAULT NULL,
    `update_by`   varchar(32) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment ='数据源表';

-- 数据源接口表
CREATE TABLE `datasource_interface`
(
    `id`            bigint      NOT NULL primary key comment '主键',
    `name`          varchar(64) not null comment '名称',
    `datasource_id` int         not null comment '数据源ID',
    `description`   varchar(255) comment '描述',
    `content`       longtext    not null comment '接口内容',
    `create_time`   datetime    DEFAULT NULL,
    `create_by`     varchar(32) DEFAULT NULL,
    `update_time`   datetime    DEFAULT NULL,
    `update_by`     varchar(32) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment ='数据源接口表';

-- 创建应用表
create TABLE `app`
(
    `id`          bigint      NOT NULL primary key comment '主键',
    `name`        varchar(64) not null comment '名称',
    `domain`      varchar(64) not null comment '域',
    `description` varchar(255) comment '描述',
    `create_time` datetime    DEFAULT NULL,
    `create_by`   varchar(32) DEFAULT NULL,
    `update_time` datetime    DEFAULT NULL,
    `update_by`   varchar(32) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment ='应用表';


-- 创建编排表
CREATE TABLE `workflow`
(
    `id`          bigint      NOT NULL primary key comment '主键',
    `name`        varchar(64) not null comment '名称',
    `description` varchar(255) comment '描述',
    `graph`       longtext    not null comment '流程图',
    `create_time` datetime    DEFAULT NULL,
    `create_by`   varchar(32) DEFAULT NULL,
    `update_time` datetime    DEFAULT NULL,
    `update_by`   varchar(32) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 comment ='编排表';