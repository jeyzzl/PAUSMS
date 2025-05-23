<%@ include file= "../../con_enoc.jsp" %>

<%
	String paisId = request.getParameter("paisId")==null?"":request.getParameter("paisId");	
	String estadoId = request.getParameter("estadoId")==null?"":request.getParameter("estadoId");	
	
	aca.catalogo.CiudadUtil ciudadU = new aca.catalogo.CiudadUtil();
	ArrayList<aca.catalogo.CatCiudad> lisCiudad = ciudadU.getLista(conEnoc,paisId, estadoId,"ORDER BY 4");

%>
<div class="edos">	
<%
	for(aca.catalogo.CatCiudad ciudad: lisCiudad){
		out.print(" <option value='"+ciudad.getCiudadId()+"'>"+ ciudad.getNombreCiudad()+"</option>");
	}
%>	
</div>
<%@ include file= "../../cierra_enoc2.jsf" %>