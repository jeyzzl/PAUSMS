<%@page import="aca.emp.spring.EmpMaestro"%>
<%@page import="java.util.List"%>

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
	List<EmpMaestro> lista	= (List<EmpMaestro>) request.getAttribute("lista");

	String estadoCivil = "Casado";

	int cont = 0;

%>
<div class="container-fluid">
	<div class="alert alert-primary" role="alert">
		<h1>Lista de Maestros Externos</h1>
	</div>
	<table id="table" class="table" data-toggle="table" data-pagination="false" data-search="true" data-show-columns="true" data-page-list="[10, 25, 50, 100, all]" data-show-header="true" data-show-footer="true">
		<thead class="table-info">	 
			<tr>
			    <th align="left" width="3%">No.</th>
			    <th align="left" width="3%">N° Nom</th>
			    <th align="left" width="15%"><spring:message code="aca.Nombre"/></th>
			    <th align="left" width="3%"><spring:message code="aca.Genero"/></th>
			    <th align="left" width="5%">Edo. Civil</th>
			    <th align="left" width="5%"><spring:message code="aca.Telefono"/></th>
			    <th align="left" width="10%">E-mail</th>
			    <th align="left" width="3%"><spring:message code="aca.Estado"/></th>
			</tr>
		</thead>
		<tbody>    
<%  for (EmpMaestro maestro : lista) {
			
		if(maestro.getEstadoCivil().equals("S")){
			estadoCivil = "Soltero";
		} else if(maestro.getEstadoCivil().equals("D")){
			estadoCivil = "Divorciado";
		}

		cont++;  
%>
	  		<tr>
				<td align="left"><%= cont %></td>  
	  			<td align="center"><%= maestro.getCodigoPersonal() %></td>
<%		String aMaterno = maestro.getApellidoMaterno()==null ? "" : maestro.getApellidoMaterno();
		if(aMaterno.toLowerCase().equals("null")) aMaterno = "";
%>
			  	<td align="left">&nbsp;&nbsp;&nbsp;<%= maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+aMaterno%></td>
			  	<td align=left><%= maestro.getGenero().equals("M") ? "Hombre" : "Mujer"%></td>
			  	<td align="left"><%= estadoCivil%></td>
<%		String tel = maestro.getTelefono()==null ? "-" : maestro.getTelefono();
		if(tel.toLowerCase().equals("null") || tel.toLowerCase().equals("s/n")) tel = "-";
%>
	  			<td align="center"><%=tel%></td>
<% 		String email = maestro.getEmail()==null ? "-" : maestro.getEmail();
		if(email.toLowerCase().equals("null")) email = "-";
%>
	  			<td align="center"><%=email%></td>
	  			<td align="center"><%=maestro.getEstado().equals("A") ? "Activo" : "Inactivo"%></td>
	  		</tr>	
	
<%   } %>
  		</tbody>
	</table>
</div>