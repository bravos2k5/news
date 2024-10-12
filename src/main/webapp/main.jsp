<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Web Tin Tức</title>
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
        <aside>
            <div class="sidebar-box">
                <h3>Quan trọng</h3>
                <ul>
                    <li>Bản tin 1</li>
                    <li>Bản tin 2</li>
                    <li>Bản tin 3</li>
                    <li>Bản tin 4</li>
                    <li>Bản tin 5</li>
                </ul>
            </div>
            <div class="sidebar-box">
                <h3>Mới nhất</h3>
                <ul>
                    <li>Bản tin mới 1</li>
                    <li>Bản tin mới 2</li>
                    <li>Bản tin mới 3</li>
                    <li>Bản tin mới 4</li>
                    <li>Bản tin mới 5</li>
                </ul>
            </div>
            <div class="sidebar-box">
                <h3>Đã xem</h3>
                <ul>
                    <jsp:useBean id="recentNewsList" scope="session" type="java.util.List<com.bravos.news.entity.News>"/>
                    <c:if test="${recentNewsList.size() == 0}">
                        <li>Chưa có thông tin</li>
                    </c:if>
                    <c:forEach var="recentNews" items="${recentNewsList}">
                        <li><a style="text-decoration: none" href="${pageContext.request.contextPath}/news/id/${recentNews.id}">- ${recentNews.title}</a></li>
                    </c:forEach>
                </ul>
            </div>
            <div class="sidebar-box">
                <h3>Newsletter</h3>
                <p>Đăng ký để nhận bản tin mới nhất của chúng tôi.</p>
                <form>
                    <label>

                        <input type="email" placeholder="Email của bạn"
                               style="width: 95%; padding: 8px; margin-bottom: 10px; border: 1px solid #ddd; border-radius: 4px;">
                    </label>
                    <button type="submit"
                            style="width: 100%; padding: 8px; background-color: #3498db; color: white; border: none; border-radius: 4px; cursor: pointer;">
                        Đăng
                        ký
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
                <li><a href="#">Trang chủ</a></li>
                <li><a href="#">Tin tức mới nhất</a></li>
                <li><a href="#">Sự kiện</a></li>
                <li><a href="#">Liên hệ</a></li>
            </ul>
        </div>
        <div class="footer-section">
            <h3>Liên hệ</h3>
            <ul>
                <li>Email: info@thietketudo.vn</li>
                <li>Điện thoại: (84) 123 456 789</li>
                <li>Địa chỉ: 123 Đường ABC, Quận XYZ, Hà Nội</li>
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
        <p>&copy; 2024 Thiết kế tự do. Tất cả các quyền được bảo lưu.</p>
    </div>
</footer>
</body>

</html>

