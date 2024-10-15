<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error - 404</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
<div class="container">
    <div class="error-content">
        <img src=${pageContext.request.contextPath}"/img/404.png" alt="404 Error">
        <h1>Oops! Trang không tìm thấy</h1>
        <p>Rất tiếc, trang bạn đang tìm kiếm như tình cảm của crush dành cho bạn vậy</p>
        <a href=${pageContext.request.contextPath}"/home" class="btn">Quay lại trang chủ</a>
    </div>
</div>
</body>
</html>
