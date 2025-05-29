import { useContext, useEffect, useState } from "react";
import { Button, Card, Col, Container, Form, ListGroup, Row } from "react-bootstrap";
import DatePicker from "react-datepicker";
import { MyUserContext } from "../configs/Contexts";
import { useNavigate } from "react-router-dom";
import Apis, { authApis, endpoints } from "../configs/Apis";
import MySpinner from "./layout/MySpinner";

const DatLichKhamUser = () => {
    const user = useContext(MyUserContext);
    const navigate = useNavigate();
    const [benhViens, setBenhViens] = useState([]);
    const [benhVienId, setBenhVienId] = useState(-1);
    const [khoas, setKhoas] = useState([]);
    const [khoaId, setKhoaId] = useState(-1);
    const [lichTrong, setLichTrong] = useState([]);
    const [bacSis, setBacSis] = useState([]);
    const [bacSiId, setBacSiId] = useState(-1);
    const [bacSiHT, setBacSiHT] = useState("");
    const [khungGio, setKhungGio] = useState([]);
    const [khungGioId, setKhungGioId] = useState(-1);
    const [isLoading, setIsLoading] = useState(false);

    const [date, setDate] = useState();

    const loadBenhViens = async () => {
        const res = await Apis.get(endpoints.bennvienuser);
        setBenhViens(res.data);
    }

    const loadKhoas = async () => {
        setBacSis([]);
        if (benhVienId === -1) {
            setKhoas([]);
        } else {
            const res = await Apis.get(endpoints.khoauser + "?benhVienId=" + benhVienId);
            setKhoas(res.data);
        }

    }

    const loadBacSis = async () => {
        if (khoaId === -1)
            setBacSis([]);
        else {
            const res = await Apis.get(endpoints.bacsiuser + "?khoaId=" + khoaId);
            setBacSis(res.data);
        }
    }
    const handleOnClickChooseBacSi = (id, ho, ten) => {
        setBacSiId(id);
        setBacSiHT(ho + " " + ten);
    }

    const loadLichBacSi = async () => {
        if (bacSiId === -1 || date == null)
            setLichTrong([]);
        else {
            const res = await Apis.get(endpoints.khunggiotrong + "/" + bacSiId + "?ngay=" + `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`);
            setLichTrong(res.data);
        }
    }

    const loadKhungGios = async () => {
        const res = await Apis.get(endpoints.khunggio);
        setKhungGio(res.data);
    }

    const createLichKham = async () => {
        if (khungGioId === -1 || bacSiId === -1) {
            alert("Chưa chọn bác sĩ hoặc khung giờ");
        }
        else {
            setIsLoading(true);
            try {
                const Data = {
                    'ngay': `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`,
                    'isAccept': 'false',
                    'daKham': 'false',
                }
                const res = await authApis().post(`${endpoints.lichkhamuser}?bacSiId=${bacSiId}&khungGioId=${khungGioId}`, Data);
                if (res.status === 200) {
                    alert("Tạo lịch thành công");
                    navigate("/lichkhamuser");
                }
            } catch (e) {
                if (e.response) {
                    alert(e.response.data);
                }
            } finally {
                setIsLoading(false);
            }
        }
    }

    useEffect(() => {
        loadBenhViens();
        loadKhungGios();
    }, [])

    useEffect(() => {
        loadKhoas();
    }, [benhVienId])

    useEffect(() => {
        loadBacSis();
    }, [khoaId])

    useEffect(() => {
        loadLichBacSi();
    }, [date, bacSiId])



    if (!user) return <h1>Vui lòng đăng nhập</h1>
    return (
        <>
            <h2>Đặt lịch khám</h2>
            <Container style={{ backgroundColor: "#d6eaff", padding: "20px" }}>
                <h3>Chọn bệnh viện</h3>
                <Form.Select aria-label="Default select example" onChange={(e) => setBenhVienId(e.target.value)} value={benhVienId}>
                    <option value="-1" >Chọn bệnh viện </option>
                    {benhViens.map(bv =>
                        <option key={bv.id} value={bv.id} >{bv.tenBenhVien}</option>
                    )}
                </Form.Select>
            </Container>
            <Container style={{ backgroundColor: "#d6eaff", marginTop: "20px", padding: "20px", marginBottom: "20px" }}>
                <h3>Chọn khoa</h3>
                <Form.Select aria-label="Default select example" onChange={(e) => setKhoaId(e.target.value)} value={khoaId}>
                    <option value="-1" >Chọn khoa</option>
                    {khoas.map(k =>
                        <option key={k.id} value={k.id}  >{k.tenKhoa}</option>
                    )}
                </Form.Select>
            </Container>
            <h3>Các bác sĩ</h3>
            <Container style={{ backgroundColor: "#d6eaff", marginTop: "20px", padding: "20px", maxHeight: "100vh", overflowY: "scroll" }}>

                <Row>
                    {bacSis
                        .filter(bs => bs.giayPhepHanhNgheId && bs.giayPhepHanhNgheId.isValid)
                        .map(bs => (
                            <Col key={bs.id} md={4} sm={6} xs={12} className="mb-4">
                                <Card style={{ width: '18rem' }}>
                                    {!bs.avatar && <Card.Img variant="top" src="/default-avatar.png" />}
                                    {bs.avatar && <Card.Img variant="top" src={bs.avatar} />}
                                    <Card.Body>
                                        <Card.Title>{bs.ho} {bs.ten}</Card.Title>
                                        <Card.Text>
                                            {bs.khoaId.moTa}
                                        </Card.Text>
                                        <Button variant="primary" onClick={() => handleOnClickChooseBacSi(bs.id, bs.ho, bs.ten)}>
                                            Chọn bác sĩ
                                        </Button>
                                    </Card.Body>
                                </Card>
                            </Col>
                        ))}
                </Row>

            </Container>
            <Container style={{ backgroundColor: "#d6eaff", padding: "20px" }}>
                <h3>Bác sĩ đã chọn : {bacSiHT}  </h3>
                <Form.Group className="mb-3">
                    <Form.Label>Chọn ngày : </Form.Label>
                    <DatePicker
                        selected={date}
                        onChange={(date) => setDate(date)}
                        dateFormat="dd/MM/yyyy"
                        className="form-control"
                    />
                </Form.Group>
                <h3>Lịch của bác sĩ vào ngày : </h3>
                <ListGroup variant="flush">
                    {Object.entries(lichTrong).map(([khungGio, isBooked], idx) => (
                        <ListGroup.Item key={idx}>
                            {khungGio} - {isBooked ? 'Đã có lịch' : 'Còn trống'}
                        </ListGroup.Item>
                    ))}
                </ListGroup>
            </Container>
            <Container style={{ backgroundColor: "#d6eaff", marginTop: "20px", padding: "20px", marginBottom: "20px" }}>
                <h3>Chọn khung giờ</h3>
                <Form.Select aria-label="Default select example" selected={khungGioId} onChange={(e) => setKhungGioId(e.target.value)}>
                    <option value={-1}>Chọn khung giờ</option>
                    {khungGio.map(kg =>
                        <option key={kg.id} value={kg.id}>{kg.tenKg}</option>
                    )}
                </Form.Select>
            </Container>
            {isLoading && <Button variant="warning" disabled>Tạo lịch</Button>}
            {!isLoading && <Button variant="warning" onClick={createLichKham}>Tạo lịch</Button>}
            {isLoading && <MySpinner />}
        </>
    );
};

export default DatLichKhamUser;
