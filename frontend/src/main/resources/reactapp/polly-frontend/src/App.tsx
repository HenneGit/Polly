import React, {useState} from 'react'
import InputField from './component/input/InputField';
import './App.css'
import {Survey} from "./model/models";

const App: React.FC = () => {

    const [survey, setSurvey] = useState<string>("")
    const [surveys, setSurveys] = useState<Survey[]>([])

    let mySurveys = fetch("http://localhost:8080/survey/get").then(resp => resp.json()).then(resp => {
        for (let s of resp) {
        }
    });
    const handleAddSurvey = (e: React.FormEvent) => {
        e.preventDefault();
        if (survey) {
            setSurveys([...surveys, {id: "", name: survey, description: ""}])
            setSurvey("");
        }
    };
    console.log(surveys);
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
