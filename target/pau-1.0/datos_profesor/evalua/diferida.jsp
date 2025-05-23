<%@ page import="java.text.*" %>
<%@page import="aca.util.Fecha"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="fecha" scope="page" class="aca.util.Fecha"/>
<jsp:useBean id="Actual" scope="page" class="aca.kardex.ActualUtil"/>
<jsp:useBean id="Diferida" scope="page" class="aca.kardex.KrdxCursoCal"/>

<head>
<script type="text/javascript" src="../../js/popcalendar.js"></script>
<script type="text/javascript">

	function Grabar(){		
		if (confirm("Esta operación graba y cierra el contrato de calificación diferida, ¿Está correcta la información?")){
			document.frmDiferida.submit();
		}
	}

	function Cancelar(cursoCargaId,cursoId,materia,maestro){		
		if (confirm("¿Estas seguro de borrar las notas diferidas?")){
			document.location.href = "diferida?Accion=3&CursoCargaId="+cursoCargaId+"&CursoId="+cursoId+"&Materia="+materia+"&Maestro="+maestro;
		}
	}
	
</script>
</head>
<%
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String cursoCargaId		= request.getParameter("CursoCargaId");
	String cursoId			= request.getParameter("CursoId");
	String materia			= request.getParameter("Materia");
	String maestro			= request.getParameter("Maestro");
	
	String strFecha			= aca.util.Fecha.getHoy();
	String yearName			= aca.util.NumberToLetter.convertirLetras(Integer.parseInt(fecha.getYear(strFecha))).trim();
	String carreraOrigen	= aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conEnoc, cursoCargaId);
	String carreraId		= aca.plan.PlanUtil.getCarreraId(conEnoc,carreraOrigen.substring(0,8));
	
	String accion 		 	= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	int Accion				= Integer.parseInt(accion);
	boolean esAdmin 		= Boolean.parseBoolean(session.getAttribute("admin")+""); 
    String Resultado    	= "";
	
	ArrayList lisDiferidas	= Actual.getListDiferida(conEnoc,cursoCargaId, "ORDER BY CODIGO_PERSONAL");
	
    //operaciones a realizar 
	 switch (Accion){
	     	    
	    case 2: { //Grabar las notas diferidas
	    	//System.out.println("Grabando...!");
	    	for (int i=0; i<lisDiferidas.size();i++){
	    		aca.kardex.KrdxCursoAct diferida = (aca.kardex.KrdxCursoAct) lisDiferidas.get(i);
		    	String descripcion = request.getParameter("FFinal"+i);
		    	if (descripcion!= null){
		    		Diferida.setCursoCargaId(cursoCargaId);
		    		Diferida.setCodigoPersonal(diferida.getCodigoPersonal());
		    		Diferida.setCursoId(cursoId);
		    		Diferida.setEstado("S");
		    		Diferida.setFecha(fecha.getHoy());
		    		Diferida.setFechaFinal(request.getParameter("FFinal"+i));
		    		Diferida.setNota(request.getParameter("FCal"+i));
		    		Diferida.setTipo("D");
		    		Diferida.setTipoCalId("6");
		    		Diferida.setTipoNota("O");
		    	
			    	if (Diferida.existeReg(conEnoc) == false){
						if (Diferida.insertReg(conEnoc)){
							Resultado = "!! Los Datos han sido Guardados Correctamente !!";							
						}else{
							Resultado = "No Grabó: "+Diferida.getCursoCargaId();
						}
					}else{	
						if (Diferida.updateReg(conEnoc)){ 
							Resultado = "!! Los Datos han sido Modificados Correctamente !!";							
						}else{
							Resultado = "No Cambió: "+Diferida.getCursoCargaId();
						}
					}
		    	}		    	
	      	}//fin del for
	    	break;
	    }//fin del case 2
	    
		case 3: { //Cancelar las evaluaciones diferidas			
			Diferida.setCursoCargaId(cursoCargaId);
			Diferida.setCursoId(cursoId);
			if(Diferida.tieneDiferidas(conEnoc)){
				if (Diferida.deleteDiferidasMateria(conEnoc)){					
					Resultado = "!! Se borraron las notas diferidas !!";
				}else{
					Resultado = "!! Error al intentar cancelar las notas diferidas !!";
				}
			}	
	    	break;
	    }//fin del case 3
	  
	 }// fin del switch
%>
<table class="goback"><tr><td><a class="btn btn-primary" href="evaluar?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro%>&Materia=<%=materia%>&CursoId=<%=cursoId%>&EvaluacionId=<%=request.getParameter("EvaluacionId")%>">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td></tr></table>
<br>
<table style="width:60%; margin:0 auto;" class="tabla">
<tr>
  <td>&nbsp;&nbsp;&nbsp;</td>
  <td>
    <table style="width:100%; margin:0 auto">
      <tr>
        <td><img align="left" src="../../imagenes/logo_small.jpg"></td>
        <td align="right" colspan="4"><font face="arial" size="5"><b><%=aca.parametros.Parametros.getInstitucion(conEnoc, "1") %></b></font>
        <br><font size="2">Contrato para calificación diferida</font></td>
      </tr>
      <tr><td colspan="5">Facultad:&nbsp;&nbsp;<u><%= aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc,aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc,carreraId))%></u></td></tr>
      <tr>
        <td colspan="4">Especialidad:&nbsp;&nbsp;<u><%= aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc,carreraId)%></u></td>
        <td colspan="1">Grado:&nbsp;<u><%=aca.plan.CursoUtil.getCiclo(conEnoc,cursoId)%></u></td>
      </tr>
      <tr><td>&nbsp;</td></tr>
      <tr>
        <td colspan="5" align="justify">
		   En la Ciudad de Montemorelos, Nuevo León, México, siendo los <u><%=fecha.getDia(Fecha.getHoy())%></u> días del mes de 
		   <u><%=fecha.getMesNombre(Fecha.getHoy()) %></u> de <u><%=yearName %></u>  se levantó la presente acta en
		   donde se solicita aplazar la entrega de califiaciones de la siguiente materia a la fecha
		   mencionada. Si el estudiante no cumple con su compromiso, recibirá la califiación aquí
		   asentada. Una vez cumplidos los requisitos de la materia, la coordinación levantará un
		   acta de califiación ordinaria en la que se consignará la calificación correspondiente.
        </td>
      </tr>
      <tr><td>&nbsp;</td></tr>
      <tr><td colspan="5"><blockquote>Materia:&nbsp;<u><%= materia %></u></blockquote></td></tr>
      <tr><td colspan="5"><blockquote>Profesor:&nbsp;<u><%= maestro %></u></blockquote></td></tr>
      <tr>
        <td colspan="1"> y que corresponden al semestre</td>
        <td colspan="4"><%=aca.carga.CargaUtil.getCiclo(conEnoc,cursoCargaId.substring(0,6))%> &nbsp;&nbsp;&nbsp;del ciclo escolar:&nbsp;&nbsp;<u><%= cursoCargaId.substring(0,4) %></u>
        </td>
      </tr>
      <tr><td>&nbsp;</td></tr>
    </table>
    <form action="diferida?Accion=2" method="post" name="frmDiferida" target="_self">
	<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
	<input type="hidden" name="CursoId" value="<%=cursoId%>">
	<input type="hidden" name="Maestro" value="<%=maestro%>">
	<input type="hidden" name="Materia" value="<%=materia%>">
    <table style="width:100%" align="center" class="tabla">   
      <tr>
      	<th width="2%"><spring:message code="aca.Numero"/></th>
        <th width="8%">N. Mat.</th>
        <th width="20%"><spring:message code="aca.NombreDelAlumno"/></th>
        <th width="10%">Fecha Límite</th>
        <th width="7%">Tipo Calif.</th>
        <th width="5%">Calif.</th>
        <th width="10%">Firma del Alumno</th>
      </tr>
<%	for(int i=0;i<lisDiferidas.size();i++){
		aca.kardex.KrdxCursoAct diferida = (aca.kardex.KrdxCursoAct) lisDiferidas.get(i);
		
		Diferida.setCodigoPersonal(diferida.getCodigoPersonal());
		Diferida.setCursoCargaId(cursoCargaId);
		Diferida.setCursoId(cursoId);
		if(Diferida.existeReg(conEnoc)){
			Diferida.mapeaRegId(conEnoc,diferida.getCodigoPersonal(),cursoCargaId, cursoId);			
	%>	
	      <tr>
	      	<td align="center"><%=i+1%></td>
	        <td align="center"><%=Diferida.getCodigoPersonal()%></td>
	        <td align="left"><%= aca.alumno.AlumUtil.getNombreAlumno(conEnoc,diferida.getCodigoPersonal(),"NOMBRE") %></td>
	        <td align="center"><input name="FFinal<%=i%>" type="text" class="text" id="FFinal" size="12" maxlength="10" onfocus="focusFecha(this);" value="<%= Diferida.getFechaFinal() %>">
	            <input type="hidden" class="button" id="fotoFecha" name="fotoFecha" src="../../imagenes/calendario.gif" onClick="javascript:showCalendar(this, document.getElementById('FFinal<%=i%>'), 'dd/mm/yyyy',null,1,-1,-1);">
	        </td>
	      	<td align="center"><%=aca.catalogo.TipoCalUtil.getNombreCorto(conEnoc, Diferida.getTipoCalId()) %></td>
	        <td align="center"><input name="FCal<%=i%>" style="text-align:right" type="text" class="text" id="FCal" size="3" maxlength="3" value="<%=Diferida.getNota()%>"></td>	        
	        <td>&nbsp;</td>
	      </tr>
	<% 
		}
		else{%>
			<tr>
		      	<td align="center"><%=i+1%></td>
		        <td align="center"><%=diferida.getCodigoPersonal()%></td>
		        <td align="left"><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc,diferida.getCodigoPersonal(),"NOMBRE") %></td>
		        <td align="center"><input name="FFinal<%=i%>" type="text" class="text" id="FFinal" size="12" maxlength="10" onfocus="focusFecha(this);" value="<%= fecha.getHoy() %>">
		            <input type="hidden" class="button" id="fotoFecha" name="fotoFecha" src="../../imagenes/calendario.gif" onClick="javascript:showCalendar(this, document.getElementById('FFinal<%=i%>'), 'dd/mm/yyyy',null,1,-1,-1);">
		        </td>
		        <td align="center">&nbsp;</td>
		        <td align="center"><input name="FCal<%=i%>" style="text-align:right" type="text" class="text" id="FCal" size="3" maxlength="3" value="<%=diferida.getNota()%>"></td>		       
		        <td>&nbsp;</td>
	      	</tr>	
	<%	}
	}
%>
    </table>
    <table style="width:100%; margin:0 auto">
      <tr><td>&nbsp;</td></tr>
      <tr>
        <td colspan="2" align="center">__________________________<br><div align="center"><b>Firma del Coordinador(a)</b></div></td>
        <td>&nbsp;</td>
        <td colspan="2" align="center">___________________________<br><div align="center"><b>Firma del Docente</b></div></td>
      </tr>
      <tr><td>&nbsp;</td></tr>
      <tr>
        <td colspan="5" align="center">
<% 
	Diferida.setCodigoPersonal(Diferida.getCodigoPersonal());
	Diferida.setCursoId(Diferida.getCursoId());
	Diferida.setCursoCargaId(Diferida.getCursoCargaId());
	if(Diferida.existeReg(conEnoc)){%>        
	        <input class="btn btn-info" type="button" value="PDF" onclick="location='diferidaPDF?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro%>&Materia=<%=materia%>&CursoId=<%=cursoId %>'" />&nbsp; &nbsp;
<% 		if (esAdmin){%>		       	        
	        <input class="btn btn-danger" type="button" value="Cancelar" onclick="javascript:Cancelar('<%=cursoCargaId%>','<%=cursoId%>','<%=materia%>','<%=maestro%>')" />
<% 		}%>	        
<%	}else{%>
		<input class="btn btn-primary" type="button" value="Guardar" onclick="javascript:Grabar()" />
<%	}%>        
        </td>
      </tr>
    </table>
    </form> 
  </td>
  <td>&nbsp;&nbsp;&nbsp;</td>
</tr>
</table>
<%@ include file= "../../cierra_enoc.jsp" %>