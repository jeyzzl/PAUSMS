<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AlumConstancia" scope="page" class="aca.alumno.AlumConstancia"/>
<jsp:useBean id="AlumConstanciaU" scope="page" class="aca.alumno.AlumConstanciaUtil"/>

<%

	String constanciaId         = request.getParameter("constanciaId")==null?"0":request.getParameter("constanciaId");

	AlumConstancia = AlumConstanciaU.mapeaRegId(conEnoc, constanciaId);
%>

<link rel="stylesheet" href="../../js/ckeditor/contents.css"></link>


<div class="container-fluid">
	
	<%=AlumConstancia.getConstancia()%>
	
</div>	



<%@ include file= "../../cierra_enoc.jsp" %>