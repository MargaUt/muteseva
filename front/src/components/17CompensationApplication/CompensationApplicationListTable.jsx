import React, { Component } from 'react';
import NotFoundMessage from '../08CommonComponents/NotFoundMessage';
import '../../App.css';

class CompensationApplicationListTable extends Component {


    render() {
        const {
            kompensacijos,
            search
        } = this.props;
        if (search !== "" && kompensacijos.length === 0) {
            return (
                <NotFoundMessage message="Prašymų pagal įvestą datą nerasta" />
            )
        } else {
            return (
                <div className="table-responsive-md">

                    <table className="table" >

                        <thead className="no-top-border">
                            <tr >
                                <th>Pateikimo data</th>
                                <th>Vaiko vardas, pavardė</th>
                                <th>Ugdymo įstaigos pavadinimas</th>
                                <th>Globėjo vardas, pavardė</th>
                                <th>Daugiau informacijos</th>
                            </tr>
                        </thead>
                        <tbody >
                            {
                                kompensacijos.map((kompensacija) => (

                                    <tr key={kompensacija.id}>

                                        <React.Fragment>
                                            <td>{kompensacija.submitedAt}</td>
                                            <td>{kompensacija.childName + " " + kompensacija.childSurname}</td>
                                            <td>{kompensacija.privateKindergarten.kindergartenName}</td>
                                            <td>{kompensacija.mainGuardian.name + " " + kompensacija.mainGuardian.surname}</td>
                                            <td>

                                                <button type="button" className="btn btn-primary" data-bs-toggle="modal" data-bs-target={"#k" + kompensacija.id}>
                                                    Prašymo forma
                                                </button>

                                                <div className="modal fade" id={"k" + kompensacija.id} tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                    <div className="modal-dialog modal-xl">
                                                        <div className="modal-content">
                                                            <div className="modal-header">
                                                                <h5 className="modal-title" id="exampleModalLabel">Švietimo specialisto kompensacijų forma</h5>
                                                                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                            </div>
                                                            <div className="modal-body">


                                                                <div className="table-responsive-md">

                                                                    <table className="table" >
                                                                        <thead className="no-top-border">
                                                                            <tr >
                                                                                <th>Vaiko asmens kodas</th>
                                                                                <th>Vaiko vardas, pavardė</th>
                                                                                <th>Globėjo vardas, pavardė</th>
                                                                                <th> Globėjo asmens kodas</th>
                                                                                <th>Globėjo el. paštas</th>
                                                                                <th>Globėjo tel. nr.</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody >
                                                                            <React.Fragment>
                                                                                <td>{kompensacija.childPersonalCode}</td>
                                                                                <td>{kompensacija.childName + " " + kompensacija.childSurname}</td>
                                                                                <td>{kompensacija.mainGuardian.name + " " + kompensacija.mainGuardian.surname}</td>
                                                                                <td>{kompensacija.mainGuardian.personalCode}</td>
                                                                                <td>{kompensacija.mainGuardian.email}</td>
                                                                                <td>{kompensacija.mainGuardian.phone}</td>
                                                                            </React.Fragment>
                                                                        </tbody>
                                                                    </table>
                                                                </div>

                                                                <div className="table-responsive-md">
                                                                    <table className="table" >
                                                                        <thead className="no-top-border">
                                                                            <tr >
                                                                                <th>Ugdymo įstaigos pavadinimas</th>
                                                                                <th>Ugdymo įstaigos kodas</th>
                                                                                <th>Ugdymo įstaigos adresas</th>
                                                                                <th>Ugdymo įstaigos kontaktinis tel.nr.</th>
                                                                                <th>Ugdymo įstaigos sąskaitos nr.</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody >
                                                                            <React.Fragment>
                                                                                <td>{kompensacija.privateKindergarten.kindergartenName}</td>
                                                                                <td>{kompensacija.privateKindergarten.kindergartenCode}</td>
                                                                                <td>{kompensacija.privateKindergarten.kindergartenAddress} </td>
                                                                                <td>{kompensacija.privateKindergarten.kindergartenPhone}</td>
                                                                                <td>{kompensacija.privateKindergarten.kindergartenAccountNumber}</td>
                                                                            </React.Fragment>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                            <div className="modal-footer">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </React.Fragment>
                                    </tr>
                                ))
                            }
                        </tbody>
                    </table>
                </div>

            );
        }
    }
}

export default CompensationApplicationListTable;