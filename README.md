# Database

## 1 tb_user

### 1.1 Table

| Column        | Description  | Type   | Remark                                     |
| ------------- | ------------ | ------ | ------------------------------------------ |
| ID            | 主键（编号） | String | Not Null, Unique, Primary Key, Auto Create |
| NAME          | 用户名       | String | Not Null, Unique                           |
| PASSWORD      | 密码         | String | Not Null                                   |
| PHONE         | 电话号码     | String | Not Null                                   |
| EMAIL         | 邮箱         | String | Not Null                                   |
| ICON          | 头像         | String |                                            |
| COUNTRY       | 国家（地区） | String |                                            |
| INSTITUTE     | 机构         | String |                                            |
| INSTITUTETYPE | 机构类型     | String |                                            |
| FIELD         | 专业领域     | String |                                            |
| PURPOSE       | 用途         | String |                                            |
| ROLE          | 用户角色     | String | Not Null, "manager" or "visitor"           |
| SIGN_COUNT    | 用户访问量   | Int    | Default 0                                  |

### 1.2 URL

| URL                       | FUNCTION   | METHOD | PARAM                                    | RESULT              | REMARK              |
| ------------------------- | ---------- | ------ | ---------------------------------------- | ------------------- | ------------------- |
| /user/insert              | 插入用户   | PUT    | name,password,role[,phone,email,icon]    | {code,body,message} | phone,email         |
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

| Column            | Description  | Type    | Remark                                     |
| ----------------- | ------------ | ------- | ------------------------------------------ |
| ID                | 主键（编号） | String  | Not Null, Unique, Primary Key, Auto Create |
| TITLE             | 标题         | String  | Not Null                                   |
| UPLOADED          | 作者         | String  |                                            |
| userName          | 上传用户     | String  | Not Null, "manager" or "userId/userName"   |
| downloadAuthority | 下载权限     | Boolean | Default true                               |
| TIME              | 时间         | Date    |                                            |
| TYPE_1            | 类型一       | String  | Not Null                                   |
| TYPE_2            | 类型二       | String  |                                            |
| KEYWORDS          | 关键词       | String  | Not Null                                   |
| ABSTRACT          | 摘要         | String  |                                            |
| REFRENCE          | 参考文献     | String  |                                            |
| PICTURE           | 图片         | String  |                                            |
| OLD_FILENAME      | 中文名称     | String  |                                            |
| NEW_FILENAME      | 英文名称     | String  |                                            |
| FORMAT            | 数据格式     | String  |                                            |
| PATH              | 路径         | String  |                                            |
| RAM               | 数据大小     | String  |                                            |
| DOWNLOAD_TIM      | 下载数量     | Int     |                                            |

### 2.2 URL

- HDFS：地理数据存储及下载

| URL                         | FUNCTION                       | METHOD | PARAM                                                        | RESULT              | REMARK |
| --------------------------- | ------------------------------ | ------ | ------------------------------------------------------------ | ------------------- | ------ |
| /geodata/insert             | 插入用户                       | PUT    | title,uploader,type1,type2[,tags,source,abstractInfo,reference,pic,oldName,newName,format,path,ram,downloadTimes] | {code,body,message} |        |
| /geodata/batchinsert        | 批量插入                       | PUT    | [Geodata[,Geodata...]]                                       | {code,body,message} |        |
| /geodata/delete/{id}        | 删除用户                       | DELETE |                                                              | {code,body,message} |        |
| /geodata//batchdelete/{ids} | 批量删除                       | DELETE |                                                              | {code,body,message} |        |
| /geodata/select/{id}        | 查询用户                       | GET    |                                                              | {code,body,message} |        |
| /geodata/batchseletct/{ids} | 批量查询                       | GET    |                                                              | {code,body,message} |        |
| /geodata/allselect          | 全部查询                       | GET    |                                                              |                     |        |
| /geodata/byuserName         | 名字查询                       | GET    | String userName                                              |                     |        |
| /geodata/update             | 更新用户                       | POST   | id[,title,uploader,type1,type2,tags,source,abstractInfo,reference,pic,oldName,newName,format,path,ram,downloadTimes] | {code,body,message} |        |
| /geodata/batchupdate        | 批量更新                       | POST   | [Geodata[,Geodata...]]                                       | {code,body,message} |        |
| /geodata/bytype1            | 按照一级目录分类               | GET    | type, page                                                   | {code,body,message} |        |
| /geodata/bytype2            | 按照二级目录分类               | GET    | type, page                                                   | {code,body,message} |        |
| /geodata/dis                | 返回结果的唯一不同值与对应数量 | GET    | field, page                                                  | {code,body,message} |        |
| /geodata/downloadplus       | 更新数据库中下载次数           | GET    | id                                                           | {code,body,message} |        |
| /geodata/populardata        | 返回下载数量最多的五条数据     | GET    |                                                              |                     |        |

## 3 tb_student_paper

### 3.1 Table

| Column          | Description   | Type   | Remark                                       |
| --------------- | ------------- | ------ | -------------------------------------------- |
| ID              | 主键（编号）  | String | Not Null, Unique, Primary Key, Auto Create   |
| TITLE           | 标题          | String | Not Null, Unique                             |
| ENGLISH_TITLE   | 标题（英文）  | String | Not Null, Unique                             |
| AUTHOR          | 作者          | String | Not Null                                     |
| PUBLISHER       | 所在单位/学校 | String |                                              |
| TERTIARY_AUTHOR | 指导老师      | String |                                              |
| YEAR            | 发表年份      | String |                                              |
| TYPE            | 文章类型      | String | Not Null, "bachelor" or "master" or "doctor" |
| KEYWORDS        | 关键词        | String |                                              |
| ABSTRACT        | 摘要          | String |                                              |
| URL             | 链接          | String | Unique                                       |

### 3.2 URL

| URL                              | FUNCTION | METHOD | PARAM                                                        | RESULT              | REMARK      |
| -------------------------------- | -------- | ------ | ------------------------------------------------------------ | ------------------- | ----------- |
| /studentpaper/insert             | 插入用户 | PUT    | title,englishTitle,author,type[,publisher,tertiaryAuthor,year,keywords,abstract_,url] | {code,body,message} |             |
| /studentpaper/delete/{id}        | 删除用户 | DELETE |                                                              | {code,body,message} | body值为id  |
| /studentpaper/select/{id}        | 查询用户 | GET    |                                                              | {code,body,message} |             |
| /studentpaper/batchseletct/{ids} | 批量查询 | GET    | [pageNo,pageSize]                                            | ode,body,message}   | 逗号","分隔 |
| /studentpaper/allselect          | 查询用户 | GET    | [pageNo,pageSize]                                            | {code,body,message} | body为page  |
| /studentpaper/update             | 更新用户 | POST   | id[,title,englishTitle,author,publisher,tertiaryAuthor,year,type,keywords,abstract_,url] | {code,body,message} | id为必要值  |

## 4 tb_academic_paper

### 4.1 Table

| Column             | Description   | Type   | Remark                                     |
| ------------------ | ------------- | ------ | ------------------------------------------ |
| ID                 | 主键（编号）  | String | Not Null, Unique, Primary Key, Auto Create |
| TITLE              | 标题          | String | Not Null, Unique                           |
| ENGLISH_TITLE      | 标题（英文）  | String | Not Null                                   |
| TYPE               | 文章类型      | String | Not Null, "conference" or "journal"        |
| AUTHOR             | 作者          | String | Not Null                                   |
| AUTHOR_AFFILIATION | 所在单位/学校 | String |                                            |
| YEAR               | 发表年份      | String |                                            |
| SOURCE_NAME        | 期刊名称      | String |                                            |
| VOLUME             | 期刊卷数      | String |                                            |
| ISSUE              | 期刊期数      | String |                                            |
| PAGES              | 期刊页数      | String |                                            |
| KEYWORDS           | 关键词        | String |                                            |
| ABSTRACT           | 摘要          | String |                                            |
| DOI                | DOI           | String | Unique                                     |
| ISSU               | ISSU          | String |                                            |
| URL                | 链接          | String | Not Nul                                    |

### 4.2 URL

| URL                               | FUNCTION | METHOD | PARAM                                                        | RESULT              | REMARK      |
| --------------------------------- | -------- | ------ | ------------------------------------------------------------ | ------------------- | ----------- |
| /academicpaper/insert             | 插入用户 | PUT    | title,englishTitle,type,author,url[,authorAffiliation,year,sourceName,volume,issue,pages,keywords,abstract_,doi,issu] | {code,body,message} |             |
| /academicpaper/delete/{id}        | 删除用户 | DELETE |                                                              | {code,body,message} | body值为id  |
| /academicpaper/select/{id}        | 查询用户 | GET    |                                                              | {code,body,message} |             |
| /academicpaper/batchseletct/{ids} | 批量查询 | GET    | [pageNo,pageSize]                                            | ode,body,message}   | 逗号","分隔 |
| /academicpaper/allselect          | 查询用户 | GET    | [pageNo,pageSize]                                            | {code,body,message} | body为page  |
| /academicpaper/update             | 更新用户 | POST   | id[,title,englishTitle,type,author,authorAffiliation,year,sourceName,volume,issue,pages,keywords,abstract_,doi,issu,url] | {code,body,message} | id为必要值  |

## 5 tb_lectures

### 5.1 Table

| Column  | Description  | Type   | Remark                                     |
| ------- | ------------ | ------ | ------------------------------------------ |
| ID      | 主键（编号） | String | Not Null, Unique, Primary Key, Auto Create |
| NAME    | 姓名         | String | Not Null                                   |
| SPEAKER | 演讲者       | String |                                            |
| PLACE   | 地点         | String |                                            |
| TIME    | 时间         | String |                                            |
| URL     | 链接         | String |                                            |

### 5.2 URL

| URL                         | FUNCTION | METHOD | PARAM                            | RESULT              | REMARK      |
| --------------------------- | -------- | ------ | -------------------------------- | ------------------- | ----------- |
| /lecture/insert             | 插入用户 | PUT    | name[,speaker,place,time,url]    | {code,body,message} |             |
| /lecture/delete/{id}        | 删除用户 | DELETE |                                  | {code,body,message} | body值为id  |
| /lecture/select/{id}        | 查询用户 | GET    |                                  | {code,body,message} |             |
| /lecture/batchseletct/{ids} | 批量查询 | GET    | [pageNo,pageSize]                | ode,body,message}   | 逗号","分隔 |
| /lecture/allselect          | 查询用户 | GET    | [pageNo,pageSize]                | {code,body,message} | body为page  |
| /lecture/update             | 更新用户 | POST   | id[,name,speaker,place,time,url] | {code,body,message} | id为必要值  |

## 6 tb_online_tools

### 6.1 Table

| Column   | Description  | Type   | Remark                                     |
| -------- | ------------ | ------ | ------------------------------------------ |
| ID       | 主键（编号） | String | Not Null, Unique, Primary Key, Auto Create |
| NAME     | 名称         | String | Not Null, Unique                           |
| ABSTRACT | 介绍         | String |                                            |
| PICTURE  | 图片         | String |                                            |
| URL      | 链接         | String | Not Null                                   |

### 6.2 URL

- 基础：增删改查及批量操作

## 7 tb_map_servers

### 7.1 Table

| Column      | Description  | Type   | Remark                                     |
| ----------- | ------------ | ------ | ------------------------------------------ |
| ID          | 主键（编号） | String | Not Null, Unique, Primary Key, Auto Create |
| NAME        | 名称         | String | Not Null, Unique                           |
| COMPANY     | 公司         | String | Not Null                                   |
| REGION      | 地区         | String | Not Null, "CN" or "other"                  |
| SERVER      | 提供的服务   | String |                                            |
| LIMITED     | 使用限制     | String |                                            |
| PICTURE     | 图片         | String |                                            |
| DESCRIPTION | 描述         | String |                                            |
| URL         | 链接         | String | Not Null                                   |

### 7.2 URL

- 基础：增删改查及批量操作

## 8 tb_group_member

### 8.1 Table

| Column  | Description  | Type   | Remark                                         |
| ------- | ------------ | ------ | ---------------------------------------------- |
| ID      | 主键（编号） | Int    | Not Null, Unique, Primary Key, Auto Create     |
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

## 9 tb_log

### 9.1 Table

| Column   | Description  | Type | Remark |
| -------- | ------------ | ---- | ------ |
| ID       | 主键（编号） |      |        |
| ACTID    | 操作员id     |      |        |
| ROLE     | 操作员角色   |      |        |
| TIME     | 操作时间     |      |        |
| TABLE    | 操作表格     |      |        |
| OBJECTID | 操作数据id   |      |        |
| TYPE     | 操作类型     |      |        |

### 9.2 URL

- 基础：增加数据（增、删、查操作时触发）
- 基础：查询数据及批量操作

- 查询数据（通过ACTID，通过TIME（TIME范围），通过TABLE）

- 定期删除日志：数据表触发器（如超过10000条数据，以时间顺序删除8000条）

## 10 tb_teach_model

### 10.1 Table

| **Column**    | **Description**    | **Type**  | **Remark**                                 |
| ------------- | ------------------ | :-------- | :----------------------------------------- |
| TEACHMODEL_ID | 教学案例编号       | Int       | Not Null, Unique, Primary Key, Auto Create |
| NAME          | 名称               | String    | Not Null                                   |
| DESCRIPTION   | 描述               | String    |                                            |
| DATE          | 创建日期           | TimeStamp | Not Null, Auto Create                      |
| KEYWORDS      | 关键字             | String    |                                            |
| GROUP_ID      | 案例来源团队       | String    |                                            |
| AUTHOR_ID     | 案例作者           | String    |                                            |
| EMAIL         | 案例作者邮箱       | String    |                                            |
| FILE_TEMPLATE | 案例临时存储路径   | String    | Not Null                                   |
| FILE_PATH     | 案例转换后存储路径 | String    | Not Null                                   |
| PIC_PATH      | 案例图片样式       | String    |                                            |
| FILE_TYPE     | 案例文件类型       | String    | Not Null                                   |

### 10.2 URL

| **URL**                              | **FUNCTION**                         | **REMARK**       | **METHOD** | **PARAM**                                             | **RESULT**            |
| ------------------------------------ | ------------------------------------ | ---------------- | ---------- | ----------------------------------------------------- | --------------------- |
| /uploadTeachModel                    | 管理员教学案例上传                   | 权限控制：管理员 | POST       | @SessionAttribute("role"){data, uploadPic, uploadFil} | {code,message}        |
| /unregisterTeachModel/{teachModelId} | 管理员删除教学案例                   | 权限控制：管理员 | DELETE     | @SessionAttribute("role"){teachModelId, userId}       | {code,message}        |
| /allselect                           | 获取所有教学案例                     | 所有用户         | GET        | {userId}                                              | {code, body, message} |
| /select/{id}                         | 获取指定ID的教学案例的详细描述和文件 | 所有用户         | GET        | {teachModelId}                                        | {code, body, message} |

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

| Name   | Task                                                         |
| ------ | ------------------------------------------------------------ |
| 周育全 | base类实现，tb_user、tb_student_paper、tb_academic_paper、tb_group_member设计及实现、数据收集及入库 |
| 张郑良 | tb_log设计及实现                                             |
| 赵佳晖 | tb_geographic_data、tb_online_tools设计及实现                |
| 张家瑞 | tb_teaching_cases设计及实现                                  |
| 冯瀑霏 | tb_map_servers设计及实现，数据收集及入库                     |
| 陈柠檬 | tb_lectures设计及实现、数据收集及入库                        |

- academic_paper：数据量
- map_server：company、region
- lecture：detail_time 和 time

