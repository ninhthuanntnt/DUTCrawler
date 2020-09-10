
import React from 'react';
import { Link } from 'react-router-dom';
import { login, logout } from '../actions';
import { connect } from 'react-redux';
import { Navbar, Nav, Form, FormControl, Button, Modal } from 'react-bootstrap';

class Header extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            errorMessage: null,
            isShowedLogin: false
        }
    }

    changeInputData = (event) => {
        let target = event.target;

        this.setState({
            [target.name]: target.value
        })
    }

    submitLoginInfo = () => {
        this.props.login(this.state.username, this.state.password);
        this.handleHideLogin();
    }

    handleShowLogin = () => {
        this.setState({
            isShowedLogin: true
        });
    }

    handleHideLogin = () => {
        this.setState({
            username: "",
            password: "",
            isShowedLogin: false
        });
    }

    render() {
        let isExistToken = (
            this.props.token !== undefined &&
            this.props.token !== null &&
            this.props.token.trim() !== ""
        ) ? true : false;

        let loginModal = (isExistToken) ? "" : (
            <Modal show={this.state.isShowedLogin} onHide={this.handleHideLogin}>
                <Modal.Header closeButton>
                    <Modal.Title>Đăng nhập</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group controlId="formBasicEmail">
                            <Form.Label>Mã số sinh viên</Form.Label>
                            <Form.Control type="text"
                                onChange={this.changeInputData}
                                value={this.state.username}
                                name="username" />
                            <Form.Text className="text-muted">
                                Mã số sinh viên để đăng nhập vào trang sinh viên
                        </Form.Text>
                        </Form.Group>

                        <Form.Group controlId="formBasicPassword">
                            <Form.Label>Mật khẩu</Form.Label>
                            <Form.Control type="password"
                                onChange={this.changeInputData}
                                value={this.state.password}
                                name="password" />
                            <Form.Text className="text-muted">
                                Mật khẩu để đăng nhập vào trang sinh viên
                        </Form.Text>
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={this.handleHideLogin}>
                        Thoát
                        </Button>
                    <Button variant="primary" onClick={this.submitLoginInfo}>
                        Đăng nhập
                        </Button>
                </Modal.Footer>
            </Modal>
        )

        return (
            <Navbar className="navbar navbar-expand-lg navbar-light bg-light">
                <Link to="/">
                    <Navbar.Brand>DUT Crawler</Navbar.Brand>
                </Link>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link as={Link} to="/notification">Thông báo</Nav.Link>
                        <Nav.Link as={Link} to="/score">Điểm</Nav.Link>
                        <Nav.Link as={Link} to="/schedule">Lịch học/thi</Nav.Link>
                    </Nav>
                    <Button variant="primary"
                        className="mr-2"
                        onClick={(isExistToken) ? this.props.logout : this.handleShowLogin}>
                        {(isExistToken) ? "Đăng xuất" : "Đăng nhập"}
                    </Button>

                    <Form inline>
                        <FormControl type="text" placeholder="Search" className="mr-sm-2" />
                        <Button variant="outline-success">Search</Button>
                    </Form>
                </Navbar.Collapse>

                {loginModal}
            </Navbar>
        );
    }
}

var mapStateToProps = (state) => ({
    token: state.loginedData.token
});

var mapDispatchToProps = (dispatch) => ({
    login: (username, password) => dispatch(login(username, password)),
    logout: () => dispatch(logout())
});

export default connect(mapStateToProps, mapDispatchToProps)(Header);