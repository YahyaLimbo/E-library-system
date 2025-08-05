import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "../components/Navbar/Navbar";
import Searchbar from "../components/Searchbar/Searchbar";
import Sidebar from "../components/Sidebar/Sidebar";
import Profile from "../components/Profile/Profile";
//import later components here


const ProfilePage = () => {
  return (
    <div className="LandingPage">
        <Navbar />
        <Searchbar />
        <Profile />
        </div>
  );
}
export default ProfilePage;