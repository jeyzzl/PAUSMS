<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.admision.spring.AdmRequisito"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<%
	String facultadId			= (String)request.getAttribute("facultadId");
	String facultadNombre		= (String)request.getAttribute("facultadNombre");

	List<CatCarrera> lisCarreras					= (List<CatCarrera>)request.getAttribute("lisCarreras");
	HashMap<String,String> mapaCoordinadores 		= (HashMap<String,String>) request.getAttribute("mapaCoordinadores");
	HashMap<String,String> mapaPlanes 				= (HashMap<String,String>) request.getAttribute("mapaPlanes");
	HashMap<String,String> mapaPlanesAdmision 		= (HashMap<String,String>) request.getAttribute("mapaPlanesAdmision");
	HashMap<String,String> mapaRequisitos 			= (HashMap<String,String>) request.getAttribute("mapaRequisitos");
%>
<body>
<div class="container-fluid">
	<h2><spring:message code="aca.Carreras"/> <small class="text-muted fs-4"> ( <%=facultadNombre%> )</small></h2>
	<div class="alert alert-info">
		<a href="facultad" class="btn btn-primary"><spring:message code='aca.Regresar'/></a>&nbsp;&nbsp;
		<strong><spring:message code='aca.SeleccioneUnaCarrera'/>&nbsp;!</strong>
	</div>
  	<table id="table" class="table table-sm table-bordered table-striped">
   	<thead>
	  	<tr class="table-info">
	    	<th><h3><spring:message code="aca.Numero"/></h3></th>
	    	<th><h3><spring:message code="aca.Clave"/></h3></th>
	    	<th><h3><spring:message code="aca.Nombre"/></h3></th>
	    	<th><h3><spring:message code="aca.Coordinador"/></h3></th>
	    	<th><h3><spring:message code='aca.NumeroPlanes'/></h3></th>
	    	<th><h3><spring:message code='aca.NumeroAdmision'/></h3></th>
	    	<th><h3>No.Requirements</h3></th>
	    	<th><h3><spring:message code='aca.Copiar'/></h3></th>
	  	</tr>
	</thead>
	<tbody>
<%	
	int row = 0;
	for(CatCarrera carrera: lisCarreras){
		row++;
		String totalRequisitos = "0";
		if (mapaRequisitos.containsKey(carrera.getCarreraId())){
			totalRequisitos = mapaRequisitos.get(carrera.getCarreraId());
		}
		
		String totPlanes	= "0";
		if (mapaPlanes.containsKey(carrera.getCarreraId())){
			totPlanes = mapaPlanes.get(carrera.getCarreraId());
		}
		
		String totPlanesAdmision	= "0";
		if (mapaPlanesAdmision.containsKey(carrera.getCarreraId())){
			totPlanesAdmision = mapaPlanesAdmision.get(carrera.getCarreraId());
		}
		
		String coordinador = "-";
		if (mapaCoordinadores.containsKey(carrera.getCodigoPersonal())){
			coordinador = mapaCoordinadores.get(carrera.getCodigoPersonal());
		}
%>
	  	<tr>
	    	<td align="center"><strong><%=row%></strong></td>
	    	<td align="center"><strong><%=carrera.getCarreraId() %></strong></td>
	    	<td>
				<a href="documentos?Facultad=<%=facultadId%>&Carrera=<%=carrera.getCarreraId()%>">
					<strong><%=carrera.getNombreCarrera() %></strong>
				</a>  
			</td>
	    	<td><strong><%=coordinador%></strong></td>
	    	<td align="center"><%=totPlanes %></td>
	    	<td align="center"><%=totPlanesAdmision%></td>
	    	<td align="center"><%=totalRequisitos%></td>
	    	<td align="center">
<% 		if(totalRequisitos.equals("0")){%>
				<img width="20" onmouseover="this.src='../../imagenes/duplicar-over.png'" onmouseout="this.src='../../imagenes/duplicar.png'" src="../../imagenes/duplicar.png" alt="Copiar" onclick="window.location.href='escogerCarreras?CarreraDestino=<%=carrera.getCarreraId() %>';" style="cursor:pointer;">
 <% 	}else{
    		out.print("&nbsp;");
    	}
%>
    		</td>
	  	</tr>
<%	} %>
	</tbody>
	</table>		
</body>
</html>