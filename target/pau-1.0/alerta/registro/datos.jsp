<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.alerta.spring.AlertaDatos"%>
<%@page import="aca.alerta.spring.AlertaPeriodo"%>
<%@page import="aca.alerta.spring.AlertaHistorial"%>
<%@page import="aca.alerta.spring.AlertaAntro"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<style>
	body{
		background: white;
	} 
	
	input[type=checkbox]{
	  /* Double-sized Checkboxes */
	  -ms-transform: scale(1.5); /* IE */
	  -moz-transform: scale(1.5); /* FF */
	  -webkit-transform: scale(1.5); /* Safari and Chrome */
	  -o-transform: scale(1.5); /* Opera */
	  padding: 10px;
	}
	
	th{
		padding-right: 30px !important;
	}
</style>

<body>

<%
	String periodoId	= (String) request.getAttribute("periodoId");
	String accion		= (String) request.getAttribute("accion");
	String msj			= (String) request.getAttribute("msj");
	
	List<AlertaDatos> lisDatos 		= (List<AlertaDatos>) request.getAttribute("lisDatos");
	List<AlertaPeriodo> lisPeriodos 	= (List<AlertaPeriodo>) request.getAttribute("lisPeriodos");
	
	HashMap<String, AlertaHistorial> mapaHistorial	= (HashMap<String, AlertaHistorial>) request.getAttribute("mapaHistorial");
	HashMap<String, AlertaAntro> mapaAntro			= (HashMap<String, AlertaAntro>) request.getAttribute("mapaAntro");
	HashMap<String, String> mapaAlumnosAlerta		= (HashMap<String, String>) request.getAttribute("mapaAlumnosAlerta");
	HashMap<String, String> mapaAlumnosEnAlerta		= (HashMap<String, String>) request.getAttribute("mapaAlumnosEnAlerta");
	HashMap<String, String> mapaEmpleadosEnAlerta	= (HashMap<String, String>) request.getAttribute("mapaEmpleadosEnAlerta");
%>

<div class="container-fluid">

	<h2><spring:message code="aca.Datos"/></h2>
	
	<%=msj%>
	<form action="" name="forma">
		<div class="alert alert-info" align="left">
			<a href="accion?Nuevo=1&PeriodoId=<%=periodoId%>" class="btn btn-primary"><i class="icon-plus icon-white"></i><spring:message code='aca.Nuevo'/></a>&nbsp;&nbsp;
			<input type="text" id="buscar" placeholder="Buscar..." size="20" maxlength="25" />&nbsp;&nbsp;
			
			<select name="periodoSanitaria" id="periodoSanitaria" style="width:280px; onchange="document.forma.submit()">
			<%
				for(AlertaPeriodo periodo: lisPeriodos){
			%>
					<option value="<%=periodo.getPeriodoId() %>" <%if(periodoId.equals(periodo.getPeriodoId()))out.print("selected"); %>><%=periodo.getPeriodoNombre() %></option>
			<%
				}
			%>
			</select>
			
		</div>
	</form>
	<table class="table table-sm table-bordered" id="table">
		<thead>
			<tr>
				<th>#</th>
				<th><spring:message code="aca.Op"/></th>
				<th><spring:message code="aca.Matricula"/></th>
				<th><spring:message code="aca.Nombre"/></th>
				<th><spring:message code="aca.Hist"/></th>
				<th><spring:message code="aca.Antro"/></th>
				<th><spring:message code='aca.Direccion'/></th>
				<th><spring:message code='aca.Procedencia'/></th>
				<th><spring:message code="aca.Correo"/></th>
				<th><spring:message code="aca.Celular"/></th>
				<th><spring:message code="aca.Estado"/></th>
				<th><spring:message code='aca.Usuario'/></th>
			</tr>
		</thead>
		<%
			int cont = 0;
			for(AlertaDatos dato : lisDatos){
				cont++;
				boolean historial	= mapaHistorial.containsKey(dato.getPeriodoId()+dato.getCodigoPersonal());
				boolean antro		= mapaAntro.containsKey(dato.getPeriodoId()+dato.getCodigoPersonal());
				
				String nombreAlumno = "-";
				if(mapaAlumnosAlerta.containsKey(dato.getCodigoPersonal())){
					nombreAlumno = mapaAlumnosAlerta.get(dato.getCodigoPersonal());
				}
				
				String nombreUsuario = "-";
				
				if(dato.getUsuario().substring(0, 1).equals("9")){
					if(mapaEmpleadosEnAlerta.containsKey(dato.getUsuario())){
						nombreUsuario = mapaEmpleadosEnAlerta.get(dato.getUsuario());
					}
				}else{
					if(mapaAlumnosEnAlerta.containsKey(dato.getUsuario())){
						nombreUsuario = mapaAlumnosEnAlerta.get(dato.getUsuario());
					}
				}
		%>
			<tr>
				<td><%=cont %></td>
				<td>
					<a href="accion?matricula=<%=dato.getCodigoPersonal()%>&PeriodoId=<%=dato.getPeriodoId()%>&readonly=1"><i class="fas fa-pencil-alt"></i></a>
				</td>
				<td><%=dato.getCodigoPersonal() %></td>
				<td><%=nombreAlumno%></td>
				<td><span class="<%=historial?"":"badge bg-warning"%>"><%=historial?"Listo":"Falta"%></span></td>
				<td><span class="<%=antro?"":"badge bg-warning"%>"><%=antro?"Listo":"Falta"%></span></td>
				<td><%=dato.getDireccion() %></td>
				<td><%=dato.getProcedencia() %></td>
				<td><%=dato.getCorreo() %></td>
				<td><%=dato.getCelular() %></td>
				<td><%=(dato.getEstado()!=null && dato.getEstado().equals("A")) ? "Autorizado" : "Pendiente" %></td>
				<td><%=nombreUsuario%></td>
			</tr>
		<%
			}
		%>
	</table>	
</div>
<link rel="stylesheet" href="../../js/tablesorter/themes/blue/style.css"/>
<script src="../../js/tablesorter/jquery.tablesorter.js"></script>
<script src="../../js/search.js"></script>
<script>
	jQuery('#table').tablesorter();

	jQuery('#buscar').search({
		table: jQuery("#table")}
	);
</script> 