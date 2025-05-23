<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.musica.spring.MusiAlumno"%>
<%@page import="aca.musica.spring.MusiPadres"%>
<% 	
	String estado 		= request.getParameter("Estado")==null?"S":request.getParameter("Estado");
	
	// Listad de alumnos
	ArrayList<MusiAlumno> lisAlumnos				= (ArrayList<MusiAlumno>)request.getAttribute("lisAlumnos");
	
	// Mapa de alumnos en alum_personal 
	HashMap<String, String> mapaPersonal 	= (HashMap<String, String>) request.getAttribute("mapaPersonal");
	HashMap<String, MusiPadres> mapaPadres 	= (HashMap<String, MusiPadres>) request.getAttribute("mapaPadres");
	HashMap<String, String> mapaUbicacion 	= (HashMap<String, String>) request.getAttribute("mapaUbicacion");
	HashMap<String, String> mapaAcademico 	= (HashMap<String, String>) request.getAttribute("mapaAcademico");
	HashMap<String, String> mapaPlan	 	= (HashMap<String, String>) request.getAttribute("mapaPlan");
%> 
<div class="container-fluid">
	<h2>Alumnos registrados</h2>
	<form name="frmAlumnos" action="alumnos" method="post">
	<div class="alert alert-info d-flex align-items-center">
		Estado:
		<select name="Estado" class="form-select" style="width:150px" onchange="document.frmAlumnos.submit();">
			<option value="P" <%=estado.equals("P")?"selected":""%>>1.Proceso</option>
			<option value="S" <%=estado.equals("S")?"selected":""%>>2.Solicitante</option>
			<option value="A" <%=estado.equals("A")?"selected":""%>>3.Admitido</option>
		</select>
		&nbsp;
		Filtro: <input type="text" class="form-control" style="width:150px" placeholder="Buscar..." id="buscar">
	</div>
	</form>
	<table id="table" class="table table-condensed table-bordered">
	<thead>
		<tr>
			<th width="3%">Op.</th>
			<th width="1%">#</th>	
			<th width="12%">Apellidos</th>
			<th width="12%">Nombre</th>
			<th width="3%">Seguro</th>
			<th width="10%">Celular</th>
			<th width="10%">Correo</th>
			<th width="5%">Datos/Padres</th>
			<th width="5%">CodigoUm</th>
			<th width="5%">Estado</th>		
			<th width="5%">Personal</th>
			<th width="5%">Ubicación</th>
			<th width="5%">Académico</th>
			<th width="5%">Plan</th>
		</tr>
	</thead>
	<tbody>
<% 
		int row=0;
		for(MusiAlumno alumno : lisAlumnos){
			row++;
			String estadoNombre = "P";
			if(alumno.getEstado().equals("P")){
				estadoNombre = "1.En Proceso";
			}else if(alumno.getEstado().equals("S")){
				estadoNombre = "2.Solicitante";
			}else{
				estadoNombre = "3.Admitido";
			}
			
			String padres = "<span class='badge bg-warning'>NO</span>";
			if (mapaPadres.containsKey(alumno.getCodigoId())){
				padres = "<span class='badge bg-success'>SI</span>";
			}
			
			String personal = "<span class='badge bg-warning'>NO</span>";
			if (mapaPersonal.containsKey(alumno.getCodigoUM())){
				personal = "<span class='badge bg-success'>SI</span>";
			}
			String ubicacion = "<span class='badge bg-warning'>NO</span>";
			if (mapaUbicacion.containsKey(alumno.getCodigoUM())){
				ubicacion = "<span class='badge bg-success'>SI</span>";
			}
			String academico = "<span class='badge bg-warning'>NO</span>";
			if (mapaAcademico.containsKey(alumno.getCodigoUM())){
				academico = "<span class='badge bg-success'>SI</span>";
			}
			String plan = "<span class='badge bg-warning'>NO</span>";
			if (mapaPlan.containsKey(alumno.getCodigoUM())){
				plan = "<span class='badge bg-success'>SI</span>";
			}			
%>
		<tr>
			<td>
			<%	if (alumno.getEstado().equals("S") && alumno.getCodigoUM().length() < 7){%>
				<a href="traspasar?Codigo=<%=alumno.getCodigoId()%>&Estado=<%=estado%>"><i class="fas fa-arrow-circle-right"></i></a>
			<%	} %>					
			</td>
			<td><%=row%></td>
			<td><%=alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()%></td>
			<td><%=alumno.getNombre()%></td>	
			<td><%=alumno.getSeguro()%></td>
			<td><%=alumno.getCelular()%></td>
			<td><%=alumno.getEmail()%></td>
			<td><%=padres%></td>
			<td>
				<a href="editarCodigo?Codigo=<%=alumno.getCodigoId()%>&Estado=<%=estado%>"><i class="fas fa-pen-square"></i></a>&nbsp; &nbsp;[<%=alumno.getCodigoUM()%>]
			</td>
			<td><%=estadoNombre%></td>
			<td><%=personal%></td>
			<td><%=ubicacion%></td>
			<td><%=academico%></td>
			<td><%=plan%></td>
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