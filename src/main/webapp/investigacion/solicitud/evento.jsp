<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.HashMap"%>

<jsp:useBean id="InvEventoU" scope="page" class="aca.investiga.InvEventoUtil"/>
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
	
	String folio 		= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	if (accion.equals("1")){
		aca.investiga.InvEventoUtil.updateEstado(conEnoc, folio, codigo,"1");
		//conEnoc.commit();
	}
	
	ArrayList<aca.investiga.InvEvento> lisEventos	= InvEventoU.getListEventosEmpleado(conEnoc, codigo, "ORDER BY FOLIO");
%>

<html>
<head>
<body>
	<div class="container-fluid">
		<h1>Listado de eventos</h1>
		<div class="alert alert-info">
			Usuario: [ <%=codigo%> ] &nbsp; <%=nombreUsuario%> &nbsp;&nbsp; <a href="accion?Accion=0" class="btn btn-primary"><i class="fas fa-plus"></i> Agregar</a>&nbsp;
		</div>
	
	<table class="table table-sm table-bordered">  
		<thead class="table-info">
			<tr>				
				<th width="10%">Opción</th>
				<th width="4%">Referencia</th>
				<th width="17%">Proyecto/Protocolo</th>
				<th width="6%">Fecha Sol.</th>
				<th width="5%"><spring:message code="aca.Tipo"/></th>
				<th width="22%">Evento</th>
				<th width="4%">Gastos</th>
				<th width="27%"><spring:message code='aca.Descripcion'/></th>
				<th width="5%"><spring:message code="aca.Estado"/></th>
			</tr>
		</thead>
<%
	for (int i=0; i<lisEventos.size();i++){
		aca.investiga.InvEvento eventos = (aca.investiga.InvEvento) lisEventos.get(i);
		
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
%>		
		<tr>			
			<td>
<% 			if (eventos.getEstado().equals("0")){%>						
				<a href="accion?Accion=3&Folio=<%= eventos.getFolio() %>" class="btn btn-info btn-sm" title="Editar"><i class="fas fa-pencil-alt"></i></a>&nbsp;	
				<a onclick="javascript:eliminar('<%=eventos.getFolio()%>');" class="btn btn-danger btn-sm"><i class="fas fa-trash-alt icon-white" title="Eliminar"></i></a>
				<a onclick="javascript:enviar('<%=eventos.getFolio()%>');" class="btn btn-primary btn-sm"><i class="icon-arrow-right icon-white" title='Enviar'></i></a>
<% 				if(InvEventoU.existeReg(conEnoc, eventos.getCodigoPersonal(), eventos.getFolio())){%>			
					<a href="alumno?Folio=<%= eventos.getFolio() %>" class="btn btn-warning btn-sm" title="Agregar Alumno"><i class="icon-user icon-white"></i></a>&nbsp;					
<% 			
				}
			}	
%>				
			</td>			
			<td><%= eventos.getFolio() %></td>
			<td><%= aca.investiga.InvProyectoUtil.getNombreProyecto(conEnoc, eventos.getProyectoId()) %></td>
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
	function eliminar(folio){
		if(confirm("¿Estás seguro que deseas eliminar este registro?")){
			location.href = "accion?Accion=2&Folio="+folio;
		}
	}
	
	function enviar(folio){
		if(confirm("¡Al enviar la solicitud ya no podrás hacer modificaciones! ¿Deseas continuar?")){
			location.href = "evento?Accion=1&Folio="+folio;
		}
	}
</script>
<%@ include file= "../../cierra_enoc.jsp" %>