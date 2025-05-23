<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.conva.spring.ConvPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<script type="text/javascript">
	function GrabaCarreras(){		
		document.forma.submit();
	}
</script>
<style>	
	.alert-alto-m{
		height:35px;
	}
</style>
<%
	String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");

	List<CatFacultad> 	lisFacultades		= (List<CatFacultad>) request.getAttribute("lisFacultades");
	List<CatCarrera> 	lisCarreras			= (List<CatCarrera>) request.getAttribute("lisCarreras");
	List<MapaPlan> 	lisPlanes				= (List<MapaPlan>) request.getAttribute("lisPlanes");
	HashMap<String,String> mapaCarreras 	= (HashMap<String,String>) request.getAttribute("mapaCarreras");
%>

<body>
<div class="container-fluid">
	<h2>Carreras Permitidas</h2>
	<div class="alert alert-primary">
		<a class="btn btn-primary" href="listado"><i class="fas fa-arrow-left"></i></a>
	</div>	
	<form name="forma" action="grabarCarreras" method="POST">	
	<input type="hidden" name=PeriodoId value="<%=periodoId%>">
<%
	for(CatFacultad facultad : lisFacultades){
%>
	<div class="alert alert-success d-flex align-items-center alert-alto-m mt-2">
		<h3><%=facultad.getNombreFacultad()%>
			<a class="badge bg-dark" onclick="jQuery('.checkboxCarrera<%=facultad.getFacultadId()%>').prop('checked', true)" style="color:white; font-size:11px;"><spring:message code='aca.Todos'/></a> 
			<a class="badge bg-warning" onclick="jQuery('.checkboxCarrera<%=facultad.getFacultadId()%>').prop('checked', false)" style="color:white; font-size:11px;"><spring:message code='aca.Ninguno'/></a>
		</h3>
	</div>
<% 		
		for(CatCarrera carrera : lisCarreras){
			if (carrera.getFacultadId().equals(facultad.getFacultadId())){
%>	
	<div class="container-fluid">
	<div class="row border">
		<div class="col-5">
			&nbsp;&nbsp; <input class="checkboxCarrera<%=facultad.getFacultadId()%>" type="checkbox" name="<%=carrera.getCarreraId()%>" value="<%=carrera.getCarreraId()%>" <%=mapaCarreras.containsKey(carrera.getCarreraId())?"checked":""%> >	
			&nbsp;&nbsp; <%=carrera.getNombreCarrera()%>
		</div>
		<div class="col-4">
<%			for (MapaPlan plan : lisPlanes){
				if (plan.getCarreraId().equals(carrera.getCarreraId())){
%>				
				&nbsp;<span class="badge bg-dark" title="<%=plan.getCarreraSe()%>"><%=plan.getPlanId()%></span>
<% 				}
			}
%>			
		</div>
	</div>		
	</div>		
<% 
			}
		}
	}
%>
	<div class="row" style="margin: 20px 20px 0 20px;border:1px;">
		<div class="col-5">
			<input class="btn btn-primary" type="button" name="button" value="<spring:message code="aca.Guardar"/>" onclick='javascript:GrabaCarreras();'/>
		</div>		
	</div>	
	</form>
</div>
</body>