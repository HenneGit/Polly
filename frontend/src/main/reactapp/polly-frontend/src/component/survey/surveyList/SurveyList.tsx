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
}

const SurveyList: React.FC<Props> = ({surveys, dispatchSurveys, dispatchQuestions, setQuestionEdit}) => {
    return <div className='surveys'>
        {surveys.length !== 0 ? surveys.sort((a, b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0)).map((survey) =>
            <SingleSurvey survey={survey}
                          dispatchSurveys={dispatchSurveys} dispatchQuestions={dispatchQuestions} setQuestionEdit={setQuestionEdit}/>
        ) : null}
    </div>;
}

export default SurveyList;