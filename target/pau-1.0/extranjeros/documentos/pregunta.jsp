<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
	String s_documento			= request.getParameter("f_documento");
%>
<form name="form1" method="post" action="borrar">
  <table style="width:75%"  align="center" class="table table-fullcondensed">
    <tr> 
      <td align="center"><a class="btn btn-primary" href="documentos">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
    </tr>
    <tr> 
      <td align="center">&iquest; Esta seguro de querer borrar el documento ? 
      </td>
    </tr>
    <tr>
      <td align="center">
        <input class="btn btn-primary" type="submit" name="Borrar" value="Aceptar">        
        <input type="hidden" name="f_documento" value="<%=s_documento%>">
      </td>
    </tr>
  </table>
</form>
