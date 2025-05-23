<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%			
	String menuId			= (String) request.getAttribute("menuId");
	String menuNombre 		= (String) request.getAttribute("menuNombre");
	
	List<aca.menu.spring.Modulo> lisModulos					= (List<aca.menu.spring.Modulo>) request.getAttribute("lisModulos");
	List<aca.menu.spring.ModuloOpcion> lisOpciones			= (List<aca.menu.spring.ModuloOpcion>) request.getAttribute("lisOpciones");		
	HashMap<String,String> mapaTotUsuarios 					= (HashMap<String,String>) request.getAttribute("mapaTotUsuarios");
%>	
<input type="hidden" value=<%=menuId %>>
<div class="container-fluid">
	<h3>Menu modules: <small class="text-muted"><%= menuNombre %></small></h3>
	<div class="alert alert-info">
		<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>	
	<table class="table table-bordered">
<%		
	for (aca.menu.spring.Modulo modulo : lisModulos){			
%>				
	<thead>
		<tr> 
			<th colspan="6"><h4><a title="Edit name" href="editarModulo?id=<%=modulo.getModuloId()%>"><i class="fas fa-edit" style="color:black"></i></a> &nbsp;<b><%=modulo.getNombreModulo()%> / <%=modulo.getNombreIngles()%></b></h4></th>
		</tr>
	</thead>
	<thead class="table-info">
		<tr>			
			<th class="text-center" width="3%">Op.</th>
			<th class="text-center" width="3%">#</th>
			<th class="text-center" width="5%">Key</th>
			<th width="15%">Option title</th>
			<th width="15%">English title</th>
			<th width="40%">Comment</th>
			<th class="text-end" width="3%"># Users</th>
			<th class="text-center" width="3%">State</th>
		</tr>
	</thead>
<%
		int row = 0;
		for (aca.menu.spring.ModuloOpcion opcion : lisOpciones){
			if (opcion.getModuloId().equals(modulo.getModuloId())){
				row++;		
				String totUsuarios = "0";
				if(mapaTotUsuarios.containsKey(opcion.getOpcionId())){
					totUsuarios = mapaTotUsuarios.get(opcion.getOpcionId());
				}
				
				String stringState = "-";
%>
		<tr> 
			<td><a title="Edit name" href="editarOpcion?id=<%=menuId%>&OpcionId=<%=opcion.getOpcionId()%>"><i class="fas fa-edit"></i></a></td>
			<td class="text-center"><%=row%></td>
      		<td class="text-center"> 
				<%=opcion.getOpcionId()%>
			</td>
    		<td><a href="aplicacion?id=<%=menuId%>&opcion=<%=opcion.getOpcionId()%>"><%=opcion.getNombreOpcion()%></a></td>
    		<td>&nbsp;<%=opcion.getNombreIngles()%></td>
    		<td>&nbsp;<%=opcion.getUsuarios()%></td>
    		<td class="text-end"><%=totUsuarios%></td>
    		<td class="text-center">
    		<%
    			if(opcion.getIcono().equals("A")){
    				stringState = "<span class='badge bg-dark rounded-pill'>Active</span>";
    			}else if(opcion.getIcono().equals("I")){
    				stringState = "<span class='badge bg-danger rounded-pill'>Inactive</span>";
    			}else{
    				stringState = "<span class='badge bg-primary rounded-pill'>In Review</span>";
    			}
    		%>
    		<%= stringState %>
    		</td>
		</tr>
<%		
			}
		} 
	}	
%>
	</table>	  
</div>