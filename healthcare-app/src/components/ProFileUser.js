import { useContext, useEffect, useState } from "react";
import { Button, Container, Image, Card, Row, Col } from "react-bootstrap";
import { MyUserContext } from "../configs/Contexts";

const ProFileUser = () => {
    const [loading, setLoading] = useState(true);
    const user = useContext(MyUserContext);

    useEffect(() => {
        if (user) {
            setLoading(false);
        } else {
            setLoading(true);
        }
    }, [user]);

    if (loading) {
        return <div>Đang tải thông tin hồ sơ...</div>;
    }

    if (!user) {
        return <div>Người dùng chưa đăng nhập.</div>;
    }

    return (
        <Container>
            <Row className="mt-4">
                <Col md={4} className="text-center">
                    <Image
                        src={user.avatar || "/default-avatar.png"}
                        roundedCircle
                        width="150"
                        height="150"
                        alt="User Avatar"
                    />
                </Col>
                <Col md={8}>
                    <Card>
                        <Card.Body>
                            <Card.Title>{user.username || "Chưa có tên đăng nhập"}</Card.Title>
                            <Card.Subtitle className="mb-2 text-muted">
                                {user.email || "Chưa có email"}
                            </Card.Subtitle>
                            <Card.Text>
                                <strong>Họ và tên: </strong>
                                {user.ho || "Không có"} {user.ten || ""}
                            </Card.Text>
                            <Card.Text>
                                <strong>Số điện thoại: </strong>
                                {user.phone || "Không có"}
                            </Card.Text>
                            <Card.Text>
                                <strong>Số CCCD: </strong>
                                {user.cccd || "Không có"}
                            </Card.Text>
                            <Card.Text>
                                <strong>Vai trò: </strong>
                                {user.role || "Không xác định"}
                            </Card.Text>
                            {user.khoaId && (
                                <>
                                    <Card.Text>
                                        <strong>Khoa: </strong>
                                        {user.khoaId.tenKhoa || "Không có"}
                                    </Card.Text>
                                    <Card.Text>
                                        <strong>Mô tả khoa: </strong>
                                        {user.khoaId.moTa || ""}
                                    </Card.Text>
                                </>
                            )}
                            {/* <Button variant="primary" onClick={() => alert("Chỉnh sửa hồ sơ")}>
                                Chỉnh sửa hồ sơ
                            </Button> */}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default ProFileUser;
