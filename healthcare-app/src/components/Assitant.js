import React, { useState, useEffect, useRef } from 'react';
import { Form, Button, ListGroup, Spinner, Container, Row, Col } from 'react-bootstrap';
import Apis, { endpoints } from "../configs/Apis";

const Assistant = () => {
    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState("");
    const [isResponding, setIsResponding] = useState(false);
    const messagesEndRef = useRef(null);

    
    useEffect(() => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    }, [messages]);

    const handleSendMessage = async (e) => {
        e.preventDefault();
        if (!newMessage.trim()) return;

        
        const userMessage = { sender: 'user', content: newMessage };
        setMessages(prev => [...prev, userMessage]);
        setNewMessage("");
        setIsResponding(true);

        try {
           
            const response = await Apis.post(endpoints.assitant, {
                userMessage: newMessage
            });

          
            const aiMessage = { sender: 'ai', content: response.data.response };
            setMessages(prev => [...prev, aiMessage]);
        } catch (error) {
            console.error("Lỗi khi gọi API:", error);
            const errorMessage = { sender: 'ai', content: "Xin lỗi, có lỗi xảy ra khi kết nối với AI." };
            setMessages(prev => [...prev, errorMessage]);
        } finally {
            setIsResponding(false);
        }
    };

    return (
        <>
        <h2>Trợ lý ảo</h2>
        <Container className="chat-container my-4" >
            <Row className="justify-content-center">
                <Col md={8} lg={6}>
                    <div className="border rounded p-3 bg-light" >
                        
                        <div className="chat-messages mb-3" style={{ height: '400px', overflowY: 'auto' }}>
                            <ListGroup variant="flush">
                                {messages.map((msg, index) => (
                                    <ListGroup.Item 
                                        key={index} 
                                        className={`mb-2 rounded ${msg.sender === 'user' ? 'bg-primary text-white align-self-end' : 'bg-light align-self-start'}`}
                                        style={{ maxWidth: '80%', alignSelf: msg.sender === 'user' ? 'flex-end' : 'flex-start' }}
                                    >
                                        {msg.content}
                                    </ListGroup.Item>
                                ))}
                                {isResponding && (
                                    <ListGroup.Item className="bg-light align-self-start" style={{ maxWidth: '80%' }}>
                                        <Spinner animation="border" size="sm" /> Đang phản hồi...
                                    </ListGroup.Item>
                                )}
                                <div ref={messagesEndRef} />
                            </ListGroup>
                        </div>

                        
                        <Form onSubmit={handleSendMessage}>
                            <Form.Group className="d-flex">
                                <Form.Control
                                    type="text"
                                    value={newMessage}
                                    onChange={(e) => setNewMessage(e.target.value)}
                                    placeholder="Nhập tin nhắn..."
                                    disabled={isResponding}
                                />
                                <Button 
                                    variant="primary" 
                                    type="submit" 
                                    disabled={!newMessage.trim() || isResponding}
                                    className="ms-2"
                                >
                                    Gửi
                                </Button>
                            </Form.Group>
                        </Form>
                    </div>
                </Col>
            </Row>
        </Container>
        <p style={{color:"blue"}}>@Powered by deepseek/deepseek-r1-0528:free</p>
        </>
    );
};

export default Assistant;