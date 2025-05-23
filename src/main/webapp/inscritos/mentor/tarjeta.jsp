<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>

<jsp:useBean id="personal" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="inscritosU" scope="page" class="aca.vista.InscritosUtil"/>
<jsp:useBean id="acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil" />
<%
	String codigoPersonal   = (String) session.getAttribute("codigoPersonal");
	String facultad			= request.getParameter("facultad");
	String matricula 		= "";

	String cargas = request.getParameter("cargas");
	String modalidades = request.getParameter("modalidades");
%>
<style>
	TD  {
		font : normal 8pt Verdana, Tahoma;
	}
</style>
<div class="container-fluid">
<h2><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc,facultad)%></h2>
<div class="alert alert-info">
	<a class="btn btn-primary" href="inscritos?">Regresar</a>
</div>
<table style="width:90%"  align="center" cellpadding="1" cellspacing="1" class="tabla">
  <tr>
<%	
int cont = 0;
acceso = AccesoU.mapeaRegId(conEnoc, codigoPersonal);
	ArrayList<aca.vista.Inscritos> lisInscritos		= inscritosU.getListAllUM(conEnoc,"WHERE ENOC.FACULTAD(CARRERA_ID) = '"+facultad+"' AND CARGA_ID IN("+cargas+") AND MODALIDAD_ID IN ("+modalidades+") ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");
    for(int j=0; j<lisInscritos.size(); j++){			
		aca.vista.Inscritos insc = (aca.vista.Inscritos) lisInscritos.get(j);
			matricula = insc.getCodigoPersonal();
			if( acceso.getAccesos().indexOf(insc.getCarreraId())!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){				
				if ((cont % 3) == 0 && cont!=0) { %> </tr><tr><td colspan="3">&nbsp;</td></tr><tr><%}
%>
	<td align="center">
	<table background='../../portales/maestro/bg.gif' width="298" height="210"  cellpadding="1">
	  <tr valign='top' height='165'>
		<td>&nbsp;</td>
		<td width='70' align='center'>
			<table border='1'   >
				<tr>&nbsp;<td bordercolor='#8596CA'><img src="../../foto?Codigo=<%=matricula%>&Tipo=O" width="70"></td></tr>
			</table> 	
			<b><%=matricula%></b>
		</td>
		<br><br>
		<td>
			<table width='100%'   cellspacing='8'>
				<tr><td><font size=2><b><%= insc.getNombre()+" "+insc.getApellidoPaterno()+" "+insc.getApellidoMaterno()%></b></font></td></tr>
				<tr><td><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc,insc.getCarreraId())%></td></tr>
				<tr><td><%= aca.catalogo.CatReligionDao.getNombreReligion(conEnoc,insc.getReligionId()) %></td></tr>
				<tr><td><%= aca.vista.InscritosUtil.getEdad(conEnoc,matricula) %> años (<%= insc.getFNacimiento()%>)</td></tr>
				<tr><td><%= aca.catalogo.CiudadUtil.getNombreCiudad(conEnoc,insc.getPaisId(),insc.getEstadoId(),insc.getCiudadId())%>, <%= aca.catalogo.EstadoUtil.getNombreEstado(conEnoc, insc.getPaisId(),insc.getEstadoId())%>, <%= aca.catalogo.PaisUtil.getNombrePais(conEnoc,insc.getPaisId())%></td></tr>
				<tr><td><%if(insc.getResidenciaId().equals("E"))out.print("Externo");else out.print("Interno");%>, <%if(aca.alumno.AlumUtil.esInscrito(conEnoc,matricula))out.print("Inscrito");else out.print("No inscrito");%></td></tr>
			</table>			 	
		</td>
	</tr>
	</table>
	</td>
<%			cont++;
		}
	}%>
  </tr>
</table>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>