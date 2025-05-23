<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.catalogo.CatFacultad"%>
<%@page import="aca.catalogo.CatCarrera"%>
<%@page import="aca.plan.MapaNuevoUnidad"%>
<%@page import="aca.plan.MapaNuevoActividad"%>
<%@page import="aca.plan.MapaNuevoProducto"%>
<%@page import="aca.plan.MapaNuevoBibliografia"%>
<%@page import="aca.plan.MapaNuevoBiblioUnidad"%>

<jsp:useBean id="mapaNuevoPlan" class="aca.plan.MapaNuevoPlan" scope="page"/>
<jsp:useBean id="mapaNuevoCurso" class="aca.plan.MapaNuevoCurso" scope="page"/>
<jsp:useBean id="mapaNuevoUnidad" class="aca.plan.MapaNuevoUnidad" scope="page"/>
<jsp:useBean id="mapaNuevoUnidadU" class="aca.plan.MapaNuevoUnidadUtil" scope="page"/>
<jsp:useBean id="mapaNuevoActividad" class="aca.plan.MapaNuevoActividad" scope="page"/>
<jsp:useBean id="mapaNuevoActividadU" class="aca.plan.MapaNuevoActividadUtil" scope="page"/>
<jsp:useBean id="mapaNuevoProductoU" class="aca.plan.MapaNuevoProductoUtil" scope="page"/>
<jsp:useBean id="mapaNuevoBibliografia" class="aca.plan.MapaNuevoBibliografia" scope="page"/>
<jsp:useBean id="mapaNuevoBibliografiaU" class="aca.plan.MapaNuevoBibliografiaUtil" scope="page"/>
<jsp:useBean id="mapaNuevoBiblioUnidad" class="aca.plan.MapaNuevoBiblioUnidad" scope="page"/>
<jsp:useBean id="mapaNuevoBiblioUnidadU" class="aca.plan.MapaNuevoBiblioUnidadUtil" scope="page"/>
<jsp:useBean id="accesoU" class="aca.acceso.AccesoUtil" scope="page"/>
<%
	String institucion 		= (String)session.getAttribute("institucion");

	String planId			= request.getParameter("planId");
	String versionId		= request.getParameter("versionId");
	String cursoId			= request.getParameter("cursoId");
	String opcion			= request.getParameter("Opcion")==null?"0":request.getParameter("Opcion");
	
	int	accion				= Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));
	String bibliografiaId	= request.getParameter("bibliografiaId")==null?"0":request.getParameter("bibliografiaId");
	
	String tipo				= "";
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	ArrayList<MapaNuevoUnidad> listUnidades = mapaNuevoUnidadU.getListCurso(conEnoc, cursoId, "ORDER BY ORDEN, UNIDAD_ID");
	ArrayList<MapaNuevoActividad> listActividades = null;
	ArrayList<MapaNuevoProducto> listProductos = null;
	ArrayList<MapaNuevoBibliografia> listBibliografias = null;	
	
	mapaNuevoPlan.mapeaRegId(conEnoc, planId, versionId);
	mapaNuevoCurso.mapeaRegId(conEnoc, cursoId, planId, versionId);
	//System.out.println("antes de revisar permisos");
	
	//  && !Acceso.esCotejador(conEnoc, codigoPersonal)) && !Acceso.esAdministrador(conEnoc, codigoPersonal)
	if(!mapaNuevoCurso.getEstado().equals("1") && opcion.equals("0")){
		if ( mapaNuevoPlan.getIdioma().equals("E") ){
			response.sendRedirect("verMateria?planId="+planId+"&versionId="+versionId+"&cursoId="+cursoId);
		}else{
			response.sendRedirect("verMateriaI?planId="+planId+"&versionId="+versionId+"&cursoId="+cursoId);
		}
	}
	
	switch(accion){
		case 25:{//Borrar Bibliografia Global
			conEnoc.setAutoCommit(false);
			mapaNuevoBibliografia.mapeaRegId(conEnoc, cursoId, bibliografiaId);
			if(mapaNuevoBibliografiaU.deleteReg(conEnoc, cursoId, bibliografiaId)){
				mapaNuevoBiblioUnidadU.deleteReg(conEnoc, cursoId, bibliografiaId);
				conEnoc.commit();
			}else{
				conEnoc.rollback();
			}
			conEnoc.setAutoCommit(true);
		}break;
	}
%>
<head>	
	<meta http-equiv="Content-type" content="text/html;"> 	
	<style type="text/css">
		td.button{
			border-style: solid;
			border-width: 1px;
			border-color: #FFFFFF;
		}
		
		textarea{
			font-size: 7pt;
		}
	</style>
	<script type="text/javascript"><!--
		var theWidth, theHeight;
	
		function inicio(){
			if (window.innerWidth){
			  theWidth = window.innerWidth 
			  theHeight = window.innerHeight 
			} 
			else if (document.documentElement && document.documentElement.clientWidth){
			  theWidth = document.documentElement.clientWidth 
			  theHeight = document.documentElement.clientHeight 
			} 
			else if (document.body){
			  theWidth = document.body.clientWidth 
			  theHeight = document.body.clientHeight 
			}
			revisaEstadoMateria();
		}
	
		function checkSize(obj, event, maxlength){
			if(event){//Si no es al guardar
				obj = $(obj);
				if(event.keyCode != 8){
					if(obj.value.length > (maxlength-5)){
						obj.value = obj.value.substring(0, (maxlength - 5));
						muestraMensaje("El texto fue recortado.<br>Tamaño maximo permitido es de <b>"+(maxlength - 5)+"</b> caracteres", "yellow");
					}
				}
			}else{//Si es al guardar
				maxlength = parseInt($w(obj.className), 10);
				if(obj.value.length > (maxlength-5)){
					alert("entro en el guardar - "+obj.value.length);
					obj.value = obj.value.substring(0, (maxlength - 5));
					alert(obj.value.length);
					muestraMensaje("El texto fue recortado.<br>Tamaño maximo permitido es de <b>"+(maxlength - 5)+"</b> caracteres", "yellow");
				}
			}
		}

		function wordwrap( str, int_width, str_break, cut ) {
		    // Wraps a string to a given number of characters
		    // 
		    // +    discuss at: http://kevin.vanzonneveld.net/techblog/article/javascript_equivalent_for_phps_wordwrap/
		    // +       version: 810.819
		    // +   original by: Jonas Raoni Soares Silva (http://www.jsfromhell.com)
		    // +   improved by: Nick Callen
		    // +    revised by: Jonas Raoni Soares Silva (http://www.jsfromhell.com)
		    // +   improved by: Kevin van Zonneveld (http://kevin.vanzonneveld.net)
		    // +   improved by: Sakimori
		    // *     example 1: wordwrap('Kevin van Zonneveld', 6, '|', true);
		    // *     returns 1: 'Kevin |van |Zonnev|eld'
		    // *     example 2: wordwrap('The quick brown fox jumped over the lazy dog.', 20, '<br />\n');
		    // *     returns 2: 'The quick brown fox <br />\njumped over the lazy<br />\n dog.'
		    // *     example 3: wordwrap('Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.');
		    // *     returns 3: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod \ntempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim \nveniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea \ncommodo consequat.'

		    // PHP Defaults
		    
		    var m = ((arguments.length >= 2) ? arguments[1] : 75   );
		    var b = ((arguments.length >= 3) ? arguments[2] : "\n" );
		    var c = ((arguments.length >= 4) ? arguments[3] : false);

		    var i, j, l, s, r;

		    str += '';

		    if (m < 1) {
		        return str;
		    }

		    for (i = -1, l = (r = str.split("\n")).length; ++i < l; r[i] += s) {
		        for(s = r[i], r[i] = ""; s.length > m; r[i] += s.slice(0, j) + ((s = s.slice(j)).length ? b : "")){
		            j = c == 2 || (j = s.slice(0, m + 1).match(/\S*(\s)?$/))[1] ? m : j.input.length - j[0].length || c == 1 && m || j.input.length + (j = s.slice(m).match(/^\S*/)).input.length;
		        }
		    }

		    return r.join("\n");
		}

		function toHTML(texto){
			//Enters
			while(texto.indexOf("\n") != -1){
				texto = texto.replace("\n","<br>");
			}
			//Espacios
			while(texto.indexOf(" ") != -1){
				texto = texto.replace(" ","&nbsp;");
			}
			return texto;
		}

		function toString(texto){
			if(texto == "-")
				texto = "";
			//Enters
			while(texto.indexOf("<br>") != -1){
				texto = texto.replace("<br>","\n");
			}
			while(texto.indexOf("<BR>") != -1){//Internet Explorer
				texto = texto.replace("<BR>","\n");
			}
			//Espacios
			while(texto.indexOf("&nbsp;") != -1){
				texto = texto.replace("&nbsp;"," ");
			}
			while(texto.indexOf("&NBSP;") != -1){//Internet Explorer
				texto = texto.replace("&NBSP;"," ");
			}
			return texto;
		}

		function $escape(obj){
			if(obj.readAttribute("cols")){
				obj.value = wordwrap(obj.value, (parseInt(obj.readAttribute("cols"), 10) - 0));
				checkSize(obj);
			}
			var frase = obj.value;
			frase = frase.replace(/[^\w.,áÁéÉíÍóÓúÚñÑ@\n:;!¿\?()&/$%"\[\]=\{\}*|-]/g, " ");
			frase = encodeURIComponent(frase);
			return frase;
		}

		var numCargando = 0;
		function muestraCargando(){
			numCargando++;
			if(!$("cargando")){
				$(document.body).insert({
					bottom: '<img id="cargando" src="../../imagenes/cargando2.gif" width="80px" />'
				});
				var cargando = $("cargando");
				if(!document.all)
					cargando.style.position = "fixed";
				else
					cargando.style.position = "absolute";
				cargando.style.zIndex = "1000";
				cargando.style.top = "0px";
				cargando.style.left = (theWidth-cargando.offsetWidth - 100)+"px";
			}
		}
		
		function ocultaCargando(){
			numCargando--;
			if(numCargando == 0){
				$("cargando").remove();
			}
		}

		var tMensaje;
		function muestraMensaje(mensaje, colorFondo){
			if(tMensaje)
				clearTimeout(tMensaje);
			var div = $("mensaje");
			if(!div){
				$(document.body).insert({
					bottom: '<div id="mensaje" style="background-color: '+colorFondo+'; position: fixed; z-index: 1000; overflow: auto;"><table><tr><td class="titulo2">'+mensaje+'</td></tr></table></div>'
				});
				div = $("mensaje");
				if(document.all)
					div.style.position = "absolute";
			}else{
				div.innerHTML = '<table><tr><td class="titulo2">'+mensaje+'</td></tr></table>';
				div.style.backgroundColor = colorFondo;
			}
			//alert(div.innerHTML);
			div.style.top = (0 - div.offsetHeight)+"px";
			div.style.left = (theWidth/2 - div.offsetWidth/2)+"px";
			mueveVertical("mensaje", 0);
			tMensaje = setTimeout("ocultaMensaje();", 4000);
		}
		
		function ocultaMensaje(){
			$("mensaje").remove();
		}

		var tiempoMensaje;
		function mueveVertical(objeto, destino){
			var obj = $(objeto);
			var salto = 2;
			var tiempoSalto = 50;
			if(tiempoMensaje)
				clearTimeout(tiempoMensaje);
			if(obj.offsetTop > destino){
				if((obj.offsetTop-salto) > destino){
					tiempoMensajeSesion = setTimeout("mueveVertical('"+objeto+"', "+destino+");", tiempoSalto);
					obj.style.top = (obj.offsetTop-salto)+"px";
				}else{
					obj.style.top = destino+"px";
				}
			}else if(obj.offsetTop < destino){
				if((obj.offsetTop+salto) < destino){
					tiempoMensajeSesion = setTimeout("mueveVertical('"+objeto+"', "+destino+");", tiempoSalto);
					obj.style.top = (obj.offsetTop+salto)+"px";
				}else{
					obj.style.top = destino+"px";
				}
			}
		}

		function revisaFormaCurso(){
			if(document.formaCurso.mediosRecursos.value != "" ||
			   document.formaCurso.eeDiagnostica.value != "" ||
			   document.formaCurso.eeFormativa.value != "" ||
			   document.formaCurso.eeSumativa.value != "" ||
			   document.formaCurso.escala.value != ""){				
				guardar();
			}else{
				alert("Llene por lo menos un campo para poder guardar");
			}
		}

		var urlDefault = 'editaUnidadAccion?planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId%>';
		function guardar(){			
			var url = urlDefault+
					  "&Accion=2"+
					  "&mediosRecursos="+encodeURIComponent($("mediosRecursos").value)+
					  "&eeDiagnostica="+encodeURIComponent($("eeDiagnostica").value)+
					  "&eeFormativa="+encodeURIComponent($("eeFormativa").value)+
					  "&eeSumativa="+encodeURIComponent($("eeSumativa").value)+
					  "&escala="+encodeURIComponent($("escala").value);
			alert
			new Ajax.Request(url, {
				method: "get",
				onSuccess: function(req){					
					eval(req.responseText);
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para guardar el curso.\nIntentelo de nuevo");
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				},
				contentType: "text/html;charset=UTF-8"
			});
		}

		function nuevaUnidad(){
			cancelarEditarUnidad();
			var url = urlDefault+
					  "&Accion=1";
					  
			new Ajax.Request(url,{
				method: "get",
				onSuccess: function(req){
					$("nuevo").innerHTML = req.responseText;
					$("btnUnidad").innerHTML='<tr><td align="center"><input type="button" class="button" value="Guardar" onclick="guardarUnidad();" /><input type="button" class="button" value="Cancelar" onclick="cancelarNuevaUnidad();" /></td></tr>';
					$("tiempo").focus();
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para agregar una unidad.\nIntentelo de nuevo");
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				}
			});
		}

		function cancelarNuevaUnidad(){
			$("nuevo").innerHTML = '';
			$("btnUnidad").innerHTML='<tr><td align="center" class="button" height="20px" onclick="nuevaUnidad();">Agregar Unidad</td></tr>';
		}

		function guardarUnidad(){
			var url = urlDefault+
			  "&Accion=5"+
			  "&nombre="+$escape($("nombre"))+
			  "&tiempo="+$escape($("tiempo"))+
			  "&temas="+$escape($("temas"));
			  
			new Ajax.Request(url, {
				method: "get",
				onSuccess: function(req){
					eval(req.responseText);
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para guardar la unidad.\nIntentelo de nuevo");
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				}
			});
		}

		function borrarUnidad(unidadId){
			if(confirm("¿Está seguro que desea borrar la unidad?\nTenga en cuenta que ya borrada no se podrá recuperar")){
				var url = urlDefault+
				  "&Accion=3"+
				  "&unidadId="+unidadId;
				  
				new Ajax.Request(url,{
					method: "get",
					onSuccess: function(req){
						eval(req.responseText);
					},
					onFailure: function(req){
						alert("No se pudo conectar a la pagina para borrar la unidad.\nIntentelo de nuevo");
					},
					onCreate: function(req){
						muestraCargando();
					},
					onComplete: function(req){
						ocultaCargando();
					}
				});
			}
		}

		var tmpTiempo, tmpNombre, tmpTemas;
		function editarUnidad(unidadId){
			cancelarNuevaUnidad();
			cancelarEditarUnidad();
			tmpTiempo = $("tiempo"+unidadId).innerHTML;
			tmpNombre = $("nombre"+unidadId).innerHTML;
			tmpTemas = $("temas"+unidadId).innerHTML;
			document.formaUnidades.unidadId.value = unidadId;
			$("tiempo"+unidadId).innerHTML = '<textarea id="tiempo" name="tiempo" onkeyup="checkSize(this, event, 50);" class="50" cols="15" rows="5">'+toString($("tiempo"+unidadId).innerHTML)+'</textarea>';
			$("nombre"+unidadId).innerHTML = '<textarea id="nombre" name="nombre" onkeyup="checkSize(this, event, 200);" class="200" cols="18" rows="5">'+toString($("nombre"+unidadId).innerHTML)+'</textarea>';
			$("temas"+unidadId).innerHTML = '<textarea id="temas" name="temas" onkeyup="checkSize(this, event, 4000);" class="4000" cols="65" rows="20">'+toString($("temas"+unidadId).innerHTML)+'</textarea>';
			$("unidad"+unidadId).insert({
				bottom: '<input type="button" class="button" id="btnEditaUnidadG" value="Guardar" onclick="modificarUnidad(\''+unidadId+'\');" />'+
						'<input type="button" class="button" id="btnEditaUnidadC" value="Cancelar" onclick="cancelarEditarUnidad();" />'
			});
		}

		function cancelarEditarUnidad(){
			var tmp = $("tiempo");
			if(tmp && tmpTiempo){
				tmp.getOffsetParent().innerHTML = tmpTiempo;
				tmpTiempo = null;
			}

			tmp = $("nombre");
			if(tmp && tmpNombre){
				tmp.getOffsetParent().innerHTML = tmpNombre;
				tmpNombre = null;
			}

			tmp = $("temas");
			if(tmp && tmpTemas){
				tmp.getOffsetParent().innerHTML = tmpTemas;
				tmpTemas = null;
			}

			if($("btnEditaUnidadG"))
				$("btnEditaUnidadG").remove();
			if($("btnEditaUnidadC"))
				$("btnEditaUnidadC").remove();
		}

		function modificarUnidad(unidadId){
			var url = urlDefault+
			  "&Accion=6"+
			  "&unidadId="+unidadId+
			  "&nombre="+$escape($("nombre"))+
			  "&tiempo="+$escape($("tiempo"))+
			  "&temas="+$escape($("temas"));
			  
			new Ajax.Request(url, {
				method: "get",
				onSuccess: function(req){
					eval(req.responseText);
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para modificar la unidad.\nIntentelo de nuevo");
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				}
			});
		}

		function recargarUnidad(unidadId){
			$("unidad"+unidadId).style.height = ($("unidad"+unidadId).getHeight())+"px";
			$("unidad"+unidadId).innerHTML = '<img src="../../imagenes/cargando.gif" />';
			var url = urlDefault+
			  "&Accion=7"+
			  "&unidadId="+unidadId;
			  
			new Ajax.Request(url,{
				method: "get",
				onSuccess: function(req){
					$("unidad"+unidadId).innerHTML = req.responseText;
					$("accionDocenteNombre"+unidadId).innerHTML = $("nombre"+unidadId).innerHTML;
					$("unidad"+unidadId).style.height = "";
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para recargar la unidad.\nRegrese al listado de materias y vuelva a entrar");
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				}
			});
		}

		function editarAccionDocente(unidadId){
			var div = $("editaAccionDocente");
			if(!div){
				$(document.body).insert({
					bottom: '<div id="editaAccionDocente"></div>'
				});
			}
			div = $("editaAccionDocente");
			div.setStyle({
				position: 'absolute',
				zIndex: '50',
				backgroundColor: 'white',
				border: 'solid 1px blue',
				width: '740px',
				height: '230px'
			});
			div.clonePosition($("accionDocenteDatos"+unidadId),{setWidth: false, setHeight: false});
			div.innerHTML = '<table>'+
								'<tr>'+
									'<td><textarea id="accionDocente" onkeyup="checkSize(this, event, 1000);" class="1000" cols="130" rows="11">'+toString($("accionDocenteDatos"+unidadId).innerHTML)+'</textarea></td>'+
								'</tr>'+
								'<tr>'+
									'<td align="center">'+
										'<input type="button" class="button" value="Guardar" onclick="modificarAccionDocente(\''+unidadId+'\');" />'+
										'<input type="button" class="button" value="Cancelar" onclick="cancelarEditarAccionDocente();" />'+
									'</td>'+
								'</tr>'+
							'</table>';
			$("accionDocente").focus();
		}

		function cancelarEditarAccionDocente(){
			$("editaAccionDocente").remove();
		}

		function modificarAccionDocente(unidadId){
			var url = urlDefault+
			  "&Accion=8"+
			  "&unidadId="+unidadId+
			  "&accionDocente="+$escape($("accionDocente"));
			  
			new Ajax.Request(url,{
				method: "get",
				onSuccess: function(req){
					eval(req.responseText);
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para modificar la Accion Docente.\nIntentelo de nuevo");
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				}
			});
		}

		function nuevoObjetivo(unidadId){
			var div = $("editaObjetivo");
			if(!div){
				$(document.body).insert({
					bottom: '<div id="editaObjetivo"></div>'
				});
			}
			div = $("editaObjetivo");
			div.setStyle({
				position: 'absolute',
				zIndex: '50',
				backgroundColor: 'white',
				border: 'solid 1px blue',
				width: '220px',
				height: '250px'
			});
			div.clonePosition($("btnAgregarObjetivo"+unidadId),{setWidth: false, setHeight: false});
			div.innerHTML = '<table>'+
							'<tr>'+
								'<td><textarea id="objetivo" onkeyup="checkSize(this, event, 1000);" class="1000" cols="20" rows="11"></textarea></td>'+
							'</tr>'+
							'<tr>'+
							'<td align="center">'+
								'Seleccione un tipo'+
								'<select id="tipoObjetivo">'+
									'<option value="1">Conocimientos</option>'+
									'<option value="2">Habilidades</option>'+
									'<option value="3">Actitudes</option>'+
								'</select>'+
							'</td>'+
						'</tr>'+
							'<tr>'+
								'<td align="center">'+
									'<input type="button" class="button" value="Guardar" onclick="guardarObjetivo(\''+unidadId+'\');" />'+
									'<input type="button" class="button" value="Cancelar" onclick="cancelarNuevoObjetivo();" />'+
								'</td>'+
							'</tr>'+
						'</table>';
			$("objetivo").focus();
		}

		function cancelarNuevoObjetivo(){
			if($("editaObjetivo"))
				$("editaObjetivo").remove();
		}

		function guardarObjetivo(unidadId){
			var url = urlDefault+
			  "&Accion=9"+
			  "&unidadId="+unidadId+
			  "&objetivo="+$escape($("objetivo"))+
			  "&tipo="+$("tipoObjetivo").value;
			  
			new Ajax.Request(url,{
				method: "get",
				onSuccess: function(req){
					eval(req.responseText);
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para guardar el Objetivo.\nIntentelo de nuevo");
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				}
			});
		}

		function editarObjetivo(unidadId, actividadId){
			var div = $("editaObjetivo");
			if(!div){
				$(document.body).insert({
					bottom: '<div id="editaObjetivo"></div>'
				});
			}
			div = $("editaObjetivo");
			div.setStyle({
				position: 'absolute',
				zIndex: '50',
				backgroundColor: 'white',
				border: 'solid 1px blue',
				width: '220px',
				height: '230px'
			});
			div.clonePosition($("objetivo"+unidadId+"-"+actividadId),{setWidth: false, setHeight: false});
			div.innerHTML = '<table>'+
								'<tr>'+
									'<td><textarea id="objetivo" cols="20" rows="11" onkeyup="checkSize(this, event, 1000);" class="1000">'+toString($("objetivo"+unidadId+"-"+actividadId).innerHTML)+'</textarea></td>'+
								'</tr>'+
								'<tr>'+
									'<td align="center">'+
										'<input type="button" class="button" value="Guardar" onclick="modificarObjetivo(\''+unidadId+'\', \''+actividadId+'\');" />'+
										'<input type="button" class="button" value="Cancelar" onclick="cancelarEditarObjetivo();" />'+
										'<input type="button" class="button" value="Borrar" onclick="borrarObjetivo(\''+unidadId+'\', \''+actividadId+'\');" />'+
									'</td>'+
								'</tr>'+
							'</table>';
			$("objetivo").focus();
		}

		function cancelarEditarObjetivo(){
			cancelarNuevoObjetivo();
		}

		function modificarObjetivo(unidadId, actividadId){
			var url = urlDefault+
			  "&Accion=10"+
			  "&unidadId="+unidadId+
			  "&actividadId="+actividadId+
			  "&objetivo="+$escape($("objetivo"));
			  
			new Ajax.Request(url,{
				method: "get",
				onSuccess: function(req){
					eval(req.responseText);
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para modificar el Objetivo.\nIntentelo de nuevo");
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				}
			});
		}

		function editarActividad(unidadId, actividadId){
			cancelarEditarCriterio();
			var div = $("editaActividad");
			if(!div){
				$(document.body).insert({
					bottom: '<div id="editaActividad"></div>'
				});
			}
			div = $("editaActividad");
			div.setStyle({
				position: 'absolute',
				zIndex: '50',
				backgroundColor: 'white',
				border: 'solid 1px blue',
				width: '180px',
				height: '230px'
			});
			div.clonePosition($("actividad"+unidadId+"-"+actividadId),{setWidth: false, setHeight: false});
			div.innerHTML = '<table>'+
								'<tr>'+
									'<td><textarea id="actividad" onkeyup="checkSize(this, event, 1000);" class="1000" cols="20" rows="11">'+toString($("actividad"+unidadId+"-"+actividadId).innerHTML)+'</textarea></td>'+
								'</tr>'+
								'<tr>'+
									'<td align="center">'+
										'<input type="button" class="button" value="Guardar" onclick="modificarActividad(\''+unidadId+'\', \''+actividadId+'\');" />'+
										'<input type="button" class="button" value="Cancelar" onclick="cancelarEditarActividad();" />'+
									'</td>'+
								'</tr>'+
							'</table>';
			$("actividad").focus();
		}

		function cancelarEditarActividad(){
			if($("editaActividad"))
				$("editaActividad").remove();
		}

		function modificarActividad(unidadId, actividadId){
			var url = urlDefault+
			  "&Accion=11"+
			  "&unidadId="+unidadId+
			  "&actividadId="+actividadId+
			  "&descripcion="+$escape($("actividad"));
			  
			new Ajax.Request(url,{
				method: "get",
				onSuccess: function(req){
					eval(req.responseText);
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para guardar la Actividad.\nIntentelo de nuevo");
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				}
			});
		}

		function editarCriterio(unidadId, actividadId){
			cancelarEditarActividad();
			var div = $("editaCriterio");
			if(!div){
				$(document.body).insert({
					bottom: '<div id="editaCriterio"></div>'
				});
			}
			div = $("editaCriterio");
			div.setStyle({
				position: 'absolute',
				zIndex: '50',
				backgroundColor: 'white',
				border: 'solid 1px blue',
				width: '180px',
				height: '230px'
			});
			div.clonePosition($("criterio"+unidadId+"-"+actividadId),{setWidth: false, setHeight: false});
			div.innerHTML = '<table>'+
								'<tr>'+
									'<td><textarea id="criterio" onkeyup="checkSize(this, event, 1000);" class="1000" cols="20" rows="11">'+toString($("criterio"+unidadId+"-"+actividadId).innerHTML)+'</textarea></td>'+
								'</tr>'+
								'<tr>'+
									'<td align="center">'+
										'<input type="button" class="button" value="Guardar" onclick="modificarCriterio(\''+unidadId+'\', \''+actividadId+'\');" />'+
										'<input type="button" class="button" value="Cancelar" onclick="cancelarEditarCriterio();" />'+
									'</td>'+
								'</tr>'+
							'</table>';
			$("criterio").focus();
		}

		function cancelarEditarCriterio(){
			if($("editaCriterio"))
				$("editaCriterio").remove();
		}

		function modificarCriterio(unidadId, actividadId){
			var url = urlDefault+
			  "&Accion=20"+
			  "&unidadId="+unidadId+
			  "&actividadId="+actividadId+
			  "&criterio="+$escape($("criterio"));
			  
			new Ajax.Request(url,{
				method: "get",
				onSuccess: function(req){
					eval(req.responseText);
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para modificar el Criterio de Desempeño.\nIntentelo de nuevo");
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				}
			});
		}

		function borrarObjetivo(unidadId, actividadId){
			if(confirm("¿Está seguro que desea borrar el Objetivo y su respectiva Activdad?\nTenga en cuenta que ya borrados no se podrán recuperar")){
				var url = urlDefault+
				  "&Accion=12"+
				  "&unidadId="+unidadId+
				  "&actividadId="+actividadId;
				  
				new Ajax.Request(url,{
					method: "get",
					onSuccess: function(req){
						eval(req.responseText);
					},
					onFailure: function(req){
						alert("No se pudo conectar a la pagina para borrar el Objetivo.\nIntentelo de nuevo");
					},
					onCreate: function(req){
						muestraCargando();
					},
					onComplete: function(req){
						ocultaCargando();
					}
				});
			}
		}

		function editarBibliografiaGlobal(bibliografiaId){
			var div = $("editaBibliografia");
			if(!div){
				$(document.body).insert({
					bottom: '<div id="editaBibliografia" align="center"></div>'
				});
			}
			div = $("editaBibliografia");
			div.setStyle({
				position: 'absolute',
				zIndex: '50',
				backgroundColor: 'white',
				border: 'solid 1px blue',
				width: '500px',
				height: '130px'
			});
			div.clonePosition($("bibliografia"+bibliografiaId),{setWidth: false, setHeight: false});
			div.innerHTML = '<img src="../../imagenes/cargando.gif" />';
			
			var url = urlDefault+
			  "&Accion=23"+
			  "&bibliografiaId="+bibliografiaId;
			  
			new Ajax.Request(url,{
				method: "get",
				onSuccess: function(req){
					div.innerHTML = req.responseText;
					$("bibliografia").focus();
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para cargar la Bibliografia.\nIntentelo de nuevo");
					cancelarEditarBibliografiaGlobal();
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				}
			});
		}

		function cancelarEditarBibliografiaGlobal(){
			if($("editaBibliografia"))
				$("editaBibliografia").remove();
		}

		function modificarBibliografiaGlobal(bibliografiaId){
			var url = urlDefault+
			  "&Accion=24"+
			  "&bibliografiaId="+bibliografiaId+
			  "&bibliografia="+$escape($("bibliografia"))+
			  "&referencia="+$escape($("referencia"));
			  
			new Ajax.Request(url,{
				method: "get",
				onSuccess: function(req){
					eval(req.responseText);
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para modificar la Bibliografia.\nIntentelo de nuevo");
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				}
			});
		}

		function borrarBibliografiaGlobal(bibliografiaId){
			if(confirm("¿Está seguro que desea borrar la Bibliografia?\nTenga en cuenta que ya borrada no se podrá recuperar")){
				document.formaBiblio.Accion.value = "25";
				document.formaBiblio.bibliografiaId.value = bibliografiaId;
				document.formaBiblio.submit();		
			}
		}
		
		function guardarBibliografia(unidadId){
			if(biblioId){
				var url = urlDefault+
				  "&Accion=13"+
				  "&unidadId="+unidadId+
				  "&bibliografiaId="+biblioId+
				  "&especificacion="+$escape($("especificacion"));
				  
				new Ajax.Request(url,{
					method: "get",
					onSuccess: function(req){
						eval(req.responseText);
					},
					onFailure: function(req){
						alert("No se pudo conectar a la pagina para guardar la Bibliografia.\nIntentelo de nuevo");
					},
					onCreate: function(req){
						muestraCargando();
					},
					onComplete: function(req){
						ocultaCargando();
					}
				});
			}else{
				alert("Elija una de las bibliografias para poder guardar");
			}
		}

		function nuevaBibliografia(unidadId){
			//alert("Por el momento la creacion de Bibliografias se encuentra suspendida por mejoras a la misma\nLamentamos los inconvenienties que esto pueda ocasionarle");
			var div = $("editaBibliografia");
			if(!div){
				$(document.body).insert({
					bottom: '<div id="editaBibliografia" align="center"></div>'
				});
			}
			div = $("editaBibliografia");
			div.setStyle({
				position: 'absolute',
				zIndex: '50',
				backgroundColor: 'white',
				border: 'solid 1px blue',
				width: '400px',
				height: '250px',
				align: 'center'
			});
			div.clonePosition($("btnAgregarBibliografia"+unidadId),{setWidth: false, setHeight: false});
			div.style.left = (div.offsetLeft+$("btnAgregarBibliografia"+unidadId).offsetWidth-div.offsetWidth)+"px";
			div.innerHTML = '<img src="../../imagenes/cargando.gif" />';

			var url = urlDefault+
					  "&Accion=21"+
					  "&unidadId="+unidadId;

			new Ajax.Request(url, {
				method: "get",
				onSuccess: function(req){
					$("editaBibliografia").innerHTML = req.responseText;
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para solicitar una nueva Bibliografia.\nIntentelo de nuevo");
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				}
			});
		}

		var biblioId;
		function elegirBibliografia(bibliografiaId){
			var btn = $("btnGuardar");
			if(biblioId)
				$("biblio"+biblioId).style.backgroundColor = "";
			$("biblio"+bibliografiaId).style.backgroundColor = "#B5E1F8";
			biblioId = bibliografiaId;
		}

		function cancelarNuevaBibliografia(){
			if($("editaBibliografia"))
				$("editaBibliografia").remove();
			biblioId = null;
		}

		function editarBibliografia(unidadId, bibliografiaId, id){
			//alert("Por el momento la modificacion de Bibliografias se encuentra suspendida por mejoras a la misma\nLamentamos los inconvenienties que esto pueda ocasionarle");
			var div = $("editaBibliografia");
			if(!div){
				$(document.body).insert({
					bottom: '<div id="editaBibliografia"></div>'
				});
			}
			div = $("editaBibliografia");
			div.setStyle({
				position: 'absolute',
				zIndex: '50',
				backgroundColor: 'white',
				border: 'solid 1px blue',
				width: '200px',
				height: '110px'
			});
			div.clonePosition($("bibliografia"+unidadId+"-"+bibliografiaId+"-"+id),{setWidth: false, setHeight: false});
			div.style.left = (div.offsetLeft+$("bibliografia"+unidadId+"-"+bibliografiaId+"-"+id).offsetWidth-div.offsetWidth)+"px";
			div.innerHTML = '<table>'+
								'<tr>'+
									'<td><textarea id="especificacion" onkeyup="checkSize(this, event, 200);" class="200" cols="20" rows="4">'+toString($("bibliografia"+unidadId+"-"+bibliografiaId+"-"+id).innerHTML)+'</textarea></td>'+
								'</tr>'+
								'<tr>'+
									'<td align="center">'+
										'<input type="button" class="button" value="Guardar" onclick="modificarBibliografia(\''+unidadId+'\', \''+bibliografiaId+'\', \''+id+'\');" />'+
										'<input type="button" class="button" value="Cancelar" onclick="cancelarEditarBibliografia();" />'+
										'<input type="button" class="button" value="Borrar" onclick="borrarBibliografia(\''+unidadId+'\', \''+bibliografiaId+'\', \''+id+'\');" />'+
									'</td>'+
								'</tr>'+
							'</table>';
			$("bibliografia").focus();
		}

		function cancelarEditarBibliografia(){
			if($("editaBibliografia"))
				$("editaBibliografia").remove();
		}

		function modificarBibliografia(unidadId, bibliografiaId, id){
			var url = urlDefault+
			  "&Accion=14"+
			  "&unidadId="+unidadId+
			  "&bibliografiaId="+bibliografiaId+
			  "&id="+id+
			  "&especificacion="+$escape($("especificacion"));
			  
			new Ajax.Request(url,{
				method: "get",
				onSuccess: function(req){
					eval(req.responseText);
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para modificar la Bibliografia.\nIntentelo de nuevo");
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				}
			});
		}

		function borrarBibliografia(unidadId, bibliografiaId, id){
			if(confirm("¿Está seguro que desea borrar la Bibliografia?\nTenga en cuenta que ya borrada no se podrá recuperar")){
				var url = urlDefault+
				  "&Accion=15"+
				  "&unidadId="+unidadId+
				  "&bibliografiaId="+bibliografiaId+
				  "&id="+id;
				  
				new Ajax.Request(url,{
					method: "get",
					onSuccess: function(req){
						eval(req.responseText);
					},
					onFailure: function(req){
						alert("No se pudo conectar a la pagina para borrar la Bibliografia.\nIntentelo de nuevo");
					},
					onCreate: function(req){
						muestraCargando();
					},
					onComplete: function(req){
						ocultaCargando();
					}
				});
			}
		}

		function recargarBibliografiaTotal(){
			var url = urlDefault+
			  "&Accion=16";

			$("bibliografiaTotal").innerHTML = '<img src="../../imagenes/cargando.gif" />';
			
			new Ajax.Request(url,{
				method: "get",
				onSuccess: function(req){
					$("bibliografiaTotal").innerHTML = req.responseText;
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para recargar la Bibliografia Total.\nIntentelo de nuevo");
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				}
			});
		}

		function cerrarMateria(){
			if(confirm("¿Está seguro que desea cerrar la materia?")){
				var url = urlDefault+
				  "&Accion=17";
				
				new Ajax.Request(url,{
					method: "get",
					onSuccess: function(req){
						eval(req.responseText);
					},
					onFailure: function(req){
						alert("No se pudo conectar a la pagina para Cerrar la Materia.\nIntentelo de nuevo");
					},
					onCreate: function(req){
						muestraCargando();
					},
					onComplete: function(req){
						ocultaCargando();
					}
				});
			}
		}

		function revisaEstadoMateria(){
			var url = urlDefault+"&Accion=18";
			
			new Ajax.Request(url,{
				method: "get",
				onSuccess: function(req){
					eval(req.responseText);
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para revisar el estado de la materia.\nIntentelo de nuevo");
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				}
			});
		}				
	</script>	
</head>
<body>
<div class="container-fluid">
	<h3>Editar Materia<small class="text-muted fs-5">( <%=mapaNuevoPlan.getNombre() %> - <%=mapaNuevoCurso.getNombre() %> )</small></h3>
	<div class="alert alert-info">
		<a href="materias?planId=<%=planId %>&versionId=<%=versionId %>" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
	</div>		
	<table class="table table-condensed" style="border: solid 1px black;" border="1">
		<tr>
			<td width="250px"><b>ASIGNATURA:</b></td>
			<td><%=mapaNuevoCurso.getNombre() %></td>
			<td colspan="2"><b>SEMESTRE:</b></td>
			<td colspan="2"><%=mapaNuevoCurso.getCiclo() %></td>
			<td colspan="3"><b>CLAVE:</b></td>
			<td colspan="3"><%=mapaNuevoCurso.getClave() %></td>
			<td><b>SERIACIÓN</b></td>
			<td><%=mapaNuevoCurso.getSeriacion() %></td>
		</tr>
		<tr>
			<td colspan="6"><b>COMPETENCIA DEL PERFIL QUE ATIENDE LA ASIGNATURA:</b></td>
			<td colspan="8"><%=mapaNuevoCurso.getCompetencia().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
		</tr>
		<tr>
			<td width="250px"><b>PRODUCTOS DE APRENDIZAJE<small class="text-muted"> (OBJETIVO DE LA ASIGNATURA):</small></b></td>
			<td width="600px">
<% 		
			if(aca.catalogo.CatCarreraUtil.getCoordinador(conEnoc, mapaNuevoPlan.getCarreraId()).equals(codigoPersonal) || accesoU.esAdministrador(conEnoc, codigoPersonal)){%>
			  <a href="cursoAccion?planId=<%=planId%>&versionId=<%=versionId%>&cursoId=<%=cursoId%>" class="btn btn-primary btn-sm">
			  	<i class='icon-pencil icon-white'></i>
			  </a>
<% 			 }%>			  
			  <br>
			  <%=mapaNuevoCurso.getDescripcion().replaceAll("\n","<br>").replaceAll(" ","&nbsp;")%>
			</td>
		</tr>
		<tr>
			<td width="250px"><b>L&Iacute;NEA CURRICULAR:</b></td>
			<td><%=mapaNuevoCurso.getUbicacion() %></td>
<%
	if(mapaNuevoPlan.getHts().equals("S")){
%>
			<td><b>HTS:</b></td>
			<td><%=mapaNuevoCurso.getHst() %></td>
<%
	}
	if(mapaNuevoPlan.getHps().equals("S")){
%>
			<td><b>HPS:</b></td>
			<td><%=mapaNuevoCurso.getHsp() %></td>
<%
	}
	if(mapaNuevoPlan.getHfd().equals("S")){
%>
			<td><b>HFD:</b></td>
			<td><%=mapaNuevoCurso.getHfd() %></td>
<%
	}
	if(mapaNuevoPlan.getHei().equals("S")){
%>
			<td><b>HEI:</b></td>
			<td><%=mapaNuevoCurso.getHei() %></td>
<%
	}
%>
			<td><b>THS:</b></td>
			<td><%=mapaNuevoCurso.getThs() %></td>
			<td><b>CRS:</b></td>
			<td><%=mapaNuevoCurso.getCreditos() %></td>
			<td><b>HORAS TOTALES:</b></td>
			<td><%=mapaNuevoCurso.getHt() %></td>
		</tr>
		<tr>
			<td width="250px"><b>PROYECTO INTEGRADOR AL QUE SE VINCULA LA ASIGNATURA</b></td>
			<td width="600px" >
				<a href="cursoAccion?planId=<%=planId%>&versionId=<%=versionId%>&cursoId=<%=cursoId%>" class="btn btn-primary btn-sm">
				  <i class='icon-pencil icon-white'></i>
				</a>				
				<br>
				<%=mapaNuevoCurso.getProyecto().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %>
			</td>
		</tr>		
	</table>
	<br>
	<!-- Esta tabla es para la unidad -->
	<form id="formaUnidades" name="formaUnidades" action="editaUnidadAccion?planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId %>" method="post">
		<input type="hidden" id="Accion" name="Accion" />
		<input type="hidden" id="unidadId" name="unidadId" />
<%
	for(int i = 0; i < listUnidades.size(); i++){
		mapaNuevoUnidad = (MapaNuevoUnidad) listUnidades.get(i);
		String uid = mapaNuevoUnidad.getUnidadId();
		listActividades = mapaNuevoActividadU.getListUnidad(conEnoc, cursoId, uid, "ORDER BY TIPO, ACTIVIDAD_ID");
		listProductos 	= mapaNuevoProductoU.getListUnidad(conEnoc, cursoId, uid, "ORDER BY TIPO, PRODUCTO_ID");		
%>		
		<table style="width:100%">
			<tr>
				<td>
					<h3>					    
					    <a href="unidadAccion?planId=<%=planId%>&versionId=<%=versionId%>&cursoId=<%=cursoId%>&unidadId=<%=mapaNuevoUnidad.getUnidadId()%>" class="btn btn-primary btn-sm"><i class='icon-pencil icon-white'></i></a>					 
					    <a href="javascript:borrarUnidad('<%=uid %>');" class="btn btn-danger btn-sm"><i class='icon-trash icon-white'></i></a>
				    &nbsp;&nbsp;
						<%=mapaNuevoUnidad.getNombre() %>
				    </h3>						
				</td>
			</tr>
		</table>
		<br>
		<table style="border: solid 1px black;  table-layout: fixed;" border=1>
			<tr>
				<td align="center" width="4%" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>TIEMPO ESTIMADO</b></td>
				<td align="center" width="24%" style="border-bottom: solid 1px black; border-right: solid 1px black; text-align: justify;"><b>PRODUCTOS DE APRENDIZAJE</b></td>
				<td align="center" width="24%" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>CONTENIDO</b></td>
				<td align="center" width="24%" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>METODOLOGÍA<br><small class="text-muted">ACTIVIDADES DE APRENDIZAJE</small></b></td>
				<td align="center" width="24%" style="border-bottom: solid 1px black;"><b>EVALUACIÓN(Evidencias)</b></td>
			</tr>
			<tr>
				<td valign="top" width="100px" id="tiempo<%=uid %>" style="border-right: solid 1px black;">
					<%=mapaNuevoUnidad.getTiempo()%>
				</td>
				<td valign="top" width="120px" style="border-right: solid 1px black;">
					<table style="width:100%">
						<tr>
							<td colspan="2">
								<a href="productoAccion?planId=<%=planId%>&versionId=<%=versionId%>&cursoId=<%=cursoId%>&unidadId=<%=mapaNuevoUnidad.getUnidadId()%>" class="btn btn-primary btn-sm">
								  <i class='icon-pencil icon-white'></i>
								</a>
							</td>
						</tr>							
<%
		String tipoProducto = "0";
		String tipoNombre 	= "-";
		for(aca.plan.MapaNuevoProducto producto : listProductos){
%>
						<tr>
							<td width="6px" valign="top">-</td>
							<td ><%=aca.plan.MapaNuevoCursoUtil.cortaFrase(producto.getDescripcion(),40).replaceAll("\n","<br>").replaceAll(" ","&nbsp;")%></td>
						</tr>
<%		} %>
							
					</table>
				</td>
				<td valign="top" id="temas<%=uid %>" style="border-right: solid 1px black;">
					<%=aca.plan.MapaNuevoCursoUtil.cortaFrase(mapaNuevoUnidad.getTemas(),40).replaceAll("\n","<br>").replaceAll(" ","&nbsp;")%>
				</td>
				<td valign="top" width="120px" style="border-right: solid 1px black;">
					<table style="width:100%">
						<tr>
							<td colspan="2">
								<a href="actividadAccion?planId=<%=planId%>&versionId=<%=versionId%>&cursoId=<%=cursoId%>&unidadId=<%=mapaNuevoUnidad.getUnidadId()%>" class="btn btn-primary btn-sm">
								  <i class='icon-pencil icon-white'></i>
								</a>
							</td>
						</tr>
<%
		int row = 0;		
		for(aca.plan.MapaNuevoActividad actividad : listActividades){
			row++;						
%>
						<tr>
							<td width="6px" valign="top"><%=row%>. </td>
							<td><%=aca.plan.MapaNuevoCursoUtil.cortaFrase(actividad.getDescripcion(),40).replaceAll("\n","<br>").replaceAll(" ","&nbsp;")%></td>
						</tr>			
<%
		}
%>							
					</table>&nbsp;
				</td>
				<td valign="top" width="120px">
					<table style="width:100%">
						<tr>
							<td colspan="2">								  
							  <a href="evaluacionAccion?planId=<%=planId%>&versionId=<%=versionId%>&cursoId=<%=cursoId%>&unidadId=<%=mapaNuevoUnidad.getUnidadId()%>" class="btn btn-primary btn-sm">
								  <i class='icon-pencil icon-white'></i>
							  </a>	
							  <br>
								<%=aca.plan.MapaNuevoCursoUtil.cortaFrase(mapaNuevoUnidad.getAccionDocente(),40).replaceAll("\n","<br>").replaceAll(" ","&nbsp;")%>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<br>		
<%
	}
%>
	</form>
	<div id="nuevo"></div>
	<table style="width:850px" id="btnUnidad">
		<tr>
			<td align="center">
				<a href="unidadAccion?planId=<%=planId%>&versionId=<%=versionId%>&cursoId=<%=cursoId%>&unidadId=0" class="btn btn-success btn-sm">
					<i class='icon-plus icon-white'></i>&nbsp; Agregar Unidad
				</a>
			</td>
		</tr>
	</table>
	<br>
	<form id="formaCurso" name="formaCurso" action="editaUnidadAccion?planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId %>" method="post">
	<input type="hidden" id="escala" name="escala" value="De 0 a 10 puntos"/>
	<table style="width:850px" style="border: solid 1px black;">
		<tr>
			<td>&nbsp;</td>
		</tr>		
		<tr>
			<td><b>MEDIOS Y RECURSOS DID&Aacute;CTICOS:</b></td>
		</tr>
		<tr>
			<td><textarea id="mediosRecursos" name="mediosRecursos" onkeyup="checkSize(this, event, 1000);" class="1000" cols="100"><%=mapaNuevoCurso.getMediosRecursos() %></textarea></td>
		</tr>
		<tr>
			<td><b>ELEMENTOS DE EVALUACI&Oacute;N</b></td>
		</tr>
		<tr>
			<td><b>Diagn&oacute;stica:</b></td>
		</tr>
		<tr>
			<td><textarea id="eeDiagnostica" name="eeDiagnostica" onkeyup="checkSize(this, event, 1000);" class="1000" cols="100"><%=mapaNuevoCurso.getEeDiagnostica() %></textarea></td>
		</tr>
		<tr>
			<td><b>Formativa:</b></td>
		</tr>
		<tr>
			<td><textarea id="eeFormativa" name="eeFormativa" onkeyup="checkSize(this, event, 1000);" class="1000" cols="100"><%=mapaNuevoCurso.getEeFormativa() %></textarea></td>
		</tr>
		<tr>
			<td><b>Sumativa:</b></td>
		</tr>
		<tr>
			<td><textarea id="eeSumativa" name="eeSumativa" onkeyup="checkSize(this, event, 1000);" class="1000" cols="100"><%=mapaNuevoCurso.getEeSumativa() %></textarea></td>
		</tr>
		<tr>
			<td><b>ESCALA DE CALIFICACIONES (Ponderaci&oacute;n de las calificaciones):</b></td>
		</tr>
		<tr>
			<td>***<%=mapaNuevoCurso.getEscala()%></td>
		</tr>
	</table>
	</form>
	<br>
	<table>
		<tr>
			<td><input type="button" class="btn btn-primary" value="Guardar" onclick="revisaFormaCurso();" /></td>
		</tr>
	</table>
	<br>
	<form name="formaBiblio" action="editaUnidad?planId=<%=planId%>&versionId=<%=versionId%>&cursoId=<%=cursoId%>">
	<input type="hidden" id="planId" name="planId" value="<%=planId%>" />
		<input type="hidden" id="cursoId" name="cursoId" value="<%=cursoId%>" />
		<input type="hidden" id="versionId" name="versionId" value="<%=versionId%>" />
		<input type="hidden" id="Accion" name="Accion" value="<%=accion%>" />
		<input type="hidden" id="bibliografiaId" name="bibliografiaId" />
		<table style="width:850px" style="border: solid 1px black;" id="bibliografiaTotal">
			<tr>
				<td align="center" colspan="3"><b>BIBLIOGRAFIA TOTAL</b></td>
			</tr>
<%
		tipo = "";
		listBibliografias = mapaNuevoBibliografiaU.getListCurso(conEnoc, cursoId, "ORDER BY TIPO, BIBLIOGRAFIA");
		for(int j = 0; j < listBibliografias.size(); j++){
			mapaNuevoBibliografia = (MapaNuevoBibliografia) listBibliografias.get(j);
			if(!tipo.equals(mapaNuevoBibliografia.getTipo())){
				tipo = mapaNuevoBibliografia.getTipo();
%>
			<tr>
				<td colspan="3"><b><%=tipo.equals("1")?"Libros y revistas:":"Enlaces electr&oacute;nicos:" %></b></td>
			</tr>
<%
			}
%>
			<tr>
				<td width="65px" valign="top" style="border-right: solid 1px gray;">
	 				<a href="biblioAccion?planId=<%=planId%>&versionId=<%=versionId%>&cursoId=<%=cursoId%>&unidadId=<%=mapaNuevoUnidad.getUnidadId()%>&bibliografiaId=<%=mapaNuevoBibliografia.getBibliografiaId()%>&Accion=2" class="btn btn-primary btn-sm"><i class='icon-pencil icon-white'></i></a>					 
					<a href="javascript:borrarBibliografiaGlobal('<%=mapaNuevoBibliografia.getBibliografiaId() %>');" class="btn btn-danger btn-sm"><i class='icon-trash icon-white'></i></a>
				</td>
				<td style="border-right: solid 1px gray;" width="100px"><%=mapaNuevoBibliografia.getReferencia().replaceAll("\n","<br>").replaceAll(" ","&nbsp;").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;") %></td>
				<td id="bibliografia<%=mapaNuevoBibliografia.getBibliografiaId() %>"><%=mapaNuevoBibliografia.getBibliografia().replaceAll("\n","<br>").replaceAll(" ","&nbsp;").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;") %></td>
			</tr>
<%
		}
%>
			<tr>
				<td colspan="3" align="center">
				<a href="biblioAccion?planId=<%=planId%>&versionId=<%=versionId%>&cursoId=<%=cursoId%>&unidadId=0" class="btn btn-success btn-sm">
						<i class='icon-plus icon-white'></i>&nbsp; Agregar Bibliografia
					</a>
				</td>
			</tr>
		</table>
	</form>
	<br>
	<br>
	<table>
		<tr>
			<td><input type="button" class="btn btn-primary" value="Cerrar Materia" onclick="cerrarMateria();" /></td>
		</tr>
	</table>
	<script type="text/javascript">
		inicio();
	</script>
</div>	
</body>

<%@ include file= "../../cierra_enoc.jsp" %>