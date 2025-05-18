import { Button, Container } from "react-bootstrap";
import { MyUserContext } from "../configs/Contexts";
import { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import MySpinner from "./layout/MySpinner";

const ChiTIetLichKhamUser = () => {
    const user = useContext(MyUserContext);
    const [lichKham, setLichKham] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const { id } = useParams();
    const navigator = useNavigate();


    const [isDeleting, setIsDeleting] = useState(false);

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
    useEffect(() => {
        loadLichKham();
    }, [])

    const deleteLichKham = async () => {
        setIsDeleting(true);
        try {
            const res = await authApis().delete(endpoints.lichkhamuser + "/" + lichKham.id);

            if (res.status === 200) {
                alert("Hủy lịch khám thành công !");
                navigator("/lichkhamuser");
            }
        } catch (e) {
            if (e.response) {
                alert(e.response.data);
            }

        } finally {
            setIsDeleting(false);
        }

    }

    if (!user) return <p>Vui lòng đăng nhập</p>
    return (

        <>
            {isLoading && <MySpinner />}
            {!isLoading &&
                <Container style={{ maxHeigh: "70vh", backgroundColor: "#d6eaff", padding: "10px", borderRadius: "8px", marginTop: "20px" }}>
                    <p style={st}><strong >ID:</strong>{lichKham.id} </p>
                    <p style={st}><strong >Thời gian:</strong>{lichKham.khungGio.tenKg} </p>
                    <p style={st}><strong >Ngày khám:</strong>{lichKham.ngay[2].toString().padStart(2, "0") + "/" +
                        lichKham.ngay[1].toString().padStart(2, "0") + "/" +
                        lichKham.ngay[0]}</p>
                    <p style={st}><strong >Bác sĩ:</strong> {lichKham.bacSiId.ho} {lichKham.bacSiId.ten}</p>
                    <p style={st}><strong >Bệnh viện:</strong>{lichKham.bacSiId.khoaId.benhvien.tenBenhVien}</p>
                    <p style={st}><strong >Khoa :</strong> {lichKham.bacSiId.khoaId.tenKhoa} </p>
                    <p style={st}><strong >Email bác sĩ :</strong>{lichKham.bacSiId.email}</p>
                    {lichKham.isAccept && <><strong style={st}>Đã chấp nhận</strong><br /> </>}
                    {!lichKham.isAccept && <><strong style={st}>Chưa chấp nhận</strong><br /> </>}
                    {lichKham.daKham && <><strong style={st}>Đã khám</strong> <br /></>}
                    {!lichKham.daKham && <><strong style={st}>Chưa khám</strong><br /> </>}
                    <strong >-----------------------------------------</strong><br />
                    <p style={st}><strong >Khung giờ chi tiết: </strong>{lichKham.khungGio.gioBatDau[0] + ":" + lichKham.khungGio.gioBatDau[1]}h đến {lichKham.khungGio.gioKetThuc[0] + ":" + lichKham.khungGio.gioKetThuc[1]}h</p>
                    <p style={st}><strong >Địa chỉ bệnh viện : </strong>{lichKham.bacSiId.khoaId.benhvien.diaChi}</p>
                    {!lichKham.daKham && <Button variant="danger" onClick={deleteLichKham}>Hủy lịch khám</Button>}
                    {isDeleting && <MySpinner />}
                    <div className="mt-3">
                        <Button
                            variant="success"
                            href={`/tuvan/${lichKham.id}`}
                            target="_blank"
                        >
                            Video Call
                        </Button>
                    </div>

                    <div className="mt-3">
                        <button
                            className="btn btn-info"
                            onClick={() => {
                                const otherUserId = user.role === "ROLE_DOCTOR" ? lichKham.benhNhanId.id : lichKham.bacSiId.id;
                                navigator(`/doctor/chat/${otherUserId}`);
                            }}
                        >
                            Chat
                        </button>
                    </div>
                </Container>
            }
        </>
    );
};

const st = {
    fontSize: "30px",
}

export default ChiTIetLichKhamUser;
