package com.bravos.news.servlet.admin.api;

import com.bravos.news.utils.azure.SasTokenGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/public/generateSasToken")
public class SasTokenApi extends HttpServlet {

    private ObjectMapper mapper;

    @Override
    public void init() {
        mapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        Cookie[] cookies = req.getCookies();
        String sasToken = null;
        for (var cookie : cookies) {
            if(cookie.getName().equals("sasToken")) {
                writer.print(mapper.writeValueAsString(new Response(1,cookie.getValue())));
                return;
            }
        }
        try {
            sasToken = SasTokenGenerator.generateSasToken("image", "rw", 3);
        } catch (Exception e) {
            e.printStackTrace();
            writer.print(mapper.writeValueAsString(new Response(0,null)));
        }

        Cookie cookie = new Cookie("sasToken",sasToken);
        cookie.setMaxAge(3 * 3600);
        cookie.setHttpOnly(true);
        resp.addCookie(cookie);
        writer.print(mapper.writeValueAsString(new Response(1,sasToken)));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Response {
        private int status;
        private String token;
    }

}
