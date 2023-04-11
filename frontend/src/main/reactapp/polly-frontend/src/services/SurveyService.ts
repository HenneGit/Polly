import {Survey} from "../model/models";
import http from "./http-common"
import {SURVEY_ACTION_TYPE, SurveyReducerAction} from "../component/survey/SurveyReducer";
import React from "react";


const getAll = async (dispatch: React.Dispatch<SurveyReducerAction>) => {
    let response = await http.get<Array<Survey>>("/survey/get").then(resp => resp.data);
    return dispatch({type: SURVEY_ACTION_TYPE.GET, payload: response});
};

const get = async (id: string, dispatch: React.Dispatch<SurveyReducerAction>) => {
    let response = await http.get<Survey>(`/survey/${id}`).then(resp => resp.data);
    dispatch({
        type: SURVEY_ACTION_TYPE.GET,
        payload: response,
    })
};

const add = async (data: Survey, dispatch: React.Dispatch<SurveyReducerAction>) => {
    let response = await http.post<Survey>("/survey/add", data).then(resp => resp.data);
    dispatch({
        type: SURVEY_ACTION_TYPE.ADD,
        payload: response,
    });
};

const update = async (data: Survey, dispatch: React.Dispatch<SurveyReducerAction>) => {
    let resp = await http.put<any>(`/survey/update`, data).then(resp => resp.data);
    dispatch({type: SURVEY_ACTION_TYPE.UPDATE, payload: resp})
};

const remove = async (id: string, dispatch : React.Dispatch<SurveyReducerAction>) => {
    await http.delete<any>(`/survey/delete/${id}`).then(resp => resp.data);
    dispatch({type: SURVEY_ACTION_TYPE.REMOVE, payload: id})
};

const SurveyService = {
    getAll,
    get,
    add,
    update,
    remove,
};

export default SurveyService;