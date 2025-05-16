import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const AddBenh = () => {
  const { donKhamId } = useParams();
  const [benhName, setBenhName] = useState('');
  const [benhs, setBenhs] = useState([]);
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const loadBenhs = async () => {
    try {
      const response = await axios.get(`/api/donkham/${donKhamId}/benh`);
      setBenhs(response.data);
    } catch (err) {
      console.error('Lỗi khi load danh sách bệnh', err);
    }
  };

  useEffect(() => {
    loadBenhs();
  }, [donKhamId]);

  const handleAddBenh = async () => {
    try {
      await axios.post(`/api/donkham/${donKhamId}/benh`, {
        ten: benhName,
      });
      setMessage('Thêm bệnh thành công!');
      setBenhName('');
      await loadBenhs();
    } catch (err) {
      setMessage(err.response?.data || 'Lỗi khi thêm bệnh');
    }
  };

  const handleNext = () => {
    // Chuyển sang trang thêm xét nghiệm
    navigate(`/doctor/donkham/${donKhamId}/themxetnghiem`);
  };

  return (
    <div style={{ padding: '20px', maxWidth: '600px' }}>
      <h2>Thêm bệnh vào đơn khám</h2>
      <input
        type="text"
        placeholder="Tên bệnh"
        value={benhName}
        onChange={(e) => setBenhName(e.target.value)}
      />
      <button onClick={handleAddBenh}>Thêm bệnh</button>
      <button onClick={handleNext} style={{ marginLeft: '10px' }}>
        Tiếp tục thêm xét nghiệm
      </button>

      <h4>Danh sách bệnh đã thêm</h4>
      {benhs.length === 0 ? (
        <p>Chưa có bệnh nào được thêm.</p>
      ) : (
        <ul>
          {benhs.map((b, idx) => (
            <li key={idx}>{b.ten}</li>
          ))}
        </ul>
      )}

      <p style={{ color: 'green', marginTop: '15px' }}>{message}</p>
    </div>
  );
};

export default AddBenh;
