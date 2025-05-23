<%@ include file= "../../con_enoc.jsp" %>
<%
	String codigoId		= (String) session.getAttribute("CodigoId");
	boolean borrar 		= false;
	
	String dirFoto = application.getRealPath("/WEB-INF/fotos/"+codigoId+".jpg");
	java.io.File foto = new java.io.File(dirFoto);
	if (foto.exists()){
		if (foto.delete()){
			borrar = true;
		}
	} 
	if (borrar){
		response.sendRedirect("datos");
	}else{
		out.println("<font color=black> No se puedo elimiar la foto del alumno:["+codigoId+"] - "+aca.musica.MusiAlumno.getNombre(conEnoc, codigoId, "NOMBRE")+"</font>");
	}
%>
<%@ include file="../../cierra_enoc.jsp" %>