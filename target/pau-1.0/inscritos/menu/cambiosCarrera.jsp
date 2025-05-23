<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ page import="aca.util.Fecha"%>
<%@ page import="java.util.*"%>

<jsp:useBean id="EstadisticaU" scope="page" class="aca.vista.EstadisticaUtil"/>
<jsp:useBean id="CarreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="PlanU" scope="page" class="aca.alumno.PlanUtil"/>

<script type="text/javascript">
	function Mostrar(){	
		document.forma.Accion.value = 1;
		document.forma.submit();
	}
</script>
<%
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	java.text.DecimalFormat getFormato2	= new java.text.DecimalFormat("Cr ###,##0.00; Db ###,##0.00");
	
	String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");

	String cargas				= (String)session.getAttribute("cargas") == null?aca.carga.CargaUtil.getCargasActivas(conEnoc, aca.util.Fecha.getHoy()):session.getAttribute("cargas").toString();
	String modalidades			= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
	String fechaIni				= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin				= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	String lisModo[] 			= modalidades.replace("'", "").split(",");	session.setAttribute("fechaFin", fechaFin);
	String muestraModalidades 	= "";
	
	// Lista de alumnos inscritos en el rango de fechas
	ArrayList<aca.vista.Estadistica> lisAlumnos			=  new ArrayList<aca.vista.Estadistica>();
	// Mapa que trae las carreras
	java.util.HashMap<String,aca.catalogo.CatCarrera> mapCarreras	= new HashMap<String,aca.catalogo.CatCarrera>();
	
	if(accion.equals("1")){
		lisAlumnos			= EstadisticaU.lisCambioCarreraPorFechas(conEnoc, fechaIni, fechaFin, cargas, "'I','1','2','4','5','6'", " ORDER BY CODIGO_PERSONAL");
	
		mapCarreras	= CarreraU.getMapAll(conEnoc, "");
	}
	
	
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
		<h2>Alumnos con cambios de carrera<small class="text-muted fs-5">(Cargas : <%=cargas.replace("'","") %> | Modalidades : <%=muestraModalidades %> )</small></h2>
		<form id="forma" name="forma" action="cambiosCarrera?" method="post">
			<input type="hidden" name="Accion">
			<div class="alert alert-info">
				<a href="menu" class="btn btn-primary">Menú</a>
				Fecha Inicio: <input id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10"/>&nbsp;&nbsp;
				Fecha Final: <input id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10"/>&nbsp;&nbsp;
				<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>
			</div>	
		</form>
		
		<table class="table">
			<tr>
				<th>#</th>
				<th>Matricula</th>
				<th>Nombre</th>
				<th>Carga</th>
				<th>Modalidad</th>
				<th>Carrera</th>
				<th>Carreras registradas</th>
			</tr>
<%
		boolean validaCurp = false;

		int cont = 0;
		for (aca.vista.Estadistica alumno : lisAlumnos){
			cont++;
			
			String nombreModalidad 	= aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, alumno.getModalidadId());
			String nombreCarga		= aca.carga.CargaUtil.getNombreCarga(conEnoc, alumno.getCargaId());
			String nombreCarrera 	= aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, alumno.getCarreraId());
			String nivelId			= "";
			String carreras 		= "";
			
			if(mapCarreras.containsKey(alumno.getCarreraId())){	
				
				nivelId = mapCarreras.get(alumno.getCarreraId()).getNivelId();
				
				ArrayList<String> idCarreras = PlanU.getLisPorMatriculayNivel(conEnoc, alumno.getCodigoPersonal(), nivelId);
				
				if(idCarreras.size() > 1){
					int contador = 0;
					for (String ca : idCarreras){
						contador++;
						carreras += ca+" - "+aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, ca);
						if(idCarreras.size() > contador){
							carreras += ", ";
						}
					}	
%>		
					<tr>
						<td><%=cont%></td>
						<td><%=alumno.getCodigoPersonal()%></td>
						<td><%=alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()%></td>
						<td><%=nombreCarga%></td>
						<td><%=nombreModalidad%></td>
						<td><%=nombreCarrera%></td>
						<td><%=carreras%></td>
					</tr>
<%
				}				
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