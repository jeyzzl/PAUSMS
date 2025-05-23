<%@page import="aca.util.Fecha"%>
<%@ page import="java.util.*"%>

<%@ include file="../../con_enoc.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Carga" scope="page" class="aca.carga.Carga" />
<jsp:useBean id="CargaU" scope="page" class="aca.carga.CargaUtil" />
<jsp:useBean id="CatFacultadU" scope="page" class="aca.catalogo.CatFacultadUtil" />
<jsp:useBean id="CatCarreraU" scope="page" class="aca.catalogo.CatCarreraUtil" />
<jsp:useBean id="CatModalidadU" scope="page" class="aca.catalogo.ModalidadUtil" />
<jsp:useBean id="AlumEstadoU" scope="page" class="aca.alumno.EstadoUtil" />
<jsp:useBean id="empEstadistica" scope="page" class="aca.emp.EmpEstadistica" />
<jsp:useBean id="AlumnoU" scope="page" class="aca.alumno.AlumUtil"/>

<script type="text/javascript">
	function Mostrar(){		
		document.forma.submit();
	}
</script>
<%
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String usuario 						= (String) session.getAttribute("codigoPersonal");
	String tCarga 						= request.getParameter("tCarga")==null?"":request.getParameter("tCarga");
	String nCarga 						= request.getParameter("nCarga")==null?"":request.getParameter("nCarga");
	String accion 						= request.getParameter("Accion")==null?"0":request.getParameter("Accion");  
	String fInscripcion					= request.getParameter("fecha");
	String modalidades					= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
	String cargas						= (String)session.getAttribute("cargas") == null?aca.carga.CargaUtil.getCargasActivas(conEnoc, aca.util.Fecha.getHoy()):session.getAttribute("cargas").toString();
	String matricula					= "";
	String cargaEstadistica             = "";
	String modalidadEstadistica         = "";
	double promedio						= 0;		
	
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	
	if (accion.equals("1")){		
		session.setAttribute("fechaIni", fechaIni);
		session.setAttribute("fechaFin", fechaFin);
	} 
	
	int cont							= 0;
	int contRepetidos					= 0;

	boolean first						= false;

	if(fInscripcion == null){
		fInscripcion = Fecha.getHoy();
		first = true;
	}
	
	String lisModo[] 		= modalidades.replace("'", "").split(",");
	
	// Map de deuda del alumno
		HashMap<String, String> mapProrroga					= aca.financiero.FesCcPagareDetUtil.mapPagare(conEnoc, cargas, "'P'", "'I'");
		
	// Map de deuda del alumno
	HashMap<String, String> mapPagare	 				= aca.financiero.FesCcPagareDetUtil.mapPagare(conEnoc, cargas, "'A'", "'I'");
		
		
	
	// Lista de alumnos inscritos en las facultades y carreras
	ArrayList<aca.alumno.AlumEstado> lisInscritos = AlumEstadoU.getLista(conEnoc, cargas, modalidades, "I", fechaIni, fechaFin, " ORDER BY FACULTAD_ID, CARRERA_ID");
	
	// Map de Alumnos
	HashMap<String, aca.alumno.AlumPersonal> mapAlumnos 			= AlumnoU.mapInscritosEnCargas(conEnoc, cargas);
	// HashMap de Facultades
	HashMap<String, aca.catalogo.CatFacultad> mapFacultad 			= CatFacultadU.getMapFacultad(conEnoc, "");	
	// Map de Carreras
	HashMap<String, aca.catalogo.CatCarrera> mapCarrera 			= CatCarreraU.getMapAll(conEnoc, "");
	// Map de Modalidades
	HashMap<String, aca.catalogo.CatModalidad> mapModalidad 		= aca.catalogo.ModalidadUtil.getMapAll(conEnoc,"");
	// Map de costos de credito
	HashMap<String, String> mapCostoCredito 						= aca.financiero.FesCcMateriaUtil.mapCostoCredito(conEnoc, cargas, "'I'");
	// Map de creditos vendidos
	HashMap<String, String> mapCreditos 							= aca.financiero.FesCcMateriaUtil.mapCreditosCarga(conEnoc, cargas, "'I'");
	// Map de creditos vendidos
	HashMap<String, String> mapMovimiento 							= aca.financiero.FesCCMovimientoUtil.mapMovimientoCarga(conEnoc, cargas, "'01','02','03','04','06','07','08','21','24','27','28','29','30','31','51'", "'I'");
	//Map saldo Inicial
	HashMap<String, String> mapSaldoInicial 						= aca.financiero.FesCCMovimientoUtil.mapSaldoInicial(conEnoc, cargas, "'20'", "'I'");
	// Map de deuda del alumno
	HashMap<String, String> mapDeuda 								= aca.financiero.FesCCMovimientoUtil.mapDeudaAlumno(conEnoc, cargas, "'23','50'", "'I'");
	// Map de beca basica
	HashMap<String, String> mapBecaBasica						= aca.financiero.FesCcPagareDetUtil.mapPagare(conEnoc, cargas, "'BB'", "'I'");
	// Map de beca adicional
	HashMap<String, String> mapBecaAdicional					= aca.financiero.FesCcPagareDetUtil.mapPagare(conEnoc, cargas, "'BA'", "'I'");
	// Map de calculo de cobro
	HashMap<String, aca.financiero.FesCcobro> mapCalculo		= aca.financiero.FesCcobroUtil.mapInscritosCargas(conEnoc, cargas);
%>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
	<style>
		.titulo td{
			border:1px solid gray;
		}
		
		.table td.alert-info {
			background-color: #d9edf7 !important;
			border-color: #bce8f1;
			color: #3a87ad;
		}
	</style>
</head>
<body>
<div class="container-fluid">
	<h3>Estadistica de ventas por cargas y modalidades</h3>
	<form id="forma" name="forma" action="ventas?Accion=1" method="post">
		<div class="alert alert-info">
			<b>Cargas:</b> <%= cargas.replace("'", "")%>&nbsp;<a href="cargas" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;
			<b>Modalidades:</b>
<%
			for(String mod:lisModo){
				String nombreModalidad = aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, mod);
				out.print("["+nombreModalidad+"] ");	
			}		
%>
			<a href="modalidades" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;		
		</div>
		<div class="alert alert-info">		 
			Fecha Inicio: <input id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
			Fecha Final: <input id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
			<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>		 
		</div>
<%

	String facultadTmp	= "";
	String carreraTmp 	= "";
	
	int row = 0;
	for (aca.alumno.AlumEstado alumno : lisInscritos){
		row++;
		
		// Nombre del alumno
		String nombreAlumno = "";
		if (mapAlumnos.containsKey(alumno.getCodigoPersonal())){
			aca.alumno.AlumPersonal alumPersonal = (aca.alumno.AlumPersonal) mapAlumnos.get(alumno.getCodigoPersonal());
			nombreAlumno = alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno()+" "+alumPersonal.getNombre();
		}
		
		String nombreFacultad 	= "";
		if (mapFacultad.containsKey(alumno.getFacultadId())){
			nombreFacultad = mapFacultad.get(alumno.getFacultadId()).getNombreFacultad();
		}
		
		String nombreCarrera	= "";
		if (mapCarrera.containsKey(alumno.getCarreraId())){
			nombreCarrera = mapCarrera.get(alumno.getCarreraId()).getNombreCarrera();
		}
		
		String nombreModalidad = "";
		if (mapModalidad.containsKey(alumno.getModalidadId())){
			nombreModalidad = mapModalidad.get(alumno.getModalidadId()).getNombreModalidad();
		}
		
		String costoCredito = "0";
		if (mapCostoCredito.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId())){
			costoCredito = mapCostoCredito.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId());
		}
		
		String creditos = "0";
		if (mapCreditos.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId())){
			creditos = mapCreditos.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId());
		}
		
		String costoMatricula = "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"01")){
			costoMatricula = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"01");
		}
		
		String costoEnsenanza = "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"02")){
			costoEnsenanza = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"02");
		}
		
		String costoInternado = "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"03")){
			costoInternado = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"03");
		}
		
		String costoPagare = "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"08")){
			costoPagare = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"08");
		}
		
		String prorrogas = "0";
		if (mapProrroga.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId())){
			prorrogas = mapProrroga.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId());
		}
		
		String pagares = "0";
		if (mapPagare.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId())){
			pagares = mapPagare.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId());
		}
		
		double costoTotal = Double.parseDouble(costoMatricula)+Double.parseDouble(costoEnsenanza)+Double.parseDouble(costoInternado)+Double.parseDouble(costoPagare);
		
		String becaHijo		= "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"04")){
			becaHijo = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"04");
		}
		
		String descContado		= "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"06")){
			descContado = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"06");
		}
		String descExtempo 		= "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"21")){
			descExtempo = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"21");
		}
		
		String descuento = "0";
		descuento = descContado + descExtempo;
		
		String extemporanea		= "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"24")){
			extemporanea = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"24");
		}
		
		String becaEnsenanza = "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"07")){
			becaEnsenanza = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"07");
		}
		
		String becaInstitucional = "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"27")){
			becaInstitucional = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"27");
		}
		
		String becaInstitucional2 = "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"28")){
			becaInstitucional2 = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"28");
		}
		
		String becaPosgrado = "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"29")){
			becaPosgrado = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"29");
		}
		
		String becaPosgrado2 = "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"30")){
			becaPosgrado2 = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"30");
		}
		
		String becaEmpleado = "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"51")){
			becaPosgrado2 = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"51");
		}
		
		double becaUM = Double.parseDouble(becaEnsenanza) + Double.parseDouble(becaInstitucional) - Double.parseDouble(becaInstitucional2) + Double.parseDouble(becaPosgrado) + Double.parseDouble(becaPosgrado2) + Double.parseDouble(becaEmpleado);
		
		String deudaAlumno = "0";
		if (mapDeuda.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId())){
			deudaAlumno = mapDeuda.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId());
		}
		
		// Obtiene el porcentaje de deuda en la inscripción definitiva
		double porDeuda = (Double.parseDouble(deudaAlumno) / costoTotal)*100;
		if (Double.parseDouble(deudaAlumno) >= 0){
			porDeuda = 0;
		}else{
			porDeuda = porDeuda*-1;
		}
		
		/*************** SALDO INICIAL *************/
		String saldoInicial = "0";
		if (mapSaldoInicial.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"20")){
			saldoInicial = mapSaldoInicial.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"20");
		}		
		
		String formaPago = "P";
		if (mapCalculo.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId())){
			aca.financiero.FesCcobro calculo = (aca.financiero.FesCcobro) mapCalculo.get(alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId());
			formaPago = calculo.getFormaPago();
		}	
		
		String becaBasica		= "0";
		if (mapBecaBasica.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId())){
			becaBasica = mapBecaBasica.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId());
		}
		
		String becaAdicional 	= "0";
		if (mapBecaAdicional.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId())){
			becaAdicional = mapBecaAdicional.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId());
		}
		
		double pagoAnticipado = 0;
		if (formaPago.equals("P")){
			if (Double.parseDouble(deudaAlumno) >= 0){
				pagoAnticipado = Double.parseDouble(deudaAlumno);
			}else{
				if ((Double.parseDouble(prorrogas)+Double.parseDouble(pagares)) > 0){
					pagoAnticipado = Double.parseDouble(deudaAlumno)+Double.parseDouble(prorrogas)+Double.parseDouble(pagares);
				}else{
					pagoAnticipado = 0;
				}				
			}
		}
		
		double pagareNeto		= 0;
		if ((Double.parseDouble(prorrogas)+Double.parseDouble(pagares)) > 0){
			pagareNeto = Double.parseDouble(pagares)-Double.parseDouble(becaBasica)-Double.parseDouble(becaAdicional)-pagoAnticipado;
		}		
		
		/*
		String costoExtemporanea = "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"24")){
			costoExtemporanea = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"24");
		}
		*/
		
		if(!facultadTmp.equals(alumno.getFacultadId())){
			facultadTmp = alumno.getFacultadId();
%>
			
			</table>
			
			<h1><%=nombreFacultad %></h1>
			
			<table style="width:120%" class="table table-bordered table-condesed">		
			  <tr>
					<th class="center" colspan="9"><spring:message code="aca.DatosDelAlumno"/></th>
					<th class="center">Sal.Ini.</th>
					<th class="center" colspan="8"><spring:message code="aca.Costo"/>s del período</th>
					<th class="center" colspan="3">Becas y Desctos.</th>
					<th class="center" colspan="2"><spring:message code="aca.Saldo"/> Neto</th>
					<th class="center" colspan="6">Financiamiento</th>
				</tr>
				<tr>
					<td>#</td>
					<td><spring:message code="aca.Mat"/></td>
					<td><spring:message code="aca.Alumno"/></td>
					<td><spring:message code="aca.Fecha"/></td>
					<td><spring:message code="aca.Carga"/></td>
					<td>Blq.</td>
					<td><spring:message code="aca.Plan"/></td>
					<td>Mod.</td>
					<td><spring:message code="aca.Res"/></td>
					<td style="text-align:right">$ Sald. Ini.</td>
					<td style="text-align:right">$ Mat.</td>
					<td style="text-align:right">Créd.</td>
					<td style="text-align:right">$ Créd.</td>
					<td style="text-align:right">$ Enza.</td>					
					<td style="text-align:right">$ Inter.</td>
					<td style="text-align:right">$ Pag.</td>
					<td style="text-align:right">$ Extemp.</td>
					<td style="text-align:right">$ Costo</td>
					<td>Beca UM</td>
					<td>Bec. Obrero</td>
					<td>Desc.</td>	
					<td style="text-align:right">$ Saldo</td>
					<td style="text-align:right">% Deuda</td>		
					<td style="text-align:right">$ Prorro.</td>
					<td style="text-align:right">$ Pag.Tot.</td>	
					<td style="text-align:right">$ Bec.Bas.</td>
					<td style="text-align:right">$ Bec.Ad.</td>
					<td style="text-align:right">$ Anticipo</td>
					<td style="text-align:right">$ Pag.Net.</td>							
				</tr>
<%		
		}
		
		if(!carreraTmp.equals(alumno.getCarreraId())){
			carreraTmp = alumno.getCarreraId();
%>
			<tr>
				<td colspan="29" class="alert alert-info">
					<strong><%=nombreCarrera %></strong>
				</td>
			</tr>
<%
		}
%>
			<tr>
				<td><%=row%></td>
				<td><%=alumno.getCodigoPersonal()%></td>
				<td><%=nombreAlumno%></td>
				<td><%=alumno.getFecha()%></td>
				<td><%=alumno.getCargaId()%></td>
				<td><%=alumno.getBloqueId()%></td>
				<td><%=alumno.getPlanId()%></td>			
				<td><%=nombreModalidad%></td>
				<td><%=alumno.getResidenciaId().equals("E")?"Externo":"Interno"%></td>
				<td style="text-align:right"><%=saldoInicial %></td>
				<td style="text-align:right"><%=getFormato.format(Double.parseDouble(costoMatricula))%></td>
				<td style="text-align:right"><%=creditos%></td>
				<td style="text-align:right"><%=getFormato.format(Double.parseDouble(costoCredito))%></td>
				<td style="text-align:right"><%=getFormato.format(Double.parseDouble(costoEnsenanza))%></td>			
				<td style="text-align:right"><%= getFormato.format(Double.parseDouble(costoInternado))%></td>
				<td style="text-align:right"><%= getFormato.format(Double.parseDouble(costoPagare))%></td>
				<td style="text-align:right"><%= getFormato.format(Double.parseDouble(extemporanea))%></td>
				<td style="text-align:right"><%= getFormato.format(costoTotal)%></td>
				<td style="text-align:right"><%= getFormato.format(becaUM)%></td>
				<td style="text-align:right"><%= getFormato.format(Double.parseDouble(becaHijo))%></td>
				<td style="text-align:right"><%= getFormato.format(Double.parseDouble(descuento))%></td>
				<td style="text-align:right"><%= getFormato.format(Double.parseDouble(deudaAlumno))%></td>
				<td style="text-align:right"><%= getFormato.format(porDeuda)%></td>
				<td style="text-align:right"><%= getFormato.format(Double.parseDouble(prorrogas))%></td>
				<td style="text-align:right"><%= getFormato.format(Double.parseDouble(pagares))%> </td>
				<td style="text-align:right"><%= getFormato.format(Double.parseDouble(becaBasica))%></td>
				<td style="text-align:right"><%= getFormato.format(Double.parseDouble(becaAdicional))%></td>
				<td style="text-align:right"><%= getFormato.format(pagoAnticipado)%></td>
				<td style="text-align:right"><%= getFormato.format(pagareNeto)%></td>
				
					
			</tr>
<%		
		}
%>		
		</table>
	</form>
</div>		
</body>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>
<%@ include file="../../cierra_enoc.jsp"%>