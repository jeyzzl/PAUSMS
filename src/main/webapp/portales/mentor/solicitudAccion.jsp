<%@ include file= "../../con_enoc.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.catalogo.CatEstado"%>
<%@page import="aca.catalogo.CatCiudad"%>

<jsp:useBean id="catEstado" scope="page" class="aca.catalogo.CatEstado"/>
<jsp:useBean id="catEstadoU" scope="page" class="aca.catalogo.EstadoUtil"/>
<jsp:useBean id="catCiudad" scope="page" class="aca.catalogo.CatCiudad"/>
<jsp:useBean id="catCiudadU" scope="page" class="aca.catalogo.CiudadUtil"/>
<%
	int accion = Integer.parseInt(request.getParameter("Accion"));

	switch(accion){
		case 5:{// Muestra los estados
			String paisId = request.getParameter("paisId");
			ArrayList<CatEstado> lisEstados	= catEstadoU.getLista(conEnoc, paisId, "ORDER BY NOMBRE_ESTADO");
%>
			objEstado.innerHTML = ''+
<%
			for(int i = 0; i < lisEstados.size(); i++){
				catEstado = (CatEstado) lisEstados.get(i);
%>
				'<option value="<%=catEstado.getEstadoId() %>"><%=catEstado.getNombreEstado() %></option>'+
<%
			}
%>
			'';
<%
			if(lisEstados.size() == 0){
%>
				objEstado.innerHTML = '<option value="0">Sin Estado</option>';
<%
			}
		}break;
		case 6:{//Muestra las ciudades
			String paisId	= request.getParameter("paisId");
			String estadoId	= request.getParameter("estadoId");
			ArrayList<CatCiudad> lisCiudades	= catCiudadU.getLista(conEnoc, paisId, estadoId, "ORDER BY NOMBRE_CIUDAD");
%>
			objCiudad.innerHTML = ''+
<%
			for(int i = 0; i < lisCiudades.size(); i++){
				catCiudad = (CatCiudad) lisCiudades.get(i);
%>
				'<option value="<%=catCiudad.getCiudadId() %>"><%=catCiudad.getNombreCiudad() %></option>'+
<%
			}
%>
			'';
<%
			if(lisCiudades.size() == 0){
%>
				objEstado.innerHTML = '<option value="0">Sin Ciudad</option>';
<%
			}
		}break;
	}
%>
<%@ include file= "../../cierra_enoc2.jsf" %>