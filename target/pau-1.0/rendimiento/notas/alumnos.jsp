<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.vista.spring.Estadistica"%>
<%@ page import="aca.vista.spring.CargaAcademica"%>
<%@ page import="aca.vista.spring.AlumnoCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>
<%
	String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
	String nombreCarga		= (String)request.getAttribute("nombreCarga");
	String nombreCarrera	= (String)request.getAttribute("nombreCarrera");

	List<Estadistica> listaEstadistica 		= (List<Estadistica>) request.getAttribute("listaEstadistica");
	List<CargaAcademica> lisMaterias		= (List<CargaAcademica>) request.getAttribute("lisMaterias");
	HashMap<String, AlumnoCurso> mapaNotas 	= (HashMap<String, AlumnoCurso>) request.getAttribute("mapaNotas");	
%>
</head>
<div class="container-fluid">
<h2>Concentrado de Calificaciones<small class="text-muted fs-4">( <%=nombreCarga%> - <%=nombreCarrera%> )</small></h2>	
<div class="alert alert-info">
	<a href="reporte?CargaId=<%=cargaId%>&CarreraId=<%=carreraId%>" align="left" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	&nbsp;
</div>
	<table class="table table-bordered">
	<thead class="table-info">
		<tr>
			<th ><spring:message code="aca.Numero"/></th>
			<th ><spring:message code="aca.Plan"/></th>
			<th ><spring:message code="aca.Matricula"/></th>
			<th><spring:message code="aca.Nombre"/></th>
<%		for(CargaAcademica materia : lisMaterias){%>
			<th><font size="1"><%=materia.getCiclo()%>"."<%=materia.getNombreCurso()%></font></th>
<%}%>
		</tr>
	</thead>
<% 		
		int row = 0;
		for(Estadistica alumno : listaEstadistica){
			row++;
%>
		<tr>
			<td align="center"><%=row%></td>
			<td align="center"><%=alumno.getPlanId()%></td>
			<td align="center"><%=alumno.getCodigoPersonal()%></td>
			<td><%=alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()+" "+alumno.getNombre() %></td>
<%			
			for(CargaAcademica carga : lisMaterias){
				String nota 		= "0";
				String tipoNota		= "0";
				String textoNota	= "-";
				if (mapaNotas.containsKey(alumno.getCodigoPersonal()+carga.getCursoId())){
					nota 		= mapaNotas.get(alumno.getCodigoPersonal()+carga.getCursoId()).getNota();
					tipoNota 	= mapaNotas.get(alumno.getCodigoPersonal()+carga.getCursoId()).getTipoCalId();
					if (tipoNota.equals("1")){
						textoNota = "<span class='badge badge-inverse'>"+nota+"</span>";
					}else if (tipoNota.equals("2")||tipoNota.equals("3")||tipoNota.equals("4")){
						textoNota = "<span class='badge badge-warning'>"+nota+"</span>";
					}else{
						textoNota = "<span class='badge'>"+nota+"</span>";
					}
				}
%>
			<td align="center"><%=textoNota%></td>
<%			}
%>
		</tr>
	<% 
		}
	%>
	</table>
</div>