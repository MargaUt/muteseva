import React, { Component } from 'react';
import NotFoundMessage from '../08CommonComponents/NotFoundMessage';
import AuthContext from "../11Context/AuthContext";

import '../../App.css';
class MealsListTable extends Component {

    render() {
        const {
            meals,
            inEditMode,
            editRowId,
            errorMessages,
            hasErrors,
            onEditData,
            onSave,
            onChange,
            onDelete} = this.props;
        if ( meals.length === 0) {
            return (
                <NotFoundMessage message="Patiekalai pagal pavadinimą nerasta"/>
            )
        }else{
        return (
            <AuthContext.Consumer>{(authContext) =>
            <div className="table-responsive-md">

                <table className="table" >

                    <thead className="no-top-border">
                        <tr >
                            <th>Patiekalo pavadinimas</th>
                            <th>Patiekalo aprašymas</th>
                            {authContext.state.role === 'ADMIN' &&
                            <th>Redaguoti duomenis</th>
                            }
                            {authContext.state.role === 'ADMIN' &&
                            <th className="deleteColumn">Ištrinti patiekalą</th>
                            }
                        </tr>
                    </thead>
                    <tbody >
                        {
                            meals.map((meal) => (
                                <tr key={meal.id}>
                                    {inEditMode && editRowId === meal.id ?
                                        (
                                            <React.Fragment>
                                                <td>
                                                    <input
                                                        type="text"
                                                        className="form-control"
                                                        id="txtMealName"
                                                        name="name"
                                                        value={meal.name}
                                                        onChange={(event) => onChange(event)}
                                                        placeholder="Patiekalo Pavadinimas"
                                                        title="Patiekalo pavadinimas privalomas"
                                                        required
                                                    />
                                                    {errorMessages.name && <div className="text-danger">{errorMessages.name}</div>}
                                                </td>
                                                <td>
                                                    <input
                                                        type="text"
                                                        className="form-control"
                                                        id="txtMealDesc"
                                                        name="description"
                                                        value={meal.description}
                                                        onChange={(event) => onChange(event)}
                                                        placeholder="Patiekalo aprašymas"
                                                        title="Patiekalo aprašymas privalomas"
                                                        required
                                                    />
                                                    {errorMessages.description && <div className="text-danger">{errorMessages.description}</div>}
                                                </td>
                                                <td>
                                                    <button
                                                        type="submit"
                                                        className="btn btn-primary btn-sm btn-block"
                                                        id="btnSaveUpdatedMeniu"
                                                        onClick={() => onSave({ id: meal.id, item: meal })}
                                                        disabled={hasErrors}>
                                                        Saugoti
                                                        </button>
                                                </td>
                                            </React.Fragment>
                                        ) : (
                                            <React.Fragment>
                                                <td>{meal.name}</td>
                                                <td>{meal.description}</td>
                                               
                                                {authContext.state.role === 'ADMIN' &&
                                                <td>
                                                    <button
                                                        className="btn btn-outline-primary btn-sm btn-block"
                                                        id="btnUpdateMeniu"
                                                        onClick={() => onEditData(meal)}>
                                                        Redaguoti
                                                    </button>
                                                </td>
                                                }
                                            </React.Fragment>
                                        )}

                                    {authContext.state.role === 'ADMIN' &&
                                    <td>
                                        <button
                                            onClick={() => onDelete(meal)}
                                            id="btnDeleteMeal"
                                            className="btn btn-outline-danger btn-sm btn-block">
                                            Ištrinti
                                            </button>
                                    </td>
                                    }
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

export default MealsListTable;
