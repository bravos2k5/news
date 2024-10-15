package com.bravos.news.servlet.admin.api;

import com.bravos.news.dao.UserDAO;
import com.bravos.news.dto.NewUserCreated;
import com.bravos.news.dto.CreatedResponse;
import com.bravos.news.dto.UsersRequest;
import com.bravos.news.utils.EmailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/private/users")
public class UsersApiServlet extends HttpServlet {

    private UserDAO userDAO;
    private ObjectMapper mapper;

    @Override
    public void init() {
        userDAO = new UserDAO();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UsersRequest usersRequest = mapper.readValue(req.getReader(),UsersRequest.class);
        String message = "";
        int status = 0;
        CreatedResponse usersReponse = new CreatedResponse();
        if(usersRequest.getType().equals("edit")) {
            boolean b = userDAO.update(usersRequest);
            message = b ? "Cập nhật thành công" : "Cập nhật thất bại";
            status = b ? 1 : 0;
            usersReponse.setId(usersRequest.getId());
        }
        else if(usersRequest.getType().equals("add")) {
            NewUserCreated userCreated = userDAO.insert(usersRequest);
            if(userCreated != null) {
                message = "Tài khoản mật khẩu truy cập tài khoản này được gửi tới gmail";
                EmailUtil.sendEmail(usersRequest.getEmail(),"Tài khoản mật khẩu truy cập",
                        "Tài khoản: " + usersRequest.getUsername() + "\n" +
                                "Mật khẩu: " + userCreated.getPassword());
                status = 1;
                usersReponse.setId(userCreated.getUsersRequest().getId());
            }
        }
        usersReponse.setMessage(message);
        usersReponse.setStatus(status);
        resp.setContentType("json/application");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        String jsonRes = mapper.writeValueAsString(usersReponse);
        writer.print(jsonRes);
        writer.flush();
    }

}
