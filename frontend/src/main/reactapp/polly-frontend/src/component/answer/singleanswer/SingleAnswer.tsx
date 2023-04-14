import {Answer, Question} from "../../../model/models";
import React, {useState} from "react";
import {AnswerReducerAction} from "../AnswerReducer";
import {AiFillEdit} from "react-icons/ai";
import {MdDelete, MdDone} from "react-icons/md";
import AnswerService from "../../../services/AnswerService";
import {RxCross2} from "react-icons/rx";

interface AnswerProps {
    answer: Answer;
    question: Question,
    dispatch: React.Dispatch<AnswerReducerAction>
}


const SingleAnswer: React.FC<AnswerProps> = ({answer, question, dispatch}) => {
    const [newAnswerText, setEditAnswerText] = useState<string>(answer.answerText);
    const [isEdit, setEdit] = useState<boolean>(false);

    const deleteAnswer = () => {
        AnswerService.remove(answer.primaryKey, dispatch);
    };

    const handleUpdate = () => {
        AnswerService.update({primaryKey: answer.primaryKey, answerText: newAnswerText, questionPk: answer.questionPk}, dispatch);
        setEdit(false);
    };

    const StaticAnswer = () => {
        return (<div className='answer--single'>
            <span>{answer.answerText}</span>
            <div className="answer--icons">
                <div className='question__single--icons'>
                        <span className="icon" onClick={() => setEdit(true)}>
                            <AiFillEdit/>
                        </span>
                    <span className="icon" onClick={() => deleteAnswer()}>
                            <MdDelete/>
                        </span>
                </div>

            </div>
        </div>)
    }

    return (<div>
        {isEdit ? (<div>
                <input className="survey__single--input"
                       onChange={(e) => setEditAnswerText(e.target.value)}
                       placeholder="AnswerText"
                       value={newAnswerText}/>
                <div className='question__single--icons'>
                    <span className="icon" onClick={() => handleUpdate()}>
                        <MdDone/>
                    </span>
                    <span className="icon" onClick={() => setEdit(false)}>
                        <RxCross2/>
                    </span>
                </div>
            </div>
        ) : (<StaticAnswer/>)}
    </div>);
};

export default SingleAnswer;