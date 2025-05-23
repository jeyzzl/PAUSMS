<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "java.util.*" %>

<jsp:useBean id="acceso" scope="page" class="aca.mentores.MentAcceso"/>

<%	String 	sCodigo	= (String) session.getAttribute("codigoPersonal");
    String  sAc		= "";
    String	sCarrera= "";
    String 	sBgcolor= "";
    int	i			= 0;

	acceso.mapeaRegId(conEnoc, sCodigo);
	sAc = acceso.getAcceso();
	StringTokenizer toke = new StringTokenizer(sAc," ");
%>
<br>
<table style="width:62%" align="center" class="tabla">
  <tr>
  	<td><div align="center">
 	<font color="#000066" size="3"><strong>Carreras Asignadas</strong></font></div></td>
  </tr>
  <tr>
  	<td><div align="center"><a href="portal">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></div></td>
  </tr>
</table>
<br>
<table style="width:62%" align="center" class="tabla">
  <tr>
  	<td><strong>Mensaje:&nbsp;&nbsp;</strong>Click sobre la Carrera!</td>
  <tr> 
    <th><div align="center"><strong>Carreras</strong></div></th>
  </tr>
</table>
<%		while (toke.hasMoreTokens()){
			sCarrera = toke.nextToken();
			if((i%2)==0) sBgcolor = ""; else sBgcolor = sColor;
			i++;
%>
<table style="width:508" height="24"  align="center" class="tabla">
  <tr <%=sBgcolor%>> 
    <td width="458"><li> <a href="../../mentores/mentor_alumno/entra_mentor.jsp?pAcceso=1&carreraId=<%=sCarrera%>"><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc,sCarrera)%></a></td>
  </tr>
</table>
<%  	} //fin del While
		toke = null;
%>
<%@ include file= "../../cierra_enoc.jsp" %> 