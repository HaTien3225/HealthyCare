import React from "react";
import JitsiMeeting from "../components/JitsiMeeting";
import { useParams } from "react-router-dom";
import { MyUserContext } from "../configs/Contexts";
import { useContext } from "react";

const DoctorTuvan = () => {
  const { lichKhamId } = useParams(); // e.g., /tuvan/123
  const user = useContext(MyUserContext);

  const roomName = `tuvan-${lichKhamId}`;
  const userName = user?.lastName + " " + user?.firstName;
 if (!user) return <p>Vui lòng đăng nhập</p>
  return (
    <div className="container mt-4">
      <h3>Phòng tư vấn: {roomName}</h3>
      <JitsiMeeting roomName={roomName} userName={userName} />
    </div>
  );
};

export default DoctorTuvan;
