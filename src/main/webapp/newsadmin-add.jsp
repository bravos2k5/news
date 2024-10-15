<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/news-management-detail.css">

<div class="container">
    <form class="edit-form">
        <div class="form-group">
            <label for="title">Tiêu đề:</label>
            <input placeholder="Nhập tiêu đề ở đây" type="text" id="title" name="title">
        </div>
        <div class="form-group">
            <label for="content">Nội dung:</label>
            <textarea placeholder="Nhập nội dung ở đây" id="content" name="content"></textarea>
        </div>
        <div class="form-group">
            <label for="category">Thể loại:</label>
            <select name="category" id="category">
                <jsp:useBean id="categories" scope="application" type="java.util.List<com.bravos.news.entity.Category>"/>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="image-upload">
            <label for="image">Chọn ảnh mới</label>
            <input type="file" id="image" name="image" accept="image/*">
            <div class="image-preview-container">
                <img id="image-preview" src="" alt="Preview ảnh">
            </div>
        </div>
        <div class="action-buttons">
            <button type="button" onclick="create()" class="save-btn">Đăng bài báo</button>
        </div>
    </form>
</div>

<script src="${pageContext.request.contextPath}/js/news.js"></script>