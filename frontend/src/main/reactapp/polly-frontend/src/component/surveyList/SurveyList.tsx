import React, {Dispatch, SetStateAction, useReducer} from "react";
import {Survey} from "../../model/models";
import "./SurveyList.css"
import SingleSurvey from "../survey/SingleSurvey";
import {SURVEY_ACTION_TYPE, surveyReducer} from "../survey/SurveyReducer";

interface Props {
    surveys: Survey[];
    dispatch: any
}

const SurveyList: React.FC<Props> = ({surveys, dispatch}) => {

    return <div className='surveys'>
        {surveys.map((survey) =>
            <SingleSurvey survey={survey}
                          surveys={surveys} dispatch={dispatch}/>
        )}

    </div>


}

export default SurveyList;