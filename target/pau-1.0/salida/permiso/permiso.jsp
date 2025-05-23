<%@ page import= "java.sql.Timestamp"%>
<%@ page import= "java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.salida.spring.SalSolicitud"%>
<%@page import="aca.salida.spring.SalInforme"%>


<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<html>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
	<script type="text/javascript">
	
	function Mostrar(){
		document.frmBusqueda.Accion.value = "5";
		document.frmBusqueda.submit();
	}
	
	function Permiso(salidaId,permiso,filtro) {
		var texto = "";		
		if(permiso === 'S'){
			texto = "Estás seguro de dar permiso a esta solicitud de salida?";
		}else{
			texto = "Estás seguro de quitar permiso a esta solicitud de salida?";
		}
		
		if (confirm(texto) == true) {
			document.location.href = "grabarPermiso?salida="+salidaId+"&permiso="+permiso+"&FiltroEstado="+filtro;
		}
	}
	
	</script>	
</head>
	
<%
	String codigoPersonal 	= (String)session.getAttribute("codigoPersonal");
	boolean esAdmin 		= (boolean)request.getAttribute("esAdmin");
	boolean autorizar 		= (boolean)request.getAttribute("autorizar");
	
	String fechaIni			= request.getParameter("FechaIni")==null?"01/01/"+aca.util.Fecha.getHoy().substring(6,10):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?"31/12/"+aca.util.Fecha.getHoy().substring(6,10):request.getParameter("FechaFin");
	String filtroEstado 	= request.getParameter("FiltroEstado")==null?"0":request.getParameter("FiltroEstado");
	SimpleDateFormat sdf 	= new SimpleDateFormat("dd/MM/yyyy");
	String fechaHoy 		= aca.util.Fecha.getHoy();	
    Date hoy 				= sdf.parse(fechaHoy);	
	Timestamp timestamp 	= new Timestamp(System.currentTimeMillis());
	
	List<SalSolicitud> lisSolicitud 		= (List<SalSolicitud>)request.getAttribute("lisSolicitud");
	
	HashMap<String,String> mapaGrupoNombre 	= (HashMap<String,String>)request.getAttribute("mapaGrupoNombre");
	HashMap<String,String> mapaMaestros 	= (HashMap<String,String>)request.getAttribute("mapaMaestros");
	HashMap<String,SalInforme> mapaInformes = (HashMap<String,SalInforme>)request.getAttribute("mapaInformes");
	
	int dias 			= 0;
	int diasImplicados 	= 0;
	
%>
<body>
<div class="container-fluid">
<h2>Autorización de salidas</h2>
 	<form id="frmBusqueda" name="frmBusqueda" method="post" action="permiso">
	<input type="hidden" name="Accion">
	<div class="alert alert-info d-flex align-items-center">
		Inicio: <input type="text" class="form-control" data-date-format="dd/mm/yyyy" id="FechaIni" name="FechaIni" value="<%=fechaIni%>" style="width:120px;" required/>&nbsp;&nbsp;
		Fin: <input type="text" class="form-control" data-date-format="dd/mm/yyyy" id="FechaFin" name="FechaFin" value="<%=fechaFin%>" style="width:120px;" required/>&nbsp;&nbsp;
		Estado: <select name="FiltroEstado" id="FiltroEstado" class="form-select" style="width:150px;">	
					<option value="0" <%=filtroEstado.equals("0")?"Selected":""%>>Todas</option>				
					<option value="S" <%=filtroEstado.equals("S")?"Selected":""%>>Solicitud</option>
					<option value="E" <%=filtroEstado.equals("E")?"Selected":""%>>Enviada</option>
					<option value="P" <%=filtroEstado.equals("P")?"Selected":""%>>Preautorizado</option>
					<option value="A" <%=filtroEstado.equals("A")?"Selected":""%>>Autorizado</option>
				</select>&nbsp;&nbsp;
<%-- 	Estado: <input type="text" class="input input-medium"  value="<%=filtroEstado%>" required/>&nbsp;&nbsp; --%>
		<a class="btn btn-primary btn-small" href="javascript:Mostrar();"><i class="fas fa-sync-alt"></i></a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar en la tabla" id="buscar" style="width:200px;">
	</div>
	</form>
	<table id="table" class="table table-sm table-bordered table-striped">
		<tr class="table-info">
			<th width="3%" style="text-align:center">#</th>
			<th width="5%" style="text-align:center">Folio</th>
			<th width="5%" style="text-align:center">Fecha Salida</th>
			<th width="7%" style="text-align:center">Hora Salida</th>
			<th width="5%" style="text-align:center">Fecha Llegada</th>
			<th width="7%" style="text-align:center">Hora Llegada</th>
			<th width="12%">Grupo</th>
			<th width="12%">Lugar</th>
			<th width="12%">Usuario/Registro</th>
			<th width="12%">Responsable/actividad</th>
			<th width="5%" style="text-align:center">PDF</th>
			<th width="8%" style="text-align:center">Estado</th>
<% 		if(codigoPersonal.equals("9800308") || codigoPersonal.equals("9801161")){%>
			<th width="8%" style="text-align:center">Permiso</th>
<% 		}%>
			<th width="8%" style="text-align:center">Autorizó</th>
			<th width="5%" style="text-align:center">Informe</th>
		</tr>
<%		
	int i = 0;
	for (SalSolicitud sol : lisSolicitud) {
		String estado = "";
		String filtro = "";
		if (sol.getPreautorizo().equals("0") && sol.getAutorizo().equals("0") && sol.getFolio().equals("0")){
			filtro = "S";
			if (sol.getEstado().equals("S")){
				estado="<span class='badge bg-warning'>Solicitud</span>";
			}else{
				filtro = "E";
				estado="<span class='badge bg-success'>Enviada</span>";
			}
		}else if(!sol.getPreautorizo().equals("0") && sol.getFolio().equals("0")){
			filtro = "P";
			estado="<span class='badge bg-info'>Preautorizado</span>";
		}else if(!sol.getAutorizo().equals("0") && !sol.getFolio().equals("0")){
			filtro = "A";
			estado="<span class='badge bg-dark'>Autorizado</span>";
		}
		
		if(!filtro.equals(filtroEstado) && !filtroEstado.equals("0")){
			continue;
		}
		
		String fechaSalida 			= sol.getFechaSalida().substring(0, sol.getFechaSalida().indexOf(" "));
		String horaSalida 			= sol.getFechaSalida().substring(sol.getFechaSalida().indexOf(" "), sol.getFechaSalida().lastIndexOf(":"));
		String fechaLlegada 		= sol.getFechaLlegada().substring(0, sol.getFechaLlegada().indexOf(" "));
		String horaLlegada 			= sol.getFechaLlegada().substring(sol.getFechaLlegada().indexOf(" "), sol.getFechaLlegada().lastIndexOf(":"));
		
		boolean pernoctar 			= false;
		boolean solicitudCorrecta 	= false;								
		Date salidaTmp	 			= sdf.parse(fechaSalida);
		Date llegadaTmp	 			= sdf.parse(fechaLlegada);				
		int diasLlegada	 			= new Long((llegadaTmp.getTime()-salidaTmp.getTime())/1000/60/60/24).intValue();
		dias 						= new Long((salidaTmp.getTime()-hoy.getTime())/1000/60/60/24).intValue();
		int daysApart 				= (int)((salidaTmp.getTime() - hoy.getTime()) / (1000*60*60*24l));				
		if(diasLlegada > 0){
			pernoctar = true;
		}
		
		if(sol.getPaisId().equals("91") && sol.getEstadoId().equals("19")){
			if(pernoctar){
				diasImplicados = 14;
			}else{
				diasImplicados = 3;
			}
		}else if(sol.getPaisId().equals("91")){
			if(pernoctar){
				diasImplicados = 14;
			}else{
				diasImplicados = 7;
			}
		}else {
			if(pernoctar){
				diasImplicados = 20;
			}else{
				diasImplicados = 7;
			}
		}
		
		if(diasImplicados <= dias){
			solicitudCorrecta = true;
		}
		//System.out.println("Salida:"+sol.getSalidaId()+":"+diasImplicados+":"+dias+" Apart:"+daysApart);
		
		String grupoNombre = "";			
		if(mapaGrupoNombre.containsKey(sol.getGrupoId())){
			grupoNombre = mapaGrupoNombre.get(sol.getGrupoId());
		}
		
		String maestroNombre = "";
		if (mapaMaestros.containsKey(sol.getResponsable())){
			maestroNombre = mapaMaestros.get(sol.getResponsable());
		}	
		
		String existeInforme = "<span class='badge bg-warning'><a style='color:white'>NO</a></span>";
	    if (mapaInformes.containsKey(sol.getSalidaId())){
	    	existeInforme = "<span class='badge bg-info'><a href='verInforme?SalidaId="+sol.getSalidaId()+"' style='color:white'>SI</a></span>";
	    }
%>
		<tr>
			<td style="text-align:center"><%=i+1%></td>
			<td style="text-align:center"><span class="badge bg-dark"><%=sol.getSalidaId()%></span></td>
			<td style="text-align:center"><%=fechaSalida%></td>
			<td style="text-align:center"><%=horaSalida%></td>
			<td style="text-align:center"><%=fechaLlegada%></td>
			<td style="text-align:center"><%=horaLlegada%></td>
			<td ><%=grupoNombre%></td>
			<td><%=sol.getLugar()%></td>
			<td><%=sol.getUsuario()%></td>
			<td><%=maestroNombre%></td>
			<td style="text-align:center"><a class="btn btn-info" href="../solicitud/pdf?salida=<%=sol.getSalidaId()%>">PDF</a></td>
			<td style="text-align:center"><%=estado%></td>
<% 		if(codigoPersonal.equals("9800308") || codigoPersonal.equals("9801161") || codigoPersonal.equals("9800058")){%>
			<td style="text-align:center;">	
				<div class="btn-group" role="group" aria-label="Basic radio toggle button group">
<% 			if(sol.getPermiso().equals("S")){%>
			  		<a class="btn btn-success">Si</a>
			  		<a class="btn btn-outline-danger" onclick="javascript:Permiso('<%=sol.getSalidaId()%>','N','<%=filtroEstado%>')">No</a>
<% 			}else {%>
			  		<a class="btn btn-outline-success" onclick="javascript:Permiso('<%=sol.getSalidaId()%>','S','<%=filtroEstado%>')">Si</a>
			  		<a class="btn btn-danger">No</a>
<% 			}%>					
				</div>
				<%=sol.getPermiso()%>
			</td>
<% 		}%>
			<td style="text-align:center">
<%		if (sol.getPreautorizo()==null || sol.getPreautorizo().equals("0")){
			if(sol.getEstado().equals("E") || sol.getPermiso().equals("S")){
%>
				<a class="btn btn-sm btn-danger" href="regresar?SalidaId=<%=sol.getSalidaId()%>">Regresar</a>
<% 					if( (solicitudCorrecta && !sol.getUsuario().equals(codigoPersonal)) || (codigoPersonal.equals("9801161") || codigoPersonal.equals("9800058") || codigoPersonal.equals("9800308")) ){ %>
				<a class="btn btn-sm btn-success" href="permiso?salida=<%=sol.getSalidaId()%>&Accion=1">Preautorizar</a>
<% 					}else if (!sol.getUsuario().equals(codigoPersonal)){%>
				<a class="btn btn-sm btn-warning" href="javascript:diasInvalidos();">Preautorizar</a>
<% 					}
			}%>
	<%	}else if (sol.getFolio()==null||sol.getFolio().equals("0")){%>
				<a class="btn btn-sm btn-danger" href="permiso?salida=<%=sol.getSalidaId()%>&Accion=2">Cancelar</a>
	<% 		if(autorizar){
					out.print("<a class='btn btn-sm btn-success' href='permiso?salida="+sol.getSalidaId()+"&Accion=3'>Autorizar</a>");
			}%>
	<%	}else{
				out.println("<b>"+sol.getFolio()+"</b> ["+sol.getAutorizo()+"]");
			if(autorizar){
				out.print("<a class='btn btn-sm btn-danger' href='permiso?salida="+sol.getSalidaId()+"&Accion=4'>Cancelar</a>");
			}
		}
%>
			</td>
			<td style="text-align:center;"><%=existeInforme%></td>
		</tr>
<%
		i++;
		}
%>
	</table>
</div>
</body>
<script src="../../js/search.js"></script>
<script type="text/javascript"> 
	$('#buscar').search();
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
</script>
	
