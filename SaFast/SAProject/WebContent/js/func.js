function byId(id){
	return document.getElementById(id);
}

function replaceAll(target, search, replacement) {
    return target.split(search).join(replacement);
}

function fireEvent(obj,evt){
	
	var fireOnThis = obj;
	if( document.createEvent ) {
	  var evObj = document.createEvent('MouseEvents');
	  evObj.initEvent( evt, true, false );
	  fireOnThis.dispatchEvent(evObj);
	} else if( document.createEventObject ) {
	  fireOnThis.fireEvent('on'+evt);
	}
}

function viewAllegati(id, path){
	var itm = "id_"+id;
	var progressivo = byId(itm).value;
	$.ajax(path+"/AllegatiScheda?progressivo="+progressivo+"&scarica=0" )
	  .done(function(res) {
		 $("<div>"+res+"</div>").dialog({
		      modal: true,
		      position: { my: 'top', at: 'top+150' },
		      width:600,
		      height:300,
		      title:"Elenco Allegati"
		  });
	  })
	  .fail(function(res) {
	 //   alert( "error" );
	  });
}

function downloadAllegati(idallegati, path){
	$.ajax(path+"/AllegatiScheda?idallegati="+idallegati+"&scarica=1" )
	  .done(function(res) {
		  fireEvent(document.getElementById(res),'click');
	  })
	  .fail(function(res) {
	 //   alert( "error" );
	  });
}

function openCloseScheda(id, path){
	var itm = "testo_"+id;
	var numsch = byId("numscheda_"+id).innerHTML;
	$imp = $("<img src='css/images/star_red.png'/>");
	
	var important = byId("important_"+id).innerHTML;
	
	if(important!=null && important!=""){
		 $( "#"+itm ).dialog({
		      modal: true,
		      position: { my: 'top', at: 'top+150' },
		      width:800,
		      height:600,
		      title:"Scheda: "+numsch,
		      buttons: {
		    	  	Normale: function() {
			    	  	$.ajax(path+"/ImportantScheda?numsch="+numsch+"&type=normal" )
			    	  	  .done(function() {
			    	  		caricaSchede();
			    	  		$("#"+itm).dialog("close");
			    	  		 openCloseScheda(id, path);
			    	  	  })
			    	  	  .fail(function() {
			    	  	  });
			        },
			        Modifica: function() {
			        	$.ajax(path+"/ModificaScheda?numsch="+numsch+"&chiudi=0" )
			    	  	  .done(function(el) {
			    	  		$diaMod = $("<div id='diaMod'>"+el+"</div>");  
			    	  		$("body").append($diaMod);
			    	  		$( "#diaMod" ).dialog({
					      	      modal: true,
					      	      position: { my: 'top', at: 'top+150' },
					      	      width:800,
					      	      height:600,
					      	      title:"Scheda: "+numsch,
					      	      buttons: {
					      	    	  "Chiudi":function(){
					      	    		var form = byId("chiudiScheda");
					      	    		var numScheda = byId("numscheda").value;
					      	    		var info = byId("info").value;
					      	    		var ore = byId("ore").value;
					      	    		var testo = byId("testo").value;
					      	    		
					      	    		info = replaceAll(info,"\n","<br>");
					      	    		testo = replaceAll(testo,"\n","<br>");
					      	    		
					      	    		$.ajax(path+"/ChiudiScheda?numsch="+numScheda+"&info="+info+"&ore="+ore+"&testo="+testo+"&chiudi=1" )
							    	  	  .done(function(el) {							    	  	
							    	  		$("#diaMod").dialog( "close" );
							    	  		caricaSchede();
							    	  	  })
								    	  .fail(function() {
									      });
					      	    		
					      	    	  },
					      	    	"Salva":function(){
					      	    		var form = byId("chiudiScheda");
					      	    		var numScheda = byId("numscheda").value;
					      	    		var info = byId("info").value;
					      	    		var ore = byId("ore").value;
					      	    		var testo = byId("testo").value;
					      	    		
					      	    		info = replaceAll(info,"\n","<br>");
					      	    		testo = replaceAll(testo,"\n","<br>");
					      	    		
					      	    		$.ajax(path+"/ChiudiScheda?numsch="+numScheda+"&info="+info+"&ore="+ore+"&testo="+testo+"&chiudi=0" )
							    	  	  .done(function(el) {
							    	  		  $("#diaMod").dialog("close");
							    	  	  })
								    	  .fail(function() {
									      });
					      	    		
					      	    	  },
					      	    	"Esci":{
					      	    		text 	: "Esci",
					      	    		id 		: "esci",
					      	    		click	: function(){		
					      	    			caricaSchede();
							    	  		$( "#diaMod" ).dialog( "close" );
							    	  		
					      	    		}
					      	    	}
					      	      }
					        	});
			    	  	  })
			    	  	  .fail(function() {
			    	  	 //   alert( "error" );
			    	  	  });
			        	  $("#"+itm).dialog("close");
			        	
				    },
			        Ok: function() {
			        	  $("#"+itm).dialog("close");
			        }
		      }
		    });
	}else{
	
	    $( "#"+itm ).dialog({
	      modal: true,
	      position: { my: 'top', at: 'top+150' },
	      width:800,
	      height:600,
	      title:"Scheda: "+numsch,
	      buttons: {
	    	  	Importante: function() {
		    	  	$.ajax(path+"/ImportantScheda?numsch="+numsch+"&type=important" )
		    	  	  .done(function() {
		    	  		caricaSchede();
		    	  		$("#"+itm).dialog("close");
		    	  		 openCloseScheda(id, path);
		    	  	  })
		    	  	  .fail(function() {
		    	  	 //   alert( "error" );
		    	  	  });
		        },
		        Modifica: function() {
		        	$.ajax(path+"/ModificaScheda?numsch="+numsch+"&chiudi=0" )
		    	  	  .done(function(el) {
		    	  		$diaMod = $("<div id='diaMod'>"+el+"</div>");  
		    	  		$("body").append($diaMod);
		    	  		$( "#diaMod" ).dialog({
				      	      modal: true,
				      	      position: { my: 'top', at: 'top+150' },
				      	      width:800,
				      	      height:600,
				      	      title:"Scheda: "+numsch,
				      	      buttons: {
				      	    	  "Chiudi":function(){
				      	    		var form = byId("chiudiScheda");
				      	    		var numScheda = byId("numscheda").value;
				      	    		var info = byId("info").value;
				      	    		var ore = byId("ore").value;
				      	    		var testo = byId("testo").value;
				      	    		
				      	    		info = replaceAll(info,"\n","<br>");
				      	    		testo = replaceAll(testo,"\n","<br>");
				      	    		
				      	    		$.ajax(path+"/ChiudiScheda?numsch="+numScheda+"&info="+info+"&ore="+ore+"&testo="+testo+"&chiudi=1" )
						    	  	  .done(function(el) {							    	  	
						    	  		$("#diaMod").dialog( "close" );
						    	  		caricaSchede();
						    	  	  })
							    	  .fail(function() {
								      });
				      	    		
				      	    	  },
				      	    	"Salva":function(){
				      	    		var form = byId("chiudiScheda");
				      	    		var numScheda = byId("numscheda").value;
				      	    		var info = byId("info").value;
				      	    		var ore = byId("ore").value;
				      	    		var testo = byId("testo").value;
				      	    		
				      	    		info = replaceAll(info,"\n","<br>");
				      	    		testo = replaceAll(testo,"\n","<br>");
				      	    		
				      	    		$.ajax(path+"/ChiudiScheda?numsch="+numScheda+"&info="+info+"&ore="+ore+"&testo="+testo+"&chiudi=0" )
						    	  	  .done(function(el) {
						    	  		$("#diaMod").dialog( "close" );
						    	  	  })
							    	  .fail(function() {
								      });
				      	    		
				      	    	  },
				      	    	"Esci":{
				      	    		text 	: "Esci",
				      	    		id 		: "esci",
				      	    		click	: function(){		
				      	    			caricaSchede();
				      	    			$("#diaMod").dialog( "close" );
						    	  		
				      	    		}
				      	    	}
				      	      }
				        	});
		    	  	  })
		    	  	  .fail(function() {
		    	  	 //   alert( "error" );
		    	  	  });
		        	  $("#"+itm).dialog("close");
		        	
			    },
		        Ok: function() {
		        	  $("#"+itm).dialog("close");
		        }
	      }
	    });
	}
}




