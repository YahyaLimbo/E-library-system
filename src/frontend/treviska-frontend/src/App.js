import './App.css';
import Navbar from './components/Navbar/Navbar.js';
import Searchbar from './components/Searchbar/Searchbar.js';
import React from 'react';
import Landingpage from './pages/LandingPage.js';
//import later components here
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<Landingpage />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;