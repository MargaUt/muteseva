import React, { useEffect, useReducer } from "react";
import { Switch, Route, Redirect } from "react-router-dom";

import "./index.css";
import "./App.css";
import Spinner from "./components/08CommonComponents/Spinner";
import swal from "sweetalert";

import Login from "./components/01Login/LoginContainer";
import NotFound from "./components/03NotFound/NotFound";
import Admin from "./components/04Admin/AdminContainer";
import AdminNavBar from "./components/00Navigation/AdminNavBar";
import UserNavBar from "./components/00Navigation/UserNavBar";

import AuthContext from "./components/11Context/AuthContext";
import http from "./components/10Services/httpService";
import CommonErrorHandler from "./components/10Services/CommonErrorHandler";
import apiEndpoint from "./components/10Services/endpoint";
import { UserHomeContainer } from "./components/02Main/UserHomeContainer";
import { IstaiguContainer } from './components/18MaitinimoIstaiga/IstaiguContainer';
import { MeniuContainer } from './components/19Meniu/MeniuContainer';
import { MealContainer } from './components/20Meals/MealContainer';

var initState = {
    isAuthenticated: null,
    username: null,
    role: null,
    error: null,
};

const reducer = (state, action) => {
    switch (action.type) {
        case "LOGIN":
            return {
                ...state,
                isAuthenticated: true,
                username: action.payload.username,
                role: action.payload.role,
                error: null,
            };
        case "LOGOUT":
            return {
                ...state,
                isAuthenticated: false,
                username: null,
                role: null,
                error: null,
            };
        case "ERROR":
            return {
                ...state,
                isAuthenticated: false,
                username: null,
                role: null,
                error: action.payload,
            };
        default:
            return state;
    }
};

function App() {
    const [state, dispatch] = useReducer(reducer, initState);

    useEffect(() => {

        if (state.isAuthenticated === null) {
            http
                .get(`${apiEndpoint}/api/loggedUserRole`)
                .then((resp) => {
                    dispatch({
                        type: "LOGIN",
                        payload: { role: resp.data },
                    });
                })
                .catch((error) => {
                    const unexpectedError = error.response &&
                        error.response.status >= 400 &&
                        error.response.status < 500;

                    if (!unexpectedError || (error.response && error.response.status === 404)) {
                        swal("Įvyko klaida, puslapis nurodytu adresu nepasiekiamas");
                        dispatch({
                            type: "ERROR",
                        });
                    } else dispatch({
                        type: "ERROR",
                        payload: error.response.status,
                    });
                });
        }
    }, [state.isAuthenticated]);

    if (state.isAuthenticated) {
        switch (state.role) {
            case "ADMIN":
                return ( <
                    AuthContext.Provider value = {
                        { state, dispatch } } >
                    <
                    CommonErrorHandler >
                    <
                    div className = "container-fluid px-0" >
                    <
                    AdminNavBar >
                    <
                    Switch >
                    <
                    Route exact path = "/"
                    component = { Admin }
                    /> <
                    Route exact path = "/home"
                    component = { Admin }
                    /> <
                    Route exact path = "/admin"
                    component = { Admin }
                    /> <
                    Route exact path = "/istaigos"
                    component = { IstaiguContainer }
                    /> <
                    Route path = "/meniu/:id"
                    component = { MeniuContainer }
                    /> <
                    Route path = "/meals/:id"
                    component = { MealContainer }
                    /> <
                    Route path = "*"
                    component = { NotFound }
                    /> <
                    /Switch> <
                    /AdminNavBar> <
                    /div> <
                    /CommonErrorHandler> <
                    /AuthContext.Provider>
                );

            case "USER":
                return ( <
                    AuthContext.Provider value = {
                        { state, dispatch } } >
                    <
                    CommonErrorHandler >
                    <
                    div className = "container-fluid px-0" >
                    <
                    UserNavBar >
                    <
                    Switch >
                    <
                    Route exact path = "/"
                    component = { UserHomeContainer }
                    /> <
                    Route exact path = "/home"
                    component = { UserHomeContainer }
                    /> <
                    Route exact path = "/prasymai"
                    component = { UserHomeContainer }
                    /> <
                    Route

                    path = "/istaigos"
                    component = { IstaiguContainer }
                    /> <
                    Route path = "*"
                    component = { NotFound }
                    /> <
                    /Switch> <
                    /UserNavBar> <
                    /div> <
                    /CommonErrorHandler> <
                    /AuthContext.Provider>
                );
            default:
                return ( <
                    AuthContext.Provider value = {
                        { state, dispatch } } >
                    <
                    div className = "container-fluid px-0" >
                    <
                    NotFound / >
                    <
                    /div> <
                    /AuthContext.Provider>
                );
        }
    } else if (state.isAuthenticated === false) {
        return ( <
            div >
            <
            AuthContext.Provider value = {
                { state, dispatch } } >
            <
            Switch >
            <
            Route exact path = "/login"
            component = { Login }
            /> <
            Route path = "*" >
            <
            Redirect to = "/login" / >
            <
            /Route>  <
            /Switch> <
            /AuthContext.Provider> <
            /div>
        );
    } else return <Spinner / > ;
}

export default App;