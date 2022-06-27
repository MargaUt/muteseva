import React, { Component } from 'react';

import Table from '../08CommonComponents/Table';

class UserCompensationApplicationsTable extends Component {

    columns = [
        {
            key: 'date',
            path: 'date',
            label: 'Pateikimo data',
            content: application => <span>{application.submitedAt}</span>
        },

        {
            key: 'childSurname',
            path: 'childSurname',
            label: 'Vaiko vardas, pavardė',
            content: application => <span>{application.childName} {application.childSurname}</span>
        },
        {
            key: 'kindergartenName',
            path: 'kindergartenName',
            label: 'Ugdymo įstaigos pavadinimas',
            content: application => <span>{application.kindergartenName} </span>
        },
        {
            key: 'kindergartenAccountNumber',
            path: 'kindergartenAccountNumber',
            label: 'Ugdymo įstaigos sąskaitos numeris',
            content: application => <span>{application.kindergartenAccountNumber} </span>
        },
        
        // Dropped .btn-block for utilities. Instead of using .btn-block on the .btn, wrap your buttons with .d-grid and a .gap-* utility to space them as needed.
        {
            
            key: 'delete',
            label: 'Ištrinti prašymą',
            content: application => <button onClick={() => this.props.onDelete(application)} id="btnDeleteApplication" className="btn btn-outline-danger btn-sm w-100">Ištrinti</button>

        }

    ]


    render() {
        const { applications } = this.props;

        return (
            <Table
                columns={this.columns}
                data={applications}

            />
        );
    }
}


export default UserCompensationApplicationsTable;