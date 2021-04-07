package com.jdbc.PreparedStatementCrud;

import com.jdbc.util.JdbcConnections;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 利用preparedStatemwnt实现增删改查
 */
public class preparedStatementUpdate {
    //修改表中的一条记录
    public static void testUpdata() {
        Connection coon = null;
        PreparedStatement ps = null;
        try {
            //获取数据库的连接
            coon = JdbcConnections.getConnection();
            //预编译sql语句，返回preparedStatement的实例
            String sql = "update jobs set job_title = ? where job_id = ?";
            ps = coon.prepareStatement(sql);
            //填充占位符
            ps.setObject(1, "QIU");
            ps.setObject(2, "AD_VP");
            //执行
            ps.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //资源关闭
            JdbcConnections.closeResource(coon, ps);
        }
    }

    /**
     * 通用增删改操作
     *
     * @param sql
     * @param args
     */
    public static int Updata(String sql, Object... args) {//sql中占位符的个数与可变形参的长度相等
        Connection coon = null;
        PreparedStatement ps = null;
        try {
            //连接数据库
            coon = JdbcConnections.getConnection();
            //预编译sql语句
            ps = coon.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);//sql中索引从1开始
            }
            //执行
            return ps.executeUpdate();//返回操作的行数
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //资源的关闭
            JdbcConnections.closeResource(coon, ps);
        }
        return 0;
    }

    public static void main(String[] args) {
//        testUpdata();
//        String sql = "update jobs set job_title = ? where job_id = ?";
//        Updata(sql, "Cong", "AD_VP");
    }
}
