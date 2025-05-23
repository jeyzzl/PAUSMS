<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.financiero.spring.FesCcobro"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoAlumno			= (String)session.getAttribute("codigoAlumno");
	String alumnoNombre 		= (String)request.getAttribute("alumnoNombre");
	
	List<FesCcobro> lisCalculos 					= (List<FesCcobro>)request.getAttribute("lisCalculos");
	HashMap<String,String> mapaCreditos 			= (HashMap<String,String>)request.getAttribute("mapaCreditos");
	HashMap<String,CargaBloque> mapaBloques			= (HashMap<String,CargaBloque>)request.getAttribute("mapaBloques");
	HashMap<String,CargaBloque> mapaBloquesActivos	= (HashMap<String,CargaBloque>)request.getAttribute("mapaBloquesActivos");
%>
<div class="container-fluid">
	<h1>Cálculos de Cobro <small class="text-muted fs-5">( <%= codigoAlumno%> - <%=alumnoNombre%> )</small></h1>
	<hr>
	<table class="table table-sm table-bordered">
	<tr class="table-info">
		<th>#</th>
		<th><spring:message code="aca.Facultad"/></th>
		<th><spring:message code="aca.Carrera"/></th>
		<th><spring:message code="aca.Plan"/></th>
		<th><spring:message code="aca.Carga"/></th>
		<th><spring:message code="aca.Bloque"/></th>
		<th><spring:message code="aca.Modalidad"/></th>
		<th class="right"><spring:message code="aca.Creditos"/></th>
		<th>FormaPago</th>
		<th><spring:message code="aca.Estado"/></th>
		<th><spring:message code="aca.Fecha"/></th>
		<th>Cálculo</th>
	</tr>
<%
	int row=0;	
	for (FesCcobro calculo : lisCalculos){
		row++;
		
		boolean esActivo = false;
		if (mapaBloquesActivos.containsKey(calculo.getCargaId()+calculo.getBloque())){
			esActivo = true;
		}
		
		String bloqueNombre = "-";
		if (mapaBloques.containsKey(calculo.getCargaId()+calculo.getBloque())){
			bloqueNombre = mapaBloques.get(calculo.getCargaId()+calculo.getBloque()).getNombreBloque();
		}
			
		String creditos = "0";
		if (mapaCreditos.containsKey(calculo.getCargaId()+calculo.getBloque())){
			creditos = mapaCreditos.get(calculo.getCargaId()+calculo.getBloque());
		}		
%>
	<tr>
		<td><%= row%></td>
		<td><%= calculo.getFacultad() %></td>
		<td><%= calculo.getCarrera() %></td>
		<td><%= calculo.getPlanId() %></td>
		<td><%= calculo.getCargaId() %></td>
		<td>[<%= calculo.getBloque() %>] - <%=bloqueNombre %></td>
		<td><%= calculo.getModalidad() %></td>
		<td class="right"><%= creditos %></td>
		<td><%= calculo.getFormaPago().equals("P")?"Pagar&eacute;":"Contado" %></td>
		<td><%= calculo.getInscrito().equals("S")?"Inscrito":"Pendiente" %></td>
		<td><%= calculo.getFecha() %></td>
		<td>
<%		if (esActivo == true && calculo.getInscrito().equals("N")){%>
			<form name="Calculo<%=calculo.getCargaId()+calculo.getBloque()%>" method="post" style="margin-bottom: -5px;" action="https://virtual-um.um.edu.mx/financiero/calculoCobroEstimado.html" target="_blank">
			<input type="hidden" name="txtMatricula" value="<%=calculo.getMatricula() %>">			
			<input type="hidden" name="txtCarga" value="<%=calculo.getCargaId()%>@<%=calculo.getBloque()%>@<%=calculo.getPlanId()%>">			
			<input type="hidden" name="sltFormaPago" value="<%=calculo.getFormaPago()%>">					
			<button class="btn btn-primary btn-small" type="submit"><i class="fas fa-sync-alt"></i></button>
			</form>
<%				
		}else if(calculo.getInscrito().equals("S")){
			out.print("<a class='btn btn-success btn-sm' href='https://virtual-um.um.edu.mx/financiero/calculoCobroReimpresion.html?txtMatricula="+calculo.getMatricula()+"&txtCarga="+calculo.getCargaId()+"&Bloque="+calculo.getBloque()+"' target='_blank'>Ver</a>");
		}else{
			out.print("<a class='btn btn-danger btn-sm'><i class='fas fa-times' ></i></a>");
		}
%>
		</td>
<%	} %>
	</tr>
	</table>
</div>