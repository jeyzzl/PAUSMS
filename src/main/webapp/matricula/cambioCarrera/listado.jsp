<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="aca.matricula.spring.CambioCarrera"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	AlumPersonal alumno	= (AlumPersonal)request.getAttribute("alumno");

	List<CambioCarrera> lisCambioCarrera	= (List<CambioCarrera>)request.getAttribute("lisCambioCarrera");
	HashMap<String,CatCarrera> mapaCarreras	= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,String> mapCarreraPlan	= (HashMap<String,String>)request.getAttribute("mapCarreraPlan");
%>
<div class="container-fluid">
	<h2>Course change requests<small class="text-muted fs-5">(<%=alumno.getCodigoPersonal()+" - "+alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()%>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="nuevaSolicitud">New Request</a>
	</div>
	<table class="table table-sm table-bordered">
		<tr class="table-info">
			<th>#</th>
			<th>PDF</th>
			<th>Original Course</th>
			<th>New Course</th>
			<th>Date</th>
		</tr>
<%
	int row=0;	
	for (CambioCarrera solicitud : lisCambioCarrera){
		row++;
		
		String carreraBaja = "";
		String carreraAlta = "";
		
		if(mapCarreraPlan.containsKey(solicitud.getCarreraBaja())){
			carreraBaja = mapCarreraPlan.get(solicitud.getCarreraBaja());
		}

		if(mapCarreraPlan.containsKey(solicitud.getCarreraAlta())){
			carreraAlta = mapCarreraPlan.get(solicitud.getCarreraAlta());
		}
		
		if(mapaCarreras.containsKey(carreraBaja)){
			carreraBaja = mapaCarreras.get(carreraBaja).getNombreCarrera();
		}

		if(mapaCarreras.containsKey(carreraAlta)){
			carreraAlta = mapaCarreras.get(carreraAlta).getNombreCarrera();
		}
%>
		<tr>
			<td><%=row%></td>
			<td><a class="btn btn-warning" href="pdf?SolicitudId=<%=solicitud.getSolicitudId()%>" target="_blank"><i class="fas fa-file-pdf"></i></a>&nbsp;
				<a onclick="javascript:borrarEvento('<%=solicitud.getSolicitudId()%>')" class="btn btn-danger" style="width:40px;" title="Delete"><i class="fas fa-trash-alt"></i></a>
			</td>
			<td><%=carreraBaja%></td>
			<td><%=carreraAlta%></td>
			<td><%=solicitud.getFecha()%></td>
<%	} %>
		</tr>
	</table>
</div>
<script type="text/javascript">
	function borrarEvento(solicitudId){
 		if (confirm("Are you sure to delete the degree change?") == true) {
			document.location.href = "eliminar?SolicitudId="+solicitudId;
  		}
	}
</script>