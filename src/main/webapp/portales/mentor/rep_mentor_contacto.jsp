<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="contactoU" scope="page" class="aca.mentores.MentContactoUtil"/>

<%	String sMentor 			= (String) session.getAttribute("codigoPersonal");
	String sAccion			= request.getParameter("Accion");
	String sMentorNombre 	= "";
	String sNombreAlumno	= "";
	String sMatricula		= request.getParameter("matricula");
	String sContactoId		= "";
	String sFecha			= "";
	String sDescripcion		= "";
	String sBgcolor			= "";
	ArrayList<aca.mentores.MentContacto> lisMenContacto	= new ArrayList<aca.mentores.MentContacto>();
	
	if(sAccion== null) sAccion="0";
%>

<table style="width:60%; margin:0 auto;">
  <tr>
    <th>MENTOR:&nbsp;[&nbsp;<%=sMentor%>&nbsp;]&nbsp;&nbsp;<%=aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, sMentor, "NOMBRE")%></th>
  </tr>
</table>
<br>
<table style="margin: 0 auto;">
	<tr>
		<td colspan="5"><div align="center"><font size="2">
		<strong>Alumnos Entrevistados</strong></font></div>
		</td>
	</tr>
	<tr>
		<td colspan="5"><div align="center"><a href="frm_mentor_contacto?Accion=<%=sAccion%>&matricula=<%=sMatricula%>">
		<strong> &lsaquo;&lsaquo; Regresar</strong></a></div>
		</td>
	</tr>
	<tr>
		<td colspan="5">&nbsp;</td>
	</tr>
  <tr>
    <th width="7%" height="23">Num</th>
    <th width="12%"><spring:message code="aca.Matricula"/></th>
    <th width="30%"><spring:message code="aca.Nombre"/></th>
    <th width="40%">Motivo</th>
    <th width="11%"><spring:message code="aca.Fecha"/></th>
  </tr>
<%	if (sAccion.equals("1")){
		lisMenContacto= contactoU.getHistorial(conEnoc, sMentor, "ORDER BY 1");
	}else{
		lisMenContacto= contactoU.getLista(conEnoc, sMentor, "ORDER BY 1");
	}
	for(int i=0; i<lisMenContacto.size(); i++){
		aca.mentores.MentContacto contacto= (aca.mentores.MentContacto)lisMenContacto.get(i);
		if((i%2)==0) sBgcolor= ""; else sBgcolor = sColor;
%>
  <tr <%=sBgcolor%>>
    <td align="center"><%=contacto.getContactoId() %></td>
    <td align="center"><%=contacto.getCodigoPersonal() %></td>
    <td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, contacto.getCodigoPersonal(), "NOMBRE") %></td>
    <td><%=aca.mentores.MentMotivo.getMotivoNombre(conEnoc, contacto.getMotivoId()) %></td>
    <td align="center"><%=contacto.getFechaContacto() %></td>
  </tr>
<%	}
	lisMenContacto = null;	%>
</table>
<%@ include file= "../../cierra_enoc.jsp" %>