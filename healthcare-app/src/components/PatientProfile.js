import { useState, useEffect } from 'react';
import { Button, Container, Form, Alert, Spinner } from 'react-bootstrap';
import Apis, { endpoints } from '../configs/Apis';

const PatientProfile = ({ patientId }) => {
    const [patientProfile, setPatientProfile] = useState(null);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState(null);
    const [formData, setFormData] = useState({
        chieuCao: '',
        canNang: '',
        birth: ''
    });

    useEffect(() => {
        const loadProfile = async () => {
            try {
                const res = await Apis.get(endpoints.getHoSoSucKhoe(patientId));
                setPatientProfile(res.data);
                setFormData({
                    chieuCao: res.data.chieuCao,
                    canNang: res.data.canNang,
                    birth: res.data.birth
                });
            } catch (err) {
                setMessage("Không tìm thấy hồ sơ bệnh nhân");
            } finally {
                setLoading(false);
            }
        };

        if (patientId) {
            loadProfile();
        }
    }, [patientId]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const updatedProfile = { ...formData };
            await Apis.put(endpoints.updateHoSoSucKhoe(patientId), updatedProfile);
            setMessage('Cập nhật hồ sơ thành công!');
        } catch (err) {
            setMessage('Cập nhật hồ sơ thất bại!');
        }
    };

    if (loading) return <Spinner animation="border" />;

    return (
        <Container className="mt-4">
            <h3>Hồ Sơ Bệnh Nhân</h3>
            {message && <Alert variant="info">{message}</Alert>}

            {patientProfile ? (
                <Form onSubmit={handleSubmit}>
                    <Form.Group controlId="chieuCao">
                        <Form.Label>Chiều Cao</Form.Label>
                        <Form.Control
                            type="text"
                            name="chieuCao"
                            value={formData.chieuCao}
                            onChange={handleInputChange}
                        />
                    </Form.Group>

                    <Form.Group controlId="canNang">
                        <Form.Label>Cân Nặng</Form.Label>
                        <Form.Control
                            type="text"
                            name="canNang"
                            value={formData.canNang}
                            onChange={handleInputChange}
                        />
                    </Form.Group>

                    <Form.Group controlId="birth">
                        <Form.Label>Ngày Sinh</Form.Label>
                        <Form.Control
                            type="date"
                            name="birth"
                            value={formData.birth}
                            onChange={handleInputChange}
                        />
                    </Form.Group>

                    <Button variant="primary" type="submit">Cập nhật</Button>
                </Form>
            ) : (
                <p>Không có thông tin hồ sơ cho bệnh nhân này.</p>
            )}
        </Container>
    );
};

export default PatientProfile;
