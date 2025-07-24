import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header"></header>
      <form>
        <label htmlFor="username">Username:</label>
        <input type="text" id="username" name="username" placeholder="Username" />
        <label htmlFor="password">Password:</label>
        <input type="password" id="password" name="password" placeholder="Password" />
        <button type="submit">Login</button>
        <button type="button">Register</button>
        <button type="button">Forgot Password</button>
        
      </form>
    </div>
  );
}

export default App;