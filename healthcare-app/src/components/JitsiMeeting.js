import React, { useEffect } from "react";

const JitsiMeeting = ({ roomName, userName }) => {
  useEffect(() => {
    const domain = "meet.jit.si";
    const options = {
      roomName: roomName,
      width: "100%",
      height: 600,
      parentNode: document.getElementById("jitsi-container"),
      userInfo: {
        displayName: userName,
      },
      configOverwrite: {
        disableInviteFunctions: true,
      },
      interfaceConfigOverwrite: {
        SHOW_JITSI_WATERMARK: false,
      },
    };

    const api = new window.JitsiMeetExternalAPI(domain, options);

    return () => api?.dispose();
  }, [roomName, userName]);

  return (
    <div id="jitsi-container" style={{ width: "100%", height: "600px" }}></div>
  );
};

export default JitsiMeeting;
