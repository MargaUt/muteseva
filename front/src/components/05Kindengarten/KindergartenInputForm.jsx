import React, { useState, useEffect } from "react";
import { useHistory } from 'react-router-dom';
import "../../App.css";
import http from "../10Services/httpService";
import apiEndpoint from "../10Services/endpoint";
import swal from "sweetalert";

function KindergartenInputForm() {
  const initKindergartenData = {
    address: "",
    capacityAgeGroup2to3: 0,
    capacityAgeGroup3to6: 0,
    elderate: "",
    id: "",
    name: "",
    director: "",
  };

  var savingStatus = false;

  const [data, setData] = useState(initKindergartenData);
  const [elderates, setElderate] = useState([]);
  const history = useHistory();

  useEffect(() => {
    http
      .get(`${apiEndpoint}/api/darzeliai/elderates`)
      .then((response) => {
        setElderate(response.data);
      })
      .catch((error) => {
        swal({
          text: "Įvyko klaida nuskaitant seniūnijas. " + error.response.data,
          button: "Gerai",
        });
      });
  }, [setElderate]);

  const handleSubmit = (event) => {
    event.preventDefault();
    //console.log("saugoma į serverį");
    //console.log(data);
    savingStatus = true;
    http
      .post(`${apiEndpoint}/api/darzeliai/manager/createKindergarten`, data)
      .then((response) => {
        //console.log("įrašyta: " + response.data);
        swal({
          text: "Naujas darželis „" + data.name + "“ pridėtas sėkmingai!",
          button: "Gerai",
        });
        savingStatus = false;
        resetForm(event);
        history.push("/new");
        history.replace("/darzeliai")
      })
      .catch((error) => {
        if (error.response.status === 409) {
          swal({
            text:
              "Įvyko klaida įrašant naują darželį. " +
              error.response.data +
              "\n\nPatikrinkite duomenis ir bandykite dar kartą",
            button: "Gerai",
          });
        }
        savingStatus = false;
      });
  };

  const validateField = (event) => {
    const target = event.target;

    if (target.validity.valueMissing) {
      if (target.id === "elderate") {
        target.setCustomValidity("Reikia pasirinkti seniūniją");
      } else target.setCustomValidity("Būtina užpildyti šį laukelį");
    } else if (target.validity.patternMismatch) {
      if (target.id === "id")
        target.setCustomValidity("Įstaigos kodą turi sudaryti 9 skaitmenys");
      if (target.id === "name")
        target.setCustomValidity("Pavadinimas turi būti 3-50 simbolių ir negali prasidėti tarpu");
      if (target.id === "address")
        target.setCustomValidity("Adresas turi būti 3-50 simbolių");
      if (target.id === "director")
        target.setCustomValidity("Netinkamo formato vardas ir pavardė");
    } else if (target.validity.rangeUnderflow || target.validity.rangeOverflow) {
      target.setCustomValidity("Negali būti mažiau nei 0 ir daugiau nei 999");

    } else {
      target.setCustomValidity("");
    }
  };

  const handleChange = (event) => {
    validateField(event);
   
    setData({
      ...data,
      [event.target.name]: event.target.value,
    });
    
  };

  const resetForm = (event) => {
    event.preventDefault();
    setData(initKindergartenData);
  };

  return (
    <div>
      <form onSubmit={handleSubmit} onReset={resetForm}>
        <h6 className="py-3">
          <b>Pridėti naują darželį </b>
        </h6>
        <div className="mb-3">
          <label htmlFor="id" className="form-label">
            Įstaigos kodas <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control"
            name="id"
            id="id"
            value={data.id}
            onChange={handleChange}
            onInvalid={validateField}
            required
            pattern="\d{9}"
            placeholder="123456789"
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite įstaigos (darželio) kodą (9 skaitmenys)"
          />
        </div>

        <div className="mb-3">
          <label htmlFor="name" className="form-label">
            Pavadinimas <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control "
            name="name"
            id="name"
            value={data.name}
            onChange={handleChange}
            onInvalid={validateField}
            required
            minLength="3"
            maxLength="50"
            pattern="[^ ][A-zÀ-ž0-9\x22 \-'.,]*"
            placeholder="3-50 simbolių"
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite darželio pavadinimą (nuo 3 iki 50 simbolių)"
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
            onInvalid={validateField}
            required
            placeholder="Adresas"
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite darželio adresą"
            pattern="[^\n]{3,50}"
          />
        </div>

        <div className="mb-3">
          <label htmlFor="director" className="form-label">
            Darželio direktoriaus vardas ir pavardė <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            className="form-control"
            name="director"
            id="director"
            value={data.director}
            onChange={handleChange}
            onInvalid={validateField}
            required
            placeholder="Vardas Pavardė"
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite darželio direktoriaus vardą ir pavardę"
            pattern="^(?:\p{L}{2,70})+((-)*( )*(?:\p{L}+))*$"
          />
        </div>

        <div className="mb-3">
          <label htmlFor="elderate" className="form-label">
            Seniūnija <span className="fieldRequired">*</span>
          </label>
          <select
            type="text"
            className="form-select"
            name="elderate"
            id="elderate"
            value={data.elderate}
            onChange={handleChange}
            onInvalid={validateField}
            required
            placeholder="Seniūnija"
            data-toggle="tooltip"
            data-placement="top"
            title="Pasirinkite seniūniją, kuriai priskiriamas darželis"
          >
            <option value="" disabled hidden label="Pasirinkite" />
            {elderates.map((option) => (
              <option value={option} label={option} key={option} />
            ))}
          </select>
        </div>
        <h6 className="py-3">
          <b>Laisvų vietų skaičius </b>
          <span className="fieldRequired">*</span>
        </h6>
        <div className="mb-3">
          <label htmlFor="capacityAgeGroup2to3">2-3 metų grupėse</label>
          <input
            type="number"
            min="0"
            max="999"
            className="form-control"
            name="capacityAgeGroup2to3"
            id="capacityAgeGroup2to3"
            value={data.capacityAgeGroup2to3}
            onChange={handleChange}
            onInvalid={validateField}
            required
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite 2-3 metų amžiaus grupėse esančių vietų skaičių"
          />
        </div>

        <div className="mb-3">
          <label htmlFor="capacityAgeGroup3to6">3-6 metų grupėse</label>
          <input
            type="number"
            min="0"
            max="999"
            className="form-control"
            name="capacityAgeGroup3to6"
            id="capacityAgeGroup3to6"
            value={data.capacityAgeGroup3to6}
            onChange={handleChange}
            onInvalid={validateField}
            required
            data-toggle="tooltip"
            data-placement="top"
            title="Įveskite 2-3 metų amžiaus grupėse esančių vietų skaičių"
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
    </div>
  );
}

export default KindergartenInputForm;
