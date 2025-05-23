<%@ include file= "../../con_enoc.jsp" %>
<%@ include file="../../idioma.jsp"%>

	<table class="goback">
	<tr>
		<td><a href="dato_dep.jsp">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
	</tr>
	</table>
<%
	String codigoEmpleado		= (String) session.getAttribute("codigoEmpleado");
	String empleadoId 			= (String) session.getAttribute("empleadoId");
	String dependienteId 		= (String) session.getAttribute("dependienteId");
	String nombreDep 			= aca.emp.Dependiente.getNombre(conEnoc, empleadoId, dependienteId);
	boolean borrar 				= false;
	
	String dirFoto 		= application.getRealPath("/WEB-INF/fotos/"+codigoEmpleado+"-"+dependienteId+".jpg");	
	java.io.File foto 	= new java.io.File(dirFoto);
	int i=0;
	if (foto.exists()){
		
		// Ciclo para corregir error en sistemas opreativos windows(No es necesario si el servidor de aplicaciones está en linux)
		while(!borrar && i<50000){
			if(foto.delete()){
				borrar = true;				
			}
			i++;
		}
	}
	if (borrar){
		out.print("<div class='alert alert-success'>Borrado...<a class='btn btn-primary' href='dato_dep.jsp'>Regresar</a></div>");
		//response.sendRedirect("dato_dep.jsp");
	}else{
		out.println("<font color=black> No se puedo elimiar la foto de dependiente:["+codigoEmpleado+"-"+dependienteId+"] - "+nombreDep+"</font><br>");
		out.println("<font color=black> ESPERE 5 MINUTOS O SUSTITUYA LA IMAGEN</font>");
	}
%>
<%@ include file="../../cierra_enoc.jsp" %>