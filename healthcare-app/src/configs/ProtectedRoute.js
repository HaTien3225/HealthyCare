import { useContext } from "react";
import { Navigate, useLocation } from "react-router-dom";
import { MyUserContext } from "./Contexts";

const ProtectedRoute = ({ allowedRoles, children }) => {
  const user = useContext(MyUserContext);
  const location = useLocation();

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  if (
    user.role === "ROLE_DOCTOR" &&
    !user.giayPhepHanhNgheId &&
    location.pathname !== "/upload-license"
  ) {
    return <Navigate to="/upload-license" replace />;
  }

  if (!allowedRoles.includes(user.role)) {
    return <Navigate to="/" replace />;
  }
  return children;
};

export default ProtectedRoute;
