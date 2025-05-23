<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<%@page import="aca.financiero.spring.ContEjercicio"%>
<%@page import="aca.bec.spring.BecInforme"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.bec.spring.BecInformeAlumno"%>
<%@page import="aca.bec.spring.BecAcuerdo"%>
<%@page import="aca.bec.spring.BecPuestoAlumno"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.financiero.spring.ContCcosto"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean scope="page" id="BecInformeU" class="aca.bec.BecInformeUtil"/>
<jsp:useBean scope="page" id="BecInforme" class="aca.bec.BecInforme"/>
<jsp:useBean scope="page" id="ContEjercicioU" class="aca.financiero.ContEjercicioUtil" />
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
	
	// Mapa de departamentos
	HashMap<String, ContCcosto> mapaCostos				= (HashMap<String,ContCcosto>) request.getAttribute("mapaCostos");
	
	// Mapa de funciones en SunPlus
	HashMap <String, String> mapaFunciones				= (HashMap<String,String>) request.getAttribute("mapaFunciones"); 
	
	// Mapa de precios
	HashMap <String, String> mapaPrecios				= (HashMap<String,String>) request.getAttribute("mapaPrecios");
	
	// Mapa de puestos del alumno
	HashMap<String, BecPuestoAlumno> mapaPuestos		= (HashMap<String,BecPuestoAlumno>) request.getAttribute("mapaPuestos");
	
	// Mapa de puestos del alumno
	HashMap<String, AlumPersonal> mapaAlumnos			= (HashMap<String,AlumPersonal>) request.getAttribute("mapaAlumnos");
	
	// Mapa de puestos del alumno
	HashMap<String, String> mapaCalculos				= (HashMap<String,String>) request.getAttribute("mapaCalculos");

	HashMap<String, CatCarrera> mapaCarreras				= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	
	
%>
<div class="container-fluid">
	<h1>Rendimiento por informe</h1>
	<form name="frmInforme" id="frmInforme" method="post" action="rendimientoInforme">
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
		<th style="width:3%;">#</th>
		<th style="width:5%;"><spring:message code="aca.Matricula"/></th>
		<th style="width:25%;"><spring:message code="aca.Alumno"/></th>
		<th style="width:25%;"><spring:message code="aca.Carrera"/></th>
		<th style="width:5%;">S.P.Car.</th>
		<th style="width:10%;">Clave</th>
		<th style="width:15%;">Departamento</th>
		<th style="width:5%;">S.P.Dept.</th>
		<th style="width:5%;">Folio/Calc.</th>
		<th style="width:3%;" class="right">Tipo</th>
		<th style="width:3%;" class="right">Horas</th>
		<th style="width:3%;" class="right">Precio</th>
		<th style="width:3%;" class="right">Total</th>
		<th style="width:3%;" class="right">Tardanzas</th>
		<th style="width:3%;" class="right">Ausencias</th>			
		<th style="width:3%;" class="right">Punt.</th>
		<th style="width:3%;" class="right">Fun.</th>
		<th style="width:3%;" class="right">Tiem.</th>
		<th style="width:3%;" class="right">Inic.</th>
		<th style="width:3%;" class="right">Rel.</th>
		<th style="width:3%;" class="right">Resp.</th>
		<th style="width:3%;" class="right">Prod.</th>
		<th style="width:3%;" class="right">Cuid.</th>
		<th style="width:3%;" class="right">Promedio</th>	
	</tr>	
	</thead>	
<%
	int con = 1;	
	if(!lisInformeAlumno.isEmpty()){
		
		for(BecInformeAlumno informeAlumno : lisInformeAlumno){		
			
			String nombreAlumno 	= "-";
			if (mapaAlumnos.containsKey(informeAlumno.getCodigoPersonal())){
				nombreAlumno = mapaAlumnos.get(informeAlumno.getCodigoPersonal()).getNombre()+" "+mapaAlumnos.get(informeAlumno.getCodigoPersonal()).getApellidoPaterno()+" "+mapaAlumnos.get(informeAlumno.getCodigoPersonal()).getApellidoMaterno();
			}
			
			String planId 	= "0";
			String tipo 	= "0";
			if (mapaPuestos.containsKey( informeAlumno.getPuestoId())){
				planId 		= mapaPuestos.get( informeAlumno.getPuestoId()).getPlanId();
				tipo 		= mapaPuestos.get( informeAlumno.getPuestoId()).getTipo();	
			}else{
				planId = aca.alumno.PlanUtil.getPlanActual(conEnoc, informeAlumno.getCodigoPersonal());
			}
			
			String carreraAlumno 	= aca.alumno.PlanUtil.getCarreraIdPLAN(conEnoc, planId);
			String sPCarr		 	= "-";
			
			if (mapaCarreras.containsKey(carreraAlumno)){
				sPCarr = mapaCarreras.get(carreraAlumno).getCcostoId();
			}
			
			if (mapaFunciones.containsKey(sPCarr)){
				sPCarr = mapaFunciones.get(sPCarr);
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
			
			String deptoNombre = "**";
			if (mapaCostos.containsKey(informeAlumno.getIdCcosto())){				
				deptoNombre = mapaCostos.get(informeAlumno.getIdCcosto()).getNombre();
			}
%>				
	<tr>
		<td><%=con%></td>
		<td><%=informeAlumno.getCodigoPersonal()%></td>
		<td><%=nombreAlumno%></td>
		<td><%=carreraAlumno%></td>
		<td><%=sPCarr%></td>
		<td><%=informeAlumno.getIdCcosto()%></td>
		<td><%=deptoNombre%></td>
		<td><%=funcion%></td>
		<td><%=folioCalculo%></td>
		<td><%=tipo%></td>
		<td class="right"><%=informeAlumno.getHoras()%></td>
		<td class="right"><%=getFormato.format(precio)%></td>
		<td class="right"><%=getFormato.format(total)%></td>
		<td class="right"><%=tardanzas %></td>
		<td class="right"><%=ausencias %></td>			
		<td class="right"><%=Integer.parseInt(informeAlumno.getPuntualidad())+5%></td>
		<td class="right"><%=Integer.parseInt(informeAlumno.getFuncion())+5%></td>
		<td class="right"><%=Integer.parseInt(informeAlumno.getTiempo())+5%></td>
		<td class="right"><%=Integer.parseInt(informeAlumno.getIniciativa())+5%></td>
		<td class="right"><%=Integer.parseInt(informeAlumno.getRelacion())+5%></td>
		<td class="right"><%=Integer.parseInt(informeAlumno.getRespeto())+5%></td>
		<td class="right"><%=Integer.parseInt(informeAlumno.getProductivo())+5%></td>
		<td class="right"><%=Integer.parseInt(informeAlumno.getCuidado())+5%></td>
		<td class="right"><b><%=promedio%></b></td>			
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