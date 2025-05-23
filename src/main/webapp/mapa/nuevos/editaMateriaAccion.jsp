<%@ include file= "../../con_enoc.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.vista.Maestros"%>
<%@page import="aca.vista.MaestrosUtil"%>

<jsp:useBean id="maestros" class="aca.vista.Maestros" scope="page"/>
<jsp:useBean id="maestrosU" class="aca.vista.MaestrosUtil" scope="page"/>
<%
	Integer accion		= Integer.parseInt(request.getParameter("Accion"));
	
	switch(accion){
		case 5:{//Buscar Maestro
			String frase = "%"+request.getParameter("frase").replaceAll(" ", "%")+"%";
			ArrayList<Maestros> listMaestros = maestrosU.getListAll(conEnoc, "WHERE UPPER(NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO) LIKE UPPER('"+frase+"') ORDER BY NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO");
%>
			<table style="width:100%">
<%
			for(int i = 0; i < listMaestros.size(); i++){
				maestros = (Maestros) listMaestros.get(i);
%>
				<tr class="button" onclick="agregaMaestro('<%=maestros.getCodigoPersonal() %>');">
					<td width="60px" style="border-bottom: dotted 1px gray;"><%=maestros.getCodigoPersonal() %></td>
					<td style="border-bottom: dotted 1px gray;"><%=maestros.getNombre() %> <%=maestros.getApellidoPaterno() %> <%=maestros.getApellidoMaterno() %></td>
				</tr>
<%
			}
			if(listMaestros.size() == 0){
%>
				<tr><td><b>No se encontraron resultados</b></td></tr>
<%
			}
%>
			</table>
<%
		}break;
	}
%>
<%@ include file= "../../cierra_enoc2.jsf" %>