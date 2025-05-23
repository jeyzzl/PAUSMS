<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.catalogo.spring.CatFacultad" %>
<%@ page import="aca.catalogo.spring.CatCarrera" %>
<%@ page import="aca.mentores.spring.MentCarrera" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoEmpleado 	= (String) session.getAttribute("codigoEmpleado");
	String empleadoNombre	= (String) request.getAttribute("empleadoNombre");
	String periodoId		= (String) request.getAttribute("periodoId");
	String carreraId		= (String) request.getAttribute("carreraId");	
	
	List<MentCarrera> lisMentCarrera 				= (List<MentCarrera>) request.getAttribute("lisMentCarrera");
	HashMap<String,CatFacultad> mapaFacultades 		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaMaestros				= (HashMap<String,String>) request.getAttribute("mapaMaestros");
%>

<div class="container-fluid">
	<h2>Mentor Degree<small class="text-muted fs-4">( <%=codigoEmpleado%> - <%=empleadoNombre%>)</small></h2>	
	<div class="alert alert-info">
		<a href="mentor?carrera=<%=carreraId %>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
  	<tr>
		<th>#</th>
    	<th>School</th>
    	<th>Degree</th>
    	<th>Mentor</th>
	</tr>
	</thead>
<%
	int cont = 0;
	for(MentCarrera mentCarrera : lisMentCarrera){
		cont++;
		String facultadId		= "0";
		String facultadNombre 	= "-";
		String carreraNombre 	= "-";
		if (mapaCarreras.containsKey(mentCarrera.getCarreraId())){
			carreraNombre 	= mapaCarreras.get(mentCarrera.getCarreraId()).getNombreCarrera();
			facultadId		= mapaCarreras.get(mentCarrera.getCarreraId()).getFacultadId(); 
			if (mapaFacultades.containsKey(facultadId)){
				facultadNombre  = mapaFacultades.get(facultadId).getNombreFacultad();
			}			
		}
		String mentorNombre 	= "-";
		if (mapaMaestros.containsKey(mentCarrera.getMentorId())){
			mentorNombre 		= mapaMaestros.get(mentCarrera.getMentorId());
		}
%>
				<tr> 
			<td><%=cont %></td>
		    <td><%=facultadNombre%></td>
		    <td><%=carreraNombre%></td>
		    <td><%=mentorNombre%></td>
		</tr>
<%	
	}
%>
	</table>
</div>