import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";

const HoSoSucKhoeBacSi = () => {
  const { benhNhanId } = useParams(); // lấy ID từ URL
  const [hoSo, setHoSo] = useState(null);
  const [message, setMessage] = useState("");

  useEffect(() => {
    if (benhNhanId) {
      setMessage("");
      authApis()
        .get(endpoints.getHoSoSucKhoe(benhNhanId))
        .then((res) => {
          const data = res.data;

          // Chuyển birth từ [yyyy, mm, dd] → "yyyy-mm-dd"
          if (Array.isArray(data.birth)) {
            const [year, month, day] = data.birth;
            data.birth = `${year}-${String(month).padStart(2, "0")}-${String(day).padStart(2, "0")}`;
          }

          setHoSo(data);
        })
        .catch(() => {
          setMessage("Không tìm thấy hồ sơ bệnh nhân.");
          setHoSo(null);
        });
    }
  }, [benhNhanId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setHoSo((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleUpdate = () => {
    if (!hoSo) return;
    setMessage("");

    // Convert birth from "yyyy-mm-dd" to [yyyy, mm, dd]
    let updateData = { ...hoSo };
    if (typeof updateData.birth === "string") {
      const [year, month, day] = updateData.birth.split("-").map(Number);
      updateData.birth = [year, month, day];
    }

    authApis()
      .put(endpoints.updateHoSoSucKhoe(benhNhanId), updateData)
      .then(() => {
        setMessage("Cập nhật thành công!");
      })
      .catch(() => {
        setMessage("Lỗi khi cập nhật hồ sơ.");
      });
  };

  if (!hoSo) return <div>Đang tải dữ liệu...</div>;

  return (
    <div className="container">
      <h2>Hồ sơ sức khỏe bệnh nhân</h2>
      <div>
        <label>Chiều cao (cm): </label>
        <input
          type="number"
          name="chieuCao"
          value={hoSo.chieuCao || ""}
          onChange={handleChange}
        />
      </div>
      <div>
        <label>Cân nặng (kg): </label>
        <input
          type="number"
          name="canNang"
          value={hoSo.canNang || ""}
          onChange={handleChange}
        />
      </div>
      <div>
        <label>Ngày sinh: </label>
        <input
          type="date"
          name="birth"
          value={hoSo.birth || ""}
          onChange={handleChange}
        />
      </div>
      <button onClick={handleUpdate}>Cập nhật hồ sơ</button>
      {message && <p>{message}</p>}
    </div>
  );
};

export default HoSoSucKhoeBacSi;
