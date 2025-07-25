import React,{ useState } from 'react';
import { Menu, X, ChevronDown, CircleUserRound, Search } from 'lucide-react';
import './Navbar.css';
//declare a const mobilemenu false and setMobileMenu to setMobileMenu
const Navbar = () => {
  return (
    <div className="navbar">
    <div className="navbar-brand">
      <a href="/" className="brand-link">Treviska</a>
    </div>
    <div className="navbar-auth">
      <a href="/login" className="auth-link">Login</a>
      <a href="/register" className="auth-link">Register</a>
    </div>
    <div className ="navbar-container">
      <div className="navbar-menu-icon">
        <Menu 
            size={24} 
            className="menu-icon"
          />
          <CircleUserRound
          size={24}
          className="menu-icon"/>
          <Search
            size={24}
            className="menu-icon"
          />
          <div className = "topnav">
            <a class="active" href="#home">Home</a>
  <a href="#about">About</a>
  <a href="#contact">Contact</a>
  <input type="text" placeholder="Search.."></input>
  </div>
      </div>
    </div>
  </div>
  );
};

export default Navbar;