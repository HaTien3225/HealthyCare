import { useEffect, useState, useContext } from "react";
import { Button, Table, Alert } from "react-bootstrap";
import { MyUserContext } from "../configs/Contexts";  
import Apis, { endpoints } from "../configs/Apis";   

const PendingLichKham = () => {
    const [lichKhams, setLichKhams] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const user = useContext(MyUserContext);

    const fetchPending = async () => {
        if (!user) {
            setError("Vui lòng đăng nhập để xem lịch khám");
            setLoading(false);
            return;
        }
        try {
            const res = await Apis.get(endpoints.lichkhampending(user.id));
            if (res.status !== 200) throw new Error("Lấy lịch khám thất bại");
            const data = res.data;  // nếu axios
            setLichKhams(data);
        } catch (e) {
            setError(e.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchPending();
    }, [user?.id]);  // chạy lại khi user.id thay đổi

    const handleAccept = async (id) => {
        try {
            const res = await Apis.put(endpoints.accept(id));
            if (res.status !== 200) throw new Error("Chấp nhận lịch khám thất bại");
            fetchPending();
        } catch (e) {
            setError(e.message);
        }
    };

    const handleReject = async (id) => {
        try {
            const res = await Apis.put(endpoints.reject(id));
            if (res.status !== 200) throw new Error("Từ chối lịch khám thất bại");
            fetchPending();
        } catch (e) {
            setError(e.message);
        }
    };

    if (loading) return <div>Đang tải lịch khám...</div>;
    if (error) return <Alert variant="danger">{error}</Alert>;
    if (lichKhams.length === 0) return <div>Không có lịch khám chờ duyệt</div>;

    return (
        <>
            <h3>Danh sách lịch khám chờ duyệt</h3>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>Ngày</th>
                        <th>Bệnh nhân</th>
                        <th>Email</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    {lichKhams.map((lk) => (
                        <tr key={lk.id}>
                            <td>{new Date(lk.ngay).toLocaleDateString()}</td>
                            <td>{lk.benhNhanId.ho} {lk.benhNhanId.ten}</td>
                            <td>{lk.benhNhanId.email}</td>
                            <td>
                                <Button variant="success" onClick={() => handleAccept(lk.id)} className="me-2">
                                    Chấp nhận
                                </Button>
                                <Button variant="danger" onClick={() => handleReject(lk.id)}>
                                    Từ chối
                                </Button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </>
    );
};

export default PendingLichKham;
