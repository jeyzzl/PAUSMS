<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.carga.spring.Carga"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>


<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
<script type="text/javascript">
	
	function Mostrar(){					
		document.forma.Accion.value="1";
		document.forma.submit();
	}

</script>
<%  
	String fInscripcion					= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
	String cargas						= (String)session.getAttribute("cargas") == null?"":session.getAttribute("cargas").toString();
	String accion 						= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	List<Carga> lisCarga		= (List<Carga>) request.getAttribute("lisCarga");
	List<Carga> lisActivas		= (List<Carga>) request.getAttribute("lisActivas");
	
	HashMap<String,String> mapaPrimerInscripcion 	=(HashMap<String,String>) request.getAttribute("mapaPrimerInscripcion");
	HashMap<String,String> mapaUltimaInscripcion 	=(HashMap<String,String>) request.getAttribute("mapaUltimaInscripcion");
	
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
	<h1>Elegir Cargas</h1>
	<form id="forma" name="forma" action="cargas" method="post">
	<input name="Accion" type="hidden">
	<div class="alert alert-info">
		<a class="btn btn-primary btn-small" href="menu"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		Fecha:
		<input type="text" id="Fecha" name="Fecha" class="input" style="margin:0;" maxlength="10" data-date-format="dd/mm/yyyy" value="<%=fInscripcion %>" />&nbsp;
		<a class="btn btn-primary btn-small" onclick="javascript:document.forma.submit();"><i class="fas fa-sync-alt"></i></a>
	</div>	
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	
		<tr>
			<th>
				Elegir
				<a onclick="jQuery('.checkboxCarga').prop('checked',true)" class="btn btn-primary"><spring:message code='aca.Todos'/></a>&nbsp;
				<a onclick="jQuery('.checkboxCarga').prop('checked', false)" class="btn btn-primary"><spring:message code='aca.Ninguno'/></a>	
			</th>
			<th><spring:message code="aca.Clave"/></th>
			<th><spring:message code="aca.Carga"/></th>
			<th>Periodo</th>
			<th>Insc. inical</th>
			<th>Insc. Final</th>
			<th><spring:message code="aca.Estado"/></th>
		</tr>	
	</thead>
		<tr><td colspan="4"><b><spring:message code="aca.Seleccionar"/>:</b></td></tr>		
<%
	String cargaSelected = "";
	for( Carga carga : lisCarga){
		if (cargas.contains(carga.getCargaId()))
			cargaSelected = "checked";
		else
			cargaSelected = "";
		
		String inicioInscripcion = "-";
		String finInscripcion = "-";
		
		if(mapaPrimerInscripcion.containsKey(carga.getCargaId()) ){
			inicioInscripcion = mapaPrimerInscripcion.get(carga.getCargaId());
		}
		
		if(mapaUltimaInscripcion.containsKey(carga.getCargaId()) ){
			finInscripcion = mapaUltimaInscripcion.get(carga.getCargaId());
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
			<td><%=esActiva?"Activa":"Inactiva"%></td>
		</tr>
<%	} %>
		<tr><td colspan="4"><input class="btn btn-primary" type="submit" value="Grabar" onclick="Mostrar();"/></td></tr>
	</table>
	
	</form>
	
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script type="text/javascript">		
		jQuery('#Fecha').datepicker();
</script>
</div>