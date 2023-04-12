import React, {useEffect, useReducer, useState} from 'react'
import InputField from './component/input/InputField';
import './App.css'
import {Question, Survey} from "./model/models";
import {surveyReducer} from "./component/survey/SurveyReducer";
import surveyService from "./services/SurveyService";
import SurveyList from "./component/survey/surveyList/SurveyList";
import {questionReducer} from "./component/question/QuestionReducer";
import QuestionList from "./component/question/questionlist/QuestionList";


const App: React.FC = () => {
    let initState: Survey[] = [];
    let initQuestionState: Question[] = [];
    const [survey, setSurvey] = useState<string>("")
    const [surveys, dispatchSurveys] = useReducer(surveyReducer, initState);
    const [activeSurvey, setActiveSurvey] = useState<Survey>({primaryKey:"", name:"", description:""});
    const [questions, dispatchQuestions] = useReducer(questionReducer, initQuestionState);
    const [isQuestionEdit, setQuestionEdit] = useState<boolean>(false)

    useEffect(() => {
        surveyService.getAll(dispatchSurveys);
    }, []);

    return (
        <div className='app'>
            <span className='header'>Polly</span>
            <InputField survey={survey} setSurvey={setSurvey}
                        handleAddSurvey={(e) => surveyService.add({
                            primaryKey: "",
                            name: survey,
                            description: ""
                        }, dispatchSurveys)}/>
            <SurveyList surveys={surveys} dispatchSurveys={dispatchSurveys} dispatchQuestions={dispatchQuestions} setQuestionEdit={setQuestionEdit} setActiveSurvey={setActiveSurvey}/>
            <QuestionList questions={questions} dispatchQuestions={dispatchQuestions} isQuestionEdit={isQuestionEdit} setQuestionEdit={setQuestionEdit} dispatchSurveys={dispatchSurveys} survey={activeSurvey}/>
        </div>
    );
}

export default App
