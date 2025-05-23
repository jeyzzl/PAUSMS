<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import="aca.disciplina.spring.CondAlumno"%>
<%@ page import="aca.disciplina.spring.CondReporte"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Mostrar(){		
		document.forma.submit();
	}
</script>
<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />

<% 	
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	
	List<CondAlumno> lisRegistros 	= (List<CondAlumno>)request.getAttribute("lisRegistros");	
	
	HashMap<String, String> mapaAlumnos 		= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
	HashMap<String, String>	mapaJueces 			= (HashMap<String, String>) request.getAttribute("mapaJueces");
	HashMap<String, CondReporte> mapaReportes 	= (HashMap<String, CondReporte>) request.getAttribute("mapaReportes");
%>
<div class="container-fluid">
	<h2>Report by Date</h2>	
	<form name="forma" action="registros" method="post">		
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" class="btn btn-primary btn-sm"><i class="fas fa-arrow-left icon-white"></i></a>&nbsp;&nbsp;
		Start Date: <input id="FechaIni" name="FechaIni" type="text" class="form-control" data-date-format="dd/mm/yyyy" data-format="hh:mm:ss" value="<%=fechaIni%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
		<span class="add-on">
    	 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
   		 </span>
		End Date: <input id="FechaFin" name="FechaFin" type="text" class="form-control" data-date-format="dd/mm/yyyy" data-format="hh:mm:ss" value="<%=fechaFin%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
		<span class="add-on">
    	 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
   		 </span>
		<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>	
	</div>	
	</form>
	<table class="table table-sm table-striped">  
  	<tr> 
   		<th width="4%"><spring:message code="aca.Numero"/></th>
    	<th width="7%"><spring:message code="aca.Fecha"/></th>
    	<th width="8%"><spring:message code="aca.Matricula"/></th>
    	<th width="32%"><spring:message code="aca.Nombre"/></th>
    	<th width="17%">Judge</th>
    	<th width="20%">Report</th>
    	<th width="8%"><spring:message code="aca.Tipo"/></th>    
    	<th width="4%">Value</th>
 	</tr>
<% 
	int row=0;
	for ( CondAlumno registro : lisRegistros){		
		row++;
		
		String reporteTipo 		= "-";
		String reporteNombre	= "-";
		if (mapaReportes.containsKey(registro.getIdReporte())){
			reporteTipo		= mapaReportes.get(registro.getIdReporte()).getTipo();
			reporteNombre	= mapaReportes.get(registro.getIdReporte()).getNombre();
		}
		
		if (reporteTipo.equals("D")){
			reporteTipo = "Miscounduct";
		}else if (reporteTipo.equals("C")){
			reporteTipo = "Praise";
		}else{
			reporteTipo = "Other";
		}
		
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(registro.getMatricula())){
			alumnoNombre = mapaAlumnos.get(registro.getMatricula());
		}
		
		String juezNombre = "-";
		if (mapaJueces.containsKey(registro.getIdJuez())){
			juezNombre = mapaJueces.get(registro.getIdJuez());
		}
%>
	<tr>
    	<td><%=row%></td>
    	<td><%=registro.getFecha()%></td>
    	<td><%=registro.getMatricula()%></td>
    	<td><%=alumnoNombre%></td>
    	<td><%=juezNombre%></td>
    	<td><%=reporteNombre%></td>
    	<td><%=reporteTipo%></td>
    	<td><%=registro.getCantidad()%></td>
  	</tr>
<%	} %>  
	</table>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>