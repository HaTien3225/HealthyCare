import { useContext, useEffect, useState } from "react";
import { Button, Container, Form, ListGroup, Pagination } from "react-bootstrap";
import { MyUserContext } from "../configs/Contexts";
import Apis, { authApis, endpoints } from "../configs/Apis";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { useNavigate } from "react-router-dom";

const HoSoSucKhoeUser = () => {
    const [hssk, setHssk] = useState([]);
    const [loading, setLoading] = useState(true);
    const user = useContext(MyUserContext);
    const [donKhams, setDonKhams] = useState([]);
    const [startDate, setStartDate] = useState();
    const [page,setPage] = useState(1);
    const [kw,setKw] = useState();
    const navigate = useNavigate();
    const loadHoSo = async () => {
        setLoading(true);
        if (user != null)
            try {
                const res = await authApis().get(endpoints["hososuckhoe"]);
                console.info("res data", res.data);
                setHssk(res.data);
            }
            catch (e) {
                console.log(e.message);
            }
            finally {
                setLoading(false);
            }
    }
    const loadDonKham = async (page,kw,date) => {
        if (user != null) {            
            try {
                if(page <= 0){
                    page=1;
                    setPage(1);
                }
                let url = `${endpoints.donkhamuser}?page=${page}&pageSize=${10}`;
                if(kw != null && kw != "")
                    url += `&kw=${kw}`;
                if(date != null){
                    const fdate = `${date.getFullYear()}-${(date.getMonth()+1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
                    url += `&date=${fdate}`;
                }
                console.log(url);
                const res = await authApis().get(url);
                setDonKhams(res.data);
                if(donKhams.length === 0)
                    setPage(1);
            }
            catch (e) {
                console.log(e);
            }
        }
    }
    

    useEffect(() => {
        loadDonKham(page,kw,startDate);
    }, [page])

    useEffect(()=>{
        loadHoSo();
    },[])

    { if (user === null) return <h1>Vui lòng đăng nhập</h1> }
    if (loading) return <div>Đang tải...</div>;
    if (hssk.length === 0) return (
        <div style={{ alignItems: "center", justifyContent: "center", display: "flex", flexDirection: "column" }}>
            <p style={lGI}>Chưa có hồ sơ súc khỏe...</p>
            <Button onClick={() => navigate("/hososuckhoe-create")}  >Tạo</Button>
        </div>
    )
    return (
        <>
            <h2 style={{ marginTop: "10px", }}>Hồ sơ sức khỏe {user.ho} {user.ten}</h2>
            <ListGroup style={{ margin: "10px" }}>
                <ListGroup.Item style={lGI}>ID :{hssk.id} </ListGroup.Item>
                <ListGroup.Item style={lGI}>Chiều cao : {hssk.chieuCao}</ListGroup.Item>
                <ListGroup.Item style={lGI}>Cân nặng :{hssk.canNang}</ListGroup.Item>
                <ListGroup.Item style={lGI}>
                    Năm sinh: {hssk.birth[2].toString().padStart(2, '0')}/
                    {hssk.birth[1].toString().padStart(2, '0')}/
                    {hssk.birth[0]}
                </ListGroup.Item>
            </ListGroup>
            <Container style={{ alignItems: "center", justifyContent: "center", display: "flex" }}>
                <Form.Label>Search ghi chú :</Form.Label>
                <Form.Control
                    type="text"
                    onChange={(e) => setKw(e.target.value)}
                    id="search"
                    aria-describedby="passwordHelpBlock"
                    style={{ marginTop: "20px", marginBottom: "20px" }}
                />
            
            </Container>
            <Form.Group className="mb-3" style={{  }}>
                <Form.Label>Chọn ngày</Form.Label>
                <DatePicker
                    selected={startDate}
                    onChange={(date) => setStartDate(date)}
                    className="form-control" // Kết hợp với Bootstrap
                    dateFormat="dd/MM/yyyy"
                />
            </Form.Group>
            <Button style={{margin:"20px"}} onClick={() => loadDonKham(1,kw,startDate) }>Search</Button>
            <h2>Các đơn khám :</h2>
            <Container style={{ height: "70vh", overflow: "scroll" ,marginTop:"20px"}}>
                <ListGroup>
                    {donKhams.map(dk => (
                        <ListGroup.Item
                            key={dk.id}
                            variant="success"
                            style={{ marginBottom: "10px", borderRadius: "8px" }}
                        >
                            <p><strong>ID:</strong> {dk.id}</p>
                            <p><strong>Ghi chú:</strong> {dk.ghiChu}</p>
                            <p><strong>Bệnh:</strong> {dk.benhId ? dk.benhId.tenBenh : "Chưa cập nhật"}</p>
                            <p><strong>Ngày tạo:</strong>
                                {dk.createdDate[2].toString().padStart(2, "0") + "/" +
                                    dk.createdDate[1].toString().padStart(2, "0") + "/" +
                                    dk.createdDate[0]}
                            </p>
                            <Button onClick={() => navigate(`/chitietdonkham/${dk.id}`)}>Xem chi tiết</Button>
                        </ListGroup.Item>
                    ))}
                </ListGroup>
                {donKhams.length === 0 && <p>Không có đơn khám nào.</p>}
            </Container>
            <div style={{ alignItems: "center", justifyContent: "center", display: "flex", flexDirection: "column" }}>
                <Pagination>
                    <Pagination.Prev onClick={() => setPage(page - 1)}/>
                    <Pagination.Next onClick={() => setPage(page + 1)}/>
                </Pagination>
            </div>
            
        </>
    );

};
const lGI = {
    fontSize: "20px",
    color: 'green',
    marginTop: '10px',
    fontWeight: 'bold'
}




export default HoSoSucKhoeUser;
