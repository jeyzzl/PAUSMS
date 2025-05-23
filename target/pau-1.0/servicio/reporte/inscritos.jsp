<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.ssoc.spring.SsocInicio"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatPais"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>

<% 
	List<SsocInicio> lisInscrito				= (List<SsocInicio>) request.getAttribute("lisInscrito");
	
	HashMap<String,String> mapaAlumnos			= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
	HashMap<String,CatFacultad> mapaFacultades 	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaCarreraPlan		= (HashMap<String,String>) request.getAttribute("mapaCarreraPlan");
	HashMap<String,CatPais> mapaPaises 			= (HashMap<String,CatPais>) request.getAttribute("mapaPaises");
	HashMap<String,String> mapaNacionalidades	= (HashMap<String,String>) request.getAttribute("mapaNacionalidades");
	HashMap<String, String> mapaAvance		 	= (HashMap<String, String>)request.getAttribute("mapaAvance");
	
	String facTemp = "X";	
%>
<body>
<div class="container-fluid">
	<h3>Inscritos en Servicio Social</h3>
	<div class="alert alert-info"><a href="menu" class="btn btn-primary">Regresar</a></div>
	<table class="table table-condensed table-nohover">  
<%
	int row = 0;
	for (SsocInicio inicio : lisInscrito){
		row++;
		String carreraPlan	= "-";	
		String facultadId	= "-";
		if (mapaCarreraPlan.containsKey(inicio.getPlanId())){
			carreraPlan = mapaCarreraPlan.get(inicio.getPlanId());
			
			if (mapaCarreras.containsKey(carreraPlan)){
				facultadId = mapaCarreras.get(carreraPlan).getFacultadId(); 
			}
		}
		
		String nombreFacultad = "";
		if (mapaFacultades.containsKey(facultadId)){
			nombreFacultad = mapaFacultades.get(facultadId).getNombreFacultad();
		}
	
		String nombreAlumno = "-";
	 	if(mapaAlumnos.containsKey(inicio.getCodigoPersonal())){
	 		nombreAlumno = mapaAlumnos.get(inicio.getCodigoPersonal());
	 		
	 		
	 	}
	 	
	 	String nacionalidadAlumno = "91";
	 	if(mapaNacionalidades.containsKey(inicio.getCodigoPersonal())){
	 		nacionalidadAlumno = mapaNacionalidades.get(inicio.getCodigoPersonal());
	 	}
	 	
	 	String nacionalidad = "-";
	 	if(mapaPaises.containsKey(nacionalidadAlumno)){
	 		nacionalidad = mapaPaises.get(nacionalidadAlumno).getNombrePais();
	 	}
	 	
	 	String avance = "-";
	 	if(mapaAvance.containsKey(inicio.getCodigoPersonal()+inicio.getPlanId())){
	 		avance = mapaAvance.get(inicio.getCodigoPersonal()+inicio.getPlanId());
	 		
	 	} 
	 	 
	 	
		if (!facTemp.equals(facultadId)){
			facTemp = facultadId;
		
%>
	<tr class="alert alert-success"><td colspan="9" style="font-size:12pt;"> <%=nombreFacultad%></td></tr>
 	<tr> 
    	<th width="2%"><spring:message code="aca.Numero"/></th>
    	<th width="7%"><spring:message code="aca.Matricula"/></th>
    	<th width="32%"><spring:message code="aca.Nombre"/></th>
    	<th width="10%"> Plan </th>
    	<th width="10%"> F. Inicio </th>
    	<th width="5%">Porcentaje</th>
    	<th width="7%">Semestre</th>
     	<th width="7%">% Actual</th>
     	<th width="7%">Nacionalidad</th>
  	</tr>
<% 
		}
%>
	<tr > 
   		<td align="center"><font size="1"><%=row%></font></td>
  	  	<td align="center"><font size="1"><%=inicio.getCodigoPersonal()%></font></td>
   	 	<td align="left"><font size="1"><%=nombreAlumno%></font></td> 
  	  	<td align="center"><font size="1"><%=inicio.getPlanId()%></font></td>
    	<td align="center"><font size="1"><%=inicio.getFecha()%></font></td> 
    	<td align="center"><font size="1"><%=inicio.getPorcentaje()%>%</font></td>
    	<td align="center"><font size="1"><%=inicio.getSemestre()%></font></td>
    	<td align="center"><font size="1"><%=avance%>%</font></td>
    	<td align="center"><font size="1"><%=nacionalidad %></font></td>
<%--     <td align="center"><font size="1"><%= aca.alumno.PlanUtil.getAlumPromCreditos(conEnoc, inicio.getCodigoPersonal(), inicio.getPlanId()) %></font></td> --%>
<%--     <td align="center"><font size="1"><%= aca.catalogo.PaisUtil.getNombrePais(conEnoc, aca.alumno.AlumUtil.getPaisId(conEnoc, inicio.getCodigoPersonal())) %></font></td> --%>
  	</tr>
<% 
	}
%>
	</table>
</div>
</body>