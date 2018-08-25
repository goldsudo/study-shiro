# 利用此项目学习shiro
## 目前学习进度：
### 1.shiro的整体认证流程
* 构建SecurityManager环境
* 主体Subject提交认证请求
* 获取UsernamePasswordToken用户令牌并登陆
* 登录subject.login(token)
* 角色校验subject.checkRole("xxx");
* 权限校验subject.checkPermission("xxx");
* 登出subject.logout();
### 2.基于ini配置文件的Realm
### 3.基于jdbc的Realm（需要使用符合shiro的表结构，或者自定义sql语句）
### 4.自定义的Realm
### 5.spring、springMVC整合shiro
### 6.使用jdbcTemplate作为shiro自定义Realm的数据来源
### 7.基于aop的shiro注解实现api的角色或者权限过滤（@RequiresRoles用来过滤角色，@RequiresPermissions用来过滤权限）
### 8.自定义shiro的filter规则，实现role1 or role2的权限过滤规则（shiro的filter默认只支持role1 and role2）
