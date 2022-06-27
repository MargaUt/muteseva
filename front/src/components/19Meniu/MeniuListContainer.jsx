import React, { Component } from 'react';
import swal from 'sweetalert';
import { withRouter } from 'react-router-dom';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import '../../App.css';

import MeniuListTable from './MeniuListTable';
export class MeniuListContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            menius: [],
            inEditMode: false,
            editRowId: "",
            editedMeniu: null,
            errorMessages: {}
        }
    }
    componentDidMount() {
        this.getMeniuInfo();
        document.addEventListener("keydown", this.handleEscape, false);
    }

    componentWillUnmount() {
        document.removeEventListener("keydown", this.handleEscape, false);
    }

    handleEscape = (e) => {
        if (e.key === 'Escape') {
            this.onCancel();
             
            setTimeout(function () {
                window.location.reload();
            }, 10);
        }
    }


    getMeniuInfo() {
        var id = this.props.match.params.id
        console.log("meniusssss", this.props.match)
        var uri = `${apiEndpoint}/api/meniu/` + id;
        http
            .get(uri)
            .then((response) => {
                this.setState({
                    menius: response.data,
                });

            }).catch(() => {});
    }


    handleDelete = (item) => {

        swal({
            text: "Ar tikrai norite ištrinti meniu?",
            buttons: ["Ne", "Taip"],
            dangerMode: true,
        }).then((actionConfirmed) => {
            if (actionConfirmed) {
                const id = item.id;
                http
                    .delete(`${apiEndpoint}/api/meniu/admin/deleteMeniu/${id}`)
                    .then((response) => {
                        swal({
                            text: response.data,
                            button: "Gerai"
                        });
                        this.getMeniuInfo();

                    }).catch(() => {});
            }
        });
    }

    handleView = (item) => {
        const id = item.id;
        this.props.history.push("/meals/" + id);
        
       
}

    handleEditIstaiga = (item) => {

        this.setState({
            inEditMode: true,
            editRowId: item.id,
            editedMeniu: item
        });
    }

    onCancel = () => {
        this.setState(
            {
                inEditMode: false,
                editRowId: "",
                editedMeniu: null
            }
        )
    }

    handleChange = ({ target: input }) => {

        const errorMessages = this.state.errorMessages;

        if (input.validity.valueMissing || input.validity.patternMismatch || input.validity.rangeUnderflow || input.validity.rangeOverflow || input.validity.tooLong || input.validity.tooShort) {
            errorMessages[input.name] = `*${input.title}`;
        } else {
            delete errorMessages[input.name];
        }
        const meniu = this.state.editedMeniu;
        meniu[input.name] = input.value;
        this.setState({
            editedMeniu: meniu,
            errorMessages: errorMessages
        });
    }

    handleSaveEdited = () => {
        const { editedMeniu, editRowId, errorMessages } = this.state;
     

        if (Object.keys(errorMessages).length === 0) {
            http.put(`${apiEndpoint}/api/meniu/admin/updateMeniu/${editRowId}`, editedMeniu)
                .then(() => {
                    this.onCancel();
                }).catch(error => {
                    if (error && error.response.status === 409) {
                        swal({
                            text: error.response.data,
                            button: "Gerai"
                        });
                    }
                    
                })
        }
    }




    render() {

        const { menius, inEditMode, editRowId, errorMessages } = this.state;
        console.log("meniu", menius)

        const hasErrors = Object.keys(errorMessages).length === 0 ? false : true;

        return (
            <React.Fragment>

                <MeniuListTable
                    menius={menius}
                    inEditMode={inEditMode}
                    editRowId={editRowId}
                    errorMessages={errorMessages}
                    hasErrors={hasErrors}
                    onView={this.handleView}
                    onDelete={this.handleDelete}
                    onEditData={this.handleEditIstaiga}
                    onEscape={this.handleEscape}
                    onChange={this.handleChange}
                    onSave={this.handleSaveEdited}
                />


            </React.Fragment>
        )
    }
}

export default withRouter(MeniuListContainer);
