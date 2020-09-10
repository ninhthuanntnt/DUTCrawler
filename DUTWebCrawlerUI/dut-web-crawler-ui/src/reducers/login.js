import { FETCH_LOGINED_DATA, CLEAR_LOGINED_DATA } from '../const/ActionType';

var initState = {
    token: null,
    class: null
};

export const login = (state = initState, action) => {

    let loginedData = { ...state };
    switch (action.type) {
        case FETCH_LOGINED_DATA:
            loginedData.token = action.data.token;
            loginedData.class = action.data.additionalData;
            loginedData.semester = renderListSemester(loginedData.class);
            localStorage.setItem("token", loginedData.token);
            localStorage.setItem("class", loginedData.class);
            return loginedData;
        case CLEAR_LOGINED_DATA:
            loginedData.token = "";
            loginedData.class = "";
            localStorage.setItem("token", "");
            localStorage.setItem("class", "");
            return loginedData;
        default:
            return state;
    }
}

function renderListSemester(userClass) {
    if (userClass == null) {
        return null;
    }

    let startYear = +("20" + userClass.substring(0, 2));
    let endYear = startYear + 1;
    let currentDate = new Date();
    let currentYear = currentDate.getFullYear();
    let semester = []

    while (+endYear <= currentYear + 1) {
        semester.push(`1/${startYear}-${endYear}`);
        semester.push(`2/${startYear}-${endYear}`);
        semester.push(`Hè/${startYear}-${endYear}`);
        startYear += 1;
        endYear += 1;
    }

    if(currentYear < endYear){
        semester.pop();
        semester.pop();
    }else if(currentDate.getMonth() < 7){
        semester.pop();
    }
    return semester;
}
