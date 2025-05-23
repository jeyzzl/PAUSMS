<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.investiga.spring.InvProyecto"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.vista.spring.Maestros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String codigo			= session.getAttribute("codigoPersonal")==null?"0":(String) session.getAttribute("codigoPersonal");	
	String proyectoId 		= request.getParameter("ProyectoId")==null?"0":request.getParameter("ProyectoId");	
	String nombreUsuario	= (String)request.getAttribute("usuarioNombre");	
	// facultades donde el usuario es referente
	String facultades		= (String)request.getAttribute("facultad");
	boolean admin			= false;
	boolean adminEtica		= false;
		
	// Usuarios administradores
	if (codigo.equals("9800308")||codigo.equals("9800079") || codigo.equals("9800306") || codigo.equals("9801139") 
		||codigo.equals("9801092") || codigo.equals("9830367") || codigo.equals("9801240") || codigo.equals("9830152") || codigo.equals("9800512")) admin = true;
	if (codigo.equals("9800330")) adminEtica = true;	
	
	// Lista de proyectos de investigación 
	List<InvProyecto> lisProyectos 		= (List<InvProyecto>)request.getAttribute("lisProyectos");
	
	//HashMap de comentarios
	HashMap<String, String> mapaComentarios		= (HashMap<String,String>)request.getAttribute("mapaComentarios");
	HashMap<String, Maestros> mapaEmpleados		= (HashMap<String,Maestros>)request.getAttribute("mapaEmpleados");
	HashMap<String, CatCarrera> mapaCarreras	= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaArchivos			= (HashMap<String,String>)request.getAttribute("mapaArchivos");
%>
<html>
<head>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">		
	body{
		background : white;
	}
	 	
		div.scroll_vertical::-webkit-scrollbar-track
		{
			-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
			border-radius: 10px;
			background-color: #F5F5F5;
		}
		
		div.scroll_vertical::-webkit-scrollbar
		{
			width: 12px;
			background-color: #F5F5F5;
		}
		
		div.scroll_vertical::-webkit-scrollbar-thumb
		{
			border-radius: 10px;
			-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
			background-color: #555;
		}			
		div.scroll_vertical {
			height: 85px;
			overflow: auto;
			padding: 8px;
		}
	</style>
</head>
<body>	
	<div class="container-fluid">		
		<h2>Listado de proyectos<small class="text-muted fs-5">&nbsp;(Usuario:<%=codigo%> - <%=nombreUsuario%>)</small></h2>
		<div class="alert alert-info">
			<input type="text" class="search-query form-control" placeholder="Buscar" id="buscar" style="width:200px;">
		</div>
		<table class="table table-sm table-bordered" id="table"> 
			<thead class="table-info">
				<tr class="info">
					<th width="4%">#</th>
					<th width="4%">Referencia</th>
					<th width="16%">Proyecto/Protocolo</th>
					<th width="6%"><spring:message code="aca.Carrera"/></th>
					<th width="6%"><spring:message code="aca.Nombre"/></th>
					<th width="9%"><spring:message code="aca.Tipo"/></th>
					<th width="7%">Linea</th>
					<th width="5%">Fecha Ini.</th>
					<th width="6%">Fecha Fin.</th>
					<th width="8%"><spring:message code="aca.Documento"/></th>
					<th width="5%"><spring:message code="aca.Estado"/></th>
				</tr>
			</thead>
<%
	int row = 0;
	String nombreDepartamento = ""; 
	for (int i=0; i<lisProyectos.size();i++){
		InvProyecto proyectos = (InvProyecto) lisProyectos.get(i);
		row++;
		
		String nombreTipo = " ";
		if(proyectos.getTipo().equals("1")){
			nombreTipo = "Académico-científica";
		}else if(proyectos.getTipo().equals("2")){
			nombreTipo = "Investigación institucional";
		}else if(proyectos.getTipo().equals("3")){
			nombreTipo = "Investigación educativa";
		}else{
			nombreTipo = "Desarrollo de habilidades de investigación";
		}
		
		boolean tienePdf 		= false;
		
		
		// Verifica si existe la imagen	
		String proyecto = application.getRealPath("/investigacion/proyecto/archivos/"+proyectos.getProyectoId()+".pdf");
		
		
		java.io.File archivo = new java.io.File(proyecto);
		if (archivo.exists()){
			tienePdf = true;
		}	
		
		String nombreEstado = "-";
		if (proyectos.getEstado().equals("0")) nombreEstado = "Capturado";
		if (proyectos.getEstado().equals("1")) nombreEstado = "Comité Inv.";
		if (proyectos.getEstado().equals("2")) nombreEstado = "Comité Etica.";
		if (proyectos.getEstado().equals("3")) nombreEstado = "DPI";
		if (proyectos.getEstado().equals("4")) nombreEstado = "DPI-Registrado";
		if (proyectos.getEstado().equals("5")) nombreEstado = "Concluido";
		
		boolean cancelar 	= false;
		boolean autorizar	= false;
		if (admin){
			cancelar 	= true;
			autorizar 	= true;
		}else if (adminEtica){
			if (proyectos.getEstado().equals("2")){
				autorizar = true;
				cancelar = true;
			}			
		}else{
			if (proyectos.getEstado().equals("1")){
				autorizar = true;
				cancelar = true;
			}
		}
		
		String empleadoNombre = "-";
		if (mapaEmpleados.containsKey(proyectos.getCodigoPersonal())){
			empleadoNombre = mapaEmpleados.get(proyectos.getCodigoPersonal()).getNombre()+", "+mapaEmpleados.get(proyectos.getCodigoPersonal()).getApellidoPaterno()+" "+mapaEmpleados.get(proyectos.getCodigoPersonal()).getApellidoMaterno();
		}
		
		String carreraNombre = "-";			
		if (mapaCarreras.containsKey(proyectos.getCarreraId() )){
			carreraNombre = mapaCarreras.get(proyectos.getCarreraId()).getNombreCorto();
		}
%>		
		<tr>
			<td><%= row %></td>
			<td><%= proyectos.getProyectoId()%></td>
			<td><%= proyectos.getProyectoNombre() %></td>
			<td><%= carreraNombre%></td>
			<td><%= empleadoNombre%></td>
			<td><%= nombreTipo %></td>
			<td><%= proyectos.getLinea() %></td>
			<td><%= proyectos.getFechaInicio() %></td>
			<td><%= proyectos.getFechaFinal() %></td>
			
			<td>			  
<%			
			if (mapaArchivos.containsKey(proyectos.getProyectoId()+"10")){
%>
				<a href="archivo?proyectoId=<%=proyectos.getProyectoId()%>&folio=10" title="Descargar"><i class="fas fa-download"></i> Proyecto</a><br>
<%			
			}
			if (mapaArchivos.containsKey(proyectos.getProyectoId()+"1")){
%>
				<a href="archivo?proyectoId=<%=proyectos.getProyectoId()%>&folio=1" title="Descargar"><i class="fas fa-download"></i> Instrumento</a><br>
<%
			}
			if (mapaArchivos.containsKey(proyectos.getProyectoId()+"2")){
%>
				<a href="archivo?proyectoId=<%=proyectos.getProyectoId()%>&folio=2" title="Descargar"><i class="fas fa-download"></i> Consentimiento</a><br>
<% 
			}
			if (mapaArchivos.containsKey(proyectos.getProyectoId()+"3")){
%>
				<a href="archivo?proyectoId=<%=proyectos.getProyectoId()%>&folio=3" title="Descargar"><i class="fas fa-download"></i> Asentimiento</a><br>
<%
			}
			if (mapaArchivos.containsKey(proyectos.getProyectoId()+"4")){
%>
				<a href="archivo?proyectoId=<%=proyectos.getProyectoId()%>&folio=4" title="Descargar"><i class="fas fa-download"></i> Confidencialidad</a><br>
<%
			}
			if (mapaArchivos.containsKey(proyectos.getProyectoId()+"5")){
%>
				<a href="archivo?proyectoId=<%=proyectos.getProyectoId()%>&folio=5" title="Consen. Foto y video"><i class="fas fa-download"></i> Consen. FV</a><br>
<%
			}
			if (mapaArchivos.containsKey(proyectos.getProyectoId()+"6")){
%>
				<a href="archivo?proyectoId=<%=proyectos.getProyectoId()%>&folio=6" title="Consen. Oral"><i class="fas fa-download"></i> Consen. oral</a><br>
<%
			}
			if (mapaArchivos.containsKey(proyectos.getProyectoId()+"7")){
%>
				<a href="archivo?proyectoId=<%=proyectos.getProyectoId()%>&folio=7" title="Consen. padres e hijos"><i class="fas fa-download"></i> Consen. P.H.</a>
<%
			}
			if (mapaArchivos.containsKey(proyectos.getProyectoId()+"8")){
%>			  
				<a href="archivo?proyectoId=<%=proyectos.getProyectoId()%>&folio=8" title="Otro"><i class="fas fa-download"></i> Extenso</a>
<%
			}
%>			  
			</td>
			<td><%= nombreEstado %></td>
		</tr>
<%	} %>
		</table>
	</div> 
</body>
</html>
<script src="../../js/jquery-1.9.1.min.js"></script>
<script src="../../js/search.js"></script>	
<script>	
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>