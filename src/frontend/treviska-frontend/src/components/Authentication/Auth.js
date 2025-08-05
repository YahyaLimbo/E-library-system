import React,{ useState, useEffect}from 'react';
import { useLocation} from 'react-router-dom';
import './Auth.css';

const Auth=()=>{
    const location = useLocation();
    // might rewrite it using bool ? true:false
    const getTabFromUrl = () => {
        if (location.pathname === '/register') {
            return 'register';
        } else if (location.pathname === '/login') {
            return 'login';
        }
        return 'login'; 
    };

      useEffect(() => {
        SetTab(getTabFromUrl());
    }, [location.pathname]);

    const [Tab,SetTab]= useState("login");
    const Login =() =>{
        return (
         <>
            <form>
                <h2>Login</h2>
                <input type="text" placeholder="Username" required />
                <input type="password" placeholder="Password" required />
                <button type="submit">Login</button>
                            <div className="auth-links">
                <span onClick={() => SetTab('register')}>Don't have an account? Register</span>
                <span onClick={() => SetTab('forgot')}>Forgot password?</span>
            </div>
            </form>
            
        </>
        )
    }
    const Register=() =>{
        return (
        <>
            <form>
                <h2>Register</h2>
                <input type="text" placeholder="Email" required />
                <input type="text" placeholder="Username" required />
                <input type="password" placeholder="Password" required />
                <button type="submit">Register</button>
                            <div className="auth-links">
                <span onClick={() => SetTab("login")}>Do you have an account?Login here</span>
            </div>
            </form>
        </>
        )
    }
    //later implement logic box when prompting a message to confirm an email is sent plus a return button
    const ForgotPass=()=>{
        return (
        <> 
            <form>
                 <h2>Forgot password</h2>
                <input type="text" placeholder="Email" required />
                <button type="submit">Recover password</button>
                            <div className="auth-links">
                <span onClick={() => SetTab("login")}>Go back on Login</span>
                <span onClick={() => SetTab('register')}>Don't have an account? Register</span>
                            </div>
                            </form>
        </>
        )
    }
     console.log('Current URL:', location.pathname); // Debug line
    console.log('Current Tab:', Tab); // Debug line
    return (
        <div className="auth-container">
            <div className="auth-box">
                {Tab==='login'&&Login()}
                {Tab==='register'&&Register()}
                {Tab==='forgot'&&ForgotPass()}
            </div>
        </div>
    );
};

export default Auth;