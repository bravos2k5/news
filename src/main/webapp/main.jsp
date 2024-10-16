<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bravos News</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="icon" type="image/x-icon" href="https://s1.vnecdn.net/vnexpress/restruct/images/favicon.ico">
</head>
<body>
<header>
    <div class="container">
        <h1>Bravos News</h1>
    </div>
</header>
<nav>
    <div class="container">
        <ul>
            <li><a href="${pageContext.request.contextPath}/home">Trang chủ</a></li>
            <jsp:useBean id="categories" scope="application" type="java.util.List<com.bravos.news.entity.Category>"/>
            <c:forEach var="category" items="${categories}">
                <li><a href="${pageContext.request.contextPath}/news/category/${category.id}">${category.name}</a></li>
            </c:forEach>
        </ul>
    </div>
</nav>
<div class="container">
    <main>

        <section class="content">
            <%--@elvariable id="page" type="java.lang.String"--%>
            <jsp:include page="${page}"/>
        </section>
        <aside id="sideNews">
            <div class="sidebar-box">
                <h3>Quan trọng</h3>
                <ul>
                    <jsp:useBean id="importantNewsList" scope="application" type="java.util.List<com.bravos.news.dto.SideBarNews>"/>
                    <c:forEach var="news" items="${importantNewsList}">
                        <li><a style="text-decoration: none"
                               href="${pageContext.request.contextPath}/news/id/${news.id}">- ${news.title}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="sidebar-box">
                <h3>Mới nhất</h3>
                <ul>
                    <jsp:useBean id="latestNewsList" scope="application" type="java.util.List<com.bravos.news.dto.SideBarNews>"/>
                    <c:forEach var="news" items="${latestNewsList}">
                        <li><a style="text-decoration: none"
                               href="${pageContext.request.contextPath}/news/id/${news.id}">- ${news.title}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="sidebar-box">
                <h3>Đã xem</h3>
                <ul>
                    <jsp:useBean id="recentNewsList" scope="session"
                                 type="java.util.List<com.bravos.news.dto.SideBarNews>"/>
                    <c:if test="${recentNewsList.size() == 0}">
                        <li>Chưa có thông tin</li>
                    </c:if>
                    <c:forEach var="news" items="${recentNewsList}">
                        <li><a style="text-decoration: none"
                               href="${pageContext.request.contextPath}/news/id/${news.id}">- ${news.title}</a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="sidebar-box">
                <h3>Newsletter</h3>
                <p>Đăng ký để nhận bản tin mới nhất của chúng tôi.</p>
                <form>
                    <label>
                        <input name="emailForm" id="emailForm" type="email" placeholder="Email của bạn"
                               style="width: 95%; padding: 8px; margin-bottom: 10px; border: 1px solid #ddd; border-radius: 4px;">
                    </label>
                    <button type="button" onclick="register()"
                            style="width: 100%; padding: 8px; background-color: #3498db; color: white; border: none; border-radius: 4px; cursor: pointer;">
                        Đăng ký
                    </button>
                </form>
            </div>
        </aside>
    </main>
</div>

<footer>
    <div class="footer-content">
        <div class="footer-section">
            <h3>Về chúng tôi</h3>
            <p>Chúng tôi là một trang web cung cấp thông tin đa dạng về văn hóa, pháp luật, và thể thao tại Việt
                Nam.</p>
        </div>
        <div class="footer-section">
            <h3>Liên kết nhanh</h3>
            <ul>
                <li><a href="${pageContext.request.contextPath}/home">Trang chủ</a></li>
                <li><a href="#sideNews">Tin tức mới nhất</a></li>
                <li><a href="#">Liên hệ</a></li>
            </ul>
        </div>
        <div class="footer-section">
            <h3>Liên hệ</h3>
            <ul>
                <li>Email: baonqps41272@gmail.com</li>
                <li>Điện thoại: (84) 704 795 312</li>
            </ul>
        </div>
        <div class="footer-section">
            <h3>Theo dõi chúng tôi</h3>
            <div class="social-icons">
                <a href="#" title="Facebook">FB</a>
                <a href="#" title="Twitter">TW</a>
                <a href="#" title="Instagram">IG</a>
                <a href="#" title="LinkedIn">LI</a>
            </div>
        </div>
    </div>
    <div class="footer-bottom">
        <p>&copy; 2024 Nguyễn Quốc Bảo PS41272.</p>
    </div>
</footer>
<script src="${pageContext.request.contextPath}/js/letter.js"></script>
</body>
</html>

