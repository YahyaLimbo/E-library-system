import React,{ useState, useEffect}from 'react';
import { useLocation, useNavigate} from 'react-router-dom';
import { authService } from '../../services/authService';
import './Auth.css';

const Auth=()=>{
    const location = useLocation();
    const navigate = useNavigate();
    
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
    const [loginData, setLoginData] = useState({ username: '', password: '' });
    const [registerData, setRegisterData] = useState({ email: '', username: '', password: '' });
    const [forgotData, setForgotData] = useState({ email: '' });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');
        
        try {
            await authService.login(loginData);
            setSuccess('Login successful!');
            navigate('/');
        } catch (err) {
            setError(err.message || 'Login failed');
        } finally {
            setLoading(false);
        }
    };

    const handleRegister = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError('');
        
        try {
            await authService.register(registerData);
            setSuccess('Registration successful! You can now login.');
            SetTab('login');
            navigate('/login');
            setRegisterData({ email: '', username: '', password: '' });
        } catch (err) {
            setError(err.message || 'Registration failed');
        } finally {
            setLoading(false);
        }
    };

    const Login =() =>{
        return (
         <>
            <form onSubmit={handleLogin}>
                <h2>Login</h2>
                {error && <div className="error-message">{error}</div>}
                {success && <div className="success-message">{success}</div>}
                <input 
                    type="text" 
                    placeholder="Username" 
                    value={loginData.username}
                    onChange={(e) => setLoginData({...loginData, username: e.target.value})}
                    required 
                />
                <input 
                    type="password" 
                    placeholder="Password" 
                    value={loginData.password}
                    onChange={(e) => setLoginData({...loginData, password: e.target.value})}
                    required 
                />
                <button type="submit" disabled={loading}>
                    {loading ? 'Logging in...' : 'Login'}
                </button>
                <div className="auth-links">
                    <span onClick={() => { SetTab('register'); navigate('/register'); }}>Don't have an account? Register</span>
                    <span onClick={() => SetTab('forgot')}>Forgot password?</span>
                </div>
            </form>
        </>
        )
    }
    const Register=() =>{
        return (
        <>
            <form onSubmit={handleRegister}>
                <h2>Register</h2>
                {error && <div className="error-message">{error}</div>}
                {success && <div className="success-message">{success}</div>}
                <input 
                    type="email" 
                    placeholder="Email" 
                    value={registerData.email}
                    onChange={(e) => setRegisterData({...registerData, email: e.target.value})}
                    required 
                />
                <input 
                    type="text" 
                    placeholder="Username" 
                    value={registerData.username}
                    onChange={(e) => setRegisterData({...registerData, username: e.target.value})}
                    required 
                />
                <input 
                    type="password" 
                    placeholder="Password" 
                    value={registerData.password}
                    onChange={(e) => setRegisterData({...registerData, password: e.target.value})}
                    required 
                />
                <button type="submit" disabled={loading}>
                    {loading ? 'Registering...' : 'Register'}
                </button>
                <div className="auth-links">
                    <span onClick={() => { SetTab('login'); navigate('/login'); }}>Do you have an account? Login here</span>
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
                    <span onClick={() => { SetTab('login'); navigate('/login'); }}>Go back on Login</span>
                    <span onClick={() => { SetTab('register'); navigate('/register'); }}>Don't have an account? Register</span>
                </div>
                            </form>
        </>
        )
    }
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