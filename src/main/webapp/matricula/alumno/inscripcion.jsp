<%@page import="aca.financiero.FesCcobro"%>
<%@ include file= "../../con_enoc.jsp" %> 
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="EstadoUtil" scope="page" class="aca.alumno.EstadoUtil"/>
<jsp:useBean id="CargaUtil" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<%	
	String sAccion	= request.getParameter("Accion");
	if (sAccion==null) sAccion = "0";
	int nAccion = Integer.parseInt(sAccion);
	int cont	= 1;

	//declaracion de variables	
	String s_carga			 	= null;
	String s_nombre			 	= null;
	String s_codigo_personal	= null;
	
	ArrayList<aca.alumno.AlumEstado> listaCargas = null;

	switch (nAccion){
		case 0:{
			s_codigo_personal = (String) session.getAttribute("codigoAlumno");
			listaCargas = EstadoUtil.getLista(conEnoc, s_codigo_personal);
			break;
		}
		case 1:{
			
			s_codigo_personal = request.getParameter("codigoPersonal");
			session.setAttribute("codigoAlumno", s_codigo_personal);
			listaCargas = EstadoUtil.getLista(conEnoc, s_codigo_personal);
			break;
		}
	}
	
%>
<head>
</head>
<div class="container-fluid">
<h2><%=AlumUtil.getNombre(conEnoc, s_codigo_personal, "NOMBRE")%> <small class="text-muted fs-4">( <%=s_codigo_personal%> )</small></h2>
<div class="alert alert-info">
</div>
	<table style="width:55%"  class="table table-condensed"> 
	  <tr> 
	  	<th ><b><spring:message code="aca.Numero"/></b></th>
	    <th width="14%" height="20"><b><spring:message code="aca.Carga"/></b></th>
		<th width="60%" height="20"><b><spring:message code="aca.Nombre"/></b></th>
	    <th width="13%" height="20"><b><spring:message code="aca.Bloque"/></b></th>
	    <th width="13%" height="20" align="center"><b><spring:message code="aca.Status"/></b></th>
	    <th width="13%" height="20" align="center"><b><spring:message code="aca.Fecha"/></b></th>
	  </tr>
	<%
		  	for(aca.alumno.AlumEstado ae : listaCargas){
		  		s_carga	 		= ae.getCargaId();
	%>
	  <tr class="tr2">
	    <td><%=cont %></td>
	    <td width="14%"><%=s_carga%></td>
		<td width="60%"><%=CargaUtil.getNombre(conEnoc, s_carga)%></td>
	    <td width="13%" align="center"><%=ae.getBloqueId()%></td>
	    <td width="13%" align="center"><%=ae.getEstado()%></td>
	    <td width="13%" align="center"><%=aca.financiero.FesCcobroUtil.getFecha(conEnoc, ae.getCodigoPersonal(), s_carga) %></td>
	  </tr>
	<%		cont++;} %>
	</table>
</div>
<%@ include file= "../../cierra_enoc.jsp" %> 