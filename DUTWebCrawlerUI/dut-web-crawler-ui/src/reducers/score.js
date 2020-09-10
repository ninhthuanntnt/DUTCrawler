import { FETCH_SCORE } from "../const/ActionType";

var initState = [];

export const score = (state = initState, action) => {

    let score = [...state];
    switch (action.type) {
        case FETCH_SCORE:

            score = action.data.result;
            for (let s of score) {
                for(let key in s){
                    s[key] = (s[key] === -1)?"":s[key]; 
                }
            }
            return action.data.result;
        default:
            return state;
    }
}