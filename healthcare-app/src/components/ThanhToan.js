import { Button, Container, Form } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import { MyUserContext } from "../configs/Contexts";
import { useContext, useEffect, useState } from "react";
import { authApis, endpoints } from "../configs/Apis";



const ThanhToan = () => {

    const { id } = useParams();
    const user = useContext(MyUserContext);
    const navigate = useNavigate();
    const [thanhToanInfo,setThanhToanInfo] = useState([]);
    const [isLoad,setIsLoad] = useState(true);

    const loadThanhToan = async () => {
        // if(user != null){
            try{
                const res = await authApis().get(endpoints.thanhtoan +`?donKhamId=${id}`);
                setThanhToanInfo(res.data);
                console.info(res.data);               
            }
            catch(e){
                console.log(e);
            }
            finally{
                setIsLoad(false);
            }
        // }
    }

    useEffect(() =>{
        loadThanhToan();
    },[])

    const handlePayment = () => {

        window.open(thanhToanInfo.vnPayURL, "_blank");
    };
    if(user === null){
        return(<p>Vui lòng đăng nhập</p>);
    }
    return (
        <>
        { !isLoad && <Container style={{ backgroundColor: "#e3f2fd", padding: "20px", borderRadius: "8px", marginTop: "30px" }}>
            <h2>Thông tin thanh toán</h2>
            <Form>
                <Form.Group className="mb-3">
                    <Form.Label>Mã đơn khám : {thanhToanInfo.donKhamId}</Form.Label>               
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Ghi chú : {thanhToanInfo.ghiChu}</Form.Label>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Tổng tiền : {thanhToanInfo.totalPrice.toLocaleString("vi-VN")} VNĐ</Form.Label>
                </Form.Group>
                <Button variant="primary" onClick={handlePayment}>
                    Thanh toán VNPay
                </Button>
            </Form>
        </Container>}
        {isLoad && <p>Đang load</p>}
        </>
    );
};

export default ThanhToan;
