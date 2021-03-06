import React, { Component } from 'react'
import '../../App.css';
import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import swal from 'sweetalert';
import UserDocumentListTable from './UserDocumentListTable';

export default class UserDocumentContainer extends Component {
    
    constructor(props) {
        super(props);
        this.state = {
            documentList: [],
            showUploadForm: false,
            documentToUpload: "",
            documentValid: false,
        };
        this.uploadDocument = this.uploadDocument.bind(this);
        this.uploadForm = this.uploadForm.bind(this);
        this.uploadDocumentOnChange = this.uploadDocumentOnChange.bind(this);
        this.getDocuments = this.getDocuments.bind(this);
        this.handleDownload = this.handleDownload.bind(this);
    }
    
    componentDidMount() {
        this.getDocuments();
    }

    getDocuments() {
        http.get(`${apiEndpoint}/api/documents/documents`)
            .then((response) => {
                const documentListInWrongOrder = this.mapDocumentsToViewmodel(response.data)
                this.setState({
                    documentList: documentListInWrongOrder.reverse()
                })
            })
            .catch((error) => {
                swal({         
                    text: "Įvyko klaida perduodant duomenis iš serverio.",         
                    button: "Gerai",
                  });
                //console.log(error);
            })
    }

    mapDocumentsToViewmodel = (docList) => {
        const docViewmodelList = docList.map((document) => ({
            id: document.documentId,
            name: document.name,
            uploadDate: document.uploadDate
        }))
        return docViewmodelList;
    }

    uploadDocument(document) {
        const formData = new FormData();
        formData.append('name',document.name);
        formData.append('file',document);
        const config = {
            headers: {
                'content-type': 'multipart/form-data'
            }
        }
        http.post(`${apiEndpoint}/api/documents/upload`, formData, config)
            .then((response) => {
                this.getDocuments();
                swal({
                    text: "Pažyma buvo įkelta sėkmingai",
                    buttons: "Gerai"
                })
                this.setState({showUploadForm: false, documentToUpload: "", documentValid: false})
            })
            .catch((error) => {
                //console.log(error);
                swal({
                    text: "Įvyko klaida įkeliant pažymą",
                    buttons: "Gerai"
                })
            })
    }

    validateDocument = (doc) => {
        if(doc.type === "application/pdf" && doc.size <= 1024000) {
            this.setState({documentValid: true});
        }
        else {
            this.setState({documentValid: false});
        }
    }

    uploadDocumentOnChange(e) {
        const file = e.target.files[0];
        if(file.type === "application/pdf") {
            if(file.size <= 1024000) {
                this.setState({documentToUpload: file});
            }
            else {
                swal({
                    text: "Failas per didelis, leidžiama iki 1 MB",
                    icon: "error",
                })
            }
        }
        else {
            swal({
                text: "Netinkamo formato dokumentas. Pažyma turi būti pdf formatu",
                icon: "error",
            })
        }
        this.validateDocument(file);
    }

    uploadForm = () => {
        if(this.state.showUploadForm) {
            return (
                <div className="form">
                    <div className="mb-3">
                        <h6 className="py-3">Pažyma privalo būti .pdf formato ir neužimti daugiau negu 1 MB vietos.</h6>
                        <input 
                            type="file"
                            className="form-control-file"
                            id="inputUploadDocument"
                            onChange={this.uploadDocumentOnChange}
                        />
                    </div>
                    <div className="row">
                        <div className="col">
                            <button 
                                id="btnUploadDocument"
                                className="btn btn-primary"
                                onClick={() => this.uploadDocument(this.state.documentToUpload)}
                                disabled={!this.state.documentValid}
                            >
                                Įkelti
                            </button>
                            <button
                                id="btnCancelUpload"
                                className="btn btn-secondary"
                                style={{marginLeft: "64px"}}
                                onClick={() => {this.setState({showUploadForm: false, documentValid: false})}}>
                                Atšaukti
                            </button>
                        </div>   
                    </div>
                </div>
            )
        }
        else {
            return (
                <div className="row">
                    <div className="col">
                        <button 
                            id="btnUploadDocument"
                            className="btn btn-primary"
                            onClick={() => {this.setState({showUploadForm: true})}}
                        >
                            Įkelti naują
                        </button>
                    </div>
                </div>
            )
        }
    }

    handleDelete = (document) => {
        swal({
            text: "Ar tikrai norite ištrinti pažymą?",
            buttons: ["Ne", "Taip"],
            dangerMode: true,
        }).then((actionConfirmed) => {
            if(actionConfirmed) {
                http.delete(`${apiEndpoint}/api/documents/delete/${document.id}`)
                    .then((response) => {
                        this.getDocuments();
                        swal({
                            text: "Pažyma buvo sėkmingai ištrinta",
                            button: "Gerai"
                        })
                    })
                    .catch((error) => {
                        //console.log(error);
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
            //console.log(error);
            swal({
                text: "Įvyko klaida atsisiunčiant pažymą.",
                buttons: "Gerai",
            })
        })
    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col">
                        <h6 className="py-3"><b>Mano pažymos</b></h6>
                    </div>
                </div>
                <div className="row">
                    <div className="col">
                        {
                            //** Upload Form */
                            this.uploadForm()
                        }
                    </div>
                </div>
                <div className="row formHeader">
                    <div className="col-6">
                        {
                        //**UserDocumentList */
                        <UserDocumentListTable
                            documents={this.state.documentList}
                            onDelete={this.handleDelete}
                            onDownload={this.handleDownload}
                        />
                        }
                    </div>
                </div>
            </div>
        )
    }
}
