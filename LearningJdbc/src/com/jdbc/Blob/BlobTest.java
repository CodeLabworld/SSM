package com.jdbc.Blob;

import com.jdbc.util.JdbcConnections;

import java.io.*;
import java.sql.*;

public class BlobTest {
    /**
     * 向数据库中添加Blob类型的数据
     */
    public static void InsertTest() {
        Connection coon = null;
        PreparedStatement ps = null;
        try {
            coon = JdbcConnections.getConnection();
            String sql = "insert into customers(name,email,birth,photo)values(?,?,?,?)";
            ps = coon.prepareStatement(sql);
            ps.setObject(1, "秋秋");
            ps.setObject(2, "1103637169@qq.com");
            ps.setObject(3, "2020-03-10");
            FileInputStream fis = new FileInputStream(new File("D:\\宝贝\\-1f763af2bb334a9b.jpg"));
            ps.setBlob(4, fis);
            ps.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcConnections.closeResource(coon, ps);

        }
    }

    public static void BolbQuery() {
        Connection coon = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            coon = JdbcConnections.getConnection();
            String sql = "SELECT id,name,email,birth,photo FROM `customers` WHERE id=?;";
            ps = coon.prepareStatement(sql);
            ps.setInt(1, 19);
            rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");//获取值
                //........
                System.out.println(name);
                //获取Blob类型的数据,保存在本地
                Blob blob = rs.getBlob("photo");
                is = blob.getBinaryStream();
                fos = new FileOutputStream("1.jpg");
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JdbcConnections.closeResource(coon, ps, rs);
        }


    }

    public static void main(String[] args) {
//        InsertTest();
        BolbQuery();
    }

}
