import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';

import '../../App.css';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import swal from 'sweetalert';

import inputValidator from '../08CommonComponents/InputValidator';


class AdminCreateUser extends Component {

    constructor(props) {
        super(props);
        this.state = {
            role: "ADMIN",
            name: "",
            surname: "",
            birthdate: "",
            personalCode: "",
            address: "",
            phone: "",
            email: ""
        }
        this.roleDropdownOnChange = this.roleDropdownOnChange.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    drawSelector() {
        return (
            <div className="form">
                <div className="mb-3 ">
                    <label htmlFor="role-selector"
                    className="form-label">Naudotojo rolė:</label>
                    <select name="role-selector" id="selRole" className="form-select" value={this.state.role} onChange={this.roleDropdownOnChange}>
                        <option value="ADMIN">Administratorius</option>
                        <option value="USER">Vartotojas</option>
                    </select>
                </div>
                <div className="form-group ">
                    <label htmlFor="txtEmail"
                     className="form-label">El. paštas <span className="fieldRequired">*</span></label>
                    <input
                        type="text"
                        className="form-control"
                        id="txtEmail"
                        name="email"
                        value={this.state.email}
                        placeholder="El. paštas"
                        onChange={this.handleChange}
                        onInvalid={(e) => inputValidator(e)}
                        required
                        pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}"
                    />
                </div>
            </div>
        )
    }

    drawForm(role) {
        if (role === "ADMIN") {
            return (
                <div className="form">
                    <div className="mb-3">
                        <label htmlFor="txtName"
                         className="form-label">Vardas <span className="fieldRequired">*</span></label>
                        <input
                            type="text"
                            className="form-control"
                            id="txtName"
                            name="name"
                            value={this.state.name}
                            onChange={this.handleChange}
                            onInvalid={(e) => inputValidator(e)}
                            placeholder="Vardas"
                            required
                            pattern="[A-zÀ-ž]{2,32}"
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="txtSurname"  className="form-label">Pavardė <span className="fieldRequired">*</span></label>
                        <input
                            type="text"
                            className="form-control"
                            id="txtSurname"
                            name="surname"
                            value={this.state.surname}
                            onChange={this.handleChange}
                            onInvalid={(e) => inputValidator(e)}
                            placeholder="Pavardė"
                            required
                            pattern="[A-zÀ-ž]{2,32}"
                        />
                    </div>
                </div>
            )
        }
        else if (role === "USER") {
            return (
                <div className="mb-3">
                    <div className="form">
                        <div className="mb-3">
                            <label htmlFor="txtName">Vardas <span className="fieldRequired">*</span></label>
                            <input
                                type="text"
                                className="form-control"
                                id="txtName"
                                name="name"
                                value={this.state.name}
                                onChange={this.handleChange}
                                onInvalid={(e) => inputValidator(e)}
                                placeholder="Vardas"
                                required
                                pattern="[A-zÀ-ž]{2,32}"
                            />
                        </div>
                    </div>
                    <div className="form">
                        <div className="mb-3">
                            <label htmlFor="txtSurname">Pavardė <span className="fieldRequired">*</span></label>
                            <input
                                type="text"
                                className="form-control"
                                id="txtSurname"
                                name="surname"
                                value={this.state.surname}
                                onChange={this.handleChange}
                                onInvalid={(e) => inputValidator(e)}
                                placeholder="Pavardė"
                                required
                                pattern="[A-zÀ-ž]{2,32}"
                            />
                        </div>
                    </div>
                </div>
            )
        }
    }

    resetState = () => {
        this.setState({
            name: "",
            surname: "",
            birthdate: "",
            personalCode: "",
            address: "",
            phone: "",
            email: ""
        })

    }

    roleDropdownOnChange(event) {
        event.preventDefault()
        this.setState({
            role: event.target.value,
        })
        this.resetState();
    }

    handleChange(event) {
        const target = event.target;
        inputValidator(event);
        this.setState({
            [target.name]: target.value
        })
    }

    handleSubmit = (event) => {
        event.preventDefault();
        http.post(`${apiEndpoint}/api/users/admin/createuser`, {
            "address": this.state.address,
            //"birthdate": this.state.birthdate,
            "email": this.state.email,
            "name": this.state.name,
            "password": this.state.email,
            "personalCode": this.state.personalCode,
            "phone": this.state.phone,
            "role": this.state.role,
            "surname": this.state.surname,
            "username": this.state.email
        })
            .then(() => {
                swal({
                    text: "Naujas naudotojas buvo sėkmingai sukurtas.",
                    button: "Gerai"
                }).then(() => {
                    this.props.history.push("/new")
                    this.props.history.replace("/admin")
                })
            })
            .catch((error) => {
                
                swal({                   
                    text: error.response.data,                   
                    button: "Gerai"
                })
            })

    }

    render() {
        return (
            <div >
                <h6 className="py-3"><b>Naujo naudotojo sukūrimas</b></h6>
                <form onSubmit={this.handleSubmit}>
                    {this.drawSelector()}
                    {this.drawForm(this.state.role)}
                    <h6 className="py-3"><b>Naudotojo prisijungimai</b></h6>

                    <div className="row">
                        <div className="col-12">
                            <p><b>Naudotojo vardas</b></p>
                        </div>
                        <div className="col-12">
                            <p>{this.state.email}</p>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-12">
                            <p><b>Slaptažodis</b></p>
                        </div>
                        <div className="col-12">
                            <p>{this.state.email}</p>
                        </div>
                    </div>
                    <div className="mb-3">
                        <button className="btn btn-outline-danger float-start" onClick={this.resetState} id="btnClean">Išvalyti</button>
                        <button type="submit" className="btn btn-primary float-end" id="btnCreate">Sukurti</button>
                    </div>
                </form>

            </div>
        )
    }
}

export default withRouter(AdminCreateUser)