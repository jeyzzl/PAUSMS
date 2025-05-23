<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.padre.spring.PadrePersonal"%>
<%@page import="aca.padre.spring.PadreAlumno"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%> 

<%@ include file= "menu.jsp" %>
<%	
	String codigoPersonal 	= (String) session.getAttribute("codigoAlumno");	
	String mensaje			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	String alumnoNombre 	= (String)request.getAttribute("alumnoNombre");
	String fechaHoy 		= aca.util.Fecha.getHoy();
	
	// Lista de hijos
	List<PadreAlumno> lisPadre  				= (List<PadreAlumno>) request.getAttribute("lisPadres");
	HashMap<String,PadrePersonal> mapaPadres	= (HashMap<String,PadrePersonal>) request.getAttribute("mapaPadres");
%>
<div class="container-fluid">
	<h4>
		<a href="resumen"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;Padres o Tutores (<small class="text-muted fs-6">Alumno:  <%=alumnoNombre%></small>)
	</h4>
	<hr>
	<table style="width:75%"  class="table">
		<tr> 
	    	<th width="10%"><spring:message code="aca.Numero"/></th>
	   		<th width="20%"><spring:message code="aca.Clave"/></th>
	    	<th width="60%"><spring:message code="aca.Nombre"/> del Padre o Tutor</th>
	    	<th width="10%">Acceso</th>
	  	</tr>
<%	for(int i=0; i<lisPadre.size(); i++){
		PadreAlumno padre = (PadreAlumno) lisPadre.get(i);
		
		String estado = "";
		if (padre.getEstado().equals("S")){
			estado = "Solicitado";
		}else if (padre.getEstado().equals("A")){ 
			estado = "Autorizado";
		}else{ 
			estado = "Inactivo";
		}	
		
		String padreNombre = "-";
		if (mapaPadres.containsKey(padre.getPadreId())){
			padreNombre = mapaPadres.get(padre.getPadreId()).getNombre()+" "+mapaPadres.get(padre.getPadreId()).getPaterno()+" "+mapaPadres.get(padre.getPadreId()).getMaterno(); 
		}
%>
		<tr> 
    		<td><%= i+1%></td>    		
    		<td><a href="javascript:autorizar('<%=padre.getPadreId()%>')"><%=padre.getPadreId()%></a></td>
    		<td>
    			<a href="javascript:autorizar('<%=padre.getPadreId()%>')">
    		 	<%=padreNombre%>
    		 	</a>
    		</td>
    		<td><%=estado%></td>
  		</tr>
  <%	}	%>
	</table>
</div>
<script>
	function autorizar(padre){
		if ( confirm("¡Esta operación autoriza al padre o tutor el acceso a tu portal académico! ¿Deseas autorizar el acceso?") ){
			location.href = "grabarPadre?PadreId="+padre;
		}	
	}
</script>