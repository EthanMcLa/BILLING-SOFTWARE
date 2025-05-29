import Menubar from "./components/Menubar/Menubar";
import {Route, Routes} from "react-router-dom";
import Dashboard from "./pages/Explore/DashBoard/DashBoard";
import ManageCategory from "./pages/Explore/DashBoard/ManageUsers/ManageCategory/ManageCategory";
import ManageUsers from "./pages/Explore/DashBoard/ManageUsers/ManageUsers";
import Manageitems from "./pages/Explore/DashBoard/ManageUsers/ManageCategory/ManageItems/ManageItems";
import Explore from "./pages/Explore/Explore";
import { Toaster } from 'react-hot-toast';
import Login from "./pages/Explore/Login/Login";
const App = () => {
  return (
    <div> 
      <Menubar />
      <Toaster /> 
      <Routes>
        <Route path="/dashboard" element={<Dashboard /> }/>
        <Route path="/category" element={<ManageCategory /> }/>
        <Route path="/users" element={<ManageUsers /> }/>
        <Route path="/Items" element={<Manageitems/> }/>
        <Route path="/Explore" element={<Explore /> }/>
        <Route path="/login" element={<Login /> }/>
        <Route path="/" element={<Dashboard /> }/>
         
      </Routes>
    </div> 
  );
}

export default App; 