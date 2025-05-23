<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.util.HashMap"%>

<jsp:useBean id="ProgramacionU" scope="page" class="aca.rotaciones.RotProgramacionUtil"/>
<jsp:useBean id="AlumPersonalU" scope="page" class="aca.alumno.AlumUtil"/>

<script src="../../utilerias/admin/typeahead/bootstrap-transition.js"></script>
<script src="../../utilerias/admin/typeahead/bootstrap-modal.js"></script>
<script src="../../utilerias/admin/typeahead/bootstrap-typeahead.js"></script>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<head>
<script>
	function reporte(fecha){
		var matricula = document.getElementById("matricula").value;
		
		if(matricula.length == 7){
			location.href="alumnos?CodigoPersonal="+matricula+"&Fecha="+fecha;
		}else{
			alert("Favor de escribir una matricula valida de 7 caracteres");
		}
	}
	function fecha(codigoPersonal){
		location.href="alumnos?Fecha="+jQuery('#dp2').val()+"&CodigoPersonal="+codigoPersonal;
	}
</script>
<%
	ArrayList<String> matriculas = ProgramacionU.getMatriculas(conEnoc, "ORDER BY 1");
	HashMap<String, aca.alumno.AlumPersonal> alumnos = AlumPersonalU.getMapRotaciones(conEnoc);

	String source = "";
	for(String matricula: matriculas){
		String nombre = "";			
		if(alumnos.get(matricula)!=null){
			aca.alumno.AlumPersonal alumno = alumnos.get(matricula);
			
			nombre = alumno.getNombre()+" "+alumno.getApellidoPaterno() + " "+ alumno.getApellidoMaterno();
		}
		
		source += " \" "+matricula + " - " + nombre + " \", " ;			
	}
	
	source = source.substring(0, source.length()-2);
	
	String codigoPersonal = request.getParameter("CodigoPersonal")==null?"":request.getParameter("CodigoPersonal");
	String fecha = request.getParameter("Fecha")==null?"":request.getParameter("Fecha");
	String filtroFecha = "";
	if(!fecha.equals("")){
		filtroFecha = " AND F_INICIO >= TO_DATE('"+fecha+"', 'DD/MM/YYYY') ";
	}
	
	ArrayList<aca.rotaciones.RotProgramacion> rotaciones = null;
	if(!codigoPersonal.equals("")){
		rotaciones = ProgramacionU.getListAll(conEnoc, "WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+filtroFecha+" ORDER BY F_INICIO");
	}
	
	
%>
<style>
	.alumno{
		font-size:16px;
	}
	.nota{
		font-size:14px;
		color:black;
	}
</style>
</head>

<body style="background:white;">

<div class="container-fluid">
	<h2>Reportes por alumno</h2>
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" class="btn btn-primary" style="left:10px;position:absolute;">regresar</a>&nbsp;&nbsp;
		 <b>Alumno: &nbsp;</b>
		 <input type="text" class="form-control" value="<%=codigoPersonal%>" id="matricula" style="width:120px;" data-value="-" data-provide="typeahead" data-items="4" data-source='[<%=source %>]'>
		 &nbsp;
		 <button onclick="reporte('<%=fecha %>');" class="btn btn-primary"><i class="icon-list-alt"></i> Generar Reporte</button>
		 &nbsp;
		 <%if(!codigoPersonal.equals("")){%>		
				<input name="fechaInicio" type="text" class="form-control" value="<%=fecha.equals("")?aca.util.Fecha.getHoy():fecha%>" data-date-format="dd/mm/yyyy" id="dp2" style="width:120px">
				&nbsp;
				<%if(fecha.equals("")){ %>
					<a class="btn btn-primary" onclick="fecha('<%=codigoPersonal %>');" ><i class="icon-calendar"></i> Filtrar por Fecha</a>
				<%}else{ %>
					<a class="btn btn-primary" onclick="fecha('<%=codigoPersonal %>');" ><i class="icon-calendar"></i> Filtrar por Fecha</a>&nbsp;
					<a class="btn btn-primary" onclick="location.href='alumnos?CodigoPersonal=<%=codigoPersonal%>'" ><i class="fas fa-trash-alt"></i> No Filtrar por Fecha</a>
				<%} %>			
		<%} %>
	</div>		
</div>
<%if(!codigoPersonal.equals("") && rotaciones.size()>0){ 
	String genero = alumnos.get(codigoPersonal).getSexo();
%>
	<table style="width:95%" align="center">
	<tr>
		<td align="center"><h3>COORDINACIÓN DE CAMPOS CLÍNICOS</h3></td>
	</tr>
	<tr>
		<td align="center"><h4>Reporte Programación del Alumno</h4></td>
	</tr>
	<tr>
		<td>&nbsp;<br>&nbsp;</td>
	</tr>
	<tr>
		<td class="alumno">
			<b><spring:message code="aca.Matricula"/>: </b><%=codigoPersonal %>
		</td>
	</tr>
	<tr>
		<td class="alumno">
			<b>Alumn<%if(genero.equals("M")){out.print("o");}else{out.print("a");} %>: </b><%=alumnos.get(codigoPersonal).getApellidoPaterno() %>, <%=alumnos.get(codigoPersonal).getNombre() %>
		</td>
	</tr>
	</table>
	<table style="width:95%" align="center" class="table table-smtable-bordered">
	<thead class="table-info">
	<tr>
		<th>Especialidad</th>
		<th><spring:message code="aca.FechaInicio"/></th>
		<th>Fecha Fin</th>
		<th>Hospital</th>
	</tr>
	</thead>
	<tbody>
	<%for(aca.rotaciones.RotProgramacion rot: rotaciones){ %>
	<tr>
		<td><%=aca.rotaciones.RotEspecialidad.getNombre(conEnoc, rot.getEspecialidadId())  %></td>
		<td><%=rot.getfInicio() %></td>
		<td><%=rot.getfFinal() %></td>
		<td><%=aca.rotaciones.RotHospital.getNombreCorto(conEnoc, rot.getHospitalId()) %></td>
	</tr>
	<%} %>
	</tbody>
	</table>
	<table style="width:95%" align="center" >
	<tr>
		<td colspan="4" class="nota">
		Nota: Por el presente documento, yo: <%=alumnos.get(codigoPersonal).getNombre() %> <%=alumnos.get(codigoPersonal).getApellidoPaterno() %> <%=alumnos.get(codigoPersonal).getApellidoMaterno() %> 
		 reconozco haber hecho esta programación 
		y me comprometo a realizarla en las fechas y sedes asignadas. Si así no fuere me comprometo a asumir el costo y 
		consecuencias que deriven de mi incumplimiento, omisión o cambio.
		</td>
	</tr>
	</table>
	<br><br>
	<table style="width:95%" align="center" >
	<tr>
		<td class="nota" style="border-top:1px solid black;width:230px;" align="center">
			Firma de<%if(genero.equals("M")){out.print("l");}else{out.print(" la");} %> Alumn<%if(genero.equals("M")){out.print("o");}else{out.print("a");} %> y Fecha
		</td>
		<td></td>
	</tr>
	</table>
<%}else{%>
	<table style="width:80%; margin:0 auto;">
	<tr>
		<td align="center">
			<h3>Seleccione una matricula <b>valida</b> y que tenga especialidades programadas</h3>
			<%if(!fecha.equals("")){%><h3>En la fecha seleccionada</h3><%} %>
		</td>
	</tr>
	</table>	
<%} %>
</body>
<script>
	jQuery('#dp2').datepicker();
</script>
<%@ include file= "../../cierra_enoc.jsp" %>