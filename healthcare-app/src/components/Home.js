import { useEffect, useState } from "react";
import { Alert, Button, Card, Col, Row } from "react-bootstrap";
import Apis, { endpoints } from "../configs/Apis";
import { useSearchParams } from "react-router-dom";
import MySpinner from "./layout/MySpinner";

const Home = () => {
    const [doctors, setDoctors] = useState([]);  // Initialize as an empty array
    const [loading, setLoading] = useState(true);
    const [page, setPage] = useState(1);
    const [q] = useSearchParams();

    // Load doctors list based on current page and filters
    const loadDoctors = async () => {
        try {
            setLoading(true);

            let url = `${endpoints['doctors']}?page=${page}`;

            // Apply filters if available
            const specId = q.get('specId');
            if (specId) url += `&specializationId=${specId}`;

            const kw = q.get('kw');
            if (kw) url += `&kw=${kw}`;

            const res = await Apis.get(url);

            // Log the response to check structure
            console.log("API response:", res.data);

            // Ensure res.data is an array before using .map
            if (Array.isArray(res.data)) {
                // If no doctors are found, stop pagination
                if (res.data.length === 0) {
                    setPage(0);
                } else {
                    // Append new doctors to the list if not on the first page
                    if (page === 1) {
                        setDoctors(res.data);
                    } else {
                        setDoctors(prev => [...prev, ...res.data]);
                    }
                }
            } else {
                console.error("Expected an array, but got:", res.data);
                setDoctors([]);  // Reset doctors if data is not an array
            }
        } catch (ex) {
            console.error(ex);
        } finally {
            setLoading(false);
        }
    };

    // Run on page or search parameters change
    useEffect(() => {
        if (page > 0) loadDoctors();
    }, [page, q]);

    // Reset pagination and doctor list when search params change
    useEffect(() => {
        setPage(1);
        setDoctors([]);
    }, [q]);

    // Handle load more action (pagination)
    const loadMore = () => {
        if (!loading && page > 0) {
            setPage(page + 1);
        }
    };

    return (
        <></>
        // <>
        //     {loading && <MySpinner />}

        //     {/* If no doctors found */}
        //     {doctors.length === 0 && !loading && (
        //         <Alert variant="info" className="mt-2">Không tìm thấy bác sĩ phù hợp!</Alert>
        //     )}

        //     {/* Display doctors in cards */}
        //     <Row>
        //         {doctors.map(d => (
        //             <Col className="p-2" md={3} xs={6} key={d.id}>
        //                 <Card>
        //                     <Card.Img variant="top" src={d.image || "/default-avatar.png"} />
        //                     <Card.Body>
        //                         <Card.Title>{d.name}</Card.Title>
        //                         <Card.Text>
        //                             <strong>Chuyên khoa:</strong> {d.specialization}<br />
        //                             <strong>Phí khám:</strong> {d.price.toLocaleString()} VNĐ
        //                         </Card.Text>
        //                         <Button className="me-1" variant="primary">Xem chi tiết</Button>
        //                     </Card.Body>
        //                 </Card>
        //             </Col>
        //         ))}
        //     </Row>

        //     {/* Pagination button */}
        //     {page > 0 && (
        //         <div className="text-center mb-2">
        //             <Button variant="info" onClick={loadMore} disabled={loading}>
        //                 Xem thêm bác sĩ...
        //             </Button>
        //         </div>
        //     )}
        //</>
    );
};

export default Home;
