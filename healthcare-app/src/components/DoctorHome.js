import { useContext } from "react";
import { MyUserContext } from "../configs/Contexts";


const DoctorHome = () => {
  const user = useContext(MyUserContext);

  if (!user) return <div>Vui lòng đăng nhập để xem lịch khám</div>;

  return (
    <div>
      <h2>Chào bác sĩ {user.ho} {user.ten}</h2>
    </div>
  );
};

export default DoctorHome;
