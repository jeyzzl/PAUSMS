<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.catalogo.CatCarrera"%>
<%@page import="aca.plan.MapaNuevoUnidad"%>
<%@page import="aca.plan.MapaNuevoActividad"%>
<%@page import="aca.plan.MapaNuevoProducto"%>
<%@page import="aca.plan.MapaNuevoBibliografia"%>
<%@page import="aca.plan.MapaNuevoBiblioUnidad"%>

<jsp:useBean id="mapaNuevoPlan" class="aca.plan.MapaNuevoPlan" scope="page"/>
<jsp:useBean id="mapaNuevoCurso" class="aca.plan.MapaNuevoCurso" scope="page"/>
<jsp:useBean id="mapaNuevoCursoU" class="aca.plan.MapaNuevoCursoUtil" scope="page"/>
<jsp:useBean id="mapaNuevoUnidad" class="aca.plan.MapaNuevoUnidad" scope="page"/>
<jsp:useBean id="mapaNuevoUnidadU" class="aca.plan.MapaNuevoUnidadUtil" scope="page"/>
<jsp:useBean id="mapaNuevoActividad" class="aca.plan.MapaNuevoActividad" scope="page"/>
<jsp:useBean id="mapaNuevoActividadU" class="aca.plan.MapaNuevoActividadUtil" scope="page"/>
<jsp:useBean id="mapaNuevoProductoU" class="aca.plan.MapaNuevoProductoUtil" scope="page"/>
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
	ArrayList<MapaNuevoProducto> listProductos = null;
	ArrayList<MapaNuevoBibliografia> listBibliografias = null;	
	
	switch(accion){
		case 5:{//Cerrar materia para administrador y coordinador
			mapaNuevoCurso.mapeaRegId(conEnoc, cursoId, planId, versionId);
		
			// incrementa el nivel de monitoreo (1=Maestro, 2=Coordinador, 3=Administrador y 4=Cerrado)
			int estado = Integer.parseInt(mapaNuevoCurso.getEstado());
			if (estado < 4) estado = estado+1;
			
			mapaNuevoCurso.setEstado(String.valueOf(estado));
			
			if(mapaNuevoCursoU.updateReg(conEnoc, mapaNuevoCurso)){
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
				document.location = "verMateria?planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId %>&Accion=5";
			}
		}

		function abrirMateria(){
			document.location = "verMateria?planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId %>&Accion=7";
			
		}
	</script>
</head>
<body>
<div class="container-fluid">
	<h3>Plan:<%=planId %><small class="text-muted fs-5">( <%=mapaNuevoPlan.getNombre() %> - <%=mapaNuevoCurso.getNombre() %> )</small></h3>
	<div class="alert alert-info">
		<a href="materias?planId=<%=planId %>&versionId=<%=versionId %>" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>&nbsp; &nbsp;
<%	
	if(esAdmin){
%>
<!-- style="position: fixed; top: 70px; left: 30px;" -->
<input type="button" class="btn btn-primary" value="Cerrar Materia" onclick="cerrarMateria();"/>
<!-- style="position: fixed; top: 100px; left: 30px;" -->
<input type="button" class="btn btn-primary" value="Abrir Materia" onclick="abrirMateria();"  />
<!-- style="position: fixed; top: 130px; left: 30px;" -->
<input type="button" class="btn btn-primary" value="Editar Unidades" onclick="document.location = 'editaUnidad?planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId %>&Opcion=1';" />
<%
	}else if(accesos.indexOf(mapaNuevoPlan.getCarreraId()) != -1){//Si es coordinador
			if(mapaNuevoCurso.getEstado().equals("2")){//Si el nivel de revision esta en el coordinador
%>
	<!-- style="position: fixed; top: 70px; left: 30px;" -->
	<input type="button" class="button btn-primary" value="Cerrar Materia" onclick="cerrarMateria();"/>
	<!-- style="position: fixed; top: 100px; left: 30px;" -->
	<input type="button" class="button btn-primary" value="Abrir Materia" onclick="abrirMateria();"/>
<%
			}
	}
%>		
	</div>
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
%>
	<table class="table table-condensed" align="center" style="border: solid 1px black;" border=1>
		<tr>
			<td width="180px"><b>ASIGNATURA:</b></td>
			<td><%=mapaNuevoCurso.getNombre() %></td>
			<td colspan="2"><b>SEMESTRE:</b></td>
			<td colspan="2"><%=mapaNuevoCurso.getCiclo() %></td>
			<td colspan="3"><b>CLAVE:</b></td>
			<td colspan="3"><%=mapaNuevoCurso.getClave() %></td>
			<td><b>SERIACI&Oacute;N</b></td>
			<td><%=mapaNuevoCurso.getSeriacion() %></td>
		</tr>
		<tr>
			<td colspan="6"><b>COMPETENCIA DEL PERFIL QUE ATIENDE LA ASIGNATURA:</b></td>
			<td colspan="8"><%=mapaNuevoCurso.getCompetencia() %></td>
		</tr>
		<tr>
			<td width="180px"><b>PRODUCTOS DE APRENDIZAJE(OBJETIVO DE LA ASIGNATURA):</b></td>
			<td width="670px" colspan="13" id="objetivoAsignaturaTD"><%=mapaNuevoCurso.getDescripcion().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
		</tr>
		<tr>
			<td width="180px"><b>L&Iacute;NEA CURRICULAR:</b></td>
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
			<td><b>HORAS TOTALES:</b></td>
			<td><%=mapaNuevoCurso.getHt() %></td>
		</tr>
		<tr>
			<td width="250px"><b>PROYECTO INTEGRADOR AL QUE SE VINCULA LA ASIGNATURA</b></td>
			<td width="600px" >
				<a href="cursoAccion?planId=<%=planId%>&versionId=<%=versionId%>&cursoId=<%=cursoId%>" class="btn btn-primary btn-sm">
				  <i class='icon-pencil icon-white'></i>
				</a>				
				<br>
				<%=mapaNuevoCurso.getProyecto().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %>
			</td>
		</tr>
	</table>
	<br>
	<!-- Esta tabla es para la unidad -->
<%
	for(int i = 0; i < listUnidades.size(); i++){
		mapaNuevoUnidad = (MapaNuevoUnidad) listUnidades.get(i);
		String uid = mapaNuevoUnidad.getUnidadId();
		listActividades = mapaNuevoActividadU.getListUnidad(conEnoc, cursoId, uid, "ORDER BY TIPO, ACTIVIDAD_ID");
		listProductos 	= mapaNuevoProductoU.getListUnidad(conEnoc, cursoId, uid, "ORDER BY TIPO, PRODUCTO_ID");
%>
		
		<br>
		<div class="alert alert-info">
			<span class="label label-pill label-inverse"><%=mapaNuevoUnidad.getOrden()%></span>&nbsp;<%=mapaNuevoUnidad.getNombre().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %>
		</div>
		<table style="border: solid 1px black;" class="table table-condensed">				
			<tr>
				<td width="20%" align="center" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>TIEMPO ESTIMADO</b></td>
				<td width="20%" align="center" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>PRODUCTOS DE APRENDIZAJE</b></td>
				<td width="20%" align="center" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>CONTENIDO</b></td>
				<td width="20%" align="center" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>METODOLOGÍA<br>ACTIVIDADES DE APRENDIZAJE</b></td>
				<td width="20%" align="center" style="border-bottom: solid 1px black;"><b>EVALUACIONES(Evidencias)</b></td>
			</tr>
			<tr>
				<td valign="top" id="tiempo<%=uid %>" style="border-right: solid 1px black;"><%=mapaNuevoUnidad.getTiempo().replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
				<td valign="top" style="border-right: solid 1px black;">
					<table style="width:100%">						
<%
		for(aca.plan.MapaNuevoProducto producto : listProductos){
%>							
						<tr>
							<td width="6px" valign="top">-</td>
							<td><%=aca.plan.MapaNuevoCursoUtil.cortaFrase(producto.getDescripcion(),40).replaceAll("\n","<br>").replaceAll(" ","&nbsp;")%></td>
						</tr>
<%
		}
%>
					</table>
				</td>
				<td valign="top" id="temas<%=uid %>" style="border-right: solid 1px black;"><%=aca.plan.MapaNuevoCursoUtil.cortaFrase(mapaNuevoUnidad.getTemas(),40).replaceAll("\n","<br>").replaceAll(" ","&nbsp;") %></td>
				<td valign="top" width="120px" style="border-right: solid 1px black;">
					<table style="width:100%">
<%		
		int row = 0;
		for(aca.plan.MapaNuevoActividad actividad : listActividades){		
			row++;
%>						
						<tr>
							<td width="6px" valign="top"><%=row%>.</td>
							<td><%=aca.plan.MapaNuevoCursoUtil.cortaFrase(actividad.getDescripcion(),40).replaceAll("\n","<br>").replaceAll(" ","&nbsp;")%></td>							
						</tr>						
<%
		}
%>
					</table>&nbsp;
				</td>
				<td valign="top" width="120px">
					<table style="width:100%">
						<tr>
							<td colspan="2">						  
								<%=mapaNuevoUnidad.getAccionDocente().replaceAll("\n","<br>").replaceAll(" ","&nbsp;")%>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<br>		
<%
	}
%>
	<br>
	<table style="width:850px" align="center" style="border: solid 1px black;" class="tabla">
		<tr>
			<td><b>ACCI&Oacute;N DOCENTE (Metodolog&iacute;a):</b></td>
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
	<table style="width:850px" align="center" style="border: solid 1px black;" id="bibliografiaTotal" class="tabla">
		<tr>
			<td align="center" colspan="3"><b>BIBLIOGRAFIA TOTAL</b></td>
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
</div>	
</body>

<%@ include file= "../../cierra_enoc.jsp" %>