<%@ page import= "java.util.List"%>
<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.vista.spring.Inscritos"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ include file= "menu.jsp" %>
<html>
<head></head>
<%
	String colorPortal 			= session.getAttribute("colorPortal")==null?"":(String)session.getAttribute("colorPortal");
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");	
	aca.util.Fecha fecha		= new aca.util.Fecha();	
	
	String fechaHoy 			= fecha.getFecha("1");
	String fechaTemp 			= "";
	String strMes			= fecha.getMes(fechaHoy);
	String codigoTemp		= "X";
	String nombreAlumno		= "";
	
	ArrayList fechas 		= fecha.getSemanaActual();	
	int 	mes				= Integer.parseInt(strMes);
	int 	dia 			= Integer.parseInt(fecha.getDia(fechaHoy));
	int		i 				= 0;	
	
	List<Inscritos> lisAlumno					= (List<Inscritos>) request.getAttribute("lisInscritos");
	HashMap<String,CatFacultad> mapaFacultades	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
%>
<body>
<div class="container-fluid">
	<h3>Birthdays
		<small class="text-muted fs-6">( From 
		<%=fecha.getDia((String)fechas.get(0))%> of <%=fecha.getMesNombre((String)fechas.get(0))%>&nbsp;
		<spring:message code="aca.Al"/> <%=fecha.getDia((String)fechas.get(6))%> of <%=fecha.getMesNombre((String)fechas.get(6))%> )
		</small>	 
	</h3>
<%	
	for (int j=0;j<7;j++){
		fechaTemp	= (String)fechas.get(j);
		String sDia = fecha.getDia(fechaTemp);
		int row = 0;
%>
	<div class="alert alert-info mt-1 mb-2"><h4><spring:message code="portal.alumno.cumple.Dia"/>:&nbsp;<%=fecha.getDia(fechaTemp)%> <spring:message code="portal.alumno.cumple.De"/> <%=fecha.getMesNombre(fechaTemp)%></h4></div>
	<div class="row row-cols-1 row-cols-md-2 ml-1">	
<%
		for (Inscritos alumno : lisAlumno){
			
			if ( !alumno.getCodigoPersonal().equals(codigoTemp) && alumno.getfNacimiento().substring(0,5).equals(fechaTemp.substring(0,5))){
				codigoTemp = alumno.getCodigoPersonal();
				nombreAlumno = alumno.getNombre();
				if (nombreAlumno.length()>14) nombreAlumno = nombreAlumno.substring(0,13);
				row++;
				
				int edadAlumno = aca.util.Fecha.edad(alumno.getfNacimiento(), fechaTemp);
				String carreraNombre 	= "";
				String facultadNombre 	= "";
				if (mapaCarreras.containsKey(alumno.getCarreraId())){
					carreraNombre 	= mapaCarreras.get(alumno.getCarreraId()).getNombreCarrera();
					facultadNombre  = mapaCarreras.get(alumno.getCarreraId()).getFacultadId();
					if (mapaFacultades.containsKey(facultadNombre)){
						facultadNombre =  mapaFacultades.get(facultadNombre).getNombreCorto();
					}
				}				
%>  
			<div class="card border-dark bg-light mb-2 mr-1" style="max-width: 15rem;">
				<div class="card-header"><span data-bs-toggle="tooltip" data-bs-placement="right" title="<%=edadAlumno%>"><i class="fas fa-birthday-cake"></i> <%=alumno.getNombre()%></span></div>
			  	<div class="card-body ">
			    	<img src="../../foto?Codigo=<%=alumno.getCodigoPersonal()%>" class="rounded" width="150px">
			  	</div>
			  	<div class="card-footer">
			    	<span data-bs-toggle="tooltip" data-bs-placement="right" title="<%=carreraNombre%>"><i class="fas fa-book-reader"></i> <%=facultadNombre%></span>
			  	</div>
			</div>					
<%			}%>		
<%		}%>
	</div>
<%	}%>
</div>
</body>
<script type="text/javascript">	
	$(function () {
  		$('[data-bs-toggle="tooltip"]').tooltip();  		
	});
</script>
