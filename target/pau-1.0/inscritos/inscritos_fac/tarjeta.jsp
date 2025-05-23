<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>

<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.catalogo.spring.CatReligion"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatCiudad"%>
<%@page import="aca.vista.spring.Inscritos"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String matricula 		= "";
	int cont 				= 0;
	String nombreCarrera 	= "";
	String religion 		= "";
	String edad 			= "";
	String pais 			= "";
	String estado 			= "";
	String ciudad 			= "";
	boolean esInscrito 		= false;
	
	String codigoPersonal 	= (String) request.getAttribute("codigoPersonal");
	String facultad 		= (String) request.getAttribute("facultad");
	String cargas 			= (String) request.getAttribute("cargas");
	String modalidades 		= (String) request.getAttribute("modalidades");
	String nombreFacultad 	= (String) request.getAttribute("nombreFacultad");
	Acceso acceso 			= (Acceso) request.getAttribute("acceso");
	
	List<Inscritos> lisInscritos 						= (List<Inscritos>) request.getAttribute("lisInsc");
	HashMap<String,String> mapaCarrerasFacultad 		= (HashMap<String,String>)request.getAttribute("mapaCarrerasFacultad");
	HashMap<String,CatReligion> mapaReligion 			= (HashMap<String,CatReligion>)request.getAttribute("mapaReligion");
	HashMap<String,String> mapaEdades 					= (HashMap<String,String>)request.getAttribute("mapaEdades");
	HashMap<String,CatPais> mapaPais 					= (HashMap<String,CatPais>)request.getAttribute("mapaPais");
	HashMap<String,String> mapaEstados 					= (HashMap<String,String>)request.getAttribute("mapaEstados");
	HashMap<String,CatCiudad> mapaCiudad 				= (HashMap<String,CatCiudad>)request.getAttribute("mapaEdades");
	HashMap<String,AlumPersonal> mapInscritosEnCargas 	= (HashMap<String,AlumPersonal>)request.getAttribute("mapInscritosEnCargas");
%>
<style>
	TD  {
		font : normal 8pt Verdana, Tahoma;
	}
</style>
<div class="container-fluid">
	<h2><%=nombreFacultad%></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="inscritos">Return</a>
	</div>
	<table style="width:90%"  cellpadding="1" cellspacing="1" class="tabla">
  	<tr><td colspan="3">&nbsp;</td></tr>
  	<tr>
<%	
	for(Inscritos insc : lisInscritos){		
		matricula = insc.getCodigoPersonal();

		if(mapaCarrerasFacultad.containsKey(insc.getCarreraId())){
			nombreCarrera = mapaCarrerasFacultad.get(insc.getCarreraId());
		}
		if(mapaReligion.containsKey(insc.getReligionId())){
			religion = mapaReligion.get(insc.getReligionId()).getNombreReligion();
		}
		if(mapaEdades.containsKey(matricula)){
			edad = mapaEdades.get(matricula);
		}
		if(mapaPais.containsKey(insc.getPaisId())){
			pais = mapaPais.get(insc.getPaisId()).getNombrePais();
		}
		if(mapaCiudad.containsKey(insc.getPaisId()+insc.getEstadoId()+insc.getCiudadId())){
			ciudad = mapaCiudad.get(insc.getPaisId()+insc.getEstadoId()+insc.getCiudadId()).getNombreCiudad();
		}
		if(mapaEstados.containsKey(insc.getPaisId()+insc.getEstadoId())){
			estado = mapaEstados.get(insc.getPaisId()+insc.getEstadoId());
		}
		if(mapInscritosEnCargas.containsKey(matricula)){
			esInscrito = true;
		}
		
		if( acceso.getAccesos().indexOf(insc.getCarreraId())!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){				
			if ((cont % 3) == 0 && cont!=0) { %> 
			</tr><tr><td colspan="3">&nbsp;</td></tr><tr><%
			}
%>
		<td align="center">
		<table background='../../portales/maestro/bg.gif' width="298" height="210"  cellpadding="1">
	  	<tr valign='top' height='165'>
			<td>&nbsp;</td>
			<td width='70' align='center'>
				<table border='1'   >
				<tr>&nbsp;<td bordercolor='#8596CA'><img src="../../foto?Codigo=<%=matricula%>&Tipo=O" width="70"></td></tr>
				</table> 	
			<%=matricula%>
			</td>
			<td>
				<table width='100%'   cellspacing='8'>
					<tr><td><font size=2><b><%=insc.getNombre()+" "+insc.getApellidoPaterno()+" "+insc.getApellidoMaterno()%></b></font></td></tr>
					<tr><td><%=nombreCarrera%></td></tr>
					<tr><td><%=religion%></td></tr>
					<tr><td><%=edad%> years (<%= insc.getfNacimiento()%>)</td></tr>
					<tr><td><%=ciudad%>, <%=estado%>, <%=pais%></td></tr>
					<tr><td><%if(insc.getResidenciaId().equals("E"))out.print("Off-campus");else out.print("Boarding");%>, <%if(esInscrito)out.print("Enrolled");else out.print("Not Enrolled");%></td></tr>
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