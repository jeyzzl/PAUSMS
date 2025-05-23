<%@page import="java.util.HashMap"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>

<jsp:useBean id="mentU" scope="page" class="aca.mentores.MentAlumnoUtil"/>
<jsp:useBean id="acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil" />
<jsp:useBean id="alumnoU" scope="page" class="aca.alumno.AlumUtil"/>

<% 
		
	String codigo		= (String) session.getAttribute("codigoPersonal");
	String periodoId	= aca.catalogo.CatPeriodoUtil.getPeriodo(conEnoc);
	String cargaId		= "";
	String mentor       = "X";
	String ment         = "";
	String  folio 		= "";
	String ent1="0",ent2="0",ent3="0";

	String bgColor		= ""; 
	int cont 			= 0;
	
	HashMap <String, aca.alumno.AlumPersonal> mapPersonal 	= alumnoU.getMapInscritos(conEnoc);
	HashMap <String, aca.mentores.MentAlumno> mapMentor 	= aca.mentores.MentAlumnoUtil.getMapMentorAlumno(conEnoc, periodoId);
	
	ArrayList<aca.mentores.MentAlumno> lisMentAlum 	    = mentU.getListMentPeriodo(conEnoc,periodoId,"ORDER BY FACULTAD(ALUM_CARRERA_ID(CODIGO_PERSONAL)), MENTOR_ID" );
	
	String alumno = "";
%>
<body>

<div class="container-fluid">
	<h1>Alumnos por Tutor</h1>
	<div class="alert alert-info">
		<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Regresar</a>		
	</div>
	
  <table style="width:70%" align="center"    id="noayuda">
    <table style="margin: 0 auto;"   >
      <tr>
        <td align="center" style="font-size: 12pt;"><b>Reporte de Alumnos por Mentor</b></td>
      </tr>
    </table><br>
    <table class="tabla" width="70%" align="center"   >

<% 
	acceso = AccesoU.mapeaRegId(conEnoc, codigo);	
		for (int i=0; i<lisMentAlum.size(); i++){cont++;
			aca.mentores.MentAlumno mentA = (aca.mentores.MentAlumno) lisMentAlum.get(i);
			if( acceso.getAccesos().indexOf(mentA.getCarreraId())!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){
			cargaId = aca.carga.CargaUtil.getMejorCarga(conEnoc, mentA.getCodigoPersonal());
			
			if(mapMentor.containsKey(mentA.getCodigoPersonal())){
				ment = mapMentor.get(mentA.getCodigoPersonal()).getMentorId();
			}
			
			if ( !mentor.equals(ment) ){
				mentor = ment;	
	
	
%>      
  <tr> <td colspan="5"> &nbsp;</td></tr>
  <tr> <td colspan="8" style="font-size:12pt;"> <%= aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, mentA.getMentorId(), "NOMBRE")  %></td> </tr>
	  <tr>
	    <th width="3%">N°</th>
	    <th width="8%">Matrícula</th>
	    <th width="25%">Nombre</th>
	    <th width="10%">Plan</th>
	    <th width="15%">Carga</th>
	    <th width="10%">Ent1</th>
	    <th width="10%">Ent2</th>
	    <th width="10%">Ent3</th>
	  </tr> 
<%  	
		
			} 
		if(i%2 == 0){ bgColor = "bgcolor='#CCCCCC'"; }else{ bgColor = "";}
		ent1 = aca.mentores.MentPerfil.getPerfilFolio(conEnoc, mentA.getCodigoPersonal(), cargaId, mentA.getMentorId(), "1" );
		ent2 = aca.mentores.MentPerfil.getPerfilFolio(conEnoc, mentA.getCodigoPersonal(), cargaId, mentA.getMentorId(), "2" );
		ent3 = aca.mentores.MentPerfil.getPerfilFolio(conEnoc, mentA.getCodigoPersonal(), cargaId, mentA.getMentorId(), "3" );
		
		if(mapPersonal.containsKey(mentA.getCodigoPersonal())){
			alumno = mapPersonal.get(mentA.getCodigoPersonal()).getNombreLegal();
		}
		
%>	
    <tr <%=bgColor%>>
      <td align="left"><%= cont %></td>
      <td align="center"><%= mentA.getCodigoPersonal() %></td>
      <td align="left"><%= alumno %></td>
      <td align="center">&nbsp;</td>
      <td align="center"><%= cargaId %></td>
      <td align="center"><%if(ent1.equals("1")) out.print("SI"); else out.print("NO"); %></td>
      <td align="center"><%if(ent2.equals("2")) out.print("SI"); else out.print("NO"); %></td>
      <td align="center"><%if(ent3.equals("3")) out.print("SI"); else out.print("NO"); %></td>
    </tr>  	
<% 		}//fin del for

	}// fin de accesos%> 
	</table> 
  </table>
 </div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>