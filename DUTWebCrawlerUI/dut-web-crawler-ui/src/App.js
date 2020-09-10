import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Header from './components/Header';
import Home from './components/Home';
import Notification from './components/Notification';
import Score from './components/Score';
import Schedule from './components/Schedule';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import { connect } from 'react-redux';
import Page403 from './components/Page403';
import { loadDataFromLocal } from './actions';

class App extends React.Component {
	render() {
		return (
			<Router>
				<div>
					<Header />
					<Switch>
						<Route exact path="/">
							<Home />
						</Route>
						<Route path="/notification">
							<Notification />
						</Route>
						<Route path="/score" >
							{(this.props.token !== "") ? <Score /> : <Page403 />}
						</Route>
						<Route path="/schedule">
							{(this.props.token !== "") ? <Schedule /> : <Page403 />}
						</Route>
					</Switch>
				</div>
			</Router>
		);
	}

	componentWillMount(){
		this.props.loadDataFromLocal();
	}
}
var mapStateToProps = (state) => ({
	token: state.loginedData.token
});

var mapDispatchToProps = (dispatch)=>({
	loadDataFromLocal: ()=>dispatch(loadDataFromLocal())
});

export default connect(mapStateToProps, mapDispatchToProps)(App);
