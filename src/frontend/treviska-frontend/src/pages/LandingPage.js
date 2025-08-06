import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "../components/Navbar/Navbar";
import { Search } from "lucide-react";
import Searchbar from "../components/Searchbar/Searchbar";
import Homepage from "../components/Homepage/Homepage";
import Sidebar from "../components/Sidebar/Sidebar";

//import later components here


const LandingPage = () => {
  return (
    <div className="LandingPage">
        <Navbar />
        <Searchbar />
          <Homepage/>
        </div>
  );
}
export default LandingPage;