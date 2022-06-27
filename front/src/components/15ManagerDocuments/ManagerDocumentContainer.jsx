import React, { Component } from 'react'
import '../../App.css';
import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import swal from 'sweetalert';
import Pagination from './../08CommonComponents/Pagination';
import ManagerDocumentListTable from './ManagerDocumentListTable';
import SearchBox from './../08CommonComponents/SeachBox';

export default class ManagerDocumentContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            documentList: [],
            showUploadForm: false,
            documentToUpload: "",
            documentValid: false,
            pageSize: 10,
            currentPage: 1,
            totalPages: 0,
            totalElements: 0,
            numberOfElements: 0,
            searchQuery: ""
        };
  
      
    }

    componentDidMount() {
        this.getDocuments(this.state.currentPage, "");
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
    getDocuments = (currentPage, searchQuery) => {

        const { pageSize } = this.state;

        let page = currentPage - 1;

        if (page < 0) page = 0;

        var uri = `${apiEndpoint}/api/documents/documents/page?page=${page}&size=${pageSize}`;

        if (searchQuery !== "") {
            const encodedSearch = encodeURIComponent(searchQuery);
            uri = `${apiEndpoint}/api/documents/documents/page?page=${page}&size=${pageSize}&search=${encodedSearch}`;
        }

        // if (uploadDate !== "") {
        //     uri = `${apiEndpoint}/api/documents/documents/page/${uploadDate}?page=${page}&size=${pageSize}`;
        // }
        http.get(uri)
            .then((response) => {
                this.setState({
                    documentList: this.mapDocumentsToViewmodel(response.data.content),
                    totalPages: response.data.totalPages,
                    totalElements: response.data.totalElements,
                    numberOfElements: response.data.numberOfElements,
                    currentPage: response.data.number + 1

                })
            })
            .catch(() => {});
    }

    mapDocumentsToViewmodel = (docList) => {
        const docViewmodelList = docList.map((document) => ({
            id: document.documentId,
            name: document.name,
            fullName: document.fullName,
            uploadDate: document.uploadDate
        }))
        return docViewmodelList;
    }


    handleDelete = (document) => {
        swal({
            text: "Ar tikrai norite ištrinti pažymą?",
            buttons: ["Ne", "Taip"],
            dangerMode: true,
        }).then((actionConfirmed) => {
            if (actionConfirmed) {
                http.delete(`${apiEndpoint}/api/documents/delete/${document.id}`)
                    .then((response) => {
                        this.getDocuments(this.state.currentPage, "");
                        swal({
                            text: "Pažyma buvo sėkmingai ištrinta",
                            button: "Gerai"
                        })
                    })
                    .catch((error) => {
                        swal({
                            text: "Įvyko klaida",
                            button: "Gerai"
                        })
                    })
            }
        })
    }

    handleDownload = (doc) => {
        http.request({
            url: `${apiEndpoint}/api/documents/get/${doc.id}`,
            method: "GET",
            responseType: "blob",
        }).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', `${doc.name}`);
            document.body.appendChild(link);
            link.click();
            link.remove();
        }).catch((error) => {
            swal({
                text: "Įvyko klaida atsisiunčiant pažymą.",
                buttons: "Gerai",
            })
        })
    }
    
    handlePageChange = (page) => {
        this.setState({ currentPage: page });
        this.getDocuments(page, this.state.searchQuery);
    };

    handleSearch = (e) => {

        const searchQuery = e.currentTarget.value;
        this.setState({ searchQuery: searchQuery });
        this.getDocuments(1, searchQuery);
    }

    render() {
        const placeholder = "Ieškoti pagal vaiko atstovo vardą ir pavardę";
        const { documentList, totalElements, pageSize, searchQuery} = this.state;
        return (
            <div>
                <div className="container pt-4">
                    <h6 className="py-3"><b>Vaiko atstovų pateiktos pažymos</b></h6>
            <React.Fragment>
                    <SearchBox
                        value={searchQuery}
                        onSearch={this.handleSearch}
                        placeholder={placeholder}
                     />
                    <ManagerDocumentListTable
                        documents={documentList}
                        onEscape={this.handleEscape}
                        onDelete={this.handleDelete}
                        onDownload={this.handleDownload}
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
