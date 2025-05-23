<%@ page import="java.text.*" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">		
	function Borrar( ){
		if(confirm("Are you sure to delete all grades?")==true){	  		
			document.frmGrupo.submit();
		}		
	}	
</script>

<%
	DecimalFormat getformato= new DecimalFormat("##0.00;(##0.00)");

 	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
 	String codigoEmpleado	= (String) session.getAttribute("codigoEmpleado");
	String cursoCargaId		= request.getParameter("CursoCargaId")==null?"000000":request.getParameter("CursoCargaId");
	String cargaId 			= cursoCargaId.substring(0,6);
	String cargaNombre		= (String) request.getAttribute("cargaNombre");
	String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	String cursoId			= (String) request.getAttribute("cursoCargaId");
	String nombreMaestro 	= (String) request.getAttribute("nombreMaestro");
	String nombreMateria 	= (String) request.getAttribute("nombreMateria");
	String resultado		= "";
	
	int numInsc				= (int) request.getAttribute("numInsc");
	int numBaja				= (int) request.getAttribute("numBaja");
	
	int numEst				= (int) request.getAttribute("numEst");
	int numEstReg			= (int) request.getAttribute("numEstReg");
	int numAct				= (int) request.getAttribute("numAct");
	int numActReg			= (int) request.getAttribute("numActReg");		
%>
<div class="container-fluid">
	<h3>Delete Evaluations <small class="text-muted fs-6"> ( <b>Professor:</b> <%=nombreMaestro%> &nbsp; <b>Subject:</b> <%=nombreMateria%>&nbsp;) </small></h3>
	<div class="alert alert-info">
		<a href="cursos" class="btn btn-primary btn-sm"><spring:message code="aca.Regresar"/></a>&nbsp;			
	</div>	
<%	if (!mensaje.equals("-")){%>
		<div class="alert alert-secondary"><%=mensaje%></div>
<%	}%>
	<form name="frmGrupo" action="deleteNotas?CursoCargaId=<%=cursoCargaId%>" method="post">
	<table class="table table-sm table-bordered">
	<tr>
  		<th> Subject Data</th>
	</tr>
	<tr>
  		<td>
  			<b>Load:</b> [ <%=cargaId%> - <%=cargaNombre%> ]&nbsp;&nbsp;<b>Record:</b> [ <%=cursoCargaId%> ]&nbsp;&nbsp;&nbsp;    	
	    </td>
	</tr>	  
	<tr>
  		<td>  			
	    	<b>Students:</b>&nbsp; &nbsp;Enrolled[ <b><%=numInsc%></b> ] &nbsp; Drop Out[ <b><%=numBaja%></b> ]	    	
	    </td>
	</tr>
	<tr>
  		<td>  				    	
	    	<b>Strategies:</b>&nbsp; &nbsp;Added[ <b><%= numEst%></b> ] &nbsp; Registered Grades[ <b><%= numEstReg%></b> ]	    	
	    </td>
	</tr>
	<tr>
  		<td>    	
	    	<b>Activities:</b>&nbsp; &nbsp;Added[ <b><%= numAct%></b> ] &nbsp; Registered Grades[ <b><%= numActReg%></b> ]
	    </td>
	</tr>
	</table>
	<div class="alert alert-info">	
<% 	if ( numEst > 0 ){%>
	  	<input name="Borrar Notas" onClick="javascript:Borrar()" type="button" value="Delete Grades" class="btn btn-danger btn-sm">
<% 	}else{
	out.print(" No grades registered!");
}%> 	  
	</div>  	
</form>