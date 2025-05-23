<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.financiero.spring.FesCcobro"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Mostrar(){
		document.forma.submit();
	}
</script>
<%	
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");	
	String year					= aca.util.Fecha.getHoy().substring(6);	
	String fechaIni				= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin				= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	
	List<FesCcobro> lisBajas 						= (List<FesCcobro>) request.getAttribute("lisBajas");
	
	HashMap<String,CatFacultad> mapaFacultades 		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaMovimientos 			= (HashMap<String,String>) request.getAttribute("mapaMovimientos");
%>
<head>	
	<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
</head>
<body>
<div class="container-fluid">
	<h2>Bajas de alumnos</h2>
	<form id="forma" name="forma" action="bajatotal" method="post">
	<div class="alert alert-info">
		<a href="menu" class="btn btn-primary">Menú</a>
		Fecha Inicio: <input id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
		Fecha Final: <input id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
		<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>
	</div>
	</form>
	<table class="table table-sm table-bordered table-striped">
	<tr class="table-info">
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
	
	for (FesCcobro inscrito : lisBajas){
		row++;
		
		String nombreCarrera 	= "-";
		String facultadId		= "000";
		String nombreFacultad	= "-";
		if (mapaCarreras.containsKey(inscrito.getCarreraId())){
			nombreCarrera 	= mapaCarreras.get(inscrito.getCarreraId()).getNombreCarrera();
			facultadId 		= mapaCarreras.get(inscrito.getCarreraId()).getFacultadId();
			if (mapaFacultades.containsKey(facultadId)){
				nombreFacultad = mapaFacultades.get(facultadId).getNombreCorto();
			}
		}
		
		String costoMatricula = "0";
		if (mapaMovimientos.containsKey( inscrito.getMatricula()+inscrito.getCargaId()+inscrito.getBloque()+"01")){
			costoMatricula = mapaMovimientos.get( inscrito.getMatricula()+inscrito.getCargaId()+inscrito.getBloque()+"01");
			totMatricula += Double.parseDouble(costoMatricula);
		}
		
		String costoEnsenanza = "0";
		if (mapaMovimientos.containsKey( inscrito.getMatricula()+inscrito.getCargaId()+inscrito.getBloque()+"02")){
			costoEnsenanza = mapaMovimientos.get( inscrito.getMatricula()+inscrito.getCargaId()+inscrito.getBloque()+"02");
			totEnsenanza += Double.parseDouble(costoEnsenanza);
		}
		
		String costoInternado = "0";
		if (mapaMovimientos.containsKey( inscrito.getMatricula()+inscrito.getCargaId()+inscrito.getBloque()+"03")){
			costoInternado = mapaMovimientos.get( inscrito.getMatricula()+inscrito.getCargaId()+inscrito.getBloque()+"03");
			totInternado += Double.parseDouble(costoInternado);
		}
		
		String costoCuarto = "0";
		if (mapaMovimientos.containsKey( inscrito.getMatricula()+inscrito.getCargaId()+inscrito.getBloque()+"11")){
			costoCuarto = mapaMovimientos.get( inscrito.getMatricula()+inscrito.getCargaId()+inscrito.getBloque()+"11");
			totInternado += Double.parseDouble(costoCuarto);
		}
		
		String costoComida = "0";
		if (mapaMovimientos.containsKey( inscrito.getMatricula()+inscrito.getCargaId()+inscrito.getBloque()+"12")){
			costoComida = mapaMovimientos.get( inscrito.getMatricula()+inscrito.getCargaId()+inscrito.getBloque()+"12");
			totInternado += Double.parseDouble(costoComida);
		}		
		
		String costoPagare = "0";
		if (mapaMovimientos.containsKey( inscrito.getMatricula()+inscrito.getCargaId()+inscrito.getBloque()+"08")){
			costoPagare = mapaMovimientos.get( inscrito.getMatricula()+inscrito.getCargaId()+inscrito.getBloque()+"08");
			totPagare += Double.parseDouble(costoPagare);
		}	
		
		String costoPagare2 = "0";
		if (mapaMovimientos.containsKey( inscrito.getMatricula()+inscrito.getCargaId()+inscrito.getBloque()+"31")){
			costoPagare2 = mapaMovimientos.get( inscrito.getMatricula()+inscrito.getCargaId()+inscrito.getBloque()+"31");
			totPagare += Double.parseDouble(costoPagare2);
		}
		
		String extemporanea		= "0";
		if (mapaMovimientos.containsKey( inscrito.getMatricula()+inscrito.getCargaId()+inscrito.getBloque()+"24")){
			extemporanea = mapaMovimientos.get( inscrito.getMatricula()+inscrito.getCargaId()+inscrito.getBloque()+"24");
			totExtemporanea += Double.parseDouble(extemporanea);
		}
		
		double costoTotal = Double.parseDouble(costoMatricula)+ Double.parseDouble(costoEnsenanza)+
							Double.parseDouble(costoInternado)+ Double.parseDouble(costoPagare)+
							Double.parseDouble(extemporanea);
		totGeneral += costoTotal;		 
%>
	<tr>
		<td><%=row%></td>
		<td><%=inscrito.getMatricula()%></td>	
		<td><%=inscrito.getNombre()%></td>
		<td><%=inscrito.getCargaId()%></td>
		<td><%=inscrito.getBloque()%></td>
		<td><%=inscrito.getModalidad() %></td>
		<td><%=inscrito.getResidencia().equals("E")?"Externo":"Interno"%></td>
		<td><%=inscrito.getNacionalidad()%></td>
		<td><%=nombreFacultad%></td>
		<td><%=nombreCarrera%></td>
		<td><%=inscrito.getFecha()%></td>		
		<td class="right"><%=getFormato.format(Double.parseDouble(costoMatricula))%></td>
		<td class="right"><%=getFormato.format(Double.parseDouble(costoEnsenanza))%></td>
		<td class="right"><%=getFormato.format(Double.parseDouble(costoInternado)+Double.parseDouble(costoCuarto)+Double.parseDouble(costoComida))%></td>
		<td class="right"><%=getFormato.format(Double.parseDouble(costoPagare)+Double.parseDouble(costoPagare2))%></td>
		<td class="right"><%=getFormato.format(Double.parseDouble(extemporanea))%></td>
		<td class="right"><%=getFormato.format(costoTotal)%></td>
	</tr>
<%	} %>	
	<tr>
		<th colspan="11" style="text-align:right"> T O T A L &nbsp;</th>
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
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>