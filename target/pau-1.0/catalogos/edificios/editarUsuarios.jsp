<%@page import="aca.catalogo.spring.CatEdificio"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="java.util.List"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Grabar(){
		if(document.frmUsuario.EdificioId != "" && document.frmUsuario.MaestroId != ""){
			
			document.frmUsuario.submit();
			
		} else {
			alert("<spring:message code="aca.JSCompletar"/> ");
		}
	}

	function Borrar(edificio, usuario){
		if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  		document.location.href="borrarUsuario?EdificioId="+edificio+"&CodigoPersonal="+usuario;
	  	}
	}
</script>

<%
	// Declaracion de variables	
	String edificioId		= request.getParameter("EdificioId")==null?"0":request.getParameter("EdificioId");

	List<String> lisUsuarios 	 			= (List<String>)request.getAttribute("lisUsuarios");
	HashMap<String,String> mapaEnEdificios 	= (HashMap<String,String>)request.getAttribute("mapaEnEdificios");
	List<Maestros> lisMaestros 				= (List<Maestros>)request.getAttribute("lisMaestros");
%>
<html>
<head>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
</head>
<div class="container-fluid">
	<h1>Users List</h1>
	<form name="frmUsuario" action="grabarUsuario" method="post">
	<div class="alert alert-info"> 
		<a class="btn btn-primary" href="edificios"><i class="fas fa-arrow-left"></i></a> 
		<div class="align-items-center d-inline-flex">
			<label for="">&nbsp; Select user/professor: &nbsp;</label>
			<select class="form-select chosen" name="MaestroId" id="MaestroId" style="width:500px">
			<% for(Maestros maestro : lisMaestros){ %>
				<option value="<%=maestro.getCodigoPersonal()%>"><%=maestro.getCodigoPersonal()%> - <%=maestro.getNombre()%> <%=maestro.getApellidoMaterno().equals("-")?"":maestro.getApellidoMaterno()%> <%=maestro.getApellidoPaterno()%></option>
			<% } %>
			</select>
			&nbsp;&nbsp;
			<a href="javascript:Grabar()" class="btn btn-primary"> <spring:message code="aca.Guardar"/> </a>
		</div>
	</div>
		<input name="EdificioId" type="hidden" class="form-control" id="EdificioId" value="<%=edificioId%>">
	</form>
	
	<table class="table table-bordered">
	<tr>
		<th>Op.</th>
		<th>#</th>
		<th>User ID</th>
		<th><spring:message code="aca.Nombre"/></th>
	</tr>
<%
	if (lisUsuarios.size()>=1){
		int row=0;
		for(String usuario : lisUsuarios){
			row++;
			String nombre = "-";
			if (mapaEnEdificios.containsKey(usuario)){
				nombre = mapaEnEdificios.get(usuario);
			}
			
%>
	<tr>
		<td><a href="javascript:Borrar('<%=edificioId%>','<%=usuario%>')" class="fas fa-trash-alt" title="<spring:message code="aca.Eliminar"/>"></a></td>
 		<td><%=row%></td>
 		<td><%=usuario%></td>
 		<td><%=nombre%></td>
	</tr>
<%		}
	}
%>
	</table>

</div>
</body>
</html>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chosen").chosen();
</script>