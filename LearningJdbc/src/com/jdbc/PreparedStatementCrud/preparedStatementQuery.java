package com.jdbc.PreparedStatementCrud;

import com.jdbc.Bean.Job;
import com.jdbc.util.JdbcConnections;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class preparedStatementQuery {

    /**
     * 利用preparedStatement实现不同表的查询操作方法一,返回一条记录
     *
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public static <T> T Query1(Class<T> clazz, String sql, Object... args) {
        Connection coon = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //连接数据库
            coon = JdbcConnections.getConnection();
            //预编译sql语句
            ps = coon.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            //获取结果集的元数据（得到列数）
            ResultSetMetaData rsmd = rs.getMetaData();
            //得到列数
            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {
                T t = clazz.newInstance();
                //            处理一行数据
                for (int i = 0; i < columnCount; i++) {
                    Object columValue = rs.getObject(i + 1);
                    //获取每个列的列名(存储类名和数据库中名字相同时用getColumnName,不同时取别名后用getColumnLable)
                    String columnName = rsmd.getColumnName(i + 1);
                    //把对象里的columnName属性赋值为columValue,通过反射
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columValue);
                }
                return t;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            //资源关闭
            JdbcConnections.closeResource(coon, ps, rs);

        }
        return null;
    }

    /**
     * 利用preparedStatement实现不同表的查询操作方法二，返回多条记录
     *
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public static <T> List<T> Query2(Class<T> clazz, String sql, Object... args) {
        Connection coon = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //连接数据库
            coon = JdbcConnections.getConnection();
            //预编译sql语句
            ps = coon.prepareStatement(sql);
            //填充占位符(判断是否需要填充)
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }
            }

            rs = ps.executeQuery();
            //获取结果集的元数据（得到列数）
            ResultSetMetaData rsmd = rs.getMetaData();
            //得到列数
            int columnCount = rsmd.getColumnCount();
            //创建集合对象
            ArrayList<T> list = new ArrayList<>();
            while (rs.next()) {
                T t = clazz.newInstance();
                // 处理一行数据
                for (int i = 0; i < columnCount; i++) {
                    Object columValue = rs.getObject(i + 1);
                    //获取每个列的列名(存储类名和数据库中名字相同时用getColumnName,不同时取别名后用getColumnLable)
                    String columnName = rsmd.getColumnName(i + 1);
                    //把对象里的columnName属性赋值为columValue,通过反射
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columValue);
                }
                list.add(t);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            //资源关闭
            JdbcConnections.closeResource(coon, ps, rs);

        }
        return null;
    }

    public static void main(String[] args) {
//        String sql = "SELECT job_id,job_title,min_salary, max_salary FROM `jobs` WHERE job_id=?";
//        Job job = Query1(Job.class, sql, "AD_VP");
//        System.out.println(job);

        String sql = "SELECT job_id,job_title,min_salary, max_salary FROM `jobs`";
        List<Job> list = Query2(Job.class, sql);
        list.forEach(System.out::println);
    }

}
