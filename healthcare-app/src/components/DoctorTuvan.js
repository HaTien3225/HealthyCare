import { useEffect, useState, useContext } from "react";
import { Button, Container, Form, ListGroup, Spinner, Alert } from "react-bootstrap";
import Apis, { endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Contexts";

const DoctorTuvan = () => {
    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState("");
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState(null);
    const user = useContext(MyUserContext);
    const [patientId, setPatientId] = useState(""); // Để thay thế bằng ID bệnh nhân

    useEffect(() => {
        const loadMessages = async () => {
            try {
                const res = await Apis.get(endpoints.doctorTuvan(user.id, patientId));
                setMessages(res.data);
            } catch (err) {
                console.error(err);
                setMessage("Lỗi khi tải tin nhắn");
            } finally {
                setLoading(false);
            }
        };

        if (user?.role === "ROLE_DOCTOR" && patientId) {
            loadMessages();
        }
    }, [user, patientId]);

    const handleSendMessage = async () => {
        if (!newMessage) {
            setMessage("Tin nhắn không thể trống.");
            return;
        }

        const newTinNhan = {
            senderId: user.id,
            receiverId: patientId,
            message: newMessage,
        };

        try {
            await Apis.post(endpoints.sendTuvan, newTinNhan);
            setMessages([...messages, newTinNhan]); // Cập nhật tin nhắn mới vào danh sách
            setNewMessage(""); // Reset input
            setMessage("Tin nhắn đã được gửi.");
        } catch (err) {
            console.error(err);
            setMessage("Không thể gửi tin nhắn.");
        }
    };

    if (loading) return <Spinner animation="border" />;

    return (
        <Container className="mt-4">
            <h3>Hệ thống tư vấn</h3>
            {message && <Alert variant="info">{message}</Alert>}

            <div>
                <Form.Group controlId="formPatientId">
                    <Form.Label>Nhập ID bệnh nhân</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Nhập ID bệnh nhân"
                        value={patientId}
                        onChange={(e) => setPatientId(e.target.value)}
                    />
                </Form.Group>

                <ListGroup>
                    {messages.map((msg, idx) => (
                        <ListGroup.Item key={idx}>
                            <strong>{msg.senderId === user.id ? "Bác sĩ" : "Bệnh nhân"}: </strong>
                            {msg.message}
                        </ListGroup.Item>
                    ))}
                </ListGroup>

                <Form.Group controlId="formNewMessage">
                    <Form.Label>Nhập tin nhắn</Form.Label>
                    <Form.Control
                        as="textarea"
                        rows={3}
                        value={newMessage}
                        onChange={(e) => setNewMessage(e.target.value)}
                    />
                </Form.Group>
                <Button variant="primary" onClick={handleSendMessage}>Gửi tin nhắn</Button>
            </div>
        </Container>
    );
};

export default DoctorTuvan;
