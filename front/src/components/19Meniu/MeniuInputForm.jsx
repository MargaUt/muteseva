import React, { useState} from "react";
import { useHistory, useParams } from 'react-router-dom';
import "../../App.css";
import http from "../10Services/httpService";
import apiEndpoint from "../10Services/endpoint";
import swal from "sweetalert";

function MeniuInputForm() {
  const params = useParams(); 

  const initMeniuData = {
    id: "",
    meniuName: "",
    istaigosid: params.id
  };

  var savingStatus = false;

  const [data, setData] = useState(initMeniuData);
  const history = useHistory();
 


  const handleSubmit = (event) => {
    event.preventDefault();
    savingStatus = true;
    http
      .post(`${apiEndpoint}/api/meniu`, data)
      .then((response) => {
        swal({
          text: "Naujas meniu „" + data.meniuName + "“ pridėtas sėkmingai!",
          button: "Gerai",
        });
        resetForm(event);
        history.push("/meniu/" + data.istaigosid);
        window.location.reload(); 
      })
      .catch((error) => {
        if (error.response.status === 409) {
          swal({
            text:
              "Įvyko klaida įrašant naują meniu. " +
              error.response.data +
              "\n\nPatikrinkite duomenis ir bandykite dar kartą",
            button: "Gerai",
          });
        }
        savingStatus = false;
      });
  };

  const handleChange = (event) => {
   
    setData({
      ...data,
      [event.target.name]: event.target.value,
    });
    
  };

  const resetForm = (event) => {
    event.preventDefault();
    setData(initMeniuData);
  };

  return (
    <div>
      <form onSubmit={handleSubmit} onReset={resetForm}>
        <h6 className="py-3">
          <b>Pridėti naują maitinimo įstaigą </b>
        </h6>
        <div className="mb-3">
          <label htmlFor="name" className="form-label">
          Meniu pavadinimas <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control "
            name="meniuName"
            id="meniuName"
            value={data.meniuName}
            onChange={handleChange}
            required
            minLength="3"
            placeholder="3-50 simbolių"
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite maitinimo įstaigos pavadinimą"
          />
        </div>

        <button
          type="reset"
          className="btn btn-outline-danger ms-2 mb-3"
          id="btnClearmeniu"
        >
          Išvalyti
        </button>
        <button
          type="submit"
          className="btn btn-primary mb-3"
          id="btnSaveMeniu"
          disabled={savingStatus}
        >
          {savingStatus ? "Pridedama..." : "Pridėti"}
        </button>
      </form>
    </div>
  );
}

export default MeniuInputForm;
