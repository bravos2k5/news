package com.bravos.news.servlet.admin.view;

import com.bravos.news.dao.CategoryDAO;
import com.bravos.news.dao.LetterDAO;
import com.bravos.news.dao.NewsDAO;
import com.bravos.news.dao.UserDAO;
import com.bravos.news.dto.NewsItemAdmin;
import com.bravos.news.dto.UserInfo;
import com.bravos.news.entity.Category;
import com.bravos.news.entity.User;
import com.bravos.news.entity.enums.Role;
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

    private CategoryDAO categoryDAO;
    private LetterDAO letterDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
        categoryDAO = new CategoryDAO();
        letterDAO = new LetterDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String function = req.getRequestURI().substring("/admin".length());
        switch (function) {
            case "/categories" -> handleCategory(req);
            case "/personal" -> handlePersonal(req);
            case "/users" -> handleUsers(req);
            case "/letters" -> handleLetters(req);
            default -> handleDashBoard(req);
        }
        req.getRequestDispatcher(getServletContext().getContextPath() + "/admin.jsp").forward(req,resp);
    }

    private void handleLetters(HttpServletRequest req) {
        req.setAttribute("page","letters.jsp");
    }

    private void handleUsers(HttpServletRequest req) {
        List<User> users = userDAO.findAll();
        req.setAttribute("users",users);
        req.setAttribute("page","users.jsp");
    }

    private void handlePersonal(HttpServletRequest req) {
        req.setAttribute("page","personal.jsp");
    }

    private void handleDashBoard(HttpServletRequest req) {
        req.setAttribute("page","dashboard.jsp");
    }

    private void handleCategory(HttpServletRequest req) {
        List<Category> categories = categoryDAO.findAll();
        req.setAttribute("categories",categories);
        req.setAttribute("page","category.jsp");
    }

}
