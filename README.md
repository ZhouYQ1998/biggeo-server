# 1 tb_user

## 1.1 Table

| Column   | Description  | Type   | Remark                           |
| -------- | ------------ | ------ | -------------------------------- |
| ID       | 唯一身份编号 | String | Not Null, Unique, Auto Create    |
| NAME     | 用户名       | String | Not Null, Unique                 |
| PASSWORD | 密码         | String | Not Null                         |
| PHONE    | 电话号码     | String |                                  |
| EMAIL    | 邮箱         | String |                                  |
| ICON     | 头像         | String |                                  |
| ROLE     | 用户角色     | String | Not Null, "Manager" or "Visitor" |

## 1.2 URL

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

# 2 tb_student_paper

## 2.1 Table

| Column          | Description   | Type   | Remark                                       |
| --------------- | ------------- | ------ | -------------------------------------------- |
| ID              | 唯一身份编号  | Int    | Not NUll, Unique, Auto Create                |
| TITLE           | 标题          | String | Not NUll, Unique                             |
| ENGLISH_TITLE   | 标题（英文）  | String | Not NUll, Unique                             |
| AUTHOR          | 作者          | String | Not NUll                                     |
| PUBLISHER       | 所在单位/学校 | String |                                              |
| TERTIARY_AUTHOR | 指导老师      | String |                                              |
| YEAR            | 发表年份      | String |                                              |
| TYPE            | 文章类型      | String | Not NUll, "bachelor" or "master" or "doctor" |
| KEYWORDS        | 关键词        | String |                                              |
| ABSTRACT        | 摘要          | String |                                              |
| URL             | 链接          | String | Unique                                       |

# 3 tb_academic_paper

## 3.1 Table

| Column             | Description   | Type   | Remark                              |
| ------------------ | ------------- | ------ | ----------------------------------- |
| ID                 | 唯一身份编号  | Int    | Not NUll, Unique, Auto Create       |
| TITLE              | 标题          | String | Not NUll, Unique                    |
| ENGLISH_TITLE      | 标题（英文）  | String | Not NUll, Unique                    |
| TYPE               | 文章类型      | String | Not Null, "conference" or "journal" |
| AUTHOR             | 作者          | String | Not NUll                            |
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
| URL                | 链接          | String | Unique                              |

# 4 tb_online_tools

## 4.1 Table

| Column   | Description  | Type   | Remark                        |
| -------- | ------------ | ------ | ----------------------------- |
| ID       | 唯一身份编号 | Int    | Not NUll, Unique, Auto Create |
| NAME     | 名称         | String | Not NUll, Unique              |
| ABSTRACT | 介绍         | String |                               |
| PICTURE  | 图片         | String |                               |
| URL      | 链接         | String | Unique                        |

# 5 tb_map_servers

## 5.1 Table

| Column      | Description  | Type   | Remark                        |
| ----------- | ------------ | ------ | ----------------------------- |
| ID          | 唯一身份编号 | Int    | Not NUll, Unique, Auto Create |
| NAME        | 名称         | String | Not NUll, Unique              |
| COMPANY     | 公司         | String | Not NUll, Unique              |
| REGION      | 地区         | String | Not NUll, "CN" or "Other"     |
| SERVER      | 提供的服务   | String |                               |
| LIMITED     | 使用限制     | String |                               |
| FEATURE     | 特点         | String |                               |
| PICTURE     | 图片         | String |                               |
| DESCRIPTION | 描述         | String |                               |
| URL         | 链接         | String | Unique                        |

