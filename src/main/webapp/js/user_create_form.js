document.addEventListener("DOMContentLoaded", function () {
    const benhvienSelect = document.getElementById("benhvien");
    const khoaSelect = document.getElementById("khoaid");

    benhvienSelect.addEventListener("change", async function () {
        const benhvienId = this.value;

        // Xóa danh sách cũ
        khoaSelect.innerHTML = '<option value="">-- Chọn khoa --</option>';

        if (!benhvienId) return;

        try {
            const response = await fetch(`/ung_dung_dat_lich_kham_suc_khoe_truc_tuyen/admin/api/khoas?benhvienid=${benhvienId}&pagesize=100`);
            if (!response.ok) throw new Error("Failed to fetch");

            const data = await response.json();

            data.forEach(khoa => {
                const option = document.createElement("option");
                option.value = khoa.id;
                option.textContent = khoa.tenKhoa || "Không tên";
                khoaSelect.appendChild(option);
            });
        } catch (error) {
            console.error("Error loading khoa:", error);
        }
    });
});
document.addEventListener("DOMContentLoaded", function () {
    const roleSelect = document.getElementById("roleId");
    const benhvienSelect = document.getElementById("benhvien");
    const khoaSelect = document.getElementById("khoaid");

    function toggleFields() {
        const selectedRole = roleSelect.options[roleSelect.selectedIndex].text;
        const isDoctor = selectedRole === "ROLE_DOCTOR";

        benhvienSelect.disabled = !isDoctor;
        khoaSelect.disabled = !isDoctor;
    }

    roleSelect.addEventListener("change", toggleFields);

    // Gọi khi trang load lần đầu
    toggleFields();
});
//document.addEventListener("DOMContentLoaded", function () {
//    const form = document.querySelector("form");
//    form.addEventListener("submit", function (e) {
//        e.preventDefault(); // Ngăn chuyển trang để debug
//
//        const formData = new FormData(this);
//        for (const [key, value] of formData.entries()) {
//            console.log(`${key}: ${value}`);
//        }
//
//        // Sau khi kiểm tra xong, có thể submit lại thủ công nếu cần:
//        // this.submit(); // Đừng bật lại dòng này nếu vẫn đang test
//    });
//});