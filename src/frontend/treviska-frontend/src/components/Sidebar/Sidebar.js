import React from 'react';
import { Search, X, Filter } from 'lucide-react';
import './Sidebar.css';

// we include an icon and include an animation to scroll
const Sidebar = () => {
    return (
        <div className="sidebar">
            <div className="sidebar-content">
                <div className="categories-section">
                    <h1 className="sidebar-title">Material List</h1>
                    <nav className="sidebar-nav">
                        <ul className="sidebar-menu">
                            <li className="sidebar-item">
                                <a href="#books" className="sidebar-link">Books</a>
                            </li>
                            <li className="sidebar-item">
                                <a href="#" className="sidebar-link">Journals</a>
                            </li>
                            <li className="sidebar-item">
                                <a href="#" className="sidebar-link">Magazines</a>
                            </li>
                            <li className="sidebar-item">
                                <a href="#" className="sidebar-link">NewsPaper</a>
                            </li>
                            <li className="sidebar-item">
                                <a href="#" className="sidebar-link">Reseach Paper</a>
                            </li>
                            <li className="sidebar-item">
                                <a href="#" className="sidebar-link">Conference Paper</a>
                            </li>
                            <li className="sidebar-item">
                                <a href="#" className="sidebar-link">Reference Material</a>
                            </li>
                            <li className="sidebar-item">
                                <a href="#" className="sidebar-link">Thesis</a>
                            </li>
                            <li className="sidebar-item">
                                <a href="#" className="sidebar-link">Archive</a>
                            </li>
                            <li className="sidebar-item">
                                <a href="#" className="sidebar-link">Dataset</a>
                            </li>
                            <li className="sidebar-item">
                                <a href="#" className="sidebar-link">AudioBook</a>
                            </li>
                        </ul>
                    </nav>
                </div>
                
                
                <div className="fiction-section">
                    <h3 className="sidebar-title fiction-title">Filters</h3>
                    <nav className="sidebar-nav">
                        <ul className="sidebar-menu">
                            <li className="sidebar-item">
                                <a href="#" className="sidebar-link">Draft example</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    );
};

export default Sidebar;