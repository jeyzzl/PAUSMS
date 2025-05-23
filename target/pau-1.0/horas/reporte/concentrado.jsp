
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.vista.spring.Maestros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String estado 		= request.getParameter("Estado")==null?"S":request.getParameter("Estado");
	String tipo 		= request.getParameter("Tipo")==null?"O":request.getParameter("Tipo");
	String cargas 		= request.getParameter("Cargas")==null?"0":request.getParameter("Cargas");
	
	List<CatFacultad> lisFacultades			= (List<CatFacultad>)request.getAttribute("lisFacultades");
	List<Maestros> lisMaestros				= (List<Maestros>)request.getAttribute("lisMaestros");
	HashMap<String,String> mapaGastos		= (HashMap<String,String>)request.getAttribute("mapaGastos");
	HashMap<String,String> mapaMaterias		= (HashMap<String,String>)request.getAttribute("mapaMaterias");
	HashMap<String,String> mapaHoras		= (HashMap<String,String>)request.getAttribute("mapaHoras");
	HashMap<String,String> mapaHorasSemana	= (HashMap<String,String>)request.getAttribute("mapaHorasSemana");
	
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
	<form name="frmReporte" action="concentrado" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" class="btn btn-primary">Regresar</a>&nbsp;&nbsp;
		Estado:&nbsp;
		<select name="Estado" id="Estado" class="form-select" style="width:120px">			
			<option value="S" <%=estado.equals("S")?"selected":""%>>Solicitud</option>
			<option value="A" <%=estado.equals("A")?"selected":""%>>Autorizada</option>
			<option value="N" <%=estado.equals("N")?"selected":""%>>Nómina</option>
		</select>
		&nbsp;&nbsp;
		Tipo:&nbsp;
		<select name="Tipo" id="Tipo" class="form-select" style="width:120px">			
			<option value="O" <%=tipo.equals("O")?"selected":""%>>Otoño</option>
			<option value="I" <%=tipo.equals("I")?"selected":""%>>Invierno</option>
			<option value="P" <%=tipo.equals("P")?"selected":""%>>Primavera</option>
			<option value="V" <%=tipo.equals("V")?"selected":""%>>Verano</option>
		</select>
		&nbsp;&nbsp;
		Cargas:&nbsp; <input type="text" name="Cargas" id="Cargas" class="form-control" style="width:120px" size="25" maxlength="50" value="<%=cargas%>"/>
		&nbsp;&nbsp;
		<a class="btn btn-primary" href="javascript:Refrescar()"><i class="fas fa-sync-alt"></i></a>		
	</div>
	<div class="alert alert-info  d-flex align-items-center">
		Filtrar: <input type="text" class="form-control" style="width:120px" placeholder="Buscar..." id="buscar">
		&nbsp;&nbsp;
	</div>
	</form>
	<table class="table table-bordered table-striped" id="table">
<%
	for (CatFacultad facultad : lisFacultades){		
%>	
		<thead>
		<tr>			
			<th colspan="8"><%=facultad.getFacultadId()%>:<%=facultad.getNombreFacultad()%></th>
		</tr>
		<tr>			
			<th width="5%"><spring:message code="aca.Numero"/></th>				
			<th width="10%">Código</th>	
			<th width="40%">Maestro</th>
			<th width="5%" class="right">Materias</th>
			<th width="5%" class="right">Hr.Sem.</th>
			<th width="5%" class="right">Hr.Per.</th>
			<th width="5%" class="right">Precio</th>
			<th width="25%">&nbsp;</th>
		</tr>
		</thead>
		<tbody>
<%
		int num 				= 0;	
		float totHoras			= 0;
		float totHorasSemana	= 0;
		float totGastos 		= 0;
		for (Maestros maestro:lisMaestros){
			if (mapaMaterias.containsKey(maestro.getCodigoPersonal()+facultad.getFacultadId())){
		
				num++;
				String materias = "0";
				if (mapaMaterias.containsKey(maestro.getCodigoPersonal()+facultad.getFacultadId())){
					materias = mapaMaterias.get(maestro.getCodigoPersonal()+facultad.getFacultadId());
				}
				
				String gastos = "0";
				if (mapaGastos.containsKey(maestro.getCodigoPersonal()+facultad.getFacultadId())){
					gastos = mapaGastos.get(maestro.getCodigoPersonal()+facultad.getFacultadId());
				}
				
				String horas = "0";
				if (mapaHoras.containsKey(maestro.getCodigoPersonal()+facultad.getFacultadId())){
					horas = mapaHoras.get(maestro.getCodigoPersonal()+facultad.getFacultadId());
				}
				
				String horasSemana = "0";
				if (mapaHorasSemana.containsKey(maestro.getCodigoPersonal()+facultad.getFacultadId())){
					horasSemana = mapaHorasSemana.get(maestro.getCodigoPersonal()+facultad.getFacultadId());
				}				
				
				totHoras 		+= Float.valueOf(horas);
				totHorasSemana 	+= Float.valueOf(horasSemana);
				totGastos 		+= Float.valueOf(gastos);		
%>	
		<tr>
			<td width="3%"><%=num%></td>						
			<td width="5%"><%=maestro.getCodigoPersonal()%></td>	
			<td width="5%"><%=maestro.getApellidoPaterno()%> <%=maestro.getApellidoMaterno()%>, <%=maestro.getNombre()%></td>			
			<td width="5%" class="right"><%=materias%></td>
			<td width="5%" class="right"><%=frmDecimal.format(Double.valueOf(horasSemana))%></td>
			<td width="5%" class="right"><%=frmDecimal.format(Double.valueOf(horas))%></td>
			<td width="5%" class="right"><%=frmDecimal.format(Double.valueOf(gastos))%></td>				
		</tr>
<%
			}
		}
%>
		</tbody>
		<tr>
			<th width="2%" height="22" colspan="4">Totales</th>
			<th width="9%" style="text-align:right"><%=frmDecimal.format(Double.valueOf(totHorasSemana))%></th>
			<th width="9%" style="text-align:right"><%=frmDecimal.format(Double.valueOf(totHoras))%></th>
		    <th width="9%" style="text-align:right"><%=frmDecimal.format(Double.valueOf(totGastos))%></th>
		    <th width="25%" style="text-align:right">&nbsp;</th>
	  	</tr>
<%	}%>	  	
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
	    
	jQuery('#table').tablesorter();
	jQuery('#buscar').focus().search({table:jQuery("#table")});	
</script>