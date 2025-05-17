import { BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/layout/Header";
import Footer from "./components/layout/Footer";
import Home from "./components/Home";
import { Container } from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import Register from "./components/Register";
import Login from "./components/Login";
import { MyDispatchContext, MyUserContext } from "./configs/Contexts";
import { useReducer } from "react";
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


const App = () => {
  const [user, dispatch] = useReducer(MyUserReducer, null);


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
              {/*user */}
              <Route path="/hososuckhoe" element={<HoSoSucKhoeUser/>}/>
              <Route path="/hososuckhoe-create" element={<HoSoSucKhoeCreate/>}/>
            </Routes>
          </Container>

          <Footer />
        </BrowserRouter>
      </MyDispatchContext.Provider>
    </MyUserContext.Provider>
  );
}

export default App;
