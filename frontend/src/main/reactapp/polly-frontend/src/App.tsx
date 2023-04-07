import React, {useState} from 'react'
import InputField from './component/input/InputField';
import './App.css'
import SurveyService from "./services/SurveyService";
import {Survey} from "./model/models";
import SurveyList from "./component/surveyList/SurveyList";

const App: React.FC = () => {

    const [survey, setSurvey] = useState<string>("")
    const [surveys, setSurveys] = useState<Survey[]>([])

    let surveys1 = SurveyService.getAll().then((resp :any) => {
        setSurveys(resp.data);
    });
    const handleAddSurvey = (e: React.FormEvent) => {
        e.preventDefault();
    };


    return (
        <div className='app'>
            <span className='header'>Polly</span>
            <InputField survey={survey} setSurvey={setSurvey} handleAddSurvey={handleAddSurvey}/>
            <SurveyList surveys={surveys} setSurveys={setSurveys}/>
        </div>
    )
}

export default App
