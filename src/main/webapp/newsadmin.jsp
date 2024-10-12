<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user-management.css">
<div class="container">
  <h1 id="mini-title">Quản lý tin tức</h1>
  <button class="add-user-btn" onclick="openModal()">Thêm tin tức mới</button>
  <table id="news-table" class="user-table">
    <thead>
    <tr>
      <th>Tiêu đề</th>
      <th>Danh mục</th>
      <th>Tác giả</th>
      <th>Ngày đăng</th>
      <th>Lượt xem</th>
      <th>Hiển thị trang chủ</th>
      <th>Hành động</th>
    </tr>
    </thead>
    <tbody id="newsTableBody">
    <jsp:useBean id="newsList" scope="request" type="java.util.List<com.bravos.news.dto.NewsAdmin>"/>
    <c:forEach var="news" items="${newsList}">
      <tr>
        <td>${news.title}</td>
        <td>${news.categoryName}</td>
        <td>${news.authorName}</td>
        <td>${news.postedDate}</td>
        <td>${news.viewCount}</td>
        <td>${news.home ? 'Có' : 'Không'}</td>
        <td>
          <button class="action-btn edit-btn" onclick="editNews('${news.id}')">Sửa</button>
          <button class="action-btn delete-btn" onclick="deleteNews('${news.id}')">Xóa</button>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

<!-- Modal for adding/editing news -->
<div id="newsModal" class="modal">
  <div class="modal-content">
    <span class="close" onclick="closeModal()">&times;</span>
    <h2 id="modalTitle">Thêm tin tức mới</h2>
    <form id="newsForm" enctype="multipart/form-data">
      <input type="hidden" id="newsId" name="id">
      <div class="form-group">
        <label for="title">Tiêu đề:</label>
        <input type="text" id="title" name="title" required>
      </div>
      <div class="form-group">
        <label for="categoryId">Danh mục:</label>
        <select id="categoryId" name="categoryId" required>
          <c:forEach var="category" items="${categories}">
            <option value="${category.id}">${category.name}</option>
          </c:forEach>
        </select>
      </div>
      <div class="form-group">
        <label for="content">Nội dung:</label>
        <textarea id="content" name="content" rows="5" required></textarea>
      </div>
      <div class="form-group">
        <label for="image">Hình ảnh:</label>
        <input type="file" id="image" name="image" accept="image/*">
        <img id="imagePreview" src="" alt="Preview" style="max-width: 200px; display: none;">
      </div>
      <div class="form-group">
        <label for="isHome">Hiển thị trang chủ:</label>
        <input type="checkbox" id="isHome" name="isHome">
      </div>
      <button type="button" onclick="submitNewsForm()" class="submit-btn">Lưu</button>
    </form>
  </div>
</div>

<script src="${pageContext.request.contextPath}/js/news.js"></script>