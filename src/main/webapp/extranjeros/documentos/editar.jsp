<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String s_documento			= request.getParameter("f_documento");
    String s_descripcion		= request.getParameter("f_descripcion");
	String s_imagen				= request.getParameter("f_imagen");
%>
<form name="form1" method="post" class="form-control" action="editar_documento">
  <table style="width:46%"  align="center">
    <tr> 
      <td align="center"><a href="documentos" class="btn btn-primary">Regresar</a></td>
    </tr>
  </table>
  <table style="width:46%"  align="center" class="table">
    <tr> 
      <td width="22%"><b>Documento </b></td>
      <td width="78%"> 
        <input type="text" class="form-control" name="f_doc" value= "<%=s_documento%>" disabled="disabled">
        <input type="hidden" name="f_documento" value = "<%=s_documento%>">
      </td>
    </tr>
    <tr> 
      <td width="22%" height="31"><b>Descripcion </b></td>
      <td width="78%" height="31"> 
        <input type="text" class="form-control" name="f_descripcion" value="<%=s_descripcion%>" maxlength="60" size="60">
      </td>
    </tr>
    <tr> 
      <td width="22%"><b>Imagen </b></td>
      <td width="78%"> 
        <input type="text" class="form-control" name="f_imagen" value="<%=s_imagen%>">
      </td>
    </tr>
    <tr>
      <td width="22%">&nbsp;</td>
      <td width="78%">
        <input class="btn btn-primary" type="submit" name="Submit" value="Actualizar">
      </td>
    </tr>
  </table>
  </form>
<p>&nbsp;</p>