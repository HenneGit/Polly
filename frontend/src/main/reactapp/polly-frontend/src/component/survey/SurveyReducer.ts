import {Survey} from "../../model/models";
import SurveyService from "../../services/SurveyService";

export const enum SURVEY_ACTION_TYPE {
    GET, ADD, REMOVE, ADD_QUESTION, UPDATE
}

export type ReducerAction = {
    type: SURVEY_ACTION_TYPE.GET | SURVEY_ACTION_TYPE.ADD | SURVEY_ACTION_TYPE.REMOVE | SURVEY_ACTION_TYPE.ADD_QUESTION | SURVEY_ACTION_TYPE.UPDATE;
    payload: any;

}


export const surveyReducer = (state: Survey[], action: ReducerAction): Survey[] => {
    switch (action.type) {
        case SURVEY_ACTION_TYPE.GET:
            console.log(action.payload);
            return action.payload;
        case SURVEY_ACTION_TYPE.ADD:
            return [...state, action.payload];
        case SURVEY_ACTION_TYPE.ADD_QUESTION:

        case SURVEY_ACTION_TYPE.REMOVE:
            console.log(state);
            console.log(action.payload);

            return state.filter(survey => survey.primaryKey !== action.payload);
        case SURVEY_ACTION_TYPE.UPDATE:
            return state.map(survey => (
                survey.primaryKey === action.payload.primaryKey ? {...survey, survey: action.payload} : survey
            ));
        default:
            return state;
    }

}

async function getData() {

}

