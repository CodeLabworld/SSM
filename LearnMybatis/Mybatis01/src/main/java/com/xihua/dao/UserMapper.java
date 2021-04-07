package com.xihua.dao;

import com.xihua.pojo.User;

import java.util.List;
import java.util.Map;

//注意
public interface UserMapper {
    //获取全部用户
    List<User> getUserList();

    //根据id查询
    User getUserByid(int id);

    //inset一个用户
    int addUser(User user);

    //insert一个用户（map键值插入）
    int addUserMap(Map<String, Object> map);

    //修改一个用户
    int updateUser(User user);

    //删除一个用户
    int deleteUser(int id);

}
