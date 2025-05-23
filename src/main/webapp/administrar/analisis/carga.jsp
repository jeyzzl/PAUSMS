<%@page import="java.util.HashMap"%>
<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CatFacultadU" scope="page" class="aca.catalogo.CatFacultadUtil"/>
<jsp:useBean id="CargaGrupoActividadU" scope="page" class="aca.carga.CargaGrupoActividadUtil"/>
<jsp:useBean id="CargaGrupoU" scope="page" class="aca.carga.CargaGrupoUtil"/>

<%
	String cargaId = request.getParameter("CargaId")==null?session.getAttribute("cargaId").toString():request.getParameter("CargaId");
	
	// Lista de facultades con materias en la carga 
	ArrayList <aca.catalogo.CatFacultad> lisFacultades	= CatFacultadU.listFacultadesConCarga(conEnoc, cargaId, "ORDER BY NOMBRE_FACULTAD");
	
	// Mapa para las materias por carga
	HashMap<String, String> mapMaterias					= CargaGrupoU.mapMateriasFacultad(conEnoc, cargaId);
	
	// Mapa para las materias con evaluaciones
	HashMap<String, String> mapMatEval					= CargaGrupoU.mapMateriasConEval(conEnoc, cargaId);
	
	// Mapa materias con esquema completo
	HashMap<String, String> mapMateriasEsqueCompleto	= CargaGrupoU.mapMateriasConEsquemaCompleto(conEnoc, cargaId);
	
	// Mapa para las materias con evaluaciones
	HashMap<String, String> mapMatActi					= CargaGrupoU.mapMateriasConActi(conEnoc, cargaId);
	
	// Mapa para los maestros por carga
	HashMap<String, String> mapMaestros					= CargaGrupoU.mapMaestrosFacultad(conEnoc, cargaId);
	
	// Mapa materias agendadas por facultad
	HashMap<String, String> mapAgendadas				= CargaGrupoU.mapAgendadasFacultad(conEnoc, cargaId);
	
	// Mapa meses agendadas por facultad
	HashMap<String, String> mapaMesesPorCarga			= CargaGrupoActividadU.mapaMesesPorCarga(conEnoc, cargaId);
	
		
%>
<div class="container-fluid">
	<h2><spring:message code="aca.EstadisticaDeEvaluaciones"/><small class="text-muted fs-4">(<%=cargaId%>)</small></h2>
	<div class="alert alert-info">
		<a href="cargas" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<br>
	<table class="table table-sm table-bordered">
	<thead class="table-info">
		<tr>
			<th>#</th>
			<th><spring:message code="aca.Clave"/></th>
			<th><spring:message code="aca.Facultad"/></th>
			<th class="text-right"><spring:message code="aca.Maestros"/></th>
			<th class="text-right"><spring:message code="aca.Materias"/></th>
			<th class="text-right">Con Evaluation.</th>
			<th class="text-right">%</th>
			<th class="text-right">Schema 100%</th>
			<th class="text-right">%</th>
			<th class="text-right">Con Active</th>
			<th class="text-right">Scheduled</th>
			<th class="text-right">%</th>
			<th class="text-right">M01</th>
			<th class="text-right">M02</th>
			<th class="text-right">M03</th>
			<th class="text-right">M04</th>
			<th class="text-right">M05</th>
			<th class="text-right">M06</th>
			<th class="text-right">M07</th>
			<th class="text-right">M08</th>
			<th class="text-right">M09</th>
			<th class="text-right">M10</th>
			<th class="text-right">M11</th>
			<th class="text-right">M12</th>		
		</tr>
	</thead>	
<%
	int cont = 0;
	for(aca.catalogo.CatFacultad facultad : lisFacultades){
		cont++;
		
		String maestros = "0";
		if(mapMaestros.containsKey( facultad.getFacultadId() )){
			maestros = mapMaestros.get( facultad.getFacultadId() );
		}
		
		String materias ="0";
		if(mapMaterias.containsKey( facultad.getFacultadId() )){
			materias = mapMaterias.get( facultad.getFacultadId() );
		}
		
		String matEval ="0";
		if(mapMatEval.containsKey( facultad.getFacultadId() )){
			matEval = mapMatEval.get( facultad.getFacultadId() );
		}	
		
		String agendadas = "0"; 
		if(mapAgendadas.containsKey( facultad.getFacultadId() )){
			agendadas = mapAgendadas.get( facultad.getFacultadId() );
		}
		
		String esquemaCompleto = "0"; 
		if(mapMateriasEsqueCompleto.containsKey( facultad.getFacultadId() )){
			esquemaCompleto = mapMateriasEsqueCompleto.get( facultad.getFacultadId() );
		}
		
		String matActi ="0";
		if(mapMatActi.containsKey( facultad.getFacultadId() )){
			matActi = mapMatActi.get( facultad.getFacultadId() );
		}
		
		int porcentajeConEval = (Integer.parseInt(matEval)*100) / Integer.parseInt(materias);
		

		String colorEval 	= "";
		String textEval 	= "#000000";
		if(porcentajeConEval >= 80) {
			colorEval = "style='background-color:#9FF781';";
		}else if(porcentajeConEval <= 50) {
			colorEval = "style='background-color:#F78181';";
		}else {
			colorEval = "style='background-color:#F4FA58';";			
		}
		
		int porcentajeEsquema	= (Integer.parseInt(esquemaCompleto)*100) / Integer.parseInt(materias);
		
		String colorEsquema	= "";
		String textEsquema	= "#000000";
		if(porcentajeEsquema >= 80) { 
			colorEsquema = "style='background-color:#9FF781';";
		}else if(porcentajeEsquema <= 50) {
			colorEsquema = "style='background-color:#F78181';";
		}else {
			colorEsquema = "style='background-color:#F4FA58';";
		}
		
		int porcentajeAgendada	= (Integer.parseInt(agendadas)*100) / Integer.parseInt(materias);
		
		String colorAgendada	= "";
		String textAgendada	= "#000000";
		if(porcentajeAgendada >= 80) { 
			colorAgendada = "style='background-color:#9FF781';";
		}else if(porcentajeAgendada <= 50) {
			colorAgendada = "style='background-color:#F78181';";
		}else {
			colorAgendada = "style='background-color:#F4FA58';";
		}
		
		String m01="0",m02="0",m03="0",m04="0",m05="0",m06="0",m07="0",m08="0",m09="0",m10="0",m11="0",m12="0";
		if (mapaMesesPorCarga.containsKey(facultad.getFacultadId()+"01")) m01 = mapaMesesPorCarga.get(facultad.getFacultadId()+"01");
		if (mapaMesesPorCarga.containsKey(facultad.getFacultadId()+"02")) m02 = mapaMesesPorCarga.get(facultad.getFacultadId()+"02");
		if (mapaMesesPorCarga.containsKey(facultad.getFacultadId()+"03")) m03 = mapaMesesPorCarga.get(facultad.getFacultadId()+"03");
		if (mapaMesesPorCarga.containsKey(facultad.getFacultadId()+"04")) m04 = mapaMesesPorCarga.get(facultad.getFacultadId()+"04");
		if (mapaMesesPorCarga.containsKey(facultad.getFacultadId()+"05")) m05 = mapaMesesPorCarga.get(facultad.getFacultadId()+"05");
		if (mapaMesesPorCarga.containsKey(facultad.getFacultadId()+"06")) m06 = mapaMesesPorCarga.get(facultad.getFacultadId()+"06");
		if (mapaMesesPorCarga.containsKey(facultad.getFacultadId()+"07")) m07 = mapaMesesPorCarga.get(facultad.getFacultadId()+"07");
		if (mapaMesesPorCarga.containsKey(facultad.getFacultadId()+"08")) m08 = mapaMesesPorCarga.get(facultad.getFacultadId()+"08");
		if (mapaMesesPorCarga.containsKey(facultad.getFacultadId()+"09")) m09 = mapaMesesPorCarga.get(facultad.getFacultadId()+"09");
		if (mapaMesesPorCarga.containsKey(facultad.getFacultadId()+"10")) m10 = mapaMesesPorCarga.get(facultad.getFacultadId()+"10");
		if (mapaMesesPorCarga.containsKey(facultad.getFacultadId()+"11")) m11 = mapaMesesPorCarga.get(facultad.getFacultadId()+"11");
		if (mapaMesesPorCarga.containsKey(facultad.getFacultadId()+"12")) m12 = mapaMesesPorCarga.get(facultad.getFacultadId()+"12");	
%>
		<tr>
			<td><%= cont %></td>
			<td><a href="carga_maestros?CargaId=<%=cargaId%>&FacultadId=<%=facultad.getFacultadId()%>"><%= facultad.getFacultadId() %></a></td>
			<td><a href="carga_maestros?CargaId=<%=cargaId%>&FacultadId=<%=facultad.getFacultadId()%>"><%= facultad.getNombreFacultad() %></a></td>			
			<td class="text-right"><%= maestros %></td>
			<td class="text-right"><%= materias %></td>
			<td class="text-right"><%= matEval %></td>
			<td class="text-right"><span class="label" <%=colorEval%>><font color="<%= textEval %>"><%= porcentajeConEval %> %</font></span></td>
			<td class="text-right"><%= esquemaCompleto %></td>
			<td class="text-right"><span class="label" <%=colorEsquema%>><font color="<%= textEsquema %>"><%= porcentajeEsquema %> %</font></span></td>
			<td class="text-right"><%= matActi %></td>
			<td class="text-right"><%= agendadas %></td>
			<td class="text-right"><span class="label" <%=colorAgendada%>><font color="<%= textAgendada %>"><%= porcentajeAgendada %> %</font></span></td>
			<td class="text-right"><%=m01%></td>
			<td class="text-right"><%=m02%></td>
			<td class="text-right"><%=m03%></td>
			<td class="text-right"><%=m04%></td>
			<td class="text-right"><%=m05%></td>
			<td class="text-right"><%=m06%></td>
			<td class="text-right"><%=m07%></td>
			<td class="text-right"><%=m08%></td>
			<td class="text-right"><%=m09%></td>
			<td class="text-right"><%=m10%></td>
			<td class="text-right"><%=m11%></td>
			<td class="text-right"><%=m12%></td>		
		</tr>
<%	} %>		
	</table>
</div>
<%@ include file="../../cierra_enoc.jsp"%>