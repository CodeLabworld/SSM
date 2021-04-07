package com.web.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Servlet1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 请求转发
         * 地址不变；一次请求，共享request域中的数据 
         */
        String username = req.getParameter("username");
        System.out.println(username);
        req.setAttribute("key1", "1");

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/Servlet2");
        //走向Servlet2
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
