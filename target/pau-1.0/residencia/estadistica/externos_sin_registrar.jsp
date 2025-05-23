<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.vista.spring.Inscritos"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
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
	
	String sCarga				= request.getParameter("fCarga");	
	String sSexotmp				= "X";	
	String sBgcolor				= "";	
	int contador 				= 0;
	
	List<Inscritos> lisAlumnos					= (List<Inscritos>) request.getAttribute("lisAlumnos");	
	HashMap<String,CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaEdades		 	= (HashMap<String,String>) request.getAttribute("mapaEdades");
%>
<div class="container-fluid">
	<h2>Unregistered Off-campus Students List</h2>
	<form name="forma" action="externos_sin_registrar?Accion=1" method="post">
	<div class="alert alert-info">
		<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		Start Date: <input data-format="hh:mm:ss" id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10"/>&nbsp;&nbsp;
		<span class="add-on">
    	 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
   		 </span>
		End Date: <input data-format="hh:mm:ss" id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10"/>&nbsp;&nbsp;
		<span class="add-on">
    	 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
   		 </span>
		<a href="javascript:Mostrar()" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>	
	</div>	
	</form>	  	
	<table id="table" class="table table-sm table-bordered">
<%			
	int row=0;
	for(Inscritos inscrito : lisAlumnos){ 
		row++;
		
		String nombreCarrera = "-";
		if (mapaCarreras.containsKey(inscrito.getCarreraId())){
			nombreCarrera = mapaCarreras.get(inscrito.getCarreraId()).getNombreCarrera();
		}
		String edad = "0";
		if (mapaEdades.containsKey(inscrito.getCodigoPersonal())){
			edad = mapaEdades.get(inscrito.getCodigoPersonal());
		}
		
		if(!inscrito.getSexo().equals(sSexotmp)){
			sSexotmp = inscrito.getSexo();
			row=1;
%>	 
	  <thead>  
	  <tr>
	    <th colspan = "6" class="alert alert-info"><h3>Gender : <%=sSexotmp.equals("F")?"Female":"Male"%></h3></th>
	  </tr>
	  </thead>	
	  <thead class="table-info">  
	  <tr> 
	  	<th width="7%"><spring:message code="aca.Numero"/></th>
	    <th width="13%"><spring:message code="aca.Matricula"/></th>
	    <th width="48%"><spring:message code="aca.Nombre"/></th>
	    <th width="22%"><spring:message code="aca.Carrera"/></th>
	    <th width="22%" style="text-align:right;"><spring:message code="aca.Edad"/></th>
	    <th width="22%">Enrollment</th>
	  </tr>
	  </thead>	
<%
		}				
%>
	  <tr class="tr2" <%=sBgcolor%>> 
	    <td width="7%" align="center"><font size="2"><%=row%></font></td>  
	    <td width="13%" align="center"><font size="2"><%=inscrito.getCodigoPersonal()%></font></td>
	    <td width="48%"><font size="2"><%=inscrito.getNombre()+" "+inscrito.getApellidoPaterno()+" "+inscrito.getApellidoMaterno()%></font></td>
	    <td width="22%"><font size="2"><%=nombreCarrera%></font></td>
	    <td width="22%" style="text-align:right;"><font size="2"><%=edad%></font></td>
	   	<td width="22%"><font size="2"><%=inscrito.getfInscripcion()%></font></td>
	  </tr> 
<%
	  
	 } // fin del for	
%>
	</table>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>