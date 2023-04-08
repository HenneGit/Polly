import React, {Dispatch, SetStateAction, useReducer} from "react";
import {Survey} from "../../model/models";
import "./SurveyList.css"
import SingleSurvey from "../survey/SingleSurvey";
import {SURVEY_ACTION_TYPE, surveyReducer} from "../survey/SurveyReducer";

interface Props {
    surveys: Survey[];
}

const SurveyList: React.FC<Props> = ({surveys}) => {
    const [state, dispatch] = useReducer(surveyReducer, surveys)

    return <div className='surveys'>
        {surveys.map((survey) =>
            <SingleSurvey survey={survey}
                          surveys={state}/>
        )}

    </div>


}

export default SurveyList;