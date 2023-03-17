# 本地项目位置

[E:\StudySpace\2022\springboot-study](E:\StudySpace\2022\springboot-study)

## 写入新用户时请先查询
```sql
SELECT a.id
FROM `base_admin` a, `base_user` u 
WHERE a.`username` = "22" OR u.`username` = "22"
```