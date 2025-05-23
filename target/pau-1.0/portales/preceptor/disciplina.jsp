<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.internado.spring.IntAlumReporte"%>
<%@ page import= "aca.internado.spring.IntReporte"%>

<script type="text/javascript">	
	function Refrescar(){		
		document.forma.submit();
	}
</script>

<%
	boolean esAdmin				= (boolean)request.getAttribute("esAdmin");
	boolean esPreceptor			= (boolean)request.getAttribute("esPreceptor");
	String fechaIni 			= (String)request.getAttribute("fechaIni");
	String fechaFin 			= (String)request.getAttribute("fechaFin");
	String nombreDormitorio 	= (String)request.getAttribute("nombreDormitorio");
	
	// Map de reportes
	HashMap<String, IntReporte> mapReporte		= (HashMap<String, IntReporte>)request.getAttribute("mapReporte");
	HashMap<String, AlumPersonal> mapAlumnos 	= (HashMap<String, AlumPersonal>)request.getAttribute("mapAlumnos");
	HashMap<String, String> mapEmpleadoCorto 	= (HashMap<String, String>)request.getAttribute("mapEmpleadoCorto");
	
	// Lista de reportes en el rango de fechas
	List <IntAlumReporte> listReportes = (List <IntAlumReporte>)request.getAttribute("listReportes");	
%>

<%@ include file="portal.jsp" %>

<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />	
</head>

<div class="container-fluid">
	<h3>
		Discipline <small> (<%=nombreDormitorio%>)</small>
	</h3>
	<form id="forma" name="forma" action="disciplina" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<%
		if(esAdmin || esPreceptor){ out.print("&nbsp;<a href='../../internado/dormitorios/dormitorios' class='btn btn-info'><i class='icon-white icon-arrow-left'></i>Menu</a>&nbsp; &nbsp;"); }
		%>
		<a href="disciplina_alta" class="btn btn-primary">Add</a>&nbsp;&nbsp;&nbsp;&nbsp;
		Start Date: <input id="FechaIni" name="FechaIni" type="text" class="form-control" style="width:120px" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10"/>&nbsp;&nbsp;
		End Date: <input id="FechaFin" name="FechaFin" type="text"  class="form-control" style="width:120px" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10"/>&nbsp;&nbsp;
		<a href="javascript:Refrescar()" class="btn btn-primary" ><i class="fas fa-sync-alt"></i></a>			
	</div>
	</form>
	<div class="alert alert-info ">
		<input class="form-control" type="text" class="input-medium search-query"  style="width:10rem" placeholder="Search..." id="buscar">
	</div>
	<table id="table" width="100%" class="table table-bordered table-striped">
		<thead>
		<tr class="table-dark">
			<th style="color:white"><spring:message code="aca.Numero"/></th>
			<th style="color:white">Option</th>
			<th style="color:white"><spring:message code="aca.Matricula"/></th>
			<th style="color:white"><spring:message code="aca.Alumno"/></th>
			<th style="color:white"><spring:message code="aca.Folio"/></th>
			<th style="color:white"><spring:message code="aca.Fecha"/></th>
			<th style="color:white">Report</th>
			<th style="color:white"><spring:message code='aca.Cantidad'/></th>
			<th style="color:white"><spring:message code="aca.Comentario"/></th>
			<th style="color:white"><spring:message code='aca.Usuario'/></th>
		</tr>
		</thead>
<%		
	int i = 1;
	for(IntAlumReporte intAlumReporte : listReportes){
		
		String reporte = "";
		if(mapReporte.containsKey(intAlumReporte.getReporteId())){
			reporte = mapReporte.get(intAlumReporte.getReporteId()).getReporteNombre();
		}
		
		String alumnoNombre = "";
		if (mapAlumnos.containsKey(intAlumReporte.getCodigoPersonal() )){
			AlumPersonal alum =  mapAlumnos.get(intAlumReporte.getCodigoPersonal());
			alumnoNombre = (alum.getApellidoMaterno().equals("-")?"":alum.getApellidoMaterno())+" "+alum.getApellidoPaterno()+" "+", "+alum.getNombre();
		}

		String nombreEmpleado = "";
		if (mapEmpleadoCorto.containsKey(intAlumReporte.getUsuario() )){
			nombreEmpleado = mapEmpleadoCorto.get(intAlumReporte.getUsuario());
		}
%>
		<tbody>
		<tr>
			<td><%=i++%></td>
			<td>
				<a href="disciplina_alta?CodigoAlumno=<%=intAlumReporte.getCodigoPersonal()%>&Folio=<%=intAlumReporte.getFolio()%>" class="text-decoration-none">
					<i class="fas fa-pencil-alt"></i>
				</a>
				&nbsp;&nbsp; 
				<a href="disciplina?Accion=1&CodigoAlumno=<%=intAlumReporte.getCodigoPersonal()%>&Folio=<%=intAlumReporte.getFolio()%>" class="text-decoration-none">
					<i class="fas fa-trash-alt text-danger"></i>
				</a>
			</td>
			<td><%=intAlumReporte.getCodigoPersonal() %></td>
			<td><%= alumnoNombre %></td>
			<td><%=intAlumReporte.getFolio() %></td>			
			<td><%=intAlumReporte.getFecha()%></td>
			<td><%=reporte%></td>
			<td><%=intAlumReporte.getCantidad() %></td>
			<td><%=intAlumReporte.getComentario()%></td>
			<td><%=intAlumReporte.getUsuario()%></td>
		</tr>
		</tbody>
<%
	}
%>
	</table>	
</div>
<script type="text/javascript" src="../../js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="../../js/search.js"></script>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>
	jQuery('.disciplina').addClass('active');
	jQuery('#buscar').focus().search({table:$("#table")});
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
</script>	