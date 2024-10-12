package com.bravos.news.servlet;

import com.bravos.news.listener.SessionListener;
import com.bravos.news.utils.XJdbc;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/api/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("json/application");
        PrintWriter writer = resp.getWriter();
        String sql = "{CALL spDashboard()}";
        try {
            ResultSet rs = XJdbc.getResultSet(sql);
            if(rs.next()) {
                writer.print(new ObjectMapper().writeValueAsString(new DashBoardData(
                        rs.getInt("categoryCount"),
                        SessionListener.getActiveSessions(),
                        rs.getInt("newsCount"),
                        rs.getInt("homeNewsCount")
                )));
                writer.flush();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class DashBoardData {
        private int categories;
        private int users;
        private int news;
        private int homes;
    }

}
