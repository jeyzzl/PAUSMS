<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.admision.spring.AdmAsesor"%>
<%@ page import= "aca.vista.spring.Maestros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar( asesorId ){
		if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  		document.location="borrarAsesor?AsesorId="+asesorId;
	  	}
	}
</script>
<%
	List<AdmAsesor> lisAsesores 						= (List<AdmAsesor>)request.getAttribute("lisAsesores");
	HashMap<String, String> mapEmpleadoNombre 			= (HashMap<String, String>)request.getAttribute("mapEmpleadoNombre");

%>
<div class="container-fluid">
	<h2>Advisors</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editarAsesor">Add</a>	
	</div>
	<table class="table table-sm table-bordered">
	<thead class="table-dark">  
  	<tr> 
  		<th width="5%"><spring:message code="aca.Operacion"/></th>
  		<th width="19%">Advisor ID</th>
  		<th width="19%">Name</th>
  		<th width="19%">Email</th>
  		<th width="19%">Phone Number</th>
  		<th width="19%">State</th>
  	</tr>
  	</thead>
  	<tbody>
<%	
	for(AdmAsesor asesor: lisAsesores){
		String numUsada 	= "0";
		String textUsada 	= "<span class='badge bg-warning rounded-pill'>0</span>"; 
		
%>  
	<tr> 
		<td style="text-align: center">
			<a title="<spring:message code="aca.Modificar"/>" href="editarAsesor?AsesorId=<%=asesor.getAsesorId()%>"><i class="fas fa-edit"></i></a>
			
			<a title="<spring:message code="aca.Eliminar"/>" href="javascript:Borrar('<%=asesor.getAsesorId()%>')"><i class="fas fa-trash-alt" style="color:red"></i></a>
	  
		</td>
    	<td readonly><%=asesor.getAsesorId()%></td>
    	<td readonly><%=mapEmpleadoNombre.get(asesor.getAsesorId())%></td>
    	<td><%=asesor.getCorreo()%></td>
    	<td><%=asesor.getTelefono()%></td>
    	<td><%=asesor.getEstado()%></td>
  	</tr>
<%	} %>  
	</table>
</div>