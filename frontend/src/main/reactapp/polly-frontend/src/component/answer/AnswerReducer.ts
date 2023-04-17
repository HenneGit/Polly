
import {Answer} from "../../model/models";
/**
 * type of action to perform in reducer.
 */
export const enum ANSWER_ACTION_TYPE {
    GET, ADD, REMOVE, ADD_ANSWER, UPDATE, GET_BY_QUESTION_ID
}

/**
 * the reducer action indicating what action to perform with a given payload.
 */
export type AnswerReducerAction = {
    type: ANSWER_ACTION_TYPE.GET_BY_QUESTION_ID | ANSWER_ACTION_TYPE.ADD | ANSWER_ACTION_TYPE.REMOVE |  ANSWER_ACTION_TYPE.UPDATE;
    payload: any;
}

/**
 * reducer for performing crud operations on questions.
 * @param state the old state to carry out the given action on.
 * @param action the action to perform.
 */
export const answerReducer = (state: Answer[], action: AnswerReducerAction): Answer[] => {
    switch (action.type) {
        case ANSWER_ACTION_TYPE.GET_BY_QUESTION_ID:
            return action.payload;
        case ANSWER_ACTION_TYPE.ADD:
            return [...state, action.payload];
        case ANSWER_ACTION_TYPE.REMOVE:
            return state.filter(answer => answer.primaryKey !== action.payload);
        case ANSWER_ACTION_TYPE.UPDATE:
            console.log(action.payload);
            let withoutOldAnswer = state.filter(answer => answer.primaryKey !== action.payload.primaryKey);
            return [...withoutOldAnswer, action.payload];
        default:
            return state;
    }

}