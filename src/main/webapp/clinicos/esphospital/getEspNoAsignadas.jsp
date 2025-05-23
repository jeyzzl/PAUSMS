<%@page contentType="text/html; charset=iso-8859-1" pageEncoding="iso-8859-1"%>
<%@ page import= "java.util.List"%>
<%@ page import= "aca.rotaciones.spring.RotEspecialidad"%>
<%
	String hospitalId = request.getParameter("hospitalId")==null?"0":request.getParameter("hospitalId");

	List<RotEspecialidad> lisEspecialidades 	= (List<RotEspecialidad>) request.getAttribute("lisEspecialidades");	
	for(RotEspecialidad esp : lisEspecialidades){	
	%>
		<tr>
			<td>
				<i title="asignar especialidad" class="icon-circle-arrow-left"></i> <%=esp.getEspecialidadNombre() %> 
				<input type="hidden" value="<%=esp.getEspecialidadId()%>" />
			</td>
		</tr>
	<%		
	}
%>