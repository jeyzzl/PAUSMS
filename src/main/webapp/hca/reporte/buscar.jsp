<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.hca.HcaMaestro"%>
<jsp:useBean id="accesoU"  class="aca.acceso.AccesoUtil" scope="page"/>
<jsp:useBean id="acceso"  class="aca.acceso.Acceso" scope="page"/>
<jsp:useBean id="maestroU"  class="aca.hca.HcaMaestroUtil" scope="page"/>

<%	String accion	=request.getParameter("accion");
	String sCodigo	= (String)session.getAttribute("codigoPersonal");
	if(accion==null)accion="";
	String id=request.getParameter("id");
	if(id==null)id="";
%>
<h3>Empleados</h3>
<table width='98%' align='center' class="table table-bordered table-condensed">
	<tr>
		<td align='center'>Empleado</td>
		<td align='center'><spring:message code="aca.Nombre"/></td>
	</tr>
<%	if(accion.equals("2")){
		session.setAttribute("codigoEmpleado",id);
%>
	<script>
		opener.location.href='anual';
		window.close();
	</script>
<%
	}else{
		acceso.setCodigoPersonal(sCodigo);
		if(accesoU.existeReg(conEnoc, sCodigo)==true){
			ArrayList empleados = new ArrayList();
			if(Boolean.parseBoolean(session.getAttribute("admin")+"") || accesoU.esSupervisor(conEnoc, sCodigo)==true){
				empleados = maestroU.getListAll(conEnoc, "ORDER BY ENOC.EMP_APELLIDO(CODIGO_PERSONAL)");
				for(int i=0;i<empleados.size();i++){
					HcaMaestro maestro =(HcaMaestro) empleados.get(i);
					id= maestro.getCodigoPersonal();
%>
	<tr>
		<td width='10%'><a href="buscar?id=<%=maestro.getCodigoPersonal()%>&accion=2"><%=maestro.getCodigoPersonal()%></a></td>
		<td width='90%'><%=aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, maestro.getCodigoPersonal(), "APELLIDO")%></td>
	</tr>
<%				}
			}else{
				empleados = maestroU.getListAcceso(conEnoc, sCodigo, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
				for(int i=0;i<empleados.size();i++){
					HcaMaestro maestro =(HcaMaestro) empleados.get(i);
					id= maestro.getCodigoPersonal();
%>
	<tr>
		<td width='10%'><a href="buscar?id=<%=maestro.getCodigoPersonal()%>&accion=2"><%=maestro.getCodigoPersonal()%></a></td>
		<td width='90%'><%= aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, maestro.getCodigoPersonal(), "APELLIDO")%></td>
	</tr>
<%
				}
				if(empleados.size() == 0){
%>
	<tr>
		<td>No tienes Acceso a ver ning&uacute;n empleado!!</td>
	</tr>
<%				
				}
			}
		}else{
%>
	<tr>
		<td>No tienes Acceso a ver ning&uacute;n empleado!!</td>
	</tr>
<%
		}
	}%>
</table>
</form>
<%@ include file= "../../cierra_enoc.jsp" %>