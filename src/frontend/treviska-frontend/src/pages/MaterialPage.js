import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "../components/Navbar/Navbar";
import Searchbar from "../components/Searchbar/Searchbar";
import Sidebar from "../components/Sidebar/Sidebar";
import Material from "../components/Material/Material";

//import later components here


const MaterialPage = () => {
  return (
    <div className="LandingPage">
        <Navbar />
        <Searchbar />
          <Material/>
        </div>
  );
}
export default MaterialPage;