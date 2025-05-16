import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const AddXetNghiem = () => {
  const { donKhamId } = useParams();
  const [xetNghiemTen, setXetNghiemTen] = useState('');
  const [xetNghiemKetQua, setXetNghiemKetQua] = useState('');
  const [message, setMessage] = useState('');

  const handleAddXetNghiem = async () => {
    try {
      await axios.post(`/api/donkham/${donKhamId}/xetnghiem`, {
        tenXetNghiem: xetNghiemTen,
        ketQua: xetNghiemKetQua,
      });
      setMessage('Thêm xét nghiệm thành công!');
      setXetNghiemTen('');
      setXetNghiemKetQua('');
    } catch (err) {
      setMessage(err.response?.data || 'Lỗi khi thêm xét nghiệm');
    }
  };

  return (
    <div style={{ padding: '20px', maxWidth: '600px' }}>
      <h2>Thêm xét nghiệm cho đơn khám</h2>
      <input
        type="text"
        placeholder="Tên xét nghiệm"
        value={xetNghiemTen}
        onChange={(e) => setXetNghiemTen(e.target.value)}
      />
      <input
        type="text"
        placeholder="Kết quả"
        value={xetNghiemKetQua}
        onChange={(e) => setXetNghiemKetQua(e.target.value)}
      />
      <button onClick={handleAddXetNghiem}>Thêm xét nghiệm</button>

      <p style={{ color: 'green', marginTop: '15px' }}>{message}</p>
    </div>
  );
};

export default AddXetNghiem;
