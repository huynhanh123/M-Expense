/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

// Wait for the deviceready event before using any of Cordova's device APIs.
// See https://cordova.apache.org/docs/en/latest/cordova/events/events.html#deviceready

document.addEventListener('deviceready', onDeviceReady, false);

function onDeviceReady() {
    // Cordova is now initialized. Have fun!

    console.log('Running cordova-' + cordova.platformId + '@' + cordova.version);
    document.getElementById('deviceready').classList.add('ready');
}

let ERROR = 'ERROR';
let currentTripId = 'currentTripId';
let db = window.openDatabase('FGW', '1.0', 'FGW', 20000);

$(document).on('ready', onDeviceReady);

$(document).on('click', '#page-home #panel-open', function () {
    $('#page-home #panel').panel('open');
});

$(document).on('click', '#page-create #panel-open', function () {
    $('#page-create #panel').panel('open');
});

$(document).on('click', '#page-setting #panel-open', function () {
    $('#page-setting #panel').panel('open');
});

$(document).on('click', '#page-about #panel-open', function () {
    $('#page-about #panel').panel('open');
});

$(document).on('click', '#page-setting #btn-backup', function () {
    let message = 'Backup to cloud successfully.';

    log(message);
    toast(message);
});

$(document).on('click', '#page-setting #btn-reset', function () {
    db.transaction(function (tx) {
        let query = `DROP TABLE Expense`;
        tx.executeSql(query, [], function (tx, result) {
            log(`Drop table 'Expense' successfully.`);
        }, transactionError);

        query = `DROP TABLE Trip`;
        tx.executeSql(query, [], function (tx, result) {
            log(`Drop table 'Trip' successfully.`);

            prepareDatabase(db);

            toast('Reset successfully.')
        }, transactionError);
    });
});

// Page CREATE
$(document).on('submit', '#page-create #frm-register', confirmTrip);
$(document).on('submit', '#page-create #frm-confirm', registerTrip);
$(document).on('click', '#page-create #frm-confirm #edit', function () {
    $('#page-create #frm-confirm').popup('close');
});

// Page LIST
$(document).on('pagebeforeshow', '#page-home', showList);

$(document).on('submit', '#page-home #frm-search', search);

$(document).on('keyup', $('#page-home #txt-filter'), filterTrip);

$(document).on('click', '#page-home #btn-reset', showList);
$(document).on('click', '#page-home #btn-filter-popup', openFormSearch);
$(document).on('click', '#page-home #list-trip li a', navigatePageDetail);

// Page DETAIL
$(document).on('pagebeforeshow', '#page-detail', showDetail);

$(document).on('click', '#page-detail #btn-toggle-expense', function () {
    let expenseDisplay = $('#page-detail #expense').css('display');

    $('#page-detail #expense').css('display', expenseDisplay == 'none' ? 'block' : 'none');
});

$(document).on('click', '#page-detail #btn-update-popup', showUpdate);
$(document).on('click', '#page-detail #btn-delete-popup', function () {
    changePopup($('#page-detail #option'), $('#page-detail #frm-delete'));
});

$(document).on('click', '#page-detail #frm-update #cancel', function () {
    $('#page-detail #frm-update').popup('close');
});

$(document).on('click', '#page-detail #frm-add-expense #cancel', function () {
    $('#page-detail #frm-add-expense').popup('close');
});

$(document).on('submit', '#page-detail #frm-update', updateTrip);
$(document).on('submit', '#page-detail #frm-delete', deleteTrip);
$(document).on('submit', '#page-detail #frm-add-expense', addExpense);
$(document).on('keyup', '#page-detail #frm-delete #txt-confirm', confirmDeleteTrip);

function onDeviceReady() {
    log(`Device is ready.`);

    prepareDatabase(db);
}

function log(message, type = 'INFO') {
    console.log(`${new Date()} [${type}] ${message}`);
}

function changePopup(sourcePopup, destinationPopup) {
    let afterClose = function () {
        destinationPopup.popup("open");
        sourcePopup.off("popupafterclose", afterClose);
    };

    sourcePopup.on("popupafterclose", afterClose);
    sourcePopup.popup("close");
}

function confirmTrip(e) {
    e.preventDefault();

    log('Open the confirmation popup.');

    $('#page-create #frm-confirm #name').text($('#page-create #frm-register #name').val());
    $('#page-create #frm-confirm #type').text($('#page-create #frm-register #type').val());
    $('#page-create #frm-confirm #destination').text($('#page-create #frm-register #destination').val());
    $('#page-create #frm-confirm #status').text($('#page-create #frm-register #status').val());
    $('#page-create #frm-confirm #date').text($('#page-create #frm-register #date').val());
    $('#page-create #frm-confirm #description').text($('#page-create #frm-register #description').val());
    $('#page-create #frm-confirm #risk').text($('#page-create #frm-register #risk').val());
    

    $('#page-create #frm-confirm').popup('open');
}

function registerTrip(e) {
    e.preventDefault();

    let name = $('#page-create #frm-register #name').val();
    let destination = $('#page-create #frm-register #destination').val();
    let date = $('#page-create #frm-register #date').val();
    let status = $('#page-create #frm-register #status').val();
    let type = $('#page-create #frm-register #type').val();
    let description = $('#page-create #frm-register #description').val();
    let risk = $('#page-create #frm-register #risk').val();

    db.transaction(function (tx) {
        let query = `INSERT INTO Trip (Name, Type, Destination, Status, Description, Risk, Date) VALUES (?, ?, ?, ?, ?, ?, julianday('${date}'))`;
        tx.executeSql(query, [name, type, destination, status, description, risk], transactionSuccess, transactionError);

        function transactionSuccess(tx, result) {
            let message = `Added trip '${name}'.`;

            log(message);
            toast(message);

            $('#page-create #frm-register').trigger('reset');
            $('#page-create #frm-register #name').focus();

            $('#page-create #frm-confirm').popup('close');
        }
    });
}

function showList() {
    db.transaction(function (tx) {
        let query = `SELECT *, date(Date) AS DateConverted FROM Trip`;

        tx.executeSql(query, [], transactionSuccess, transactionError);

        function transactionSuccess(tx, result) {
            log(`Get list of trips successfully.`);
            displayList(result.rows);
        }
    });
}

function navigatePageDetail(e) {
    e.preventDefault();

    let id = $(this).data('details').Id;
    localStorage.setItem(currentTripId, id);

    $.mobile.navigate('#page-detail', { transition: 'none' });
}

function showDetail() {
    let id = localStorage.getItem(currentTripId);

    db.transaction(function (tx) {
        let query = `SELECT *, date(Date) AS DateConverted FROM Trip WHERE Id = ?`;

        tx.executeSql(query, [id], transactionSuccess, transactionError);

        function transactionSuccess(tx, result) {
            if (result.rows[0] != null) {
                log(`Get details of trip '${result.rows[0].name}' successfully.`);

                $('#page-detail #detail #name').text(result.rows[0].Name);
                $('#page-detail #detail #destination').text(result.rows[0].Destination);
                $('#page-detail #detail #description').text(result.rows[0].Description);
                $('#page-detail #detail #risk').text(result.rows[0].Risk);
                $('#page-detail #detail #date').text(result.rows[0].DateConverted);
                $('#page-detail #detail #type').text(result.rows[0].Type);
                $('#page-detail #detail #status').text(result.rows[0].Status);

                showExpense();
            }
            else {
                let errorMessage = 'Trip not found.';

                log(errorMessage, ERROR);

                $('#page-detail #detail #name').text(errorMessage);
                $('#page-detail #btn-update').addClass('ui-disabled');
                $('#page-detail #btn-delete-confirm').addClass('ui-disabled');
            }
        }
    });
}

function confirmDeleteTrip() {
    let text = $('#page-detail #frm-delete #txt-confirm').val();

    if (text == 'confirm delete') {
        $('#page-detail #frm-delete #btn-delete').removeClass('ui-disabled');
    }
    else {
        $('#page-detail #frm-delete #btn-delete').addClass('ui-disabled');
    }
}

function deleteTrip(e) {
    e.preventDefault();

    let tripId = localStorage.getItem(currentTripId);

    db.transaction(function (tx) {
        let name = '';

        let query = 'SELECT * FROM Trip WHERE Id = ?';
        tx.executeSql(query, [tripId], function (tx, result) {
            name = result.rows[0].Name;
        }, transactionError);

        query = 'DELETE FROM Expense WHERE TripId = ?';
        tx.executeSql(query, [tripId], function (tx, result) {
            log(`Delete expenses of trip '${tripId}' successfully.`);
        }, transactionError);

        query = 'DELETE FROM Trip WHERE Id = ?';
        tx.executeSql(query, [tripId], function (tx, result) {
            let message = `Deleted trip '${name}'.`;

            log(message);
            toast(message);

            $('#page-detail #frm-delete').trigger('reset');

            $.mobile.navigate('#page-list', { transition: 'none' });
        }, transactionError);
    });
}

function addExpense(e) {
    e.preventDefault();

    let tripId = localStorage.getItem(currentTripId);
    let type = $('#page-detail #frm-add-expense #type').val();
    let amount = parseInt($('#page-detail #frm-add-expense #amount').val());
    let date = $('#page-detail #frm-add-expense #date').val();
    let time = $('#page-detail #frm-add-expense #time').val();
    let comment = $('#page-detail #frm-add-expense #comment').val();

    db.transaction(function (tx) {
        let query = `INSERT INTO Expense (Type, Amount, Comment, TripId, Date, Time) VALUES (?, ?, ?, ?, julianday('${date}'), julianday('${time}'))`;
        tx.executeSql(query, [type, amount, comment, tripId], transactionSuccess, transactionError);

        function transactionSuccess(tx, result) {
            let message = `Added expense '${amount.toLocaleString("en-US")} VNĐ (${type})'.`;

            log(message);
            toast(message);

            $('#page-detail #frm-add-expense').trigger('reset');
            $('#page-detail #frm-add-expense').popup('close');

            showExpense();
        }
    });
}

function showExpense() {
    let id = localStorage.getItem(currentTripId);

    db.transaction(function (tx) {
        let query = `SELECT *, date(Date) AS DateConverted, time(Time) AS TimeConverted FROM Expense WHERE TripId = ? ORDER BY Date DESC, Time DESC`;

        tx.executeSql(query, [id], transactionSuccess, transactionError);

        function transactionSuccess(tx, result) {
            log(`Get list of expenses successfully.`);

            let expenseList = '';
            let currentDate = '';
            for (let expense of result.rows) {
                if (currentDate != expense.DateConverted) {
                    expenseList += `<div class='list-date'>${expense.DateConverted}</div>`;
                    currentDate = expense.DateConverted;
                }

                expenseList += `
                    <div class='list'>
                        <table style='white-space: nowrap; width: 100%;'>
                            <tr style='height: 25px;'>
                                <td>${expense.Type}: ${expense.Amount.toLocaleString('en-US')} VNĐ</td>
                                <td style='text-align: right;'>${expense.TimeConverted}</td>
                            </tr>

                            <tr>
                                <td colspan='2'><em>${expense.Comment}</em></td>
                            </tr>
                        </table>
                    </div>`;
            }

            expenseList += `<div class='list end-list'>You've reached the end of the list.</div>`;

            $('#page-detail #expense #list').empty().append(expenseList);

            log(`Show list of expenses successfully.`);
        }
    });
}

function showUpdate() {
    let id = localStorage.getItem(currentTripId);

    db.transaction(function (tx) {
        let query = `SELECT *, date(Date) as DateConverted FROM Trip WHERE Id = ?`;

        tx.executeSql(query, [id], transactionSuccess, transactionError);

        function transactionSuccess(tx, result) {
            if (result.rows[0] != null) {
                log(`Get details of trip '${result.rows[0].Name}' successfully.`);

                $(`#page-detail #frm-update #name`).val(result.rows[0].Name);
                $(`#page-detail #frm-update #type`).val(result.rows[0].Type);
                $(`#page-detail #frm-update #destination`).val(result.rows[0].Destination);
                $(`#page-detail #frm-update #date`).val(result.rows[0].DateConverted);
                $(`#page-detail #frm-update #status`).val(result.rows[0].Status);
                $(`#page-detail #frm-update #risk`).val(result.rows[0].Risk).slider("refresh");
                $(`#page-detail #frm-update #description`).val(result.rows[0].Description);

                changePopup($('#page-detail #option'), $('#page-detail #frm-update'));
            }
        }
    });
}

function updateTrip(e) {
    e.preventDefault();

    let id = localStorage.getItem(currentTripId);
    let name = $('#page-detail #frm-update #name').val();
    let type = $('#page-detail #frm-update #type').val();
    let destination = $('#page-detail #frm-update #destination').val();
    let status = $('#page-detail #frm-update #status').val();
    let date = $('#page-detail #frm-update #date').val();
    let description = $('#page-detail #frm-update #description').val();
    let risk = $('#page-detail #frm-update #risk').val();

    db.transaction(function (tx) {
        let query = `UPDATE Trip SET Name = ?, Type = ?, Destination = ?, Status = ?, Description = ?, Risk = ?, Date = julianday('${date}') WHERE Id = ?`;

        tx.executeSql(query, [name, type, destination, status, description, risk, id], transactionSuccess, transactionError);

        function transactionSuccess(tx, result) {
            let message = `Updated trip '${name}'.`;

            log(message);
            toast(message);

            showDetail();

            $('#page-detail #frm-update').popup('close');
        }
    });
}

function filterTrip() {
    let filter = $('#page-home #txt-filter').val().toLowerCase();
    let li = $('#page-home #list-trip ul li');

    for (let i = 0; i < li.length; i++) {
        let a = li[i].getElementsByTagName('a')[0];
        let text = a.textContent || a.innerText;

        li[i].style.display = text.toLowerCase().indexOf(filter) > -1 ? '' : 'none';
    }
}

function openFormSearch(e) {
    e.preventDefault();
    $('#page-home #frm-search').popup('open');
}

function search(e) {
    e.preventDefault();

    let name = $('#page-home #frm-search #name').val();
    let destination = $('#page-home #frm-search #destination').val();
    let date = $('#page-home #frm-search #date').val();

    db.transaction(function (tx) {
        let query = `SELECT *, date(Date) as DateConverted FROM Trip WHERE`;

        query += name ? ` Name LIKE "%${name}%"   AND` : '';
        query += destination ? ` Destination LIKE "%${destination}%"   AND` : '';
        query += date ? ` Date = julianday('${date}')   AND` : '';

        query = query.substring(0, query.length - 6);

        tx.executeSql(query, [], transactionSuccess, transactionError);

        function transactionSuccess(tx, result) {
            log(`Search trips successfully.`);

            displayList(result.rows);

            $('#page-home #frm-search').trigger('reset');
            $('#page-home #frm-search').popup('close');
        }
    });
}

function displayList(list) {
    let tripList = `<ul id='list-trip' data-role='listview' class='ui-nodisc-icon ui-alt-icon'>`;

    tripList += list.length == 0 ? '<li><h2>There is no trip.</h2></li>' : '';

    for (let trip of list) {
        tripList +=
            `<li><a data-details='{"Id" : ${trip.Id}}'>
                <h6>${trip.Name}</h6>
                <p><small>${trip.DateConverted} - <em>${trip.Destination}</em></small></p>
            </a></li>`;
    }
    tripList += `</ul>`;

    $('#list-trip').empty().append(tripList).listview('refresh').trigger('create');

    log(`Show list of trips successfully.`);
}

function toast(message) {
    $("<div class='ui-loader ui-overlay-shadow ui-body-e ui-corner-all'><p>" + message + "</p></div>")
        .css({
            background: '#EF9175',
            color: 'black',
            font: '12px Arial, sans-serif',
            display: 'block',
            opacity: 1,
            position: 'fixed',
            padding: '2px',
            'border-radius': '25px',
            'text-align': 'center',
            'box-shadow': 'none',
            '-moz-box-shadow': 'none',
            '-webkit-box-shadow': 'none',
            width: '250px',
            left: ($(window).width() - 254) / 2,
            top: $(window).height() - 115
        })

        .appendTo($.mobile.pageContainer).delay(3500)

        .fadeOut(400, function () {
            $(this).remove();
        });
}
