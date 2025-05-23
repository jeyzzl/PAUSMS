<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.bec.spring.BecInforme"%>
<%@page import="aca.bec.spring.BecInformeAlumno"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<% 
	DecimalFormat getFormato	= new DecimalFormat("###,##0.00;(###,##0.00)");

	String codigoPersonal 		= (String) session.getAttribute("codigoAlumno");
	String alumnoNombre			= (String) request.getAttribute("alumnoNombre");
	
	List<BecInformeAlumno> lisInformes 				= (List<BecInformeAlumno>) request.getAttribute("lisInformes");
	HashMap<String,BecInforme> mapaInformes			= (HashMap<String,BecInforme>) request.getAttribute("mapaInformes");
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

<%@ include file= "menu.jsp" %>

<div class="container-fluid">
	<h3>Listado de informes del alumno<small class="text-muted fs-5">(<%=codigoPersonal%> - <%=alumnoNombre%>)</small></h3>
	<div class="alert alert-info">
	  <a href="datos" class="btn btn-primary btn-sm"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>&nbsp;
	</div>	
	<table class="table table-condensed">
	<tr>
		<th>#</th>
		<th>Informe</th>
		<th class="right">Horas</th>
		<th class="right">Puntualidad</th>
		<th class="right">Función</th>
		<th class="right">Tiempo</th>
		<th class="right">Iniciativa</th>
		<th class="right">Relación</th>
		<th class="right">Respeto</th>
		<th class="right">Productivo</th>
		<th class="right">Cuidado</th>
		<th class="right"><spring:message code="aca.Promedio"/></th>
	</tr>
	<%
	int cont = 1;	
	for(BecInformeAlumno informe: lisInformes){
		String nombre = "-";
		if (mapaInformes.containsKey(informe.getInformeId())){
			nombre = mapaInformes.get(informe.getInformeId()).getInformeNombre();
		}
		
		double total = (Double.parseDouble(informe.getPuntualidad())+ Double.parseDouble(informe.getFuncion())+
		Double.parseDouble(informe.getTiempo())+Double.parseDouble(informe.getIniciativa())+Double.parseDouble(informe.getRelacion())+
		Double.parseDouble(informe.getRespeto())+Double.parseDouble(informe.getProductivo())+Double.parseDouble(informe.getCuidado())+40)/8*10;
		
		String color = "-";
		if (total>= 85){
			color = "style='background-color:green'";
		}else if (total>=70&&total<85){	
			color = "style='background-color:#d4da22'";
		}else if (total<70){
			color = "style='background-color:red'";
		}
		
		String colorPuntualidad = "";
		if (Integer.parseInt(informe.getPuntualidad())+5 >= 9 ){
			colorPuntualidad = "style='background-color:green'";
		}else if (Integer.parseInt(informe.getPuntualidad())+5 >= 7 && Integer.parseInt(informe.getPuntualidad())+5 < 9){
			colorPuntualidad = "style='background-color:#d4da22'";
		}else{
			colorPuntualidad = "style='background-color:red'";
		}
		
		String colorFuncion = "";
		if (Integer.parseInt(informe.getFuncion())+5 >= 9 ){
			colorFuncion = "style='background-color:green'";
		}else if (Integer.parseInt(informe.getFuncion())+5 >= 7 && Integer.parseInt(informe.getFuncion())+5 < 9){
			colorFuncion = "style='background-color:#d4da22'";
		}else{
			colorFuncion = "style='background-color:red'";
		}
		
		String colorTiempo = "";
		if (Integer.parseInt(informe.getTiempo())+5 >= 9 ){
			colorTiempo = "style='background-color:green'";
		}else if (Integer.parseInt(informe.getTiempo())+5 >= 7 && Integer.parseInt(informe.getTiempo())+5 < 9){
			colorTiempo = "style='background-color:#d4da22'";
		}else{
			colorTiempo = "style='background-color:red'";
		}
		
		String colorIniciativa = "";
		if (Integer.parseInt(informe.getIniciativa())+5 >= 9 ){
			colorIniciativa = "style='background-color:green'";
		}else if (Integer.parseInt(informe.getIniciativa())+5 >= 7 && Integer.parseInt(informe.getIniciativa())+5 < 9){
			colorIniciativa = "style='background-color:#d4da22'";
		}else{
			colorIniciativa = "style='background-color:red'";
		}
		
		String colorRelacion = "";
		if (Integer.parseInt(informe.getRelacion())+5 >= 9 ){
			colorRelacion = "style='background-color:green'";
		}else if (Integer.parseInt(informe.getRelacion())+5 >= 7 && Integer.parseInt(informe.getRelacion())+5 < 9){
			colorRelacion = "style='background-color:#d4da22'";
		}else{
			colorRelacion = "style='background-color:red'";
		}
		
		String colorRespeto = "";
		if (Integer.parseInt(informe.getRespeto())+5 >= 9 ){
			colorRespeto = "style='background-color:green'";
		}else if (Integer.parseInt(informe.getRespeto())+5 >= 7 && Integer.parseInt(informe.getRespeto())+5 < 9){
			colorRespeto = "style='background-color:#d4da22'";
		}else{
			colorRespeto = "style='background-color:red'";
		}
		
		String colorProductivo = "";
		if (Integer.parseInt(informe.getProductivo())+5 >= 9 ){
			colorProductivo = "style='background-color:green'";
		}else if (Integer.parseInt(informe.getProductivo())+5 >= 7 && Integer.parseInt(informe.getProductivo())+5 < 9){
			colorProductivo = "style='background-color:#d4da22'";
		}else{
			colorProductivo = "style='background-color:red'";
		}
		
		String colorCuidado = "";
		if (Integer.parseInt(informe.getCuidado())+5 >= 9 ){ 
			colorCuidado = "style='background-color:green'";
		}else if (Integer.parseInt(informe.getCuidado())+5 >= 7 && Integer.parseInt(informe.getCuidado())+5 < 9){
			colorCuidado = "style='background-color:#d4da22'";
		}else{
			colorCuidado = "style='background-color:red'";
		}		
	%>
	<tr>
		<td><%= cont %></td>
		<td><%= nombre%></td>
		<td class="right"><span class="badge badge-dark"><%= informe.getHoras()%></span></td>
		<td class="right"><span class="badge" <%=colorPuntualidad%>><%= Integer.parseInt(informe.getPuntualidad())+5 %></span></td>
		<td class="right"><span class="badge" <%=colorFuncion%>><%= Integer.parseInt(informe.getFuncion())+5 %></span></td>
		<td class="right"><span class="badge" <%=colorTiempo%>><%= Integer.parseInt(informe.getTiempo())+5 %></span></td>
		<td class="right"><span class="badge" <%=colorIniciativa%>><%= Integer.parseInt(informe.getIniciativa())+5 %></span></td>
		<td class="right"><span class="badge" <%=colorRelacion%>><%= Integer.parseInt(informe.getRelacion())+5 %></span></td>
		<td class="right"><span class="badge" <%=colorRespeto%>><%= Integer.parseInt(informe.getRespeto())+5 %></span></td>
		<td class="right"><span class="badge" <%=colorProductivo%>><%= Integer.parseInt(informe.getProductivo())+5 %></span></td>
		<td class="right"><span class="badge" <%=colorCuidado%>><%= Integer.parseInt(informe.getCuidado())+5 %></span></td>
		<td class="right"><span class="badge" <%=color%>><%=getFormato.format(total)%></span></td>
	</tr>
<%
		cont++; 
	}	
%>
	</table>	
</div>