import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Apis, { endpoints } from '../configs/Apis';

const AddBenh = () => {
  const { donKhamId } = useParams();
  const [keyword, setKeyword] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [assignedBenhs, setAssignedBenhs] = useState([]);
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  // Load các bệnh đã được gán cho đơn khám
  const loadAssignedBenhs = async () => {
    try {
      const res = await Apis.get(endpoints.get_benh(donKhamId));
      setAssignedBenhs(res.data);
    } catch (err) {
      console.error('Lỗi khi load danh sách bệnh đã gán:', err);
    }
  };

  // Tìm bệnh theo từ khóa
  const searchBenh = async () => {
    try {
      const res = await Apis.get(endpoints.search_benh(keyword));
      setSearchResults(res.data);
    } catch (err) {
      console.error('Lỗi khi tìm bệnh:', err);
    }
  };

  // Gán bệnh đã chọn vào đơn khám
  const assignBenh = async (benhId) => {
    try {
      await Apis.post(endpoints.add_benh(donKhamId, benhId));
      setMessage('Đã thêm bệnh vào đơn khám!');
      setKeyword('');
      setSearchResults([]);
      await loadAssignedBenhs();
    } catch (err) {
      setMessage(err.response?.data || 'Lỗi khi thêm bệnh');
    }
  };

  const handleNext = () => {
    navigate(`/doctor/donkham/${donKhamId}/themxetnghiem`);
  };

  useEffect(() => {
    loadAssignedBenhs();
  }, [donKhamId]);

  return (
    <div style={{ padding: '20px', maxWidth: '600px' }}>
      <h2>Thêm bệnh vào đơn khám</h2>

      <input
        type="text"
        placeholder="Nhập tên bệnh để tìm"
        value={keyword}
        onChange={(e) => setKeyword(e.target.value)}
      />
      <button onClick={searchBenh}>Tìm bệnh</button>

      <ul>
        {searchResults.map((b, idx) => (
          <li key={b.id}>
            {b.tenBenh}
            <button
              style={{ marginLeft: '10px' }}
              onClick={() => assignBenh(b.id)}
            >
              Thêm vào đơn khám
            </button>
          </li>
        ))}
      </ul>

      <h4>Danh sách bệnh đã gán</h4>
      {assignedBenhs.length === 0 ? (
        <p>Chưa có bệnh nào được thêm.</p>
      ) : (
        <ul>
          <li>{assignedBenhs.tenBenh}</li>
        </ul>
      )}

      <button onClick={handleNext}>Tiếp tục thêm xét nghiệm</button>

      <p style={{ color: 'green', marginTop: '15px' }}>{message}</p>
    </div>
  );
};

export default AddBenh;
