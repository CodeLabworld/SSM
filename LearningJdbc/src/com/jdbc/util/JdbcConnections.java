package com.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 操作数据库的工具类
 */
public class JdbcConnections {
    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        //加载配置文件
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.propertise");
        Properties pros = new Properties();
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");
        //加载驱动
        Class.forName(driverClass);
        //获取连接
        Connection coon = DriverManager.getConnection(url, user, password);
        return coon;
    }

    /**
     * 关闭资源操作
     *
     * @param coon
     * @param ps
     */
    public static void closeResource(Connection coon, Statement ps) {
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (coon != null)
                coon.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 关闭资源
     *
     * @param coon
     * @param ps
     * @param rs
     */
    public static void closeResource(Connection coon, Statement ps, ResultSet rs) {
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (coon != null)
                coon.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
