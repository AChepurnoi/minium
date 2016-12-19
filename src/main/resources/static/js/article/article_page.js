/**
 * Created by Granium on 10.12.16.
 */



$(document).ready(function () {
    $('#comment').click(function () {
        var url = window.location.href;
        var commentUrl = url.concat("/comment");
        $.ajax({
            type: 'POST',
            url: commentUrl,
            data: $("#comment-form").serialize(),
            success: function (data) {
                window.location.href = url

            }

        });
    });
    var csrf = $('#csrf').text();

    $('.delete-comment-btn').click(function () {
        var url = window.location.href;
        var id = $(this).data('id');
        var commentUrl = url.concat('/comment/').concat(id);
        $.ajax({
            type: 'DELETE',
            url: commentUrl,
            headers: {
                'X-CSRF-TOKEN': csrf
            },
            success: function (data) {
                window.location.href = url

            }

        });
    });

    $('.delete-article-btn').click(function () {
        var url = window.location.href;
        var id = $(this).data('id');
        var commentUrl = url.concat('/delete');
        $.ajax({
            type: 'DELETE',
            url: commentUrl,
            headers: {
                'X-CSRF-TOKEN': csrf
            },
            success: function (data) {
                window.location.href = "/article"

            }

        });
    });

    $('.like-article-btn').click(function () {
        var url = window.location.href;
        var id = $(this).data('id');
        var likeUrl = url.concat("/like");
        $.ajax({
            type: 'POST',
            url: likeUrl,
            headers: {
                'X-CSRF-TOKEN': csrf
            },
            success: function (data) {
                window.location.href = url

            }

        });
    });
    
    
});