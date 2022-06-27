import React, { Component } from 'react';
import '../../App.css';
import http from '../10Services/httpService';
import apiEndpoint from '../10Services/endpoint';
import SearchBox from '../08CommonComponents/SeachBox';
import Pagination from './../08CommonComponents/Pagination';
import MapSearchDropdown from './MapSearchDropdown';

class MapSearchContainer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            kindergartens: [],
            pageSize: 10,
            currentPage: 1,
            totalPages: 0,
            totalElements: 0,
            numberOfElements: 0,
            searchQuery: "",
            errorMessages: {},
            placeholder: "Ieškoti pagal darželio pavadinimą arba adresą..."
        }
    }

    componentDidMount() {
        this.cleanSearch = this.cleanSearch.bind(this);
        this.getKindergartenInfo(this.state.currentPage, "");
        document.addEventListener("keydown", this.handleEscape, false);
    }

    componentWillUnmount() {
        document.removeEventListener("keydown", this.handleEscape, false);
    }

    handleSearch = (e) => {
        const search = e.currentTarget.value;
        this.setState({
            searchQuery: search,
            placeholder: "Ieškoti pagal darželio pavadinimą arba adresą..."
        });
        this.getKindergartenInfo(1, search);
    }

    handlePageChange = (page) => {
        this.setState({ currentPage: page });
        this.getKindergartenInfo(page, this.state.searchQuery);
    };

    getKindergartenInfo(currentPage, search) {

        const { pageSize } = this.state;

        let page = currentPage - 1;

        if (page < 0) page = 0;

        if (search !== "") {
            let encodedsearch = encodeURIComponent(search);
            var uri = `${apiEndpoint}/api/darzeliai/manager/search?page=${page}&size=${pageSize}&search=${encodedsearch}`;
            http
                .get(uri)
                .then((response) => {

                    this.setState({
                        kindergartens: response.data.content,
                        totalPages: response.data.totalPages,
                        totalElements: response.data.totalElements,
                        numberOfElements: response.data.numberOfElements,
                        currentPage: response.data.number + 1
                    });
                }).catch(() => {});

        } else {
            this.setState({
                kindergartens: [],
                totalPages: 0,
                totalElements: 0,
                numberOfElements: 0,
                currentPage: 1,
            });
        }
    }

    chosenKindergarten = (kindergarten) => {
        this.props.sendKindergartenFromSearch(kindergarten)
        this.setState({
            kindergartens: [],
            pageSize: 10,
            currentPage: 1,
            totalPages: 0,
            totalElements: 0,
            numberOfElements: 0,
            searchQuery: "",
            errorMessages: {},
            placeholder: `${kindergarten.name}, ${kindergarten.address}`
        });
    }

    cleanSearch() {
        this.props.cleanSearch()
        this.setState({
            placeholder: "Ieškoti pagal darželio pavadinimą arba adresą..."
        })
    }

    render() {
        const { kindergartens, totalElements, pageSize, searchQuery } = this.state;

        return ( <
            div className = "container-flexible pt-2" >
            <
            React.Fragment >

            <
            SearchBox value = { searchQuery }
            onSearch = { this.handleSearch }
            placeholder = { this.state.placeholder }
            /> <
            div className = "d-grid pt-2" >
            <
            button className = "btn btn-outline-secondary"
            type = "button"
            onClick = { this.cleanSearch } > Išvalyti paiešką < /button> <
            /div>

            <
            MapSearchDropdown kindergartens = { kindergartens }
            chosenKindergarten = { this.chosenKindergarten }
            search = { searchQuery }
            /> <
            div className = ' mt-3' >
            <
            Pagination itemsCount = { totalElements }
            pageSize = { pageSize }
            onPageChange = { this.handlePageChange }
            currentPage = { this.state.currentPage }
            /> <
            /div> <
            /React.Fragment> <
            /div>
        )
    }
}
export default MapSearchContainer;