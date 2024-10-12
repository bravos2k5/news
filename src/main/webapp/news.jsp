<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="newsThread" scope="request" type="com.bravos.news.dto.NewsThread"/>
<c:set var="newsThread" value="${newsThread}" scope="request"/>
<jsp:useBean id="scNewsList" scope="request" type="java.util.List<com.bravos.news.entity.News>"/>
<c:set var="scNewsList" value="${scNewsList}" scope="request"/>

<article class="news-detail">
    <h1 class="news-title">${newsThread.news.title}</h1>
    <img src="${newsThread.news.image}" alt="Ảnh minh họa" class="news-image">
    <div class="news-content">
        <p>${newsThread.news.content}</p>
    </div>
    <div class="news-meta">
        <span>Ngày đăng: <fmt:formatDate pattern="dd/MM/yyyy" value="${newsThread.news.postedDate}"/></span>
        <span>Tác giả: ${newsThread.authorName}</span>
        <span>Lượt xem: ${newsThread.news.viewCount}</span>
    </div>
    <div class="related-news">
        <h3>Tin cùng thể loại</h3>
        <ul>
            <c:forEach var="scNews" items="${scNewsList}">
                <li>
                    <a href="${pageContext.request.contextPath}/news/id/${scNews.id}">${scNews.title}</a>
                    <div class="news-excerpt">${scNews.content.substring(0,scNews.content.length() > 100 ? 100 : scNews.content.length())}...</div>
                </li>
            </c:forEach>
        </ul>
    </div>
</article>
<jsp:useBean id="viewedNews" scope="session" type="java.util.Set<java.util.UUID>"/>
<c:if test="${!viewedNews.contains(newsThread.news.id.toString())}">
    <script>
        window.onload = function () {
            setTimeout(function () {
                increaseView();
            },20000);
        };
        function increaseView() {
            let pathname = window.location.pathname;
            let pathParts = pathname.split("/");
            let id = pathParts[pathParts.length - 1];
            fetch("/increaseViewCount?id=" + id, {
                method: 'POST',
            });
        }
    </script>
</c:if>