import React, { useState} from "react";
import { useHistory, useParams } from 'react-router-dom';
import "../../App.css";
import http from "../10Services/httpService";
import apiEndpoint from "../10Services/endpoint";
import swal from "sweetalert";

function MeniuInputForm() {
  const params = useParams(); 

  const initMeniuData = {
    id: params.id,
    bookName: "",
    booksPages: 0,
    isbn: 0,
    picture: "",
    santrauka: "",
    categoryid: params.id
  };

  var savingStatus = false;

  const [data, setData] = useState(initMeniuData);
  const history = useHistory();
 


  const handleSubmit = (event) => {
    event.preventDefault();
    savingStatus = true;
    http
      .post(`${apiEndpoint}/api/book`, data)
      .then((response) => {
        swal({
          text: "Naujas meniu „" + data.bookName + "“ pridėtas sėkmingai!",
          button: "Gerai",
        });
        resetForm(event);
        history.push("/knyga/" + data.id);
        window.location.reload(); 
      })
      .catch((error) => {
        if (error.response.status === 409) {
          swal({
            text:
              "Įvyko klaida įrašant naują knygą. " +
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
          <b>Pridėti naują knygą </b>
        </h6>
        <div className="mb-3">
          <label htmlFor="name" className="form-label">
          Knygos pavadinimas <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control "
            name="bookName"
            id="bookName"
            value={data.bookName}
            onChange={handleChange}
            required
            minLength="3"
            placeholder="3-50 simbolių"
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite knygos pavadinimą"
          />
        </div>

        <div className="mb-3">
          <label htmlFor="name" className="form-label">
          Knygos santrauka <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control "
            name="santrauka"
            id="santrauka"
            value={data.santrauka}
            onChange={handleChange}
            required
            minLength="3"
            placeholder="3-50 simbolių"
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite knygos santrauką"
          />
        </div>

        <div className="mb-3">
          <label htmlFor="name" className="form-label">
          Knygos puslapių skaičius <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control "
            name="booksPages"
            id="booksPages"
            value={data.booksPages}
            onChange={handleChange}
            required
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite puslapių skaičius "
          />
        </div>

        <div className="mb-3">
          <label htmlFor="name" className="form-label">
          Knygos ISBN kodas <span className="fieldRequired">*</span>
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
            placeholder="13 simbolių"
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite knygos ISBN kodas"
          />
        </div>

        {/* <div className="mb-3">
          <label htmlFor="name" className="form-label">
          Knygos paveiksliukas<span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control "
            name="picture"
            id="picture"
            value={data.picture}
            onChange={handleChange}
            required
            minLength="3"
            placeholder="13 simbolių"
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite mkny"
          />
        </div> */}

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
