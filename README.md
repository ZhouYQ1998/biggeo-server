# Database

## 1 tb_user

### 1.1 Table

| Column        | Description  | Type   | Remark                           |
| ------------- | ------------ | ------ | -------------------------------- |
| ID            | 主键（编号） | String | Primary Key, Auto Create         |
| NAME          | 用户名       | String | Not Null, Unique                 |
| PASSWORD      | 密码         | String | Not Null                         |
| PHONE         | 电话号码     | String | Not Null                         |
| EMAIL         | 邮箱         | String | Not Null                         |
| ICON          | 头像         | String | Default 1.png                    |
| COUNTRY       | 国家（地区） | String |                                  |
| INSTITUTE     | 机构         | String |                                  |
| INSTITUTETYPE | 机构类型     | String |                                  |
| FIELD         | 专业领域     | String |                                  |
| PURPOSE       | 用途         | String |                                  |
| ROLE          | 用户角色     | String | Not Null, "manager" or "visitor" |
| SIGN_COUNT    | 用户访问次数 | Int    | Not Null, Default 0              |

### 1.2 URL

| URL                       | FUNCTION   | METHOD | PARAM                               | RESULT              | REMARK              |
| ------------------------- | ---------- | ------ | ----------------------------------- | ------------------- | ------------------- |
| /user/insert              | 插入用户   | PUT    | name,password,phone,email,role[...] | {code,body,message} |                     |
| /user/batchinsert         | 批量插入   | PUT    | User[,User...]                      | {code,body,message} |                     |
| /user/delete/{id}         | 删除用户   | DELETE |                                     | {code,body,message} |                     |
| /user/deletebyname/{name} | 删除用户   | DELETE |                                     | {code,body,message} |                     |
| /user//batchdelete/{ids}  | 批量删除   | DELETE |                                     | {code,body,message} |                     |
| /user/select/{id}         | 查询用户   | GET    |                                     | {code,body,message} |                     |
| /user/selectbyname/{name} | 查询用户   | GET    |                                     | {code,body,message} |                     |
| /user/batchseletct/{ids}  | 批量查询   | GET    | [pageNo,pageSize]                   | ode,body,message}   |                     |
| /user/allselect           | 查询用户   | GET    | [pageNo,pageSize]                   | {code,body,message} |                     |
| /user/update              | 更新用户   | POST   | id[...]                             | {code,body,message} |                     |
| /user/batchupdate         | 批量更新   | POST   | User[,User...]                      | {code,body,message} |                     |
| /user/login               | 用户登录   | POST   | name,password                       | {code,body,message} |                     |
| /user/loginstatus         | 登录状态   | GET    |                                     | {code,body,message} |                     |
| /user/logout              | 用户注销   | POST   |                                     | {code,body,message} |                     |
| /user/check/{email}       | 发送验证码 | GET    |                                     | {code,body,message} | body值为code和email |
| /user/checkbyname/{name}  | 发送验证码 | GET    |                                     | {code,body,message} |                     |
| /user/statistic           | 统计访问量 | GET    |                                     | {code,body,message} |                     |

- ids为逗号分隔的id组字符串，下同
- 删除操作返回值的body值为id或name，下同
- 更新操作id为必要值，其他属性为可选值，下同
- 插入和更新的批量操作返回值的body值包括“t”（实体）和“message”（实体操作结果），下同
- PageNo默认为1，PageSize默认为20，下同

## 2 tb_geographic_dataset

### 2.1 Table

| Column     | Description  | Type   | Remark                   |
| ---------- | ------------ | ------ | ------------------------ |
| ID         | 主键（编号） | String | Primary Key, Auto Create |
| TITLE      | 数据集标题   | String | Not Null                 |
| AUTHOR     | 作者         | String |                          |
| RE_TIME    | 时间         | Date   |                          |
| TYPE_1     | 类型一       | String | Not Null                 |
| TYPE_2     | 类型二       | String | Not Null                 |
| KEYWORDS   | 关键词       | String |                          |
| ABSTRACT   | 摘要         | String |                          |
| REFRENCE   | 参考文献     | String |                          |
| PICTURE    | 图片         | String |                          |
| FORMAT     | 数据格式     | String | Not Null                 |
| PATH       | 路径         | String | Not Null                 |
| RAM        | 数据大小     | String | Not Null                 |
| VIEW_TIMES | 访问数量     | Int    | Not Null                 |

### 2.2 URL

| URL                         | FUNCTION             | METHOD | PARAM                          | RESULT              | REMARK |
| --------------------------- | -------------------- | ------ | ------------------------------ | ------------------- | ------ |
| /geodata/insert             | 插入数据             | PUT    | title,userName,type1,path[...] | {code,body,message} |        |
| /geodata/batchinsert        | 批量插入             | PUT    | Geodata[,Geodata...]           | {code,body,message} |        |
| /geodata/delete/{id}        | 删除数据             | DELETE |                                | {code,body,message} |        |
| /geodata//batchdelete/{ids} | 批量删除             | DELETE |                                | {code,body,message} |        |
| /geodata/select/{id}        | 查询数据             | GET    |                                | {code,body,message} |        |
| /geodata/batchseletct/{ids} | 批量查询             | GET    |                                | {code,body,message} |        |
| /geodata/allselect          | 全部查询             | GET    |                                | {code,body,message} |        |
| /geodata/update             | 更新数据             | POST   | id[...]                        | {code,body,message} |        |
| /geodata/batchupdate        | 批量更新             | POST   | Geodata[,Geodata...]           | {code,body,message} |        |
| /geodata/bytype1            | 一级分类             | GET    | type,[pageNo,pageSize]]        | {code,body,message} |        |
| /geodata/bytype2            | 二级分类             | GET    | type,[pageNo,pageSize]]        | {code,body,message} |        |
| /geodata/dis                | 字段唯一不同值与数量 | GET    | field,[pageNo,pageSize]]       | {code,body,message} |        |
| /geodata/populardata        | 查询最多下载         | GET    |                                | {code,body,message} |        |
| /geodata/detail/{id}        | 查询数据集详情       | GET    |                                | {code,body,message} |        |

## 3 tb_geographic_dataitem

### 3.1 Table

| Column  | Description  | Type   | Remark                   |
| ------- | ------------ | ------ | ------------------------ |
| ID      | 主键（编号） | String | Primary Key, Auto Create |
| TITLE   | 标题         | String | Not Null                 |
| DATASET | 数据集       | String | Not Null, Foreign Key    |
| FORMAT  | 数据格式     | String | Not Null                 |
| RAM     | 数据大小     | String | Not Null                 |

## 4 tb_student_paper

### 4.1 Table

| Column          | Description   | Type   | Remark                                       |
| --------------- | ------------- | ------ | -------------------------------------------- |
| ID              | 主键（编号）  | String | Primary Key, Auto Create                     |
| TITLE           | 标题          | String | Not Null, Unique                             |
| ENGLISH_TITLE   | 标题（英文）  | String |                                              |
| AUTHOR          | 作者          | String | Not Null                                     |
| PUBLISHER       | 所在单位/学校 | String |                                              |
| TERTIARY_AUTHOR | 指导老师      | String |                                              |
| YEAR            | 发表年份      | String |                                              |
| TYPE            | 文章类型      | String | Not Null, "bachelor" or "master" or "doctor" |
| KEYWORDS        | 关键词        | String |                                              |
| ABSTRACT        | 摘要          | String |                                              |
| URL             | 链接          | String | Unique                                       |

### 4.2 URL

| URL                              | FUNCTION | METHOD | PARAM                               | RESULT              | REMARK |
| -------------------------------- | -------- | ------ | ----------------------------------- | ------------------- | ------ |
| /studentpaper/insert             | 插入论文 | PUT    | title,englishTitle,author,type[...] | {code,body,message} |        |
| /studentpaper/delete/{id}        | 删除论文 | DELETE |                                     | {code,body,message} |        |
| /studentpaper/select/{id}        | 查询论文 | GET    |                                     | {code,body,message} |        |
| /studentpaper/selectnew          | 查询最新 | GET    |                                     | {code,body,message} |        |
| /studentpaper/fuzzyname/{key}    | 模糊查询 | GET    | [pageNo,pageSize]                   | {code,body,message} |        |
| /studentpaper/batchseletct/{ids} | 批量查询 | GET    | [pageNo,pageSize]                   | {code,body,message} |        |
| /studentpaper/allselect          | 查询论文 | GET    | [pageNo,pageSize]                   | {code,body,message} |        |
| /studentpaper/update             | 更新论文 | POST   | id[...]                             | {code,body,message} |        |

## 5 tb_academic_paper

### 5.1 Table

| Column             | Description   | Type   | Remark                              |
| ------------------ | ------------- | ------ | ----------------------------------- |
| ID                 | 主键（编号）  | String | Primary Key, Auto Create            |
| TITLE              | 标题          | String | Not Null, Unique                    |
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
| URL                | 链接          | String | Not Null                            |

### 5.2 URL

| URL                               | FUNCTION | METHOD | PARAM                      | RESULT              | REMARK |
| --------------------------------- | -------- | ------ | -------------------------- | ------------------- | ------ |
| /academicpaper/insert             | 插入论文 | PUT    | title,type,author,url[...] | {code,body,message} |        |
| /academicpaper/delete/{id}        | 删除论文 | DELETE |                            | {code,body,message} |        |
| /academicpaper/select/{id}        | 查询论文 | GET    |                            | {code,body,message} |        |
| /academicpaper/selectnew          | 查询最新 | GET    |                            | {code,body,message} |        |
| /academicpaper/fuzzyname/{key}    | 模糊查询 | GET    | [pageNo,pageSize]          | {code,body,message} |        |
| /academicpaper/batchseletct/{ids} | 批量查询 | GET    | [pageNo,pageSize]          | {code,body,message} |        |
| /academicpaper/allselect          | 查询论文 | GET    | [pageNo,pageSize]          | {code,body,message} |        |
| /academicpaper/update             | 更新论文 | POST   | id[...]                    | {code,body,message} |        |

## 6 tb_lectures

### 6.1 Table

| Column      | Description  | Type   | Remark                   |
| ----------- | ------------ | ------ | ------------------------ |
| ID          | 主键（编号） | String | Primary Key, Auto Create |
| NAME        | 姓名         | String | Not Null                 |
| SPEAKER     | 演讲者       | String |                          |
| PLACE       | 地点         | String |                          |
| TIME        | 发布时间     | Date   |                          |
| DETAIL_TIME | 详细时间     | String |                          |
| URL         | 链接         | String |                          |

### 6.2 URL

| URL                           | FUNCTION | METHOD | PARAM                   | RESULT              | REMARK |
| ----------------------------- | -------- | ------ | ----------------------- | ------------------- | ------ |
| /lecture/insert               | 插入讲座 | PUT    | name[...]               | {code,body,message} |        |
| /lecture/delete/{id}          | 删除讲座 | DELETE |                         | {code,body,message} |        |
| /lecture/select/{id}          | 查询讲座 | GET    |                         | {code,body,message} |        |
| /lecture/selectnew            | 查询最新 | GET    |                         | {code,body,message} |        |
| /lecture/fuzzyname/{key}      | 模糊查询 | GET    | [pageNo,pageSize]       | {code,body,message} |        |
| /lecture/fuzzynameorder/{key} | 模糊查询 | GET    | order[,pageNo,pageSize] | {code,body,message} |        |
| /lecture/batchseletct/{ids}   | 批量查询 | GET    | [pageNo,pageSize]       | {code,body,message} |        |
| /lecture/allselect            | 查询讲座 | GET    | [pageNo,pageSize]       | {code,body,message} |        |
| /lecture/allselectorder       | 查询讲座 | GET    | order[,pageNo,pageSize] | {code,body,message} |        |
| /lecture/update               | 更新讲座 | POST   | id[...]                 | {code,body,message} |        |
| /lecture/crawl                | 爬取讲座 | GET    |                         | {code,body,message} |        |

- 爬取讲座：删除已有讲座公告，爬取地球科学学院官网最新3页讲座
- order为必要值（desc或asc）

## 7 tb_online_tools

### 7.1 Table

| Column   | Description  | Type   | Remark                   |
| -------- | ------------ | ------ | ------------------------ |
| ID       | 主键（编号） | String | Primary Key, Auto Create |
| NAME     | 名称         | String | Not Null, Unique         |
| ABSTRACT | 介绍         | String |                          |
| PICTURE  | 图片         | String |                          |
| URL      | 链接         | String | Not Null, Unique         |

### 7.2 URL

- 基础：增删改查及批量操作

| URL                             | FUNCTION | METHOD | PARAM                      | RESULT              | REMARK |
| ------------------------------- | -------- | ------ | -------------------------- | ------------------- | ------ |
| /onlinetools/insert             | 插入     | PUT    | NAME,URL[...]              | {code,body,message} |        |
| /onlinetools/batchinsert        | 批量插入 | PUT    | OnlineTool[,OnlineTool...] | {code,body,message} |        |
| /onlinetools/delete/{id}        | 删除     | DELETE |                            | {code,body,message} |        |
| /onlinetools/batchdelete/{id}   | 批量删除 | DELETE |                            | {code,body,message} |        |
| /onlinetools/select/{id}        | 查询     | GET    |                            | {code,body,message} |        |
| /onlinetools/batchseletct/{ids} | 批量     | GET    | [pageNo,pageSize]          | {code,body,message} |        |
| /onlinetools/allselect          | 查询     | GET    | [pageNo,pageSize]          | {code,body,message} |        |
| /onlinetools/fuzzyname/{key}    | 模糊查询 | GET    | [pageNo,pageSize]          | {code,body,message} |        |
| /onlinetools/update             | 更新     | POST   | id[...]                    | {code,body,message} |        |

## 8 tb_map_servers

### 8.1 Table

| Column      | Description  | Type   | Remark                    |
| ----------- | ------------ | ------ | ------------------------- |
| ID          | 主键（编号） | String | Primary Key, Auto Create  |
| NAME        | 名称         | String | Not Null, Unique          |
| COMPANY     | 公司         | String | Not Null                  |
| REGION      | 地区         | String | Not Null, "CN" or "other" |
| SERVER      | 提供的服务   | String |                           |
| LIMITED     | 使用限制     | String |                           |
| PICTURE     | 图片         | String |                           |
| DESCRIPTION | 描述         | String |                           |
| URL         | 链接         | String | Not Null, Unique          |

### 8.2 URL

- 基础：增删改查及批量操作

| URL                            | FUNCTION | METHOD | PARAM                        | RESULT              | REMARK |
| ------------------------------ | -------- | ------ | ---------------------------- | ------------------- | ------ |
| /mapservice/insert             | 插入     | PUT    | NAME,COUNTYR,REGION,URL[...] | {code,body,message} |        |
| /mapservice/batchinsert        | 批量插入 | PUT    | [MapService，[MapService]]   | {code,body,message} |        |
| /mapservice/delete/{id}        | 删除     | DELETE |                              | {code,body,message} |        |
| /mapservice/batchdelete/{id}   | 批量删除 | DELETE |                              | {code,body,message} |        |
| /mapservice/select/{id}        | 查询     | GET    |                              | {code,body,message} |        |
| /mapservice/batchseletct/{ids} | 批量     | GET    | [pageNo,pageSize]            | ode,body,message}   |        |
| /mapservice/allselect          | 查询     | GET    | [pageNo,pageSize]            | {code,body,message} |        |
| /mapservice/fuzzyname/{key}    | 模糊查询 | GET    | [pageNo,pageSize]            | {code,body,message} |        |
| /mapservice/update             | 更新     | POST   | id[...]                      | {code,body,message} |        |

## 9 tb_group_member

### 9.1 Table

| Column  | Description  | Type   | Remark                                         |
| ------- | ------------ | ------ | ---------------------------------------------- |
| ID      | 主键（编号） | Int    | Primary Key, Auto Create                       |
| NAME    | 姓名         | String | Not Null                                       |
| VERSION | 开发版本     | String | "V1.0" or "V2.0"                               |
| TEAM    | 小组编号     | String |                                                |
| ROLE    | 角色         | String | Not Null, "instructor" or "leader" or "member" |
| EMAIL   | 邮箱         | String |                                                |
| PHOTO   | 照片         | String |                                                |
### 9.2 URL

| URL                     | FUNCTION       | METHOD | PARAM                         | RESULT      | REMARK |
| ----------------------- | -------------- | ------ | ----------------------------- | ----------- | ------ |
| /member/allselect       | 查询所有开发者 | GET    | [PageNo,PageSize]             | {code,body} |        |
| /member/selectteachers  | 查询指导老师   | GET    | [PageNo,PageSize]             | {code,body} |        |
| /member/selectbyversion | 查询开发者     | GET    | version,[PageNo,PageSize]     | {code,body} |        |
| /member/selectbyteam    | 查询开发者     | GET    | version,team[PageNo,PageSize] | {code,body} |        |

## 10 tb_log

### 10.1 Table

| Column   | Description  | Type      | Remark                   |
| -------- | ------------ | --------- | ------------------------ |
| ID       | 主键（编号） | String    | Primary Key, Auto Create |
| ACTID    | 操作员id     | String    | Not Null                 |
| ROLE     | 操作员角色   | String    | Not Null                 |
| TIME     | 操作时间     | Timestamp | Not Null                 |
| TABLE    | 操作表格     | String    | Not Null                 |
| OBJECTID | 操作数据id   | String    | Not Null                 |
| TYPE     | 操作类型     | String    | Not Null                 |

### 10.2 URL

| URL                        | FUNCTION | METHOD | PARAM                                      | RESULT              | REMARK |
| -------------------------- | -------- | ------ | ------------------------------------------ | ------------------- | ------ |
| /log/insert                | 插入日志 | PUT    | id,actId,role,time,tableName,objectId,type | {code,body,message} |        |
| /log/selectbyactid/{actId} | 查询日志 | GET    |                                            | {code,body,message} |        |
| /log//selectbytime         | 查询日志 | GET    | startTime,endTime                          | {code,body,message} |        |
| /log/allselect             | 查询日志 | GET    | [pageNo,pageSize]                          | {code,body,message} |        |

## 11 tb_teach_model

- 该部分由 **邵剑**、**王泺棋** 负责

### 11.1 Table

| **Column**    | **Description**    | **Type**  | **Remark**               |
| ------------- | ------------------ | :-------- | :----------------------- |
| TEACHMODEL_ID | 教学案例编号       | String    | Primary Key, Auto Create |
| NAME          | 名称               | String    | Not Null                 |
| DESCRIPTION   | 描述               | String    |                          |
| DATE          | 创建日期           | TimeStamp | Not Null, Auto Create    |
| KEYWORDS      | 关键字             | String    |                          |
| GROUP_ID      | 案例来源团队       | String    |                          |
| AUTHOR_ID     | 案例作者           | String    |                          |
| EMAIL         | 案例作者邮箱       | String    |                          |
| FILE_TEMPLATE | 案例临时存储路径   | String    | Not Null                 |
| FILE_PATH     | 案例转换后存储路径 | String    | Not Null                 |
| PIC_PATH      | 案例图片样式       | String    |                          |
| FILE_TYPE     | 案例文件类型       | String    | Not Null                 |

### 11.2 URL

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
| 周育全 | base类设计及实现，tb_user、tb_student_paper、tb_academic_paper、tb_lectures、tb_group_member设计及实现，数据整理 |
| 张郑良 | tb_log设计及实现                                             |
| 赵佳晖 | tb_geographic_data、tb_online_tools、tb_map_servers设计及实现，数据整理 |
| 张家瑞 | tb_teaching_cases设计及实现                                  |
| 冯瀑霏 | 数据收集及入库                                               |
| 陈柠檬 | 数据收集及入库                                               |
