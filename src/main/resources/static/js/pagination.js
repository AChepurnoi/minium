/**
 * Created by Granium on 19.12.16.
 */

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

$(document).ready(function () {
    var query = getUrlParameter("query");
    var page = getUrlParameter("page");
    var originUrl = window.location.href.split('?')[0];
    if (page == undefined) page = 0;
    else page = parseInt(page);
    var prevUrl = originUrl.concat('?page=').concat(page - 1);
    var nextUrl = originUrl.concat('?page=').concat(page + 1);

    if (query != undefined) {
        prevUrl = prevUrl.concat('&query=').concat(query);
        nextUrl = nextUrl.concat('&query=').concat(query);
    }
    $('#prev_page').attr("href", prevUrl);
    $('#next_page').attr("href", nextUrl);

});