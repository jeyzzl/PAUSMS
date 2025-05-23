<%@ page import="java.util.List"%>
<%@page import="aca.carga.spring.Carga"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
<script type="text/javascript">
	
	function Mostrar(){					
		document.forma.Accion.value="1";
		document.forma.submit();
	}

</script>
<%
	String codigoPersonal 	= (String) request.getAttribute("codigoPersonal");
	String fInscripcion 	= (String) request.getAttribute("fInscripcion");
	String cargas 			= (String) request.getAttribute("cargas");
	String accion 			= (String) request.getAttribute("accion");
	boolean esActiva 		= false;
	String mensaje 			= "";
	
	List<Carga> lisCarga	= (List<Carga>)request.getAttribute("lisCargas");
	List<Carga> lisActivas	= (List<Carga>)request.getAttribute("lisActivas");
	
	if (accion.equals("1")){
		cargas = "";
		for(Carga carga : lisCarga){
			if(request.getParameter(carga.getCargaId()) != null ){			
				if(cargas.equals("")){
					cargas = "'"+carga.getCargaId()+"'";
				}else{
					cargas += ",'"+carga.getCargaId()+"'";						
				}
			}
		}
		mensaje = "1";
		session.setAttribute("cargas", cargas);	
	}		
%>
<head>
	
</head>	
<body>
<div class="container-fluid">
	<h2>Select Loads</h2>
	<form id="forma" name="forma" action="cargasActivas" method="post">
	<input name="Accion" type="hidden">
	<div class="alert alert-info">
		<a class="btn btn-primary btn-small" href="inscritos"><spring:message code="aca.Regresar"/></a>
	</div>	
<% if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Saved
	</div>	
<% }%>
	<table style="width:50%" class="table table-bordered table-sm">
		<tr class="table-info">
			<th>
				Select
				<a onclick="jQuery('.checkboxCarga').prop('checked',true)" class="badge bg-success"><spring:message code='aca.Todos'/></a>&nbsp;
				<a onclick="jQuery('.checkboxCarga').prop('checked', false)" class="badge bg-warning"><spring:message code='aca.Ninguno'/></a>	
			</th>
			<th><spring:message code="aca.Clave"/></th>
			<th><spring:message code="aca.Carga"/></th>
			<th>Period</th>
			<th><spring:message code="aca.Status"/></th>
		</tr>				
<%
	String cargaSelected = "";
	for(Carga carga : lisCarga){
		if (cargas.contains(carga.getCargaId())){
			cargaSelected = "checked";
		}else{
			cargaSelected = "";
		}
		for(Carga cargaActiva : lisActivas){
			if (cargaActiva.getCargaId().equals(carga.getCargaId())){
				esActiva = true;
			}
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
			<td><%=esActiva?"Active":"Inactive"%></td>
		</tr>		
<%	} %>
		<tr><td colspan="5"><input class="btn btn-primary" type="submit" value="Save" onclick="Mostrar();"/></td></tr>
	</table>
	
	</form>
	
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script type="text/javascript">		
		jQuery('#Fecha').datepicker();
</script>
</div>