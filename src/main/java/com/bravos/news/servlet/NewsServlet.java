package com.bravos.news.servlet;

import com.bravos.news.dao.NewsDAO;
import com.bravos.news.dto.NewsItem;
import com.bravos.news.dto.NewsThread;
import com.bravos.news.entity.News;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

@WebServlet("/news/*")
public class NewsServlet extends HttpServlet {

    private NewsDAO newsDAO;

    @Override
    public void init() {
        newsDAO = new NewsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String uri = req.getRequestURI();
            if(uri.startsWith("/news/id/")) {
                UUID id = UUID.fromString(uri.substring("/news/id/".length()));
                NewsThread news = newsDAO.getNewsThread(id);
                if(news == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                List<News> newsList = newsDAO.getNewsByCategory(news.getNews().getCategoryId());
                HttpSession session = req.getSession();
                List<News> recentNews = (List<News>) session.getAttribute("recentNewsList");
                recentNews.remove(news.getNews());
                recentNews.addFirst(news.getNews());
                if(recentNews.size() == 11) {
                    recentNews.removeLast();
                }
                session.setAttribute("recentNewsList",recentNews);
                req.setAttribute("page","news.jsp");
                req.setAttribute("newsThread",news);
                req.setAttribute("scNewsList",newsList);
                req.getRequestDispatcher(getServletContext().getContextPath() + "/main.jsp").forward(req,resp);
            }
            else if(uri.startsWith("/news/category/")) {
                int id = Integer.parseInt(uri.substring("/news/category/".length()));
                List<NewsItem> itemList = newsDAO.getNewsItemsByCategory(id);
                if(itemList == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                req.setAttribute("newsList",itemList);
                req.setAttribute("page","items.jsp");
                req.getRequestDispatcher(getServletContext().getContextPath() + "/main.jsp").forward(req,resp);
            }
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
