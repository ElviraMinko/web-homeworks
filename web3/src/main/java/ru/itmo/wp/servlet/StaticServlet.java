package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;


public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String uri = request.getRequestURI();
        String[] str = uri.split("[+]");
        response.setContentType(getServletContext().getMimeType(str[0]));
        OutputStream outputStream = response.getOutputStream();
        boolean flag = true;
        for (String s : str) {
            File file = new File("C:\\Users\\Эльвира\\IdeaProjects\\web3\\src\\main\\webapp\\static" + s);
            File file2 = new File(getServletContext().getRealPath("/static/" + s));
            if (!file.isFile() && !file2.isFile()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                flag = false;
            }
        }

        if (flag) {
            for (String s : str) {
                File file = new File("C:\\Users\\Эльвира\\IdeaProjects\\web3\\src\\main\\webapp\\static" + s);
                if (file.isFile()) {
                    Files.copy(file.toPath(), outputStream);
                } else {
                    file = new File(getServletContext().getRealPath("/static/" + s));
                    Files.copy(file.toPath(), outputStream);
                }
            }
        }
    }

}
 /* String uri = request.getRequestURI();
        File file1 = new File("src\\main\\webapp\\static"+ uri);
        if (file1.isFile()) {
            response.setContentType(getServletContext().getMimeType(file1.getName()));
            try (OutputStream outputStream = response.getOutputStream()) {
                Files.copy(file1.toPath(), outputStream);
            }
        } else {
            File file = new File(getServletContext().getRealPath("/static" + uri));
            if (file.isFile()) {
                response.setContentType(getServletContext().getMimeType(file.getName()));
                try (OutputStream outputStream = response.getOutputStream()) {
                    Files.copy(file.toPath(), outputStream);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }*/
 /*String uri = request.getRequestURI();
    String[] str = uri.split("[+]");
        response.setContentType(getServletContext().getMimeType(str[0]));
                OutputStream outputStream = response.getOutputStream();

                for (String s : str) {
                File file = new File("src\\main\\webapp\\static" + s);
                if (file.isFile()) {
                Files.copy(file.toPath(), outputStream);
                } else {
                file = new File(getServletContext().getRealPath("/static/" + s));
                if (file.isFile()) {
                Files.copy(file.toPath(), outputStream);

                } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                }
                }*/