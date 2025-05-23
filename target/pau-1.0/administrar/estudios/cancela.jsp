<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="cancelaU" scope="page" class="aca.alumno.CancelaEstudioUtil"/>
<%		
	String sBgcolor			= "";
	int row 				=-1;
		
	ArrayList<aca.alumno.CancelaEstudio> lisCancela 	= cancelaU.getListAll(conEnoc, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
%>

<div class="container-fluid">
	<h2>Student list with cancelled studies</h2>
	<div class="alert alert-info d-flex align-items-center"></div>

	<table class="table table-sm table-bordered">
	<tr align="center">	
		<th align="center"><spring:message code="aca.Numero"/></th>
		<th align="center"><spring:message code="aca.Matricula"/></th>
		<th class="text-left"><spring:message code="aca.Nombre"/></th>
		<th class="text-left"><spring:message code="aca.Plan"/></th>
		<th class="text-left"><spring:message code="aca.Comentario"/></th>	
	</tr>
<%	
	for(aca.alumno.CancelaEstudio alumno : lisCancela){
		row++;		
%>  
	<tr class="tr2">	
		<td align="center"><%=row+1 %></td>	
		<td align="center"><%=alumno.getCodigoPersonal()%></td>
		<td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, alumno.getCodigoPersonal(), "NOMBRE") %></td>
		<td align="center"><%=alumno.getPlanId()%></td>	
		<td align="left"><%=alumno.getComentario()%></td>
  	</tr>
  
<% }%>
	</table>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>