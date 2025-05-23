<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.Carga"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Carga" scope="page" class="aca.carga.Carga" />
<jsp:useBean id="CargaU" scope="page" class="aca.carga.CargaUtil" />

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
<script type="text/javascript">
	
	function Mostrar(){					
		document.forma.Accion.value="1";
		document.forma.submit();
	}

</script>
<%
	String codigoPersonal		= (String) request.getAttribute("codigoPersonal"); 
	String fInscripcion			= (String) request.getAttribute("fInscripcion");
	String cargas				= (String) request.getAttribute("cargas");
	String accion 				= (String) request.getAttribute("accion");
	
	List<Carga> lisCarga		= (List<Carga>) request.getAttribute("lisCarga");
	List<Carga> lisActivas		= (List<Carga>) request.getAttribute("lisActivas");
	
	HashMap<String,String> mapaFechaIni = (HashMap<String,String>) request.getAttribute("mapaFechaIni");
	HashMap<String,String> mapaFechaFin = (HashMap<String,String>) request.getAttribute("mapaFechaFin");
%>
<head></head>	
<body>
<div class="container-fluid">
	<h1>Select Loads</h1>
	<form id="forma" name="forma" action="cargas" method="post">
	<input name="Accion" type="hidden">
	<div class="alert alert-info">
		<a class="btn btn-primary btn-small" href="bestadistica"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		Date:
		<input type="text" id="Fecha" name="Fecha" class="input" maxlength="10" data-date-format="dd/mm/yyyy" value="<%=fInscripcion %>" style="margin:0; width:120px;"/>&nbsp;
		<a class="btn btn-primary btn-small" onclick="javascript:document.forma.submit();"><i class="fas fa-sync-alt"></i></a>
	</div>	
	<table class="table table-sm table-striped" style="width:70%">
	<thead>
		<tr class="table-info">
			<th>Select
				<a onclick="jQuery('.checkboxCarga').prop('checked',true)" class="badge bg-success"><spring:message code='aca.Todos'/></a>&nbsp;
				<a onclick="jQuery('.checkboxCarga').prop('checked', false)" class="badge bg-warning"><spring:message code='aca.Ninguno'/></a>
			</th>
			<th><spring:message code="aca.Clave"/></th>
			<th><spring:message code="aca.Carga"/></th>
			<th>Period</th>
			<th>Begin Enrollment</th>
			<th>End Enrollment</th>
			<th><spring:message code="aca.Status"/></th>
		</tr>
	</thead>
	<tbody>				
<%
	String cargaSelected = "";
	for(Carga carga : lisCarga){
		if (cargas.contains(carga.getCargaId()))
			cargaSelected = "checked";
		else
			cargaSelected = "";
		
		String inicioInscripcion = "0";
		if (mapaFechaIni.containsKey(carga.getCargaId())){
			inicioInscripcion = mapaFechaIni.get(carga.getCargaId());
		}
		
		String finInscripcion = "0";
		if (mapaFechaIni.containsKey(carga.getCargaId())){
			finInscripcion = mapaFechaFin.get(carga.getCargaId());
		}
		
		boolean esActiva 			= false;
		for(Carga cargaActiva : lisActivas){
			if (cargaActiva.getCargaId().equals(carga.getCargaId())) esActiva = true;
		}
%>
		<tr>
			<td>
				<input class="checkboxCarga" type="checkbox" id="<%=carga.getCargaId() %>" name="<%=carga.getCargaId() %>" value="S" <%=cargaSelected%>/>
			</td>
			<td>			
				<%=carga.getCargaId() %>																					
			</td>
			<td>			
				<b><%=carga.getNombreCarga() %></b>																					
			</td>
			<td>			
				[<%=carga.getFInicio() %> - <%=carga.getFFinal() %>																					
			</td>
			<td><%= inicioInscripcion %></td>
			<td><%= finInscripcion %></td>
			<td><%=esActiva?"Active":"Inactive"%></td>
		</tr>		
<%	} %>
		<tr><td colspan="7"><input class="btn btn-primary" type="submit" value="Save" onclick="Mostrar();"/></td></tr>
	</tbody>	
	</table>	
	</form>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script type="text/javascript">		
		jQuery('#Fecha').datepicker();
</script>