<%@ page import="java.io.*" %>
<%@ page import="java.util.List" %>
<%@ page import="aca.pg.archivo.spring.PosArchGeneral" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	if(request.getParameter("Codigo")!=null) session.setAttribute("codigoAlumno", request.getParameter("Codigo"));
	String codigoPersonal		= request.getParameter("codigoAlumno");
	String codigoUsuario 		= (String) session.getAttribute("codigoPersonal");
	String folio				= "0";	
	String alumnoNombre 		= (String) request.getAttribute("alumnoNombre");	
	
	if(codigoPersonal == null)
		codigoPersonal = (String) session.getAttribute("codigoAlumno");
	else
		session.setAttribute("codigoAlumno", codigoPersonal);
	
	List<PosArchGeneral> lisDocumentos	= (List<PosArchGeneral>) request.getAttribute("lisDocumentos");
%>
<head>
<script type="text/javascript">
	function inicio(){
		$("fotos").style.height = (document.viewport.getHeight()-60)+"px";
	}
	
	function muestraFoto(obj){
		var obj = $(obj);
		var visor = $("visor");
		var panel = $("panel");
		var folio = obj.id.substring(6);
		
		visor.innerHTML = '&nbsp;';
		panel.innerHTML = '&nbsp;';
		
		var visorW = visor.getWidth(), visorH = visor.getHeight();		
		/*visor.insert('<img title="Eliminar" src="../../imagenes/no.png" class="btn btn-danger" style="position: absolute;" title="Eliminar Imagen" onclick="borrarImagen('+folio+');" nosave /><br><br><img id="preview" src="'+obj.src+'" width="90%" />');*/
		visor.insert('<a href="javascript:borrarImagen('+folio+');" class="btn btn-danger" title="Delete"/><b>X</b></a><br><img id="preview" src="'+obj.src+'" width="90%" />');
		var preview = $("preview");
		while(preview.getWidth() > (visorW - 40) || preview.getHeight() > (visorH - 10)){
			preview.style.width = (preview.getWidth()-10)+"px"; 
		}
		
		muestraDocumentos(folio);
	}
	
	function muestraDocumentos(folio){
		var url = "verDocumentos?Folio="+folio;
		
		new Ajax.Request(url, {
			method: 'get',
			onSuccess: function(req){
				$("panel").innerHTML = req.responseText;
			},
			onFailure: function(req){
				alert("An error occurred while requesting the document panel.\n Click on the image to try again.");
			}
		});
	}
	
	function muestraStatus(documentoId, folio){
		var url = "verStatus?DocumentoId="+documentoId+"&Folio="+folio;
						  
		$("statusBox").innerHTML = '';
		$("numHojaBox").innerHTML = '';
		
		new Ajax.Request(url, {
			method: 'get',
			onSuccess: function(req){
				$("statusBox").innerHTML = req.responseText;
			},
			onFailure: function(req){
				alert("An error occurred when requesting the status panel.\nClick on the Document and try again");
			}
		});
	}
	
	function muestraNumHojas(iddocumento, idstatus, folio){
		var url = "verHojas?DocumentoId="+iddocumento+ "&StatusId="+idstatus+"&Folio="+folio;
				  
		$("numHojaBox").innerHTML = '';
		
		new Ajax.Request(url, {
			method: 'get',
			onSuccess: function(req){
				$("numHojaBox").innerHTML = req.responseText;
			},
			onFailure: function(req){
				alert("An error occurred when requesting the page number panel.\nClick on the Status and try again");
			}
		});
	}
	
	//Esta funcion ya manda guardar el documento en su respectivo lugar
	function asignarDocument(iddocumento, idstatus, folio){
		var url = "asignar?iddocumento="+iddocumento+"&idstatus="+idstatus+"&folio="+folio+"&hoja="+$("hoja").value+"&origen="+$("origen").value;
		
		new Ajax.Request(url, {
			method: 'get',
			onSuccess: function(req){
				eval(req.responseText);
			},
			onFailure: function(req){
				alert("An error occurred while requesting Assign Document.\nTry assigning it again. If the error persists, contact system support.");
			}
		});
	}
	
	function borrarImagen(folio){
		if(confirm("Are you sure you want to delete the general image?")){		
			var url = "borrar?Folio="+folio;
			
			new Ajax.Request(url, {
				method: 'get',
				onSuccess: function(req){
					eval(req.responseText);
				},
				onFailure: function(req){
					alert("An error occurred while deleting the document.\nClik on the image and try again");
				}
			});
		}
	}
	
	function deshabilitaPantalla(){
		$(document.body).insert({bottom: '<div id="screenDisabled"></div>'});
		
		var screenDisabled = $('screenDisabled');
		screenDisabled.style.width = document.viewport.getWidth()+'px';
		screenDisabled.style.height = document.viewport.getHeight()+'px';
		screenDisabled.setStyle({
			position: 'absolute',
			zIndex: 50,
			left: '0px',
			top: '0px',
			backgroundColor: 'gray',
			cssFloat: 'left',
			opacity: 0.3
		});
	}
	
	function habilitaPantalla(){
		if($('screenDisabled')){
			$('screenDisabled').remove();
		}
	}
	
	function muestraDocumentoExistente(iddocumento, idstatus, folio, hoja, origen){
		$(document.body).insert({bottom: '<div id="documentoExistente" align="center" onmousedown="dragStart(event);" onmouseup="dragStop(event);">'+
										 	'<font size="2" color="white"><b>The document already has the selected sheet'+
										 	' digitized.<br>Your image is as follows:</b></font><br><br>'+
										 	'<img src="../../fotoArchivo?matricula=<%=codigoPersonal%>&documento='+iddocumento+'&hoja='+hoja+'" height="380px" nosave /><br><br>'+
										 	'<input type="button" class="btn btn-success" value="Reemplazar" onclick="reemplazaDocumento(\''+iddocumento+'\', \''+idstatus+'\', \''+folio+'\', \''+hoja+'\', \''+origen+'\');" />'+
										 	'&nbsp;<input type="button" class=btn btn-secondary" value="Cancelar" onclick="cancelaReemplazo();" />'+
										 '</div>'});
		var documentoExistente = $('documentoExistente');
		documentoExistente.setStyle({
			position: 'absolute',
			zIndex: 100,
			backgroundColor: 'black',
			height: '476px'
		});
		documentoExistente.style.left = ((document.viewport.getWidth()/2) - (documentoExistente.getWidth()/2))+'px';
		documentoExistente.style.top = ((document.viewport.getHeight()/2) - (documentoExistente.getHeight()/2))+'px';
	}
	
	function reemplazaDocumento(iddocumento, idstatus, folio, hoja, origen){
		var url = "reemplazar?iddocumento="+iddocumento+"&idstatus="+idstatus+"&folio="+folio+"&hoja="+hoja+"&origen="+origen;
		
		new Ajax.Request(url, {
			method: 'get',
			onSuccess: function(req){
				eval(req.responseText);
			},
			onFailure: function(req){
				alert(req.responseText);
				alert("An error occurred while requesting Assign Document. Try assigning it again. If the error persists, report it to systems.");
			}
		});
	}
	
	function cancelaReemplazo(){
		$('documentoExistente').remove();
		habilitaPantalla();
	}
	
	var vent = new Object();
	function dragStart(event){
		var obj = $("documentoExistente");
		vent.X = Event.pointerX(event) - Position.cumulativeOffset(obj)[0];
		vent.Y = Event.pointerY(event) - Position.cumulativeOffset(obj)[1];
		Event.observe(document, 'mousemove', dragGo);
	}
	
	var theWidth = document.viewport.getWidth();
	var theHeight = document.viewport.getHeight();
	function dragGo(event){
		var obj = $("documentoExistente");
		if((Event.pointerX(event) - vent.X + obj.offsetWidth) < theWidth)
			obj.style.left = (Event.pointerX(event) - vent.X)+"px";
		if((Event.pointerY(event) - vent.Y + obj.offsetHeight) < theHeight)
			obj.style.top = (Event.pointerY(event) - vent.Y)+"px";
		if((obj.offsetTop + obj.offsetHeight) > theHeight){
			obj.style.top = (theHeight - obj.offsetHeight)+"px";
		}else if(obj.offsetTop < 10){
			obj.style.top = "10px";
		}
		if((obj.offsetLeft + obj.offsetWidth) > theWidth){
			obj.style.left = (theWidth - obj.offsetWidth)+"px";
		}else if(obj.offsetLeft < 10){
			obj.style.left = "10px";
		}
	}
	
	function dragStop(event){
		Event.stopObserving(document, 'mousemove', dragGo);
	}
</script>
</head>
<body>
<div class="container-fluid">
	<h2>Unregistered documents<small>( [<%=codigoPersonal%>] &nbsp; <%=alumnoNombre%>)</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a href="documentos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table class="table table-bordered table-condensed" style="width:100%" >
	<tr>
   		<td valign="top" align="center" width="180px" style="border: solid 1px black;">
   	 		<div id="fotos" style="height:100%; overflow: auto;">
		<%	for(PosArchGeneral general : lisDocumentos){ %>      
				<img name="Imagen<%=general.getFolio() %>" id="Imagen<%=general.getFolio() %>" src="../../fotoGeneral?matricula=<%=codigoPersonal%>&folio=<%=general.getFolio() %>" width="150" nosave border="1" hspace="2" vspace="2" class="button" onclick="muestraFoto(this);">					
		<%	} %>
			</div>
		</td>
		<td id="visor" valign="top" align="center" style="border: solid 1px black;">
			&nbsp;
		</td>
		<td id="panel" valign="top" width="300px" style="vertical-align:text-top; border:solid 1px black;">
			&nbsp;
		</td>
  	</tr>
	</table>
	<script type="text/javascript">
		inicio();
	</script>
</div>
</body>