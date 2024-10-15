let isChanged = false;
let newFile = null;

document.getElementById('image').addEventListener('change', function (e) {
    const file = e.target.files[0];
    if (file.size > 10 * 1024 * 1024) {
        alert('Kích thước ảnh phải <= 10MB');
        return;
    }
    const reader = new FileReader();
    reader.onload = function (event) {
        document.getElementById('image-preview').src = event.target.result;
    }
    isChanged = true;
    newFile = file;
    reader.readAsDataURL(file);
});

async function save() {

    let newsRequest = {
        id: document.getElementById("newsId").innerHTML,
        title: document.getElementById("title").value,
        content: document.getElementById("content").value,
        categoryId: document.getElementById("category").value,
        imgStatus: isChanged,
        image: isChanged ? newFile.name : document.getElementById("perm-img").src
    };

    let uploadSuccess = true;

    if (isChanged) {
        const sasToken = await getSasToken();
        if (!sasToken) return;

        const url = `https://bravosrepo2.blob.core.windows.net/image/${newsRequest.id}/${newFile.name}?${sasToken}`;

        uploadSuccess = await uploadToBlob(url, newFile);
    }

    if (uploadSuccess) {

        await fetch('/api/public/news/edit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newsRequest)
        }).then(response => {
            if (!response.ok) {
                alert('Lỗi cập nhật thông tin');
            }
            return response.json();
        }).then(data => {
            if (data.status === 1) {
                if (isChanged) {
                    document.getElementById("perm-img").src = data.newImgUrl;
                }
                document.getElementById("perm-title").innerHTML = newsRequest.title;
                document.getElementById("perm-content").innerHTML = newsRequest.content;
                alert('Cập nhật thành công');
            } else {
                alert('Cập nhật thất bại, hãy thử lại sau');
            }
        })
        newFile = null;
        isChanged = false;
    }
}

async function uploadToBlob(url, file) {
    const response = await fetch(url, {
        method: 'PUT',
        headers: {
            'x-ms-blob-type': 'BlockBlob',
            'Content-Type': file.type
        },
        body: file,
        mode: 'cors'
    });

    if (!response.ok) {
        console.error('Upload failed:', response.status, await response.text());
        return false;
    }

    console.log('File uploaded successfully');
    return true;
}

async function getSasToken() {
    let sasToken = null;

    const response = await fetch('/api/public/generateSasToken', {
        method: 'POST'
    });
    if (!response.ok) {
        alert('Có lỗi xảy ra');
        return null;
    }
    const data = await response.json();
    if (data.status !== 1) {
        alert('Hệ thống upload ảnh đang bị lỗi');
        return null;
    }
    sasToken = data.token;
    return sasToken;
}

async function remove(id = null) {
    await fetch(`/api/public/news/remove?id=${id}`, {
        method: 'POST'
    }).then(response => {
        if (!response.ok) {
            alert('Lỗi khi xóa');
        }
        return response.json();
    }).then(data => {
        if (data.status === 1) {
            alert('Xóa thành công');
            window.location.href = '/admin/news';
        } else {
            alert('Không thể xóa!');
        }
    });
}

async function create() {

    let news = {
        id: uuidv4(),
        title: document.getElementById("title").value,
        content: document.getElementById("content").value,
        categoryId: document.getElementById("category").value,
        image: newFile.name
    }

    const sasToken = await getSasToken();
    if (!sasToken) return;

    const url = `https://bravosrepo2.blob.core.windows.net/image/${news.id}/${newFile.name}?${sasToken}`;

    let uploadSuccess = await uploadToBlob(url, newFile);

    if (uploadSuccess) {

        await fetch('/api/public/news/create', {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify(news)
        }).then(response => {
            if (!response.ok) {
                alert('Lỗi đăng tải');
            }
            return response.json();
        }).then(data => {
            if(data.status === 1) {
                alert('Đăng bài thành công');
                window.location.href = `/news/id/${news.id}`;
            }
            alert(data.message);
        });

    }

}

function uuidv4() {
    return "10000000-1000-4000-8000-100000000000".replace(/[018]/g, c =>
        (+c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> +c / 4).toString(16)
    );
}
