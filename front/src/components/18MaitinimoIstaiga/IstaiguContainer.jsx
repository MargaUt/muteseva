import React, { Component } from 'react';

import '../../App.css';

import { IstaiguListContainer } from './IstaiguListContainer';
import  IstaiguInputForm from './IstaiguInputForm';

export class IstaiguContainer extends Component {


    render() {
        console.log("this", this)
        return (
            <div>
                <div className="container pt-4">

                    <div className="row ">

                    <div className="bg-light pb-3 col-12 col-sm-12 col-md-12 col-lg-3 pt-1">
                            <IstaiguInputForm />
                        </div>
                        <div className="col-12 col-sm-12 col-md-12 col-lg-9 pt-1">
                            <h6 className="py-3"><b>Maitinimo Įstaigos</b></h6>
                            <IstaiguListContainer history={this.props.history} />
                        </div>

                    </div>
                </div>
            </div>
        )
    }
}

export default IstaiguContainer;
