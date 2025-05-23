<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.bec.spring.BecInforme"%>
<%@page import="aca.financiero.spring.ContEjercicio"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<% 
	String ejercicioId 						= (String) request.getAttribute("ejercicioId"); 
	String mensaje							= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	List<ContEjercicio> lisEjercicios 		= (List<ContEjercicio>) request.getAttribute("lisEjercicios");
	List<BecInforme> lisInformes 			= (List<BecInforme>)request.getAttribute("lisInformes");
	HashMap<String,String> mapaInformes 	= (HashMap<String,String>)request.getAttribute("mapaInformes");
%>
<style>
	body{
		background:white;
	}
	.puestosAlum td, .puestosAlum th{
		background: white !important;
	}
	
	.puestosAlum th{
		color: black !important;
		border: 1px solid #DCDEDE !important;
	}
</style>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<div class="container-fluid">
	<h2>Catálogo de Informes</h2>
	<form action="informe" name="form" method="post">
	<div class="alert alert-info">	
		<select name="EjercicioId" id="EjercicioId" style="width:150px;" onchange="document.form.submit()">
<%	for(ContEjercicio ejercicio: lisEjercicios){ %>
			<option value="<%=ejercicio.getIdEjercicio()%>" <%=ejercicioId.equals(ejercicio.getIdEjercicio())?"selected":""%>><%=ejercicio.getIdEjercicio() %></option>
<%	} %>
		</select>
		&nbsp;&nbsp;
		<a href="editar"  class="btn btn-primary"><i class="fas fa-plus"></i><spring:message code='aca.Añadir'/></a>
	</div>
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>	
	</form>
	<table class="table table-condensed" align="center">
	<tr>
		<th>#</th>
		<th>Editar</th>
		<th>InformeId</th>
		<th><spring:message code="aca.Nombre"/></th>
		<th>Fecha Ini</th>
		<th>Fecha Fin</th>
		<th>Nivel</th>
		<th>Orden</th>
		<th><spring:message code="aca.Estado"/></th>	 
		<th>#Inf.</th>
	</tr>
<%
	int row=0;
	for (BecInforme informe : lisInformes){
		row++;
		
		String total = "0";
		if (mapaInformes.containsKey(informe.getInformeId())){
			total = mapaInformes.get(informe.getInformeId());
		}
%>
	<tr>
		<td><%= row%></td>
		<td>
			<a href="editar?InformeId=<%=informe.getInformeId()%>"><i class="fas fa-edit"></i></a>
<%		if (total.equals("0")){%>
			<a href="borrar?InformeId=<%=informe.getInformeId()%>"><i class="fas fa-trash-alt"></i></a>
<% 		}%>			
		</td>
		<td><%= informe.getInformeId() %></td>
		<td><%= informe.getInformeNombre() %></td>
		<td><%= informe.getFechaIni() %></td>
		<td><%= informe.getFechaFin() %></td>
		<td><%= informe.getNivel() %></td>
		<td><%= informe.getOrden() %></td>
		<td><%= informe.getEstado() %></td>
		<td><span class="badge bg-dark"><%=total%></span></td>
	</tr>
<%
	}	
%>
	</table>	
</div>