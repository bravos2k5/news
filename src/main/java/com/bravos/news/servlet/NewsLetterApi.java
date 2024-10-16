package com.bravos.news.servlet;

import com.bravos.news.dao.LetterDAO;
import com.bravos.news.entity.Letter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/letter")
public class NewsLetterApi extends HttpServlet {

    private ObjectMapper mapper;
    private LetterDAO letterDAO;

    @Override
    public void init() throws ServletException {
        mapper = new ObjectMapper();
        letterDAO = new LetterDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        EmailRequest emailRequest = mapper.readValue(req.getReader(), EmailRequest.class);

        PrintWriter writer = resp.getWriter();
        String message;
        int statusCode;

        if (emailRequest.getEmail() == null || emailRequest.getEmail().isBlank()) {
            message = "Email không được bỏ trống";
            statusCode = 0;
            sendResponse(writer, message, statusCode);
            return;
        }

        Letter letter = letterDAO.findById(emailRequest.getEmail());

        if (letter == null) {
            letter = new Letter(emailRequest.getEmail(), true);
            letter = letterDAO.insert(letter);
            message = (letter == null) ? "Lỗi khi đăng ký" : "Đăng ký thành công";
            statusCode = (letter == null) ? 0 : 1;
        } else {
            letter.setEnable(emailRequest.isStatus());
            letterDAO.update(letter);
            message = "Cập nhật đăng ký thành công";
            statusCode = 1;
        }

        sendResponse(writer, message, statusCode);
    }

    private void sendResponse(PrintWriter writer, String message, int statusCode) throws IOException {
        Response response = new Response(message, statusCode);
        writer.print(mapper.writeValueAsString(response));
        writer.flush();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailRequest {
        private String email;
        private boolean status = true;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String message;
        private int code;
    }
}
