<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<jsp:useBean id="LogAlumnoU" scope="page" class="aca.log.LogAlumnoUtil"/>

<script type="text/javascript">
	function Mostrar(){
		document.forma.submit();
	}
</script>
<%
	String fechaInicio 	= request.getParameter("FechaInicio")==null?"01/08/2017":request.getParameter("FechaInicio");
	String fechaFinal   = request.getParameter("FechaFinal")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFinal");
	
	// Lista de alumnos con datos modificados 
	ArrayList<String>lisAlumnos = LogAlumnoU.lisActualizadosPorFecha(conEnoc, fechaInicio, fechaFinal, "'ALUM_PERSONAL','ALUM_UBICACION'");
	
	// Mapa de alumnos con datos modificados en Alum_Personal
	java.util.HashMap<String,String> mapaPersonal = LogAlumnoU.mapaModificaTabla(conEnoc, fechaInicio, fechaFinal, "ALUM_PERSONAL");
	
	// Mapa de alumnos con datos modificados en Alum_Ubicacion
	java.util.HashMap<String,String> mapaUbicacion = LogAlumnoU.mapaModificaTabla(conEnoc, fechaInicio, fechaFinal, "ALUM_UBICACION");
%>
<body>
<div class="container-fluid">
	<h1>Alumos que actualizaron sus datos</h1>
	<form method="post" action="datos" name="forma">
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" class="btn btn-primary">Regresar</a>&nbsp;&nbsp;
		Fecha Ini.&nbsp; <input type="text" class="form-control" style="width:150px;" data-date-format="dd/mm/yyyy" id="FechaInicio" name="FechaInicio" value="<%=fechaInicio%>" size="9"/>&nbsp;&nbsp;
		Fecha Fin.&nbsp; <input type="text" class="form-control" style="width:150px;" data-date-format="dd/mm/yyyy" id="FechaFinal" name="FechaFinal" value="<%=fechaFinal%>" size="9"/>&nbsp;&nbsp;
		<a href="javascript:Mostrar()" class="btn btn-primary"><i class="fas fa-sync-alt"></i></a>
	</div>
	</form>
	<table class="table table-striped table-bordered">
		<tr class="table-info">
			<th>#</th>
			<th>Matrícula</th>
			<th>Alumno</th>
			<th>Personal</th>
			<th>Ubicación</th>
		</tr>
<% 
		int row=0;
		for(String alumno: lisAlumnos){
			row++;
			
			String personal = "NO";
			if (mapaPersonal.containsKey(alumno)){
				personal = "SI";
			}
			
			String ubicacion = "NO";
			if (mapaUbicacion.containsKey(alumno)){
				ubicacion = "SI";
			}
%>
		<tr>
			<td><%=row%></td>
			<td><%=alumno%></td>
			<td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, alumno, "NOMBRE")%></td>
			<td><%=personal%></td>
			<td><%=ubicacion%></td>
		</tr>
<% 
	}
%>
	</table>
</div>
</body>
<script>
	jQuery('#FechaInicio').datepicker();
	jQuery('#FechaFinal').datepicker();
</script>
<%@ include file= "../../cierra_enoc.jsp" %> 