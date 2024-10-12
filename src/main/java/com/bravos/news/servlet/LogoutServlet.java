package com.bravos.news.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("user");
        for (Cookie cookie : req.getCookies()) {
            if(cookie.getName().equals("accessToken")) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                cookie.setPath("/");
                resp.addCookie(cookie);
                break;
            }
        }
        resp.sendRedirect( getServletContext().getContextPath() + "/login");
    }

}
