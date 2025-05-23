<%@ include file= "../../con_enoc.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.plan.MapaNuevoPlan"%>
<%@page import="aca.catalogo.CatCarrera"%>
<%@page import="aca.plan.MapaNuevoCurso"%>
<%@page import="aca.plan.MapaNuevoUnidad"%>
<%@page import="aca.plan.MapaNuevoActividad"%>
<%@page import="aca.plan.MapaNuevoBibliografia"%>
<%@page import="aca.plan.MapaNuevoProducto"%>

<jsp:useBean id="mapaNuevoPlan" class="aca.plan.MapaNuevoPlan" scope="page"/>
<jsp:useBean id="mapaNuevoPlanU" class="aca.plan.MapaNuevoPlanUtil" scope="page"/>
<jsp:useBean id="mapaNuevoCurso" class="aca.plan.MapaNuevoCurso" scope="page"/>
<jsp:useBean id="mapaNuevoCursoU" class="aca.plan.MapaNuevoCursoUtil" scope="page"/>
<jsp:useBean id="mapaNuevoCursoDestino" class="aca.plan.MapaNuevoCurso" scope="page"/>
<jsp:useBean id="mapaNuevoCursoDestinoU" class="aca.plan.MapaNuevoCursoUtil" scope="page"/>
<jsp:useBean id="mapaNuevoUnidad" class="aca.plan.MapaNuevoUnidad" scope="page"/>
<jsp:useBean id="mapaNuevoUnidadU" class="aca.plan.MapaNuevoUnidadUtil" scope="page"/>
<jsp:useBean id="mapaNuevoActividad" class="aca.plan.MapaNuevoActividad" scope="page"/>
<jsp:useBean id="mapaNuevoActividadU" class="aca.plan.MapaNuevoActividadUtil" scope="page"/>
<jsp:useBean id="mapaNuevoBibliografia" class="aca.plan.MapaNuevoBibliografia" scope="page"/>
<jsp:useBean id="mapaNuevoBibliografiaU" class="aca.plan.MapaNuevoBibliografiaUtil" scope="page"/>
<jsp:useBean id="mapaNuevoBiblioUnidad" class="aca.plan.MapaNuevoBiblioUnidad" scope="page"/>
<jsp:useBean id="mapaNuevoBiblioUnidadU" class="aca.plan.MapaNuevoBiblioUnidadUtil" scope="page"/>
<jsp:useBean id="MapaNuevoProducto" class="aca.plan.MapaNuevoProducto" scope="page"/>
<jsp:useBean id="MapaNuevoProductoU" class="aca.plan.MapaNuevoProductoUtil" scope="page"/>
<%
	Integer accion			= Integer.parseInt(request.getParameter("Accion"));
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String accesos			= "";
	
	switch(accion){
		case 5:{//Listar Planes para copiar
			ArrayList<MapaNuevoPlan> listPlanes	= mapaNuevoPlanU.getListMaestro(conEnoc, codigoPersonal, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), NOMBRE");
			String facultadId = "";
%>
			<table style="width:100%">
<%
			for(int i = 0; i < listPlanes.size(); i++){
				mapaNuevoPlan = (MapaNuevoPlan) listPlanes.get(i);
				String facultadTmp = aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, mapaNuevoPlan.getCarreraId());
				String cursoIdDestino = request.getParameter("cursoIdDestino");
				if(!facultadId.equals(facultadTmp)){
					facultadId = facultadTmp;
%>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4" class="titulo2" style="text-align: left;"><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, facultadId) %></td>
				</tr>
				<tr>
					<th><spring:message code="aca.Numero"/></th>
					<th>Plan ID</th>
					<th><spring:message code="aca.Nombre"/></th>
					<th><spring:message code="aca.Carrera"/></th>
				</tr>
<%
				}
%>
				<tr class="button">
					<td width="20px" align="right" onclick="mostrarMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>', '<%=cursoIdDestino %>');"><%=i+1 %></td>
					<td width="80px" align="center" onclick="mostrarMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>', '<%=cursoIdDestino %>');"><%=mapaNuevoPlan.getPlanId() %></td>
					<td onclick="mostrarMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>', '<%=cursoIdDestino %>');"><%=mapaNuevoPlan.getNombre() %></td>
					<td onclick="mostrarMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>', '<%=cursoIdDestino %>');"><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, mapaNuevoPlan.getCarreraId()) %></td>
				</tr>
<%
			}
%>
				<tr><td colspan="4" align="center"><input type="button" class="btn btn-primary" value="Cancelar" onclick="$('divCopiar').remove();" /></td></tr>
			</table>
<%
		}break;
		case 6:{//Listar Materias para copiar
			String ciclo = "";
			String planIdOrigen = request.getParameter("planIdOrigen");
			String versionIdOrigen = request.getParameter("versionIdOrigen");
			String cursoIdDestino = request.getParameter("cursoIdDestino");
			ArrayList<MapaNuevoCurso> listCursos = null;
			accesos = aca.acceso.AccesoUtil.getAccesos(conEnoc, codigoPersonal);
			mapaNuevoPlan.mapeaRegId(conEnoc, planIdOrigen, versionIdOrigen);
			if(accesos.indexOf(mapaNuevoPlan.getCarreraId()) != -1)//Si es coordinador (acceso a la carrera)
				listCursos = mapaNuevoCursoU.getListPlan(conEnoc, planIdOrigen, versionIdOrigen, "ORDER BY CICLO, NOMBRE");
			else
				listCursos = mapaNuevoCursoU.getListPlanMaestro(conEnoc, planIdOrigen, versionIdOrigen, codigoPersonal, "ORDER BY CICLO, NOMBRE");
%>
			<table style="width:100%">
<%
			for(int i = 0; i < listCursos.size(); i++){
				mapaNuevoCurso = (MapaNuevoCurso) listCursos.get(i);
				if(!ciclo.equals(mapaNuevoCurso.getCiclo())){
					ciclo = mapaNuevoCurso.getCiclo();
%>
				<tr>
					<td colspan="11">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="11" class="titulo2" style="text-align: left;">Semestre <%=ciclo %></td>
				</tr>
				<tr>
					<th width="40px"><spring:message code="aca.Numero"/></th>
					<th width="120px"><spring:message code="aca.Clave"/></th>
					<th>Asignatura</th>
					<th width="80px">Unidades</th>
				</tr>
<%
				}
				String numUnidades = aca.plan.MapaNuevoUnidadUtil.numUnidades(conEnoc, mapaNuevoCurso.getCursoId());
				if(numUnidades.equals("0")){
%>
				<tr>
<%
				}else{
%>
				<tr class="button" onclick="copiarDatos('<%=planIdOrigen %>', '<%=versionIdOrigen %>', '<%=mapaNuevoCurso.getCursoId() %>', '<%=mapaNuevoCurso.getNombre() %>', '<%=cursoIdDestino %>');">
<%
				}
%>
					<td width="40px" align="right"><%=i+1 %></td>
					<td width="120px" align="center"><%=mapaNuevoCurso.getClave() %></td>
					<td><%=mapaNuevoCurso.getNombre() %></td>
					<td width="80px" align="center"><%=numUnidades %></td>
				</tr>
<%
			}
%>
				<tr><td colspan="4" align="center"><input type="button" class="btn btn-primary" value="Cancelar" onclick="$('divCopiar').remove();" /></td></tr>
			</table>
<%
		}break;
		case 7:{
			//Copiar datos
			String planIdOrigen 	= request.getParameter("planIdOrigen");
			String versionIdOrigen	= request.getParameter("versionIdOrigen");
			String cursoIdOrigen 	= request.getParameter("cursoIdOrigen");
			String planIdDestino	= request.getParameter("planIdDestino");
			String versionIdDestino	= request.getParameter("versionIdDestino");
			String cursoIdDestino 	= request.getParameter("cursoIdDestino");
			boolean error 			= false;
			
			mapaNuevoCurso.mapeaRegId(conEnoc, planIdOrigen, versionIdOrigen, cursoIdOrigen);
			mapaNuevoCursoDestino.mapeaRegId(conEnoc, cursoIdDestino, planIdDestino, versionIdDestino);
			
			mapaNuevoCursoDestino.setDescripcion(mapaNuevoCurso.getDescripcion());
			mapaNuevoCursoDestino.setMediosRecursos(mapaNuevoCurso.getMediosRecursos());
			mapaNuevoCursoDestino.setEeDiagnostica(mapaNuevoCurso.getEeDiagnostica());
			mapaNuevoCursoDestino.setEeFormativa(mapaNuevoCurso.getEeFormativa());
			mapaNuevoCursoDestino.setEeSumativa(mapaNuevoCurso.getEeSumativa());
			mapaNuevoCursoDestino.setProyecto(mapaNuevoCurso.getProyecto());
			mapaNuevoCursoDestino.setEscala(mapaNuevoCurso.getEscala());
			mapaNuevoCursoDestino.setCompetencia(mapaNuevoCurso.getCompetencia());
			if(!mapaNuevoCursoDestinoU.updateReg(conEnoc, mapaNuevoCursoDestino)){
				error = true;
			}
			
			ArrayList<MapaNuevoUnidad> listUnidades = mapaNuevoUnidadU.getListCurso(conEnoc, cursoIdOrigen, "ORDER BY ORDEN, UNIDAD_ID");
			ArrayList<MapaNuevoBibliografia> listBibliografias = null;
			ArrayList<MapaNuevoActividad> listActividades = null;	
			ArrayList<MapaNuevoProducto> listProductos = null;
			conEnoc.setAutoCommit(false);
			
			// Copia la bibliografias de la materia
			listBibliografias = mapaNuevoBibliografiaU.getListCurso(conEnoc, cursoIdOrigen, "ORDER BY TIPO, BIBLIOGRAFIA");
			for(int i = 0; i < listBibliografias.size(); i++){
				mapaNuevoBibliografia = (MapaNuevoBibliografia) listBibliografias.get(i);
				mapaNuevoBibliografia.setCursoId(cursoIdDestino);//Reemplazamos el curso
				if(!mapaNuevoBibliografiaU.insertReg(conEnoc, mapaNuevoBibliografia)){
					error = true;
				}
			}
			
			for(int i = 0; i < listUnidades.size(); i++){
				mapaNuevoUnidad = (MapaNuevoUnidad) listUnidades.get(i);
				mapaNuevoUnidad.setCursoId(cursoIdDestino);//Reemplazamos el curso
				if(mapaNuevoUnidadU.insertReg(conEnoc, mapaNuevoUnidad)){
					
					// Lista de actividades en la unidad
					listActividades = mapaNuevoActividadU.getListUnidad(conEnoc, cursoIdOrigen, mapaNuevoUnidad.getUnidadId(), "ORDER BY TIPO, ACTIVIDAD_ID");
					for(int j = 0; j < listActividades.size(); j++){
						mapaNuevoActividad = (MapaNuevoActividad) listActividades.get(j);
						mapaNuevoActividad.setCursoId(cursoIdDestino);//Reemplazamos el curso
						if(!mapaNuevoActividadU.insertReg(conEnoc, mapaNuevoActividad)){
							error = true;
						}
					}
					
					// Lista de productos de aprendizaje
					listProductos = MapaNuevoProductoU.getListUnidad(conEnoc, cursoIdOrigen, mapaNuevoUnidad.getUnidadId(), "ORDER BY TIPO, PRODUCTO_ID");				
					for(int j = 0; j < listProductos.size(); j++){
						MapaNuevoProducto = (MapaNuevoProducto) listProductos.get(j);
						MapaNuevoProducto.setCursoId(cursoIdDestino);//Reemplazamos el curso
						if(!MapaNuevoProductoU.insertReg(conEnoc, MapaNuevoProducto)){
							error = true;
						}
					}
					
				}else{
					error = true;
				}
			}
			if(error){
				conEnoc.rollback();
%>
				muestraMensaje("Ocurrio un error al guardar. Intentelo de nuevo", "red");
<%
			}else{
				conEnoc.commit();
%>
				document.location = document.location+"&Accion=7";
<%
			}
			conEnoc.setAutoCommit(true);
		}break;
		case 8:{//Listar Planes para traspaso
			ArrayList<MapaNuevoPlan> listPlanes	= mapaNuevoPlanU.getListAll(conEnoc, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), NOMBRE");
			String facultadId = "";
			String planIdOrigen	= request.getParameter("planIdOrigen");
			String versionIdOrigen	= request.getParameter("versionIdOrigen");
			String cursoIdOrigen = request.getParameter("cursoIdOrigen");
			String nombreOrigen = request.getParameter("nombreOrigen");
%>
			<table style="width:100%">
<%
			for(int i = 0; i < listPlanes.size(); i++){
				mapaNuevoPlan = (MapaNuevoPlan) listPlanes.get(i);
				String facultadTmp = aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, mapaNuevoPlan.getCarreraId());
				if(!facultadId.equals(facultadTmp)){
					facultadId = facultadTmp;
%>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4" class="titulo2" style="text-align: left;"><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, facultadId) %></td>
				</tr>
				<tr>
					<th><spring:message code="aca.Numero"/></th>
					<th>Plan ID</th>
					<th><spring:message code="aca.Nombre"/></th>
					<th><spring:message code="aca.Carrera"/></th>
				</tr>
<%
				}
%>
				<tr class="button">
					<td width="20px" align="right" onclick="copiarMateria('<%=cursoIdOrigen %>', '<%=nombreOrigen %>', '<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=i+1 %></td>
					<td width="80px" align="center" onclick="copiarMateria('<%=cursoIdOrigen %>', '<%=nombreOrigen %>', '<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=mapaNuevoPlan.getPlanId() %></td>
					<td onclick="copiarMateria('<%=cursoIdOrigen %>', '<%=nombreOrigen %>', '<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=mapaNuevoPlan.getNombre() %></td>
					<td onclick="copiarMateria('<%=cursoIdOrigen %>', '<%=nombreOrigen %>', '<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, mapaNuevoPlan.getCarreraId()) %></td>
				</tr>
<%
			}
%>
				<tr><td colspan="4" align="center"><input type="button" class="btn btn-primary" value="Cancelar" onclick="$('divCopiar').remove();" /></td></tr>
			</table>
<%
		}break;
		case 9:{//Copiar materia (traspaso)
			String planIdOrigen 		= request.getParameter("planIdOrigen");
			String versionIdOrigen 		= request.getParameter("versionIdOrigen");
			String cursoIdOrigen 		= request.getParameter("cursoIdOrigen");
			String planIdDestino 		= request.getParameter("planIdDestino");
			String versionIdDestino 	= request.getParameter("versionIdDestino");
			boolean error = false;
			
			conEnoc.setAutoCommit(false);
			
			mapaNuevoCurso = mapaNuevoCursoU.mapeaRegId(conEnoc, cursoIdOrigen, planIdOrigen, versionIdOrigen);
			mapaNuevoCurso.setPlanId(planIdDestino);
			mapaNuevoCurso.setVersionId(versionIdDestino);
			mapaNuevoCurso.setCursoId(mapaNuevoCursoU.maximoReg(conEnoc));
			
			if(mapaNuevoCursoU.insertReg(conEnoc, mapaNuevoCurso)){
				String cursoIdDestino = mapaNuevoCurso.getCursoId();
				
				mapaNuevoCurso = mapaNuevoCursoU.mapeaRegId(conEnoc, cursoIdOrigen, planIdOrigen, versionIdOrigen);				
				mapaNuevoCursoDestino.mapeaRegId(conEnoc, cursoIdDestino, planIdDestino, versionIdDestino);
				
				mapaNuevoCursoDestino.setDescripcion(mapaNuevoCurso.getDescripcion());
				mapaNuevoCursoDestino.setMediosRecursos(mapaNuevoCurso.getMediosRecursos());
				mapaNuevoCursoDestino.setEeDiagnostica(mapaNuevoCurso.getEeDiagnostica());
				mapaNuevoCursoDestino.setEeFormativa(mapaNuevoCurso.getEeFormativa());
				mapaNuevoCursoDestino.setEeSumativa(mapaNuevoCurso.getEeSumativa());
				mapaNuevoCursoDestino.setEscala(mapaNuevoCurso.getEscala());
				mapaNuevoCursoDestino.setCompetencia(mapaNuevoCurso.getCompetencia());
				if(!mapaNuevoCursoDestinoU.updateReg(conEnoc, mapaNuevoCursoDestino)){
					error = true;
				}
				
				ArrayList<MapaNuevoUnidad> listUnidades = mapaNuevoUnidadU.getListCurso(conEnoc, cursoIdOrigen, "ORDER BY ORDEN,UNIDAD_ID");
				ArrayList<MapaNuevoActividad> listActividades = null;
				ArrayList<MapaNuevoBibliografia> listBibliografias = null;
				ArrayList<MapaNuevoProducto> listProductos = null;
				
				listBibliografias = mapaNuevoBibliografiaU.getListCurso(conEnoc, cursoIdOrigen, "ORDER BY TIPO, BIBLIOGRAFIA");
				for(int i = 0; i < listBibliografias.size(); i++){
					mapaNuevoBibliografia = (MapaNuevoBibliografia) listBibliografias.get(i);
					mapaNuevoBibliografia.setCursoId(cursoIdDestino);//Reemplazamos el curso
					if(!mapaNuevoBibliografiaU.insertReg(conEnoc, mapaNuevoBibliografia)){
						error = true;
					}
				}
				for(int i = 0; i < listUnidades.size(); i++){
					mapaNuevoUnidad = (MapaNuevoUnidad) listUnidades.get(i);
					mapaNuevoUnidad.setCursoId(cursoIdDestino);//Reemplazamos el curso
					if(mapaNuevoUnidadU.insertReg(conEnoc,mapaNuevoUnidad)){
						
						// Lista de actividades
						listActividades = mapaNuevoActividadU.getListUnidad(conEnoc, cursoIdOrigen, mapaNuevoUnidad.getUnidadId(), "ORDER BY TIPO, ACTIVIDAD_ID");
						for(int j = 0; j < listActividades.size(); j++){
							mapaNuevoActividad = (MapaNuevoActividad) listActividades.get(j);
							mapaNuevoActividad.setCursoId(cursoIdDestino);
							//Reemplazamos el curso
							if(!mapaNuevoActividadU.insertReg(conEnoc, mapaNuevoActividad)){
								error = true;
							}
						}						
						
						// Lista de productos de aprendizaje
						listProductos = MapaNuevoProductoU.getListUnidad(conEnoc, cursoIdOrigen, mapaNuevoUnidad.getUnidadId(), "ORDER BY TIPO, PRODUCTO_ID");				
						for(int j = 0; j < listProductos.size(); j++){
							MapaNuevoProducto = (MapaNuevoProducto) listProductos.get(j);
							MapaNuevoProducto.setCursoId(cursoIdDestino);
							//Reemplazamos el curso
							if(!MapaNuevoProductoU.insertReg(conEnoc, MapaNuevoProducto)){
								error = true;
							}
						}
						
					}else{
						error = true;
					}
				}
			}else{
				error = true;
			}
			if(error){
				conEnoc.rollback();
%>
				muestraMensaje("Ocurrio un error al guardar. Intentelo de nuevo", "red");
<%
			}else{
				conEnoc.commit();
%>
				muestraMensaje("Se copio correctamente la materia", "yellow");
				$("divCopiar").remove();
<%
			}
			conEnoc.setAutoCommit(true);
		}break;
	}
%>
<%@ include file= "../../cierra_enoc2.jsf" %>