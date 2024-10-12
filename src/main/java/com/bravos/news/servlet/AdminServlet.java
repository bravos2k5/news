package com.bravos.news.servlet;

import com.bravos.news.dao.CategoryDAO;
import com.bravos.news.dao.LetterDAO;
import com.bravos.news.dao.NewsDAO;
import com.bravos.news.dao.UserDAO;
import com.bravos.news.entity.Category;
import com.bravos.news.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {

    private UserDAO userDAO;
    private NewsDAO newsDAO;
    private CategoryDAO categoryDAO;
    private LetterDAO letterDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
        newsDAO = new NewsDAO();
        categoryDAO = new CategoryDAO();
        letterDAO = new LetterDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(session.getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }
        String function = req.getRequestURI().substring("/admin".length());
        switch (function) {
            case "/categories" -> handleCategory(req);
            case "/news" -> handleNews(req);
            case "/personal" -> handlePersonal(req);
            case "/users" -> handleUsers(req);
            case "/letters" -> handleLetters(req);
            default -> handleDashBoard(req);
        }
        req.getRequestDispatcher(getServletContext().getContextPath() + "/admin.jsp").forward(req,resp);
    }

    private void handleLetters(HttpServletRequest req) {

    }

    private void handleUsers(HttpServletRequest req) {
        List<User> users = userDAO.findAll();
        req.setAttribute("users",users);
        req.setAttribute("page","users.jsp");
    }

    private void handlePersonal(HttpServletRequest req) {
        req.setAttribute("page","personal.jsp");
    }

    private void handleNews(HttpServletRequest req) {

        req.setAttribute("page","newsadmin.jsp");
    }

    private void handleDashBoard(HttpServletRequest req) {
        req.setAttribute("page","dashboard.jsp");
    }

    private void handleCategory(HttpServletRequest req) {
        List<Category> categories = categoryDAO.findAll();
        req.setAttribute("categories",categories);
        req.setAttribute("page","category.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }



}
