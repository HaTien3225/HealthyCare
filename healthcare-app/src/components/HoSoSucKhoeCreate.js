import { useContext, useState } from "react";
import { Button, Container, Form } from "react-bootstrap";
import DatePicker from "react-datepicker";
import { useNavigate } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Contexts";


const HoSoSucKhoeCreate = () => {
    const [chieuCao, setChieuCao] = useState();
    const [canNang, setCanNang] = useState();
    const [birth, setBirth] = useState();
    const navigate = useNavigate();
    const user = useContext(MyUserContext); 

    const createHoSo = async () => {
        if(chieuCao === null || canNang === null || birth === null )
            alert("Chưa điền đủ thông tin");
        else{
            const data = {
                'chieuCao':chieuCao,
                'canNang':canNang,
                'birth':`${birth.getFullYear()}-${(birth.getMonth()+1).toString().padStart(2, '0')}-${birth.getDate().toString().padStart(2, '0')}`
            }
            const res = await authApis().post(endpoints.hososuckhoe,data);
            if(res.status === 201){
                alert("Tạo hồ sơ sức khỏe thành công");
                navigate("/hososuckhoe");
            }else{
                alert("Lỗi ! tạo hồ sơ sức khỏe thất bại");
                navigate("/hososuckhoe");
            }
        }
    }

    return (
        <Container style={{ maxWidth: '500px', marginTop: '30px' }}>
            <h3>Tạo Hồ Sơ Sức Khỏe</h3>
            <Form >
                <Form.Group className="mb-3">
                    <Form.Label>Chiều cao (cm)</Form.Label>
                    <Form.Control
                        type="number"
                        value={chieuCao}
                        onChange={(e) => setChieuCao(e.target.value)}
                        placeholder="Nhập chiều cao"
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Cân nặng (kg)</Form.Label>
                    <Form.Control
                        type="number"
                        value={canNang}
                        onChange={(e) => setCanNang(e.target.value)}
                        placeholder="Nhập cân nặng"
                    />
                </Form.Group>

                <Form.Group className="mb-3">
                    <Form.Label>Ngày sinh</Form.Label>
                    <DatePicker
                        selected={birth}
                        onChange={(date) => setBirth(date)}
                        dateFormat="dd/MM/yyyy"
                        className="form-control"
                    />
                </Form.Group>

                <Button variant="primary" onClick={createHoSo} >
                    Tạo
                </Button>
            </Form>
        </Container>
    );
};
const lGI = {
    fontSize: "20px",
    color: 'green',
    marginTop: '10px',
    fontWeight: 'bold'
}




export default HoSoSucKhoeCreate;
