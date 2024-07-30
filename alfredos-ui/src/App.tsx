import "./App.css";
import logo from "./assets/logo.png";
import { Outlet } from "react-router-dom";

function App() {
  return (
    <>
      <div className="wrapper">
        <img src={logo} alt="logo" />
        <Outlet />
      </div>
    </>
  );
}

export default App;
