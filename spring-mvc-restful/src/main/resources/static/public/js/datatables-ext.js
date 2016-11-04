// IIFE - Immediately Invoked Function Expression
(function(extendDatatables) {

  // The global jQuery object is passed as a parameter
  extendDatatables(window.jQuery, window, document);

}(function($, window, document) {

  // The $ is now locally scoped, it won't collide with other libraries 

  // Listen for the jQuery ready event on the document
  // READY EVENT BEGIN
  $(function() {
   
    // The DOM is ready!
    //console.log('The DOM is ready');
       
      // Configure the datatables rendering
  	$.extend( $.fn.dataTable.ext.classes, {
  	  "sFilter": "dataTables_filter col-sm-6",
  	  "sInfo": "dataTables_info col-sm-6",
  	  "sPaging": "dataTables_paginate col-sm-6 paging_", /* Note that the type is postfixed */
  	  "sProcessing": "dataTables_processing progress-bar progress-bar-striped active"
  	});
     
    // Add a create row button 
    $.extend($.fn.dataTable.ext.buttons, {
      'add': createButton
    });
              
  	$.extend(  $.fn.dataTable, {
  	  'renderTools': renderTools
  	});

    // Set datatables defaults
    $.extend($.fn.dataTable.defaults, {
      'ajax': loadData,
      'fnInitComplete': saveSelectedRowToState,
      'stateSaveParams': loadFromState,
      'buttons': {
        'dom': {
          'container': {
            'className': 'dt-buttons btn-group col-sm-6'
          }
        },
        'buttons': [
          'add', {
            'extend': 'colvis',
            'className': 'btn-action',
          }, {
            'extend': 'pageLength',
            'className': 'btn-action',
          },
        ]
      },
      'columnDefs': [
        {
          'targets': 'dttools', // First column from the right
          'render': {
       	    'display': $.fn.dataTable.renderTools
          }
        },
        {
          'targets': '_all',
          'render': {
            'display': $.fn.dataTable.render.text()
          }
        }
      ],
      'deferRender': true,
      'dom': 'Bfrtip',
      'processing': true,
      'responsive': true,
      'retrieve': true,
      'serverSide': true,
      'stateSave': true
    });
   
    // Initialize all datatables in current page
    $('table[data-datatables="true"]').each(function(){
      var tableId = $(this).attr('id');
      console.log('Initializing datatables with id ' + tableId);
      var datatables = $(this).DataTable();
      registerEvents(datatables);
    });
	   	
  	///////////////////////
  	// Private functions
  	///////////////////////
  	
  	/**
  	 * Creates a new button in the buttons plugin toolbar to create a new row
  	 * using the value of the table tag attribute 'data-create-url-function' as
  	 * a function which returns the URL or, if it is not defined, the value of
  	 * the table tag attribute 'data-create-url' to be used as the URL.
  	 */
  	function createButton(datatables, conf) {
  	  return {
  	    'action': function(e, datatables, node, config) {
  	      var url = getCreateUrl(datatables);
  	      if (url) {
  	        location.href = url;
  	      }
  	    },
  	    'className': 'btn-action add',
  	    'text': datatables.i18n('buttons.add', 'Add')
  	  };
  	};
  
  	/**
  	 * Generates and executes an ajax request whose goal is to load data for a 
  	 * DataTable element.
  	 *
  	 * @param data DataTable object data
  	 * @param callback Name of the function to call with the server data obtained 
  	 * 			once the ajax request has been completed
  	 * @param settings DataTable object options
  	 */
  	function loadData(data, callback, settings) {
  	  var datatables = this.DataTable();
  	  
  	  console.log('Loading data for datatables: ' + datatables);
  	  
  	  var url = getLoadUrl(datatables);
  	  var validUrl = false;
  	  if (url) {
  	    
  	    // If it is a detail table, we have to get the parent id from 
  	    // the selected row in the parent table, and replace the
  	    // _PARENT_ID_ variable in the given URL. 
  	    var parentDatatables = getParentDatatables(datatables);
  	    if (parentDatatables) {
  	      var selected = parentDatatables.row({selected: true});
  	    
  	      if (selected.any()) {
  	        var parentId = selected.data().id;
  	        if (parentId) {
  	          url = url.replace('_PARENT_ID_', parentId);
  	          validUrl = true;
  	        }
  	      }        
  	    } else {
  	      validUrl = true;	
  	    }
  	  }
  	    
  	  if (validUrl) {
  	    loadDataFromUrl(datatables, data, callback, url);
  	  } else {
  		callback(emptyData(data.draw));
  	  }
  	}
  	
  	/**
  	 * Generates and executes an ajax request whose goal is to load data for a 
  	 * DataTable element.
  	 *
  	 * @param datatables DataTable on which the calling should act upon
  	 * @param data DataTable object data
  	 * @param callback Name of the function to call with the server data obtained 
  	 * 			once the ajax request has been completed
  	 * @param url Url to use for ajax request
  	 */
  	function loadDataFromUrl(datatables, data, callback, url) {
  	  var $token = $("meta[name='_csrf']");
  	  var $header = $("meta[name='_csrf_header']");
  	  
  	  $.ajax({
  	    url : url,
  	    type : 'GET',
  	    data : data,
  	    dataType : 'json',
  	    headers : {
  	      Accept : "application/vnd.datatables+json",
  	    },
  	    beforeSend: function (request) {
  	  	  if($token != null && $token.length > 0 && $header != null && $header.length > 0) {
  	        request.setRequestHeader($header.attr("content"), $token.attr("content"));
  	  	  }
  	    }
  	  })
  	  .done(function(result) {
  		callback(result);
  	    if(datatables.state.loaded()){
  	        var rowSelectedId = datatables.state.loaded().rowSelectedId;
  	      if(rowSelectedId){
  	        var rowSelected = datatables.row('#' + rowSelectedId);
  	        if(rowSelected.length > 0){
  	          rowSelected.select();
  	        }
  	      }
  	    }
  	  })
  	  .fail(function(jqXHR, status) {
  	    $('#datatablesErrorModal').modal('show');
  	    callback(emptyData(data.draw));
  	  });
  	}
  	
  	/**
  	 * Returns the parent datatables whose id is given through
  	 * the 'data-parent-table' attribute.
  	 */
  	function getParentDatatables(datatables) {
  	  var parentTableId = getParentTableId(datatables);
  	  console.log('parentTableId = ' + parentTableId)
  	  if (parentTableId) {
  	    return $(parentTableId).DataTable();
  	  }
  	}
  	
  	/**
  	 * Returns the parent table id when this datatables is a detail.
  	 */
  	function getParentTableId(datatables) {
  	  var $jQueryTable = jQueryTable(datatables);
  	  var parentTableId = getDataValue(datatables, 'parent-table');
  	  if (parentTableId) {
  		return "#" + parentTableId;
  	  }
  	}		
  	
  	/**
  	 * Deletes the element whose id is the one in the datatables
  	 * row whose _delete_ button has been selected, and the
  	 * the opened modal confirmacion has been accepted 
  	 * (see modal-confirm-delete.html)
  	 * @param datatables DataTable on which the calling should act upon
  	 */
  	function deleteElement(datatables) {
  	  var tableId = getTableId(datatables);
  	  var rowId = $('#' + tableId + 'DeleteRowId').data('row-id');
  	  var url = getDeleteUrl(datatables, rowId);
  	    	    
  	  $.ajax({
  	     url: url,
  	     type: 'DELETE'
  	  })
  	  .done(function(result) {
  	    var $deleteSuccess = $('#' + tableId + 'DeleteSuccess');
  	    $deleteSuccess.modal();
  		  datatables.ajax.reload(); // Refresh Datatables
  	  })
  	  .fail(function(jqXHR, status) {
  	    var $deleteError = $('#' + tableId + 'DeleteError');
  	    $deleteError.modal();
  	  });
  	}
  	
  	/**
  	 * Returns the URL to load the data for a Datatables. The value
  	 * is defined through a 'data-load-url' attribute in the 
  	 * Datatables table tag.
  	 * @param datatables DataTable on which the calling should act upon
  	 */
  	function getLoadUrl(datatables) {
  	  return getDataValue(datatables, 'load-url');
  	}
  	
  	/**
  	 * Returns the URL to create a new element for the Datatables.
  	 * The value is defined in the Datatables table tag with a 
  	 * 'data-create-url-function' as a function which returns the URL
  	 * or, if it is not defined, the value of the attribute
  	 * 'data-create-url' to be used as the URL.
  	 *
  	 * @param datatables DataTable on which the calling should act upon
  	 */
  	function getCreateUrl(datatables) {
  	  var urlFunction = getDataValue(datatables, 'create-url-function');
  	  return urlFunction ? $[urlFunction]() : getDataValue(datatables, 'create-url');
  	}
  			
  	/**
  	 * Returns the URL to show the details of an element of the Datatables.
  	 * The value is defined in the Datatables table tag with a 
  	 * 'data-show-url' as the URL to use. 
  	 * The URL contains the text *_ID_* in the place where
  	 * the selected element Id has to be inserted.
  	 *
  	 * @param datatables DataTable on which the calling should act upon
  	 * @param id identifier of the element to edit
  	 */
  	function getShowUrl(datatables, id) {
  	  var url = getDataValue(datatables, 'show-url');
  	  return url.replace('_ID_', id);
  	}
  	
  	/**
  	 * Returns the URL to edit an element of the Datatables.
  	 * The value is defined in the Datatables table tag with a 
  	 * 'data-edit-url' as the URL to use. 
  	 * The URL contains the text *_ID_* in the place where
  	 * the selected element Id has to be inserted.
  	 *
  	 * @param datatables DataTable on which the calling should act upon
  	 * @param id identifier of the element to edit
  	 */
  	function getEditUrl(datatables, id) {
  	  var url = getDataValue(datatables, 'edit-url');
  	  return url.replace('_ID_', id);
  	}
  	
  	/**
  	 * Returns the URL to remove an element of the Datatables.
  	 * The value is defined in the Datatables table tag with a 
  	 * 'data-delete-url' as the URL to use. 
  	 * The URL contains the text *_ID_* in the place where
  	 * the selected element Id has to be inserted.
  	 *
  	 * @param datatables DataTable on which the calling should act upon
  	 * @param id identifier of the element to remove
  	 */
  	function getDeleteUrl(datatables, id) {
  	  var url = getDataValue(datatables, 'delete-url');
  	  return url.replace('_ID_', id);
  	}
  	
  	/**
  	 * Returns the 'data-name' attribute value of a datatables.
  	 * @param datatables DataTable on which the calling should act upon
  	 * @param name the name of the data attribute to return the value of
  	 */
  	function getDataValue(datatables, name) {
  	  var $dt = jQueryTable(datatables);
  	  return $dt.data(name);
  	}
  	
  	/**
  	 * Returns the jQuery object for the given datatables element.
  	 */
  	function jQueryTable(datatables) {
  	  return $(datatables.table().node());
  	}
  	
  	/**
  	 * Returns the table id attribute for the given datatables element.
  	 */
  	function getTableId(datatables) {
  	  var $jQueryTable = jQueryTable(datatables);
  	  return $jQueryTable.attr('id');
  	}
  	
  	/**
  	 * Generates a JSON object with the necessary data for indicating a 
  	 * DataTables object that 0 elements have been found.
  	 * This is used for details related tables, when a parent table
  	 * row is not selected.
  	 *
  	 * @param draw DataTables request counter
  	 * @returns {json} JSON object with empty data 
  	 */
  	function emptyData(draw) {
  	  return {
  	    'data' : new Array(),
  	    'draw' : draw,
  	    'error' : null,
  	    'recordsFiltered' : '0',
  	    'recordsTotal' : '0'
  	  };
  	}
  	
  	/**
  	 * If a row is selected, store it in the persisted table state
  	 * so if the user goes to another page and returns, the current
  	 * selected row is still selected.
  	 * @param oSettings DataTable object options
  	 * @param json
  	 */
  	function saveSelectedRowToState(oSettings, json) {
  	  var datatables = this.DataTable();
  	  var state = datatables.state;
  	  datatables.on('select', function(e, dt, type, indexes) {
  		if (type === 'row') {
  		  var rowSelectedId = datatables.rows(indexes).ids()[0];
  		  state.loaded().rowSelectedId = rowSelectedId;
  		  state.save();
  		}
  	  });
  	  if (!state.loaded()) {
  	    oSettings.oLoadedState = datatables.state();
  	  }
  	}
  	
  	/**
  	 * Loads a previously persisted datatables state.
  	 * @param settings DataTable object options
  	 * @param data DataTable object data
  	 */
  	function loadFromState(settings, data) {
  	  var datatables = this.DataTable();
  	  loadSelectedRowFromState(datatables, data);
  	}
  	
  	/**
  	 * Loads a previously selected row id from the persisted state.
  	 * @param settings DataTable object options
  	 * @param data DataTable object data
  	 */
  	function loadSelectedRowFromState(datatables, data) {
  	  var state = datatables.state;
  	  if (state.loaded()) {
  	    var rowSelectedId = state.loaded().rowSelectedId;
  	    if (rowSelectedId) {
  	      data.rowSelectedId = rowSelectedId;
  	    }
  	  }
  	}
  	
  	/**
  	 * Registers events for the given datatables
  	 */
  	function registerEvents(datatables) {
  	  console.log("Registering events for datatables: " + datatables);
  	  registerDeleteModalEvents(datatables);
  	  registerToParentEvents(datatables);
  	}
  	
  	/**
  	 * Registers the events related to the delete modals, so the
  	 * modal knows the id of the row to delete.
  	 */
  	function registerDeleteModalEvents(datatables) {
  	  var tableId = getTableId(datatables);
  	  var $deleteConfirm = $('#' + tableId + 'DeleteConfirm');
  	  
  	  // When the delete element modal is opened, copy the current
  	  // element id to be deleted to the 'TABLE_ID + DeleteRowId'
  	  // element
  	  $deleteConfirm.on('show.bs.modal', function(e) {
  	    // Get data-row-id attribute of the clicked element
  	    var rowId = jQuery(e.relatedTarget).data('row-id');
  	    // Populate the row-id data attribute in the modal 
  	    $('#' + tableId + 'DeleteRowId').data('row-id', rowId)
  	  });
  	
  	  $('#' + tableId + 'DeleteButton').on('click', function() {
  	 	deleteElement(datatables);
  	  });
  	}
  	
  	/**
  	 * When a table is linked to parent table, for a master detail list
  	 * for example, it registers the row selection events in the parent
  	 * table to update the data in the child table.
  	 */
  	function registerToParentEvents(datatables) {
  	  var parentDatatables = getParentDatatables(datatables);
  		
  	  if (parentDatatables) {
  	    // Register to de/select events
  	  	parentDatatables.on('select', function () {
  	      datatables.ajax.reload();
  	  	});
  	 
  	    parentDatatables.on('deselect', function () {
  	      datatables.ajax.reload();
  	    });
  	    
  	    // Register to reload finished event, needed when the selected row has
  	    // been deleted in the parent table or any other change
        parentDatatables.on('xhr.dt', function () {
          datatables.ajax.reload();
        });  	    
  	  }
  	}
    
  	/**
  	 * Renders the tools column, with the buttons to perform operations
  	 * on the table rows.
  	 */
    function renderTools( data, type, full, meta ) {
      var datatables = new $.fn.dataTable.Api(meta.settings);
      var tableId = getTableId(datatables);
      return '<a role="button" class="btn-action showInfo" href="' + getShowUrl(datatables, data) + '" ></a>' +
             '<a role="button" class="btn-action edit" href="' + getEditUrl(datatables, data) + '"></a>' +
             '<a role="button" class="btn-action delete" data-toggle="modal" data-target="#' + tableId + 'DeleteConfirm" data-row-id="'+ data +'"/>'
    }
    
  });
   
  // READY EVENT END
  //console.log('The DOM may not be ready');
   
  // The rest of code goes here!
}));
