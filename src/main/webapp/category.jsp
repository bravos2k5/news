<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user-management.css">
<div class="container">
    <h1 id="mini-title">Quản lý danh mục</h1>
    <button class="add-user-btn" onclick="openModal()">Thêm danh mục mới</button>
    <table id="users-table" class="user-table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Tên danh mục</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody id="userTableBody">
        <jsp:useBean id="categories" scope="request" type="java.util.List<com.bravos.news.entity.Category>"/>
        <c:forEach var="category" items="${categories}">
            <tr>
                <td>${category.id}</td>
                <td>${category.name}</td>
                <td>
                    <button class="action-btn edit-btn" onclick="editCategory('${category.id}')">Sửa</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- Modal for adding/editing user -->
<div id="userModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2 id="modalTitle">Thêm danh mục mới</h2>
        <form id="userForm">
            <div class="form-group">
                <input name="type" type="hidden" id="type">
                <input name="type" type="hidden" id="id">
                <label for="name">Tên danh mục:</label>
                <input name="name" type="text" id="name" required>
            </div>
            <button onclick="sendRequest()" type="button" class="submit-btn">Lưu</button>
        </form>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/category.js"></script>
