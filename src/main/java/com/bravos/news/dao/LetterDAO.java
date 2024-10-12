package com.bravos.news.dao;

import com.bravos.news.entity.Letter;
import com.bravos.news.utils.DatabaseManager;
import com.bravos.news.utils.XJdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LetterDAO implements IDataObject<Letter, String> {

    @Override
    public Letter findById(String id) {
        String sql = "SELECT * FROM Letter WHERE id = ?";
        List<Letter> letters = findBySql(sql, id);
        return letters.isEmpty() ? null : letters.get(0);
    }

    @Override
    public List<Letter> findAll() {
        String sql = "SELECT * FROM Letter";
        return findBySql(sql);
    }

    @Override
    public Letter insert(Letter object) {
        String sql = "INSERT INTO Letter(id, isEnable) VALUES(?, ?)";
        XJdbc.excuteUpdate(sql, object.getId(), object.isEnable());
        return object;
    }

    @Override
    public Letter update(Letter object) {
        String sql = "UPDATE Letter SET isEnable = ? WHERE id = ?";
        if (XJdbc.excuteUpdate(sql, object.isEnable(), object.getId()) > 0) {
            return object;
        }
        throw new IllegalArgumentException("This letter does not exist");
    }

    @Override
    public void delete(Letter object) {
        String sql = "DELETE FROM Letter WHERE id = ?";
        XJdbc.excuteUpdate(sql, object.getId());
    }

    @Override
    public List<Letter> findBySql(String sql, Object... args) {
        List<Letter> letters = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = XJdbc.getResultSet(sql, args);
            while (rs.next()) {
                Letter letter = new Letter();
                letter.setId(rs.getString("id"));
                letter.setEnable(rs.getBoolean("isEnable"));
                letters.add(letter);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.gI().closeResultSet(rs);
        }
        return letters;
    }
}
