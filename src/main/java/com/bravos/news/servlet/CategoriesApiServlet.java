package com.bravos.news.servlet;

import com.bravos.news.dao.CategoryDAO;
import com.bravos.news.dto.CategoriesRequest;
import com.bravos.news.entity.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/admin/categories")
public class CategoriesApiServlet extends HttpServlet {

    private ObjectMapper mapper;
    private CategoryDAO categoryDAO;

    @Override
    public void init() {
        categoryDAO = new CategoryDAO();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CategoriesRequest categoriesRequest = mapper.readValue(req.getReader(), CategoriesRequest.class);
        Category category = null;
        CategoryResponse categoryResponse = new CategoryResponse();
        if(categoriesRequest.getType().equals("edit")) {
            category = categoryDAO.update(new Category(categoriesRequest.getId(),categoriesRequest.getName()));
            categoryResponse.setStatus(category != null ? 1 : 0);
            categoryResponse.setMessage(category != null ? "Cập nhật thành công" : "Cập nhật thất bại");
        }
        if(categoriesRequest.getType().equals("add")) {
            category = categoryDAO.insert(new Category(categoriesRequest.getId(),categoriesRequest.getName()));
            categoryResponse.setStatus(category != null ? 1 : 0);
            categoryResponse.setMessage(category != null ? "Thêm thành công" : "Thêm thất bại");
        }
        resp.setContentType("json/application");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        if (category != null) {
            categoryResponse.setId(category.getId());
            categoryResponse.setName(category.getName());
        }
        writer.print(mapper.writeValueAsString(categoryResponse));
        writer.flush();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class CategoryResponse {
        private int id;
        private String name;
        private int status;
        private String message;
    }

}
