import {Question} from "../../../model/models";
import React from "react";
import {QuestionReducerAction} from "../QuestionReducer";
import SingleQuestion from "../singlequestion/SingleQuestion";
import {MdAdd} from "react-icons/md";

interface QuestionProps {
    questions: Question[];
    dispatchQuestions: React.Dispatch<QuestionReducerAction>;
    isQuestionEdit: boolean;
    //survey: Survey,
}

const QuestionList: React.FC<QuestionProps> = ({questions, dispatchQuestions, isQuestionEdit}) => {


    return (
        <div className='question--list'>
            {isQuestionEdit ? (questions.map((question) =>
                    <SingleQuestion question={question}
                                    dispatch={dispatchQuestions}/>
                )
            ) : null}
            {isQuestionEdit ? (<span>
                <MdAdd/>
            </span>) : null}
        </div>
    );


}

export default QuestionList;