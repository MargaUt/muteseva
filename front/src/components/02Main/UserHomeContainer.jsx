import React, { Component } from 'react';

import '../../App.css';
import swal  from 'sweetalert';

import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import Pagination from '../08CommonComponents/Pagination';
import UserApplicationsTable from './UserApplicationsTable';
import UserCompensationApplicationsTable from './UserCompensationApplicationsTable';

export class UserHomeContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            applications: [],
            compensationApplications: [],
            applicationPagination: {
                currentPage: 1,
                postsPerPage: 5,
            },
            compensationApplicationPagination: {
                currentPage: 1,
                postsPerPage: 5,
            }
        }
    }
    componentDidMount() {
        this.getUserApplications();
        this.getUserCompensationApplications();
    }

    getUserApplications() {
        http
            .get(`${apiEndpoint}/api/prasymai/user`)
            .then((response) => {
                
                this.setState({ applications: response.data });
                const orderedApplications = this.state.applications.reverse();
                this.setState({ applications: orderedApplications});

            }).catch(() => {});
    }

    getUserCompensationApplications() {
        http
            .get(`${apiEndpoint}/api/naujas_kompensacija/user`)
            .then((response) => {
                
                this.setState({ compensationApplications: response.data });
                const orderedCompensationApplications = this.state.compensationApplications.reverse();
                this.setState({ compensationApplications: orderedCompensationApplications });
                
            }).catch(() => {});

    }

    handleApplicationDelete = (item) => {
       
        swal({
            text: "Ar tikrai norite ištrinti prašymą?",
            buttons: ["Ne", "Taip"],
            dangerMode: true,
        }).then((actionConfirmed) => {
            if (actionConfirmed) {
                http.delete(`${apiEndpoint}/api/prasymai/user/delete/${item.id}`)
                    .then((response) => {                       
                        swal({
                            text: response.data,
                            button: "Gerai"
                        })
                        this.setState({ applicationPagination: {
                            ...this.state.applicationPagination,
                            currentPage: 1
                        }})
                        this.getUserApplications();
                    }).catch(() => {});
            }
        });
    }

    handleCompensationApplicationDelete = (item) => {
       
        swal({
            text: "Ar tikrai norite ištrinti prašymą?",
            buttons: ["Ne", "Taip"],
            dangerMode: true,
        }).then((actionConfirmed) => {
            if (actionConfirmed) {
                http.delete(`${apiEndpoint}/api/naujas_kompensacija/user/delete/${item.id}`)
                    .then((response) => {                       
                        swal({
                            text: response.data,
                            button: "Gerai"
                        })
                        this.setState({ compensationApplicationPagination: {
                            ...this.state.applicationPagination,
                            currentPage: 1
                        }})
                        this.getUserCompensationApplications();
                    }).catch(() => {});
            }
        });
    }

        getApplications() {
            const indexOfLastPost = this.state.applicationPagination.currentPage
             * this.state.applicationPagination.postsPerPage;
            const indexOfFirstPost = indexOfLastPost - this.state.applicationPagination.postsPerPage;
            const currentPosts = this.state.applications.slice(indexOfFirstPost, indexOfLastPost);

            const paginate = pageNumber => this.setState({ applicationPagination: {
                ...this.state.applicationPagination,
                currentPage: pageNumber
            }})

            if (this.state.applications.length > 0) {
                return (
                <>
                    <h6 className="pl-2 pt-3">Prašymai dėl patekimo į darželius</h6>
                    <div className="row pt-2">
                        <div className="col-12 col-sm-12 col-md-12 col-lg-12">
                            <UserApplicationsTable
                                applications={currentPosts}
                                onDelete={this.handleApplicationDelete}
                            />
                            <Pagination 
                                itemsCount={this.state.applications.length}
                                pageSize={this.state.applicationPagination.postsPerPage} 
                                currentPage={this.state.applicationPagination.currentPage}
                                onPageChange={paginate}
                            />
                        </div>
                    </div>
                </>
                )
            }
        }

        getCompensationApplications() {

            const indexOfLastPost = this.state.compensationApplicationPagination.currentPage
             * this.state.compensationApplicationPagination.postsPerPage;
            const indexOfFirstPost = indexOfLastPost - this.state.compensationApplicationPagination.postsPerPage;
            const currentPosts = this.state.compensationApplications.slice(indexOfFirstPost, indexOfLastPost)

            const paginate = pageNumber => this.setState({ compensationApplicationPagination: {
                ...this.state.compensationApplicationPagination,
                currentPage: pageNumber
            }})

            if (this.state.compensationApplications.length > 0) {
                return (
                    <>
                        <h6 className="pl-2 pt-3">Kompensacijų prašymai</h6>
                        <div className="row pt-2">
                            <div className="col-12 col-sm-12 col-md-12 col-lg-12">
                                <UserCompensationApplicationsTable
                                applications={currentPosts}
                                onDelete={this.handleCompensationApplicationDelete}
                                />
                                <Pagination 
                                itemsCount={this.state.compensationApplications.length}
                                pageSize={this.state.compensationApplicationPagination.postsPerPage} 
                                currentPage={this.state.compensationApplicationPagination.currentPage}
                                onPageChange={paginate}
                                />
                            </div>
                        </div>
                    </>
                )
            }
        }

        
        render() {

        if (this.state.applications.length + this.state.compensationApplications.length === 0) return <div className="container pt-5"><h6 className="pt-5">Jūs neturite pateiktų prašymų.</h6></div>

        return (

            <div className="container pt-4" >
                {this.getApplications()}
                {this.getCompensationApplications()}
                
            </div>
        )
    }
}

export default UserHomeContainer
