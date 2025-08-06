import React, { useState } from 'react';
import './Profile.css';
import { User, Calendar } from 'lucide-react';

const Profile = () => {
    const userData = {
        memberSince: 'January 2023'
    };

    return (
        <div className="Profile-container">
            <div className="Profile-main">
                <h3 className="profile-header">User name displayed here</h3>
                <User className="user-icon" size={20} strokeWidth={1} absoluteStrokeWidth />
                <div className="profile-meta">
                    <Calendar size={16} />
                    <span>Member since {userData.memberSince}</span>
                </div>
            </div>
            <div className="profile-section">
                <h3 className="section-title">Account Settings</h3>
                <h3 className ="section-title">Account History</h3>
                <h3 className ="section-title">My Collection</h3>
                <div className="settings-content">
                </div>
            </div>
        </div>
    );
};

export default Profile;