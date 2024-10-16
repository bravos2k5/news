<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user-management.css">
<div class="container">
    <h1 id="mini-title">Quản lý thư báo</h1>
    <table id="users-table" class="user-table">
        <thead>
        <tr>
            <th>Email</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody id="userTableBody">
        <jsp:useBean id="letters" scope="request" type="java.util.List<com.bravos.news.entity.Letter>"/>
        <c:forEach var="letter" items="${letters}">
            <tr>
                <td>${letter.id}</td>
                <td hidden="hidden">${letter.enable}</td>
                <td>
                    <button style="background-color: ${letter.enable ? "red" : "blue"}" id="${letter.id}" class="action-btn edit-btn"
                            onclick="edit('${letter.id}', ${!letter.enable})">${letter.enable ? "Tắt" : "Mở"}</button>
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
<script>
    function edit(id = null, enable = false) {
        fetch('/letter', {
            method: 'POST',
            headers: {
                'Content-type': 'Application/json'
            },
            body: JSON.stringify({
                email: id,
                status: enable
            })
        }).then(response => {
            if (!response.ok) {
                alert('Lỗi');
            }
            return response.json();
        }).then(data => {
            if(data.code === 1) {
                document.getElementById(id).style.backgroundColor = enable ? "red" : "blue";
                document.getElementById(id).innerHTML = enable ? 'Tắt' : "Mở";
            }
            alert(data.message)
        })
    }
</script>
