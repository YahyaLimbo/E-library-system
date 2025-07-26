import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "../components/Navbar/Navbar";
import { Search } from "lucide-react";
import Searchbar from "../components/Searchbar/Searchbar";
import Hero from "../components/Homepage/Homepage"
import HomePage from "../components/Homepage/Homepage";
//import later components here


const LandingPage = () => {
  return (
    <div className="LandingPage">
        <Navbar />
        <Searchbar />
    </div>
  );
}
export default LandingPage;