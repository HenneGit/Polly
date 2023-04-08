import React, {useEffect, useReducer, useState} from 'react'
import InputField from './component/input/InputField';
import './App.css'
import {Survey} from "./model/models";
import SurveyList from "./component/surveyList/SurveyList";
import {SURVEY_ACTION_TYPE, surveyReducer} from "./component/survey/SurveyReducer";
import SurveyService from "./services/SurveyService";


const App: React.FC = () => {
    let initState: Survey[] = [];

    const [survey, setSurvey] = useState<string>("")
    const [surveys, dispatch] = useReducer(surveyReducer, initState);

    useEffect(() => {
        dispatch({
            type: SURVEY_ACTION_TYPE.GET,
            payload: {primaryKey: "", name: survey, description: ""}
        })
    }, []);

    return (
        <div className='app'>
            <span className='header'>Polly</span>
            <InputField survey={survey} setSurvey={setSurvey}
                        handleAddSurvey={(e) => dispatch({
                            type: SURVEY_ACTION_TYPE.ADD,
                            payload: {primaryKey: "", name: survey, description: ""}
                        })}/>
            <SurveyList surveys={surveys}/>
        </div>
    )
}

export default App
