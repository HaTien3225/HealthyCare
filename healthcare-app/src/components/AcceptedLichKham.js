import { useContext, useEffect, useState } from "react";
import { Table, Alert, Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import Apis, { endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Contexts";

const AcceptedLichKham = () => {
  const [lichKhams, setLichKhams] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const user = useContext(MyUserContext);
  const navigate = useNavigate();

  const fetchAccepted = async () => {
    try {
      const res = await Apis.get(endpoints.acceptedLichKham(user.id));
      setLichKhams(res.data);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAccepted();
  }, [user.id]);

  const handleStatusChange = async (id, daKham) => {
    try {
      await Apis.put(endpoints.updateStatusLichKham(id), { daKham });
      fetchAccepted();
    } catch (e) {
      alert("Cập nhật trạng thái thất bại: " + e.message);
    }
  };

  if (loading) return <div>Đang tải lịch khám...</div>;
  if (error) return <Alert variant="danger">{error}</Alert>;
  if (lichKhams.length === 0) return <div>Không có lịch khám đã chấp nhận</div>;

  return (
    <>
      <h3>Danh sách lịch khám đã chấp nhận</h3>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Ngày</th>
            <th>Bệnh nhân</th>
            <th>Email</th>
            <th>Đã khám</th>
            <th>Khám bệnh</th>
          </tr>
        </thead>
        <tbody>
          {lichKhams.map((lk) => (
            <tr key={lk.id}>
              <td>{new Date(lk.ngay).toLocaleDateString()}</td>
              <td>{lk.benhNhanId.ho} {lk.benhNhanId.ten}</td>
              <td>{lk.benhNhanId.email}</td>
              <td>
                <Form.Check 
                  type="checkbox" 
                  checked={lk.daKham} 
                  onChange={e => handleStatusChange(lk.id, e.target.checked)} 
                />
              </td>
              <td>
                <button 
                  className="btn btn-primary" 
                  onClick={() => navigate(`/doctor/lichkham/${lk.id}/taodon`)}>
                  Khám bệnh
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </>
  );
};

export default AcceptedLichKham;
