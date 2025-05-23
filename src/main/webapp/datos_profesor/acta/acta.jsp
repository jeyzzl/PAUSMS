<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CargaGrupoImp" scope="page" class="aca.carga.CargaGrupoImp"/>
<jsp:useBean id="CargaGrupoImpU" scope="page" class="aca.carga.CargaGrupoImpUtil"/>
<jsp:useBean id="MapaCurso" scope="page" class="aca.plan.MapaCurso"/>
<jsp:useBean id="MapaCursoU" scope="page" class="aca.plan.CursoUtil"/>
<jsp:useBean id="AlumnoU" scope="page" class="aca.alumno.AlumUtil"/>

<jsp:useBean id="cargaGrupoEvaluacion" scope="page" class="aca.carga.CargaGrupoEvaluacion"/>
<jsp:useBean id="evaluacionU" scope="page" class="aca.carga.CargaGrupoEvaluacionUtil"/>
<jsp:useBean id="grupoU" scope="page" class="aca.carga.CargaGrupoUtil"/>
<jsp:useBean id="estrategiaU" scope="page" class="aca.catalogo.EstrategiaUtil"/>
<jsp:useBean id="krdxEvaluacionUtil" scope="page" class="aca.kardex.EvaluacionUtil"/>
<jsp:useBean id="kardex" scope="page" class="aca.kardex.KrdxCursoAct"/>
<jsp:useBean id="tipoCalU" scope="page" class="aca.catalogo.TipoCalUtil"/>
<html>
<head>
	<link href="../../print.css" rel="stylesheet" type="text/css" media="print">
</head>
<style type="text/css">
<!--
.style2 {
	font-size: 11px;
	font-family: Arial, Helvetica, sans-serif;
}
.style3 {
	font-family: Arial, Helvetica, sans-serif;
	font-weight: bold;
	font-size: 12px;
}
.style4 {
	font-family: Arial, Helvetica, sans-serif;
	font-weight: bold;
	font-size: 20px;
}
TH  {
	border-width : 1px;
	border-color : #000000;
	border-style : solid;
	padding : 1px;
	font-family: Arial, Helvetica, sans-serif;
	font-weight: bold;
	font-size: 12px;
	color: white;
}
TD  {
	border-color : #000000;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
}

-->
</style>
<body bgcolor="#FFFFFF">
<%
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("##0.0;-##0.0");

	String institucion 			= (String)session.getAttribute("institucion");
	String grupoId				= request.getParameter("GrupoId");
	float notaAprobatoria 		= 70;
	
	// Datos del grupo
	CargaGrupoImp = CargaGrupoImpU.mapeaRegId(conEnoc, grupoId);
	MapaCurso = MapaCursoU.mapeaRegId(conEnoc, CargaGrupoImp.getCursoId());
	
	// Lista de alumnos
	ArrayList<aca.alumno.AlumPersonal> lisAlumnos 		= AlumnoU.getListaActas(conEnoc, grupoId, "ORDER BY NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO");
%>
<table  cellspacing="2"  align="left">
	<tr>
	  <td width="120" align="left" valign="top">
			<a href="javascript:window.print()"><img src="../../imagenes/logo.jpg"  width="98" alt="Imprimir"></a><br>			<br>
			<span class="style2"><br>
			<%=institucion%><br><br>
			Apdo. 16-5 C.P. 67530<br>
			Montemorelos, NL,<br>
			M&eacute;xico<br>
			<br>
			<strong><spring:message code="aca.Tel"/>efonos:</strong><br>
			Directo(826) 263-0908<br>
			Conmutador 263-0900<br>
			Ext. 119,120,121 <br>
			Fax (826) 263-0979<br>
			<br>
			<br>
			<b><spring:message code="aca.Creada"/></b> por el Gobierno<br>
			del estado de Nuevo<br>
			León, México, mediante<br>
			Resolución Oficial<br>
			publicada el 5 de mayo<br>
			de 1973.<br>
			<br>
			<b>Clave de la Institución</b><br>
			ante la SEP y Dirección<br>
			General de Estadística<br>
		19MSU1017U</span>		</td>
		<td width="100%" valign="top" bordercolor="0" >	  
			<table style="width:100%" cellpadding="2"  border="2" bordercolor="#777777">
			  <tr>
				<td width="50%" align="center">
					<span class="style3">Documento Oficial ( Ordinario - Extraordinario )</span>
				</td>
				<td width="50%" align="right"><span class="style4">&nbsp;</span></td>
			  </tr>
			</table>
			<table style="width:100%" cellpadding="2" >
			  <tr>
				<td width="50%" align="center" >				<table style="width:100%" cellpadding="2" cellspacing="2">
                  <tr>
                    <th colspan="2" align="left">Facultad de Teología</th>
                  </tr>
                  <tr>
                    <th colspan="2" align="left">Licenciatura en Teología</th>
                  </tr>
                  <tr>
                    <th width="22%" align="left">Curso:</th>
                    <td width="78%"><%=MapaCurso.getNombreCurso()%></td>
                  </tr>
                  <tr>
                    <th align="left">Semestre:</th>
                    <td><%=MapaCurso.getCiclo()%></td>
                  </tr>
                  <tr>
                    <th align="left">Créditos:</th>
                    <td><%=MapaCurso.getCreditos()%></td>
                  </tr>
                  <tr>
<%	
	String sHp = MapaCurso.getHp();
	String sHt = MapaCurso.getHt();
	if (sHp.equals("")) sHp ="0";
	int hp = Integer.parseInt(sHp);
	if (sHt.equals("")) sHt ="0";
	int ht = Integer.parseInt(sHt);
	String maestro			= aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, CargaGrupoImp.getMaestro(), "NOMBRE");
%>
                    <th align="left">Horas:</th>
                    <td><%=ht+hp%></td>
                  </tr>
			  
                  <tr>
                    <th align="left">Profesor:</th>
                    <td><%=maestro%></td>
                  </tr>
                  <tr>
                    <td colspan="2">
						<table style="width:100%"  cellspacing="2" >
							<tr>
								<th width="7%"><font size="1"><spring:message code="aca.Numero"/></font></th>
								<th width="16%"><font size="1"><spring:message code="aca.Fecha"/></font></th>
								<th width="63%"><font size="1"><spring:message code="aca.Nombre"/> Evaluación</font></th>
								<th width="14%"><font size="1">Ponderado</font></th>
							</tr>
							<tr>
							  <td align="center"><font size="1"><%=1%>.-</font></td>
							  <td align="center"><font size="1"><%=CargaGrupoImp.getFecha()%></font></td>
							  <td><font size="1">Evaluación sumativa/promedio final</font></td>
							  <td align="right"><font size="1">100%</font></td>
							</tr>					
							<tr>
							  <th colspan="4" align="right"><font size="1">Suma: 100%</font></th>
						    </tr>
						</table>
					</td>
                  </tr>
                </table>
				</td>
				<td width="50%" align="left" valign="top">
					<table style="width:100%" height="100%" cellpadding="2"  border="2" bordercolor="#777777">
						<tr><td width="100%" align="center">
							<b>Observaciones:</b> La nota mínima aprobatoria es <%=Float.parseFloat(MapaCurso.getNotaAprobatoria())/10%>.<br>  
							Montemorelos, Nuevo León<br>
							<%=CargaGrupoImp.getFecha()%><br><br>
							_______________________________________<br>
							Coordinador: <b><%=aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, "9800800","NOMBRE") %></b><br><br>
							_______________________________________<br>
							Profesor: <b><%=maestro%></b><br>

						</td></tr>
					</table>
				</td>
			  </tr>
			  <tr>
			  	<td colspan="2">
					<table style="width:100%"  cellspacing="2" >
					  <tr>
						<th><font size="1"><spring:message code="aca.Numero"/></font></th>
						<th><font size="1"><spring:message code="aca.Plan"/></font></th>
						<th><font size="1">Código</font></th>
						<th><font size="1"><spring:message code="aca.NombreDelAlumno"/></font></th>
						<th width="25"><font size="1"><%=1%></font></th>
						<th><font size="1">Puntos</font></th>
						<th><font size="1"><spring:message code="aca.Total"/></font></th>
						<th><font size="1">P.Ex.</font></th>
						<th><font size="1">Prom.</font></th>
						<th><font size="1">Extra.</font></th>
						<th><font size="1">Estado.</font></th>
					  </tr>
<%	
	for (aca.alumno.AlumPersonal alumno :lisAlumnos){
		String nota 	= aca.vista.AlumnoCursoUtil.getNotaCurso(conEnoc, alumno.getCodigoPersonal(), CargaGrupoImp.getCursoId(), "1", " ORDER BY F_EVALUCION DESC");		
%>
					  <tr>
						<td align="center" height="15"><font size="1"><%=1%></font></td>
						<td align="center"><font size="1">TEOL2011</font></td>
						<td align="center"><font size="1"><%=alumno.getCodigoPersonal()%></font></td>	
						<td align="left"><font size="1"><%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%></font></td>
						<td width="25" align="right"><font size="1"><% out.print(getFormato.format(Double.valueOf(nota)/10));%></font></td>
						<td align="center"><font size="1"><b><%=nota%></b></font></td>
						<td align="center"><font size="1"><b><%=nota%></b></font></td>
						<td align="center"><font size="1"><b>0</b></font></td>
						<td align="center"><font size="1"><b><%=getFormato.format(Double.valueOf(nota)/10)%></b></font></td>
						<td align="center"><font size="1"><b>-</b></font></td>
						<td align="center"><font size="1"><%=tipoCalU.getNombre(conEnoc, "1","1")%></font></td>
					 </tr>
<%
	} //fin de ciclo lisAlumnos
%>
					<tr>
						<th colspan="30"><font size="2">Fin de Listado de Alumnos &nbsp; &nbsp; &nbsp; &nbsp;   Nº: <%=CargaGrupoImp.getCursoCargaId()%></font></th>
					  </tr>
				  </table>
				</td>
			  </tr>
			</table>
			
		</td>
	</tr>
</table>
<%@ include file= "../../cierra_enoc.jsp" %>