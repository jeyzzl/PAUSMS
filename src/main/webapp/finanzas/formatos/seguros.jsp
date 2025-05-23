<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.alumno.spring.AlumDocumento"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import="aca.bec.spring.BecSolicitud"%>
<%@page import="aca.acceso.spring.Acceso"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<html>
<head></head>
<%
	Acceso acceso 		= (Acceso) request.getAttribute("acceso");	
	String tipos 		= (String) request.getAttribute("tipos");
	String fechaIni		=  request.getParameter("FechaIni")==null?"01"+aca.util.Fecha.getHoy().substring(2,10):request.getParameter("FechaIni");
	String fechaFin		=  request.getParameter("FechaFin")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFin");

	List<AlumDocumento> lisSolicitudesSeguro 				= (List<AlumDocumento>)request.getAttribute("lisSolicitudesSeguro");
	HashMap<String, AlumPersonal> mapaAlumnosEnSolicitud	= (HashMap<String, AlumPersonal>)request.getAttribute("mapaAlumnosEnSolicitud");
	HashMap<String, MapaPlan> mapaPlanes					= (HashMap<String, MapaPlan>)request.getAttribute("mapaPlanes");
%>
<body>
<div class="container-fluid">
	<h1>Solicitudes</h1>
	<form name="frmSeguros" action="seguros" method="post">
	<div class="alert alert-info d-flex align-items-center">
		Tipos:
		<select name="Tipos" onchange="cambioTipo(this.value)" class="form-select"  style="width:120px">
			<option value="0" <%=tipos.equals("'2','3','4'") ? "selected" : ""%>>Todos</option>
			<option value="'2'" <%=tipos.equals("'2'") ? "selected" : ""%>>Seguro accidentes</option>
			<option value="'3'" <%=tipos.equals("'3'") ? "selected" : ""%>>Educación garantizada</option>
			<option value="'4'" <%=tipos.equals("'4'") ? "selected" : ""%>>Seguro de vida</option>
		</select>&nbsp;
			Fecha Inicio: <input type="text" class="form-control" style="width:120px" data-date-format="dd/mm/yyyy" id="FechaIni" name="FechaIni" value="<%=fechaIni%>" required/>&nbsp;
			Fecha Final: <input type="text" class="form-control" style="width:120px" data-date-format="dd/mm/yyyy" id="FechaFin" name="FechaFin" value="<%=fechaFin%>" required/>&nbsp;
			<a href="javascript:Refrescar()" class="btn btn-primary"><i class="fas fa-sync-alt"></i></a>&nbsp;
			<input type="text" class=" form-control search-query" style="width:150px" placeholder="Buscar..." id="buscar">
	</div>
	</form>
	<table id="table" style="width:100%" class="table table-condensed">
	<tr>
		<th>#</th>
		<th>Op.</th>
		<th>Matr&iacute;cula</th>
		<th><spring:message code="aca.Nombre"/></th>
		<th><spring:message code="aca.Plan"/></th>
		<th>Fecha</th>
		<th>Estado</th>
		<th>Nombre</th>
		<th>Tipo</th>
	</tr>
<%
	int row = 0;
	for (AlumDocumento alumDocumento: lisSolicitudesSeguro) {
		row++;
		
		String nombreAlumno = "";
		if(mapaAlumnosEnSolicitud.containsKey(alumDocumento.getCodigoPersonal())){
			nombreAlumno = mapaAlumnosEnSolicitud.get(alumDocumento.getCodigoPersonal()).getNombre()+" "+mapaAlumnosEnSolicitud.get(alumDocumento.getCodigoPersonal()).getApellidoPaterno()+" "+mapaAlumnosEnSolicitud.get(alumDocumento.getCodigoPersonal()).getApellidoMaterno();
		}
		
		String nombrePlan	= "";
		String carreraId	= "";
		if(mapaPlanes.containsKey(alumDocumento.getPlanId())){
			nombrePlan	= mapaPlanes.get(alumDocumento.getPlanId()).getCarreraSe();
			carreraId	= mapaPlanes.get(alumDocumento.getPlanId()).getCarreraId();
		}
		
		String nombreTipo = "";
		
		if(alumDocumento.getTipo().equals("2")){
			nombreTipo = "Seguro accidentes";
		}else if(alumDocumento.getTipo().equals("3")){
			nombreTipo = "Educación garantizada";
		}else if(alumDocumento.getTipo().equals("4")){
			nombreTipo = "Seguro de vida";
		}
		
		if(acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") || acceso.getAccesos().contains(carreraId)){
%>
	<tr>
		<td><%=row%></td>
		<td>
<%			if (alumDocumento.getEstado().equals("A")){ %>			
			<a href="cambiarEstado?CodigoPersonal=<%=alumDocumento.getCodigoPersonal()%>&Folio=<%=alumDocumento.getFolio()%>&Estado=S" title="Cambiar estado"><span class="badge bg-dark">A</span></a>
<%			}else{%>
			<a href="cambiarEstado?CodigoPersonal=<%=alumDocumento.getCodigoPersonal()%>&Folio=<%=alumDocumento.getFolio()%>&Estado=A" title="Cambiar estado"><span class="badge bg-warning">S</span></a>
<%			}%>			
		</td>
		<td><%=alumDocumento.getCodigoPersonal()%></td>
		<td><%=nombreAlumno%></td>		
		<td><%=alumDocumento.getPlanId()%></td>
		<td><%=alumDocumento.getFecha()%></td>
		<td><%=alumDocumento.getEstado().equals("S")?"Solicitud":"Autorizado"%></td>
		<td>
			<a href="bajarArchivo?CodigoPersonal=<%=alumDocumento.getCodigoPersonal()%>&Folio=<%=alumDocumento.getFolio()%>" title="Descargar"><i class="fas fa-download"></i> <%=alumDocumento.getNombre()%></a>
		</td>
		<td><%=nombreTipo%></td>
		
	</tr>
<%		}
	}
%>
	</table>
</div>
<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script src="../../js/search.js"></script>
<script type="text/javascript">
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
	jQuery('#buscar').search();
	
	function Borrar(CategoriaId) {
		if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
			document.location = "borrar?CategoriaId="+CategoriaId;
		}
	}

	function cambioTipo(tipos) {
		document.location = "seguros?Tipos="+tipos;
	}
	
	function Refrescar(){
		document.frmSeguros.submit();		
	}
 </script>
</body>
</html>
