<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import= "aca.catalogo.spring.CatEdificio"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	
	function Borrar( edificioId ){
		Swal.fire({
			  title: 'Do you want to delete this record?',
			  text: "Information can't be recovered!",
			  icon: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes',
			  cancelButtonText: 'Cancel'
			}).then((result) => {
			  if (result.isConfirmed) {
			    Swal.fire(
			      'Deleted!',
			      'The building has been deleted',
			      'success'
			    )
		  		setTimeout(() => {
		  			document.location.href="borrarEdificio?EdificioId="+edificioId;
		  		}, 1000);
			  }
			});
	}
	function Borrar(edificioId){
		if(confirm("Do you want to delete this record?")==true){
  			document.location.href="borrarEdificio?EdificioId="+edificioId;
		}			
	}	
</script>
<%	
	String codigoId 	= session.getAttribute("codigoPersonal")+"";
	boolean admin 		= Boolean.parseBoolean(session.getAttribute("admin")+"");
	
	List<CatEdificio> lisEdificio 			= (List<CatEdificio>) request.getAttribute("lisEdificios");	
	HashMap<String, String> mapSalones 		= (HashMap<String, String>)request.getAttribute("mapaSalones");	
	HashMap<String, String> mapaUsuarios 	= (HashMap<String, String>)request.getAttribute("mapaUsuarios");
%>

<html>
<div class="container-fluid">
	<h1><spring:message code="catalogos.edificio.Titulo"/></h1>
<div class="alert alert-info">
	<a class="btn btn-primary" href="editarEdificio"><spring:message code="aca.Anadir"/></a>
</div>
<table style="width:60%" >
  	<tr>
  <% if(admin){%>
		<td align="right"></td> 
  <% } %>
  
	</tr>
</table>
<table class="table table-sm table-bordered">  
	<tr>
  <%if(admin){%>
    	<th width="5%"><spring:message code="aca.Operacion"/></th>
<%	} %>
    	<th width="5%"><spring:message code="aca.Numero"/></th>
    	<th width="70%"><spring:message code="aca.Descripcion"/></th>
    	<th width="10%" class="text-center">Classrooms</th>
    	<th width="10%" class="text-center">Users</th>
  	</tr>
<%
	String usuario  = "";
	
	for(CatEdificio edificio : lisEdificio){		
		
		String totSal = "0";
		if (mapSalones.containsKey(edificio.getEdificioId())){
			totSal = mapSalones.get(edificio.getEdificioId());
		}
		
		String totUser = "0";
		if (mapaUsuarios.containsKey(edificio.getEdificioId())){
			totUser = mapaUsuarios.get(edificio.getEdificioId());
		}
		
%>
	<tr> 
<%		if(admin){ %>
	    <td style="text-align: center">
			<a href="editarEdificio?EdificioId=<%=edificio.getEdificioId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>&nbsp;
	<%		if(totSal.equals("0") && totUser.equals("0")){%>	
			<a href="javascript:Borrar('<%=edificio.getEdificioId()%>')" class="fas fa-trash-alt" title="<spring:message code="aca.Eliminar"/>"></a>
	<%		}%>	
		</td>
<%		} %>
		<td align="center"><%=edificio.getEdificioId()%></td>
		<td>
				<%=edificio.getNombreEdificio()%>
		</td>
		<td class="text-center">
			<a href="salones?EdificioId=<%=edificio.getEdificioId()%>">
				<span class="badge <%=totSal.equals("0")?"bg-warning":"bg-dark"%> rounded-pill"><%=totSal%></span>
			</a>
		</td>
		<td class="text-center">
			<a href="editarUsuarios?EdificioId=<%=edificio.getEdificioId()%>" title="Lista de usuarios">
			<span class="badge <%=totUser.equals("0")?"bg-warning":"bg-dark"%> rounded-pill"><%=totUser%></span>
			</a>
		</td>
	</tr>
<%	} %>  
</table>
</div>
</body>
</html>
