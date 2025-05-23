<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%  
	String opcion 	= request.getParameter("Opcion")==null?"0":request.getParameter("Opcion");
	String enviar	= "menu";
	
	if (opcion.equals("1")){
		enviar="rep_alum_ment_facu";
	}else if (opcion.equals("2")){
		enviar="rep_entrevista";
	}else if (opcion.equals("3")){
		enviar="rep_alum_sin_mentor";
	}
	
	List<CatFacultad> lisFacultades 		= (List<CatFacultad>) request.getAttribute("lisFacultades");
	HashMap<String, String> mapaDirectores 	= (HashMap<String, String>) request.getAttribute("mapaDirectores");	
%>
<div class="container-fluid">	
	<h2>Facultades</h2>
	<div class="alert alert-info">
		<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
	</div>
 	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
			<tr> 
				<th><spring:message code="aca.Numero"/></th>
				<th><spring:message code="aca.Facultad"/></th>
				<th><spring:message code="aca.Director"/></th>
			</tr>
		</thead>
		<%for (CatFacultad fac : lisFacultades){
			String nombreDirector = "-";
			if(mapaDirectores.containsKey(fac.getCodigoPersonal())){
				nombreDirector = mapaDirectores.get(fac.getCodigoPersonal());
			}
		%>
			<tr>
				<td><%=fac.getFacultadId()%></td>
			    <td><a href="<%=enviar%>?facultad=<%=fac.getFacultadId()%>"><font size="2"><%=fac.getTitulo()%> de <%=fac.getNombreFacultad()%></a></td>
			    <td><font size="1"><%=nombreDirector%></font></td>
			  </tr>
		<%}%> 
	</table>			
</div>