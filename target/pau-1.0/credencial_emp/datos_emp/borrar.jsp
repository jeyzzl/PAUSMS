<%@ include file= "../../con_enoc.jsp" %>
	<table class="goback">
	<tr>
		<td><a href="dato_emp.jsp">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
	</tr>
	</table>
<%
	String codigoEmpleado		= (String) session.getAttribute("codigoEmpleado");
	boolean borrar 				= false;
	
	String dirFoto 		= application.getRealPath("/WEB-INF/fotos/"+codigoEmpleado+".jpg");	
	java.io.File foto 	= new java.io.File(dirFoto);
	int i=0;
	if (foto.exists()){
		
		// Ciclo para corregir error en sistema oprrativos windows(No es necesario si el servidor de aplicaciones está en linux)
		while(!borrar && i<30000){
			if(foto.delete()){
				borrar = true;				
			}
			i++;
		}		
	}
	if (borrar){
		//response.sendRedirect("dato_emp.jsp");
		out.print("<div class='alert alert-success'>Borrado...<a class='btn btn-primary' href='datos_emp.jsp'>Regresar</a></div>");
	}else{
		out.println("<font color=black> No se puedo elimiar la foto del empleado:["+codigoEmpleado+"] - "+aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, codigoEmpleado, "NOMBRE")+"</font><br>");
		out.println("<font color=black> ESPERE 5 MINUTOS O SUSTITUYA LA IMAGEN</font>");
	}
%>
<%@ include file="../../cierra_enoc.jsp" %>