<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.disciplina.spring.CondAlumno"%>
<%@page import="aca.disciplina.spring.CondReporte"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<script type="text/javascript">
	function Borrar( codigo, periodo, folio){
		if(confirm("Estas seguro de eliminar el registro: "+folio)==true){
	  		document.location="grabar?Accion=4&codigoPersonal="+codigo+"&Periodo="+periodo+"&folio="+folio;
	  	}
	}
</script>
<%
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno			= (String) request.getParameter("codigoAlumno");
	String periodoId			= (String) request.getAttribute("periodoId");
	String alumnoNombre			= (String) request.getAttribute("alumnoNombre");
	
	String sBgcolor				= "";	
	int nDescontar				= 0;
	int nUnidad					= 0;
	int nTotal					= 0;
	String sColor				= "#eeeeee";
	
	List<CatPeriodo> lisPeriodos 				= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<CondAlumno> lisRegistros				= (List<CondAlumno>) request.getAttribute("lisRegistros");	
	HashMap<String,String> mapaLugares			= (HashMap<String,String>) request.getAttribute("mapaLugares");
	HashMap<String,CondReporte> mapaReportes	= (HashMap<String,CondReporte>) request.getAttribute("mapaReportes");
	HashMap<String,String> mapaMaestros			= (HashMap<String,String>) request.getAttribute("mapaMaestros");
%>
<div class="container-fluid">
	<h2>Registry of Units and Praises<small class="text-muted fs-5"> (<%=codigoAlumno%> - <%=alumnoNombre%>)</small></h2>
	<form id="forma" name="forma" action="unidad" method="post">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="portal">Return</a>&nbsp;&nbsp;
		<select id="periodo" name="periodo" onchange="document.forma.submit();">
<%	for(CatPeriodo periodo : lisPeriodos){ %>
			<option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"Selected":"" %>><%=periodo.getNombre()%></option>
<%	}%>
    	</select>
    	&nbsp;&nbsp;&nbsp;
    	<a class="btn btn-primary" href="grabar?Periodo=<%=periodoId%>&codigoPersonal=<%=codigoAlumno%>&Accion=1"><spring:message code='aca.Añadir'/></a>
	</div>
	<table class="table table-sm">
  	<tr> 
    	<th width="5%" height="21">Op.</th>
    	<th width="5%"><spring:message code="aca.Folio"/></th>
    	<th width="10%" valign="top"><spring:message code="aca.Fecha"/></th>
    	<th width="20%">Report</th>
    	<th width="20%">Location</th>
    	<th width="25%" align="center">User</th>    	    
    	<th width="5%" class="right">Unit</th>
    	<th width="5%" class="right">Praise</th>
  	</tr>
<%	
	for(int i=0; i<lisRegistros.size(); i++){
		
		CondAlumno alumno 	= (CondAlumno) lisRegistros.get(i);		
		String maestro 		= "0";
		if(alumno.getEmpleado() == null){
			maestro = alumno.getIdJuez();			
		}else{ 
			maestro = alumno.getEmpleado();
		}
		
		String maestroNombre = "-";
		if (mapaMaestros.containsKey(maestro)){
			maestroNombre = mapaMaestros.get(maestro);
		}
		
		String tipoReporte 		= "0";
		String reporteNombre 	= "-";
		if (mapaReportes.containsKey(alumno.getIdReporte())){
			tipoReporte 	= mapaReportes.get(alumno.getIdReporte()).getTipo();
			reporteNombre 	= mapaReportes.get(alumno.getIdReporte()).getNombre();
		}
		
		String lugar = "-";
		if (mapaLugares.containsKey(alumno.getIdLugar())){
			lugar = mapaLugares.get(alumno.getIdLugar());
		}
		
		if ((i % 2) == 0 ){ sBgcolor = sColor; }else{ sBgcolor = ""; }	
%>
	<tr class="tr2" <%=sBgcolor%>> 
    	<td height="24" valign="top" align="center">
<%		if (tipoReporte.equals("C") && alumno.getEmpleado().equals(codigoPersonal)){ %>	
    		<a href="grabar?Periodo=<%=periodoId%>&codigoAlumno=<%=codigoAlumno%>&folio=<%=alumno.getFolio()%>&Accion=5"><i class="fas fa-pencil-alt"></i></a>&nbsp;
    		<a href="javascript:Borrar('<%=codigoAlumno%>','<%=periodoId%>','<%=alumno.getFolio()%>')"><i class="fas fa-trash" ></i></a>
<%		} %>    		 
    	</td>
    	<td><%=alumno.getFolio()%></td>  
    	<td><%=alumno.getFecha()%></td> 
    	<td><%=reporteNombre%></td>
    	<td><%=lugar%></td>    	
   		<td><%=maestroNombre%></td>    	
    	<td class="right">  
<%		
		if(tipoReporte.equals("D")){
      		out.print(alumno.getCantidad()); 
 			nUnidad = nUnidad + Integer.parseInt(alumno.getCantidad());
		}
%>
      	</td>
    	<td class="right"> 
<%		
		if(tipoReporte.equals("C")){
			out.print(alumno.getCantidad()); 
			nDescontar = nDescontar + Integer.parseInt(alumno.getCantidad());
		}
%>
      	</td>
  	</tr>
  
<%	}
	nTotal = nUnidad - nDescontar;
	if(nTotal<0){  nTotal = 0;	}
%>
	</table>
	</form>
</div>
