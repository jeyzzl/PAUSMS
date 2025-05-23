<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.exa.spring.ExaEvento"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	List<ExaEvento> lisEventos				= (List<ExaEvento>) request.getAttribute("lisEventos"); 
	HashMap<String,String> mapaRegistrados 	= (HashMap<String,String>) request.getAttribute("mapaRegistrados");
%>
<html>
<head>
<script type="text/javascript">
	function Borrar(EventoId){
		if (confirm("Estás seguro de eliminar el registro: " + EventoId) == true){
			document.location.href = "borrar?EventoId="+EventoId;
		}
	}
</script>
</head>
<body>
	<div class="container-fluid">
	<h2>Listado de eventos</h2>
	<div class="alert alert-info">
 		<a class="btn btn-primary" href="accion?Accion=1"><spring:message code='aca.Añadir'/></a>
	</div>		
	<table  class="table table-striped table-bordered">
	<thead class="table-dark table-sm">	
	<tr>
		<th style="text-align:center" width="5%"><spring:message code="aca.Operacion"/></th>
		<th style="text-align:center"width="5%"><spring:message code="aca.Numero"/></th>
		<th width="35%"><spring:message code='aca.Descripcion'/></th>
		<th width="45%">Lugar</th>
		<th style="text-align:center" width="10%"><spring:message code="aca.Fecha"/></th>
		<th style="text-align:center" width="10%">Registrados</th>
	</tr>
	</thead>
	<tbody>
<%
	int row = 0; 
	for(ExaEvento evento : lisEventos){
		row++;	
		
		String registrados = "0";
		String textoReg = "<span class='badge bg-warning rounded-pill'>0</span>";
		if (mapaRegistrados.containsKey(evento.getEventoId())){
			registrados = mapaRegistrados.get(evento.getEventoId());
			textoReg = "<span class='badge bg-dark rounded-pill'>"+registrados+"</span>";
		}
%>
	<tr>
		<td style="text-align: center">
			<a href="accion?EventoId=<%= evento.getEventoId() %>" title="Modificar"><i class="fas fa-edit"></i></a>	
<%		if (registrados.equals("0")){%>						
			&nbsp;
			<a href="javascript:Borrar('<%= evento.getEventoId() %>')" title="Eliminar" style="color:red"><i class="fas fa-trash-alt"></i></a>
<% 		}%>						
		</td>
		<td align="center"><%=row%></td>
		<td><%= evento.getNombre() %></td>
		<td><%=evento.getLugar() %></td>
		<td align="center"><%=evento.getFechaEvento() %></td>
		<td align="center"><%=textoReg%></td>
	</tr>
<%	} %>
	</table>
</body>
</html>