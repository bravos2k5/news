<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang quản trị tinh tế</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
    <link rel="icon" type="image/x-icon" href="https://s1.vnecdn.net/vnexpress/restruct/images/favicon.ico">
</head>
<body>
<header>
    <div class="container">
        <div class="header-content">
            <div class="logo">
                <h1 id="logo-h1">Quản trị hệ thống</h1>
            </div>
            <div class="user-actions">
                <jsp:useBean id="user" scope="session" type="com.bravos.news.dto.UserInfo"/>
                <span class="user-info">Xin chào,
                    <a style="color: #f5d6d6; text-decoration: none"
                       href="${pageContext.request.contextPath}/admin/personal"> ${user.fullName}</a>
                </span>
                <form method="post" action="${pageContext.request.contextPath}/logout">
                    <button type="submit" class="logout-btn">Đăng xuất</button>
                </form>
            </div>
        </div>
    </div>
</header>
<nav id="mainNav">
    <div class="container">
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/dashboard"
                   class="${page == 'dashboard.jsp' ? 'active' : ''}">Tổng quan</a></li>

            <c:if test="${user.role == 'ADMIN'}">
                <li><a href="${pageContext.request.contextPath}/admin/categories"
                       class="${page == 'category.jsp' ? 'active' : ''}">Danh mục</a></li>
            </c:if>

            <c:if test="${user.role == 'ADMIN'}">
                <li><a href="${pageContext.request.contextPath}/admin/letters"
                       class="${page == 'letters.jsp' ? 'active' : ''}">Thư báo</a></li>
            </c:if>

            <c:if test="${user.role == 'ADMIN'}">
                <li><a href="${pageContext.request.contextPath}/admin/users"
                       class="${page == 'users.jsp' ? 'active' : ''}">Người dùng</a></li>
            </c:if>

            <li><a href="${pageContext.request.contextPath}/admin/news"
                   class="${page == 'newsadmin.jsp' ? 'active' : ''}">Tin tức</a></li>

        </ul>
    </div>
</nav>

<jsp:include page="${page}"/>

<script>
    window.addEventListener('scroll', function () {
        const nav = document.getElementById('mainNav');
        if (window.scrollY > 100) {
            nav.classList.add('scrolled');
        } else {
            nav.classList.remove('scrolled');
        }
    });

</script>
</body>
</html>


