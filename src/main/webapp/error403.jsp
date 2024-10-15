<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Error - 404</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
<div class="container">
  <div class="error-content">
    <img src=${pageContext.request.contextPath}"/img/403.png" alt="403 Error">
    <h1>Oops! Bạn tính làm gì</h1>
    <p>Bạn bị sai trính tã url hay bạn đang muốn đi đâu nào</p>
    <a href=${pageContext.request.contextPath}"/home" class="btn">Quay lại trang chủ</a>
  </div>
</div>
</body>
</html>
