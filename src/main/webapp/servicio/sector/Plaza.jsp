<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="plazaU" scope="page" class="aca.ssoc.PlazaUtil"/>
<jsp:useBean id="plaza" scope="page" class="aca.ssoc.Plaza"/>

<%
		String Institucion_Id			= request.getParameter("Institucion_Id");
		String Plaza_Id					= request.getParameter("Plaza_Id");
		
		ArrayList<aca.ssoc.Plaza> lisPlaza = plazaU.getListAll(conEnoc, Institucion_Id, "ORDER BY 1");
%>
<!-- inicio de estructura -->
<div class="container-fluid">
	<h1>Catálogo de Plazas</h1>
	<div class="alert alert-info">
		<a href="Institucion?Institucion=<%=Institucion_Id%>" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
		<a href="AccionC?Accion=1&Institucion_Id=<%=Institucion_Id%>" class="btn btn-primary">A&ntilde;adir Plaza</a>
	</div>
	<table class="tabla" style="width:80%">
	<tr> 
    	<th width="10%" height="18"><font ><spring:message code="aca.Operacion"/></font></th>
      	<th width="6%" height="10"><font >Id</font></th>
      	<th width="20%" height="18"><font><spring:message code='aca.Descripcion'/></font></th>
      	<th width="8%" height="18"><font ><spring:message code="aca.Institucion"/></font></th>
    </tr>
<%
	for (int i=0; i< lisPlaza.size(); i++){
		aca.ssoc.Plaza plaz = (aca.ssoc.Plaza) lisPlaza.get(i);
%> 
    <tr> 
    	<td width="10%" align="center"><a href="AccionC?Accion=3&Plaza_Id=<%=plaz.getPlaza_Id()%>&Plaza_Nombre=<%=plaz.getPlaza_Nombre()%>&Institucion_Id=<%=plaz.getInstitucion_Id()%>"> Editar</a> 
	  	<a href="AccionC?Accion=4&Plaza_Id=<%=plaz.getPlaza_Id()%>&Institucion_Id=<%=plaz.getInstitucion_Id()%>&Plaza_Nombre=<%=plaz.getPlaza_Nombre()%>" onclick="javascript:alert('Esta seguro de borrar este Documento?');return true"><spring:message code='aca.Borrar'/></a></td>
      	<td width="6%" align="center"><font color="#000000" size="2"><%=plaz.getPlaza_Id()%></font></td>
      	<td width="20%"><font color="#000000" size="2"><%=plaz.getPlaza_Nombre()%></font></td>
      	<td width="8%"><font color="#000000" size="2"><%=plaz.getInstitucion_Id()%></font></td>
    </tr> 
<%
	}
%>
 	</table>
 </div>
<%@ include file= "../../cierra_enoc.jsp" %>