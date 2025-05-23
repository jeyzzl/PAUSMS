
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.emp.spring.EmpTipoPago"%>
<%@page import="aca.emp.spring.EmpRango"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String estado 		= request.getParameter("Estado")==null?"S":request.getParameter("Estado");
	String fecha 		= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
	String cargaId 		= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	String tipo 		= request.getParameter("Tipo")==null?"T":request.getParameter("Tipo");
	
	List<aca.emp.spring.EmpHoras> lisHoras		= (List<aca.emp.spring.EmpHoras>)request.getAttribute("listaHoras");
	HashMap<String,String> mapMaestros			= (HashMap<String,String>)request.getAttribute("mapaMaestros");
	HashMap<String,String> mapMaterias			= (HashMap<String,String>)request.getAttribute("mapaMaterias");
	HashMap<String,CatFacultad> mapFacultades	= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapCarreras		= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");	
	HashMap<String,EmpTipoPago> mapaPagos 		= (HashMap<String,EmpTipoPago>)request.getAttribute("mapaPagos");
	HashMap<String, EmpRango> mapaRangos		= (HashMap<String, EmpRango>)request.getAttribute("mapaRangos");
	HashMap<String, String> mapaRangosEmp		= (HashMap<String, String>)request.getAttribute("mapaRangosEmp");
	
	java.text.DecimalFormat frmDecimal		= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8" />	
	<meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8">	
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>	
	<link rel="stylesheet" href="../../js/tablesorter/themes/blue/style.css"/>	
</head>

<div class="container-fluid">
	<h1>Reporte de Maestros por horas</h1>
	<form name="frmReporte" action="reporte" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" class="btn btn-primary">Regresar</a>&nbsp;&nbsp;
		Estado:&nbsp;
		<select name="Estado" id="Estado" class="form-select" style="width:150px">
			<option value="S" <%=estado.equals("S")?"selected":""%>>Solicitud</option>
			<option value="A" <%=estado.equals("A")?"selected":""%>>Autorizada</option>
			<option value="N" <%=estado.equals("N")?"selected":""%>>Nómina</option>
		</select>
		&nbsp;&nbsp;
		Fecha:&nbsp;<input type="text" name="Fecha" id="Fecha" size="12" class="form-control" style="width:150px" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=fecha%>" />&nbsp;&nbsp;
		Carga:&nbsp;<input type="text" name="CargaId" id="CargaId" class="form-control" style="width:150px" size="12" maxlength="10" value="<%=cargaId%>" />
		&nbsp;&nbsp;
		Tipo:&nbsp;
		<select name="Tipo" class="form-select" style="width:150px">
			<option value="T" <%= tipo.equals("V")?"selected":""%>>Todos</option>
			<option value="V" <%= tipo.equals("V")?"selected":""%>>Verano</option>
			<option value="O" <%= tipo.equals("O")?"selected":""%>>Otoño</option>
			<option value="I" <%= tipo.equals("I")?"selected":""%>>Invierno</option>
			<option value="P" <%= tipo.equals("P")?"selected":""%>>Primavera</option>
		</select>
		&nbsp;&nbsp;
		<a class="btn btn-primary" href="javascript:Refrescar()"><i class="fas fa-sync-alt"></i></a>	
	</div>
	<div class="alert alert-info d-flex align-items-center">
		Filtrar:&nbsp; <input type="text" class="form-control" style="width:150px" placeholder="Buscar..." id="buscar">
		&nbsp;&nbsp;
		<a href="javascript:tableToExcel('table', 'horas-')" style="float:center;" class="btn btn-success"><i class="icon-white icon-arrow-down"></i> Excel</a>		
	</div>
	</form>
	<table class="table table-striped table-bordered" id="table">
		<thead>
		<tr class="table-info">			
			<th width="3%" class="center">Estado</th>
			<th width="3%" class="center"><spring:message code="aca.Numero"/></th>
			<th width="5%">Contrato</th>
			<th width="5%" class="center">Fecha</th>			
			<th width="5%">Carga</th>	
			<th width="5%">Facultad</th>
			<th width="5%">Carrera</th>
			<th width="5%">Codigo</th>
			<th width="15%">Maestro</th>
			<th width="15%">Rango</th>
			<th width="15%">Materia</th>
			<th width="15%">Otra Materia</th>
			<th width="3%">Tipo</th>
			<th width="3%">Pago</th>
			<th width="5%" class="center">F. Inicio</th>
			<th width="5%" class="center">F. Fin</th>
			<th width="3%" class="right">F.S.</th>
			<th width="3%" class="right">Sem.</th>
			<th width="5%" class="right">Tot/hrs.</th>
			<th width="5%" class="right">Precio</th>			
			<th width="5%" class="right">Tot/Per.</th>			
		</tr>
		</thead>
		<tbody>
<%
	int totHoras 	= 0;	
	float totPer 	= 0;
	int row=0;
	for (aca.emp.spring.EmpHoras horas : lisHoras){
		row++;
		String nombreMaestro = "-";
		if (mapMaestros.containsKey(horas.getCodigoPersonal())){
			nombreMaestro = mapMaestros.get(horas.getCodigoPersonal());
		}
		
		String nombreMateria = "-";
		if (mapMaterias.containsKey(horas.getCursoId())){
			nombreMateria = mapMaterias.get(horas.getCursoId());
		}
		
		String facultadId		= "0";
		String facultadNombre 	= "-";
		String carreraNombre 	= "-";
		if (mapCarreras.containsKey(horas.getCarreraId())){
			carreraNombre 		= mapCarreras.get(horas.getCarreraId()).getNombreCorto();
			facultadId 			= mapCarreras.get(horas.getCarreraId()).getFacultadId();
			if (mapFacultades.containsKey(facultadId)){
				facultadNombre 		= mapFacultades.get(facultadId).getNombreCorto();
			}
		}
		
		float totHora 		= Float.valueOf(horas.getFrecuencia()) * Float.valueOf(horas.getSemanas());
		float totSemana 	= Float.valueOf(horas.getPrecio()) * Float.valueOf(horas.getFrecuencia());
		float totPeriodo 	= totSemana * Float.valueOf(horas.getSemanas());
		totHoras 	+= totHora;
		totPer 		+= totPeriodo;
		
		String tipoPago = "*";
		if (mapaPagos.containsKey(horas.getTipoPagoId())){
			tipoPago = mapaPagos.get(horas.getTipoPagoId()).getCorto();
		}
		
		String nombreRango 		= "-";
		
		if (mapaRangosEmp.containsKey(horas.getCodigoPersonal()+horas.getCargaId())){
			String rangoId = mapaRangosEmp.get(horas.getCodigoPersonal()+horas.getCargaId());
			if (mapaRangos.containsKey(rangoId)){
				nombreRango 	= mapaRangos.get(rangoId).getRangoNombre();
			}		
		}	
%>		
		<tr>			
			<td width="3%" class="center">			
			<%= horas.getEstado() %>
			</td>
			<td width="3%" class="center"><%=row%></td>
			<td width="5%"><%= horas.getContratoId()%></td>
			<td width="5%" class="center"><%= horas.getFecha() %></td>
			<td width="5%"><%= horas.getCargaId() %></td>
			<td width="5%"><%= facultadNombre %></td>
			<td width="5%"><%= carreraNombre %></td>
			<td width="5%"><%= horas.getCodigoPersonal() %></td>
			<td width="15%"><%= nombreMaestro%></td>
			<td width="15%"><%= nombreRango%></td>
			<td width="15%"><%= nombreMateria %></td>			
			<td width="15%"><%= horas.getMateria() %></td>
			<td width="3%" class="center"><%= horas.getTipo() %></td>
			<td width="3%" class="center"><%=tipoPago%></td>
			<td width="5%" class="center"><%= horas.getFechaIni() %></td>			
			<td width="5%" class="center"><%= horas.getFechaFin() %></td>
			<td width="3%" class="right"><%= horas.getFrecuencia() %></td>	
			<td width="3%" class="right"><%= horas.getSemanas() %></td>
			<td width="5%" class="right"><%= frmDecimal.format(totHora) %></td>
			<td width="5%" class="right"><%= horas.getPrecio() %></td>			
			<td width="5%" class="right"><%= frmDecimal.format(totPeriodo) %></td>
		</tr>
<%		
	}
%>	
		</tbody>
		<tfoot>
		<tr>			
			<th class="right" COLSPAN="18"><b>T O T A L E S</b></th>
			<th class="right"><b><%= totHoras %></b></th>
			<th class="right"><b>&nbsp;</b></th>
			<th class="right"><b><%= frmDecimal.format(totPer) %></b></th>	
		</tr>
		</tfoot>
	</table>	
</div>

<script src="../../js/tablesorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="../../js/search.js"></script>
<script type="text/javascript" src="../../js/tableToExcel/tableToExcel.js" charset="UTF-8"></script>
<script type="text/javascript">
	function Refrescar( ){	
		document.frmReporte.submit();
	}
	
	function exportData(tabla){
	    var blob = new Blob([document.getElementById(tabla).innerHTML], {
	        type: "text/plain;charset=utf-8;"
	    });
	    saveAs(blob, "horas.xls");
	}   
	    
	jQuery('#Fecha').datepicker();	
	jQuery('#table').tablesorter();
	jQuery('#buscar').focus().search({table:jQuery("#table")});	
</script>