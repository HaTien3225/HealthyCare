import { useContext, useEffect, useState } from "react";
import { Button, Container } from "react-bootstrap";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Contexts";
import MySpinner from "./layout/MySpinner";
import { useNavigate } from "react-router-dom";

const DanhGiaDoctor = () => {
    const [danhGias, setDanhGias] = useState([]);
    const user = useContext(MyUserContext);
    const [isLoading, setIsLoading] = useState(true);
    const navigate = useNavigate();

    const loadDanhGias = async () => {
        try {
            let url = `${endpoints.danhgiadoctor}`;
            const res = await authApis().get(url);
            setDanhGias(res.data);
            console.log(res.data);
        } catch (e) {
            console.error(e.response?.data || e.message);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        loadDanhGias();
    }, []);

    if (!user) return <h1>Vui lòng đăng nhập</h1>;

    return (
        <>
            <h3>Các đánh giá chưa phản hồi:</h3>
            {isLoading && <MySpinner />}
            {!isLoading && (
                <Container
                    style={{
                        height: "70vh",
                        overflow: "scroll",
                        backgroundColor: "#fef0d9",
                        boxShadow: "0px 0px 40px 1px black",
                        marginTop: "30px",
                        padding: "20px"
                    }}
                >
                    {danhGias.map((d) => (
                        <Container
                            key={d.id}
                            style={{
                                backgroundColor: "#fdd9a0",
                                padding: "10px",
                                marginTop: "20px"
                            }}
                        >
                            <p><strong>Đánh giá ID:</strong> {d.id}</p>
                            <p><strong>Người đánh giá:</strong> {d.benhNhanId.ho} {d.benhNhanId.ten}</p>
                            <p><strong>Ngày khám:</strong> {d.lichKhamId.ngay[2].toString().padStart(2, '0')}/
                                {d.lichKhamId.ngay[1].toString().padStart(2, '0')}/
                                {d.lichKhamId.ngay[0]}</p>
                            <p><strong>Nội dung đánh giá:</strong> {d.binhLuan}</p>
                            <Button
                                variant="warning"
                                onClick={() => navigate("/danhgiareply/" + d.id)}
                            >
                                Phản hồi
                            </Button>
                        </Container>
                    ))}
                    {danhGias.length === 0 && <p>Không có đánh giá nào cần phản hồi</p>}
                </Container>
            )}
        </>
    );
};

export default DanhGiaDoctor;
