const beginButton = document.getElementById('buttonBegin');

let numDice = 10;
var diceNumbers = [];

function randomNumbers() {
    diceNumbers = [];
    for (let i = 0; i < numDice; i++) {
        let rand = Math.floor(Math.random() * 6) + 1;
        diceNumbers.push(rand);
    }
}

function rollDice() {
    randomNumbers();
    let rollDiv = document.getElementById('diceRolls');
    rollDiv.innerHTML = diceNumbers.join(", ");
}

function rollDice2() {
    randomNumbers();
    let rollDiv = document.getElementById('diceRolls2');
    rollDiv.innerHTML = diceNumbers.join(", ");
}

function rollDice3() {
    randomNumbers();
    let rollDiv = document.getElementById('diceRolls3');
    rollDiv.innerHTML = diceNumbers.join(", ");
}