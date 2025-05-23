<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.financiero.spring.FinComentario"%> 
<%@page import="aca.financiero.spring.FesCcobro"%>
<%@page import="aca.financiero.spring.ContSaldosRip"%>
<%@page import="aca.financiero.spring.ContConcepto"%>
<%@page import="aca.financiero.spring.ContMovimiento"%>
<%@page import="aca.financiero.spring.A3lAsalfldg"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.plan.spring.MapaPlan"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="menumov.jsp"%>

<style>
	span.muestra {
		display: none;
		width: 220px;
		background-color: gray;
		color: #fff;
		text-align: center;
		border-radius: 6px;
		padding: 5px 0;
		/*      Position the tooltip  */
		position: absolute;
		z-index: 1;
	}
</style>

<script type="text/javascript">
	function muestra(recive) {
		document.getElementById('muestra' + recive).style.position = 'absolute';
		document.getElementById('muestra' + recive).style.display = 'inline-block';
	}

	function oculta(recive) {
		document.getElementById('muestra' + recive).style.display = 'none';
	}
</script>

<%
	java.text.DecimalFormat getformato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String usuario 				= session.getAttribute("codigoEmpleado").toString();
	String matricula 			= session.getAttribute("codigoAlumno").toString();
	String cargaId 				= session.getAttribute("cargaId").toString();

	String year 				= request.getParameter("year")==null?aca.util.Fecha.getHoy().substring(6, 10):request.getParameter("year");
	String cuenta 				= request.getParameter("cuenta")==null?"TODO":request.getParameter("cuenta");
	String division 			= request.getParameter("division")==null?"":request.getParameter("division");
	
	boolean existeAlumno 		= (boolean) request.getAttribute("existeAlumno");
	double saldoAntOracle 		= (double) request.getAttribute("saldoAntOracle");
	double saldoAntSunPlus 		= (double) request.getAttribute("saldoAntSunPlus");
	double saldoFormal 			= (double) request.getAttribute("saldoFormal");
	double saldoIdiomas 		= (double) request.getAttribute("saldoIdiomas");
	double saldoCimum 			= (double) request.getAttribute("saldoCimum");
	double saldoCimumAllende	= (double) request.getAttribute("saldoCimumAllende");
	double saldoRemedial 		= (double) request.getAttribute("saldoRemedial");
	
	String nombreAlumno 		= (String) request.getAttribute("nombreAlumno");
	boolean tieneComentarios 	= (boolean) request.getAttribute("tieneComentarios");
	boolean tieneSaldoRip 		= (boolean) request.getAttribute("tieneSaldoRip");

	ContSaldosRip rip = (ContSaldosRip) request.getAttribute("rip");

	// Lista de comentarios del alumno
	List<FinComentario> lisComentarios 				= (List<FinComentario>) request.getAttribute("lisComentarios");

	// Lista de calculos
	List<FesCcobro> lisCalculos 					= (List<FesCcobro>) request.getAttribute("lisCalculos");

	// Lista de movimientos en Oracle
	List<ContMovimiento> lisMovOracle 				= (List<ContMovimiento>) request.getAttribute("lisMovOracle");

	// Lista de movimientos en SunPlus
	List<A3lAsalfldg> lisMovSunPlus 				= (List<A3lAsalfldg>) request.getAttribute("lisMovSunPlus");
	
	// Lista de movimientos en SunPlus
	List<String> lisAlumnoCuentas 					= (List<String>) request.getAttribute("lisAlumnoCuentas");

	// Lista de planes activos del alumno
	List<CargaAlumno> lisPlanes 					= (List<CargaAlumno>) request.getAttribute("lisPlanes");

	// map de conceptos de movimientos
	HashMap<String, ContConcepto> mapaConcepto 		= (HashMap<String, ContConcepto>) request.getAttribute("mapaConceptos");

	// map de conceptos de movimientos
	HashMap<String, String> mapaMaestros 			= (HashMap<String, String>) request.getAttribute("mapaMaestros");

	// map de codigos de la dimension FLAG
	HashMap<String, String> mapaCodigos 			= (HashMap<String, String>) request.getAttribute("mapaCodigos");
	
	HashMap<String,MapaPlan> mapaPlanes 			= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	
	java.util.HashMap<String, String> mapaCuentas 	= (HashMap<String, String>) request.getAttribute("mapaCuentas");

	String fechaHoy 		= aca.util.Fecha.getHoy();
	String ripStatus 		= "-";
	String ejercicioId 		= "x";
	String fechaInicio 		= "X";
	String fechaFinal 		= "X";

	double numCreditos 		= 0;
	double numCargos 		= 0;
	double numSaldo 		= 0;
	double saldoAnterior	= 0;

	String strCreditos 		= "";
	String strCargos		= "";
	String strSaldo 		= "";
	String strNaturaleza 	= "";
	String conceptoId 		= "";
	String libro 			= "";

	if (existeAlumno) {

		// Define la fecha de inicio de la consulta y el ejercicio contable
		fechaInicio = "01/01/" + fechaHoy.substring(6, 10);
		ejercicioId = "001-" + fechaHoy.substring(6, 10);
		if (!year.equals("")) {
			ejercicioId = "001-" + year;
			fechaInicio = "01/01/" + year;
		}
		
		// fechaFinal
		fechaFinal = year.equals("") ? fechaHoy : "31/12/" + year;

		numCreditos = 0;
		numCargos = 0;	
		
		//System.out.println("Datos1:"+year+":"+saldoAntOracle+":"+saldoAntSunPlus+":"+saldoFormal+":"+saldoIdiomas+":"+saldoCimum);
				
		if (Integer.parseInt(year) >= 2019){
			if (cuenta.equals("TODO")){
				saldoAnterior 	= saldoAntSunPlus;
			}else{
				if (cuenta.equals("SFORMA01")) saldoAnterior = saldoFormal; 
				if (cuenta.equals("SIDIOM01")) saldoAnterior = saldoIdiomas;
				if (cuenta.equals("SCIMUN01")) saldoAnterior = saldoCimum;
				if (cuenta.equals("SCIMUN02")) saldoAnterior = saldoCimumAllende;
				if (cuenta.equals("SREMEDIAL")) saldoAnterior = saldoRemedial;
			}
		}else{
			if (cuenta.equals("TODO")){
				saldoAnterior 	= saldoAntOracle;
				saldoFormal		= saldoAntOracle;
			}else{
				if (cuenta.equals("SFORMA01")) saldoAnterior = saldoFormal;
				if (cuenta.equals("SIDIOM01")) saldoAnterior = saldoIdiomas;
				if (cuenta.equals("SCIMUN01")) saldoAnterior = saldoCimum;
				if (cuenta.equals("SCIMUN02")) saldoAnterior = saldoCimumAllende;
				if (cuenta.equals("SREMEDIAL")) saldoAnterior = saldoRemedial;
			}
		}
		
		//System.out.println("Datos2:"+saldoAntOracle+":"+saldoAntSunPlus+":"+saldoFormal+":"+saldoIdiomas+":"+saldoCimum);

		strSaldo = getformato.format(saldoAnterior);

		double totalCargos = 0;
		double totalCreditos = 0;

		if (saldoAnterior >= 0) {
			totalCreditos 	= saldoAnterior;
			strCreditos 	= getformato.format(saldoAnterior);
			strCargos 		= "0";
			strNaturaleza 	= "C";
		} else {
			totalCargos = (saldoAnterior * -1);
			strCargos = getformato.format(saldoAnterior * -1);
			strCreditos = "0";
			strNaturaleza = "D";
		}

		int anio = Integer.parseInt(fechaHoy.substring(6, 10));
		int yearBack1 = anio - 1;
		int yearBack2 = anio - 2;
		int yearBack3 = anio - 3;
		int yearBack4 = anio - 4;
%>
<html>
<!-- <head> -->
<!-- 	<script> -->
<!--  		parent.tabs.document.body.style.backgroundColor = parent.contenidoP.document.bgColor; -->
<!-- 	</script> -->
<!-- </head> -->
<body>
<div class="container-fluid">
	<h1 class="mt-1">Alumno 
	<small class="text-muted"style="font-size:25px">
	( <%=matricula%> - <%=nombreAlumno%> )</small></h1>	
	<div class="alert alert-info">
		<p class="font20">Años:
	  	<button class=" font20 badge rounded-pill <%=year.equals(String.valueOf(yearBack1))?"bg-dark":"bg-primary"%>" onclick="location.href='financieromov?year=<%=yearBack1%>&division=<%=division%>'"><%=yearBack1%></button>
	  	<button class=" font20 badge rounded-pill <%=year.equals(String.valueOf(aca.util.Fecha.getHoy().substring(6, 10)))?"bg-dark":"bg-primary"%>" onclick="location.href='financieromov?division=<%=division%>'"><%=anio%></button>
	  	&nbsp;&nbsp;&nbsp;
	  	Cuentas:&nbsp;	  	 	  	
	  	<a class="badge rounded-pill <%=cuenta.equals("TODO")?"bg-dark":"bg-info"%>" href="financieromov?year=<%=year%>&division=<%=division%>&cuenta=TODO">Todo</a>&nbsp;
<%		if (mapaCuentas.containsKey("SFORMA01")){%>	  	
	  	<a class="badge rounded-pill <%=cuenta.equals("SFORMA01")?"bg-dark":"bg-info"%>" href="financieromov?year=<%=year%>&division=<%=division%>&cuenta=SFORMA01">
	  	Formal
	  	</a>&nbsp;
<%		} //-, <%=mapaCuentas.get("SFORMA01"), <%=mapaCuentas.get("SIDIOM01"), -<%=mapaCuentas.get("SCIMUN01")  %>	  	
<%		if (mapaCuentas.containsKey("SREMED01")){%>
	  	<a class="badge rounded-pill <%=cuenta.equals("SREMED01")?"bg-dark":"bg-info"%>" href="financieromov?year=<%=year%>&division=<%=division%>&cuenta=SREMED01">
	  	Remedial
	  	</a>&nbsp;
<%		}%>	  
<%		if (mapaCuentas.containsKey("SIDIOM01")){%>
	  	<a class="badge rounded-pill <%=cuenta.equals("SIDIOM01")?"bg-dark":"bg-info"%>" href="financieromov?year=<%=year%>&division=<%=division%>&cuenta=SIDIOM01">
	  	Idiomas</a>&nbsp;
<%		}%>
<%		if (mapaCuentas.containsKey("SCIMUN01")){%>
	  	<a class="badge rounded-pill <%=cuenta.equals("SCIMUN01")?"bg-dark":"bg-info"%>" href="financieromov?year=<%=year%>&division=<%=division%>&cuenta=SCIMUN01">
	  	CIMUM
	  	</a>&nbsp;
<%		}%>	  
<%		if (mapaCuentas.containsKey("SCIMUN02")){%>
	  	<a class="badge rounded-pill <%=cuenta.equals("SCIMUN02")?"bg-dark":"bg-info"%>" href="financieromov?year=<%=year%>&division=<%=division%>&cuenta=SCIMUN02">
	  	CIMUM-A
	  	</a>&nbsp;
<%		}%>	
	  	&nbsp;&nbsp;
	  	Cálculos: 
<%		for (FesCcobro calculo : lisCalculos) {
			if (calculo.getFecha().substring(0, 4).equals(year)) {
				if(calculo.getInscrito().equals("S")){%>
	  			<a class='badge rounded-pill bg-success' title="<%=calculo.getFecha()%>" href="https://virtual-um.um.edu.mx/financiero/calculoCobroReimpresion.html?txtMatricula=<%=calculo.getMatricula()%>&txtCarga=<%=calculo.getCargaId()%>&Bloque=<%=calculo.getBloque()%>" target='_blank'><%=calculo.getCargaId()%></a>
	  			&nbsp;
<%				}else{ %>
				<a class='badge rounded-pill bg-primary' title="<%=calculo.getFecha()%>" href="https://virtual-um.um.edu.mx/financiero/calculoCobroEstimado.html?txtCarga=<%=calculo.getCargaId()%>@<%=calculo.getBloque()%>@<%=calculo.getPlanId()%>&sltFormaPago=<%=calculo.getFormaPago()%>&txtMatricula=<%=calculo.getMatricula()%>" target='_blank'><%=calculo.getCargaId()%></a>
<%
				}
			}
		}%>		
		</p>
	</div>			
<%		
		if (tieneSaldoRip) {

			String mensaje = "-";
			if (rip.getNaturaleza().equals("D")) {
				mensaje = "¡ Atención ! Este alumno presenta un saldo en cuentas incobrables de $"
						+ rip.getSaldo() + ". Favor de pasar a la oficina de finanzas estudiantiles.";
			} else {
				mensaje = "¡ Atención ! Este alumno presenta un credito congelado en su cuenta de $"
						+ rip.getSaldo() + ". Favor de pasar a la oficina de finanzas estudiantiles.";
			}%>						
	<div class="alert alert-danger"><h3><%=mensaje%></h3></div>
<%		}%>		


	<table class="table table-condensed table-bordered estadoCuenta" style="width:98%">	 
	<thead>
		<tr>			
			<th width="11%" class="left"><font size="5" face="Arial"><spring:message code="aca.Fecha"/></font></th>
			<th width="50%" class="left"><font size="5" face="Arial"><spring:message code="aca.Descripcion"/></font></th>
			<th width="13%" class="text-right"><font size="5" face="Arial"><spring:message code="datosAlumno.portal.EdoCargos"/></font></th>
			<th width="13%" class="text-right"><font size="5" face="Arial"><spring:message code="aca.Creditos"/></font></th>		
			<th width="13%" class="text-right"><font size="5" face="Arial"><spring:message code="datosAlumno.portal.EdoSaldo"/></font></th>
		</tr>
	</thead>
	<tbody>
<%		double saldo = 0;

		if (saldoFormal != 0 && (cuenta.equals("TODO") || cuenta.equals("SFORMA01")) ){ 
			if (saldoFormal >= 0) {
				strNaturaleza = "C";
			} else {
				strNaturaleza = "D";
			}
			saldo = saldoFormal;
%>
	 	<tr> 
			<td class="descripcion" colspan="2" align="center">
				<p class="font18">
					<spring:message code="datosAlumno.portal.EdoSaldo"/> <spring:message code="datosAlumno.portal.EdoAnterior"/> Formal
				</p>
			</td>
			<td style="text-align:right;"><p class="font18"><%=saldoFormal>0?0:getformato.format(saldoFormal)%></p></td>
			<td style="text-align:right;"><p class="font18"><%=saldoFormal<0?0:getformato.format(saldoFormal)%></p></td>
			<td style="text-align:right;">
		    	<font size="3" face="Arial" <%if (saldo < 0) out.print("color='#FF0000'"); else out.print("color='#000066'");%>><strong>
					<p class="font18"><%=getformato.format(saldo)%></p>
				</strong></font>
			</td>
		</tr>	
<%		}
		if (saldoIdiomas != 0 && ( cuenta.equals("TODO") || cuenta.equals("SIDIOM01"))){
			if (saldoIdiomas >= 0) {
				strNaturaleza = "C";
			} else {
				strNaturaleza = "D";
			}		
			saldo = saldo + saldoIdiomas;
%>
	 	<tr> 
			<td class="descripcion" colspan="2" align="center">
				<p class="font18">
					<spring:message code="datosAlumno.portal.EdoSaldo"/> <spring:message code="datosAlumno.portal.EdoAnterior"/> Idiomas
				</p>
			</td>
			<td style="text-align:right;"><p class="font18"><%=saldoIdiomas > 0 ? 0 : getformato.format(saldoIdiomas)%></p></td>
			<td style="text-align:right;"><p class="font18"><%=saldoIdiomas < 0 ? 0 : getformato.format(saldoIdiomas)%></p></td>
			<td style="text-align:right;">
		    	<font size="1" face="Arial" <%if (saldo < 0) out.print("color='#FF0000'"); else out.print("color='#000066'");%>><strong>
				<p class="font18"><%=getformato.format(saldo)%></p>
			  </strong></font>
			</td>			
		</tr>				
<%		}
		if (saldoCimum != 0 && (cuenta.equals("TODO") || cuenta.equals("SCIMUN01"))){
			if (saldoCimum >= 0) {
				strNaturaleza = "C";
			} else {
				strNaturaleza = "D";
			}
			saldo = saldo + saldoCimum;
%>
	 	<tr> 
			<td class="descripcion" colspan="2" align="center">
				<p class="font18">
					<spring:message code="datosAlumno.portal.EdoSaldo"/> <spring:message code="datosAlumno.portal.EdoAnterior"/> CIMUM
				</p>
			</td>
			<td style="text-align:right;"><p class="font18"><%=saldoCimum > 0 ? 0 : getformato.format(saldoCimum)%></p></td>
			<td style="text-align:right;"><p class="font18"><%=saldoCimum < 0 ? 0 : getformato.format(saldoCimum)%></p></td>
			<td style="text-align:right;">
		    	<font size="1" face="Arial" <%if (saldo < 0) out.print("color='#FF0000'"); else out.print("color='#000066'");%>><strong>
		      		<p class="font18"><%=getformato.format(saldo)%></p>
			  	</strong></font>
			</td>			
		</tr>				
<%		}%>
		
<%		if (saldoCimumAllende != 0 && (cuenta.equals("TODO") || cuenta.equals("SCIMUN02"))){
			if (saldoCimumAllende >= 0) {
				strNaturaleza = "C";
			} else {
				strNaturaleza = "D";
			}
			
			saldo = saldo + saldoCimumAllende;
	%>
		 	<tr> 
				<td class="descripcion" colspan="2" align="center">
					<p class="font18">
						<spring:message code="datosAlumno.portal.EdoSaldo"/> <spring:message code="datosAlumno.portal.EdoAnterior"/> CIMUM-A
					</p>
				</td>
				<td style="text-align:right;"><p class="font18"><%=saldoCimumAllende > 0 ? 0 : getformato.format(saldoCimumAllende)%></p></td>
				<td style="text-align:right;"><p class="font18"><%=saldoCimumAllende < 0 ? 0 : getformato.format(saldoCimumAllende)%></p></td>
				<td style="text-align:right;">
			      <font size="1" face="Arial" <%if (saldo < 0) out.print("color='#FF0000'"); else out.print("color='#000066'");%>><strong>
					<p class="font18"><%=getformato.format(saldo)%></p>
				  </strong></font>
				</td>	     
			</tr>				
	<%	}%>  
		
	<%	if (saldoRemedial != 0 && (cuenta.equals("TODO") || cuenta.equals("SREMED01"))){
		if (saldoRemedial >= 0) {
			strNaturaleza = "C";
		} else {
			strNaturaleza = "D";
		}		
		saldo = saldo + saldoRemedial;
%>
	 	<tr> 
			<td class="descripcion" colspan="7" align="center">
				<font size="1" face="Arial">
					<spring:message code="datosAlumno.portal.EdoSaldo"/> <spring:message code="datosAlumno.portal.EdoAnterior"/> Remedial
				</font>
			</td>
			<td style="text-align:right;"><font size="1" face="Arial"><%=saldoRemedial > 0 ? 0 : getformato.format(saldoRemedial)%></font></td>
			<td style="text-align:right;"><font size="1" face="Arial"><%=saldoRemedial < 0 ? 0 : getformato.format(saldoRemedial)%></font></td>
			<td style="text-align:right;">
		      <font size="1" face="Arial" <%if (saldo < 0) out.print("color='#FF0000'"); else out.print("color='#000066'");%>><strong>
				<%=getformato.format(saldo)%>
			  </strong></font>
			</td>
		</tr>				
<%	}%>
<%
		boolean sem = false;
		boolean tetra1 = false;
		boolean tetra2 = false;
		
		if (Integer.parseInt(year) <= 2018) {

			numSaldo = saldoAnterior;
			/******* BUSCA LOS MOVIMIENTOS EN ORACLE ******/
			for (ContMovimiento movimiento : lisMovOracle){
				numCargos = 0;
				numCreditos = 0;
				strNaturaleza = movimiento.getNaturaleza();
				libro = movimiento.getIdLibro();
				if (strNaturaleza.equals("D")) {
					numCargos = Double.valueOf(movimiento.getImporte());
					strCargos = getformato.format(numCargos);
					strCreditos = " ";
					numSaldo = numSaldo - numCargos;
				} else {
					numCreditos = Double.valueOf(movimiento.getImporte());
					strCreditos = getformato.format(numCreditos);
					strCargos = " ";
					numSaldo = numSaldo + numCreditos;
				}
				strSaldo = getformato.format(numSaldo);

				String fechaMov = movimiento.getFecha();
				
				totalCargos += numCargos;
				totalCreditos += numCreditos;
%>
		  <tr>
			<td align="center"><font size="1" face="Arial"><%=fechaMov%></font></td>
			<td class="descripcion" align="left"><font size="1" face="Arial"><%=movimiento.getDescripcion()%></font></td>
			<td style="text-align:right;"><font size="1" face="Arial"><%=strCargos%></font></td>
			<td style="text-align:right;"><font size="1" face="Arial"><%=strCreditos%></font></td>		
			<td style="text-align:right;">
			  <font size="4" face="Arial" <%=numSaldo<0?"color='#FF0000'":"color='#000066'"%>><strong><%=strSaldo%></strong></font>
 			</td>
		  </tr>
<%			} //while de movimientos%>
<%		} else {
			/******************** BUSCA LOS MOVIMIENTOS EN SUNPLUS *****************************/
			numSaldo = saldoAnterior;

			for (A3lAsalfldg movimiento : lisMovSunPlus) {
// 				System.out.println("DOS : "+movimiento.getAccntCode());
				numCargos = 0;
				numCreditos = 0;
				strNaturaleza = movimiento.getdC();
				conceptoId = movimiento.getAnalT7();
				libro = movimiento.getJrnalNo();
				String allocation = movimiento.getAllocation();
				if (!allocation.equals("C")) {
					if (strNaturaleza.equals("D")) {
						numCargos = Double.valueOf(movimiento.getAmount()) * -1;
						strCargos = getformato.format(numCargos);
						strCreditos = " ";
						numSaldo = numSaldo - numCargos;
					} else {
						numCreditos = Double.valueOf(movimiento.getAmount());
						strCreditos = getformato.format(numCreditos);
						strCargos = " ";
						numSaldo = numSaldo + numCreditos;
					}
					strSaldo = getformato.format(numSaldo);	
				}
				
				String fechaMov = movimiento.getTransDatetime();

				totalCargos += numCargos;
				totalCreditos += numCreditos;
				
				String grupoAjustes 		= "SAJUCO01,SAJUSA01,SAJUSB01,SAJUSC01,SAJUSR01,SAJUST01,SAJUSX01,SCOREC01,SINICI01,SRETIR01,STRASP01";
				String grupoDescuentos 		= "SAYUED01,SBCONV01,SBDEPT01,SBDOCE01,SBDONA01,SBEBAS01,SBEBAS02,SBECAD01,SBECAD02,SBECOL01,SBENFE01,"+
											"SBENTR01,SBEVER01,SBEXCE01,SBHCEE01,SBINOV01,SBINTE01,SBINVE01,SBMISI01,SBMUNI01,SBONIF01,SBORFA01,SBPESO01,"+
											"SBPOST01,SBPREG01,SBPREP01,SBPROM01,SBREGI01,SBVENT01,SBZAMB01,SCOLPO01,SCOREC01,SDESCU01,SDESPI01,SDHNOS01";
				String grupoEnsenanza		= "SENSCI01,SENSEA01,SENSEC01,SENSED01,SENSID01,SMATRI01";
				String grupoPagos			= "SDONAT01,SPAGAR01,SPAGON01";
				String grupoOtros			= "SOTROS01";
				String grupoCalculo			= "SCALCU01";
					
				if (!allocation.equals("C")) {
	%>
	 	 	<tr>
				<td align="center"><font size="1" face="Arial"><%=fechaMov%></font></td>
				<td class="descripcion" align="left"><font size="1" face="Arial"><%=movimiento.getDescriptn()%></font></td>
				<td style="text-align:right;"><font size="1" face="Arial"><%=strCargos%></font></td>
				<td style="text-align:right;"><font size="1" face="Arial"><%=strCreditos%></font></td>		
				<td style="text-align:right;">
				  <font size="4" face="Arial" <%=numSaldo<0?"color='#FF0000'":"color='#000066'"%>><strong><%=strSaldo%></strong></font>
	 			</td>
		  	</tr>
<%				}
			} //while de movimientos en Sun Plus
		}
	} //if principal
%>
	</tbody>	
	</table> 	
</div>