<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
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
	String codigoPersonal				= (String) session.getAttribute("codigoPersonal");	  
	String fInscripcion					= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
	String cargas						= (String)session.getAttribute("cargas") == null?"":session.getAttribute("cargas").toString();
	String accion 						= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	ArrayList<aca.carga.Carga> lisCarga		= CargaU.getListPorFecha(conEnoc, fInscripcion, "ORDER BY CARGA_ID");
	ArrayList<aca.carga.Carga> lisActivas	= CargaU.getListCargasActivas(conEnoc, " ORDER BY CARGA_ID");
	
	if (accion.equals("1")){
		
		cargas = "";
		for(int i = 0; i < lisCarga.size(); i++){
			aca.carga.Carga carga = (aca.carga.Carga) lisCarga.get(i);
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
		<a class="btn btn-primary btn-small" href="ventas"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		Fecha:
		<input type="text" id="Fecha" name="Fecha" class="input" style="margin:0;" maxlength="10" data-date-format="dd/mm/yyyy" value="<%=fInscripcion %>" />&nbsp;
		<a class="btn btn-primary btn-small" onclick="javascript:document.forma.submit();"><i class="fas fa-sync-alt"></i></a>
	</div>	
	<table style="width:50%" class="table table-condensed">
		<tr>
			<th>
				Elegir
				<a onclick="jQuery('.checkboxCarga').prop('checked',true)" class="btn btn-sm"><spring:message code='aca.Todos'/></a>&nbsp;
				<a onclick="jQuery('.checkboxCarga').prop('checked', false)" class="btn btn-sm"><spring:message code='aca.Ninguno'/></a>	
			</th>
			<th><spring:message code="aca.Clave"/></th>
			<th><spring:message code="aca.Carga"/></th>
			<th>Periodo</th>
			<th>Insc. inical</th>
			<th>Insc. Final</th>
			<th><spring:message code="aca.Estado"/></th>
		</tr>	
		<tr><td colspan="4"><b><spring:message code="aca.Seleccionar"/>:</b></td></tr>	
		
<%
	String cargaSelected = "";
	for( aca.carga.Carga carga : lisCarga){
		if (cargas.contains(carga.getCargaId()))
			cargaSelected = "checked";
		else
			cargaSelected = "";
		
		String inicioInscripcion 	= aca.vista.Estadistica.getPrimerDiaInscrpcion(conEnoc, carga.getCargaId());
		String finInscripcion 		= aca.vista.Estadistica.getPrimerDiaInscrpcion(conEnoc, carga.getCargaId());
		
		boolean esActiva 			= false;
		for( aca.carga.Carga cargaActiva : lisActivas){
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
		<tr><td colspan="7"><input class="btn btn-primary" type="submit" value="Grabar" onclick="Mostrar();"/></td></tr>
	</table>
	
	</form>
	
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script type="text/javascript">		
		jQuery('#Fecha').datepicker();
</script>
</div>
<%@ include file="../../cierra_enoc.jsp"%>