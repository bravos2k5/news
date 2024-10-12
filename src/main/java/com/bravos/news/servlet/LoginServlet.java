package com.bravos.news.servlet;

import com.bravos.news.dao.UserDAO;
import com.bravos.news.dto.UserInfo;
import com.bravos.news.entity.User;
import com.bravos.news.jwt.JwtUtil;
import com.bravos.news.utils.RegexUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        HttpSession session = req.getSession();
        boolean isRemember = false;
        String accessKey = null;
        UserInfo user = (UserInfo) session.getAttribute("user");
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals("remember")) {
                isRemember = cookie.getValue().equalsIgnoreCase("true");
                continue;
            }
            if(cookie.getName().equals("accessToken")) {
                accessKey = cookie.getValue();
            }
        }
        if(user != null && JwtUtil.isValid(accessKey,user.getUsername())) {
            resp.sendRedirect("admin");
            return;
        }
        if(isRemember && accessKey != null) {
            user = JwtUtil.extractUserInfoIfValid(accessKey);
            user.setFullName(userDAO.findFullName(user.getId()));
            session.setAttribute("user",user);
            resp.sendRedirect("admin");
            return;
        }
        req.getRequestDispatcher("login.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username").trim();
        String rawPassword = req.getParameter("password").trim();
        boolean isRemember = req.getParameter("remember") != null;
        User user;
        HttpSession session = req.getSession();
        if(!RegexUtil.isUsername(username) && rawPassword.length() < 6) {
            req.setAttribute("message","Thông tin tài khoản mật khẩu không chính xác!");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
            return;
        }
        user = userDAO.findByUsername(username);
        if(user == null || BCrypt.checkpw(rawPassword,user.getPassword())) {
            req.setAttribute("message","Thông tin tài khoản mật khẩu không chính xác!");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
            return;
        }
        UserInfo userInfo = new UserInfo(user.getId(),user.getUsername(),user.getFullName(),user.getRole());
        Cookie rememberCookie = new Cookie("remember",isRemember ? "true" : "false");
        rememberCookie.setMaxAge(3600 * 24);
        Cookie tokenCookie = new Cookie("accessToken",JwtUtil.generateToken(userInfo));
        tokenCookie.setHttpOnly(true);
        tokenCookie.setMaxAge(3600 * 24);
        session.setAttribute("user",userInfo);
        resp.addCookie(rememberCookie);
        resp.addCookie(tokenCookie);
        resp.sendRedirect("admin");
    }

}
