import { useEffect, useState } from "react";
import { Alert, Button, Card, Carousel, Col, Container, Image, Row } from "react-bootstrap";
import Apis, { endpoints } from "../configs/Apis";
import { useSearchParams } from "react-router-dom";
import MySpinner from "./layout/MySpinner";

const Home = () => {
    return (
        <>
            <Carousel>
                <Carousel.Item>
                    <Image src="/360_F_172745118_kfspuHw9aNALulN1yZI5VKWPQ6VqJVEt.jpg" style={{ width: '100%', height: '500px', }} />
                    <Carousel.Caption>

                    </Carousel.Caption>
                </Carousel.Item>
                <Carousel.Item>
                    <Image src="/modern-hospital-clinic-building-exterior-67609416.jpg" style={{ width: '100%', height: '500px' }} />
                    <Carousel.Caption>

                    </Carousel.Caption>
                </Carousel.Item>
                <Carousel.Item>
                    <Image src="/smiling-medical-team-standing-together-outside-hospital-127007504.jpg" style={{ width: '100%', height: '500px' }} />
                    <Carousel.Caption>

                    </Carousel.Caption>
                </Carousel.Item>
                <Carousel.Item>
                    <Image src="/il_fullxfull.3798184838_48gu.jpg" style={{ width: '100%', height: '500px' }} />
                    <Carousel.Caption>

                    </Carousel.Caption>
                </Carousel.Item>
            </Carousel>
            <Container className="mt-5">
                <Row className="justify-content-center">
                    <Col md={8}>
                        <Card className="shadow-lg">
                            <Card.Body>
                                <Card.Title as="h2" className="mb-3 text-primary text-center">
                                    Giới thiệu Bệnh viện
                                </Card.Title>
                                <Card.Text style={{ fontSize: '1.1rem', lineHeight: '1.6' }}>
                                    Chào mừng bạn đến với hệ thống bệnh viện của chúng tôi! Với đội ngũ y bác sĩ tận tâm,
                                    hệ thống trang thiết bị hiện đại, chúng tôi cam kết mang lại dịch vụ chăm sóc sức khỏe tốt nhất
                                    cho cộng đồng.
                                    <br /><br />
                                    Hệ thống đặt lịch khám trực tuyến giúp bạn tiết kiệm thời gian, chủ động lựa chọn bác sĩ và khung giờ phù hợp.
                                    Hãy trải nghiệm dịch vụ y tế thông minh cùng chúng tôi.
                                </Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
            </Container>
        </>
    );
};

export default Home;
