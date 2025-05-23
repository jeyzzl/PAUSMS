	<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatPais"%>
<%@ page import= "aca.catalogo.spring.CatReligion"%>
<%@ page import= "aca.catalogo.spring.CatTipoAlumno"%>

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
	String modalidades 			= (String) session.getAttribute("modalidadesReportes");
	String fechaIni				= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin				= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	String opcion  				= request.getParameter("opcion")==null?"Todos":request.getParameter("opcion");	
	String dormitorio 			= "";	
	int totNacion				= 0;
	int totReligion				= 0;
	int totEdad					= 0;
	int totTipoAlum				= 0;
	
	if (opcion.equals("Todos")) 
		dormitorio = "'1','2','3','4'";
	else
		dormitorio = "'"+opcion+"'";
	
	List<String> lisEdades 		= (List<String>)request.getAttribute("lisEdades");
	List<String> lisReligiones 	= (List<String>)request.getAttribute("lisReligiones");
	List<String> lisNaciones	= (List<String>)request.getAttribute("lisNaciones");
	List<String> lisTipos		= (List<String>)request.getAttribute("lisTipos");
	
	// map de edades
	HashMap<String, String> mapaEdades 				= (HashMap<String,String>)request.getAttribute("mapaEdades");	
	// map de religiones
	HashMap<String, String> mapaReligiones			= (HashMap<String,String>)request.getAttribute("mapaReligiones");	
	// map de religiones
	HashMap<String, String> mapaNaciones			= (HashMap<String,String>)request.getAttribute("mapaNaciones");
	// map de tipo de alumnos
	HashMap<String, String> mapaTipos				= (HashMap<String,String>)request.getAttribute("mapaTipos");		
	// map de catalogo de religiones
	HashMap<String, CatReligion> mapaCatReligiones	= (HashMap<String,CatReligion>)request.getAttribute("mapaCatReligiones");
	// map de catalogo de religiones
	HashMap<String, CatPais> mapaPaises				= (HashMap<String,CatPais>)request.getAttribute("mapaPaises");
	// map de catalogo de religiones
	HashMap<String, CatTipoAlumno> mapaCatTipos		= (HashMap<String,CatTipoAlumno>)request.getAttribute("mapaCatTipos");	
%>

<div class="container-fluid">	
	<h2>Statistics</h2>
	<form id="forma" name="forma" action="estadisticas" method="post" >
		<div class="alert alert-info">
			<a href="menu" class="btn btn-primary"><i class="icon-arrow-left icon-white"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
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
	
		<table class="table">	
		  <tr align=left> 
		    <td height="18" colspan="5" valign="top">		    
		    	<select id="opcion" name="opcion" onchange="document.forma.submit();">	    		    		
					<option value ="Todos" <% if (opcion.equals("Todos")) out.print("selected"); %>>Off-campus Statistics</option>
					<option value ="1" <% if (opcion.equals("1")) out.print("selected"); %>>Dormitory # 1 Statistics</option>
					<option value ="2" <% if (opcion.equals("2")) out.print("selected"); %>>Dormitory # 2 Statistics</option>
					<option value ="3" <% if (opcion.equals("3")) out.print("selected"); %>>Dormitory # 3 Statistics</option>
					<option value ="4" <% if (opcion.equals("4")) out.print("selected"); %>>Dormitory # 4 Statistics</option>	
		    	</select>		    	
		    </td>
		  </tr>
		</table>
	</form>	
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th>Country</th>
			<th Style="text-align:right;"><spring:message code="aca.Total"/></th>
		</tr>
	</thead>
<%	
	for(String nacion : lisNaciones){
		String nombreNacion	= "";
		if (mapaPaises.containsKey(nacion)){
			nombreNacion = mapaPaises.get(nacion).getNombrePais();
		}
			
		String totalNacion 	= "0";			
		if (mapaNaciones.containsKey(nacion)){
				totalNacion = mapaNaciones.get(nacion);
				totNacion=totNacion+Integer.parseInt(totalNacion); 
		}		
%>
		<tr>
			<td><%=nombreNacion%></td>
			<td Style="text-align:right;"><%=totalNacion%></td>
		</tr>
<%	 
	}
%> 
		<tr>
			<td style="font-weight:bold">TOTAL</td>
			<td style="text-align:right; font-weight:bold"><%=totNacion%></td>
		</tr>
	</table><br><br>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th><spring:message code="aca.Edad"/></th>
			<th Style="text-align:right;"><spring:message code="aca.Total"/></th>
		</tr>
	</thead>
<%
	for(String edad : lisEdades){						
		String totalEdad	= "0";			
		if (mapaEdades.containsKey(edad)){
			totalEdad	= mapaEdades.get(edad);
			totEdad		= totEdad+Integer.parseInt(totalEdad);
		}
%>
		<tr>
			<td><%=edad%></td>
			<td Style="text-align:right;"><%=totalEdad%></td>
		</tr>
<%
	}
%>
		<tr>
			<td style="font-weight:bold">TOTAL</td>
			<td Style="text-align:right; font-weight:bold"><%=totEdad%></td>
		</tr>
	</table><br><br>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th>Religion</th>
			<th><spring:message code="aca.Total"/></th>
		</tr>
	</thead>
<%
	for(String religion : lisReligiones){		
		String nombreReligion	= "";
		if (mapaCatReligiones.containsKey(religion)){
			nombreReligion = mapaCatReligiones.get(religion).getNombreReligion();
		}
		
		String totalReligion 	= "0";			
		if (mapaReligiones.containsKey(religion)){
			totalReligion 	= mapaReligiones.get(religion);
			totReligion		= totReligion+Integer.parseInt(totalReligion);
		}
%>
		<tr>
			<td><%=nombreReligion%></td>
			<td Style="text-align:right;"><%=totalReligion%></td>
		</tr>
<%
	}
%>
		<tr>
			<td style="font-weight:bold">TOTAL</td>
			<td Style="text-align:right; font-weight:bold"><%=totReligion%></td>
		</tr>		
	</table><br><br>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th><spring:message code="aca.TipoAlumno"/></th>
			<th Style="text-align:right;"><spring:message code="aca.Total"/></th>
		</tr>
	</thead>
<%
	for(String tipo : lisTipos){		
		String nombreTipo	= "";
		if (mapaCatTipos.containsKey(tipo)){
			nombreTipo = mapaCatTipos.get(tipo).getNombreTipo();
		}
			
		String totalTipo 	= "0";			
		if (mapaTipos.containsKey(tipo)){
			totalTipo 		= mapaTipos.get(tipo);
			totTipoAlum	= totTipoAlum+Integer.parseInt(totalTipo);
		}
%>
		<tr>
			<td><%=nombreTipo%></td>
			<td Style="text-align:right;"><%=totalTipo%></td>
		</tr>
<%
	}
%>
		<tr>
			<td style="font-weight:bold">TOTAL</td>
			<td Style="text-align:right; font-weight:bold"><%=totTipoAlum%></td>
		</tr>
	</table>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>