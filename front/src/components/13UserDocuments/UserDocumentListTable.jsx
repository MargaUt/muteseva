import React, { Component } from 'react'
import Table from '../08CommonComponents/Table';

export default class UserDocumentListTable extends Component {
//  Dropped .btn-block for utilities. Instead of using .btn-block on the .btn, wrap your buttons with .d-grid and a .gap-* utility to space
//             them as needed. Switch to responsive classes for even more control over them. Read the docs for some examples.
    columns = [
        {
            key: "uploadDate",
            label: "Įkelimo data",
            content: document => <span>{document.uploadDate}</span>
        },
        {
            key: "name",
            label: "Pavadinimas",
            content: document => <span>{document.name}</span>
        },
        {
            key: "download",
            label: "Atsisiųsti",
            content: document => <button
                className="btn btn-primary btn-sm w-100"
                onClick={() => this.props.onDownload(document)}
                >
                    Atsisiųsti
                </button>
        },
        {
            key: "delete",
            label: "Ištrinti pažymą",
            content: document => <button 
                className="btn btn-outline-danger btn-sm w-100"
                onClick={() => this.props.onDelete(document)}
                >
                    Ištrinti
                </button>
        }
    ]



    render() {
        const { documents } = this.props;
        return (
            <Table
                columns={this.columns}
                data={documents}
            />
        )
    }
}
