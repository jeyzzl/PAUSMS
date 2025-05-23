<%@page contentType="text/html; charset=iso-8859-1" pageEncoding="iso-8859-1"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.rotaciones.spring.RotHospitalEspecialidad"%>
<%@ page import= "aca.rotaciones.spring.RotEspecialidad"%>

<%
	String hospitalId = request.getParameter("hospitalId");
	List<RotHospitalEspecialidad> lisEspecialidades 	= (List<RotHospitalEspecialidad>) request.getAttribute("lisEspecialidades");
	HashMap<String,RotEspecialidad> mapaEspecialidades 	= (HashMap<String,RotEspecialidad>) request.getAttribute("mapaEspecialidades");
	
	for(RotHospitalEspecialidad hospEsp : lisEspecialidades){
		String especialidadNombre = "-";
		if (mapaEspecialidades.containsKey(hospEsp.getEspecialidadId())){
			especialidadNombre = mapaEspecialidades.get(hospEsp.getEspecialidadId()).getEspecialidadNombre();
		}
	%>
		<tr>
			<td <%if(hospEsp.getEstado().equals("I"))out.print("class=inactive"); %>>
				<i title="editar especificaciones" class="fas fa-edit"></i>
				<span class="descripcion"><%=especialidadNombre%></span>
				<i title="desasignar especialidad" class="icon-circle-arrow-right"></i>
				<input type="hidden" value="<%=hospEsp.getEspecialidadId()%>" />
				<div class="alert alert-info">
					<b>Contacto Principal:</b> <span class="cPrincipal" ><%=hospEsp.getContactoPrincipal()==null?"":hospEsp.getContactoPrincipal() %></span><br>
					<b>Puesto:</b> <span class="pPrincipal" ><%=hospEsp.getPuestoPrincipal()==null?"":hospEsp.getPuestoPrincipal() %></span><br>
					<b>Contacto Secundario:</b> <span class="cSecundario" ><%=hospEsp.getContactoSecundario()==null?"":hospEsp.getContactoSecundario() %></span><br>
					<b>Puesto:</b> <span class="pSecundario" ><%=hospEsp.getPuestoSecundario()==null?"":hospEsp.getPuestoSecundario() %></span><br>
					<b><spring:message code="aca.Estado"/>:</b> <%=hospEsp.getEstado().equals("A")?"Activo":"Inactivo" %>
					<input type="hidden" value="<%=hospEsp.getEstado() %>" class="estado">
					<br>
				</div>
			</td>
		</tr>
	<%		
	}
%>