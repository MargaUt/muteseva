import React, { useState} from "react";
import { useHistory, useParams } from 'react-router-dom';
import "../../App.css";
import http from "../10Services/httpService";
import apiEndpoint from "../10Services/endpoint";
import swal from "sweetalert";

function MealsInputForm() {
  const params = useParams(); 

  const initMealData = {
    id: "",
    description: "",
    name: "",
    meniuId: params.id
  };

  var savingStatus = false;

  const [data, setData] = useState(initMealData);
  const history = useHistory();
 


  const handleSubmit = (event) => {
    event.preventDefault();
    savingStatus = true;
    http
      .post(`${apiEndpoint}/api/meals/admin/createMeal`, data)
      .then((response) => {
        swal({
          text: "Naujas patiekalas „" + data.name + "“ pridėtas sėkmingai!",
          button: "Gerai",
        });
        resetForm(event);
        history.push("/meals/" + data.meniuId);
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
    setData(initMealData);
  };

  return (
    <div>
      <form onSubmit={handleSubmit} onReset={resetForm}>
        <h6 className="py-3">
          <b>Pridėti naują patiekalą</b>
        </h6>

        <div className="mb-3">
          <label htmlFor="name" className="form-label">
          Patiekalo pavadinimas <span className="fieldRequired">*</span>
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
            placeholder="3-50 simbolių"
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite patiekalo pavadinimą"
          />
        </div>

        <div className="mb-3">
          <label htmlFor="name" className="form-label">
          Patiekalo aprašymas <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control "
            name="description"
            id="description"
            value={data.description}
            onChange={handleChange}
            required
            minLength="3"
            placeholder="3-50 simbolių"
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite patiekalo aprašymą"
          />
        </div>

        <button
          type="reset"
          className="btn btn-outline-danger ms-2 mb-3"
          id="btnClearMeal"
        >
          Išvalyti
        </button>
        <button
          type="submit"
          className="btn btn-primary mb-3"
          id="btnSaveMeal"
          disabled={savingStatus}
        >
          {savingStatus ? "Pridedama..." : "Pridėti"}
        </button>
      </form>
    </div>
  );
}

export default MealsInputForm;
