import React, { useState } from 'react';
import { Navigate, useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import Apis, { authApis, endpoints } from '../configs/Apis';

const AddXetNghiem = () => {
  const { donKhamId } = useParams();
  const [moTa, setMoTa] = useState('');
  const [message, setMessage] = useState('');
   const navigate = useNavigate();

  const handleAddXetNghiem = async () => {
    try {
      await authApis().post(endpoints.add_xet_nghiem(donKhamId), {
        moTa
      });
      setMessage('Thêm xét nghiệm thành công!');
      setMoTa('');
    } catch (err) {
      setMessage(err.response?.data || 'Lỗi khi thêm xét nghiệm');
    }
  };
  const handleNext = () => {
    navigate(`/doctor/accepted`);
  };

  return (
    <div style={{ padding: '20px', maxWidth: '600px' }}>
      <h2>Thêm xét nghiệm cho đơn khám</h2>
      <input
        type="text"
        placeholder="Mô tả xét nghiệm"
        value={moTa}
        onChange={(e) => setMoTa(e.target.value)}
      />

      <button onClick={handleAddXetNghiem}>Thêm xét nghiệm</button>
      <button onClick={handleNext}>Hoàn tất</button>

      <p style={{ color: 'green', marginTop: '15px' }}>{message}</p>
    </div>
  );
};

export default AddXetNghiem;
