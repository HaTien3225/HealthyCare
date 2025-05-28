import React, { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { authApis, endpoints } from "../configs/Apis";
import { Container, Form, Button, Alert, Pagination, ListGroup } from 'react-bootstrap';
import DatePicker from "react-datepicker";
import { MyUserContext } from "../configs/Contexts";

const HoSoSucKhoeBacSi = () => {
  const { benhNhanId } = useParams();
  const [hoSo, setHoSo] = useState(null);
  const [message, setMessage] = useState("");

  /////////
  const [donKhams, setDonKhams] = useState([]);
  const [startDate, setStartDate] = useState();
  const [page, setPage] = useState(1);
  const [kw, setKw] = useState();
  const navigate = useNavigate();
  const user = useContext(MyUserContext);


  const handleChange = (e) => {
    const { name, value } = e.target;
    setHoSo((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleUpdate = () => {
    if (!hoSo) return;
    setMessage("");


    let updateData = { ...hoSo };
    if (typeof updateData.birth === "string") {
      const [year, month, day] = updateData.birth.split("-").map(Number);
      updateData.birth = [year, month, day];
    }

    authApis()
      .put(endpoints.updateHoSoSucKhoe(benhNhanId), updateData)
      .then(() => {
        setMessage("Cập nhật thành công!");
      })
      .catch(() => {
        setMessage("Lỗi khi cập nhật hồ sơ.");
      });
  };

  const loadDonKham = async (page, kw, date) => {
    if (user != null) {
      try {
        if (page <= 0) {
          page = 1;
          setPage(1);
        }
        let url = `${endpoints.donkhamview}?page=${page}&pageSize=${10}&benhNhanId=${benhNhanId}`;
        if (kw != null && kw != "")
          url += `&kw=${kw}`;
        if (date != null) {
          const fdate = `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
          url += `&date=${fdate}`;
        }
        console.log(url);
        const res = await authApis().get(url);
        setDonKhams(res.data);
        if (donKhams.length === 0)
          setPage(1);
      }
      catch (e) {
        console.log(e);
      }
    }
  }

  useEffect(() => {
    if (benhNhanId) {
      setMessage("");
      authApis()
        .get(endpoints.getHoSoSucKhoe(benhNhanId))
        .then((res) => {
          const data = res.data;


          if (Array.isArray(data.birth)) {
            const [year, month, day] = data.birth;
            data.birth = `${year}-${String(month).padStart(2, "0")}-${String(day).padStart(2, "0")}`;
          }

          setHoSo(data);
        })
        .catch(() => {
          setMessage("Không tìm thấy hồ sơ bệnh nhân.");
          setHoSo(null);
        });
        
    }
  }, [benhNhanId]);

   useEffect(() => {
        loadDonKham(page,kw,startDate);
    }, [page])

  if (!hoSo) return <div>Đang tải dữ liệu...</div>;

  return (
    <>

      <div className="container">
        <Container>
          <h2 className="mb-4">Hồ sơ sức khỏe bệnh nhân</h2>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Chiều cao (cm):</Form.Label>
              <Form.Control
                type="number"
                name="chieuCao"
                value={hoSo.chieuCao || ""}
                onChange={handleChange}
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Cân nặng (kg):</Form.Label>
              <Form.Control
                type="number"
                name="canNang"
                value={hoSo.canNang || ""}
                onChange={handleChange}
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Ngày sinh:</Form.Label>
              <Form.Control
                type="date"
                name="birth"
                value={hoSo.birth || ""}
                onChange={handleChange}
              />
            </Form.Group>

            <Button variant="primary" onClick={handleUpdate}>
              Cập nhật hồ sơ
            </Button>

            {message && <Alert variant="info" className="mt-3">{message}</Alert>}
          </Form>
        </Container>
      </div>

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
      <Form.Group className="mb-3" style={{}}>
        <Form.Label>Chọn ngày</Form.Label>
        <DatePicker
          selected={startDate}
          onChange={(date) => setStartDate(date)}
          className="form-control"
          dateFormat="dd/MM/yyyy"
        />
      </Form.Group>
      <Button style={{ margin: "20px" }} onClick={() => loadDonKham(1, kw, startDate)}>Search</Button>
      <h2>Các đơn khám :</h2>
      <Container style={{ height: "70vh", overflow: "scroll", marginTop: "20px" }}>
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
              <Button onClick={() => navigate(`/chitietdonkhamview/${dk.id}`)}>Xem chi tiết</Button>
            </ListGroup.Item>
          ))}
        </ListGroup>
        {donKhams.length === 0 && <p>Không có đơn khám nào.</p>}
      </Container>
      <div style={{ alignItems: "center", justifyContent: "center", display: "flex", flexDirection: "column" }}>
        <Pagination>
          <Pagination.Prev onClick={() => setPage(page - 1)} />
          <Pagination.Next onClick={() => setPage(page + 1)} />
        </Pagination>
      </div>
    </>
  );
};

export default HoSoSucKhoeBacSi;
