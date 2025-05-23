<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.hca.spring.HcaMaestro"%>
<%@page import="aca.vista.spring.Maestros"%>
<jsp:useBean id="accesoU"  class="aca.acceso.AccesoUtil" scope="page"/>
<jsp:useBean id="acceso"  class="aca.acceso.Acceso" scope="page"/>
<jsp:useBean id="maestroU"  class="aca.hca.HcaMaestroUtil" scope="page"/>
<jsp:useBean id="MaestrosU"  class="aca.vista.MaestrosUtil" scope="page"/>

<%	
	String accion				= (String) request.getAttribute("accion");		
	String codigoPersonal		= (String) request.getAttribute("codigoPersonal");		
	String id 					= (String) request.getAttribute("id");
	boolean accionExiste 		= (boolean) request.getAttribute("accionExiste");		
	boolean esSupervisor 		= (boolean) request.getAttribute("esSupervisor");
	
	List<Maestros> empleados 				= (List<Maestros>) request.getAttribute("empleados");
	List<HcaMaestro> maestros 				= (List<HcaMaestro>) request.getAttribute("maestros");
	HashMap<String, Maestros> mapaMaestros 	= (HashMap<String, Maestros>) request.getAttribute("mapaMaestros");
%>
<div class="container-fluid">
<h3>Empleados</h3>
<table class="table table-condensed">
	<tr>
		<td align='center'>Empleado</td>
		<td align='center'><spring:message code="aca.Nombre"/></td>
	</tr>
<%	if(accion.equals("2")){%>
	<script>
		opener.location.href='docente';		
		window.close();
	</script>
<%
	}else{		
		if(accionExiste){
			if(esSupervisor){				
				for(int i=0;i<empleados.size();i++){
					Maestros maestro = empleados.get(i);
					id = maestro.getCodigoPersonal();
%>
	<tr>
	  <td width='10%'><a href="buscar?id=<%=maestro.getCodigoPersonal()%>&accion=2"><%=maestro.getCodigoPersonal()%></a></td>
	  <td width='90%'><%=maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno()+" "+maestro.getNombre()%></td>
	</tr>
<%				}
			}else{
				for(int i=0;i<maestros.size();i++){
					HcaMaestro maestro =  maestros.get(i);
					id= maestro.getCodigoPersonal();
					
					String nombreMaestro = "";
					Maestros maes = new Maestros();
					if(mapaMaestros.containsKey(maestro.getCodigoPersonal())){
						maes = mapaMaestros.get(maestro.getCodigoPersonal());
						nombreMaestro = maes.getApellidoPaterno()+" "+maes.getApellidoPaterno()+" "+maes.getNombre();
					}
%>
	<tr>
		<td width='10%'><a href="buscar?id=<%=maestro.getCodigoPersonal()%>&accion=2"><%=maestro.getCodigoPersonal()%></a></td>
		<td width='90%'><%= nombreMaestro%></td>
	</tr>
<%
				}
				if(maestros.size() == 0){
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
</div>
