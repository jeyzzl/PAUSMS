<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="InvBitacoraU" scope="page" class="aca.investiga.InvBitacoraUtil"/>
<jsp:useBean id="Usuario" scope="page" class="aca.vista.Usuarios"/>
<%
	String codigo			= (String) session.getAttribute("codigoPersonal");
	String proyectoId 		= request.getParameter("ProyectoId")==null?"0":request.getParameter("ProyectoId");

	ArrayList<aca.investiga.InvBitacora> lisBitacora = InvBitacoraU.getListProyecto(conEnoc, proyectoId, " ORDER BY FOLIO");
	
%>

<html>
<head>
<body>
	<div class="container-fluid">
		<h1>Bitácora</h1>
		<div class="alert alert-info">
			<a href="proyecto" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
		</div>
	
	<table class="table table-sm table-bordered">  
		<thead>
		<tr>
    		<td colspan="5" height="20" align="center"><strong><font size="2">Proyecto: [ <%=proyectoId%> ] &nbsp; <%=aca.investiga.InvProyectoUtil.getNombreProyecto(conEnoc, proyectoId)%></font></strong></td>
  		</tr>	
		</thead>
		<thead class="table-info">
		<tr>
			<th width="3%">Referencia</th>
			<th width="10%"><spring:message code="aca.Nombre"/></th>
			<th width="7%"><spring:message code="aca.Fecha"/></th>
			<th width="7%">Estado Ant.</th>
			<th width="7%">Estado Actual</th>
			
		</tr>
		</thead>
<%	
	String estadoOld = "";
	String estadoNew = "";
	for (int i=0; i<lisBitacora.size();i++){
		aca.investiga.InvBitacora bitacora = (aca.investiga.InvBitacora) lisBitacora.get(i);
		if(bitacora.getEstadoOld().equals("0")) estadoOld = "Capturado"; ;
		if(bitacora.getEstadoOld().equals("1")) estadoOld = "Comité Inv.";
		if(bitacora.getEstadoOld().equals("2")) estadoOld = "Comité Ética";
		if(bitacora.getEstadoOld().equals("3")) estadoOld = "DPI";
		if(bitacora.getEstadoOld().equals("4")) estadoOld = "DPI Registrado";
		if(bitacora.getEstadoNew().equals("0")) estadoNew = "Capturado"; 
		if(bitacora.getEstadoNew().equals("1")) estadoNew = "Comité Inv.";
		if(bitacora.getEstadoNew().equals("2")) estadoNew = "Comité Ética";
		if(bitacora.getEstadoNew().equals("3")) estadoNew = "DPI";
		if(bitacora.getEstadoNew().equals("4")) estadoNew = "DPI-Registrado";
%>		
		<tr>
			<td><%= bitacora.getFolio() %></td>
			<td><%= aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, bitacora.getUsuario(), "NOMBRE") %></td>
			<td><%= bitacora.getFecha() %></td>
			<td><%= estadoOld %></td>
			<td><%= estadoNew %></td>
		</tr>
<%	} %>
	</table>
	</div>	
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>