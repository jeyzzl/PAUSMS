<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.mentores.spring.MentMotivo"%>
<%@page import="aca.mentores.spring.MentContacto"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String mentorId			= (String) session.getAttribute("codigoPersonal");
	String periodoId		= request.getParameter("PeriodoId")==null?session.getAttribute("ciclo").toString():request.getParameter("PeriodoId");
	String mentorNombre		= (String) request.getAttribute("mentorNombre");
	String 	mes				= "0";
	String sBgcolor			= "";
	String sColor			= "#eeeeee";
	
	// Lista de entrevistas
	List<MentContacto> lisEntrevistas			= (List<MentContacto>) request.getAttribute("lisEntrevistas");
	HashMap<String,MentMotivo> mapaMotivos	 	= (HashMap<String,MentMotivo>)request.getAttribute("mapaMotivos");
	HashMap<String,String> mapaAlumnos	 		= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
%>
<div class="container-fluid">
	<h2>Entrevistas del mentor<small class="text-muted">( Mentor:<%=mentorNombre%> - Periodo:<%=periodoId%> )</small></h2>
	<div class="alert alert-info">
		<a href="portal?PeriodoId=<%=periodoId%>" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i><spring:message code='aca.Regresar'/></a>
	</div>
	<table style="width:1100" align="center" class="table table-fullcondensed">		  	
<%
	for(int i=0; i<lisEntrevistas.size(); i++){
		MentContacto contacto = (MentContacto)lisEntrevistas.get(i);
		
		if (!contacto.getFechaContacto().substring(3,5).equals(mes)){ 
			mes = contacto.getFechaContacto().substring(3,5);		
%>  
  		<tr class="alert alert-info">
			<td colspan="8" align="center"><h3>Mes: <%=aca.util.Fecha.getMesNombre(Integer.parseInt(mes)) %></h3></td>
  		</tr>
  		<tr> 
	    	<th width="4%">#</th>
	    	<th width="4%"><spring:message code='aca.Borrar'/></th>
	    	<th width="5%"><spring:message code="aca.Matricula"/></th>
	    	<th width="25%"><spring:message code="aca.Nombre"/></th>
	    	<th width="8%">Motivo</th>
	    	<th width="4%"><spring:message code="aca.Tipo"/></th>
	    	<th width="150%"><spring:message code="aca.Comentario"/></th>
			<th width="8%"><spring:message code="aca.Fecha"/></th>
	  	</tr>     
<%		
		}
		String motivoNombre = "Otro";
		if (mapaMotivos.containsKey(contacto.getMotivoId())){
			motivoNombre = mapaMotivos.get(contacto.getMotivoId()).getMotivoNombre(); 
		}
			
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(contacto.getCodigoPersonal())){
				alumnoNombre = mapaAlumnos.get(contacto.getCodigoPersonal()); 
			}	
		if((i%2)==0) sBgcolor = ""; else sBgcolor = sColor;
		
		String tipoContacto = "-";
		if (contacto.getTipo().equals("A")) tipoContacto = "Asamblea";
		if (contacto.getTipo().equals("V")) tipoContacto = "Visita";
		if (contacto.getTipo().equals("R")) tipoContacto = "Reunión";
		if (contacto.getTipo().equals("E")) tipoContacto = "Entrevista";
%>
		<tr <%=sBgcolor%>> 
	    	<td height="21"><%=i+1%></td>
	    	<td>		
				<a href="contacto_del?periodoId=<%=periodoId%>&contactoId=<%=contacto.getContactoId()%>&codigoAlumno=<%=contacto.getCodigoPersonal() %>&mentorId=<%=mentorId%>&Opcion=2"> 
	        	<img src="../../imagenes/borrador.gif" width="12" height="12" >
	        	</a>
        	</td>
        	<td><div align="center"><%=contacto.getCodigoPersonal() %></div></td>
	    	<td><%=alumnoNombre%></td>
	    	<td><%=motivoNombre%></td>
	    	<td><div align="center"><%=tipoContacto%></div></td>
			<td><div><%if(contacto.getComentario()==null){%>&nbsp;<%}else{%><%=contacto.getComentario() %><%}%></div></td>
			<td><div align="center"><%=contacto.getFechaContacto() %></div></td>
	  	</tr>
<%
	}
%>
	</table>
</div>