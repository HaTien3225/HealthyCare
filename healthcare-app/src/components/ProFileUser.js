import { useContext, useEffect, useState } from "react";
import { Button, Container, Image, Card, Row, Col } from "react-bootstrap";
import { MyUserContext } from "../configs/Contexts";

const ProFileUser = () => {
    const [loading, setLoading] = useState(true);  // Thêm trạng thái loading
    const user = useContext(MyUserContext); // Lấy dữ liệu người dùng từ context

    useEffect(() => {
        if (user) {
            setLoading(false);  // Đảm bảo set loading là false khi đã có dữ liệu
        } else {
            setLoading(true); // Nếu không có user thì tiếp tục loading
        }
    }, [user]);

    if (loading) {
        return <div>Loading profile...</div>;  // Hiển thị khi đang tải
    }

    if (!user) {
        return <div>User is not logged in.</div>; // Nếu không có user, hiển thị thông báo lỗi
    }

    console.log(user);
    


    return (
        <Container>
            <Row className="mt-4">
                <Col md={4}>
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
                            <Card.Title>{user?.username || "Chưa có tên"}</Card.Title>
                            <Card.Subtitle className="mb-2 text-muted">
                                {user.email || "Chưa có email"}
                            </Card.Subtitle>
                            <Card.Text>
                                <strong>Tên: </strong>
                                {user.ho || "Không có thông tin"} {user.ten || "Không có thông tin"}
                            </Card.Text>
                            <Card.Text>
                                <strong>Số điện thoại: </strong>{user.phone || "Không có thông tin"}
                            </Card.Text>
                            <Button variant="primary" onClick={() => alert('Chỉnh sửa hồ sơ')}>
                                Chỉnh sửa hồ sơ
                            </Button>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default ProFileUser;
