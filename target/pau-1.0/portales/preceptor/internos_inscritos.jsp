<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.vista.spring.Inscritos"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.vista.spring.Maestros"%>

<script type="text/javascript">
	function Mostrar(){		
		document.forma.submit();
	}
</script>

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />

<%	
	String fechaIni			= (String)request.getAttribute("fechaIni");
	String fechaFin			= (String)request.getAttribute("fechaFin");
	
	boolean esAdmin			= (boolean)request.getAttribute("esAdmin");
	boolean esPreceptor		= (boolean)request.getAttribute("esPreceptor");
	String dormitorioId		= (String)request.getAttribute("dormitorioId");
	String nombreDormitorio	= (String)request.getAttribute("nombreDormitorio");

	HashMap<String,String> mapMentorAlumno	= (HashMap<String,String>)request.getAttribute("mapMentorAlumno");
	HashMap<String,String> mapExiste		= (HashMap<String,String>)request.getAttribute("mapExiste");
	HashMap<String,CatCarrera> mapaCarrera	= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarrera");
	HashMap<String,Maestros> mapaMaestros	= (HashMap<String,Maestros>)request.getAttribute("mapaMaestros");

%>
<%@ include file="portal.jsp" %>
<head>	
	<script type="text/javascript">
		function orden(order){
			document.location.href="internos_inscritos?orden="+order;
		}
	</script>
</head>
<div class="container-fluid">
	<h3>
		Enrolled Dormitory Students <small> (<%=nombreDormitorio%>)</small>
	</h3>
	<form id="forma" name="forma">	
	<div class="alert alert-info d-flex align-items-center ">
<%
		if(esAdmin || esPreceptor){ out.print("&nbsp;<a href='../../internado/dormitorios/dormitorios' class='btn btn-info'><i class='icon-white icon-arrow-left'></i>Menu</a>&nbsp; &nbsp;"); }
%>	
		Start Date: <input data-format="hh:mm:ss" id="FechaIni"  name="FechaIni" type="text" class="form-control" style="width:120px" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10"/>&nbsp;&nbsp;
		<span class="add-on">
    	 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
   		 </span>
		End Date: <input data-format="hh:mm:ss" id="FechaFin" name="FechaFin" type="text" class="form-control" style="width:120px" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10"/>&nbsp;&nbsp;
		<span class="add-on">
    	 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
   		 </span>
		<a href="javascript:Mostrar();" class="btn btn-info btn-sm"><i class="fas fa-sync-alt"></i></a>	
	</div>					
	<table class="table table-striped table-bordered">
	<thead>
	<tr class="table-info">
		<th><spring:message code="aca.Numero"/></th>
		<th onclick="orden('ORDER BY CODIGO_PERSONAL');"><spring:message code="aca.Matricula"/></th>
		<th onclick="orden('ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE');"><spring:message code="aca.Nombre"/></th>
		<th onclick="orden('ORDER BY ENOC.NOMBRE_CARRERA(CARRERA_ID), APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE');"><spring:message code="aca.Carrera"/></th>
		<th>Assigned</th>
		<th>Mentor</th>		
	</tr>
	</thead>
	<tbody>
<%
	List<Inscritos> lisInscritos = (List<Inscritos>)request.getAttribute("lisInscritos");

	String bgColor = "";
	int i = 0;
	for(Inscritos inscrito : lisInscritos){
		String mentorId			= "";
		if(mapMentorAlumno.containsKey(inscrito.getCodigoPersonal())){
			mentorId = mapMentorAlumno.get(inscrito.getCodigoPersonal());
		}
		
		String mentorNombre		= "";
		if(mapaMaestros.containsKey(mentorId)){
			mentorNombre = mapaMaestros.get(mentorId).getNombre()+" "+mapaMaestros.get(mentorId).getApellidoPaterno()+" "+mapaMaestros.get(mentorId).getApellidoMaterno();
		}
		
		if ((i+1) % 2==0) bgColor="bgcolor='#ffffff'"; else bgColor="";
		
		String nombreCarrera = "";
		if(mapaCarrera.containsKey(inscrito.getCarreraId())){
			nombreCarrera = mapaCarrera.get(inscrito.getCarreraId()).getNombreCarrera();
		}		
%>
			<tr <%=bgColor %>>
				<td><%=i+1 %></td>
				<td><%=inscrito.getCodigoPersonal() %></td>
				<td><%=inscrito.getApellidoMaterno().equals("-")?"":inscrito.getApellidoMaterno()%> <%=inscrito.getApellidoPaterno() %> <%=inscrito.getNombre() %></td>
				<td><%=nombreCarrera%></td>
<%				if(mapExiste.containsKey(inscrito.getCodigoPersonal())){%>
				<td class="text-center"><span class='badge bg-dark rounded-pill'>S</span></td>
<%
				}else {
%>				
				<td class="text-center"><span class='badge bg-warning rounded-pill'>N</span></td>
<%
				}
%>	
				<td align="left"><%=mentorNombre%></td>				
			</tr>
<%
		i++;
	}
%>
	<tbody>
	</table>			
	</form>
</div>
<script>
	jQuery('.inscritos').addClass('active');
</script>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>