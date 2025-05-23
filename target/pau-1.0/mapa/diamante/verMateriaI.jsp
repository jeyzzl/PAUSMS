<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>


<%@page import="aca.catalogo.CatCarrera"%>
<%@page import="aca.plan.MapaNuevoUnidad"%>
<%@page import="aca.plan.MapaNuevoActividad"%>
<%@page import="aca.plan.MapaNuevoBibliografia"%>
<%@page import="aca.plan.MapaNuevoBiblioUnidad"%>

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
	String cursoId			= request.getParameter("cursoId");
	String tipo				= "";
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String accesos			= "";
	String respuesta		= "";
	String institucion 		= (String)session.getAttribute("institucion");
	
	int accion				= Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));
	
	boolean esAdmin			= Boolean.parseBoolean(session.getAttribute("admin")+"");
	
	ArrayList<MapaNuevoUnidad> listUnidades = mapaNuevoUnidadU.getListCurso(conEnoc, cursoId, "ORDER BY ORDEN, UNIDAD_ID");
	ArrayList<MapaNuevoActividad> listActividades = null;
	ArrayList<MapaNuevoBibliografia> listBibliografias = null;
	ArrayList<MapaNuevoBiblioUnidad> listBiblioUnidad = null;
	
	switch(accion){
		case 5:{//Cerrar materia para administrador y coordinador
			mapaNuevoCurso.mapeaRegId(conEnoc, cursoId, planId, versionId);
		
			// incrementa el nivel de monitoreo (1=Maestro, 2=Coordinador, 3=Administrador y 4=Cerrado)
			int estado = Integer.parseInt(mapaNuevoCurso.getEstado());
			if (estado < 4) estado = estado+1;
			
			mapaNuevoCurso.setEstado(String.valueOf(estado));
			
			if(mapaNuevoCursoU.updateReg(conEnoc,mapaNuevoCurso)){
				respuesta = "<font size=2 color=blue><b>Se cerr&oacute; correctamente la materia</b></font>";
			}else{
				respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al cerrar. Int&eacute;ntelo de nuevo</b></font>";
			}
		}break;		
		case 7:{//Abre la materia para usuario administrador y coordinador
			mapaNuevoCurso.mapeaRegId(conEnoc, cursoId, planId, versionId);
		
			// Decrementa el nivel de monitoreo (1=Maestro, 2=Coordinador, 3=Administrador y 4=Cerrado)
			int estado = Integer.parseInt(mapaNuevoCurso.getEstado());
			if (estado > 1) estado = estado-1;
			
			mapaNuevoCurso.setEstado(String.valueOf(estado));			
			
			if(mapaNuevoCursoU.updateReg(conEnoc, mapaNuevoCurso)){
				respuesta = "<font size=2 color=blue><b>Se abri&oacute; correctamente la materia</b></font>";
			}else{
				respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al abrir. Int&eacute;ntelo de nuevo</b></font>";
			}
		}break;		
	}
	accesos = aca.acceso.AccesoUtil.getAccesos(conEnoc, codigoPersonal);
	mapaNuevoPlan.mapeaRegId(conEnoc, planId, versionId);
	mapaNuevoCurso.mapeaRegId(conEnoc, cursoId, planId, versionId);

%>
<head>
	<script type="text/javascript">
		function cerrarMateria(){
			if(confirm("¿Está seguro que desea cerrar la materia?")){			
				document.location = "verMateriaI?planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId %>&Accion=5";
			}
		}

		function abrirMateria(){
			document.location = "verMateriaI?planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId %>&Accion=7";
			
		}
	</script>
</head>
<body>
	<table class="goback">
		<tr>
			<td><a href="materias?planId=<%=planId %>&versionId=<%=versionId %>">&lsaquo;&lsaquo;Back</a></td>
		</tr>
	</table>
<%
	if(!respuesta.equals("")){
%>
	<table style="margin: 0 auto;">
		<tr>
			<td align="center"><%=respuesta %></td>
		</tr>
	</table>
<%
	}

	if(esAdmin){
%>
<input type="button" class="btn btn-primary" value="Cerrar Materia" onclick="cerrarMateria();" style="position: fixed; top: 70px; left: 30px;" />
<input type="button" class="btn btn-primary" value="Abrir Materia" onclick="abrirMateria();" style="position: fixed; top: 100px; left: 30px;" />
<input type="button" class="btn btn-primary" value="Editar Unidades" onclick="document.location = 'editaUnidadI?planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId %>&Opcion=1';" style="position: fixed; top: 130px; left: 30px;" />
<%
	}else if(accesos.indexOf(mapaNuevoPlan.getCarreraId()) != -1){//Si es coordinador
			if(mapaNuevoCurso.getEstado().equals("2")){//Si el nivel de revision esta en el coordinador
%>
	<input type="button" class="button" value="Cerrar Materia" onclick="cerrarMateria();" style="position: fixed; top: 70px; left: 30px;" />
	<input type="button" class="button" value="Abrir Materia" onclick="abrirMateria();" style="position: fixed; top: 100px; left: 30px;" />
<%
			}
	}
%>
	<table style="width:90%" align="center">
		<tr>
			<td class="titulo"><%=institucion.toUpperCase()%></td>
		</tr>
		<tr>
			<td class="titulo2"><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, mapaNuevoPlan.getCarreraId())) %></td>
		</tr>
		<tr>
			<td class="titulo2"><%=mapaNuevoPlan.getNombre() %></td>
		</tr>
	</table>
	<br>
	<table style="width:850px" align="center" style="border: solid 1px black;" border=1>
		<tr>
			<td width="180px"><b>SUBJECT:</b></td>
			<td><%=mapaNuevoCurso.getNombre() %></td>
			<td colspan="2"><b>SEMESTER:</b></td>
			<td colspan="2"><%=mapaNuevoCurso.getCiclo() %></td>
			<td colspan="3"><b>ID:</b></td>
			<td colspan="3"><%=mapaNuevoCurso.getClave() %></td>
			<td><b>SERIALIZING</b></td>
			<td><%=mapaNuevoCurso.getSeriacion() %></td>
		</tr>
		<tr>
			<td width="180px"><b>OBJECTIVE OF THE COURSE:</b></td>
			<td width="670px" colspan="13" id="objetivoAsignaturaTD"><%=mapaNuevoCurso.getDescripcion().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
		</tr>
		<tr>
			<td width="180px"><b>CURRICULUM AREA:</b></td>
			<td><%=mapaNuevoCurso.getUbicacion() %></td>
<%
	if(mapaNuevoPlan.getHts().equals("S")){
%>
			<td><b>HTS:</b></td>
			<td><%=mapaNuevoCurso.getHst() %></td>
<%
	}
	if(mapaNuevoPlan.getHps().equals("S")){
%>
			<td><b>HPS:</b></td>
			<td><%=mapaNuevoCurso.getHsp() %></td>
<%
	}
	if(mapaNuevoPlan.getHfd().equals("S")){
%>
			<td><b>HFD:</b></td>
			<td><%=mapaNuevoCurso.getHfd() %></td>
<%
	}
	if(mapaNuevoPlan.getHei().equals("S")){
%>
			<td><b>HEI:</b></td>
			<td><%=mapaNuevoCurso.getHei() %></td>
<%
	}
%>
			<td><b>THS:</b></td>
			<td><%=mapaNuevoCurso.getThs() %></td>
			<td><b>CRS:</b></td>
			<td><%=mapaNuevoCurso.getCreditos() %></td>
			<td><b>TOTAL HOURS:</b></td>
			<td><%=mapaNuevoCurso.getHt() %></td>
		</tr>
		<tr>
			<td colspan="6"><b>COMPETENCE PROFILE ATTENDED BY COURSE:</b></td>
			<td colspan="8"><%=mapaNuevoCurso.getCompetencia() %></td>
		</tr>
	</table>
	<br>
	<!-- Esta tabla es para la unidad -->
<%
	for(int i = 0; i < listUnidades.size(); i++){
		mapaNuevoUnidad = (MapaNuevoUnidad) listUnidades.get(i);
		String uid = mapaNuevoUnidad.getUnidadId();
		listActividades = mapaNuevoActividadU.getListUnidad(conEnoc, cursoId, uid, "ORDER BY TIPO, ACTIVIDAD_ID");
		listBiblioUnidad = mapaNuevoBiblioUnidadU.getListCursoUnidad(conEnoc, cursoId, uid, "ORDER BY (SELECT TIPO FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA"+ 
				" WHERE CURSO_ID = ENOC.MAPA_NUEVO_BIBLIO_UNIDAD.CURSO_ID AND BIBLIOGRAFIA_ID = ENOC.MAPA_NUEVO_BIBLIO_UNIDAD.BIBLIOGRAFIA_ID), ID");
%>
		<div id="unidad<%=mapaNuevoUnidad.getUnidadId() %>" align="center">
			<br>
			<table style="width:850px" align="center" style="border: solid 1px black;">
				<tr>
					<td align="center" width="100px" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>ESTIMATED TIME</b></td>
					<td align="center" width="120px" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>NAME AND PURPOSE OF THE UNIT
					</b></td>
					<td align="center" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>THEMES AND SUBTHEMES</b></td>
					<td align="center" width="120px" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>LEARNING ACTIVITIES</b></td>
					<td align="center" width="120px" style="border-bottom: solid 1px black;"><b>REFERENCES</b></td>
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
								<td colspan="2"><b>OBJECTIVE:</b></td>
							</tr>
<%
		for(int j = 0; j < listActividades.size(); j++){
			mapaNuevoActividad = (MapaNuevoActividad) listActividades.get(j);
			if(!tipo.equals(mapaNuevoActividad.getTipo())){
				tipo = mapaNuevoActividad.getTipo();
%>
							<tr>
								<td colspan="2"><b><%=tipo.equals("1")?"Knowledge:":tipo.equals("2")?"Skills:":"Attitudes:" %></b></td>
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
%>
						</table>
					</td>
					<td valign="top" id="temas<%=uid %>" style="border-right: solid 1px black;"><%=mapaNuevoUnidad.getTemas().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
					<td valign="top" width="120px" style="border-right: solid 1px black;">
						<table style="width:100%">
<%
		tipo = "";
		for(int j = 0; j < listActividades.size(); j++){
			mapaNuevoActividad = (MapaNuevoActividad) listActividades.get(j);
			if(!tipo.equals(mapaNuevoActividad.getTipo())){
				tipo = mapaNuevoActividad.getTipo();
%>
							<tr>
								<td colspan="2"><b><%=tipo.equals("1")?"KNOWLEDGE (Know)":tipo.equals("2")?"SKILLS (Know what to do)":"ATTITUDES (Know how to be)" %></b></td>
							</tr>
<%
			}
%>
							<tr>
								<td colspan="2">Activity:</td>
							</tr>
							<tr>
								<td width="6px" valign="top">-</td>
								<td id="actividad<%=uid %>-<%=mapaNuevoActividad.getActividadId() %>"><%=mapaNuevoActividad.getDescripcion().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
							</tr>
							<tr>
								<td colspan="2">Performance criteria</td>
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
		for(int j = 0; j < listBiblioUnidad.size(); j++){
			mapaNuevoBiblioUnidad = (MapaNuevoBiblioUnidad) listBiblioUnidad.get(j);
			mapaNuevoBibliografia.mapeaRegId(conEnoc, cursoId, mapaNuevoBiblioUnidad.getBibliografiaId());
			if(!tipo.equals(mapaNuevoBibliografia.getTipo())){
				tipo = mapaNuevoBibliografia.getTipo();
%>
							<tr>
								<td colspan="2"><b><%=tipo.equals("1")?"Books and Magazines:":"links:" %></b></td>
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
			<td><b>TEACHER ACTION (Methodology):</b></td>
		</tr>
		<tr>
			<td>
				<table style="width:100%" id="tablaAccionDocente">
<%
	for(int i = 0; i < listUnidades.size(); i++){
		mapaNuevoUnidad = (MapaNuevoUnidad) listUnidades.get(i);
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
			<td><b>DIDACTIC RESOURCES:</b></td>
		</tr>
		<tr>
			<td><%=mapaNuevoCurso.getMediosRecursos() %></td>
		</tr>
		<tr>
			<td><b>ELEMENTS OF ASSESSMENT</b></td>
		</tr>
		<tr>
			<td><b>Diagnostic:</b></td>
		</tr>
		<tr>
			<td><%=mapaNuevoCurso.getEeDiagnostica() %></td>
		</tr>
		<tr>
			<td><b>Formative:</b></td>
		</tr>
		<tr>
			<td><%=mapaNuevoCurso.getEeFormativa() %></td>
		</tr>
		<tr>
			<td><b>Summative:</b></td>
		</tr>
		<tr>
			<td><%=mapaNuevoCurso.getEeSumativa() %></td>
		</tr>
		<tr>
			<td><b>GRADING SCALE (Weighting grades):</b></td>
		</tr>
		<tr>
			<td><%=mapaNuevoCurso.getEscala() %></td>
		</tr>
	</table>
	<br>
	<table style="width:850px" align="center" style="border: solid 1px black;" id="bibliografiaTotal">
		<tr>
			<td align="center" colspan="3"><b>TOTAL BIBLIOGRAPHY</b></td>
		</tr>
<%
	tipo = "";
	listBibliografias = mapaNuevoBibliografiaU.getListCurso(conEnoc, cursoId, "ORDER BY TIPO, BIBLIOGRAFIA");
	for(int j = 0; j < listBibliografias.size(); j++){
		mapaNuevoBibliografia = (MapaNuevoBibliografia) listBibliografias.get(j);
		if(!tipo.equals(mapaNuevoBibliografia.getTipo())){
			tipo = mapaNuevoBibliografia.getTipo();
%>
		<tr>
			<td colspan="2"><b><%=tipo.equals("1")?"Books and Magazines:":"Links:" %></b></td>
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
</body>

<%@ include file= "../../cierra_enoc.jsp" %>