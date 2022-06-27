import React, { Component } from 'react';
import '../../App.css';
import http from "../10Services/httpService";
import apiEndpoint from "../10Services/endpoint";
import Table from '../08CommonComponents/Table';
import swal from 'sweetalert';

class UserApplicationsTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
          mainGuardian: {
            name: "",
            surname: "",
            personalCode: "",
            phone: "",
            email: "",
            address: "",
            username: "",
          },
          correctPersonalInfo: false,
        }
        this.checkboxOnChange = this.checkboxOnChange.bind(this);
    }
    componentDidMount() {
    /** get logged in user data */
    http
    .get(`${apiEndpoint}/api/users/user`)
    .then((response) => {
      this.setState({
        mainGuardian: {
          ...this.state.mainGuardian,
          name: response.data.name,
          surname: response.data.surname,
          personalCode: response.data.personalCode,
          phone: response.data.phone,
          email: response.data.username,
          address: response.data.address,
          username: response.data.username,
          role: response.data.role,
        },
      });
    })}

    /** Checkbox onChange */
  checkboxOnChange(e) {
    this.setState({
      correctPersonalInfo: e.target.checked
      })
  }

    columns = [
        {
            key: 'date',
            path: 'date',
            label: 'Pateikimo data',
            content: application => <span>{application.submitedAt}</span>
        },

        {
            key: 'childSurname',
            path: 'childSurname',
            label: 'Vaiko vardas, pavardė',
            content: application => <span>{application.childName} {application.childSurname}</span>
        },
        {
            key: 'status',
            path: 'status',
            label: 'Prašymo statusas',
            content: application => <span>{application.status} </span>
        },
        {
            key: 'kindergarten',
            path: 'kindergarten',
            label: 'Priimta į darželį',
            content: application =>
                <span>
                    {application.status === 'Patvirtintas' ? <span>{application.kindergartenName}</span> : <span>-</span>}
                </span>
        },
        {
            key: 'document',
            path: 'document',
            label: 'Sutartis',
            content: application =>
                <span>
                    {application.status === 'Patvirtintas' ? 
                    <span>
                       <button
                        onClick={() => this.setState({correctPersonalInfo: false})}
                        data-bs-toggle="modal"
                        data-bs-target={"#a" + application.id}
                        id="btnOpenDocumentModal" 
                        className="btn btn-outline-primary btn-sm w-100">Sugeneruoti ir atsisiųsti</button>

                         <div className="modal fade" id={"a" + application.id} tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div className="modal-dialog modal-lg">
                                <div className="modal-content">
                                    <div className="modal-body">
                                        <p className='modal-bigger-font'>{application.childName} {application.childSurname} buvo priimtas į darželį "{application.kindergartenName}"</p>
                                        <p className='modal-regular-font modal-margin-regular'>Sutartis bus sugeneruota PDF formatu ir atsiųsta į jūsų įrenginį</p>
                                        <br/>
                                        <br/>
                                        <div className='row'>
                                            <div className='col-lg-7'>
                                                <p className='modal-regular-font modal-margin-regular'>Vaiko vardas ir pavardė:</p>
                                                <p className='modal-regular-font modal-margin-regular'>Vaiko atstovo vardas ir pavardė:</p>
                                                <p className='modal-regular-font modal-margin-regular'>Faktinės gyvenamosios vietos adresas:</p>
                                                <p className='modal-regular-font modal-margin-regular'>Telefono nr:</p>
                                                <p className='modal-regular-font modal-margin-regular'>El. paštas:</p>
                                            </div>
                                            <div className='col-lg-5'>
                                                <p className='modal-regular-font'>{application.childName} {application.childSurname}</p>
                                                <p className='modal-regular-font'>{this.state.mainGuardian.name} {this.state.mainGuardian.surname}</p>
                                                <p className='modal-regular-font'>{this.state.mainGuardian.address}</p>
                                                <p className='modal-regular-font'>{this.state.mainGuardian.phone}</p>
                                                <p className='modal-regular-font'>{this.state.mainGuardian.email}</p>
                                                <a href={"/darzelis/profilis/atnaujinti"} className="modal-link">Keisti atstovo duomenis</a>
                                            </div>
                                        </div>
                                        <br/>
                                        <br/>
                                        <br/>
                                        <div className="form-check modal-regular-font modal-margin-regular">
                                            <input
                                                type="checkbox"
                                                className="form-check-input"
                                                name="correctPersonalInfo"
                                                id="chkCorrectPersonalInfo"
                                                checked={this.state.correctPersonalInfo}
                                                onChange={this.checkboxOnChange}
                                            />
                                            <label className="form-check-label form-label" htmlFor="correctPersonalInfo">
                                                Patvirtinu, kad asmens duomenys teisingi
                                            </label>
                                        </div>
                                        <br/>
                                        <button type="button" disabled={!this.state.correctPersonalInfo} onClick={() => this.handleDownload(application)} data-bs-dismiss="modal" className="btn btn-outline-primary modal-button">Sugeneruoti ir atsisiųsti</button>
                                        <button type="button" className="btn btn-outline-danger modal-button" data-bs-dismiss="modal">Atšaukti</button>
                                    </div>
                                </div>
                            </div>
                        </div>  
    
                    </span> : 
                    <span>
                        <button
                        disabled={true}
                        className="btn btn-outline-primary btn-sm w-100">Sugeneruoti ir atsisiųsti</button>
                        </span>}
                </span>
        },
        {
            key: 'waiting',
            path: 'waiting',
            label: 'Laukiančiųjų eilės numeris',
            content: application =>
                <span>
                    {application.status === 'Laukiantis' ? <span>{application.numberInWaitingList}</span> : <span>-</span>}
                </span>
        },
        // Dropped .btn-block for utilities. Instead of using .btn-block on the .btn, wrap your buttons with .d-grid and a .gap-* utility to space them as needed.
        {
            
            key: 'delete',
            label: 'Ištrinti prašymą',
            content: application => <button onClick={() => this.props.onDelete(application)} id="btnDeleteApplication" className="btn btn-outline-danger btn-sm w-100">Ištrinti</button>

        }

    ]

    handleDownload = (application) => {
        http.request({
            url: `${apiEndpoint}/api/contract/get`,
            params: {
                id: application.id
            },

            method: "GET",
            responseType: "blob",
        }).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', 'sutartis.pdf');
            document.body.appendChild(link);
            link.click();
            link.remove();
        }).catch((error) => {
            swal({
                text: "Įvyko klaida atsisiunčiant dokumentą.",
                buttons: "Gerai",
            })
        })
    }

    render() {
        const { applications } = this.props;

        return (
            <Table
                columns={this.columns}
                data={applications}

            />
        );
    }
}


export default UserApplicationsTable;
