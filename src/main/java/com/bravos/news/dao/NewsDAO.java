package com.bravos.news.dao;

import com.bravos.news.dto.*;
import com.bravos.news.entity.News;
import com.bravos.news.utils.DatabaseManager;
import com.bravos.news.utils.XJdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NewsDAO implements IDataObject<News, UUID> {

    @Override
    public News findById(UUID id) {
        String sql = "SELECT * FROM News WHERE id = ?";
        List<News> newsList = findBySql(sql, id);
        return newsList.isEmpty() ? null : newsList.getFirst();
    }

    @Override
    public List<News> findAll() {
        String sql = "SELECT * FROM News";
        return findBySql(sql);
    }

    public boolean isCorrectAuthor(String authorId, String newsId) {
        String sql = "SELECT isHome FROM News WHERE id = ? and authorId = ? ";
        ResultSet rs = null;
        try {
            rs = XJdbc.getResultSet(sql, newsId,authorId);
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.gI().closeResultSet(rs);
        }
    }

    private List<NewsItemAdmin> findItemsAdminBySQL(String sql, Object... args) {
        List<NewsItemAdmin> newsItems = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = XJdbc.getResultSet(sql, args);
            while (rs.next()) {
                NewsItemAdmin newsItem = new NewsItemAdmin();
                newsItem.setId(rs.getString("id"));
                newsItem.setTitle(rs.getString("title"));
                newsItem.setPostedDate(rs.getDate("postedDate"));
                newsItems.add(newsItem);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.gI().closeResultSet(rs);
        }
        return newsItems;
    }

    private List<SideBarNews> findSideBarNewsBySQL(String sql, Object... args) {
        List<SideBarNews> newsItems = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = XJdbc.getResultSet(sql, args);
            while (rs.next()) {
                SideBarNews newsItem = new SideBarNews();
                newsItem.setId(UUID.fromString(rs.getString("id")));
                newsItem.setTitle(rs.getString("title"));
                newsItems.add(newsItem);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.gI().closeResultSet(rs);
        }
        return newsItems;
    }

    public int updateInfo(NewsAdminRequest request) {
        String sql = "{CALL spUpdateNews(?,?,?,?,?,?)}";
        return XJdbc.excuteUpdate(sql,
                request.getId(),
                request.getTitle(),
                request.getContent(),
                request.getImage(),
                Integer.parseInt(request.getCategoryId()),
                request.getHome().equals("home")
        );
    }

    public List<SideBarNews> getImportantNews() {
        String sql = "{CALL getImportantNews()}";
        return findSideBarNewsBySQL(sql);
    }

    public List<SideBarNews> getLastestNews() {
        String sql = "{CALL getLatestNews()}";
        return findSideBarNewsBySQL(sql);
    }

    public List<NewsItemAdmin> findAllItemAdmin() {
        String sql = "SELECT id, title, postedDate FROM News";
        return findItemsAdminBySQL(sql);
    }

    public List<NewsItemAdmin> findByAuthor(UUID authorId) {
        String sql = "SELECT * FROM News WHERE authorId = ?";
        return findItemsAdminBySQL(sql,authorId);
    }

    public List<NewsItemAdmin> findByAuthorAndKey(UUID authorId, String key) {
        String sql = "{CALL spFindNewsByAuthorAndKey(?,?)}";
        return findItemsAdminBySQL(sql,authorId,key);
    }

    public List<NewsItemAdmin> findByKey(String key) {
        String sql = "{CALL spFindNewsByKey(?)}";
        return findItemsAdminBySQL(sql,key);
    }

    @Override
    public News insert(News object) {
        String sql = "INSERT INTO News(id, title, categoryId, content, image, postedDate, authorId, viewCount, isHome) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        XJdbc.excuteUpdate(sql,
                object.getId(), object.getTitle(), object.getCategoryId(),
                object.getContent(), object.getImage(), object.getPostedDate(),
                object.getAuthorId(), object.getViewCount(), object.isHome());
        return object;
    }

    public int insert(NewsAdminRequest newsInfo, UUID authorId) {
        String sql = "INSERT INTO News(id, title, categoryId, content, image, authorId) VALUES(?,?,?,?,?,?)";
        return XJdbc.excuteUpdate(sql,
                newsInfo.getId(),newsInfo.getTitle(),newsInfo.getCategoryId(),
                newsInfo.getContent(),newsInfo.getImage(),authorId);
    }

    @Override
    public News update(News object) {
        String sql = "UPDATE News SET title = ?, categoryId = ?, content = ?, image = ?, postedDate = ?, authorId = ?, viewCount = ?, isHome = ? WHERE id = ?";
        if (XJdbc.excuteUpdate(sql, object.getTitle(), object.getCategoryId(), object.getContent(), object.getImage(), object.getPostedDate(), object.getAuthorId(), object.getViewCount(), object.isHome(), object.getId()) > 0) {
            return object;
        }
        throw new IllegalArgumentException("This news does not exist");
    }

    @Override
    public boolean delete(News object) {
        String sql = "DELETE FROM News WHERE id = ?";
        return XJdbc.excuteUpdate(sql, object.getId()) > 0;
    }

    public List<NewsItem> getHomePageItems() {
        String sql = "{CALL getHomePageItems}";
        return findItemsBySQL(sql);
    }

    public List<NewsItem> getNewsItemsByCategory(int categoryId) {
        String sql = "{CALL getNewsItemsByCategory(?)}";
        return findItemsBySQL(sql, categoryId);
    }

    public List<News> getNewsByCategory(int categoryId) {
        String sql = "{CALL getNewsByCategory(?)}";
        return findBySql(sql, categoryId);
    }

    public List<NewsItem> getNewsItemsByReporter(UUID reporterId) {
        String sql = "{CALL getNewsItemsByCategory(?)}";
        return findItemsBySQL(sql, reporterId);
    }

    public NewsThread getNewsThread(UUID uuid) {
        String sql = "{CALL getNewsThread(?)}";
        ResultSet rs = null;
        try {
            rs = XJdbc.getResultSet(sql, uuid.toString());
            if (rs.next()) {
                NewsThread newsThread = new NewsThread();
                News news = new News();
                news.setId(uuid);
                news.setCategoryId(rs.getInt("categoryId"));
                news.setAuthorId(UUID.fromString(rs.getString("authorId")));
                news.setContent(rs.getNString("content"));
                news.setImage(rs.getString("image"));
                news.setTitle(rs.getString("title"));
                news.setViewCount(rs.getInt("viewCount"));
                news.setPostedDate(rs.getDate("postedDate"));
                newsThread.setNews(news);
                newsThread.setAuthorName(rs.getNString("fullName"));
                return newsThread;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.gI().closeResultSet(rs);
        }
    }

    private List<NewsItem> findItemsBySQL(String sql, Object... args) {
        List<NewsItem> newsItems = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = XJdbc.getResultSet(sql, args);
            while (rs.next()) {
                NewsItem newsItem = new NewsItem();
                newsItem.setId(UUID.fromString(rs.getString("id")));
                newsItem.setTitle(rs.getString("title"));
                newsItem.setImage(rs.getString("image"));
                newsItem.setPostedDate(rs.getDate("postedDate"));
                newsItem.setAuthorName(rs.getString("authorName"));
                newsItems.add(newsItem);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.gI().closeResultSet(rs);
        }
        return newsItems;
    }

    public void updateViewsCount(HashMap<UUID,Integer> map) {
        String sql = "UPDATE news set viewCount = viewCount + ? WHERE id = ?";
        Connection connection = DatabaseManager.gI().getConnection();
        try {
            if(connection != null) {
                connection.setAutoCommit(false);
                PreparedStatement ps = connection.prepareStatement(sql);
                for(var news : map.entrySet()) {
                    ps.setInt(1,news.getValue());
                    ps.setObject(2,news.getKey());
                    ps.addBatch();
                }
                ps.executeBatch();
                connection.commit();
            }
        } catch (SQLException e) {
            DatabaseManager.gI().rollBack(connection);
        } finally {
            DatabaseManager.gI().closeConnection(connection);
        }
    }

    @Override
    public List<News> findBySql(String sql, Object... args) {
        List<News> newsList = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = XJdbc.getResultSet(sql, args);
            while (rs.next()) {
                News news = new News();
                news.setId(UUID.fromString(rs.getString("id")));
                news.setTitle(rs.getString("title"));
                news.setCategoryId(rs.getInt("categoryId"));
                news.setContent(rs.getString("content"));
                news.setImage(rs.getString("image"));
                news.setPostedDate(rs.getDate("postedDate"));
                news.setAuthorId(UUID.fromString((rs.getString("authorId"))));
                news.setViewCount(rs.getInt("viewCount"));
                news.setHome(rs.getBoolean("isHome"));
                newsList.add(news);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.gI().closeResultSet(rs);
        }
        return newsList;
    }
}
