<%
	String alumnoMovil 	= (String) request.getAttribute("alumnoMovil");	
	if (alumnoMovil.equals("S")){		
		response.sendRedirect("../alumno/resumenmov");	
	}else{
		response.sendRedirect("../alumno/resumen");
	}	
%>