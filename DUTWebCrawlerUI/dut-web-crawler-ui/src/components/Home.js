
import React from 'react';
import { Link } from 'react-router-dom'
import caledarImage from '../assets/images/Calendar.jpg';
import scoreImage from '../assets/images/score.png';
import notiImage from '../assets/images/notification.png';
import '../assets/styles/home.css';
import { Container, Row, Col, Card } from 'react-bootstrap';

class Home extends React.Component {

    render() {
        return (
            <Container fluid>
                <Row className="mt-3 justify-content-center">
                    <Col sm={4} xs={12} md={3} className="m-2">
                        <Card style={{ width: "100%", height: "100%" }}>
                            <Card.Img variant="top" src={caledarImage} />
                            <Card.Body>
                                <Card.Title>
                                    Lịch học/thi
                                </Card.Title>
                                <Card.Text>
                                    Hiển thị thông tin lịch học
                                </Card.Text>
                                <Link to="/schedule" className="btn btn-primary">Go somewhere</Link>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col sm={4} xs={12} md={3} className="m-2">
                        <Card style={{ width: "100%", height: "100%" }}>
                            <Card.Img variant="top" src={scoreImage} />
                            <Card.Body>
                                <Card.Title>
                                    Điểm
                                </Card.Title>
                                <Card.Text>
                                    Hiển thị điểm chi tiết và tổng kết điểm từng học kỳ
                                </Card.Text>
                                <Link to="/score" className="btn btn-primary">Go somewhere</Link>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col sm={4} xs={12} md={3} className="m-2">
                        <Card style={{ width: "100%", height: "100%" }}>
                            <Card.Img variant="top" src={notiImage} />
                            <Card.Body>
                                <Card.Title>
                                    Thông báo
                                </Card.Title>
                                <Card.Text>
                                    Hiển thị thông báo chung của nhà trường và thông báo đến lớp
                                    học phần của giáo viên
                                </Card.Text>
                                <Link to="/notification" className="btn btn-primary">Go somewhere</Link>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col sm={4} xs={12} md={3} className="m-2">
                        <Card style={{ width: "100%", height: "100%" }}>
                            <Card.Img variant="top" src={caledarImage} />
                            <Card.Body>
                                <Card.Title>
                                    Lịch học/thi
                            </Card.Title>
                                <Card.Text>
                                    Hiển thị thông tin lịch học
                            </Card.Text>
                                <Link to="/schedule" className="btn btn-primary">Go somewhere</Link>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col sm={4} xs={12} md={3} className="m-2">
                        <Card style={{ width: "100%", height: "100%" }}>
                            <Card.Img variant="top" src={scoreImage} />
                            <Card.Body>
                                <Card.Title>
                                    Điểm
                            </Card.Title>
                                <Card.Text>
                                    Hiển thị điểm chi tiết và tổng kết điểm từng học kỳ
                            </Card.Text>
                                <Link to="/score" className="btn btn-primary">Go somewhere</Link>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col sm={4} xs={12} md={3} className="m-2">
                        <Card style={{ width: "100%", height: "100%"  }}>
                            <Card.Img variant="top" src={notiImage} />
                            <Card.Body>
                                <Card.Title>
                                    Thông báo
                            </Card.Title>
                                <Card.Text>
                                    Hiển thị thông báo chung của nhà trường và thông báo đến lớp
                                    học phần của giáo viên
                            </Card.Text>
                                <Link to="/score" className="btn btn-primary">Go somewhere</Link>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
            </Container>
        );
    }
}
export default Home;