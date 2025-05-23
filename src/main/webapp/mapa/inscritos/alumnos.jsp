<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<%@ page import= "aca.alumno.spring.AlumAcademico"%>
<%@ page import= "aca.vista.spring.Estadistica"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>
<%
	String idCurso 			= request.getParameter("idCurso");	
	String planId 			= request.getParameter("planId");
	String facultad 		= request.getParameter("facultad");
	String ciclo  			= request.getParameter("ciclo");
	String cursoClave  		= request.getParameter("cursoClave");
	String materiaNombre  	= (String)request.getAttribute("materiaNombre");
	
	String facCorto 	= "";
	
	List<Estadistica> lisInscritos 					= (List<Estadistica>)request.getAttribute("lisInscritos");
	
	HashMap<String, CatFacultad> mapaFacultades 	= (HashMap<String, CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String, CatCarrera> mapaCarreras 		= (HashMap<String, CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String, CatModalidad> mapaModalidades	= (HashMap<String, CatModalidad>)request.getAttribute("mapaModalidades");
	HashMap<String, AlumAcademico> mapaResidencia	= (HashMap<String, AlumAcademico>)request.getAttribute("mapaResidencia");
	HashMap<String,String> mapaInscritosPorMateria 	= (HashMap<String,String>) request.getAttribute("mapaInscritosPorMateria");
%>
<style>
	td{
		padding:5px;
	}
</style>
</head>
<body>
<div class="container-fluid">
	<h3><spring:message code="aca.mapa.inscritos.alumnos.Frase1"/><small class="text-muted fs-5">(<%=cursoClave+"-"+materiaNombre%>)</small></h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="materia?idCurso=<%=idCurso%>&cursoClave=<%=cursoClave%>&ciclo=<%=ciclo%>&planId=<%=planId%>&facultad=<%=facultad%>"><spring:message code="aca.Regresar"/></a>
	</div>	
	<table  align="center" class="table table-bordered table-sm ">
	<tr class="table-info">
		<th><spring:message code="aca.Numero"/></th>
		<th><spring:message code="aca.Facultad"/></th>
		<th width="25%"><spring:message code="aca.Carrera"/></th>
		<th><spring:message code="aca.Plan"/></th>
		<th><spring:message code="aca.Matricula"/></th>
		<th><spring:message code="aca.Alumnos"/></th>
		<th><spring:message code="aca.Carga"/></th>
		<th><spring:message code="aca.Modalidad"/></th>
		<th><spring:message code="aca.Residencia"/></th>
		<th><spring:message code="aca.Dormitorio"/></th>
		<th><spring:message code="aca.NumMaterias"/></th>
	</tr>
<%
	int cont = 0;
	String codigoAnterior = "0";
	for(Estadistica inscrito : lisInscritos){ 
		cont++;
		
		// Busca el nombre corto de la facultad
		facCorto = "-";
		String carreraNombre = "";
		if (mapaCarreras.containsKey(inscrito.getCarreraId())){
			CatCarrera car = mapaCarreras.get(inscrito.getCarreraId());
			carreraNombre = car.getNombreCarrera();
			if (mapaFacultades.containsKey(car.getFacultadId())){
				CatFacultad fac = mapaFacultades.get(car.getFacultadId());
				facCorto = fac.getNombreCorto();
			}
		}
		
		String modalidadNombre = "-";
		if (mapaModalidades.containsKey(inscrito.getModalidadId())){
			modalidadNombre = mapaModalidades.get(inscrito.getModalidadId()).getNombreModalidad();
		}
		
		String residencia = "-";
		if(mapaResidencia.containsKey(inscrito.getCodigoPersonal())){
			residencia = mapaResidencia.get(inscrito.getCodigoPersonal()).getResidenciaId();
		}
		
		String dormitorio = "-";
		if(mapaResidencia.containsKey(inscrito.getCodigoPersonal())){
			dormitorio = mapaResidencia.get(inscrito.getCodigoPersonal()).getDormitorio();
		}
		
		String numMaterias = "0";
		if(mapaInscritosPorMateria.containsKey(inscrito.getCodigoPersonal())){
			numMaterias = mapaInscritosPorMateria.get(inscrito.getCodigoPersonal());
		}
		if ( !codigoAnterior.equals(inscrito.getCodigoPersonal()) ){			
			codigoAnterior = inscrito.getCodigoPersonal();
%>
	<tr>
		<td align="center"><%=cont%></td>
		<td><%=facCorto%></td>
		<td><%=inscrito.getCarreraId()+" - "+carreraNombre %></td>
		<td><%=inscrito.getPlanId() %></td>
		<td><%=inscrito.getCodigoPersonal() %></td>
		<td><%=inscrito.getNombre()+" "+inscrito.getApellidoPaterno()+" "+inscrito.getApellidoMaterno() %></td>
		<td align="center"><%=inscrito.getCargaId() %></td>
		<td><%=modalidadNombre %></td>
		<td><%=residencia.equals("E")?"Externo":"Interno"%></td>
		<td><%=dormitorio%></td>
		<td><%=numMaterias%></td>
	</tr>
<%		} 
	}
	if(lisInscritos.size()==0){
%>
	<tr>
		<td colspan="4" align="center"><spring:message code="aca.mapa.inscritos.alumnos.Frase2"/></td>
	</tr>	
<%  } %>
	</table>
</div>
</body>