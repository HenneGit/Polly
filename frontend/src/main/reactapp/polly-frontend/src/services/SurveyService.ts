import {Survey} from "../model/models";
import http from "./http-common"
import {SURVEY_ACTION_TYPE} from "../component/survey/SurveyReducer";


const getAll = async (dispatch: any) => {
    let response = await http.get<Array<Survey>>("/survey/get").then(resp => resp.data);
    console.log(response);
    return dispatch({type: SURVEY_ACTION_TYPE.GET, payload: response});
};

const get = async (id: string, dispatch: any) => {
    let response = await http.get<Survey>(`/survey/${id}`).then(resp => resp.data);
    dispatch({
        type: SURVEY_ACTION_TYPE.GET,
        payload: response,
    })
};

const add = async (data: Survey, dispatch: any) => {
    let response = await http.post<Survey>("/survey/add", data).then(resp => resp.data);
    dispatch({
        type: SURVEY_ACTION_TYPE.ADD,
        payload: response,
    });
};

const update = async (data: Survey, dispatch: any) => {
    let resp = await http.put<any>(`/survey/update`, data).then(resp => resp.data);
    dispatch({type: SURVEY_ACTION_TYPE.UPDATE, payload: resp})
};

const remove = async (id: string, dispatch : any) => {
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