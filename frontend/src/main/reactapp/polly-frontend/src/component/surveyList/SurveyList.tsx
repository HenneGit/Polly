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
        {surveys.sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0)).map((survey) =>
            <SingleSurvey survey={survey}
                          dispatch={dispatch}/>
        )}
    </div>
}

export default SurveyList;