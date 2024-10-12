let categories = [];
let table = document.getElementById("users-table");
let rows = table.querySelectorAll("tbody tr");
rows.forEach(row => {
    let cells = row.querySelectorAll("td");
    let rowData = {
        id: cells[0].textContent.trim(),
        name: cells[1].textContent.trim()
    }
    categories.push(rowData);
});

function renderCategory() {
    const tableBody = document.getElementById('userTableBody');
    tableBody.innerHTML = '';
    categories.forEach(category => {
        const row = `
                    <tr>            
                        <td>${category.id}</td>
                        <td>${category.name}</td>
                        <td>
                            <button class="action-btn edit-btn" onclick="editCategory('${category.id}')">Sửa</button>
                        </td>                   
                    </tr>
                `;
        tableBody.innerHTML += row;
    });
}

function openModal(categoryId = null) {
    const modal = document.getElementById('userModal');
    const form = document.getElementById('userForm');
    const modalTitle = document.getElementById('modalTitle');

    if (categoryId) {
        const category = categories.find(u => u.id === categoryId);
        modalTitle.textContent = 'Sửa tên danh mục';
        document.getElementById("type").value = 'edit';
        document.getElementById('name').value = category.name;
        document.getElementById('id').value = category.id;
    } else {
        modalTitle.textContent = 'Thêm danh mục mới';
        document.getElementById("type").value = 'add';
        form.reset();
    }

    modal.style.display = 'block';
}

function closeModal() {
    document.getElementById('userModal').style.display = 'none';
}

function editCategory(categoryId) {
    openModal(categoryId);
}

function sendRequest() {
    let category = {
        type: document.getElementById("type").value,
        id: Number.parseInt(document.getElementById("id").value),
        name: document.getElementById("name").value
    }
    fetch('/api/admin/categories', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(category)
    })
        .then(response => {
            if(!response.ok) {
                alert("Có lỗi xảy ra");
            }
            return response.json();
        })
        .then(data => {
            if(data.status === 1) {
                if(category.type === 'edit') {
                    let categoryToUpdate = categories.findIndex(categori => categori.id == category.id);
                    if(categoryToUpdate !== -1) {
                        categories[categoryToUpdate] = category;
                    }
                    renderCategory();
                }
                if(category.type === 'add') {
                    category.id = data.id;
                    categories.push(category);
                    renderCategory();
                }
            }
            alert(data.message);
            closeModal();
        })
        .catch(error => console.error('Error:', error));

}
