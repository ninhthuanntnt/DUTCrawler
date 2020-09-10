import { combineReducers } from "redux";
import { notification } from "./notification";
import { score } from "./score";
import { schedule } from "./schedule";
import { login } from "./login";

export default combineReducers({
    notifications: notification,
    score,
    schedule,
    loginedData: login
});