import React, { Component } from 'react';
import NotFoundMessage from '../08CommonComponents/NotFoundMessage';

import '../../App.css';
class IstaiguListTable extends Component {

    render() {
        const {
            istaigos,
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
        if (search !=="" && istaigos.length === 0) {
            return (
                <NotFoundMessage message="Maitinimo įstaigų pagal įvestą pavadinimą nerasta"/>
            )
        }else{
        return (
            <div className="table-responsive-md">

                <table className="table" >

                    <thead className="no-top-border">
                        <tr >
                            <th>Maitinimo įstaigos kodas</th>
                            <th>Adresas</th>
                            <th>Pavadinimas</th>
                            <th>Peržiūrėti meniu</th>
                            <th>Redaguoti duomenis</th>
                            <th className="deleteColumn">Ištrinti maitinimo įstaigą</th>
                        </tr>
                    </thead>
                    <tbody >
                        {
                            istaigos.map((istaiga) => (
                                <tr key={istaiga.kodas}>
                                    {inEditMode && editRowId === istaiga.id ?
                                        (
                                            <React.Fragment>
                                                <td>
                                                    <input
                                                        type="text"
                                                        className="form-control"
                                                        id="txtIstaigosKodas"
                                                        name="kodas"
                                                        value={istaiga.kodas}
                                                        onChange={(event) => onChange(event)}
                                                        placeholder="Maitinmo Įstaigos kodas"
                                                        title="Kodas privalomas"
                                                        required
                                                    />
                                                    {errorMessages.kodas && <div className="text-danger">{errorMessages.kodas}</div>}
                                                </td>
                                                <td>
                                                    <input
                                                        type="text"
                                                        className="form-control"
                                                        id="txtIstaigaAddress"
                                                        name="address"
                                                        value={istaiga.address}
                                                        onChange={(event) => onChange(event)}
                                                        placeholder="Adresas"
                                                        title="Adresas privalomas"
                                                        required
                                                        pattern="[^\n]{3,50}"
                                                    />
                                                    {errorMessages.address && <div className="text-danger">{errorMessages.address}</div>}
                                                </td>
                                       
                                                <td >
                                                    <input
                                                        type="text"
                                                        className="form-control"
                                                        id="txtIstaigatenName"
                                                        name="pavadinimas"
                                                        value={istaiga.pavadinimas}
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
                                                        onClick={() => onSave({ id: istaiga.id, item: istaiga })}
                                                        disabled={hasErrors}>
                                                        Saugoti
                                                        </button>
                                                </td>
                                            </React.Fragment>
                                        ) : (
                                            <React.Fragment>
                                                <td>{istaiga.kodas}</td>
                                                <td>{istaiga.address}</td>
                                                <td>{istaiga.pavadinimas}</td>
                                               
                                                
                                                <td>
                                                    <button
                                                        className="btn btn-outline-primary btn-sm btn-block"
                                                        id="btnUpdateMeniu"
                                                        onClick={() => onEditData(istaiga)}>
                                                        Redaguoti
                                                    </button>
                                                </td>
                                            </React.Fragment>
                                        )}
                                    <td>
                                        <button
                                            onClick={() => onView(istaiga)}
                                            id="btnViewMeniu"
                                            className="btn btn-outline-danger btn-sm btn-block">
                                            Peržiūrėti meniu
                                            </button>
                                    </td>
                                    <td>
                                        <button
                                            onClick={() => onDelete(istaiga)}
                                            id="btnDeleteMeniu"
                                            className="btn btn-outline-danger btn-sm btn-block">
                                            Ištrinti
                                            </button>
                                    </td>
                  
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

export default IstaiguListTable;
