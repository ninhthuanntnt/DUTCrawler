
import React from 'react';
import '../assets/styles/notification.css';
import { connect } from 'react-redux';
import { loadNotification } from '../actions';
import { Container, Row, Col, ListGroup, Card, Pagination } from 'react-bootstrap';

class Header extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            type: "general",
            page: 1
        }
    }

    changeSelection = (event) => {
        var target = event.target;
        var type = target.dataset.type;

        this.props.loadNotification(type, this.state.page);
        this.setState({
            type
        });
    }
    loadFirstPage = ()=>{
        this.setState({
            page: 1
        });
        this.props.loadNotification(this.state.type, 1);
    }

    loadLastPage = ()=>{
        this.setState({
            page: 10
        });
        this.props.loadNotification(this.state.type, 10);
    }

    loadPrevPage = ()=>{
        if(this.state.page > 1){
            this.setState({
                page: this.state.page - 1
            });
            this.props.loadNotification(this.state.type, this.state.page - 1);
        }
    }

    loadNextPage = ()=>{
        this.setState({
            page: this.state.page + 1
        });
        this.props.loadNotification(this.state.type, this.state.page + 1);
    }

    render() {

        let listNotification = this.props.notifications.map((item, index) => {
            return (
                <div key={index} className="card">
                    <div className="card-header ">
                        Ngày {item.date}
                    </div>
                    <div className="card-body">
                        <h5 className="card-title">{item.title}</h5>
                        <p className="card-text" dangerouslySetInnerHTML={{ __html: item.content }}></p>
                    </div>
                </div>
            )
        });

        return (
            <Container fluid className="mt-3">
                <Row>
                    <Col sm={3}>
                        <ListGroup>
                            <ListGroup.Item action active={(this.state.type === "general") ? true : false}
                                className={" text-center"}
                                data-type="general"
                                onClick={this.changeSelection}>
                                Thông báo chung
                            </ListGroup.Item>
                            <ListGroup.Item action active={(this.state.type === "class") ? true : false}
                                className={" text-center"}
                                data-type="class"
                                onClick={this.changeSelection}>
                                Thông báo đến lớp học phần
                            </ListGroup.Item>
                            <Card className="ntnt-card">
                                <Card.Header className="card-header bg-primary text-white text-center">
                                    Bộ lọc
                                    </Card.Header>
                                <Card.Body className="card-body">
                                    <Row className="flex-column align-items-center">
                                        <p>Trang</p>
                                        <Pagination className="pagination justify-content-center">
                                            <Pagination.First onClick={this.loadFirstPage}/>
                                            <Pagination.Prev onClick={this.loadPrevPage}/>
                                            <Pagination.Item active>{this.state.page}</Pagination.Item>
                                            <Pagination.Next onClick={this.loadNextPage}/>
                                            <Pagination.Last onClick={this.loadLastPage}/>
                                        </Pagination>
                                    </Row>
                                    <Row className="flex-column align-items-center">
                                        <p>Ngày</p>
                                        <input type="date" class="form-control" pattern="\d{1,2}/\d{1,2}/\d{4}" />
                                    </Row>
                                </Card.Body>
                            </Card>

                        </ListGroup>
                    </Col>
                    <div className="col-sm-9">
                        <div className="ntnt-scroll" style={{ height: "calc(100vh - 6rem)" }}>
                            {listNotification}
                        </div>
                    </div>
                </Row>
            </Container>
        );
    }

    componentDidMount() {
        this.props.loadNotification(this.state.type, this.state.page);
    }
}

var mapStateToProp = (state) => {
    return {
        notifications: state.notifications
    }
}

var mapDispatchToProp = (dispatch) => {
    return {
        loadNotification: (type, page) => dispatch(loadNotification(type, page))
    }
}

export default connect(mapStateToProp, mapDispatchToProp)(Header);