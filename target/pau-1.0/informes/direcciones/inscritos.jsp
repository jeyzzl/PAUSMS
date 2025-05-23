<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil" />
<jsp:useBean id="Tutor" scope="page" class="aca.alumno.AlumUbicacion"/>
<jsp:useBean id="Pais" scope="page" class="aca.catalogo.CatPais"/>
<jsp:useBean id="PaisU" scope="page" class="aca.catalogo.PaisUtil"/>
<jsp:useBean id="Estado" scope="page" class="aca.catalogo.CatEstado"/>
<jsp:useBean id="Ciudad" scope="page" class="aca.catalogo.CatCiudad"/>
<jsp:useBean id="EstadoU" scope="page" class="aca.catalogo.EstadoUtil"/>
<jsp:useBean id="CiudadU" scope="page" class="aca.catalogo.CiudadUtil"/>
<jsp:useBean id="alumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>

<!-- inicio de estructura -->
<%
	String codigo			= (String) session.getAttribute("codigoPersonal");
	String sCarga			= request.getParameter("f_carga");

	ResultSet rset			= null;
	Statement stmt			= conEnoc.createStatement();
	String COMANDO			= "";

	int cont 				= 1;
	int nInscritos 			= 0, nCalculos 		= 0;
	int nHombres			= 0, nMujeres		= 0;
	int nInternos			= 0, nExternos 		= 0;
	int nNacional			= 0, nExtranjero	= 0;

	String sPlan 			= "X";
	String sMatricula 		= "X";
	String sNombreAlumno 	= "X";
	String sFacultad 		= "X";
	String sCarrera 		= "X";
	String sCarreraTemp		= "X";
	String sNombreFacultad 	= "X";
	String sNombreCarrera	= "X";
	String sSexo 			= "X";
	String sResidencia 		= "X";
	String sFechaNac 		= "X";
	String sModalidad		= "X";
	
	String sAutoSector		= "";
	String sAdmin			= "";
	String sSup				= "";
	String sBgcolor			= "";
	String sGrado			= "";
	String sSemestre		= "";
	
//	sNombreCarrera = alumnoUtil.getCarrera(conEnoc,(String) session.getAttribute("codigoAlumno"));

	COMANDO = "SELECT ADMINISTRADOR, SUPERVISOR, ACCESOS FROM ENOC.ACCESO WHERE CODIGO_PERSONAL= '"+codigo+"' "; 
	rset = stmt.executeQuery(COMANDO);
	if (rset.next()){	
		sAdmin 		= rset.getString("ADMINISTRADOR");
		sSup		= rset.getString("SUPERVISOR");
		sAutoSector	= rset.getString("ACCESOS");
 	}

	COMANDO = "SELECT DISTINCT(E.CODIGO_PERSONAL) AS CODIGO, "+
//			"E.PLAN_ID, "+
			"ENOC.APELLIDO(E.CODIGO_PERSONAL) AS NOMBRE, "+
//			"TO_CHAR(E.F_NACIMIENTO,'DD-MM-YYYY') AS FECHA, "+
//			"ENOC.ALUMNO_MODALIDAD(E.CODIGO_PERSONAL) AS MODALIDAD, "+
			"ENOC.FACULTAD(E.CARRERA_ID) AS FACULTAD, "+
			"E.CARRERA_ID "+
//			"CASE E.RESIDENCIA_ID WHEN 'I' THEN 'Int.' ELSE 'Ext.' END AS RESIDENCIA, "+
//			"CASE E.SEXO WHEN 'M' THEN 'M' ELSE 'F' END AS SEXO, A.GRADO, A.SEMESTRE "+
			"FROM ENOC.ESTADISTICA E, ENOC.ALUM_ACADEMICO A "+ 
			"WHERE CARGA_ID IN "+
				"(SELECT CARGA_ID FROM ENOC.CARGA "+ 
				"WHERE TO_DATE(now(), 'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL) "+
			"AND E.CODIGO_PERSONAL=A.CODIGO_PERSONAL " +
			"ORDER BY FACULTAD, CARRERA_ID, NOMBRE ";
	rset = stmt.executeQuery(COMANDO);
%>
<table id="noayuda" width="90%"  align="center">
<%
    while(rset.next()){
//		sPlan 				= rset.getString("PLAN_ID");
		sMatricula 			= rset.getString("CODIGO");
		sNombreAlumno 		= rset.getString("NOMBRE");
//		sSexo 				= rset.getString("SEXO");
//		sResidencia 		= rset.getString("RESIDENCIA");
		sCarreraTemp 		= rset.getString("CARRERA_ID");
//		sFechaNac 			= rset.getString("FECHA");
//		sModalidad			= rset.getString("MODALIDAD");
//		sGrado				= rset.getString("GRADO");
//		sSemestre			= rset.getString("SEMESTRE");
		alumno = AlumUtil.mapeaRegId(conEnoc, sMatricula);
		Tutor.mapeaRegId(conEnoc,sMatricula);
		Pais = PaisU.mapeaRegId(conEnoc,Tutor.getTPais());
		Estado = EstadoU.mapeaRegId(conEnoc,Tutor.getTPais(),Tutor.getTEstado());
		Ciudad = CiudadU.mapeaRegId(conEnoc,Tutor.getTPais(),Tutor.getTEstado(),Tutor.getTCiudad());

		if( (sAutoSector.indexOf(sCarreraTemp) != -1) || sAdmin.equals("S") || sSup.equals("S") ){
			nInscritos++;
			if (sSexo.equals("M")){ nHombres++; }else{ nMujeres++; }	 			
			if (sResidencia.equals("Int.")){ nInternos++; }else{ nExternos++; }
			
			if(!sFacultad.equals(rset.getString("FACULTAD"))){
		    	sFacultad = rset.getString("FACULTAD");
			 	sNombreFacultad = aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc,sFacultad);
%>	 	

  <tr>
    <td align="center" colspan= "6"><b>&nbsp;</b></td>
  </tr>
  <tr>
    <td align="center" colspan= "6"><b><font color="#000099" size="3"><b><%=sNombreFacultad%></b></font></td>
  </tr>

<%  
       		}//fin del if de facultades diferentes
			if(!sCarrera.equals( sCarreraTemp)){
	   			sCarrera = sCarreraTemp; 
				sNombreCarrera = aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, sCarrera);
%>


  <tr><td>&nbsp;</td></tr>
  <tr> 
    <td colspan= "6" ><b><font color="#000099" size="2" face="Arial Narrow, Arial">Programa:</font><font color="#000099" size="2"> 
      <%=sNombreCarrera%></font></b></td>
  </tr>

  <tr> 
    <th width="2%" height="18" align="center"><spring:message code="aca.Numero"/></th>
    <th width="8%" height="18" align="center"><b><spring:message code="aca.Matricula"/></b></th>
    <th width="30%" height="18" align="center"><b><spring:message code="aca.Nombre"/></b></th>
    <th width="30%" height="18" align="center"><b>Padre</b></th>
    <th width="30%" height="18" align="center"><b>Madre</b></th>
  </tr>
  <%
          		cont = 1;
          	}//fin del if de carreras diferentes
		if ((cont % 2) != 0 ) sBgcolor = sColor; else sBgcolor = "";
%>
  <tr <%=sBgcolor%>> 
    <td><font size="1">&nbsp;</font><%=cont%></font></td>
    <td align="center"><font size="1"><%=sMatricula%></font></td>
    <td><font size="1"><%=sNombreAlumno%></font></td>
    <td><font size="1"><%=Tutor.getPNombre()%></font></td>
    <td align="center"><font size="1"><%=Tutor.getMNombre()%></font></td>
  </tr>
  <tr <%=sBgcolor%>> 
    <td><font size="1">&nbsp;</font></font></td>
    <td colspan= "4"><font size="1"><%=Tutor.getTNombre()%> - <%=Pais.getNombrePais()%>, <%=Estado.getNombreEstado()%>,
     <%=Ciudad.getNombreCiudad()%>. <%=Tutor.getTDireccion()%>, Col. <%=Tutor.getTColonia()%> <%=Tutor.getTCodigo()%>
     <%=Tutor.getTApartado()%>. Tel. <%=Tutor.getTTelefono()%></font></td>
  </tr>
  <%
     cont++;
		} 
 } // fin del while
%>
</table> 
<br>
<table style="width:90%"  align="center">
  <tr> 
    <th width="12%"><font size="1">Inscritos: <%=nInscritos%></font></th>
    <th width="16%"><font size="1">Internos: <%=nInternos%></font></th>
    <th width="14%" align="center"><font size="1">Externos: <%=nExternos%></font></th>
  </tr>
</table>
<div align="center"><font size="3" face="Times New Roman, Times, serif"> <br>
<b><font color="#000099">Fin del Listado...</font> </b> </font></div>
<%
	if (rset!=null){ rset.close(); }
	if (stmt!=null){ stmt.close(); }
%>
<%@ include file= "../../cierra_enoc.jsp" %>