// Open the modal for adding a new news article
function openModal() {
    document.getElementById('newsModal').style.display = 'block';
    document.getElementById('modalTitle').innerText = 'Thêm tin tức mới';
    document.getElementById('newsForm').reset();
    document.getElementById('newsId').value = '';
    document.getElementById('imagePreview').style.display = 'none';
}

// Close the modal
function closeModal() {
    document.getElementById('newsModal').style.display = 'none';
}

// Edit an existing news article
function editNews(id) {
    fetch(`/admin/news/${id}`)
        .then(response => response.json())
        .then(news => {
            document.getElementById('newsModal').style.display = 'block';
            document.getElementById('modalTitle').innerText = 'Sửa tin tức';
            document.getElementById('newsId').value = news.id;
            document.getElementById('title').value = news.title;
            document.getElementById('categoryId').value = news.categoryId;
            document.getElementById('content').value = news.content;
            document.getElementById('isHome').checked = news.isHome;
            if (news.image) {
                document.getElementById('imagePreview').src = news.image;
                document.getElementById('imagePreview').style.display = 'block';
            } else {
                document.getElementById('imagePreview').style.display = 'none';
            }
        });
}

// Delete a news article
function deleteNews(id) {
    if (confirm('Bạn có chắc chắn muốn xóa tin tức này?')) {
        fetch(`/admin/news/${id}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    location.reload();
                } else {
                    alert('Có lỗi xảy ra khi xóa tin tức.');
                }
            });
    }
}

// Submit the news form
function submitNewsForm() {
    const form = document.getElementById('newsForm');
    const formData = new FormData(form);

    const url = formData.get('id') ? `/admin/news/${formData.get('id')}` : '/admin/news';
    const method = formData.get('id') ? 'PUT' : 'POST';

    fetch(url, {
        method: method,
        body: formData
    })
        .then(response => {
            if (response.ok) {
                closeModal();
                location.reload();
            } else {
                alert('Có lỗi xảy ra khi lưu tin tức.');
            }
        });
}

// Preview the selected image
document.getElementById('image').addEventListener('change', function(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('imagePreview').src = e.target.result;
            document.getElementById('imagePreview').style.display = 'block';
        }
        reader.readAsDataURL(file);
    }
});

// Close the modal when clicking outside of it
window.onclick = function(event) {
    if (event.target == document.getElementById('newsModal')) {
        closeModal();
    }
}