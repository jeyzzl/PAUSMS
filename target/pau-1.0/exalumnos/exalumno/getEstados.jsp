<%@ include file= "../../con_enoc.jsp" %>

<jsp:useBean id="estadoU" scope="page" class="aca.exa.ExaEstadoUtil"/>

<%	
	String Pais = request.getParameter("pais");	
	ArrayList<aca.exa.ExaEstado> estados = estadoU.getLista(conEnoc,  Pais, "ORDER BY 1");	
%>
<%for(aca.exa.ExaEstado estado: estados){%>
	<option value="<%=estado.getEstadoId() %>"><%=estado.getNombreEstado() %></option>
<%} %>

<%@ include file= "../../cierra_enoc2.jsf" %>