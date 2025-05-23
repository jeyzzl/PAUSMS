<%@page import="java.util.List"%>
<%@page import="aca.leg.spring.LegDocumento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	List<LegDocumento> lisDocumentos = (List<LegDocumento>)request.getAttribute("lisDocumentos");
%>
<div class="container-fluid">
<h2>Legal Documents</h2>
<div class="alert alert-info">
	<a href="grabar" class="btn btn-primary">Add Document</a>		
</div>
  <table style="width:80%" >
  </table>
  <table   dwcopytype="CopyTableRow" class="table table-bordered table-sm">
    <tr class="table-info"> 
      <th width="5%" height="18"><font color="black"><spring:message code="aca.Operacion"/></font></th>
      <th width="5%" height="18"><font color="black">Id</font></th>
      <th width="30%" height="18"><font color="black"><spring:message code='aca.Descripcion'/></font></th>
      <th width="30%" height="18"><font color="black">Imagee</font></th>
    </tr>
<%	for (LegDocumento doc : lisDocumentos){ %>
    <tr class="tr2"> 
      <td width="5%" align="center">
	      <a class="fas fa-edit" href="editar?f_documento=<%=doc.getIdDocumentos()%>&f_descripcion=<%=doc.getDescripcion()%>&f_imagen=<%=doc.getImagen()%>"></a> 
		  <a class="fas fa-trash-alt" href="pregunta?f_documento=<%=doc.getIdDocumentos()%>"></a></td>
      <td width="5%" align="center"><font color="#000000" size="2"><%=doc.getIdDocumentos()%></font></td>
      <td width="30%"><font color="#000000" size="1"><%=doc.getDescripcion()%></font></td>
      <td width="30%"><font color="#000000" size="1"><%=doc.getImagen()%></font></td>
    </tr>
<%	} %>
</table>
</div>