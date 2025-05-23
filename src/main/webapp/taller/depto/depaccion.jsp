<%@page import="aca.bec.BecAcceso"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "java.util.* "%>

<jsp:useBean id="Usuario" scope="page" class="aca.vista.UsuariosUtil"/>
<jsp:useBean id="BecAcceso" scope="page" class="aca.bec.BecAcceso"/>
<jsp:useBean id="BecAccesoU" scope="page" class="aca.bec.BecAccesoUtil"/>
<jsp:useBean id="ContCcostoU" scope="page" class="aca.financiero.ContCcostoUtil"/>

<%
	String codigoPersonal				= (String)session.getAttribute("codigoPersonal");
	String ejercicioId					= (String)session.getAttribute("ejercicioId");
	String ccosto						= request.getParameter("Ccosto");
	String accion						= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		
	int cont							= 1;
	int numMensaje						= 0;
	
	
	if(accion.equals("1")){//Grabar 
		BecAcceso.setCodigoPersonal(request.getParameter("nomina"));
		BecAcceso.setIdEjercicio(ejercicioId);
		BecAcceso.setIdCcosto(ccosto);
		BecAcceso.setFecha(aca.util.Fecha.getHoy());
		BecAcceso.setUsuario(codigoPersonal);
		if(aca.emp.EmpleadoUtil.existeRegClave(conEnoc, request.getParameter("nomina"))){//Si existe el empleado que estás metiendo
			if(BecAccesoU.existeReg(conEnoc, request.getParameter("nomina"), ejercicioId, ccosto)){//Si existe el empleado en la tabla de privilegios
				numMensaje = 2 ;				
			}else{
				BecAccesoU.insertReg(conEnoc, BecAcceso);
				numMensaje = 1 ;
			}	
		}else{
			numMensaje = 3;
		}
		
		
	}else if(accion.equals("2")){//Borrar
		BecAcceso.setCodigoPersonal(request.getParameter("Elimina"));
		BecAcceso.setIdEjercicio(ejercicioId);
		BecAcceso.setIdCcosto(ccosto);
		BecAccesoU.deleteReg(conEnoc, request.getParameter("Elimina"), ejercicioId, ccosto);
		numMensaje = 4 ;
	}
	
	String usuario							= aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, codigoPersonal,  "NOMBRE");
	ArrayList <aca.bec.BecAcceso> lista 	= BecAccesoU.getListDepartamento(conEnoc, ejercicioId, ccosto," ORDER BY ENOC.EMP_APELLIDO(CODIGO_PERSONAL)");
%>
<style>
	body{
		background:white;
	}
</style>
<body>
<br>
	<div class="container-fluid">
		<form name="frmEmpleado" action="depaccion?Ccosto=<%=ccosto%>" method="post">
			<input type="hidden" name="Accion" value="<%=accion%>"/>
			<input type="hidden" name="Elimina" value=""  />
			<div>
				<h2><%=aca.financiero.ContCcostoUtil.getNombre(conEnoc, ejercicioId, ccosto)%><small class="text-muted fs-4"> Empleados con privilegios</small></h2>
			</div>
			<div class="alert alert-info">
				<a href="depto" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;&nbsp;
				<input type="text" name="nomina" id="nomina" placeholder="Número de nómina" maxlength="7"/>&nbsp;&nbsp;<a href="javascript:grabar();" class="btn btn-info"><i class="icon-ok icon-white"></i> Agregar empleado</a>			
			</div>
<%	if(numMensaje==1){%>
			<div class="alert alert-info">Se agregó al empleado <%=request.getParameter("nomina")%></div>						
<%}else if(numMensaje==2){%>
			<div class="alert alert-danger">No se puede agregar al empleado <%=request.getParameter("nomina")%> porque ya existe</div>
<%}else if(numMensaje==3){%>
			<div class="alert alert-danger">El código <%=request.getParameter("nomina")%> no pertenece a ningún empleado</div>
<%}else if(numMensaje==4){%>
			<div class="alert alert-info">Se eliminó al empleado <%=request.getParameter("Elimina")%></div>	
<%} %>
			
					
			
	<%	if(!lista.isEmpty()){%>
		<table id="table" class="table table-sm table-bordered">
			<thead class="table-info">
				<tr>
					<th>Op.</th>
					<th>#</th>
					<th>Código Personal</th>
					<th><spring:message code="aca.Nombre"/></th>
					<th><spring:message code="aca.Fecha"/></th>
					<th><spring:message code='aca.Usuario'/></th>			
				</tr>
			</thead>	 
	<%	
			for(aca.bec.BecAcceso acceso : lista){
		%>
				<tr>
					
					<td><a href="javascript:borrar('<%=acceso.getCodigoPersonal()%>');"><i class="fas fa-trash-alt"></i></a></td>
					<td><%=cont%></td>
					<td><%=acceso.getCodigoPersonal()%></td>
					<td><%=aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, acceso.getCodigoPersonal(),  "APELLIDO")%></td>	
					<td><%=acceso.getFecha() %></td>
					<td><%=aca.emp.EmpleadoUtil.getNombreCorto(conEnoc, acceso.getUsuario(), "NOMBRE") %></td>		
				</tr>
				
		<% cont++;
			}
	%>
		</table>
	<%
		}else{%>
			<div align="center">
				<h3>No hay empleados con privilegios para este departamento</h3>
			</div>
	<%
		}	
	%>
		</form>
	
		</div>
	</body>
	<script>
		function grabar(){
			if(document.frmEmpleado.nomina.value!=""){
				document.frmEmpleado.Accion.value="1";
				document.frmEmpleado.submit();	
			}else{
				alert("¡El campo de nómina no puede estar vacío!");				
			}
		}
		
		
		function borrar(codigoPersonal){
			if(confirm("¿Estás seguro que deseas borrar el registro "+codigoPersonal+"?")){
				document.frmEmpleado.Accion.value="2";
				document.frmEmpleado.Elimina.value= codigoPersonal;
				document.frmEmpleado.submit();
			}
		}
	</script>
<%@ include file= "../../cierra_enoc.jsp" %>