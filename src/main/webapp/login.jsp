<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang đăng nhập hệ thống quản trị</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
<div class="container">
    <div class="form-box">
        <h1 id="title">Đăng nhập</h1>
        <form action="login" method="post" id="login-form">
            <div class="input-group">
                <input name="username" type="text" placeholder="Username" required>
                <input name="password" type="password" placeholder="Password" required>
            </div>
            <div class="remember-me">
                <input type="checkbox" id="remember" name="remember">
                <label for="remember">Ghi nhớ tài khoản</label>
            </div>
            <div class="btn-group">
                <button type="submit">Đăng nhập</button>
                <p>Chưa có tài khoản ? <span onclick="switchForm()">Đăng ký</span></p>
                <p style="color: red">${message}</p>
            </div>
        </form>

        <form id="signup-form" style="display: none;">
            <div class="input-group">
                <input type="text" placeholder="Username" required>
                <input type="email" placeholder="Email" required>
                <input type="password" placeholder="Password" required>
            </div>
            <div class="btn-group">
                <button type="submit">Đăng ký</button>
                <p>Đã có tài khoản? <span onclick="switchForm()">Login</span></p>
            </div>
        </form>
    </div>
</div>
<script>
    function switchForm() {
        const loginForm = document.getElementById('login-form');
        const signupForm = document.getElementById('signup-form');
        const title = document.getElementById('title');

        if (loginForm.style.display === 'none') {
            loginForm.style.display = 'block';
            signupForm.style.display = 'none';
            title.innerText = 'Đăng nhập';
        } else {
            loginForm.style.display = 'none';
            signupForm.style.display = 'block';
            title.innerText = 'Đăng ký';
        }
    }
</script>
</body>
</html>


