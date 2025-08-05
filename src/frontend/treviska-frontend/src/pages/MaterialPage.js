import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "../components/Navbar/Navbar";
import Searchbar from "../components/Searchbar/Searchbar";
import Sidebar from "../components/Sidebar/Sidebar";

//import later components here


const LandingPage = () => {
  return (
    <div className="LandingPage">
        <Navbar />
        <Searchbar />
          <Sidebar/>
        </div>
  );
}
export default MaterialPage;