import React, { useContext, useEffect, useState } from 'react';
import { Container, ListGroup, Spinner, Alert, Pagination, Form, Button } from 'react-bootstrap';
import { format } from 'date-fns';
import { authApis, endpoints } from '../configs/Apis';
import { MyUserContext } from '../configs/Contexts';

const BenhManageDoctor = () => {
  const [benhList, setBenhList] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize, setPageSize] = useState(10);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [formData, setFormData] = useState({ tenBenh: '', moTa: '' });
  const [isCreating, setIsCreating] = useState(false);
  const [createError, setCreateError] = useState(null);
  const [createSuccess, setCreateSuccess] = useState(false);

  const user = useContext(MyUserContext);

  const fetchBenhList = async () => {
    try {
      setLoading(true);
      setError(null);
      
      const response = await authApis().get(endpoints.benhdoctor, {
        params: { page: currentPage, pageSize }
      });

      setBenhList(response.data);
    } catch (err) {
      setError(err.message || 'Có lỗi xảy ra khi tải danh sách bệnh');
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    try {
      setIsCreating(true);
      setCreateError(null);
      
      await authApis().post(endpoints.benhdoctor, formData);
      
      setFormData({ tenBenh: '', moTa: '' });
      setCreateSuccess(true);
      fetchBenhList(); // Refresh list
      
      setTimeout(() => setCreateSuccess(false), 3000);
    } catch (err) {
      setCreateError(err.response?.data?.message || 'Tạo bệnh thất bại');
    } finally {
      setIsCreating(false);
    }
  };

  useEffect(() => {
    fetchBenhList();
  }, [currentPage, pageSize]);

  const handlePageChange = (newPage) => {
    if (newPage >= 1) setCurrentPage(newPage);
  };
  if(!user)return<h1>Vui lòng đăng nhập</h1>

  return (
    <Container className="mt-4">
      <h3>Quản lý Bệnh</h3>

      {/* Form tạo mới */}
      <Form onSubmit={handleCreate} className="mb-4">
        <Form.Group className="mb-3">
          <Form.Label>Tên bệnh</Form.Label>
          <Form.Control 
            type="text" 
            value={formData.tenBenh}
            onChange={(e) => setFormData({...formData, tenBenh: e.target.value})}
            required 
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label>Mô tả</Form.Label>
          <Form.Control 
            as="textarea" 
            rows={3}
            value={formData.moTa}
            onChange={(e) => setFormData({...formData, moTa: e.target.value})}
          />
        </Form.Group>

        <div className="d-flex align-items-center gap-2">
          <Button variant="success" type="submit" disabled={isCreating}>
            Tạo bệnh
          </Button>
          {isCreating && <Spinner animation="border" size="sm" />}
        </div>

        {createSuccess && <Alert variant="success" className="mt-2">Tạo bệnh thành công!</Alert>}
        {createError && <Alert variant="danger" className="mt-2">{createError}</Alert>}
      </Form>

      {/* Danh sách bệnh */}
      {error && <Alert variant="danger">{error}</Alert>}

      {loading ? (
        <div className="text-center">
          <Spinner animation="border" variant="primary" />
        </div>
      ) : (
        <>
          <div style={{ height: '60vh', overflowY: 'scroll', border: '1px solid #ddd', borderRadius: '4px' }}>
            <ListGroup variant="flush">
              {benhList.map((benh, index) => (
                <ListGroup.Item key={index}>
                  <div className="d-flex justify-content-between align-items-start">
                    <div>
                      <h5>{benh.tenBenh}</h5>
                      <p className="text-muted">{benh.moTa}</p>
                    </div>
                    <small className="text-muted">
                      {format(new Date(benh.createdDate), 'dd/MM/yyyy HH:mm')}
                    </small>
                  </div>
                </ListGroup.Item>
              ))}
            </ListGroup>
          </div>

          {/* Phân trang */}
          <div className="d-flex justify-content-center mt-3">
            <Pagination>
              <Pagination.Prev 
                onClick={() => handlePageChange(currentPage - 1)} 
                disabled={currentPage === 1}
              />
              <Pagination.Item active>{currentPage}</Pagination.Item>
              <Pagination.Next 
                onClick={() => handlePageChange(currentPage + 1)}
                disabled={benhList.length < pageSize}
              />
            </Pagination>
          </div>
        </>
      )}
    </Container>
  );
};

export default BenhManageDoctor;