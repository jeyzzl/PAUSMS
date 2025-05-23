<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.financiero.spring.ContEjercicio"%>
<%@page import="aca.financiero.spring.ContCcosto"%>
<%@page import="aca.bec.spring.BecPuestoAlumno"%>
<%@page import="aca.bec.spring.BecAcuerdo"%>
<%@page import="aca.bec.spring.BecTipo"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<%
	java.text.DecimalFormat getFormato= new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String codigo			= (String)session.getAttribute("codigoPersonal");
	boolean admin			= (boolean)request.getAttribute("admin");
	String idEjercicio 		= (String)request.getAttribute("idEjercicio");
	String fechaHoy 		= aca.util.Fecha.getHoy();
	String fecha 			= request.getParameter("fechaParametro")==null?fechaHoy:request.getParameter("fechaParametro");	
	String estado 			= request.getParameter("estado")==null?"I":request.getParameter("estado");	
	String mensaje 			= (String)request.getAttribute("mensaje");
	
	double totalBeca 		= 0;	
	String fallback 		= "";
	String fechaInscrito 	= "";
	String departamento 	= "";
	String nombreDepto		= "";	
	boolean mostrar 		= false;	
	String total			= "";
	
	// Lista de ejercicios
	List<ContEjercicio> lisEjercicios 			= (List<ContEjercicio>)request.getAttribute("lisEjercicios");
	List<BecAcuerdo> lisAcuerdos 				= (List<BecAcuerdo>)request.getAttribute("lisAcuerdos");		
	// Mapa de Inscritos
	HashMap<String,String> mapInscritos 		= (HashMap<String,String>)request.getAttribute("mapInscritos");	
	// Mapa de puestos de alumnos
	HashMap<String,BecPuestoAlumno> mapPuestos 	= (HashMap<String,BecPuestoAlumno>)request.getAttribute("mapPuestos");	
	// Mapa de los departamentos
	HashMap<String,ContCcosto> mapDepartamentos = (HashMap<String,ContCcosto>)request.getAttribute("mapDepartamentos");
	//Total de beca
	HashMap<String,String> mapTotales			= (HashMap<String,String>)request.getAttribute("mapTotales");	
	HashMap<String,AlumPersonal> mapAlumnos		= (HashMap<String,AlumPersonal>)request.getAttribute("mapAlumnos");
	HashMap<String,BecTipo> mapTipos			= (HashMap<String,BecTipo>)request.getAttribute("mapTipos");
%>
<style>
	body{
		background:white;
	}
</style>

<div class="container-fluid">	
	<h2 style="margin-bottom:10px;">Acuerdo de Becas</h2>	
	<%=mensaje.equals("-")?"":mensaje%>
	<form action="acuerdos" name="forma" method="post">
		<div class="alert alert-info">
		<a class="btn btn-primary" href="menu"><i class="fas fa-arrow-left"></i></a> &nbsp;&nbsp;&nbsp;
		<select name="estado" id="estado" style="width:150px;" onchange="document.forma.submit()">
			<option value="I" <%if(estado.equals("I"))out.print("selected"); %>>Ejercido</option>
			<option value="A" <%if(estado.equals("A"))out.print("selected"); %>>Alta</option>
		</select>
			<div style="float:right">
			Fecha de revisión <input type="text" data-date-format="dd/mm/yyyy" id="fechaParametro" name="fechaParametro" value="<%=fecha%>"/>
			<a class="btn btn-success" onclick="javascript:document.frmPuestos.submit();">Cargar fecha</a>
			
			<select name="idEjercicio" id="idEjercicio" style="width:150px;" onchange="document.forma.submit()">
			<%
				for(ContEjercicio ejercicio: lisEjercicios){	
			%>
					<option value="<%=ejercicio.getIdEjercicio() %>" <%if(idEjercicio.equals(ejercicio.getIdEjercicio()))out.print("selected"); %>><%=ejercicio.getIdEjercicio() %></option>
			<%
				}
			%>
			</select>
			</div>
		</div>
	</form>
	
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">
		<tr>
			<th>#</th>
			<th><spring:message code="aca.Codigo"/></th>
			<th><spring:message code="aca.Alumno"/></th>
			<th><spring:message code="aca.Tipo"/></th>
			<th><spring:message code="aca.Fecha"/></th>
			<th class="right"><spring:message code="aca.Matricula"/></th>
			<th class="right">Enseñanza</th>
			<th class="right">Internado</th>
			<th>Valor</th>
			<th>Vigencia</th>
			<th><spring:message code="aca.Estado"/></th>
			<th><spring:message code="aca.Inscrito"/></th>
			<th>Lugar</th>
			<th class="right"><spring:message code="aca.Beca"/></th>
		</tr>
	</thead>
<%		 
		int cont = 0;		
		String tipoTmp = "";
		if(lisAcuerdos.size()>0)tipoTmp=lisAcuerdos.get(0).getTipo();
		
		double totalTipo = 0;
		
		for(BecAcuerdo acuerdo: lisAcuerdos){
			cont++;
			
			// Obtiene la fecha de inscripcion de los alumnos
			if (mapInscritos.containsKey(acuerdo.getCodigoPersonal()) ) 
				fechaInscrito = mapInscritos.get(acuerdo.getCodigoPersonal());
			else
				fechaInscrito = "X";
			
			// Obtiene el departamento de trabajo del alumno
			nombreDepto = " ";
			if (mapPuestos.containsKey(acuerdo.getCodigoPersonal())){
				BecPuestoAlumno puesto = (BecPuestoAlumno) mapPuestos.get(acuerdo.getCodigoPersonal());
				departamento = puesto.getIdCcosto();
				if (mapDepartamentos.containsKey(puesto.getIdCcosto())){
					nombreDepto = mapDepartamentos.get(puesto.getIdCcosto()).getNombre();
				}else{
					
				}
			}else{
				departamento = "";
			}
			
			if (mapTotales.containsKey(acuerdo.getCodigoPersonal()+acuerdo.getTipo())) {
				total 		= getFormato.format(Double.parseDouble(mapTotales.get(acuerdo.getCodigoPersonal()+acuerdo.getTipo())));
				totalBeca 	+= Double.parseDouble(mapTotales.get(acuerdo.getCodigoPersonal()+acuerdo.getTipo()));
			} else {
				total = getFormato.format(Double.parseDouble("0"));
			}
			
			if(!tipoTmp.equals(acuerdo.getTipo())){
%>
				<tr>
					<td colspan= "13" style="text-align:center"><b>T &nbsp; O &nbsp; T &nbsp; A &nbsp; L<b></td>		
					<td style="text-align:right"><b><%=getFormato.format(totalTipo)%></b></td>
				</tr>
<%				
				tipoTmp = acuerdo.getTipo();
				totalTipo = 0;
			}
			
			String alumnoNombre = "-";
			if (mapAlumnos.containsKey(acuerdo.getCodigoPersonal())){
				AlumPersonal alumno = mapAlumnos.get(acuerdo.getCodigoPersonal());
				alumnoNombre = alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno();
			}
			
			String tipoNombre = "-";
			if (mapTipos.containsKey(acuerdo.getTipo())){				
				tipoNombre = mapTipos.get(acuerdo.getTipo()).getNombre();
			}
%>
			<tr>
				<td><%=cont %></td>
				<td><%=acuerdo.getCodigoPersonal() %></td>
				<td><%=alumnoNombre%></td>
				<td><%=tipoNombre%></td>
				<td><%=acuerdo.getFecha() %></td>
				<td style="text-align:right"><%=acuerdo.getMatricula() %></td>
				<td style="text-align:right"><%=acuerdo.getEnsenanza() %> </td>
				<td style="text-align:right"><%=acuerdo.getInternado() %></td>
				<td><%=acuerdo.getValor().equals("C")?"Cantidad":"Porcentaje" %></td>
				<td><%=acuerdo.getVigencia() %></td>
				<td><%=acuerdo.getEstado().equals("I")?"Ejercido":"Alta" %></td>
				<td><%=fechaInscrito.equals("X")?" ":fechaInscrito%></td>
				<td class="ayuda mensaje <%=nombreDepto%>"><%=departamento%></td>
				<td style="text-align:right"><%=total%></td>
			</tr>
<%		
			if (mapTotales.containsKey(acuerdo.getCodigoPersonal()+acuerdo.getTipo())) {
				totalTipo += Double.parseDouble(mapTotales.get(acuerdo.getCodigoPersonal()+acuerdo.getTipo()));
			}
		} %>
			<tr>
				<td colspan= "13" style="text-align:center"><b>T &nbsp; O &nbsp; T &nbsp; A &nbsp; L<b></td>		
				<td style="text-align:right"><b><%=getFormato.format(totalTipo)%></b></td>
			</tr>
	</table>	
</div>
<script>	
	jQuery('#fechaParametro').datepicker();
</script>