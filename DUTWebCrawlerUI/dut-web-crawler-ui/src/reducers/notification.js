const {FETCH_NOTIFICATION } = require("../const/ActionType");

var initState = [];

export const notification = (state = initState, action) => {

    let notifications = [...state];

    switch(action.type){
        case FETCH_NOTIFICATION:
            console.log("FETCHED")
            notifications = action.data;
            return notifications;
        default:
            return state;
    }
}
