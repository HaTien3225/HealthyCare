import { useEffect, useState, useContext } from "react";
import { Container, Card, Row, Col, Spinner, Alert } from "react-bootstrap";
import Apis, { endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Contexts";

const DoctorStatistics = () => {
    const [statistics, setStatistics] = useState(null);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState(null);
    const user = useContext(MyUserContext);

    useEffect(() => {
        const loadStatistics = async () => {
            try {
                const res = await Apis.get(endpoints.doctorThongKe(user.id));
                setStatistics(res.data);
            } catch (err) {
                console.error(err);
                setMessage("Lỗi khi tải thông tin thống kê");
            } finally {
                setLoading(false);
            }
        };

        if (user?.role === "ROLE_DOCTOR") {
            loadStatistics();
        }
    }, [user]);

    if (loading) return <Spinner animation="border" />;

    return (
        <Container className="mt-4">
            <h3>Thống kê lịch khám</h3>
            {message && <Alert variant="danger">{message}</Alert>}

            <Row>
                <Col md={6}>
                    <Card>
                        <Card.Body>
                            <Card.Title>Tổng số lịch khám</Card.Title>
                            <Card.Text>{statistics?.totalAppointments || 0}</Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
                <Col md={6}>
                    <Card>
                        <Card.Body>
                            <Card.Title>Lịch khám chưa thực hiện</Card.Title>
                            <Card.Text>{statistics?.pendingAppointments || 0}</Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default DoctorStatistics;
