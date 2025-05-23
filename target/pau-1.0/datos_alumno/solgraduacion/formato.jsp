<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.alumno.spring.AlumGradua"%>
<%@page import="aca.alumno.spring.AlumGraduaMat"%>
<%@page import="aca.catalogo.spring.CatTipoCurso"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	String institucion 			= (String)session.getAttribute("institucion");
	String planId 				= (String)request.getAttribute("planId");	
	String carreraId 			= (String)request.getAttribute("carreraId");
	AlumPersonal alumPersonal	= (AlumPersonal) request.getAttribute("alumPersonal");
	AlumGradua Gradua 			= (AlumGradua)request.getAttribute("Gradua");
	String facultad 			= (String)request.getAttribute("facultad");	
	String carrera 				= (String)request.getAttribute("carrera");
	int numMatIns 				= (int)request.getAttribute("numMatIns");
	int numCreditos				= 0;	
	
	List<MapaCurso> lisCursos 							= (List<MapaCurso>)request.getAttribute("lisCursos");
	HashMap<String,String> mapaAcreditados 				= (HashMap<String,String>)request.getAttribute("mapaAcreditados");
	HashMap<String,AlumGraduaMat> mapaGraduaMaterias 	= (HashMap<String,AlumGraduaMat>)request.getAttribute("mapaGraduaMaterias");
	HashMap<String,CatTipoCurso> mapaTipos			 	= (HashMap<String,CatTipoCurso>)request.getAttribute("mapaTipos");

	aca.util.Fecha fecha	= new aca.util.Fecha();			
%>
<body>
<table style="margin: 0 auto;  width:98%" class="tabla">
 <tr>
  <td>
  <div style="margin:40px 10px 40px 20px">
  <a href="solicitud" class="btn btn-primary btn-sm"><i class="fas fa-arrow-left"></i></a>
 <table style="margin: 0 auto;  width:100%" class="tabla">
  <tr>
    <td align="center"><font size="3"><b><%=institucion.toUpperCase()%><br>Vicerrectoría Académica</b><br><br><em>Solicitud de candidatura a graduación</em></font>
    </td>
  </tr>  
  <table style="margin: 0 auto;  width:98%"  cellpadding="4">
  <tr>
    <td align="right" colspan="4"><b>Fecha de llenado:</b>&nbsp;<%= fecha.getFecha("2") %></td>
  </tr>  
  <tr>
    <td align="left" class="etiqueta" colspan="4">
      <b>Matrícula:</b>&nbsp;<u><%=alumPersonal.getCodigoPersonal() %>&nbsp;</u>&nbsp;&nbsp;&nbsp;
      <b>A. Paterno:</b>&nbsp;<u><%=alumPersonal.getApellidoPaterno() %>&nbsp;</u>&nbsp;&nbsp;&nbsp;
      <b>A. Materno:</b>&nbsp;<u><%=alumPersonal.getApellidoMaterno() %>&nbsp;</u>&nbsp;&nbsp;&nbsp;
      <b>Nombre:</b>&nbsp;<u><%=alumPersonal.getNombre() %>&nbsp;</u>
    </td>      
  </tr>
  <tr>
    <td align="left" class="etiqueta" colspan="4">
      <b>Facultad:</b> &nbsp; <u><%=facultad%></u>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <b>Carrera: &nbsp; </b><u><%=carrera%></u>
    </td>
  </tr>
  <tr>
    <td colspan="4"><b><font size="2">Análisis de la situación académica</font></b></td>
  <tr>
  <tr>
    <td colspan="4">
      1. El n&uacute;mero de materias que me faltan para concluir la carrera (incluyendo este periodo) es: <b><u>&nbsp;&nbsp;<%= numMatIns+Integer.parseInt(Gradua.getMatPen()) %>&nbsp;&nbsp;</u></b>
    </td>
  <tr>
  <tr>
     <td width="25%">2. Planeo graduar en: 
	 <td width="25%">Fin de curso regular <% if (Gradua.getEvento().equals("R")){ %>
	        <img src="../../imagenes/checked.ico" alt="">
	      <% }else{%>
	      	<img src="../../imagenes/unchecked.ico" alt="">
	      <% }%>&nbsp; &nbsp;</td>
	 <td width="25%">Fin de verano <% if (Gradua.getEvento().equals("V")){ %>
	        <img src="../../imagenes/checked.ico" alt="">
	      <% }else{%>
	      	<img src="../../imagenes/unchecked.ico" alt="">
	      <% }%>&nbsp; &nbsp; </td>
     <td width="25%">
       <b>Fecha:</b>&nbsp;<u><%= Gradua.getFechaGraduacion()%></u>
     </td>
  </tr>
  <tr>
     <td>3. Marcharé por:  
	<td>Diploma de pasante <% if (Gradua.getAvance().equals("P")){ %>
	        <img src="../../imagenes/checked.ico" alt="">
	      <% }else{ %>
	      	<img src="../../imagenes/unchecked.ico" alt="">
	      <% }  %>&nbsp; &nbsp;</td>
	      
	 <td>Titulación  <% if (Gradua.getAvance().equals("T")){ %>
	        <img src="../../imagenes/checked.ico" alt="">
	      <% }else{%>
	      	<img src="../../imagenes/unchecked.ico" alt="">
	      <% }%>&nbsp; &nbsp; </td> 
  </tr>
  <tr>
    <td colspan="4"><b>Nota:</b>&nbsp;El alumno tiene derecho a marchar sólo en un (1) evento de graduación por programa. La única opción 
                    disponible para alumnos de posgrado es la de titulación.  
    </td>
  </tr>
  <tr>
    <td colspan="4">
      Materias inscritas en este periodo: <b><u>&nbsp;&nbsp;<%= numMatIns %>&nbsp;&nbsp;</u></b>
      &nbsp;&nbsp;&nbsp;&nbsp;
      Materias por cursar <b><u>&nbsp;&nbsp;<%= Gradua.getMatPen() %>&nbsp;&nbsp;</u></b>
      &nbsp;&nbsp;&nbsp;&nbsp;
      Créditos por cursar <b><u>&nbsp;&nbsp;<%= numCreditos %>&nbsp;&nbsp;</u></b>
    </td>
  </tr>
    <table style="width:98%" align="center"   border="1">
       <tr>
         <th width="5%" height="20">Sem.</th>
         <th width="55%">Materias</th>
         <th width="5%">Crs.</th>
         <th width="15"><spring:message code="aca.Tipo"/></th>
         <th width="20">Fecha Programada</th>
       </tr>
<%
	for (int i=0; i< lisCursos.size(); i++){
		MapaCurso curso = lisCursos.get(i);
		
		String nombreTipo = "";
		if (mapaTipos.containsKey(curso.getTipoCursoId())){
			nombreTipo = mapaTipos.get(curso.getTipoCursoId()).getNombreTipoCurso();
		}
		
		if ( curso.getTipoCursoId().equals("1")||curso.getTipoCursoId().equals("3")||curso.getTipoCursoId().equals("2")||
				 curso.getTipoCursoId().equals("4")||curso.getTipoCursoId().equals("5")||curso.getTipoCursoId().equals("8") )
			{
				numCreditos	 += Float.parseFloat(curso.getCreditos());
		}else if (curso.getTipoCursoId().equals("7")){
			if ( mapaAcreditados.containsKey(curso.getCursoId()) == false){
				numCreditos += Float.parseFloat(curso.getCreditos());
			}			
		}
		
		AlumGraduaMat alumGraduaMat = new AlumGraduaMat();		
		if (mapaGraduaMaterias.containsKey(codigoAlumno+curso.getCursoId())){
			alumGraduaMat = mapaGraduaMaterias.get(codigoAlumno+curso.getCursoId());			
			if ( curso.getTipoCursoId().equals("1")||curso.getTipoCursoId().equals("3")||
					curso.getTipoCursoId().equals("5")||curso.getTipoCursoId().equals("8") ){
%>
	  <tr>
	    <td align="center" height="20"><%= curso.getCiclo() %>-</td>
	    <td><%= curso.getNombreCurso() %></td>
	    <td align="center"><%= curso.getCreditos() %></td>
	    <td><%= nombreTipo %></td>
	    <td><%= alumGraduaMat.getProgramada() %></td>
	  </tr>	
<%	
			}else if (curso.getTipoCursoId().equals("2") || curso.getTipoCursoId().equals("7")){
%>
	  <tr>
	    <td align="center" height="20"><%= curso.getCiclo() %>*</td>
	    <td>Materia Optativa/Electiva</td>
	    <td align="center"><%= curso.getCreditos() %> </td>
	    <td>OPTATIVA</td>
	    <td><%= alumGraduaMat.getProgramada() %></td>
	  </tr>			
<%			}
		}
	}
%>  	  
    </table>
   <table width=t="98%" align="center" >
   <tr>
     <td colspan="4">&nbsp;</td>
   </tr> 
   <tr>
     <td colspan="4">
       Por este medio me comprometo a cursar las materias pendientes antes de participar en los ejercicios de graduación. Estoy consciente de que si dejo de cumplir con este compromiso perderé el privilegio de participar y deber&eacute; volver a solicitarlo para el próximo evento de graduación.
     </td>
   </tr>
   <tr>
     <td colspan="4">Quedo enterado de que debo entregar el paquete de fotos a más tardar 3 semanas antes de la fecha de graduación.
     </td>
   </tr>
   <tr><td colspan="4">&nbsp;</td></tr>
   <tr><td colspan="4">&nbsp;</td></tr>
   <tr><td colspan="4">&nbsp;</td></tr>
   <tr>
     <td colspan="2"><center>_________________________<br>Alumno Solicitante</center></td>
     <td colspan="2"><center>_________________________<br>Coordinador de Carrera</center></td>
   </tr>
   <tr><td colspan="4">&nbsp;</td></tr>
   <tr>
     <td colspan="4" align="center">El diploma de agradecimiento quiero otorgárselo a: <b><%=Gradua.getDiploma() %></b></td>
   </tr>       
   </table>
  </table>  
 </table>
   </div>
  </td>
  <tr>
</table>
</form>
</body>