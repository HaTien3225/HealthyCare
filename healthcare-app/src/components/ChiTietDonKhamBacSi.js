import { useContext, useEffect, useState } from "react";
import { Button, Container, ListGroup } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Contexts";
import MySpinner from "./layout/MySpinner";

const ChiTietDonKhamBacSi = () => {

  const { benhNhanId } = useParams();
  const [donKham, setDonKham] = useState([]);
  const [chiTiet, setChiTiet] = useState([]);
  const [xetNghiem,setXetNghiem]= useState([]);
  const [isLoadDK, setIsLoadDK] = useState(true);
  const [isLoadCT, setIsLoadCT] = useState(true);
  const [isLoadXN,setIsLoadXN] = useState(true);
  const user = useContext(MyUserContext);
  const navigate = useNavigate();
  const loadDonKham = async () => {
    try {
      setIsLoadDK(true);
      const res = await authApis().get(endpoints.donkhamview + `/${benhNhanId}`);
      if (res.status != 200)
        alert("load đơn khám " + benhNhanId + " thất bại");
      else {
        setDonKham(res.data);
      }
    } catch (e) {
      console.log(e);
    } finally {
      setIsLoadDK(false);
    }
  }
  const loadChiTiet = async ()=>{
     try {
      setIsLoadCT(true);
      const res = await authApis().get(endpoints.chitietdonkhamview+`/${benhNhanId}`);
      console.log(endpoints.chitietdonkhamview+`/${benhNhanId}`);
      if (res.status != 200)
        alert("load chi tiết thất bại");
      else {
        setChiTiet(res.data);
      }
    } catch (e) {
      console.log(e);
    } finally {
      setIsLoadCT(false);
    }
  }
  const loadXetNghiem = async ()=>{
     try {
      setIsLoadXN(true);
      const res = await authApis().get(endpoints.xetnghiemview+`/${benhNhanId}`);
      console.log(endpoints.xetnghiemview+`/${benhNhanId}`);
      if (res.status != 200)
        alert("load xét nghiệm thất bại");
      else {
        setXetNghiem(res.data);
      }
    } catch (e) {
      console.log(e);
    } finally {
      setIsLoadXN(false);
    }
  }

  useEffect(() => {
    loadDonKham();
    loadChiTiet();
    loadXetNghiem();
  }, [])

  

  { if (user === null) return <h1>Vui lòng đăng nhập</h1> }
  { if (isLoadDK) return <MySpinner/> }

  return (
    <>
    <h3>Đơn khám :</h3>
      <ListGroup style={{ margin: "10px" }}>
        <ListGroup.Item style={lGI}>ID :{donKham.id} </ListGroup.Item>
        <ListGroup.Item style={lGI}>Ghi chú: {donKham.ghiChu}</ListGroup.Item>
        <ListGroup.Item style={lGI}>Bệnh :{donKham.benhId.tenBenh} - {donKham.benhId.moTa}</ListGroup.Item>
        <ListGroup.Item style={lGI}>
          Ngày khám: {donKham.createdDate[2].toString().padStart(2, '0')}/
          {donKham.createdDate[1].toString().padStart(2, '0')}/
          {donKham.createdDate[0]}
        </ListGroup.Item>
   
      </ListGroup>
      <h3>Chi tiết đơn khám :</h3>
      < ListGroup variant="flush">
      {chiTiet.map(ct =>
        <Container key={ct.id} style={{ backgroundColor: "#e0f0ff", padding: "20px", borderRadius: "8px" }}>
          <ListGroup.Item>Nội dung : {ct.dichVu} </ListGroup.Item>
          <ListGroup.Item>Giá tiền : {ct.giaTien.toLocaleString("vi-VN")} VNĐ</ListGroup.Item>
        </Container>
      )}
      {chiTiet.length === 0 && <p>Ko có chi tiết !</p>}
      {isLoadCT && <MySpinner/>} 
      </ListGroup>
      <h3 style={{marginTop: "20px"}}>Các xét nghiệm :</h3>
      < ListGroup variant="flush">
      {xetNghiem.map(x =>
        <Container key={x.id} style={{ backgroundColor: "#ffb8b8", padding: "20px", borderRadius: "8px" }}>
          <ListGroup.Item>Xét nghiệm : {x.moTa}</ListGroup.Item>
        </Container>
      )} 
      </ListGroup>
      {xetNghiem.length === 0 && <p>Ko có xét nghiệm !</p>}
      {isLoadXN && <p>Đang load...</p>} 
    </>
  );
};
const lGI = {
  fontSize: "20px",
  color: 'green',
  marginTop: '10px',
  fontWeight: 'bold'
}

const lGI1 = {
  fontSize: "20px",
  color: 'red',
  marginTop: '10px',
  fontWeight: 'bold'
}
export default ChiTietDonKhamBacSi;
