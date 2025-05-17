import { useContext, useState } from "react";
import { Alert, Button, Form } from "react-bootstrap";
import Apis, { authApis, endpoints } from "../configs/Apis";
import MySpinner from "./layout/MySpinner";
import { useNavigate, useSearchParams } from "react-router-dom";
import cookie from "react-cookies";
import { MyDispatchContext } from "../configs/Contexts";
import { jwtDecode } from 'jwt-decode';

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

      // Make login API request
      let res = await Apis.post(endpoints["login"], user);
      const token = res.data.token;

      // Save token to cookies
      cookie.save("token", token);


      const decoded = jwtDecode(token);


      // Đảm bảo lấy đúng trường từ decoded
      const { sub: username, role } = decoded; // sub có thể là username
      console.log("Username:", username);
      console.log("Role:", role);

      // Fetch user information from the server
      let u = await authApis().get(endpoints["current-user"]);

      localStorage.setItem("token", token);
      localStorage.setItem("user", JSON.stringify({ ...u.data, role }));


      // Dispatch login action
      dispatch({
        type: "login",
        payload: { ...u.data, role: role }
      });



      // Navigate to appropriate page based on role
      if (role === "ROLE_DOCTOR") {
        nav("/doctor");
      } else {
        nav("/user");
      }

    } catch (err) {
      console.error(err);
      setMsg("Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin!");
    } finally {
      setLoading(false);
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

        <Form.Group className="mb-3">
          {loading ? <MySpinner /> : <Button type="submit" variant="danger">Đăng nhập</Button>}
        </Form.Group>
      </Form>
    </>
  );
};

export default Login;
