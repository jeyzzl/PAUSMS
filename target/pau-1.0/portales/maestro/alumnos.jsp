<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.vista.spring.AlumnoCurso"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
 	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");	
	String maestroNombre 		= request.getParameter("Maestro");
	String materiaNombre 		= request.getParameter("Materia");
	
	List<AlumnoCurso> lisAlumnos				= (List<AlumnoCurso>)request.getAttribute("lisAlumnos");
	HashMap<String,AlumPersonal> mapaAlumnos	= (HashMap<String,AlumPersonal>)request.getAttribute("mapaAlumnos");
	
	String sFraseBaja		= "xxxxxxxxxx¡B.A.J.A!xxxxxxxxxxx";
	String sFraseTitulo		= "xxxxxxxxx¡T.I.T.U.L.O!xxxxxxxx";
	int j=0, i=0, row=0;	
%>
<table style="width:100%; margin:0 auto;" cellspacing="1" cellpadding="1">
  <tr><td align="CENTER" valign="TOP"><strong>U N I V E R S I T Y &nbsp; O F &nbsp; M O N T E M O R E L O S , &nbsp; A. C.</strong></td></tr>
<% /* 
  <tr>
	<td align="CENTER" valign="TOP">
	  <a href="javascript:window.print()">
	    <img src="../../imagenes/printer.gif"  height="18" alt="Imprimir">
	  </a>
	</td>
  </tr>
  */
 %> 
  <tr>
    <td align="center">
	  <font color="#000099"><strong>[ <%=codigoPersonal%> ] - <%=maestroNombre%><br><%=materiaNombre%></strong></font><br>&nbsp;
	</td>
  </tr>
  <tr><td align="center"><b>PERSONAL USE FORM</b></td></tr>  
</table>

<table border="1" style="margin:0 auto;" bordercolor="#000000">
  <tr align="CENTER" style="font-size: 10pt;"> 
    <th rowspan="2" valign="top" align="CENTER"><b><spring:message code="aca.Numero"/></b></th>
    <th rowspan="2" valign="top" align="CENTER"><b>Code</b></th>
    <th rowspan="2" valign="top" align="CENTER"><b><spring:message code="aca.Nombre"/></b></th>
    <th rowspan="2" valign="top" align="CENTER"><b><spring:message code="aca.Plan"/></b></th>
    <th colspan="30" align="CENTER"><b>Cycles</b></th>
  </tr>
  <tr> 
<%
	for(j=1;j<31;j++){ %>
    <th style="font-size: 06pt;" align="CENTER"><b><%=j%></b></th>
    <%	} %>
  </tr>
<%
	for (AlumnoCurso ac : lisAlumnos){
		if (!ac.getTipoCalId().equals("M")){
			row++;
			String nombreAlumno = "-";
			if (mapaAlumnos.containsKey(ac.getCodigoPersonal())){
				nombreAlumno = mapaAlumnos.get(ac.getCodigoPersonal()).getNombre()+" "+mapaAlumnos.get(ac.getCodigoPersonal()).getApellidoPaterno()+" "+mapaAlumnos.get(ac.getCodigoPersonal()).getApellidoMaterno();
			}			
%>
  <tr> 
    <td align="center"  style="font-size: 07pt;"><%=row%></td>
    <td style="font-size: 07pt;" align="center"><%=ac.getCodigoPersonal()%></td>
    <td width="220" align="left"  style="font-size: 07pt;">&nbsp;<%=nombreAlumno%></td>
    <td style="font-size: 07pt;" align="center"><%=ac.getCursoId().substring(0,8)%></td>
<% 			for (j=0;j<30;j++){
				if (ac.getTipoCalId().equals("3")){ %>
    <td width="15" align="center"> <font color="#990000" size="2" face="Arial Narrow, Arial"><b><%=sFraseBaja.substring(j,j+1)%></b></font></td>
<%				}else if (ac.getTitulo().equals("S")){  %>    
	<td width="15" align="center"> <font color="#990000" size="2" face="Arial Narrow, Arial"><b><%=sFraseTitulo.substring(j,j+1)%></b></font></td>
<%				}else{  %>
    <td width="15">&nbsp;</td>
<%				}
			}
		} // fin de tipoCal!= 'M'
%>
  </tr>
<%	}%>
</table>
<!-- fin de estructura -->