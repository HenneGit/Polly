import {Question, Survey} from "../../../model/models";
import React from "react";
import {QuestionReducerAction} from "../QuestionReducer";
import SingleQuestion from "../singlequestion/SingleQuestion";
import {MdAdd, MdOutlineKeyboardBackspace} from "react-icons/md";
import {SurveyReducerAction} from "../../survey/SurveyReducer";
import surveyService from "../../../services/SurveyService";
import questionService from "../../../services/QuestionService";

interface QuestionProps {
    questions: Question[];
    dispatchQuestions: React.Dispatch<QuestionReducerAction>;
    dispatchSurveys: React.Dispatch<SurveyReducerAction>;
    isQuestionEdit: boolean;
    setQuestionEdit: React.Dispatch<React.SetStateAction<boolean>>;
    survey: Survey,
}

const QuestionList: React.FC<QuestionProps> = ({questions, dispatchQuestions, dispatchSurveys, isQuestionEdit,setQuestionEdit, survey}) => {
    console.log(survey);
    const closeQuestions = () => {
        setQuestionEdit(false);
        surveyService.getAll(dispatchSurveys);
    }
    const addQuestion = () => {
        questionService.add({
            primaryKey: "",
            question: "",
            name: "New Question",
            description: "",
            surveyPk: survey.primaryKey
        }, dispatchQuestions);
    }

    return (
        <div className='question--list'>
            {isQuestionEdit ? (questions.map((question) =>
                    <SingleQuestion question={question}
                                    dispatch={dispatchQuestions} isEditQuestion={question.question === ""}/>
                )
            ) : null}
            {isQuestionEdit ? (
                <div  className='icons'>
                    <span onClick={addQuestion}>
                        <MdAdd/>
                    </span>
                    <span onClick={closeQuestions}>
                        <MdOutlineKeyboardBackspace/>
                    </span>
                </div>
            ) : null}
        </div>
    );


}

export default QuestionList;