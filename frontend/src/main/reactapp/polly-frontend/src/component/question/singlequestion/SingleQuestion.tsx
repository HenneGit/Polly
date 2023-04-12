import {Question} from "../../../model/models";
import React, {useState} from "react";
import {QuestionReducerAction} from "../QuestionReducer";
import {MdDone, MdDelete} from "react-icons/md";
import {RxCross2} from "react-icons/rx";
import QuestionService from "../../../services/QuestionService";


type Props = {
    question: Question,
    dispatch: React.Dispatch<QuestionReducerAction>;
    isEditQuestion: boolean
}

const SingleQuestion = ({question, dispatch, isEditQuestion}: Props) => {

    const [newName, setEditQuestionName] = useState<string>(question.name);
    const [newDescription, setEditDescription] = useState<string>(question.description);
    const [newQuestion, setEditQuestion] = useState<string>(question.question);

    const handleUpdate = () => {
        QuestionService.update({primaryKey: question.primaryKey, question: newQuestion, name: newName,description: newDescription, surveyPk: question.surveyPk }, dispatch);
    }
    const deleteQuestion = () => {
        QuestionService.remove(question.primaryKey, dispatch);

    }

    const EditQuestion = () => {
        return (<form>
            <input onChange={(e) => setEditQuestionName(e.target.value)} placeholder="Name"/>
            <input onChange={(e) => setEditDescription(e.target.value)} placeholder="Description"/>
            <input onChange={(e) => setEditQuestion(e.target.value)} placeholder="Question"/>
            <div className='question__single--icons'>
                        <span className="icon" onClick={() => handleUpdate()}>
                            <MdDone/>
                        </span>
                <span className="icon" onClick={() => isEditQuestion = false}>
                            <RxCross2/>
                        </span><span className="icon" onClick={() => deleteQuestion()}>
                            <MdDelete/>
                        </span>
            </div>
        </form>)
    }

    const StaticQuestion = () => {
        return (<div className="question--single">
            <div className="question--single__details">

            </div>
        </div>)
    }

    return (
        <form>
            {isEditQuestion ? (<EditQuestion/>) : (<StaticQuestion/>)}
        </form>
    );

}


export default SingleQuestion;