<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.vista.spring.Inscritos"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%		
	String facTemp		= "X";
	String carrTemp 	= "X";
	
	List<Inscritos> lisInscritos 					= (List<Inscritos>) request.getAttribute("lisInscritos");	
	HashMap<String,CatFacultad> mapaFacultades 		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");	
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaAutorizados	 		= (HashMap<String,String>) request.getAttribute("mapaAutorizados");	
%>
<div class="container-fluid">
	<h1>Drop Out Students</h1>
	<div class="alert alert-info">
		<a href='fecha' class='btn btn-info'><i class='icon-white icon-arrow-left'></i>Date</a>
	</div>
	<table class="table table-condensed table-stripped">  
<%				
	int row = 0;
	for (Inscritos alumno : lisInscritos){
    	row++;
    	
    	// Busca la facultad a la que pertenece la carrera
    	String facAlumno		= "000";
    	String nombreCarrera 	= "-";
    	String nombreFacultad	= "-";
    	if (mapaCarreras.containsKey(alumno.getCarreraId())){    		
    		facAlumno 		= mapaCarreras.get(alumno.getCarreraId()).getFacultadId();
    		nombreCarrera 	= mapaCarreras.get(alumno.getCarreraId()).getNombreCarrera();
    		if (mapaFacultades.containsKey(facAlumno)){
    			nombreFacultad = mapaFacultades.get(facAlumno).getNombreFacultad();
    		}
    	}
    	
    	String autorizado = "N";
    	if (mapaAutorizados.containsKey(alumno.getCodigoPersonal())){
    		autorizado = mapaAutorizados.get(alumno.getCodigoPersonal());
    	}			
		if(!facTemp.equals(facAlumno)){
			facTemp = facAlumno;
%>
		<tr>
		   	<td colspan="4" class="titulo2"><b><%=nombreFacultad%><b></td>
		 	</tr>
<%     	}//fin del if de facultades diferentes
			
		if(!carrTemp.equals( alumno.getCarreraId() )){
	   			carrTemp = alumno.getCarreraId();
%>

		<tr><td colspan="4">&nbsp;</td></tr>
		<tr> 
		   	<td colspan="4" class="titulo3"><%=nombreCarrera%></b></td>
		</tr>
		<tr> 
			<th width="5%" align="center"><spring:message code="aca.Numero"/></th>
		    <th width="10%" align="center"><b><spring:message code="aca.Matricula"/></b></th>
		    <th width="60%" align="center"><b><spring:message code="aca.Nombre"/></b></th>	
		    <th width="25%" align="center"><b>Comment</b></th>
		</tr>
<%		
		}//fin del if de carreras diferentes
%>
		<tr> 
			<td align="center"><%=row%></td>
			<td align="center"><%=alumno.getCodigoPersonal()%></td>
			<td><%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%> <%=alumno.getNombre()%></td>
			<td><%=autorizado%></td>
  		</tr>
<%		
	} // fin del while
%>
	</table>
</div>