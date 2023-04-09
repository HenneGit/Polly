import React, {Dispatch, SetStateAction, useEffect, useReducer, useRef, useState} from "react";
import {Survey} from "../../model/models";
import {AiFillDelete, AiFillEdit} from "react-icons/ai";
import "./SingleSurvey.css"
import {SURVEY_ACTION_TYPE, surveyReducer} from "./SurveyReducer";
import SurveyService from "../../services/SurveyService";

type Props = {
    survey: Survey
    surveys: Survey[],
    dispatch: any
}

const SingleSurvey = ({survey, surveys, dispatch}: Props) => {
    const [edit, setEdit] = useState<boolean>(false);
    const inputRef = useRef<HTMLInputElement>(null);


    return (
        <form className='survey__single' onSubmit={(e) => {SurveyService.update(survey, dispatch)}}>
            {edit ? (
                <input
                    ref={inputRef}
                    value={survey.name}
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
                <span className="icon" onClick={() => SurveyService.remove(survey.primaryKey, dispatch)}>
              <AiFillDelete/>
            </span>
            </div>
        </form>
    )
}


export default SingleSurvey;