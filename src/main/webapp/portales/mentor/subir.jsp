<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%			
	boolean existe		= (boolean)request.getAttribute("existe");	
	if (existe==false){
%>
	<table style="width:75%"  align="center" class="table">
  	<tr> 
    	<td><div align="center"><font size="2"><strong>El alumno no existe...!</strong></font></div></td>
  	</tr>
  	<tr> 
	    <td><div align="center"><a href="portal">Ok</a></div></td>
  	</tr>
	</table>
<%	
	}else{
		response.sendRedirect("../portalAlumno/portal");
	}
%>