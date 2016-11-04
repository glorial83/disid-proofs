// IIFE - Immediately Invoked Function Expression
(function(extendSelect2) {

  // The global jQuery object is passed as a parameter
  extendSelect2(window.jQuery, window, document);

}(function($, window, document) {

  // The $ is now locally scoped, it won't collide with other libraries 

  // Listen for the jQuery ready event on the document
  // READY EVENT BEGIN
  $(function() {
   
    // The DOM is ready!
    //console.log('The DOM is ready');

    // Init select simple
    $('.dropdown-select-simple').select2({
      debug : false,
      theme : 'bootstrap',
      allowClear : true,
    });

    // Init select with AJAX search
    $('.dropdown-select-ajax').select2(
      {
        debug : false,
        theme : 'bootstrap',
        allowClear : true,
        ajax : {
          data : function(params) {
            // set search params names to match with GlobalSearch and
            // Pageable arguments
            var query = {
              'search[value]' : params.term,
              'page' : params.page - 1,
            }
            return query;
          },
          // parse the results into the format expected by Select2.
          processResults : function(data, page) {

            // entity attribute names are specified using the Select2
            // options feature that are setted using data-* attributes in each
            // select element
            var idField = this.options.get('idField');
            var txtFields = this.options.get('textFields');
            var fields = txtFields.split(',');

            // parse the results into the format expected by Select2.
            // The results are inside a Page object, so we have to iterate
            // over the entities in the content attribute.
            var results = [];
            $.each(data.content, function(i,
                entity) {
              var id = entity[idField];
              var text = '';

              // compose the text to be rendered from the specified
              // entity fields
              $.each(fields, function(i,
                  fieldName) {
                text = text.concat(' ',
                    entity[fieldName]);
              });

              // Select2 assumes the ID value is in the 'id' field and the
              // shown text is in the 'text' field, so we have to put
              // them in the results object
              var obj = {
                'id' : id,
                'text' : $.trim(text)
              };

              // Put the entity attribute-values in the response array to be able
              // to process them in Select2 events
              $.each(entity, function(key,
                  val) {
                var attribute = $.trim(key);
                var value = $.trim(val);
                obj[attribute] = value;
              });

              // Set the processed object as the request results
              results.push(obj);
            });

            // calc page info
            var morePages = !data.last;

            return {
              results : results,
              pagination : {
                more : morePages
              }
            };
          },
        },
      }
    );    
  });
   
  // READY EVENT END
  //console.log('The DOM may not be ready');
   
  // The rest of code goes here!
}));

