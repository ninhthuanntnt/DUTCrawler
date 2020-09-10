
import React from 'react';
import { loadScore } from '../actions';
import { connect } from 'react-redux';
import { Table, Row, Container, DropdownButton, Dropdown } from 'react-bootstrap';

class Score extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            curSemester: this.props.semester[this.props.semester.length - 1]
        }
    }

    selectSemester = (event) => {
        this.setState({
            curSemester: event.target.innerText
        });
    }

    getSemesterCode = (curSemester)=>{
        let index = curSemester.indexOf("/");
        let prefixType = curSemester.substring(index + 3, index + 5);
        let subfix = curSemester.substring(0, index);
        let subfixType = ((subfix.indexOf("Hè") !== -1) ? "21" : subfix + "0");

        return prefixType + subfixType;
    }

    render() {
        
        let curSemesterCode = this.getSemesterCode(this.state.curSemester);
        let listScores = this.props.score
            .filter((score) => {
                return score.code.indexOf(`.${curSemesterCode}.`) > -1
            })
            .map((score, index) => {
                return (
                    <tr key={index}>
                        <td>{index}</td>
                        <td>{score.code}</td>
                        <td>{score.subject}</td>
                        <td>{score.credit}</td>
                        <td>{score.score1}</td>
                        <td>{score.score2}</td>
                        <td>{score.score3}</td>
                        <td>{score.score4}</td>
                        <td>{score.score5}</td>
                        <td>{score.score6}</td>
                        <td>{score.score7}</td>
                        <td>{score.score8}</td>
                        <td>{score.score9}</td>
                    </tr>
                )
            });
        
        let listSemester = this.props.semester.map((el) => {
            return (
                <Dropdown.Item key={el}
                    onClick={this.selectSemester}
                    active={this.state.curSemester.trim() === el.trim() ? true : false}>{el}</Dropdown.Item>
            );
        });
        return (
            <Container fluid className="p-5">
                <Row className="justify-content-center">
                    <DropdownButton variant="primary" title={this.state.curSemester}>
                        {listSemester}
                    </DropdownButton>
                </Row>
                <Row className="mt-3 justify-content-center">
                    <Table hover responsive>
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Tổng số tín chỉ</th>
                                <th scope="col">Số tín chỉ học lại</th>
                                <th scope="col">Điểm TBC học kỳ T4</th>
                                <th scope="col">Điểm TBC học bổng</th>
                                <th scope="col">Điểm TBC học kỳ T10</th>
                                <th scope="col">Xếp loại học tập</th>
                                <th scope="col">Điểm rèn luyện</th>
                                <th scope="col">Số tín chỉ tích lũy</th>
                                <th scope="col">Điểm TBC tích lũy T4</th>
                                <th scope="col">Điểm TB rèn luyện các kỳ</th>
                            </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </Table>
                </Row>
                <Row className="mt-3 justify-content-center">
                    <h3>Chi tiết</h3>
                    <Table hover responsive>
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Mã lớp HP</th>
                                <th scope="col">Tên lớp HP</th>
                                <th scope="col">Số TC</th>
                                <th scope="col">Bài tập</th>
                                <th scope="col">Cuối kì</th>
                                <th scope="col">Đồ án</th>
                                <th scope="col">Giữa kì</th>
                                <th scope="col">Lý thuyết</th>
                                <th scope="col">Thực hành</th>
                                <th scope="col">Thang 10</th>
                                <th scope="col">Thang 4</th>
                                <th scope="col">Chữ</th>
                            </tr>
                        </thead>
                        <tbody>
                            {listScores}
                        </tbody>
                    </Table>
                </Row>
            </Container>
        );
    }

    componentDidMount() {
        this.props.loadScore();
    }
}

var mapStateToProps = (state) => {
    return {
        score: state.score,
        token: state.loginedData.token,
        semester: state.loginedData.semester
    }
}

var mapDispatchToProps = (dispatch) => {
    return {
        loadScore: (token) => dispatch(loadScore(token))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Score);