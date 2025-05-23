import { useContext, useEffect, useState } from "react";
import { Button, Container } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Contexts";
import MySpinner from "./layout/MySpinner";

const DanhGiaUser = () => {

    const [danhGias, setDanhGias] = useState([]);
    const user = useContext(MyUserContext);
    const [isLoading,setIsLoading] = useState(true);

    const loadDanhGias = async () => {

        try {
            let url = `${endpoints.danhgiauser}?isBinhLuan=false&isPhanHoi=false`;
            const res = await authApis().get(url);
            setDanhGias(res.data);
            console.log(res.data);
            console.log(url);
        } catch (e) {
            console.log(e.response.data);
        }
        finally{
            setIsLoading(false);
        }
    }

    useEffect(()=>{
        loadDanhGias();
    },[])

    if(!user) return <h1>Vui lòng đăng nhập</h1>

    return (
        <>
            <h3>Các lịch khám chưa đánh giá :</h3>
            {isLoading && <MySpinner/>}
            {!isLoading && <Container style={{ height: "70vh", overflow: "scroll", backgroundColor: "#d6f3ff", boxShadow: "0px 0px 40px 1px black", marginTop: "30px", padding: "20px" }}>
                {danhGias.map(d =>
                    <Container key={d.id} style={{ backgroundColor: "#b0e9ff", padding: "10px", marginTop: "20px" }}>
                        <p><strong>Lịch khám ID : </strong> {d.id}</p>
                        <p><strong>Ngày : </strong>{d.lichKhamId.ngay[2].toString().padStart(2, '0')}/
                            {d.lichKhamId.ngay[1].toString().padStart(2, '0')}/
                            {d.lichKhamId.ngay[0]}</p>
                        <p><strong>Bác sĩ : </strong>{d.bacSiId.ho} {d.bacSiId.ten}</p>
                        <Button variant="info" >Đánh giá</Button>
                    </Container>)}
                {danhGias.length === 0 && <p>Không có lịch khám chưa đánh giá</p>}
            </Container>}
        </>
    );
};

export default DanhGiaUser;
