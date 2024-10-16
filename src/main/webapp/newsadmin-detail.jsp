<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/news-management-detail.css">

<jsp:useBean id="news" scope="request" type="com.bravos.news.entity.News"/>
<div class="container">
    <div class="news-detail">
        <div class="news-header">
            <h2 id="perm-title" class="news-title">${news.title}</h2>
            <div class="news-date">Ngày đăng: ${news.postedDate}</div>
        </div>
        <img id="perm-img" src="${news.image}" alt="Ảnh bài viết" class="news-image">
        <div class="news-content">
            <p id="perm-content">${news.content}</p>
        </div>
    </div>

    <form class="edit-form">
        <p id="newsId" hidden="hidden">${news.id}</p>
        <div class="form-group">
            <label for="title">Tiêu đề:</label>
            <input type="text" id="title" name="title" value="${news.title}">
        </div>
        <div class="form-group">
            <label for="content">Nội dung:</label>
            <textarea id="content" name="content">${news.content}</textarea>
        </div>
        <div class="form-group">
            <label for="category">Thể loại:</label>
            <select name="category" id="category">
                <jsp:useBean id="categories" scope="application" type="java.util.List<com.bravos.news.entity.Category>"/>
                <c:forEach var="category" items="${categories}">
                    <option ${news.categoryId == category.id ? 'selected' : ''} value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>
        </div>
        <br>
        <jsp:useBean id="user" scope="session" type="com.bravos.news.dto.UserInfo"/>
        <c:if test="${user.role == 'ADMIN'}">
            <div class="form-group">
                <label for="home">Hiển thị trên trang chủ:</label>
                <input type="checkbox" id="home" name="home" value="home" ${news.home ? "checked" : ""}/>
            </div>
        </c:if>
        <div class="image-upload">
            <label for="image">Chọn ảnh mới</label>
            <input type="file" id="image" name="image" accept="image/*">
            <div class="image-preview-container">
                <img id="image-preview" src="${news.image}" alt="Preview ảnh">
            </div>
        </div>
        <div class="action-buttons">
            <button type="button" onclick="save()" class="save-btn">Lưu thay đổi</button>
            <button type="button" onclick="remove()" class="delete-btn">Xóa bài viết</button>
        </div>
    </form>
</div>

<script src="${pageContext.request.contextPath}/js/news.js"></script>