import React, { Component } from 'react';
import swal from 'sweetalert';
import { withRouter } from 'react-router-dom';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import '../../App.css';

import MeniuListTable from './MealsListTable';
export class MealListContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            meals: [],
            inEditMode: false,
            editRowId: "",
            editedMeal: null,
            errorMessages: {}
        }
    }
    componentDidMount() {
        this.getMealsInfo();
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


    getMealsInfo() {

        var id = this.props.match.params.id
        console.log("meals", id)
        var uri = `${apiEndpoint}/api/meals/` + id;
        http
            .get(uri)
            .then((response) => {
                this.setState({
                    meals: response.data,
                });

            }).catch(() => {});
    }


    handleDelete = (item) => {

        swal({
            text: "Ar tikrai norite ištrinti patiekalą?",
            buttons: ["Ne", "Taip"],
            dangerMode: true,
        }).then((actionConfirmed) => {
            if (actionConfirmed) {
                const id = item.id;
                http
                    .delete(`${apiEndpoint}/api/meals/admin/delete/${id}`)
                    .then((response) => {
                        swal({
                            text: response.data,
                            button: "Gerai"
                        });
                        this.getMealsInfo();

                    }).catch(() => {});
            }
        });
    }

    handleView = (item) => {
        const id = item.id;
        this.props.history.push("/meals/" + id);
        
       
}

    handleEditMeal = (item) => {

        this.setState({
            inEditMode: true,
            editRowId: item.id,
            editedMeal: item
        });
    }

    onCancel = () => {
        this.setState(
            {
                inEditMode: false,
                editRowId: "",
                editedMeal: null
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
        const meal = this.state.editedMeal;
        meal[input.name] = input.value;
        this.setState({
            editedMeal: meal,
            errorMessages: errorMessages
        });
    }

    handleSaveEdited = () => {
        const { editedMeal, editRowId, errorMessages } = this.state;
     

        if (Object.keys(errorMessages).length === 0) {
            http.put(`${apiEndpoint}/api/meals/admin/update/${editRowId}`, editedMeal)
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

        const { meals, inEditMode, editRowId, errorMessages } = this.state;

        const hasErrors = Object.keys(errorMessages).length === 0 ? false : true;

        return (
            <React.Fragment>

                <MeniuListTable
                    meals={meals}
                    inEditMode={inEditMode}
                    editRowId={editRowId}
                    errorMessages={errorMessages}
                    hasErrors={hasErrors}
                    onView={this.handleView}
                    onDelete={this.handleDelete}
                    onEditData={this.handleEditMeal}
                    onEscape={this.handleEscape}
                    onChange={this.handleChange}
                    onSave={this.handleSaveEdited}
                />


            </React.Fragment>
        )
    }
}

export default withRouter(MealListContainer);
