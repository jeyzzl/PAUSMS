<%@ include file= "../../con_enoc.jsp" %>
<%@page contentType="text/html; charset=iso-8859-1" pageEncoding="iso-8859-1"%>
<%
	String periodoId = request.getParameter("periodo");
	ArrayList<aca.carga.Carga> lisCarga = new aca.carga.CargaUtil().getListAll(conEnoc,"WHERE PERIODO IN ('"+periodoId+"') AND ESTADO = '1' ORDER BY 4,1");
%>
<%	for (aca.carga.Carga carga : lisCarga){%>
		<option value="<%=carga.getCargaId()%>"><%=carga.getCargaId()%> - <%=carga.getNombreCarga()%></option>
<%	}%> 

<%@ include file= "../../cierra_enoc2.jsf" %>