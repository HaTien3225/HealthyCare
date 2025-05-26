import { useContext, useState } from "react";
import { Button, Form, InputGroup } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import { MyUserContext } from "../configs/Contexts";
import { authApis, endpoints } from "../configs/Apis";
import MySpinner from "./layout/MySpinner";

const DanhGiaCreate = () => {
    const { id } = useParams();
    const user = useContext(MyUserContext);
    const navigate = useNavigate();
    const [rating, setRating] = useState(5);
    const [binhLuan, setBinhLuan] = useState("");
    const [isFetching,setIsFetching] = useState(false);

    const binhLuanCreate = async()  => {
        if(binhLuan == null || binhLuan === "")
            alert("Chưa điền đủ thông tin");
        else if(binhLuan.length > 1024)
            alert("Bình luận quá dài")
        else{
            setIsFetching(true);
            try{
                let data = {
                    "binhLuan":binhLuan,
                    "rating":rating
                }
                const res = await authApis().put(`${endpoints.danhgiauser}/${id}`,data);
                alert("Đánh giá thành công");
                navigate("/danhgia");
            }catch(e){
                if(e.response)
                    alert(e.response.data);
            }
            finally{
                setIsFetching(false);
            }
        }
    }


    const ratingUpdater = (op) => {
        if (op === '-' && rating > 1)
            setRating(prev => prev - 1);
        else if (op === '+' && rating < 5)
            setRating(prev => prev + 1);

        if (rating <= 0)
            setRating(1);
        if (rating >= 6)
            setRating(5);
    }
    if(!user) return <h1>Vui long đăng nhập</h1>

    return (
        <>
            <Form.Group className="mb-3 mt-3" controlId="reviewText">
                <Form.Label>Đánh giá lịch khám</Form.Label>
                <Form.Control
                    as="textarea"
                    rows={3}
                    placeholder="Hãy chia sẻ cảm nhận của bạn..."
                    value={binhLuan}
                    onChange={(e) => setBinhLuan(e.target.value)}
                />
            </Form.Group>

            <Form.Group className="mb-3" controlId="reviewRating">
                <Form.Label>Điểm đánh giá (từ 1 đến 5)</Form.Label>
                <InputGroup style={{ width: "30vh" }}>
                    <InputGroup.Text>
                        <Button variant="outline-secondary" onClick={() => ratingUpdater('-')} >-</Button>
                    </InputGroup.Text>
                    <Form.Control
                        type="number"
                        min="1"
                        max="5"
                        defaultValue="5"
                        className="text-center"
                        value={rating}
                        onChange={(e) => setRating(e.target.value)}
                    />
                    <InputGroup.Text>
                        <Button variant="outline-secondary" onClick={() => ratingUpdater('+')}>+</Button>
                    </InputGroup.Text>
                </InputGroup>
                <Form.Text className="text-muted">
                    Chọn điểm từ 1 (rất không hài lòng) đến 5 (rất hài lòng) ⭐
                </Form.Text>
            </Form.Group>

            {!isFetching && <Button variant="primary" onClick={binhLuanCreate}>
                Gửi đánh giá
            </Button>}
            {isFetching && <Button variant="primary" disabled>
                Gửi đánh giá
            </Button>}
            {isFetching && <MySpinner/>}
        </>
    );
};

export default DanhGiaCreate;
