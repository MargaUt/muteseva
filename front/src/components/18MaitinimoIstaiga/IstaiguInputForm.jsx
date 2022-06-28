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
    name: ""
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
      .post(`${apiEndpoint}/api/category/createCategory`, data)
      .then((response) => {
        //console.log("įrašyta: " + response.data);
        swal({
          text: "Nauja kategorija„" + data.name + "“ pridėta sėkmingai!",
          button: "Gerai",
        });
        resetForm(event);
        history.push("/kategorijos");
        window.location.reload(); 
      })
      .catch((error) => {
        if (error.response.status === 409) {
          swal({
            text:
              "Įvyko klaida įrašant naują kategoriją. " +
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
          <b>Pridėti naują kategoriją </b>
        </h6>
      

        <div className="mb-3">
          <label htmlFor="name" className="form-label">
            Kategorijos pavadinimas<span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control "
            name="name"
            id="name"
            value={data.name}
            onChange={handleChange}
            required
            minLength="3"
            maxLength="50"
            placeholder="3-50 simbolių"
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite kategorijos pavadinimą"
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
