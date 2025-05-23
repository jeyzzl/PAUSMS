<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.plan.spring.MapaNuevoPlan"%>
<%@page import="aca.plan.spring.MapaNuevoCurso"%>
<%@page import="aca.plan.spring.MapaNuevoUnidad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String planId			= request.getParameter("planId")==null?"0":request.getParameter("planId");
	String versionId		= request.getParameter("versionId")==null?"0":request.getParameter("versionId");	
	String respuesta		= (String)request.getAttribute("respuesta");	
	String idioma			= (String)request.getAttribute("idioma");
	String ciclo			= "";
	String accesos			= "";
	
	int	accion				= Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));
	
	boolean esAdmin			= Boolean.parseBoolean(session.getAttribute("admin")+"");
	boolean esCotejador		= (boolean)request.getAttribute("esCotejador");
	
	MapaNuevoPlan mapaNuevoPlan 		= (MapaNuevoPlan)request.getAttribute("mapaNuevoPlan");
	List<MapaNuevoCurso> lisCursos 		= (List<MapaNuevoCurso>) request.getAttribute("lisCursos");
	HashMap<String,String> mapaMaestros = (HashMap<String,String>)request.getAttribute("mapaMaestros");
	HashMap<String,String> mapaUnidades = (HashMap<String,String>)request.getAttribute("mapaUnidades");
%>
<head>
	<style type="text/css">
		table{
			border-spacing: 0;
		}
		
		tr.button{
			background-color: #CCCCCC;
		}
		
		tr.button td{
			border-top: solid 1px #EEEEEE;
			border-bottom: solid 1px gray;
		}
		
		th{
			border-right: solid 1px white;
		}
	</style>
	<script type="text/javascript">
	
		// "OJO" Esta función incluye codigo java 'puro' mezclado con codigo 'java script'
		function gotoTematica(planId, versionId, cursoId, maestros){			
<%		
			if(esCotejador){
				if (idioma.equals("E")){
%>
					document.location = "editaUnidad?planId="+planId+"&versionId="+versionId+"&cursoId="+cursoId;
<%				}else{ %>			
					document.location = "editaUnidadI?planId="+planId+"&versionId="+versionId+"&cursoId="+cursoId;
<%				}	
			}else{ //Si no es administrador
%>			
				if(maestros.indexOf('<%=codigoPersonal %>') != -1){ //Si le pertenece la materia 
<%					if ( idioma.equals("E") ){  %>					
						document.location = "editaUnidad?planId="+planId+"&versionId="+versionId+"&cursoId="+cursoId;
<%					}else{ %>					
						document.location = "editaUnidadI?planId="+planId+"&versionId="+versionId+"&cursoId="+cursoId;
<%					} %>				
				}else{ //Si no le pertenece la materia quiere decir que es coordinador
<%					if ( idioma.equals("E") ){ %>					
						document.location = "verMateria?planId="+planId+"&versionId="+versionId+"&cursoId="+cursoId;
<%					}else{ %>					
						document.location = "verMateriaI?planId="+planId+"&versionId="+versionId+"&cursoId="+cursoId;
<%					} %>				
				}
<%			} %>
		}

		function borrar(planId, versionId, cursoId){
			if(confirm("Esta seguro que desea borrar el curso?")){
				document.location = 'materias?Accion=3&planId='+planId+'&versionId='+versionId+'&cursoId='+cursoId;
			}
		}

		//---------- Seccion de copiar -------------------
		
		var defaultURL = "materiasAccion?planIdDestino=<%=planId %>&versionIdDestino=<%=versionId %>";
		
		function mostrarPlanes(obj, cursoIdDestino){
			var div = $("divCopiar");
			if(!div){
				$(document.body).insert({
					bottom: '<div id="divCopiar" align="center"><img src="../../imagenes/cargando.gif" /></div>'
				});
				div = $("divCopiar");
			}

			div.setStyle({
				position: "absolute",
				zIndex: "100",
				width: "450px",
				height: "250px",
				backgroundColor: "white",
				border: "solid 1px blue",
				overflow: "auto"
			});
			div.clonePosition(obj,{setWidth: false, setHeight: false});
			div.style.left = (div.offsetLeft - div.offsetWidth + obj.offsetWidth)+"px";

			var url = defaultURL+
			  "&Accion=5"+
			  "&cursoIdDestino="+cursoIdDestino;
			  
			new Ajax.Request(url,{
				method: "get",
				onSuccess: function(req){
					$("divCopiar").innerHTML = req.responseText;
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para cargar los Planes.\nIntentelo de nuevo");
					$("divCopiar").remove();
				}
			});
		}

		function mostrarMaterias(planIdOrigen, versionIdOrigen, cursoIdDestino){
			var div = $("divCopiar");
			div.innerHTML = '<img src="../../imagenes/cargando.gif" />';

			var url = defaultURL+
			  "&Accion=6"+
			  "&planIdOrigen="+planIdOrigen+
			  "&versionIdOrigen="+versionIdOrigen+
			  "&cursoIdDestino="+cursoIdDestino;
			  
			new Ajax.Request(url,{
				method: "get",
				onSuccess: function(req){
					$("divCopiar").innerHTML = req.responseText;
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para cargar las Materias.\nIntentelo de nuevo");
					$("divCopiar").remove();
				}
			});
		}

		function copiarDatos(planIdOrigen, versionIdOrigen, cursoIdOrigen, nombreOrigen, cursoIdDestino){
			if(confirm('Esta seguro que desea copiar los datos de la materia "'+nombreOrigen+'" a la seleccionada?')){
				var url = defaultURL+
				  "&Accion=7"+
				  "&planIdOrigen="+planIdOrigen+
				  "&versionIdOrigen="+versionIdOrigen+
				  "&cursoIdOrigen="+cursoIdOrigen+
				  "&cursoIdDestino="+cursoIdDestino;
				  
				new Ajax.Request(url,{
					method: "get",
					onSuccess: function(req){
						eval(req.responseText);
					},
					onFailure: function(req){
						alert("No se pudo conectar a la pagina para cargar las Materias.\nIntentelo de nuevo");
						$("divCopiar").remove();
					}
				});
			}
		}

		//---------- Seccion de traspaso ------------
		
		var defaultURLTraspaso = "materiasAccion?planIdOrigen=<%=planId %>&versionIdOrigen=<%=versionId %>";

		function mostrarPlanesTraspaso(obj, cursoIdOrigen, nombreOrigen){
			var div = $("divCopiar");
			if(!div){
				$(document.body).insert({
					bottom: '<div id="divCopiar" align="center"><img src="../../imagenes/cargando.gif" /></div>'
				});
				div = $("divCopiar");
			}

			div.setStyle({
				position: "absolute",
				zIndex: "100",
				width: "450px",
				height: "250px",
				backgroundColor: "white",
				border: "solid 1px blue",
				overflow: "auto"
			});
			div.clonePosition(obj,{setWidth: false, setHeight: false});
			div.style.left = (div.offsetLeft - div.offsetWidth + obj.offsetWidth)+"px";

			var url = defaultURLTraspaso+"&Accion=8"+"&cursoIdOrigen="+cursoIdOrigen+"&nombreOrigen="+nombreOrigen;
			  
			new Ajax.Request(url,{
				method: "get",
				onSuccess: function(req){
					$("divCopiar").innerHTML = req.responseText;
				},
				onFailure: function(req){
					alert("No se pudo conectar a la pagina para cargar los Planes.\nIntentelo de nuevo");
					$("divCopiar").remove();
				}
			});
		}

		function copiarMateria(cursoIdOrigen, nombreOrigen, planIdDestino, versionIdDestino){
			if(confirm('Esta seguro que desea copiar la materia "'+nombreOrigen+'" al plan seleccionado?')){
				var url = defaultURLTraspaso+
				  "&Accion=9"+
				  "&planIdDestino="+planIdDestino+
				  "&versionIdDestino="+versionIdDestino+
				  "&cursoIdOrigen="+cursoIdOrigen;
				  
				new Ajax.Request(url,{
					method: "get",
					onSuccess: function(req){
						eval(req.responseText);
					},
					onFailure: function(req){
						alert("No se pudo conectar a la pagina para cargar las Materias.\nIntentelo de nuevo");
						$("divCopiar").remove();
					}
				});
			}
		}

		//------------------ Seccion de mensaje ---------------

		var tMensaje;
		var theWidth, theHeight;
		function muestraMensaje(mensaje, colorFondo){
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
	</script>
</head>
<body>
<div class="container-fluid">
	<h2>Materias de <%=planId %><small class="text-muted fs-4"> ( <%=mapaNuevoPlan.getNombre() %> - Version <%=mapaNuevoPlan.getVersionNombre() %> )</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a href="planes" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
		&nbsp;&nbsp;
<%	if(esAdmin){ %>		
		<a href="editaMateria?planId=<%=planId %>&versionId=<%=versionId %>" class="btn btn-success"><spring:message code='aca.Nuevo'/></a>		
<%	} %>
		
	</div>	
<%
	if(!respuesta.equals("")){
%>
	<table style="margin: 0 auto;"><tr><td align="center"><%=respuesta %></td></tr></table>
<%
	}
%>		
	<table class="table table-sm table-bordered table-striped">
<%
	int row=0;
	for(MapaNuevoCurso mapaNuevoCurso : lisCursos){
		row++;
		if(!ciclo.equals(mapaNuevoCurso.getCiclo())){ 
			ciclo = mapaNuevoCurso.getCiclo();
%>		
		<tr>
			<td colspan="20" style="text-align: left;"><h3>Semestre <%=ciclo %></h3></td>
		</tr>
		<tr class="table-info">
<%
			if(esAdmin || accesos.indexOf(mapaNuevoPlan.getCarreraId()) != -1 || esCotejador){
%>
			<th width="60px"><spring:message code="aca.Operacion"/></th>
<%
			}
%>
			<th width="20px"><spring:message code="aca.Numero"/></th>
<% 			if(mapaNuevoPlan.getIdioma().equals("E")){%>
			<th width="30px"><spring:message code="aca.Clave"/></th>
			<th>Asignatura</th>
			<th width="250px"><spring:message code="aca.Maestro"/></th>
			<th width="30px" class="right">Ciclo</th>
			<th width="30px" class="right">Cred.</th>
			<th width="30px" class="right">HTS</th>
			<th width="30px" class="right">HPS</th>
			<th width="30px" class="right">HEI</th>
			<th width="30px" class="right">THS</th>
			<th width="30px" class="right">HT</th>
			<th width="30px" class="right">HSS</th>
			<th width="30px" class="right">HAS</th>
			<th width="70px">Nivel de Revisión</th>
			<th width="70px">N° Unidades</th>
			<th width="70px" class="ayuda mensaje Copie los datos de alguna otra materia a la seleccionada con esta columna"><spring:message code='aca.Copiar'/></th>
<% 			}else{%>
			<th width="30px">Id</th>
			<th>Subject</th>
			<th width="250px">Teacher</th>
			<th width="30px" class="right">Ciclo</th>
			<th width="30px" class="right">Credits</th>
			<th width="30px" class="right">HTS?</th>
			<th width="30px" class="right">HPS?</th>
			<th width="30px" class="right">HEI?</th>
			<th width="30px" class="right">THS?</th>
			<th width="30px" class="right">HT?</th>
			<th width="30px" class="right">HSS?</th>
			<th width="30px" class="right">HAS?</th>
			<th width="70px">Revision Level</th>
			<th width="70px">N° Units</th>
			<th width="70px" class="ayuda mensaje Copie los datos de alguna otra materia a la seleccionada con esta columna">Copy</th>
<% 			}%>			
<%
			if(esAdmin){
				
				if(mapaNuevoPlan.getIdioma().equals("E")){
%>
			<th width="70px" class="ayuda mensaje Copie la materia, junto con su cabecera, a otro plan">Transferir</th>
			<th>Imprimir</th>
			
<%				}else{
	
%>	
			<th width="70px" class="ayuda mensaje Copie la materia, junto con su cabecera, a otro plan">Transfer</th>
			<th>Print</th>
<% 	
				}
			}
%>
		</tr>
<%
		}
%>
		<tr>
<%		
		if(esAdmin){//Si es administrador			
%>
			<td width="60px" align="center">
				<a href="editaMateria?planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=mapaNuevoCurso.getCursoId() %>" class="btn btn-primary btn-sm"><i class="fas fa-edit"></i></a>					 
				<a href="javascript:borrar('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>');" class="btn btn-danger btn-sm"><i class="fas fa-trash-alt"></i></i></a>
			</td>
<%
		}else if(accesos.indexOf(mapaNuevoPlan.getCarreraId()) != -1 || esCotejador){			
			if(mapaNuevoCurso.getEstado().equals("1") || mapaNuevoCurso.getEstado().equals("2") || esCotejador){				
%>
			<td width="60px" align="center">
				<img class="button" title="Editar" src="../../imagenes/editar.gif" title="Modificar" onclick="document.location = 'editaMateria?planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=mapaNuevoCurso.getCursoId() %>';" />
			</td>
<%
			}else{
%>
			<td width="60px">&nbsp;</td>
<%
			}
		}
%>
			<td width="20px" align="center" onclick="gotoTematica('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getCodigoMaestro() %>');"><%=row%></td>
			<td width="30px" align="center" onclick="gotoTematica('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getCodigoMaestro() %>');">
			<%=(mapaNuevoCurso.getCodigoMaestro().indexOf(codigoPersonal)!=-1)?"<b>":""%>
			<%=mapaNuevoCurso.getClave() %>
			<%=(mapaNuevoCurso.getCodigoMaestro().indexOf(codigoPersonal)!=-1)?"</b>":""%>
			</td>
			<td onclick="gotoTematica('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getCodigoMaestro() %>');">
			<%=(mapaNuevoCurso.getCodigoMaestro().indexOf(codigoPersonal)!=-1)?"<b>":""%>
			<%=mapaNuevoCurso.getNombre() %>
			<%=(mapaNuevoCurso.getCodigoMaestro().indexOf(codigoPersonal)!=-1)?"</b>":""%>
			</td>
			<td width="250px" align="center" onclick="gotoTematica('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getCodigoMaestro() %>');">
<%
		String[] maestros = mapaNuevoCurso.getCodigoMaestro().split("-");
		for(int j = 1; j < maestros.length; j++){
			String maestroNombre = "";
			if (mapaMaestros.containsKey(maestros[j])){
				maestroNombre = mapaMaestros.get(maestros[j]);
			}
%>
				<p class="ayuda alumno <%=maestros[j] %>">
				<%=maestros[j].equals(codigoPersonal)?"<b>":""%>
					<%=maestros[j] %> <%=maestroNombre%>
				<%=maestros[j].equals(codigoPersonal)?"</b>":""%>	
				</p>
<%
		}
		if(maestros.length == 0){
%>
				&nbsp;
<%
		}
		String numUnidades = "0";
		if (mapaUnidades.containsKey(mapaNuevoCurso.getCursoId())){
			numUnidades = mapaUnidades.get(mapaNuevoCurso.getCursoId());
		}
%>
			</td>
			<td width="30px" class="right" onclick="gotoTematica('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getCodigoMaestro() %>');"><%=mapaNuevoCurso.getCiclo() %></td>
			<td width="30px" class="right" onclick="gotoTematica('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getCodigoMaestro() %>');"><%=mapaNuevoCurso.getCreditos() %></td>
			<td width="30px" class="right" onclick="gotoTematica('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getCodigoMaestro() %>');"><%=mapaNuevoCurso.getHst() %></td>
			<td width="30px" class="right" onclick="gotoTematica('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getCodigoMaestro() %>');"><%=mapaNuevoCurso.getHsp() %></td>
			<td width="30px" class="right" onclick="gotoTematica('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getCodigoMaestro() %>');"><%=mapaNuevoCurso.getHei() %></td>
			<td width="30px" class="right" onclick="gotoTematica('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getCodigoMaestro() %>');"><%=mapaNuevoCurso.getThs() %></td>
			<td width="30px" class="right" onclick="gotoTematica('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getCodigoMaestro() %>');"><%=mapaNuevoCurso.getHt() %></td>
			<td width="30px" class="right" onclick="gotoTematica('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getCodigoMaestro() %>');"><%=mapaNuevoCurso.getHss() %></td>
			<td width="30px" class="right" onclick="gotoTematica('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getCodigoMaestro() %>');"><%=mapaNuevoCurso.getHas() %></td>
			<td width="70px" align="center" onclick="gotoTematica('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getCodigoMaestro() %>');"><%=mapaNuevoCurso.getEstado().equals("1")?"Maestro":mapaNuevoCurso.getEstado().equals("2")?"Coordinador":mapaNuevoCurso.getEstado().equals("3")?"Administrador":"Terminado" %></td>
			<td width="70px" align="center" onclick="gotoTematica('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getCodigoMaestro() %>');"><%=numUnidades %></td>
<%
		if(numUnidades.equals("0") && mapaNuevoCurso.getCodigoMaestro().indexOf(codigoPersonal) != -1){
%>
			<td width="70px" align="center" class="ayuda mensaje Copie los datos de alguna otra materia a esta"><img class="button" title="Editar" src="../../imagenes/editar.gif" onclick="mostrarPlanes(this, '<%=mapaNuevoCurso.getCursoId() %>');" /></td>
<%
			if(esAdmin){
%>
			<td width="70px" onclick="gotoTematica('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getCodigoMaestro() %>');"></td>
<%     			if(mapaNuevoPlan.getIdioma().equals("E")){ %>					
			       <td width="30px" align="center"><img class="button" src="../../imagenes/printer.gif" onclick="location.href='muestraMateriaPdf?planId=<%=mapaNuevoCurso.getPlanId() %>&versionId=<%=mapaNuevoCurso.getVersionId() %>&cursoId=<%=mapaNuevoCurso.getCursoId() %>';" /></td>
<%				}else{   %>
				   <td width="30px" align="center"><img class="button" src="../../imagenes/printer.gif" onclick="location.href='muestraMateriaPdfI?planId=<%=mapaNuevoCurso.getPlanId() %>&versionId=<%=mapaNuevoCurso.getVersionId() %>&cursoId=<%=mapaNuevoCurso.getCursoId() %>';" /></td>
<% 				
				}
			}
%>
<%
		}else{
%>
			<td width="70px" onclick="gotoTematica('<%=planId %>', '<%=versionId %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getCodigoMaestro() %>');">&nbsp;</td>
<%
			if(esAdmin){
%>
			<td width="70px" align="center" class="ayuda mensaje Copie la materia, junto con su cabecera, a otro plan"><img class="button" title="Editar" src="../../imagenes/editar.gif" onclick="mostrarPlanesTraspaso(this, '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getNombre() %>');" /></td>
<%     			if(mapaNuevoPlan.getIdioma().equals("E")){ %>			
					<td width="30px" align="center"><img class="button" src="../../imagenes/printer.gif" onclick="location.href='muestraMateriaPdf?planId=<%=mapaNuevoCurso.getPlanId() %>&versionId=<%=mapaNuevoCurso.getVersionId() %>&cursoId=<%=mapaNuevoCurso.getCursoId() %>';" /></td>	
<%
				}else{
%>				
					<td width="30px" align="center"><img class="button" src="../../imagenes/printer.gif" onclick="location.href='muestraMateriaPdfI?planId=<%=mapaNuevoCurso.getPlanId() %>&versionId=<%=mapaNuevoCurso.getVersionId() %>&cursoId=<%=mapaNuevoCurso.getCursoId() %>';" /></td>
<%				
				}
			}
			
		}
%>
		</tr>
<%
	}
%>
	</table>
	<br>
	<table style="width:100%">
	  <tr><td class="end"></td></tr>
	</table>
</div>	
</body>