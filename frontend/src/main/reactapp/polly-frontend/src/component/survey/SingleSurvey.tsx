import React, {useEffect, useReducer, useRef, useState} from "react";
import {Survey} from "../../model/models";
import {AiFillDelete, AiFillEdit} from "react-icons/ai";
import "./SingleSurvey.css"
import {ReducerAction, SURVEY_ACTION_TYPE, surveyReducer} from "./SurveyReducer";

type Props = {
    survey: Survey
    surveys: Survey[],
    setSurveys: React.Dispatch<React.SetStateAction<Array<Survey>>>;
}

const SingleSurvey = ({survey, surveys, setSurveys}: Props) => {
    const [state, dispatch] = useReducer(surveyReducer, surveys);
    const [edit, setEdit] = useState<boolean>(false);

    const inputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        inputRef.current?.focus();
    }, [edit]);


    return (
        <form className='survey__single' onSubmit={(e) => dispatch({type: SURVEY_ACTION_TYPE.ADD, payload : survey})}>
            {edit ? (
                <input
                    ref={inputRef}
                    value={survey.name}
                    onChange={(e) => dispatch({type: SURVEY_ACTION_TYPE.UPDATE, payload : survey})}
                    className="survey__single--text"
                />
            ) : (
                <span className="survey__single--text">{survey.name}</span>
            )}
            <div>
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
                <span className="icon" onClick={() => dispatch({type: SURVEY_ACTION_TYPE.REMOVE, payload : survey})}>
              <AiFillDelete/>
            </span>
            </div>
        </form>
    )
}


export default SingleSurvey;