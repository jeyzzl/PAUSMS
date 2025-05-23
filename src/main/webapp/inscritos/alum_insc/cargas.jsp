<%@ page import="java.util.List"%>
<%@ page import="aca.carga.spring.Carga"%>
<%@ page import="java.util.HashMap"%>

<%@ include file= "../../headPortal.jsp" %>
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
	//String codigoPersonal				= (String) session.getAttribute("codigoPersonal");	  
	String cargas						= (String)session.getAttribute("cargas") == null?"":session.getAttribute("cargas").toString();
	String fInscripcion					= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
	String accion 						= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	List<Carga> lisCarga				= (List<Carga>)request.getAttribute("lisCarga");
	List<Carga> lisActivas				= (List<Carga>)request.getAttribute("lisActivas");
	HashMap<String,String> mapaPrimerDia 	= (HashMap<String,String>) request.getAttribute("mapaPrimerDia");
	HashMap<String,String> mapaUltimoDia 	= (HashMap<String,String>) request.getAttribute("mapaUltimoDia");
	
	if (accion.equals("1")){
		
		cargas = "";
		for(int i = 0; i < lisCarga.size(); i++){
			Carga carga = (Carga) lisCarga.get(i);
			if(request.getParameter(carga.getCargaId()) != null ){			
				if(cargas.equals(""))
					cargas = "'"+carga.getCargaId()+"'";
				else
					cargas += ",'"+carga.getCargaId()+"'";						
			}
		}
		session.setAttribute("cargas", cargas);	
	}		
%>
<head>
	
</head>	
<body>
<div class="container-fluid">
	<h2>Select Loads</h2>
	<form id="forma" name="forma" action="cargas" method="post">
	<input name="Accion" type="hidden">
	<div class="alert alert-info">
		<a class="btn btn-primary btn-small" href="estadistica?Cargas=<%=cargas%>"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		Date:
		<input type="text" id="Fecha" name="Fecha" class="input" maxlength="10" data-date-format="dd/mm/yyyy" value="<%=fInscripcion %>" style="width:120px; margin:0;"/>&nbsp;
		<a class="btn btn-primary btn-small" onclick="javascript:document.forma.submit();"><i class="fas fa-sync-alt"></i></a>
	</div>	
	<table class="table table-sm table-bordered" style="width:70%">
		<tr class="table-info">
			<th>
				Select
				<a onclick="jQuery('.checkboxCarga').prop('checked',true)" class="badge bg-success"><spring:message code='aca.Todos'/></a>&nbsp;
				<a onclick="jQuery('.checkboxCarga').prop('checked', false)" class="badge bg-warning"><spring:message code='aca.Ninguno'/></a>	
			</th>
			<th><spring:message code="aca.Clave"/></th>
			<th><spring:message code="aca.Carga"/></th>
			<th>Period</th>
			<th>Begin Enrollment</th>
			<th>End Enrollment</th>
			<th><spring:message code="aca.Estado"/></th>
		</tr>	
		<tr><td colspan="4"><b><spring:message code="aca.Seleccionar"/>:</b></td></tr>		
<%
	String cargaSelected = "";
	for( Carga carga : lisCarga){
		if (cargas.contains(carga.getCargaId()))
			cargaSelected = "checked";
		else
			cargaSelected = "";
		
		String inicioInscripcion 	= "0";
		if (mapaPrimerDia.containsKey(carga.getCargaId())){
			inicioInscripcion = mapaPrimerDia.get(carga.getCargaId());
		}
		
		String finInscripcion 		= "0";
		if (mapaUltimoDia.containsKey(carga.getCargaId())){
			finInscripcion = mapaUltimoDia.get(carga.getCargaId());
		}
		
		boolean esActiva 			= false;
		for( Carga cargaActiva : lisActivas){
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
		<tr><td colspan="4"><input class="btn btn-primary" type="submit" value="Save" onclick="Mostrar();"/></td></tr>
	</table>	
	</form>	
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script type="text/javascript">		
		jQuery('#Fecha').datepicker();
</script>
</div>
