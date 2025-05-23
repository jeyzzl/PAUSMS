<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.TreeMap"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.apFisica.spring.ApFisicaAlumno"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.apFisica.spring.ApFisicaGrupo"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<script type="text/javascript">
	function cambiaCarga(){
  		document.forma.submit();
	}
</script>

<!-- inicio de estructura -->
<%	
	String clave 			= request.getParameter("clave")==null?"COSM191":request.getParameter("clave");
	String cargaId			= request.getParameter("carga")==null?(String)session.getAttribute("cargaId"):request.getParameter("carga");
	
	List<AlumnoCurso> lista 				= (List<AlumnoCurso>)request.getAttribute("lista");
	List<Carga> lisCarga 					= (List<Carga>)request.getAttribute("lisCarga");
	
	HashMap<String, AlumPersonal> mapaAlumnos        = (HashMap<String, AlumPersonal>)request.getAttribute("mapaAlumnos");
	HashMap<String, CatCarrera> carreraNombre 			= (HashMap<String, CatCarrera>)request.getAttribute("carreraNombre");
	
%>
<div class="container-fluid">
	<h2>Alumnos sin Registro</h2>
	<form name="forma" action="sinRegistro">
	<div class="alert alert-info">
		<a href="menu" class="btn btn-primary btn-sm rounded-pill"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<strong>Carga:</strong>&nbsp;
        <select name="carga" style="width:350px">
<%		
		for (Carga carga : lisCarga){
%>
          <option <%if(cargaId.equals(carga.getCargaId())) out.print(" Selected ");%> value="<%=carga.getCargaId()%>"> 
            <%=carga.getCargaId()%> - <%=carga.getNombreCarga()%> </option>
<%		}%>
        </select>&nbsp;&nbsp;
		Materias: 
		<select name="clave" style="width:450px" >
			<option value="COSM191" <%if(clave.equals("COSM191")) out.print("selected"); %>>COSM191 Desarrollo Personal para una Cultura Universitaria</option>
			<option value="COSM192" <%if(clave.equals("COSM192")) out.print("selected"); %>>COSM192 Desarrollo Personal: Preparación para la Vida</option>
			<option value="COSM293" <%if(clave.equals("COSM293")) out.print("selected"); %>>COSM293 Desarrollo Personal: Entorno Sustentable </option>
			<option value="COSM294" <%if(clave.equals("COSM294")) out.print("selected"); %>>COSM294 Desarrollo Personal: Visión Emprendedora</option>
			<option value="COSM395" <%if(clave.equals("COSM395")) out.print("selected"); %>>COSM395 Desarrollo Personal: Gestión Comunitaria</option>
				<option value="COSM396" <%if(clave.equals("COSM396")) out.print("selected"); %>>COSM396 Desarrollo Personal: Visión de Servicio</option>
		</select>
		&nbsp;
		<a href="javascript:cambiaCarga()" class="btn btn-primary"><i class="fas fa-filter icon-white"></i>Filtrar</a> 
	</div>
	</form>
	<table class="table table-sm">
	<thead>
	<tr>
		<th>#</th>
		<th>Matricula</th>
		<th>Alumno</th>
		<th>Carrera</th>
		<th>Correo</th>
		<th>Teléfono</th>
	</tr>
	</thead>
	<tbody>
	<%
	int cont = 1;
	for(AlumnoCurso x : lista){
		
		String carreraN = "-";
		if(carreraNombre.containsKey(x.getCarreraId())){
			carreraN = carreraNombre.get(x.getCarreraId()).getNombreCarrera();
		}
		String correo = "";
		String telefono = "";
		String nombreAlumno = "-";
		if(mapaAlumnos.containsKey(x.getCodigoPersonal())){
			correo = mapaAlumnos.get(x.getCodigoPersonal()).getEmail();
			telefono = mapaAlumnos.get(x.getCodigoPersonal()).getTelefono();
			nombreAlumno = mapaAlumnos.get(x.getCodigoPersonal()).getNombre()+" "+mapaAlumnos.get(x.getCodigoPersonal()).getApellidoPaterno()+" "+mapaAlumnos.get(x.getCodigoPersonal()).getApellidoMaterno();
		}
		
		out.print("<tr>");
		out.print("<td>"+cont+"</td>");
		out.print("<td>"+x.getCodigoPersonal()+"</td>");
		out.print("<td>"+nombreAlumno+"</td>");
		out.print("<td>"+carreraN+"</td>");
		out.print("<td>"+correo+"</td>");
		out.print("<td>"+telefono+"</td>");
		out.print("</tr>");
		cont++;
	}
	%>
	</tbody>
	</table>
</div>	

<script>
    jQuery('#Fecha').datepicker();
</script>