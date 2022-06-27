import React, { Component } from 'react';

import '../../App.css';

import { MealListContainer } from './MealListContainer';
import  MealsInputForm from './MealsInputForm';

export class MealContainer extends Component {

    render() {
        return (
            <div>
                <div className="container pt-4">

                    <div className="row ">

                    <div className="bg-light pb-3 col-12 col-sm-12 col-md-12 col-lg-3 pt-1">
                            <MealsInputForm />
                        </div>
                        <div className="col-12 col-sm-12 col-md-12 col-lg-9 pt-1">
                            <h6 className="py-3"><b>Meniu</b></h6>
                            <MealListContainer match={this.props.match} history={this.props.history}/>
                        </div>

                    </div>
                </div>
            </div>
        )
    }
}

export default MealContainer;
