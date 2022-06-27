import React, { Component } from 'react'
import Table from '../08CommonComponents/Table';
import NotFoundMessage from '../08CommonComponents/NotFoundMessage';

export default class ManagerDocumentListTable extends Component {
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
            label: "Atstovo vardas, pavardė",
            content: document => <span>{document.fullName}</span>
        },
        {
            key: "download",
            label: "Atsisiųsti",
            content: document => <button
                className="btn btn-primary btn-sm w-45"
                onClick={() => this.props.onDownload(document)}
            >
                Atsisiųsti
            </button>
        }
    ]



    render() {

        const { documents, search } = this.props;

        if (search !== "" && documents.length === 0) {
            return (
                <NotFoundMessage message="Pažymų pagal įvestą vardą ir pavardę nerasta" />
            )
        } else {
            return (
                <Table
                    columns={this.columns}
                    data={documents}
                />
            )
        }
    }
}
