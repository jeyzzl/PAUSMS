<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AlumConstancia" scope="page" class="aca.alumno.AlumConstancia"/>
<jsp:useBean id="AlumConstanciaU" scope="page" class="aca.alumno.AlumConstanciaUtil"/>
<jsp:useBean id="AlumConstanciaImpresion" scope="page" class="aca.alumno.AlumConstanciaImpresion"/>
<jsp:useBean id="AlumConstanciaImpresionU" scope="page" class="aca.alumno.AlumConstanciaImpresionUtil"/>
<jsp:useBean id="AlumPersonal" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="AlumAcademico" scope="page" class="aca.alumno.AlumAcademico"/>
<jsp:useBean id="AcademicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
<jsp:useBean id="escuela" scope="page" class="aca.catalogo.CatEscuela" />
<jsp:useBean id="Fecha" scope="page" class="aca.util.Fecha" />

<%

	String constanciaId         = request.getParameter("constanciaId");
	String codigoId      		= request.getParameter("codigoId");
	
	AlumPersonal = AlumUtil.mapeaRegId(conEnoc, codigoId);
	AlumAcademico = AcademicoU.mapeaRegId(conEnoc, codigoId);
	
	if(constanciaId == null || codigoId == null){
		response.sendRedirect("constancias");
	}

	AlumConstancia = AlumConstanciaU.mapeaRegId(conEnoc, constanciaId);
	
	
	/* FORMATEAR TEXTO */
	
	String dia  			= aca.util.NumberToLetter.convertirLetras( Integer.parseInt(Fecha.getDia(aca.util.Fecha.getHoy())) );
	String mes  			= Fecha.getMesNombre( aca.util.Fecha.getHoy() );
	String year  			= aca.util.NumberToLetter.convertirLetras( Integer.parseInt(Fecha.getYear(aca.util.Fecha.getHoy())) );
	String pais 			= aca.catalogo.PaisUtil.getNombrePais(conEnoc, AlumPersonal.getPaisId());
	String estado 			= aca.catalogo.EstadoUtil.getNombreEstado(conEnoc, AlumPersonal.getPaisId(), AlumPersonal.getEstadoId());
	String ciudad 			= aca.catalogo.CiudadUtil.getNombreCiudad(conEnoc, AlumPersonal.getPaisId(), AlumPersonal.getEstadoId(), AlumPersonal.getCiudadId());
	String plan				= aca.alumno.PlanUtil.getPlanActual(conEnoc, codigoId);
	String facultad			= aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, aca.alumno.PlanUtil.getCarreraId(conEnoc, codigoId)));
	String carrera 			= aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, aca.alumno.PlanUtil.getCarreraId(conEnoc, codigoId));
	
	/* VACACIONES */
	String modalidadId		= aca.alumno.AcademicoUtil.getModalidadId(conEnoc,codigoId);
	String planId			= aca.alumno.PlanUtil.getPlanActual(conEnoc,codigoId);
	String carreraId		= aca.plan.PlanUtil.getCarreraId(conEnoc,planId);	
	String nivelId			= aca.alumno.PlanUtil.getCarreraNivel(conEnoc,carreraId);
	String fExamen			= aca.alumno.AlumVacacionLista.getFechaExamen(conEnoc, nivelId, modalidadId).equals("X")?"00/00/0000":aca.alumno.AlumVacacionLista.getFechaExamen(conEnoc, nivelId, modalidadId);
	String fInicio			= aca.alumno.AlumVacacionLista.getFechaInicio(conEnoc, nivelId, modalidadId).equals("X")?"00/00/0000":aca.alumno.AlumVacacionLista.getFechaInicio(conEnoc, nivelId, modalidadId);
	String fFinal			= aca.alumno.AlumVacacionLista.getFechaInicio(conEnoc, nivelId, modalidadId).equals("X")?"00/00/0000":aca.alumno.AlumVacacionLista.getFechaInicio(conEnoc, nivelId, modalidadId);
	int diaSiguiente		= 0;
	if(fFinal != "X"){
		diaSiguiente = Integer.parseInt(Fecha.getDia(fFinal));
		diaSiguiente = diaSiguiente+1;
	}
	
	
	String constanciaHTML = AlumConstancia.getConstancia();
	
	constanciaHTML = constanciaHTML.replaceAll("#LetraGenero", AlumPersonal.getSexo().equals("F")?"a":"o");
	constanciaHTML = constanciaHTML.replaceAll("#Matricula", codigoId.trim());
	constanciaHTML = constanciaHTML.replaceAll("#Nombre", AlumPersonal.getNombre().trim());
	constanciaHTML = constanciaHTML.replaceAll("#Paterno", AlumPersonal.getApellidoPaterno().trim());
	constanciaHTML = constanciaHTML.replaceAll("#Materno", AlumPersonal.getApellidoMaterno().trim());
	constanciaHTML = constanciaHTML.replaceAll("#Nacionalidad", aca.catalogo.PaisUtil.getNacionalidad(conEnoc, AlumPersonal.getPaisId()).trim());
	constanciaHTML = constanciaHTML.replaceAll("#Plan", plan);
	constanciaHTML = constanciaHTML.replaceAll("#Facultad", facultad);
	constanciaHTML = constanciaHTML.replaceAll("#Carrera", carrera);
	constanciaHTML = constanciaHTML.replaceAll("#Semestre",  aca.util.NumberToLetter.convertirLetras2(AcademicoU.getSemestre(conEnoc, codigoId)).toLowerCase() );
	constanciaHTML = constanciaHTML.replaceAll("#Residencia", AlumAcademico.getResidenciaId().equals("E")?"Externo":"Interno");
	constanciaHTML = constanciaHTML.replaceAll("#Curp", AlumPersonal.getCurp().trim());
	constanciaHTML = constanciaHTML.replaceAll("#Pais", pais.trim());
	constanciaHTML = constanciaHTML.replaceAll("#Estado", estado.trim());
	constanciaHTML = constanciaHTML.replaceAll("#Ciudad", ciudad.trim());
	constanciaHTML = constanciaHTML.replaceAll("#Fecha", aca.util.Fecha.getHoy().trim());
	constanciaHTML = constanciaHTML.replaceAll("#DiaNumero", Integer.parseInt(Fecha.getDia(aca.util.Fecha.getHoy()).trim())+"");
	constanciaHTML = constanciaHTML.replaceAll("#MesNumero", Integer.parseInt(Fecha.getMes(aca.util.Fecha.getHoy()).trim())+"");
	constanciaHTML = constanciaHTML.replaceAll("#YearNumero", Fecha.getYear(aca.util.Fecha.getHoy()).trim());
	constanciaHTML = constanciaHTML.replaceAll("#Dia", dia.trim());
	constanciaHTML = constanciaHTML.replaceAll("#Mes", mes.trim());
	constanciaHTML = constanciaHTML.replaceAll("#Year", year.trim());
	constanciaHTML = constanciaHTML.replaceAll("#PeriodoActual", aca.util.Fecha.getPeriodoActual().trim());
	constanciaHTML = constanciaHTML.replaceAll("#UltimoDiaDeClases", Fecha.getDia(fExamen).trim());
	constanciaHTML = constanciaHTML.replaceAll("#UltimoMesDeClases", Fecha.getMesNombre(fExamen).trim());
	constanciaHTML = constanciaHTML.replaceAll("#UltimoYearDeClases", Fecha.getYear(fExamen).trim());
	constanciaHTML = constanciaHTML.replaceAll("#PrimerDiaDeVacaciones", Fecha.getDia(fInicio).trim());
	constanciaHTML = constanciaHTML.replaceAll("#PrimerMesDeVacaciones", Fecha.getMesNombre(fInicio).trim());
	constanciaHTML = constanciaHTML.replaceAll("#PrimerYearDeVacaciones", Fecha.getYear(fInicio).trim());
	constanciaHTML = constanciaHTML.replaceAll("#UltimoDiaDeVacacionesSiguiente", (diaSiguiente+"").trim());
	constanciaHTML = constanciaHTML.replaceAll("#UltimoMesDeVacaciones", Fecha.getMesNombre(fFinal).trim());
	constanciaHTML = constanciaHTML.replaceAll("#UltimoYearDeVacaciones", Fecha.getYear(fFinal).trim());
	constanciaHTML = constanciaHTML.replaceAll("#UltimoDiaDeVacaciones", Fecha.getDia(fFinal).trim());
	constanciaHTML = constanciaHTML.replaceAll("#Foto", "<img width='97' src='../../foto?Codigo="+codigoId.trim()+"&Tipo=O'/>");
	
	
	String opcional = request.getParameter("opcional")==null?"sin":request.getParameter("opcional");
	
	if(opcional.equals("sin")){
%>
		<style>
			.ckeditor-opcional{
			  	-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=0)"; /* IE8 */
  				filter: alpha(opacity=0); /* IE 5-7 */
  				opacity: 0; /* Modern Browsers */
			}
		</style>
<%
	}
%>

<link rel="stylesheet" href="../../js-plugins/ckeditor/contents.css"></link>

<div class="container-fluid">
	
	<%=constanciaHTML %>
	
</div>	


<%
AlumConstanciaImpresion.setCodigoPersonal(codigoId);
AlumConstanciaImpresion.setConstanciaId(constanciaId);
AlumConstanciaImpresionU.insertReg(conEnoc, AlumConstanciaImpresion);
%>

<%@ include file= "../../cierra_enoc.jsp" %>