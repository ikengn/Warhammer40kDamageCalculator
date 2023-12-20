const addRowButton = document.getElementById('button');
const table = document.getElementById('table');

// Test button to add rows with placeholder text
addRowButton.addEventListener("click", function(){
    const addRow = document.createElement("tr");
    const c1 = document.createElement("td");
    const c2 = document.createElement("td");
    const c3 = document.createElement("td");

    c1.textContent = "test1";
    c2.textContent = "test2";
    c3.textContent = "test3";

    addRow.appendChild(c1);
    addRow.appendChild(c2);
    addRow.appendChild(c3);

    table.querySelector("tbody").appendChild(addRow);

});