import {Answer} from "../model/models";
import http from "./http-common"


const getAll = () => {
    return http.get<Array<Answer>>("/answer/get");
};

const get = (id: string) => {
    return http.get<Answer>(`/answer/${id}`);
};

const add = (data: Answer) => {
    return http.post<Answer>("/answer/add", data);
};

const update = (data: Answer) => {
    return http.put<Answer>(`/answer/update`, data);
};

const remove = (id: string) => {
    return http.delete<any>(`/answer/delete/${id}`);
};

const getByQuestionId = (id: string) => {
    return http.get<Answer>(`/question/getByQuestionId/${id}`);
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