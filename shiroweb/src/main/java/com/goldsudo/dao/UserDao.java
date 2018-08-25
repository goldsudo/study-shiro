package com.goldsudo.dao;

import com.goldsudo.entity.User;

import javax.annotation.Resource;
import java.util.List;

public interface UserDao {
    User getUserByUserName(String userName);

    List<String> queryRolesByUserName(String userName);
}
