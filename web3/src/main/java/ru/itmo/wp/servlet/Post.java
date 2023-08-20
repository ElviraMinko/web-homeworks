package ru.itmo.wp.servlet;


import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class Post extends HttpServlet {
    private static final List<Pair> messages = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String uri = req.getRequestURI();
        HttpSession session = req.getSession();
        Gson gson = new Gson();
        if (uri.contains("message/auth")) {
            String name = req.getParameter("user");
            if (name != null) {
                session.setAttribute("user", name);
                resp.getOutputStream().write(convertToBytes(gson.toJson(name)));
            } else if (session.getAttribute("user") == null) {
                resp.getOutputStream().write(convertToBytes(gson.toJson("")));
            } else {
                resp.getOutputStream().write(convertToBytes(gson.toJson(session.getAttribute("user"))));
            }
        } else if (uri.contains("message/findAll")) {
            System.out.println(gson.toJson(messages));
            if(session.getAttribute("user") != null){
                resp.getOutputStream().write(convertToBytes(gson.toJson(messages)));
            }

        } else if (uri.contains("message/add")) {
            if (session.getAttribute("user") == null) {
                session.setAttribute("user", "");
            }
            if(req.getParameter("text") != null) {
                messages.add(new Pair((String) session.getAttribute("user"), req.getParameter("text")));
            }
        }
        resp.setContentType("application/json");

    }

    static class Pair {
        String user;
        String text;

        Pair(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }

    public byte[] convertToBytes(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }
}
// http://localhost:8080/messages.html