import { useContext, useEffect, useState } from "react";
import { Button, Container, Table, Spinner, Alert } from "react-bootstrap";
import Apis, { endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Contexts";

const DoctorSchedule = () => {
    const [appointments, setAppointments] = useState([]);  // Khởi tạo mảng rỗng
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState(null);
    const user = useContext(MyUserContext);

    useEffect(() => {
        const loadAppointments = async () => {
            try {
                const res = await Apis.get(endpoints.doctorLichKham(user.id));
                
                // Kiểm tra nếu res.data là mảng trước khi cập nhật state
                if (Array.isArray(res.data)) {
                    setAppointments(res.data);
                } else {
                    setAppointments([]);  // Nếu không phải mảng, reset appointments
                    setMessage("Lỗi: Dữ liệu không hợp lệ");
                }
            } catch (err) {
                console.error(err);
                setMessage("Lỗi khi tải lịch khám");
            } finally {
                setLoading(false);
            }
        };

        if (user?.role === "ROLE_DOCTOR") {
            loadAppointments();
        }
    }, [user]);

    const handleMarkAsCompleted = async (id) => {
        try {
            const updated = { daKham: true };
            // Sử dụng endpoint từ cấu hình API
            await Apis.put(endpoints.updateLichKham(id), updated);
            setAppointments(prev =>
                prev.map(app => (app.id === id ? { ...app, daKham: true } : app))
            );
            setMessage("Đã cập nhật trạng thái khám");
        } catch (err) {
            console.error(err);
            setMessage("Cập nhật thất bại");
        }
    };

    if (loading) return <Spinner animation="border" />;

    return (
        <Container className="mt-4">
            <h3>Lịch khám chưa thực hiện</h3>
            {message && <Alert variant="info">{message}</Alert>}
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Họ tên bệnh nhân</th>
                        <th>Ngày khám</th>
                        <th>Giờ khám</th>
                        <th>Thực hiện</th>
                    </tr>
                </thead>
                <tbody>
                    {appointments.map((a, idx) => (
                        <tr key={a.id}>
                            <td>{idx + 1}</td>
                            <td>{a.benhNhan?.hoTen || "Chưa có thông tin"}</td>
                            <td>{a.ngayKham}</td>
                            <td>{a.khungGio}</td>
                            <td>
                                {!a.daKham ? (
                                    <Button
                                        variant="success"
                                        onClick={() => handleMarkAsCompleted(a.id)}
                                    >
                                        Đánh dấu đã khám
                                    </Button>
                                ) : (
                                    <span className="text-success">Đã khám</span>
                                )}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </Container>
    );
};

export default DoctorSchedule;
