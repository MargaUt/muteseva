import React, { Component } from 'react';
import NotFoundMessage from '../08CommonComponents/NotFoundMessage';
import AuthContext from "../11Context/AuthContext";
import '../../App.css';
class IstaiguListTable extends Component {
    //static contextType = AuthContext
    render() {
        const {
            kategorijos,
            inEditMode,
            editRowId,
            errorMessages,
            hasErrors,
            onEditData,
            onSave,
            onChange,
            onView, 
            onDelete,
            search } = this.props;
        if (search !=="" && kategorijos.length === 0) {
            return (
                <NotFoundMessage message="Kategorijų įstaigų pagal įvestą pavadinimą nerasta"/>
            )
        }else{
        return ( 
            <AuthContext.Consumer>{(authContext) =>
                <div className="table-responsive-md">

                <table className="table" >

                    <thead className="no-top-border">
                        <tr >
                            <th>Knygų kategorijos pavadinimas</th>
                            {authContext.state.role === 'ADMIN' &&
                                <th>Redaguoti duomenis</th>
                            }
                            <th>Peržiūrėti knygas</th>
                            {authContext.state.role === 'ADMIN' &&
                                <th className="deleteColumn">Ištrinti maitinimo įstaigą</th>
                            }
                        </tr>
                    </thead>
                    <tbody >
                        {
                            kategorijos.map((ketegorija) => (
                                <tr key={ketegorija.kodas}>
                                    {inEditMode && editRowId === ketegorija.id  ?
                                        (
                                            <React.Fragment>
                                             
                                            
                                                <td >
                                                    <input
                                                        type="text"
                                                        className="form-control"
                                                        id="txtIstaigatenName"
                                                        name="pavadinimas"
                                                        value={ketegorija.pavadinimas}
                                                        onChange={(event) => onChange(event)}
                                                        placeholder="Pavadinimas"
                                                        title="Pavadinimas turi būti 3-50 simbolių ir negali prasidėti tarpu"
                                                        required
                                                        minLength="3"
                                                        maxLength="50"
                                                        pattern="[^ ][A-zÀ-ž0-9\x22 \-'.,]*"
                                                    />
                                                    {errorMessages.pavadinimas && <div className="text-danger">{errorMessages.pavadinimas}</div>}
                                                </td>
                                     
                                                <td>
                                                    <button
                                                        type="submit"
                                                        className="btn btn-primary btn-sm btn-block"
                                                        id="btnSaveUpdatedKindergarten"
                                                        onClick={() => onSave({ id: ketegorija.id, item: ketegorija })}
                                                        disabled={hasErrors}>
                                                        Saugoti
                                                        </button>
                                                </td>
                                            </React.Fragment>
                                        ) : (
                                            <React.Fragment>
                                                <td>{ketegorija.name}</td>
                                               
                                                
                                                {authContext.state.role === 'ADMIN' &&
                                                <td>
                                                    <button
                                                        className="btn btn-outline-primary btn-sm btn-block"
                                                        id="btnUpdateMeniu"
                                                        onClick={() => onEditData(ketegorija)}>
                                                        Redaguoti
                                                    </button>
                                                </td>
                                                }
                                            </React.Fragment>
                                        )}
                                    <td>
                                        <button
                                            onClick={() => onView(ketegorija)}
                                            id="btnViewMeniu"
                                            className="btn btn-outline-danger btn-sm btn-block">
                                            Peržiūrėti meniu
                                        </button>
                                    </td>
                                    {authContext.state.role === 'ADMIN' &&
                                    <td>
                                        <button
                                            onClick={() => onDelete(ketegorija)}
                                            id="btnDeleteMeniu"
                                            className="btn btn-outline-danger btn-sm btn-block">
                                            Ištrinti
                                        </button>
                                    </td>}
                  
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
            }
            </AuthContext.Consumer>
        );
    }
}
}

export default IstaiguListTable;
