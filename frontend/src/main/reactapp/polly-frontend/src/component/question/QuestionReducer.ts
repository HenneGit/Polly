/**
 * type of action to perform in reducer.
 */
import {Question} from "../../model/models";

export const enum QUESTION_ACTION_TYPE {
    GET, ADD, REMOVE, ADD_ANSWER, UPDATE
}

/**
 * the reducer action indicating what action to perform with a given payload.
 */
export type QuestionReducerAction = {
    type: QUESTION_ACTION_TYPE.GET | QUESTION_ACTION_TYPE.ADD | QUESTION_ACTION_TYPE.REMOVE | QUESTION_ACTION_TYPE.ADD_ANSWER | QUESTION_ACTION_TYPE.UPDATE;
    payload: any;

}

/**
 * reducer for performing crud operations on surveys.
 * @param state the old state to carry out the given action on.
 * @param action the action to perform.
 */
export const questionReducer = (state: Question[], action: QuestionReducerAction): Question[] => {
    switch (action.type) {
        case QUESTION_ACTION_TYPE.GET:
            return action.payload;
        case QUESTION_ACTION_TYPE.ADD:
            return [...state, action.payload];
        case QUESTION_ACTION_TYPE.ADD_ANSWER:
        case QUESTION_ACTION_TYPE.REMOVE:
            return state.filter(question => question.primaryKey !== action.payload);
        case QUESTION_ACTION_TYPE.UPDATE:
            let withoutOldQuestion = state.filter(question => question.primaryKey !== action.payload.primaryKey);
            return [...withoutOldQuestion, action.payload];
        default:
            return state;
    }

}