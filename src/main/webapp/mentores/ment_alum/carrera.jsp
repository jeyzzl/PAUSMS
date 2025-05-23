<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>

<%@page import="aca.mentores.spring.MentAcceso"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.acceso.spring.Acceso"%>

<%
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String sModulo			= (String) request.getAttribute("sModulo");
	String sCarpeta     	= (String) request.getAttribute("sCarpeta");
	String periodoId		= (String) request.getAttribute("periodoId");
	String fecha 			= (String) request.getAttribute("fecha");

	MentAcceso mentAcceso 	= (MentAcceso) request.getAttribute("mentAcceso");
	Acceso acceso 			= (Acceso) request.getAttribute("acceso");
	
	session.setAttribute("ciclo", periodoId);	
   	String sFacultad		= "X";
   	String sBgcolor			= "";  	 	
   	String nombreFacultad	= "";  	 	
   	String numMentores		= "";  	 	
   	String numEntrevistas	= "";  	 	
 	
	List<CatPeriodo> lisPeriodo = (List<CatPeriodo>) request.getAttribute("lisPeriodo");
	List<CatCarrera> lisCarrera	= (List<CatCarrera>) request.getAttribute("lisCarrera");
	
	HashMap<String, CatFacultad> mapaFacultad = (HashMap<String, CatFacultad>) request.getAttribute("mapFacultad");
	HashMap<String, String> mapNumMentoresCarrera = (HashMap<String, String>) request.getAttribute("mapNumMentoresCarrera");
	HashMap<String, String> mapEntrevistasCarrera = (HashMap<String, String>) request.getAttribute("mapEntrevistasCarrera");
%>

<div class="container-fluid">
	<h2>School and Degree</h2>
	<form id="forma" name="forma" action="carrera" method="post" id="noayuda">
	<div class="alert alert-info d-flex align-items-center">
		<select id="periodo" name="PeriodoId" onchange="document.forma.submit();" class="form-select" style="width:200px">
		<%for(CatPeriodo per : lisPeriodo){%>
			<option value="<%=per.getPeriodoId() %>"<%=per.getPeriodoId().equals(periodoId)?" Selected":"" %>><%=per.getNombre() %></option>
		<%}%>
		</select>&nbsp;&nbsp;
		<input data-date-format="dd/mm/yyyy" type="text" name="fechaMentores" value="<%=fecha %>" class="form-control" style="width:120px"/>&nbsp;&nbsp;
		<button class="btn btn-primary"><i class="fas fa-sync-alt"></i> Update</button>&nbsp;
		<a href="altaMentor?PeriodoId=<%=periodoId%>" class="btn btn-primary"><i class="fas fa-plus"></i> Mentor</a>		
	</div>
	<table id="table" class="table table-sm table-bordered">
<%	for (int i= 0; i<lisCarrera.size(); i++){
		CatCarrera carrera	= (CatCarrera) lisCarrera.get(i);
		nombreFacultad 	= "";			
		if(mentAcceso.getAcceso().indexOf(carrera.getCarreraId()) != -1 || acceso.getAdministrador().equals("S") ){
			if(!sFacultad.equals(carrera.getFacultadId())){
				
				sFacultad = carrera.getFacultadId();
				if(mapaFacultad.containsKey(sFacultad)){
					nombreFacultad = mapaFacultad.get(sFacultad).getNombreFacultad();
				}					
%>
	<thead>	 
	<tr> 
 		<th colspan="5"><h3><%=nombreFacultad%></h3></th>
	</tr>
	</thead>
	<thead class="table-info">	 
	<tr>
		<th>Course</th>
	 	<th class="right">N° Mentors</th>
	  	<th class="right">N° Interviews</th>
	</tr>
	</thead>
<%			} //fin del if facultad
				
			numMentores 	= "0";
			if(mapNumMentoresCarrera.containsKey(carrera.getCarreraId()+periodoId)){
				numMentores = mapNumMentoresCarrera.get(carrera.getCarreraId()+periodoId);
			}
				
			numEntrevistas 	= "0";
			if(mapEntrevistasCarrera.containsKey( carrera.getCarreraId() )){
				numEntrevistas = mapEntrevistasCarrera.get(carrera.getCarreraId());
			}
%>
	<tr> 
   		<td><a href="mentor?carrera=<%=carrera.getCarreraId()%>"><%=carrera.getCarreraId()%>: <%=carrera.getNombreCarrera()%></a></td>
		<td style="text-align:right"><%=numMentores%></td>
		<td style="text-align:right"><%=numEntrevistas %></td>
	</tr>
<%		}
	}
%>
	</table>
	</form>
</div>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>
	jQuery('input[name="fechaMentores"]').datepicker();;	
</script>