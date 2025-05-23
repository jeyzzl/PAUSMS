<%@ include file= "../../con_enoc.jsp" %>

<%
	String paisId = request.getParameter("paisId")==null?"":request.getParameter("paisId");	

	aca.catalogo.EstadoUtil estadoU = new aca.catalogo.EstadoUtil();
	ArrayList<aca.catalogo.CatEstado> lisEstado = estadoU.getLista(conEnoc, paisId,"ORDER BY 1,3");

%>
<div class="edos">	
<%
	for(aca.catalogo.CatEstado edo: lisEstado){
		out.print(" <option value='"+edo.getEstadoId()+"'>"+ edo.getNombreEstado()+"</option>");
	}
%>	
</div>
<%@ include file= "../../cierra_enoc2.jsf" %>