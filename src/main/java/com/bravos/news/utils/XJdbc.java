package com.bravos.news.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class XJdbc {

    private static PreparedStatement getStatement(String sql, Object...params) throws SQLException {
        Connection connection = DatabaseManager.gI().getConnection();
        if(connection == null) {
            throw new RuntimeException("Connection is null");
        }
        try {
            PreparedStatement ps;
            if(sql.startsWith("{")) {
                ps = connection.prepareCall(sql);
            } else {
                ps = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            }
            System.out.println(sql);
            for(int i = 0; i < params.length; i++) {
                ps.setObject(i + 1,params[i]);
            }
            return ps;
        } catch (SQLException e) {
            DatabaseManager.gI().closeConnection(connection);
            throw new RuntimeException(e);
        }
    }

    public static int excuteUpdate(String sql, Object...params) {
        PreparedStatement ps = null;
        try {
            ps = getStatement(sql,params);
            return ps.executeUpdate();
        } catch (SQLException e) {
            DatabaseManager.gI().rollBack(ps);
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.gI().closeConnection(ps);
        }
    }

    public static Object excuteUpdateAndGetKey(String sql, Object...params) {
        ResultSet rs = null;
        PreparedStatement pss = null;
        try(PreparedStatement ps = getStatement(sql,params)) {
            pss = ps;
            int affectedRow = ps.executeUpdate();
            if(affectedRow > 0) {
                rs = ps.getGeneratedKeys();
                rs.next();
                return rs.getObject(1);
            }
        } catch (SQLException e) {
            if(pss != null) {
                DatabaseManager.gI().rollBack(pss);
            }
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.gI().closeResultSet(rs);
        }
        return null;
    }

    public static ResultSet getResultSet(String sql, Object... args) {
        try {
            PreparedStatement ps = getStatement(sql,args);
            return ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
