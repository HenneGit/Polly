import React, {useRef, useState} from "react";
import {AiFillDelete, AiFillEdit} from "react-icons/ai";
import {MdDone, MdOutlineAddchart} from "react-icons/md";
import {RxCross2} from "react-icons/rx";
import "./SingleSurvey.css"
import {Survey} from "../../../model/models";
import SurveyService from "../../../services/SurveyService";
import QuestionService from "../../../services/QuestionService";
import {SurveyReducerAction} from "../SurveyReducer";
import {QuestionReducerAction} from "../../question/QuestionReducer";

type Props = {
    survey: Survey,
    dispatchSurveys: React.Dispatch<SurveyReducerAction>,
    dispatchQuestions: React.Dispatch<QuestionReducerAction>;
    setQuestionEdit:  React.Dispatch<React.SetStateAction<boolean>>;
    setActiveSurvey: React.Dispatch<React.SetStateAction<Survey>>

}

const SingleSurvey = ({survey, dispatchSurveys, dispatchQuestions, setQuestionEdit, setActiveSurvey}: Props) => {
    const [edit, setEdit] = useState<boolean>(false);
    const [newName, setEditSurveyName] = useState<string>(survey.name);
    const [newDescription, setEditSurveyDescription] = useState<string>(survey.description);
    const inputRef = useRef<HTMLInputElement>(null);

    const handleUpdate = () => {
        SurveyService.update({primaryKey: survey.primaryKey, name: newName, description: newDescription}, dispatchSurveys)
        setEdit(false);
    }

    const handleEditQuestions = () => {
        setQuestionEdit(true);
        setActiveSurvey(survey);
        QuestionService.getBySurveyId(survey.primaryKey, dispatchSurveys, dispatchQuestions);
    };


    return (
        <form className='survey__single'>
            {edit ? (
                <div className='survey__single--details'>
                    <input
                        ref={inputRef}
                        value={newName}
                        onChange={(e) => setEditSurveyName(e.target.value)}
                        className="survey__single--text"
                    />
                    <input
                        ref={inputRef}
                        value={newDescription}
                        onChange={(e) => setEditSurveyDescription(e.target.value)}
                        className="survey__single--text"
                    />
                    <div className='survey__single--icons'>
                        <span className="icon" onClick={() => handleUpdate()}>
                            <MdDone/>
                        </span>
                        <span className="icon" onClick={() => setEdit(false)}>
                            <RxCross2/>
                        </span>
                    </div>
                </div>
            ) : (
                <div className='survey__single--details'>
                    <span className="survey__single--text">{survey.name}</span>
                    <span className="survey__single--description">{survey.description}</span>
                </div>
            )}
            <div className='survey__single--icons'>
            <span
                className="icon"
                onClick={() => {
                    if (!edit) {
                        setEdit(!edit);
                    }
                }}
            >
              <AiFillEdit/>
            </span>
                <span className="icon" onClick={() => SurveyService.remove(survey.primaryKey, dispatchSurveys)}>
                    <AiFillDelete/>
                </span>
                <span className="icon" onClick={() => handleEditQuestions()}>
                    <MdOutlineAddchart/>
                </span>
            </div>
        </form>
    )
}


export default SingleSurvey;