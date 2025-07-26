import React, {useState} from 'react';
import { Search, X, Filter } from 'lucide-react';
import './Searchbar.css';

const Searchbar = () => {
  return (
    <div className={"searchbar-container"}>
        <div className="searchbar-form">
            <div className='search-input-container'>
                <input type= "text" placeholder="Search..." />
                <div className="search-icon">
                <Search />
                </div>
       </div>
            </div>
    </div>
  );
};

export default Searchbar;
