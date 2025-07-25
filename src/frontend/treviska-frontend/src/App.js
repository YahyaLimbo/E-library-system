import './App.css';
import Navbar from './components/Navbar/Navbar.js';
import Header from './components/Navbar/Navbar.js';
//import later components here
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<Navbar />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;