<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="motivoU" scope="page" class="aca.mentores.MentMotivoUtil"/>

<% 	String  sModulo			= request.getParameter("moduloId"); 
    String  sCarpeta     	= request.getParameter("carpeta");		
	ArrayList lisMenMotivo		= new ArrayList();
	lisMenMotivo			= motivoU.getListAll(conEnoc, "ORDER BY 1");
%>
<br>
<table style="width:60%; margin:0 auto;">
   <tr> 
    <td height="21">
	<div align="center"><strong><font color="#000033" size="3">
	Reasons Catalog</font></strong></div></td>
  </tr>
  <tr> 
    <td>
	<div align="center"><strong><font color="#000033" size="1">
	<a href="motivos.jsp?moduloId=<%=sModulo%>&carpeta=<%=sCarpeta%>">
	Return</a></font></strong></div></td>
  </tr>
</table>
<br>

<table class="table table-striped" style="width:314" align="center">
  <tr> 
    <th align="center"><spring:message code="aca.Clave"/></th>
    <th>&nbsp;&nbsp;Description</th>
  </tr>
  <%  for(int i=0; i<lisMenMotivo.size(); i++){
		aca.mentores.MentMotivo motivo = (aca.mentores.MentMotivo) lisMenMotivo.get(i);		
%>
  <tr> 
    <td align="center"><%=motivo.getMotivoId()%></td>
    <td><%=motivo.getMotivoNombre()%></td>
  </tr>
<%	}
  	lisMenMotivo = null;
%>
</table>

<%@ include file= "../../cierra_enoc.jsp" %>