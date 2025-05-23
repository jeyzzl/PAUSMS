<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.mentores.spring.MentAlumno"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%  
	String mentorId	 		= (String) session.getAttribute("codigoPersonal");
	String periodoId		= request.getParameter("PeriodoId")==null?session.getAttribute("ciclo").toString():request.getParameter("PeriodoId");	
	String fechaHoy 		= aca.util.Fecha.getHoy(); 
	String mentorNombre		= (String) request.getAttribute("mentorNombre");
	
	String sNomMentor 		= "";	
	String sNomAlumno 		= "";	
	int nContador			= 0;
	int row 				= 0;	
		
	List<MentAlumno> lisAlumnos					= (List<MentAlumno>) request.getAttribute("lisAlumnos");
	HashMap<String,String> mapaAlumnos	 		= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	HashMap<String,CatFacultad> mapaFacultades	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
%>
<div class="container-fluid">
	<h2><small class="text-muted fs-4">Portal del Mentor: &nbsp;( <%=mentorId%> - <%=mentorNombre%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary btn-sm" href="portal?PeriodoId=<%=periodoId%>"><i class="fas fa-arrow-left"></i><spring:message code='aca.Regresar'/></a>&nbsp;&nbsp;
	</div>
	<table style="width:70%" class="table table-condensed">  
<%
	for (int i = 0; i<lisAlumnos.size(); i++){
		MentAlumno malumno = (MentAlumno)lisAlumnos.get(i);
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(malumno.getCodigoPersonal()) ){
			alumnoNombre = mapaAlumnos.get(malumno.getCodigoPersonal());
		}
		String carreraNombre 	= "";
		String facultadNombre 	= "";
		if (mapaCarreras.containsKey(malumno.getCarreraId())){
			carreraNombre 	= mapaCarreras.get(malumno.getCarreraId()).getNombreCarrera();
			facultadNombre  = mapaCarreras.get(malumno.getCarreraId()).getFacultadId();
			if (mapaFacultades.containsKey(facultadNombre)){
				facultadNombre =  mapaFacultades.get(facultadNombre).getNombreCorto();
			}
		}
		row++;
		if ( (row % 4)== 1){ %>
  		<tr>     
<%		}%>  
		<td align="center" valign="top">
			<div class="card border-dark bg-light mb-2 mr-1" style="max-width: 15rem;">
				<div class="card-header"><i class="far fa-comment" data-bs-toggle="tooltip" data-placement="top" title="<%=malumno.getCodigoPersonal()%>"></i> <%=alumnoNombre%></div>
			  	<div class="card-body ">
			    	<img src="../../foto?Codigo=<%=malumno.getCodigoPersonal()%>" class="rounded" width="150">
			  	</div>
			  	<div class="card-footer">
			    	<span data-bs-toggle="tooltip" data-placement="top" title="<%=carreraNombre%>"><i class="fas fa-book-reader"></i> <%=facultadNombre%></span>
			  	</div>
			</div>			
		</td>
<%		if ( (row % 4)==0){%>	
  		</tr>
<%		}
	}
%>
	</table>
</div>
<script>	
	$(function () {
  		$('[data-bs-toggle="tooltip"]').tooltip()
	});
</script>