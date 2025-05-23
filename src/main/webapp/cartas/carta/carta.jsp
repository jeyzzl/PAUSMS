
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="vacacion" scope="page" class="aca.alumno.AlumVacacion" />
<jsp:useBean id="vacacionU" scope="page" class="aca.alumno.AlumVacacionLista" />

<%	
	boolean datosCapturados		= (boolean)request.getAttribute("datosCapturados");
	String nivelId				= (String) request.getAttribute("nivelId");
	String nivelNombre			= (String) request.getAttribute("nivelNombre");	
%>
<body>
<div class="container-fluid">

<%	if ( datosCapturados){
	
		if(nivelId.equals("5")){ 
			response.sendRedirect("view2"); %>
 
<%		}else{ 
			response.sendRedirect("view"); %>   
<%		}	
	}else{ %>
	<div class="alert alert-danger">¡ No están capturadas las fechas de vacaciones para <%=nivelNombre%> !</div>
<%	} %>  
</div>
</body>