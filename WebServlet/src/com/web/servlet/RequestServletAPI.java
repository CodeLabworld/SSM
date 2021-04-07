package com.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestServletAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的资源路径
        String requestURI = req.getRequestURI();
        System.out.println(requestURI);
        //绝对路径
        StringBuffer requestURL = req.getRequestURL();
        System.out.println(requestURL);
        //客户端ip
        String remoteHost = req.getRemoteHost();
        System.out.println(remoteHost);
        //请求头
        String header = req.getHeader("User-Agent");
        System.out.println(header);
        //请求参数


    }
}
