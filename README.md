# Database

## 1 tb_user

### 1.1 Table

| Column   | Description  | Type   | Remark                           |
| -------- | ------------ | ------ | -------------------------------- |
| ID       | 唯一身份编号 | String | Not Null, Unique, Auto Create    |
| NAME     | 用户名       | String | Not Null, Unique                 |
| PASSWORD | 密码         | String | Not Null                         |
| PHONE    | 电话号码     | String |                                  |
| EMAIL    | 邮箱         | String |                                  |
| ICON     | 头像         | String |                                  |
| ROLE     | 用户角色     | String | Not Null, "Manager" or "Visitor" |

### 1.2 URL

| URL                       | FUNCTION | METHOD | PARAM                                    | RESULT              | REMARK              |
| ------------------------- | -------- | ------ | ---------------------------------------- | ------------------- | ------------------- |
| /user/insert              | 插入用户 | PUT    | name,password,role[,phone,email,icon]    | {code,body,message} | phone,email为可选值 |
| /user/batchinsert         | 批量插入 | PUT    | [User[,User...]]                         | {code,body,message} |                     |
| /user/delete/{id}         | 删除用户 | DELETE |                                          | {code,body,message} | body值为id          |
| /user/deletebyname/{name} | 删除用户 | DELETE |                                          | {code,body,message} | body值为name        |
| /user//batchdelete/{ids}  | 批量删除 | DELETE |                                          | {code,body,message} | 逗号","分隔         |
| /user/select/{id}         | 查询用户 | GET    |                                          | {code,body,message} |                     |
| /user/selectbyname/{name} | 查询用户 | GET    |                                          | {code,body,message} |                     |
| /user/batchseletct/{ids}  | 批量查询 | GET    |                                          | {code,body,message} | 逗号","分隔         |
| /user/update              | 更新用户 | POST   | id[,name,password,role,phone,email,icon] | {code,body,message} | id为必要值          |
| /user/batchupdate         | 批量更新 | POST   | [User[,User...]]                         | {code,body,message} |                     |
| /user/login               | 用户登录 | POST   | name,password                            | {code,body,message} |                     |
| /user/loginstatus         | 登录状态 | GET    |                                          | {code,body,message} |                     |
| /user/logout              | 用户注销 | POST   |                                          | {code,body,message} | body值为id          |

- 批量操作返回值的body值包括“t”（实体）和“message”（实体操作结果），下同

## 2 tb_geographic_data

### 2.1 Table

| Column       | Description  | Type  | Remark                       |
| ------------ | ------------ | ----- | ---------------------------- |
| ID           | 唯一身份编号 | Int   | Not Nullt,Unique,Auto Create |
| TITLE        | 标题         | Sring | Not Null                     |
| UPLOADED     | 上传者       | Sring |                              |
| RE_TIME      | 时间         | Date  |                              |
| TYPE_1       | 类型一       | Sring | Not Null                     |
| TYPE_2       | 类型二       | Sring |                              |
| KEYWORDS     | 关键词       | Sring | Not Null                     |
| SOURCE       | 资源         | Sring |                              |
| ABSTRACT     | 摘要         | Sring |                              |
| REFRENCE     | 参考文献     | Sring |                              |
| PICTURE      | 图片         | Sring |                              |
| OLD_FILENAME | 中文名称     | Sring |                              |
| NEW_FILENAME | 英文名称     | Sring |                              |
| FORMAT       | 数据格式     | Sring |                              |
| URL          | 链接         | Sring |                              |
| RAM          | 数据大小     | Sring |                              |
| DOWNLOAD_TIM | 下载数量     | Int   |                              |



### 2.2 URL



## 3 tb_student_paper

### 3.1 Table

| Column          | Description   | Type   | Remark                                       |
| --------------- | ------------- | ------ | -------------------------------------------- |
| ID              | 唯一身份编号  | Int    | Not Null, Unique, Auto Create                |
| TITLE           | 标题          | String | Not Null, Unique                             |
| ENGLISH_TITLE   | 标题（英文）  | String | Not Null, Unique                             |
| AUTHOR          | 作者          | String | Not Null                                     |
| PUBLISHER       | 所在单位/学校 | String |                                              |
| TERTIARY_AUTHOR | 指导老师      | String |                                              |
| YEAR            | 发表年份      | String |                                              |
| TYPE            | 文章类型      | String | Not NUll, "bachelor" or "master" or "doctor" |
| KEYWORDS        | 关键词        | String |                                              |
| ABSTRACT        | 摘要          | String |                                              |
| URL             | 链接          | String |                                              |

### 3.2 URL



## 4 tb_academic_paper

### 4.1 Table

| Column             | Description   | Type   | Remark                              |
| ------------------ | ------------- | ------ | ----------------------------------- |
| ID                 | 唯一身份编号  | Int    | Not Null, Unique, Auto Create       |
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
| DOI                | DOI           | String |                                     |
| ISSU               | ISSU          | String |                                     |
| URL                | 链接          | String | Not Null,Unique                     |

### 4.2 URL



## 5 tb_lectures

### 5.1 Table

| Column  | Description  | Type  | Remark                      |
| ------- | ------------ | ----- | --------------------------- |
| ID      | 唯一身份编号 | Int   | Not Null,Unique,Auto Create |
| NAME    | 姓名         | Sring | Not Null                    |
| SPEAKER | 演讲者       | Sring |                             |
| PLACE   | 地点         | Sring |                             |
| TIME    | 时间         | Sring |                             |
| URL     | 链接         | Sring |                             |

### 5.2 URL



## 6 tb_online_tools

### 6.1 Table

| Column   | Description  | Type   | Remark                        |
| -------- | ------------ | ------ | ----------------------------- |
| ID       | 唯一身份编号 | Int    | Not Null, Unique, Auto Create |
| NAME     | 名称         | String | Not Null, Unique              |
| ABSTRACT | 介绍         | String |                               |
| PICTURE  | 图片         | String |                               |
| URL      | 链接         | String | Not Null,Unique               |

### 6.2 URL



## 7 tb_map_servers

### 7.1 Table

| Column      | Description  | Type   | Remark                        |
| ----------- | ------------ | ------ | ----------------------------- |
| ID          | 唯一身份编号 | Int    | Not Null, Unique, Auto Create |
| NAME        | 名称         | String | Not Null, Unique              |
| COMPANY     | 公司         | String | Not Null, Unique              |
| REGION      | 地区         | String | Not Null, "CN" or "other"     |
| SERVER      | 提供的服务   | String |                               |
| LIMITED     | 使用限制     | String |                               |
| FEATURE     | 特点         | String |                               |
| PICTURE     | 图片         | String |                               |
| DESCRIPTION | 描述         | String |                               |
| URL         | 链接         | String | Not Null,Unique               |

### 7.2 URL



## 8 tb_group_member

### 8.1 Table

| Column  | Description  | Type   | Remark                                         |
| ------- | ------------ | ------ | ---------------------------------------------- |
| ID      | 唯一身份编号 | Int    | Not NUll, Unique                               |
| NAME    | 姓名         | String | Not NUll                                       |
| VERSION | 开发版本     | String | "V1.0" or "V2.0"                               |
| TEAM    | 小组编号     | String |                                                |
| ROLE    | 角色         | String | Not Null, "instructor" or "leader" or "member" |
| EMAIL   | 邮箱         | String |                                                |
| PHOTO   | 照片         | String |
                                           |
### 8.2 URL

| URL                   | FUNCTION       | METHOD | PARAM                         | RESULT      | REMARK |
| --------------------- | -------------- | ------ | ----------------------------- | ----------- | ------ |
| /member/showall       | 查询所有开发者 | GET    | [PageNo,PageSize]             | {code,body} |        |
| /member/showteachers  | 查询指导老师   | GET    | [PageNo,PageSize]             | {code,body} |        |
| /member/showbyversion | 查询开发者     | GET    | version,[PageNo,PageSize]     | {code,body} |        |
| /member/showbyteam    | 查询开发者     | GET    | version,team[PageNo,PageSize] | {code,body} |        |

- PageNo默认为1，PageSize默认为20

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

