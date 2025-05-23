<%@ page import="aca.util.Fecha"%>
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
	java.text.DecimalFormat getFormato2	= new java.text.DecimalFormat("Cr ###,##0.00; Db ###,##0.00");

	String usuario 						= (String) session.getAttribute("codigoPersonal");
	String todos 						= request.getParameter("todos")==null?"":request.getParameter("todos");
	String ninguno 						= request.getParameter("ninguno")==null?"":request.getParameter("ninguno");
	String tCarga 						= request.getParameter("tCarga")==null?"":request.getParameter("tCarga");
	String nCarga 						= request.getParameter("nCarga")==null?"":request.getParameter("nCarga");  
	String fInscripcion					= request.getParameter("fecha");
	String minimoCreditos				= request.getParameter("minimoCreditos")==null?"0":request.getParameter("minimoCreditos");
	
	String cargas			= (String)session.getAttribute("cargas") == null?aca.carga.CargaUtil.getCargasActivas(conEnoc, aca.util.Fecha.getHoy()):session.getAttribute("cargas").toString();
	String modalidades		= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
	
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	
	String matricula					= "";
	String cargaEstadistica             = "";
	String modalidadEstadistica         = "";
	double promedio						= 0;	
	
	int cont							= 0;
	int contRepetidos					= 0;

	boolean first						= false;

	if(fInscripcion == null){
		fInscripcion = Fecha.getHoy();
		first = true;
	}	
	
	// Lista de alumnos inscritos en las facultades y carreras
	ArrayList<aca.alumno.AlumEstado> lisInscritos = AlumEstadoU.getListaPorFecha(conEnoc, cargas, modalidades, fechaIni, fechaFin, "I", " ORDER BY FACULTAD_ID, CARRERA_ID");
	
	// Map de Alumnos
	HashMap<String, aca.alumno.AlumPersonal> mapAlumnos 		= AlumnoU.mapInscritosEnCargas(conEnoc, cargas);
	// HashMap de Facultades
	HashMap<String, aca.catalogo.CatFacultad> mapFacultad 		= CatFacultadU.getMapFacultad(conEnoc, "");	
	// Map de Carreras
	HashMap<String, aca.catalogo.CatCarrera> mapCarrera 		= CatCarreraU.getMapAll(conEnoc, "");
	// Map de Modalidades
	HashMap<String, aca.catalogo.CatModalidad> mapModalidad 	= aca.catalogo.ModalidadUtil.getMapAll(conEnoc,"");
	// Map de costos de credito
	HashMap<String, String> mapCostoCredito 					= aca.financiero.FesCcMateriaUtil.mapCostoCredito(conEnoc, cargas, "'I'");
	// Map de creditos vendidos
	HashMap<String, String> mapCreditos 						= aca.financiero.FesCcMateriaUtil.mapCreditosCarga(conEnoc, cargas, "'I'");
	// Map de creditos vendidos
	HashMap<String, String> mapMovimiento 						= aca.financiero.FesCCMovimientoUtil.mapMovimientoCarga(conEnoc, cargas, "'01','02','03','04','06','07','08','21','24','27','28','29','30','31','51'", "'I'");
	// Map de saldo Inicial
	HashMap<String, String> mapSaldoInicial 					= aca.financiero.FesCCMovimientoUtil.mapSaldoInicial(conEnoc, cargas, "'20'", "'I'");
	// Map de deuda del alumno
	HashMap<String, String> mapDeuda 							= aca.financiero.FesCCMovimientoUtil.mapDeudaAlumno(conEnoc, cargas, "'23','50'", "'I'");
	// Map de deuda del alumno
	HashMap<String, String> mapProrroga							= aca.financiero.FesCcPagareDetUtil.mapPagare(conEnoc, cargas, "'P'", "'I'");
	// Map de deuda del alumno
	HashMap<String, String> mapPagare 							= aca.financiero.FesCcPagareDetUtil.mapPagare(conEnoc, cargas, "'A'", "'I'");
	// Map de beca basica
	HashMap<String, String> mapBecaBasica						= aca.financiero.FesCcPagareDetUtil.mapPagare(conEnoc, cargas, "'BB'", "'I'");
	// Map de beca adicional
	HashMap<String, String> mapBecaAdicional					= aca.financiero.FesCcPagareDetUtil.mapPagare(conEnoc, cargas, "'BA'", "'I'");
	// Map de calculo de cobro
	HashMap<String, aca.financiero.FesCcobro> mapCalculo		= aca.financiero.FesCcobroUtil.mapInscritosCargas(conEnoc, cargas);
	
	String lisModo[] 			= modalidades.replace("'", "").split(",");	session.setAttribute("fechaFin", fechaFin);
	String muestraModalidades 	= "";
	
	int con = 1;
	for(String mod:lisModo){
		String nombreModalidad = aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, mod);
		muestraModalidades += nombreModalidad;	
		if(con < lisModo.length){
			muestraModalidades += ", ";
		}
		con++;
	}			
%>
<head>
	<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>

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
	<h3>Ventas diarias por cargas y modalidades<small class="text-muted fs-5">( Cargas : <%=cargas.replace("'","") %> | Modalidades : <%=muestraModalidades %> )</small></h3>
	<form id="forma" name="forma" action="creditosFecha?Accion=1" method="post">
		<div class="alert alert-info">
			<a href="menu" class="btn btn-primary">Menú</a>
			Fecha Inicio: <input id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
			Fecha Final: <input id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
			<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>
		</div>	
	</form>
<%
	String facultadTmp	= "";
	String carreraTmp 	= "";
	
	int row = 0;
	for (aca.alumno.AlumEstado alumno : lisInscritos){		
		
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
		
/* MOVIMIENTOS DE CALCULO DE COBRO */

		/*************** SALDO INICIAL *************/
		String saldoInicial = "0";
		if (mapSaldoInicial.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"20")){
			saldoInicial = mapSaldoInicial.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"20");
		}		
		
		/*************** COSTOS ************/
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
		
		String costoPagare2	= "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"31")){
			costoPagare2 = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"31");
		}	
		
		String costoExtemporanea = "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"24")){
			costoExtemporanea = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"24");
		}		
		
		double costoTotal = Double.parseDouble(costoMatricula)+Double.parseDouble(costoEnsenanza)+Double.parseDouble(costoInternado)+Double.parseDouble(costoPagare)+Double.parseDouble(costoPagare2)+Double.parseDouble(costoExtemporanea);
		
		/*************** DESCUENTOS ************/
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
		
		String becaEnsenanza = "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"07")){
			becaEnsenanza = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"07");
		}
		
		String becaInstitucional = "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"27")){
			becaInstitucional = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"27");
		}
		
		String extemporanea		= "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"24")){
			extemporanea = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"24");
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
		
		/*********DEUDA DEL ALUMNO********/
		
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
		
		/*******FINANCIAMIENTO DE LA DEUDA*********/
		
		String formaPago = "P";
		if (mapCalculo.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId())){
			aca.financiero.FesCcobro calculo = (aca.financiero.FesCcobro) mapCalculo.get(alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId());
			formaPago = calculo.getFormaPago();
		}		
		
		String prorrogas = "0";
		if (mapProrroga.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId())){
			prorrogas = mapProrroga.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId());
		}
		
		String pagares = "0";
		if (mapPagare.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId())){
			pagares = mapPagare.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId());
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
		
		if(!facultadTmp.equals(alumno.getFacultadId())){
			facultadTmp = alumno.getFacultadId();
%>
			
			</table>
			
			<h2><%=nombreFacultad %></h2>
			
			<table class="table table-bordered table-sm table-bordered" style="width:100%">
			    <tr>
					<th class="text-center" colspan="9"><spring:message code="aca.DatosDelAlumno"/></th>
					<th class="text-center">Sal.Ini.</th>
					<th class="text-center" colspan="8"><spring:message code="aca.Costo"/>s del período</th>
					<th class="text-center" colspan="3">Becas y Desctos.</th>
					<th class="text-center" colspan="2"><spring:message code="aca.Saldo"/> Neto</th>
					<th class="text-center" colspan="6">Financiamiento</th>
				</tr>
				<tr class="table-info">
					<th>#</th>
					<th><spring:message code="aca.Mat"/></th>
					<th><spring:message code="aca.Alumno"/></th>
					<th><spring:message code="aca.Fecha"/></th>
					<th><spring:message code="aca.Carga"/></th>
					<th>Blq.</th>
					<th><spring:message code="aca.Plan"/></th>
					<th>Modo</th>
					<th>Resi.</th>
					<th style="text-align:right">$ Importe</th>
					<th style="text-align:right">$ Mat.</th>
					<th style="text-align:right">Créd.</th>
					<th style="text-align:right">$ Créd.</th>
					<th style="text-align:right">$ Enza.</th>					
					<th style="text-align:right">$ Int.</th>
					<th style="text-align:right">$ Pag.</th>
					<th style="text-align:right">$ Extemp.</th>
					<th style="text-align:right">$ Costo</th>					
					<th style="text-align:right">Bec.UM</th>
					<th style="text-align:right">Bec.Obrero</th>
					<th style="text-align:right">Descto</th>
					<th style="text-align:right">$ Saldo</th>
					<th style="text-align:right">% Deuda</th>					
					<th style="text-align:right">$ Prorr.</th>
					<th style="text-align:right">$ Pag.Tot.</th>
					<th style="text-align:right">$ Bec.Bas.</th>
					<th style="text-align:right">$ Bec.Ad.</th>
					<th style="text-align:right">$ Anticipo</th>
					<th style="text-align:right">$ Pag.Net.</th>
				</tr>
<%		
		}
		
		if(!carreraTmp.equals(alumno.getCarreraId())){
			carreraTmp = alumno.getCarreraId();
%>
			<tr>
				<td colspan="29" class="table-dark">
					<strong><%=nombreCarrera %></strong>
				</td>
			</tr>
<%
		}
		if (Integer.parseInt(creditos) >= Integer.parseInt(minimoCreditos)){
			row++;;	
		
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
			<td style="text-align:right"><%=getFormato.format(Double.parseDouble(saldoInicial))%></td>
			<td style="text-align:right"><%=getFormato.format(Double.parseDouble(costoMatricula))%></td>
			<td style="text-align:right"><%=creditos%></td>
			<td style="text-align:right"><%=getFormato.format(Double.parseDouble(costoCredito))%></td>
			<td style="text-align:right"><%=getFormato.format(Double.parseDouble(costoEnsenanza))%></td>			
			<td style="text-align:right"><%= getFormato.format(Double.parseDouble(costoInternado))%></td>
			<td style="text-align:right"><%= getFormato.format(Double.parseDouble(costoPagare)+Double.parseDouble(costoPagare2))%></td>
			<td style="text-align:right"><%= getFormato.format(Double.parseDouble(extemporanea))%></td>			
			<td style="text-align:right"><%= getFormato.format(costoTotal)%></td>			
			<td style="text-align:right"><%= getFormato.format(becaUM)%></td>
			<td style="text-align:right"><%= getFormato.format(Double.parseDouble(becaHijo))%></td>
			<td style="text-align:right"><%= getFormato.format(Double.parseDouble(descuento))%></td>
			<td style="text-align:right"><%= getFormato.format(Double.parseDouble(deudaAlumno))%></td>
			<td style="text-align:right"><%= getFormato.format(porDeuda)%></td>
			<td style="text-align:right"><%= getFormato.format(Double.parseDouble(prorrogas))%></td>
			<td style="text-align:right"><%= getFormato.format(Double.parseDouble(pagares))%></td>
			<td style="text-align:right"><%= getFormato.format(Double.parseDouble(becaBasica))%></td>
			<td style="text-align:right"><%= getFormato.format(Double.parseDouble(becaAdicional))%></td>
			<td style="text-align:right"><%= getFormato.format(pagoAnticipado)%></td>
			<td style="text-align:right"><%= getFormato.format(pagareNeto)%></td>
		</tr>
<%		
		}
	}
%>		
	</table>
</div>		
</body>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>
<%@ include file="../../cierra_enoc.jsp"%>