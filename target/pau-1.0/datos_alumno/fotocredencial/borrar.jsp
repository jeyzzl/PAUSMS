<%@ include file= "../../con_enoc.jsp" %>
<table class="goback">
	<tr>
		<td><a href="datos">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
	</tr>
</table>
<%
	String codigoAlumno		= (String) session.getAttribute("codigoAlumno");
	boolean borrar 			= false;
	
	String dirFoto = application.getRealPath("/WEB-INF/fotos/"+codigoAlumno+".jpg");	
	java.io.File foto = new java.io.File(dirFoto);	
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
		out.print("<div class='alert alert-success'><b>Borrado...<a class='btn btn-primary' href='datos'>Regresar</a></div>");
		//response.sendRedirect("datos");
	}else{
		out.println("<font color=black> No se puedo elimiar la foto del alumno:["+codigoAlumno+"] - "+aca.alumno.AlumUtil.getNombreAlumno(conEnoc, codigoAlumno, "NOMBRE")+"</font>");
	}
%>
<%@ include file="../../cierra_enoc.jsp" %>