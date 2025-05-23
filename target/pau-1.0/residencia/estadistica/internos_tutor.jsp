<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.acceso.spring.Acceso"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.vista.spring.Inscritos"%>
<%@ page import= "aca.alumno.spring.AlumUbicacion"%>

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
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String sCarga				= request.getParameter("fCarga");
	String modalidades 			= (String) session.getAttribute("modalidadesReportes");
	Acceso acceso 				= (Acceso) request.getAttribute("acceso");
	
	String sFacultad 			= "";
	String sCarrera 			= "";
	String sCarreraTemp			= "";	
	int cont					= 0;
	int hombres					= 0;
	int mujeres					= 0;
	int dormi1					= 0;
	int dormi2					= 0;
	int dormi3					= 0;
	int dormi4					= 0;
	int sinDormi				= 0;	
	
	List<Inscritos> lisAlumnos					= (List<Inscritos>) request.getAttribute("lisAlumnos");
	HashMap<String,CatFacultad> mapaFacultades 	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,AlumUbicacion> mapaUbicaciones 	= (HashMap<String,AlumUbicacion>) request.getAttribute("mapaUbicaciones");
%>
<div class="container-fluid">
	<h2>Dormitory Students by School and Course</h2>	
	<form name="forma" action="internos_tutor" method="post">
		<div class="alert alert-info">
			<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
			Start date: <input data-format="hh:mm:ss" id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10"/>&nbsp;&nbsp;
			<span class="add-on">
     		 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
   			 </span>
			End date: <input data-format="hh:mm:ss" id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10"/>&nbsp;&nbsp;
			<span class="add-on">
     		 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
   			 </span>
			<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>	
		</div>	
	</form> 
	<table class="table">
<%
	int contador = 0;
	for(Inscritos inscritos : lisAlumnos){
		String color="";
		cont++;
		
		String facultadId 		= "X";
		String carreraNombre 	= "-";
		String facultadNombre	= "-";
		if (mapaCarreras.containsKey(inscritos.getCarreraId())){
			facultadId 		= mapaCarreras.get(inscritos.getCarreraId()).getFacultadId();
			carreraNombre 	= mapaCarreras.get(inscritos.getCarreraId()).getNombreCarrera();
			if (mapaFacultades.containsKey(facultadId)){
				facultadNombre = mapaFacultades.get(facultadId).getNombreFacultad();
			}
		}
		
    	if( acceso.getAccesos().indexOf(sCarreraTemp) != -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){
		  	contador++;	
	  		if(!sFacultad.equals(facultadId)){
	    		sFacultad = facultadId;		    		
%>
	</table>
	<table id="table" class="table table-sm table-bordered">	
	  	<tr>
	    	<td align="center" colspan="7"><font color="#000099" size="3"><b><%=facultadNombre%></b></font></td>
	  	</tr>
<%  
	   	  	}//fin del if de facultades diferentes
		  	if(!sCarrera.equals(inscritos.getCarreraId())){
		  		sCarrera = inscritos.getCarreraId();				
				contador = 1;
%>
	</table><br>  
	<table id="table" class="table table-sm table-bordered">
		<thead>	
		<tr> 
	    	<td colspan="7"><b><font color="#000099" size="2"><%=carreraNombre%></font></b></td>
	  	</tr>
	  	</thead>
		<thead class="table-info">	
	  	<tr> 
		    <th><spring:message code="aca.Numero"/></th>
		    <th><spring:message code="aca.Matricula"/></th>
		    <th><spring:message code="aca.Nombre"/></th>
		    <th>Financial Mentor</th>
		    <th>Gender</th>
		    <th><spring:message code='aca.Dormitorio'/></th>
		    <th><spring:message code='aca.FechaInsc'/></th>
	  	</tr>
	  	</thead>
<%        	}//fin del if de carreras diferentes
		  			
			String mentorNombre = "-";
			if (mapaUbicaciones.containsKey(inscritos.getCodigoPersonal())){
				mentorNombre = mapaUbicaciones.get(inscritos.getCodigoPersonal()).gettNombre();
			}
			
			if(inscritos.getSexo().equals("M"))
				hombres++;
			else
				mujeres++;
				
			if( inscritos.getDormitorio().equals("1"))
				dormi1++;
			else if(inscritos.getDormitorio().equals("2"))
				dormi2++;
			else if(inscritos.getDormitorio().equals("3"))
				dormi3++;
			else if(inscritos.getDormitorio().equals("4"))
				dormi4++;
			else{
				sinDormi++;
			}	
			
			String sexo = inscritos.getSexo();
%>
	  	<tr>
		    <td align="center"><%=contador%></td>
		    <td align="center"><%=inscritos.getCodigoPersonal()%></td>
		   	<td align="center"><%=inscritos.getApellidoPaterno() %> <%=inscritos.getApellidoMaterno() %> <%=inscritos.getNombre() %></td>
		    <td><%=mentorNombre%></td> 
		    <td align="center"><%= sexo.equals("M")?"Male":"Female"%></td>
		    <td><%=inscritos.getDormitorio() %>
		    <td><%=inscritos.getfInscripcion() %></td>
	  	</tr>
<%
			}    	
		} // fin del while
	%>
	  	<tr><td colspan="6">&nbsp;</td></tr>
	  	<tr>
	  		<td colspan="6" class="end"><spring:message code="aca.FinDelListado"/></td>
	  	</tr>
	  	<tr><td colspan="6">&nbsp;</td></tr>
	  	<tr>
		  	<td colspan="6" align="center">
		  		<table class="table">
		  			<tr>
		  				<th colspan="5">Dormitory Total</th>
		  			</tr>
		  			<tr>
		  				<td align="center"><b>Dorm 1:</b> <%=dormi1 %></td>
		  				<td align="center"><b>Dorm 2:</b> <%=dormi2 %></td>
		  				<td align="center"><b>Dorm 3:</b> <%=dormi3 %></td>
		  				<td align="center"><b>Dorm 4:</b> <%=dormi4 %></td>
		  				<td align="center"><b>Without Dorm:</b> <%=sinDormi %></td>
		  			</tr>
		  		</table>
		  	</td>
	  	</tr>
	  	<tr>
	  		<td colspan="6">&nbsp;</td>
	  	</tr>
	  	<tr>
		  	<td colspan="6" align="center">
		  		<table class="table">
		  			<tr>
		  				<th colspan="3"><spring:message code="aca.Inscritos"/></th>
		  			</tr>
		  			<tr>
		  				<td align="center"><b>Males:</b> <%=hombres %></td>
		  				<td align="center"><b>Females:</b> <%=mujeres %></td>
		  				<td align="center"><b>Dormitory Enrolled:</b> <%=hombres+mujeres %></td>
		  			</tr>
		  		</table>
		  	</td>
	  	</tr>
	</table>
<%	if(cont <= 0){%>
	<div align="center">
	<div align="center"><strong>No students enrolled in current blocks</strong></div>
<%	}%>
	</div>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>