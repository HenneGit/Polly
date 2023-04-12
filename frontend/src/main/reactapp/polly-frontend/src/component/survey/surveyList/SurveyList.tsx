import React from "react";
import "./SurveyList.css"
import {Survey} from "../../../model/models";
import SingleSurvey from "../singlesurvey/SingleSurvey";
import {SurveyReducerAction} from "../SurveyReducer";
import {QuestionReducerAction} from "../../question/QuestionReducer";

interface Props {
    surveys: Survey[];
    dispatchSurveys: React.Dispatch<SurveyReducerAction>;
    dispatchQuestions: React.Dispatch<QuestionReducerAction>;
    setQuestionEdit: React.Dispatch<React.SetStateAction<boolean>>;
    setActiveSurvey: React.Dispatch<React.SetStateAction<Survey>>
}

const SurveyList: React.FC<Props> = ({surveys, dispatchSurveys, dispatchQuestions, setQuestionEdit, setActiveSurvey}) => {
    return <div className='surveys'>
        {surveys.length !== 0 ? surveys.sort((a, b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0)).map((survey) =>
            <SingleSurvey survey={survey}
                          dispatchSurveys={dispatchSurveys} dispatchQuestions={dispatchQuestions} setQuestionEdit={setQuestionEdit} setActiveSurvey={setActiveSurvey}/>
        ) : null}
    </div>;
}

export default SurveyList;