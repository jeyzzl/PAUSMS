<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "../../conectadbp.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.pg.archivos.ArchivosProfesor"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>

<jsp:useBean id="evaluacion" scope="page" class="aca.kardex.KrdxAlumnoEval"/>
<jsp:useBean id="evaluacionUtil" scope="page" class="aca.kardex.EvaluacionUtil"/>
<jsp:useBean id="cargaGrupoEvaluacion" scope="page" class="aca.carga.CargaGrupoEvaluacion"/>
<jsp:useBean id="estrategia" scope="page" class="aca.catalogo.EstrategiaUtil"/>
<jsp:useBean id="GrupoActUtil" scope="page" class="aca.carga.CargaGrupoActividadUtil"/>
<jsp:useBean id="kardex" scope="page" class="aca.kardex.KrdxCursoAct"/>
<jsp:useBean id="curso" scope="page" class="aca.carga.CargaGrupoCursoUtil"/>
<jsp:useBean id="fecha" scope="page" class="aca.util.Fecha"/>


<%-- <jsp:include page="../menuPadre.jsp" /> --%>
<%@ include file= "../menuPadre.jsp" %>

<% 
	String matricula = (String) session.getAttribute("codigoAlumno");
	String estado=request.getParameter("estado");
	String fechaEval	= "";
	
	GregorianCalendar fechaHoy	= new GregorianCalendar();
	GregorianCalendar fechaTmp	= null;
	
	if(estado==null)estado="";
	ArrayList<aca.kardex.KrdxAlumnoEval> lisEvaluacion 		= evaluacionUtil.getListaSinNotas(conEnoc,matricula,request.getParameter("cursoCargaId"),"ORDER BY FECHA,3");	
	ArrayList<aca.carga.CargaGrupoActividad> lisActividad	= GrupoActUtil.getListCurso(conEnoc,request.getParameter("cursoCargaId"),"ORDER BY EVALUACION_ID,ACTIVIDAD_ID");
	
	String colorPortal 		= (String)session.getAttribute("colorPortal");
	if(colorPortal==null) colorPortal="";
%>

<html><head>
<link href="css/portalAlumno.css" rel="STYLESHEET" type="text/css">
<style>
.P{border:solid 1px white;}
</style>
</head>
<body>
<form name="frmmaterias" action="materias">
<table style="width:100%; margin:0 auto;" cellpadding="10">
	<tr valign="top">
		<td align="center">
			<!--<a href="materias?CargaId=<%=request.getParameter("cargaId")%>"><b&lsaquo;&lsaquo; Regresar</b></a>  -->
				<table style="width:100%">
					<tr valign="top">
						<td colspan="3" style="text-align:center;">
							<h2>Detalle de materia</h2>
						</td>
					</tr>
					  <tr> 
						<td align="center" colspan="10">Materia: <b><%=request.getParameter("materia")%></b></td>						
					  </tr>
					  <tr> 
						<td align="center" colspan="10">Profesor: <b><%=request.getParameter("profesor")%></b></td>						
					  </tr>
					  <tr> 
						<td align="center" colspan="10">
						
						</td>
					  <tr> 
					  </tr>
					  <tr>
						<td colspan="4" align='center'>
<table><tr>
						<%	String estilo1="style='cursor:pointer;' onMouseOver=\"this.style.border='1px solid #7E4390'; this.style.backgroundColor='#DDBAF7';\" onMouseOut=\"this.style.backgroundColor='';this.style.border='1px solid white';\"";
							String link1="'transferir?id="+request.getParameter("cursoCargaId")+"&vista=paraAlumno&nomProfesor="+request.getParameter("profesor")+"&nomMateria="+request.getParameter("materia")+"';";
							String link2="'transferir?id="+request.getParameter("cursoCargaId")+"&vista=xMateria&nomProfesor="+request.getParameter("profesor")+"&nomMateria="+request.getParameter("materia")+"';";
						%>
							<td class="P" <%=estilo1%> onClick="document.location.href=<%=link1%>" title="Bajar archivos que envi&oacute; el profesor para todas las tareas">											
									<img align=absmiddle alt="Ver todos los archivos recibidos" src="/academico/imagenes/download2.gif"  hspace=1>
									<b>Archivos recibidos</b>
							</td>
							<td>
							<td class="P" <%=estilo1%> onClick="document.location.href=<%=link2%>" title="Archivos enviados para todas las tareas">
								<img align=absmiddle alt="Ver todos los archivos enviados" src="/academico/imagenes/upload.gif"  hspace=1>
								<b>Archivos enviados</b>
							</td>
							</tr>
						</table>						</td>						
					  </tr>
					  <tr>
					  	<td align="center">
							<table style="width:70%" class="table table-nobordered table-fullcondensed table-fontsmall">
								<tr>
									<th width="10%"><spring:message code="aca.Fecha"/></th>
									<th width="55%"><spring:message code="aca.Nombre"/></th>
									<th width="10%">Ponderado</th>
									<th width="5%"><spring:message code="aca.Nota"/></th>
								</tr>								
<%
								float totalFinal = 0f;
								float valorFinal = 0f;
								for (int i=0;i<lisEvaluacion.size();i++){
									evaluacion = (aca.kardex.KrdxAlumnoEval) lisEvaluacion.get(i);		
									cargaGrupoEvaluacion.mapeaRegId(conEnoc,request.getParameter("cursoCargaId"),evaluacion.getEvaluacionId());
									kardex.mapeaRegId(conEnoc,matricula,evaluacion.getCursoCargaId());
									String nota = evaluacion.getNota();
									if (nota == null) nota = "-"; 
									totalFinal += cargaGrupoEvaluacion.getTipo().equals("%")?((Float.parseFloat(evaluacion.getNota()==null?"0":evaluacion.getNota())*Float.parseFloat(cargaGrupoEvaluacion.getValor()))/100):Float.parseFloat(evaluacion.getNota()==null?"0":evaluacion.getNota());
									valorFinal += cargaGrupoEvaluacion.getTipo().equals("E")?0:Float.parseFloat(cargaGrupoEvaluacion.getValor());
%>								  <tr class="tr2">
									<td style="text-align: center;"><strong><%=cargaGrupoEvaluacion.getFecha()%></strong></td>
									<td>
<%
									String estadoCurso = curso.obtenEstado(conEnoc,cargaGrupoEvaluacion.getCursoCargaId());
									if(estadoCurso.equals("2")||estadoCurso.equals("1")){
										if(ArchivosProfesor.tieneArchivos(conn2, request.getParameter("cursoCargaId")+"-"+evaluacion.getEvaluacionId(), matricula)){
%>
										<a title="Bajar archivos que envi&oacute; el profesor para esta tarea" href='transferir?id=<%=cargaGrupoEvaluacion.getCursoCargaId()+"-"+cargaGrupoEvaluacion.getEvaluacionId()%>&vista=paraAlumnoxEstrategia&nomProfesor=<%=request.getParameter("profesor")%>&nomMateria=<%=request.getParameter("materia")%>'>
											<img alt="Ver archivos recibidos para esta estrategia" src="/academico/imagenes/download3.gif" >
										</a>
<%
										}else{
%>
										&nbsp;&nbsp;&nbsp;&nbsp;<!-- Este es para los espacios de la foto -->
<%
										}
										fechaEval = cargaGrupoEvaluacion.getFecha();
										
										fechaTmp = new GregorianCalendar(Integer.parseInt(fecha.getYear(fechaEval)), Integer.parseInt(fecha.getMes(fechaEval))-1, Integer.parseInt(fecha.getDia(fechaEval)), 23, 59, 59);
										if(fechaHoy.before(fechaTmp)){
%>
										<a title="Subir archivos para esta tarea" href='transferir?id=<%=cargaGrupoEvaluacion.getCursoCargaId()+"-"+cargaGrupoEvaluacion.getEvaluacionId()%>&vista=xEstrategia&nomProfesor=<%=request.getParameter("profesor")%>&nomMateria=<%=request.getParameter("materia")%>'>
											<img alt="Enviar Archivo" src="/academico/imagenes/upload2.gif" >
										</a>
										&nbsp;&nbsp;|&nbsp;&nbsp;<!-- Este es para los espacios despues de la foto -->
<%
										}else{
%>
										&nbsp;&nbsp;&nbsp;&nbsp;<!-- Este es para los espacios de la foto -->
										&nbsp;&nbsp;|&nbsp;&nbsp;<!-- Este es para los espacios despues de la foto -->
<%
										}
									}
%>
										<strong><%=estrategia.getNombre(conEnoc,cargaGrupoEvaluacion.getEstrategiaId())%> - <%=cargaGrupoEvaluacion.getNombreEvaluacion()%></strong>
									</td>
									<td style="text-align: center;"><strong><%=cargaGrupoEvaluacion.getValor()%><%=cargaGrupoEvaluacion.getTipo()%></strong></td>
									<td style="text-align: center;"><strong><%=nota%></strong></td>
								  </tr>
<%								  
								  for (int j=0;j<lisActividad.size();j++){
									  aca.carga.CargaGrupoActividad act = (aca.carga.CargaGrupoActividad) lisActividad.get(j);
									  if (act.getEvaluacionId().equals(evaluacion.getEvaluacionId())){
%>
								  <tr class="tr2">
								    <td style="text-align: center;"><em><%= act.getFecha() %><em></td>
								    <td>
								      <em>
								      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								      <%= act.getNombre() %>
								      <em>
								    </td>
								    <td style="text-align: center;"><em><%= act.getValor() %><em></td>
								    <td style="text-align: center;"><em><%= aca.kardex.KrdxAlumnoActiv.getNotaActividad(conEnoc,matricula,act.getActividadId()) %><em></td>
								  </tr>
<%										  
									  }
									  
								  }	// fin de for de actividades							
								}
								String pm = evaluacionUtil.getAlumnoMaximosPuntos(conEnoc,request.getParameter("cursoCargaId"),matricula);
								if(pm == null)
									pm = "0";
								
							  	String pe = evaluacionUtil.getAlumnoPuntosExtras(conEnoc,request.getParameter("cursoCargaId"),matricula);
							  	String alumnoPuntos = evaluacionUtil.getAlumnoPuntos(conEnoc,request.getParameter("cursoCargaId"),matricula);							  	
%>								  <tr class="ayuda PAL 003">
									<td>&nbsp;</td>
									<td style="text-align: right;" colspan="2"><b>Eficiencia:</b></td>
									<th style="text-align: center;"><b><%=evaluacionUtil.getAlumnoEficiencia(conEnoc,request.getParameter("cursoCargaId"),matricula)%></b></th>
								  </tr>
								  <%
								  	if (pm!=null){
								  %>
								  <tr class="ayuda PAL 003">
									<td colspan="2">&nbsp;</td>
									<td style="text-align: right;" colspan="2">
										<%=alumnoPuntos %>/<%=pm %>
										<% if (pe!=null){%>
										+ <%=pe%> extras
										<%}%>
									</td>
								  </tr>
								  <%}
%>
								  <tr class="ayuda PAL 004">
									<td>&nbsp;</td>
									<td  style="text-align:right;" colspan="2"><b>Puntos totales:</b></td>
									<th style="text-align: center;"><b><%=evaluacionUtil.getAlumnoPromedio(conEnoc,request.getParameter("cursoCargaId"),matricula)%></b></th>
								  </tr>
								  <tr class="ayuda PAL 004">
									<td colspan="2">&nbsp;</td>
									<td style="text-align: right;" colspan="2">
										<%=totalFinal %>/<%=valorFinal %>
										<% if (pe!=null){%>
										+ <%=pe%> extras
										<%}%>
									</td>
								  </tr>
<%
								  	if (!kardex.getNotaExtra().equals("0") && kardex.getNotaExtra()!=null){%>
								  <tr>
									<td colspan="2">&nbsp;</td>	
									<td style="text-align: center;"><b>Extra:</b></td>
									<th style="text-align: center;"><b><%=kardex.getNotaExtra()%></b></th>
								  </tr>								  
								  <%}%>
								  <tr class="ayuda PAL 003">
									<td>&nbsp;</td>
									<td style="text-align: right;" colspan="2"><b>Promedio final (escala 0-10): </b></td>
									<th style="text-align: center;"><b><%=Double.parseDouble(evaluacionUtil.getAlumnoEficiencia(conEnoc,request.getParameter("cursoCargaId"),matricula))/10%></b></th>
								  </tr>
								</table>
					  	</td>
					  </tr>					  
				</table>
		</td>
	</tr>
</table>
</form>
<table style="width:100%; margin:0 auto;"><tr><td background="../../imagenes/shadow.gif" height="4"></td></tr></table>


<script>
	$('.nav-tabs').find('.materias').addClass('active');
</script>

<%@ include file= "../../cierradbp.jsp" %>
<%@ include file= "../../cierra_enoc.jsp" %>