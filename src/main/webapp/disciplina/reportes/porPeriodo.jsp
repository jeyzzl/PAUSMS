<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatPeriodo"%>
<%@ page import="aca.disciplina.spring.CondAlumno"%>
<%@ page import="aca.disciplina.spring.CondReporte"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="inscritoU" scope= "page" class="aca.vista.InscritosUtil" />
<jsp:useBean id="alumnoU" scope= "page" class="aca.disciplina.CondAlumnoUtil" />
<%  
	String periodoId 	= (String)request.getAttribute("periodoId");
	String sNombre		= "X";		
	String sEmpleado	= "X";
	String sTipo		= "X";
	String sCantidad	= "";
	String sBgcolor		= "";
	String sElogio		= "";
	String sFalta		= "";	
	int nElogio			= 0;
	int nFalta			= 0;
	int nUnidad 		= 0;
	int cont			= 1;
	
	List<CatPeriodo> lisPeriodos 				= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<CondAlumno> lisAlumnos 				= (List<CondAlumno>) request.getAttribute("lisAlumnos");	
	HashMap<String, String>	mapaJueces 			= (HashMap<String, String>) request.getAttribute("mapaJueces");
	HashMap<String, CondReporte> mapaReportes 	= (HashMap<String, CondReporte>) request.getAttribute("mapaReportes");
	HashMap<String, String>	mapaLugares			= (HashMap<String, String>) request.getAttribute("mapaLugares");
%>
<div class="container-fluid">
	<h2>Report by Cycle</h2>
	<form id="forma" name="forma" action="porPeriodo" method="post" id="noayuda">
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" class="btn btn-primary btn-sm"><i class="fas fa-arrow-left icon-white"></i></a>&nbsp;&nbsp;
		Cycle:&nbsp;
		<select id="PeriodoId" name="PeriodoId" class="form-select" style="width:220px" onchange="document.forma.submit();">
<%		for(CatPeriodo periodo : lisPeriodos){ %>
			<option value="<%=periodo.getPeriodoId() %>"<%=periodo.getPeriodoId().equals(periodoId)?" Selected":"" %>><%=periodo.getNombre() %></option>
<%		} %>
    	</select>
	</div>
	</form>
	<table class="table table-sm table-bordered">
	<thead class="table-info">
	<tr align="center" class="table-info"> 
    	<th width="5%"><spring:message code="aca.Folio"/></th>
    	<th width="5%">Employee</th>
    	<th width="10%">Committee</th>
    	<th width="10%">Report</th>
    	<th width="10%"><spring:message code='aca.Descripcion'/></th>
    	<th width="10%">Location</th>
    	<th width="5%"><spring:message code="aca.Fecha"/></th>
    	<th width="5%">Type</th>
    	<th width="5%">Value</th>
  	</tr>
  	</thead>
<%	
	int row = 0;
	for (CondAlumno alumno : lisAlumnos){
		row++;
		
		String reporteNombre	= "-";
		String tipoReporte 			= "X"; 
		if (mapaReportes.containsKey(alumno.getIdReporte())){			
			reporteNombre	= mapaReportes.get(alumno.getIdReporte()).getNombre();
			tipoReporte		= mapaReportes.get(alumno.getIdReporte()).getTipo();
		}
		
		String juezNombre = "-";
		if (mapaJueces.containsKey(alumno.getIdJuez())){
			juezNombre = mapaJueces.get(alumno.getIdJuez());
		}		
		
		String lugarNombre = "-";
		if (mapaLugares.containsKey(alumno.getIdLugar())){
			lugarNombre = mapaLugares.get(alumno.getIdLugar());
		}
%>
	<tr> 
    	<td align="center"><%=alumno.getFolio()%></td>
	    <td><%=alumno.getEmpleado()%></td>
	    <td><%=juezNombre%></td>
   		<td><%=reporteNombre%></td>   	
    	<td align="center"><%=alumno.getComentario()%></td>
    	<td align="center"><%=lugarNombre%></td>
    	<td align="center"><%=alumno.getFecha()%></td>
    	<td align="center" valign="top"><%=tipoReporte.equals("C")?"Praise":"Misconduct"%></td>
    	<td align="center" valign="top"><%=alumno.getCantidad()%></td>
  	</tr>
  
<%	} %>		
	</table>
</div>