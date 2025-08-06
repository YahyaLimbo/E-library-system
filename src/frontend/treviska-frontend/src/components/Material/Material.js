import React from 'react';
import './Material.css';
import { User, Calendar } from 'lucide-react';

const Material = () => {

    return (
        <div className="material-container">
            <div className="material-main">
                <h3 className="material-header">Material title displayed here</h3>
                <img itemprop="image" src="" alt="Placeholder image"/>
            </div>

            <div className="material-section">
                <h3 className="section-title">Summary</h3>
                <h3 className ="section-title">Author</h3>
                <h3 className ="section-title">Date published</h3>
                <h3 className ="section-title">Date published</h3>
                <h3 className ="section-title">Tags:</h3>
                <h3 className ="section-title">Language</h3>
                <h3 className ="section-title">Date published</h3>
                <h3 className ="section-title">More info can be displayed</h3>
                <button className ="section-button">Read</button>
                <button className ="section-button">Download pdf</button>
                <div className="settings-content">
                </div>
            </div>
        </div>
    );
};

export default Material;