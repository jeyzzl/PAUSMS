<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.salida.spring.SalSolicitud"%>

<%@ include file="../../headPortal.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="SolicitudU" scope="page" class="aca.salida.SalidaSolicitudUtil" />
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
	<script type="text/javascript">
		function Mostrar(){
			document.frmBusqueda.Accion.value = "5";
			document.frmBusqueda.submit();
		}
	</script>
</head>	
<%
	String codigoPersonal 		= (String)session.getAttribute("codigoPersonal");	
	String accion 				= request.getParameter("Accion")!=null?request.getParameter("Accion"):"0";	
	String fechaIni				=  request.getParameter("FechaIni")==null?"01/01/"+aca.util.Fecha.getHoy().substring(6,10):request.getParameter("FechaIni");
	String fechaFin				=  request.getParameter("FechaFin")==null?"31/12/"+aca.util.Fecha.getHoy().substring(6,10):request.getParameter("FechaFin");
	
	ArrayList<SalSolicitud> lisSalidas			= (ArrayList<SalSolicitud>)request.getAttribute("lisSalidas");
	HashMap<String,String> mapaGrupoNombre 		= (HashMap<String,String>)request.getAttribute("mapaGrupoNombre");
	HashMap<String,String> mapaMaestros 		= (HashMap<String,String>)request.getAttribute("mapaMaestros");
%>
<body>

<div class="container-fluid">
	<h2>Listado de Salidas</h2>
	<form id="frmBusqueda" name="frmBusqueda" method="post" action="salidas">
	<input type="hidden" name="Accion">
	<div class="alert alert-info">
		Fecha Inicio: <input type="text" class="input input-medium" data-date-format="dd/mm/yyyy" id="FechaIni" name="FechaIni" value="<%=fechaIni%>" required/>&nbsp;&nbsp;
		Fecha Final: <input type="text" class="input input-medium" data-date-format="dd/mm/yyyy" id="FechaFin" name="FechaFin" value="<%=fechaFin%>" required/>&nbsp;&nbsp;
		<a class="btn btn-primary btn-small" href="javascript:Mostrar();"><i class="icon-search icon-white"></i> Buscar</a>
	</div>
	</form>
	<table class="table table-condensed">
		<tr>
			<th width="5%" class="center">#</th>
			<th width="10%" class="left">Grupos</th>
			<th width="10%" style="text-align:center">Salida</th>
			<th width="10%" style="text-align:center">Llegada</th>
			<th width="10%" style="text-align:left">Responsable</th>
			<th width="10%" style="text-align:left">Lugar</th>
			<th width="3%" style="text-align:left">Des.</th> 
			<th width="3%" style="text-align:left">Com.</th>
			<th width="4%" style="text-align:left">Cena</th>
			<th width="5%" style="text-align:center">PDF</th>
			<th width="8%" style="text-align:center">Estado</th>
		</tr>
<%	
	int row = 0;
	for ( SalSolicitud salida : lisSalidas ) {
		row++;
		
		String grupoNombre = "";			
		if(mapaGrupoNombre.containsKey(salida.getGrupoId())){
			grupoNombre = mapaGrupoNombre.get(salida.getGrupoId());
		}
		
		String maestroNombre = "";
		if (mapaMaestros.containsKey(salida.getResponsable())){
			maestroNombre = mapaMaestros.get(salida.getResponsable());
		}
	
		String 	estado ="";
		if (salida.getAutorizo().equals("0") && salida.getFolio().equals("0")){
			estado="<span class='badge badge-warning'>Solicitud</span>";
		}
		else if(!salida.getAutorizo().equals("0") && salida.getFolio().equals("0")){
			estado="<span class='badge badge-info'>Preautorizado</span>";
		}
		else if(!salida.getAutorizo().equals("0") && !salida.getFolio().equals("0")){
			estado="<span class='badge badge-success'>Autorizado</span>";
		}
%>
		<tr>
			<td style="text-align: center"><%=row%></td>
			<td class="left"><%=grupoNombre%></td>
			<td style="text-align:center"><%=salida.getFechaSalida()%></td>
			<td style="text-align:center"><%=salida.getFechaLlegada()%></td>
			<td class="left"><%=maestroNombre%></td>
			<td class="left"><%=salida.getLugar()%></td>
			<td class="left"><%=salida.getDesayuno()%></td>
			<td class="left"><%=salida.getComida()%></td>
			<td class="left"><%=salida.getCena()%></td>
			<td class="center"><a class="btn btn-info" href="../solicitud/pdf?salida=<%=salida.getSalidaId()%>">PDF</a></td>
			<td class="center"><%=estado%></td>	
		</tr>
			<%
			}
		%>
	</table>	
</div>
</body>
<script type="text/javascript"> 
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
</script>