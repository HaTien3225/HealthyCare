import { useEffect, useState, useContext } from "react";
import {
    Container, Card, Row, Col, Spinner, Alert, Form
} from "react-bootstrap";
import {
    BarChart, Bar,
    LineChart, Line,
    PieChart, Pie, Cell,
    XAxis, YAxis, Tooltip, Legend, ResponsiveContainer
} from "recharts";
import { MyUserContext } from "../configs/Contexts";
import { authApis, endpoints } from "../configs/Apis";

const COLORS = ["#8884d8", "#82ca9d", "#ffc658", "#ff8042", "#8dd1e1", "#a4de6c"];

const DoctorStatistics = () => {
    const user = useContext(MyUserContext);

    const [statistics, setStatistics] = useState(null);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState(null);

    const [month, setMonth] = useState(1);
    const [quarter, setQuarter] = useState(1);

    const [benhThang, setBenhThang] = useState([]);
    const [benhQuy, setBenhQuy] = useState([]);

    const [viewMode, setViewMode] = useState("month"); // "month" hoặc "quarter"
    const [chartType, setChartType] = useState("bar"); // "bar", "line", "pie"

    // Load tổng quan thống kê
    useEffect(() => {
        const loadStatistics = async () => {
            try {
                const res = await authApis().get(endpoints.doctorThongKe);
                setStatistics(res.data);
            } catch (err) {
                console.error(err);
                setMessage("Lỗi khi tải thông tin thống kê.");
            } finally {
                setLoading(false);
            }
        };

        if (user && user.role === "ROLE_DOCTOR") {
            loadStatistics();
        } else {
            setLoading(false);
            setMessage("Bạn không có quyền truy cập thống kê của bác sĩ.");
        }
    }, [user]);

    // Load bệnh theo tháng khi month thay đổi
    useEffect(() => {
        if (viewMode !== "month") return;

        const loadBenhTheoThang = async () => {
            try {
                const res = await authApis().get(endpoints.benhPhoBien(month, null));
                const data = Object.entries(res.data).map(([name, count]) => ({ name, count }));
                setBenhThang(data);
            } catch (err) {
                console.error(err);
            }
        };

        if (month) {
            loadBenhTheoThang();
        }
    }, [month, viewMode]);

    // Load bệnh theo quý khi quarter thay đổi
    useEffect(() => {
        if (viewMode !== "quarter") return;

        const loadBenhTheoQuy = async () => {
            try {
                const res = await authApis().get(endpoints.benhPhoBien(null, quarter));
                const data = Object.entries(res.data).map(([name, count]) => ({ name, count }));
                setBenhQuy(data);
            } catch (err) {
                console.error(err);
            }
        };

        if (quarter) {
            loadBenhTheoQuy();
        }
    }, [quarter, viewMode]);

    const handleMonthChange = (e) => {
        setMonth(parseInt(e.target.value));
    };

    const handleQuarterChange = (e) => {
        setQuarter(parseInt(e.target.value));
    };

    const handleViewModeChange = (e) => {
        setViewMode(e.target.value);
    };

    const handleChartTypeChange = (e) => {
        setChartType(e.target.value);
    };

    if (loading)
        return (
            <Container className="mt-4 text-center">
                <Spinner animation="border" />
                <p>Đang tải thống kê...</p>
            </Container>
        );

    const data = viewMode === "month" ? benhThang : benhQuy;
    const fillColor = viewMode === "month" ? "#8884d8" : "#82ca9d";
         if (!user) return <p>Vui lòng đăng nhập</p>
    return (
        <Container className="mt-4">
            <h3 className="mb-4">Thống kê lịch khám</h3>

            {message && <Alert variant="danger">{message}</Alert>}

            {statistics && (
                <Row className="mb-4">
                    <Col md={6}>
                        <Card>
                            <Card.Body>
                                <Card.Title>Tổng số lịch khám</Card.Title>
                                <Card.Text>{statistics.totalAppointments?.toLocaleString()}</Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col md={6}>
                        <Card>
                            <Card.Body>
                                <Card.Title>Lịch khám chưa thực hiện</Card.Title>
                                <Card.Text>{statistics.pendingAppointments?.toLocaleString()}</Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
            )}

            <Card>
                <Card.Body>
                    <Card.Title>Bệnh phổ biến</Card.Title>

                    {/* Chọn chế độ xem: tháng hoặc quý */}
                    <Form.Select value={viewMode} onChange={handleViewModeChange} className="mb-3" style={{ maxWidth: 200 }}>
                        <option value="month">Theo tháng</option>
                        <option value="quarter">Theo quý</option>
                    </Form.Select>

                    {/* Dropdown chọn tháng/quý */}
                    {viewMode === "month" && (
                        <Form.Select value={month} onChange={handleMonthChange} className="mb-3" style={{ maxWidth: 200 }}>
                            {[...Array(12)].map((_, i) => (
                                <option key={i + 1} value={i + 1}>Tháng {i + 1}</option>
                            ))}
                        </Form.Select>
                    )}

                    {viewMode === "quarter" && (
                        <Form.Select value={quarter} onChange={handleQuarterChange} className="mb-3" style={{ maxWidth: 200 }}>
                            {[1, 2, 3, 4].map((q) => (
                                <option key={q} value={q}>Quý {q}</option>
                            ))}
                        </Form.Select>
                    )}

                    {/* Chọn loại biểu đồ */}
                    <Form.Select value={chartType} onChange={handleChartTypeChange} className="mb-3" style={{ maxWidth: 200 }}>
                        <option value="bar">Biểu đồ cột (Bar)</option>
                        <option value="line">Biểu đồ đường (Line)</option>
                        <option value="pie">Biểu đồ bánh (Pie)</option>
                    </Form.Select>

                    {/* Hiển thị biểu đồ theo loại */}
                    {data.length > 0 ? (
                        <ResponsiveContainer width="100%" height={300}>
                            {chartType === "bar" && (
                                <BarChart data={data}>
                                    <XAxis dataKey="name" />
                                    <YAxis />
                                    <Tooltip />
                                    <Legend />
                                    <Bar dataKey="count" fill={fillColor} />
                                </BarChart>
                            )}
                            {chartType === "line" && (
                                <LineChart data={data}>
                                    <XAxis dataKey="name" />
                                    <YAxis />
                                    <Tooltip />
                                    <Legend />
                                    <Line type="monotone" dataKey="count" stroke={fillColor} />
                                </LineChart>
                            )}
                            {chartType === "pie" && (
                                <PieChart>
                                    <Tooltip />
                                    <Legend />
                                    <Pie
                                        data={data}
                                        dataKey="count"
                                        nameKey="name"
                                        cx="50%"
                                        cy="50%"
                                        outerRadius={100}
                                        fill={fillColor}
                                        label
                                    >
                                        {data.map((entry, index) => (
                                            <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                                        ))}
                                    </Pie>
                                </PieChart>
                            )}
                        </ResponsiveContainer>
                    ) : (
                        <p>Không có dữ liệu.</p>
                    )}
                </Card.Body>
            </Card>
        </Container>
    );
};

export default DoctorStatistics;
