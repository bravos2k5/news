let users = [];
let table = document.getElementById("users-table");
let rows = table.querySelectorAll("tbody tr");
rows.forEach(row => {
   let cells = row.querySelectorAll("td");
   let rowData = {
       id: cells[0].textContent.trim(),
       username: cells[1].textContent.trim(),
       fullName: cells[2].textContent.trim(),
       email: cells[3].textContent.trim(),
       mobile: cells[4].textContent.trim(),
       dob: cells[5].textContent.trim(),
       sex: cells[6].textContent.trim(),
       role: cells[7].textContent.trim()
   }
   users.push(rowData);
});

function renderUsers() {
    const tableBody = document.getElementById('userTableBody');
    tableBody.innerHTML = '';
    users.forEach(user => {
        const row = `
                    <tr>
                        <td hidden="hidden">${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.fullName}</td>
                        <td>${user.email}</td>
                        <td>${user.mobile}</td>
                        <td hidden="hidden">${user.dob}</td>
                        <td hidden="hidden">${user.sex}</td>
                        <td>${user.role}</td>
                        <td>
                            <button class="action-btn edit-btn" onclick="editUser('${user.id}')">Sửa</button>
                        </td>
                    </tr>
                `;
        tableBody.innerHTML += row;
    });
}

function openModal(userId = null) {
    const modal = document.getElementById('userModal');
    const form = document.getElementById('userForm');
    const modalTitle = document.getElementById('modalTitle');

    if (userId) {
        const user = users.find(u => u.id === userId);
        modalTitle.textContent = 'Sửa thông tin người dùng';
        document.getElementById("type").value = 'edit';
        document.getElementById('userId').value = user.id;
        document.getElementById('userId').readOnly = true;
        document.getElementById('username').value = user.username;
        document.getElementById('fullName').value = user.fullName;
        document.getElementById('dob').value = user.dob;
        document.getElementById('email').value = user.email;
        document.getElementById('mobile').value = user.mobile;
        document.getElementById('sex').value = user.sex;
        document.getElementById('role').value = user.role;
    } else {
        modalTitle.textContent = 'Thêm người dùng mới';
        document.getElementById('userId').readOnly = false;
        document.getElementById("type").value = 'add';
        form.reset();
    }

    modal.style.display = 'block';
}

function closeModal() {
    document.getElementById('userModal').style.display = 'none';
}

function editUser(userId) {
    openModal(userId);
}

function sendRequest() {
    let user = {
        type: document.getElementById("type").value,
        id: document.getElementById("userId").value,
        username: document.getElementById("username").value,
        fullName: document.getElementById("fullName").value,
        email: document.getElementById("email").value,
        mobile: document.getElementById("mobile").value,
        dob: document.getElementById("dob").value,
        sex: document.getElementById("sex").value,
        role: document.getElementById('role').value
    }
    fetch('/api/admin/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })
        .then(response => {
            if(!response.ok) {
                alert("Có lỗi xảy ra");
            }
            return response.json();
        })
        .then(data => {
            if(data.status === 1) {
                if(user.type === 'edit') {
                    let userToUpdate = users.findIndex(userr => userr.id === user.id);
                    if(userToUpdate !== -1) {
                        users[userToUpdate] = user;
                    }
                    renderUsers();
                }
                if(user.type === 'add') {
                    user.id = data.id;
                    users.push(user);
                    renderUsers();
                }
            }
            alert(data.message);
            closeModal();
        })
        .catch(error => console.error('Error:', error));

}
