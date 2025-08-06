import './App.css';
import React from 'react';
import Landingpage from './pages/LandingPage.js';
import AuthenticationPage from './pages/AuthenticationPage.js';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ProfilePage from './pages/ProfilePage.js';
import MaterialPage from './pages/MaterialPage.js';


function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<Landingpage />} />
          <Route path="/login" element={<AuthenticationPage/>} />
          <Route path="/register" element={<AuthenticationPage/>} />
          <Route path="/profile" element={<ProfilePage/>}/>
          <Route path="/material" element={<MaterialPage/>}/>
        </Routes>
      </Router>
    </div>
  );
}

export default App;