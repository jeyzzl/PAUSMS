<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	int accion = Integer.parseInt(request.getParameter("Accion"));
	String sColor 		= "bgcolor = '#dddddd'";

	switch(accion){
		case 1:{//Mostrar todas las carreras
			aca.catalogo.CarreraUtil catCarreraU = new aca.catalogo.CarreraUtil();
			ArrayList<aca.catalogo.CatCarrera> lisCarreras = catCarreraU.getListAll(conEnoc, "ORDER BY ENOC.FACULTAD_NOMBRE(FACULTAD_ID), NOMBRE_CARRERA");
			String facultadId = "";
%>
<table style="width:100%" class="tabla">
	<tr>
		<th><spring:message code="aca.Facultades"/>/<spring:message code="aca.Carreras"/> </th>
	</tr>
<%
			for(int i = 0; i < lisCarreras.size(); i++){
				aca.catalogo.CatCarrera catCarrera = (aca.catalogo.CatCarrera) lisCarreras.get(i);
				if(!facultadId.equals(catCarrera.getFacultadId())){
					facultadId = catCarrera.getFacultadId();
%>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr class="th2">
		<td><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, facultadId) %></td>
	</tr>
<%
				}
%>
	<tr <%=i%2!=0?sColor:"" %> class="button" onclick="muestraMaterias('<%=catCarrera.getCarreraId() %>');">
		<td><%=catCarrera.getNombreCarrera() %></td>
	</tr>
<%
			}
%>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td class="end"></td>
	</tr>
</table>
<%
		}break;
		case 2:{//Mostrar las materias de una carrera
			String carreraId = request.getParameter("carreraId");
			String planId = "";
			
			aca.plan.CursoUtil cursoU = new aca.plan.CursoUtil();
			ArrayList<aca.plan.MapaCurso> lisCursos = cursoU.getListCarrera(conEnoc, carreraId, "AND TIPOCURSO_ID!='9' ORDER BY PLAN_ID DESC, NOMBRE_CURSO");
%>
<table style="width:100%" class="tabla">
	<tr>
		<td colspan="2"><a class="button" onclick="muestraCarreras();">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
	</tr>
	<tr>
		<th colspan="2"><spring:message code="aca.Planes"/> <spring:message code="aca.de"/> <%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, carreraId) %></th>
	</tr>
<%
			for(int i = 0; i < lisCursos.size(); i++){
				aca.plan.MapaCurso mapaCurso = (aca.plan.MapaCurso) lisCursos.get(i);
				if(!planId.equals(mapaCurso.getPlanId())){
					planId = mapaCurso.getPlanId();
%>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr class="th2">
		<td colspan="2"><%=mapaCurso.getPlanId() %> - <%=aca.plan.PlanUtil.getNombrePlan(conEnoc, planId) %></td>
	</tr>
<%
				}
%>
	<tr <%=i%2!=0?sColor:"" %> class="button" onclick="cargaCurso('<%=mapaCurso.getCursoId() %>', '<%=mapaCurso.getNombreCurso() %>');">
		<td><%=mapaCurso.getCursoId() %></td>
		<td><%=mapaCurso.getNombreCurso() %></td>
	</tr>
<%
			}
%>
	<tr>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="2" class="end"></td>
	</tr>
</table>
<%
		}
	}
%>
<%@ include file= "../../cierra_enoc2.jsf" %>