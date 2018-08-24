create table users
(
	id int auto_increment
		primary key,
	username varchar(40) not null,
	password varchar(60) not null
);

create table user_roles
(
	id int auto_increment
		primary key,
	username varchar(40) null,
	role_name varchar(40) null
);

create table roles_permissions
(
  id         int auto_increment
    primary key,
  role_name  varchar(40) not null,
  permission varchar(60) not null
);

create table test_user
(
	user_name varchar(40) not null
		primary key,
	password varchar(60) null
);

