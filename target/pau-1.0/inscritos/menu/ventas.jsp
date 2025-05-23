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

	String usuario 					= (String) session.getAttribute("codigoPersonal");
	String todos 					= request.getParameter("todos")==null?"":request.getParameter("todos");
	String ninguno 					= request.getParameter("ninguno")==null?"":request.getParameter("ninguno");
	String tCarga 					= request.getParameter("tCarga")==null?"":request.getParameter("tCarga");
	String nCarga 					= request.getParameter("nCarga")==null?"":request.getParameter("nCarga");
	String accion 					= request.getParameter("Accion")==null?"0":request.getParameter("Accion");  
	String fInscripcion				= request.getParameter("fecha");
	
	
	String cargas					= (String)session.getAttribute("cargas") == null?aca.carga.CargaUtil.getCargasActivas(conEnoc, aca.util.Fecha.getHoy()):session.getAttribute("cargas").toString();
	String modalidades				= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
	
	String fechaIni					= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin					= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	
	
	String matricula				= "";
	String cargaEstadistica         = "";
	String modalidadEstadistica     = "";
	double promedio					= 0;	
	
	int cont						= 0;
	int contRepetidos				= 0;

	boolean first					= false;

	if(fInscripcion == null){
		fInscripcion = Fecha.getHoy();
		first = true;
	}
	
	// lista de modalidades 
	ArrayList<aca.catalogo.CatModalidad> lisModalidad 	= CatModalidadU.getListAll(conEnoc, "ORDER BY MODALIDAD_ID");
	
	// lista de cargas  
	ArrayList<aca.carga.Carga> lisCarga 				= CargaU.getListPorFecha(conEnoc, fInscripcion, "ORDER BY CARGA_ID");
	
	// Lista de alumnos inscritos en las facultades y carreras
	ArrayList<aca.alumno.AlumEstado> lisInscritos = AlumEstadoU.getListaPorFecha(conEnoc, cargas, modalidades, fechaIni, fechaFin, "I", " ORDER BY FACULTAD_ID, CARRERA_ID");
	
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
	<h2>Inscritos por cargas y modalidades<small class="text-muted fs-4">( Cargas : <%=cargas.replace("'","") %> | Modalidades : <%=muestraModalidades %> )</small></h2>
	<form id="forma" name="forma" action="ventas?Accion=1" method="post">
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
		
		String extemporanea		= "0";
		if (mapMovimiento.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"24")){
			extemporanea = mapMovimiento.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"24");
		}
		
		double costoTotal = Double.parseDouble(costoMatricula)+Double.parseDouble(costoEnsenanza)+Double.parseDouble(costoInternado)+Double.parseDouble(costoPagare);		
		
		/*************** SALDO INICIAL *************/
		String saldoInicial = "0";
		if (mapSaldoInicial.containsKey( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"20")){
			saldoInicial = mapSaldoInicial.get( alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()+"20");
		}
		
		if(!facultadTmp.equals(alumno.getFacultadId())){
			facultadTmp = alumno.getFacultadId();
%>
			
		</table>
		
		<h1><%=nombreFacultad %></h1>
		
		<table class="table table-bordered table-sm">		
		<tr>
			<th class="text-center" colspan="9"><spring:message code="aca.DatosDelAlumno"/></th>
			<th class="text-center">Sal.Ini.</th>
			<th class="text-center" colspan="8"><spring:message code="aca.Costo"/>s del período</th>					
		</tr>
		<tr class="table-info">
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
		</tr>
<%		
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