<%@page import="java.util.HashMap"%>

<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="EstadisticaU" scope="page" class="aca.vista.EstadisticaUtil"/>
<jsp:useBean id="FacultadU" scope="page" class="aca.catalogo.CatFacultadUtil"/>
<jsp:useBean id="CarreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>

<script type="text/javascript">	
	function Mostrar(){	
		document.forma.submit();
	}
</script>
<%	
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String year			= aca.util.Fecha.getHoy().substring(6);
	
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	
	String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String cargas 		= EstadisticaU.lisCargasEntreFechas(conEnoc, fechaIni, fechaFin);
	//System.out.println(cargas);
	// Lista de alumnos inscritos en el rango de fechas
	ArrayList<aca.vista.Estadistica> lisInscritos	= EstadisticaU.lisInscritosPorFechas(conEnoc, fechaIni, fechaFin, " ORDER BY CODIGO_PERSONAL, ENOC.ESTADISTICA.F_INSCRIPCION");

	// Map de modalidades
	java.util.HashMap<String, aca.catalogo.CatModalidad> mapModalidad 	= aca.catalogo.ModalidadUtil.getMapAll(conEnoc,"");
	
	//Map de Facultades
	HashMap<String, aca.catalogo.CatFacultad> mapFacultad 		= FacultadU.getMapFacultad(conEnoc, "");
	
	//Map de Carreras
	HashMap<String, aca.catalogo.CatCarrera> mapaCarrera = CarreraU.getMapAll(conEnoc, "ORDER BY NOMBRE_CARRERA");
	
	// Map de costos de credito
	HashMap<String, String> mapCostoCredito 			= aca.financiero.FesCcMateriaUtil.mapCostoCredito(conEnoc, cargas, "'I'");
			
	// Map de creditos vendidos
	HashMap<String, String> mapCreditos 				= aca.financiero.FesCcMateriaUtil.mapCreditosCarga(conEnoc, cargas, "'I'");
		
	// Map de creditos vendidos
	HashMap<String, String> mapMovimiento 				= aca.financiero.FesCCMovimientoUtil.mapMovimientoCarga(conEnoc, cargas, "'01','02','03','04','06','07','08','11','12','21','24','27','28','29','30','31','51'", "'I'");
	
%>
<head>	
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
</head>
<body>
<div class="container-fluid">
	<h1>Informe de Ventas por fechas</h1>
	<form id="forma" name="forma" action="ventasgcas?Accion=1" method="post">
	<div class="alert alert-info">
		<a href="menu" class="btn btn-primary">Menú</a>
		Fecha Inicio: <input id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10"/>&nbsp;&nbsp;
		Fecha Final: <input id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10"/>&nbsp;&nbsp;
		<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>
	</div>
	</form>
	<table class="table table-condensed table-striped">
	<tr>
		<th>#</th>
		<th>Matricula</th>		
		<th>Nombre del Alumno</th>
		<th>Carga</th>
		<th>Blq.</th>
		<th>Modalidad</th>
		<th>Residencia</th>
		<th>Nacionalidad</th>
		<th>Facultad</th>
		<th>Carrera</th>
		<th>Fecha Insc.</th>
		<th class="right">Cred.</th>
		<th class="right">$Cred.</th>
		<th class="right">$Mat.</th>
		<th class="right">$Enza.</th>
		<th class="right">$Inter.</th>
		<th class="right">$Pagaré</th>
		<th class="right">$Extem.</th>
		<th class="right">$Total</th>
	</tr>
<%
	int row = 0;
	double totGeneral = 0, totMatricula = 0, totEnsenanza = 0, totInternado = 0, totPagare = 0, totExtemporanea = 0;
	
	for (aca.vista.Estadistica inscrito : lisInscritos){
		row++;
		
		String nombreModalidad = "-";
		if (mapModalidad.containsKey(inscrito.getModalidadId())){
			nombreModalidad = mapModalidad.get(inscrito.getModalidadId()).getNombreModalidad();
		}
		String nombreCarrera 	= "-";
		String facultadId		= "000";
		String nombreFacultad	= "-";
		if (mapaCarrera.containsKey(inscrito.getCarreraId())){
			nombreCarrera 	= mapaCarrera.get(inscrito.getCarreraId()).getNombreCarrera();
			facultadId 		= mapaCarrera.get(inscrito.getCarreraId()).getFacultadId();
			if (mapFacultad.containsKey(facultadId)){
				nombreFacultad = mapFacultad.get(facultadId).getNombreCorto();
			}
		}
		String creditos = "0";
		if (mapCreditos.containsKey( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId())){
			creditos = mapCreditos.get( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId());
		}		
		String costoCredito = "0";
		if (mapCostoCredito.containsKey( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId())){
			costoCredito = mapCostoCredito.get( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId());
		}
		String costoMatricula = "0";
		if (mapMovimiento.containsKey( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId()+"01")){
			costoMatricula = mapMovimiento.get( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId()+"01");
			totMatricula += Double.parseDouble(costoMatricula);
		}
		
		String costoEnsenanza = "0";
		if (mapMovimiento.containsKey( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId()+"02")){
			costoEnsenanza = mapMovimiento.get( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId()+"02");
			totEnsenanza += Double.parseDouble(costoEnsenanza);
		}
		
		String costoInternado = "0";
		if (mapMovimiento.containsKey( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId()+"03")){
			costoInternado = mapMovimiento.get( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId()+"03");
			totInternado += Double.parseDouble(costoInternado);
		}
		
		String costoCuarto = "0";
		if (mapMovimiento.containsKey( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId()+"11")){
			costoCuarto = mapMovimiento.get( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId()+"11");
			totInternado += Double.parseDouble(costoCuarto);
		}
		
		String costoComida = "0";
		if (mapMovimiento.containsKey( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId()+"12")){
			costoComida = mapMovimiento.get( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId()+"12");
			totInternado += Double.parseDouble(costoComida);
		}		
		
		String costoPagare = "0";
		if (mapMovimiento.containsKey( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId()+"08")){
			costoPagare = mapMovimiento.get( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId()+"08");
			totPagare += Double.parseDouble(costoPagare);
		}	
		
		String costoPagare2 = "0";
		if (mapMovimiento.containsKey( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId()+"31")){
			costoPagare2 = mapMovimiento.get( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId()+"31");
			totPagare += Double.parseDouble(costoPagare2);
		}
		
		String extemporanea		= "0";
		if (mapMovimiento.containsKey( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId()+"24")){
			extemporanea = mapMovimiento.get( inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId()+"24");
			totExtemporanea += Double.parseDouble(extemporanea);
		}
		
		double costoTotal = Double.parseDouble(costoMatricula)+ Double.parseDouble(costoEnsenanza)+
							Double.parseDouble(costoInternado)+ Double.parseDouble(costoPagare)+
							Double.parseDouble(extemporanea);
		totGeneral += costoTotal;		
%>
	<tr>
		<td><%=row%></td>
		<td><%=inscrito.getCodigoPersonal()%></td>	
		<td><%=inscrito.getNombre()%> <%=inscrito.getApellidoPaterno()%> <%=inscrito.getApellidoMaterno()%></td>
		<td><%=inscrito.getCargaId()%></td>
		<td><%=inscrito.getBloqueId()%></td>
		<td><%=nombreModalidad%></td>
		<td><%=inscrito.getResidenciaId().equals("E")?"Externo":"Interno"%></td>
		<td><%=inscrito.getNacionalidad().equals("91")?"Mexicano":"Extranjero"%></td>
		<td><%=nombreFacultad%></td>
		<td><%=nombreCarrera%></td>
		<td><%=inscrito.getFInscripcion()%></td>
		<td class="right"><%=creditos%></td>
		<td class="right"><%=getFormato.format(Double.parseDouble(costoCredito))%></td>
		<td class="right"><%=getFormato.format(Double.parseDouble(costoMatricula))%></td>
		<td class="right"><%=getFormato.format(Double.parseDouble(costoEnsenanza))%></td>
		<td class="right"><%=getFormato.format(Double.parseDouble(costoInternado)+Double.parseDouble(costoCuarto)+Double.parseDouble(costoComida))%></td>
		<td class="right"><%=getFormato.format(Double.parseDouble(costoPagare)+Double.parseDouble(costoPagare2))%></td>
		<td class="right"><%=getFormato.format(Double.parseDouble(extemporanea))%></td>
		<td class="right"><%=getFormato.format(costoTotal)%></td>
	</tr>
<%	} %>	
	<tr>
		<th colspan="13" style="text-align:right"> T O T A L &nbsp;</th>
		<th class="right"><%=getFormato.format(totMatricula)%></th>
		<th class="right"><%=getFormato.format(totEnsenanza)%></th>
		<th class="right"><%=getFormato.format(totInternado)%></th>
		<th class="right"><%=getFormato.format(totPagare)%></th>
		<th class="right"><%=getFormato.format(totExtemporanea)%></th>
		<th class="right"><%=getFormato.format(totGeneral)%></th>
	</tr>	
	</table>	
</div>
</body>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>
<%@ include file="../../cierra_enoc.jsp"%>