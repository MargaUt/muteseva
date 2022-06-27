import React, { Component } from 'react';
import '../../App.css';
import { Map, InfoWindow, Marker, GoogleApiWrapper } from 'google-maps-react';

import http from "../10Services/httpService";
import apiEndpoint from "../10Services/endpoint";
import swal from "sweetalert";
import Geocode from "react-geocode";
import MapSearchContainer from "./MapSearchContainer"


class MapContainer extends Component {

  constructor(props) {
    super(props);
    this.state = {
      kindergartenList: [],
      emptyKindergartenList: [],
      kindergardenLoaded: false,
      showInfoWindow: false,
      activeMarker: {},
      activeKindergarten: {},
      filteredKindergartenList: [],
      filteredEmptyKindergartenList: [],
      elderates: [],
      selectedElderate: "",
    };
    this.getCoordinatesFromAddress = this.getCoordinatesFromAddress.bind(this);
    this.onMarkerClick = this.onMarkerClick.bind(this);
  }

  getCoordinatesFromAddress = async (kindergarten) => {
    return await new Promise(function (resolve, reject) {
      Geocode.setApiKey("AIzaSyC1DUUuEflhsnMlXxMXE786hc9IdSGGw5w");
      let kindergartenNew = kindergarten;
      Geocode.fromAddress(kindergarten.address + ", Vilnius").then(
        (response) => {
          const { lat, lng } = response.results[0].geometry.location;
          kindergarten = {
            name: kindergartenNew.name,
            address: kindergartenNew.address,
            elderate: kindergartenNew.elderate,
            id: kindergartenNew.id,
            capacityAgeGroup2to3: kindergartenNew.capacityAgeGroup2to3,
            capacityAgeGroup3to6: kindergartenNew.capacityAgeGroup3to6,
            placesTakenAgeGroup2to3: kindergartenNew.placesTakenAgeGroup2to3,
            placesTakenAgeGroup3to6: kindergartenNew.placesTakenAgeGroup3to6,
            latState: lat,
            lngState: lng,
          }
          if (response.status === 'OK') {
            resolve(kindergarten);
          }
        }).catch((error) => {
          resolve(kindergarten)
        })
    })
  }

  getKindergartenFromSearch = (kindergarten) => {
    var kindergartenListTemp = this.state.kindergartenList.filter(k => k.id === kindergarten.id)
    var emptyKindergartenListTemp = this.state.emptyKindergartenList.filter(k => k.id === kindergarten.id)
    this.setState({
      filteredKindergartenList: kindergartenListTemp,
      filteredEmptyKindergartenList: emptyKindergartenListTemp,
      showInfoWindow: false,
      selectedElderate: ""
    })
  }

  getElderates() {
    http
      .get(`${apiEndpoint}/api/darzeliai/elderates`)
      .then((response) => {
        this.setState({ elderates: response.data });
      })
      .catch(() => { });
  }

  handleElderateChange = (event) => {
    if (event.target.value === "Visos seniūnijos") {
      this.setState({
        selectedElderate: "",
        filteredKindergartenList: this.state.kindergartenList,
        filteredEmptyKindergartenList: this.state.emptyKindergartenList,
        showInfoWindow: false,
      });
    } else {
      var kindergartenListTemp = this.state.kindergartenList.filter(k => k.elderate === event.target.value);
      var emptyKindergartenListTemp = this.state.emptyKindergartenList.filter(k => k.elderate === event.target.value)
      this.setState({
        selectedElderate: event.target.value,
        filteredKindergartenList: kindergartenListTemp,
        filteredEmptyKindergartenList: emptyKindergartenListTemp,
        showInfoWindow: false,
      });
    }
  };

  componentDidMount() {
    this.cleanSearch = this.cleanSearch.bind(this);
    this.getElderates();
    var kindergartenListTemp = [];
    http.get(`${apiEndpoint}/api/darzeliai`).then((response) => {
      kindergartenListTemp = response.data.map((k) => ({
        name: k.name,
        address: k.address,
        elderate: k.elderate,
        id: k.id,
        capacityAgeGroup2to3: k.capacityAgeGroup2to3,
        capacityAgeGroup3to6: k.capacityAgeGroup3to6,
        placesTakenAgeGroup2to3: k.placesTakenAgeGroup2to3,
        placesTakenAgeGroup3to6: k.placesTakenAgeGroup3to6,
        latState: null,
        lngState: null
      }));

      const getKindergartenCoordinates = async () => {
        return await Promise.all(kindergartenListTemp.map((k) => this.getCoordinatesFromAddress(k)))
      }

      getKindergartenCoordinates().then(data =>
        this.setState({
          kindergartenList: data,
          filteredKindergartenList: data,
        }))
    }).catch((error) => {
      swal({
        text: "Įvyko klaida perduodant duomenis iš serverio.",
        button: "Gerai",
      });
    });

    var emptyKindergartenListTemp = [];
    http.get(`${apiEndpoint}/api/darzeliai/emptyKindergartens`).then((response) => {
      emptyKindergartenListTemp = response.data.map((k) => ({
        name: k.name,
        address: k.address,
        elderate: k.elderate,
        id: k.id,
        capacityAgeGroup2to3: k.capacityAgeGroup2to3,
        capacityAgeGroup3to6: k.capacityAgeGroup3to6,
        placesTakenAgeGroup2to3: k.placesTakenAgeGroup2to3,
        placesTakenAgeGroup3to6: k.placesTakenAgeGroup3to6,
        latState: null,
        lngState: null
      }));

      const getEmptyKindergartenCoordinates = async () => {
        return await Promise.all(emptyKindergartenListTemp.map((k) => this.getCoordinatesFromAddress(k)))
      }

      getEmptyKindergartenCoordinates().then(data =>
        this.setState({
          emptyKindergartenList: data,
          filteredEmptyKindergartenList: data,
          kindergardenLoaded: true,
        }))
    }).catch((error) => {
      swal({
        text: "Įvyko klaida perduodant duomenis iš serverio.",
        button: "Gerai",
      });
    });
  }


  onMarkerClick(props, marker) {
    this.setState({
      activeMarker: marker,
      showInfoWindow: true,
      activeKindergarten: props.name
    })
  }

  cleanSearch = () => {
    this.setState({
      showInfoWindow: false,
      filteredEmptyKindergartenList: this.state.emptyKindergartenList,
      filteredKindergartenList: this.state.kindergartenList,
      selectedElderate: "",

    })
  }

  render() {
    return(
    <div className="container pt-4">
      <div className="row ">
        <div className="bg-light col-12 col-sm-12 col-md-12 col-lg-4 pt-1">
          <div className="mt-3">
            <select
              type="text"
              className="form-select"
              name="elderate"
              id="elderate"
              value={this.state.selectedElderate}
              onChange={this.handleElderateChange}
              // onInvalid={validateField}
              placeholder="Seniūnija"
              data-toggle="tooltip"
              data-placement="top"
              title="Pasirinkite seniūniją, kuriai priskirtas darželis"
            >
              <option value="" disabled hidden label="Rūšiuoti pagal seniūnijas" />
              <option value="Visos seniūnijos" label="Visos seniūnijos" key="Visos seniūnijos" />
              {this.state.elderates.map((option) =>
                option !== "Visos seniūnijos" ? (<option value={option} label={option} key={option} />) : null
              )}
            </select>
          </div>
          <div >
            <MapSearchContainer cleanSearch={this.cleanSearch} sendKindergartenFromSearch={this.getKindergartenFromSearch} />
          </div>

        </div>

        <div className="col-12 col-sm-12 col-md-12 col-lg-8">
        <div style={{ position: 'relative', height: '65vh', width: '100%' }}>
          <Map google={this.props.google}
            initialCenter={{
              lat: 54.69597,
              lng: 25.29283
            }}
            zoom={11}
            onClick={this.onMapClicked}>

            {this.state.filteredKindergartenList.map(kindergarten => (
              <Marker
                icon={{
                  url: "http://maps.google.com/mapfiles/ms/icons/green-dot.png"
                }}
                key={kindergarten.id}
                name={kindergarten}
                position={{ lat: kindergarten.latState, lng: kindergarten.lngState }}
                onClick={this.onMarkerClick}
              >
              </Marker>
            ))}

            {this.state.filteredEmptyKindergartenList.map(kindergarten => (
              <Marker
                icon={{
                  url: "http://maps.google.com/mapfiles/ms/icons/red-dot.png"
                }}
                key={kindergarten.id}
                name={kindergarten}
                position={{ lat: kindergarten.latState, lng: kindergarten.lngState }}
                onClick={this.onMarkerClick}
              >
              </Marker>
            ))}

            <InfoWindow
              marker={this.state.activeMarker}
              visible={this.state.showInfoWindow}
            >
              <div>
                <h4>Darželis "{this.state.activeKindergarten.name}"</h4>
                <br></br>
                <p>Adresas: {this.state.activeKindergarten.address}</p>
                <p>Seniūnija: {this.state.activeKindergarten.elderate}</p>
                <p>Laisvų vietų 2-3 metų grupėje: {this.state.activeKindergarten.capacityAgeGroup2to3 - this.state.activeKindergarten.placesTakenAgeGroup2to3}</p>
                <p>Laisvų vietų 3-6 metų grupėje: {this.state.activeKindergarten.capacityAgeGroup3to6 - this.state.activeKindergarten.placesTakenAgeGroup3to6}</p>
              </div>
            </InfoWindow>
          </Map>
        </div>
        </div>
      </div>
    </div>
    )
  }
}

const LoadingContainer = (props) => (
  <div>Žemėlapis kraunasi</div>
)

export default GoogleApiWrapper({
  apiKey: ('AIzaSyC1DUUuEflhsnMlXxMXE786hc9IdSGGw5w'),
  LoadingContainer: LoadingContainer
})(MapContainer)