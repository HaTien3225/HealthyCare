function deleteBenhVien(endpoint, id, redirect_endpoint) {
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
function loadKhoa(benhvienId) {
    fetch(`/ung_dung_dat_lich_kham_suc_khoe_truc_tuyen/admin/api/khoas?benhvienid=${benhvienId}&page=${currentPage}`)
            .then(response => response.json())
            .then(data => {
                // Gọi hàm hiển thị dữ liệu khoa
                displayKhoaList(data);
                data_length = data.length;
            })
            .catch(error => {
                console.error('Lỗi khi tải dữ liệu khoa:', error);
            });
}

// Hàm hiển thị danh sách khoa
function displayKhoaList(khoaList) {
    const khoaListDiv = document.getElementById("khoaList");
    khoaListDiv.innerHTML = '';  // Xóa dữ liệu cũ trước khi hiển thị mới

    // Lặp qua các khoa và hiển thị
    khoaList.forEach(khoa => {
        const khoaItem = document.createElement('a');
        khoaItem.href = '#';
        khoaItem.classList.add('list-group-item', 'list-group-item-action');
        khoaItem.innerHTML = `${khoa.id} - ${khoa.tenKhoa}`;

        // Thêm vào phần danh sách
        khoaListDiv.appendChild(khoaItem);
    });
}

// Hàm thay đổi trang
function changePage(direction, benhvienId) {
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
    loadKhoa(benhvienId);
}
