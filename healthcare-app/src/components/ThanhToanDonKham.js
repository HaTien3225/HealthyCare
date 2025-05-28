import { useContext, useEffect, useState } from "react";
import { Button, Container, ListGroup, Pagination } from "react-bootstrap";
import { MyUserContext } from "../configs/Contexts";
import { useNavigate } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import MySpinner from "./layout/MySpinner";

const ThanhToanDonKham = () => {


    const [loading, setLoading] = useState(true);
    const user = useContext(MyUserContext);
    const [donKhams, setDonKhams] = useState([]);

    const [page, setPage] = useState(1);
    const navigate = useNavigate();

    const loadDonKham = async (page) => {
        setLoading(true);
        // if (user != null) {
            try {
                if (page <= 0) {
                    page = 1;
                    setPage(1);
                }
                let url = `${endpoints.donkhamuser}?page=${page}&pageSize=${10}&isPaid=false`;

                console.log(url);
                const res = await authApis().get(url);
                setDonKhams(res.data);
                if (donKhams.length === 0)
                    setPage(1);
            }
            catch (e) {
                console.log(e);
            }finally{
                setLoading(false);
            }
        // }
    }

    useEffect(() => {
        loadDonKham(page);
    }, [page])
    if(!user)return<p>Vui lòng đang nhập</p>
    return (
        <>
            <h2>Các đơn khám chưa thanh toán:</h2>
            {loading && <MySpinner/>}
            {!loading && <Container style={{ height: "70vh", overflow: "scroll", marginTop: "20px" }}>
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
                            <p><strong style={{ color: "red" }}>Chưa thanh toán</strong> </p>
                            <Button onClick={() => navigate(`/chitietdonkham/${dk.id}`)}>Xem chi tiết</Button>
                        </ListGroup.Item>
                    ))}
                </ListGroup>
                {donKhams.length === 0 && <p>Đã thanh toán hết các đơn khám.</p>}
            </Container>}
            <div style={{ alignItems: "center", justifyContent: "center", display: "flex", flexDirection: "column" }}>
                <Pagination>
                    <Pagination.Prev onClick={() => setPage(page - 1)} />
                    <Pagination.Next onClick={() => setPage(page + 1)} />
                </Pagination>
            </div>
        </>
    );
};

export default ThanhToanDonKham;