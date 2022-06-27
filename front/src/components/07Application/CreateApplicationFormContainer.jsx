import React, { Component } from "react";
import Select from "react-select";
import { withRouter } from "react-router-dom";

import DatePicker from "react-datepicker";
import { registerLocale } from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import lt from "date-fns/locale/lt";

import http from "../10Services/httpService";
import apiEndpoint from "../10Services/endpoint";
import swal from "sweetalert";

import applicationInputValidator from "../08CommonComponents/InputValidator";

import "../../App.css";
import "../08CommonComponents/datePickerStyle.css";
import { subYears } from "date-fns";

import { parseISO } from "date-fns/esm";


registerLocale("lt", lt);

class CreateApplicationFormContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      mainGuardian: {
        name: "",
        surname: "",
        personalCode: "",
        phone: "",
        email: "",
        address: "",
        username: "",
      },
      additionalGuardian: {
        name: "",
        surname: "",
        personalCode: "",
        phone: "",
        email: "",
        address: "",
      },
      birthdate: "",
      childName: "",
      childPersonalCode: "",
      childSurname: "",
      kindergartenChoises: {
        kindergartenId1: "",
        kindergartenId2: "",
        kindergartenId3: "",
        kindergartenId4: "",
        kindergartenId5: "",
      },
      priorities: {
        childIsAdopted: false,
        familyHasThreeOrMoreChildrenInSchools: false,
        guardianDisability: false,
        guardianInSchool: false,
        livesInVilnius: false,
        parentLivedInVilniusMoreThan2Years: false,
      },
      kindergartenList: [],
      additionalGuardianInput: false,
      registrationDisabled: false,
      childButtonText: "Surasti vaiką",
      childCodeDisabled: false,
    };
    this.mainGuardianOnChange = this.mainGuardianOnChange.bind(this);
    this.additionalGuardianOnChange = this.additionalGuardianOnChange.bind(
      this
    );
    this.childOnChange = this.childOnChange.bind(this);
    this.checkboxOnChange = this.checkboxOnChange.bind(this);
    this.submitHandle = this.submitHandle.bind(this);

  }

  componentDidMount() {
    /** Get registation status */
    http.get(`${apiEndpoint}/api/status`)
      .then((response) => {
        //console.log(response.data.registrationActive);
        this.setState({ registrationDisabled: !response.data.registrationActive })
      })

    /** get logged in user data */
    http
      .get(`${apiEndpoint}/api/users/user`)
      .then((response) => {
        this.setState({
          mainGuardian: {
            ...this.state.mainGuardian,
            name: response.data.name,
            surname: response.data.surname,
            personalCode: response.data.personalCode,
            phone: response.data.phone,
            email: response.data.username,
            address: response.data.address,
            username: response.data.username,
            role: response.data.role,
          },
        });

        /** get kindergarten list */
        var kindergartenList = [];
        http.get(`${apiEndpoint}/api/darzeliai`).then((response) => {
          kindergartenList = response.data.map((k) => ({
            value: k.id,
            label: k.name + " (" + k.address + ")",
            disabled: "no",
          }));
          this.setState({
            kindergartenList,
          });
        });
      })
      .catch((error) => {
        swal({
          text: "Įvyko klaida perduodant duomenis iš serverio.",
          button: "Gerai",
        });
      });
  }

  /** FORMOS */
  /** Atstovu formos */
  userForm(mainGuardian) {
    if (mainGuardian) {
      return (
        <div className="form">
          <h6 className="formHeader">Atstovas 1</h6>
          <div className="mb-3">
            <label htmlFor="txtName"
              className="form-label">
              Vardas <span className="fieldRequired">*</span>
            </label>
            <input
              type="text"
              title=""
              id="txtMainName"
              name="name"
              placeholder="Vardas"
              className="form-control"
              value={this.state.mainGuardian.name}
              onChange={this.mainGuardianOnChange}
              onInvalid={(e) => applicationInputValidator(e)}
              disabled={this.state.registrationDisabled}
              required
              minlenght="2"
              maxlenght="70"
              pattern="^\p{L}+(( )+(?:\p{L}+))*$"
            />
          </div>
          <div className="mb-3">
            <label htmlFor="txtSurname"
              className="form-label">
              Pavardė <span className="fieldRequired">*</span>
            </label>
            <input
              type="text"
              title=""
              id="txtMainSurname"
              name="surname"
              placeholder="Pavardė"
              className="form-control"
              value={this.state.mainGuardian.surname}
              onChange={this.mainGuardianOnChange}
              onInvalid={(e) => applicationInputValidator(e)}
              disabled={this.state.registrationDisabled}
              required
              minlenght="2"
              maxlenght="70"
              pattern="^\p{L}+((-)+(?:\p{L}+))*$"
            />
          </div>
          <div className="mb-3">
            <label htmlFor="txtPersonalCode"
              className="form-label">
              Asmens kodas <span className="fieldRequired">*</span>
            </label>
            <input
              type="text"
              title=""
              id="txtMainPersonalCode"
              name="personalCode"
              placeholder="Asmens kodas"
              className="form-control"
              value={this.state.mainGuardian.personalCode}
              onChange={this.mainGuardianOnChange}
              onInvalid={(e) => applicationInputValidator(e)}
              disabled={this.state.registrationDisabled}
              required
              pattern="[0-9]{11}"
            />
          </div>
          <div className="mb-3">
            <label htmlFor="txtTelNo" className="form-label">
              Telefonas <span className="fieldRequired">*</span>
            </label>
            <div className="input-group">
              <input
                type="tel"
                title=""
                id="txtMainPhone"
                name="phone"
                placeholder="+37012345678"
                className="form-control"
                value={this.state.mainGuardian.phone}
                onChange={this.mainGuardianOnChange}
                onInvalid={(e) => applicationInputValidator(e)}
                disabled={this.state.registrationDisabled}
                required
                pattern="[+]{1}[0-9]{5,19}"
              ></input>
            </div>
          </div>
          <div className="mb-3">
            <label htmlFor="txtEmail" className="form-label">
              El. paštas <span className="fieldRequired">*</span>
            </label>
            <input
              type="text"
              title=""
              id="txtMainEmail"
              name="email"
              placeholder="El. paštas"
              className="form-control"
              value={this.state.mainGuardian.email}
              onChange={this.mainGuardianOnChange}
              onInvalid={(e) => applicationInputValidator(e)}
              disabled={this.state.registrationDisabled}
              required
              pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}"
            />
          </div>
          <div className="mb-3">
            <label htmlFor="txtAddress" className="form-label">
              Adresas <span className="fieldRequired">*</span>
            </label>
            <input
              type="text"
              title=""
              className="form-control"
              id="txtMainAddress"
              name="address"
              placeholder="Adresas"
              value={this.state.mainGuardian.address}
              onChange={this.mainGuardianOnChange}
              onInvalid={(e) => applicationInputValidator(e)}
              disabled={this.state.registrationDisabled}
              required
            />
          </div>
        </div>
      );
    } else {
      return (
        <div className="form">
          <div className="row pt-1">
            <div className="col-7">
              <h6 className="formHeader">Atstovas 2</h6>
            </div>
            <div className="col-5">
              <button
                type="button"
                id="btnEnableAdditionalGuardian"
                className="btn btn-primary btn-sm w-100 float-end my-1"
                style={{ padding: "4px" }}
                onClick={(e) => {
                  this.setState({
                    ...this.state,
                    additionalGuardianInput: !this.state
                      .additionalGuardianInput,
                  });
                }}
                disabled={this.state.registrationDisabled}
              >
                {!this.state.additionalGuardianInput ? "Pridėti" : "Pašalinti"}
              </button>
            </div>
          </div>
          <div className="mb-3">
            <label htmlFor="txtName" className="form-label">
              Vardas <span className="fieldRequired">*</span>
            </label>
            <input
              type="text"
              title=""
              id="txtAdditionalName"
              name="name"
              placeholder="Vardas"
              className="form-control"
              value={this.state.additionalGuardian.name}
              onChange={this.additionalGuardianOnChange}
              onInvalid={(e) => applicationInputValidator(e)}
              disabled={!this.state.additionalGuardianInput || this.state.registrationDisabled}
              minlenght="2"
              maxlenght="70"
              pattern="^\p{L}+(( )+(?:\p{L}+))*$"
              required
            />
          </div>
          <div className="mb-3">
            <label htmlFor="txtSurname" className="form-label">
              Pavardė <span className="fieldRequired">*</span>
            </label>
            <input
              type="text"
              title=""
              id="txtAdditionalSurname"
              name="surname"
              placeholder="Pavardė"
              className="form-control"
              value={this.state.additionalGuardian.surname}
              onChange={this.additionalGuardianOnChange}
              onInvalid={(e) => applicationInputValidator(e)}
              disabled={!this.state.additionalGuardianInput || this.state.registrationDisabled}
              minlenght="2"
              maxlenght="70"
              pattern="^\p{L}+((-)+(?:\p{L}+))*$"
              required
            />
          </div>
          <div className="mb-3">
            <label htmlFor="txtPersonalCode" className="form-label">
              Asmens kodas <span className="fieldRequired">*</span>
            </label>
            <input
              type="text"
              title=""
              id="txtAdditionalPersonalCode"
              name="personalCode"
              placeholder="Asmens kodas"
              className="form-control"
              value={this.state.additionalGuardian.personalCode}
              onChange={this.additionalGuardianOnChange}
              onInvalid={(e) => applicationInputValidator(e)}
              disabled={!this.state.additionalGuardianInput || this.state.registrationDisabled}
              pattern="[0-9]{11}"
              required
            />
          </div>
          <div className="mb-3">
            <label htmlFor="txtTelNo" className="form-label">
              Telefonas <span className="fieldRequired">*</span>
            </label>
            <div className="input-group">
              <input
                type="tel"
                title=""
                id="txtAdditionalPhone"
                name="phone"
                placeholder="+37012345678"
                className="form-control"
                value={this.state.additionalGuardian.phone}
                onChange={this.additionalGuardianOnChange}
                onInvalid={(e) => applicationInputValidator(e)}
                disabled={!this.state.additionalGuardianInput || this.state.registrationDisabled}
                pattern="[+]{1}[0-9]{5,19}"
                required
              />
            </div>
          </div>
          <div className="mb-3">
            <label htmlFor="txtEmail" className="form-label">
              El. paštas <span className="fieldRequired">*</span>
            </label>
            <input
              type="text"
              title=""
              id="txtAdditionalEmail"
              name="email"
              placeholder="El. paštas"
              className="form-control"
              value={this.state.additionalGuardian.email}
              onChange={this.additionalGuardianOnChange}
              onInvalid={(e) => applicationInputValidator(e)}
              disabled={!this.state.additionalGuardianInput || this.state.registrationDisabled}
              pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}"
              required
            />
          </div>
          <div className="mb-3">
            <label htmlFor="txtAddress" className="form-label">
              Adresas <span className="fieldRequired">*</span>
            </label>
            <input
              type="text"
              title=""
              className="form-control"
              id="txtAdditionalAddress"
              name="address"
              placeholder="Adresas"
              value={this.state.additionalGuardian.address}
              onChange={this.additionalGuardianOnChange}
              onInvalid={(e) => applicationInputValidator(e)}
              disabled={!this.state.additionalGuardianInput || this.state.registrationDisabled}
              required
            />
          </div>
        </div>
      );
    }
  }

  /** Vaiko forma */
  childForm() {
    return (
      <div className="form">
          <h6 className="formHeader">Vaiko duomenys</h6>
        <div className="mb-3">
          <label htmlFor="txtName" className="form-label">
            Vaiko vardas <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            title=""
            id="txtChildName"
            name="childName"
            placeholder="Vaiko vardas"
            className="form-control"
            value={this.state.childName}
            onChange={this.childOnChange}
            onInvalid={(e) => applicationInputValidator(e)}
            disabled={true}
            required
            minlenght="2"
            maxlenght="70"
            pattern="^\p{L}+(( )+(?:\p{L}+))*$"
          />
        </div>
        <div className="mb-3">
          <label htmlFor="txtSurname" className="form-label">
            Vaiko pavardė <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            title=""
            id="txtChildSurname"
            name="childSurname"
            placeholder="Vaiko pavardė"
            className="form-control"
            value={this.state.childSurname}
            onChange={this.childOnChange}
            onInvalid={(e) => applicationInputValidator(e)}
            disabled={true}
            required
            minlenght="2"
            maxlenght="70"
            pattern="^\p{L}+((-)+(?:\p{L}+))*$"
          />
        </div>
          <div className="row mb-3">
            <div className="col-lg-8">
          <label htmlFor="txtPersonalCode" className="form-label">
            Asmens kodas <span className="fieldRequired">*</span>
          </label>
          <input
            type="text"
            title=""
            id="txtChildPersonalCode"
            name="childPersonalCode"
            placeholder="Asmens kodas"
            className="form-control"
            value={this.state.childPersonalCode}
            onChange={this.childOnChange}
            onInvalid={(e) => applicationInputValidator(e)}
            disabled={this.state.registrationDisabled || this.state.childCodeDisabled}
            required
            pattern="[0-9]{11}"
          />
          </div>
          <div className="col">
          <button type="button" 
            disabled={this.state.registrationDisabled}
            className="btn btn-primary child--button" 
            onChange={this.childOnChange}
          
            onClick={() => this.getChildInfoFromHisPersonalCode(this.state.childPersonalCode)}>
              {this.state.childButtonText}</button>
            </div>
            </div>     
        {/** Gimimo data */}
        <div className="mb-3">
          <label htmlFor="txtBirthdate" className="form-label">
            Gimimo data <span className="fieldRequired">*</span>
          </label>
          <DatePicker
            className="form-control"
            locale="lt"
            id="childBirthday"
            dateFormat="yyyy/MM/dd"
            placeholderText="YYYY-MM-DD"
            selected={this.state.birthdate}
            onChange={(e) => {
              this.setState({ birthdate: e });
            }}
            minDate={subYears(new Date(), 6)}
            maxDate={subYears(new Date(), 1)}
            // disabled={this.state.registrationDisabled}
            disabled={true}
          />
        </div>
      </div>
    );
  }

  /** Checkbox forma prioritetams */
  checkboxPriorityForm() {
    return (
      <div className="form">
        <h6 className="formHeader">
          Vaiko priėmimo tvarkos prioritetai
        </h6>
        <p>Pažymėkite tinkamus prioritetus</p>

            <div className="form-check">
              <input
                type="checkbox"
                className="form-check-input"
                name="livesInVilnius"
                id="chkLivesInVilnius"
                checked={this.state.priorities.livesInVilnius}
                onChange={this.checkboxOnChange}
                disabled={this.state.registrationDisabled}
              />
              <label className="form-check-label form-label" htmlFor="livesInVilnius">
                Vaiko deklaruojama gyvenamoji vieta yra Vilniaus miesto
                savivaldybė
              </label>
            </div>
            <div className="form-check">
              <input
                type="checkbox"
                className="form-check-input"
                name="parentLivedInVilniusMoreThan2Years"
                id="chkParentLivedInVilniusForMoreThan2Years"
                checked={this.state.priorities.parentLivedInVilniusMoreThan2Years}
                onChange={this.checkboxOnChange}
                disabled={this.state.registrationDisabled}
              />
              <label className="form-check-label form-label" htmlFor="parentLivedInVilniusMoreThan2Years">
                Vaiko vieno iš tėvų deklaruojamoji gyvenamoji vieta Vilniaus savivaldybėje yra ne mažiau kaip 2 metai
              </label>
            </div>
            <div className="form-check">
              <input
                type="checkbox"
                className="form-check-input"
                name="childIsAdopted"
                id="chkChildIsAdopted"
                checked={this.state.priorities.childIsAdopted}
                onChange={this.checkboxOnChange}
                disabled={this.state.registrationDisabled}
              />
              <label className="form-check-label form-label" htmlFor="childIsAdopted">
                Vaikas yra įvaikintas
              </label>
            </div>
            <div className="form-check">
              <input
                type="checkbox"
                className="form-check-input"
                name="familyHasThreeOrMoreChildrenInSchools"
                id="chkFamilyHasThreeOrMoreChildrenInSchools"
                checked={this.state.priorities.familyHasThreeOrMoreChildrenInSchools}
                onChange={this.checkboxOnChange}
                disabled={this.state.registrationDisabled}
              />
              <label
                className="form-check-label form-label"
                htmlFor="familyHasThreeOrMoreChildrenInSchools"
              >
                Šeima augina (globoja) tris ir daugiau vaikų, kurie mokosi pagal
                bendrojo ugdymo programas
              </label>
            </div>
            <div className="form-check">
              <input
                type="checkbox"
                className="form-check-input"
                name="guardianInSchool"
                id="chkGuardianInSchool"
                checked={this.state.priorities.guardianInSchool}
                onChange={this.checkboxOnChange}
                disabled={this.state.registrationDisabled}
              />
              <label className="form-check-label form-label" htmlFor="guardianInSchool">
                Vienas iš tėvų (globėjų) mokosi bendrojo ugdymo mokykloje
              </label>
            </div>
            <div className="form-check">
              <input
                type="checkbox"
                className="form-check-input"
                name="guardianDisability"
                id="chkGuardianDisability"
                checked={this.state.priorities.guardianDisability}
                onChange={this.checkboxOnChange}
                disabled={this.state.registrationDisabled}
              />
              <label className="form-check-label form-label" htmlFor="guardianDisability">
                Vienas iš tėvų (globėjų) turi ne daugiau kaip 40 procentų
                darbingumo lygio
              </label>
            </div>
          </div>       
    );
  }

  /** Darzeliu sarasas i options formata */
  kindergartenListToSelect(kList, priorityFieldName) {
    var optionsList = kList.map((k) => ({
      value: k.id,
      label: k.name + " (" + k.address + ")",
      name: priorityFieldName,
    }));
    return optionsList;
  }
  

  /** Darzeliu prioritetu forma */
  kindergartenPriorityForm() {
    return (
      <div className="form">
        <h6 className="formHeader">Darželių prioritetas</h6>
        <p>Pasirinkite darželių prioritetą, daugiausiai leidžiamos 5
          įstaigos.</p>

        <div className="mb-3">
          <label htmlFor="kindergartenId1" className="form-label">
            1 prioritetas <span className="fieldRequired">*</span>
          </label>
          <span id="selectKindergarten1">
            <Select
              className="basic-single"
              classNamePrefix="select"
              name="kindergartenId1"
              id="selKindergartenId1"
              placeholder="Pasirinkite darželį iš sąrašo"
              options={this.state.kindergartenList}
              onChange={(e) => {
                if (
                  e.value !== this.state.kindergartenChoises.kindergartenId1
                ) {
                  const lastIdValue = this.state.kindergartenChoises
                    .kindergartenId1;
                  this.setState({
                    ...this.state,
                    kindergartenChoises: {
                      ...this.state.kindergartenChoises,
                      kindergartenId1: e.value,
                    },
                  });
                  this.state.kindergartenList.forEach((element) => {
                    if (element.value === lastIdValue) {
                      element.disabled = "no";
                    }
                  });
                }
                this.setState({
                  ...this.state,
                  kindergartenChoises: {
                    ...this.state.kindergartenChoises,
                    kindergartenId1: e.value,
                  },
                });
                this.state.kindergartenList.forEach((element) => {
                  if (element.value === e.value) {
                    element.disabled = "yes";
                  }
                });
              }}
              isOptionDisabled={(option) => option.disabled === "yes" || this.state.registrationDisabled}
            />
          </span>
        </div>
        <div className="mb-3">
          <label htmlFor="kindergartenId2" className="form-label">2 prioritetas</label>
          <Select
            name="kindergartenId2"
            id="selKindergartenId2"
            placeholder="Pasirinkite darželį iš sąrašo"
            options={this.state.kindergartenList}
            onChange={(e) => {
              if (
                e.value !== this.state.kindergartenChoises.kindergartenId2
              ) {
                const lastIdValue = this.state.kindergartenChoises
                  .kindergartenId2;
                this.setState({
                  ...this.state,
                  kindergartenChoises: {
                    ...this.state.kindergartenChoises,
                    kindergartenId2: e.value,
                  },
                });
                this.state.kindergartenList.forEach((element) => {
                  if (element.value === lastIdValue) {
                    element.disabled = "no";
                  }
                });
              }
              this.setState({
                ...this.state,
                kindergartenChoises: {
                  ...this.state.kindergartenChoises,
                  kindergartenId2: e.value,
                },
              });
              this.state.kindergartenList.forEach((element) => {
                if (element.value === e.value) {
                  element.disabled = "yes";
                }
              });
            }}
            isOptionDisabled={(option) => option.disabled === "yes" || this.state.registrationDisabled}
          />
        </div>
        <div className="mb-3">
          <label htmlFor="kindergartenId3" className="form-label">3 prioritetas</label>
          <Select
            name="kindergartenId3"
            id="selKindergartenId3"
            placeholder="Pasirinkite darželį iš sąrašo"
            options={this.state.kindergartenList}
            onChange={(e) => {
              if (
                e.value !== this.state.kindergartenChoises.kindergartenId3
              ) {
                const lastIdValue = this.state.kindergartenChoises
                  .kindergartenId3;
                this.setState({
                  ...this.state,
                  kindergartenChoises: {
                    ...this.state.kindergartenChoises,
                    kindergartenId3: e.value,
                  },
                });
                this.state.kindergartenList.forEach((element) => {
                  if (element.value === lastIdValue) {
                    element.disabled = "no";
                  }
                });
              }
              this.setState({
                ...this.state,
                kindergartenChoises: {
                  ...this.state.kindergartenChoises,
                  kindergartenId3: e.value,
                },
              });
              this.state.kindergartenList.forEach((element) => {
                if (element.value === e.value) {
                  element.disabled = "yes";
                }
              });
            }}
            isOptionDisabled={(option) => option.disabled === "yes" || this.state.registrationDisabled}
          />
        </div>
        <div className="mb-3">
          <label htmlFor="kindergartenId4" className="form-label">4 prioritetas</label>
          <Select
            name="kindergartenId4"
            id="selKindergartenId4"
            placeholder="Pasirinkite darželį iš sąrašo"
            options={this.state.kindergartenList}
            onChange={(e) => {
              if (
                e.value !== this.state.kindergartenChoises.kindergartenId4
              ) {
                const lastIdValue = this.state.kindergartenChoises
                  .kindergartenId4;
                this.setState({
                  ...this.state,
                  kindergartenChoises: {
                    ...this.state.kindergartenChoises,
                    kindergartenId4: e.value,
                  },
                });
                this.state.kindergartenList.forEach((element) => {
                  if (element.value === lastIdValue) {
                    element.disabled = "no";
                  }
                });
              }
              this.setState({
                ...this.state,
                kindergartenChoises: {
                  ...this.state.kindergartenChoises,
                  kindergartenId4: e.value,
                },
              });
              this.state.kindergartenList.forEach((element) => {
                if (element.value === e.value) {
                  element.disabled = "yes";
                }
              });
            }}
            isOptionDisabled={(option) => option.disabled === "yes" || this.state.registrationDisabled}
          />
        </div>
        <div className="mb-3">
          <label htmlFor="kindergartenId5" className="form-label">5 prioritetas</label>
          <Select
            name="kindergartenId5"
            id="selKindergartenId5"
            placeholder="Pasirinkite darželį iš sąrašo"
            options={this.state.kindergartenList}
            onChange={(e) => {
              if (
                e.value !== this.state.kindergartenChoises.kindergartenId5
              ) {
                const lastIdValue = this.state.kindergartenChoises
                  .kindergartenId5;
                this.setState({
                  ...this.state,
                  kindergartenChoises: {
                    ...this.state.kindergartenChoises,
                    kindergartenId5: e.value,
                  },
                });
                this.state.kindergartenList.forEach((element) => {
                  if (element.value === lastIdValue) {
                    element.disabled = "no";
                  }
                });
              }
              this.setState({
                ...this.state,
                kindergartenChoises: {
                  ...this.state.kindergartenChoises,
                  kindergartenId5: e.value,
                },
              });
              this.state.kindergartenList.forEach((element) => {
                if (element.value === e.value) {
                  element.disabled = "yes";
                }
              });
            }}
            isOptionDisabled={(option) => option.disabled === "yes" || this.state.registrationDisabled}
          />
        </div>
      </div>

    );
  }

  /** Pagrindinio atstovo formos onChange */
  mainGuardianOnChange(e) {
    applicationInputValidator(e);
    this.setState({
      mainGuardian: {
        ...this.state.mainGuardian,
        [e.target.name]: e.target.value,
      },
    });
  }

  /** Antro atstovo formos onChange */
  additionalGuardianOnChange(e) {
    applicationInputValidator(e);
    this.setState({
      additionalGuardian: {
        ...this.state.additionalGuardian,
        [e.target.name]: e.target.value,
      },
    });
  }

  /** Vaiko formos onChange */
  childOnChange(e) {
    applicationInputValidator(e);
    this.setState({
      [e.target.name]: e.target.value,
    });
  }

  /** Checkbox onChange */
  checkboxOnChange(e) {
    this.setState({
      priorities: {
        ...this.state.priorities,
        [e.target.name]: e.target.checked,
      },
    });
  }

  /** Handle submit */
  submitHandle(e) {
    e.preventDefault();
    if(!this.state.childCodeDisabled) {
      swal({
          icon: "error",
          title: "Klaida!",
          text: `Vaiko duomenys turi būti gauti iš sistemos.`,
        });
  } else {

    if(!this.state.childCodeDisabled) { 
      swal({
        icon: "error",
        title: "Įvyko klaida",
        text: "Vaiko duomenys turi būti surasti",
      });

    } else {
    const data = {
      additionalGuardian: this.state.additionalGuardian,
      birthdate: this.state.birthdate.toLocaleDateString("en-CA"),
      childName: this.state.childName,
      childPersonalCode: this.state.childPersonalCode,
      childSurname: this.state.childSurname,
      kindergartenChoises: this.state.kindergartenChoises,
      mainGuardian: this.state.mainGuardian,
      priorities: this.state.priorities
    }
    if (!this.state.kindergartenChoises.kindergartenId1) {
      swal({
        title: "Įvyko klaida",
        text: "1 Prioritetas yra privalomas",
      });
    } else {
      http
        .post(`${apiEndpoint}/api/prasymai/user/new`, data)
        .then((response) => {
          //console.log(response);
          swal({
            text: response.data,
            button: "Gerai",
          });

          this.props.history.push("/prasymai")
        })
        .catch((error) => {
          swal({
            text: "Įvyko klaida. " + error.response.data,
            button: "Gerai"
          });
        });
    }
  }
}
}

  drawMessageRegistrationNotAvailable(status) {
    if (status) {
      return (
        <div className="alert alert-warning p-1" role="alert">
          Šiuo metu registracija nevykdoma
        </div>
      )
    }
  }

  
  /** Get child's main information by his personal code.
   *  Additional check is made to check the validity of child's personal code. */
  async getChildInfoFromHisPersonalCode(personalCode) {
    if(!this.state.childCodeDisabled) {
      var failed = false;    

      if(/\D/.test(personalCode)) {
        swal({
          icon: "error",
          title: "Netinkamo formato asmens kodas!",
          text: `Asmens kodą sudaro 11 skaitmenų, jūsų asmens kodas turi neleistinų simbolių.`,
        });
      }
      else if(personalCode.length !== 11) {
        swal({
          icon: "error",
          title: "Netinkamo ilgio asmens kodas!",
          text: `Asmens kodą sudaro 11 skaitmenų, jūs įvedėte ${personalCode.length}.`,
        });
      }
      else {
          async function getData(url = '', data = {}) {
            const response = await fetch(url, {
              method: 'GET',
              mode: 'cors',
              cache: 'no-cache',
              credentials: 'same-origin',
              headers: {
                'Content-Type': 'application/json'
              },
              redirect: 'follow',
              referrerPolicy: 'no-referrer',
            });
            if (response.status !== 200) {
              failed = true;
            }
            return response.json();
          }

          getData(`https://darzelis.akademijait.vtmc.lt/registru-centras/vaikai/${personalCode}`, { asmensKodas: {personalCode} })
              .then(response => {
              if(failed) {
                swal({
                  icon: "error",
                  title: `${response.details}!`,
                  text: `Patikrinkite, ar teisingai įvedėte asmens kodą`,
                })
              } else {
                this.setState((response) = ({
                  childName: response.vardas,
                  childSurname: response.pavarde,
                  birthdate: parseISO(response.gimimoData),
                  childButtonText: "Pakeisti",
                  childCodeDisabled: true
                }));
              }
            })              
      }
    } else {
      this.setState({
        childName: "",
        childSurname: "",
        birthdate: "",
        childButtonText: "Surasti vaiką",
        childCodeDisabled: false
      })
    }
    }
  

  render() {

    return (
      <div className="container pt-4">
        {
          this.drawMessageRegistrationNotAvailable(this.state.registrationDisabled)
        }
        <div className="form">
          <form onSubmit={this.submitHandle}>
            <div className="row">
              <div className="col-4 pt-1">
                {
                  /** Atstovas 1 */
                  this.userForm(true)
                }
              </div>

              <div className="col-4 pt-1">
                {
                  /** Atstovas 2 */
                  this.userForm(false)
                }
              </div>

              <div className="col-4">
                {
                  /** Vaiko forma */
                  this.childForm()
                }
              </div>
            </div>
            <div className="row">
              <div className="col-12">{this.checkboxPriorityForm()}</div>

              <div className="col-7">
                <div className="">{this.kindergartenPriorityForm()}</div>

                <p>
                  Dėmesio! Jei pirmu numeriu nurodytoje įstaigoje nėra laisvų
                  vietų, vieta skiriama antru numeriu pažymėtoje įstaigoje, jei
                  joje yra laisvų vietų ir t. t. Jeigu visuose prašyme pažymėtose
                  įstaigose nėra laisvų vietų, prašymas lieka laukiančiųjų eilėje.
                </p>

                <button type="submit" className="btn btn-primary mt-3" disabled={this.state.registrationDisabled}>
                  Sukurti prašymą
                </button>

              </div>
            </div>
          </form>
        </div>
      </div>
    );
  }
}


export default withRouter(CreateApplicationFormContainer);

