<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.alumno.spring.AlumDocumento"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import="aca.bec.spring.BecSolicitud"%>
<%@ page import="aca.bec.spring.BecSolPeriodo"%>
<%@page import="aca.acceso.spring.Acceso"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<html>
<head>
 	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<link rel="stylesheet" href="../../js/chosen/chosen.css" />
</head>
<%
	String tipos 			= request.getParameter("Tipos")==null?"T":request.getParameter("Tipos");
	List<AlumDocumento> lisSolicitudes						= (List<AlumDocumento>)request.getAttribute("lisSolicitudes");
	List<BecSolPeriodo> lisBecSolPeriodo					= (List<BecSolPeriodo>)request.getAttribute("lisBecSolPeriodo");
	HashMap<String, AlumPersonal> mapaAlumnosEnSolicitud	= (HashMap<String, AlumPersonal>)request.getAttribute("mapaAlumnosEnSolicitud");
	HashMap<String, MapaPlan> mapaPlanes					= (HashMap<String, MapaPlan>)request.getAttribute("mapaPlanes");
	HashMap<String, BecSolicitud> mapaSolicitudes			= (HashMap<String, BecSolicitud>)request.getAttribute("mapaSolicitudes");
	HashMap<String, BecSolPeriodo> mapaBecSolPeriodo		= (HashMap<String, BecSolPeriodo>)request.getAttribute("mapaBecSolPeriodo");
	
	Acceso acceso 			= (Acceso) request.getAttribute("acceso");
	AlumDocumento documento = new AlumDocumento();
	
	String periodoId	=  request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
	
	String fechaIni 			= (String) session.getAttribute("fechaInicio");
	String fechaFin 			= (String) session.getAttribute("fechaFinal");	
%>
<body>
<div class="container-fluid">
	<h1>Solicitudes</h1>
	<form name="frmBecas" action="listado" method="post">
		<div class="alert alert-info">
			Filtro:
			<select name="Tipos" onchange='javascript:document.frmBecas.submit();' style="width: 150px">
				<option value="T" <%=tipos.equals("T")?"Selected":""%>>Todos</option>
				<option value="A" <%=tipos.equals("A")?"Selected":""%>>Autorizado</option>
				<option value="S" <%=tipos.equals("S")?"Selected":""%>>Solicitud</option>
				<option value="I" <%=tipos.equals("I")?"Selected":""%>>Inactivo</option>
			</select>&nbsp;
			Período:
			<select name="PeriodoId" onchange='javascript:document.frmBecas.submit();' style="width: 150px">
				<option value="0" <%=tipos.equals("T")?"Selected":""%>>Todos</option>
<% 			for(BecSolPeriodo objeto : lisBecSolPeriodo){%>
				<option value="<%=objeto.getPeriodoId()%>" <%=objeto.getPeriodoId().equals(periodoId)?"Selected":""%>><%=objeto.getPeriodoNombre()%></option>
<% 			}%>
			</select>&nbsp;
			Fecha Inicio: <input type="text" class="input input-medium" data-date-format="dd/mm/yyyy" id="FechaIni" name="FechaIni" value="<%=fechaIni%>" required/>&nbsp;
			Fecha Final: <input type="text" class="input input-medium" data-date-format="dd/mm/yyyy" id="FechaFin" name="FechaFin" value="<%=fechaFin%>" required/>&nbsp;
			<a href="javascript:Refrescar()" class="btn btn-primary"><i class="fas fa-sync"></i></a>&nbsp;
			<input type="text" class="input-medium search-query" placeholder="Buscar..." id="buscar">
		</div>
	</form><hr>
	<table id="table" style="width:100%" class="table table-sm">
	<thead>
	<tr>
		<th>#</th>
		<th>Op.</th>
		<th>Matr&iacute;cula</th>
		<th><spring:message code="aca.Nombre"/></th>
		<th>Plan</th>
		<th>Periodo</th>
		<th>Fecha</th>
		<th>Fecha Modi.</th>
		<th>Estado</th>
<%	if(acceso.getBecas().equals("S")){%>		
		<th>Cambiar</th>
<%	} %>		
		<th>Nombre</th>
		<th>% Coor.</th>
		<th>% Com.</th>
		<th>Comentario</th>
	</tr>
	</thead>
<%				
	int row = 0;
	for (AlumDocumento alumDocumento: lisSolicitudes) {
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
		
		String porcentajeCoord	= "";
		String porcentajeCom 	= "";
		String becSolPeriodoId 	= "0";
		if(mapaSolicitudes.containsKey(alumDocumento.getCodigoPersonal()+alumDocumento.getFolio())){
			porcentajeCoord = mapaSolicitudes.get(alumDocumento.getCodigoPersonal()+alumDocumento.getFolio()).getPorCoordinador();
			porcentajeCom 	= mapaSolicitudes.get(alumDocumento.getCodigoPersonal()+alumDocumento.getFolio()).getPorComision();
			becSolPeriodoId	= mapaSolicitudes.get(alumDocumento.getCodigoPersonal()+alumDocumento.getFolio()).getPeriodoId();
		}

		boolean muestra = true;
		BecSolPeriodo becSolPeriodo = new BecSolPeriodo();
		if(mapaBecSolPeriodo.containsKey(becSolPeriodoId)){
			becSolPeriodo = mapaBecSolPeriodo.get(becSolPeriodoId);
		}
		
		if(!periodoId.equals("0")){
			if(!periodoId.equals(becSolPeriodoId)){
				muestra = false;
			}
		}
		
		String comentario = "-";
		if(mapaSolicitudes.containsKey(alumDocumento.getCodigoPersonal()+alumDocumento.getFolio())){
			comentario = mapaSolicitudes.get(alumDocumento.getCodigoPersonal()+alumDocumento.getFolio()).getComentario();
		}
		
		String estado = "Inactivo";
		if (alumDocumento.getEstado().equals("S")) estado = "<span class='badge bg-dark'>Solicitud</span>";
		if (alumDocumento.getEstado().equals("A")) estado = "<span class='badge bg-info'>Autorizado</span>";
		if (alumDocumento.getEstado().equals("I")) estado = "<span class='badge bg-warning'>Inactivo</span>";
			
		if(acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") || acceso.getAccesos().contains(carreraId)){		
			if(muestra){
%>
	<tbody>
	<tr>
		<td><%=row%></td>
		<td>
<%			if(acceso.getAdministrador().equals("S") || acceso.getAccesos().contains(carreraId)){ %>		
			<a href="nuevaBecSolicitud?Matricula=<%=alumDocumento.getCodigoPersonal()%>&Folio=<%=alumDocumento.getFolio()%>&Tipos=<%=tipos%>&FechaIni=<%=fechaIni%>&FechaFin=<%=fechaFin%>" title="Agregar porcentaje coordinador">				
				<i class="fas fa-pen-square"></i>			
			</a>
			&nbsp;
<%			} %>			
<%			if(acceso.getBecas().equals("S")){%>			
			<a href="nuevaPorComision?Matricula=<%=alumDocumento.getCodigoPersonal()%>&Folio=<%=alumDocumento.getFolio()%>&Tipos=<%=tipos%>&FechaIni=<%=fechaIni%>&FechaFin=<%=fechaFin%>" title="Agregar porcentaje comision">
				<i class="fas fa-save"></i>
			</a>
<%			} %>			
		</td>
		<td><%=alumDocumento.getCodigoPersonal()%></td>
		<td><%=nombreAlumno%></td>
		<td><%=alumDocumento.getPlanId()%></td>
		<td><%=becSolPeriodo.getPeriodoNombre()%></td>
		<td><%=alumDocumento.getFechaCrea()%></td>
		<td><%=alumDocumento.getFecha()%></td>
		<td><%=estado%></td>
<%			if(acceso.getBecas().equals("S")){%>		
		<td>
			<a href="cambiarestado?Matricula=<%=alumDocumento.getCodigoPersonal()%>&Folio=<%=alumDocumento.getFolio()%>&Estado=S"><span class="badge bg-info">S</span></a>
			<a href="cambiarestado?Matricula=<%=alumDocumento.getCodigoPersonal()%>&Folio=<%=alumDocumento.getFolio()%>&Estado=A"><span class="badge bg-dark">A</span></a>
			<a href="cambiarestado?Matricula=<%=alumDocumento.getCodigoPersonal()%>&Folio=<%=alumDocumento.getFolio()%>&Estado=I"><span class="badge bg-warning">I</span></a>
		</td>
<% 			}%>		
		<td>
			<a href="bajarArchivo?CodigoPersonal=<%=alumDocumento.getCodigoPersonal()%>&Folio=<%=alumDocumento.getFolio()%>" title="Descargar"><i class="fas fa-download"></i> <%=alumDocumento.getNombre()%></a>
		</td>
		<td><%=porcentajeCoord%></td>
		<td><%=porcentajeCom%></td>
		<td><%=comentario%></td>
	</tr>
<%		}
		}
	}  %>
	</tbody>
	</table>
</div>
</body>
<script type="text/javascript" src="../../js/search.js"></script>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">
	jQuery('#buscar').focus().search({table:jQuery("#table")});
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();

	function Borrar(CategoriaId) {
		if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
			document.location = "borrar?CategoriaId="+CategoriaId;
		}
	}
	function Refrescar(){
		document.frmBecas.submit();		
	}

 </script>
</html>
