export interface Survey {
    primaryKey: string;
    name: string;
    description: string;
}

export interface Question {
    primaryKey: string;
    name: string;
    description: string;
    question: string;
    surveyPk: string;

}

export interface Answer {
    primaryKey: string;
    answerText: string;
    questionPk: string;
}