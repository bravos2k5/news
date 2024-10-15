package com.bravos.news.servlet.admin.api;

import com.bravos.news.listener.DataStorage;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

@WebServlet("/increaseViewCount")
public class ViewCountApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            HttpSession session = req.getSession();
            String newsId = req.getParameter("id");
            UUID uuid = UUID.fromString(newsId);
            Set<String> viewedNews = (Set<String>) session.getAttribute("viewedNews");
            if(viewedNews.add(newsId)) {
                HashMap<UUID,Integer> viewsMap = DataStorage.GET_VIEW_COUNT_MAP();
                viewsMap.put(uuid,viewsMap.getOrDefault(uuid,0) + 1);
                session.setAttribute("viewedNews",viewedNews);
            }
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}
