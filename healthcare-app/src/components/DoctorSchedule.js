import { useContext, useEffect, useState } from "react";
import { Button, Container, Table, Spinner, Alert } from "react-bootstrap";
import Apis, { endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Contexts";

const DoctorSchedule = () => {
    const [appointments, setAppointments] = useState([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState(null);
    const user = useContext(MyUserContext);

    useEffect(() => {
        const loadAppointments = async () => {
            setLoading(true);
            try {
                if (!user?.id) {
                    setMessage("Không xác định được thông tin bác sĩ.");
                    return;
                }

                const res = await Apis.get(endpoints.doctorLichKham(user.id));
                if (Array.isArray(res.data)) {
                    setAppointments(res.data);
                } else {
                    setAppointments([]);
                    setMessage("Lỗi: Dữ liệu lịch khám không hợp lệ.");
                }
            } catch (err) {
                console.error(err);
                setMessage("Lỗi khi tải lịch khám.");
            } finally {
                setLoading(false);
            }
        };

        if (user?.role === "ROLE_DOCTOR") {
            loadAppointments();
        } else {
            setLoading(false);
            setMessage("Bạn không có quyền truy cập lịch khám.");
        }
    }, [user]);

    const handleMarkAsCompleted = async (id) => {
        try {
            const updated = { daKham: true };
            await Apis.put(endpoints.updateLichKham(id), updated);
            setAppointments(prev =>
                prev.map(app => (app.id === id ? { ...app, daKham: true } : app))
            );
            setMessage("Đã cập nhật trạng thái lịch khám.");
        } catch (err) {
            console.error(err);
            setMessage("Cập nhật trạng thái thất bại.");
        }
    };

    const formatDateArray = (arr) => {
        if (!Array.isArray(arr) || arr.length < 3) return "Không rõ";
        const [year, month, day] = arr;
        return `${day.toString().padStart(2, "0")}/${month.toString().padStart(2, "0")}/${year}`;
    };

    if (loading) {
        return (
            <Container className="mt-4 text-center">
                <Spinner animation="border" />
                <p>Đang tải lịch khám...</p>
            </Container>
        );
    }

    return (
        <Container className="mt-4">
            <h3>Lịch khám chưa thực hiện</h3>
            {message && (
                <Alert variant={message.toLowerCase().includes("lỗi") || message.toLowerCase().includes("thất bại") ? "danger" : "info"}>
                    {message}
                </Alert>
            )}
            {appointments.length === 0 ? (
                <p className="text-muted">Không có lịch khám nào cần thực hiện.</p>
            ) : (
                <Table striped bordered hover responsive>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Họ tên bệnh nhân</th>
                            <th>Ngày khám</th>
                            <th>Khung giờ</th>
                            <th>Thực hiện</th>
                        </tr>
                    </thead>
                    <tbody>
                        {appointments.map((a, idx) => (
                            <tr key={a.id || idx}>
                                <td>{idx + 1}</td>
                                <td>{a.benhNhanId ? `${a.benhNhanId.ho} ${a.benhNhanId.ten}` : "Chưa có thông tin"}</td>
                                <td>{formatDateArray(a.ngay)}</td>
                                <td>{a.khungGio?.tenKg || "Không rõ"}</td>
                                <td>
                                    {!a.daKham ? (
                                        <Button
                                            variant="success"
                                            onClick={() => handleMarkAsCompleted(a.id)}
                                        >
                                            Đánh dấu đã khám
                                        </Button>
                                    ) : (
                                        <span className="text-success fw-bold">Đã khám</span>
                                    )}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            )}
        </Container>
    );
};

export default DoctorSchedule;
