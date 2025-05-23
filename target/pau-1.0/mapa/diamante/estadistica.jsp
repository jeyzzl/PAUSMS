<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.plan.MapaNuevoPlan"%>
<%@page import="aca.catalogo.CatCarrera"%>

<jsp:useBean id="mapaNuevoPlan" class="aca.plan.MapaNuevoPlan" scope="page"/>
<jsp:useBean id="mapaNuevoPlanU" class="aca.plan.MapaNuevoPlanUtil" scope="page"/>

<script type="text/javascript">
	function yearPlanes(){
		var year = document.getElementById("yearPlan").value;
		document.location.href = "estadistica?yearPlan="+year;
	}
</script>

<%
	String facultadId = "";
	
	String yearPlan		= request.getParameter("yearPlan")==null?"0":request.getParameter("yearPlan");
	
	ArrayList<MapaNuevoPlan> listPlanes	= new ArrayList<MapaNuevoPlan>();
	
	if(yearPlan.equals("0")){
		listPlanes	= mapaNuevoPlanU.getListAll(conEnoc, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), NOMBRE");
	}else{
		listPlanes	= mapaNuevoPlanU.getListPorYear(conEnoc, yearPlan, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), NOMBRE");
	}
	
%>
<head>
	<script type="text/javascript">
		function muestraPorcentaje(objeto, porcentaje, textoADesplegar){
			var obj = $(objeto);
			var texto = porcentaje+'%';
			if(textoADesplegar != '')
				texto += ' - '+textoADesplegar;
			obj.innerHTML = '<div id="graf-'+objeto+'" style="background-color: yellow; overflow: visible;"><font color="black" style="position: relative;"><b>'+texto+'</b></font></div>';

		}
	</script>
</head>
<body>
<div class="container-fluid">
	<h1>Estad&iacute;stica de planes</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="planes"><spring:message code="aca.Regresar"/></a>
		<select id="yearPlan" onchange="yearPlanes()">
			<option value="0" <%=yearPlan.equals("0")?"selected":""%>>Todos</option>
			<option value="2017" <%=yearPlan.equals("2017")?"selected":""%>>2017</option>
		</select>
	</div>
	<table class="table table-condensed" width="70%">
<%
	for(int i = 0; i < listPlanes.size(); i++){
		mapaNuevoPlan = (MapaNuevoPlan) listPlanes.get(i);
		String facultadTmp = aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, mapaNuevoPlan.getCarreraId());
		if(!facultadId.equals(facultadTmp)){
			facultadId = facultadTmp;
%>
			<tr>
				<td colspan="2" class="titulo2">
					<b><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, facultadId) %></b>
				</td>
			</tr>
			<tr>
				<th><spring:message code="aca.Nombre"/></th>
				<th>Porcentaje</th>
			</tr>
	<%	} %>
		<tr>
			<td colspan="2"><b><%=mapaNuevoPlan.getNombre() %></b></td>
		</tr>
		<tr>
			<td>Materias en terminado</td>
			<td id="terminado-<%=mapaNuevoPlan.getPlanId() %>"></td>
		</tr>
		<tr>
			<td>Materias en administrador</td>
			<td id="administrador-<%=mapaNuevoPlan.getPlanId() %>"></td>
		</tr>
		<tr>
			<td>Materias en coordinador</td>
			<td id="coordinador-<%=mapaNuevoPlan.getPlanId() %>"></td>
		</tr>
		<tr>
			<td>Materias en maestro</td>
			<td id="maestro-<%=mapaNuevoPlan.getPlanId() %>"></td>
		</tr>
		<tr>
			<td>Materias con alguna unidad</td>
			<td id="unidad-<%=mapaNuevoPlan.getPlanId() %>"></td>
		</tr>
		<tr>
			<td colspan="2">
				<script type="text/javascript">
					muestraPorcentaje('terminado-<%=mapaNuevoPlan.getPlanId() %>', <%=aca.plan.MapaNuevoCursoUtil.porcentajeEnEstado(conEnoc, mapaNuevoPlan.getPlanId(), mapaNuevoPlan.getVersionId(), "4") %>, ''); // Materias en administrador
					muestraPorcentaje('administrador-<%=mapaNuevoPlan.getPlanId() %>', <%=aca.plan.MapaNuevoCursoUtil.porcentajeEnEstado(conEnoc, mapaNuevoPlan.getPlanId(), mapaNuevoPlan.getVersionId(), "3") %>, ''); // Materias en administrador
					muestraPorcentaje('coordinador-<%=mapaNuevoPlan.getPlanId() %>', <%=aca.plan.MapaNuevoCursoUtil.porcentajeEnEstado(conEnoc, mapaNuevoPlan.getPlanId(), mapaNuevoPlan.getVersionId(), "2") %>, ''); // Materias en coordinador
					muestraPorcentaje('maestro-<%=mapaNuevoPlan.getPlanId() %>', <%=aca.plan.MapaNuevoCursoUtil.porcentajeEnEstado(conEnoc, mapaNuevoPlan.getPlanId(), mapaNuevoPlan.getVersionId(), "1") %>, ''); // Materias en maestro
					muestraPorcentaje('unidad-<%=mapaNuevoPlan.getPlanId() %>', <%=(int)((Float.parseFloat(aca.plan.MapaNuevoCursoUtil.getMateriasConUnidades(conEnoc, mapaNuevoPlan.getPlanId(), mapaNuevoPlan.getVersionId())) / Float.parseFloat(aca.plan.MapaNuevoCursoUtil.getMateriasTotales(conEnoc, mapaNuevoPlan.getPlanId(), mapaNuevoPlan.getVersionId())))*100f) %>, '<%=aca.plan.MapaNuevoCursoUtil.getMateriasConUnidades(conEnoc, mapaNuevoPlan.getPlanId(), mapaNuevoPlan.getVersionId()) %> de <%=aca.plan.MapaNuevoCursoUtil.getMateriasTotales(conEnoc, mapaNuevoPlan.getPlanId(), mapaNuevoPlan.getVersionId()) %>'); // Materias con alguna unidad
				</script>
			</td>
		</tr>
<%
	}
%>
	</table>
	<br>
	</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>