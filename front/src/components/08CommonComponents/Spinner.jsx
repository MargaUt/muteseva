import React from 'react'

function Spinner() {
    return (
        <div className="d-flex justify-content-center my-5">
        <div className="spinner-border" role="status">
          <span className="visually-hidden">Palaukite...</span>
        </div>
      </div>
    )
}

export default Spinner
