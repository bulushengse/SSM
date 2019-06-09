--创建表空间BWSD_SSM
--create tablespace BWSD_SSM
--datafile 'D:\oracle\product\10.2.0\oradata\orcl\BWSD_SSM.dbf' size 500M
--autoextend on next 1M maxsize 1000M
---extent management local;

--设置默认的表空间
--alter database default tablespace BWSD_SSM;

-- ----------------------------
-- Table structure for SYS_MENU
-- ----------------------------
CREATE TABLE "SYS_MENU" (
"MENU_ID" NUMBER NOT NULL ,
"MENU_NAME" VARCHAR2(255 BYTE) NULL ,
"MENU_URL" VARCHAR2(255 BYTE) NULL ,
"PARENT_ID" VARCHAR2(100 BYTE) NULL ,
"MENU_ORDER" VARCHAR2(100 BYTE) NULL ,
"MENU_ICON" VARCHAR2(30 BYTE) NULL ,
"MENU_TYPE" VARCHAR2(10 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;
-- ----------------------------
-- Records of SYS_MENU
-- ----------------------------
INSERT INTO "SYS_MENU" VALUES ('1', '系统管理', null, '0', '1', 'icon-desktop', '1');
INSERT INTO "SYS_MENU" VALUES ('2', '系统用户', 'user/listUsers.do', '1', '1', null, '1');
INSERT INTO "SYS_MENU" VALUES ('3', '组织角色', 'role/listRoles.do', '1', '2', null, '1');
INSERT INTO "SYS_MENU" VALUES ('4', '在线管理', 'onlinemanager/list.do', '1', '3', null, '1');
INSERT INTO "SYS_MENU" VALUES ('5', '信息管理', null, '0', '2', 'icon-list-alt', '2');
INSERT INTO "SYS_MENU" VALUES ('6', '图片管理', 'pictures/list.do', '5', '1', null, '2');
INSERT INTO "SYS_MENU" VALUES ('7', '客户管理', 'custtype/list.do', '5', '2', null, '2');
INSERT INTO "SYS_MENU" VALUES ('8', '工具测试', null, '0', '3', 'icon-facetime-video', '1');
INSERT INTO "SYS_MENU" VALUES ('9', '接口测试', 'tool/interfaceTest.do', '8', '1', null, '1');
INSERT INTO "SYS_MENU" VALUES ('10', '打印测试', 'tool/printTest.do', '8', '2', null, '1');
INSERT INTO "SYS_MENU" VALUES ('11', '短信测试', 'tool/SendSMSTest.do', '8', '3', null, '1');
INSERT INTO "SYS_MENU" VALUES ('12', '邮件测试', 'tool/SendEmailTest.do', '8', '4', null, '1');
INSERT INTO "SYS_MENU" VALUES ('13', '物流测试', 'tool/transportTest.do', '8', '5', null, '1');
INSERT INTO "SYS_MENU" VALUES ('14', '定位测试', 'tool/locationTest.do', '8', '6', null, '1');
INSERT INTO "SYS_MENU" VALUES ('15', '图表统计', 'tool/reportAndView.do', '8', '7', null, '1');

-- ----------------------------
-- Table structure for SYS_ROLE
-- ----------------------------
CREATE TABLE "SYS_ROLE" (
"ROLE_ID" VARCHAR2(100 BYTE) NOT NULL ,
"ROLE_NAME" VARCHAR2(100 BYTE) NULL ,
"RIGHTS" VARCHAR2(255 BYTE) NULL ,
"PARENT_ID" VARCHAR2(100 BYTE) NULL ,
"ADD_QX" VARCHAR2(255 BYTE) NULL ,
"DEL_QX" VARCHAR2(255 BYTE) NULL ,
"EDIT_QX" VARCHAR2(255 BYTE) NULL ,
"CHA_QX" VARCHAR2(255 BYTE) NULL ,
"QX_ID" VARCHAR2(100 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;
-- ----------------------------
-- Records of SYS_ROLE
-- ----------------------------
INSERT INTO "SYS_ROLE" VALUES ('1', '系统管理员', '1048574', '0', '1', '1', '1', '1', '1');
INSERT INTO "SYS_ROLE" VALUES ('2', '超级管理员', '1048574', '0', '1', '1', '1', '1', '2');
INSERT INTO "SYS_ROLE" VALUES ('6860150abf64486da96fdcf1ae0e28e1', 'A级管理员', '1048574', '2', '0', '0', '0', '0', '6860150abf64486da96fdcf1ae0e28e1');
INSERT INTO "SYS_ROLE" VALUES ('4', '部门A', '54', '0', '0', '0', '0', '1', null);
INSERT INTO "SYS_ROLE" VALUES ('6', '部门B', '18', '0', '0', '0', '0', '1', null);
INSERT INTO "SYS_ROLE" VALUES ('7', '会员级别', '34', '0', '0', '0', '0', '0', null);
INSERT INTO "SYS_ROLE" VALUES ('abac05d7f93148149fda3b4af8b05fbb', '中级会员', '34', '7', '0', '0', '0', '0', 'abac05d7f93148149fda3b4af8b05fbb');
INSERT INTO "SYS_ROLE" VALUES ('9b55251207324a2aae8bd073cbaf3971', '高级会员', '34', '7', '0', '0', '0', '0', '9b55251207324a2aae8bd073cbaf3971');
INSERT INTO "SYS_ROLE" VALUES ('cc874ff76fa149f38503039742b65b57', '初级会员', '34', '7', '0', '0', '0', '0', 'cc874ff76fa149f38503039742b65b57');
INSERT INTO "SYS_ROLE" VALUES ('7f6719a1069941b6af50b2539864d511', '部门经理', '18', '6', '0', '0', '0', '0', '7f6719a1069941b6af50b2539864d511');
INSERT INTO "SYS_ROLE" VALUES ('98e2524a98144326a129bd9490f9075f', '组长', '54', '4', '0', '0', '0', '0', '98e2524a98144326a129bd9490f9075f');

-- ----------------------------
-- Table structure for SYS_USER
-- ----------------------------
CREATE TABLE "SYS_USER" (
"USER_ID" VARCHAR2(100 BYTE) NOT NULL ,
"USERNAME" VARCHAR2(255 BYTE) NULL ,
"PASSWORD" VARCHAR2(255 BYTE) NULL ,
"NAME" VARCHAR2(255 BYTE) NULL ,
"RIGHTS" VARCHAR2(255 BYTE) NULL ,
"ROLE_ID" VARCHAR2(100 BYTE) NULL ,
"LAST_LOGIN" VARCHAR2(255 BYTE) NULL ,
"IP" VARCHAR2(100 BYTE) NULL ,
"STATUS" VARCHAR2(32 BYTE) NULL ,
"BZ" VARCHAR2(255 BYTE) NULL ,
"SKIN" VARCHAR2(10 BYTE) NULL ,
"EMAIL" VARCHAR2(32 BYTE) NULL ,
"NUMBER" VARCHAR2(100 BYTE) NULL ,
"PHONE" VARCHAR2(32 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;
COMMENT ON COLUMN "SYS_USER"."USER_ID" IS '用户id';
COMMENT ON COLUMN "SYS_USER"."USERNAME" IS '用户名';
COMMENT ON COLUMN "SYS_USER"."PASSWORD" IS '密码';
COMMENT ON COLUMN "SYS_USER"."NAME" IS '姓名';
COMMENT ON COLUMN "SYS_USER"."RIGHTS" IS '菜单显示权限';
COMMENT ON COLUMN "SYS_USER"."ROLE_ID" IS '角色id';
COMMENT ON COLUMN "SYS_USER"."LAST_LOGIN" IS '最后登录时间';
COMMENT ON COLUMN "SYS_USER"."IP" IS '登录端ip地址';
COMMENT ON COLUMN "SYS_USER"."STATUS" IS '状态';
COMMENT ON COLUMN "SYS_USER"."BZ" IS '备注';
-- ----------------------------
-- Records of SYS_USER
-- ----------------------------                                                                         
INSERT INTO "SYS_USER" VALUES ('1', 'admin', 'de41b7fb99201d8334c23c014db35ecd92df81bc', '系统管理员', '1133671055321055258374707980945218933803269864762743594642571294', '1', '2015-06-04 00:46:08', '127.0.0.1', '0', '最高统治者', 'default', 'admin@main.com', '001', null);
INSERT INTO "SYS_USER" VALUES ('23747df8990049818c76709197ea9b87', 'dingdan', 'ff4e4a636da6adebcdd2cdc52bb1b36e4c09482f', '订单', null, '6860150abf64486da96fdcf1ae0e28e1', '2015-06-04 00:46:52', '127.0.0.1', '0', 'dfsd', 'default', 'zhangsand@www.com', '11301', '18765667676');
INSERT INTO "SYS_USER" VALUES ('371ef05e9dd649269384d1657b50bec5', 'dafangandi', '202cb962ac59075b964b07152d234b70', '大范甘迪', null, '6860150abf64486da96fdcf1ae0e28e1', null, null, '0', 'sdfs', 'default', '234234@qq.com', '234241', null);

-- ----------------------------
-- Table structure for TB_PICTURES
-- ----------------------------
CREATE TABLE "TB_PICTURES" (
"TITLE" VARCHAR2(255 BYTE) NULL ,
"NAME" VARCHAR2(255 BYTE) NULL ,
"PATH" VARCHAR2(255 BYTE) NULL ,
"CREATETIME" VARCHAR2(255 BYTE) NULL ,
"MASTER_ID" VARCHAR2(255 BYTE) NULL ,
"BZ" VARCHAR2(255 BYTE) NULL ,
"PICTURES_ID" VARCHAR2(100 BYTE) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "TB_PICTURES"."TITLE" IS '标题';
COMMENT ON COLUMN "TB_PICTURES"."NAME" IS '文件名';
COMMENT ON COLUMN "TB_PICTURES"."PATH" IS '路径';
COMMENT ON COLUMN "TB_PICTURES"."CREATETIME" IS '创建时间';
COMMENT ON COLUMN "TB_PICTURES"."MASTER_ID" IS '属于';
COMMENT ON COLUMN "TB_PICTURES"."BZ" IS '备注';
COMMENT ON COLUMN "TB_PICTURES"."PICTURES_ID" IS 'ID';

-- ----------------------------
-- Records of TB_PICTURES
-- ----------------------------
INSERT INTO "TB_PICTURES" VALUES ('图片', '3a82fb0892be4714a801ebe17f302ffb.png', '20150406/3a82fb0892be4714a801ebe17f302ffb.png', '2015-04-06 18:18:36', '1', '图片管理处上传', '9e3a0d3a3cc04588922b33be140dee9e');
INSERT INTO "TB_PICTURES" VALUES ('图片', 'e8cf6b36f92f4600a1f3faba127eb863.png', '20150406/e8cf6b36f92f4600a1f3faba127eb863.png', '2015-04-06 18:18:36', '1', '图片管理处上传', 'b9cf61cb5cc743dc9d0ce88f50e92408');



-- ----------------------------
-- Table structure for TB_CUST
-- ----------------------------
CREATE TABLE "TB_CUST" (
"CUST_ID" VARCHAR2(255 BYTE) NOT NULL  ,
"CUST_NAME" VARCHAR2(255 BYTE) NULL ,
"CUST_TYPE_ID" VARCHAR2(255 BYTE) NULL ,
"CUST_ADDR" VARCHAR2(255 BYTE) NULL ,
"CUST_CONTECT" VARCHAR2(255 BYTE) NULL ,
"CUST_PHONE" VARCHAR2(255 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;
COMMENT ON COLUMN "TB_CUST"."CUST_ID" IS 'ID';
COMMENT ON COLUMN "TB_CUST"."CUST_NAME" IS '客户名称';
COMMENT ON COLUMN "TB_CUST"."CUST_TYPE_ID" IS '客户类别ID';
COMMENT ON COLUMN "TB_CUST"."CUST_ADDR" IS '地址';
COMMENT ON COLUMN "TB_CUST"."CUST_CONTECT" IS '联系人';
COMMENT ON COLUMN "TB_CUST"."CUST_PHONE" IS '联系方式';

-- ----------------------------
-- Records of TB_PICTURES
-- ----------------------------
INSERT INTO "TB_CUST" VALUES ('d181b37ed6194fd9af41985209c31799', '深圳福田区客户1', 'ad4dc77448b3478fa45c2e88aa7bdd8c', '深圳市福田区xxx', '小王', '1234567890');
INSERT INTO "TB_CUST" VALUES ('dcc473c63a104888806d57c3c9324159', '深圳福田区客户2', 'ad4dc77448b3478fa45c2e88aa7bdd8c', '深圳市福田区xxx', '小李', '1234567890');
INSERT INTO "TB_CUST" VALUES ('1kj2k3121k3j2kj3k2n23j2k23jk23jj', '深圳福田区客户3', 'ad4dc77448b3478fa45c2e88aa7bdd8c', '深圳市福田区xxx', '小谢', '1234567890');
INSERT INTO "TB_CUST" VALUES ('031b7e49acee45288b09739274f32574', '深圳南山区客户1', 'e4d7b586f94c45c49b2e1a0764d2ca7e', '深圳市南山区xxx', '张三', '1234567890');
INSERT INTO "TB_CUST" VALUES ('ba58e56e10454733816427843b92a468', '深圳南山区客户2', 'e4d7b586f94c45c49b2e1a0764d2ca7e', '深圳市南山区xxx', '李四', '1234567890');
INSERT INTO "TB_CUST" VALUES ('7e6d1c749b7d4de1b98b22bc3599b108', '深圳南山区客户3', 'e4d7b586f94c45c49b2e1a0764d2ca7e', '深圳市南山区xxx', '王五', '1234567890');
INSERT INTO "TB_CUST" VALUES ('96dd59510c34431aa26dbc99a2b8e9c5', '北京大客户', '799445937798430e8dc305e1ca1bd208', '北京市xxx', '小明', '1234567890');
INSERT INTO "TB_CUST" VALUES ('348394debb734d43b7ec80b9840cabdd', '上海大客户', 'a443425890474b6e95780d7e68476e5f', '上海市xxx', '111', '1234567890');
INSERT INTO "TB_CUST" VALUES ('ce39b15a61954e81bef71e5c63a8cedc', '浙江杭州客户1', '9d7090cbaf47450e92772a6bb6bf47ab', '杭州市xxx', '小红', '1234567890');
INSERT INTO "TB_CUST" VALUES ('b392792fd7cb4a1ebee4e81085452795', '浙江杭州客户2', '9d7090cbaf47450e92772a6bb6bf47ab', '', '', '');
INSERT INTO "TB_CUST" VALUES ('0dd43f1173f1483c9098e2cae7158be3', '浙江杭州客户3', '9d7090cbaf47450e92772a6bb6bf47ab', '', '', '');


-- ----------------------------
-- Table structure for TB_CUST_TYPE
-- ----------------------------
CREATE TABLE "TB_CUST_TYPE" (
"CUST_TYPE_ID" VARCHAR2(255 BYTE) NOT NULL  ,
"CUST_TYPE_NAME" VARCHAR2(255 BYTE) NULL ,
"FATHER_ID" VARCHAR2(255 BYTE) NULL ,
"ICONSKIN" VARCHAR2(255 BYTE) NULL
);

COMMENT ON COLUMN "TB_CUST_TYPE"."CUST_TYPE_ID" IS 'ID';
COMMENT ON COLUMN "TB_CUST_TYPE"."CUST_TYPE_NAME" IS '客户类别名称';
COMMENT ON COLUMN "TB_CUST_TYPE"."FATHER_ID" IS '上级ID';
COMMENT ON COLUMN "TB_CUST_TYPE"."ICONSKIN" IS '节点图标';

-- ----------------------------
-- Records of TB_PICTURES
-- ----------------------------
INSERT INTO "TB_CUST_TYPE" VALUES ('0', '客户类别', '-1', 'pIcon01');
INSERT INTO "TB_CUST_TYPE" VALUES ('799445937798430e8dc305e1ca1bd208', '北京', '0', 'pIcon01');
INSERT INTO "TB_CUST_TYPE" VALUES ('a443425890474b6e95780d7e68476e5f', '上海', '0', 'pIcon01');
INSERT INTO "TB_CUST_TYPE" VALUES ('5108a017771d4855b1a926842a54d1df', '广东', '0', 'pIcon01');
INSERT INTO "TB_CUST_TYPE" VALUES ('bb0740255e10484eaad0a26b560af833', '浙江', '0', 'pIcon01');
INSERT INTO "TB_CUST_TYPE" VALUES ('b77382db877e4fe0a6ef426eecd69154', '江苏', '0', 'pIcon01');
INSERT INTO "TB_CUST_TYPE" VALUES ('4cbde5ad260a4f869943c6fe37603f61', '广东广州', '5108a017771d4855b1a926842a54d1df', 'pIcon01');
INSERT INTO "TB_CUST_TYPE" VALUES ('3708e5b57602401cb9bb56c57837b67a', '广东深圳', '5108a017771d4855b1a926842a54d1df', 'pIcon01');
INSERT INTO "TB_CUST_TYPE" VALUES ('9d7090cbaf47450e92772a6bb6bf47ab', '浙江杭州', 'bb0740255e10484eaad0a26b560af833', 'pIcon01');
INSERT INTO "TB_CUST_TYPE" VALUES ('e5c2e44eed144236816585df6a922f9d', '江苏南京', 'b77382db877e4fe0a6ef426eecd69154', 'pIcon01');
INSERT INTO "TB_CUST_TYPE" VALUES ('8d64161b84b24e80a24e6c7839c4f94a', '江苏苏州', 'b77382db877e4fe0a6ef426eecd69154', 'pIcon01');
INSERT INTO "TB_CUST_TYPE" VALUES ('4ce7703b4da949d2aaef65657cd50838', '江苏无锡', 'b77382db877e4fe0a6ef426eecd69154', 'pIcon01');
INSERT INTO "TB_CUST_TYPE" VALUES ('bb63f7da15134b30afa46c35a4f79e31', '广州天河区', '4cbde5ad260a4f869943c6fe37603f61', 'pIcon01');
INSERT INTO "TB_CUST_TYPE" VALUES ('ad4dc77448b3478fa45c2e88aa7bdd8c', '深圳福田区', '3708e5b57602401cb9bb56c57837b67a', 'pIcon01');
INSERT INTO "TB_CUST_TYPE" VALUES ('e4d7b586f94c45c49b2e1a0764d2ca7e', '深圳南山区', '3708e5b57602401cb9bb56c57837b67a', 'pIcon01');
INSERT INTO "TB_CUST_TYPE" VALUES ('fe395eab9a454655ad798825d8cfdb2a', '深圳宝安区', '3708e5b57602401cb9bb56c57837b67a', 'pIcon01');


-- ----------------------------
-- Checks structure for table SYS_MENU
-- ----------------------------
ALTER TABLE "SYS_MENU" ADD CHECK ("MENU_ID" IS NOT NULL);
-- ----------------------------
-- Primary Key structure for table SYS_MENU
-- ----------------------------
ALTER TABLE "SYS_MENU" ADD PRIMARY KEY ("MENU_ID");

-- ----------------------------
-- Checks structure for table SYS_ROLE
-- ----------------------------
ALTER TABLE "SYS_ROLE" ADD CHECK ("ROLE_ID" IS NOT NULL);
-- ----------------------------
-- Primary Key structure for table SYS_ROLE
-- ----------------------------
ALTER TABLE "SYS_ROLE" ADD PRIMARY KEY ("ROLE_ID");

-- ----------------------------
-- Checks structure for table SYS_USER
-- ----------------------------
ALTER TABLE "SYS_USER" ADD CHECK ("USER_ID" IS NOT NULL);
-- ----------------------------
-- Primary Key structure for table SYS_USER
-- ----------------------------
ALTER TABLE "SYS_USER" ADD PRIMARY KEY ("USER_ID");

-- ----------------------------
-- Checks structure for table TB_PICTURES
-- ----------------------------
ALTER TABLE "TB_PICTURES" ADD CHECK ("PICTURES_ID" IS NOT NULL);
-- ----------------------------
-- Primary Key structure for table TB_PICTURES
-- ----------------------------
ALTER TABLE "TB_PICTURES" ADD PRIMARY KEY ("PICTURES_ID");

-- ----------------------------
-- Checks structure for table TB_CUST
-- ----------------------------
ALTER TABLE "TB_CUST" ADD CHECK ("CUST_ID" IS NOT NULL);
-- ----------------------------
-- Primary Key structure for table TB_CUST
-- ----------------------------
ALTER TABLE "TB_CUST" ADD PRIMARY KEY ("CUST_ID");

-- Checks structure for table TB_CUST_TYPE
-- ----------------------------
ALTER TABLE "TB_CUST_TYPE" ADD CHECK ("CUST_TYPE_ID" IS NOT NULL);
-- ----------------------------
-- Primary Key structure for table TB_CUST_TYPE
-- ----------------------------
ALTER TABLE "TB_CUST_TYPE" ADD PRIMARY KEY ("CUST_TYPE_ID");
