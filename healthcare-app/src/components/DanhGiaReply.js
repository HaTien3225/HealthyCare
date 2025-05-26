import { useContext, useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Button, Container, Form, Alert } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Contexts";
import MySpinner from "./layout/MySpinner";

const DanhGiaReply = () => {
    const { id } = useParams();
    const [danhGia, setDanhGia] = useState(null);
    const [phanHoi, setPhanHoi] = useState("");
    const [isLoading, setIsLoading] = useState(true);
    const user = useContext(MyUserContext);
    const navigate = useNavigate();

    useEffect(() => {
        const loadDanhGia = async () => {
            try {
                const res = await authApis().get(`${endpoints.danhgiadoctor}/${id}`);
                setDanhGia(res.data);
                setPhanHoi(res.data.phanHoi || "");
            } catch (e) {
                console.error(e.response?.data || e.message);
            } finally {
                setIsLoading(false);
            }
        };

        loadDanhGia();
    }, [id]);

    const handleReply = async (evt) => {
        evt.preventDefault();

        if (!phanHoi.trim()) {
            alert("Nội dung phản hồi không được để trống!");
            return;
        }

        if (danhGia.phanHoi) {
            alert("Đánh giá này đã được phản hồi trước đó!");
            return;
        }

        try {
            let res = await authApis().put(`${endpoints.danhgiadoctor}/${id}/reply`, phanHoi, {
                headers: {
                    "Content-Type": "text/plain",
                },
            });

            alert("Phản hồi đánh giá thành công!");
            navigate("/danhgiadoctor");
        } catch (e) {
            console.error(e.response?.data || e.message);
            alert("Phản hồi thất bại!");
        }
    };

    if (!user) return <h1>Vui lòng đăng nhập</h1>;
    if (isLoading || !danhGia) return <MySpinner />;

    return (
        <Container style={{ maxWidth: "700px", marginTop: "30px" }}>
            <h3>Phản hồi đánh giá</h3>
            <p><strong>Người đánh giá:</strong> {danhGia.benhNhanId.ho} {danhGia.benhNhanId.ten}</p>
            <p><strong>Nội dung đánh giá:</strong> {danhGia.binhLuan}</p>

            {danhGia.phanHoi ? (
                <Alert variant="info">
                    <strong>Phản hồi đã gửi:</strong>
                    <div style={{ whiteSpace: "pre-wrap", marginTop: "10px" }}>{danhGia.phanHoi}</div>
                </Alert>
            ) : (
                <Form onSubmit={handleReply}>
                    <Form.Group className="mb-3" controlId="replyContent">
                        <Form.Label><strong>Phản hồi của bác sĩ</strong></Form.Label>
                        <Form.Control
                            as="textarea"
                            rows={5}
                            value={phanHoi}
                            onChange={(e) => setPhanHoi(e.target.value)}
                            required
                            placeholder="Nhập nội dung phản hồi..."
                        />
                    </Form.Group>
                    <Button variant="primary" type="submit">
                        Gửi phản hồi
                    </Button>
                </Form>
            )}
        </Container>
    );
};

export default DanhGiaReply;
