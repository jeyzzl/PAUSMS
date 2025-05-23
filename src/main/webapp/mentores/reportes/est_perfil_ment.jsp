<%@page import="aca.mentores.MentPerfil"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.catalogo.CatCarrera"%>
<%@page import="aca.vista.MenPerfil"%>
<%@page import="aca.vista.Inscritos"%>
<%@page import="aca.mentores.MentCarrera"%>
<%@page import="aca.alumno.AlumPersonal"%>
<%@page import="aca.mentores.MentAlumno"%>

<jsp:useBean id="acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil" />
<jsp:useBean id="catCarrera" scope="page" class="aca.catalogo.CatCarrera"/>
<jsp:useBean id="catCarreraUtil" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="menPerfil" scope="page" class="aca.vista.MenPerfil"/>
<jsp:useBean id="menPerfilUtil" scope="page" class="aca.vista.MenPerfilUtil"/>
<jsp:useBean id="mentorCarrera" scope="page" class="aca.mentores.MentCarrera"/>
<jsp:useBean id="mentorCarreraUtil" scope="page" class="aca.mentores.MentCarreraUtil"/>
<jsp:useBean id="mentorAlumno" scope="page" class="aca.mentores.MentAlumno"/>
<jsp:useBean id="mentorAlumnoUtil" scope="page" class="aca.mentores.MentAlumnoUtil"/>
<jsp:useBean id="mentPerfilU" scope="page" class="aca.mentores.MentPerfilUtil"/>
<jsp:useBean id="AlumU" scope="page" class="aca.alumno.AlumUtil"/>


<%
	String codigo		= (String) session.getAttribute("codigoPersonal");
	acceso = AccesoU.mapeaRegId(conEnoc, codigo);
	String folio 		= request.getParameter("folio");
	String institucion 	= (String)session.getAttribute("institucion");
	String sPeriodo		= aca.catalogo.CatPeriodoUtil.getPeriodo(conEnoc);

	
	// MAP DE CARRERAS
	java.util.HashMap<String, aca.catalogo.CatCarrera> mapCarrera = catCarreraUtil.getMapAll(conEnoc,"");

	// Map Alumno
	
		java.util.HashMap<String, aca.mentores.MentAlumno>		mapAlum	= aca.mentores.MentAlumnoUtil.getMapMentorAlumno(conEnoc, sPeriodo) ;
	//Nombre de Alumno	
		java.util.HashMap<String, String> mapAlumNom = AlumU.mapMentAlumno(conEnoc, sPeriodo); 
	
	if(folio == null)
		folio = "0";
%>
<%@page import="aca.util.Fecha"%>

	<div class="container-fluid">
	<h1>
		Listado de Perfil de Ingreso por Mentores
	</h1>
	<div class="alert alert-info">
		<a href="menu_mentor_admin" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>
    

<form name="forma" id="forma">
	Elija perfil 
	
	<div class="btn-toolbar">
  <div class="btn-group">
    	 	<a href="est_perfil_ment?folio=1" class="btn-small btn"><b>1</b></a>
			 <a href="est_perfil_ment?folio=2" class="btn-small btn"><b>2</b></a>
			 <a href="est_perfil_ment?folio=3" class="btn-small btn"><b>3</b></a>
  </div>
</div>
		
	
<%
	if(!folio.equals("0")){
		
		
%>
	<table style="width:100%" id="noayuda" class="tabla">
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center"><font size="2"><strong><%=institucion%></strong></font></td>
		</tr>
		<tr>
			<td align="center"><strong>Vicerrector&iacute;a Estudiantil</strong></td>
		</tr>
		<tr>
			<td align="center">Perfil de ingreso y seguimiento [Concentrado del informe <%=folio %>]</td>
		</tr>
		<tr>
			<td align="center"><%=Fecha.getHoy() %></td>
		</tr>
		<tr>
			<td>
				<table style="width:100%" border="1">
					<tr>
						<th colspan="4">&nbsp;</th>
						<th colspan="3">Rel.</th>
						<th colspan="3"><spring:message code="aca.Res"/></th>
						<th colspan="5">Alim. (lugar)</th>
						<th colspan="3">Alim. (Reg)</th>
						<th colspan="3">H. Dev.</th>
						<th colspan="3">V. Rel.</th>
						<th colspan="4">Problemas</th>
						<th colspan="2">Lider</th>
						<th colspan="3">Trabajo</th>
						<th colspan="4">V. Estudiantil</th>
						<th colspan="5">Riesgo</th>
					</tr>
					<tr>
						<td><b>Mentor</b></td>
						<td style="writing-mode: tb-rl;"><b>Carr.</b></td>
						<td style="writing-mode: tb-rl;"><spring:message code="aca.Mat"/></td>
						<td style="writing-mode: tb-rl;"><spring:message code="aca.Nombre"/></td>
						<td style="writing-mode: tb-rl;">ASD bautizado</td>
						<td style="writing-mode: tb-rl;">ASD no bautizado</td>
						<td style="writing-mode: tb-rl;">No ASD</td>
						<td style="writing-mode: tb-rl;">Alumno interno</td>
						<td style="writing-mode: tb-rl;">Alumno externo (casado, con padres, tios, etc.)</td>
						<td style="writing-mode: tb-rl;">Alumno que vive solo o con compañeros</td>
						<td style="writing-mode: tb-rl;">Comedor</td>
						<td style="writing-mode: tb-rl;">Snack</td>
						<td style="writing-mode: tb-rl;">Casa de asistencia</td>
						<td style="writing-mode: tb-rl;">En la propia casa</td>
						<td style="writing-mode: tb-rl;">Otros lugares</td>
						<td style="writing-mode: tb-rl;">Comidas regulares 3 al d&iacute;a</td>
						<td style="writing-mode: tb-rl;">Comidas regulares 2 al d&iacute;a</td>
						<td style="writing-mode: tb-rl;">Comidas regulares 1 al d&iacute;a</td>
						<td style="writing-mode: tb-rl;">Tiempo dedicado 1 hora diaria</td>
						<td style="writing-mode: tb-rl;">Tiempo dedicado 1 hora a la semana</td>
						<td style="writing-mode: tb-rl;">Menos de 1 hora a la semana</td>
						<td style="writing-mode: tb-rl;">Asistencia regular iglesia central UM</td>
						<td style="writing-mode: tb-rl;">Asistencia regular a otra iglesia</td>
						<td style="writing-mode: tb-rl;">No asiste regularmente a la iglesia</td>
						<td style="writing-mode: tb-rl;">Problema familiar serio este semestre</td>
						<td style="writing-mode: tb-rl;">Problema financiero serio este semestre</td>
						<td style="writing-mode: tb-rl;">Repitiendo materias este semestre</td>
						<td style="writing-mode: tb-rl;">Materias pendientes de otro semestre</td>
						<td style="writing-mode: tb-rl;">Es lider espiritual por su influencia</td>
						<td style="writing-mode: tb-rl;">Ocupa una posici&oacute;n de liderazgo</td>
						<td style="writing-mode: tb-rl;">Alumno no trabaja</td>
						<td style="writing-mode: tb-rl;">Alumno trabaja de 1-3 horas diarias</td>
						<td style="writing-mode: tb-rl;">Alumno trabaja 4 o m&aacute;s horas diarias</td>
						<td style="writing-mode: tb-rl;">Desaf&iacute;os con la integraci&oacute;n al ambiente</td>
						<td style="writing-mode: tb-rl;">Situaciones en su relaci&oacute;n social</td>
						<td style="writing-mode: tb-rl;">Progreso acad&eacute;mico</td>
						<td style="writing-mode: tb-rl;">Intenci&oacute;n de regresar</td>
						<td style="writing-mode: tb-rl;">Problemas familiares/personales</td>
						<td style="writing-mode: tb-rl;">Integraci&oacute;n al ambiente</td>
						<td style="writing-mode: tb-rl;">Relaciones sociales</td>
						<td style="writing-mode: tb-rl;">Asuntos acad&eacute;micos</td>
						<td style="writing-mode: tb-rl;">Situaci&oacute;n financiera</td>
					</tr>
<%
		String facultad 		= "";
		String cargas			= aca.carga.CargaUtil.getCargasActivas(conEnoc,aca.util.Fecha.getHoy());
		ArrayList lisCarreras 		= catCarreraUtil.getListAll(conEnoc, "ORDER BY ENOC.FACULTAD_NOMBRE(FACULTAD_ID)");
		ArrayList lisPerfil		= menPerfilUtil.getListCargas(conEnoc, cargas, " ORDER BY ENOC.FACULTAD_NOMBRE(FACULTAD_ID), EMP_NOMBRE(MENTOR_ID), ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		ArrayList lisMentorCarrera	= mentorCarreraUtil.getListAll(conEnoc, "WHERE PERIODO_ID = (SELECT PERIODO_ID FROM ENOC.CAT_PERIODO WHERE now() BETWEEN F_INICIO AND F_FINAL) ORDER BY  EMP_NOMBRE(MENTOR_ID)"); 
		ArrayList lisMentorAlumno	= mentorAlumnoUtil.getListAll(conEnoc, "WHERE PERIODO_ID = (SELECT PERIODO_ID FROM ENOC.CAT_PERIODO WHERE now() BETWEEN F_INICIO AND F_FINAL) AND STATUS = 'A' ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)"); 
		
		
		//Map Alumno - mentor
		
		
		
		for(int i = 0; i < lisCarreras.size(); i++){
			catCarrera = (CatCarrera) lisCarreras.get(i);		 	
		 if( acceso.getAccesos().indexOf( catCarrera.getCarreraId())!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){	
			int alumnosConPerfil	= 0;
			
			for(int j = 0; j < lisMentorCarrera.size(); j++){
				mentorCarrera = (MentCarrera) lisMentorCarrera.get(j);
				
				if(mentorCarrera.getCarreraId().equals(catCarrera.getCarreraId())){
					for(int k = 0; k < lisMentorAlumno.size(); k++){
						mentorAlumno = (MentAlumno) lisMentorAlumno.get(k);
						
						String codigoPersonal 	= "-";
						String nombreAlum		= "-";
						String nombreMent		= "-";
						String carrera			= "-";
						
						if(mapAlum.containsKey(mentorAlumno.getCodigoPersonal())){
							codigoPersonal	= mentorAlumno.getCodigoPersonal();
							nombreMent 		= mentorAlumno.getMentorId();
							carrera			= mapCarrera.get(mentorAlumno.getCarreraId()).getNombreCarrera();
							
							
							if(mapAlumNom.containsKey(codigoPersonal)){
								
								nombreAlum= mapAlumNom.get(codigoPersonal);
										
							}
							
						}
						// Buscar el nombre de la carrera
						
					
						
						
						if(mentorCarrera.getMentorId().equals(mentorAlumno.getMentorId()) && aca.vista.InscritosUtil.getCarreraId(conEnoc, mentorAlumno.getCodigoPersonal()).equals(catCarrera.getCarreraId())){
							boolean entro = false;	
							
						for(int l = 0; l < lisPerfil.size(); l++){
								menPerfil = (MenPerfil) lisPerfil.get(l);
					
								//if(menPerfil.existeReg(conEnoc, mentorAlumno.getMentorId())==true){
								if(menPerfil.getCarreraId().equals(catCarrera.getCarreraId()) && menPerfil.getFolio().equals(folio) && mentorCarrera.getMentorId().equals(mentorAlumno.getMentorId()) && mentorAlumno.getCodigoPersonal().equals(menPerfil.getCodigoPersonal())){
									alumnosConPerfil++;
									entro = true;
%>
					  <tr>
					    <td><%=  aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, mentorAlumno.getMentorId(), "NOMBRE")   %></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<%=aca.catalogo.CatCarreraUtil.getNombreCorto(conEnoc, catCarrera.getCarreraId()) %></td>
						<td><%=menPerfil.getCodigoPersonal() %> - <%=mentorAlumno.getPeriodoId() %></td>
						<td align="center"><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, menPerfil.getCodigoPersonal(), "APELLIDO") %></td>
						<td align="center"><%=menPerfil.getRelAsdb().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getRelAsd().equals("S")?"S":"&nbsp;" %></td>
						<td align="center">123<%=menPerfil.getRelNasd().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getResInt().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getResExt().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getResTipo().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getComComedor().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getComSnack().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getComAsistencia().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getComCasa().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getComOtro().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getCom3().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getCom2().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getCom1().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getDevDiaria().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getDevSemana().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getDevMenos().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getIglUm().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getIglOtra().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getIglNinguna().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getProFamiliar().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getProFinanciero().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getProMateria().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getProPendiente().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getLidEspiritual().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getLidPosicion().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getTraNada().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getTraMedio().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getTraCompleto().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getEstDesafios().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getEstRelaciones().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getEstProgreso().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getEstRegresa().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getRiesgoPersonal().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getRiesgoIntegracion().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getRiesgoRelaciones().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getRiesgoAcademico().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getRiesgoFinanciero().equals("S")?"S":"&nbsp;" %></td>
					</tr>
<%
								}	
							  //}
							}
							if(!entro){
								
								
								menPerfil = new MenPerfil();
								
								
								
%>
					<tr>
						<td><%= aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, mentorAlumno.getMentorId(), "NOMBRE")  %></td>
						<td><%=carrera %></td>
						<td><%=codigoPersonal%></td>
						<td align="center"><%=nombreAlum %></td>
						<td align="center"><%=menPerfil.getRelAsdb()%></td>
						<td align="center"><%=menPerfil.getRelAsd()%></td>
						<td align="center"><%=menPerfil.getRelNasd()%></td>
						<td align="center"><%=menPerfil.getResInt().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getResExt().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getResTipo().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getComComedor().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getComSnack().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getComAsistencia().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getComCasa().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getComOtro().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getCom3().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getCom2().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getCom1().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getDevDiaria().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getDevSemana().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getDevMenos().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getIglUm().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getIglOtra().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getIglNinguna().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getProFamiliar().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getProFinanciero().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getProMateria().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getProPendiente().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getLidEspiritual().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getLidPosicion().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getTraNada().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getTraMedio().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getTraCompleto().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getEstDesafios().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getEstRelaciones().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getEstProgreso().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getEstRegresa().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getRiesgoPersonal().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getRiesgoIntegracion().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getRiesgoRelaciones().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getRiesgoAcademico().equals("S")?"S":"&nbsp;" %></td>
						<td align="center"><%=menPerfil.getRiesgoFinanciero().equals("S")?"S":"&nbsp;" %></td>
					</tr>
<%
							}
						}
					}
					
					
					
					
				}
			}
		 } // fin de if de filtro por carreras
		} // fin de for
%>
				</table>
			</td>
		</tr>
<%
	}
%>
	</table>
</form>

<%@ include file= "../../cierra_enoc.jsp" %>