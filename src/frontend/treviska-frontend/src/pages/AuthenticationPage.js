import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "../components/Navbar/Navbar";
import Searchbar from "../components/Searchbar/Searchbar";
import Auth from "../components/Authentication/Auth";



const AuthenticationPage = () => {
  return (
    <div className="LandingPage">
        <Navbar />
        <Searchbar />
        <Auth />
        </div>
  );
}
export default AuthenticationPage;