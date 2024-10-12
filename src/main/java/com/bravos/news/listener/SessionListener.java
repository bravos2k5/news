package com.bravos.news.listener;

import com.bravos.news.entity.News;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Getter
    private static int activeSessions = 0;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute("recentNewsList",new ArrayList<News>());
        session.setAttribute("viewedNews",new HashSet<String>());
        ++activeSessions;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.removeAttribute("recentNewsList");
        session.removeAttribute("viewedNews");
        --activeSessions;
    }

}
