import {Question} from "../../../model/models";
import React from "react";
import {QuestionReducerAction} from "../QuestionReducer";
import SingleQuestion from "../singlequestion/SingleQuestion";

interface Props {
    questions: Question[],
    dispatchQuestions: React.Dispatch<QuestionReducerAction>;
    //survey: Survey,
}

const QuestionList: React.FC<Props> = ({questions}, dispatchQuestions) => {

    return (
        <div className='questions'>
            {questions.sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0)).map((question) =>
                <SingleQuestion question={question}
                              dispatch={dispatchQuestions}/>
            )}
        </div>

    )


}

export default QuestionList;