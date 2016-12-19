/**
 * Created by Granium on 15.12.16.
 */

$(document).ready(function(){
    var csrf = $('#csrf').text();
    $('#follow-btn').click(function(){
        var username = $('#user-page-username').text();
        var url = '/user/'.concat(username);
        var target = url + '/follow';
        $.ajax({
            type: 'POST',
            headers: {
                'X-CSRF-TOKEN': csrf
            },
            url: target,
            success: function(data){
                window.location.href = url
            }

        });
    })
});