 <!-- page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" -->

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.plan.MapaNuevoActividad"%>
<%@page import="aca.plan.MapaNuevoBibliografia"%>
<%@page import="aca.plan.MapaNuevoBiblioUnidad"%>

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
	Integer accion		= Integer.parseInt(request.getParameter("Accion"));
	String planId		= request.getParameter("planId");
	String versionId	= request.getParameter("versionId");
	String cursoId		= request.getParameter("cursoId");

	switch(accion){
		case 1:{//Nueva unidad
%>
			<form id="formaUnidad" name="formaUnidad" action="editaUnidadAccion?Accion=5&planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId %>">
				<br>
				<table style="width:850px" align="center" style="border: solid 2px black;">
					<tr>
						<td align="center" width="100px" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>TIEMPO ESTIMADO</b></td>
						<td align="center" width="120px" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>NOMBRE Y OBJETIVO DE LA UNIDAD</b></td>
						<td align="center" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>TEMAS Y SUBTEMAS</b></td>
						<td align="center" width="120px" style="border-bottom: solid 1px black; border-right: solid 1px black;"><b>ACTIVIDADES DE APRENDIZAJE</b></td>
						<td align="center" width="120px" style="border-bottom: solid 1px black;"><b>BIBLIOGRAF&Iacute;A</b></td>
					</tr>
					<tr>
						<td valign="top" style="border-right: solid 1px black;"><textarea id="tiempo" name="tiempo" onkeyup="checkSize(this, event, 50);" class="50" cols="15" rows="5"></textarea></td>
						<td valign="top" style="border-right: solid 1px black;">
							<table style="width:100%">
								<tr>
									<td colspan="2"><textarea id="nombre" name="nombre" onkeyup="checkSize(this, event, 200);" class="200" cols="18" rows="5"></textarea></td>
								</tr>
							</table>
						</td>
						<td valign="top" style="border-right: solid 1px black;"><textarea id="temas" name="temas" onkeyup="checkSize(this, event, 4000);" class="4000" cols="65" rows="20"></textarea></td>
						<td style="border-right: solid 1px black;">
							&nbsp;
						</td>
						<td valign="top">
							<table style="width:100%">
								<tr>
									<td>Libros y revistas:</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>Enlaces electr&oacute;nicos:</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<br>
			</form>
<%
		}break;
		case 2:{// Guardar curso
			conEnoc.setAutoCommit(false);
			mapaNuevoCurso.mapeaRegId(conEnoc, cursoId, planId, versionId );
			mapaNuevoCurso.setMediosRecursos(request.getParameter("mediosRecursos"));
			mapaNuevoCurso.setEeDiagnostica(request.getParameter("eeDiagnostica"));
			mapaNuevoCurso.setEeFormativa(request.getParameter("eeFormativa"));
			mapaNuevoCurso.setEeSumativa(request.getParameter("eeSumativa"));
			mapaNuevoCurso.setEscala(request.getParameter("escala"));
			if(mapaNuevoCursoU.existeReg(conEnoc, cursoId, planId, versionId)){
				if(mapaNuevoCursoU.updateReg(conEnoc, mapaNuevoCurso)){
					conEnoc.commit();
%>
					muestraMensaje('La materia fue modificada correctamente','yellow');
					setTimeout("document.location = document.location",500);
					//guardado();
<%
				}else{
					conEnoc.rollback();
%>
					muestraMensaje('Ocurrio un error al modificar la tematica.<br>Intentelo de nuevo','red');
<%
				}
			}
			conEnoc.setAutoCommit(true);
		}break;
		case 3:{//borrar unidad
			String unidadId = request.getParameter("unidadId");
			mapaNuevoUnidad.mapeaRegId(conEnoc, cursoId, unidadId);
			if(mapaNuevoUnidadU.deleteReg(conEnoc, cursoId, unidadId)){
%>
				muestraMensaje('La unidad fue borrada correctamente','yellow');
				$('unidad<%=mapaNuevoUnidad.getUnidadId() %>').remove();
				$('accionDocente<%=mapaNuevoUnidad.getUnidadId() %>').remove();
				recargarBibliografiaTotal();
<%
			}else{
%>
				muestraMensaje('Ocurrio un error al borrar la unidad.<br>Intentelo de nuevo','red');
<%
			}
		}break;
		case 5:{//Guardar Unidad Nueva
			mapaNuevoUnidad.setCursoId(cursoId);
			mapaNuevoUnidad.setUnidadId(mapaNuevoUnidadU.maximoReg(conEnoc, cursoId));
			mapaNuevoUnidad.setNombre(request.getParameter("nombre"));
			mapaNuevoUnidad.setTiempo(request.getParameter("tiempo"));
			mapaNuevoUnidad.setTemas(request.getParameter("temas"));
			mapaNuevoUnidad.setAccionDocente("");
			if(mapaNuevoUnidadU.insertReg(conEnoc, mapaNuevoUnidad)){
				String uid = mapaNuevoUnidad.getUnidadId();
%>
				muestraMensaje('La unidad fue guardada correctamente','yellow');
				cancelarNuevaUnidad();
				$("formaUnidades").insert({
					bottom: '<div id="unidad<%=uid %>" align="center" onmouseover="this.style.backgroundColor = \'#dddddd\';" onmouseout="this.style.backgroundColor = \'\';"></div>'
				});
				recargarUnidad('<%=uid %>');
				$("tablaAccionDocente").insert({
					bottom: '<tr id="accionDocente<%=uid %>">'+
								'<td id="accionDocenteNombre<%=uid %>" width="100px"><%=mapaNuevoUnidad.getNombre().replaceAll("\n", "\\n") %></td>'+
								'<td id="accionDocenteDatos<%=uid %>" class="button" onclick="editarAccionDocente(\'<%=uid %>\');"><%=mapaNuevoUnidad.getAccionDocente() %></td>'+
							'</tr>'
				});
<%
			}else{
%>
				muestraMensaje('Ocurrio un error al guardar la unidad.<br>Intentelo de nuevo','red');
<%
			}
		}break;
		case 6:{//Modificar Unidad
			String unidadId = request.getParameter("unidadId");
			mapaNuevoUnidad.mapeaRegId(conEnoc, cursoId, unidadId);
			mapaNuevoUnidad.setTiempo(request.getParameter("tiempo"));
			mapaNuevoUnidad.setNombre(request.getParameter("nombre"));
			mapaNuevoUnidad.setTemas(request.getParameter("temas"));
			if(mapaNuevoUnidadU.updateReg(conEnoc, mapaNuevoUnidad)){
%>
				muestraMensaje('La unidad fue modificada correctamente','yellow');
				recargarUnidad('<%=unidadId %>');
<%
			}else{
%>
				muestraMensaje('Ocurrio un error al guardar la unidad.<br>Intentelo de nuevo','red');
<%
			}
		}break;
		case 7:{//Recargar unidad
			String uid = request.getParameter("unidadId");
			String tipo = "";
			mapaNuevoUnidad.mapeaRegId(conEnoc, cursoId, uid);
			ArrayList<MapaNuevoActividad> listActividades = mapaNuevoActividadU.getListUnidad(conEnoc, cursoId, uid, "ORDER BY TIPO, ACTIVIDAD_ID");
			ArrayList<MapaNuevoBiblioUnidad> listBiblioUnidad = mapaNuevoBiblioUnidadU.getListCursoUnidad(conEnoc, cursoId, uid, "ORDER BY (SELECT TIPO FROM ENOC.MAPA_NUEVO_BIBLIOGRAFIA"+ 
					" WHERE CURSO_ID = ENOC.MAPA_NUEVO_BIBLIO_UNIDAD.CURSO_ID AND BIBLIOGRAFIA_ID = ENOC.MAPA_NUEVO_BIBLIO_UNIDAD.BIBLIOGRAFIA_ID), ID");
			mapaNuevoUnidad.setTiempo(mapaNuevoUnidad.getTiempo().replaceAll(" ","&nbsp;").replaceAll("\n","<br>").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;").replaceAll("Ñ","&Ntilde;").replaceAll("Á","&Aacute;").replaceAll("É","&Eacute;").replaceAll("Í","&Iacute;").replaceAll("Ó","&Oacute;").replaceAll("Ú","&Uacute;"));
			mapaNuevoUnidad.setNombre(mapaNuevoUnidad.getNombre().replaceAll(" ","&nbsp;").replaceAll("\n","<br>").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;").replaceAll("Ñ","&Ntilde;").replaceAll("Á","&Aacute;").replaceAll("É","&Eacute;").replaceAll("Í","&Iacute;").replaceAll("Ó","&Oacute;").replaceAll("Ú","&Uacute;"));
			mapaNuevoUnidad.setTemas(mapaNuevoUnidad.getTemas().replaceAll(" ","&nbsp;").replaceAll("\n","<br>").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;").replaceAll("Ñ","&Ntilde;").replaceAll("Á","&Aacute;").replaceAll("É","&Eacute;").replaceAll("Í","&Iacute;").replaceAll("Ó","&Oacute;").replaceAll("Ú","&Uacute;"));
			//System.out.println(mapaNuevoUnidad.getNombre().indexOf(" "));
%>
			<table style="width:850px" align="center">
				<tr>
					<td>
						<img class="button" title="Editar" src="../../imagenes/editar.gif" title="Editar" onclick="editarUnidad('<%=uid %>');" />
						<img class="button" title="Eliminar" src="../../imagenes/no.png" title="Eliminar" onclick="borrarUnidad('<%=uid %>');" />
					</td>
				</tr>
			</table>
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
					<td valign="top" width="100px" id="tiempo<%=uid %>" style="border-right: solid 1px black;"><%=mapaNuevoUnidad.getTiempo() %></td>
					<td valign="top" width="120px" style="border-right: solid 1px black;">
						<table style="width:100%">
							<tr>
								<td colspan="2" id="nombre<%=uid %>"><%=mapaNuevoUnidad.getNombre().replaceAll("\n","<br>").replaceAll(" ","&nbsp;").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;").replaceAll("Ñ","&Ntilde;").replaceAll("Á","&Aacute;").replaceAll("É","&Eacute;").replaceAll("Í","&Iacute;").replaceAll("Ó","&Oacute;").replaceAll("Ú","&Uacute;") %></td>
							</tr>
							<tr>
								<td colspan="2">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="2"><b>PRODUCTOS DE APRENDIZAJE:</b></td>
							</tr>
<%
		for(int j = 0; j < listActividades.size(); j++){
			mapaNuevoActividad = (MapaNuevoActividad) listActividades.get(j);
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
								<td id="objetivo<%=uid %>-<%=mapaNuevoActividad.getActividadId() %>" onclick="editarObjetivo('<%=uid %>', '<%=mapaNuevoActividad.getActividadId() %>');" class="button"><%=mapaNuevoActividad.getObjetivo().replaceAll("\n","<br>").replaceAll(" ","&nbsp;").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;").replaceAll("Ñ","&Ntilde;").replaceAll("Á","&Aacute;").replaceAll("É","&Eacute;").replaceAll("Í","&Iacute;").replaceAll("Ó","&Oacute;").replaceAll("Ú","&Uacute;") %></td>
							</tr>
<%
		}
%>
							<tr>
								<td colspan="2"><input type="button" class="button" value="Agregar" id="btnAgregarObjetivo<%=uid %>" onclick="nuevoObjetivo('<%=uid %>');" /></td>
							</tr>
						</table>
					</td>
					<td valign="top" id="temas<%=uid %>" style="border-right: solid 1px black;"><%=mapaNuevoUnidad.getTemas() %></td>
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
								<td id="actividad<%=uid %>-<%=mapaNuevoActividad.getActividadId() %>" onclick="editarActividad('<%=uid %>', '<%=mapaNuevoActividad.getActividadId() %>');" class="button"><%=mapaNuevoActividad.getDescripcion().replaceAll("\n","<br>").replaceAll(" ","&nbsp;").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;").replaceAll("Ñ","&Ntilde;").replaceAll("Á","&Aacute;").replaceAll("É","&Eacute;").replaceAll("Í","&Iacute;").replaceAll("Ó","&Oacute;").replaceAll("Ú","&Uacute;") %></td>
							</tr>
							<tr>
								<td colspan="2">Criterio de desempe&ntilde;o</td>
							</tr>
							<tr>
								<td width="6px" valign="top">-</td>
								<td id="criterio<%=uid %>-<%=mapaNuevoActividad.getActividadId() %>" onclick="editarCriterio('<%=uid %>', '<%=mapaNuevoActividad.getActividadId() %>');" class="button"><%=mapaNuevoActividad.getCriterio().replaceAll("\n","<br>").replaceAll(" ","&nbsp;").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;").replaceAll("Ñ","&Ntilde;").replaceAll("Á","&Aacute;").replaceAll("É","&Eacute;").replaceAll("Í","&Iacute;").replaceAll("Ó","&Oacute;").replaceAll("Ú","&Uacute;") %></td>
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
								<td colspan="2"><b><%=tipo.equals("1")?"Libros y revistas:":"Enlaces electr&oacute;nicos:" %></b></td>
							</tr>
<%
		}
%>
							<tr>
								<td width="6px" valign="top">-</td>
								<td><%=mapaNuevoBibliografia.getReferencia().replaceAll("\n","<br>").replaceAll(" ","&nbsp;").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;").replaceAll("Ñ","&Ntilde;").replaceAll("Á","&Aacute;").replaceAll("É","&Eacute;").replaceAll("Í","&Iacute;").replaceAll("Ó","&Oacute;").replaceAll("Ú","&Uacute;") %></td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td id="bibliografia<%=uid %>-<%=mapaNuevoBiblioUnidad.getBibliografiaId() %>-<%=mapaNuevoBiblioUnidad.getId() %>" onclick="editarBibliografia('<%=uid %>', '<%=mapaNuevoBiblioUnidad.getBibliografiaId() %>', '<%=mapaNuevoBiblioUnidad.getId() %>');" class="button"><%=mapaNuevoBiblioUnidad.getEspecificacion().replaceAll("\n","<br>").replaceAll(" ","&nbsp;").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;").replaceAll("Ñ","&Ntilde;").replaceAll("Á","&Aacute;").replaceAll("É","&Eacute;").replaceAll("Í","&Iacute;").replaceAll("Ó","&Oacute;").replaceAll("Ú","&Uacute;") %></td>
							</tr>
<%
	}
%>
							<tr>
								<td colspan="2">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="2"><input type="button" class="button" value="Agregar" id="btnAgregarBibliografia<%=uid %>" onclick="nuevaBibliografia('<%=uid %>');" /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<br>
<%
		}break;
		case 8:{//Modificar Accion Docente de una unidad
			String unidadId = request.getParameter("unidadId");
			String accionDocente = request.getParameter("accionDocente");
			mapaNuevoUnidad.mapeaRegId(conEnoc, cursoId, unidadId);
			mapaNuevoUnidad.setAccionDocente(accionDocente);
			if(mapaNuevoUnidadU.updateReg(conEnoc, mapaNuevoUnidad)){
%>
				$("accionDocenteDatos<%=unidadId %>").innerHTML = toHTML($("accionDocente").value);
				$("editaAccionDocente").remove();
				muestraMensaje('La unidad fue modificada correctamente','yellow');
<%
			}else{
%>
				muestraMensaje('Ocurrio un error al modificar la Carga Docente.<br>Intentelo de nuevo','red');
<%
			}
		}break;
		case 9:{//Guardar nuevo Objetivo
			String unidadId = request.getParameter("unidadId");
			String objetivo = request.getParameter("objetivo");
			mapaNuevoActividad.setCursoId(cursoId);
			mapaNuevoActividad.setUnidadId(unidadId);
			mapaNuevoActividad.setActividadId(mapaNuevoActividadU.maximoReg(conEnoc, cursoId, unidadId));
			mapaNuevoActividad.setObjetivo(objetivo);
			mapaNuevoActividad.setTipo(request.getParameter("tipo"));
			if(mapaNuevoActividadU.insertReg(conEnoc, mapaNuevoActividad)){
%>
				muestraMensaje('El Objetivo fue agregado correctamente','yellow');
				recargarUnidad('<%=unidadId %>');
				cancelarNuevoObjetivo();
<%
			}else{
%>
				muestraMensaje('Ocurrio un error al guardar el Objetivo.<br>Intentelo de nuevo','red');
<%
			}
		}break;
		case 10:{//Modificar Objetivo
			String unidadId = request.getParameter("unidadId");
			String actividadId = request.getParameter("actividadId");
			String objetivo = request.getParameter("objetivo");
			
			mapaNuevoActividad.mapeaRegId(conEnoc, cursoId, unidadId, actividadId);
			mapaNuevoActividad.setObjetivo(objetivo);
			if(mapaNuevoActividadU.updateReg(conEnoc, mapaNuevoActividad)){
%>
				muestraMensaje('El Objetivo fue modificado correctamente','yellow');
				$("objetivo<%=unidadId %>-<%=actividadId %>").innerHTML = toHTML($("objetivo").value);
				cancelarEditarObjetivo();
<%
			}else{
%>
				muestraMensaje('Ocurrio un error al modificar el Objetivo.<br>Intentelo de nuevo','red');
<%
			}
		}break;
		case 11:{//Modificar Actividad
			String unidadId = request.getParameter("unidadId");
			String actividadId = request.getParameter("actividadId");
			String descripcion = request.getParameter("descripcion");
			mapaNuevoActividad.mapeaRegId(conEnoc, cursoId, unidadId, actividadId);
			mapaNuevoActividad.setDescripcion(descripcion);
			if(mapaNuevoActividadU.updateReg(conEnoc, mapaNuevoActividad)){
%>
				muestraMensaje('La Actividad fue modificada correctamente','yellow');
				$("actividad<%=unidadId %>-<%=actividadId %>").innerHTML = toHTML($("actividad").value);
				cancelarEditarActividad();
<%
			}else{
%>
				muestraMensaje('Ocurrio un error al modificar la Actividad.<br>Intentelo de nuevo','red');
<%
			}
		}break;
		case 12:{//Borrar Objetivo y Actividad
			String unidadId = request.getParameter("unidadId");
			String actividadId = request.getParameter("actividadId");
			mapaNuevoActividad.mapeaRegId(conEnoc, cursoId, unidadId, actividadId);
			if(mapaNuevoActividadU.deleteReg(conEnoc, cursoId, unidadId, actividadId)){
%>
				muestraMensaje('El Objetivo y su respectiva Activadad fueron borrados correctamente','yellow');
				recargarUnidad('<%=unidadId %>');
				cancelarEditarObjetivo();
<%
			}else{
%>
				muestraMensaje('Ocurrio un error al borar el Objetivo.<br>Intentelo de nuevo','red');
<%
			}
		}break;
		case 13:{//Guardar nueva Bibliografia
			String unidadId = request.getParameter("unidadId");
			String bibliografiaId = request.getParameter("bibliografiaId");
			String especificacion = request.getParameter("especificacion");
			
			mapaNuevoBiblioUnidad.setCursoId(cursoId);
			mapaNuevoBiblioUnidad.setUnidadId(unidadId);
			mapaNuevoBiblioUnidad.setBibliografiaId(bibliografiaId);
			mapaNuevoBiblioUnidad.setId(mapaNuevoBiblioUnidadU.maximoReg(conEnoc, cursoId, unidadId, bibliografiaId));
			mapaNuevoBiblioUnidad.setEspecificacion(especificacion);
			if(mapaNuevoBiblioUnidadU.insertReg(conEnoc, mapaNuevoBiblioUnidad)){
%>
				muestraMensaje('La Bibliografia fue agregada correctamente','yellow');
				recargarUnidad('<%=unidadId %>');
				cancelarNuevaBibliografia();
<%
			}else{
%>
				muestraMensaje('Ocurrio un error al guardar la Bibliografia.<br>Intentelo de nuevo','red');
<%
			}
		}break;
		case 14:{//Modificar Bibliografia
			String unidadId = request.getParameter("unidadId");
			String bibliografiaId = request.getParameter("bibliografiaId");
			String especificacion = request.getParameter("especificacion");
			String id = request.getParameter("id");
			mapaNuevoBiblioUnidad.mapeaRegId(conEnoc, cursoId, unidadId, bibliografiaId, id);
			mapaNuevoBiblioUnidad.setEspecificacion(especificacion);
			if(mapaNuevoBiblioUnidadU.updateReg(conEnoc, mapaNuevoBiblioUnidad)){
%>
				muestraMensaje('La Bibliografia fue modificada correctamente','yellow');
				$("bibliografia<%=unidadId %>-<%=bibliografiaId %>-<%=id %>").innerHTML = toHTML($("especificacion").value);
				cancelarEditarBibliografia();
<%
			}else{
%>
				muestraMensaje('Ocurrio un error al modificar la Bibliografia.<br>Intentelo de nuevo','red');
<%
			}
		}break;
		case 15:{//Borrar Bibliografia
			String unidadId = request.getParameter("unidadId");
			String bibliografiaId = request.getParameter("bibliografiaId");
			String id = request.getParameter("id");
			mapaNuevoBiblioUnidad.mapeaRegId(conEnoc, cursoId, unidadId, bibliografiaId, id);
			if(mapaNuevoBiblioUnidadU.deleteReg(conEnoc, cursoId, bibliografiaId)){
%>
				muestraMensaje('La Bibliografia fue borrada correctamente','yellow');
				recargarUnidad('<%=unidadId %>');
				cancelarEditarBibliografia();
<%
			}else{
%>
				muestraMensaje('Ocurrio un error al borrar la Bibliografia.<br>Intentelo de nuevo','red');
<%
			}
		}break;
		case 16:{//Recargar Bibliografia Total
			String tipo = "";
			String bibliografia = "";
			ArrayList<MapaNuevoBibliografia> listBibliografias = mapaNuevoBibliografiaU.getListCurso(conEnoc, cursoId, "ORDER BY TIPO, BIBLIOGRAFIA");
%>
			<tr>
				<td align="center" colspan="3"><b>BIBLIOGRAFIA TOTAL</b></td>
			</tr>
<%
			tipo = "";
			for(int j = 0; j < listBibliografias.size(); j++){
				mapaNuevoBibliografia = (MapaNuevoBibliografia) listBibliografias.get(j);
				if(!tipo.equals(mapaNuevoBibliografia.getTipo())){
					tipo = mapaNuevoBibliografia.getTipo();
%>
			<tr>
				<td colspan="3"><b><%=tipo.equals("1")?"Libros y revistas:":"Enlaces electr&oacute;nicos:" %></b></td>
			</tr>
<%
				}
%>
			<tr>
				<td width="35px" valign="top" style="border-right: solid 1px gray;">
					<img class="button" title="Editar" src="../../imagenes/editar.gif" title="Editar" onclick="editarBibliografiaGlobal('<%=mapaNuevoBibliografia.getBibliografiaId() %>');" />
					<img class="button" title="Eliminar" src="../../imagenes/no.png" title="Eliminar" onclick="borrarBibliografiaGlobal('<%=mapaNuevoBibliografia.getBibliografiaId() %>');" />
				</td>
				<td style="border-right: solid 1px gray;" width="100px"><%=mapaNuevoBibliografia.getReferencia().replaceAll("\n","<br>").replaceAll(" ","&nbsp;").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;").replaceAll("Ñ","&Ntilde;").replaceAll("Á","&Aacute;").replaceAll("É","&Eacute;").replaceAll("Í","&Iacute;").replaceAll("Ó","&Oacute;").replaceAll("Ú","&Uacute;") %></td>
				<td id="bibliografia<%=mapaNuevoBibliografia.getBibliografiaId() %>"><%=mapaNuevoBibliografia.getBibliografia().replaceAll("\n","<br>").replaceAll(" ","&nbsp;").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;").replaceAll("Ñ","&Ntilde;").replaceAll("Á","&Aacute;").replaceAll("É","&Eacute;").replaceAll("Í","&Iacute;").replaceAll("Ó","&Oacute;").replaceAll("Ú","&Uacute;") %></td>
			</tr>
<%
			}
%>
			<tr>
				<td colspan="3" align="center"><input type="button" class="btn btn-primary" id="btnAgregarBibliografiaGlobal" value="Agregar Bibliografia Global" onclick="nuevaBibliografiaGlobal('-');" /></td>
			</tr>
<%
			
		}break;
		case 17:{//Cierra materia (Pasa a revision por el coordinador)
			mapaNuevoCurso.mapeaRegId(conEnoc, cursoId, planId, versionId);
			mapaNuevoCurso.setEstado("2");
			if(mapaNuevoCursoU.updateReg(conEnoc, mapaNuevoCurso)){
				if ( aca.plan.MapaNuevoPlanUtil.getIdioma(conEnoc, planId).equals("E") ){
%>
				document.location = "verMateria?planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId %>";
<%
				}else{
%>
				document.location = "verMateriaI?planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId %>";
<%					
				}
			}else{
%>
				muestraMensaje('Ocurrio un error al cerrar la Materia.<br>Intentelo de nuevo','red');
<%
			}
		}break;
		case 18:{//Revisa el estado de la materia. Si ya la cerro el profesor cambia de pagina
			mapaNuevoCurso.mapeaRegId(conEnoc, cursoId, planId, versionId);
			String codigoPersonal = (String) session.getAttribute("codigoPersonal");
			
			if(!mapaNuevoCurso.getEstado().equals("1") && !aca.acceso.AccesoUtil.esCotejador(conEnoc, codigoPersonal) && !Boolean.parseBoolean(session.getAttribute("admin")+"")){//Si no es uno es que ya la cerro el profe
				if ( aca.plan.MapaNuevoPlanUtil.getIdioma(conEnoc, planId).equals("E") ){
%>					
					document.location = "verMateria?planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId %>";
<%				}else{ %>				
					document.location = "verMateriaI?planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId %>";
<%				}
			}
		}break;
		//Modifica el objetivo de la asignatura
		case 19:{
			String objetivo = request.getParameter("objetivoAsignatura");
			
			mapaNuevoCurso.mapeaRegId(conEnoc, cursoId, planId, versionId);
			mapaNuevoCurso.setDescripcion(objetivo);
			if(mapaNuevoCursoU.updateReg(conEnoc, mapaNuevoCurso)){
%>
				muestraMensaje('Se guard&oacute; correctamente el Objetivo de la Asignatura','yellow');
				$("objetivoAsignaturaTD").innerHTML = toHTML($("objetivoAsignatura").value);
				cerrarEditaObjetivoAsignatura();
<%
			}else{
%>
				muestraMensaje('Ocurrio un error al guardar el Objetivo de la Asignatura.<br>Intentelo de nuevo','red');
<%
			}
		}break;
		case 20:{//Modificar Criterio
			String unidadId = request.getParameter("unidadId");
			String actividadId = request.getParameter("actividadId");
			String criterio = request.getParameter("criterio");
			mapaNuevoActividad.mapeaRegId(conEnoc, cursoId, unidadId, actividadId);
			mapaNuevoActividad.setCriterio(criterio);
			if(mapaNuevoActividadU.updateReg(conEnoc, mapaNuevoActividad)){
%>
				muestraMensaje('El Criterio fue modificado correctamente','yellow');
				$("criterio<%=unidadId %>-<%=actividadId %>").innerHTML = toHTML($("criterio").value);
				cancelarEditarCriterio();
<%
			}else{
%>
				muestraMensaje('Ocurrio un error al modificar el Criterio.<br>Intentelo de nuevo','red');
<%
			}
		}break;
		case 21:{//Nueva Bibliografia para la Unidad
			String unidadId = request.getParameter("unidadId");
			String tipo = "";
			ArrayList<MapaNuevoBibliografia> listBibliografias = mapaNuevoBibliografiaU.getListCurso(conEnoc, cursoId, "ORDER BY TIPO, BIBLIOGRAFIA");
%>
			<table style="width:100%">
				<tr>
					<td align="center"><b>Elija una Bibliografia</b></td>
				</tr>
				<tr>
					<td style="border: solid 1px gray;">
						<div style="width: 390px; height: 100px; overflow: auto; background-color: #E0E0E0;">
							<table style="width:100%">
<%
			for(int i = 0; i < listBibliografias.size(); i++){
				mapaNuevoBibliografia = (MapaNuevoBibliografia) listBibliografias.get(i);
				if(!tipo.equals(mapaNuevoBibliografia.getTipo())){
					tipo = mapaNuevoBibliografia.getTipo();
%>
								<tr>
									<td class="title3" colspan="2"><b><%=tipo.equals("1")?"Libros y revistas:":"Enlaces electr&oacute;nicos:" %></b></td>
								</tr>
<%
				}
%>
								<tr class="button" onclick="elegirBibliografia('<%=mapaNuevoBibliografia.getBibliografiaId() %>');">
									<td width="100px"><%=mapaNuevoBibliografia.getReferencia().replaceAll("\n","<br>").replaceAll(" ","&nbsp;").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;").replaceAll("Ñ","&Ntilde;").replaceAll("Á","&Aacute;").replaceAll("É","&Eacute;").replaceAll("Í","&Iacute;").replaceAll("Ó","&Oacute;").replaceAll("Ú","&Uacute;") %></td>
									<td id="biblio<%=mapaNuevoBibliografia.getBibliografiaId() %>">
										<%=mapaNuevoBibliografia.getBibliografia().replaceAll("\n","<br>").replaceAll(" ","&nbsp;").replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;").replaceAll("Ñ","&Ntilde;").replaceAll("Á","&Aacute;").replaceAll("É","&Eacute;").replaceAll("Í","&Iacute;").replaceAll("Ó","&Oacute;").replaceAll("Ú","&Uacute;") %>
									</td>
								</tr>
<%
			}
			if(listBibliografias.size() == 0){
%>
								<tr>
									<td align="center"><b>No hay ninguna bibliografia</b></td>
								</tr>
<%
			}
%>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td align="center"><input type="button" class="button" value="Agregar Bibliografia Global" onclick="nuevaBibliografiaGlobal('<%=unidadId %>');" /></td>
				</tr>
				<tr>
					<td><b>Especificaci&oacute;n (Ejemplo: Cap&iacute;tulo 3 y p&aacute;ginas 3 - 15)</b></td>
				</tr>
				<tr>
					<td><textarea id="especificacion" onkeyup="checkSize(this, event, 200);" class="200" cols="20" rows="4"></textarea></td>
				</tr>
				<tr>
					<td align="center">
						<input type="button" class="button" id="btnGuardar" value="Guardar" onclick="guardarBibliografia('<%=unidadId %>');" />
						<input type="button" class="button" value="Cancelar" onclick="cancelarNuevaBibliografia();" />
					</td>
				</tr>
			</table>
<%
		}break;
		case 22:{//Guardar Bibliografia global
			String unidadId = request.getParameter("unidadId");
			mapaNuevoBibliografia.setCursoId(cursoId);
			mapaNuevoBibliografia.setBibliografiaId(mapaNuevoBibliografiaU.maximoReg(conEnoc, cursoId));
			mapaNuevoBibliografia.setBibliografia(request.getParameter("bibliografia"));
			mapaNuevoBibliografia.setReferencia(request.getParameter("referencia"));
			mapaNuevoBibliografia.setTipo(request.getParameter("tipo"));
			
			if(mapaNuevoBibliografiaU.insertReg(conEnoc, mapaNuevoBibliografia)){
%>
				muestraMensaje('La Bibliografia fue guardada correctamente','yellow');
<%
				if(unidadId.equals("-")){
%>
					cancelarEditarBibliografiaGlobal();
<%
				}else{
%>
					nuevaBibliografia('<%=request.getParameter("unidadId") %>');
<%
				}
%>
				recargarBibliografiaTotal();
<%
			}else{
%>
				muestraMensaje('Ocurrio un error al guardar la Bibliografia.<br>Intentelo de nuevo','red');
<%
			}
		}break;
		case 23:{//Editar Bibliografia Global
			String bibliografiaId = request.getParameter("bibliografiaId");
			mapaNuevoBibliografia.mapeaRegId(conEnoc, cursoId, bibliografiaId);
%>
			<table>
				<tr title="Bibliografia completa">
					<td><b>Bibliografia</b></td>
					<td><textarea id="bibliografia" onkeyup="checkSize(this, event, 200);" class="200" cols="80"><%=mapaNuevoBibliografia.getBibliografia().replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;").replaceAll("Ñ","&Ntilde;").replaceAll("Á","&Aacute;").replaceAll("É","&Eacute;").replaceAll("Í","&Iacute;").replaceAll("Ó","&Oacute;").replaceAll("Ú","&Uacute;") %></textarea></td>
				</tr>
				<tr title="Abreviacion con la que se citar&aacute; la bibliografia (Ej. [perez, 2007])">
					<td><b>Autor y Fecha</b></td>
					<td><input type="text" class="text" id="referencia" size="40" maxlength="190" value="<%=mapaNuevoBibliografia.getReferencia().replaceAll("ñ","&ntilde;").replaceAll("á","&aacute;").replaceAll("é","&eacute;").replaceAll("í","&iacute;").replaceAll("ó","&oacute;").replaceAll("ú","&uacute;").replaceAll("Ñ","&Ntilde;").replaceAll("Á","&Aacute;").replaceAll("É","&Eacute;").replaceAll("Í","&Iacute;").replaceAll("Ó","&Oacute;").replaceAll("Ú","&Uacute;") %>" /></td>
				</tr>
				<tr>
				<tr>
					<td align="center" colspan="2">
						<input type="button" class="button" value="Guardar" onclick="modificarBibliografiaGlobal('<%=bibliografiaId %>');" />
						<input type="button" class="button" value="Cancelar" onclick="cancelarEditarBibliografiaGlobal();" />
						<input type="button" class="button" value="Borrar" onclick="borrarBibliografiaGlobal('<%=bibliografiaId %>');" />
					</td>
				</tr>
			</table>
<%
		}break;
		case 24:{//Modificar Bibliografia Global
			String bibliografiaId = request.getParameter("bibliografiaId");
			String bibliografia = request.getParameter("bibliografia");
			String referencia = request.getParameter("referencia");
			
			mapaNuevoBibliografia.mapeaRegId(conEnoc, cursoId, bibliografiaId);
			mapaNuevoBibliografia.setBibliografia(bibliografia);
			mapaNuevoBibliografia.setReferencia(referencia);
			if(mapaNuevoBibliografiaU.updateReg(conEnoc, mapaNuevoBibliografia)){
%>
				muestraMensaje('La Bibliografia fue modificada correctamente','yellow');
				setTimeout("location.href = location.href;", 500);
<%
			}else{
%>
				muestraMensaje('Ocurrio un error al modificar la Bibliografia.<br>Intentelo de nuevo','red');
<%
			}
		}break;
		case 25:{//Borrar Bibliografia Global
			conEnoc.setAutoCommit(false);
			String bibliografiaId = request.getParameter("bibliografiaId");
			
			mapaNuevoBibliografia.mapeaRegId(conEnoc, cursoId, bibliografiaId);
			if(mapaNuevoBibliografiaU.deleteReg(conEnoc, cursoId, bibliografiaId)){
				mapaNuevoBiblioUnidadU.deleteReg(conEnoc, cursoId, bibliografiaId);
				conEnoc.commit();
%>
				muestraMensaje('La Bibliografia fue borrada correctamente','yellow');
				setTimeout("location.href = location.href;", 500);
<%
			}else{
				conEnoc.rollback();
%>
				muestraMensaje('Ocurrio un error al borrar la Bibliografia.<br>Verifique que no este ligada con ninguna unidad','red');
<%
			}
			conEnoc.setAutoCommit(true);
		}break;
		//Modifica el objetivo de la asignatura
		case 26:{
			String proyecto = request.getParameter("proyectoAsignatura");
			
			mapaNuevoCurso.mapeaRegId(conEnoc, cursoId, planId, versionId);
			mapaNuevoCurso.setProyecto(proyecto);
			if(mapaNuevoCursoU.updateReg(conEnoc, mapaNuevoCurso)){
		%>
				muestraMensaje('Se guard&oacute; correctamente el Proyecto integrador de la Asignatura','yellow');
				$("proyectoAsignaturaTD").innerHTML = toHTML($("proyectoAsignatura").value);
				cerrarEditaProyectoAsignatura();
		<%
			}else{
		%>
				muestraMensaje('Ocurrio un error al guardar el Proyecto integrador de la Asignatura.<br>Intentelo de nuevo','red');
		<%
			}
		}break;
	}
%> 
<%@ include file= "../../cierra_enoc2.jsf" %>