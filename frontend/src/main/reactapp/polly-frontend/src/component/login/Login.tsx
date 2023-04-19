import React, {useRef} from "react";

interface Properties {

}


const Login:React.FC<Properties> = ({} : Properties) => {

    return (
        <form className='form'>
            <input className='input__username'
                   placeholder='Username'/>
            <input className='input__password'
                   placeholder='Password'/>
            <button type='submit' className='input__submit'>Add</button>
        </form>
    )
}

export default Login;