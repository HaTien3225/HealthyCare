import { useContext, useEffect, useState } from "react";
import { Button, Container, Form, Image, Nav, Navbar, NavDropdown } from "react-bootstrap";
import Apis, { endpoints } from "../../configs/Apis";
import { Link, useNavigate } from "react-router-dom";
import { MyDispatchContext, MyUserContext } from "../../configs/Contexts";

const Header = () => {
    const [specializations, setSpecializations] = useState([]);
    const nav = useNavigate();
    const [kw, setKw] = useState("");
    const user = useContext(MyUserContext);
    const dispatch = useContext(MyDispatchContext);

    // Load danh sách chuyên khoa
    useEffect(() => {
        // const loadSpecializations = async () => {
        //     try {
        //         let res = await Apis.get(endpoints['specializations']);
        //         setSpecializations(res.data);
        //     } catch (err) {
        //         console.error(err);
        //     }
        // };
        // loadSpecializations();
    }, []);

    // Xử lý tìm kiếm
    const search = (e) => {
        e.preventDefault();
        if (kw.trim() !== "")
            nav(`/?kw=${kw}`);
    };

    return (
        <Navbar expand="lg" className="bg-body-tertiary shadow-sm">
            <Container>
                {/* Logo và Tên ứng dụng */}
                <Navbar.Brand as={Link} to="/" className="fw-bold text-primary">
                    Healthy_OU's
                </Navbar.Brand>

                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    {/* Menu bên trái */}
                    <Nav className="me-auto align-items-center">
                        <Link to="/" className="nav-link">Trang chủ</Link>

                        {/* <NavDropdown title="Chuyên khoa" id="specialization-dropdown">
                            {specializations.map(s => (
                                <Link to={`/?specId=${s.id}`} key={s.id} className="dropdown-item">
                                    {s.name}
                                </Link>
                            ))}
                        </NavDropdown> */}

                        {user?.role === "ROLE_DOCTOR" && (
                            <>
                                <Link to="/doctor/pending" className="nav-link">Lịch khám chờ duyệt</Link>
                                <Link to="/doctor/accepted" className="nav-link">Lịch khám đã chấp nhận</Link>


                            </>
                        )}
                        {user?.role === "ROLE_USER" && (
                            <>
                                <Link to="/hososuckhoe" className="nav-link">Hồ sơ sức khỏe</Link>
                                <NavDropdown
                                    id="nav-dropdown-dark-example"
                                    title="Lịch khám"
                                    menuVariant="dark">
                                    <NavDropdown.Item href="#action/3.3">Lịch khám của tôi</NavDropdown.Item>
                                    <NavDropdown.Divider />
                                    <NavDropdown.Item href="#action/3.4">Đặt lịch khám</NavDropdown.Item>
                                </NavDropdown>
                                <Link to="#" className="nav-link">Đánh giá bác sĩ</Link>
                                <Link to="#" className="nav-link">Thanh toán</Link>
                            </>
                        )}
                    </Nav>

                    {/* Form tìm kiếm */}
                    {/* <Form className="d-flex me-3" onSubmit={search}>
                        <Form.Control
                            type="text"
                            value={kw}
                            onChange={e => setKw(e.target.value)}
                            placeholder="Tìm bác sĩ hoặc chuyên khoa..."
                            className="me-2"
                        />
                        <Button type="submit" variant="outline-success">Tìm</Button>
                    </Form> */}

                    {/* Menu tài khoản */}
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
                                        src={user.avatar}
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
                            <Link to="/profile" className="dropdown-item">Thông tin cá nhân</Link>


                            <NavDropdown.Divider />
                            <Button
                                onClick={() => dispatch({ type: "logout" })}
                                className="dropdown-item text-danger"
                            >
                                Đăng xuất
                            </Button>
                        </NavDropdown>
                    )}
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default Header;
