<%@page import="aca.carga.spring.CargaPracticaAlumno"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="java.util.ArrayList"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<html>
<head>
	<script type="text/javascript">
	function borrar(codigoAlumno,folio){
		if (window.confirm("Are you sure you want to delete this record?")) { 
			document.location.href="borrarPractica?CodigoAlumno="+codigoAlumno+"&Folio="+folio;
		}
	}
	</script>
</head>
<body>
<div class="container-fluid">
<%
	String cargaId		= (String)request.getAttribute("cargaId");
	String bloqueId 	= (String)request.getAttribute("bloqueId");
	String inicia		= (String)request.getAttribute("inicia");
	String termina		= (String)request.getAttribute("termina");
	String codigoAlumno	= (String)request.getAttribute("codigoAlumno");
	AlumPersonal alumno	= (AlumPersonal)request.getAttribute("alumno");
	
	ArrayList<CargaPracticaAlumno> listaPracticas = (ArrayList<CargaPracticaAlumno>)request.getAttribute("listaPracticas");
%>
	<h3>Register Practices<small>(<%=codigoAlumno%> - <%=alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()%>)</small></h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="materias"><i class="fas fa-arrow-left"></i></a>
		&nbsp; &nbsp;
	</div>
	<form name="frmPracticas" action="grabarPractica" method="post">
		<input type="hidden" name="CodigoAlumno" value="<%=codigoAlumno%>">
		<input type="hidden" name="CargaId" value="<%=cargaId%>">
		<input type="hidden" name="BloqueId" value="<%=bloqueId%>">
   		<label >Starts</label>
		<input type="text" name="Inicia" id="Inicia" value="<%=inicia%>" data-date-format="dd/mm/yyyy">
   		<label>Ends</label>
   		<input type="text" name="Termina" id="Termina" value="<%=termina%>" data-date-format="dd/mm/yyyy">
   		<label>Status</label>
		<select name="Estado">
			<option value="A">Active</option>
			<option value="L">Release</option>			
		</select>
   		<label>Comment</label>
		<input type="text" class="form-control" name="Comentario"><br>
  		<button type="submit" class="btn btn-primary">Save</button>
	</form>
	<table class="table">
		<thead>
			<tr>
				<th>Ap. Number</th>
				<th>Starts</th>
				<th>Ends</th>
				<th>Comment</th>
				<th>Status</th>
			</tr>
		</thead>
		<tbody>
<% 		for(CargaPracticaAlumno practica : listaPracticas){
			String estado = "Active";			
			if(practica.getEstado().equals("L")){
				estado = "Release";
			}
%>
			<tr>
				<td>
					<%=practica.getFolio()%>&nbsp;
					<a title="Borrar" href="javascript:borrar('<%=codigoAlumno%>','<%=practica.getFolio()%>')"><i class="fas fa-times"></i></a>
				</td>
				<td><%=practica.getFechaIni()%></td>
				<td><%=practica.getFechaFin()%></td>
				<td><%=practica.getComentario()%></td>
				<td><%=estado%></td>
			</tr>
<% 		}%>
		</tbody>
	</table>
</div>
</body>
<script>
	jQuery('#Inicia').datepicker();
	jQuery('#Termina').datepicker();
</script>
</html>