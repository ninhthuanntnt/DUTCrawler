import { FETCH_SCHEDULE } from "../const/ActionType";

var initState = []
export const schedule = (state = initState, action) => {

    let schedule = [...state];

    switch (action.type) {
        case FETCH_SCHEDULE:
            schedule = action.data.result;
        return schedule;

        default:
            return state;
    }
}