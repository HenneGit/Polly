import {Question} from "../model/models";
import http from "./http-common"
import React from "react";
import {SURVEY_ACTION_TYPE, SurveyReducerAction} from "../component/survey/SurveyReducer";
import {QUESTION_ACTION_TYPE, QuestionReducerAction} from "../component/question/QuestionReducer";


const getAll = async (dispatch: React.Dispatch<any>) => {
    return await http.get<Array<Question>>("/question/get");
};

const get = async (id: string, dispatch: React.Dispatch<any>) => {
    return await http.get<Question>(`/question/${id}`);
};

const add = async (data: Question, dispatch: React.Dispatch<any>) => {
    return await http.post<Question>("/question/add", data);
};

const update = async (data: Question, dispatch: React.Dispatch<any>) => {
    return await http.put<Question>(`/question/update`, data);
};

const remove = async (id: string, dispatch: React.Dispatch<any>) => {
    return await http.delete<any>(`/question/delete/${id}`);
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
    getBySurveyId
};

export default QuestionService;