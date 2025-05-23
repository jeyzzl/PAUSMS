<%@page import="aca.util.Fecha"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.Carga"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Mostrar(){
		document.forma.submit();
	}
</script>
<%
	String codigo			= (String) session.getAttribute("codigoPersonal");
	String sCarga			= request.getParameter("f_carga");
	String cargas			= (String) request.getAttribute("cargas");
	String modalidades		= (String) request.getAttribute("modalidades");
	
 	if(cargas.equals("")) cargas="''";
 	if(modalidades.equals("")) modalidades="''";
	
	String fechaIni			= (String) request.getAttribute("fechaIni");
	String fechaFin			= (String) request.getAttribute("fechaFin");
	String accion 			= (String) request.getAttribute("accion");
	String periodoId 		= (String) request.getAttribute("periodoId");
	
	String lisModo[] 		= modalidades.replace("'", "").split(",");
	
	HashMap<String,CatModalidad> mapaModalidades = (HashMap<String,CatModalidad>)request.getAttribute("mapaModalidades");
%>
<head>		
	<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
</head>	

<div class="container-fluid">
	<h2>Enrolled List</h2>
	<form id="forma" name="forma" action="inscritos?Accion=1" method="post">
		<div class="alert alert-info">
			<b>Loads:</b> <%= cargas.replace("'", "")%>&nbsp;<a href="cargas" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;
			<b>Modalities:</b>
<%
			for(String mod:lisModo){
				String modalidadNombre = "-";
				if (mapaModalidades.containsKey(mod)){
					modalidadNombre = mapaModalidades.get(mod).getNombreModalidad();
				}
				out.print("["+modalidadNombre+"] ");
			}		
%>
			<a href="modalidades" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;		
		</div>
		<div class="alert alert-info">		 
			Start date: <input id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
			End date: <input id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
			<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>		 
		</div>
<%	if(accion!=null){ %>

		<h2>Select a Listing</h2>
		<h3 class="btn btn-primary" onclick="reporte1();">Listing 1</h3>
		<table class="table table-sm table-bordered">
			<tr>
				<th colspan="14">Parameters</th>
			</tr>
			<tr class="table-info">
				<th><spring:message code="aca.Numero"/></th>
				<th><spring:message code="aca.Matricula"/></th>
				<th><spring:message code="aca.Nombre"/></th>
				<th>Surname</th>
				<th><spring:message code="aca.Edad"/></th>
				<th>Birth Date</th>
				<th><spring:message code="aca.Genero"/></th>
				<th><spring:message code="aca.Residencia"/></th>
				<th>Religion</th>
				<th><spring:message code="aca.Tipo"/></th>
				<th><spring:message code="aca.Carga"/></th>
				<th><spring:message code="aca.Modalidad"/></th>
				<th><spring:message code="aca.Plan"/></th>
				<th>Nationality</th>
			</tr>
		</table>		
		<br>		
		<h3 class="btn btn-primary" onclick="reporte2();">Listing 2</h3>
		<table class="table table-sm table-bordered">
			<tr>
				<th colspan="14">Parameters</th>
			</tr>
			<tr class="table-info">
				<th><spring:message code="aca.Numero"/></th>
				<th><spring:message code="aca.Facultad"/></th>
				<th><spring:message code="aca.Carrera"/></th>
				<th><spring:message code="aca.Carga"/></th>
				<th><spring:message code="aca.Matricula"/></th>
				<th><spring:message code="aca.Nombre"/></th>
				<th>Surname</th>
				<th><spring:message code="aca.Genero"/></th>
				<th><spring:message code="aca.Residencia"/></th>
				<th>Religion</th>
				<th><spring:message code="aca.Tipo"/></th>
				<th><spring:message code="aca.Modalidad"/></th>
				<th><spring:message code="aca.Pais"/></th>
				<th><spring:message code="aca.Estado"/></th>
			</tr>
		</table>
	<%} %>
	</form>
</div>
<script>
	function reporte1(){
		window.location="reporte1?cargas=<%=cargas%>&modalidades=<%=modalidades%>&FechaIni=<%=fechaIni%>&FechaFin=<%=fechaFin%>";
	}
	function reporte2(){
		window.location="reporte2?cargas=<%=cargas%>&modalidades=<%=modalidades%>&FechaIni=<%=fechaIni%>&FechaFin=<%=fechaFin%>";
	}
</script>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>