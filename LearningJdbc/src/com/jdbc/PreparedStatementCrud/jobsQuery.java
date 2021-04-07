package com.jdbc.PreparedStatementCrud;

import com.jdbc.Bean.Job;
import com.jdbc.util.JdbcConnections;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;

/**
 * 针对jobs的表的操作
 */
public class jobsQuery {
    public static void TestQueryForJobs() {
        Connection coon = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            //连接数据库
            coon = JdbcConnections.getConnection();
            //预编译sql语句
            String sql = "SELECT job_id,job_title,min_salary, max_salary FROM `jobs` WHERE job_id=?";
            ps = coon.prepareStatement(sql);
            //填充占位符
            ps.setObject(1, "AD_VP");
            //执行
            resultSet = ps.executeQuery();
            //处理结果集
            if (resultSet.next()) {//判断结果集的下一条是否有数据，有就指针下移
                //获取当前数据的各个字段
                String job_id = resultSet.getString(1);
                String job_title = resultSet.getString(2);
                int min_salary = resultSet.getInt(3);
                int max_salary = resultSet.getInt(4);

                //把数据封装为一个对象
                Job job = new Job(job_id, job_title, min_salary, max_salary);
                System.out.println(job);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //关闭资源
            JdbcConnections.closeResource(coon, ps, resultSet);
        }

    }

    /**
     * 通用jobs表查询操作
     *
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Job QueryForJobs(String sql, Object... args) {
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
                Job job = new Job();
                //            处理一行数据
                for (int i = 0; i < columnCount; i++) {
                    Object columValue = rs.getObject(i + 1);
                    //获取每个列的列名(存储类名和数据库中名字相同时用getColumnName,不同时取别名后用getColumnLable)
                    String columnName = rsmd.getColumnName(i + 1);
                    //把对象里的columnName属性赋值为columValue,通过反射
                    Field field = Job.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(job, columValue);
                }
                return job;
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
        } finally {
            //资源关闭
            JdbcConnections.closeResource(coon, ps, rs);

        }
        return null;
    }

    public static void main(String[] args) {
//        TestQueryForJobs();
        String sql = "SELECT job_id,job_title,min_salary, max_salary FROM `jobs` WHERE job_id=?";
        Job job1 = QueryForJobs(sql, "AD_VP");
        System.out.println(job1);

    }
}
