<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.ModOpcion"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function CopiarTodo(origen,destino){
		if (confirm("¿Estás seguro de copiar los privilegios del usuario: "+origen+" para el usuario:"+destino+"?")){
			document.location.href="copiarTodo?Origen="+origen+"&Destino="+destino;
		}
	}		
	function CopiarOpcion(destino,opcion,buscar) {	
		document.location.href = "copiarOpcion?Destino="+destino+"&Opcion="+opcion+"&Buscar="+buscar;
	}	
</script>
<%
	String codigoBuscar 		= request.getParameter("Buscar")==null?"0":request.getParameter("Buscar");
	boolean existeCodigo		= (boolean)request.getAttribute("existeCodigo");
	String codigoUltimo 		= (String)session.getAttribute("codigoUltimo");
	String nombrePrincipal 		= (String)request.getAttribute("nombrePrincipal");
	String nombreSecundario 	= (String)request.getAttribute("nombreSecundario");
	
	List<ModOpcion> lisPrincipal 			= (List<ModOpcion>)request.getAttribute("lisPrincipal");
	List<ModOpcion> lisSecundario 			= (List<ModOpcion>)request.getAttribute("lisSecundario");		
	HashMap<String, String> mapaMenus 		= (HashMap<String, String>)request.getAttribute("mapaMenus");
	HashMap<String, String> mapaPrincipal 	= (HashMap<String, String>)request.getAttribute("mapaPrincipal");
	HashMap<String, String> mapaSecundario 	= (HashMap<String, String>)request.getAttribute("mapaSecundario");
	String opciones ="";
	int cont = 0;	
%>
<div class="container-fluid">
	<h2><a href="usuario"><i class="fas fa-arrow-left"></i></a>&nbsp;Copy Privileges</h2>
	<hr>	
	<div class ="row">
		<div class="col-6">
			<div class="alert alert-info d-flex align-items-center">   			
   				<%=codigoUltimo%> - <%=nombrePrincipal%>
<%		if (existeCodigo && lisPrincipal.size()>=1){%>
   				 &nbsp; <a href="javascript:CopiarTodo('<%=codigoUltimo%>','<%=codigoBuscar%>');" class="btn btn-primary btn-sm"><i class="far fa-copy"></i></a>
<%		} %>   				 
			</div>
			<table class="table table-bordered">
			<thead class="table-info">
			<tr> 
				<th>Op.</th>
				<th>Name</th>
				<th>Module</th>
				<th>Option</th>
			</tr>
			</thead>
			<tbody>		
<%
		for (ModOpcion opcion : lisPrincipal){	
			cont++;			
			if(mapaMenus.containsKey(opcion.getModuloId())){
				opciones = mapaMenus.get(opcion.getModuloId());
			}
		
%>
			<tr>
				<td class="fs-6">
<%			if (existeCodigo && mapaSecundario.containsKey(opcion.getOpcionId())==false){%>	
				 <a href="javascript:CopiarOpcion('<%=codigoBuscar%>','<%=opcion.getOpcionId()%>','<%=codigoBuscar%>');"><i class="fas fa-arrow-alt-circle-right" style="color:green"></i>
<% 			}else if (existeCodigo){%>
				<i class="far fa-check-circle" style="color:gray"></i>
<%			} %>								
				</td>
		   		<td class="fs-6"><%=opciones%></td>
		   	 	<td class="fs-6"><%=opcion.getNombreModulo()%></td>
		    	<td class="fs-6"><%=opcion.getNombreOpcion()%></td>
		   	</tr> 
<%
		}	
%>
			</tbody>		
			</table>
		</div>
		<div class="col-6">		
			<form name="frmCopiar" action="copiar" method="post">	
			<div class="alert alert-info d-flex align-items-center">
    		<input name="Buscar" type="text" class="form-control" placeholder="Buscar" value="<%=codigoBuscar%>" style="width:150px">&nbsp;&nbsp;
   			<a href="javascript:document.frmCopiar.submit();" class="btn btn-primary btn-sm"><i class="fas fa-search"></i></a>
   			&nbsp; &nbsp; <%=nombreSecundario%>       
			</div>
			</form>
			<table class="table table-bordered">
			<thead class="table-info">
			<tr> 
				<th>Op.</th>
				<th>Name</th>
				<th>Module</th>
				<th>Option</th>
			</tr>
			</thead>			
			<tbody>	
<%
		for (ModOpcion opcion : lisSecundario){	
			cont++;		
			
			if(mapaMenus.containsKey(opcion.getModuloId())){
				opciones = mapaMenus.get(opcion.getModuloId());
			}

%>
			<tr>
				<td class="fs-6">
<%			if (existeCodigo && mapaPrincipal.containsKey(opcion.getOpcionId())==false){%>
				<a href="javascript:CopiarOpcion('<%=codigoUltimo%>','<%=opcion.getOpcionId()%>','<%=codigoBuscar%>');"><i class="fas fa-arrow-alt-circle-left" style="color:green"></i>				
<% 			}else if (existeCodigo){%>
				<i class="far fa-check-circle" style="color:gray"></i>
<%			} %>				
				</td>
		   		<td class="fs-6"><%=opciones%></td>
		   	 	<td class="fs-6"><%=opcion.getNombreModulo()%></td>
		    	<td class="fs-6"><%=opcion.getNombreOpcion() %></td>
		   	</tr> 
<%		}%> 
			</tbody>	
			</table>		
		</div>
	</div>
</div>