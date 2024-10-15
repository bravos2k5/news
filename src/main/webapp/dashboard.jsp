<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <div class="dashboard">
        <div class="card">
            <i class="fas fa-folder card-icon"></i>
            <h3>Tổng số danh mục</h3>
            <p id="categoryCount">10</p>
        </div>
        <div class="card">
            <i class="fas fa-users card-icon"></i>
            <h3>Tổng số người dùng</h3>
            <p id="activeSession">100</p>
        </div>
        <div class="card">
            <i class="fas fa-newspaper card-icon"></i>
            <h3>Tổng số tin tức</h3>
            <p id="newsCount">500</p>
        </div>
        <div class="card">
            <i class="fas fa-home card-icon"></i>
            <h3>Tin tức trang chủ</h3>
            <p id="homeNewsCount">20</p>
        </div>
    </div>
</div>
<script>
    function getDashBoard() {
        fetch('/api/public/dashboard', {
            method: 'GET'
        })
            .then(response => {
                if(!response.ok) {
                    alert('Có lỗi xảy ra');
                }
                return response.json();
            })
            .then(data => {
                document.getElementById('categoryCount').innerHTML = data.categories;
                document.getElementById('activeSession').innerHTML = data.users;
                document.getElementById('newsCount').innerHTML = data.news;
                document.getElementById('homeNewsCount').innerHTML = data.homes;
            })
    }
    getDashBoard();
    setInterval(getDashBoard,30000);
</script>
