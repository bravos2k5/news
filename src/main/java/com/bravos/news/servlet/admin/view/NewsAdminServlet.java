package com.bravos.news.servlet.admin.view;

import com.bravos.news.dao.NewsDAO;
import com.bravos.news.dto.NewsItemAdmin;
import com.bravos.news.dto.UserInfo;
import com.bravos.news.entity.News;
import com.bravos.news.entity.enums.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = {"/admin/news","/admin/news/edit","/admin/news/add"})
public class NewsAdminServlet extends HttpServlet {

    private NewsDAO newsDAO;

    @Override
    public void init() {
        newsDAO = new NewsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if(uri.startsWith("/admin/news/edit")) {
            handleNewsEdit(req,resp);
            return;
        }
        if(uri.startsWith("/admin/news/add")) {
            handleCreateNews(req,resp);
            return;
        }
        handleNewsList(req,resp);
    }

    private void handleCreateNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("page","newsadmin-add.jsp");
        req.getRequestDispatcher(getServletContext().getContextPath() + "/admin.jsp").forward(req,resp);
    }

    private void handleNewsEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserInfo user = (UserInfo) session.getAttribute("user");
        String id = req.getParameter("id");
        if(id == null || id.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if(user.getRole() != Role.ADMIN && !newsDAO.isCorrectAuthor(user.getId().toString(),id)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        News news = newsDAO.findById(UUID.fromString(id));
        if(news == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        req.setAttribute("page","newsadmin-detail.jsp");
        req.setAttribute("news",news);
        req.getRequestDispatcher(getServletContext().getContextPath() + "/admin.jsp").forward(req,resp);
    }

    private void handleNewsList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserInfo user = (UserInfo) session.getAttribute("user");
        List<NewsItemAdmin> newsList;
        if(user.getRole() == Role.ADMIN) {
            newsList = newsDAO.findAllItemAdmin();
        }
        else {
            newsList = newsDAO.findByAuthor(user.getId());
        }
        req.setAttribute("newsItems",newsList);
        req.setAttribute("page","newsadmin.jsp");
        req.getRequestDispatcher(getServletContext().getContextPath() + "/admin.jsp").forward(req,resp);
    }

}
