package com.bravos.news.servlet.admin.api;

import com.bravos.news.dao.NewsDAO;
import com.bravos.news.dto.NewsAdminRequest;
import com.bravos.news.dto.UserInfo;
import com.bravos.news.entity.News;
import com.bravos.news.entity.enums.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet("/api/public/news/*")
public class NewsApiServlet extends HttpServlet {

    private ObjectMapper mapper;
    private NewsDAO newsDAO;

    @Override
    public void init() {
        newsDAO = new NewsDAO();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        if(uri.startsWith("/api/public/news/edit")) {
            updateNews(req,resp);
            return;
        }
        if(uri.startsWith("/api/public/news/remove")) {
            deleteNews(req,resp);
            return;
        }
        if (uri.startsWith("/api/public/news/create")) {
            createNews(req,resp);
            return;
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void createNews(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        UserInfo user = (UserInfo) session.getAttribute("user");
        NewsAdminRequest news = mapper.readValue(req.getReader(),NewsAdminRequest.class);
        news.setImage("https://bravosrepo2.blob.core.windows.net/image/" + news.getId() + "/" + news.getImage());
        NewsResponse newsResponse = new NewsResponse(1);
        StringBuilder message = new StringBuilder();
        if(news.getTitle() == null || news.getTitle().isBlank()) {
            message.append("Tiêu đề không được trống").append("\n");
        }
        if(news.getContent() == null || news.getContent().isBlank()) {
            message.append("Nội dung không được bỏ trống").append("\n");
        }
        if(message.isEmpty()) {
            newsResponse.setStatus(1);
        }
        newsResponse.setMessage(message.toString());
        if(newsResponse.getStatus() != 0) {
            int status = newsDAO.insert(news,user.getId());
            newsResponse.setStatus(status);
        }
        PrintWriter writer = resp.getWriter();
        writer.print(mapper.writeValueAsString(newsResponse));
        writer.flush();
    }

    private void deleteNews(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String newsId = req.getParameter("id");
        HttpSession session = req.getSession();
        UserInfo user = (UserInfo) session.getAttribute("user");
        if(user.getRole() != Role.ADMIN &&
                !newsDAO.isCorrectAuthor(user.getId().toString(),newsId)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        PrintWriter writer = resp.getWriter();
        int status = newsDAO.delete(new News(UUID.fromString(newsId))) ? 1 : 0;
        writer.print(mapper.writeValueAsString(new NewsResponse(status)));
        writer.flush();
    }

    private void updateNews(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        NewsAdminRequest newsRequest = mapper.readValue(req.getReader(),NewsAdminRequest.class);
        HttpSession session = req.getSession();
        UserInfo user = (UserInfo) session.getAttribute("user");
        NewsResponse newsResponse = new NewsResponse();
        if(newsRequest == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        News news = newsDAO.findById(UUID.fromString(newsRequest.getId()));
        if (news == null) {
            resp.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        }
        if(user.getRole() != Role.ADMIN &&
                !newsDAO.isCorrectAuthor(user.getId().toString(),newsRequest.getId())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        if(newsRequest.isImgStatus()) {
            String imgSrc = "https://bravosrepo2.blob.core.windows.net/image/" + newsRequest.getId() + "/" + newsRequest.getImage();
            newsRequest.setImage(imgSrc);
        }
        if(newsRequest.getHome() == null) newsRequest.setHome("");
        if(user.getRole() != Role.ADMIN) {
            newsRequest.setHome(news.isHome() ? "home" : "");
        }
        int status = newsDAO.updateInfo(newsRequest);
        newsResponse.setStatus(status);
        newsResponse.setNewImgUrl(newsRequest.getImage());
        PrintWriter writer = resp.getWriter();
        writer.print(mapper.writeValueAsString(newsResponse));
        writer.flush();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NewsResponse {
        private int status;
        private String newImgUrl;
        private String message;

        public NewsResponse(int status) {
            this.status = status;
        }
    }


}
