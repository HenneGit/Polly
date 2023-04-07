import {Question} from "../model/models";
import http from "./http-common"


const getAll = async () => {
    return await http.get<Array<Question>>("/question/get");
};

const get = async (id: string) => {
    return await http.get<Question>(`/question/${id}`);
};

const add = async (data: Question) => {
    return await http.post<Question>("/question/add", data);
};

const update = async (data: Question) => {
    return await http.put<Question>(`/question/update`, data);
};

const remove = async (id: string) => {
    return await http.delete<any>(`/question/delete/${id}`);
};

const getBySurveyId = async (id: string) => {
    return await http.get<Question>(`/question/getBySurveyId/${id}`);
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