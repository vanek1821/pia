function addRow() {
    var table = document.getElementById('itemTable');
    var rowCount = table.rows.length;
    var row = table.insertRow(rowCount);

    var cellName = row.insertCell(0);
    cellName.innerHTML ="<input type='text' id='items" + rowCount + ".name' name='items[" + (rowCount - 1) + "].name' th:disabled='${invoice.cancelled}\'>";

    var cellQuantity = row.insertCell(1);
    cellQuantity.innerHTML ="<input type='number' step='1' min='0' id='items" + rowCount + ".quantity' name='items[" + (rowCount - 1) + "].quantity' th:disabled='${invoice.cancelled}\'>";

    var cellPricePerOne = row.insertCell(2);
    cellPricePerOne.innerHTML ="<input type='number' step='1' min='0' id='items" + rowCount + ".price' name='items[" + (rowCount - 1) + "].price' th:disabled='${invoice.cancelled}\'>";

    var cellDelete = row.insertCell(3);
    var buttonDelete = document.createElement('button');
    buttonDelete.type = "button";
    buttonDelete.innerText = "delete";
    buttonDelete.onclick = (function(buttonDelete) {return function() {deleteRow(buttonDelete);}})(buttonDelete);
    cellDelete.appendChild(buttonDelete);
}

function deleteRow(button) {
    var row = button.parentNode.parentNode;
    row.parentNode.removeChild(row);
}