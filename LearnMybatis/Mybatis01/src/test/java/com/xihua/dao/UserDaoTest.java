package com.xihua.dao;

import com.xihua.pojo.User;
import com.xihua.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class UserDaoTest {
    @Test
    public void test() {
        //获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        try {
            //getMapper
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> userList = mapper.getUserList();

            for (User user : userList) {
                System.out.println(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }

    }

    @Test
    public void getUserByidTest() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserByid(2);
        System.out.println(user);
        sqlSession.close();
    }

    @Test
    public void InsertUserTest() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int res = mapper.addUser(new User(1, "秋", "123456"));
        if (res > 0) {
            System.out.println("添加完成");
        }
        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void InsertUserMapTest() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("username", "qiuqiu");
        map.put("userpassword", "12345678");
        mapper.addUserMap(map);

        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void UpdateUserTest() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int res = mapper.updateUser(new User(6, "cong", "8888"));
        if (res > 0) {
            System.out.println("修改完成");
        }
        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void DeletUserTest() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int res = mapper.deleteUser(4);
        if (res > 0) {
            System.out.println("删除完成");
        }
        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }
}
