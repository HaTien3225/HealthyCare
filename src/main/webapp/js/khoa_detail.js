function deleteKhoa(endpoint, id, redirect_endpoint) {
    if (confirm("Bạn chắc chắn xóa không?") === true) {
        fetch(`${endpoint}/${id}`, {
            method: 'delete'
        }).then(res => {
            if (res.status === 204) {
                alert("Xóa thành công!");
                window.location.href = redirect_endpoint;
            } else
                alert("Hệ thống bị lỗi!");
        });
    }
}
let currentPage = 1;
let data_length = 0;


// Hàm tải khoa từ API
function loadBenh(khoaId) {
    fetch(`/ung_dung_dat_lich_kham_suc_khoe_truc_tuyen/admin/api/benhs?khoaid=${khoaId}&page=${currentPage}`)
            .then(response => response.json())
            .then(data => {
                // Gọi hàm hiển thị dữ liệu khoa
                displayBenhList(data);
                data_length = data.length;
            })
            .catch(error => {
                console.error('Lỗi khi tải dữ liệu khoa:', error);
            });
}

// Hàm hiển thị danh sách khoa
function displayBenhList(benhList) {
    const benhListDiv = document.getElementById("benhList");
    benhListDiv.innerHTML = '';  // Xóa dữ liệu cũ trước khi hiển thị mới

    // Lặp qua các khoa và hiển thị
    benhList.forEach(benh => {
        const benhItem = document.createElement('a');
        benhItem.href = `/ung_dung_dat_lich_kham_suc_khoe_truc_tuyen/admin/benhs/${benh.id}`;
        benhItem.classList.add('list-group-item', 'list-group-item-action');
        benhItem.innerHTML = `${benh.id} - ${benh.tenBenh}`;

        // Thêm vào phần danh sách
        benhListDiv.appendChild(benhItem);
    });
}

// Hàm thay đổi trang
function changePage(direction, khoaid) {
    const nextButton = document.getElementById("next-page-button");
    if (direction === 1) {
        currentPage--;
        nextButton.disabled = false;// Giảm trang
    } else if (direction === 2) {
        if (data_length === 0)
        {
            nextButton.disabled = true;
        } else
            currentPage++;  // Tăng trang
    }

    // Gọi lại loadKhoa sau khi thay đổi trang
    if(currentPage <= 0)
        currentPage = 1;
    loadBenh(khoaid);
}