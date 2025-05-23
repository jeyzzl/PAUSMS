/*
 * 
 * 	By David Blanco
 * 
 */

(function($) {

    var Search = function(element, options){
        //Defaults are below
        var settings = $.extend({}, $.fn.search.defaults, options);
        
        
        //Some vars
        var input   = $(element),
        table 	    = settings.table,
        thead 		= table.children('thead'),
        th    		= thead.find('th'),
		tbody 		= table.children('tbody'),
		tr          = tbody.children('tr'),
        select;
        
        
        //ADD THE COLUMN FILTER
        if(settings.columns){
            var columns = '<option>Todo</option>';
            th.each(function(){
                columns += '<option>'+$(this).text()+'</option>';
            });

        	select = $('<select style="margin: 0 8px 0 0;" class="input-medium"> '+columns+' </select>');
        	select.insertBefore(input);
        }


        //START THE SEARCH
        var startSearching = function(){
            var value = input.val();
            var selectedColumn = select!=undefined?select.find(':selected').index()-1:undefined; // -1 because of the extra option 'Todo'

            tr.each(function(){
                $this = $(this);
                var encontro = false;



                var searching = $this.find('td');
                
                if(selectedColumn != undefined && selectedColumn != -1){//if there's a selected column and if is not the 'Todo' one
                    searching = $this.find('td').eq(selectedColumn); //only search in the index of the selected column
                }

                searching.each(function(){
                    if( $(this).html().toLowerCase().indexOf(value.toLowerCase()) !== -1 ){
                        encontro = true;
                    }
                });



                if(!encontro){
                    $this.hide();
                }else{
                    $this.show();
                }
            });
        }
        

        //What trigger the search
		input.keyup(function(){
			startSearching();
		});

        if(select != undefined){
            select.on('change', function(){
                startSearching();
            });
        }






        return this;

    };//END


        
    $.fn.search = function(options) {
        return this.each(function(key, value){
            var element = $(this);
            // Return early if this element already has a plugin instance
            if (element.data('search')) return element.data('search');
            // Pass options to plugin constructor
            var search = new Search(this, options);
            // Store plugin object in this element's data
            element.data('search', search);
        });
    };
  
    //Default settings
    $.fn.search.defaults = {
        table: $('#table'),
        columns: false,
    };
    
    $.fn._reverse = [].reverse;
  
})(jQuery);