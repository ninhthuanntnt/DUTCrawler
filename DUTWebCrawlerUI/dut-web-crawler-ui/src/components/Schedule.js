
import React from 'react';
import { Container, Row, DropdownButton, Dropdown, Table } from 'react-bootstrap';
import { connect } from 'react-redux';
import { loadSchedule } from '../actions';

class Schedule extends React.Component {

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
        this.loadSchedule(event.target.innerText);
    }

    getSemesterCode = (curSemester) => {
        let index = curSemester.indexOf("/");
        let prefixType = curSemester.substring(index + 3, index + 5);
        let subfix = curSemester.substring(0, index);
        let subfixType = ((subfix.indexOf("Hè") !== -1) ? "21" : subfix + "0");

        return prefixType + subfixType;
    }

    loadSchedule = (curSemester) => {
        this.props.loadSchedule(this.getSemesterCode(curSemester));
    }

    render() {
        let listSemester = this.props.semester.map((el) => {
            return (
                <Dropdown.Item key={el}
                    onClick={this.selectSemester}
                    active={this.state.curSemester.trim() === el.trim() ? true : false}>{el}</Dropdown.Item>
            );
        });

        let totalTuition = 0;
        let totalCredit = 0;
        let listSchedule = this.props.schedule.map((el, index) => {
            totalCredit += (el.credit !== -1) ? el.credit : 0;
            totalTuition += (el.tuition !== -1) ? el.tuition : 0;
            return (
                <tr key={index}>
                    <td>{el.code}</td>
                    <td>{el.name}</td>
                    <td>{el.credit}</td>
                    <td>{el.tuition}</td>
                    <td>{el.payed}</td>
                    <td>{el.lecturer}</td>
                    <td>{el.schedule}</td>
                    <td>{el.studyingWeek}</td>
                </tr>
            )
        });
        return (
            <Container fluid className="p-5">
                <Row className="justify-content-center">
                    <DropdownButton variant="success" title={this.state.curSemester}>
                        {listSemester}
                    </DropdownButton>
                </Row>
                <Row className="mt-3 justify-content-center">
                    <Table hover responsive>
                        <thead>
                            <tr>
                                <th scope="col">Mã lớp HP</th>
                                <th scope="col">Tên lớp HP</th>
                                <th scope="col">Số TC</th>
                                <th scope="col">Học phí</th>
                                <th scope="col">Nợ</th>
                                <th scope="col">Giảng viên</th>
                                <th scope="col">Thời khóa biểu</th>
                                <th scope="col">Tuần học</th>
                            </tr>
                        </thead>
                        <tbody>
                            {listSchedule}
                        </tbody>
                        <tfoot>
                            <th colSpan={2} className="text-right">
                            </th>
                            <th>
                                {totalCredit}
                            </th>
                            <th colSpan={5}>
                                {totalTuition}
                            </th>
                        </tfoot>
                    </Table>
                </Row>
            </Container>
        );
    }

    componentDidMount() {
        this.loadSchedule(this.state.curSemester);
    }
}

var mapStateToProps = (state) => {
    return {
        schedule: state.schedule,
        token: state.loginedData.token,
        semester: state.loginedData.semester
    }
}

var mapDispatchToProps = (dispatch) => {
    return {
        loadSchedule: (type) => dispatch(loadSchedule(type))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Schedule);