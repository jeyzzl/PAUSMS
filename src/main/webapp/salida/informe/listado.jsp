<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.salida.spring.SalSolicitud"%>
<%@page import="aca.salida.spring.SalGrupo"%>
<%@page import="aca.salida.spring.SalInforme"%>

<%@ include file="../../headPortal.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
	<script type="text/javascript">
		function Mostrar(){
			document.frmBusqueda.submit();
		}
	</script>
</head>	
<%	
	//String codigoPersonal 	= (String)request.getAttribute("codigoPersonal");	
	String fechaIni			= (String)request.getAttribute("fechaIni");
	String fechaFin			= (String)request.getAttribute("fechaFin");	
	
	List<SalInforme> lisInformes 					= (List<SalInforme>)request.getAttribute("lisInformes");	
	HashMap<String,String> mapaMaestros 			= (HashMap<String,String>)request.getAttribute("mapaMaestros");		
	HashMap<String,SalSolicitud> mapaSolicitudes 	= (HashMap<String,SalSolicitud>)request.getAttribute("mapaSolicitudes");
	HashMap<String,SalGrupo> mapaGrupos 			= (HashMap<String,SalGrupo>)request.getAttribute("mapaGrupos");
%>
<body>

<div class="container-fluid">
	<h2>Listado de Informes</h2>
	<form id="frmBusqueda" name="frmBusqueda" method="post" action="listado">
	<input type="hidden" name="Accion">
	<div class="alert alert-info d-flex align-items-center">
		Fecha Inicio: <input type="text" style="width:120px;" class="form-control" data-date-format="dd/mm/yyyy" id="FechaIni" name="FechaIni" value="<%=fechaIni%>" required/>&nbsp;&nbsp;
		Fecha Final: <input type="text" style="width:120px;" class="form-control" data-date-format="dd/mm/yyyy" id="FechaFin" name="FechaFin" value="<%=fechaFin%>" required/>&nbsp;&nbsp;		
		<a class="btn btn-primary btn-small" href="javascript:Mostrar();"><i class="icon-search icon-white"></i> Buscar</a>
	</div>
	</form>
	<table class="table table-sm table-bordered table-striped">
	<tr class="table-info">
		<th width="5%" >#</th>			
		<th width="5%" >Salida</th>
		<th width="15%" >Grupo</th>
		<th width="10%" >Lugar</th>
		<th width="20%" >Incidentes</th>
		<th width="25%" >Observaciones</th>
		<th width="10%" >Fecha</th>
		<th width="10%" >Usuario</th>			
	</tr>				
	<%
	//aquí va un for 
	String usuario = "";
	for (int i = 0; i < lisInformes.size(); i++) {
		SalInforme sol = lisInformes.get(i);
		
		String grupoId 		= "0";
		String grupoNombre 	= "-";
		String lugar		= "-";
		if(mapaSolicitudes.containsKey(sol.getSalidaId()) ){
			lugar 			= mapaSolicitudes.get(sol.getSalidaId()).getLugar();
			grupoId 		= mapaSolicitudes.get(sol.getSalidaId()).getGrupoId();			
			if (mapaGrupos.containsKey(grupoId)){
				grupoNombre = mapaGrupos.get(grupoId).getGrupoNombre();
			}
		}
		
		if(mapaMaestros.containsKey(sol.getUsuario()) ){
			usuario = mapaMaestros.get(sol.getUsuario());
		}
		%>
		<tr>
			<td ><%=i+1%></td>
			<td ><%=sol.getSalidaId() %></td>
			<td ><%=grupoNombre%></td>		
			<td ><%=lugar%></td>
			<td ><%=sol.getIncidentes() %></td>
			<td ><%=sol.getObservaciones() %></td>
			<td ><%=sol.getFecha() %></td>
			<td ><%=usuario%></td>
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
