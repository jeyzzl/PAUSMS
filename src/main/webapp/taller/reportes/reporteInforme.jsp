<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<%@page import="aca.financiero.spring.ContEjercicio"%>
<%@page import="aca.bec.spring.BecInforme"%>
<%@page import="aca.bec.spring.BecTipo"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.bec.spring.BecInformeAlumno"%>
<%@page import="aca.bec.spring.BecAcuerdo"%>
<%@page import="aca.bec.spring.BecPuestoAlumno"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.financiero.spring.ContCcosto"%>
<%@page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean scope="page" id="BecInformeU" class="aca.bec.BecInformeUtil"/>
<jsp:useBean scope="page" id="BecInforme" class="aca.bec.BecInforme"/>
<jsp:useBean scope="page" id="BecTipoDao" class="aca.bec.spring.BecTipoDao"/>
<jsp:useBean scope="page" id="ContEjercicioU" class="aca.financiero.ContEjercicioUtil"/>
<jsp:useBean scope="page" id="BecInformeAlumnoU" class="aca.bec.BecInformeAlumnoUtil"/>
<jsp:useBean id="Alumno" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="BecPuestoAlumno" scope="page" class="aca.bec.BecPuestoAlumno"/>
<jsp:useBean id="BecPuestoAlumnoU" scope="page" class="aca.bec.BecPuestoAlumnoUtil"/>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<%	
	java.text.DecimalFormat getFormato 			= new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String ejercicioId				= (String)request.getAttribute("ejercicioId");
	String informeId				= (String)request.getAttribute("informeId");
	
	// Lista de ejercicios
	List<ContEjercicio> lisEjercicios 					= (List<ContEjercicio>) request.getAttribute("lisEjercicios");
	
	// Lista de ejercicios
	List<BecInforme> lisInformes 						= (List<BecInforme>) request.getAttribute("lisInformes");
	
	// Lista de informes de alumnos
	List<BecInformeAlumno> lisInformeAlumno				= (List<BecInformeAlumno>) request.getAttribute("lisInformesAlumno");
	
	List<BecAcuerdo> lisAcuerdos							= (List<BecAcuerdo>) request.getAttribute("lisAcuerdos");
	
	// Mapa de departamentos
	HashMap<String, ContCcosto> mapaCostos				= (HashMap<String,ContCcosto>) request.getAttribute("mapaCostos");
	
	// Mapa de funciones en SunPlus
	HashMap <String, String> mapaFunciones				= (HashMap<String,String>) request.getAttribute("mapaFunciones"); 
	
	// Mapa de precios
	HashMap <String, String> mapaPrecios				= (HashMap<String,String>) request.getAttribute("mapaPrecios");
	
	// Mapa de puestos del alumno
	HashMap<String, BecPuestoAlumno> mapaPuestos		=  (HashMap<String,BecPuestoAlumno>) request.getAttribute("mapaPuestos");
	
	// Mapa de puestos del alumno
	HashMap<String, AlumPersonal> mapaAlumnos			=  (HashMap<String,AlumPersonal>) request.getAttribute("mapaAlumnos");
	
	// Mapa de puestos del alumno
	HashMap<String, String> mapaCalculos				=  (HashMap<String,String>) request.getAttribute("mapaCalculos");

	HashMap<String, CatCarrera> mapaCarreras			=  (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");		
	
	HashMap<String, BecTipo> mapaBecTipos				=  (HashMap<String,BecTipo>) request.getAttribute("mapaBecTipos");

	HashMap<String, MapaPlan> mapPlanes				=  (HashMap<String,MapaPlan>) request.getAttribute("mapPlanes");
%>
<div class="container-fluid">
	<h1>Rendimiento por informe</h1>
	<form name="frmInforme" id="frmInforme" method="post" action="reporteInforme">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="menu"><i class="fas fa-arrow-left"></i></a>	
		Ejecicio
		<select style="position: right;" id="ejercicioId" name="ejercicioId" onchange="document.frmInforme.submit()">				
<%	for(ContEjercicio ej : lisEjercicios){%>
			<option value="<%=ej.getIdEjercicio()%>" <%if(ejercicioId.equals(ej.getIdEjercicio())){out.print("selected");}%>><%=ej.getIdEjercicio()%></option>											
<%	}%>				
		</select>				
		Informes 
		<select style="width:300px;" id="InformeId" name="InformeId" onchange="document.frmInforme.submit()">				
<%	for(BecInforme info : lisInformes){						
%>
			<option value="<%=info.getInformeId()%>" <%if(informeId.equals(info.getInformeId())){out.print("selected");}%>><%=info.getInformeNombre()%></option>											
<%	}	
%>				
		</select>	
	</div>
	</form>	
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">
	<tr>
		<th rowspan="2" style="width:3%;">#</th>
		<th rowspan="2" style="width:5%;"><spring:message code="aca.Matricula"/></th>		
		<th rowspan="2" style="width:25%;"><spring:message code="aca.Carrera"/></th>
		<th rowspan="2" style="width:5%;">Función(1)</th>
		<th rowspan="2" style="width:5%;">Ord.Id</th>
		<th rowspan="2" style="width:25%;"><spring:message code="aca.Alumno"/></th>		
		<th rowspan="2" style="width:15%;">Departamento</th>
		<th rowspan="2" style="width:5%;">Función(2)</th>
		<th rowspan="2" style="width:5%;">Folio/Calc.</th>
		<th colspan="6" style="width:3%;" class="center">Tipo de Becas</th>		
		<th rowspan="2" style="width:3%;" class="right">Horas</th>
		<th rowspan="2" style="width:3%;" class="right">Precio</th>
		<th rowspan="2" style="width:3%;" class="right">Total</th>			
	</tr>	
	</thead>	
	<tr>	
		<td><strong>Beca#1</strong></td>
		<td><strong>Flag</strong></td>
		<td><strong>TFW</strong></td>
	   	<td><strong>Beca#2</strong></td>
	   	<td><strong>Flag</strong></td>
		<td><strong>TFW</strong></td>
	</tr>		
<%
	int con = 1;	
	if(!lisInformeAlumno.isEmpty()){
		
		for(BecInformeAlumno informeAlumno : lisInformeAlumno){		
			
			String nombreAlumno 	= "-";
			if (mapaAlumnos.containsKey(informeAlumno.getCodigoPersonal())){
				nombreAlumno = mapaAlumnos.get(informeAlumno.getCodigoPersonal()).getNombre()+" "+mapaAlumnos.get(informeAlumno.getCodigoPersonal()).getApellidoPaterno()+" "+mapaAlumnos.get(informeAlumno.getCodigoPersonal()).getApellidoMaterno();
			}
			
			String planId 	= "0";
			String orgId 	= "-";
			String tipo 	= "0";
			String dato		= "<span class='badge bg-dark'>"+informeAlumno.getCodigoPersonal()+"</span>";
			if (mapaPuestos.containsKey( informeAlumno.getPuestoId())){
				planId 		= mapaPuestos.get( informeAlumno.getPuestoId()).getPlanId();
				tipo 		= mapaPuestos.get( informeAlumno.getPuestoId()).getTipo();	
			}else{
				planId 		= aca.alumno.PlanUtil.getPlanActual(conEnoc, informeAlumno.getCodigoPersonal());
				dato		= "<span class='badge bg-warning'>"+informeAlumno.getCodigoPersonal()+"</span>";
			}
			
			String carreraAlumno 	= aca.alumno.PlanUtil.getCarreraIdPLAN(conEnoc, planId);
			String sPCarr		 	= "-";
			
			if (mapaCarreras.containsKey(carreraAlumno)){
				sPCarr = mapaCarreras.get(carreraAlumno).getCcostoId();
			}
			
			if (mapaFunciones.containsKey(sPCarr)){
				sPCarr = mapaFunciones.get(sPCarr);
			}

			if (mapPlanes.containsKey(planId)){
				orgId = mapPlanes.get(planId).getOficial();
			}
			
			carreraAlumno 			= aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, carreraAlumno);
			
			String tardanzas 		= informeAlumno.getTardanzas();
			String ausencias 		= informeAlumno.getAusencias();
			
			int sumaPuntos 	= Integer.parseInt(informeAlumno.getPuntualidad())+Integer.parseInt(informeAlumno.getFuncion())+
							Integer.parseInt(informeAlumno.getTiempo())+Integer.parseInt(informeAlumno.getIniciativa())+
							Integer.parseInt(informeAlumno.getRelacion())+Integer.parseInt(informeAlumno.getRespeto())+
							Integer.parseInt(informeAlumno.getProductivo())+Integer.parseInt(informeAlumno.getCuidado()) + 40;
			
			double promedio	= 0;
			if (sumaPuntos > 0){
				promedio = Double.parseDouble(String.valueOf(sumaPuntos)) / 8 * 10; 
			}
			
			String funcion = "-";
			if (mapaFunciones.containsKey(informeAlumno.getIdCcosto())){
				funcion = mapaFunciones.get(informeAlumno.getIdCcosto());
			}
			
			String ejercicioPuesto = informeAlumno.getPuestoId().substring(0, 8);
			//System.out.println("Puesto:"+ejercicioPuesto);
			double precio = 0;
			
			if (tipo.equals("T")) tipo = "O";
			if (mapaPrecios.containsKey(tipo)){
				precio = Double.valueOf(mapaPrecios.get(tipo));
			}
			if (tipo.equals("O")) tipo = "T";
			
			double total = Double.valueOf(informeAlumno.getHoras())*precio;
			
			String folioCalculo = "0";
			if (mapaCalculos.containsKey(informeAlumno.getCodigoPersonal())){			
				folioCalculo = mapaCalculos.get(informeAlumno.getCodigoPersonal());
			}
			
			String depto = "-";
			if (mapaCostos.containsKey(informeAlumno.getIdCcosto())){
				depto = mapaCostos.get(informeAlumno.getIdCcosto()).getNombre();
			}
			
			if(orgId.equals("I")){
				orgId = "SIDIOM01";
			}else if(orgId.equals("C")){
				orgId = "SCIMUN01";
			}else{
				orgId = "SFORMA01";
			}			
%>				
	<tr>
		<td><%=con%></td>
		<td><%=dato%></td>
		<td><%=carreraAlumno%></td>
		<td><%=sPCarr%></td>
		<td><%=orgId%></td>
		<td><%=nombreAlumno%></td>		
		<td><%=depto%></td>
		<td><%=funcion%></td>
		<td><%=folioCalculo%></td>
<% 	
		int row = 0;
		for(aca.bec.spring.BecAcuerdo acuerdo : lisAcuerdos){
			if (acuerdo.getCodigoPersonal().equals(informeAlumno.getCodigoPersonal())){
				row++;
				String tipoNombre 	= "-";
				String flag 		= "-";
				String cuenta 		= "-";
				if (mapaBecTipos.containsKey(acuerdo.getTipo())){
					tipoNombre 	= mapaBecTipos.get(acuerdo.getTipo()).getNombre();
					flag		= mapaBecTipos.get(acuerdo.getTipo()).getFlag();
					cuenta 		= mapaBecTipos.get(acuerdo.getTipo()).getCuenta();
				}				
%>
			<td><%=tipoNombre%></td>
			<td><%=flag%></td>
			<td><%=cuenta%></td>
<% 		
			}
		}
		if (row==0){
			// Agrega 6 columnas en blanco para compensar el espacio colspan=6
			out.println("<td>-</td><td>-</td><td>-</td><td>-</td><td>-</td><td>-</td>");
		}else if (row==1){
			// Agrega 3 columnas en blanco para compensar el espacio colspan=6
			out.println("<td>-</td><td>-</td><td>-</td>");
		}
%>				
		<td class="right"><%=informeAlumno.getHoras()%></td>
		<td class="right"><%=getFormato.format(precio)%></td>
		<td class="right"><%=getFormato.format(total)%></td>					
	</tr>	
<%
			con++;
		}		
	}else{		
%>
	<tr>
		<td></td>
		<td colspan="8" ><strong>No hay alumnos registrados en el ejercicio e informe seleccionados</strong></td>
	</tr>	
<%
	}
%>
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