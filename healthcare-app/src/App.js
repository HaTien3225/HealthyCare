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
import DoctorStatistics from "./components/DoctorStatistics.js";
import DoctorTuvan from "./components/DoctorTuvan.js";
import ChatComponent from "./components/ChatComponent.js";
import LichKhamUser from "./components/LichKhamUser.js";
import ChiTIetLichKhamUser from "./components/ChiTietLichKhamUser.js";
import ThanhToanDonKham from "./components/ThanhToanDonKham.js";
import HoSoSucKhoeBacSi from "./components/HoSoSucKhoeBacSi.js";
import DatLichKhamUser from "./components/DatLichKhamUser.js";
import BenhManageDoctor from "./components/BenhManageDoctor.js";
import DanhGiaUser from "./components/DanhGiaUser.js";
import UploadLicense from "./components/UploadLicense.js";
import DanhGiaCreate from "./components/DanhGiaCreate.js";
import DanhGiaView from "./components/DanhGiaView.js";
import DanhGiaDoctor from "./components/DanhGiaDoctor.js";
import DanhGiaReply from "./components/DanhGiaReply.js";
import ChiTietDonKhamBacSi from "./components/ChiTietDonKhamBacSi.js";
import ProtectedRoute from "./configs/ProtectedRoute.js";
import Assitant from "./components/Assitant.js";




const App = () => {
  const storedUser = localStorage.getItem("user");
  const initialUser = storedUser ? JSON.parse(storedUser) : null;

  const [user, dispatch] = useReducer(MyUserReducer, initialUser);


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

              {/* Doctor Routes */}
              <Route path="/doctor" element={
                <ProtectedRoute allowedRoles={["ROLE_DOCTOR"]}>
                  <DoctorHome />
                </ProtectedRoute>
              } />
              <Route path="/doctor/pending" element={
                <ProtectedRoute allowedRoles={["ROLE_DOCTOR"]}>
                  <PendingLichKham />
                </ProtectedRoute>
              } />
              <Route path="/doctor/accepted" element={
                <ProtectedRoute allowedRoles={["ROLE_DOCTOR"]}>
                  <AcceptedLichKham />
                </ProtectedRoute>
              } />
              <Route path="/doctor/lichkham/:lichKhamId/taodon/:benhNhanId" element={
                <ProtectedRoute allowedRoles={["ROLE_DOCTOR"]}>
                  <CreateDonKham />
                </ProtectedRoute>
              } />
              <Route path="/doctor/donkham/:donKhamId/thembenh" element={
                <ProtectedRoute allowedRoles={["ROLE_DOCTOR"]}>
                  <AddBenh />
                </ProtectedRoute>
              } />
              <Route path="/doctor/donkham/:donKhamId/themxetnghiem" element={
                <ProtectedRoute allowedRoles={["ROLE_DOCTOR"]}>
                  <AddXetNghiem />
                </ProtectedRoute>
              } />
              <Route path="/doctor/hosobenhnhan/:benhNhanId" element={
                <ProtectedRoute allowedRoles={["ROLE_DOCTOR"]}>
                  <HoSoSucKhoeBacSi />
                </ProtectedRoute>
              } />
              <Route path="/doctor/thongke" element={
                <ProtectedRoute allowedRoles={["ROLE_DOCTOR"]}>
                  <DoctorStatistics />
                </ProtectedRoute>
              } />
              <Route path="/tuvan/:lichKhamId" element={
                <ProtectedRoute allowedRoles={["ROLE_DOCTOR"]}>
                  <DoctorTuvan />
                </ProtectedRoute>
              } />
              <Route path="/doctor/chat/:id" element={
                <ProtectedRoute allowedRoles={["ROLE_DOCTOR"]}>
                  <ChatComponent />
                </ProtectedRoute>
              } />
              <Route path="/doctor/benhmanage" element={
                <ProtectedRoute allowedRoles={["ROLE_DOCTOR"]}>
                  <BenhManageDoctor />
                </ProtectedRoute>
              } />
              <Route path="/upload-license" element={
                <ProtectedRoute allowedRoles={["ROLE_DOCTOR"]}>
                  <UploadLicense />
                </ProtectedRoute>
              } />
              <Route path="/danhgiadoctor" element={
                <ProtectedRoute allowedRoles={["ROLE_DOCTOR"]}>
                  <DanhGiaDoctor />
                </ProtectedRoute>
              } />
              <Route path="/danhgiareply/:id" element={
                <ProtectedRoute allowedRoles={["ROLE_DOCTOR"]}>
                  <DanhGiaReply />
                </ProtectedRoute>
              } />
              <Route path="/chitietdonkhamview/:benhNhanId" element={
                <ProtectedRoute allowedRoles={["ROLE_DOCTOR"]}>
                  <ChiTietDonKhamBacSi />
                </ProtectedRoute>
              } />

              {/* User Routes */}
              <Route path="/user" element={
                <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                  <UserHome />
                </ProtectedRoute>
              } />
              <Route path="/hososuckhoe" element={
                <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                  <HoSoSucKhoeUser />
                </ProtectedRoute>
              } />
              <Route path="/hososuckhoe-create" element={
                <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                  <HoSoSucKhoeCreate />
                </ProtectedRoute>
              } />
              <Route path="/chitietdonkham/:id" element={
                <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                  <ChiTietDonKham />
                </ProtectedRoute>
              } />
              <Route path="/thanhtoan/:id" element={
                <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                  <ThanhToan />
                </ProtectedRoute>
              } />
              <Route path="/lichkhamuser" element={
                <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                  <LichKhamUser />
                </ProtectedRoute>
              } />
              <Route path="/lichkhamuser/:id" element={
                <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                  <ChiTIetLichKhamUser />
                </ProtectedRoute>
              } />
              <Route path="/cacdonchuathanhtoan" element={
                <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                  <ThanhToanDonKham />
                </ProtectedRoute>
              } />
              <Route path="/datlichkham" element={
                <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                  <DatLichKhamUser />
                </ProtectedRoute>
              } />
              <Route path="/danhgia" element={
                <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                  <DanhGiaUser />
                </ProtectedRoute>
              } />
              <Route path="/danhgiacreate/:id" element={
                <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                  <DanhGiaCreate />
                </ProtectedRoute>
              } />
              <Route path="/xemdanhgia" element={
                <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                  <DanhGiaView />
                </ProtectedRoute>
              } />
              <Route path="/assitant" element={
                <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                  <Assitant />
                </ProtectedRoute>
              } />
              {/* Common */}
              <Route path="/profile" element={<ProFileUser />} />
            </Routes>

          </Container>

          <Footer />
        </BrowserRouter>
      </MyDispatchContext.Provider>
    </MyUserContext.Provider>
  );
}

export default App;
