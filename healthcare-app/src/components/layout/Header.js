import { useContext, useState } from "react";
import { Container, Image, Nav, Navbar, NavDropdown } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { MyDispatchContext, MyUserContext } from "../../configs/Contexts";

const Header = () => {
    const nav = useNavigate();
    const [kw, setKw] = useState("");
    const user = useContext(MyUserContext);
    const dispatch = useContext(MyDispatchContext);
    const navigate = useNavigate();

    const search = (e) => {
        e.preventDefault();
        if (kw.trim() !== "") nav(`/?kw=${kw}`);
    };

    return (
        <Navbar expand="lg" className="bg-body-tertiary shadow-sm">
            <Container>
                <Navbar.Brand as={Link} to="/" className="fw-bold text-primary">
                    Healthy_OU's
                </Navbar.Brand>

                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="me-auto align-items-center">
                        <Link to="/" className="nav-link">Trang chủ</Link>

                        {user?.role === "ROLE_DOCTOR" && user.isActive === true && (
                            <>

                                <Link to="/doctor/thongke" className="nav-link">Thống kê</Link>
                                <NavDropdown title="Lịch khám" menuVariant="light">
                                    <NavDropdown.Item as={Link} to="/doctor/pending">Lịch khám chờ duyệt</NavDropdown.Item>
                                    <NavDropdown.Divider />
                                    <NavDropdown.Item as={Link} to="/doctor/accepted">Lịch khám đã chấp nhận</NavDropdown.Item>
                                </NavDropdown>
                                <Link to="/doctor/benhmanage" className="nav-link">Bệnh trong khoa</Link>
                                <NavDropdown title="Đánh giá" menuVariant="dark">
                                    <NavDropdown.Item as={Link} to="/danhgiadoctor">
                                        Đánh giá của bệnh nhân
                                    </NavDropdown.Item>
                                    {/* <NavDropdown.Divider />
                                    <NavDropdown.Item as={Link} to="/xemdanhgia">
                                        Xem tất cả đánh giá
                                    </NavDropdown.Item> */}
                                </NavDropdown>

                            </>
                        )}

                        {user?.role === "ROLE_USER" && (
                            <>
                                <Link to="/hososuckhoe" className="nav-link">Hồ sơ sức khỏe</Link>

                                <NavDropdown title="Lịch khám" menuVariant="dark">
                                    <NavDropdown.Item as={Link} to="/lichkhamuser">Lịch khám của tôi</NavDropdown.Item>
                                    <NavDropdown.Divider />
                                    <NavDropdown.Item as={Link} to="/datlichkham">Đặt lịch khám</NavDropdown.Item>
                                </NavDropdown>

                                <NavDropdown title="Đánh giá" menuVariant="dark">
                                    <NavDropdown.Item as={Link} to="/danhgia">Đánh giá bác sĩ</NavDropdown.Item>
                                    <NavDropdown.Divider />
                                    <NavDropdown.Item as={Link} to="/xemdanhgia">Xem đánh giá</NavDropdown.Item>
                                </NavDropdown>

                                <Link to="/cacdonchuathanhtoan" className="nav-link">Thanh toán</Link>
                            </>
                        )}
                    </Nav>

                    {/* Nếu muốn bật lại form tìm kiếm */}
                    {/* 
                    <Form className="d-flex me-3" onSubmit={search}>
                        <Form.Control
                            type="text"
                            value={kw}
                            onChange={e => setKw(e.target.value)}
                            placeholder="Tìm bác sĩ hoặc chuyên khoa..."
                            className="me-2"
                        />
                        <Button type="submit" variant="outline-success">Tìm</Button>
                    </Form> 
                    */}

                    {user === null ? (
                        <>
                            <Link to="/register" className="nav-link text-success">Đăng ký</Link>

                            <Link to="/login" className="nav-link text-danger">Đăng nhập</Link>
                        </>
                    ) : (
                        <NavDropdown
                            title={
                                <span>
                                    <Image
                                        src={user.avatar || "/default-avatar.png"}
                                        roundedCircle
                                        width="30"
                                        height="30"
                                        className="me-2"
                                        alt="avatar"
                                    />
                                    {user.username}
                                </span>
                            }
                            id="user-nav-dropdown"
                            align="end"
                        >
                            <NavDropdown.Item as={Link} to="/profile">Thông tin cá nhân</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item as="button" className="text-danger" onClick={() => { dispatch({ type: "logout" }); navigate("/login"); }}>
                                Đăng xuất
                            </NavDropdown.Item>
                        </NavDropdown>
                    )}
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default Header;
