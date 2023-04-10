import React, {useRef, useState} from "react";
import {Survey} from "../../model/models";
import {AiFillDelete, AiFillEdit} from "react-icons/ai";
import {MdDone} from "react-icons/md";
import "./SingleSurvey.css"
import SurveyService from "../../services/SurveyService";

type Props = {
    survey: Survey
    dispatch: any
}

const SingleSurvey = ({survey, dispatch}: Props) => {
    const [edit, setEdit] = useState<boolean>(false);
    const [newName, setEditSurveyName] = useState<string>(survey.name);
    const [newDescription, setEditSurveyDescription] = useState<string>(survey.description);
    const inputRef = useRef<HTMLInputElement>(null);

    const handleUpdate = () => {
        console.log(newName);
        console.log(newDescription);
        SurveyService.update({primaryKey: survey.primaryKey, name: newName, description: newDescription}, dispatch)
        setEdit(false);
    }


    return (
        <form className='survey__single' onSubmit={(e) => {
        }}>
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
                    <span className="icon" onClick={() => handleUpdate()}>
                    <MdDone/>
                </span>
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
                <span className="icon" onClick={() => SurveyService.remove(survey.primaryKey, dispatch)}>
                    <AiFillDelete/>
                </span>
            </div>
        </form>
    )
}


export default SingleSurvey;