<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CalU" scope="page" class="aca.kardex.KrdxCursoCalUtil"/>
<% 
	ArrayList lisCargas = CalU.getListAll(conEnoc, " WHERE TIPO = 'C' ORDER BY CURSO_CARGA_ID");
	String cargaId = "";
%>
<body>
<div class="container-fluid">
<h1>Periodos Academicos</h1>
<table id="table" class="table table-sm table-bordered">
<thead class="table-info">	 
  <tr>
  	<th width="30%"><spring:message code="aca.Carga"/></th>
  	<th width="5%">N° Sol.</th>
  </tr>
</thead>
<% for (int i=0;i<lisCargas.size();i++){
		aca.kardex.KrdxCursoCal cal = (aca.kardex.KrdxCursoCal) lisCargas.get(i);
		if(!cargaId.equals(cal.getCursoCargaId().substring(0,6))){
			cargaId = cal.getCursoCargaId().substring(0,6);
%> 
  <tr>
    <td><a href="solicitud?CargaId=<%=cargaId%>"><%= aca.carga.CargaUtil.getNombreCarga(conEnoc,cargaId) %></a></td>
    <td align="center"><%= aca.kardex.KrdxCursoCal.numCoreccion(conEnoc, cargaId) %></td>
  </tr>
<% 
		}
	}	
%>  
</table>
</div>
</body>

<%@ include file= "../../cierra_enoc.jsp" %>

