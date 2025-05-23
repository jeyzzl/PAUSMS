<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<%@ page import= "aca.catalogo.spring.CatPais"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.vista.spring.Estadistica"%>
<%@ page import= "aca.leg.spring.LegExtranjero"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String usuario 			= (String) session.getAttribute("codigoPersonal");

	String codigoPersonal 	= "";	
	String carreraAnt		= "";	
	String bgColor			= "";
	String estilo 			= "";
	int cont				= 0;
	int cambios				= 0;
	
	String modalidades 	= (String) session.getAttribute("modalidadesReportes");
	List<Estadistica> lisExtranjeros  				= (List<Estadistica>) request.getAttribute("lisExtranjeros");
	HashMap<String,CatModalidad> mapaModalidades 	= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidades");
	HashMap<String,CatPais> mapaPaises 				= (HashMap<String,CatPais>) request.getAttribute("mapaPaises");	
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,LegExtranjero> mapaExtranjeros	= (HashMap<String,LegExtranjero>) request.getAttribute("mapaExtranjeros");
	HashMap<String,Estadistica> mapaCarreraAnterior	= (HashMap<String,Estadistica>) request.getAttribute("mapaCarreraAnterior");	
%>

<div class="container-fluid">
	<h2>Extranjeros con cambios de Carrera</h2>

	<div class="alert alert-info">
		<a href="elegir" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>
	<table  class="table table-bordered table-striped table-fontsmall">
	<tr> 
  		<th align="center"><spring:message code="aca.Numero"/></th>
		<th align="center"><spring:message code="aca.Matricula"/></th>
		<th align="center"><spring:message code="aca.Nombre"/></th>  
		<th align="center">Nacionalidad</th>
		<th align="center">Sexo</th>
		<th align="center"><spring:message code="aca.Modalidad"/></th>
		<th align="center">Insc. Ant.</th>
		<th align="center">Carrera FM3</th>
		<th align="center">Insc. Act.</th>
		<th align="center">Carrera Actual.</th>  
	</tr>
<%		
	if(lisExtranjeros.size() != 0){		
		for(int i = 0; i < lisExtranjeros.size(); i++){
			Estadistica est = (Estadistica) lisExtranjeros.get(i);
			if(!est.getCodigoPersonal().equals(codigoPersonal)){
				codigoPersonal = est.getCodigoPersonal();
				
				String paisNombre = "-";
				if (mapaPaises.containsKey(est.getNacionalidad())){
					paisNombre = mapaPaises.get(est.getNacionalidad()).getNombrePais();
				}
				
				String modalidadNombre = "-";
				if (mapaModalidades.containsKey(est.getModalidadId())){
					modalidadNombre = mapaModalidades.get(est.getModalidadId()).getNombreModalidad();
				}
				
				String carreraNombre = "-";
				if (mapaCarreras.containsKey(est.getCarreraId())){
					carreraNombre = mapaCarreras.get(est.getCarreraId()).getNombreCarrera();
				}
				
				String carreraAnterior 		= "0";
				String fechaAnt				= "0"; 
				String planAnterior			= "-";
				if (mapaCarreraAnterior.containsKey(est.getCodigoPersonal())){
					carreraAnterior 	= mapaCarreraAnterior.get(est.getCodigoPersonal()).getCarreraId();
					fechaAnt			= mapaCarreraAnterior.get(est.getCodigoPersonal()).getFechaInscripcion();
					planAnterior		= mapaCarreraAnterior.get(est.getCodigoPersonal()).getPlanId();
				}			
				
				String carreraAnteriorNombre = "";
				if (mapaCarreras.containsKey(carreraAnterior)){
					carreraAnteriorNombre = mapaCarreras.get(carreraAnterior).getNombreCarrera();
				}
				
				// Define el color del fondo del renglon y cuenta la cantidad de cambios de carrera 
				if ( carreraAnterior.equals(est.getCarreraId()) ){
					bgColor 	= "bgcolor='#eeeeee'";
					estilo 		= "style=color:black;";
				}else{ 
					bgColor 	= "bgcolor='#cccccc'";
					estilo 		= "style=color:red;";
					cambios++;
				}				
%>
	<tr > 
	  <td <%=estilo%> align="center"><%=cont+1%></td>
	  <td <%=estilo%> align="center"><%= est.getCodigoPersonal()%></td>
	  <td <%=estilo%>><%= est.getNombre()%></td>
	  <td <%=estilo%>><%= paisNombre%></td>
	  <td <%=estilo%> align="left"><%=est.getSexo().equals("M")?"Hombre":"Mujer"%></td>
	  <td <%=estilo%>><%=modalidadNombre %></td>
	  <td <%=estilo%>><%= fechaAnt %></td>
	  <td <%=estilo%>><%=planAnterior%> - <%= carreraAnteriorNombre%></td>
	  <td <%=estilo%>><%=est.getFechaInscripcion()%></td>
	  <td <%=estilo%>><%=est.getPlanId()%> - <%= carreraNombre%></td>
	</tr>
<%
				cont++;
			}
		}
%>
	</table>
	<div class="alert alert-info">  
    	Total de Extranjeros: <%=cont%> &nbsp; &nbsp; Cambios de Carrera: <%=cambios%>  
	</div>

<%	
	}else{ 
%>
	<br>
	<br>
	<center>
  		<font color="#000099" size="1"><strong>No hay cambios de carrera</strong></font> 
	</center>
	
<%	}%>

</div>