function InputValidator(event) {
    const target = event.target;



    if (target.validity.valueMissing || target.value === "") {
        if (target.name === "birthdate") {
            target.setCustomValidity("Gimimo data yra privalomas laukelis")

        } else if (target.name === "phone" || target.name === "additionalPhone") {
            target.setCustomValidity("Telefono numeris yra privalomas laukelis")
        } else {
            target.setCustomValidity(target.placeholder + " yra privalomas laukelis");
        }

    } else if (target.name === "email" || target.name === "additionalEmail") {
        if (target.validity.patternMismatch) {
            target.setCustomValidity("Neteisingas el. pašto formatas")
        } else {
            target.setCustomValidity("")
        }
    } else if (target.name === "personalCode" || target.name === "childPersonalCode" || target.name === "additionalPersonalCode") {
        if (target.validity.patternMismatch) {
            target.setCustomValidity("Asmens koda sudaro 11 skaičių, įvesta skaičių: " + target.value.length)
        } else {
            target.setCustomValidity("")
        }
    } else if (target.name === "name" || target.name === "childName" || target.name === "additionalName") {
        if (target.validity.patternMismatch) {
            target.setCustomValidity("Netinkamo formato vardas. Vedant daugiau kaip vieną vardą, vardus reikia atskirti tarpeliu")
        } else {
            target.setCustomValidity("")
        }
    } else if (target.name === "surname" || target.name === "childSurname" || target.name === "additionalSurname") {
        if (target.validity.patternMismatch) {
            target.setCustomValidity("Netinkamo formato pavardė. Vedant daugiau kaip vieną pavardę, pavardes reikia atskirti brūkšneliu")
        } else {
            target.setCustomValidity("")
        }
    } else if (target.name === "address" || target.name === "additionalAddress") {
        if (target.validity.patternMismatch) {
            target.setCustomValidity("Netinkamo formato adresas")
        } else {
            target.setCustomValidity("")
        }
    } else if (target.name === "phone" || target.name === "additionalPhone") {
        if (target.value.includes('+')) {
            if (target.validity.patternMismatch) {
                target.setCustomValidity("Telefono numerį sudaro nuo 4 iki 19 skaičių, įvesta skaičių: " + (0 + target.value.length - 1))
            }
            else {
                target.setCustomValidity("");
            }
        } else {
            target.setCustomValidity('Formatas: +37000000000')
        }
    } else if (target.id === "txtNewPassword" || target.id === "txtNewPasswordRepeat") {
        if (target.validity.patternMismatch) {
            target.setCustomValidity("Slaptažodis turi būti 8-255 simbolių ilgio, turėti bent vieną didžiąją ir mažąją raides ir bent vieną skaičių")
        }
        else {
            target.setCustomValidity("");
        }
    } else if (target.id === "txtOldPassword") {
        target.setCustomValidity("");
    } else {
    target.setCustomValidity("")
    }
}

export default InputValidator;
