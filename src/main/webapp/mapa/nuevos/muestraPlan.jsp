<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.plan.MapaNuevoCurso"%>
<%@page import="aca.plan.MapaNuevoUnidad"%>
<%@page import="aca.plan.MapaNuevoActividad"%>
<%@page import="aca.plan.MapaNuevoBibliografia"%>
<%@page import="aca.plan.MapaNuevoBiblioUnidad"%>
<%@page import="aca.catalogo.CatCarrera"%>

<jsp:useBean id="mapaNuevoPlan" class="aca.plan.MapaNuevoPlan" scope="page"/>
<jsp:useBean id="mapaNuevoCurso" class="aca.plan.MapaNuevoCurso" scope="page"/>
<jsp:useBean id="mapaNuevoCursoU" class="aca.plan.MapaNuevoCursoUtil" scope="page"/>
<jsp:useBean id="mapaNuevoUnidad" class="aca.plan.MapaNuevoUnidad" scope="page"/>
<jsp:useBean id="mapaNuevoUnidadU" class="aca.plan.MapaNuevoUnidadUtil" scope="page"/>
<jsp:useBean id="mapaNuevoActividad" class="aca.plan.MapaNuevoActividad" scope="page"/>
<jsp:useBean id="mapaNuevoActividadU" class="aca.plan.MapaNuevoActividadUtil" scope="page"/>
<jsp:useBean id="mapaNuevoBibliografia" class="aca.plan.MapaNuevoBibliografia" scope="page"/>
<jsp:useBean id="mapaNuevoBibliografiaU" class="aca.plan.MapaNuevoBibliografiaUtil" scope="page"/>
<jsp:useBean id="mapaNuevoBiblioUnidad" class="aca.plan.MapaNuevoBiblioUnidad" scope="page"/>
<jsp:useBean id="mapaNuevoBiblioUnidadU" class="aca.plan.MapaNuevoBiblioUnidadUtil" scope="page"/>
<%
	String planId			= request.getParameter("planId");
	String versionId		= request.getParameter("versionId");
	String ciclo			= "";
	String institucion 		= (String)session.getAttribute("institucion");
	
	ArrayList<MapaNuevoCurso> listCursos = null;
	ArrayList<MapaNuevoUnidad> listUnidades = null;
	ArrayList<MapaNuevoActividad> listActividades = null;
	ArrayList<MapaNuevoBibliografia> listBibliografias = null;
	ArrayList<MapaNuevoBiblioUnidad> listBiblioUnidad = null;
	
	mapaNuevoPlan.mapeaRegId(conEnoc, planId, versionId);
	
	listCursos = mapaNuevoCursoU.getListPlan(conEnoc, planId, versionId, "ORDER BY CICLO, NOMBRE");
%>
<body>
	<input type="button" class="btn btn-primary" onclick="location.href='muestraPlanPdf?planId=<%=planId %>&versionId=<%=versionId %>';" value="Ver PDF" style="position: relative; left: 20px; top: 60px;" />
	<table class="goback">
		<tr>
			<td><a href="planes">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
		</tr>
	</table>
	<table style="margin: 0 auto;">
		<tr>
			<td class="titulo">Materias de <%=planId %></td>
		</tr>
		<tr>
			<td class="titulo2"><%=mapaNuevoPlan.getNombre() %> - Version <%=mapaNuevoPlan.getVersionNombre() %></td>
		</tr>
	</table>
	<table style="width:90%" align="center" >
<%
	for(int i = 0; i < listCursos.size(); i++){
		mapaNuevoCurso = (MapaNuevoCurso) listCursos.get(i);
		String cursoId = mapaNuevoCurso.getCursoId();
		listUnidades = mapaNuevoUnidadU.getListCurso(conEnoc, cursoId, "ORDER BY UNIDAD_ID");
		if(!ciclo.equals(mapaNuevoCurso.getCiclo())){
			ciclo = mapaNuevoCurso.getCiclo();
%>
		<tr>
			<td colspan="11">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="11" class="titulo2">Semestre <%=ciclo %></td>
		</tr>
<%
		}
%>
		<tr>
			<td colspan="11">
				<table style="width:90%" align="center">
					<tr>
						<td class="titulo"><%=institucion.toUpperCase()%></td>
					</tr>
					<tr>
						<td class="titulo2"><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, mapaNuevoPlan.getCarreraId())) %></td>
					</tr>
					<tr>
						<td class="titulo2"><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, mapaNuevoPlan.getCarreraId()) %></td>
					</tr>
				</table>
				<br>
				<table style="width:850px" align="center" style="border: solid 1px black;" border=1>
					<tr>
						<td width="180px"><b>ASIGNATURA:</b></td>
						<td><%=mapaNuevoCurso.getNombre() %></td>
						<td colspan="2"><b>SEMESTRE:</b></td>
						<td colspan="2"><%=mapaNuevoCurso.getCiclo() %></td>
						<td colspan="2"><b>CLAVE:</b></td>
						<td colspan="2"><%=mapaNuevoCurso.getClave() %></td>
						<td><b>SERIACI&Oacute;N</b></td>
						<td><%=mapaNuevoCurso.getSeriacion() %></td>
					</tr>
					<tr>
						<td width="180px"><b>OBJETIVO DE LA ASIGNATURA:</b></td>
						<td width="670px" colspan="11" id="objetivoAsignaturaTD"><%=mapaNuevoCurso.getDescripcion().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
					</tr>
					<tr>
						<td width="180px"><b>L&Iacute;NEA CURRICULAR:</b></td>
						<td><%=mapaNuevoCurso.getUbicacion() %></td>
						<td><b>HTS:</b></td>
						<td><%=mapaNuevoCurso.getHst() %></td>
						<td><b>HPS:</b></td>
						<td><%=mapaNuevoCurso.getHsp() %></td>
						<td><b>THS:</b></td>
						<td><%=mapaNuevoCurso.getThs() %></td>
						<td><b>CRS:</b></td>
						<td><%=mapaNuevoCurso.getCreditos() %></td>
						<td><b>HORAS TOTALES:</b></td>
						<td><%=mapaNuevoCurso.getHt() %></td>
					</tr>
					<tr>
						<td colspan="6"><b>COMPETENCIA DEL PERFIL QUE ATIENDE LA ASIGNATURA:</b></td>
						<td colspan="6"><%=mapaNuevoCurso.getCompetencia() %></td>
					</tr>
				</table>
				<br>
				<!-- Esta tabla es para la unidad -->
<%
	for(int j = 0; j < listUnidades.size(); j++){
		mapaNuevoUnidad = (MapaNuevoUnidad) listUnidades.get(j);
		String uid = mapaNuevoUnidad.getUnidadId();
		String tipo = "";
		listActividades = mapaNuevoActividadU.getListUnidad(conEnoc, cursoId, uid, "ORDER BY TIPO, ACTIVIDAD_ID");
		listBiblioUnidad = mapaNuevoBiblioUnidadU.getListCursoUnidad(conEnoc, cursoId, uid, "ORDER BY (SELECT TIPO FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA"+ 
				" WHERE CURSO_ID = ENOC.MAPA_NUEVO_BIBLIO_UNIDAD.CURSO_ID AND BIBLIOGRAFIA_ID = ENOC.MAPA_NUEVO_BIBLIO_UNIDAD.BIBLIOGRAFIA_ID), ID");
%>
				<div id="unidad<%=mapaNuevoUnidad.getUnidadId() %>" align="center">
					<br>
					<table style="width:850px" align="center" style="border: solid 1px black;">
						<tr>
							<td align="center" width="100px" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>TIEMPO ESTIMADO</b></td>
							<td align="center" width="120px" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>NOMBRE Y OBJETIVO DE LA UNIDAD</b></td>
							<td align="center" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>TEMAS Y SUBTEMAS</b></td>
							<td align="center" width="120px" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>ACTIVIDADES DE APRENDIZAJE</b></td>
							<td align="center" width="120px" style="border-bottom: solid 1px black;"><b>BIBLIOGRAF&Iacute;A</b></td>
						</tr>
						<tr>
							<td valign="top" width="100px" id="tiempo<%=uid %>" style="border-right: solid 1px black;"><%=mapaNuevoUnidad.getTiempo().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
							<td valign="top" width="120px" style="border-right: solid 1px black;">
								<table style="width:100%">
									<tr>
										<td colspan="2" id="nombre<%=uid %>"><%=mapaNuevoUnidad.getNombre().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
									</tr>
									<tr>
										<td colspan="2">&nbsp;</td>
									</tr>
									<tr>
										<td colspan="2"><b>OBJETIVO:</b></td>
									</tr>
<%
		for(int k = 0; k < listActividades.size(); k++){
			mapaNuevoActividad = (MapaNuevoActividad) listActividades.get(k);
			if(!tipo.equals(mapaNuevoActividad.getTipo())){
				tipo = mapaNuevoActividad.getTipo();
%>
									<tr>
										<td colspan="2"><b><%=tipo.equals("1")?"Conocimientos:":tipo.equals("2")?"Habilidades:":"Actitudes:" %></b></td>
									</tr>
<%
			}
%>
									<tr>
										<td width="6px" valign="top">-</td>
										<td id="objetivo<%=uid %>-<%=mapaNuevoActividad.getActividadId() %>"><%=mapaNuevoActividad.getObjetivo().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
									</tr>
<%
		}
System.out.println(mapaNuevoUnidad.getTemas());
%>
								</table>
							</td>
							<td valign="top" id="temas<%=uid %>" style="border-right: solid 1px black;"><%=mapaNuevoUnidad.getTemas().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
							<td valign="top" width="120px" style="border-right: solid 1px black;">
								<table style="width:100%">
<%
		tipo = "";
		for(int k = 0; k < listActividades.size(); k++){
			mapaNuevoActividad = (MapaNuevoActividad) listActividades.get(k);
			if(!tipo.equals(mapaNuevoActividad.getTipo())){
				tipo = mapaNuevoActividad.getTipo();
%>
									<tr>
										<td colspan="2"><b><%=tipo.equals("1")?"CONOCIMIENTO (Saber)":tipo.equals("2")?"HABILIDAD (Saber hacer)":"ACTITUD (Saber ser)" %></b></td>
									</tr>
<%
			}
%>
									<tr>
										<td colspan="2">Actividad:</td>
									</tr>
									<tr>
										<td width="6px" valign="top">-</td>
										<td id="actividad<%=uid %>-<%=mapaNuevoActividad.getActividadId() %>"><%=mapaNuevoActividad.getDescripcion().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
									</tr>
									<tr>
										<td colspan="2">Criterio de desempe&ntilde;o</td>
									</tr>
									<tr>
										<td width="6px" valign="top">-</td>
										<td id="criterio<%=uid %>-<%=mapaNuevoActividad.getActividadId() %>"><%=mapaNuevoActividad.getCriterio().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
									</tr>
<%
		}
%>
								</table>&nbsp;
							</td>
							<td valign="top" width="120px">
								<table style="width:100%">
<%
		tipo = "";
		for(int k = 0; k < listBiblioUnidad.size(); k++){
			mapaNuevoBiblioUnidad = (MapaNuevoBiblioUnidad) listBiblioUnidad.get(k);
			mapaNuevoBibliografia.mapeaRegId(conEnoc, cursoId, mapaNuevoBiblioUnidad.getBibliografiaId());
			if(!tipo.equals(mapaNuevoBibliografia.getTipo())){
				tipo = mapaNuevoBibliografia.getTipo();
%>
									<tr>
										<td colspan="2"><b><%=tipo.equals("1")?"Libros y revistas:":"Enlaces electr&oacute;nicos:" %></b></td>
									</tr>
<%
			}
%>
									<tr>
										<td width="6px" valign="top">-</td>
										<td><%=mapaNuevoBibliografia.getReferencia().replaceAll(" ","&nbsp;").replaceAll("\n","<br>").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;") %></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td id="bibliografia<%=uid %>-<%=mapaNuevoBiblioUnidad.getBibliografiaId() %>-<%=mapaNuevoBiblioUnidad.getId() %>"><%=mapaNuevoBiblioUnidad.getEspecificacion().replaceAll(" ","&nbsp;").replaceAll("\n","<br>").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;") %></td>
									</tr>
<%
		}
%>
									<tr>
										<td colspan="2">&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<br>
				</div>
<%
	}
%>
				<br>
				<table style="width:850px" align="center" style="border: solid 1px black;">
					<tr>
						<td><b>ACCI&Oacute;N DOCENTE (Metodolog&iacute;a):</b></td>
					</tr>
					<tr>
						<td>
							<table style="width:100%" id="tablaAccionDocente">
<%
	for(int j = 0; j < listUnidades.size(); j++){
		mapaNuevoUnidad = (MapaNuevoUnidad) listUnidades.get(j);
		String uid = mapaNuevoUnidad.getUnidadId();
%>
								<tr id="accionDocente<%=uid %>">
									<td id="accionDocenteNombre<%=uid %>" width="100px" style="border-bottom: solid 1px gray;"><%=mapaNuevoUnidad.getNombre().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
									<td id="accionDocenteDatos<%=uid %>" style="border-bottom: solid 1px gray;"><%=mapaNuevoUnidad.getAccionDocente().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
								</tr>
<%
	}
%>
							</table>
						</td>
					</tr>
					<tr>
						<td><b>MEDIOS Y RECURSOS DID&Aacute;CTICOS:</b></td>
					</tr>
					<tr>
						<td><%=mapaNuevoCurso.getMediosRecursos() %></td>
					</tr>
					<tr>
						<td><b>ELEMENTOS DE EVALUACI&Oacute;N</b></td>
					</tr>
					<tr>
						<td><b>Diagn&oacute;stica:</b></td>
					</tr>
					<tr>
						<td><%=mapaNuevoCurso.getEeDiagnostica() %></td>
					</tr>
					<tr>
						<td><b>Formativa:</b></td>
					</tr>
					<tr>
						<td><%=mapaNuevoCurso.getEeFormativa() %></td>
					</tr>
					<tr>
						<td><b>Sumativa:</b></td>
					</tr>
					<tr>
						<td><%=mapaNuevoCurso.getEeSumativa() %></td>
					</tr>
					<tr>
						<td><b>ESCALA DE CALIFICACIONES (Ponderaci&oacute;n de las calificaciones):</b></td>
					</tr>
					<tr>
						<td><%=mapaNuevoCurso.getEscala() %></td>
					</tr>
				</table>
				<br>
				<table style="width:850px" align="center" style="border: solid 1px black;" id="bibliografiaTotal">
					<tr>
						<td align="center" colspan="3"><b>BIBLIOGRAFIA TOTAL</b></td>
					</tr>
<%
	String tipo = "";
	listBibliografias = mapaNuevoBibliografiaU.getListCurso(conEnoc, cursoId, "ORDER BY TIPO, BIBLIOGRAFIA");
	for(int j = 0; j < listBibliografias.size(); j++){
		mapaNuevoBibliografia = (MapaNuevoBibliografia) listBibliografias.get(j);
		if(!tipo.equals(mapaNuevoBibliografia.getTipo())){
			tipo = mapaNuevoBibliografia.getTipo();
%>
					<tr>
						<td colspan="2"><b><%=tipo.equals("1")?"Libros y revistas:":"Enlaces electr&oacute;nicos:" %></b></td>
					</tr>
<%
		}
%>
					<tr>
						<td style="border-right: solid 1px gray;" width="100px"><%=mapaNuevoBibliografia.getReferencia().replaceAll("\n","<br>").replaceAll(" ","&nbsp;").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;") %></td>
						<td id="bibliografia<%=mapaNuevoBibliografia.getBibliografiaId() %>"><%=mapaNuevoBibliografia.getBibliografia().replaceAll("\n","<br>").replaceAll(" ","&nbsp;").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;") %></td>
					</tr>
<%
	}
%>
				</table>
				<br>
				<br>
				<br>
				<br>
			</td>
		</tr>
<%
	}
%>
	</table>
	<br>
	<table style="width:100%">
		<tr><td class="end"><spring:message code="aca.FinDelListado"/></td></tr>
	</table>
</body>

<%@ include file= "../../cierra_enoc.jsp" %>