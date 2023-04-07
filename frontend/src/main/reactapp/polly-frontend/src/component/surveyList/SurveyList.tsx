import React from "react";
import {Survey} from "../../model/models";
import "./SurveyList.css"
import SingleSurvey from "../survey/SingleSurvey";

interface Props {
    surveys: Survey[];
    setSurveys : React.Dispatch<React.SetStateAction<Survey[]>>
}


const SurveyList : React.FC<Props> = ({surveys, setSurveys}) => {
    return <div className='surveys'>
        {surveys.map((survey) =>
            <SingleSurvey survey={survey} setSurveys={setSurveys} surveys={surveys}></SingleSurvey>
        )}

    </div>



}

export default SurveyList;