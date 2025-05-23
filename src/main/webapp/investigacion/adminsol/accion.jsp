<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="InvEvento" scope="page" class="aca.investiga.InvEvento"/>
<% 
	String codigo			= request.getParameter("Codigo")==null?"0":request.getParameter("Codigo");	
	String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	conEnoc.setAutoCommit(false);
	
	if (accion.equals("1")){
		// Cancelar		
		String estadoOld 	=  aca.investiga.InvEventoUtil.getEstado(conEnoc, folio, codigo);
		String estadoNew 	= String.valueOf(Integer.parseInt(estadoOld)-1).trim();	
		if ( !estadoOld.equals("0") && aca.investiga.InvEventoUtil.updateEstado(conEnoc, folio, codigo, estadoNew)){
				conEnoc.commit();
		}
			
	}else if (accion.equals("2")){
		// Autorizar		
		String estadoOld 	=  aca.investiga.InvEventoUtil.getEstado(conEnoc, folio, codigo);
		String estadoNew 	= String.valueOf(Integer.parseInt(estadoOld)+1).trim();
		
		if ( !estadoOld.equals("3") && aca.investiga.InvEventoUtil.updateEstado(conEnoc, folio, codigo, estadoNew)){
				conEnoc.commit();
		}			
	}
	
	conEnoc.setAutoCommit(true);
%>	
<%@ include file= "../../cierra_enoc.jsp" %>
<% 
	response.sendRedirect("evento");
%>