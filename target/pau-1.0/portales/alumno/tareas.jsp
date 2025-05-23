<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="evaluacionUtil" scope="page" class="aca.carga.CargaGrupoEvaluacionUtil"/>
<jsp:useBean id="cge" scope="page" class="aca.carga.CargaGrupoEvaluacion"/>
<jsp:useBean id="fecha" scope="page" class="aca.util.Fecha"/>
<jsp:useBean id="estrategia" scope="page" class="aca.catalogo.CatEstrategia"/>
<jsp:useBean id="EstrategiaU" scope="page" class="aca.catalogo.EstrategiaUtil"/>
<jsp:useBean id="alumnoCurso" scope="page" class="aca.vista.AlumnoCurso"/>
<jsp:useBean id="AlumnoCursoU" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<html>
<head>
<%
	String colorPortal = (String)session.getAttribute("colorPortal");
	if(colorPortal==null)colorPortal="";
%>

<%-- <jsp:include page="../menu.jsp" /> --%>
<%@ include file= "menu.jsp" %>
<link href="css/portalAlumno.css" rel="STYLESHEET" type="text/css">
</head>
<%
	String fechaInicio	= request.getParameter("fechaInicio");
	String fechaIA		="";
	String fechaIB		="";
	ArrayList fechas = null;
	if (fechaInicio==null){
		fechas = fecha.getSemanaActual();
		fechaInicio = (String) fechas.get(0);
	}else{
		fechas = fecha.getSemana(fechaInicio);
	}
	String fechaFin	= (String)fechas.get(6);
	fechaIA = fecha.getSemanaAnterior(fechaInicio);
	fechaIB = fecha.getSemanaSiguiente(fechaInicio);
%>

<body onload='muestraPagina();'>
	<script>
		parent.tabs.document.body.style.backgroundColor=parent.contenidoP.document.bgColor;
	</script>

	<table style="width:80%" height="100%"  cellpadding="10" align="left">
	<tr valign="top">
		<td>
			<table style="width:100%"   align="center" >
				<tr><td align="right"><a href='tareas?fechaInicio=<%=fecha.getFecha("1")%>'><spring:message code="datosAlumno.portal.Nota5"/>&nbsp;&nbsp;&nbsp;</a></td></tr>
				<tr><td>
						<table style="width:100%"   class="table table-condensed table-nohover">
							<tr valign="top">
								<th colspan="2" style="text-align:center;">
									<a href='tareas?fechaInicio=<%=fechaIA%>'><img src='img/left.gif'  align="absmiddle"align="absmiddle"></a>
									<b><%=fecha.getDia(fechaInicio)%> de <%=fecha.getMesNombre(fechaInicio)%> de <%=fecha.getYear(fechaInicio)%> - <%=fecha.getDia(fechaFin)%> de <%=fecha.getMesNombre(fechaFin)%> de <%=fecha.getYear(fechaFin)%>:</b>
									<a href='tareas?fechaInicio=<%=fechaIB%>'><img src='img/right.gif'  align="absmiddle"></a>
								</th>
							</tr>
							
							<tr>
								<td colspan='2'>
									<table style="width:100%; margin:0 auto">
<%										String color="";
										for (int i=0;i<7;i++){
										if(i%2==1)color="bgcolor='#f7f7ff'";
										else color="";
										ArrayList tareas = evaluacionUtil.getTareas(conEnoc,(String)session.getAttribute("codigoAlumno"),(String)session.getAttribute("cargaId"),(String)fechas.get(i));
%>										<tr <%=color%> height='55' valign='center'>
											<td width='70' align='center'>
												<b><%=fecha.getNombreDia((String)fechas.get(i))%></b><br>
												<%=fecha.getDia((String)fechas.get(i))%>/<%=fecha.getMes((String)fechas.get(i))%>
											</td>
											<td>
<%												if (tareas.size()>0){
													for (int j=0;j<tareas.size();j++){
														cge = (aca.carga.CargaGrupoEvaluacion) tareas.get(j);
														estrategia = EstrategiaU.mapeaRegId(conEnoc,cge.getEstrategiaId());
														alumnoCurso = AlumnoCursoU.mapeaRegId(conEnoc,(String)session.getAttribute("codigoAlumno"),cge.getCursoCargaId());
%>														
														<font color='#000000'><b><%=alumnoCurso.getNombreCurso()%></b></font> -
														<%=estrategia.getNombreEstrategia()%> - <%=cge.getNombreEvaluacion()%> - <font color='#000044'><b><%=cge.getValor()%> <%=cge.getTipo()%></b></font>
														<br>
<%													}
												}else{
%>													<spring:message code="datosAlumno.portal.Nota1"/>
<%												}
%>											</td>
										</tr>
<%									}
%>									</table>
								</td>
							</tr>
						</table>
				</td></tr>
			</table>
		</td>
	</tr>
</table>

<script>
	$('.nav-tabs').find('.tareas').addClass('active');
</script>
<%@ include file= "../../cierra_enoc.jsp" %>