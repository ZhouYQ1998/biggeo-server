# 1 User

## 1.1 Table

| Column   | Description  | Type   | Remark                           |
| -------- | ------------ | ------ | -------------------------------- |
| Id       | 唯一身份编号 | String | Not Null, Unique, Auto Create    |
| Name     | 用户名       | String | Not Null, Unique                 |
| Password | 密码         | String | Not Null                         |
| Phone    | 电话号码     | String |                                  |
| Email    | 邮箱         | String |                                  |
| Role     | 用户角色     | String | Not Null, "Manager" or "Visitor" |

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

