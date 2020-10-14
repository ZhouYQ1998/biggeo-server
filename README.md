# Database

## 1 tb_user

### 1.1 Table

| Column        | Description  | Type   | Remark                           |
| ------------- | ------------ | ------ | -------------------------------- |
| ID            | 主键（编号） | String | Not Null, Unique, Auto Create    |
| NAME          | 用户名       | String | Not Null, Unique                 |
| PASSWORD      | 密码         | String | Not Null                         |
| PHONE         | 电话号码     | String | Not Null                         |
| EMAIL         | 邮箱         | String | Not Null                         |
| ICON          | 头像         | String |                                  |
| COUNTRY       | 国家（地区） | String |                                  |
| INSTITUTE     | 机构         | String |                                  |
| INSTITUTETYPE | 机构类型     | String |                                  |
| FIELD         | 专业领域     | String |                                  |
| PURPOSE       | 用途         | String |                                  |
| ROLE          | 用户角色     | String | Not Null, "manager" or "visitor" |
| SIGN_COUNT    | 用户访问量   | Int    | Default 0                        |

### 1.2 URL

| URL                       | FUNCTION   | METHOD | PARAM                                    | RESULT              | REMARK              |
| ------------------------- | ---------- | ------ | ---------------------------------------- | ------------------- | ------------------- |
| /user/insert              | 插入用户   | PUT    | name,password,role[,phone,email,icon]    | {code,body,message} | phone,email为可选值 |
| /user/batchinsert         | 批量插入   | PUT    | [User[,User...]]                         | {code,body,message} |                     |
| /user/delete/{id}         | 删除用户   | DELETE |                                          | {code,body,message} | body值为id          |
| /user/deletebyname/{name} | 删除用户   | DELETE |                                          | {code,body,message} | body值为name        |
| /user//batchdelete/{ids}  | 批量删除   | DELETE |                                          | {code,body,message} | 逗号","分隔         |
| /user/select/{id}         | 查询用户   | GET    |                                          | {code,body,message} |                     |
| /user/selectbyname/{name} | 查询用户   | GET    |                                          | {code,body,message} |                     |
| /user/batchseletct/{ids}  | 批量查询   | GET    | [pageNo,pageSize]                        | ode,body,message}   | 逗号","分隔         |
| /user/allselect           | 查询用户   | GET    | [pageNo,pageSize]                        | {code,body,message} | body为page          |
| /user/update              | 更新用户   | POST   | id[,name,password,role,phone,email,icon] | {code,body,message} | id为必要值          |
| /user/batchupdate         | 批量更新   | POST   | [User[,User...]]                         | {code,body,message} |                     |
| /user/login               | 用户登录   | POST   | name,password                            | {code,body,message} |                     |
| /user/loginstatus         | 登录状态   | GET    |                                          | {code,body,message} |                     |
| /user/logout              | 用户注销   | POST   |                                          | {code,body,message} | body值为id          |
| /user/check/{email}       | 发送验证码 | GET    |                                          | {code,body,message} | body值为code和email |
| /user/checkbyname/{name}  | 发送验证码 | GET    |                                          | {code,body,message} |                     |
| /user/statistic           | 统计访问量 | GET    |                                          | {code,body,message} |                     |

- 插入和更新的批量操作返回值的body值包括“t”（实体）和“message”（实体操作结果），下同
- PageNo默认为1，PageSize默认为20，下同

## 2 tb_geographic_data

### 2.1 Table

| Column               | Description  | Type    | Remark                                     |
| -------------------- | ------------ | ------- | ------------------------------------------ |
| ID                   | 主键（编号） | String  | Not Null, Unique, Auto Create，primary key |
| TITLE                | 标题         | String  | Not Null                                   |
| UPLOADED             | 作者         | String  |                                            |
| userName             | 上传用户     | String  | Not Null, "manager" or "userId/userName"   |
| downloadAuthority    | 下载权限     | Boolean | Default true                               |
| TIME                 | 时间         | Date    |                                            |
| TYPE_1               | 类型一       | String  | Not Null                                   |
| TYPE_2               | 类型二       | String  |                                            |
| KEYWORDS             | 关键词       | String  | Not Null                                   |
| **（已删除）**SOURCE | 资源         | String  |                                            |
| ABSTRACT             | 摘要         | String  |                                            |
| REFRENCE             | 参考文献     | String  |                                            |
| PICTURE              | 图片         | String  |                                            |
| OLD_FILENAME         | 中文名称     | String  |                                            |
| NEW_FILENAME         | 英文名称     | String  |                                            |
| FORMAT               | 数据格式     | String  |                                            |
| PATH                 | 路径         | String  |                                            |
| RAM                  | 数据大小     | String  |                                            |
| DOWNLOAD_TIM         | 下载数量     | Int     |                                            |

### 2.2 URL

- 基础：增删改查及批量操作

- 查询个人数据（通过user_id/通过user_name）

- 查询最高下载量的论文（5）

- **HDFS：所有地理数据存储**

| URL                         | FUNCTION                       | METHOD | PARAM                                                        | RESULT              | REMARK |
| --------------------------- | ------------------------------ | ------ | ------------------------------------------------------------ | ------------------- | ------ |
| /geodata/insert             | 插入用户                       | PUT    | title,uploader,type1,type2[,tags,source,abstractInfo,reference,pic,oldName,newName,format,path,ram,downloadTimes] | {code,body,message} |        |
| /geodata/batchinsert        | 批量插入                       | PUT    | [Geodata[,Geodata...]]                                       | {code,body,message} |        |
| /geodata/delete/{id}        | 删除用户                       | DELETE | id                                                           | {code,body,message} |        |
| /geodata//batchdelete/{ids} | 批量删除                       | DELETE | ids,ids,ids                                                  | {code,body,message} |        |
| /geodata/select/{id}        | 查询用户                       | GET    | id                                                           | {code,body,message} |        |
| /geodata/batchseletct/{ids} | 批量查询                       | GET    |                                                              | {code,body,message} |        |
| /geodata/allselect          | 全部查询                       | GET    |                                                              |                     |        |
| /geodata/byuserName         | 名字查询                       | GET    | String userName                                              |                     |        |
| /geodata/update             | 更新用户                       | POST   | id[,title,uploader,type1,type2,tags,source,abstractInfo,reference,pic,oldName,newName,format,path,ram,downloadTimes] | {code,body,message} |        |
| /geodata/batchupdate        | 批量更新                       | POST   | [Geodata[,Geodata...]]                                       | {code,body,message} |        |
| /geodata/bytype1            | 按照一级目录分类               | GET    | String type, Page page                                       | {code,body,message} |        |
| /geodata/bytype2            | 按照二级目录分类               | GET    | String type, Page page                                       | {code,body,message} |        |
| /geodata/dis                | 返回结果的唯一不同值与对应数量 | GET    | String field, Page page                                      | {code,body,message} |        |
| /geodata/downloadplus       | 更新数据库中下载次数           | GET    | String id                                                    | {code,body,message} |        |
| /geodata/populardata        | 返回下载数量最多的五条数据     | GET    |                                                              |                     |        |



### 2.3 建库语句

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_geographic_data
-- ----------------------------
DROP TABLE IF EXISTS `tb_geographic_data`;
CREATE TABLE `tb_geographic_data`  (
  `ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TITLE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `UPLOADER` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `userName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `downloadAuthority` tinyint(1) NULL DEFAULT 1,
  `RE_TIME` date NULL DEFAULT NULL,
  `TYPE_1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TYPE_2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `KEYWORDS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `SOURCE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ABSTRACT` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `REFERENCE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `PICTURE` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `OLD_FILENAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `NEW_FILENAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `FORMAT` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `PATH` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `RAM` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `DOWNLOAD_TIMES` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_geographic_data
-- ----------------------------
INSERT INTO `tb_geographic_data` VALUES ('367', '南极冰架年崩解数据集（2005-2019）', '戚梦真,刘岩,程晓*,冯启阳,惠凤鸣,陈卓奇', 'zjh', 1, '2020-07-01', '土地资源类数据', '地形、地貌、土壤数据', '南极,冰架,崩解,遥感', NULL, '崩解是南极冰架物质平衡的核心过程之一，也是精细监测冰架变化的重要物理量。作者运用2005-2019年每年8月初的多源遥感数据，提取了2005年8月至2019年8月14年间南极冰架发生的所有面积在1 km²以上的年崩解事件，计算了它们的面积、厚度、崩解量与崩解周期等，得到南极冰架年崩解数据集（2005-2019）。该数据集包括14个年度的南极冰架崩解分布数据，包括冰架崩解年份区间、崩解区长度、面积、平均厚度、崩解量、崩解周期和崩解类型等信息。该数据集以.shp格式存储，由112个数据文件组成，数据总量为10.2 MB（压缩为1个文件，3.23 MB）。', '戚梦真,刘岩,程晓*,冯启阳,惠凤鸣,陈卓奇.南极冰架年崩解数据集（2005-2019）[DB/J].全球变化数据仓储,2020.DOI:10.3974/geodb.2020.04.09.V1.', 'http://www.geodoi.ac.cn/Upload/1516/Image/Suo_202072817619637315527798764105.jpg;http://www.geodoi.ac.cn/Upload/1516/Image/Suo_202072817620637315527807884627.jpg', '南极冰架年崩解数据集（2005-2019）', 'IcebergCalvingAntarctic_2005-2019', '.shp', '10.79.231.81:/data/images/geodata/LandResource/Topography&Soil/IcebergCalvingAntarctic_2005-2019.rar', '3.23 MB', 4);

SET FOREIGN_KEY_CHECKS = 1;

## 3 tb_student_paper

### 3.1 Table

| Column          | Description   | Type   | Remark                                       |
| --------------- | ------------- | ------ | -------------------------------------------- |
| ID              | 主键（编号）  | String | Not Null, Unique, Auto Create                |
| TITLE           | 标题          | String | Not Null, Unique                             |
| ENGLISH_TITLE   | 标题（英文）  | String | Not Null, Unique                             |
| AUTHOR          | 作者          | String | Not Null                                     |
| PUBLISHER       | 所在单位/学校 | String |                                              |
| TERTIARY_AUTHOR | 指导老师      | String |                                              |
| YEAR            | 发表年份      | String |                                              |
| TYPE            | 文章类型      | String | Not Null, "bachelor" or "master" or "doctor" |
| KEYWORDS        | 关键词        | String |                                              |
| ABSTRACT        | 摘要          | String |                                              |
| URL             | 链接          | String |                                              |

### 3.2 URL

- 基础：增删改查及批量操作

## 4 tb_academic_paper

### 4.1 Table

| Column             | Description   | Type   | Remark                              |
| ------------------ | ------------- | ------ | ----------------------------------- |
| ID                 | 主键（编号）  | String | Not Null, Unique, Auto Create       |
| TITLE              | 标题          | String | Not Null, Unique                    |
| ENGLISH_TITLE      | 标题（英文）  | String | Not Null, Unique                    |
| TYPE               | 文章类型      | String | Not Null, "conference" or "journal" |
| AUTHOR             | 作者          | String | Not Null                            |
| AUTHOR_AFFILIATION | 所在单位/学校 | String |                                     |
| YEAR               | 发表年份      | String |                                     |
| SOURCE_NAME        | 期刊名称      | String |                                     |
| VOLUME             | 期刊卷数      | String |                                     |
| ISSUE              | 期刊期数      | String |                                     |
| PAGES              | 期刊页数      | String |                                     |
| KEYWORDS           | 关键词        | String |                                     |
| ABSTRACT           | 摘要          | String |                                     |
| DOI                | DOI           | String | Unique                              |
| ISSU               | ISSU          | String |                                     |
| URL                | 链接          | String | Not Null,Unique                     |

### 4.2 URL

- 基础：增删改查及批量操作

## 5 tb_lectures

### 5.1 Table

| Column  | Description  | Type   | Remark                        |
| ------- | ------------ | ------ | ----------------------------- |
| ID      | 主键（编号） | String | Not Null, Unique, Auto Create |
| NAME    | 姓名         | String | Not Null                      |
| SPEAKER | 演讲者       | String |                               |
| PLACE   | 地点         | String |                               |
| TIME    | 时间         | String |                               |
| URL     | 链接         | String |                               |

### 5.2 URL

- 基础：增删改查及批量操作

## 6 tb_online_tools

### 6.1 Table

| Column   | Description  | Type   | Remark                        |
| -------- | ------------ | ------ | ----------------------------- |
| ID       | 主键（编号） | String | Not Null, Unique, Auto Create |
| NAME     | 名称         | String | Not Null, Unique              |
| ABSTRACT | 介绍         | String |                               |
| PICTURE  | 图片         | String |                               |
| URL      | 链接         | String | Not Null                      |

### 6.2 URL

- 基础：增删改查及批量操作

## 7 tb_map_servers

### 7.1 Table

| Column      | Description  | Type   | Remark                        |
| ----------- | ------------ | ------ | ----------------------------- |
| ID          | 主键（编号） | String | Not Null, Unique, Auto Create |
| NAME        | 名称         | String | Not Null, Unique              |
| COMPANY     | 公司         | String | Not Null                      |
| REGION      | 地区         | String | Not Null, "CN" or "other"     |
| SERVER      | 提供的服务   | String |                               |
| LIMITED     | 使用限制     | String |                               |
| PICTURE     | 图片         | String |                               |
| DESCRIPTION | 描述         | String |                               |
| URL         | 链接         | String | Not Null                      |

### 7.2 URL

- 基础：增删改查及批量操作

### 7.3 建库语句

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_map_servers
-- ----------------------------
DROP TABLE IF EXISTS `tb_map_servers`;
CREATE TABLE `tb_map_servers`  (
  `ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '唯一身份编号',
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `COMPANY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公司',
  `REGION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '地区',
  `SERVERS` varchar(2222) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '提供的服务',
  `LIMITED` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '使用限制',
  `PICTURE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片',
  `DESCRIBES` varchar(2222) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `URL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '链接',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `name`(`NAME`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_map_servers
-- ----------------------------
INSERT INTO `tb_map_servers` VALUES ('1', '腾讯', 'TECENT', '中国', 'API/SDK', NULL, NULL, NULL, 'LBS.QQ.COM');
INSERT INTO `tb_map_servers` VALUES ('10', '百度10', 'null', 'null', 'API/SDK', NULL, NULL, NULL, 'LBS.BAIDU.COM3');
INSERT INTO `tb_map_servers` VALUES ('2', '百度', 'BAIDU', '中国', 'API/SDK', NULL, NULL, NULL, 'LBS.BAIDU.COM');
INSERT INTO `tb_map_servers` VALUES ('3', '高德', 'ALI', '中国', 'API/SDK', NULL, NULL, NULL, 'LBS.GAODE.COM');
INSERT INTO `tb_map_servers` VALUES ('4', '百度qwd9', 'nukjll', 'njgull', 'API/SDKqwjghd', NULL, NULL, NULL, 'LBS.BAIDU.COjgM3');
INSERT INTO `tb_map_servers` VALUES ('8', '百度8', 'BAIDU2', '中国', NULL, NULL, NULL, NULL, 'LBS.BAIDU.COM3');
INSERT INTO `tb_map_servers` VALUES ('9', '百度9', 'null', 'null', 'API/SDK', NULL, NULL, NULL, 'LBS.BAIDU.COM3');

SET FOREIGN_KEY_CHECKS = 1;

## 8 tb_group_member

### 8.1 Table

| Column  | Description  | Type   | Remark                                         |
| ------- | ------------ | ------ | ---------------------------------------------- |
| ID      | 主键（编号） | Int    | Not Null, Unique, Auto Create                  |
| NAME    | 姓名         | String | Not Null                                       |
| VERSION | 开发版本     | String | "V1.0" or "V2.0"                               |
| TEAM    | 小组编号     | String |                                                |
| ROLE    | 角色         | String | Not Null, "instructor" or "leader" or "member" |
| EMAIL   | 邮箱         | String |                                                |
| PHOTO   | 照片         | String |                                                |
### 8.2 URL

| URL                   | FUNCTION       | METHOD | PARAM                         | RESULT      | REMARK |
| --------------------- | -------------- | ------ | ----------------------------- | ----------- | ------ |
| /member/allselect     | 查询所有开发者 | GET    | [PageNo,PageSize]             | {code,body} |        |
| /member/showteachers  | 查询指导老师   | GET    | [PageNo,PageSize]             | {code,body} |        |
| /member/showbyversion | 查询开发者     | GET    | version,[PageNo,PageSize]     | {code,body} |        |
| /member/showbyteam    | 查询开发者     | GET    | version,team[PageNo,PageSize] | {code,body} |        |

# 9 tb_log

## 9.1 Table

| Column   | Description  | Type | Remark |
| -------- | ------------ | ---- | ------ |
| ID       | 主键（编号） |      |        |
| ACTID    | 操作员id     |      |        |
| ROLE     | 操作员角色   |      |        |
| TIME     | 操作时间     |      |        |
| TABLE    | 操作表格     |      |        |
| OBJECTID | 操作数据id   |      |        |
| TYPE     | 操作类型     |      |        |

## 9.2 URL

- 基础：增加数据（增、删、查操作时触发）
- 基础：查询数据及批量操作

- 查询数据（通过ACTID，通过TIME（TIME范围），通过TABLE）

- 定期删除日志：数据表触发器（如超过10000条数据，以时间顺序删除8000条）

# 10 tb_teaching_cases

- 与第3组进行对接

# Resut code

| Code                       | Constants | Description         |
| -------------------------- | --------- | ------------------- |
| SUCCESS                    | 200       | 成功                |
| SYSTEM_ERROR               | 9000      | 未知系统错误        |
| VALIDATE_ERROR             | 9001      | 参数验证错误        |
| SERVICE_ERROR              | 9002      | 业务逻辑验证错误    |
| CACHE_ERROR                | 9003      | 缓存访问错误        |
| DAO_ERROR                  | 9004      | 数据访问错误        |
| USERNAME_OR_PASSWORD_ERROR | 1001      | 用户名或密码错误    |
| USER_NOT_EXIST             | 1002      | 用户不存在          |
| SSO_TOKEN_ERROR            | 2001      | TOKEN未授权或已过期 |
| SSO_PERMISSION_ERROR       | 2002      | 没有访问权限        |
| QUEUE_ERROR                | 8001      | 队列溢出错误        |

# Task Division

| Name   | Task                                                       |
| ------ | ---------------------------------------------------------- |
| 周育全 | base类实现，tb_user、tb_group_member设计及实现             |
| 张郑良 | tb_log设计及实现                                           |
| 赵佳晖 | tb_geographic_data设计及实现                               |
| 张家瑞 | tb_teaching_cases设计及实现                                |
| 冯瀑霏 | tb_online_tools、tb_map_servers设计及实现                  |
| 陈柠檬 | tb_student_paper、tb_academic_paper、tb_lectures设计及实现 |

# TODO

- 数据下载：/home/dxdsj_platform/xxx