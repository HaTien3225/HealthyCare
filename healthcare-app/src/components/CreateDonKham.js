import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { authApis, endpoints } from '../configs/Apis';
import { Form, Button, Alert, ListGroup, Spinner, InputGroup } from 'react-bootstrap';

const CreateDonKham = () => {
  const { lichKhamId, benhNhanId } = useParams();
  const navigate = useNavigate();
  
  // States
  const [ghiChu, setGhiChu] = useState('');
  const [selectedBenhId, setSelectedBenhId] = useState(null);
  const [searchKeyword, setSearchKeyword] = useState('');
  const [listBenh, setListBenh] = useState([]);
  const [chiTietDonKhamList, setChiTietDonKhamList] = useState([{ dichVu: '', giaTien: '' }]);
  const [xetNghiemList, setXetNghiemList] = useState([{ moTa: '' }]);
  const [loading, setLoading] = useState({ search: false, submit: false });
  const [errors, setErrors] = useState({ main: null, benh: null });

  // Tìm kiếm bệnh với debounce
  useEffect(() => {
    const searchBenh = async () => {
      try {
        setLoading(prev => ({...prev, search: true}));
        const res = await authApis().get(endpoints.benhdoctor, {
          params: { kw: searchKeyword }
        });
        setListBenh(res.data);
      } catch (err) {
        console.error("Lỗi tải danh sách bệnh:", err);
      } finally {
        setLoading(prev => ({...prev, search: false}));
      }
    };

    const debounceTimer = setTimeout(() => {
      if (searchKeyword.trim().length > 0) {
        searchBenh();
      } else {
        setListBenh([]);
      }
    }, 500);

    return () => clearTimeout(debounceTimer);
  }, [searchKeyword]);

  // Thêm trường dynamic
  const addField = (type) => {
    const newField = type === 'dichVu' 
      ? { dichVu: '', giaTien: '' } 
      : { moTa: '' };
    
    if (type === 'dichVu') {
      setChiTietDonKhamList(prev => [...prev, newField]);
    } else {
      setXetNghiemList(prev => [...prev, newField]);
    }
  };

  // Cập nhật dynamic fields
  const handleFieldChange = (type, index, field, value) => {
    if (type === 'dichVu') {
      const updated = chiTietDonKhamList.map((item, i) => 
        i === index ? {...item, [field]: value} : item
      );
      setChiTietDonKhamList(updated);
    } else {
      const updated = xetNghiemList.map((item, i) => 
        i === index ? {...item, [field]: value} : item
      );
      setXetNghiemList(updated);
    }
  };

  // Validation
  const validateForm = () => {
    const newErrors = {
      main: null,
      benh: !selectedBenhId ? 'Vui lòng chọn bệnh' : null,
      dichVu: chiTietDonKhamList.some(item => !item.dichVu || !item.giaTien),
      xetNghiem: xetNghiemList.some(item => !item.moTa)
    };

    setErrors(newErrors);
    return !Object.values(newErrors).some(e => e);
  };

  // Xử lý submit
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    setLoading(prev => ({...prev, submit: true}));

    try {
      // Step 1: Tạo đơn khám với params trong URL
      const donKhamRes = await authApis().post(
        `${endpoints.khambenh}?benhNhanId=${benhNhanId}&lichKhamId=${lichKhamId}&benhId=${selectedBenhId}`,
        { ghiChu, isPaid: false }
      );

      if (!donKhamRes.data?.id) throw new Error('Tạo đơn khám thất bại');
      const donKhamId = donKhamRes.data.id;

      // Step 2: Gọi song song 2 API với params trong URL
      const [ctRes, xnRes] = await Promise.all([
        authApis().post(
          `${endpoints.chitietdonkhamdoctor}?donKhamId=${donKhamId}`,
          chiTietDonKhamList.map(ct => ({
            ...ct,
            giaTien: Number(ct.giaTien)
          }))
        ),
        authApis().post(
          `${endpoints.xetnghiemdoctor}?donKhamId=${donKhamId}`,
          xetNghiemList
        )
      ]);

      if (ctRes.status === 200 && xnRes.status === 200) {
        alert('Tạo đơn thành công!');
        navigate("/doctor/accepted");
      }
    } catch (err) {
      setErrors(prev => ({
        ...prev,
        main: err.response?.data?.message || err.message || 'Lỗi hệ thống'
      }));
    } finally {
      setLoading(prev => ({...prev, submit: false}));
    }
  };

  return (
    <div className="container mt-4 p-4 border rounded" style={{ maxWidth: '800px' }}>
      <h2 className="mb-4 text-primary">Tạo Đơn Khám Mới</h2>

      <Form onSubmit={handleSubmit}>
        {/* Tìm kiếm bệnh */}
        <Form.Group className="mb-4">
          <Form.Label className="fw-bold">Chọn Bệnh</Form.Label>
          <Form.Control
            type="text"
            value={searchKeyword}
            onChange={(e) => setSearchKeyword(e.target.value)}
            placeholder="Nhập tên bệnh để tìm kiếm..."
            isInvalid={!!errors.benh}
          />
          
          {errors.benh && <Form.Control.Feedback type="invalid">{errors.benh}</Form.Control.Feedback>}

          <ListGroup className="mt-2 shadow-sm" style={{ maxHeight: '200px', overflowY: 'auto' }}>
            {loading.search ? (
              <ListGroup.Item className="text-center">
                <Spinner animation="border" size="sm" />
              </ListGroup.Item>
            ) : (
              listBenh.map(benh => (
                <ListGroup.Item
                  key={benh.id}
                  action
                  onClick={() => setSelectedBenhId(benh.id)}
                  active={selectedBenhId === benh.id}
                  className="d-flex justify-content-between align-items-center"
                >
                  <div>
                    <span className="fw-bold">{benh.tenBenh}</span>
                    <span className="text-muted ms-2">{benh.moTa}</span>
                  </div>
                  {selectedBenhId === benh.id && <i className="bi bi-check2-circle text-success"></i>}
                </ListGroup.Item>
              ))
            )}
          </ListGroup>
        </Form.Group>

        {/* Ghi chú */}
        <Form.Group className="mb-4">
          <Form.Label className="fw-bold">Ghi chú</Form.Label>
          <Form.Control
            as="textarea"
            rows={3}
            value={ghiChu}
            onChange={(e) => setGhiChu(e.target.value)}
            placeholder="Nhập ghi chú cho đơn khám..."
          />
        </Form.Group>

        {/* Dịch vụ khám */}
        <div className="mb-4">
          <div className="d-flex justify-content-between align-items-center mb-3">
            <h5 className="fw-bold">Dịch Vụ Khám</h5>
            <Button 
              variant="outline-primary" 
              size="sm"
              onClick={() => addField('dichVu')}
            >
              <i className="bi bi-plus-circle me-2"></i>Thêm dịch vụ
            </Button>
          </div>

          {chiTietDonKhamList.map((item, index) => (
            <InputGroup key={index} className="mb-2">
              <Form.Control
                placeholder="Tên dịch vụ"
                value={item.dichVu}
                onChange={(e) => handleFieldChange('dichVu', index, 'dichVu', e.target.value)}
                isInvalid={errors.dichVu}
              />
              <Form.Control
                type="number"
                placeholder="Giá tiền (VND)"
                value={item.giaTien}
                onChange={(e) => handleFieldChange('dichVu', index, 'giaTien', e.target.value)}
                isInvalid={errors.dichVu}
              />
            </InputGroup>
          ))}
          {errors.dichVu && <Alert variant="danger" className="mt-2">Vui lòng điền đủ thông tin dịch vụ</Alert>}
        </div>

        {/* Xét nghiệm */}
        <div className="mb-4">
          <div className="d-flex justify-content-between align-items-center mb-3">
            <h5 className="fw-bold">Xét Nghiệm</h5>
            <Button 
              variant="outline-primary" 
              size="sm"
              onClick={() => addField('xetNghiem')}
            >
              <i className="bi bi-plus-circle me-2"></i>Thêm xét nghiệm
            </Button>
          </div>

          {xetNghiemList.map((item, index) => (
            <Form.Group key={index} className="mb-2">
              <Form.Control
                placeholder="Mô tả xét nghiệm"
                value={item.moTa}
                onChange={(e) => handleFieldChange('xetNghiem', index, 'moTa', e.target.value)}
                isInvalid={errors.xetNghiem}
              />
            </Form.Group>
          ))}
          {errors.xetNghiem && <Alert variant="danger" className="mt-2">Vui lòng nhập mô tả xét nghiệm</Alert>}
        </div>

        {/* Thông báo lỗi */}
        {errors.main && <Alert variant="danger" className="mt-3">{errors.main}</Alert>}

        {/* Nút submit */}
        <div className="d-grid gap-2 mt-4">
          <Button 
            variant="primary" 
            size="lg"
            type="submit"
            disabled={loading.submit}
          >
            {loading.submit ? (
              <>
                <Spinner animation="border" size="sm" className="me-2" />
                Đang tạo đơn...
              </>
            ) : (
              <>
                <i className="bi bi-file-earmark-medical me-2"></i>
                Tạo Đơn Khám
              </>
            )}
          </Button>
        </div>
      </Form>
    </div>
  );
};

export default CreateDonKham;