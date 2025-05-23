<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.vista.spring.CargaAcademica"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<%@ page import= "aca.vista.spring.CargaHorario"%>
<%@ page import= "aca.catalogo.spring.CatSalon"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%	
	String cargaId 			= (String)session.getAttribute("cargaId"); 
	String nombreCarga 		= (String)request.getAttribute("nombreCarga");

	List<CargaAcademica> lisMaterias 				= (List<CargaAcademica>) request.getAttribute("lisMaterias");
	List<CargaHorario> lisHorarios					= (List<CargaHorario>) request.getAttribute("lisHorarios");
	HashMap<String, CatModalidad> mapaModalidades 	= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidades");
	HashMap<String, String> mapaMaestros 			= (HashMap<String,String>) request.getAttribute("mapaMaestros");
	HashMap<String, CatSalon> mapaSalones 			= (HashMap<String,CatSalon>) request.getAttribute("mapaSalones");
	HashMap<String, MapaCurso> mapaCursos 			= (HashMap<String,MapaCurso>) request.getAttribute("mapaCursos");
%>

<div class="container-fluid">
	<h2>Subject List <small class="text-muted fs-5">(  <%=cargaId%> | <%=nombreCarga%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="elegir"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th><spring:message code="aca.Plan" /></th>
		<th><spring:message code="aca.Ciclo" /></th>
		<th><spring:message code="aca.Acta" /></th>		
		<th><spring:message code="aca.Materia" /></th>
		<th><spring:message code="aca.Crd" /></th>	
		<th><spring:message code="aca.TH" /></th>
		<th><spring:message code="aca.Maestro" /></th>
		<th>Hour</th>
		<th>Classroom</th>		
		<th>Schedule#1</th>
		<th>Schedule#2</th>
		<th>Schedule#3</th>
		<th>Schedule#4</th>
	</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for (CargaAcademica materia : lisMaterias){
		row++;
		
		String maestro = materia.getCodigoPersonal()==null?"0":materia.getCodigoPersonal();
		if (mapaMaestros.containsKey(maestro)){
			maestro = mapaMaestros.get(maestro);
		}
		
		String modalidad = "0";
		if (mapaModalidades.containsKey(materia.getModalidadId() )){
			modalidad = mapaModalidades.get(materia.getModalidadId()).getNombreModalidad();
		}
		
		String pideHora 	= "N";
		String pideSalon 	= "N";
		if (mapaCursos.containsKey(materia.getCursoId())){
			pideHora 	= mapaCursos.get(materia.getCursoId()).getHorario();
			pideSalon 	= mapaCursos.get(materia.getCursoId()).getSalon();
		}
		
		String horario1 = ""; 
		String horario2 = "";
		String horario3 = "";
		String horario4 = "";
		int cont=0;
		for ( CargaHorario hora : lisHorarios){
			if (materia.getCursoCargaId().equals(hora.getCursoCargaId())){
				cont++;
				String diaNombre = "";
				if (hora.getDia().equals("1")) diaNombre = "<span class='badge bg-warning rounded-pill'>SUN</span>";
				if (hora.getDia().equals("2")) diaNombre = "<span class='badge bg-info rounded-pill'>MON</span>";
				if (hora.getDia().equals("3")) diaNombre = "<span class='badge bg-secondary rounded-pill'>TUE</span>";
				if (hora.getDia().equals("4")) diaNombre = "<span class='badge bg-success rounded-pill'>WED</span>";
				if (hora.getDia().equals("5")) diaNombre = "<span class='badge bg-primary rounded-pill'>THU</span>";
				if (hora.getDia().equals("6")) diaNombre = "<span class='badge bg-dark rounded-pill'>FRI</span>";
				if (hora.getDia().equals("7")) diaNombre = "<span class='badge bg-danger rounded-pill'>SAT</span>";
				
				String salonNombre = "";
				if (mapaSalones.containsKey(hora.getSalonId())){
					salonNombre = mapaSalones.get(hora.getSalonId()).getNombreSalon();
				}
				
				if (cont==1) horario1 = salonNombre+"| "+diaNombre+"| "+hora.getHoraInicio()+":"+hora.getMinutoInicio()+"-"+hora.getHoraFinal()+":"+hora.getMinutoFinal();
				if (cont==2) horario2 = salonNombre+"| "+diaNombre+"| "+hora.getHoraInicio()+":"+hora.getMinutoInicio()+"-"+hora.getHoraFinal()+":"+hora.getMinutoFinal();
				if (cont==3) horario3 = salonNombre+"| "+diaNombre+"| "+hora.getHoraInicio()+":"+hora.getMinutoInicio()+"-"+hora.getHoraFinal()+":"+hora.getMinutoFinal();
				if (cont==4) horario4 = salonNombre+"| "+diaNombre+"| "+hora.getHoraInicio()+":"+hora.getMinutoInicio()+"-"+hora.getHoraFinal()+":"+hora.getMinutoFinal();
			}
		}
%>		
	<tr>
		<td><%=row%></td>
		<td align="center"><%=materia.getPlanId()%></td>		
		<td align="center"><%=materia.getCiclo()%></td>
		<td><%=materia.getCursoCargaId().split("-")[1]%></td>		
		<td><%=materia.getNombreCurso()%> <%=materia.getGrupo()%></td>
		<td align="center"><%=materia.getCreditos()%></td>
		<td align="center"><%=materia.getHh()%></td>			
		<td><i><%=maestro%></i></td>		
		<td align="LEFT"><%=pideHora.equals("S")?"YES":"NO"%></td>
		<td align="LEFT"><%=pideSalon.equals("S")?"YES":"NO"%></td>
		<td align="LEFT"><%=horario1%></td>
		<td align="LEFT"><%=horario2%></td>
		<td align="LEFT"><%=horario3%></td>
		<td align="LEFT"><%=horario4%></td>
	</tr>
<%		
	} 
%>
	</tbody>
	</table>
 </div>