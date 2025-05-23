<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.vista.spring.Inscritos"%>
<%@ page import= "aca.alumno.spring.AlumAcademico"%>
<%@ page import= "aca.carga.spring.CargaPracticaAlumno"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.internado.spring.ComAutorizacion"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp"%>

<script type="text/javascript">
	function Mostrar(){		
		document.forma.submit();
	}
</script>

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />

<%	
	String fechaIni				= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin				= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");	
	String codigoTemp			= "0";
	
	String sexoTemp				= "X";	
	int contM					= 0;
	int contH					= 0;
	int con 					= 0;
	int row 					= 0;
	String modalidades 			= (String) session.getAttribute("modalidadesReportes");
	
	List<Inscritos> lisInscritos 				= (List<Inscritos>)request.getAttribute("lisInscritos");
	List<CargaPracticaAlumno> lisFechas			= (List<CargaPracticaAlumno>)request.getAttribute("lisFechas");
	HashMap<String,CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String, String> mapaRequerido		= (HashMap<String,String>) request.getAttribute("mapaRequerido");
	HashMap<String, Carga> mapaFechas			= (HashMap<String,Carga>) request.getAttribute("mapaFechas");
	HashMap<String, String> mapaResidencias		= (HashMap<String,String>) request.getAttribute("mapaResidencias");
	HashMap<String,ComAutorizacion> mapaComidas	= (HashMap<String,ComAutorizacion>) request.getAttribute("mapaComidas");
	HashMap<String, String> mapaDias			= (HashMap<String,String>) request.getAttribute("mapaDias");
	
%>

<div class="container-fluid">
	<h2>Dormitory Students by Gender List</h2>
	<div class="alert alert-info">
		<b>Note:</b> Students in red have not been registered.
	</div>
	<form name="forma" action="internos_genero?Accion=1" method="post">
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
	for (Inscritos inscrito : lisInscritos){
		
		if (!codigoTemp.equals(inscrito.getCodigoPersonal()) ){
			codigoTemp = inscrito.getCodigoPersonal();
			
			con++;					
		  	if(inscrito.getSexo().equals("F")){ contM++; }else{ contH++; }
			
		  	String requeridos = "Temporal";
		  	if(mapaRequerido.containsKey(inscrito.getCodigoPersonal())){
		  		requeridos = "Permanente";
		  	}
		  	
		  	if(!sexoTemp.equals(inscrito.getSexo())){
		  		if (!sexoTemp.equals("X")) out.print("</table>");
		  		
		  		sexoTemp = inscrito.getSexo();
				row=0;
			
%> 
	<div class = "alert alert-success">
		<h3>Gender: <% if (inscrito.getSexo().equals("F")) out.print("Female"); else out.print("Male");%></h3>
	</div>	  
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	    
	  	<tr>
		    <th><strong><spring:message code="aca.Numero"/></strong></th>    
		    <th><strong><spring:message code="aca.Matricula"/></strong></th>
		    <th><strong><spring:message code="aca.Nombre"/></strong></th>
		    <th><strong><spring:message code="aca.Carrera"/></strong></th>
		    <th><strong>Dormitory</strong></th>
		    <th><strong><spring:message code='aca.FechaInsc'/></strong></th>		    
		    <th><strong><spring:message code='aca.Tipo'/></strong></th>
		    <th><strong>Practical Dates</strong></th>
		    <th class="right"><strong>#Days</strong></th>
		    <th class="right"><strong>Days/Charged</strong></th>
		    <th><strong>Breakfast.</strong></th>
		    <th><strong>Lunch</strong></th>
		    <th><strong>Dinner</strong></th>
	  	</tr>
	</thead>
	<%		} 
		  	row++;
			String carreraNombre = "-";
			if (mapaCarreras.containsKey(inscrito.getCarreraId())){
				carreraNombre = mapaCarreras.get(inscrito.getCarreraId()).getNombreCarrera();
			}
			
			String fechasPracticas = "";
			int dias = 0;
			if(requeridos.equals("Temporal")){
				for(CargaPracticaAlumno practicas : lisFechas){
					if(inscrito.getCodigoPersonal().equals(practicas.getCodigoPersonal()) && inscrito.getBloqueId().equals(practicas.getBloqueId())){
						if(fechasPracticas.length()==0){
							fechasPracticas += aca.util.Fecha.getFechaConMesCorto(practicas.getFechaIni())+" al "+aca.util.Fecha.getFechaConMesCorto(practicas.getFechaFin());
						}else{
							fechasPracticas += ", "+aca.util.Fecha.getFechaConMesCorto(practicas.getFechaIni())+" al "+aca.util.Fecha.getFechaConMesCorto(practicas.getFechaFin());
						}
						dias += aca.util.Fecha.diasEntreFechas(practicas.getFechaIni(), practicas.getFechaFin())+1;
					}
				}
			}else{
				if (mapaFechas.containsKey(inscrito.getCargaId())){
					fechasPracticas += aca.util.Fecha.getFechaConMesCorto(mapaFechas.get(inscrito.getCargaId()).getIniInternado())+" al "+aca.util.Fecha.getFechaConMesCorto(mapaFechas.get(inscrito.getCargaId()).getFinInternado());
					dias = aca.util.Fecha.diasEntreFechas(mapaFechas.get(inscrito.getCargaId()).getIniInternado(), mapaFechas.get(inscrito.getCargaId()).getFinInternado())+1;
				}
			}
			
			String diasInternado = "0"; 
			if (mapaDias.containsKey(inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId())){
				diasInternado =  mapaDias.get(inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId());
			}
			
			if(fechasPracticas.length()==0){
				if(mapaResidencias.containsKey(inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId())){
					if((mapaResidencias.get(inscrito.getCodigoPersonal()+inscrito.getCargaId()+inscrito.getBloqueId())).equals("E")){
						fechasPracticas = "Enrolled as off-campus student";
					}
				}
			}
			
			String comidas = "000";
			if (mapaComidas.containsKey(inscrito.getCodigoPersonal())){
				comidas = mapaComidas.get(inscrito.getCodigoPersonal()).getTipoComida();
			}
			String desayuno = "NO";
			String comida	= "NO";
			String cena 	= "NO";
			if (comidas.substring(0,1).equals("1")) desayuno = "YES"; 
			if (comidas.substring(1,2).equals("1")) comida = "YES";
			if (comidas.substring(2,3).equals("1")) cena = "YES";
	%>
	  	<tr> 
		    <td width="3%" align="center"><b><%=row%></b></td>
		    <td width="5%"><%=inscrito.getCodigoPersonal() %></td>
		    <td width="20%"><%=inscrito.getApellidoPaterno()%>&nbsp;<%=inscrito.getApellidoMaterno()%>&nbsp;<%=inscrito.getNombre()%></td>
		    <td width="20%"><%=carreraNombre%></td>
		    <td width="5%"><%=inscrito.getDormitorio()%></td>
		    <td width="5%"><%=inscrito.getfInscripcion() %></td>
	    	<td width="5%"><%=requeridos%></td> 
	    	<td width="15%"><%=fechasPracticas%></td>
	    	<td width="5%" class="right"><%=dias%></td>
	    	<td width="5%" class="right"><%=diasInternado%></td>
	    	<td width="5%" class="center"><%=desayuno%></td>
	    	<td width="5%" class="center"><%=comida%></td>
	    	<td width="5%" class="center"><%=cena%></td>
	  	</tr> 
<%
		}
	 } // fin del for	
%>
	</table>	
	<div class="alert alert-success">
		<h3>Total by gender &nbsp; &nbsp; Males: <%=contH %> &nbsp; &nbsp; Females: <%=contM%> &nbsp;&nbsp; Dormitory Enrolled: <%=con%></h3>
	</div>      
<% 	if (con==0){ %>		
	<div align="center"><strong>No students enrolled in current blocks</strong></div>
<%	} %>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>
