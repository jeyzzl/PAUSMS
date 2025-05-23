<%@ page import="java.util.HashMap"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Mostrar(){
		document.forma.submit();
	}
	
	function Trayectoria(){
		document.getElementById("cargando").style.display = "block";
		location.href = "trayectoria";
	}
</script>
<%
	String periodoId 		= (String)request.getAttribute("periodoId");
	String cargas			= (String)request.getAttribute("cargas");
	String modalidades		= (String)request.getAttribute("modalidades");
	
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
	
	if (accion.equals("1")){		
		session.setAttribute("fechaIni", fechaIni);
		session.setAttribute("fechaFin", fechaFin);
	}
	
	HashMap<String,String> mapaModalidades = (HashMap<String,String>)request.getAttribute("mapaModalidades");
	
	if(modalidades.equals(""))modalidades="' '";
	if(cargas.equals(""))cargas="' '";		
	
	String lisModo[] 		= modalidades.replace("'", "").split(",");	session.setAttribute("fechaFin", fechaFin);
%>

<html>
<head>		
	<link rel="stylesheet" href="../../js/datepicker/datepicker.css"/>
</head>	
<body>
	<div class="container-fluid">
		<h2>Reportes de Inscritos</h2>
		<form id="forma" name="forma" action="menu?Accion=1" method="post">
			<div class="alert alert-info">
			<b>Cargas:</b> <%= cargas.replace("'", "")%>&nbsp;<a href="cargas" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;
			<b>Modalidades:</b>
<%
			for(String mod:lisModo){
				String nombreModalidad = "";
				if (mapaModalidades.containsKey(mod)){
					nombreModalidad = mapaModalidades.get(mod);
				}
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
		</form>
		<span id="cargando" style="display: none;">
			<button class="btn btn-success" type="button" disabled>
	  			<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
	 			Cargando...
			</button>
			<br>
		</span>
		<br>
		<div class="row-fluid">          	
            <ul class="list-group">
              <li class="list-group-item"><a href="creditosFecha"><i class="fas fa-arrow-circle-right"></i>Créditos por fecha</a></li>
              <li class="list-group-item"><a href="inscritosgcas"><i class="fas fa-arrow-circle-right"></i>Ventas por fechas(Carreras)</a></li>
              <li class="list-group-item"><a href="ventasgcas"><i class="fas fa-arrow-circle-right"></i>Ventas por fechas(Detallado)</a></li>
              <li class="list-group-item"><a href="bajatotal"><i class="fas fa-arrow-circle-right"></i>Bajas totales por año</a></li>
              <li class="list-group-item"><a href="altasbajas"><i class="fas fa-arrow-circle-right"></i>Altas/Bajas por año</a></li>	              
              <li class="list-group-item"><a href="ventas"><i class="fas fa-arrow-circle-right"></i>Inscritos (cargas, modalidades y costos)</a></li>
              <li class="list-group-item"><a href="curpIncorrecta"><i class="fas fa-arrow-circle-right"></i>Alumnos con datos incorrectos(CURP)</a></li>
              <li class="list-group-item"><a href="cambiosCarrera"><i class="fas fa-arrow-circle-right"></i>Alumnos con cambios de carrera </a></li>
              <li class="list-group-item"><a href="semaforizacion"><i class="fas fa-arrow-circle-right"></i>Semaforización</a></li>
              <li class="list-group-item"><a href="semaforizacion_alumno"><i class="fas fa-arrow-circle-right"></i>Semaforización alumnos</a></li>
              <li class="list-group-item"><a href="javascript:Trayectoria();"><i class="fas fa-arrow-circle-right"></i>Alumnos/Trayectoria</a></li>
            </ul>        	
		</div>
	</div>
</body>
</html>
<style>
	body{
		background:white;
	}
</style>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>