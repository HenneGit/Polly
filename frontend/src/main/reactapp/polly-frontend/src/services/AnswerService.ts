import {Answer} from "../model/models";
import http from "./http-common"
import React from "react";
import {ANSWER_ACTION_TYPE, AnswerReducerAction} from "../component/answer/AnswerReducer";


const getAll = () => {
    return http.get<Array<Answer>>("/answer/get");
};

const get = (id: string) => {
    return http.get<Answer>(`/answer/${id}`);
};

const add = async (data: Answer, dispatch: React.Dispatch<AnswerReducerAction>) => {
    let newAnswer = await http.post<Answer>("/answer/add", data).then(resp => resp.data);
    dispatch({type: ANSWER_ACTION_TYPE.ADD, payload: newAnswer});
};

const update = async (data: Answer, dispatch: React.Dispatch<AnswerReducerAction>) => {
    let updatedQuestion = await http.put<Answer>(`/answer/update`, data).then(resp => resp.data);
    dispatch({type: ANSWER_ACTION_TYPE.UPDATE, payload: updatedQuestion})
};

const remove = async (id: string ,dispatch: React.Dispatch<AnswerReducerAction>) => {
    await http.delete<any>(`/answer/delete/${id}`);
    dispatch({type: ANSWER_ACTION_TYPE.REMOVE, payload: id})

};
const getByQuestionId = async (id: string, dispatch: React.Dispatch<AnswerReducerAction>) => {
    let data = await http.get<Answer>(`/answer/getByQuestionId/${id}`).then(resp => resp.data);
    dispatch({type: ANSWER_ACTION_TYPE.GET_BY_QUESTION_ID, payload: data})
};

const AnswerService = {
    getAll,
    get,
    add,
    update,
    remove,
    getByQuestionId
};

export default AnswerService;