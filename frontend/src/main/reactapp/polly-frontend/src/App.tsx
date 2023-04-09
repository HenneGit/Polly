import React, {useEffect, useReducer, useState} from 'react'
import InputField from './component/input/InputField';
import './App.css'
import {Survey} from "./model/models";
import SurveyList from "./component/surveyList/SurveyList";
import {surveyReducer} from "./component/survey/SurveyReducer";
import surveyService from "./services/SurveyService";


const App: React.FC = () => {
    let initState: Survey[] = [];

    const [survey, setSurvey] = useState<string>("")
    const [surveys, dispatch] = useReducer(surveyReducer, initState);

    useEffect(() => {
        surveyService.getAll(dispatch);
    }, []);

    return (
        <div className='app'>
            <span className='header'>Polly</span>
            <InputField survey={survey} setSurvey={setSurvey}
                        handleAddSurvey={(e) => surveyService.add({primaryKey:"", name:survey, description: ""}, dispatch)}/>
            <SurveyList surveys={surveys} dispatch={dispatch}/>
        </div>
    )
}

export default App
