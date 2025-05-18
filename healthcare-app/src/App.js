import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/layout/Header";
import Footer from "./components/layout/Footer";
import Home from "./components/Home";
import { Container } from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import Register from "./components/Register";
import Login from "./components/Login";
import { MyDispatchContext, MyUserContext } from "./configs/Contexts";
import { useEffect, useReducer } from "react";
import MyUserReducer from "./reducers/MyUserReducer";
import DoctorHome from "./components/DoctorHome";
import UserHome from "./components/UserHome";
import ProFileUser from "./components/ProFileUser";
import PendingLichKham from "./components/PendingLichKham";
import AcceptedLichKham from "./components/AcceptedLichKham";
import CreateDonKham from "./components/CreateDonKham.js";
import AddBenh from "./components/AddBenh.js";
import AddXetNghiem from "./components/AddXetNghiem.js";
import HoSoSucKhoeUser from "./components/HoSoSucKhoeUser.js";
import HoSoSucKhoeCreate from "./components/HoSoSucKhoeCreate.js";
import ChiTietDonKham from "./components/ChiTietDonKham.js";
import ThanhToan from "./components/ThanhToan.js";
import HoSoBenhNhan from "./components/HoSoBenhNhan.js";
import DoctorStatistics from "./components/DoctorStatistics.js";
import DoctorTuvan from "./components/DoctorTuvan.js";
import ChatComponent from "./components/ChatComponent.js";
import LichKhamUser from "./components/LichKhamUser.js";
import ChiTIetLichKhamUser from "./components/ChiTietLichKhamUser.js";


const App = () => {
  const [user, dispatch] = useReducer(MyUserReducer, null);

  useEffect(() => {
    const token = localStorage.getItem("token");
    const savedUser = localStorage.getItem("user");

    if (token && savedUser) {
      dispatch({
        type: "login",
        payload: JSON.parse(savedUser)
      });
    }
  }, []);


  return (
    <MyUserContext.Provider value={user}>
      <MyDispatchContext.Provider value={dispatch}>
        <BrowserRouter>
          <Header />

          <Container>
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/register" element={<Register />} />
              <Route path="/login" element={<Login />} />
              <Route path="/doctor" element={<DoctorHome />} />
              <Route path="/user" element={<UserHome />} />
              <Route path="/profile" element={<ProFileUser />} />
              <Route path="/doctor/pending" element={<PendingLichKham />} />
              <Route path="/doctor/accepted" element={<AcceptedLichKham />} />
              <Route path="/doctor/lichkham/:lichKhamId/taodon" element={<CreateDonKham />} />
              <Route path="/doctor/donkham/:donKhamId/thembenh" element={<AddBenh />} />
              <Route path="/doctor/donkham/:donKhamId/themxetnghiem" element={<AddXetNghiem />} />
              <Route path="/doctor/hosobenhnhan/:benhNhanId" element={<HoSoBenhNhan />} />
              <Route path="/doctor/thongke" element={<DoctorStatistics/>} />
              <Route path="/tuvan/:lichKhamId" element={<DoctorTuvan />} />
              <Route path="/doctor/chat/:id" element={<ChatComponent />} />


              {/*user */}
              <Route path="/hososuckhoe" element={<HoSoSucKhoeUser/>}/>
              <Route path="/hososuckhoe-create" element={<HoSoSucKhoeCreate/>}/>
              <Route path="/chitietdonkham/:id" element={<ChiTietDonKham />}/>
              <Route path="/thanhtoan/:id" element={<ThanhToan />}/>
              <Route path="/lichkhamuser" element={<LichKhamUser/>}/>
              <Route path="/lichkhamuser/:id" element={<ChiTIetLichKhamUser/>}/>
              
            </Routes>
          </Container>

          <Footer />
        </BrowserRouter>
      </MyDispatchContext.Provider>
    </MyUserContext.Provider>
  );
}

export default App;
