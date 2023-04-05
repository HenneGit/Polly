import React, {useState} from 'react'
import InputField from './component/input/InputField';
import './App.css'
import SurveyService from "./services/SurveyService";
import {Survey} from "./model/models";

const App: React.FC = () => {

    const [survey, setSurvey] = useState<string>("")
    const [surveys, setSurveys] = useState<Survey[]>([])

    let surveys1 = SurveyService.getAll().then((resp :any) => {
        console.log(resp.data);
    });
    const handleAddSurvey = (e: React.FormEvent) => {
        e.preventDefault();
        if (survey) {
            setSurveys([...surveys, {id: "", name: survey, description: ""}])
            setSurvey("");
        }
    };


    return (
        <div className='app'>
            <span className='header'>Polly</span>
            <InputField survey={survey} setSurvey={setSurvey} handleAddSurvey={handleAddSurvey}/>
            {surveys.map((s) => (
                <li>{s.name}</li>
            ))}
        </div>
    )
}

export default App
