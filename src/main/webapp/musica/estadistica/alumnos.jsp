<%@page import="aca.catalogo.CatReligionDao"%>
<%@page import="aca.catalogo.spring.CatReligion"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.musica.spring.MusiAlumno"%>
<%@page import="aca.catalogo.spring.CatReligion"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>
<%@page import="aca.catalogo.spring.CatCiudad"%>
<% 
	String codigoId					= (String) session.getAttribute("CodigoId");
	String estado 					= request.getParameter("Estado")==null?"S":request.getParameter("Estado");

	List<MusiAlumno> lisAlumnos		= (List<MusiAlumno>)request.getAttribute("lisAlumnos");
	
	HashMap<String,CatReligion> mapaReligiones		= (HashMap<String,CatReligion>)request.getAttribute("mapaReligiones");
	HashMap<String, CatPais> mapaPaises				= (HashMap<String, CatPais>)request.getAttribute("mapaPaises");
	HashMap<String, CatEstado> mapaEstados			= (HashMap<String, CatEstado>)request.getAttribute("mapaEstados");
	HashMap<String, CatCiudad> mapaCiudades			= (HashMap<String, CatCiudad>)request.getAttribute("mapaCiudades");
%> 
<div class="container-fluid">
	<h2>Alumnos registrados</h2>
	<form name="frmAlumnos" action="alumnos" method="post">
	<div class="alert alert-info">
		<a href="opcion" class="btn btn-primary">Regresar</a>&nbsp;&nbsp;
		Estado:
		<select name="Estado" onchange="document.frmAlumnos.submit();">
			<option value="P" <%=estado.equals("P")?"selected":""%>>Proceso</option>
			<option value="S" <%=estado.equals("S")?"selected":""%>>Solicitante</option>
			<option value="A" <%=estado.equals("A")?"selected":""%>>Admitido</option>
		</select>
		&nbsp;
		Filtro:
		<input type="text" class="input-medium search-query" placeholder="Buscar..." id="buscar">
	</div>
	</form>
	<table id="table" class="table table-condensed table-bordered">
	<thead>
	<tr>		
		<th width="1%">#</th>			
		<th width="14%">Apellidos</th>
		<th width="12%">Nombre</th>
		<th width="10%">Fecha Nacimiento</th>
		<th width="3%">Seguro</th>
		<th width="10%">Celular</th>
		<th width="10%">Correo</th>
		<th width="10%">Religión</th>
		<th width="10%">País</th>
		<th width="10%">Estado</th>
		<th width="5%">Ciudad</th>
		<th width="5%">CodigoUm</th>
		<th width="5%">Estado</th>		
	</tr>
	</thead>
	<tbody>
<%
	int row = 0;	
	for(MusiAlumno alumno : lisAlumnos){
		row++;
		String status = "0";
		if(alumno.getEstado().equals("P")){
			status = "En proceso";
		}else if(alumno.getEstado().equals("S")){
			status = "Solicitante";
		}else{
			status = "Admitido";
		}
		
		String religionNombre = "-";
		if (mapaReligiones.containsKey(alumno.getReligionId())){
			religionNombre = mapaReligiones.get(alumno.getReligionId()).getNombreCorto();
		}	
		String paisNombre = "-";
		if (mapaPaises.containsKey(alumno.getPaisId())){
			paisNombre = mapaPaises.get(alumno.getPaisId()).getNombrePais();
		}
		String estadoNombre = "-";
		if (mapaEstados.containsKey(alumno.getEstadoId())){
			estadoNombre = mapaEstados.get(alumno.getEstadoId()).getNombreEstado();
		}
		String ciudadNombre = "-";
		if (mapaCiudades.containsKey(alumno.getCiudadId())){
			ciudadNombre = mapaCiudades.get(alumno.getCiudadId()).getNombreCiudad();
		}
		
%>
	<tr>
		<td><%=row%></td>
		<td><%=alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()%></td>
		<td><%=alumno.getNombre()%></td>
		<td><%=alumno.getFechaNac()%></td>
		<td><%=alumno.getSeguro()%></td>
		<td><%=alumno.getCelular()%></td>
		<td><%=alumno.getEmail()%></td>
		<td><%=religionNombre%></td>
		<td><%=paisNombre%></td>
		<td><%=estadoNombre%></td>
		<td><%=ciudadNombre%></td>
		<td><%=alumno.getCodigoUM()%></td>
		<td><%=status%></td>
	</tr>
<%
	}
%>		
	</tbody>
	</table>
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	function Borrar(id){
		if (confirm("¿Estás seguro de borrar?")){
			document.location.href="borrar?Id="+id;
		}
	}
	
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>