CREATE TABLE `UserInfo` (
`userId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户Id, 唯一标识一个用户',
`userName` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
`faceToken` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'faceToken,唯一标识面部信息',
`addTime` datetime NOT NULL DEFAULT  CURRENT_TIMESTAMP COMMENT '添加时间',
`modifyTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
`isLock` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否锁定（0,：未锁定，1：已锁定）',
`isDelete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0：未删除，1：已删除）',
PRIMARY KEY (`userId`) 
);

CREATE TABLE `UserRole` (
`keyId` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
`userId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户Id,唯一标识一个用户',
`roleId` bigint NOT NULL COMMENT '角色Id,唯一标识一个角色',
PRIMARY KEY (`keyId`) 
);

CREATE TABLE `Role` (
`keyId` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'roleId,唯一标识一种角色',
`roleName` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色名字',
`addTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
`modifyTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
`isDelete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0：未删除，1：已删除）',
PRIMARY KEY (`keyId`) 
);

CREATE TABLE `Resource` (
`keyId` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '接口资源主键，唯一标识一个接口资源',
`resourceType` tinyint(1) NOT NULL DEFAULT 0 COMMENT '资源类型（0：请求接口，2：展示菜单）',
`resouceName` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '菜单名称',
`resourceAddress` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '资源地址',
`addTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
`modifyTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
`isDelete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0：已删除，1：未删除）',
PRIMARY KEY (`keyId`) 
);

CREATE TABLE `RoleResource` (
`keyId` bigint NOT NULL COMMENT 'keyId',
`roleId` bigint UNSIGNED NOT NULL COMMENT '角色Id',
`resourceId` bigint UNSIGNED NOT NULL COMMENT '资源Id',
PRIMARY KEY (`keyId`) 
);

