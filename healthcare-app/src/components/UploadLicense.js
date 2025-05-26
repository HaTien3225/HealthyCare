import { useContext, useState } from "react";
import { Alert, Button, Form, Spinner } from "react-bootstrap";
import Apis, { authApis, endpoints } from "../configs/Apis";
import { useNavigate, useNavigation } from "react-router-dom";
import { MyUserContext } from "../configs/Contexts";
import cookie from 'react-cookies'

const UploadLicense = () => {
    const [file, setFile] = useState(null);
    const [msg, setMsg] = useState(null);
    const [loading, setLoading] = useState(false);
    const user = useContext(MyUserContext)
    const navigate=useNavigate();

    const handleUpload = async (e) => {
        e.preventDefault();
        setMsg(null);

        if (!file) {
            setMsg("Vui lòng chọn file!");
            return;
        }

        const formData = new FormData();
        formData.append("file", file);

        try {
            setLoading(true);
            let res = await authApis().post(endpoints.upload, formData);
            setMsg(res.data || "Tải giấy phép thành công!");
            
        // localStorage.removeItem("token");
        //  localStorage.removeItem("user");
        //     cookie.remove('token');
        // if (user && user.setUser) {
        //     user.setUser(null);  
        // }
        // navigate("/login"); 
        
        } catch (err) {
            console.error(err);
            setMsg("Lỗi khi upload giấy phép!");
            setFile(null);
            e.target.reset();
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <h2 className="text-center mt-3">Upload Giấy phép hành nghề</h2>

            {msg && <Alert variant="info" className="mt-2">{msg}</Alert>}

            <Form onSubmit={handleUpload}>
                <Form.Group className="mb-3">
                    <Form.Control
                        type="file"
                        onChange={(e) => setFile(e.target.files[0])}
                        accept="image/*,.pdf"
                        required
                    />
                </Form.Group>

                <div className="text-center">
                    <Button type="submit" variant="success" disabled={loading}>
                        {loading ? <Spinner animation="border" size="sm" /> : "Tải lên"}
                    </Button>
                </div>
            </Form>
        </>
    );
};

export default UploadLicense;
