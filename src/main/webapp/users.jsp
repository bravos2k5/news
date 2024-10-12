<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user-management.css">
<div class="container">
    <h1 id="mini-title">Quản lý người dùng</h1>
    <button class="add-user-btn" onclick="openModal()">Thêm người dùng mới</button>
    <table id="users-table" class="user-table">
        <thead>
        <tr>
            <th>Tên đăng nhập</th>
            <th>Họ và tên</th>
            <th>Email</th>
            <th>Số điện thoại</th>
            <th>Vai trò</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody id="userTableBody">
        <jsp:useBean id="users" scope="request" type="java.util.List<com.bravos.news.entity.User>"/>
        <c:forEach var="auser" items="${users}">
            <tr>
                <td hidden="hidden">${auser.id}</td>
                <td>${auser.username}</td>
                <td>${auser.fullName}</td>
                <td>${auser.email}</td>
                <td>${auser.mobile}</td>
                <td hidden="hidden">${auser.birthDay}</td>
                <td hidden="hidden">${auser.sex}</td>
                <td>${auser.role}</td>
                <td>
                    <button class="action-btn edit-btn" onclick="editUser('${auser.id}')">Sửa</button>
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
        <h2 id="modalTitle">Thêm người dùng mới</h2>
        <form id="userForm">
            <input name="type" type="hidden" id="type">
            <input name="id" type="hidden" id="userId">
            <div class="form-group">
                <label for="username">Tên đăng nhập:</label>
                <input name="username" type="text" id="username" required>
            </div>
            <div class="form-group">
                <label for="fullName">Họ và tên:</label>
                <input name="fullName" type="text" id="fullName" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input name="email" type="email" id="email" required>
            </div>
            <div class="form-group">
                <label for="mobile">Số điện thoại:</label>
                <input name="mobile" type="tel" id="mobile">
            </div>
            <div class="form-group">
                <label for="dob">Ngày sinh</label>
                <input name="dob" type="date" pattern="dd/MM/yyyy" id="dob">
            </div>
            <div class="form-group">
                <label for="sex">Giới tính</label>
                <select name="sex" id="sex" required>
                    <option value="MALE">Nam</option>
                    <option value="FEMALE">Nữ</option>
                    <option value="OTHER">Other</option>
                </select>
            </div>
            <div class="form-group">
                <label for="role">Vai trò:</label>
                <select name="role" id="role" required>
                    <option value="USER">Người dùng</option>
                    <option value="ADMIN">Quản trị viên</option>
                </select>
            </div>
            <button onclick="sendRequest()" type="button" class="submit-btn">Lưu</button>
        </form>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/users.js"></script>
