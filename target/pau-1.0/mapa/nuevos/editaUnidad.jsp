<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>


<%@page import="aca.catalogo.CatFacultad"%>
<%@page import="aca.catalogo.CatCarrera"%>
<%@page import="aca.plan.MapaNuevoUnidad"%>
<%@page import="aca.plan.MapaNuevoActividad"%>
<%@page import="aca.plan.MapaNuevoBibliografia"%>
<%@page import="aca.plan.MapaNuevoBiblioUnidad"%>
<%@page import="aca.acceso.Acceso"%>

<jsp:useBean id="mapaNuevoPlan" class="aca.plan.MapaNuevoPlan" scope="page"/>
<jsp:useBean id="mapaNuevoCurso" class="aca.plan.MapaNuevoCurso" scope="page"/>
<jsp:useBean id="mapaNuevoUnidad" class="aca.plan.MapaNuevoUnidad" scope="page"/>
<jsp:useBean id="mapaNuevoUnidadU" class="aca.plan.MapaNuevoUnidadUtil" scope="page"/>
<jsp:useBean id="mapaNuevoActividad" class="aca.plan.MapaNuevoActividad" scope="page"/>
<jsp:useBean id="mapaNuevoActividadU" class="aca.plan.MapaNuevoActividadUtil" scope="page"/>
<jsp:useBean id="mapaNuevoBibliografia" class="aca.plan.MapaNuevoBibliografia" scope="page"/>
<jsp:useBean id="mapaNuevoBibliografiaU" class="aca.plan.MapaNuevoBibliografiaUtil" scope="page"/>
<jsp:useBean id="mapaNuevoBiblioUnidad" class="aca.plan.MapaNuevoBiblioUnidad" scope="page"/>
<jsp:useBean id="mapaNuevoBiblioUnidadU" class="aca.plan.MapaNuevoBiblioUnidadUtil" scope="page"/>
<%
	String planId			= request.getParameter("planId");
	String versionId		= request.getParameter("versionId");
	String cursoId			= request.getParameter("cursoId");
	String opcion			= request.getParameter("Opcion")==null?"0":request.getParameter("Opcion");
	String institucion 		= (String)session.getAttribute("institucion");
	
	String tipo				= "";
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	ArrayList<MapaNuevoUnidad> listUnidades = mapaNuevoUnidadU.getListCurso(conEnoc, cursoId, "ORDER BY UNIDAD_ID");
	ArrayList<MapaNuevoActividad> listActividades = null;
	ArrayList<MapaNuevoBibliografia> listBibliografias = null;
	ArrayList<MapaNuevoBiblioUnidad> listBiblioUnidad = null;
	
	
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
%>
<head>
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

		var urlDefault = 'editaUnidadAccion?planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId %>';
		function guardar(){
			var url = urlDefault+
					  "&Accion=2"+
					  "&mediosRecursos="+$escape($("mediosRecursos"))+
					  "&eeDiagnostica="+$escape($("eeDiagnostica"))+
					  "&eeFormativa="+$escape($("eeFormativa"))+
					  "&eeSumativa="+$escape($("eeSumativa"))+
					  "&escala="+$escape($("escala"));
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
				}
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

		function nuevaBibliografiaGlobal(unidadId){
			var div = $("editaBibliografia");
			if(!div){//Si no existe el div... osea... si se mando llamar desde el boton de hasta abajo (Bibliografia Total)
				$(document.body).insert({
					bottom: '<div id="editaBibliografia" align="center"></div>'
				});
				
				div = $("editaBibliografia");
				div.setStyle({
					position: 'absolute',
					zIndex: '50',
					backgroundColor: 'white',
					border: 'solid 1px blue',
					width: '500px',
					height: '130px'
				});
				div.clonePosition($("btnAgregarBibliografiaGlobal"),{setWidth: false, setHeight: false});
				div.style.top = (div.offsetTop - div.offsetHeight + $("btnAgregarBibliografiaGlobal").offsetHeight)+"px";
			}
			div.innerHTML = '<table style="width:100%">'+
								'<tr title="Bibliografia completa">'+
									'<td><b>Bibliografia</b></td>'+
									'<td><textarea id="bibliografia" onkeyup="checkSize(this, event, 500);" class="500" cols="80"></textarea></td>'+
								'</tr>'+
								'<tr title="Abreviacion con la que se citar&aacute; la bibliografia (Ej. [perez, 2007])">'+
									'<td><b>Autor y Fecha</b></td>'+
									'<td><input type="text" class="text" id="referencia" size="40" maxlength="190" /></td>'+
								'</tr>'+
								'<tr>'+
									'<td><b><spring:message code="aca.Tipo"/></b></td>'+
									'<td>'+
										'<select id="tipoBibliografia">'+
											'<option value="1">Libros y Revistas</option>'+
											'<option value="2">Enlaces electr&oacute;nicos</option>'+
										'</select>'+
									'</td>'+
								'</tr>'+
								'<tr>'+
									'<td align="center" colspan="2">'+
										'<input type="button" class="button" value="Guardar" onclick="guardarBibliografiaGlobal(\''+unidadId+'\');"/>'+
										'<input type="button" class="button" value="Cancelar" onclick="cancelarNuevaBibliografia();" />'+
									'</td>'+
								'</tr>'+
							'</table>';
			$("bibliografia").focus();
			
		}

		function guardarBibliografiaGlobal(unidadId){
			var url = urlDefault+
			  "&Accion=22"+
			  "&unidadId="+unidadId+
			  "&bibliografia="+$escape($("bibliografia"))+
			  "&referencia="+$escape($("referencia"))+
			  "&tipo="+$("tipoBibliografia").value;
			  
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
				var url = urlDefault+
				  "&Accion=25"+
				  "&bibliografiaId="+bibliografiaId;
				  
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

		var tmpObjetivoAsignatura = null;
		function editaObjetivoAsignatura(obj){
			cancelarEditarObjetivo();
			tmpObjetivoAsignatura = obj.innerHTML;
			var div = $("editaObjetivoAsignatura");
			if(!div){
				$(document.body).insert({
					bottom: '<div id="editaObjetivoAsignatura">'+
								'<textarea id="objetivoAsignatura" onkeyup="checkSize(this, event, 1000);" class="1000" cols="100" rows="3">'+toString(obj.innerHTML)+'</textarea><br>'+
								'&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="button" value="Guardar" onclick="modificarObjetivoAsignatura();" />'+
								'<input type="button" class="button" value="Cancelar" onclick="cancelarEditaObjetivoAsignatura();" />'+
							'</div>'
				});
				div = $("editaObjetivoAsignatura");
			}
			div.setStyle({
				position: 'absolute',
				zIndex: '50',
				backgroundColor: 'white',
				border: 'solid 1px blue',
				width: '660px',
				height: '100px'
			});
			div.clonePosition($("objetivoAsignaturaTD"),{setWidth: false, setHeight: false});
			$("objetivoAsignatura").focus();
		}

		function cancelarEditaObjetivoAsignatura(){
			if(tmpObjetivoAsignatura){
				$("objetivoAsignaturaTD").innerHTML = tmpObjetivoAsignatura;
				tmpObjetivoAsignatura = null;
			}
			cerrarEditaObjetivoAsignatura();
		}

		function cerrarEditaObjetivoAsignatura(){
			if($("editaObjetivoAsignatura"))
				$("editaObjetivoAsignatura").remove();
		}

		function modificarObjetivoAsignatura(){
			var url = urlDefault+
			  "&Accion=19"+
			  "&objetivoAsignatura="+$escape($("objetivoAsignatura"));
			  
			new Ajax.Request(url,{
				method: "get",
				onSuccess: function(req){
					eval(req.responseText);
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para modificar el Objetivo de la Asignatura.\nIntentelo de nuevo");
				},
				onCreate: function(req){
					muestraCargando();
				},
				onComplete: function(req){
					ocultaCargando();
				}
			});
		}
	--></script>
</head>
<body>
	<table class="goback">
		<tr>
			<td><a href="materias?planId=<%=planId %>&versionId=<%=versionId %>">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
		</tr>
	</table>
	<table style="width:90%" align="center">
		<tr>
			<td class="titulo"><%=institucion.toUpperCase()%></td>
		</tr>
		<tr>
			<td class="titulo2"><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, mapaNuevoPlan.getCarreraId())) %></td>
		</tr>
		<tr>
			<td class="titulo2"><%=mapaNuevoPlan.getNombre() %></td>
		</tr>
	</table>
	<br>
	<table style="width:850px" align="center" style="border: solid 1px black;" border=1>
		<tr>
			<td width="180px"><b>ASIGNATURA:</b></td>
			<td><%=mapaNuevoCurso.getNombre() %></td>
			<td colspan="2"><b>SEMESTRE:</b></td>
			<td colspan="2"><%=mapaNuevoCurso.getCiclo() %></td>
			<td colspan="3"><b>CLAVE:</b></td>
			<td colspan="3"><%=mapaNuevoCurso.getClave() %></td>
			<td><b>SERIACIÓN</b></td>
			<td><%=mapaNuevoCurso.getSeriacion() %></td>
		</tr>
		<tr>
			<td width="180px"><b>OBJETIVO DE LA ASIGNATURA:</b></td>
			<td width="670px" colspan="13" id="objetivoAsignaturaTD" onclick="editaObjetivoAsignatura(this);" class="button"><%=mapaNuevoCurso.getDescripcion().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
		</tr>
		<tr>
			<td width="180px"><b>L&Iacute;NEA CURRICULAR:</b></td>
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
			<td colspan="6"><b>COMPETENCIA DEL PERFIL QUE ATIENDE LA ASIGNATURA:</b></td>
			<td colspan="8"><%=mapaNuevoCurso.getCompetencia().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
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
		listBiblioUnidad = mapaNuevoBiblioUnidadU.getListCursoUnidad(conEnoc, cursoId, uid, "ORDER BY (SELECT TIPO FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA"+ 
				" WHERE CURSO_ID = ENOC.MAPA_NUEVO_BIBLIO_UNIDAD.CURSO_ID AND BIBLIOGRAFIA_ID = ENOC.MAPA_NUEVO_BIBLIO_UNIDAD.BIBLIOGRAFIA_ID), ID");
%>
		<div id="unidad<%=mapaNuevoUnidad.getUnidadId() %>" align="center" onmouseover="this.style.backgroundColor = '#dddddd';" onmouseout="this.style.backgroundColor = '';">
			<table style="width:850px" align="center">
				<tr>
					<td>
						<img class="button" title="Editar" src="../../imagenes/editar.gif" title="Editar" onclick="editarUnidad('<%=uid %>');" />
						<img class="button" title="Eliminar" src="../../imagenes/no.png" title="Eliminar" onclick="borrarUnidad('<%=uid %>');" />
					</td>
				</tr>
			</table>
			<br>
			<table style="width:850px" align="center" style="border: solid 1px black;">
				<tr>
					<td align="center" width="100px" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>TIEMPO ESTIMADO</b></td>
					<td align="center" width="120px" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>NOMBRE Y OBJETIVO DE LA UNIDAD</b></td>
					<td align="center" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>TEMAS Y SUBTEMAS</b></td>
					<td align="center" width="120px" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>ACTIVIDADES DE APRENDIZAJE</b></td>
					<td align="center" width="120px" style="border-bottom: solid 1px black;"><b>BIBLIOGRAF&Iacute;A</b></td>
				</tr>
				<tr>
					<td valign="top" width="100px" id="tiempo<%=uid %>" style="border-right: solid 1px black;"><%=mapaNuevoUnidad.getTiempo().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
					<td valign="top" width="120px" style="border-right: solid 1px black;">
						<table style="width:100%">
							<tr>
								<td colspan="2" id="nombre<%=uid %>"><%=mapaNuevoUnidad.getNombre().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
							</tr>
							<tr>
								<td colspan="2">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="2"><b>OBJETIVO:</b></td>
							</tr>
<%
		tipo = "";
		for(int j = 0; j < listActividades.size(); j++){
			mapaNuevoActividad = (MapaNuevoActividad) listActividades.get(j);
			
			if(!tipo.equals(mapaNuevoActividad.getTipo().trim())){
				tipo = mapaNuevoActividad.getTipo().trim();
%>
							<tr>
								<td colspan="2"><b><%=tipo.equals("1")?"Conocimientos:":tipo.equals("2")?"Habilidades:":"Actitudes:" %></b></td>
							</tr>
<%
			}
%>
							<tr>
								<td width="6px" valign="top">-</td>
								<td id="objetivo<%=uid %>-<%=mapaNuevoActividad.getActividadId() %>" onclick="editarObjetivo('<%=uid %>', '<%=mapaNuevoActividad.getActividadId() %>');" class="button"><%=mapaNuevoActividad.getObjetivo().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
							</tr>
<%
		}
%>
							<tr>
								<td colspan="2"><input type="button" class="button" value="Agregar" id="btnAgregarObjetivo<%=uid %>" onclick="nuevoObjetivo('<%=uid %>');" /></td>
							</tr>
						</table>
					</td>
					<td valign="top" id="temas<%=uid %>" style="border-right: solid 1px black;"><%=mapaNuevoUnidad.getTemas().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
					<td valign="top" width="120px" style="border-right: solid 1px black;">
						<table style="width:100%">
<%
		tipo = "";
		for(int j = 0; j < listActividades.size(); j++){
			mapaNuevoActividad = (MapaNuevoActividad) listActividades.get(j);
			if(!tipo.equals(mapaNuevoActividad.getTipo())){
				tipo = mapaNuevoActividad.getTipo();
%>
							<tr>
								<td colspan="2"><b><%=tipo.equals("1")?"CONOCIMIENTO (Saber)":tipo.equals("2")?"HABILIDAD (Saber hacer)":"ACTITUD (Saber ser)" %></b></td>
							</tr>
<%
			}
%>
							<tr>
								<td colspan="2">Actividad:</td>
							</tr>
							<tr>
								<td width="6px" valign="top">-</td>
								<td id="actividad<%=uid %>-<%=mapaNuevoActividad.getActividadId() %>" onclick="editarActividad('<%=uid %>', '<%=mapaNuevoActividad.getActividadId() %>');" class="button"><%=mapaNuevoActividad.getDescripcion().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
							</tr>
							<tr>
								<td colspan="2">Criterio de desempe&ntilde;o</td>
							</tr>
							<tr>
								<td width="6px" valign="top">-</td>
								<td id="criterio<%=uid %>-<%=mapaNuevoActividad.getActividadId() %>" onclick="editarCriterio('<%=uid %>', '<%=mapaNuevoActividad.getActividadId() %>');" class="button"><%=mapaNuevoActividad.getCriterio().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
							</tr>
<%
		}
%>
						</table>&nbsp;
					</td>
					<td valign="top" width="120px">
						<table style="width:100%">
<%
		tipo = "";
		for(int j = 0; j < listBiblioUnidad.size(); j++){
			mapaNuevoBiblioUnidad = (MapaNuevoBiblioUnidad) listBiblioUnidad.get(j);
			mapaNuevoBibliografia.mapeaRegId(conEnoc, cursoId, mapaNuevoBiblioUnidad.getBibliografiaId());
			if(!tipo.equals(mapaNuevoBibliografia.getTipo())){
				tipo = mapaNuevoBibliografia.getTipo();
%>
							<tr>
								<td colspan="2"><b><%=tipo.equals("1")?"Libros y revistas:":"Enlaces electr&oacute;nicos:" %></b></td>
							</tr>
<%
			}
%>
							<tr>
								<td width="6px" valign="top">-</td>
								<td><%=mapaNuevoBibliografia.getReferencia().replaceAll(" ","&nbsp;").replaceAll("\n","<br>").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;") %></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td id="bibliografia<%=uid %>-<%=mapaNuevoBiblioUnidad.getBibliografiaId() %>-<%=mapaNuevoBiblioUnidad.getId() %>" onclick="editarBibliografia('<%=uid %>', '<%=mapaNuevoBiblioUnidad.getBibliografiaId() %>', '<%=mapaNuevoBiblioUnidad.getId() %>');" class="button"><%=mapaNuevoBiblioUnidad.getEspecificacion().replaceAll(" ","&nbsp;").replaceAll("\n","<br>").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;") %></td>
							</tr>
<%
		}
%>
							<tr>
								<td colspan="2">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="2"><input type="button" class="button" value="Agregar" id="btnAgregarBibliografia<%=uid %>" onclick="nuevaBibliografia('<%=uid %>');" /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<br>
		</div>
<%
	}
%>
	</form>
	<div id="nuevo"></div>
	<table style="width:850px" align="center" style="border: dotted 1px gray;" id="btnUnidad">
		<tr>
			<td align="center" class="button" height="20px" onclick="nuevaUnidad();">Agregar Unidad</td>
		</tr>
	</table>
	<br>
	<form id="formaCurso" name="formaCurso" action="editaUnidadAccion?planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId %>" method="post">
	<table style="width:850px" align="center" style="border: solid 1px black;">
		<tr>
			<td><b>ACCI&Oacute;N DOCENTE (Metodolog&iacute;a):</b></td>
		</tr>
		<tr>
			<td>
				<table style="width:100%" id="tablaAccionDocente">
<%
	for(int i = 0; i < listUnidades.size(); i++){
		mapaNuevoUnidad = (MapaNuevoUnidad) listUnidades.get(i);
		String uid = mapaNuevoUnidad.getUnidadId();
%>
					<tr id="accionDocente<%=uid %>">
						<td id="accionDocenteNombre<%=uid %>" width="100px" style="border-bottom: solid 1px gray;"><%=mapaNuevoUnidad.getNombre().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
						<td id="accionDocenteDatos<%=uid %>" class="button" onclick="editarAccionDocente('<%=uid %>');" style="border-bottom: solid 1px gray;"><%=mapaNuevoUnidad.getAccionDocente().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
					</tr>
<%
	}
%>
				</table>
			</td>
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
<%-- 			<td><textarea id="escala" name="escala" onkeyup="checkSize(this, event, 1000);" class="1000" cols="60"><%=mapaNuevoCurso.getEscala().replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;").replaceAll("Ñ","&Ntilde;").replaceAll("Á","&Aacute;").replaceAll("É","&Eacute;").replaceAll("Í","&Iacute;").replaceAll("Ó","&Oacute;").replaceAll("Ú","&Uacute;") %></textarea></td> --%>
			<td><textarea id="escala" name="escala" onkeyup="checkSize(this, event, 1000);" class="1000" cols="60"><%=mapaNuevoCurso.getEscala()%></textarea></td>
		</tr>
	</table>
	</form>
	<br>
	<table style="margin: 0 auto;">
		<tr>
			<td><input type="button" class="button" value="Guardar" onclick="revisaFormaCurso();" /></td>
		</tr>
	</table>
	<br>
	<table style="width:850px" align="center" style="border: solid 1px black;" id="bibliografiaTotal">
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
			<td width="35px" valign="top" style="border-right: solid 1px gray;">
				<img class="button" title="Editar" src="../../imagenes/editar.gif" title="Editar" onclick="editarBibliografiaGlobal('<%=mapaNuevoBibliografia.getBibliografiaId() %>');" />
				<img class="button" title="Eliminar" src="../../imagenes/no.png" title="Eliminar" onclick="borrarBibliografiaGlobal('<%=mapaNuevoBibliografia.getBibliografiaId() %>');" />
			</td>
			<td style="border-right: solid 1px gray;" width="100px"><%=mapaNuevoBibliografia.getReferencia().replaceAll("\n","<br>").replaceAll(" ","&nbsp;").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;") %></td>
			<td id="bibliografia<%=mapaNuevoBibliografia.getBibliografiaId() %>"><%=mapaNuevoBibliografia.getBibliografia().replaceAll("\n","<br>").replaceAll(" ","&nbsp;").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;") %></td>
		</tr>
<%
	}
%>
		<tr>
			<td colspan="3" align="center"><input type="button" class="button" id="btnAgregarBibliografiaGlobal" value="Agregar Bibliografia Global" onclick="nuevaBibliografiaGlobal('-');" /></td>
		</tr>
	</table>
	<br>
	<br>
	<table style="margin: 0 auto;">
		<tr>
			<td><input type="button" class="btn btn-primary" value="Cerrar Materia" onclick="cerrarMateria();" /></td>
		</tr>
	</table>
	<script type="text/javascript">
		inicio();
	</script>
</body>

<%@ include file= "../../cierra_enoc.jsp" %>