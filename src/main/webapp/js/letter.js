const req = {
    email: document.getElementById("emailForm").value,
    status: true
};

function register() {
    if(req.email == null || req.email.length === 0) {
        console.log('Email null');
        return;
    }
    fetch('/letter', {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: JSON.stringify(req)
    }).then(response => {
        if (!response.ok) {
            alert('Lỗi đăng ký');
        }
        return response.json();
    }).then(data => {
        alert(data.message);
    })
}

