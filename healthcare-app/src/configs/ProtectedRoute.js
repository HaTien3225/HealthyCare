import { useContext } from "react";
import { Navigate } from "react-router-dom";
import { MyUserContext } from "./Contexts";


const ProtectedRoute = ({ allowedRoles, children }) => {
  const user = useContext(MyUserContext);

//   if (!user) {
//     // Chưa đăng nhập => chuyển hướng về trang login
//     return <Navigate to="/login" replace />;
//   }

//   if (!allowedRoles.includes(user.role)) {
//     // Vai trò không phù hợp => có thể chuyển hướng hoặc hiện thông báo lỗi
//     return <Navigate to="/" replace />;
//   }

  return children;
};

export default ProtectedRoute;
