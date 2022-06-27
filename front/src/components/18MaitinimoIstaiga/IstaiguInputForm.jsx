import React, { useState} from "react";
import { useHistory } from 'react-router-dom';
import "../../App.css";
import http from "../10Services/httpService";
import apiEndpoint from "../10Services/endpoint";
import swal from "sweetalert";
import AuthContext from "../11Context/AuthContext";

function IstaiguInputForm() {
  const initIstaiguData = {
    id: "",
    kodas: "",
    address: "",
    pavadinimas: ""
  };

  const currentUser = React.useContext(AuthContext);

  var savingStatus = false;

  const [data, setData] = useState(initIstaiguData);
  const history = useHistory();

  const handleSubmit = (event) => {
    event.preventDefault();
    //console.log("saugoma į serverį");
    //console.log(data);
    savingStatus = true;
    http
      .post(`${apiEndpoint}/api/istaigos/admin/createIstaiga`, data)
      .then((response) => {
        //console.log("įrašyta: " + response.data);
        swal({
          text: "Nauja maitinimo įstaiga „" + data.pavadinimas + "“ pridėta sėkmingai!",
          button: "Gerai",
        });
        resetForm(event);
        history.push("/istaigos");
        window.location.reload(); 
      })
      .catch((error) => {
        if (error.response.status === 409) {
          swal({
            text:
              "Įvyko klaida įrašant naują maitinimo įstaigą. " +
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
    setData(initIstaiguData);
  };

  return (
    
    <div>
            {currentUser.state.role === "ADMIN" ?
                                  (  
      <form onSubmit={handleSubmit} onReset={resetForm}>
        <h6 className="py-3">
          <b>Pridėti naują maitinimo įstaigą </b>
        </h6>
      

        <div className="mb-3">
          <label htmlFor="name" className="form-label">
            Maitinimo įstaigos kodas <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control "
            name="kodas"
            id="kodas"
            value={data.kodas}
            onChange={handleChange}
            required
            minLength="3"
            maxLength="50"
            pattern="[^ ][A-zÀ-ž0-9\x22 \-'.,]*"
            placeholder="3-50 simbolių"
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite maitinimo įstaigos kodą"
          />
        </div>

        <div className="mb-3">
          <label htmlFor="address" className="form-label">
            Adresas <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control"
            name="address"
            id="address"
            value={data.address}
            onChange={handleChange}
            required
            placeholder="Adresas"
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite maitinimo įstaigos adresą"
            pattern="[^\n]{3,50}"
          />
        </div>

        <div className="mb-3">
          <label htmlFor="director" className="form-label">
            Pavadinimas <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control"
            name="pavadinimas"
            id="pavainimas"
            value={data.pavadinimas}
            onChange={handleChange}
            required
            placeholder="Pavadinimas"
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite maitinimo įstaogos pavadinimą"
            pattern="[^ ][A-zÀ-ž0-9\x22 \-'.,]*"
          />
        </div>
        <button
          type="reset"
          className="btn btn-outline-danger ms-2 mb-3"
          id="btnClearKindergartenForm"
        >
          Išvalyti
        </button>
        <button
          type="submit"
          className="btn btn-primary mb-3"
          id="btnSaveKindergarten"
          disabled={savingStatus}
        >
          {savingStatus ? "Pridedama..." : "Pridėti"}
        </button>
      </form>
               ) : (         
                <div className="col-12 col-sm-12 col-md-12 col-lg-9 pt-1"> </div>
       )
           }
    </div>
  );
}

export default IstaiguInputForm;
