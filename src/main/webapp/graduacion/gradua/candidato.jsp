<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumGradua"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguroJquery.jsf"%>
<%@ include file="../../idioma.jsp"%>
<%
	String year				= aca.util.Fecha.getHoy().substring(6,10);
	String fechaInicio 			= request.getParameter("fechaInicio")==null?"01/01/"+year:request.getParameter("fechaInicio");
	String fechaFinal  			= request.getParameter("fechaFinal")==null?"31/12/"+year:request.getParameter("fechaFinal");	
	
	List<AlumGradua> lisGraduandos 				= (List<AlumGradua>)request.getAttribute("lisGraduandos");
	HashMap<String,String> mapaFacDelPlan		= (HashMap<String,String>) request.getAttribute("mapaFacDelPlan");
	HashMap<String,CatFacultad> mapaFacultades	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,AlumPersonal> mapaAlumnos	= (HashMap<String,AlumPersonal>) request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaEventos			= (HashMap<String,String>)request.getAttribute("mapaEventos");
	HashMap<String,String> mapaEventoAlumno		= (HashMap<String,String>)request.getAttribute("mapaEventoAlumno");
	
	String facTemp		= "X";
	String facAlumno	= "";
%>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<body>
<div class="container-fluid">
	<h2>Graduation Candidates</h2>
	<form name="forma" method="post" action="candidato">
	<div class="alert alert-info d-flex align-items-center">
		Start Date:&nbsp;
		<input class=form-control id="fechaInicio" name="fechaInicio" placeholder="Start Date" data-date-format="dd/mm/yyyy" value="<%=fechaInicio%>" style="width:120px">
		&nbsp;&nbsp;&nbsp;
		End Date:&nbsp;
		<input  class=form-control id="fechaFinal" name="fechaFinal" placeholder="End Date" data-date-format="dd/mm/yyyy" value="<%=fechaFinal%>" style="width:120px" >
		&nbsp;
		<a href="javascript:Filtrar();" class="btn btn-primary"  style="width:100px">Search</a>
	</div>
	</form>
	<table>	
<%
	int row = 0;
	for (AlumGradua graduacion : lisGraduandos){			
		row++;	
		facAlumno = "X";
		if (mapaFacDelPlan.containsKey(graduacion.getPlanId())){
			facAlumno = mapaFacDelPlan.get(graduacion.getPlanId());
		}
		if (!facTemp.equals(facAlumno)) {
			facTemp = facAlumno;
			String facultadNombre = "-";
			if (mapaFacultades.containsKey(facAlumno)){
				facultadNombre = mapaFacultades.get(facAlumno).getNombreFacultad();
			}
%>
	</table>
	<table>
		<tr align="left">
			<td colspan="6"><h3><%=facAlumno%>:<%=facultadNombre%></h3></td>
		</tr>
	</table>
	<table class="table table-condensed table-bordered table-striped table-fontsmall">
		<tr>
			<th class="table-dark" width="3%"  class="text-center""><spring:message code="aca.Numero"/></th>
			<th class="table-dark" width="6%"  class="text-center"><spring:message code="aca.Matricula"/></th>
			<th class="table-dark" width="20%" align="left"><spring:message code="aca.Nombre"/></th>
			<th class="table-dark" width="5%"  class="text-center""><spring:message code="aca.Plan"/></th>
			<th class="table-dark" width="7%"  class="text-center""><spring:message code="aca.Fecha"/></th>
			<th class="table-dark" width="7%"  class="text-center"">Graduation Date</th>
			<th class="table-dark" width="21%" class="text-center">Event</th>
			<th class="table-dark" width="5%"  class="text-center"><spring:message code="aca.Avance"/></th>
			<th class="table-dark" width="5%" class="text-center">Cl. Sub.</th>
			<th class="table-dark" width="5%" class="text-center">Enr. Sub.</th>
			<th class="table-dark" width="5%" class="text-center">Pen. Sun.</th>
			
		</tr>
<%
		}
		
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(graduacion.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(graduacion.getCodigoPersonal()).getNombre()+" "+mapaAlumnos.get(graduacion.getCodigoPersonal()).getApellidoPaterno()+" "+mapaAlumnos.get(graduacion.getCodigoPersonal()).getApellidoMaterno();
		}		
		
		String eventoId = "0";
		if (mapaEventoAlumno.containsKey(graduacion.getCodigoPersonal()+graduacion.getPlanId())){			
			eventoId = mapaEventoAlumno.get(graduacion.getCodigoPersonal()+graduacion.getPlanId());
		}
		
		String eventoNombre = "-";
		if (mapaEventos.containsKey(eventoId)){			
			eventoNombre = mapaEventos.get(eventoId);			
		}
%>
		<tr>
			<td align="center"><%=row%></td>
			<td align="center"><%=graduacion.getCodigoPersonal()%></td>
			<td align="left"><%=alumnoNombre%></td>
			<td align="center"><%=graduacion.getPlanId()%></td>
			<td align="center"><%=graduacion.getFecha() %></td>
			<td align="center"><%=graduacion.getFechaGraduacion()%></td>
			<td align="center"><%=eventoNombre%></td>
			<td align="center"><%=graduacion.getAvance().equals("T")?"Total":"Partial" %></td>
			<td align="center"><%=graduacion.getMatAc()%></td>
			<td align="center"><%=graduacion.getMatIns()%></td>
			<td align="center"><%=graduacion.getMatPen()%></td>
		</tr>
<%
	}
%>
	</table>
</div>
</body>
<script>

	function Filtrar(){
		document.forma.submit();
	}
	
	jQuery('#fechaInicio').datepicker();
	jQuery('#fechaFinal').datepicker();
</script>