<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.saii.spring.SaiiAlumno"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@ page import= "aca.catalogo.spring.CatEstado"%>
<%@ page import= "aca.catalogo.spring.CatPais"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%	
	String periodoId			= (String) request.getAttribute("periodoId");
	String grupoId				= (String) request.getAttribute("grupoId");
	String planUm				= (String) request.getAttribute("planUm");
	String fecha	 			= (String) request.getAttribute("fecha");
	String periodoNombre		= (String) request.getAttribute("periodoNombre");
	String nombreModalidad		= (String) request.getAttribute("nombreModalidad");
	
	List<SaiiAlumno> lisAlumnosPlanGrupo		= (List<SaiiAlumno>) request.getAttribute("lisAlumnosPlanGrupo");
	HashMap<String, CatEstado> mapEstados	= (HashMap<String, CatEstado>) request.getAttribute("mapEstados");
	HashMap<String, CatPais> mapPaises 		= (HashMap<String, CatPais>) request.getAttribute("mapPaises");

%>
<body>
<head>
	<script type="text/javascript">
		function borrar(folio,periodoId,grupoId,fecha){
			if(confirm("¿Está seguro que desea borrar este alumno?")){
				location.href = "borrarAlumno?Folio="+folio+"&PeriodoId="+periodoId+"&GrupoId="+grupoId+"&Fecha="+fecha;
			}
		}
	</script>
</head>
<div class="container-fluid">
	<h1>Alumnos</h1>
	<div class="alert alert-info">
		<a href="reporte?PeriodoId=<%=periodoId%>&GrupoId=<%=grupoId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
		<strong>Periodo:</strong> <%=periodoId%> <%=periodoNombre%>
		<a href="nuevoAlumno?PeriodoId=<%=periodoId%>&GrupoId=<%=grupoId%>&PlanUm=<%=planUm%>&Fecha=<%=fecha%>" class="btn btn-primary">Nuevo</a>
	</div>
	<table class="table table-condensed">
	<tr>
		<th>#</th>
		<th>Plan UM</th>
		<th>Matrícula</th>
		<th>Nombre</th>
		<th>Género</th>
		<th>Edad</th>
		<th>Grado</th>
		<th>Pais</th>
		<th>Estado</th>
		<th>Modalidad</th>
	</tr>
<%
	int row = 0;
	int cont = 0;
	for (SaiiAlumno saiiAlumno : lisAlumnosPlanGrupo){
		row++;
		
		String nombrePais = "";
		if(mapPaises.containsKey(saiiAlumno.getPaisId())){
			nombrePais = mapPaises.get(saiiAlumno.getPaisId()).getNombrePais();
		}
		
		String nombreEstado = "";
		if(mapEstados.containsKey(saiiAlumno.getPaisId()+saiiAlumno.getEstadoId())){
			nombreEstado = mapEstados.get(saiiAlumno.getPaisId()+saiiAlumno.getEstadoId()).getNombreEstado();
		}
		
%>
	<tr>
		<td>
			<%=row%>
			<a class="fas fa-edit" href="nuevoAlumno?PeriodoId=<%=periodoId%>&GrupoId=<%=grupoId%>&Folio=<%=saiiAlumno.getFolio()%>&PlanUm=<%=saiiAlumno.getPlanUm()%>&Fecha=<%=fecha%>"></a>
			<a class="fas fa-trash-alt" href="javascript:borrar('<%=saiiAlumno.getFolio()%>','<%=periodoId%>','<%=grupoId%>','<%=fecha%>');"></a>
		</td>
		<td>
<% 		if(planUm.equals(saiiAlumno.getPlanUm())){%>
			<strong><%=saiiAlumno.getPlanUm()%></strong>
<% 		}else{%>
			<%=saiiAlumno.getPlanUm()%>
<% 		}%>
		</td>
		<td><%=saiiAlumno.getCodigoPersonal()%></td>
		<td><%=saiiAlumno.getNombre()+" "+saiiAlumno.getPaterno()+" "+saiiAlumno.getMaterno()%></td>
		<td><%=saiiAlumno.getGenero().equals("H")?"Masculino":"Femenino"%></td>
		<td><%=saiiAlumno.getEdad()%></td>
		<td><%=saiiAlumno.getGrado()%></td>
		<td><%=nombrePais%></td>
		<td><%=nombreEstado%></td>
		<td><%=nombreModalidad %></td>
<%
	}
%>	
	</tr>
	</table>	
</div>	
</body>
