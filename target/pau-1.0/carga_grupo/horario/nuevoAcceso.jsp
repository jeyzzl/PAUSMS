<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatHorario"%>
<%@page import="aca.catalogo.spring.CatHorarioAcceso"%>
<%@page import="aca.vista.spring.Maestros"%>
 
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<style>
	.titulo{
		display: flex;
		justify-content: center;
	}
	
	.boxAccess{
		width: 400px;
		height: 350px;
		margin-bottom: 4rem;
		color: white;
		padding: 40px;
		background-color: #428bca;
		box-shadow: 0px 0px 10px #000;
		border-radius: 10px;
		margin-right: 2rem;
	}
	.alinearCentro{
		padding: 20px;
	}
	.botonAdd{
		margin-top: 1rem;
		display: flex;
		justify-content: center;
	}
	
	.centrar{
		display: flex;
		justify-content: center;
	}
	
	.tableAccess {
		width: 100%;
		margin-bottom: 2rem;
		color: white;
		background-color: #428bca;
		box-shadow: 0px 0px 10px #000;
		border-radius: 10px;
	}
</style>

<html>
<body>
<%	
	String horarioId 	= request.getParameter("HorarioId")==null?"0":request.getParameter("HorarioId");
	String mensaje 		= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	CatHorario horario 	= (CatHorario)request.getAttribute("horario");
	
	String getNumUsuarios 						= (String) request.getAttribute("getNumUsuarios");
	List<CatHorarioAcceso> listaHorarioId 		= (List<CatHorarioAcceso>) request.getAttribute("listaHorarioId");
	List<Maestros> listaEmpleados 				= (List<Maestros>) request.getAttribute("listaEmpleados");	
	List<Maestros> listaFilterUsuarios			= (List<Maestros>) request.getAttribute("listaFilterUsuarios");
	HashMap<String, String> mapaEmpleados 		= (HashMap<String, String>) request.getAttribute("mapaNombreEmpleado");
%>
<div class="container-fluid">
	<h2>Manage Access</h2>	  
	<div class="alert alert-info">
		<a href="listado?HorarioId=<%=horarioId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		<span><%=mensaje.equals("-") ? "" : mensaje %></span>
	</div>
	<div class="row alinearCentro">
		<div class="boxAccess col-md-3">
		<form name="frmAcceso" action="grabarAcceso" method="post">
			<h3 class="centrar">New User Access</h3>
			<label class="form-label">Schedule Id*</label>		
			<input type="text" class="input-large form-control" name="HorarioId" id="HorarioId" value="<%=horarioId%>" width="50%"/ readonly>
			<br>
			<label class="form-label">Select a User</label>
			<select id="EmpleadoId" name="EmpleadoId" class="form-control select2">
				<option value="">Search</option>
		<%		for(Maestros maestro : listaFilterUsuarios){%>
			<% if(maestro.getCodigoPersonal().equals("9800308")){%>
				<option value="<%=maestro.getCodigoPersonal()%>"><%=maestro.getCodigoPersonal()%></option>
			<% }else { %>
				<option value="<%=maestro.getCodigoPersonal()%>"><%=maestro.getCodigoPersonal()%> <%=maestro.getApellidoPaterno()%> <%=maestro.getApellidoMaterno()%></option>
			<% } %>	
		<%		} %>
			</select>
			<div class="botonAdd">
				<a class="btn btn-primary" onclick="javascript:Guardar();"><i class="fas fa-plus"></i> Add User</a>
			</div>		
		</form>	
		</div>
		<div class="col-md-9">
		<table class="table tableAccess">
		  <thead>
		    <tr>
		      <th scope="col">Timetable ID</th>
		      <th scope="col">Option</th>
		      <th scope="col">ID</th>
		      <th scope="col">Name</th>
		    </tr>
		  </thead>
	  <% 
	  	int row = 1;
	  	String nombreEmpleado = "-";
	  	for(CatHorarioAcceso access : listaHorarioId){
	  		//Hacer un split de cada uno para poder mostrarlo
	  		if(mapaEmpleados.containsKey(access.getCodigoPersonal())){
	  			nombreEmpleado = mapaEmpleados.get(access.getCodigoPersonal());
	  		}
	  %>
		  <tbody class="table-hover">
		    <tr>
		      <td scope="row"><%=row%></td>
		      <td><a title="Delete" href="javascript:DeleteUser('<%=horarioId%>','<%=access.getCodigoPersonal()%>')"><i class="fas fa-trash text-danger fa-1x"></i></a></td>
		      <td><%=access.getCodigoPersonal()%></td>
		      <td>
      <%		if(access.getCodigoPersonal().equals("9800308")){%>
		      	<%=nombreEmpleado.substring(0, 5)%>
	  <%} 		else {	%>
	  			<%=nombreEmpleado%>
	  <%} 				%>
		      </td>
		    </tr>
		  </tbody>
	<% } %>
		</table>
		</div>
	</div>
</div>
</body>
<script>
	function Guardar() {
		if (document.frmAcceso.EmpleadoId.value != "" || document.frmAcceso.EmpleadoId.value != "Search") {			
			document.frmAcceso.submit();
		} else {
			alert("¡Completa todos los campos!");
		}
	}
	
	function DeleteUser(horarioId, codigoPersonal){
		if(confirm("Are you sure to delete this user "+codigoPersonal+"?")==true){
			document.location="borrarUsuario?HorarioId="+horarioId+"&CodigoPersonal="+codigoPersonal;
	  	}
	}
    
	$('#EmpleadoId').select2({
	});
	
</script>
</html>