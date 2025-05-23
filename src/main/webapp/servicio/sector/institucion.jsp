<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="institucionU" scope="page" class="aca.ssoc.InstitucionUtil"/>
<jsp:useBean id="institucion" scope="page" class="aca.ssoc.Institucion"/>
<%
		String Sector_Id			= request.getParameter("Sector_Id");

		ArrayList<aca.ssoc.Institucion> lisInstitucion = institucionU.getListAll(conEnoc, Sector_Id, "ORDER BY 1");
%>
<!-- inicio de estructura -->
<div class="container-fluid">
<h1>Catálogo de Instituciones</h1>
<div class="alert alert-info">
	<a href="sector.jsp" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
	<a href="accionB?Accion=1&Sector_Id=<%=Sector_Id%>" class="btn btn-primary">A&ntilde;adir Institucion</a>
</div>
  <table class="tabla" style="width:80%">
    <tr> 
      <th width="10%" height="18"><spring:message code="aca.Operacion"/></th>
      <th width="6%" height="10">Id</th>
      <th width="20%" height="18"><spring:message code='aca.Descripcion'/></th>
      <th width="20%" height="18"><spring:message code="aca.Direccion"/></th>
      <th width="8%" height="18"><spring:message code="aca.Telefono"/></th>
      <th width="5%" height="18">Sector</th>
    </tr>
<%
	for (int i=0; i< lisInstitucion.size(); i++){
		aca.ssoc.Institucion sec = (aca.ssoc.Institucion) lisInstitucion.get(i);
%> 
    <tr> 
      <td width="10%" align="center"><a href="accionB?Accion=3&Institucion_Id=<%=sec.getInstitucion_Id()%>&Institucion_Nombre=<%=sec.getInstitucion_Nombre()%>&Sector_Id=<%=sec.getSector_Id()%>&Direccion=<%=sec.getDireccion()%>&Telefono=<%=sec.getTelefono()%>" class="btn btn-primary"> Editar</a> 
	  <a href="accionB?Accion=4&Institucion_Id=<%=sec.getInstitucion_Id()%>&Sector_Id=<%=sec.getSector_Id()%>&Institucion_Nombre=<%=sec.getInstitucion_Nombre()%>" onclick="javascript:alert('Esta seguro de borrar este Documento?');return true" class="btn btn-danger"><spring:message code='aca.Borrar'/></a></td>
      <td width="6%" align="center"><font color="#000000" size="2"><%=sec.getInstitucion_Id()%></font></td>
      <td width="20%"><font color="#000000" size="2"><a href="Plaza?Institucion_Id=<%=sec.getInstitucion_Id()%>" class="btn btn-primary" title="Ir a <%=sec.getInstitucion_Nombre()%>"><%=sec.getInstitucion_Nombre()%></a></font></td>
      <td width="20%"><font color="#000000" size="2"><%=sec.getDireccion()%></font></td>
      <td width="8%"><font color="#000000" size="2"><%=sec.getTelefono()%></font></td>
      <td width="5%"><font color="#000000" size="2"><%=sec.getSector_Id()%></font></td>
    </tr> 
<%
	}
%>
 </table>
 </div>
<%@ include file= "../../cierra_enoc.jsp" %>