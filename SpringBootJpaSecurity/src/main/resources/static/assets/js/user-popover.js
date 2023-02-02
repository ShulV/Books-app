$( function( )
{
    $("[data-toggle='popover']").popover( );
} );

$( ".popover-dismiss" ).popover(
    {
        trigger: "focus"
    } );

$(".user-info").click(() => {
    $(".popover-child").toggleClass('is-hidden', 'is-hidden')
});