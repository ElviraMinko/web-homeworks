package ru.itmo.wp.servlet;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CharsetFilter extends HttpFilter{
    //кодировка
    private String encoding;

    public void init(FilterConfig config) throws ServletException{
        //читаем из конфигурации
        encoding=config.getInitParameter("requestEncoding");

        //если не установлена - устанавливаем Cp1251
        if(encoding==null) encoding="Cp1251";
    }

    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain next)
            throws IOException, ServletException{
        request.setCharacterEncoding(encoding);
        next.doFilter(request, response);
    }

    public void destroy(){}
}
