<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.rol.spring.Rol"%>
<%@ page import= "aca.rol.spring.RolOpcion"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%	
	String accionFmt 		= request.getParameter("accionFmt")==null?"0":request.getParameter("accionFmt");
	
	List<Rol> lisRol 						= (List<Rol>)request.getAttribute("lisRol");
	HashMap<String,String> mapaOpcionesMenu	= (HashMap<String, String>)request.getAttribute("mapaOpciones");
%>

<html>
<head>
<style>		
	body{
		background : white;
	}
</style>
<body>
<div class="container-fluid" >
	<h1>System Roles</h1>
	<div class="alert alert-info" align="left">
		<a href="editar" class="btn btn-info"><i class="fas fa-edit"></i> Add</a>
	</div>
	
<%	if(accionFmt.equals("1")){	%>
	<div class="alert alert-info"><h3><spring:message code='aca.SeElimino'/></h3></div>
<%	} %>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th>Op.</th>
		<th>Role Id</th>
		<th>Name of Role</th>
		<th>Privileges</th>
	</tr>
	</thead>
<%
	int cont = 1;
	for(Rol roles : lisRol){
		
		String numOpciones = "0";	
		if (mapaOpcionesMenu.containsKey(roles.getRolId())){
			numOpciones = mapaOpcionesMenu.get(roles.getRolId());
		}
%>
	<tr>
		<td><%=cont %></td>
		<td>
			<a href="editar?rolId=<%=roles.getRolId()%>"><i class="fas fa-edit"></i></a>
<%	if (numOpciones.equals("0")){%>			
			<a href="javascript:Borrar('<%=roles.getRolId()%>');"><i class="fas fa-trash-alt"></i></a>
<%	} %>			
		</td>
		<td><%=roles.getRolId() %></td>
		<td>
		  <a href="opciones?rolId=<%=roles.getRolId()%>">
			<%=roles.getRolNombre() %>
		  </a>
		</td>
		<td>
			<a href="menuopciones?rolId=<%=roles.getRolId()%>&rolNombre=<%=roles.getRolNombre()%>">
				Options <%=numOpciones%>
			</a>
		</td>
	</tr>
<% 
		cont++; 
	}
%>
		</table>
</div>		
</body>
</html>
<script>
	function Borrar(rolId) {
		if (confirm("Are you sure you want to delete this role?") == true) {
			document.location.href = "borrar?rolId="+ rolId;
		}
	}
</script>