import { Container } from "react-bootstrap";
import { MyUserContext } from "../configs/Contexts";
import { useContext, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";

const ChiTIetLichKhamUser = () => {
    const user = useContext(MyUserContext);
    const [lichKham, setLichKham] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const { id } = useParams();

    const loadLichKham = async () => {
        setIsLoading(true);
        const url = `${endpoints.lichkhamuser}/${id}`;
        const res = await authApis().get(url);
        if (res.status != 200) {
            alert(res);
        }
        setLichKham(res.data);
        setIsLoading(false);
    }
    useEffect(() =>{
        loadLichKham();
    },[])

    if (!user) return <p>Vui lòng đăng nhập</p>
    return (
        
        <>
        {isLoading && <p>Đang load dữ liệu...</p>}
        {!isLoading &&
            <Container style={{ maxHeigh: "70vh", backgroundColor: "#d6eaff", padding: "10px", borderRadius: "8px", marginTop: "20px" }}>
                <strong style={st}>ID:</strong><p style={st}>{lichKham.id} </p>
                <strong style={st}>Thời gian:</strong><p style={st}>{lichKham.khungGio.tenKg} </p>
                <strong style={st}>Ngày khám:</strong><p style={st}>{lichKham.ngay[2].toString().padStart(2, "0") + "/" +
                    lichKham.ngay[1].toString().padStart(2, "0") + "/" +
                    lichKham.ngay[0]}</p>
                <strong style={st}>Bác sĩ:</strong> <p style={st}>{lichKham.bacSiId.ho} {lichKham.bacSiId.ten}</p> 
                <strong style={st}>Bệnh viện:</strong><p style={st}> {lichKham.bacSiId.khoaId.benhvien.tenBenhVien}</p> 
                <strong style={st}>Khoa :</strong> <p style={st}>{lichKham.bacSiId.khoaId.tenKhoa} </p> 
                <strong style={st}>Email bác sĩ :</strong><p style={st}>{lichKham.bacSiId.email}</p>
                {lichKham.isAccept && <><strong style={st}>Đã chấp nhận</strong><br/> </>}
                {!lichKham.isAccept && <><strong style={st}>Chưa chấp nhận</strong><br/> </>}
                {lichKham.daKham && <><strong style={st}>Đã khám</strong> <br/></>}
                {!lichKham.daKham && <><strong style={st}>Chưa khám</strong><br/> </>}
                <strong style={st}>-----------------------------------------</strong><br/>
                <strong style={st}>Khung giờ chi tiết: </strong><p style={st}>{lichKham.khungGio.gioBatDau[0]+":"+lichKham.khungGio.gioBatDau[1]}h đến {lichKham.khungGio.gioKetThuc[0]+":"+lichKham.khungGio.gioKetThuc[1]}h</p>
                <strong style={st}>Địa chỉ bệnh viện : </strong><p style={st}>{lichKham.bacSiId.khoaId.benhvien.diaChi}</p>
            </Container>
        }
        </>
    );
};

const st = {
    fontSize: "20px",
}

export default ChiTIetLichKhamUser;
