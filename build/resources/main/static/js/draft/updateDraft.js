/**
 * Created by Granium on 09.12.16.
 */

$(document).ready(function(){
    $('#publish').click(function(){
        $.ajax({
            type: 'POST',
            url: '/draft/publish',
            data: $("#form").serialize(),
            success: function(data){
                window.location.href = "/draft"
            }

        });
    })
});