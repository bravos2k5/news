<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/personal.css">
<div class="fullscreen-container">
    <button class="back-button" onclick="goBack()">← Trở về</button>
    <div class="content-wrapper">
        <h1>Quản lý thông tin cá nhân</h1>
        <div class="form-section">
            <h2>Thông tin cơ bản</h2>
            <form id="personalInfoForm">
                <div class="form-group">
                    <label for="fullName">Họ và tên:</label>
                    <input type="text" id="fullName" name="fullName" required>
                </div>
                <div class="form-group">
                    <label for="birthDay">Ngày sinh:</label>
                    <input type="text" id="birthDay" name="birthDay" required>
                </div>
                <div class="form-group">
                    <label for="mobile">Số điện thoại:</label>
                    <input type="text" id="mobile" name="mobile" required>
                </div>
                <div class="form-group">
                    <label for="sex">Giới tính:</label>
                    <select id="sex" name="sex" required>
                        <option value="male">Nam</option>
                        <option value="female">Nữ</option>
                        <option value="other">Khác</option>
                    </select>
                </div>
                <button type="submit">Cập nhật thông tin</button>
            </form>
            <div id="personalInfoMessage" class="success-message"></div>
        </div>

        <div class="form-section">
            <h2>Đổi mật khẩu</h2>
            <form id="changePasswordForm">
                <div class="form-group">
                    <label for="currentPassword">Mật khẩu hiện tại:</label>
                    <input type="password" id="currentPassword" name="currentPassword" required>
                </div>
                <div class="form-group">
                    <label for="newPassword">Mật khẩu mới:</label>
                    <input type="password" id="newPassword" name="newPassword" required>
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Xác nhận mật khẩu mới:</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                </div>
                <button type="submit">Đổi mật khẩu</button>
            </form>
            <div id="passwordMessage" class="success-message"></div>
        </div>

        <div class="form-section">
            <h2>Đổi email</h2>
            <form id="changeEmailForm">
                <div class="form-group">
                    <label for="currentEmail">Email hiện tại:</label>
                    <input type="email" id="currentEmail" name="currentEmail" required>
                </div>
                <div class="form-group">
                    <label for="newEmail">Email mới:</label>
                    <input type="email" id="newEmail" name="newEmail" required>
                </div>
                <div class="form-group">
                    <label for="confirmEmail">Xác nhận email mới:</label>
                    <input type="email" id="confirmEmail" name="confirmEmail" required>
                </div>
                <button type="submit">Đổi email</button>
            </form>
            <div id="emailMessage" class="success-message"></div>
        </div>
    </div>
</div>