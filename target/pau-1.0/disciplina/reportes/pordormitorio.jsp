<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import="aca.disciplina.spring.CondAlumno"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.vista.spring.Inscritos"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
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
	int row =0;
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	
	List<Inscritos> lisAlumnos 						= (List<Inscritos>) request.getAttribute("lisAlumnos");
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaCantidades			= (HashMap<String,String>) request.getAttribute("mapaCantidades");
	
%>
<div class="container-fluid">
	<h2>Report by Dormitory</h2>	
	<form name="forma" action="pordormitorio" method="post">		
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
	<thead>
  	<tr> 
   		<th><spring:message code="aca.Numero"/></th>
    	<th><spring:message code="aca.Matricula"/></th>
    	<th><spring:message code="aca.Nombre"/></th>
    	<th>Gender</th>
    	<th><spring:message code="aca.Carrera"/></th>
    	<th>Dormitory</th> 	
    	<th>Misconducts</th>
    	<th>Praises</th>
    	<th>Total</th>
	</tr>
	</thead>
<%
	String codigoPersonalTmp 	= "";
	for(Inscritos ins : lisAlumnos){	
		if(!ins.getCodigoPersonal().equals(codigoPersonalTmp)){
			codigoPersonalTmp 	= ins.getCodigoPersonal();
			row++;
			
		String carreraNombre = "-";
		if (mapaCarreras.containsKey(ins.getCarreraId())){
			carreraNombre = mapaCarreras.get(ins.getCarreraId()).getNombreCarrera();
		}
		
		String numUnidades	= "0";
    	if (mapaCantidades.containsKey(ins.getCodigoPersonal()+"D")){
    		numUnidades		= mapaCantidades.get(ins.getCodigoPersonal()+"D");	    		
    	}
    	
    	String numElogios	= "0";
    	if (mapaCantidades.containsKey(ins.getCodigoPersonal()+"C")){
    		numElogios		= mapaCantidades.get(ins.getCodigoPersonal()+"C");	    		
    	}	
    	
    	int total = 0;
    	if (Integer.parseInt(numUnidades) > Integer.parseInt(numElogios)){ 
    		total = Integer.parseInt(numUnidades)-Integer.parseInt(numElogios); 
    	}
%>

	<tr> 
    	<td><%=row%></td>
    	<td><font size="2"><%=ins.getCodigoPersonal()%></font></td>
	    <td><font size="2"><%=ins.getNombre()%> <%=ins.getApellidoPaterno()%> <%=ins.getApellidoMaterno()%></font></td>
    	<td><font size="2"><%=ins.getSexo().equals("F")?"Female":"Male"%></font></td>
    	<td><font size="2"><%=carreraNombre%></font></td>
    	<td><font size="2"><%=ins.getDormitorio() %></font></td>	
    	<td><font size="2"><%=numUnidades%></font></td>
    	<td><font size="2"><%=numElogios%></font></td>
    	<td><font size="2"><%=total%></font></td>
  	</tr>
<%
	}
	}
%>
	</table>
</div>