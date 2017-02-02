// IIFE - Immediately Invoked Function Expression
(function(extendDatatables) {

  // The global jQuery object is passed as a parameter
  extendDatatables(window.jQuery, window, document);

}(function($, window, document) {

  // The $ is now locally scoped, it won't collide with other libraries

  // Listen for the jQuery ready event on the document
  // READY EVENT BEGIN
  $(function() {
    // Initialize all datatables in current page
    $('table[data-custom-datatables="true"]').each(function(){
      // Use the advanced extension to auto-configure all
      // advanced features (ajax, export, add, edit, show, delete, etc.)
      // and customize some options 
      $(this).DataTable({
        advanced: {
          buttons: {
            'delete' : null,
            'csv': null,
            'excel': null
          },
          onInitComplete: function(osettings, json) {
            alert("CUSTOM INIT COMPLETE!");
          }
        }
      });
    });

  });

  // READY EVENT END
  //console.log('The DOM may not be ready');

  // The rest of code goes here!
}));
