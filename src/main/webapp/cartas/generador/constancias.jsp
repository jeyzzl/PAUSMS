<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean scope="page" id="ConstanciaU" class="aca.alumno.AlumConstanciaUtil"/>
<jsp:useBean id="AlumConstancia" scope="page" class="aca.alumno.AlumConstancia"/>

<%
	
	String usuario					= (String)session.getAttribute("codigoPersonal");
	
	String accion 					= request.getParameter("Accion")==null?"":request.getParameter("Accion");
	
	if(accion.equals("1")){//Eliminar
		AlumConstancia.setConstanciaId(request.getParameter("constanciaId"));
		
		if(ConstanciaU.deleteReg(conEnoc, AlumConstancia.getConstanciaId())){
			//Eliminado
		}else{
			//Error
		}
	}


	ArrayList<aca.alumno.AlumConstancia> constancias 	= ConstanciaU.getListUsuario(conEnoc, usuario, "ORDER BY 1");

%>

 
<div class="container-fluid">
	
	<h1>Constancias</h1>
	
	<div class="alert alert-info">
		<a href="accion" class="btn btn-primary"><i class="fas fa-plus"></i> <spring:message code='aca.Añadir'/></a>
	</div>
	
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th>#</th>
			<th>Acción</th>
			<th>Constancia</th>
			<th>Vista Previa</th>
			<th>Usuarios</th>
		</tr>
	</thead>
		<%int cont = 0; %>
		<%for(aca.alumno.AlumConstancia constancia : constancias){ %>
			<%cont++; %>
			<tr>
				<td><%=cont %></td>
				<td>
					<a href="javascript:borrar('<%=constancia.getConstanciaId()%>')"><i class="fas fa-trash-alt"></i></a>
					<a href="accion?constanciaId=<%=constancia.getConstanciaId() %>"><i class="fas fa-pencil-alt"></i></a>
				</td>
				<td><%=constancia.getConstanciaNombre() %></td>
				<td>
					<a target="_blank" href="vistaPrevia?constanciaId=<%=constancia.getConstanciaId() %>" class="btn btn-info btn-sm"><i class="fas fa-expand-alt"></i></a>
				</td>
				<td><%=constancia.getUsuarios() %></td>
			</tr>
		<%} %>
	</table>
	
</div>

<script>
	function borrar(constanciaId){
		if (confirm("¿Estás seguro que deseas eliminar este registro?") == true) {
			location.href = "constancias?Accion=1&constanciaId="+constanciaId;
		}
	}
</script>

<%@ include file= "../../cierra_enoc.jsp" %>