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
<%@ include file="menu.jsp"%>

<style>
	span.muestra{
		display: none;
		width: 220px;
		background-color: gray;
		color: #fff;
		text-align: center;
		border-radius: 6px;
		padding: 5px 0;
		/* Position the tooltip */
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
	String personal 			= session.getAttribute("codigoPersonal").toString();
	String cargaId 				= session.getAttribute("cargaId").toString();
	String esMovil				= (String) session.getAttribute("esMovil");

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
	
	double saldoFormalTodo 		= (double) request.getAttribute("saldoFormalTodo");
	double saldoIdiomasTodo		= (double) request.getAttribute("saldoIdiomasTodo");
	double saldoCimumTodo		= (double) request.getAttribute("saldoCimumTodo");
	double saldoCimumAllendeTodo= (double) request.getAttribute("saldoCimumAllendeTodo");
	double saldoRemedialTodo	= (double) request.getAttribute("saldoRemedialTodo");
	
	String nombreAlumno 		= (String) request.getAttribute("nombreAlumno");
	boolean tieneComentarios 	= (boolean) request.getAttribute("tieneComentarios");
	boolean tieneSaldoRip 		= (boolean) request.getAttribute("tieneSaldoRip");	
	ContSaldosRip rip 			= (ContSaldosRip) request.getAttribute("rip");
	
	// Lista de comentarios del alumno
	List<FinComentario> lisComentarios 				= (List<FinComentario>) request.getAttribute("lisComentarios");

	// Lista de calculos
	List<FesCcobro> lisCalculos 					= (List<FesCcobro>) request.getAttribute("lisCalculos");

	// Lista de movimientos en Oracle
	List<ContMovimiento> lisMovOracle 				= (List<ContMovimiento>) request.getAttribute("lisMovOracle");

	// Lista de movimientos en SunPlus
	List<A3lAsalfldg> lisMovSunPlus 				= (List<A3lAsalfldg>) request.getAttribute("lisMovSunPlus");
	//System.out.println("Lista sunplus:"+lisMovSunPlus.size());
	// Lista de cuentas en SunPlus
	List<String> lisAlumnoCuentas 					= (List<String>) request.getAttribute("lisAlumnoCuentas");

	List<String> lisAlumnoCuentasSinFecha 			= (List<String>) request.getAttribute("lisAlumnoCuentasSinFecha");

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
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-table.min.css">
  	<script src="<%=request.getContextPath()%>/js/bootstrap-table.min.js" type="text/javascript"></script>
  	<script src="<%=request.getContextPath()%>/js/bootstrap-table-export.min.js" type="text/javascript"></script>
  	<script src="<%=request.getContextPath()%>/js/tableExport.min.js" type="text/javascript"></script>
  	<script src="<%=request.getContextPath()%>/js/jspdf.min.js" type="text/javascript"></script>
  	<script src="<%=request.getContextPath()%>/js/jspdf.plugin.autotable.js" type="text/javascript"></script>
</head>
<body>
<div class="container-fluid">

	<h3 class="mt-1"><spring:message code="portal.alumno.cuenta.EstadoDeCuenta"/>
		<small class="text-muted fs-6" style="font-size:11px">
			( <%=matricula%> - <%=nombreAlumno%> - Del <%=fechaInicio%> al <%=fechaFinal%> )&nbsp;&nbsp;
			<b><spring:message code="portal.alumno.cuenta.Planes"/>:</b>
<%
			String nombrePlan = "-";
			int row = 0;
			for (CargaAlumno carga : lisPlanes) {
				if (mapaPlanes.containsKey(carga.getPlanId())){
					nombrePlan = mapaPlanes.get(carga.getPlanId()).getNombrePlan();
				}
				row++;
				if (row == 1)
					out.print(carga.getPlanId()+ " - "+nombrePlan);
				else
					out.print(", " + carga.getPlanId()+ " - "+nombrePlan);
			}
%>		)
		</small>
	</h3>	
	<div class="alert alert-info">
		<span data-bs-toggle="tooltip" data-bs-placement="right" title="Elegir el a�o"><i class="far fa-calendar-alt" style="color:#dc9e23"></i></span>
		<b>{</b>
<%	if(esMovil.equals("N")){ %>		
		<button class="btn btn-sm <%=year.equals(String.valueOf(yearBack4))?"btn-dark":"btn-outline-secondary"%>" onclick="location.href='financiero?year=<%=yearBack4%>&division=<%=division%>'"><%=yearBack4%></button>
		<button class="btn btn-sm <%=year.equals(String.valueOf(yearBack3))?"btn-dark":"btn-outline-secondary"%>" onclick="location.href='financiero?year=<%=yearBack3%>&division=<%=division%>'"><%=yearBack3%></button>
	  	<button class="btn btn-sm <%=year.equals(String.valueOf(yearBack2))?"btn-dark":"btn-outline-secondary"%>" onclick="location.href='financiero?year=<%=yearBack2%>&division=<%=division%>'"><%=yearBack2%></button>
<%	} %>	  	
	  	<button class="btn btn-sm <%=year.equals(String.valueOf(yearBack1))?"btn-dark":"btn-outline-secondary"%>" onclick="location.href='financiero?year=<%=yearBack1%>&division=<%=division%>'"><%=yearBack1%></button>
	  	<button class="btn btn-sm <%=year.equals(String.valueOf(aca.util.Fecha.getHoy().substring(6, 10)))?"btn-dark":"btn-outline-secondary"%>" onclick="location.href='financiero?division=<%=division%>'"><%=anio%></button>
	  	<b>}</b>
	  	&nbsp;
	  	<i class="fas fa-money-check" style="color:#dc9e23"></i><b>{</b> 	 	  	
	  	<a class="btn btn-sm <%=cuenta.equals("TODO")?"btn-dark":"btn-outline-secondary"%>" href="financiero?year=<%=year%>&division=<%=division%>&cuenta=TODO"><spring:message code="portal.alumno.cuenta.Todo"/></a>
<%		if (mapaCuentas.containsKey("SFORMA01")){%>	
	  	<a class="btn btn-sm <%=cuenta.equals("SFORMA01")?"btn-dark":"btn-outline-secondary"%>" href="financiero?year=<%=year%>&division=<%=division%>&cuenta=SFORMA01">
	  	<spring:message code="portal.alumno.cuenta.Formal"/>
	  	</a>
<%		} //-, <%=mapaCuentas.get("SFORMA01"), <%=mapaCuentas.get("SIDIOM01"), -<%=mapaCuentas.get("SCIMUN01")  %>	  	
<%		if (mapaCuentas.containsKey("SIDIOM01")){%>
	  	<a class="btn btn-sm <%=cuenta.equals("SIDIOM01")?"btn-dark":"btn-outline-secondary"%>" href="financiero?year=<%=year%>&division=<%=division%>&cuenta=SIDIOM01">
	  	<spring:message code="portal.alumno.cuenta.Idiomas"/></a>
<%		}%>
<%		if (mapaCuentas.containsKey("SCIMUN01")){%>
	  	<a class="btn btn-sm <%=cuenta.equals("SCIMUN01")?"btn-dark":"btn-outline-secondary"%>" href="financiero?year=<%=year%>&division=<%=division%>&cuenta=SCIMUN01">
	  	CIMUM-M
	  	</a>
<%		}%>
<%		if (mapaCuentas.containsKey("SCIMUN02")){%>
	  	<a class="btn btn-sm <%=cuenta.equals("SCIMUN02")?"btn-dark":"btn-outline-secondary"%>" href="financiero?year=<%=year%>&division=<%=division%>&cuenta=SCIMUN02">
	  	CIMUM-A
	  	</a>
<%		}%>	  	
<%		if (mapaCuentas.containsKey("SREMED01")){%>
	  	<a class="btn btn-sm <%=cuenta.equals("SREMED01")?"btn-dark":"btn-outline-secondary"%>" href="financiero?year=<%=year%>&division=<%=division%>&cuenta=SREMED01">&nbsp;Remedial</a>
<%		}%>  	
	  	<b>}</b>
	  	&nbsp;
	  	<i class="fas fa-file-invoice" style="color:#dc9e23"></i><b>{</b>	  	 
<%		for (FesCcobro calculo : lisCalculos) {
			if (calculo.getFecha().substring(0, 4).equals(year)) {
				if(calculo.getInscrito().equals("S")){%>			
	  			<a class='btn btn-sm btn-success' title="<%=calculo.getPlanId()+" | "+calculo.getFecha()%>" href="https://virtual-um.um.edu.mx/financiero/calculoCobroReimpresion.html?txtMatricula=<%=calculo.getMatricula()%>&txtCarga=<%=calculo.getCargaId()%>&Bloque=<%=calculo.getBloque()%>" target='_blank'><%=calculo.getCargaId()%></a>	  			
<%				}else{ %>
				<a class='btn btn-sm btn-secondary' title="<%=calculo.getPlanId()+" | "+calculo.getFecha()%>" href="https://virtual-um.um.edu.mx/financiero/calculoCobroEstimado.html?txtCarga=<%=calculo.getCargaId()%>@<%=calculo.getBloque()%>@<%=calculo.getPlanId()%>&sltFormaPago=<%=calculo.getFormaPago()%>&txtMatricula=<%=calculo.getMatricula()%>" target='_blank'><%=calculo.getCargaId()%></a>
<%
				}
			}
		}%>
		<b>}</b>
		&nbsp;
	  	<spring:message code="portal.alumno.cuenta.Comentarios"/>:<span class="badge rounded-pill <%=lisComentarios.size()>0?"bg-success":"bg-secondary"%>"><%=lisComentarios.size()%></span>&nbsp;<a href="finComentario"><i class="fas fa-plus-circle"></i></a>&nbsp;
<%	if (tieneComentarios) {%>			  	  	
  		<a data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    		<i class="fas fa-search"></i>
  		</a>&nbsp;
<% 	}%>  		
  		<a href="referencias" class="btn btn-sm btn-dark">
			<img width="25px" src="../../imagenes/deposito.png"> Bank reference
		</a>
<!-- 		&nbsp; -->
<!-- 		<a class="btn btn-sm btn-dark" onClick="window.open('http://centauro.um.edu.mx/aplicaciones/dolares.jsp', 'mywindow', 'width=800,height=600');" style="color:white"> -->
<!-- 	    	<img width="30" src="../../imagenes/deposito.png"> Pago con d&oacute;lares -->
<!-- 	   	</a>	 -->
	</div>
<%	if (tieneComentarios) {
		int cont = 0;%>		
	<div class="collapse" id="collapseExample">
			Comments:			
<%		for (FinComentario comentario : lisComentarios) {
			cont++;
			String color = "badge bg-dark";
			if (comentario.getTipo().equals("R"))
				color = "badge bg-dark";
			if (comentario.getTipo().equals("V"))
				color = "badge bg-info";
			if (comentario.getTipo().equals("D"))
				color = "badge bg-success";
			if (comentario.getTipo().equals("C"))
				color = "badge bg-warning";
			if (comentario.getTipo().equals("A"))
				color = "badge bg-important";
			int mes = Integer.parseInt(comentario.getFecha().substring(3, 5));
			String fecha = comentario.getFecha().substring(0, 2) + "-"
					+ aca.util.Fecha.getMesNombreCorto(mes);

			String nombreMaestro = "-";
			if (mapaMaestros.containsKey(comentario.getUsuario())) {
				nombreMaestro = mapaMaestros.get(comentario.getUsuario());
			}

			if (usuario.equals(comentario.getUsuario())){%>							
				<a onMouseOver="muestra('<%=cont%>')" onMouseOut="oculta('<%=cont%>')" href="finComentarioAccion?Folio=<%=comentario.getFolio()%>" class="bg <%=color%>"><%=fecha%></a>&nbsp;
				<span class="muestra" id="muestra<%=cont%>">-- <%=nombreMaestro%> --<br><%=comentario.getComentario()%></span>
<%			} else {%>			
				<span onMouseOver="muestra('<%=cont%>')" onMouseOut="oculta('<%=cont%>')" class="badge <%=color%>"><%=fecha%></span>&nbsp;
				<span class="muestra" id="muestra<%=cont%>">-- <%=nombreMaestro%> --<br><%=comentario.getComentario()%></span>
<%			} %>

<%		} %>
	</div>
	<hr>					
<%	} %>
			
<%		
		if (tieneSaldoRip) {

			String mensaje = "-";
			if (rip.getNaturaleza().equals("D")) {
				mensaje = "�Atenci&oacute;n ! This student has a balance in uncollectible accounts of $"
						+ rip.getSaldo() + ". Please stop by the student finance office.";
			} else {
				mensaje = "�Atenc&oacute;n ! This student has a frozen credit on his or her account at $"
						+ rip.getSaldo() + ". Please stop by the student finance office.";
			}%>						
	<div class="alert alert-danger"><h3><%=mensaje%></h3></div>
<%		}%>	
	<table id="table" class="table" data-bs-toggle="table" data-show-columns="true" data-show-header="true" data-show-export="true" data-export-types="['pdf']">	 	  
		<thead>
			<tr>
				<th><spring:message code="portal.alumno.cuenta.Tipo"/></th> 
<% 		if(!matricula.equals(personal) && esMovil.equals("N")){ %>
				<th><spring:message code="portal.alumno.cuenta.Poliza"/></th> 
				<th><spring:message code="portal.alumno.cuenta.Concepto"/></th> 
<%		} %>
				<th><spring:message code="portal.alumno.cuenta.Fecha"/></th> 
				<th><spring:message code="portal.alumno.cuenta.Descripcion"/></th> 
<%		if(esMovil.equals("N")){ %>	
				<th><spring:message code="portal.alumno.cuenta.Referencia"/> 1</th> 
				<th><spring:message code="portal.alumno.cuenta.Referencia"/> 2</th> 
<%		} %>
				<th class="text-end"><spring:message code="portal.alumno.cuenta.Cargos"/></th> 
				<th class="text-end"><spring:message code="portal.alumno.cuenta.Creditos"/></th> 
				<th class="text-end"><spring:message code="portal.alumno.cuenta.Saldo"/></th> 
				<th>--</th> 
			</tr> 
		</thead>
		<tbody>
			<tr>
<%		if(!matricula.equals(personal) && esMovil.equals("N")){ %>	
				<th class="descripcion" colspan="7" align="center">
					<font size="1" face="Arial">
						<spring:message code="portal.alumno.cuenta.SaldoAnteriorFormal"/>
					</font>
				</th>
<%		}else{ %>
				<th class="descripcion" colspan="5" align="center">
					<font size="1" face="Arial">
						<spring:message code="portal.alumno.cuenta.SaldoAnteriorFormal"/>
					</font>
				</th>
<%		} %>
<%		
		double saldo = 0;

		if (saldoFormal != 0 && (cuenta.equals("TODO") || cuenta.equals("SFORMA01")) ){ 
			if (saldoFormal >= 0) {
				strNaturaleza = "C";
			} else {
				strNaturaleza = "D";
			}
			
			saldo = saldoFormal;
		}
%>
				<th style="text-align:right;"><font size="1" face="Arial"><%=saldoFormal>0?0:getformato.format(saldoFormal)%></font></th>
				<th style="text-align:right;"><font size="1" face="Arial"><%=saldoFormal<0?0:getformato.format(saldoFormal)%></font></th>
				<th style="text-align:right;">
			      <font size="1" face="Arial" <%if (saldo < 0) out.print("color='#FF0000'"); else out.print("color='#000066'");%>><strong>
					<%=getformato.format(saldo)%>
				  </strong></font>
				</th>
				<th class="text-center">
			  		<font size="1" face="Arial" <%if (saldo < 0){out.print("color='#FF0000'");}else{out.print("color='#000066'");}%>>
				  		<strong>
<%		if (saldo < 0) {%>
							Db
<%		} else if (saldoIdiomas >= 0) { %>
							Cr
<%		} else {  %>
							-
<%		}%>			    </strong>
					</font>  
				</th>
			</tr> 
<%	if (saldoIdiomas != 0 && ( cuenta.equals("TODO") || cuenta.equals("SIDIOM01"))){
		if (saldoIdiomas >= 0) {
			strNaturaleza = "C";
		} else {
			strNaturaleza = "D";
		}
		
		saldo = saldo + saldoIdiomas;
%>
	 		<tr> 
<%		if(!matricula.equals(personal) && esMovil.equals("N")){ %>	
				<td class="descripcion" colspan="7" align="center">
					<font size="1" face="Arial">
						<spring:message code="datosAlumno.portal.EdoSaldo"/> <spring:message code="datosAlumno.portal.EdoAnterior"/> <spring:message code="portal.alumno.cuenta.Idiomas"/>
					</font>
				</td>
<%		}else{ %>
				<td class="descripcion" colspan="5" align="center">
					<font size="1" face="Arial">
						<spring:message code="datosAlumno.portal.EdoSaldo"/> <spring:message code="datosAlumno.portal.EdoAnterior"/> <spring:message code="portal.alumno.cuenta.Idiomas"/>
					</font>
				</td>
<%		} %>
				<td style="text-align:right;"><font size="1" face="Arial"><%=saldoIdiomas > 0 ? 0 : getformato.format(saldoIdiomas)%></font></td>
				<td style="text-align:right;"><font size="1" face="Arial"><%=saldoIdiomas < 0 ? 0 : getformato.format(saldoIdiomas)%></font></td>
				<td style="text-align:right;">
			      <font size="1" face="Arial" <%if (saldo < 0) out.print("color='#FF0000'"); else out.print("color='#000066'");%>><strong>
					<%=getformato.format(saldo)%>
				  </strong></font>
				</td>
				<td align="center">
			  		<font size="1" face="Arial" <%if (saldo < 0){out.print("color='#FF0000'");}else{out.print("color='#000066'");}%>>
				  		<strong>
<%		if (saldo < 0) {
			out.println("Db");
		} else if (saldoIdiomas >= 0) {
			out.println("Cr");
		} else {
			out.println("-");
		}
%>			      		</strong>
					</font>  
				</td>
			</tr>				
<%	}%>
<%	if (saldoCimum != 0 && (cuenta.equals("TODO") || cuenta.equals("SCIMUN01"))){
		if (saldoCimum >= 0) {
			strNaturaleza = "C";
		} else {
			strNaturaleza = "D";
		}
		
		saldo = saldo + saldoCimum;
%>
	 		<tr> 
<%		if(!matricula.equals(personal) && esMovil.equals("N")){ %>
				<td class="descripcion" colspan="7" align="left">
					<font size="1" face="Arial">
						<spring:message code="datosAlumno.portal.EdoSaldo"/> <spring:message code="datosAlumno.portal.EdoAnterior"/> CIMUM-M
					</font>
				</td>
<%		}else{ %>
				<td class="descripcion" colspan="5" align="left">
					<font size="1" face="Arial">
						<spring:message code="datosAlumno.portal.EdoSaldo"/> <spring:message code="datosAlumno.portal.EdoAnterior"/> CIMUM-M
					</font>
				</td>
<%		} %>
				<td style="text-align:right;"><font size="1" face="Arial"><%=saldoCimum > 0 ? 0 : getformato.format(saldoCimum)%></font></td>
				<td style="text-align:right;"><font size="1" face="Arial"><%=saldoCimum < 0 ? 0 : getformato.format(saldoCimum)%></font></td>
				<td style="text-align:right;">
			      <font size="1" face="Arial" <%if (saldo < 0) out.print("color='#FF0000'"); else out.print("color='#000066'");%>><strong>
					<%=getformato.format(saldo)%>
				  </strong></font>
				</td>
				<td align="center">
			  		<font size="1" face="Arial" <%if (saldo < 0){out.print("color='#FF0000'");}else{out.print("color='#000066'");}%>>
				  		<strong>
<%		if (saldo < 0) {
			out.println("Db");
		} else if (saldoCimum >= 0) {
			out.println("Cr");
		} else {
			out.println("-");
		}
%>			      		</strong>
					</font>  
				</td>
			</tr>				
<%	}%>
<%	if (saldoCimumAllende != 0 && (cuenta.equals("TODO") || cuenta.equals("SCIMUN02"))){
		if (saldoCimumAllende >= 0) {
			strNaturaleza = "C";
		} else {
			strNaturaleza = "D";
		}
		
		saldo = saldo + saldoCimumAllende;
%>
	 		<tr> 
<%		if(!matricula.equals(personal) && esMovil.equals("N")){ %>
				<td class="descripcion" colspan="7" align="center">
					<font size="1" face="Arial">
						<spring:message code="datosAlumno.portal.EdoSaldo"/> <spring:message code="datosAlumno.portal.EdoAnterior"/> CIMUM-A
					</font>
				</td>
<%		}else{ %>
				<td class="descripcion" colspan="5" align="center">
					<font size="1" face="Arial">
						<spring:message code="datosAlumno.portal.EdoSaldo"/> <spring:message code="datosAlumno.portal.EdoAnterior"/> CIMUM-A
					</font>
				</td>
<%		} %>
				<td style="text-align:right;"><font size="1" face="Arial"><%=saldoCimumAllende > 0 ? 0 : getformato.format(saldoCimumAllende)%></font></td>
				<td style="text-align:right;"><font size="1" face="Arial"><%=saldoCimumAllende < 0 ? 0 : getformato.format(saldoCimumAllende)%></font></td>
				<td style="text-align:right;">
			      <font size="1" face="Arial" <%if (saldo < 0) out.print("color='#FF0000'"); else out.print("color='#000066'");%>><strong>
					<%=getformato.format(saldo)%>
				  </strong></font>
				</td>
				<td align="center">
			  		<font size="1" face="Arial" <%if (saldo < 0){out.print("color='#FF0000'");}else{out.print("color='#000066'");}%>>
				  		<strong>
<%		if (saldo < 0) {
			out.println("Db");
		} else if (saldoCimumAllende >= 0) {
			out.println("Cr");
		} else {
			out.println("-");
		}
%>		      			</strong>
					</font>  
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
<%		if(!matricula.equals(personal) && esMovil.equals("N")){ %>
				<td class="descripcion" colspan="7" align="center">
					<font size="1" face="Arial">
						<spring:message code="datosAlumno.portal.EdoSaldo"/> <spring:message code="datosAlumno.portal.EdoAnterior"/> <spring:message code="portal.alumno.cuenta.Remedial"/>
					</font>
				</td>
<%		}else{ %>
				<td class="descripcion" colspan="5" align="center">
					<font size="1" face="Arial">
						<spring:message code="datosAlumno.portal.EdoSaldo"/> <spring:message code="datosAlumno.portal.EdoAnterior"/> <spring:message code="portal.alumno.cuenta.Remedial"/>
					</font>
				</td>
<%		} %>
				<td style="text-align:right;"><font size="1" face="Arial"><%=saldoRemedial > 0 ? 0 : getformato.format(saldoRemedial)%></font></td>
				<td style="text-align:right;"><font size="1" face="Arial"><%=saldoRemedial < 0 ? 0 : getformato.format(saldoRemedial)%></font></td>
				<td style="text-align:right;">
			      <font size="1" face="Arial" <%if (saldo < 0) out.print("color='#FF0000'"); else out.print("color='#000066'");%>><strong>
					<%=getformato.format(saldo)%>
				  </strong></font>
				</td>
				<td align="center">
			  		<font size="1" face="Arial" <%if (saldo < 0){out.print("color='#FF0000'");}else{out.print("color='#000066'");}%>>
				  		<strong>
<%		if (saldo < 0) {
			out.println("Db");
		} else if (saldoRemedial >= 0) {
			out.println("Cr");
		} else {
			out.println("-");
		}
%>		      			</strong>
					</font>  
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
			conceptoId = movimiento.getConceptoId();
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
			String concepto = "-";
			if (mapaConcepto.containsKey(movimiento.getConceptoId())) {
				concepto = mapaConcepto.get(movimiento.getConceptoId()).getNombre();
			}
	
			String tipoColor = "";
			if (strNaturaleza.equals("C")) {
				if (libro.equals("20"))
					tipoColor = "badge bg-dark";
				else
					tipoColor = "badge bg-success";
			} else {
				tipoColor = "badge bg-warning";
			}%>
	 	<tr>
			<td align="center"><span class="<%=tipoColor%>"><font size="1" face="Arial"><%=strNaturaleza%></font></span></td>
<%		if(esMovil.equals("N")){ %>
			<td align="center"><font size="1" face="Arial"><%=movimiento.getIdLibro() + movimiento.getFolio()%></font></td>
			<td align="center" title="<%=concepto%>"><font size="1" face="Arial"><%=movimiento.getConceptoId()%></font></td>
<%		} %>
			<td align="center"><font size="1" face="Arial"><%=fechaMov%></font></td>
			<td class="descripcion" align="left"><font size="1" face="Arial"><%=movimiento.getDescripcion()%></font></td>
<%		if(esMovil.equals("N")){ %>
			<td class="refrencia" align="left"><font size="1" face="Arial"><%=movimiento.getReferencia()==null?"":movimiento.getReferencia()%></font></td>
			<td class="refrencia" align="left"><font size="1" face="Arial"><%=movimiento.getReferencia2()==null?"":movimiento.getReferencia()%></font></td>
<%		} %>
			<td style="text-align:right;"><font size="1" face="Arial"><%=strCargos%></font></td>
			<td style="text-align:right;"><font size="1" face="Arial"><%=strCreditos%></font></td>		
			<td style="text-align:right;">
		  		<font size="1" face="Arial" <%if (numSaldo < 0) out.print("color='#FF0000'"); else out.print("color='#000066'");%>>
		  		<strong>
				<%=strSaldo%>
				</strong>
				</font>
			</td>
			<td align="center">
		  		<font size="1" face="Arial" <%if (numSaldo < 0)
								out.print("color='#FF0000'");
							else
								out.print("color='#000066'");%>>
					<strong>
<%			if (numSaldo < 0) {
				out.println("Db");
			} else if (numSaldo > 0) {
				out.println("Cr");
			} else {
				out.println("-");
			}
%>		  			</strong>
				</font>
			</td>
		  </tr>
<%		} //while de movimientos%>
			<tr class="total">
				<td colspan="7" style="background:#D8D8D8;letter-spacing:3px;"><spring:message code="datosAlumno.portal.EdoTerminaAnio"/></td>
				<td style="background:#D8D8D8;text-align:right;"><%=getformato.format(totalCargos)%></td>
				<td style="background:#D8D8D8;text-align:right;"><%=getformato.format(totalCreditos)%></td>
				<td style="background:#D8D8D8;"></td>
				<td style="background:#D8D8D8;"></td>
			</tr>
<%	} else {

			/******************** BUSCA LOS MOVIMIENTOS EN SUNPLUS *****************************/
			numSaldo = saldoAnterior;

			for (A3lAsalfldg movimiento : lisMovSunPlus) {
// 				System.out.println("UNO : "+movimiento.getAccntCode());
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
				String concepto = "-";
				if (mapaCodigos.containsKey(conceptoId)) {
					concepto = mapaCodigos.get(conceptoId);
				}

				String tipoColor = "";
				if (strNaturaleza.equals("C")) {
					if (libro.equals("20"))
						tipoColor = "badge bg-dark";
					else
						tipoColor = "badge bg-success";
				} else
					tipoColor = "badge bg-warning";

				String cuentaColor = "badge bg-info";
				if (movimiento.getAccntCode().contains("334")) { 
					cuentaColor = "badge bg-success";
				}
				
				String grupoAjustes 		= "SAJUCO01,SAJUSA01,SAJUSB01,SAJUSC01,SAJUSR01,SAJUST01,SAJUSX01,SCOREC01,SINICI01,SRETIR01,STRASP01";
				String grupoDescuentos 		= "SAYUED01,SBCONV01,SBDEPT01,SBDOCE01,SBDONA01,SBEBAS01,SBEBAS02,SBECAD01,SBECAD02,SBECOL01,SBENFE01,"+
											"SBENTR01,SBEVER01,SBEXCE01,SBHCEE01,SBINOV01,SBINTE01,SBINVE01,SBMISI01,SBMUNI01,SBONIF01,SBORFA01,SBPESO01,"+
											"SBPOST01,SBPREG01,SBPREP01,SBPROM01,SBREGI01,SBVENT01,SBZAMB01,SCOLPO01,SCOREC01,SDESCU01,SDESPI01,SDHNOS01";
				String grupoEnsenanza		= "SENSCI01,SENSEA01,SENSEC01,SENSED01,SENSID01,SMATRI01";
				String grupoPagos			= "SDONAT01,SPAGAR01,SPAGON01";
				String grupoOtros			= "SOTROS01";
				String grupoCalculo			= "SCALCU01";
				
				conceptoId = conceptoId.replaceAll(" ", "");
				String colorConcepto 		= ""; 
				if (conceptoId.length() > 0){
					if (grupoAjustes.contains(conceptoId)){
						colorConcepto = "badge bg-warning";									
					}
					
					if (grupoDescuentos.contains(conceptoId)){
						colorConcepto = "badge badge bg-success";
					}
					
					if (grupoEnsenanza.contains(conceptoId)){
						colorConcepto = "badge  badge bg-info";
					}
					
					if (grupoPagos.contains(conceptoId)){
						colorConcepto = "badge  badge bg-primary";
					}
					
					if (grupoCalculo.contains(conceptoId)){
						colorConcepto = "badge  badge bg-dark";
					}
				}	
								//System.out.println("Concepto:"+conceptoId+":"+colorConcepto);								
%>
<%
			if (!allocation.equals("C")) {
%>
	 	 	<tr>
				<td align="center"><span class="<%=tipoColor%>"><font size="1" face="Arial"><%=strNaturaleza%></font></span></td>
<%		if(!matricula.equals(personal)){ %>
				<td align="center"><span class="<%=cuentaColor%>"><font size="1" face="Arial"><%=movimiento.getVchrNum()%></font></span></td>
				<td align="center" title="<%=concepto%>"><span class="<%=colorConcepto%>"><font size="1" face="Arial"><%=conceptoId%></font></span></td>
<%      } %>
				<td align="center"><font size="1" face="Arial"><%=fechaMov%></font></td>
				<td class="descripcion" align="left"><font size="1" face="Arial"><%=movimiento.getDescriptn()%></font></td>
<%		if(esMovil.equals("N")){ %>
				<td class="referencia" align="left"><font size="1" face="Arial"><%=movimiento.getTreference()%></font></td>
				<td class="referencia" align="left"><font size="1" face="Arial"><%=movimiento.getAnalT5()%></font></td>
<%      } %>
				<td style="text-align:right;"><font size="1" face="Arial"><%=strCargos%></font></td>
				<td style="text-align:right;"><font size="1" face="Arial"><%=strCreditos%></font></td>		
				<td style="text-align:right;">
				  <font size="1" face="Arial" <%=numSaldo<0?"color='#FF0000'":"color='#000066'"%>><strong><%=strSaldo%></strong></font>
				</td>
				<td align="center">
				  <font size="1" face="Arial" <%=numSaldo<0?"color='#FF0000'":"color='#000066'"%>><strong>
<%		if (numSaldo < 0) {
			out.println("Db");
		} else if (numSaldo > 0) {
			out.println("Cr");
		} else {
			out.println("-");
		}
%>		  				</strong>
					</font>
				</td>
		  	</tr>
<%		}
			} //while de movimientos en Sun Plus

		
	}%>
		</tbody>
	
	</table>
<%	}//if principal%>
	<div class="alert alert-warning" role="alert">
<%		for(String cue : lisAlumnoCuentasSinFecha){
			if(cue.trim().equals("SFORMA01")){	%>
				Formal: <%=getformato.format(saldoFormalTodo)%>&nbsp;&nbsp; 
<% 			}
			if(cue.trim().equals("SIDIOM01")){	%>
				Idiomas: <%=getformato.format(saldoIdiomasTodo)%>&nbsp;&nbsp; 
<% 			}
			if(cue.trim().equals("SCIMUN01")){	%>
				Cimum: <%=getformato.format(saldoCimumTodo)%>&nbsp;&nbsp; 
<% 			}
			if(cue.trim().equals("SREMED01")){	%>
				Remedial: <%=getformato.format(saldoRemedialTodo)%>&nbsp;&nbsp; 
<% 			}%>			
	<% }%>



	</div>
  	<div class="alert alert-info" style="font-size:12px">
  		<span style="color:black;"><b><spring:message code="portal.alumno.cuenta.ColumnaDeTipo"/>:</b></span>&nbsp; 
  		<span class="badge bg-success">C</span> = Credits&nbsp;&nbsp;
  		<span class="badge bg-warning">D</span>=Charges&nbsp;&nbsp;
	  	&nbsp;&nbsp;
	  	<span style="color:black"><b><spring:message code="portal.alumno.cuenta.ColumnaDeConcepto"/>:</b></span>
	  	&nbsp;<span class="badge bg-success">&nbsp;</span>=Scholarships and Discounts &nbsp; 
		<span class="badge bg-dark">&nbsp;</span>=Calculations &nbsp;
		<span class="badge bg-primary">&nbsp;</span>=Payments &nbsp;
		<span class="badge bg-info">&nbsp;</span>=Teaching &nbsp;
		<span class="badge bg-warning">&nbsp;</span>=Adjustment &nbsp;	    
  	</div> 	
</div>	
</body>
<script>
	$('.nav-tabs').find('.edoCuenta').addClass('active');

	(function($) {
		var saldoAnterior = $('.saldoAnterior'), input1 = $('.filtro1'), input2 = $('.filtro2'), filtro1 = '', filtro2 = '', edoCuenta = $('.estadoCuenta'), totalCargos = 0, totalCreditos = 0;
		input1.keyup(function() {
			filtrarTabla();
		});
		input2.keyup(function() {
			filtrarTabla();
		});
		input3.keyup(function() {
			filtrarTabla();
		});
		function filtrarTabla() {
			filtro1 = input1.val().toLowerCase();
			filtro2 = input2.val().toLowerCase();
			hideSaldo();
			var cambiar = true;
			if (filtro1.length == 0 && filtro2.length == 0) {
				cambiar = false;
			}
			edoCuenta.find('td.descripcion').each(
					function() {
						var $this = $(this), text = $this.text().toLowerCase();
						if (cambiar) {
							if (filtro1.length == 0)
								filtro1 = "&#9";
							if (filtro2.length == 0)
								filtro2 = "&#9";
						}
						if (!$this.parent().hasClass('saldoAnterior')) {
							if (text.indexOf(filtro1) != -1
									|| text.indexOf(filtro2) != -1) {
								$this.parent('tr').show();
							} else {
								$this.parent('tr').hide();
							}
						}
					});
			totalCargos = 0;
			totalCreditos = 0;
			calcularTotal();
		}
		function hideSaldo() {
			if (filtro1.length == 0 && filtro2.length == 0) {
				saldoAnterior.show();
				edoCuenta.find('tr').each(function() {
					$this = $(this);
					$this.find('td:nth-last-child(1)').show();
					$this.find('th:nth-last-child(1)').show();
					$this.find('td:nth-last-child(2)').show();
					$this.find('th:nth-last-child(2)').show();
				});
			} else {
				saldoAnterior.hide();
				edoCuenta.find('tr').each(function() {
					$this = $(this);
					$this.find('td:nth-last-child(1)').hide();
					$this.find('th:nth-last-child(1)').hide();
					$this.find('td:nth-last-child(2)').hide();
					$this.find('th:nth-last-child(2)').hide();
				});
			}
		}
		function calcularTotal() {
			$('.total').show();
			var trs = edoCuenta.find('tr:visible');
			trs.each(function(i) {
				var $this = $(this);
				if ($this.hasClass('total')) {
					$this.children('td').eq(1).text(
							totalCargos.toFixed(2).replace(
									/(^\d{1,3}|\d{3})(?=(?:\d{3})+(?:$|\.))/g,
									'$1,'));
					$this.children('td').eq(2).text(
							totalCreditos.toFixed(2).replace(
									/(^\d{1,3}|\d{3})(?=(?:\d{3})+(?:$|\.))/g,
									'$1,'));

					if (i > 0 && $(trs[i - 1]).hasClass('total')) {
						$this.hide();
					}
				} else {
					var cargo = $this.find('td.descripcion').next().text()
							.replace(/\,/g, '');
					if ($.trim(cargo) == '')
						cargo = '0';
					totalCargos += parseFloat(cargo);

					var credito = $this.find('td.descripcion').next().next()
							.text().replace(/\,/g, '');
					if ($.trim(credito) == '')
						credito = '0';
					totalCreditos += parseFloat(credito);
				}
			});
		}
	})(jQuery);

	$(document).ready(function() {
	    $('#table').DataTable();
	} );
	
	$(function () {
  		$('[data-bs-toggle="tooltip"]').tooltip();  		
	});
</script>
</html>