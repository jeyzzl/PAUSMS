<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat" %>
<%@page import="java.util.HashMap"%>

<%@page import="aca.alumno.spring.AlumEstado"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal");
	Acceso acceso			= (Acceso)request.getAttribute("acceso");
	
	List<AlumEstado> lisBajas						= (List<AlumEstado>)request.getAttribute("lisBajas");	
	
	HashMap<String, String> mapaAlumnosBajas 		= (HashMap<String, String>)request.getAttribute("mapaAlumnosBajas");
	HashMap<String, CatFacultad> mapaFacultades		= (HashMap<String, CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String, CatCarrera> mapaCarreras		= (HashMap<String, CatCarrera>)request.getAttribute("mapaCarreras");
%>
<div class="container-fluid">
	<h1>Reporte histórico de Bajas</h1>
	<div class="alert alert-info">		
		<a href="menu" class="btn btn-primary">Menu</a>
	</div>	
	<table class="table table-bordered">
	<thead class="table-info">
	<tr> 
    	<th width="2%" height="16" align="center"><spring:message code="aca.Numero"/></th>
    	<th width="5%" height="16" align="center"><spring:message code="aca.Facultad"/></th>
    	<th width="25%" height="16" align="center"><spring:message code="aca.Carrera"/></th>
    	<th width="5%" height="16" align="center"><spring:message code="aca.Carga"/></th>
    	<th width="5%" height="16" align="center"><spring:message code="aca.Bloque"/></th>
    	<th width="5%" height="16" align="center"><spring:message code="aca.Plan"/></th>
    	<th width="5%" height="16" align="center"><spring:message code="aca.Matricula"/></th>
    	<th width="35%" height="16" align="center"><spring:message code="aca.Nombre"/></th>    	
    	<th width="10%" height="16" align="center"><spring:message code="aca.Estado"/></th>
  	</tr>
  	</thead> 
  	<tbody>
<%	 
	int row = 0;
	for(AlumEstado alumEstado : lisBajas){	
		
		if (acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") || acceso.getAccesos().indexOf(alumEstado.getCarreraId()) != -1){
			row++;
			
			String facultadNombre = "-";
			if(mapaFacultades.containsKey(alumEstado.getFacultadId())){
				facultadNombre = mapaFacultades.get(alumEstado.getFacultadId()).getNombreCorto();
			}
			
			String carreraNombre = "-";
			if(mapaCarreras.containsKey(alumEstado.getCarreraId())){
				carreraNombre = mapaCarreras.get(alumEstado.getCarreraId()).getNombreCarrera() ;
			}
			
			String nombreAlumno = "";
			if (mapaAlumnosBajas.containsKey( alumEstado.getCodigoPersonal() ) ){
				nombreAlumno = mapaAlumnosBajas.get( alumEstado.getCodigoPersonal() );
			}			
%>
	<tr> 
    	<td width="2%"><%=row%></td>
    	<td align="center"><%=facultadNombre%></td>
    	<td align="center"><%=carreraNombre%></td>    	
    	<td align="center"><%=alumEstado.getCargaId()%></td>
    	<td align="center"><%=alumEstado.getBloqueId()%></td>
    	<td align="center"><%=alumEstado.getPlanId()%></td>
    	<td><%=alumEstado.getCodigoPersonal()%></td>
    	<td><%=nombreAlumno%></td>    	
    	<td align="center">Baja</td>
  	</tr>
<%						
		}
		
	} // fin del while		
%>	
	<tr><td colspan="8">&nbsp;</td></tr>
	</tbody>  	
	</table>
</div>
<%
	lisBajas = null;
%>