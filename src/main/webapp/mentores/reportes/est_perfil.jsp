<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.catalogo.CatCarrera"%>
<%@page import="aca.vista.MenPerfil"%>
<%@page import="aca.catalogo.CatFacultad"%>
<%@page import="aca.vista.Inscritos"%>
<%@page import="aca.mentores.MentPerfil"%>

<jsp:useBean id="acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil" />
<jsp:useBean id="catCarrera" scope="page" class="aca.catalogo.CatCarrera"/>
<jsp:useBean id="catCarreraUtil" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="menPerfil" scope="page" class="aca.vista.MenPerfil"/>
<jsp:useBean id="menPerfilUtil" scope="page" class="aca.vista.MenPerfilUtil"/>

<%
	String folio 		= request.getParameter("folio");
	String institucion 	= (String)session.getAttribute("institucion");
	String codigo		= (String) session.getAttribute("codigoPersonal");
	acceso = AccesoU.mapeaRegId(conEnoc, codigo);

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
    	 	<a href="est_perfil?folio=1" class="btn-small btn"><b>1</b></a>
			 <a href="est_perfil?folio=2" class="btn-small btn"><b>2</b></a>
			 <a href="est_perfil?folio=3" class="btn-small btn"><b>3</b></a>
  </div>
</div>

<%
	if(!folio.equals("0")){
%>
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
						<th>&nbsp;</th>
						<th>&nbsp;</th>
						<th>&nbsp;</th>
						<th>&nbsp;</th>
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
						<td><b>Facultad/Carrera</b></td>
						<td style="writing-mode: tb-rl;"><b><spring:message code="aca.Inscritos"/></b></td>
						<td style="writing-mode: tb-rl;"><b>Al.Men.</b></td>
						<td style="writing-mode: tb-rl;">N° Alumnos con perfil</td>
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
		String facultad 	= "";
		String cargas		= aca.carga.CargaUtil.getCargasActivas(conEnoc,aca.util.Fecha.getHoy());
		ArrayList lisCarreras 	= catCarreraUtil.getListAll(conEnoc, " ORDER BY ENOC.FACULTAD_NOMBRE(FACULTAD_ID), NOMBRE_CARRERA");
		ArrayList lisPerfil	= menPerfilUtil.getListCargas(conEnoc, cargas, " ORDER BY ENOC.FACULTAD_NOMBRE(FACULTAD_ID), ENOC.NOMBRE_CARRERA(CARRERA_ID)");
		
		for(int i = 0; i < lisCarreras.size(); i++){
			catCarrera = (CatCarrera) lisCarreras.get(i);
		 if( acceso.getAccesos().indexOf( catCarrera.getCarreraId())!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){
			
			
			int alumnosConPerfil	= 0;
			int relAsdb 			= 0;
			int relAsd				= 0;
			int relNasd				= 0;
			int resInt				= 0;
			int resExt				= 0;
			int resTipo				= 0;
			int comComedor			= 0;
			int comSnack			= 0;
			int comAsistencia		= 0;
			int comCasa				= 0;
			int comOtro				= 0;
			int com3				= 0;
			int com2				= 0;
			int com1				= 0;
			int devDiaria			= 0;
			int devSemana			= 0;
			int devMenos			= 0;
			int iglUm				= 0;
			int iglOtra				= 0;
			int iglNinguna			= 0;
			int proFamiliar			= 0;
			int proFinanciero		= 0;
			int proMateria			= 0;
			int proPendiente		= 0;
			int lidEspiritual		= 0;
			int lidPosicion			= 0;
			int traNada				= 0;
			int traMedio			= 0;
			int traCompleto			= 0;
			int estDesafios			= 0;
			int estRelaciones		= 0;
			int estProgreso			= 0;
			int estRegresa			= 0;
			int riesgoPersonal		= 0;
			int riesgoIntegracion	= 0;
			int riesgoRelaciones	= 0;
			int riesgoAcademico		= 0;
			int riesgoFinanciero	= 0;
			
			
			if(!facultad.equals(catCarrera.getFacultadId())){
				facultad = catCarrera.getFacultadId();
				for(int j = 0; j < lisPerfil.size(); j++){
					menPerfil = (MenPerfil) lisPerfil.get(j);
					
					if(menPerfil.getFacultadId().equals(facultad) && menPerfil.getFolio().equals(folio)){
						alumnosConPerfil++;
						if(menPerfil.getRelAsdb().equals("S"))
							relAsdb++;
						if(menPerfil.getRelAsd().equals("S"))
							relAsd++;
						if(menPerfil.getRelNasd().equals("S"))
							relNasd++;
						if(menPerfil.getResInt().equals("S"))
							resInt++;
						if(menPerfil.getResExt().equals("S"))
							resExt++;
						if(menPerfil.getResTipo().equals("S"))
							resTipo++;
						if(menPerfil.getComComedor().equals("S"))
							comComedor++;
						if(menPerfil.getComSnack().equals("S"))
							comSnack++;
						if(menPerfil.getComAsistencia().equals("S"))
							comAsistencia++;
						if(menPerfil.getComCasa().equals("S"))
							comCasa++;
						if(menPerfil.getComOtro().equals("S"))
							comOtro++;
						if(menPerfil.getCom3().equals("S"))
							com3++;
						if(menPerfil.getCom2().equals("S"))
							com2++;
						if(menPerfil.getCom1().equals("S"))
							com1++;
						if(menPerfil.getDevDiaria().equals("S"))
							devDiaria++;
						if(menPerfil.getDevSemana().equals("S"))
							devSemana++;
						if(menPerfil.getDevMenos().equals("S"))
							devMenos++;
						if(menPerfil.getIglUm().equals("S"))
							iglUm++;
						if(menPerfil.getIglOtra().equals("S"))
							iglOtra++;
						if(menPerfil.getIglNinguna().equals("S"))
							iglNinguna++;
						if(menPerfil.getProFamiliar().equals("S"))
							proFamiliar++;
						if(menPerfil.getProFinanciero().equals("S"))
							proFinanciero++;
						if(menPerfil.getProMateria().equals("S"))
							proMateria++;
						if(menPerfil.getProPendiente().equals("S"))
							proPendiente++;
						if(menPerfil.getLidEspiritual().equals("S"))
							lidEspiritual++;
						if(menPerfil.getLidPosicion().equals("S"))
							lidPosicion++;
						if(menPerfil.getTraNada().equals("S"))
							traNada++;
						if(menPerfil.getTraMedio().equals("S"))
							traMedio++;
						if(menPerfil.getTraCompleto().equals("S"))
							traCompleto++;
						if(menPerfil.getEstDesafios().equals("S"))
							estDesafios++;
						if(menPerfil.getEstRelaciones().equals("S"))
							estRelaciones++;
						if(menPerfil.getEstProgreso().equals("S"))
							estProgreso++;
						if(menPerfil.getEstRegresa().equals("S"))
							estRegresa++;
						if(menPerfil.getRiesgoPersonal().equals("S"))
							riesgoPersonal++;
						if(menPerfil.getRiesgoIntegracion().equals("S"))
							riesgoIntegracion++;
						if(menPerfil.getRiesgoRelaciones().equals("S"))
							riesgoRelaciones++;
						if(menPerfil.getRiesgoAcademico().equals("S"))
							riesgoAcademico++;
						if(menPerfil.getRiesgoFinanciero().equals("S"))
							riesgoFinanciero++;
					}
				}
				
%>
					<tr>
						<td><b><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, facultad) %></b></td>
						<td align="center"><%=aca.vista.InscritosUtil.getInscritosFacultad(conEnoc, facultad) %></td>
						<td align="center"><%=MentPerfil.alumMenFac(conEnoc,facultad) %></td>
						<td align="center"><%=alumnosConPerfil %></td>
						<td align="center"><%=relAsdb %></td>
						<td align="center"><%=relAsd %></td>
						<td align="center"><%=relNasd %></td>
						<td align="center"><%=resInt %></td>
						<td align="center"><%=resExt %></td>
						<td align="center"><%=resTipo %></td>
						<td align="center"><%=comComedor %></td>
						<td align="center"><%=comSnack %></td>
						<td align="center"><%=comAsistencia %></td>
						<td align="center"><%=comCasa %></td>
						<td align="center"><%=comOtro %></td>
						<td align="center"><%=com3 %></td>
						<td align="center"><%=com2 %></td>
						<td align="center"><%=com1 %></td>
						<td align="center"><%=devDiaria %></td>
						<td align="center"><%=devSemana %></td>
						<td align="center"><%=devMenos %></td>
						<td align="center"><%=iglUm %></td>
						<td align="center"><%=iglOtra %></td>
						<td align="center"><%=iglNinguna %></td>
						<td align="center"><%=proFamiliar %></td>
						<td align="center"><%=proFinanciero %></td>
						<td align="center"><%=proMateria %></td>
						<td align="center"><%=proPendiente %></td>
						<td align="center"><%=lidEspiritual %></td>
						<td align="center"><%=lidPosicion %></td>
						<td align="center"><%=traNada %></td>
						<td align="center"><%=traMedio %></td>
						<td align="center"><%=traCompleto %></td>
						<td align="center"><%=estDesafios %></td>
						<td align="center"><%=estRelaciones %></td>
						<td align="center"><%=estProgreso %></td>
						<td align="center"><%=estRegresa %></td>
						<td align="center"><%=riesgoPersonal %></td>
						<td align="center"><%=riesgoIntegracion %></td>
						<td align="center"><%=riesgoRelaciones %></td>
						<td align="center"><%=riesgoAcademico %></td>
						<td align="center"><%=riesgoFinanciero %></td>
					</tr>
<%
				
				relAsdb 			= 0;
				relAsd				= 0;
				relNasd				= 0;
				resInt				= 0;
				resExt				= 0;
				resTipo				= 0;
				comComedor			= 0;
				comSnack			= 0;
				comAsistencia		= 0;
				comCasa				= 0;
				comOtro				= 0;
				com3				= 0;
				com2				= 0;
				com1				= 0;
				devDiaria			= 0;
				devSemana			= 0;
				devMenos			= 0;
				iglUm				= 0;
				iglOtra				= 0;
				iglNinguna			= 0;
				proFamiliar			= 0;
				proFinanciero		= 0;
				proMateria			= 0;
				proPendiente		= 0;
				lidEspiritual		= 0;
				lidPosicion			= 0;
				traNada				= 0;
				traMedio			= 0;
				traCompleto			= 0;
				estDesafios			= 0;
				estRelaciones		= 0;
				estProgreso			= 0;
				estRegresa			= 0;
				riesgoPersonal		= 0;
				riesgoIntegracion	= 0;
				riesgoRelaciones	= 0;
				riesgoAcademico		= 0;
				riesgoFinanciero	= 0;
				alumnosConPerfil	= 0;
			}
			
			for(int k = 0; k < lisPerfil.size(); k++){
				menPerfil = (MenPerfil) lisPerfil.get(k);
				
				
				if(menPerfil.getCarreraId().equals(catCarrera.getCarreraId()) && menPerfil.getFolio().equals(folio)){
					alumnosConPerfil++;
					if(menPerfil.getRelAsdb().equals("S"))
						relAsdb++;
					if(menPerfil.getRelAsd().equals("S"))
						relAsd++;
					if(menPerfil.getRelNasd().equals("S"))
						relNasd++;
					if(menPerfil.getResInt().equals("S"))
						resInt++;
					if(menPerfil.getResExt().equals("S"))
						resExt++;
					if(menPerfil.getResTipo().equals("S"))
						resTipo++;
					if(menPerfil.getComComedor().equals("S"))
						comComedor++;
					if(menPerfil.getComSnack().equals("S"))
						comSnack++;
					if(menPerfil.getComAsistencia().equals("S"))
						comAsistencia++;
					if(menPerfil.getComCasa().equals("S"))
						comCasa++;
					if(menPerfil.getComOtro().equals("S"))
						comOtro++;
					if(menPerfil.getCom3().equals("S"))
						com3++;
					if(menPerfil.getCom2().equals("S"))
						com2++;
					if(menPerfil.getCom1().equals("S"))
						com1++;
					if(menPerfil.getDevDiaria().equals("S"))
						devDiaria++;
					if(menPerfil.getDevSemana().equals("S"))
						devSemana++;
					if(menPerfil.getDevMenos().equals("S"))
						devMenos++;
					if(menPerfil.getIglUm().equals("S"))
						iglUm++;
					if(menPerfil.getIglOtra().equals("S"))
						iglOtra++;
					if(menPerfil.getIglNinguna().equals("S"))
						iglNinguna++;
					if(menPerfil.getProFamiliar().equals("S"))
						proFamiliar++;
					if(menPerfil.getProFinanciero().equals("S"))
						proFinanciero++;
					if(menPerfil.getProMateria().equals("S"))
						proMateria++;
					if(menPerfil.getProPendiente().equals("S"))
						proPendiente++;
					if(menPerfil.getLidEspiritual().equals("S"))
						lidEspiritual++;
					if(menPerfil.getLidPosicion().equals("S"))
						lidPosicion++;
					if(menPerfil.getTraNada().equals("S"))
						traNada++;
					if(menPerfil.getTraMedio().equals("S"))
						traMedio++;
					if(menPerfil.getTraCompleto().equals("S"))
						traCompleto++;
					if(menPerfil.getEstDesafios().equals("S"))
						estDesafios++;
					if(menPerfil.getEstRelaciones().equals("S"))
						estRelaciones++;
					if(menPerfil.getEstProgreso().equals("S"))
						estProgreso++;
					if(menPerfil.getEstRegresa().equals("S"))
						estRegresa++;
					if(menPerfil.getRiesgoPersonal().equals("S"))
						riesgoPersonal++;
					if(menPerfil.getRiesgoIntegracion().equals("S"))
						riesgoIntegracion++;
					if(menPerfil.getRiesgoRelaciones().equals("S"))
						riesgoRelaciones++;
					if(menPerfil.getRiesgoAcademico().equals("S"))
						riesgoAcademico++;
					if(menPerfil.getRiesgoFinanciero().equals("S"))
						riesgoFinanciero++;
				}
			} 
%>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<%=aca.catalogo.CatCarreraUtil.getNombreCorto(conEnoc, catCarrera.getCarreraId()) %></td>
						<td align="center"><%=aca.vista.InscritosUtil.getInscritosCarrera(conEnoc, catCarrera.getCarreraId()) %></td>
						<td align="center"><%=MentPerfil.alumMenCarr(conEnoc,catCarrera.getCarreraId())%></td>
						<td align="center"><%=alumnosConPerfil %></td>
						<td align="center"><%=relAsdb %></td>
						<td align="center"><%=relAsd %></td>
						<td align="center"><%=relNasd %></td>
						<td align="center"><%=resInt %></td>
						<td align="center"><%=resExt %></td>
						<td align="center"><%=resTipo %></td>
						<td align="center"><%=comComedor %></td>
						<td align="center"><%=comSnack %></td>
						<td align="center"><%=comAsistencia %></td>
						<td align="center"><%=comCasa %></td>
						<td align="center"><%=comOtro %></td>
						<td align="center"><%=com3 %></td>
						<td align="center"><%=com2 %></td>
						<td align="center"><%=com1 %></td>
						<td align="center"><%=devDiaria %></td>
						<td align="center"><%=devSemana %></td>
						<td align="center"><%=devMenos %></td>
						<td align="center"><%=iglUm %></td>
						<td align="center"><%=iglOtra %></td>
						<td align="center"><%=iglNinguna %></td>
						<td align="center"><%=proFamiliar %></td>
						<td align="center"><%=proFinanciero %></td>
						<td align="center"><%=proMateria %></td>
						<td align="center"><%=proPendiente %></td>
						<td align="center"><%=lidEspiritual %></td>
						<td align="center"><%=lidPosicion %></td>
						<td align="center"><%=traNada %></td>
						<td align="center"><%=traMedio %></td>
						<td align="center"><%=traCompleto %></td>
						<td align="center"><%=estDesafios %></td>
						<td align="center"><%=estRelaciones %></td>
						<td align="center"><%=estProgreso %></td>
						<td align="center"><%=estRegresa %></td>
						<td align="center"><%=riesgoPersonal %></td>
						<td align="center"><%=riesgoIntegracion %></td>
						<td align="center"><%=riesgoRelaciones %></td>
						<td align="center"><%=riesgoAcademico %></td>
						<td align="center"><%=riesgoFinanciero %></td>
					</tr>
<%		 } // fin de filtro por carreras
		} // fin de for de carreras
%>
				</table>
			</td>
		</tr>
<%
	} // IF FOLIO != 0
%>
	</table>
</form>

<%@ include file= "../../cierra_enoc.jsp" %>