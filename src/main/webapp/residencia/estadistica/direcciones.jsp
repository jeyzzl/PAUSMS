<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.residencia.spring.ResDatos"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.vista.spring.Estadistica"%>

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
	
	String facultadId			= "X";
	String facultadTemp			= "0";
	String bgColor			= ""; 
	int cont               	= 0;
	
	List<Estadistica> lisAlumnos						= (List<Estadistica>) request.getAttribute("lisAlumnos");
	
	HashMap<String,ResDatos> mapaDatos			 	= (HashMap<String,ResDatos>) request.getAttribute("mapaDatos");
	HashMap<String,String> mapaRazones 				= (HashMap<String,String>) request.getAttribute("mapaRazones");
	HashMap<String,CatFacultad> mapaFacultades 		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaInscritos 			= (HashMap<String,String>) request.getAttribute("mapaInscritos");
%>
<div class="container-fluid">
	<h2>Address Listing</h2>
	<form name="forma" action="direcciones" method="post">
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
		<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>	
	</div>	
	</form>
<% 
	String codigoAnterior = "X";
	for (Estadistica inscrito : lisAlumnos){
		
		if (!codigoAnterior.equals(inscrito.getCodigoPersonal())){
			cont++;
			codigoAnterior = inscrito.getCodigoPersonal();
			
			String carreraNombre	= "-";
			String facultadNombre	= "-";
			if (mapaCarreras.containsKey(inscrito.getCarreraId())){
				facultadId 			= mapaCarreras.get(inscrito.getCarreraId()).getFacultadId();
				carreraNombre		= mapaCarreras.get(inscrito.getCarreraId()).getNombreCarrera();
				if (mapaFacultades.containsKey(facultadId)){
					facultadNombre 	= mapaFacultades.get(facultadId).getNombreFacultad();
				}
			}
			
			String razonId		= "0";
			ResDatos resDatos 	= new ResDatos();
			if (mapaDatos.containsKey(inscrito.getCodigoPersonal()) && inscrito.getResidenciaId().equals("E") ){
				razonId 	= mapaDatos.get(inscrito.getCodigoPersonal()).getRazon();
				resDatos 	= mapaDatos.get(inscrito.getCodigoPersonal());
			}
			
			String razon		= "-";
			if(mapaRazones.containsKey(resDatos.getRazon())){
				razon = mapaRazones.get(resDatos.getRazon());
			}
			
			boolean esInscrito = false;
			if (mapaInscritos.containsKey(inscrito.getCodigoPersonal())){
				esInscrito = true;
			}
			
			if ( !facultadId.equals(facultadTemp) ){			
				if (!facultadTemp.equals("0")) out.print("</table>");
				facultadTemp = facultadId;
%>
	<div class="alert alert-success"><%=facultadNombre%></div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	<tr> 
		<th width="3%"><spring:message code="aca.Numero"/></th>
		<th width=5%">Load</th>
	    <th width="5%"><spring:message code="aca.Matricula"/></th>
	    <th width="17%"><spring:message code="aca.Nombre"/></th>
	    <th width="5%"><spring:message code="aca.Genero"/></th>
	    <th width="17%"><spring:message code="aca.Carrera"/></th>
	    <th width="5%"><spring:message code="aca.Residencia"/></th>
	    <th width="5%">Reason</th>
	    <th width="8%"><spring:message code="aca.Tutor"/></th>
	    <th width="8%">Neighborhood</th>
	    <th width="8%">Street and #</th>
	    <th width="5%"><spring:message code="aca.Telefono"/></th>
		<th width="5%"><spring:message code="aca.FechaInsc"/></th>
		<th width="5%">Enrolled</th>
  	</tr>
  	</thead>
<%			} %>
  	<tr> 
	    <td align="center"><font size="1"><%= cont%></font></td>
	    <td align="center"><font size="1"><%=inscrito.getCargaId()%></font></td>
	    <td align="center"><font size="1"><%=inscrito.getCodigoPersonal()%></font></td>
	    <td align="left"><font size="1"><%=inscrito.getApellidoPaterno()%> <%=inscrito.getApellidoMaterno()%> <%=inscrito.getNombre()%></font></td>
	    <td align="left"><font size="1"><%=inscrito.getSexo().equals("F")?"Female":"Male"%></font></td> 
	    <td align="left"><font size="1"><%=carreraNombre%></font></td>	    
	    <td align="left"><font size="1"><%=inscrito.getResidenciaId().equals("E")?"Off-campus":"Dormitory"%></font></td>	    
	    <td align="left"><font size="1"><%=razon%></font></td>
	    <td align="left"><font size="1"><%=resDatos.getNombreTut()+" "+resDatos.getApellidoTut()%></font></td>
	    <td align="left"><font size="1"><%=resDatos.getColonia()%></font></td>
	    <td align="left"><font size="1"><%=resDatos.getCalle()+" "+resDatos.getNumero()%></font></td>
	    <td align="left"><font size="1"><%=resDatos.getTelArea()+" "+resDatos.getTelNum()%></font></td>
	    <td align="left"><font size="1"><%=resDatos.getFecha() %></font></td>
	    <td align="left"><font size="1"><%=esInscrito?"YES":"NO"%></font></td>
  	</tr>
<% 
		}
	}
%>  
	</table>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>