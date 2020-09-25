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

| URL                       | FUNCTION | METHOD | PARAM                               | RESULT              | REMARK              |
| ------------------------- | -------- | ------ | ----------------------------------- | ------------------- | ------------------- |
| /user/insert              | 插入用户 | PUT    | name,password,role[,phone,email]    | {code,body,message} | phone,email为可选值 |
| /user/delete/{id}         | 删除用户 | DELETE |                                     | {code,message}      |                     |
| /user/deletebyname/{name} | 删除用户 | DELETE |                                     | {code,message}      |                     |
| /user/select/{id}         | 查询用户 | GET    |                                     | {code,body,message} |                     |
| /user/selectbyname/{name} | 查询用户 | GET    |                                     | {code,body,message} |                     |
| /user/update              | 更新用户 | POST   | id[,name,password,role,phone,email] | {code,body,message} | id为必要值          |
| /user/login               | 用户登录 | POST   | name,password                       | {code,body,message} |                     |
| /user/loginstatus         | 登录状态 | GET    |                                     | {code,body,message} |                     |
| /user/logout              | 用户注销 | POST   |                                     | {code,body,message} | body值为id          |

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
| TYPE            | 文章类型      | String | Not Null, "bachelor" or "master" or "doctor" |
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
| ID       | 唯一身份编号 | Int    | Not NUll, Unique, Auto Create |
| NAME     | 名称         | String | Not NUll, Unique              |
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
| ROLE    | 角色         | String | Not Null, "Instructor" or "Leader" or "Member" |
| EMAIL   | 邮箱         | String |                                                |
| PHOTO   | 照片         | String |                                                |

### 8.2 URL



# Resut code
