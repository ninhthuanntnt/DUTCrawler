import { FETCH_NOTIFICATION, FETCH_SCORE, FETCH_SCHEDULE, FETCH_LOGINED_DATA, CLEAR_LOGINED_DATA } from "../const/ActionType"
import Axios from "axios"

export const loadNotification = (type, page) => {
    return (dispatch) => {
        Axios({
            method: 'GET',
            url: `http://localhost:8080/api/notifications?notiType=${type}&pageNumber=${page}`
        }).then(res => {
            dispatch(fetchNotification(res.data));
        });
    }
}

export const login = (username, password) => {
    return (dispatch) => {
        Axios({
            method: 'POST',
            url: `http://localhost:8080/api/login`,
            data: {
                username,
                password
            }
        }).then(res=>{
            dispatch(fetchLoginedData(res.data));
        })
    }
}

export const loadScore = () =>{
    return (dispatch) =>{
        Axios({
            method: "GET",
            url: `http://localhost:8080/api/score`,
            headers:{
                'Authorization': 'Bearer ' + localStorage.getItem("token")
            },
            withCredentials: true
        }).then(res=>{
            dispatch(fetchScore(res.data));
        });
    }
}
export const loadSchedule = (type) =>{
    return (dispatch) =>{
        Axios({
            method: "GET",
            url: `http://localhost:8080/api/schedule?type=${type}`,
            headers:{
                'Authorization': 'Bearer ' + localStorage.getItem("token")
            },
            withCredentials: true
        }).then(res=>{
            dispatch(fetchSchedule(res.data));
        });
    }
}

export const fetchLoginedData = (data)=>{
    return {
        type: FETCH_LOGINED_DATA,
        data
    }
}

export const loadDataFromLocal = ()=>{
    let data = {
        token: localStorage.getItem("token"),
        additionalData: localStorage.getItem("class")
    }
    return dispatch => dispatch(fetchLoginedData(data));
}

export const fetchScore = (data)=>{
    return {
        type: FETCH_SCORE,
        data
    }
}

export const fetchSchedule = (data)=>{
    return{
        type: FETCH_SCHEDULE,
        data: data
    }
}
export const logout = ()=>{
    return {
        type: CLEAR_LOGINED_DATA
    }
}

export const fetchNotification = (data) => {
    return {
        type: FETCH_NOTIFICATION,
        data: data
    }
}