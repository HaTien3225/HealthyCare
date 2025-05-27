import { useContext, useEffect, useState } from "react";
import { Button, ButtonGroup, Container, ListGroup, Pagination, ToggleButton } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import { MyUserContext } from "../configs/Contexts";
import MySpinner from "./layout/MySpinner";


const LichKhamUser = () => {
    const [chuaChapNhan, setChuaChapNhan] = useState(false);
    const [daKham, setDaKham] = useState(false);
    const [daChapNhanNChuaKham, setDaChapNhanNChuaKham] = useState(true);

    const [isLoading, setIsLoading] = useState(true);
    const [lichKham, setIsLichKham] = useState([]);
    const navigate = useNavigate();
    const [page, setPage] = useState(1);
    const user = useContext(MyUserContext);

    const [c, setC] = useState(1);


    const loadLichKham = async () => {
        setIsLoading(true);
        if (page <= 0)
            setPage(1);
        let url = `${endpoints.lichkhamuser}?page=${page}`;
        try {
            if (chuaChapNhan)
                url += `&isAccept=false`;
            else if (daKham) {
                url += `&daKham=true`;
            } else if (daChapNhanNChuaKham) {
                url += `&isAccept=true&daKham=false`
            }

            const res = await authApis().get(url);
            if (res.status != 200)
                alert(res.response.data);

            if(res.data.length === 0)
                setPage(1);
            setIsLichKham(res.data);           
        } catch (e) {
            console.log(e);
        } finally {
            setIsLoading(false);
        }
    }

    useEffect(() => {
        loadLichKham();
    }, [c,page])

    const choicehandle = (choice) => {
        if (choice === 1) {
            setDaChapNhanNChuaKham(true);
            setChuaChapNhan(false);
            setDaKham(false);
            setC(1);
        }
        if (choice === 2) {
            setDaChapNhanNChuaKham(false);
            setChuaChapNhan(false);
            setDaKham(true);
            setC(2);
        }
        if (choice === 3) {
            setDaChapNhanNChuaKham(false);
            setChuaChapNhan(true);
            setDaKham(false);
            setC(3);
        }
    }
     if(!user )return (<p>Vui lòng đăng nhập</p>);

    return (
        <>
            <Container className="mt-4">
                <h2>Xem lịch khám</h2>

                <ButtonGroup className="mb-2">
                    <ToggleButton
                        type="checkbox"
                        variant={daChapNhanNChuaKham ? "primary" : "outline-primary"}
                        checked={daChapNhanNChuaKham}
                        value="1"
                        onClick={() => choicehandle(1)}
                    >
                        Đã chấp nhận, chưa khám
                    </ToggleButton>

                    <ToggleButton
                        type="checkbox"
                        variant={daKham ? "success" : "outline-success"}
                        checked={daKham}
                        value="2"
                        onClick={() => choicehandle(2)}
                    >
                        Đã khám
                    </ToggleButton>
                    <ToggleButton
                        type="checkbox"
                        variant={chuaChapNhan ? "warning" : "outline-warning"}
                        checked={chuaChapNhan}
                        value="2"
                        onClick={() => choicehandle(3)}
                    >
                        Chưa chấp nhận
                    </ToggleButton>
                </ButtonGroup>

                <Container style={{ height: "70vh", overflowY: "scroll", backgroundColor: "#e6f0ff", padding: "10px", borderRadius: "8px", marginTop: "20px" }}>
                    {lichKham.length === 0 && <p>Không có lịch khám</p>}
                    {isLoading && <MySpinner />}
                    {!isLoading && <ListGroup>
                        {lichKham.map( lk =>
                            <ListGroup.Item key={lk.id} style={{ backgroundColor: "#d6eaff", border: "1px solid #b0d4f1", borderRadius: "6px", marginBottom: "10px" }}>
                                <strong>Thời gian:</strong> {lk.khungGio.tenKg} <br />
                                <strong>Ngày khám:</strong>{lk.ngay[2].toString().padStart(2, "0") + "/" +
                                    lk.ngay[1].toString().padStart(2, "0") + "/" +
                                    lk.ngay[0]}<br/>
                                <strong>Bác sĩ:</strong> {lk.bacSiId.ho} {lk.bacSiId.ten} <br />
                                <strong>Bệnh viện:</strong> {lk.bacSiId.khoaId.benhvien.tenBenhVien} <br />
                                <strong>Khoa :</strong> {lk.bacSiId.khoaId.tenKhoa}  <br />
                                {lk.isAccept && <><strong>Đã chấp nhận</strong> <br /></>}
                                {!lk.isAccept && <><strong>Chưa chấp nhận</strong> <br /></>}
                                {lk.daKham && <><strong>Đã khám</strong> <br /></>}   
                                {!lk.daKham && <><strong>Chưa khám</strong> <br /></>}  
                                <Button variant="info" onClick={() => navigate(`/lichkhamuser/${lk.id}`)}>Chi tiết</Button>
                            </ListGroup.Item>
                        )}
                    </ListGroup>}
                </Container>
                <Container style={{ justifyContent: "center", alignItems: "center", display: "flex" }}>
                    <Pagination>
                        <Pagination.Prev onClick={() => setPage(prev => prev -1)}/>
                        <Pagination.Next  onClick={() => setPage(prev => prev +1)}/>
                    </Pagination>
                </Container>
            </Container>
        </>
    );
};

export default LichKhamUser;
