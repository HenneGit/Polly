import {Survey} from "../model/models";
import http from "./http-common"


const getAll = () => {
        return http.get<Array<Survey>>("/survey/get");
};

const get = (id: string) => {
        return http.get<Survey>(`/survey/${id}`);
};

const add = (data: Survey) => {
        return http.post<Survey>("/survey/add", data);
};

const update = (data: Survey) => {
        return http.put<any>(`/survey/update`, data);
};

const remove = (id: string) => {
        return http.delete<any>(`/survey/delete/${id}`);
};

const SurveyService = {
        getAll,
        get,
        add,
        update,
        remove,
};

export default SurveyService;