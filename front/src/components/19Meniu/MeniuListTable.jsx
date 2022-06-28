import React, { Component } from 'react';
import NotFoundMessage from '../08CommonComponents/NotFoundMessage';
import AuthContext from "../11Context/AuthContext";

import '../../App.css';
class MeniuListTable extends Component {

    render() {
        const {
            books,
            inEditMode,
            editRowId,
            errorMessages,
            hasErrors,
            onEditData,
            onSave,
            onChange,
            onView,
            onDelete} = this.props;
        if ( books.length === 0) {
            return (
                <NotFoundMessage message="Knygos pavadinimą nerasta"/>
            )
        }else{
        return (
            <AuthContext.Consumer>{(authContext) =>
            <div className="table-responsive-md">

                <table className="table" >

                    <thead className="no-top-border">
                        <tr >
                          
                            <th>Knygos pavadinimas</th>
                            <th>Knygos santrauka</th>
                            <th>Knygos puslapių skaičius</th>
                            <th>Knygos ISBN</th>
                            <th>Knygos paveiksliukas</th>
                            {authContext.state.role === 'ADMIN' &&
                            <th>Redaguoti duomenis</th>
                            }
                            
                            {authContext.state.role === 'ADMIN' &&
                            <th className="deleteColumn">Ištrinti meniu</th>
                            }
                        </tr>
                    </thead>
                    <tbody >
                        {
                            books.map((book) => (
                                <tr key={book.id}>

                                    {inEditMode && editRowId === book.id ?
                                        (
                                            <React.Fragment>
                                                <td>
                                                    <input
                                                        type="text"
                                                        className="form-control"
                                                        id="txtMeniuName"
                                                        name="meniuName"
                                                        value={book.bookName}
                                                        onChange={(event) => onChange(event)}
                                                        placeholder="Knygos Pavadinimas"
                                                        title="Knygos pavadinimas privalomas"
                                                        required
                                                    />
                                                    
                                                    {errorMessages.bookName && <div className="text-danger">{errorMessages.bookName}</div>}
                                                </td>
                                     
                                                <td>
                                                    <input
                                                        type="text"
                                                        className="form-control"
                                                        id="txtMeniuName"
                                                        name="santrauka"
                                                        value={book.santrauka}
                                                        onChange={(event) => onChange(event)}
                                                        placeholder="Knygos santrauka"
                                                        title="Knygos santrauka privaloma"
                                                        required
                                                    />
                                                    
                                                    {errorMessages.santrauka && <div className="text-danger">{errorMessages.santrauka}</div>}
                                                </td>

                                                <td>
                                                    <input
                                                        type="text"
                                                        className="form-control"
                                                        id="txtMeniuName"
                                                        name="booksPages"
                                                        value={book.booksPages}
                                                        onChange={(event) => onChange(event)}
                                                        placeholder="Knygos puslapių skaičius"
                                                        title="Knygos puslapių skaičius privalomas"
                                                        required
                                                    />
                                                    
                                                    {errorMessages.booksPages && <div className="text-danger">{errorMessages.booksPages}</div>}
                                                </td>
                                                <td>
                                                    <input
                                                        type="text"
                                                        className="form-control"
                                                        id="txtMeniuName"
                                                        name="isbn"
                                                        value={book.isbn}
                                                        onChange={(event) => onChange(event)}
                                                        placeholder="Knygos ISBN kodas"
                                                        title="Knygos ISBN kodas privalomas"
                                                        required
                                                    />
                                                    
                                                    {errorMessages.isbn && <div className="text-danger">{errorMessages.isbn}</div>}
                                                </td>

                                                
                                                    <td>
                                                        <input
                                                            type="text"
                                                            className="form-control"
                                                            id="txtMeniuName"
                                                            name="picture"
                                                            value={book.picture}
                                                            onChange={(event) => onChange(event)}
                                                            placeholder="Knygos paveiksliukas"
                                                            title="Knygos puslapių skaičius privalomas"
                                                            required
                                                        />
                                                        
                                                        {errorMessages.picture && <div className="text-danger">{errorMessages.picture}</div>}
                                                    </td>
                                   
                                                <td>
                                                    <button
                                                        type="submit"
                                                        className="btn btn-primary btn-sm btn-block"
                                                        id="btnSaveUpdatedMeniu"
                                                        onClick={() => onSave({ id: book.id, item: book })}
                                                        disabled={hasErrors}>
                                                        Saugoti
                                                        </button>
                                                </td>
                                            </React.Fragment>
                                        ) : (
                                            <React.Fragment>
                                                
                                                <td>{book.bookName}</td>
                                                <td>{book.santrauka}</td>
                                                <td>{book.booksPages}</td>
                                                <td>{book.isbn}</td>
                                                <td>{book.picture}</td>

                                                {authContext.state.role === 'ADMIN' &&
                                                <td>
                                                    <button
                                                        className="btn btn-outline-primary btn-sm btn-block"
                                                        id="btnUpdateMeniu"
                                                        onClick={() => onEditData(book)}>
                                                        Redaguoti
                                                    </button>
                                                </td>
                                                }
                                            </React.Fragment>
                                        )}

                                    {/* <td>
                                        <button
                                            onClick={() => onView(book)}
                                            id="btnViewMeniu"
                                            className="btn btn-outline-danger btn-sm btn-block">
                                            Peržiūrėti patiekalus
                                            </button>
                                    </td> */}
                                    {/* {authContext.state.role === 'ADMIN' &&
                                    <td>
                                        <button
                                            onClick={() => onDelete(book)}
                                            id="btnDeleteMeniu"
                                            className="btn btn-outline-danger btn-sm btn-block">
                                            Ištrinti
                                            </button>
                                    </td>
                                    } */}
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

export default MeniuListTable;
