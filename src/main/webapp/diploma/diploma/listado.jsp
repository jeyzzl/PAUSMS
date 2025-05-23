<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.diploma.spring.DipCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	List<DipCurso> lisDiplomas 				= (List<DipCurso>)request.getAttribute("lisDiplomas");
	HashMap<String,String> mapaAlumnos 		= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaPublicados 	= (HashMap<String,String>)request.getAttribute("mapaPublicados");
%>
<script>
	function Borrar(diplomaId){
		if (confirm("¿Estás seguro de borrar este registro?")){
			document.location.href="borrarDiploma?DiplomaId="+diplomaId;
		}
	}
</script>
<body>
<div class="container-fluid">
	<h2>Diplomas</h2>
	<hr>
	<div class="alert alert-info d-flex align-items-center">
		<a href="editar" class="btn btn-primary"><i class="fas fa-plus"></i></a>
	</div>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr> 
		<th>Op.</th>
		<th>Clave</th>		
		<th>Curso</th>
		<th>Tema</th>
		<th>Otra Institución</th>
		<th>Horas</th>	 
		<th>Periodos</th>
		<th>Fecha</th>
		<th>#Alum.</th>
		<th>#Pub.</th>
	</tr>
	</thead>
	<tbody>
<%
	for (DipCurso diploma : lisDiplomas){
		
		String total = "0";
		if (mapaAlumnos.containsKey(diploma.getDiplomaId())){
			total = mapaAlumnos.get(diploma.getDiplomaId());
		}
		
		String publicados = "0";
		if (mapaPublicados.containsKey(diploma.getDiplomaId())){
			publicados = mapaPublicados.get(diploma.getDiplomaId());
		}
		
%>
	<tr>
   		<td class="fs-6">
   			<a href="editar?DiplomaId=<%=diploma.getDiplomaId()%>"><i class="fas fa-edit"></i></a>
<%		if (total.equals("0")){%>
			&nbsp;<a href="javascript:Borrar('<%=diploma.getDiplomaId()%>');"><i class="fas fa-trash-alt"></i></a>
<%		} %>   			
   		</td>
	    <td class="fs-6"><%=diploma.getDiplomaId()%></td>	 	
	 	<td class="fs-6"><%=diploma.getCurso()%></td>
	  	<td class="fs-6"><%=diploma.getTema()%></td>
	  	<td class="fs-6"><%=diploma.getInstitucion()%></td>
	 	<td class="fs-6"><%=diploma.getHoras()%></td>
	 	<td class="fs-6"><%=diploma.getPeriodo()%></td>
	 	<td class="fs-6"><%=diploma.getFecha()%></td>	   
	 	<td class="fs-6 text-center"">
	 		<a href="alumnos?DiplomaId=<%=diploma.getDiplomaId()%>" class="badge rounded-pill bg-primary text-decoration-none"><%=total%></a>
	 	</td>	
	 	<td class="fs-6 text-center"">
	 		<%=publicados.equals("0")?"<span class='badge bg-warning rounded-pill'>"+publicados+"</span>":"<span class='badge bg-success rounded-pill'>"+publicados+"</span>"%>
	 	</td>
  	</tr> 
<%	} %>
	</tbody>
	</table>
</div>
</body>
</html>