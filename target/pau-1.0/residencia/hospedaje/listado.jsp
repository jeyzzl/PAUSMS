<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.residencia.spring.ResHospedaje"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<head>
</head>	
<%	
	//String codigoPersonal 	= (String)request.getAttribute("codigoPersonal");	
	
	List<ResHospedaje> lisHospedaje					= (List<ResHospedaje>)request.getAttribute("lisHospedaje");	
%>
<body>

<div class="container-fluid">
	<h1>Accommodation List</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary btn-small" title="New Accommodation" href="nuevo">New</a>
	</div>
	<table id="table" class="table table-sm table-bordered">
		<thead class="table-info">	 
			<tr>
				<th width="5%" >#</th>			
				<th width="10%" >Name</th>
				<th width="10%" >Surname</th>
				<th width="10%" >Employee ID</th>
				<th width="20%" >Address</th>
				<th width="5%" >Phone Number</th>
				<th width="5%" >Male Capacity</th>
				<th width="5%" >Female Capacity</th>			
				<th width="5%" >Bedrooms</th>			
				<th width="5%" >Status</th>			
			</tr>
		</thead>				
	<%
	int con = 1;
	for (ResHospedaje hospedaje : lisHospedaje) {%>
		<tr>
			<td ><%=con++%> <a href="nuevo?Id=<%=hospedaje.getId()%>" class="fas fa-edit"></a></td>
			<td ><%=hospedaje.getNombre()%></td>
			<td ><%=hospedaje.getApellidos()%></td>		
			<td ><%=hospedaje.getNomina()%></td>
			<td ><%=hospedaje.getDireccion()%></td>
			<td ><%=hospedaje.getTelefono()%></td>
			<td ><%=hospedaje.getCupoHombres()%></td>
			<td ><%=hospedaje.getCupoMujeres()%></td>
			<td ><%=hospedaje.getCuartos()%></td>
			<td ><%=hospedaje.getEstado().equals("A") ? "Active" : "Inactive"%></td>
		</tr>
				
		<%
	}				
	%>
	</table>
</div>
</body>
<script type="text/javascript"> 
</script>
