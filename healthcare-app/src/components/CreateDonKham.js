import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import Apis, { authApis, endpoints } from '../configs/Apis';

const CreateDonKham = () => {
  const { lichKhamId } = useParams();
  const [ghiChu, setGhiChu] = useState('');
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleCreateDonKham = async () => {
    try {
      const response = await authApis().post(endpoints.donkham(lichKhamId), {
        ghiChu,
      });
      setMessage('Tạo đơn khám thành công!');
      const donKhamId = response.data.id;
      // Chuyển sang trang thêm bệnh, truyền donKhamId
      navigate(`/doctor/donkham/${donKhamId}/thembenh`);
    } catch (err) {
      setMessage(err.response?.data || 'Lỗi khi tạo đơn khám');
    }
  };

  return (
    <div style={{ padding: '20px', maxWidth: '600px' }}>
      <h2>Tạo đơn khám</h2>
      <p><strong>Lịch khám ID:</strong> {lichKhamId}</p>
      <input
        type="text"
        placeholder="Chuẩn đoán"
        value={ghiChu}
        onChange={(e) => setGhiChu(e.target.value)}
      />
      <button onClick={handleCreateDonKham}>Tạo đơn khám</button>
      <p style={{ color: 'green', marginTop: '15px' }}>{message}</p>
    </div>
  );
};

export default CreateDonKham;
