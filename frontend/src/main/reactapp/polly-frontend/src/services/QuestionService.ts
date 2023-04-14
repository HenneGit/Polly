import {Question} from "../model/models";
import http from "./http-common"
import React from "react";
import {SURVEY_ACTION_TYPE, SurveyReducerAction} from "../component/survey/SurveyReducer";
import {QUESTION_ACTION_TYPE, QuestionReducerAction} from "../component/question/QuestionReducer";


const getAll = async (dispatch: React.Dispatch<QuestionReducerAction>) => {
    return await http.get<Array<Question>>("/question/get");
};

const get = async (id: string, dispatch: React.Dispatch<QuestionReducerAction>) => {
    return await http.get<Question>(`/question/${id}`);
};

const add = async (data: Question, dispatch: React.Dispatch<QuestionReducerAction>) => {
    let newQuestion = await http.post<Question>("/question/add", data).then(resp => resp.data);
    dispatch({type: QUESTION_ACTION_TYPE.ADD, payload: newQuestion})
};

const update = async (data: Question, dispatch: React.Dispatch<any>) => {
    let updatedQuestion = await http.put<Question>(`/question/update`, data).then(resp => resp.data);
    dispatch({type: QUESTION_ACTION_TYPE.UPDATE, payload: updatedQuestion})
};

const remove = async (id: string, dispatch: React.Dispatch<any>) => {
    await http.delete<any>(`/question/delete/${id}`);
    dispatch({type:QUESTION_ACTION_TYPE.REMOVE, payload: id} )
};

const getBySurveyId = async (id: string, dispatchSurveys: React.Dispatch<SurveyReducerAction>, dispatchQuestions: React.Dispatch<QuestionReducerAction>) => {
    let data = await http.get<Question>(`/question/getBySurveyId/${id}`).then(resp => resp.data);
    dispatchSurveys({
        type: SURVEY_ACTION_TYPE.CLEAR,
        payload: null,
    });
    dispatchQuestions({
        type: QUESTION_ACTION_TYPE.GET,
        payload: data,
    });

};


const QuestionService = {
    getAll,
    get,
    add,
    update,
    remove,
    getBySurveyId,
};

export default QuestionService;