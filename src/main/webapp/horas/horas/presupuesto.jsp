<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.emp.spring.EmpHorasPres"%>
<%@page import="aca.financiero.spring.ContCcosto"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	java.text.DecimalFormat formato 	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");	
	String cargaId						= request.getParameter("CargaId")==null?"000000":request.getParameter("CargaId");
	String tipo							= (String) request.getAttribute("tipo");
	
	List<EmpHorasPres> lisPresupuestos	= (List<EmpHorasPres>) request.getAttribute("lisPresupuestos");
	
	HashMap<String, CatFacultad> mapFacultades 	= (HashMap<String, CatFacultad>) request.getAttribute("mapFacultades");
	HashMap<String, CatCarrera> mapCarreras 	= (HashMap<String, CatCarrera>) request.getAttribute("mapCarreras");
	HashMap<String, ContCcosto> mapDeptos 		= (HashMap<String, ContCcosto>) request.getAttribute("mapDeptos");
	HashMap<String, String> mapGastos 			= (HashMap<String, String>) request.getAttribute("mapGastos");
	
	boolean encuentraCarga			= false;	
%>		
<div class="container-fluid">
	<h2>Presupuesto de Maestros por hora<small class="text-muted fs-5"> (<%=cargaId%>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="elegir?Tipo=<%=tipo%>">Regresar</a>
	</div>
	<table class="table table-sm table-striped" id="table">
	<tr class="table-info">
		<th width="5%">#</th>
		<th width="10%">Departamento</th>
		<th width="38%">Nombre</th>
		<th width="5%">Año</th>
		<th width="7%" class="right">Saldo Ant.</th>
		<th width="7%" class="right">(+)Importe</th>
		<th width="7%" class="right">(-)Solicitud</th>
		<th width="7%" class="right">(-)Autorizado</th>
		<th width="7%" class="right">(-)Nomina</th>
		<th width="7%" class="right">(=)Saldo</th>
	</tr>
<%	
	int row = 0;
	double totSaldoAnt	= 0;
	double totSaldo 	= 0;
	double totPres		= 0;
	double totSol		= 0;
	double totAut		= 0;
	double totNom		= 0;
	
	for (EmpHorasPres pres : lisPresupuestos){
		row++;
		String nombreDept = "-";
		if(mapDeptos.containsKey(pres.getDepartamentoId())){
			nombreDept = mapDeptos.get(pres.getDepartamentoId()).getNombre();
		}
		
		String gastoSol = "0";
		if (mapGastos.containsKey(pres.getDepartamentoId()+"S")){
			gastoSol = mapGastos.get(pres.getDepartamentoId()+"S");
		}
		
		String gastoAut = "0";
		if (mapGastos.containsKey(pres.getDepartamentoId()+"A")){
			gastoAut = mapGastos.get(pres.getDepartamentoId()+"A");
		}
		
		String gastoNom = "0";
		if (mapGastos.containsKey(pres.getDepartamentoId()+"N")){
			gastoNom = mapGastos.get(pres.getDepartamentoId()+"N");
		}
		
		totSaldoAnt 	+= Double.parseDouble(pres.getSaldoAnt());
		totPres			+= Double.parseDouble(pres.getImporte());
		totSol			+= Double.parseDouble(gastoSol);
		totAut			+= Double.parseDouble(gastoAut);
		totNom			+= Double.parseDouble(gastoNom);
		double gasto 	= Double.parseDouble(gastoSol) + Double.parseDouble(gastoAut) + Double.parseDouble(gastoNom);
		double saldo 	= Double.parseDouble(pres.getSaldoAnt()) + Double.parseDouble(pres.getImporte()) - gasto;
		totSaldo 		+= saldo;
%>
	<tr>
		<td><%=row%></td>		
		<td><%=pres.getDepartamentoId()%></td>
		<td><%=nombreDept%></td>
		<td><%=pres.getYear()%></td>
		<td class="right"><%=formato.format(Double.parseDouble(pres.getSaldoAnt()))%></td>
		<td class="right"><%=formato.format(Double.parseDouble(pres.getImporte()))%></td>
		<td class="right"><%=formato.format(Double.parseDouble(gastoSol))%></td>
		<td class="right"><%=formato.format(Double.parseDouble(gastoAut))%></td>
		<td class="right"><%=formato.format(Double.parseDouble(gastoNom))%></td>
		<td class="right"><%=formato.format(saldo)%></td>
	</tr>
<%		
	} 
%>	
	<tr>
		<th colspan="4">Total</th>
		<th class="right"><%=formato.format(totSaldoAnt)%></th>
		<th class="right"><%=formato.format(totPres)%></th>
		<th class="right"><%=formato.format(totSol)%></th>
		<th class="right"><%=formato.format(totAut)%></th>
		<th class="right"><%=formato.format(totNom)%></th>
		<th class="right"><%=formato.format(totSaldo)%></th>
	</tr>	
	</table>		
</div>
<script>
	function borrar(cargaId, departamentoId){
		if (confirm("¿Estas seguro de borrar este registro?")){
			location.href="borrar?CargaId="+cargaId+"&DepartamentoId="+departamentoId;
		}	
	}	
</script>
