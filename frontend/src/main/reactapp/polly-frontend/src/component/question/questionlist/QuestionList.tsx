import {Question} from "../../../model/models";
import React from "react";
import {QuestionReducerAction} from "../QuestionReducer";
import SingleQuestion from "../singlequestion/SingleQuestion";
import {MdAdd, MdOutlineKeyboardBackspace} from "react-icons/md";
import {SurveyReducerAction} from "../../survey/SurveyReducer";
import surveyService from "../../../services/SurveyService";

interface QuestionProps {
    questions: Question[];
    dispatchQuestions: React.Dispatch<QuestionReducerAction>;
    dispatchSurveys: React.Dispatch<SurveyReducerAction>;
    isQuestionEdit: boolean;
    setQuestionEdit: React.Dispatch<React.SetStateAction<boolean>>;

    //survey: Survey,
}

const QuestionList: React.FC<QuestionProps> = ({questions, dispatchQuestions, dispatchSurveys, isQuestionEdit,setQuestionEdit}) => {

    const closeQuestions = () => {
        setQuestionEdit(false);
        surveyService.getAll(dispatchSurveys);
    }
    const addQuestion = () => {
      
    }

    return (
        <div className='question--list'>
            {isQuestionEdit ? (questions.map((question) =>
                    <SingleQuestion question={question}
                                    dispatch={dispatchQuestions}/>
                )
            ) : null}
            {isQuestionEdit ? (
                <div onClick={addQuestion} className='icons'>
                    <span>
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