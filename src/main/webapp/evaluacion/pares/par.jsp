<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.vista.MaestrosUtil"%>

<jsp:useBean id="facUtil"  class="aca.catalogo.FacultadUtil" scope="page"/>
<jsp:useBean id="AccesoU"  class="aca.acceso.AccesoUtil" scope="page"/>

<%  
	String codigoPersonal = (String) session.getAttribute("codigoPersonal"); 
	ArrayList lisFac		= new ArrayList();
	lisFac					= facUtil.getListAll(conEnoc,"ORDER BY 1");
	
	int cont				= 0;
	boolean permiso			= false;
	boolean esAdmin			= Boolean.parseBoolean(session.getAttribute("admin")+"");
	boolean esSuper			= aca.acceso.AccesoUtil.esSupervisor(conEnoc, codigoPersonal);
%>
<body>
<div class="container-fluid">
<h1>Asignación de pares</h1>
<div class="alert alert-info"></div>
<table  class="table table-bordered table-striped">
	<tr class="table-info"> 
		<th width="5%"><spring:message code="aca.Numero"/></th>
		<th width="50%"><spring:message code="aca.Facultad"/></th>
		<th width="45%"><spring:message code="aca.Director"/></th>
	</tr>
  
<%
	for (int i=0; i< lisFac.size(); i++){	cont++;
		aca.catalogo.CatFacultad fac = (aca.catalogo.CatFacultad) lisFac.get(i);
		
		// Verifica que el usuario tenga permisos para mirar las facultades
		permiso = aca.acceso.AccesoUtil.getUsuarioPermiso(conEnoc, codigoPersonal, fac.getFacultadId());
		if (permiso==true||esAdmin==true||esSuper){
%>
		<tr>
			<td><font size="2"><%=fac.getFacultadId()%></font></td>
			<td><a href="asignaPar?facultad=<%=fac.getFacultadId()%>"><font size="2"><%=fac.getTitulo()%> de <%=fac.getNombreFacultad()%></font></a></td>
			<td><font size="1"><%=MaestrosUtil.getNombreMaestro(conEnoc,fac.getCodigoPersonal(),"NOMBRE")%></font></td>
		</tr>
<%
		}
	}	
	lisFac	= null;
%>

</table>
<table style="width:57%">
	<tr> 
		<td class="end">¡ Elige tu Facultad !</td>
	</tr>
</table>
</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>