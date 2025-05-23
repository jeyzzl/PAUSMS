<%@page import="java.util.List"%>
<%@page import="aca.emp.spring.EmpMaestro"%>
<%@ page import= "java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap4/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/fontawesome.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/solid.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/regular.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-table.min.css">
	<script src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>  	
	<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
  	<script src="<%=request.getContextPath()%>/bootstrap4/js/bootstrap.min.js" type="text/javascript"></script> 
  	<script src="<%=request.getContextPath()%>/js/bootstrap-table.min.js" type="text/javascript"></script>
</head>
<%	
	List<EmpMaestro> lista					= (List<EmpMaestro>) request.getAttribute("lista");
	HashMap<String,String> mapaMaestros		= (HashMap<String,String>)request.getAttribute("mapaMaestros");
	HashMap<String,String> mapaAuxiliares	= (HashMap<String,String>)request.getAttribute("mapaAuxiliares");
	
	String resultado		= request.getParameter("Resultado")==null?"-":request.getParameter("Resultado");
	int cont = 1;
%>
<body>
<div class="container-fluid">
	<h2>Employee Registration</h2>
	<div class="alert alert-primary" role="alert">
		<a href="maestro" class="btn btn-primary">New Employee</a> &nbsp; &nbsp; <%=resultado.equals("-")?"":resultado%>
	</div>	
  	<table id="table" class="table" data-toggle="table" data-pagination="false" data-search="true" data-show-columns="true" data-page-list="[10, 25, 50, 100, all]" data-show-header="true" data-show-footer="true">
	<thead>
  	    <tr>
 	    	<th>No.</th>
  	    	<th>Op.</th>
  	    	<th>ID</th>
  	    	<th>Name</th>
  	    	<th>Gender</th>
  	    	<th>Phone</th>
  	    	<th>Email</th>
  	    	<th>Status</th>
  	    	<th class="text-end">Subjects</th>
    	</tr>
	</thead>
	<tbody>
<%	for(EmpMaestro maestro : lista){
		String numPrincipal = "0";
		String numAuxiliar 	= "0";
		if(mapaMaestros.containsKey(maestro.getCodigoPersonal())){
			numPrincipal = mapaMaestros.get(maestro.getCodigoPersonal());
		}		
		if(mapaAuxiliares.containsKey(maestro.getCodigoPersonal())){
			numAuxiliar = mapaAuxiliares.get(maestro.getCodigoPersonal());
		}
%>
		<tr>
			<td><%=cont++%></td>
			<td>
				<a href="maestro?CodigoPersonal=<%=maestro.getCodigoPersonal()%>"><i class="fas fa-edit"></i></a>
<% 		if(numPrincipal.equals("0") && numAuxiliar.equals("0")){ %>	
			    <a href="javascript:borrar('<%=maestro.getCodigoPersonal()%>')"><i class="fas fa-trash" style="color:red"></i></a>&nbsp;
<%		}%>		
				<a href="fotocredencial?CodigoPersonal=<%=maestro.getCodigoPersonal()%>"><i class="fas fa-camera"></i></a>
			</td>
	 	    <td><%=maestro.getCodigoPersonal()%></td>
			<td><%=maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno()%></td>
			<td><%=maestro.getGenero().equals("M") ? "Male" : "Female"%></td>
			<td><%=maestro.getTelefono()%></td>
			<td><%=maestro.getEmail()%></td>
			<td><%=maestro.getEstado().equals("A") ? "Active" : "Inactive"%></td>
			<td class="text-end"><%=Integer.parseInt(numPrincipal)+Integer.parseInt(numAuxiliar)%></td>
		</tr>
<%}%>
	</tbody>
	</table>
</div>	
<script type="text/javascript">
	$(document).ready(function() {
	    $('#table').DataTable();
	} );
	
	function borrar(codigoPersonal){
		if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  		document.location.href="borrar?CodigoPersonal="+codigoPersonal;
	  	}
	}
</script>
</body>