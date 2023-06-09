import {Answer, Question} from "../../../model/models";
import React, {useEffect, useReducer, useRef, useState} from "react";
import {QuestionReducerAction} from "../QuestionReducer";
import {MdDone, MdDelete} from "react-icons/md";
import {RxCross2} from "react-icons/rx";
import QuestionService from "../../../services/QuestionService";
import {AiFillEdit} from "react-icons/ai";
import "./SingleQuestion.css";
import AnswerList from "../../answer/answerlist/AnswerList";
import {answerReducer} from "../../answer/AnswerReducer";
import surveyService from "../../../services/SurveyService";
import AnswerService from "../../../services/AnswerService";


type Props = {
    question: Question,
    dispatch: React.Dispatch<QuestionReducerAction>;
    isEditQuestion: boolean
}

const SingleQuestion = ({question, dispatch, isEditQuestion}: Props) => {
    let initAnswers: Answer[] = [];
    const [newName, setEditQuestionName] = useState<string>(question.name);
    const [newDescription, setEditDescription] = useState<string>(question.description);
    const [newQuestion, setEditQuestion] = useState<string>(question.question);
    const [newOrderNumber, setOrderNumber] = useState<number>(question.orderNumber);
    const [isEdit, setEdit] = useState<boolean>(isEditQuestion);
    const inputRef = useRef<HTMLInputElement>(null);
    const [newAnswers, dispatchAnswers] = useReducer(answerReducer, initAnswers);

    useEffect(() => {
        console.log(question.primaryKey);
        AnswerService.getByQuestionId(question.primaryKey, dispatchAnswers);
    }, []);

    const handleUpdate = () => {
        QuestionService.update({
            primaryKey: question.primaryKey,
            question: newQuestion,
            name: newName,
            description: newDescription,
            surveyPk: question.surveyPk,
            orderNumber: question.orderNumber
        }, dispatch);
        setEdit(false);
    }
    const deleteQuestion = () => {
        QuestionService.remove(question.primaryKey, dispatch);
    }


    const StaticQuestion = () => {
        return (<div className="question--single">
            <div className="question--single__details">
                <span>{question.orderNumber}</span>
                <span>{question.name}</span>
                <span>{question.description}</span>
                <span>{question.question}</span>
            </div>
            <div className='question__single--icons'>
                        <span className="icon" onClick={() => setEdit(true)}>
                            <AiFillEdit/>
                        </span>
                <span className="icon" onClick={() => deleteQuestion()}>
                            <MdDelete/>
                        </span>
            </div>
            <AnswerList answers={newAnswers} question={question} dispatchAnswers={dispatchAnswers}/>
        </div>)
    }

    return (
        <form>
            {isEdit ? (<div className="question--single">
                <div className="question--single__details">
                    <input ref={inputRef} className="survey__single--input"
                           onChange={(e) => setOrderNumber(parseInt(e.target.value))}
                           placeholder="Question Number"
                           value={newOrderNumber}/>
                    <input ref={inputRef} className="survey__single--input"
                           onChange={(e) => setEditQuestionName(e.target.value)}
                           placeholder="Name"
                           value={newName}/>
                    <input ref={inputRef} className="survey__single--input"
                           onChange={(e) => setEditDescription(e.target.value)}
                           placeholder="Description"
                           value={newDescription}/>
                    <input ref={inputRef} className="survey__single--input"
                           onChange={(e) => setEditQuestion(e.target.value)}
                           placeholder="Question"
                           value={newQuestion}/>
                </div>
                <div className='question__single--icons'>
                    <span className="icon" onClick={() => handleUpdate()}>
                        <MdDone/>
                    </span>
                    <span className="icon" onClick={() => setEdit(false)}>
                        <RxCross2/>
                    </span>
                    <span className="icon" onClick={() => deleteQuestion()}>
                        <MdDelete/>
                    </span>
                    <AnswerList answers={newAnswers} question={question} dispatchAnswers={dispatchAnswers}/>
                </div>
            </div>) : (<StaticQuestion/>)}
        </form>
    );

}


export default SingleQuestion;