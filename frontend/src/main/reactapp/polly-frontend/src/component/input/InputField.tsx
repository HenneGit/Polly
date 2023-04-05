import React, {useRef} from "react";
import './InputField.css';


interface Properties {
    survey: string;
    setSurvey: React.Dispatch<React.SetStateAction<string>>;
    handleAddSurvey: (e: React.FormEvent) => void;
}


const InputField:React.FC<Properties> = ({survey, setSurvey, handleAddSurvey} : Properties) => {
    const inputRef = useRef<HTMLInputElement>(null);
    return (
        <form className='form'onSubmit={(e) => {
            handleAddSurvey(e);
            inputRef.current?.blur();
        }}>
            <input className='input__box'
                   ref={inputRef}
                   placeholder='Create survey'
                   value={survey}
                   onChange={(event => setSurvey(event.target.value))}/>
            <button type='submit' className='input__submit'>Add</button>
        </form>
    )

}

export default InputField;
