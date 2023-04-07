import React, {useEffect, useRef, useState} from "react";
import {Survey} from "../../model/models";
import {AiFillEdit, AiFillDelete} from "react-icons/ai";
import SurveyService from "../../services/SurveyService";
import "./SingleSurvey.css"

type Props = {
    survey: Survey
    surveys: Survey[],
    setSurveys: React.Dispatch<React.SetStateAction<Array<Survey>>>;
}


const SingleSurvey = ({survey, surveys, setSurveys}: Props) => {
    const [edit, setEdit] = useState<boolean>(false);
    const [editSurvey, setEditSurvey] = useState<string>(survey.name);

    const handleDelete = async (id: string) => {
        await SurveyService.remove(id);
    };

    const handleEdit = async (e: React.FormEvent, updatedSurvey: Survey) => {
        let resp = await SurveyService.update(updatedSurvey);
        console.log(resp);
        setSurveys(
            surveys.map(survey => (
                survey.primaryKey === updatedSurvey.primaryKey ? {...survey, survey: editSurvey} : survey
            )));
        setEdit(false);

    };
    const inputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        inputRef.current?.focus();
    }, [edit]);


    return (
        <form className='survey__single' onSubmit={(e) => handleEdit(e, survey)}>
            {edit ? (
                <input
                    ref={inputRef}
                    value={editSurvey}
                    onChange={(e) => setEditSurvey(e.target.value)}
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
                <span className="icon" onClick={() => handleDelete(survey.primaryKey)}>
              <AiFillDelete/>
            </span>
            </div>
        </form>
    )
}


export default SingleSurvey;