<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/news-management.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
<div class="container">
    <br>
    <h2 style="color: #4a90e2">Quản lý tin tức</h2>
    <br>
    <button class="add-news-btn"><a style="text-decoration: none; color: white" href="${pageContext.request.contextPath}/admin/news/add">Thêm bài viết mới</a></button>
    <div class="news-container">
        <div class="search-container">
            <input type="text" class="search-input" placeholder="Tìm kiếm bài viết...">
            <button class="search-btn"><i class="fas fa-search"></i></button>
        </div>

        <div class="loading"></div>

        <div class="news-list">
            <jsp:useBean id="newsItems" scope="request" type="java.util.List<com.bravos.news.dto.NewsItemAdmin>"/>
            <c:forEach var="news" items="${newsItems}">
                <div class="news-item">
                    <div class="news-content">
                        <div class="news-title">${news.title}</div>
                        <div class="news-date">Ngày đăng: ${news.postedDate}</div>
                    </div>
                    <div class="news-actions">
                        <a href="${pageContext.request.contextPath}/news/id/${news.id}"><i class="fas fa-eye"></i> Xem</a>
                        <c:url var="editUrl" value="/admin/news/edit">
                            <c:param name="id" value="${news.id}" />
                        </c:url>
                        <a href="${editUrl}"><i class="fas fa-edit"></i> Sửa</a>
                        <a onclick="remove('${news.id}')" href="#"><i class="fas fa-trash"></i> Xóa</a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/news.js"></script>
<script>



    document.querySelector('.search-btn').addEventListener('click', function () {
        const loadingElement = document.querySelector('.loading');
        const newsListElement = document.querySelector('.news-list');

        // Show loading spinner
        loadingElement.style.display = 'block';
        newsListElement.style.opacity = '0.5';

        // Simulate search delay
        setTimeout(function () {
            // Hide loading spinner
            loadingElement.style.display = 'none';
            newsListElement.style.opacity = '1';

            // Here you would typically update the news list based on search results
            // For this example, we'll just log to the console
            console.log('Search performed');
        }, 1500);
    });
</script>