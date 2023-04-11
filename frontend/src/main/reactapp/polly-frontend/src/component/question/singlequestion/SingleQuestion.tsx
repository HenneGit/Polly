import {Question} from "../../../model/models";
import React from "react";
import {QuestionReducerAction} from "../QuestionReducer";


type Props = {
    question: Question,
    dispatch: React.Dispatch<QuestionReducerAction>;
}

const SingleSurvey = ({question, dispatch}: Props) => {


    return (
        <div></div>
    );

}


export default SingleSurvey;