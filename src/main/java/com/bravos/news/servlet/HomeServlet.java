package com.bravos.news.servlet;

import com.bravos.news.dao.NewsDAO;
import com.bravos.news.dto.NewsItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private NewsDAO newsDAO;

    @Override
    public void init() {
        newsDAO = new NewsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<NewsItem> itemList = newsDAO.getHomePageItems();
        req.setAttribute("newsList",itemList);
        req.setAttribute("page","items.jsp");
        req.getRequestDispatcher("main.jsp").forward(req,resp);
    }

}
