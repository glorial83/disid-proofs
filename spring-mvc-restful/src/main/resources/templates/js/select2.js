// Init select simple
jQuery('.dropdown-select-simple').select2({
  debug : false,
  theme : 'bootstrap',
  allowClear : true,
});

// Init select with AJAX search
jQuery('.dropdown-select-ajax').select2(
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
          // options
          // feature that are setted using data-* attributes in each
          // select element
          var idField = this.options
              .get('idField');
          var txtFields = this.options
              .get('textFields');
          var fields = txtFields.split(',');

          // parse the results into the format expected by Select2.
          // The results are inside a Page object, so we have to iterate
          // over the entities in the content attribute.
          var results = [];
          jQuery.each(data.content, function(i,
              entity) {
            var id = entity[idField];
            var text = '';

            // compose the text to be rendered from the specified
            // entity fields
            jQuery.each(fields, function(i,
                fieldName) {
              text = text.concat(' ',
                  entity[fieldName]);
            });

            // Select2 assumes the ID value is in the 'id' field and the
            // shown text is in the 'text' field, so we have to put
            // them in the results object
            var obj = {
              'id' : id,
              'text' : jQuery.trim(text)
            };

            // Put the entity attribute-values in the response array to be able
            // to process them in Select2 events
            jQuery.each(entity, function(key,
                val) {
              var attribute = jQuery
                  .trim(key);
              var value = jQuery.trim(val);
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
    });
