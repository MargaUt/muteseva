import React, { Component } from 'react';
import '../../App.css';
import NotFoundMessage from '../08CommonComponents/NotFoundMessage';

class MapSearchDropdown extends Component {

    sendChosenKindergarten(kindergarten) {
        this.props.chosenKindergarten(kindergarten);
    }

    render() {
        const { kindergartens, search } = this.props;
        if (search !=="" && kindergartens.length===0) {
            return (
                <NotFoundMessage message="Darželis tokiu pavadinimu ar adresu nerastas" />
            )
        } else {
            return (
                <div className="d-grid gap-1 mx-auto">
                    {kindergartens.map((kindergarten) => (
                        <button
                            key={kindergarten.id}
                            type="button"
                            className=" btn btn-light text-start"
                            onClick={(e) => { this.sendChosenKindergarten(kindergarten) }}
                        ><b>{kindergarten.name}</b>, {kindergarten.address}</button>
                    ))
                    }
                </div>
            );
        }
    }
}

export default MapSearchDropdown;
