import {Survey} from "../../model/models";

/**
 * type of action to perform in reducer.
 */
export const enum SURVEY_ACTION_TYPE {
    GET, ADD, REMOVE, ADD_QUESTION, UPDATE
}

/**
 * the reducer action indicating what action to perform with a given payload.
 */
export type ReducerAction = {
    type: SURVEY_ACTION_TYPE.GET | SURVEY_ACTION_TYPE.ADD | SURVEY_ACTION_TYPE.REMOVE | SURVEY_ACTION_TYPE.ADD_QUESTION | SURVEY_ACTION_TYPE.UPDATE;
    payload: any;

}

/**
 * reducer for performing crud operations on surveys.
 * @param state the old state to carry out the given action on.
 * @param action the action to perform.
 */
export const surveyReducer = (state: Survey[], action: ReducerAction): Survey[] => {
    switch (action.type) {
        case SURVEY_ACTION_TYPE.GET:
            return action.payload;
        case SURVEY_ACTION_TYPE.ADD:
            return [...state, action.payload];
        case SURVEY_ACTION_TYPE.ADD_QUESTION:
        case SURVEY_ACTION_TYPE.REMOVE:
            return state.filter(survey => survey.primaryKey !== action.payload);
        case SURVEY_ACTION_TYPE.UPDATE:
            let withoutOldSurvey = state.filter(survey => survey.primaryKey !== action.payload.primaryKey);
            return [...withoutOldSurvey, action.payload];
        default:
            return state;
    }

}

