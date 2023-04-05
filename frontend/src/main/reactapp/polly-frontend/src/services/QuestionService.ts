import {Question} from "../model/models";
import http from "./http-common"


const getAll = () => {
    return http.get<Array<Question>>("/question/get");
};

const get = (id: string) => {
    return http.get<Question>(`/question/${id}`);
};

const add = (data: Question) => {
    return http.post<Question>("/question/add", data);
};

const update = (data: Question) => {
    return http.put<Question>(`/question/update`, data);
};

const remove = (id: string) => {
    return http.delete<any>(`/question/delete/${id}`);
};

const getBySurveyId = (id: string) => {
    return http.get<Question>(`/question/getBySurveyId/${id}`);
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