import {Survey} from "../../model/models";
import SurveyService from "../../services/SurveyService";

export const enum SURVEY_ACTION_TYPE {
    GET, ADD, REMOVE, ADD_QUESTION, UPDATE
}

export type ReducerAction = {
    type: SURVEY_ACTION_TYPE.GET | SURVEY_ACTION_TYPE.ADD | SURVEY_ACTION_TYPE.REMOVE | SURVEY_ACTION_TYPE.ADD_QUESTION | SURVEY_ACTION_TYPE.UPDATE;
    payload: Survey;
}


export const surveyReducer = (state: Survey[], action: ReducerAction): Survey[] => {
    switch (action.type) {
        case SURVEY_ACTION_TYPE.GET:
            let allSurveys: Survey[] = [];
            setTimeout(() => {
                SurveyService.getAll().then(resp => {
                    for (let data of resp.data) {
                        allSurveys.push({primaryKey: data.primaryKey, name: data.name, description: data.description});
                    }
                });
            }, 1000);
            return allSurveys;
        case SURVEY_ACTION_TYPE.ADD:
            SurveyService.add(action.payload).then((resp) => {
                action.payload.primaryKey = resp.data.primaryKey;
            });
            return [...state, action.payload];
        case SURVEY_ACTION_TYPE.ADD_QUESTION:

        case SURVEY_ACTION_TYPE.REMOVE:
            let id = action.payload.primaryKey;
            console.log(id);
            console.log(state);
            let remove = SurveyService.remove(id);
            let newState = state.filter(survey => survey.primaryKey !== action.payload.primaryKey);
            console.log(newState);
            return newState;
        case SURVEY_ACTION_TYPE.UPDATE:
            let updatedSurvey = SurveyService.update(action.payload);
            return state.map(survey => (
                survey.primaryKey === action.payload.primaryKey ? {...survey, survey: action.payload} : survey
            ));
        default:
            return state;
    }

}

