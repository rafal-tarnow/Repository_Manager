$(function () {

    $('.jstree').jstree({
        "core" : {
            "themes" : { "stripes" : true }
        },
        "types" : {
            "default" : {
                "icon" : false
            }
        },
        "plugins" : [
            "search",
            "types",
            "wholerow"
        ]
    });
    $('.jstree').on("changed.jstree", function (e, data) {
        console.log(data.selected);
    });

    $('#search').keyup(function(){
        $('.jstree').jstree('search', $(this).val());
    });
});
