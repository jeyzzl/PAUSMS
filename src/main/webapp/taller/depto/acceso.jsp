<%@ page import= "java.util.* "%>
<%@ page import="aca.bec.spring.BecAcceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoPersonal		= (String)session.getAttribute("codigoEmpleado");
	String ejercicioId			= (String)session.getAttribute("ejercicioId");
	String periodoId			= (String)session.getAttribute("periodoId");
	String periodoNombre 		= (String)request.getAttribute("periodoNombre");	
	String ccostoNombre 		= (String)request.getAttribute("ccostoNombre");	
	String ccosto				= request.getParameter("Ccosto")==null?"0":request.getParameter("Ccosto");
	String mensaje				= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");		
	int row						= 0;
	
	List<BecAcceso> lisAccesos 				= (List<BecAcceso>)request.getAttribute("lisAccesos");
	HashMap<String,String> mapaMaestros 	= (HashMap<String,String>)request.getAttribute("mapaMaestros");	
%>
<style>
	body{
		background:white;
	}
</style>
<body>
<br>
<div class="container-fluid">
	<h2><%=ccostoNombre%><small class="text-muted fs-4">( <%=ejercicioId%> - <%=periodoNombre%> )</small></h2>
	<form name="frmEmpleado" action="grabaracceso?Ccosto=<%=ccosto%>" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<a href="depto" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<input type="text" name="CodigoPersonal" id="CodigoPersonal" class="form-control" placeholder="Número de nómina" maxlength="7" style="width:150px"/>&nbsp;
		<a href="javascript:grabar();" class="btn btn-info"><i class="fas fa-check"></i> Agregar empleado</a>	
	</div>
	</form>
<%	if(mensaje.equals("1")){%>
	<div class="alert alert-info">Se agregó al empleado</div>						
<%}else if(mensaje.equals("2")){%>
	<div class="alert alert-danger">No se puede agregar al empleado porque ya existe</div>
<%}else if(mensaje.equals("3")){%>
	<div class="alert alert-danger">El código no pertenece a ningún empleado</div>
<%}else if(mensaje.equals("4")){%>
	<div class="alert alert-info">Se eliminó al empleado</div>	
<%} %>					
<%	if(!lisAccesos.isEmpty()){%>
	<table class="table table-condensedtable-striped"  align="center">
		<tr>
			<th>Op.</th>
			<th>#</th>
			<th>Código Personal</th>
			<th><spring:message code="aca.Nombre"/></th>
			<th><spring:message code="aca.Fecha"/></th>
			<th><spring:message code='aca.Usuario'/></th>			
		</tr>
<%		
		for(BecAcceso acceso : lisAccesos){ 
			String usuarioAcceso 	= "-";
			String usuarioAlta		= "-";
			if (mapaMaestros.containsKey(acceso.getCodigoPersonal())){
				usuarioAcceso 		= mapaMaestros.get(acceso.getCodigoPersonal());
				usuarioAlta			= mapaMaestros.get(acceso.getUsuario());
			}
			int numEmpleados = acceso.getCodigoPersonal().indexOf(",")+1;
			row++;
%>
		<tr>			
			<td><a href="javascript:borrar('<%=ccosto%>','<%=acceso.getCodigoPersonal()%>');"><i class="fas fa-trash-alt"></i></a></td>
			<td><%=row%></td>
			<td><%=acceso.getCodigoPersonal()%></td>
			<td><%=usuarioAcceso%></td>	
			<td><%=acceso.getFecha() %></td>
			<td><%=usuarioAlta%></td>		
		</tr>
				
<%		} %>
	</table>
	</form>
<%	}else{ %>
	<div align="center"><h3>No hay empleados con privilegios para este departamento</h3></div>
<%	} %>
</body>
<script>
	function grabar(){
		if(document.frmEmpleado.CodigoPersonal.value!=""){
			document.frmEmpleado.submit();	
		}else{
			alert("¡El campo de nómina no puede estar vacío!");				
		}
	}
		
	function borrar(ccosto, codigoPersonal){
		if(confirm("¿Estás seguro que deseas borrar el registro "+codigoPersonal+"?")){
			document.location.href="borraracceso?Ccosto="+ccosto+"&CodigoPersonal="+codigoPersonal;
		}
	}
</script>