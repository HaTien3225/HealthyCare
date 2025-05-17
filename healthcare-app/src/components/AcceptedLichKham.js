import { useContext, useEffect, useState } from "react";
import { Table, Alert, Form, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import Apis, { endpoints, authApis } from "../configs/Apis";
import { MyUserContext } from "../configs/Contexts";
import { toast } from "react-toastify";

const AcceptedLichKham = () => {
  const [lichKhams, setLichKhams] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const user = useContext(MyUserContext);
  const navigate = useNavigate();

  const fetchAccepted = async () => {
    try {
      const res = await authApis().get(endpoints.acceptedLichKham);
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
      await authApis().put(endpoints.updateStatusLichKham(id), { daKham });
      fetchAccepted();
    } catch (e) {
      alert("Cập nhật trạng thái thất bại: " + e.message);
    }
  };

  if (loading) return <div>Đang tải lịch khám...</div>;
  if (error) return <Alert variant="danger">{error}</Alert>;
  if (lichKhams.length === 0) return <div>Không có lịch khám đã chấp nhận</div>;
  const sendTuvanEmail = async (lichKhamId) => {
    if (!window.confirm("Bạn có chắc chắn muốn gửi link tư vấn cho bệnh nhân?")) return;

    try {
      await authApis().post(endpoints.send_mail(lichKhamId));
      toast.success("Đã gửi link tư vấn thành công!");
    } catch (e) {
      toast.error("Lỗi khi gửi link tư vấn: " + e.message);
    }
  };
  return (
    <>
      <h3>Danh sách lịch khám đã chấp nhận</h3>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>Ngày</th>
            <th>Bệnh nhân</th>
            <th>Email</th>
            <th>Hồ sơ bệnh nhân</th>
            <th>Đã khám</th>
            <th>Khám bệnh</th>
            <th>Tư vấn</th>
            <th>Chat</th>
          </tr>
        </thead>
        <tbody>
          {lichKhams.map((lk) => (
            <tr key={lk.id}>
              <td>{new Date(lk.ngay).toLocaleDateString()}</td>
              <td>{lk.benhNhanId.ho} {lk.benhNhanId.ten}</td>
              <td>{lk.benhNhanId.email}</td>
              <td>
                <button
                  className="btn btn-secondary"
                  onClick={() => navigate(`/doctor/hosobenhnhan/${lk.benhNhanId.id}`)}>
                  Xem hồ sơ
                </button>
              </td>
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
              <td>
                <div className="d-flex flex-column gap-1">
                  <Button variant="success" href={`/tuvan/${lk.id}`} target="_blank">
                    Bắt đầu tư vấn
                  </Button>
                  <Button variant="warning" onClick={() => sendTuvanEmail(lk.id)}>
                    Gửi link tư vấn
                  </Button>
                </div>
              </td>
              <td>
                <button
                  className="btn btn-info"
                  onClick={() => {
                    const otherUserId = user.role === "ROLE_DOCTOR" ? lk.benhNhanId.id : lk.bacSiId.id;
                    navigate(`/doctor/chat/${otherUserId}`);
                  }}
                >
                  Chat
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
