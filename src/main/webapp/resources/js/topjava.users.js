const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

function enable(chkbox, id) {
    var enabled = chkbox.is(":checked");
    console.log(enabled);
    $.ajax({
        url: userAjaxUrl + id,
        type: "POST",
        data: "enabled=" + enabled
    }).done(function () {
        chkbox.closest("tr").attr("data-user-enabled", enabled);
        successNoty(enabled ? "common.enabled" : "common.disabled");
    }).fail(function () {
        $(chkbox).prop("checked", !enabled);
    });
}

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled",
                    "render": function (data, type, row) {
                        if (type === 'display') {
                            console.log("data after if"  + data);
                            return "<input type='checkbox' " + ($(row.enabled).is(":checked") ? "checked" : "") + " onclick='enable($(this)," + row.DT_RowId + ");'/>";
                        }
                        console.log("data before return" + data);
                        return data;
                    }
                },
                {
                    "data": "registered"
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
            /*"createdRow": function (row, data, dataIndex) {
                console.log("data enabled" + data.enabled);
                console.log("row enabled" + row.enabled);
                if (!data.enabled) {
                    $(row).attr("data-user-enabled", false);
                }
            }*/
        })
    );
});