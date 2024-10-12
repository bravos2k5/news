package com.bravos.news.dao;

import com.bravos.news.dto.NewUserCreated;
import com.bravos.news.dto.UsersRequest;
import com.bravos.news.entity.User;
import com.bravos.news.entity.enums.Role;
import com.bravos.news.entity.enums.Sex;
import com.bravos.news.utils.DatabaseManager;
import com.bravos.news.utils.XJdbc;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDAO implements IDataObject<User, UUID> {

    @Override
    public User findById(UUID id) {
        String sql = "SELECT * FROM [User] WHERE id = ?";
        List<User> users = findBySql(sql, id);
        return users.isEmpty() ? null : users.getFirst();
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM [User]";
        return findBySql(sql);
    }

    @Override
    public User insert(User object) {
        String sql = "INSERT INTO [User](username, password, fullName, birthDay, sex, mobile, email, role) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        UUID newId = (UUID) XJdbc.excuteUpdateAndGetKey(sql, object.getUsername(), object.getPassword(), object.getFullName(),
                object.getBirthDay(), object.getSex(), object.getMobile(), object.getEmail(),object.getRole());
        if(newId == null) return null;
        object.setId(newId);
        return object;
    }

    public NewUserCreated insert(UsersRequest object) {
        String sql = "INSERT INTO [User]( id,username, password, fullName, birthDay, sex, mobile, email, role) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String autoGeneratePassword = UUID.randomUUID().toString().replaceAll("-","");
        String hashpwed = BCrypt.hashpw(autoGeneratePassword,BCrypt.gensalt());
        String newId = UUID.randomUUID().toString();
        int result = XJdbc.excuteUpdate(sql, newId, object.getUsername(),
                hashpwed,
                object.getFullName(),
                object.getDob(), object.getSex(), object.getMobile(), object.getEmail(),object.getRole());
        if (result > 0) {
            object.setId(newId);
            NewUserCreated userCreated = new NewUserCreated();
            userCreated.setUsersRequest(object);
            userCreated.setPassword(autoGeneratePassword);
            return userCreated;
        }
        return null;
    }

    @Override
    public User update(User object) {
        String sql = "UPDATE [User] SET fullName = ?, birthDay = ?," +
                " sex = ?, mobile = ?, email = ?, role = ? WHERE id = ?";
        if (XJdbc.excuteUpdate(sql, object.getFullName(), object.getBirthDay(), object.getSex(),
                object.getMobile(), object.getEmail(), object.getRole(), object.getId()) > 0) {
            return object;
        }
        throw new IllegalArgumentException("This user does not exist");
    }

    public boolean update(UsersRequest usersRequest) {
        String sql = "UPDATE [User] SET fullName = ?, birthDay = ?," +
                " sex = ?, mobile = ?, email = ?, role = ? WHERE id = ?";
        return XJdbc.excuteUpdate(sql,
                usersRequest.getFullName(),usersRequest.getDob(),
                usersRequest.getSex(),usersRequest.getMobile(),usersRequest.getEmail(),
                usersRequest.getRole(),usersRequest.getId()) > 0;
    }

    @Override
    public void delete(User object) {
        String sql = "DELETE FROM [User] WHERE id = ?";
        XJdbc.excuteUpdate(sql, object.getId());
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM [User] WHERE username = ?";
        List<User> users = findBySql(sql,username);
        return users.isEmpty() ? null : users.getFirst();
    }

    public String findFullName(UUID uuid) {
        String sql = "SELECT fullName FROM [User] WHERE id = ?";
        ResultSet rs = null;
        try {
            rs = XJdbc.getResultSet(sql,uuid);
            if (rs.next()) {
                return rs.getNString("fullName");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.gI().closeResultSet(rs);
        }
    }

    @Override
    public List<User> findBySql(String sql, Object... args) {
        List<User> users = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = XJdbc.getResultSet(sql, args);
            while (rs.next()) {
                User user = new User();
                user.setId(UUID.fromString(rs.getString("id")));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("fullName"));
                user.setBirthDay(rs.getDate("birthDay"));
                user.setSex(Sex.valueOf(rs.getString("sex")));
                user.setMobile(rs.getString("mobile"));
                user.setEmail(rs.getString("email"));
                user.setRole(Role.valueOf(rs.getString("role")));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.gI().closeResultSet(rs);
        }
        return users;
    }

}
