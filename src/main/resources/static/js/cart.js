var num = 1;
var table;


const saveCart = () => {
  const form = document.getElementById('formTickets');
  const formData = new FormData(form);

  fetch('http://localhost:8080/airhermes/reservations/cart', {
    method: 'POST',
    body: formData
  })
  .then(async res => {
//    const text = await res.text(); // get raw response
//    console.log('Raw response:', text);

    // try to parse it as JSON only if it's actually JSON
//    try {
//      const json = JSON.parse(text);
//      console.log('Parsed JSON:', json);
//    } catch (err) {
//      console.error('Failed to parse JSON:', err);
//    }
  })
  .catch(err => {
    console.error('Error posting form:', err);
  });


  console.log("saved Cart content");
};

function removePassenger(rowOfPassenger) {
    num += 1;
//    let rowToRemove = document.getElementById(rowOfPassenger);
    $("tr#"+rowOfPassenger).first().remove();
}

function chooseSeat(passengerNum, idOfFlight) {
//  todo
    window.alert(passengerNum + "flight:" + idOfFlight);
}

function addPassengerRow(removable) {
    num += 1;

    const passengerNumber = num;

    const newPassengerRow = document.createElement('tr');
    newPassengerRow.setAttribute('id', 'passenger' + passengerNumber);

    table.append(newPassengerRow);



    const cellName = document.createElement('th');
    newPassengerRow.appendChild(cellName);

    const inputName = document.createElement('input');
    inputName.setAttribute('type', 'text');
    inputName.setAttribute('name', 'passengerName');
    inputName.setAttribute('required', true);
//    inputName.value = 'New Data 1';
    cellName.appendChild(inputName);


    const cellLastName = document.createElement('th');
    newPassengerRow.appendChild(cellLastName);

    const inputLastName = document.createElement('input');
    inputLastName.setAttribute('type', 'text');
    inputLastName.setAttribute('name', 'passengerSurname');
    inputLastName.setAttribute('required', true);
    //    inputLastName.value = 'New Data 1';
    cellLastName.appendChild(inputLastName);


    const cellPassport = document.createElement('th');
    newPassengerRow.appendChild(cellPassport);

    const inputPassport = document.createElement('input');
    inputPassport.setAttribute('type', 'number');
    inputPassport.setAttribute('name', 'passportNumber');
    inputPassport.setAttribute('required', true);
    inputPassport.setAttribute('minlength', 9);
    inputPassport.setAttribute('maxlength', 9);
//    inputPassport.value = 'New Data 1';
    cellPassport.appendChild(inputPassport);


    const cellSeat = document.createElement('th');
    newPassengerRow.appendChild(cellSeat);

    const inputSeat = document.createElement('input');
    inputSeat.setAttribute('type', 'text');
    inputSeat.setAttribute('name', 'seatNumber');
    inputSeat.setAttribute('required', true);
//    inputSeat.setAttribute('readonly', true);
//    inputSeat.value = 'New Data 1';
    cellSeat.appendChild(inputSeat);




    const urlParams = new URLSearchParams(window.location.search);
    let flightIDs = urlParams.getAll('flightId');

    for (let i = 0; i < flightIDs.length; i++) {
        const fID = flightIDs[i];

        const cellSeatButton = document.createElement('th');
        const buttonSeatChoice = document.createElement('button');

        buttonSeatChoice.setAttribute('type', 'button');
        buttonSeatChoice.textContent = 'Seats on Flight ' + fID;

        buttonSeatChoice.onclick = () => {
            chooseSeat('passenger' + passengerNumber, fID);
        };

        cellSeatButton.appendChild(buttonSeatChoice);
        newPassengerRow.appendChild(cellSeatButton);
    }

    if (removable) {
        const cellRemovePassenger = document.createElement('th');
        newPassengerRow.appendChild(cellRemovePassenger);

        const buttonRemovePassenger = document.createElement('button');
        buttonRemovePassenger.setAttribute('type', 'button');
        buttonRemovePassenger.textContent = '❌';

        buttonRemovePassenger.onclick = () => {
            removePassenger('passenger' + passengerNumber);
        };

        cellRemovePassenger.appendChild(buttonRemovePassenger);
    }
}




$(document).ready(
                function() {

//                    num = getIdOfLastRowInTable()...

                    table = $("table#ticketsTable");

//                    addPassengerRow(false);

                    document.getElementById('addPassengerButton').onclick = (event) => addPassengerRow(true);

                }
)