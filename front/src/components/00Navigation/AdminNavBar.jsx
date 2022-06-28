import React from 'react';
import { NavLink } from 'react-router-dom';
import logo from "../../images/logo.png";
import '../../App.css';

import LogoutContainer from './LogoutContainer';

function Navigation(props) {
    return (
        <div className="pb-8" >
            <nav className="navbar-expand-lg py-3 navbar bg-success">

                <div className="container">

                <NavLink className="navbar-brand" to={"/home"}>
                        <img className="nav-img" src={logo} alt="logotipas" loading="lazy" />
                    </NavLink>

                    <NavLink className="navbar-brand" to={"/home"}>
                    </NavLink>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        
                    </button>
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav ms-auto ">
                    {/* Renamed .ml-* and .mr-* to .ms-* and .me-*. */}


                            <li className="nav-item me-2">
                                <NavLink style={{color: 'white', textDecoration: 'none'}}  className="nav-link" id="navAdminUserList" to={"/admin"}>Naudotojai</NavLink>
                            </li>
                            <li className="nav-item me-2">
                                <NavLink style={{color: 'white', textDecoration: 'none'}}  className="nav-link" id="navAdminIstaguList" to={"/kategorijos"}>Knygų kategorijos</NavLink>
                            </li>


                            <li className="nav-item navbar-light nav-item me-2">
                                <LogoutContainer />
                            </li>

                        </ul>

                    </div>
                </div>
            </nav>
            <div>{props.children}</div>
        </div >

    );

}

export default Navigation;
