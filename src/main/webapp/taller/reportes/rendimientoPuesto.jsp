<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.afe.FesCcAfeAcuerdosUtil"%>
<%@page import="aca.bec.BecPuestoAlumno"%>
<%@page import="aca.bec.BecCategoriaUtil"%>
<%@page import="aca.financiero.ContCcosto"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean scope="page" id="BecPuestoAlumnoU" class="aca.bec.BecPuestoAlumnoUtil"/>
<jsp:useBean scope="page" id="BecPuestoU" class="aca.bec.BecPuestoUtil"/>
<jsp:useBean scope="page" id="ContEjercicioU" class="aca.financiero.ContEjercicioUtil" />
<jsp:useBean scope="page" id="CarreraU" class="aca.catalogo.CatCarreraUtil" />
<jsp:useBean scope="page" id="AlumnoU" class="aca.alumno.AlumUtil"/>
<jsp:useBean scope="page" id="CatCarreraUtil" class="aca.catalogo.CatCarreraUtil"/>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<%
	
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String ejercicioSesion		= (String) session.getAttribute("ejercicioId");
	String ejercicioId			= request.getParameter("ejercicioId")==null?ejercicioSesion:request.getParameter("ejercicioId");
	String fechaHoy 			= aca.util.Fecha.getHoy();
	String fecha 				= request.getParameter("fechaParametro")==null?fechaHoy:request.getParameter("fechaParametro");	
	
	//Ejercicios a partir del 2013
	ArrayList<aca.financiero.ContEjercicio> lisEjercicios 	= ContEjercicioU.getListProximos(conEnoc, " ORDER BY ID_EJERCICIO DESC");	
	
	//Alumnos con puesto en el ejercicio y fecha	
	ArrayList<aca.bec.BecPuestoAlumno> lisAlumPuesto		= BecPuestoAlumnoU.getListAllEjercicio(conEnoc, ejercicioId, "AND FECHA_INI<= TO_DATE('"+fecha+"', 'DD/MM/YYYY') AND FECHA_FIN>=TO_DATE('"+fecha+"', 'DD/MM/YYYY') ORDER BY ID_CCOSTO");
	
	// Map de plazas en el ejercicio
	HashMap <String, aca.bec.BecPuesto> mapPuestos			= BecPuestoU.mapPuestos(conEnoc, ejercicioId);
	
	// Map de alumnos
	HashMap <String, aca.alumno.AlumPersonal> mapAlumno		= AlumnoU.mapBecadosFecha(conEnoc, fecha);
	
	//Map Promedio de alumno
	java.util.HashMap <String, String> mapEvalAlumno 		= aca.bec.BecInformeAlumnoUtil.mapEvaluacionEnPuesto(conEnoc, fecha);	
	
	// Map de departamentos
	HashMap <String, ContCcosto> ccostos					= aca.financiero.ContCcostoUtil.getMapCentrosCosto(conEnoc, ejercicioId);
	
	//Nombres de categorías
	HashMap <String, String> mapCategorias					= aca.bec.BecCategoriaUtil.getMapCategorias(conEnoc);
	
	//Nombres de categorías
	HashMap <String, aca.catalogo.CatCarrera> mapCarrera	= CatCarreraUtil.getMapAll(conEnoc, "");

%>
<div class="container-fluid">
	<h1>Rendimiento en el Puesto</h1>
	<form name="frmPuestos" id="frmPuestos" method="post" action="rendimientoPuesto">
		<div class="alert alert-info">
			<a class="btn btn-primary" href="menu"><i class="fas fa-arrow-left"></i></a>			
			<div style="float:right">
				<select style="position: right;" id="ejercicioId" name="ejercicioId" onchange="document.frmPuestos.submit()">
				
<%				for(aca.financiero.ContEjercicio ej : lisEjercicios){
						
		%>
					<option value="<%=ej.getIdEjercicio()%>" <%if(ejercicioId.equals(ej.getIdEjercicio())){out.print("selected");}%>><%=ej.getIdEjercicio()%></option>
											
<%				}%>				
				</select>
				&nbsp; &nbsp; 
				Fecha de revisión <input type="text" data-date-format="dd/mm/yyyy" id="fechaParametro" name="fechaParametro" value="<%=fecha%>"/>
				&nbsp;&nbsp;
				<a class="btn btn-success" onclick="javascript:document.frmPuestos.submit();">Cargar fecha</a>
						
			</div>
		</div>
	</form>	
	<table class="table table-condensed table table-nohover" style="width:100%;">
		<tr>
			<th style="width:5%;">#</th>
			<th style="width:5%;"><spring:message code="aca.Matricula"/></th>
			<th style="width:15%;"><spring:message code="aca.Alumno"/></th>
			<th style="width:20%;"><spring:message code="aca.Carrera"/></th>
			<th style="width:25%;">CCosto</th>
			<th style="width:15%;">Puesto</th>
			<th style="width:5%;">Evaluación</th>
			<th style="width:10%;">Competencias</th>
		</tr>		
<%
		int con = 1;		 
		if(!lisAlumPuesto.isEmpty()){
			for(aca.bec.BecPuestoAlumno puesto : lisAlumPuesto){
				
				String nombreAlumno = "";
				String generoAlumno = "";
				if (mapAlumno.containsKey( puesto.getCodigoPersonal() )){
					aca.alumno.AlumPersonal alumno = (aca.alumno.AlumPersonal) mapAlumno.get( puesto.getCodigoPersonal() );
					nombreAlumno = alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno();
					generoAlumno = alumno.getSexo();
				}
				
				String fechaIni = puesto.getFechaIni();
				if(fechaIni==null){
					fechaIni = "01/01/1900";
				}
				String fechaFin = puesto.getFechaFin();
				if(fechaFin==null){
					fechaFin = "01/01/1901";
				}
				String fechaActual = aca.util.Fecha.getHoy();
				String fechaParam = fecha;
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				
				java.util.Date dateIni  = sdf.parse(fechaIni, new ParsePosition(0));
				java.util.Date dateFin  = sdf.parse(fechaFin, new ParsePosition(0));
				java.util.Date dateAct  = sdf.parse(fechaParam, new ParsePosition(0));
				
				double prom = 0;
				if(mapEvalAlumno.containsKey(puesto.getCodigoPersonal()+puesto.getPuestoId())){
					prom = Double.parseDouble(mapEvalAlumno.get(puesto.getCodigoPersonal()+puesto.getPuestoId()));					
				}
				
				String competencias = "-";				
				if (mapPuestos.containsKey(puesto.getIdEjercicio()+puesto.getIdCcosto()+puesto.getCategoriaId()+puesto.getPeriodoId())){
					competencias = mapPuestos.get(puesto.getIdEjercicio()+puesto.getIdCcosto()+puesto.getCategoriaId()+puesto.getPeriodoId()).getCompetencias();
				}
				
				String categoria	= "";
				if(mapCategorias.containsKey(puesto.getCategoriaId())){
					categoria = mapCategorias.get(puesto.getCategoriaId());
				}				
				
				String carrera 	= aca.alumno.PlanUtil.getCarreraIdPLAN(conEnoc, puesto.getPlanId());				
				if(mapCarrera.containsKey(carrera)){					
					carrera = mapCarrera.get(carrera).getNombreCarrera();
				}
%>	
		<tr>
			<td><%=con%></td>
			<td><%=puesto.getCodigoPersonal()%></td>
			<td><%=nombreAlumno%></td>
			<td><%=carrera%></td>
			<td><%=ccostos.get(puesto.getIdCcosto()).getNombre() %></td>
			<td><%= categoria %></td>
			<td class="right"><%=getFormato.format(prom)%></td>
			<td><%=competencias%></td>
		</tr>
<%				con++;
			}
		}else{
%>		
		<tr>
			<td></td>
			<td colspan="8" ><strong>No hay alumnos registrados en puestos con el ejercicio y fecha seleccionados</strong></td>
		</tr>							
<%		}%>
			
	</table>	
</div>
<script>
	jQuery('#fechaParametro').datepicker();
</script>
 <style>
 	body{
 		background : white;
 	}
 </style>
<%@ include file= "../../cierra_enoc.jsp" %>