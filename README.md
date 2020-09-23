# 1 User

## 1.1 Table

| Column   | Type   | Remark                           |
| -------- | ------ | -------------------------------- |
| Id       | String | Not Null, Unique, Auto Create    |
| Name     | String | Not Null, Unique                 |
| Password | String | Not Null                         |
| Phone    | String |                                  |
| Email    | String |                                  |
| Role     | String | Not Null, "Manager" or "Visitor" |

## 1.2 URL

| URL                       | FUNCTION | METHOD | PARAM                               | RESULT              | REMARK              |
| ------------------------- | -------- | ------ | ----------------------------------- | ------------------- | ------------------- |
| /user/insert              | 插入用户 | PUT    | name,password,role[,phone,email]    | {code,body,message} | phone,email为可选值 |
| /user/delete/{id}         | 删除用户 | DELETE |                                     | {code,message}      |                     |
| /user/deletebyname/{name} | 删除用户 | DELETE |                                     | {code,message}      |                     |
| /user/select/{id}         | 查询用户 | GET    |                                     | {code,body,message} |                     |
| /user/selectbyname/{name} | 查询用户 | GET    |                                     | {code,body,message} |                     |
| /user/update              | 更新用户 | POST   | id[,name,password,role,phone,email] | {code,body,message} | id为必要值          |

