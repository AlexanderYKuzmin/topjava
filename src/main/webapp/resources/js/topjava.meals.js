const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl
};

function filter() {
   /* $.get(ctx.ajaxUrl + "filter", function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });*/
    $.ajax({
        type : "Get",
        url : ctx.ajaxUrl + "filter",
        data : $("#filter").serialize()
    }).done(updateTableWithFilteredData);
}

function clearFilter() {
    $("#filter")[0].reset();
}

function updateTableWithFilteredData(data) {
    console.log(data);
    ctx.datatableApi.clear().rows.add(data).draw();
}
// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});

let startDate = $('startDate');
let endDate = $('endDate');

startDate.datetimepicker({
    timepicker: false,
    format: 'Y-m-d',
    formatDate: 'Y-m-d',
});

endDate.datetimepicker({
    timepicker: false,
    format: 'Y-m-d',
    formatDate: 'Y-m-d',
});

let startTime = $('startTime');
let endTime = $('endTime');

startTime.datetimepicker({
    datepicker: false,
    format: 'H:m'
})

endDate.datetimepicker({
    datepicker: false,
    format: 'H:m'
})