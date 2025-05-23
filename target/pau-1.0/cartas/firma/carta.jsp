
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoPersonal	= (String) session.getAttribute("codigoAlumno");
	String planId			= (String) request.getAttribute("planId");
	String carreraId		= (String) request.getAttribute("carreraId");	
	String nivelId			= (String) request.getAttribute("nivelId");
	String modalidadId		= (String) request.getAttribute("modalidadId");	
	String nombreNivel		= (String) request.getAttribute("nombreNivel");
	boolean esInscrito		= (boolean) request.getAttribute("esInscrito");
	boolean datosCapturados = (boolean) request.getAttribute("datosCapturados");
	
%>
<body>
<div class="container-fluid">
	<table style="width:95%" align="center" class="table">
  	<tr>
		<th class="oculto">Impresión de constancias..</th>
 	 </tr>
<%	if ( datosCapturados){ 
		if(nivelId.equals("5")){ 
			response.sendRedirect("view2");  	
		}else{ 
			response.sendRedirect("view");
		}	
	}else{ 
%>
 	<tr>
		<td>¡ No están capturadas las fechas de vacaciones para <%=nombreNivel %> !</td>
 	</tr>	
<%	} %>  
	</table>
</div>
</body>



