import {Answer, Question} from "../../../model/models";
import React from "react";
import SingleAnswer from "../singleanswer/SingleAnswer";
import {AnswerReducerAction} from "../AnswerReducer";
import {MdAdd} from "react-icons/md";
import AnswerService from "../../../services/AnswerService";


interface AnswerListProps {
    answers: Answer[];
    question: Question,
    dispatchAnswers: React.Dispatch<AnswerReducerAction>;
}


const AnswerList: React.FC<AnswerListProps> = ({answers, question, dispatchAnswers}) => {



    const addAnswer = () => {
        AnswerService.add({answerText: "New Answer", primaryKey: "", questionPk: question.primaryKey}, dispatchAnswers);
    }

    return (<div className='answer--list'>
        {answers.map((answer) => <SingleAnswer question={question} answer={answer} dispatch={dispatchAnswers}/>)}
        <span onClick={addAnswer}>
            <MdAdd/>
        </span>
    </div>);
};

export default AnswerList;