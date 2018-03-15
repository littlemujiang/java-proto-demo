package com;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletDemo extends HttpServlet{


    public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

        this.getServletContext();
        response.getOutputStream();
        request.getLocale();

     }

}