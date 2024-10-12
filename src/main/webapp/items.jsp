<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="newsList" scope="request" type="java.util.List<com.bravos.news.dto.NewsItem>"/>
<c:set var="newsList" value="${newsList}"/>

<c:forEach var="news" items="${newsList}">
    <a style="text-decoration: none" href="${pageContext.request.contextPath}/news/id/${news.id}">
        <article class="news-item">
            <img class="news-image" src="${news.image}" alt="">
            <div class="news-content">
                <h2 class="news-title">${news.title}</h2>
                <div class="news-meta">${news.postedDate} | ${news.authorName}</div>
            </div>
        </article>
    </a>
</c:forEach>




