import React, { Component } from 'react';
import NotFoundMessage from '../08CommonComponents/NotFoundMessage';

import '../../App.css';
class MeniuListTable extends Component {

    render() {
        const {
            menius,
            inEditMode,
            editRowId,
            errorMessages,
            hasErrors,
            onEditData,
            onSave,
            onChange,
            onView,
            onDelete} = this.props;
        if ( menius.length === 0) {
            return (
                <NotFoundMessage message="Meniu pagal pavadinimą nerasta"/>
            )
        }else{
        return (
            <div className="table-responsive-md">

                <table className="table" >

                    <thead className="no-top-border">
                        <tr >
                            <th>Meniu pavadinimas</th>
                            <th>Redaguoti duomenis</th>
                            <th className="deleteColumn">Ištrinti meniu</th>
                        </tr>
                    </thead>
                    <tbody >
                        {
                            menius.map((meniu) => (
                                <tr key={meniu.id}>
                                    {inEditMode && editRowId === meniu.id ?
                                        (
                                            <React.Fragment>
                                                <td>
                                                    <input
                                                        type="text"
                                                        className="form-control"
                                                        id="txtMeniuName"
                                                        name="meniuName"
                                                        value={meniu.meniuName}
                                                        onChange={(event) => onChange(event)}
                                                        placeholder="Meniu Pavadinimas"
                                                        title="Meniu pavadinimas privalomas"
                                                        required
                                                    />
                                                    {errorMessages.meniuName && <div className="text-danger">{errorMessages.meniuName}</div>}
                                                </td>
                                     
                                                <td>
                                                    <button
                                                        type="submit"
                                                        className="btn btn-primary btn-sm btn-block"
                                                        id="btnSaveUpdatedMeniu"
                                                        onClick={() => onSave({ id: meniu.id, item: meniu })}
                                                        disabled={hasErrors}>
                                                        Saugoti
                                                        </button>
                                                </td>
                                            </React.Fragment>
                                        ) : (
                                            <React.Fragment>
                                                <td>{meniu.meniuName}</td>
                                               
                                                <td>
                                                    <button
                                                        className="btn btn-outline-primary btn-sm btn-block"
                                                        id="btnUpdateMeniu"
                                                        onClick={() => onEditData(meniu)}>
                                                        Redaguoti
                                                    </button>
                                                </td>
                                            </React.Fragment>
                                        )}

                                    <td>
                                        <button
                                            onClick={() => onView(meniu)}
                                            id="btnViewMeniu"
                                            className="btn btn-outline-danger btn-sm btn-block">
                                            Peržiūrėti patiekalus
                                            </button>
                                    </td>
                                    <td>
                                        <button
                                            onClick={() => onDelete(meniu)}
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

export default MeniuListTable;
