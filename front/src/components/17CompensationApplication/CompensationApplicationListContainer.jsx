import React, { Component } from 'react';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import '../../App.css';

import CompensationApplicationListTable from './CompensationApplicationListTable';
import Pagination from './../08CommonComponents/Pagination';
import SearchBox from './../08CommonComponents/SeachBox';
export class CompensationApplicationListContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            kompensacijos: [],
            pageSize: 10,
            currentPage: 1,
            totalPages: 0,
            totalElements: 0,
            numberOfElements: 0,
            searchQuery: "",
            errorMessages: {}
        }
    }
    componentDidMount() {
        this.getCompensationApplicationInfo(this.state.currentPage, "");
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


    getCompensationApplicationInfo(currentPage, submitedAt) {

        const { pageSize } = this.state;

        let page = currentPage - 1;

        if (page < 0) page = 0;

        var uri = `${apiEndpoint}/api/naujas_kompensacija/manager/page?page=${page}&size=${pageSize}`;

        if (submitedAt !== "") {
            const encodedDate = encodeURIComponent(submitedAt);
            uri = `${apiEndpoint}/api/naujas_kompensacija/manager/page?page=${page}&size=${pageSize}&search=${encodedDate}`;

        }
        if (submitedAt.length===10 ||submitedAt===""){
            http
            .get(uri)
            .then((response) => {

                this.setState({
                    kompensacijos: response.data.content,
                    totalPages: response.data.totalPages,
                    totalElements: response.data.totalElements,
                    numberOfElements: response.data.numberOfElements,
                    currentPage: response.data.number + 1
                });

            }).catch(() => { });
        }
    }

    handleSearch = (e) => {

        const submitedAt = e.currentTarget.value;
        this.setState({ searchQuery: submitedAt });
        this.getCompensationApplicationInfo(1, submitedAt);
    }



    handlePageChange = (page) => {
        this.setState({ currentPage: page });
        this.getCompensationApplicationInfo(page, this.state.searchQuery);
    };



    render() {

        const placeholder = "Ieškoti pagal pateikomo datą pvz. 2021-07-09";

        const { kompensacijos, totalElements, pageSize, searchQuery, errorMessages } = this.state;

        return (

            <div>
                <div className="container pt-4">
                    <h6 className="py-3"><b>Kompensacijų prašymai</b></h6>

                    <React.Fragment>

                        <SearchBox
                            value={searchQuery}
                            onSearch={this.handleSearch}
                            placeholder={placeholder}
                        />

                        <CompensationApplicationListTable
                            kompensacijos={kompensacijos}
                            errorMessages={errorMessages}
                            onEscape={this.handleEscape}
                            search={searchQuery}
                        />

                        <Pagination
                            itemsCount={totalElements}
                            pageSize={pageSize}
                            onPageChange={this.handlePageChange}
                            currentPage={this.state.currentPage}
                        />

                    </React.Fragment>
                </div>
            </div>
        )
    }
}

export default CompensationApplicationListContainer;