import { useContext, useState } from "react";
import { Alert, Button, Form } from "react-bootstrap";
import Apis, { authApis, endpoints } from "../configs/Apis";
import MySpinner from "./layout/MySpinner";
import { useNavigate, useSearchParams } from "react-router-dom";
import cookie from "react-cookies";
import { MyDispatchContext } from "../configs/Contexts";
import { jwtDecode } from "jwt-decode";

import { signInWithPopup } from "firebase/auth";
import { auth, googleProvider } from "../configs/firebase";

const Login = () => {
  const info = [
    {
      label: "Tên đăng nhập",
      field: "username",
      type: "text"
    },
    {
      label: "Mật khẩu",
      field: "password",
      type: "password"
    }
  ];

  const [user, setUser] = useState({});
  const [msg, setMsg] = useState();
  const [loading, setLoading] = useState(false);
  const nav = useNavigate();
  const dispatch = useContext(MyDispatchContext);
  const [q] = useSearchParams();

  const login = async (e) => {
    e.preventDefault();
    setMsg(null);
    try {
      setLoading(true);

      let res = await Apis.post(endpoints["login"], user);
      const token = res.data.token;

      cookie.save("token", token);
      const decoded = jwtDecode(token);
      const { sub: username, role } = decoded;

      let u = await authApis().get(endpoints["current-user"]);


      dispatch({
        type: "login",
        payload: { ...u.data, role }
      });

      localStorage.setItem("user", JSON.stringify({ ...u.data, role }));
      localStorage.setItem("token", token);

      if (role === "ROLE_DOCTOR"&& u.data.giayPhepHanhNgheId != null &&u.data.giayPhepHanhNgheId.isValid=== false) {
        nav("/upload-license");
      }
      else {
        nav("/doctor");
      }
      if (role === "ROLE_USER" && u.data.isActive === true) {
        nav("/user");
      }
    } catch (err) {
      console.error(err);
      setMsg("Đăng nhập thất bại." + err.response.data);
    } finally {
      setLoading(false);
    }
  };

  const loginWithGoogle = async () => {
    try {
      const result = await signInWithPopup(auth, googleProvider);
      const userData = result.user;

      // Lấy idToken của Google
      const idToken = await userData.getIdToken();

      // Nếu backend có api login google, gửi token idToken lên để lấy JWT
      let res = await Apis.post(endpoints.google, { idToken: idToken });
      const token = res.data.token;
      cookie.save("token", token);
      localStorage.setItem("token", token);

      dispatch({
        type: "login",
        payload: {
          username: userData.displayName,
          email: userData.email,
          avatar: userData.photoURL,
          role: "ROLE_USER"
        }
      });

      localStorage.setItem("googleUser", JSON.stringify(userData));
      nav("/user");
    } catch (err) {
      console.error("Lỗi đăng nhập Google:", err);
      setMsg("Đăng nhập Google thất bại.");
    }
  };
  return (
    <>
      <h1 className="text-center text-success mt-2">ĐĂNG NHẬP NGƯỜI DÙNG</h1>

      {msg && <Alert variant="danger" className="mt-1">{msg}</Alert>}

      <Form onSubmit={login}>
        {info.map(i => (
          <Form.Group key={i.field} className="mb-3">
            <Form.Control
              value={user[i.field] || ""}
              onChange={(e) => setUser({ ...user, [i.field]: e.target.value })}
              type={i.type}
              placeholder={i.label}
              required
            />
          </Form.Group>
        ))}

        <Form.Group className="mb-3 text-center">
          {loading ? <MySpinner /> : <Button type="submit" variant="danger">Đăng nhập</Button>}
        </Form.Group>

        <Form.Group className="mb-3 text-center">
          <Button variant="outline-primary" onClick={loginWithGoogle}>
            <i className="fab fa-google me-2"></i> Đăng nhập bằng Google
          </Button>
        </Form.Group>
      </Form>
    </>
  );
};

export default Login;
