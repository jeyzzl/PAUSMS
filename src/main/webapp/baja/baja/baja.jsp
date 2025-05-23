<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.baja.BajaAlumpaso"%>
<%@page import="aca.baja.BajaAlumpasoUtil"%>
<%@page import="aca.internado.Alumno"%>
<%@page import="aca.alumno.AlumPersonal"%>
<%@page import="aca.carga.CargaBloque"%>
<%@page import="aca.alumno.AlumEstado"%>
<%@page import="aca.alumno.AlumPlan"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.carga.Carga"%>
<%@page import="aca.alumno.AlumAcademico"%>
<%@page import="aca.acceso.Acceso"%>
<%@page import="aca.kardex.KrdxAlumnoEval"%>
<%@page import="aca.baja.BajaCurso"%>

<jsp:useBean id="bajaPaso" scope="page" class="aca.baja.BajaPaso"/>
<jsp:useBean id="comAutorizacion" scope="page" class="aca.internado.ComAutorizacion"/>
<jsp:useBean id="bajaAlumno" scope="page" class="aca.baja.BajaAlumno"/>
<jsp:useBean id="bajaAlumnoU" scope="page" class="aca.baja.BajaAlumnoUtil"/>
<jsp:useBean id="alumU" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="bajaAlumpaso" scope="page" class="aca.baja.BajaAlumpaso"/>
<jsp:useBean id="bajaAlumpasoU" scope="page" class="aca.baja.BajaAlumpasoUtil"/>
<jsp:useBean id="alumPlan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="AlumPlanU" scope="page" class="aca.alumno.PlanUtil"/>
<jsp:useBean id="cursoU" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<jsp:useBean id="alumnoCurso" scope="page" class="aca.vista.AlumnoCurso"/>
<jsp:useBean id="bajaCurso" scope="page" class="aca.baja.BajaCurso"/>
<jsp:useBean id="krdxCursoAct" scope="page" class="aca.kardex.KrdxCursoAct"/>
<jsp:useBean id="mapaPlan" scope="page" class="aca.plan.MapaPlan"/>
<jsp:useBean id="MapaPlanU" scope="page" class="aca.plan.PlanUtil"/>
<jsp:useBean id="bajaCursoU" scope="page" class="aca.baja.BajaCursoUtil"/>
<jsp:useBean id="mapaCurso" scope="page" class="aca.plan.MapaCurso"/>
<jsp:useBean id="MapaCursoU" scope="page" class="aca.plan.CursoUtil"/>
<jsp:useBean id="cargaGrupo" scope="page" class="aca.carga.CargaGrupo"/>
<jsp:useBean id="CargaGrupoU" scope="page" class="aca.carga.CargaGrupoUtil"/>
<%
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 	= (String) session.getAttribute("codigoAlumno");
	String cargaId			= aca.alumno.EstadoUtil.getCarga(conEnoc, codigoAlumno);
	String bloqueId			= CargaBloque.getBloqueActual(conEnoc, cargaId);
	String accion			= request.getParameter("Accion");
	String carreraId		= aca.alumno.PlanUtil.getCarreraId(conEnoc, codigoAlumno);
	String institucion 		= (String)session.getAttribute("institucion");
	
	int i = 0, contador = 1;
	
	boolean guarda = false;
	
	bajaAlumnoU.mapeaRegSolicitado(conEnoc, codigoAlumno);
	
	if(bajaAlumno.getBajaId().equals("")){
%>
<head>
	<style type="text/css" media="all">
		<link href="../../academico.css" rel="STYLESHEET" type="text/css">
		body{
			background: none;
		}
	
		div#fondo1{
			position: absolute;
			z-index: 500;
			top: 90px;
			left: 320px; 
		}
	</style>
	<!-- link rel="stylesheet" type="text/css" media="print" href="print.css" -->
	<script type="text/javascript">
		function inicio(){
			var img = document.getElementById("fondo");
			var tab = document.getElementById("tabla");
			var theWidth, theHeight;
			if (window.innerWidth){
			  theWidth = window.innerWidth 
			  theHeight = window.innerHeight 
			} 
			else if (document.documentElement && document.documentElement.clientWidth){
			  theWidth = document.documentElement.clientWidth 
			  theHeight = document.documentElement.clientHeight 
			} 
			else if (document.body){
			  theWidth = document.body.clientWidth 
			  theHeight = document.body.clientHeight 
			}
			img.style.top = ((tab.offsetHeight < img.offsetHeight)?(-tab.offsetHeight / 1.3):((-tab.offsetHeight)+(tab.offsetHeight - img.offsetHeight)/2)) + "px";
		}
	</script>
</head>
<div class="container-fluid">
<table>
	<tr><td><font size="3"><b>El alumno [<%=codigoAlumno %>] no tiene ning&uacute;n proceso de baja en tr&aacute;mite</b></font></td></tr>
</table>
<%
	}else{
	
		if(BajaAlumpasoUtil.realizoPaso(conEnoc, bajaAlumno.getBajaId(), "1")){
			ArrayList<aca.baja.BajaAlumpaso> lisPasos = bajaAlumpasoU.getListPorBaja(conEnoc, bajaAlumno.getBajaId(), "ORDER BY PASO_ID");
			ArrayList<aca.vista.AlumnoCurso> lisCursos = cursoU.getListAlumnoCarga(conEnoc, codigoAlumno, cargaId, "AND TIPOCAL_ID = 'I' ORDER BY NOMBRE_CURSO");
			
			if(accion == null)
				accion = "0";
			
			if(accion.equals("1")){
				conEnoc.setAutoCommit(false);
				
				boolean seCambiaronLosPasos = true;
				for(i = 0; i < lisPasos.size(); i++){//Cambiar el estado de los pasos
					bajaAlumpaso = (BajaAlumpaso) lisPasos.get(i);
					bajaAlumpaso.setFecha(Fecha.getHoy());
					bajaAlumpaso.setEstado("S");
					if(!bajaAlumpasoU.updateReg(conEnoc, bajaAlumpaso))
						seCambiaronLosPasos &= false;
				}
				
				if(seCambiaronLosPasos){
					bajaAlumno.setFBaja(Fecha.getHoy());
					bajaAlumno.setComentario(request.getParameter("comentario"));
					if(bajaAlumnoU.updateReg(conEnoc, bajaAlumno)){
						boolean seGuardaronLosCursos = true;
						for(int j = 0; j < lisCursos.size(); j++){//Guardar las materias que se daran de baja
							alumnoCurso = (aca.vista.AlumnoCurso) lisCursos.get(j);
							bajaCurso.setBajaId(bajaAlumno.getBajaId());
							bajaCurso.setCodigoPersonal(codigoAlumno);
							bajaCurso.setCursoCargaId(alumnoCurso.getCursoCargaId());
							bajaCurso.setCursoId(alumnoCurso.getCursoId());
							bajaCurso.setCreditos(alumnoCurso.getCreditos());
							if(bajaCursoU.insertReg(conEnoc, bajaCurso)){
								krdxCursoAct.mapeaRegId(conEnoc, codigoAlumno, alumnoCurso.getCursoCargaId());
								if(KrdxAlumnoEval.existenEvaluaciones(conEnoc, codigoAlumno, alumnoCurso.getCursoCargaId())){
									if(KrdxAlumnoEval.elimnarEvaluaciones(conEnoc, codigoAlumno, alumnoCurso.getCursoCargaId())){
										if(!krdxCursoAct.deleteReg(conEnoc))
											seGuardaronLosCursos &= false;
									}else
										seGuardaronLosCursos &= false;
								}else{
									if(!krdxCursoAct.deleteReg(conEnoc))
										seGuardaronLosCursos &= false;
								}
							}else
								seGuardaronLosCursos &= false;
						}
						
						if(seGuardaronLosCursos){
%>
<table style="position:relative; z-index:10;">
	<tr>
		<td><font size="3" color="blue"><b>El alumno se di&oacute; de baja correctamente!!!</b></font></td>
	</tr>
</table>
<%
						}else{
							conEnoc.rollback();
						}
					}else{
						conEnoc.rollback();
						bajaAlumnoU.mapeaRegSolicitado(conEnoc, codigoAlumno);
					}
				}else{
					conEnoc.rollback();
				}
				
				conEnoc.setAutoCommit(true);
			}
			
			alumPlan = AlumPlanU.mapeaRegId(conEnoc, codigoAlumno);
			mapaPlan = MapaPlanU.mapeaRegId(conEnoc, alumPlan.getPlanId());
			lisCursos = cursoU.getListAlumnoCarga(conEnoc, codigoAlumno, cargaId, "AND TIPOCAL_ID = 'I' ORDER BY NOMBRE_CURSO");
%>
<form id="forma" name="forma" action="baja?Accion=1" method="post">
	<table style="width:80%; margin: 0 auto;" class="table table-condensed">
		<tr>
			<td class="titulo"><%=institucion%></td>
		</tr>
		<tr>
			<td class="titulo">Documento Oficial del Proceso de Baja</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center">[<%=codigoAlumno %>] <%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, codigoAlumno, "NOMBRE") %></td>
		</tr>
		<tr>
			<td align="center">[<%=alumPlan.getPlanId() %>] <%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, aca.alumno.PlanUtil.getCarreraId(conEnoc, codigoAlumno)) %> <br />
			<b><spring:message code="aca.RVOE"/>:</b> <%=mapaPlan.getRvoe() %></td>
		</tr>
		<tr>
			<td align="center">
				<b>Carga:</b> <%=aca.carga.CargaUtil.getNombreCarga(conEnoc, cargaId) %>&nbsp;&nbsp;&nbsp;
				<b>Modalidad:</b> <%=aca.alumno.AcademicoUtil.getModalidad(conEnoc, codigoAlumno) %>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center">
				<table class="table table-condensed">
					<tr>
						<td class="th2">Materias que se daran de Baja</td>
					</tr>
					<tr>
						<td align="center">
							<table>
								<tr>
									<td>&nbsp;</td>
									<th><spring:message code="aca.Nombre"/></th>
									<th><spring:message code="aca.Maestro"/></th>
									<th><spring:message code="aca.Tipo"/></th>
								</tr>
<%
			if(bajaAlumno.getFBaja() == null){//Si no se ha concluido la baja total
				for(int j = 0; j < lisCursos.size(); j++){
					alumnoCurso = (aca.vista.AlumnoCurso) lisCursos.get(j);
%>
								<tr>
									<td>&nbsp;</td>
									<td><%=alumnoCurso.getNombreCurso() %></td>
									<td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, alumnoCurso.getMaestro(), "NOMBRE") %></td>
									<td align="center"><%=alumnoCurso.getTipoCalId() %></td>
								</tr>
<%
				}
			}else{
				ArrayList<aca.baja.BajaCurso> lisCursosBaja = bajaCursoU.getListAlumno(conEnoc, bajaAlumno.getBajaId(), bajaAlumno.getCodigoPersonal(), "ORDER BY CURSO_ID");
				for(int j = 0; j < lisCursosBaja.size(); j++){
					bajaCurso = (BajaCurso) lisCursosBaja.get(j);
					mapaCurso = MapaCursoU.mapeaRegId(conEnoc, bajaCurso.getCursoId());
					cargaGrupo = CargaGrupoU.mapeaRegId(conEnoc, bajaCurso.getCursoCargaId());
%>
								<tr>
									<td>&nbsp;</td>
									<td><%=mapaCurso.getNombreCurso() %></td>
									<td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, cargaGrupo.getCodigoPersonal(), "NOMBRE") %></td>
									<td align="center">B</td>
								</tr>
<%
				}
			}
%>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
<%
			if(bajaAlumno.getFBaja() == null){//Si no se ha concluido la baja total
%>
		<tr>
			<td align="center">Comentario <input type="text" class="text" id="comentario" name="comentario" maxlength="70" size="30" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td style="text-align:center"><input class="btn btn-danger" type="submit" class="button" value="Baja Total" /></td>
		</tr>
<%
			}else{
%>
		<tr>
			<td align="center"><b><spring:message code="aca.Comentario"/>:</b> <%=bajaAlumno.getComentario() %></td>
		</tr>
<%
			}
%>
	</table>
</form>
<div id="fondo1"><img src="../../imagenes/logo_fondo.png" width="150px"/></div>
<script type="text/javascript">
	inicio();
	document.body.onload = inicio;
</script>
<%
		}else{
%>
<table>
	<tr>
		<td><font color="red" size="4"><b>El alumno no ha pasado por "Retenci&oacute;n y mentoria"</b></font></td>
	</tr>
</table>
<%
		}
	}
%>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>