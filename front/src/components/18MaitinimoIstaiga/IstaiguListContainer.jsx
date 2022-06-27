import React, { Component } from 'react';
import swal from 'sweetalert';
import { withRouter } from 'react-router-dom';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import '../../App.css';

import IstaiguListTable from './IstaiguListTable';
import Pagination from '../08CommonComponents/Pagination';
import SearchBox from '../08CommonComponents/SeachBox';
export class IstaiguListContainer extends Component {



    constructor(props, context) {
        super(props, context);
        this.state = {
            istaigos: [],
            pageSize: 10,
            currentPage: 1,
            totalPages: 0,
            totalElements: 0,
            numberOfElements: 0,
            searchQuery: "",
            inEditMode: false,
            editRowId: "",
            editedIstaga: null,
            errorMessages: {}
        }
    }
    componentDidMount() {
        this.getIstaigosInfo(this.state.currentPage, "");
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


    getIstaigosInfo(currentPage, pavadinimas) {

        const { pageSize } = this.state;

        let page = currentPage - 1;

        if (page < 0 ) page = 0;

        var uri = `${apiEndpoint}/api/istaigos/user/page?page=${page}&size=${pageSize}`;

        if (pavadinimas !== "") {
            let encodedName = encodeURIComponent(pavadinimas);
            uri = `${apiEndpoint}/api/istaigos/user/page?page=${page}&search=${encodedName}&size=${pageSize}`;

        }

        http
            .get(uri)
            .then((response) => {

                this.setState({
                    istaigos: response.data.content,
                    totalPages: response.data.totalPages,
                    totalElements: response.data.totalElements,
                    numberOfElements: response.data.numberOfElements,
                    currentPage: response.data.number + 1
                });

            }).catch(() => {});
    }
    
    handleSearch = (e) => {

        const pavadinimas = e.currentTarget.value;
        this.setState({ searchQuery: pavadinimas });
        this.getIstaigosInfo(1, pavadinimas);
    }

    handleDelete = (item) => {

        swal({
            text: "Ar tikrai norite ištrinti maitinimo įstaigą?",
            buttons: ["Ne", "Taip"],
            dangerMode: true,
        }).then((actionConfirmed) => {
            if (actionConfirmed) {
                const id = item.id;
                const { currentPage, numberOfElements } = this.state;
                const page = numberOfElements === 1 ? (currentPage - 1) : currentPage;
                http
                    .delete(`${apiEndpoint}/api/istaigos/admin/delete/${id}`)
                    .then((response) => {
                        swal({
                            text: response.data,
                            button: "Gerai"
                        });
                        this.setState({searchQuery: ""});
                        this.getIstaigosInfo(page, "");

                    }).catch(() => {});
            }
        });
    }




    handleView = (item) => {
            const id = item.id
            ;
            this.props.history.push("/meniu/" + id);
            
           
    }

    handleEditIstaiga = (item) => {

        this.setState({
            inEditMode: true,
            editRowId: item.id,
            editedIstaga: item
        });
    }

    onCancel = () => {
        this.setState(
            {
                inEditMode: false,
                editRowId: "",
                editedIstaga: null
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
        const istaiga = this.state.editedIstaga;
        istaiga[input.name] = input.value;
        this.setState({
            editedIstaga: istaiga,
            errorMessages: errorMessages
        });
    }

    handleSaveEdited = () => {
        const { editedIstaga, editRowId, errorMessages } = this.state;
     

        if (Object.keys(errorMessages).length === 0) {
            http.put(`${apiEndpoint}/api/istaigos/admin/update/${editRowId}`, editedIstaga)
                .then(() => {
                    this.onCancel();
                    this.getIstaigosInfo(this.state.currentPage, this.state.searchQuery);
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


    handlePageChange = (page) => {
        this.setState({ currentPage: page });
        this.getIstaigosInfo(page, this.state.searchQuery);
    };



    render() {

        const placeholder = "Ieškoti pagal pavadinimą...";

        const { istaigos, totalElements, pageSize, searchQuery, inEditMode, editRowId, errorMessages, currentUser } = this.state;

        const hasErrors = Object.keys(errorMessages).length === 0 ? false : true;

        return (
            <React.Fragment>

                <SearchBox
                    value={searchQuery}
                    onSearch={this.handleSearch}
                    placeholder={placeholder}
                />

                <IstaiguListTable
                    istaigos={istaigos}
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
                    search={searchQuery}
                    currentUser={currentUser}
                />

                <Pagination
                    itemsCount={totalElements}
                    pageSize={pageSize}
                    onPageChange={this.handlePageChange}
                    currentPage={this.state.currentPage}
                />


            </React.Fragment>
        )
    }
}

export default withRouter(IstaiguListContainer);

