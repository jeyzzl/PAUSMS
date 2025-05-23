<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.vista.spring.Inscritos"%>
<%@ page import= "aca.residencia.spring.ResDatos"%>
<%@ page import= "aca.residencia.spring.ResComentario"%>

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
	
	String facultadTemp 	= "X";
	String carreraTemp 		= "X";
	int contM				= 0;
	int contH				= 0;
	String sexoTemp			= "";	
	
	List<Inscritos> lisAlumnos						= (List<Inscritos>) request.getAttribute("lisAlumnos");	
	
	HashMap<String,ResDatos> mapaDatos		 		= (HashMap<String,ResDatos>) request.getAttribute("mapaDatos");
	HashMap<String,CatFacultad> mapaFacultades 		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String, String>	mapaRazones				= (HashMap<String,String>) request.getAttribute("mapaRazones");
	HashMap<String,ResComentario> mapaComentarios	= (HashMap<String,ResComentario>) request.getAttribute("mapaComentarios");
%>
<div class="container-fluid">
	<h2>Off-campus Students by School and Course</h2>
	<div class="alert alert-info"><b>Note:</b> Students in red have not been registered. </div>
	<form name="forma" action="externos_tutor" method="post">
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
	
	<table class="table">
<%						
		int contador 		= 0;
		int noRegistrados 	= 0;
		for(Inscritos inscrito : lisAlumnos){
			contador++;			
	  		if(inscrito.getSexo().equals("F")){ contM++; }else{ contH++; }
	  		
	  		String carreraNombre 		= "-";
	  		String facultadId			= "-";
	  		String facultadNombre		= "-";
			if (mapaCarreras.containsKey(inscrito.getCarreraId())){
				carreraNombre 	= mapaCarreras.get(inscrito.getCarreraId()).getNombreCarrera();
				facultadId 		= mapaCarreras.get(inscrito.getCarreraId()).getFacultadId();
				if (mapaFacultades.containsKey(facultadId)){
					facultadNombre = mapaFacultades.get(facultadId).getNombreFacultad();
				}
			}
			if(!facultadTemp.equals(facultadId)){
			   	facultadTemp = facultadId;
%>
	</table>
	<table class="table" id="noayuda"> 
	  	<tr> 
	    	<td align="center" colspan="11"><b><font size="3"><%=facultadNombre%></font></b></td>
	  	</tr>
<%  
			}//fin del if de facultades diferentes
				
			if(!carreraTemp.equals(inscrito.getCarreraId())){
				carreraTemp = inscrito.getCarreraId();		  		
%>
	</table>
	<br>
	<table id="table" class="table table-sm table-bordered">
	<thead>	
	  	<tr> 
	    	<td colspan="11"><b><font size="2">Program: <%=carreraTemp%> <%=carreraNombre%></font></b></td>
	  	</tr>
	</thead>
	<thead class="table-info">	
	  	<tr>
		  	<th><spring:message code="aca.Numero"/></th> 
		    <th><spring:message code="aca.Matricula"/></th>
		    <th><spring:message code="aca.Nombre"/></th>
		    <th><spring:message code="aca.Tipo"/></th>
		    <th>Reason</th>
		    <th>Residence Tutor</th>
		    <th>Neighborhood</th>
		    <th>Street</th>
		    <th>Number</th>
		    <th><spring:message code="aca.Telefono"/></th>
	    	<th><spring:message code="aca.Genero"/></th>
			<th><spring:message code="aca.Comentario"/></th>
	    	<th><spring:message code="aca.FechaInsc"/></th>
	  	</tr>
	</thead>
<%   
			}//fin del if de carreras diferentes
		 
			boolean registrado 	= false;
			String razonId		= "0";
			ResDatos resDatos 	= new ResDatos();
			if (mapaDatos.containsKey(inscrito.getCodigoPersonal())){
				registrado 	= true;
				razonId 	= mapaDatos.get(inscrito.getCodigoPersonal()).getRazon();
				resDatos 	= mapaDatos.get(inscrito.getCodigoPersonal());
			}

			String comentario = "";
			if(mapaComentarios.containsKey(inscrito.getCodigoPersonal())){
				comentario = mapaComentarios.get(inscrito.getCodigoPersonal()).getComentario();
			}
			
			if (registrado==false) noRegistrados++;
  			
  			// Busca el nombre de la razon
  			String razon		= "-";
  			if(mapaRazones.containsKey(razonId)){
  				razon = mapaRazones.get(razonId);
  			}
%>
	  	<tr>
		  	<td><font <%=!registrado?" color=\"red\"":"" %>><%=contador%></font></td>
		    <td><font <%=!registrado?" color=\"red\"":"" %>><%=inscrito.getCodigoPersonal()%></font></td>
		    <td><font <%=!registrado?" color=\"red\"":"" %>><%=inscrito.getNombre()%> <%=inscrito.getApellidoPaterno()%> <%=inscrito.getApellidoMaterno()%></font></td>
		    <td> <font <%=!registrado?" color=\"red\"":"" %>><%=razonId%></font></td>
		    <td><font <%=!registrado?" color=\"red\"":"" %>><%=razon%></font></td>
		    <td><font <%=!registrado?" color=\"red\"":"" %>><%=resDatos.getNombreTut()%> <%=resDatos.getApellidoTut()%></font></td>
		    <td><font <%=!registrado?" color=\"red\"":"" %>><%=resDatos.getColonia()%></font></td>
		    <td><font <%=!registrado?" color=\"red\"":"" %>><%=resDatos.getCalle()%></font></td>
		    <td><font <%=!registrado?" color=\"red\"":"" %>><%=resDatos.getNumero()%></font></td>
		    <td><font <%=!registrado?" color=\"red\"":"" %>><%=resDatos.getTelNum()%></font></td>
		    <td><font <%=!registrado?" color=\"red\"":"" %>><%=inscrito.getSexo()%></font></td>
			<td><%=comentario%></td>
		    <td><%=inscrito.getfInscripcion()%></td>
	  	</tr>
<%
		} // fin del for
%>
	</table>
<%
		if(contador > 0){
%>
	<table class="table">
	  	<tr>
	  		<th colspan="9">Totals</th>
	  	</tr>
	  	<tr>
		    <td align="center"><b>Males:</b> <%=contH%></td>
		    <td align="center"><b>Females:</b> <%=contM%></td>
		    <td align="center"><b>Unregistered:</b> <%=noRegistrados%></td>
		    <td align="center"><b>Off-campus Enrolled:</b> <%=contH+contM%></td>
	  	</tr>
	</table>
	
<%
	 	}else{
%>
	<div align="center"><strong>No students enrolled in current block</strong><br></div>
<% 
	 	}
%>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>