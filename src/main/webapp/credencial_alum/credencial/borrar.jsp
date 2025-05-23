<%@ include file= "../../con_enoc.jsp" %>
<jsp:useBean id="CredPersonal" scope="page" class="aca.cred.CredPersonal"/>
<jsp:useBean id="CredPersonalU" scope="page" class="aca.cred.CredPersonalUtil"/>
<%
	String matricula	= (String) session.getAttribute("codigoAlumno");
	String nombre		= (String) request.getParameter("Nombre");
	String eventoId		= (String) request.getParameter("EventoId");
	
	System.out.println("matricula "+matricula+", nombre "+nombre+", eventoid "+eventoId);
%>
<table class="goback">
	<tr>
		<td><a href="generaCredencial?Matricula=<%=matricula %>&Nombre=<%=nombre %>&EventoId=<%=eventoId %>"><i class="icon-arrow-left icon-white"></i><spring:message code="aca.Regresar"/></a></td>
	</tr>
</table>
<%
	
	boolean borrar 			= false;
	
	String dirFoto = application.getRealPath("/WEB-INF/fotos/"+matricula+".jpg");	
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
		out.print("<div class='alert alert-danger'>Borrado...<a class='btn btn-primary' href='generaCredencial?Matricula="+matricula+"&Nombre="+nombre+"&EventoId="+eventoId+"'>Regresar</a></div>");
		//response.sendRedirect("generaCredencial?Matricula="+matricula+"&Nombre="+nombre+"&EventoId="+eventoId);
	}else{
		out.println("<font color=black> No se puedo elimiar la foto del alumno:["+matricula+"] - "+CredPersonalU.getNombre(conEnoc, matricula)+"</font>");
	}
%>
<%@ include file="../../cierra_enoc.jsp" %>