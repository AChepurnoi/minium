/**
 * Created by Granium on 11.12.16.
 */


$(document).ready(function () {
    var csrf = $('#csrf').text();
    var csrfParam = $('#csrf').attr('name');

    $('.delete-btn').click(function () {
        var id = $(this).data('id');
        var url = '/draft/'.concat(id).concat('/delete');
        var data = {};
        data[csrfParam] = csrf;
        $.ajax({
            type: 'POST',
            url: url,
            data: data,
            success: function (data) {
                window.location.href = "/draft"
            }

        });
    })
});