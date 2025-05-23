<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.financiero.spring.FinPermiso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%  	
	List<FinPermiso> lisPermisos			= (List<FinPermiso>)request.getAttribute("lisPermisos");
	HashMap<String,String> mapaAlumnos		= (HashMap<String,String>)request.getAttribute("mapaAlumnos"); 
%>
<div class="container-fluid">
	<h2>Alumnos con permisos<small class="text-muted fs-5">(Nota:<i>El campo permiso se refiere a la persona que autorizo el permiso</i>)</small></h2>
	<hr>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
  	<tr align="center"> 
    	<th width="4%" align="center"><spring:message code="aca.Numero"/></th>
    	<th width="8%" align="center"><spring:message code="aca.Matricula"/></th>
    	<th width="32%" align="center"><spring:message code="aca.Nombre"/></th>    
    	<th width="10%" align="center">F. Inicio</th>
   		<th width="10%" align="center">F. Limite</th>
   		<th width="8%" align="center">Permiso</th>    
	</tr>
	</thead>
<% 
	for (int i=0;i<lisPermisos.size(); i++){ 
		FinPermiso permiso = (FinPermiso) lisPermisos.get(i);
		
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(permiso.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(permiso.getCodigoPersonal());
		}			
%>
	<tr class="tr2" align="center">
    	<td align="center"><%=i+1%></td>
    	<td align="center"><%=permiso.getCodigoPersonal()%></td>
    	<td align="left"><%=alumnoNombre%></td>
    	<td align="center"><%=permiso.getFInicio()%></td>
    	<td align="center"><%=permiso.getFLimite()%></td>
    	<td align="center"><%=permiso.getUsuario()%></td>   
  	</tr>
<% } %>  
	</table>
</div>