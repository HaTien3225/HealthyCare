import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import { Container, Form, Button, Alert } from 'react-bootstrap';

const HoSoSucKhoeBacSi = () => {
  const { benhNhanId } = useParams(); 
  const [hoSo, setHoSo] = useState(null);
  const [message, setMessage] = useState("");

  useEffect(() => {
    if (benhNhanId) {
      setMessage("");
      authApis()
        .get(endpoints.getHoSoSucKhoe(benhNhanId))
        .then((res) => {
          const data = res.data;

          
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
  <Container>
    <h2 className="mb-4">Hồ sơ sức khỏe bệnh nhân</h2>
    <Form>
      <Form.Group className="mb-3">
        <Form.Label>Chiều cao (cm):</Form.Label>
        <Form.Control
          type="number"
          name="chieuCao"
          value={hoSo.chieuCao || ""}
          onChange={handleChange}
        />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Cân nặng (kg):</Form.Label>
        <Form.Control
          type="number"
          name="canNang"
          value={hoSo.canNang || ""}
          onChange={handleChange}
        />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Ngày sinh:</Form.Label>
        <Form.Control
          type="date"
          name="birth"
          value={hoSo.birth || ""}
          onChange={handleChange}
        />
      </Form.Group>

      <Button variant="primary" onClick={handleUpdate}>
        Cập nhật hồ sơ
      </Button>
      
      {message && <Alert variant="info" className="mt-3">{message}</Alert>}
    </Form>
  </Container>
</div>
  );
};

export default HoSoSucKhoeBacSi;
