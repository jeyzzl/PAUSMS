<%@ include file="../../idioma.jsp"%>

<%
	String codigoEmpleado   = (String) session.getAttribute("codigoPersonal");	
	String nomArchivo		= request.getParameter("Archivo")==null?"-":request.getParameter("Archivo");
	
%>
<table class="goback">
	<tr>
		<td><a href="documentos.jsp"><i
				class="icon-arrow-left icon-white"></i>
			<spring:message code="aca.Regresar" /></a></td>
	</tr>
</table>
<%
	
	boolean borrar 			= false;
	System.out.println("nombre archivo" + nomArchivo );
	String dirFoto = "";
	if(!nomArchivo.contains(".")){
		dirFoto = application.getRealPath("/WEB-INF/portafolio/"+nomArchivo+".jpg");
	}else{
		dirFoto = application.getRealPath("/WEB-INF/portafolio/"+nomArchivo);
	}
	
	
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
		response.sendRedirect("documentos.jsp");
	}else{
		out.println("<font color=black> No se puedo elimiar la foto </font>");
	}
%>
