import React, { Component } from 'react';
import '../../App.css';

class NotFoundMessage extends Component {

    constructor(props) {
        super(props);
        this.enableMessage = this.enableMessage.bind(this);
        this.state = {
            displayMessage: false,
        };
        this.timer = null;
    }

    componentDidMount() {
        this.timer = setTimeout(this.enableMessage, 250);
    }

    componentWillUnmount() {
        clearTimeout(this.timer);
    }
    enableMessage() {
        this.setState({ displayMessage: true });
    }

    render() {
        const {message} = this.props;
        const { displayMessage } = this.state;
        if (displayMessage) {
            return (
                <div className="searchNotFound">
                    {message}
                </div>
            )

        } else {
            return (
                <div></div>
            )
        }
    }
}

export default NotFoundMessage;