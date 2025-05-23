<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import="java.util.HashMap"%>
<%@ page import= "aca.carga.CargaUtil"%>

<jsp:useBean id="ReligionDao" scope="page" class="aca.catalogo.CatReligionDao"/>
<jsp:useBean id="AcademicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
<jsp:useBean id="GraduaU" scope="page" class="aca.alumno.AlumGraduaUtil"/>
<jsp:useBean id="estadisticaU" scope="page" class="aca.vista.EstadisticaUtil"/>
<jsp:useBean id="CarreraU" scope="page" class="aca.catalogo.CatCarreraUtil" />
<jsp:useBean id="FacultadU" scope="page" class="aca.catalogo.CatFacultadUtil" />

<link rel="stylesheet" href="../../bootstrap/css/docs.css" type="text/css" media="screen" />

<%
	String sCarga1					= request.getParameter("carga1");
	String sCarga2					= request.getParameter("carga2");
	
	CargaUtil cargaU				= new CargaUtil();
	ArrayList<aca.vista.Estadistica> lisEstadistica	= new ArrayList<aca.vista.Estadistica>();

	String sNomCarga1				= cargaU.getNombre(conEnoc, sCarga1);
	String sNomCarga2				= cargaU.getNombre(conEnoc, sCarga2);
	String sNombre					= "";
	String sBgcolor					= "";
	int i							= 0;	
	
	String modalidades	= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
	if(modalidades.equals(""))modalidades="''";
	
	HashMap<String, aca.catalogo.CatReligion> mapReligion 	= ReligionDao.getMapAll(conEnoc, "");
	HashMap<String, aca.catalogo.CatFacultad> mapFacultad 	= FacultadU.getMapFacultad(conEnoc, "");
	HashMap<String, aca.catalogo.CatCarrera> mapCarrera 	= CarreraU.getMapAll(conEnoc, "");
	HashMap<String, aca.catalogo.CatPais> mapPais 			= aca.catalogo.PaisUtil.getMapAll(conEnoc,"");
	java.util.TreeMap<String, aca.vista.Maestros> empleados = aca.vista.MaestrosUtil.getTreeAll(conEnoc,"");
	HashMap<String, String> mapGradua 						= GraduaU.getMapGraduados(conEnoc);
	HashMap<String, String> mapDormitorio 					= AcademicoU.getMapDormi(conEnoc, "WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN('"+sCarga1+"','"+sCarga2+"') AND ESTADO = 'I')");	
	
	HashMap<String, aca.financiero.FesCcobro> map1 			= aca.financiero.FesCcobroUtil.getListComparaModalidad(conEnoc, sCarga1, sCarga2, modalidades, "1");
	HashMap<String, aca.financiero.FesCcobro> map2 			= aca.financiero.FesCcobroUtil.getListComparaModalidad(conEnoc, sCarga1, sCarga2, modalidades, "2");
	HashMap<String, aca.financiero.FesCcobro> map3 			= aca.financiero.FesCcobroUtil.getListComparaModalidad(conEnoc, sCarga2, sCarga1, modalidades, "3");	
%>
 <ul class="nav nav-list bs-docs-sidenav oculto" style="width:290px;position:fixed;_position:absolute;left:3px;top:2px;display:none;">
	 <li class="active"><a data-direction="regresaron"><i class="fas fa-arrow-circle-right"></i><spring:message code="analisis.comparativo.AlumnosAyB"/></a></li>
	 <li><a data-direction="noRegresaron"><i class="fas fa-arrow-circle-right"></i><spring:message code="analisis.comparativo.AlumnosAyNB"/></a></li>
	 <li><a data-direction="nuevos"><i class="fas fa-arrow-circle-right"></i>Alumnos que están en B y faltan en A</a></li>
	 <li><a href="cargas"><i class="fas fa-arrow-circle-right"></i>Regresar</a></li>
 </ul>

<table id="noayuda" align="CENTER" style="vertical-align:top"  width="98%" class="table table-condensed table-bordered table-striped">
  <tr>
  	<td colspan="13" style="text-align:center;" class="regresaron">
  		<div class="alert alert-info" style="margin:0;">
  			<h3><%=aca.parametros.Parametros.getInstitucion(conEnoc, "1") %></h3>
			<h4><spring:message code="analisis.comparativo.Titulo2"/></h4>
			<b><spring:message code="analisis.comparativo.AlumnosInscritosEn"/>:</b>&nbsp; <%=sNomCarga1%> <b>
			&nbsp;<spring:message code="analisis.comparativo.YEn"/>:&nbsp;</b> <%=sNomCarga2%>
  		</div>
  	</td>
  </tr>
  <tr>
  	<td colspan="13">
  		<b><spring:message code="analisis.comparativo.CargaA"/>:</b> <%=sNomCarga1%>, <b><spring:message code="analisis.comparativo.CargaB"/>:</b> <%=sNomCarga2%>
  	</td>
  </tr>
  <tr> 
    <th width="5%" height="19" align="center"><spring:message code="aca.Matricula"/></th>
    <th width="20%" height="19" align="center"><spring:message code="aca.Nombre"/> <spring:message code="aca.Alumno"/></th>
    <th width="5%" height="19" align="center"><spring:message code="aca.Facultad"/></th>    
    <th width="10%"height="19" align="center"><spring:message code="aca.Carrera"/></th>
    <th width="5%" height="19" align="center"><spring:message code="aca.Plan"/></th>
    <th width="5%"height="19" align="center"><spring:message code="aca.Semestre"/></th>
    <th width="15%"height="19" align="center"><spring:message code="aca.Mentor"/></th>
    <th width="10%" height="19" align="center"><spring:message code="aca.Pais"/></th>
    <th width="10%" height="19" align="center"><spring:message code="aca.Religion"/></th>
    <th width="4%" height="19" align="center"><spring:message code="aca.Residencia"/></th>
    <th width="8%" height="19" align="center"><spring:message code="analisis.comparativo.NumeroDormitorio"/></th>
    <th width="8%" height="19" align="center">F.Nac.</th>
  </tr>
<%	
	lisEstadistica	= estadisticaU.getListReturnModalidad(conEnoc, sCarga1, sCarga2, modalidades, " ORDER BY CARRERA_ID, APELLIDO_PATERNO");
	for (i=0; i<lisEstadistica.size(); i++){
		aca.vista.Estadistica estadistica= (aca.vista.Estadistica) lisEstadistica.get(i);
		estadistica.getResidenciaId();
		estadistica.getFNacimiento();
		if ((i % 2) == 0 ) sBgcolor = sColor; else sBgcolor = "";
		
		String religion = "-";
		// Buscar el nombre de la religion
		if (mapReligion.containsKey(estadistica.getReligionId())){
			religion = mapReligion.get(estadistica.getReligionId()).getNombreReligion();
		}
		
		// Carrera y facultad
		String facultad	= "";
		String carrera 	= "";
		if (mapCarrera.containsKey(estadistica.getCarreraId())){			
			aca.catalogo.CatCarrera carr =  (aca.catalogo.CatCarrera)mapCarrera.get(estadistica.getCarreraId());
			carrera = carr.getNombreCarrera();
			if (mapFacultad.containsKey(carr.getFacultadId())){
				facultad = mapFacultad.get( carr.getFacultadId()).getNombreCorto();
			}
		}	
		
		// Obtiene el nombre del pais
		String pais = "";
		if (mapPais.containsKey(estadistica.getPaisId())){
			pais = mapPais.get(estadistica.getPaisId()).getNombrePais();			
		}

		String codigoMent = aca.mentores.MentAlumno.getMentorId(conEnoc, estadistica.getCodigoPersonal(), sCarga2.substring(0,4));
		aca.vista.Maestros ment = empleados.get(codigoMent);
		String mentor = "";
		if(ment!=null){
			mentor = ment.getNombre() +" "+ ment.getApellidoPaterno();
		}		
		// Busca el numero de dormitorio del alumno
		String dorm = "0";
		if (mapDormitorio.containsKey(estadistica.getCodigoPersonal())){
			dorm = mapDormitorio.get(estadistica.getCodigoPersonal());
		}	
		// Busca el semestre o tetra del alumno
		String Semestre = "";		
		if(map1.get(estadistica.getCodigoPersonal()+estadistica.getCargaId()+estadistica.getBloqueId())!=null){
			Semestre = map1.get(estadistica.getCodigoPersonal()+estadistica.getCargaId()+estadistica.getBloqueId()).getSemestre();
		}	
%>
  <tr> 
    <td align="left" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=estadistica.getCodigoPersonal()%></font></b></td>
    <td align="left" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=estadistica.getNombre()+" "+estadistica.getApellidoPaterno()+" "+estadistica.getApellidoMaterno()%></font></b></td>    
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=facultad%></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=carrera%></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=estadistica.getPlanId()%></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=Semestre %></font></b></td>
    <td align="left" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=mentor %></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=pais%></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=religion %></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=estadistica.getResidenciaId()%></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"></font><%=dorm %></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=estadistica.getFNacimiento()%></font></b></td>    
  </tr>
  <%	} // For%  %>
  <tr align="right"> 
    <th height="19" colspan="10"><font size="2" face="Arial, Helvetica, sans-serif" color="#000000"><spring:message code="aca.Total"/>:</font></th>
    <th height="19" align="center"><font size="2" face="Arial, Helvetica, sans-serif" color="#000000"><%=i%></font></th>
  </tr>
</table>
<br>



<table align="CENTER" valign="TOP" cellspadding="1" width="98%" class="table table-condensed table-bordered table-striped">
  <tr>
  	<td colspan="14" style="text-align:center;" class="noRegresaron">
  		<div class="alert alert-info" style="margin:0;">
  			<h3><%=aca.parametros.Parametros.getInstitucion(conEnoc, "1") %></h3>
			<h4><spring:message code="analisis.comparativo.Titulo2"/></h4>
			<b><spring:message code="analisis.comparativo.AlumnosInscritosEn"/>:</b> &nbsp;<%=sNomCarga1%>&nbsp; <b><spring:message code="analisis.comparativo.QueNoEstanEn"/>:</b> &nbsp;<%=sNomCarga2%>
  		</div>
  	</td>
  </tr>
  <tr>
  	<td colspan="14">
  		<b><spring:message code="analisis.comparativo.CargaA"/>:</b> <%=sNomCarga1%>, <b><spring:message code="analisis.comparativo.CargaB"/>:</b> <%=sNomCarga2%>
  	</td>
  </tr>
  <tr> 
    <th width="5%" height="19" align="center"><spring:message code="aca.Matricula"/></th>
    <th width="20%" height="19" align="center"><spring:message code="aca.Nombre"/> <spring:message code="aca.Alumno"/></th>
    <th width="5%" height="19" align="center"><spring:message code="aca.Facultad"/></th>    
    <th width="10%"height="19" align="center"><spring:message code="aca.Carrera"/></th>
    <th width="5%" height="19" align="center"><spring:message code="aca.Plan"/></th>
    <th width="5"height="19" align="center"><spring:message code="aca.Semestre"/></th>
    <th width="15%"height="19" align="center"><spring:message code="aca.Mentor"/></th>
    <th width="10%" height="19" align="center"><spring:message code="aca.Pais"/></th>
    <th width="10%" height="19" align="center"><spring:message code="aca.Religion"/></th>
    <th width="4%" height="19" align="center"><spring:message code="aca.Residencia"/></th>
    <th width="7%" height="19" align="center"><spring:message code="analisis.comparativo.NumeroDormitorio"/></th>
    <th width="8%" height="19" align="center">F.Nac</th>
    <th width="4%" height="19" align="center">Grad.</th>
    <th width="4%" height="19" align="center">S.Soc.</th>
  </tr>
<%
	lisEstadistica	= estadisticaU.getListNoReturnModalidad(conEnoc, sCarga1, sCarga2,modalidades, " ORDER BY ALUM_CARRERA_HISTORICA(CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID)");
	for (i=0; i<lisEstadistica.size(); i++){
		aca.vista.Estadistica estadistica= (aca.vista.Estadistica) lisEstadistica.get(i);
		if ((i % 2) == 0 ) sBgcolor = sColor; else sBgcolor = "";
		
		String religion = "-";
		// Buscar el nombre de la religion
		if (mapReligion.containsKey(estadistica.getReligionId())){
			religion = mapReligion.get(estadistica.getReligionId()).getNombreReligion();
		}
		
		// Carrera y facultad
		String facultad	= "";
		String carrera 	= "";
		if (mapCarrera.containsKey(estadistica.getCarreraId())){			
			aca.catalogo.CatCarrera carr =  (aca.catalogo.CatCarrera)mapCarrera.get(estadistica.getCarreraId());
			carrera = carr.getNombreCarrera();
			if (mapFacultad.containsKey(carr.getFacultadId())){
				facultad = mapFacultad.get( carr.getFacultadId()).getNombreCorto();
			}
		}
		
		// Obtiene el nombre del pais
		String pais = "";
		if (mapPais.containsKey(estadistica.getPaisId())){
			pais = mapPais.get(estadistica.getPaisId()).getNombrePais();			
		}
		
		// Busca el mentor del alumno
		aca.vista.Maestros ment = empleados.get(aca.mentores.MentAlumno.getMentorId(conEnoc, estadistica.getCodigoPersonal(), sCarga2.substring(0,4)));
		String mentor = "";
		if(ment!=null){
			mentor = ment.getNombre() +" "+ ment.getApellidoPaterno();
		}
		// Busca el numero de dormitorio del alumno
		String dorm = "0";
		if (mapDormitorio.containsKey(estadistica.getCodigoPersonal())){
			dorm = mapDormitorio.get(estadistica.getCodigoPersonal());
		}
		// Busca el semestre o tetra del alumno
		String Semestre = "";		
		if(map2.get(estadistica.getCodigoPersonal()+estadistica.getCargaId()+estadistica.getBloqueId())!=null){
			Semestre = map2.get(estadistica.getCodigoPersonal()+estadistica.getCargaId()+estadistica.getBloqueId()).getSemestre();
		}
		
		// Busca si el alumno es graduado
		String graduado = "NO";
		if (mapGradua.containsKey(estadistica.getCodigoPersonal()+estadistica.getPlanId())){
			graduado = "SI";
		}
%>
  <tr> 
    <td align="left" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=estadistica.getCodigoPersonal()%></font></b></td>
    <td align="left" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=estadistica.getNombre()+" "+estadistica.getApellidoPaterno()+" "+estadistica.getApellidoMaterno()%></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=facultad%></font></b></td>    
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=carrera%></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=estadistica.getPlanId()%></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=Semestre %></font></b></td>
    <td align="left" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=mentor%></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=pais%></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=religion %></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=estadistica.getResidenciaId()%></font></b></td>    
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=dorm %></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=estadistica.getFNacimiento()%></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=graduado%></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1">-</font></b></td>
  </tr>
<%	} // For	%>
  <tr align="right"> 
  	<th height="19" colspan="10"><font size="2" face="Arial, Helvetica, sans-serif" color="#000000"><spring:message code="aca.Total"/>:</font></th>
    <th height="19" align="center"><font size="2" face="Arial, Helvetica, sans-serif" color="#000000"><%=i%></font></th>
  </tr>
</table>
<br>



<table align="CENTER" valign="TOP" cellspadding="1" width="98%" class="table table-condensed table-bordered table-striped">
  <tr>
  	<td colspan="14" style="text-align:center;" class="nuevos">
  		<div class="alert alert-info" style="margin:0;">
  			<h3><%=aca.parametros.Parametros.getInstitucion(conEnoc, "1") %></h3>
			<h4><spring:message code="analisis.comparativo.Titulo2"/></h4>
			<b><spring:message code="analisis.comparativo.AlumnosInscritosEn"/>:</b> &nbsp;<%=sNomCarga2%>&nbsp; <b><spring:message code="analisis.comparativo.QueNoEstuvieronEn"/>:</b> &nbsp;<%=sNomCarga1%>
  		</div>
  	</td>
  </tr>
  <tr>
  	<td colspan="14">
  		<b><spring:message code="analisis.comparativo.CargaA"/>:</b> <%=sNomCarga1%>, <b><spring:message code="analisis.comparativo.CargaB"/>:</b> <%=sNomCarga2%>
  	</td>
  </tr>
  <tr> 
    <th width="5%" height="19" align="center"><spring:message code="aca.Matricula"/></th>
    <th width="20%" height="19" align="center"><spring:message code="aca.Nombre"/><spring:message code="aca.Alumno"/></th>
    <th width="5%" height="19" align="center"><spring:message code="aca.Facultad"/></th>
    <th width="10%"height="19" align="center"><spring:message code="aca.Carrera"/></th>
    <th width="5%" height="19" align="center"><spring:message code="aca.Plan"/></th>    
    <th width="5%"height="19" align="center"><spring:message code="aca.Semestre"/></th>
    <th width="15%"height="19" align="center"><spring:message code="aca.Mentor"/></th>
    <th width="10%" height="19" align="center"><spring:message code="aca.Pais"/></th>
    <th width="10%" height="19" align="center"><spring:message code="aca.Religion"/></th>
    <th width="4%" height="19" align="center"><spring:message code="aca.Residencia"/></th>
    <th width="8%" height="19" align="center"><spring:message code="analisis.comparativo.NumeroDormitorio"/></th>
    <th width="8%" height="19" align="center">F.Nac.</th>
  </tr>
<%
	String periodoId 	= aca.carga.CargaUtil.getPeriodo(conEnoc, sCarga2);
	String mentorId 	= ""; 
	String mentor 		= "";
	lisEstadistica		= estadisticaU.getListNewModalidad(conEnoc, sCarga1, sCarga2, modalidades, " ORDER BY ALUM_CARRERA_HISTORICA(CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID)");
	for (i=0; i<lisEstadistica.size(); i++){
		aca.vista.Estadistica estadistica= (aca.vista.Estadistica) lisEstadistica.get(i);
		if ((i % 2) == 0 ) sBgcolor = sColor; else sBgcolor = "";
		
		String religion = "-";
		// Buscar el nombre de la religion
		if (mapReligion.containsKey(estadistica.getReligionId())){
			religion = mapReligion.get(estadistica.getReligionId()).getNombreReligion();
		}
				
		// Carrera y facultad
		String facultad	= "";
		String carrera 	= "";
		if (mapCarrera.containsKey(estadistica.getCarreraId())){			
			aca.catalogo.CatCarrera carr =  (aca.catalogo.CatCarrera)mapCarrera.get(estadistica.getCarreraId());
			carrera = carr.getNombreCarrera();
			if (mapFacultad.containsKey(carr.getFacultadId())){
				facultad = mapFacultad.get( carr.getFacultadId()).getNombreCorto();
			}
		}
		
		// Obtiene el nombre del pais
		String pais = "";
		if (mapPais.containsKey(estadistica.getPaisId())){
			pais = mapPais.get(estadistica.getPaisId()).getNombrePais();			
		}
				
		// Obtiene el nombre del mentor del alumno 
		mentorId = aca.mentores.MentAlumno.getMentorId(conEnoc, estadistica.getCodigoPersonal(), periodoId);		
		aca.vista.Maestros ment = empleados.get(aca.mentores.MentAlumno.getMentorId(conEnoc, estadistica.getCodigoPersonal(), sCarga2.substring(0,4)));
		if(ment!=null){
			mentor = ment.getNombre() +" "+ ment.getApellidoPaterno();
		}else{
			mentor = "-";
		}
		
		// Busca el numero de dormitorio del alumno
		String dorm = "0";
		if (mapDormitorio.containsKey(estadistica.getCodigoPersonal())){
			dorm = mapDormitorio.get(estadistica.getCodigoPersonal());
		}		
		// Busca el semestre o tetra del alumno
		String Semestre = "";		
		if(map3.get(estadistica.getCodigoPersonal()+estadistica.getCargaId()+estadistica.getBloqueId())!=null){
			Semestre = map3.get(estadistica.getCodigoPersonal()+estadistica.getCargaId()+estadistica.getBloqueId()).getSemestre();
		} 
%>
  <tr> 
    <td align="left" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=estadistica.getCodigoPersonal()%></font></b></td>
    <td align="left" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=estadistica.getNombre()+" "+estadistica.getApellidoPaterno()+" "+estadistica.getApellidoMaterno()%></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=facultad%></font></b></td>    
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=carrera%></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=estadistica.getPlanId()%></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=Semestre %></font></b></td>
    <td align="left" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=mentor%></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=pais%></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=religion %></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=estadistica.getResidenciaId()%></font></b></td>    
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=dorm %></font></b></td>
    <td align="center" height="19"><b><font face="Arial, Helvetica, sans-serif" size="1"><%=estadistica.getFNacimiento()%></font></b></td>
  </tr>
<%	} // For%  %>
  <tr align="right"> 
  	<th height="19" colspan="10" align="right"><font size="2" face="Arial, Helvetica, sans-serif" color="#000000"><spring:message code="aca.Total"/>:</font></th>
    <th height="19" align="center"><font size="2" face="Arial, Helvetica, sans-serif" color="#000000"><%=i%></font></th>
  </tr>
</table>

<%
	cargaU				= null;
	lisEstadistica		= null;
	estadisticaU		= null;
%>
<script>
	(function(){
		var nav = jQuery('ul.bs-docs-sidenav').show();
		nav.on('click','a',function(){
			jQuery(this).parent().addClass('active').siblings().removeClass('active');
			jQuery('html,body').animate({ scrollTop: jQuery('.'+jQuery(this).data('direction')).offset().top }, { duration: 'slow', easing: 'swing'}); 
		});
	})();
</script>
<%@ include file= "../../cierra_enoc.jsp" %>

