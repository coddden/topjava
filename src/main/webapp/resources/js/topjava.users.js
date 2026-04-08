const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

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
                    "data": "enabled"
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
        })
    );
});

window.enable = function(id, checkbox, name) {
    let enabled = checkbox.checked;
    checkbox.checked = !enabled;
    let row = $("#" + id);
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl + id + "/enable",
        data: { enabled: enabled }
    }).done(function () {
        checkbox.checked = enabled;
        row.toggleClass("text-muted", !enabled);
        successNoty(name + " is " + (enabled ? "active" : "inactive"));
    }).fail(function () {
        row.toggleClass("text-muted", !(checkbox.checked));
    });
}