import React from 'react';
import '../../App.css';


export default function Logout({ onLogout }) {
    return (

        <button onClick={onLogout} id="btnLogout" className="btn btn-light btn-outline-primary " >Atsijungti</button>
    
        )
}
