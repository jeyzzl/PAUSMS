<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.emp.spring.EmpHorasPres"%>
<%@page import="aca.financiero.spring.ContCcosto"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String periodoId					= (String) request.getAttribute("periodoId");
	String cargaId						= (String) request.getAttribute("cargaId");
	
	List<CatPeriodo> lisPeriodos 		= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 				= (List<Carga>) request.getAttribute("lisCargas");	
	List<EmpHorasPres> lisPresupuestos	= (List<EmpHorasPres>) request.getAttribute("lisPresupuestos");
	
	HashMap<String, CatFacultad> mapFacultades 	= (HashMap<String, CatFacultad>) request.getAttribute("mapFacultades");
	HashMap<String, CatCarrera> mapCarreras 	= (HashMap<String, CatCarrera>) request.getAttribute("mapCarreras");
	HashMap<String, ContCcosto> mapDeptos 		= (HashMap<String, ContCcosto>) request.getAttribute("mapDeptos");
	HashMap<String, String> mapGastos 			= (HashMap<String, String>) request.getAttribute("mapGastos");
	
	boolean encuentraCarga			= false;	
%>		
<div class="container-fluid">
	<h1>Periodos/facultades y carreras</h1>
	<form name="forma" action="lista" method="post">
	<div class="alert alert-info d-flex align-items-center"">
		<a class="btn btn-primary" href="agregar?CargaId=<%=cargaId%>"><i class="icon-white icon-plus"></i> <spring:message code='aca.Añadir'/></a>&nbsp;&nbsp;
		<b><spring:message code="aca.Periodo"/>: &nbsp;</b>
		<select onchange="javascript:document.forma.submit();" name="PeriodoId" class="form-select" style="width:140px">
	<%	for(CatPeriodo periodo : lisPeriodos){%>
		<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
	<%	}%>
    	</select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<b><spring:message code="aca.Carga"/>: &nbsp;</b>
		<select onchange='javascript:document.forma.submit();' name="CargaId" class="form-select" style="width:350px">
			<option <%=cargaId.equals("000000")?" Selected":""%> value="000000">[000000] Elige una carga</option>
	<%	for(Carga carga : lisCargas){
			if (carga.getCargaId().equals(cargaId)) encuentraCarga = true;
	%>
			<option <%if(cargaId.equals(carga.getCargaId()))out.print(" Selected ");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
	<%	}%>
		</select>		
		&nbsp;&nbsp;
	</div>	
	</form>
	<table class="table table-bordered table-striped" id="table">
	<tr class="table-info">
		<th width="5%">Op.</th>
		<th width="5%">#</th>
		<th width="10%">Departamento</th>
		<th width="35%">Nombre</th>
		<th width="5%">Año</th>
		<th width="7%" class="text-end">Saldo Ant.</th>
		<th width="7%" class="text-end">(+)Importe</th>
		<th width="7%" class="text-end">(-)Solicitud</th>
		<th width="7%" class="text-end">(-)Autorizado</th>
		<th width="7%" class="text-end">(-)Nomina</th>
		<th width="7%" class="text-end">(=)Saldo</th>
	</tr>
<%
	//System.out.println("Datos:"+encuentraCarga+":"+cargaId);
	int row 			= 0;
	double totSaldoAnt 	= 0;
	double totPres		= 0;
	double totSaldo		= 0;
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
		<td>
			<a class="fas fa-trash-alt" href="javascript:borrar('<%=pres.getCargaId()%>', '<%=pres.getDepartamentoId()%>')"></a>&nbsp;
			<a href="agregar?CargaId=<%=pres.getCargaId()%>&DepartamentoId=<%=pres.getDepartamentoId()%>" title="Modificar"><i class="fas fa-pencil-alt"></i></a>
		</td>
		<td><%=row%></td>		
		<td><%=pres.getDepartamentoId()%></td>
		<td><%=nombreDept%></td>
		<td><%=pres.getYear()%></td>
		<td class="text-end"><%=formato.format(Double.parseDouble(pres.getSaldoAnt()))%></td>
		<td class="text-end"><%=formato.format(Double.parseDouble(pres.getImporte()))%></td>
		<td class="text-end"><%=formato.format(Double.parseDouble(gastoSol))%></td>
		<td class="text-end"><%=formato.format(Double.parseDouble(gastoAut))%></td>
		<td class="text-end"><%=formato.format(Double.parseDouble(gastoNom))%></td>
		<td class="text-end"><%=formato.format(saldo)%></td>
	</tr>
<%		
	} 
%>	
	<tr>
		<th colspan="5">Total</th>
		<th class="text-end"><%=formato.format(totSaldoAnt)%></th>
		<th class="text-end"><%=formato.format(totPres)%></th>
		<th class="text-end"><%=formato.format(totSol)%></th>
		<th class="text-end"><%=formato.format(totAut)%></th>
		<th class="text-end"><%=formato.format(totNom)%></th>
		<th class="text-end"><%=formato.format(totSaldo)%></th>
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
