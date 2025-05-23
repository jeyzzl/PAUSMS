<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmUsuario"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
	
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>	
</head>
<%		
	List<AdmUsuario> lisUsuarios 		= (List<AdmUsuario>)request.getAttribute("lisUsuarios");	
%>
<body>
<div class="container-fluid">
	<h2>Users Registered without an Application</h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="proceso"><i class="fas fa-arrow-left"></i></a>&nbsp;
		<input type="text" class="form-control" style="width:150px" placeholder="Search..." id="buscar">
		</div>	

	<table class="table table-bordered" id="table">
	<thead class="table-info">
	<tr>
		<th width="2%">#</th>
		<th width="3%">Edit</th>
		<th width="20%">Name</th>
		<th width="20%">Surname</th>
		<th width="20%">Maiden Name</th>
		<th width="15%">Account</th>	
		<th width="10%">Code</th>	
		<th width="10%">Date</th>		
	</tr>
	</thead>
<%	
	int row=0;
	for(AdmUsuario usuario : lisUsuarios){
		row++;	
%>	
	<tr>
	<td><%=row%></td>
	    <td><a href="cambiaPass?UsuarioId=<%=usuario.getUsuarioId()%>" title="Cambia contrase�a"><i class="fas fa-user-lock"></i></a></td>
		<td><%=usuario.getNombre()%></td>
		<td><%=usuario.getApellidoPaterno()%></td>
		<td><%=usuario.getApellidoMaterno()%></td>
		<td><%=usuario.getCuenta()%></td>
		<td><%=usuario.getCodigo()%></td>
		<td><%=usuario.getFecha()%></td>
	</tr>
<% 	}%>
	</table>	
</div>
</body>
</html>
<script src="../../js/tablesorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="../../js/search.js"></script>
<script type="text/javascript">	    
	jQuery('#Fecha').datepicker();	
	jQuery('#table').tablesorter();
	jQuery('#buscar').focus().search({table:jQuery("#table")});	
</script>