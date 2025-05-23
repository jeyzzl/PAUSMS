<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.ssoc.spring.SsocInicio"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatPais"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>

<script type="text/javascript">
	function Mostrar(){		
		document.forma.submit();
	}
</script>
<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />

<% 	
	List<SsocInicio> lisActivos					= (List<SsocInicio>) request.getAttribute("lisActivos");

	String fechaIni								= (String) request.getAttribute("fechaIni");
	String fechaFin								= (String) request.getAttribute("fechaFin");
	
	HashMap<String,String> mapaAlumnos			= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
	HashMap<String,CatFacultad> mapaFacultades 	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaCarreraPlan		= (HashMap<String,String>) request.getAttribute("mapaCarreraPlan");
	HashMap<String,CatPais> mapaPaises 			= (HashMap<String,CatPais>) request.getAttribute("mapaPaises");
	HashMap<String,String> mapaNacionalidades	= (HashMap<String,String>) request.getAttribute("mapaNacionalidades");
	HashMap<String, String> mapaAvance		 	= (HashMap<String, String>)request.getAttribute("mapaAvance");
	HashMap<String,String> mapaAsignaciones	 	= (HashMap<String, String>)request.getAttribute("mapaAsignaciones");
	
	String facTemp = "X";
%>

<body>
<div class="container-fluid">
	<h3>Alumnos Activos</h3>
	<form name="forma" action="activos" method="post">
		<div class="alert alert-info">
			<a href="menu" class="btn btn-primary">Regresar</a>
			Fecha Inicio: <input data-format="hh:mm:ss" id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10"/>&nbsp;&nbsp;
			<span class="add-on">
	    		 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
	  			 </span>
			Fecha Final: <input data-format="hh:mm:ss" id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10"/>&nbsp;&nbsp;
			<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>
		</div>
	</form>
	<table class="table table-condensed table-nohover"> 
		<tr> 
		    <th width="4%"><spring:message code="aca.Numero"/></th>
		    <th width="7%"><spring:message code="aca.Matricula"/></th>
		    <th width="7%">Facultad</th>
		    <th width="32%"><spring:message code="aca.Nombre"/></th>
		    <th width="10%"> Plan </th>
		    <th width="10%"> F. Inicio </th>
		    <th width="5%">Porcentaje</th>
		    <th width="7%">Semestre</th>
		    <th width="7%">% Actual</th>
		    <th width="7%">Nacionalidad</th>
		    <th width="7%">Institución</th>
	  	</tr>
<%	
	int row = 0;
	for (SsocInicio activos : lisActivos){
		row++;
		String carreraPlan	= "-";	
		String facultadId	= "-";
		if (mapaCarreraPlan.containsKey(activos.getPlanId())){
			carreraPlan = mapaCarreraPlan.get(activos.getPlanId());
			
			if (mapaCarreras.containsKey(carreraPlan)){
				facultadId = mapaCarreras.get(carreraPlan).getFacultadId(); 
			}
		}
		
		String nombreFacultad = "";
		if (mapaFacultades.containsKey(facultadId)){
			nombreFacultad = mapaFacultades.get(facultadId).getNombreFacultad();
		}
	
		String nombreAlumno = "-";
	 	if(mapaAlumnos.containsKey(activos.getCodigoPersonal())){
	 		nombreAlumno = mapaAlumnos.get(activos.getCodigoPersonal());
	 	}
	 	
	 	String nacionalidadAlumno = "91";
	 	if(mapaNacionalidades.containsKey(activos.getCodigoPersonal())){
	 		nacionalidadAlumno = mapaNacionalidades.get(activos.getCodigoPersonal());
	 	}
	 	
	 	String nacionalidad = "-";
	 	if(mapaPaises.containsKey(nacionalidadAlumno)){
	 		nacionalidad = mapaPaises.get(nacionalidadAlumno).getNombrePais();
	 	}
	 	
	 	String avance = "0";
	 	if(mapaAvance.containsKey(activos.getCodigoPersonal()+activos.getPlanId())){
	 		avance = mapaAvance.get(activos.getCodigoPersonal()+activos.getPlanId());
	 	} 
	 	
		if (!facTemp.equals(facultadId)){
			facTemp = facultadId;
		}
		
		String sector = "";
		if(mapaAsignaciones.containsKey(activos.getCodigoPersonal())){
			sector = mapaAsignaciones.get(activos.getCodigoPersonal());
			
			if(sector.equals("1")){
				sector = "Privada";
			}else if(sector.equals("2")){
				sector = "Publica";
			}else if(sector.equals("3")){
				sector = "Social";
			}else if(sector.equals("4")){
				sector = "Educativo";
			}
		}
%> 	
  		<tr>
	  		<td align="center"><font size="1"><%=row%></font></td>
	  	  	<td align="center"><font size="1"><%=activos.getCodigoPersonal()%></font></td>
	   	 	<td align="left"><font size="1"><%=nombreFacultad%></font></td> 
	   	 	<td align="left"><font size="1"><%=nombreAlumno%></font></td> 
	  	  	<td align="center"><font size="1"><%=activos.getPlanId()%></font></td>
	    	<td align="center"><font size="1"><%=activos.getFecha().substring(0, 10)%></font></td> 
	    	<td align="center"><font size="1"><%=activos.getPorcentaje()%>%</font></td>
	    	<td align="center"><font size="1"><%=activos.getSemestre()%></font></td>
	    	<td align="center"><font size="1"><%=avance%>%</font></td>
	    	<td align="center"><font size="1"><%=nacionalidad %></font></td> 
	    	<td align="center"><font size="1"><%=sector %></font></td> 
   		</tr>
<% 	}%>
	</table>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>
</body>