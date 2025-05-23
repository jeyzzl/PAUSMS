<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.emp.spring.EmpContrato"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String yearActual		= aca.util.Fecha.getHoyReversa().substring(0,4);
	String year 			= request.getParameter("Year")==null?yearActual:request.getParameter("Year");
	String codigoEmpleado	= (String)request.getAttribute("codigoEmpleado");

	List<EmpContrato> lisContratos			= (List<EmpContrato>)request.getAttribute("lisContratos");
	HashMap<String, String> mapaMaestros 	= (HashMap<String, String>)request.getAttribute("mapaMaestros");
	java.text.DecimalFormat frmDecimal		= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">		
	<meta http-equiv="content-type" content="application/vnd.ms-excel; charset=UTF-8">	
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>	
	<link rel="stylesheet" href="../../js/tablesorter/themes/blue/style.css"/>	
</head>
<div class="container-fluid">
	<h2>Lista de contratos</h2>
	<form name="frmReporte" action="contratos" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" class="btn btn-primary">Regresar</a>&nbsp;&nbsp;
		Año:&nbsp;
		<select name="Year" id="Year" class="form-select" style="width:100px">
<%	
		for(int i=Integer.parseInt(yearActual); i >= Integer.parseInt(yearActual)-4; i--){ 
%>
			<option value="<%=String.valueOf(i)%>" <%=year.equals(String.valueOf(i))?"selected":""%>><%=i%></option>
<%		} %>						
		</select>	
		&nbsp;&nbsp;
		<a class="btn btn-primary" href="javascript:Refrescar()"><i class="fas fa-sync-alt"></i></a>	
		&nbsp;&nbsp;
		<input type="text" class="form-control" style="width:160px;" placeholder="Buscar..." id="buscar">
	</div>	
	</form>
	<table class="table table-striped table-bordered" id="table">
	<thead>
	<tr class="table-info">			
		<th width="3%" class="center">#</th>
		<th width="7%" class="center">Contrato</th>
		<th width="5%" class="center">Firma</th>
		<th width="5%" class="center">Nómina</th>
		<th width="20%">Empleado</th>
		<th width="8%" class="center">Institución</th>
		<th width="5%" class="center">F.Inicio</th>			
		<th width="5%" class="center">F.Final</th>		
		<th width="30%" class="text-end">Comentario</th>
		<th width="5%" class="text-end">Estado</th>
		<th width="7%" class="text-end">Precio</th>
	</tr>
	</thead>
	<tbody>
<%
	int row=0;
	for(EmpContrato contrato : lisContratos){
		String maestro = "-";
		if (mapaMaestros.containsKey(contrato.getCodigoPersonal())){
			maestro = mapaMaestros.get(contrato.getCodigoPersonal()); 
		}
%>
	<tr>			
		<td width="3%" class="center"><%=++row%></td>
		<td width="7%" class="center"><%=contrato.getContratoId()%></td>	
		<td width="7%" class="center">
<%		if(codigoEmpleado.equals("9800345") || codigoEmpleado.equals("9800308")){
			if(contrato.getFirma().equals("S")){%>
				<a class="btn btn-success" onclick="javascript:firmar('N','<%=contrato.getContratoId()%>');">Si</a>
<%			}else{%>
				<a class="btn btn-danger" onclick="javascript:firmar('S','<%=contrato.getContratoId()%>');">No</a>
<%			}
		}else{%>
			<%=contrato.getFirma().equals("S")?"SI":"NO"%>
<%		}%>
		</td>
		<td width="7%" class="center"><%=contrato.getCodigoPersonal()%></td>
		<td width="20%"><%=maestro%></td>
		<td width="8%" class="center"><%=contrato.getInstitucion()%></td>
		<td width="5%" class="center"><%=contrato.getFechaIni()%></td>			
		<td width="5%" class="center"><%=contrato.getFechaFin()%></td>		
		<td width="31%" class="text-end"><%=contrato.getComentario()%></td>
		<td width="7%" class="text-end"><%=contrato.getEstado()%></td>
		<td width="7%" class="text-end"><%=contrato.getCosto()%></td>					
	</tr>
<%	}%>	
	</tbody>
	</table>	
</div>

<script src="../../js/tablesorter/jquery.tablesorter.js"></script>
<script type="text/javascript" src="../../js/search.js"></script>
<script type="text/javascript" src="../../js/tableToExcel/tableToExcel.js" charset="UTF-8"></script>
<script type="text/javascript">
	function firmar(firmar,contrato){
		if (confirm("¿Estás seguro de cambiar la firma? : "+contrato)){
			document.location.href="firmar?Firma="+firmar+"&ContratoId="+contrato;
		}
	}
	
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