package com.bravos.news.listener;

import com.bravos.news.dao.CategoryDAO;
import com.bravos.news.dao.NewsDAO;
import com.bravos.news.dto.NewsItem;
import com.bravos.news.dto.SideBarNews;
import com.bravos.news.entity.Category;
import com.bravos.news.jwt.JwtFilter;
import com.bravos.news.utils.DatabaseManager;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

@WebListener
public class ContextListener implements ServletContextListener {

    private final NewsDAO newsDAO = new NewsDAO();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        FilterRegistration.Dynamic registration = context.addFilter("jwtFilter",new JwtFilter());
        registration.addMappingForUrlPatterns(null,false,"/*");
        this.firstInitialize(context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        updateCountViews();
        DatabaseManager.gI().shutdown();
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                System.err.println("Error deregistering JDBC driver: " + driver);
            }
        }
    }

    private void firstInitialize(ServletContext context) {
        List<Category> categories = new CategoryDAO().findAll();
        context.setAttribute("categories",categories);
        this.startCountViews();
        this.updateNews(context);
    }

    private void updateNews(ServletContext context) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                List<SideBarNews> lastestNews = newsDAO.getLastestNews();
                context.setAttribute("latestNewsList",lastestNews);
                List<SideBarNews> importantNews = newsDAO.getImportantNews();
                context.setAttribute("importantNewsList",importantNews);
                List<NewsItem> homePageItems = newsDAO.getHomePageItems();
                context.setAttribute("newsList",homePageItems);
            }
        };
        timer.scheduleAtFixedRate(task,0,300000);
    }

    private void startCountViews() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                updateCountViews();
            }
        };
        timer.scheduleAtFixedRate(task,300000,300000);
    }

    private synchronized void updateCountViews() {
        NewsDAO newsDAO = new NewsDAO();
        HashMap<UUID,Integer> map = DataStorage.GET_VIEW_COUNT_MAP();
        if (!map.isEmpty()) {
            newsDAO.updateViewsCount(map);
            DataStorage.GET_VIEW_COUNT_MAP().clear();
        }
    }

}
