<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.HashMap"%>

<jsp:useBean id="InvEventoU" scope="page" class="aca.investiga.InvEventoUtil"/>
<jsp:useBean id="InvProyecto" scope="page" class="aca.investiga.InvProyecto"/>
<jsp:useBean id="Usuario" scope="page" class="aca.vista.Usuarios"/>
<%
	String codigo			= (String) session.getAttribute("codigoPersonal");
	String nombreUsuario	= " ";
	
	Usuario.setCodigoPersonal(codigo);
	if (Usuario.existeReg(conEnoc)){
		Usuario.mapeaRegId(conEnoc, codigo);
		nombreUsuario	= Usuario.getNombre()+" "+Usuario.getApellidoPaterno()+" "+Usuario.getApellidoMaterno();
	}else{
		nombreUsuario = "¡ No existe !";
	}
	
	boolean admin			= false;
	
	// Usuarios administradores
	if (codigo.equals("9800308")||codigo.equals("9800330")||codigo.equals("9800079")) admin = true;
	
	
	//Facultad donde el usuario es Director
	String facultad = aca.catalogo.CatFacultadUtil.getFacultad(conEnoc, codigo);
	// Lista de proyectos de investigación 
	ArrayList<aca.investiga.InvEvento> lisEventos = new ArrayList<aca.investiga.InvEvento>();
	
	if (admin){
		lisEventos	= InvEventoU.getListAll(conEnoc, "ORDER BY FOLIO");	
	}else{
		lisEventos = InvEventoU.getListProyectosDirectorFac(conEnoc, facultad, "ORDER BY FOLIO, CODIGO_PERSONAL");
	}
%>

<html>
<head>
<body>
	<div class="container-fluid">
		<h2>Listado de eventos<small class="text-muted fs-4">( Usuario: <%=codigo%> &nbsp; <%=nombreUsuario%> )</small></h2> 
		<div class="alert alert-info">	
			<input type="text" class="input-medium search-query form-control" placeholder="Buscar" id="buscar">
		</div>	
		<table class="table table-sm table-bordered">  
		<thead class="table-info">
		<tr>				
			<th width="5%">Opción</th>
			<th width="4%">Referencia</th>
			<th width="8%"><spring:message code="aca.Carrera"/></th>
			<th width="16%">Proyecto/Protocolo</th>
			<th width="15%"><spring:message code="aca.Nombre"/></th>
			<th width="6%">Fecha Sol.</th>
			<th width="5%"><spring:message code="aca.Tipo"/></th>
			<th width="17%">Evento</th>
			<th width="4%">Gastos</th>
			<th width="16%"><spring:message code='aca.Descripcion'/></th>
			<th width="5%"><spring:message code="aca.Estado"/></th>
		</tr>
		</thead>
<%
	for (int i=0; i<lisEventos.size();i++){
		aca.investiga.InvEvento eventos = (aca.investiga.InvEvento) lisEventos.get(i);
		InvProyecto.mapeaRegId(conEnoc, eventos.getProyectoId());
		
		String nombreTipo = " ";
		if(eventos.getTipoEvento().equals("1")){
			nombreTipo = "Congreso";
		}else if(eventos.getTipoEvento().equals("2")){
			nombreTipo = "Workshop";
		}else{
			nombreTipo = "Jornadas";
		}
		
		String nombreEstado = "-";
		if (eventos.getEstado().equals("0")) nombreEstado = "Capturado";
		if (eventos.getEstado().equals("1")) nombreEstado = "Aprobado Facultad";
		if (eventos.getEstado().equals("2")) nombreEstado = "DPI";
		if (eventos.getEstado().equals("3")) nombreEstado = "Aprobado DPI";
		
		boolean cancelar 	= false;
		boolean autorizar	= false;
		if (admin){
			cancelar 	= true;
			autorizar 	= true;
		}else{
			if (eventos.getEstado().equals("1")){
				autorizar = true;
				cancelar = true;
			}
		}
%>		
		<tr>			
			<td>
<% 		if(autorizar){%>				
			<a onclick="javascript:autorizar('<%= eventos.getFolio() %>','<%= eventos.getCodigoPersonal() %>' );" class="btn btn-success btn-sm" title="Autorizar"><i class="icon-ok icon-white"></i></a>&nbsp;
<% 		}
		if(cancelar){%>			
			<a onclick="javascript:cancelar('<%= eventos.getFolio() %>','<%= eventos.getCodigoPersonal() %>');" class="btn btn-danger btn-sm" title="Cancelar"><i class="icon-arrow-left icon-white"></i></a>&nbsp;
<% 		}%>			
			</td>			
			<td><%= eventos.getFolio() %></td>
			<td><%= aca.catalogo.CatCarreraUtil.getNombreCorto(conEnoc,InvProyecto.getCarreraId()) %></td>
			<td><%= aca.investiga.InvProyectoUtil.getNombreProyecto(conEnoc, eventos.getProyectoId()) %></td>
			<td><%= aca.vista.UsuariosUtil.getNombreUsuarioCorto(conEnoc, eventos.getCodigoPersonal()) %></td>
			<td><%= eventos.getFechaSolicitud() %></td>
			<td><%= nombreTipo %></td>
			<td><%= eventos.getNombreEvento() %></td>
			<td><%= eventos.getGastos() %></td>
			<td><%= eventos.getDescripcion() %></td>
			<td><%= nombreEstado %></td>
		</tr>
<%	} %>
	</table>
	</div>	
</body>
</html>
<script>	
	$('#buscar').search();
	
	function cancelar(folio, codigo){
		if( confirm("¡Esta acción cancela la autorización! ¿Deseas continuar?") ){
			location.href="accion?Accion=1&Folio="+folio+"&Codigo="+codigo;
		}
	}
	
	function autorizar(folio, codigo){
		if(confirm("¡Esta acción autoriza el proyecto! ¿Deseas continuar?")){
			location.href = "accion?Accion=2&Folio="+folio+"&Codigo="+codigo;
		}
	}
</script>
<%@ include file= "../../cierra_enoc.jsp" %>