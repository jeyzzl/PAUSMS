<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.mentores.spring.MentContacto"%>
<%@ page import="aca.mentores.spring.MentMotivo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String periodoId	 	= request.getParameter("PeriodoId");
	String mentorId			= request.getParameter("MentorId");	
	String sComentario		= "";
	
	String mentorNombre		= (String) request.getAttribute("mentorNombre");
	List<MentContacto> lisEntrevistas 			= (List<MentContacto>) request.getAttribute("lisEntrevistas"); 	
	HashMap<String,MentMotivo> mapaMotivos 		= (HashMap<String,MentMotivo>) request.getAttribute("mapaMotivos");
	HashMap<String,String> mapaAlumnos			= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
%>
<div class="container-fluid">
    <h2>Interviews conducted <small class="text-muted fs-4">(<%= mentorId %>: <%=mentorNombre%>)</small></h2>
	<div class="alert alert-info"">
		<a href="mentores?periodo=<%= periodoId %>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	
  		<tr> 
    		<th width="3%">#</th>
    		<th width="7%">Interview</th>
    		<th width="7%"><spring:message code="aca.Matricula"/></th>
    		<th width="20%"><spring:message code="aca.Nombre"/></th>
    		<th width="10%">Reason</th>
    		<th width="6%">Visit</th>
    		<th width="40%"><spring:message code="aca.Comentario"/></th>
    		<th width="9%"><spring:message code="aca.Fecha"/></th>
  		</tr>
	</thead>
<%	
	for(int i=0; i<lisEntrevistas.size(); i++){
		MentContacto contacto = (MentContacto) lisEntrevistas.get(i);
		
		String motivoNombre	= "Otro";
		if (mapaMotivos.containsKey(contacto.getMotivoId())){
			motivoNombre = mapaMotivos.get(contacto.getMotivoId()).getMotivoNombre();
		}
		
		String alumnoNombre	= "-";		
		if (mapaAlumnos.containsKey(contacto.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(contacto.getCodigoPersonal());
		}
%>
		<tr> 
		    <td><%=i+1%></td>
		    <td><div align="center"><%=contacto.getContactoId() %></div></td>
		    <td><div align="center"><%=contacto.getCodigoPersonal() %></div></td>
		    <td><%=alumnoNombre%></td>
		    <td><%=motivoNombre%></td>
		    <td><div align="center"><%=contacto.getTipo() %></div></td>
<%			if (contacto.getComentario() == null) {		%>
			<td><div align="left"><%=sComentario%></div></td>
<%  		} else {%>
			    <td><div align="left"><%=contacto.getComentario() %></div></td>
<%			}%>
			    <td><div align="center"><%=contacto.getFechaContacto() %></div></td>
			  </tr>
<%			
	} 	
%>

</table>