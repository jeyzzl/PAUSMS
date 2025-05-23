<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.archivo.spring.ArchRevalida"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	List<ArchRevalida> lisRevalida				= (List <ArchRevalida>) request.getAttribute("lisRevalida");
	HashMap<String,AlumPersonal> mapaAlumnos	= (HashMap<String, AlumPersonal>) request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaAutorizados		= (HashMap<String, String>) request.getAttribute("mapaAutorizados");
	int cont = 0;	
%>
<div class="container-fluid">
	<h2>Revalidated Students</h2>
	<form action="alumnoSinDoc" method="post" name="frmdoc">
		<div class="alert alert-info d-flex align-items-center">
			<a href="menu" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
			&nbsp;&nbsp;
		</div>
		<table class="table table-sm table-bordered">
		  	<tr> 
		  		<th>#</th>
		  		<th>Op.</th>
		   		<th><spring:message code="aca.Matricula"/></th>
		    	<th><spring:message code="aca.Nombre"/></th>
		    	<th>Locks</th>		    	
		  	</tr>
<%
	int row = 0;
	for (ArchRevalida alumno : lisRevalida){
		String nombre = "";
		if(mapaAlumnos.containsKey(alumno.getCodigoPersonal())){
			nombre = mapaAlumnos.get(alumno.getCodigoPersonal()).getNombre()+" "+mapaAlumnos.get(alumno.getCodigoPersonal()).getApellidoPaterno()+" "+mapaAlumnos.get(alumno.getCodigoPersonal()).getApellidoMaterno();
		}
		String autorizado = "X";
		if ( mapaAutorizados.containsKey(alumno.getCodigoPersonal()) ){
			autorizado = mapaAutorizados.get(alumno.getCodigoPersonal());
		}
%>
			<tr> 
		  		<td align="center"><%=++row%></td>
		  		<td align="center">
  					<a onclick="eliminar('<%=alumno.getCodigoPersonal()%>');" class="badge bg-warning"><i class="fas fa-times"></i></a> 		
		  		</td>
		    	<td><%=alumno.getCodigoPersonal()%></td>
		    	<td><%=nombre%></td>
		    	<td><%=autorizado%></td>
			</tr>
<%	}%>
		</table>
	</form>
	<script type="text/javascript">
		function eliminar(codigoAlumno){
			if(confirm("¿Are you sure you want to delete the revalidation?")){
				document.location.href='borrarRevalida?CodigoAlumno='+codigoAlumno;
			}
		}
	</script>
</div>