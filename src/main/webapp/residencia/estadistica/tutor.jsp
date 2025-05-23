<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatTipoAlumno"%>
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
		
	String fechaHoy 		= aca.util.Fecha.getHoy(); 
	String modalidades 		= (String) session.getAttribute("modalidadesReportes");
		
	List<Inscritos> lisAlumnos						= (List<Inscritos>) request.getAttribute("lisAlumnos");	
	
	HashMap<String,CatTipoAlumno> mapaTipos		 	= (HashMap<String,CatTipoAlumno>) request.getAttribute("mapaTipos");
	HashMap<String,AlumUbicacion> mapaUbicaciones 	= (HashMap<String,AlumUbicacion>) request.getAttribute("mapaUbicaciones");
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
%>
<div class="container-fluid">
	<h1>Enrolled Student's Tutor</h1>
	<form name="forma" action="tutor" method="post">
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
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	
		<tr>
			<th><spring:message code="aca.Numero"/></th>
			<th><spring:message code="aca.Matricula"/></th>
			<th><spring:message code="aca.Nombre"/></th>					
			<th><spring:message code="aca.Carrera"/></th>	
			<th><spring:message code="aca.Genero"/></th>
			<th><spring:message code="aca.Tutor"/></th>
			<th>Res</th>
			<th>Marital Status</th>
			<th><spring:message code="aca.Tipo"/></th>
			<th><spring:message code="aca.FechaInsc"/></th>
		</tr>	
	</thead>				
<%
	int row=0;
	for(Inscritos inscritos : lisAlumnos){
		row++;
		String nombreCarrera = "";
		if (mapaCarreras.containsKey(inscritos.getCarreraId())){
			nombreCarrera 	= mapaCarreras.get(inscritos.getCarreraId()).getNombreCarrera();
		}
		
		String tutor = "";
		if ( mapaUbicaciones.containsKey( inscritos.getCodigoPersonal()) ){
			tutor = mapaUbicaciones.get(inscritos.getCodigoPersonal()).gettNombre();
		}
		
		String tipoAlumno = "0";
		if (mapaTipos.containsKey( inscritos.getTipoAlumnoId())){
			tipoAlumno = mapaTipos.get(inscritos.getTipoAlumnoId()).getNombreTipo();
		}
		
		String estadoCivil = "-";
		if (inscritos.getEstadoCivil().equals("S")) estadoCivil = "Single";
		if (inscritos.getEstadoCivil().equals("C")) estadoCivil = "Married";
		if (inscritos.getEstadoCivil().equals("V")) estadoCivil = "Widowed";
		if (inscritos.getEstadoCivil().equals("D")) estadoCivil = "Divorced";
%>
		<tr class="tr2">
			<td align="center"><%=row%></td>
			<td align="center"><%=inscritos.getCodigoPersonal() %></td>
			<td><%=inscritos.getApellidoPaterno() %> <%=inscritos.getApellidoMaterno() %> <%=inscritos.getNombre() %></td>
			<td><%=nombreCarrera%></td>
			<td align="center"><%=inscritos.getSexo().equals("M")?"Hombre":"Mujer"%></td>
			<td><%=tutor%></td>
			<td align="center"><%=inscritos.getResidenciaId().equals("E")?"Externo":"Interno"%></td>
			<td align="center"><%=estadoCivil%></td>
			<td><%=tipoAlumno%></td>
			<td><%=inscritos.getfInscripcion()%></td>
		</tr>
<%	} %>			
	</table>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>