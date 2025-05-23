<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="aca.vista.spring.Usuarios"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function BorrarUsuario(usuario, menu, opcion){
		if(confirm("Are you sure you want to remove ALL access for this user?")){
			document.location.href="aplicacion?Accion=4&opcion="+opcion+"&id="+menu+"&Usuario="+usuario;
		}		
	}
	
	function BorrarOpcion(usuario, menu, opcion){
		if(confirm("Are you sure you want to remove THIS access for the user?")){
			document.location.href="aplicacion?Accion=3&opcion="+opcion+"&id="+menu+"&Usuario="+usuario;
		}
	}
</script>
<%		
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal");
	String opcion			= request.getParameter("opcion")==null?"0":request.getParameter("opcion");
	String menuId			= request.getParameter("id")==null?"0":request.getParameter("id");	
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String mensaje			= (String)request.getAttribute("mensaje");
	String nombreOpcion		= (String)request.getAttribute("nombreOpcion");
	boolean esAdmin			= (boolean)request.getAttribute("esAdmin");
	
	List<Usuarios> lisUsuarios 					= (List<Usuarios>)request.getAttribute("lisUsuarios");
	HashMap<String,String> mapaInscritos 		= (HashMap<String,String>)request.getAttribute("mapaInscritos");
	HashMap<String,Maestros> mapaMaestros 		= (HashMap<String,Maestros>)request.getAttribute("mapaMaestros");
	HashMap<String,String> mapaUsuarios 		= (HashMap<String,String>)request.getAttribute("mapaUsuarios");
	HashMap<String,Acceso> mapaAccesos 			= (HashMap<String,Acceso>)request.getAttribute("mapaAccesos");	
%>
<div class="container-fluid">
	<h1>Users with access<small class="text-muted fs-4">( <%= nombreOpcion %> )</small></h1>
	<div class="alert alert-info">
		<a href="modulos?id=<%=menuId %>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
<%	if (esAdmin){%>		
		<a class="btn btn-info" href="aplicacion?Accion=1&opcion=<%=opcion%>&id=<%=menuId%>"><i class="fas fa-plus"></i> Access/All/Employee</a>&nbsp;
		<a class="btn btn-info" href="aplicacion?Accion=2&opcion=<%=opcion%>&id=<%=menuId%>"><i class="fas fa-trash-alt icon-white"></i> Remove/All/Access</a>&nbsp;
<%	} %>		
	</div>
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>		
	<table class="table table-bordered table-sm">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th>ID</th>
		<th>User</th>
		<th>Admin</th>
		<th>Supervisor</th>
		<th>Status</th>
<%	if (esAdmin){%>		
		<th>Delete</th>
<%	}%>
	</tr>
	</thead>
<%
	int row	= 0;
	for (Usuarios usuario : lisUsuarios){		
		String estado = "-";
		if (usuario.getCodigoPersonal().substring(0, 1).equals("9")){			
			if (mapaMaestros.containsKey(usuario.getCodigoPersonal())){
				estado = mapaMaestros.get(usuario.getCodigoPersonal()).getEstado();
			}else{
				estado = "I";
			}			
		}else{
			if (mapaInscritos.containsKey(usuario.getCodigoPersonal())){
				estado = "Enrolled";
			}else{
				estado = "Not Enrolled"; 
			}
		}		
		boolean esUserAdmin		= false;
		boolean esUserSuper		= false;
		if (mapaAccesos.containsKey(usuario.getCodigoPersonal())){
			esUserAdmin = mapaAccesos.get(usuario.getCodigoPersonal()).getAdministrador().equals("S")?true:false;
			esUserSuper = mapaAccesos.get(usuario.getCodigoPersonal()).getSupervisor().equals("S")?true:false;
		}
%>
	<tr>
		<td><%=++row%></td>
	    <td class="ayuda alumno <%=usuario.getCodigoPersonal()%>"><%=usuario.getCodigoPersonal()%></td>
	    <td><%=(usuario.getApellidoMaterno().equals("-")?"":usuario.getApellidoMaterno())+" "+usuario.getApellidoPaterno()+" "+usuario.getNombre()%></td>
	    <td><%=esUserAdmin?"YES":"NO"%></td>
	    <td><%=esUserSuper?"YES":"NO"%></td>
	    <td><%=estado%></td>
<%		if (esAdmin){%>    	    
	    <td>
	    	<a href="javascript:BorrarOpcion('<%=usuario.getCodigoPersonal()%>','<%=menuId%>','<%=opcion%>');"><i class="fas fa-trash" ></i>Option</a>&nbsp;&nbsp;&nbsp;&nbsp;
	    	<a href="javascript:BorrarUsuario('<%=usuario.getCodigoPersonal()%>','<%=menuId%>','<%=opcion%>');"><i class="fas fa-trash" ></i>All</a>&nbsp;&nbsp;	    	
	    </td>
<% 		}%>
	</tr>
<%	} %>
	</table>
</div>	
<!-- fin de estructura -->