<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.alumno.spring.AlumCovid"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.alerta.spring.AlertaPeriodo"%>
<%@page import="aca.alerta.spring.AlertaHistorial"%>
<%@page import="aca.alerta.spring.AlertaAntro"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.internado.spring.IntAcceso"%>
<%@page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
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
	String periodoId			= (String) request.getAttribute("periodoId");
	String mensaje				= (String) request.getAttribute("Mensaje");	
	String codigoEmpleado		= (String) request.getAttribute("codigoEmpleado");
	String nombreEmpleado		= (String) request.getAttribute("nombreEmpleado");
	
	List<AlertaPeriodo> lisPeriodos 		= (List<AlertaPeriodo>) request.getAttribute("lisPeriodos");
	List<AlumCovid> lisDatos 				= (List<AlumCovid>) request.getAttribute("lisDatos");
	
	HashMap<String,String> mapaRegistrados 	= (HashMap<String,String>) request.getAttribute("mapaRegistrados");
	HashMap<String,String> mapaMaestros 	= (HashMap<String,String>) request.getAttribute("mapaMaestros");	
%>
<div class="container-fluid">
	<h1>Empleados registrados</h1>
	<form name="forma" action="datos" method="post">
	<div class="alert alert-info">
		<select name="PeriodoId" id="PeriodoId" style="width:350px;" onchange="document.forma.submit()">
	<%	for(AlertaPeriodo periodo: lisPeriodos){ %> 
			<option value="<%=periodo.getPeriodoId() %>" <%if(periodoId.equals(periodo.getPeriodoId()))out.print("selected"); %>><%=periodo.getPeriodoNombre() %></option>
	<%	} %>
		</select>
		&nbsp;&nbsp;		
		<input type="text" id="buscar" placeholder="Buscar..." size="20" maxlength="25" />&nbsp;&nbsp;
	</div>
	</form>
	<div class="alert alert-success">			
		<i class="fas fa-cloud-upload-alt fa-2x"></i>
		<%=codigoEmpleado%> - <%=nombreEmpleado%>&nbsp;	 
<%	if (!mapaRegistrados.containsKey(codigoEmpleado)){ %>
		<a href="editar?PeriodoId=<%=periodoId%>" class="btn btn-primary"><i class="fas fa-plus"></i></a>
<% 	}else{%>	
		(Resgistrado)
<% 	}%>	
	</div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	<tr>
		<th>#</th>
		<th>Op.</th>
		<th>Matrícula</th>
		<th>Nombre</th>		
		<th>Tipo</th>
		<th>F. llegada</th>
		<th>Vac.</th>
		<th>F. Vac.</th>
		<th>Pos.</th>
		<th>F. Pos.</th>
		<th>Sosp.</th>
		<th>F. Sos.</th>
		<th>Ais.</th>
		<th>Fin Ais.</th>
		<th>Usuario</th>
		<th>F.Alta</th>
		<th>Val.</th>				
	</tr>
	</thead>
	<tbody>
<%
	int cont = 0;
 	for(AlumCovid dato : lisDatos){
 		
 		String nombre = "-";
 		if (mapaMaestros.containsKey(dato.getCodigoPersonal())){
	 		nombre = mapaMaestros.get(dato.getCodigoPersonal());
 		}		
%>		 
	<tr>
		<td><%=cont%></td>
		<td>
			<a href="javascript:Borrar('<%=dato.getCodigoPersonal()%>','<%=dato.getPeriodoId()%>')"><i class="fas fa-trash-alt"></i></a>&nbsp;
			<a href="editar?CodigoEmpleado=<%=dato.getCodigoPersonal()%>&PeriodoId=<%=dato.getPeriodoId()%>"><i class="fas fa-pencil-alt"></i></a>
		</td>
		<td><%=dato.getCodigoPersonal() %></td>
		<td><%=nombre%></td>		
		<td><%=dato.getTipo().equals("L")?"Local":"Foraneo"%></td>
		<td><%=dato.getFechaLlegada()==null?"":dato.getFechaLlegada()%></td>
		<td><%=dato.getVacuna().equals("N")?"NO":dato.getVacuna()%></td>
		<td><%=dato.getFechaVacuna()==null?"":dato.getFechaVacuna()%></td>
		<td><%=dato.getPositivoCovid().equals("N")?"NO":dato.getPositivoCovid()%></td>
		<td><%=dato.getFechaPositivo()==null?"":dato.getFechaPositivo()%></td>
		<td><%=dato.getSospechoso().equals("N")?"NO":dato.getSospechoso()%></td>
		<td><%=dato.getFechaSospechoso()==null?"":dato.getFechaSospechoso()%></td>
		<td><%=dato.getAislamiento().equals("N")?"NO":dato.getAislamiento()%></td>
		<td><%=dato.getFinAislamiento()==null?"":dato.getFinAislamiento()%></td>
		<td><%=dato.getUsuarioAlta()%></td>
		<td><%=dato.getFechaAlta()==null?"":dato.getFechaAlta()%></td>	
		<td><%=dato.getValidado().equals("N")?"NO":dato.getValidado()%></td>				
	</tr>	
<% 	} %>
	</tbody>
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
	
	function Borrar( codigoAlumno, periodoId ){
		if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  		document.location.href="borrar?CodigoEmpleado="+codigoAlumno+"&PeriodoId="+periodoId;
	  	}
	}
</script>