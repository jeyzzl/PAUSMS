<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.*" %>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.kardex.spring.KrdxCursoCal" %>
<%@page import="aca.kardex.spring.KrdxCursoAct" %>
<%@page import="aca.alumno.spring.AlumPersonal" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>
<script type="text/javascript" src="../../js/popcalendar.js"></script>
<script type="text/javascript">
	function Grabar(){		
		if (confirm("This transaction saves and closes the deferred rating contract, is the information correct?")){
			document.frmDiferida.submit();
		}
	}

	function Cancelar(cursoCargaId){		
		if (confirm("Are you sure you want to delete the delayed notes?")){
			document.location.href = "borrarDiferida?CursoCargaId="+cursoCargaId;
		}
	}	
</script>
</head>
<%
	Fecha fecha 			= new Fecha();
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	String cursoId			= (String)request.getAttribute("cursoId");
	String materia			= (String)request.getAttribute("materia");
	String maestro			= (String)request.getAttribute("maestro");
		
	String yearName			= (String)request.getAttribute("yearName");
	String carreraId		= (String)request.getAttribute("carreraId");
	
	String accion 		 	= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	int Accion				= Integer.parseInt(accion);
	boolean esAdmin 		= (boolean)request.getAttribute("esAdmin");
    String Resultado    	= "";
    
    String institucion 		= (String)request.getAttribute("institucion");
	String nombreFacultad 	= (String)request.getAttribute("nombreFacultad");
	String nombreCarrera	= (String)request.getAttribute("nombreCarrera");
	int ciclo				= (int)request.getAttribute("ciclo");
	String cargaCiclo		= (String)request.getAttribute("cargaCiclo");
	
    List<KrdxCursoAct> lisDiferidas					= (List<KrdxCursoAct>)request.getAttribute("lisDiferidas");
    HashMap<String, KrdxCursoCal> mapaDiferidas 	= (HashMap<String, KrdxCursoCal>) request.getAttribute("mapaDiferidas");
    HashMap<String, AlumPersonal> mapaAlumnos	 	= (HashMap<String, AlumPersonal>) request.getAttribute("mapaAlumnos");
    HashMap<String, String> mapaTipos			 	= (HashMap<String, String>) request.getAttribute("mapaTipos");  
%>
<table class="goback"><tr><td><a class="btn btn-primary" href="evaluar?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro%>&Materia=<%=materia%>&CursoId=<%=cursoId%>&EvaluacionId=<%=request.getParameter("EvaluacionId")%>">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td></tr></table>
<br>
<table style="width:60%; margin:0 auto" class="tabla">
<tr>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>
    <table style="width:100%; margin:0 auto">
      <tr>
        <td><img align="left" src="../../imagenes/logo_small.jpg"></td>
        <td align="right" colspan="4"><font face="arial" size="5"><b><%=institucion%></b></font>
        <br><font size="2"><spring:message code="portal.maestro.diferida.ContratoCalificacionDiferida"/></font></td>
      </tr>
      <tr><td colspan="5"><spring:message code="portal.maestro.diferida.Facultad"/>:&nbsp;&nbsp;<u><%=nombreFacultad%></u></td></tr>
      <tr>
        <td colspan="4"><spring:message code="portal.maestro.diferida.Especialidad"/>:&nbsp;&nbsp;<u><%=nombreCarrera%></u></td>
        <td colspan="1"><spring:message code="portal.maestro.diferida.Grado"/>:&nbsp;<u><%=ciclo%></u></td>
      </tr>
      <tr><td>&nbsp;</td></tr>
      <tr>
        <td colspan="5" align="justify">
		   <spring:message code="portal.maestro.diferida.MensajeUno"/> <u><%=fecha.getDia(Fecha.getHoy())%></u> <spring:message code="portal.maestro.diferida.MensajeDos"/>
		   <u><%=fecha.getMesNombre(Fecha.getHoy()) %></u> <spring:message code="portal.maestro.diferida.De"/> <u><%=yearName %></u>  <spring:message code="portal.maestro.diferida.MensajeTres"/>
        </td>
      </tr>
      <tr><td>&nbsp;</td></tr>
      <tr><td colspan="5"><blockquote><spring:message code="portal.maestro.diferida.Materia"/>:&nbsp;<u><%= materia %></u></blockquote></td></tr>
      <tr><td colspan="5"><blockquote><spring:message code="portal.maestro.diferida.Profesor"/>:&nbsp;<u><%= maestro %></u></blockquote></td></tr>
      <tr>
        <td colspan="1"> <spring:message code="portal.maestro.diferida.CorrespondenSemestre"/></td>
        <td colspan="4"><%=cargaCiclo%> &nbsp;&nbsp;&nbsp;<spring:message code="portal.maestro.diferida.CicloEscolar"/>:&nbsp;&nbsp;<u><%= cursoCargaId.substring(0,4) %></u>
        </td>
      </tr>
      <tr><td>&nbsp;</td></tr>
    </table>
    <form action="grabarDiferida" method="post" name="frmDiferida" target="_self">
	<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">	
    <table style="width:100%; margin:0 auto;" class="tabla">   
      <tr>
      	<th width="2%"><spring:message code="aca.Numero"/></th>
        <th width="8%">N. <spring:message code="portal.maestro.diferida.Mat"/></th>
        <th width="20%"><spring:message code="aca.NombreDelAlumno"/></th>
        <th width="10%"><spring:message code="portal.maestro.diferida.FechaLimite"/></th>
        <th width="7%"><spring:message code="portal.maestro.diferida.TipoCalif"/></th>
        <th width="5%"><spring:message code="portal.maestro.diferida.Calif"/></th>
        <th width="10%"><spring:message code="portal.maestro.diferida.FirmaAlumno"/></th>
      </tr>
<%	
	int row = -1;
	boolean existeDiferida = false;
	for(KrdxCursoAct diferida : lisDiferidas){
		row++;
		String nombreAlumno = "";
		if (mapaAlumnos.containsKey(diferida.getCodigoPersonal())){
			nombreAlumno = mapaAlumnos.get(diferida.getCodigoPersonal()).getNombre()+" "+mapaAlumnos.get(diferida.getCodigoPersonal()).getApellidoPaterno()+" "+mapaAlumnos.get(diferida.getCodigoPersonal()).getApellidoMaterno();
		}
		
		KrdxCursoCal krdxCursoCal = new KrdxCursoCal();
		if (mapaDiferidas.containsKey(diferida.getCodigoPersonal())){	
			existeDiferida = true;
			krdxCursoCal = mapaDiferidas.get(diferida.getCodigoPersonal());
			String nombreTipo = "-";
			if (mapaTipos.containsKey(krdxCursoCal.getTipoCalId())){
				nombreTipo = mapaTipos.get(krdxCursoCal.getTipoCalId());
			}
%>	
	      <tr>
	      	<td align="center"><%=row+1%></td>
	        <td align="center"><%= krdxCursoCal.getCodigoPersonal()%></td>
	        <td align="left"><%=nombreAlumno%></td>
	        <td align="center"><input name="FFinal<%=row%>" type="text" class="text" id="FFinal" size="12" maxlength="10" onfocus="focusFecha(this);" value="<%= krdxCursoCal.getFechaFinal() %>">
	            <input type="hidden" class="button" id="fotoFecha" name="fotoFecha" src="../../imagenes/calendario.gif" onClick="javascript:showCalendar(this, document.getElementById('FFinal<%=row%>'), 'dd/mm/yyyy',null,1,-1,-1);">
	        </td>
	      	<td align="center"><%=nombreTipo%></td>
	        <td align="center"><input name="FCal<%=row%>" style="text-align:right" type="text" class="text" id="FCal" size="3" maxlength="3" value="<%=krdxCursoCal.getNota()%>"></td>	        
	        <td>&nbsp;</td>
	      </tr>
	<% 
		}else{%>
			<tr>
		      	<td align="center"><%=row+1%></td>
		        <td align="center"><%= diferida.getCodigoPersonal()%></td>
		        <td align="left"><%=nombreAlumno%></td>
		        <td align="center"><input name="FFinal<%=row%>" type="text" class="text" id="FFinal" size="12" maxlength="10" onfocus="focusFecha(this);" value="<%= aca.util.Fecha.getHoy() %>">
		            <input type="hidden" class="button" id="fotoFecha" name="fotoFecha" src="../../imagenes/calendario.gif" onClick="javascript:showCalendar(this, document.getElementById('FFinal<%=row%>'), 'dd/mm/yyyy',null,1,-1,-1);">
		        </td>
		        <td>&nbsp;</td>
		        <td align="center"><input name="FCal<%=row%>" style="text-align:right" type="text" class="text" id="FCal" size="3" maxlength="3" value="<%=diferida.getNota()%>"></td>		        
		        <td>&nbsp;</td>
	      	</tr>	
	<%	}
	}
%>
    </table>
    <table style="width:100%; margin:0 auto">
      <tr><td>&nbsp;</td></tr>
      <tr>
        <td colspan="2" align="center">__________________________<br><div align="center"><b><spring:message code="portal.maestro.diferida.FirmaCoordinador"/>(a)</b></div></td>
        <td>&nbsp;</td>
        <td colspan="2" align="center">___________________________<br><div align="center"><b><spring:message code="portal.maestro.diferida.FirmaDocente"/></b></div></td>
      </tr>
      <tr><td>&nbsp;</td></tr>
      <tr>
        <td colspan="5" align="center">
<% 	
	if(existeDiferida){%>        
	        <input class="btn btn-info" type="button" value="PDF" onclick="location='diferidaPDF?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro%>&Materia=<%=materia%>&CursoId=<%=cursoId %>'" />&nbsp; &nbsp;
<% 		if (esAdmin){%>		       	        
	        <input class="btn btn-danger" type="button" value="Cancel" onclick="javascript:Cancelar('<%=cursoCargaId%>','<%=cursoId%>','<%=materia%>','<%=maestro%>')" />
<% 		}%>	        
<%	}else{%>
		<input class="btn btn-primary" type="button" value="Save" onclick="javascript:Grabar()" />
<%	}%>        
        </td>
      </tr>
    </table>
    </form> 
  </td>
  <td>&nbsp;&nbsp;&nbsp;</td>
</tr>
</table>