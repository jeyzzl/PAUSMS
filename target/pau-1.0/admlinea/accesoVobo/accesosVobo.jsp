<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmAccesoVobo"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.emp.spring.Empleado"%>
<html>
<head>	
	<script type = "text/javascript">
		function Borrar(codigoPersonal,carreraId){
			if(confirm("¿Estás seguro de eliminar este acceso?")==true){
		  		document.location="borrarAcceso?CodigoPersonal="+codigoPersonal+"&CarreraId="+carreraId;
		  	}
		}
	</script>
</head>
<%
	
	String empleadoCodigo			= (String)session.getAttribute("codigoEmpleado");
	String empleadoNombre			= (String)request.getAttribute("empleadoNombre");
	CatCarrera carrera 				= (CatCarrera)request.getAttribute("carrera");

	List<AdmAccesoVobo> lista 		= (List<AdmAccesoVobo>)request.getAttribute("lista");	
	HashMap<String,String> mapa 	= (HashMap<String,String>)request.getAttribute("mapa");
%>
<body>
<div class="container-fluid">
	<h1>Carrera: <%=carrera.getNombreCarrera()%></h1>
	<input type="hidden" name="CarreraId" value="<%=carrera.getCarreraId()%>">
	<form name="frm" action="grabarAcceso" method="post">
	<div class="alert alert-info" role="alert">
		<a class="btn btn-primary" href="listado">Regresar</a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		Empleado:&nbsp;<%=empleadoNombre%>
		&nbsp;&nbsp;		
		<button class="btn btn-primary" type="submit">Agregar</button>
	</div>	
	</form>
	<table class="table table-sm  table-bordered">
	<thead>
		<tr class="table-info">
			<th>#</th>
			<th></th>
			<th>Codigo Personal</th>
			<th>Nombre</th>
		</tr>
	</thead>
<%		
	int cont = 1;
	for(AdmAccesoVobo acceso : lista){
		String nombre = "";
		if(mapa.containsKey(acceso.getCodigoPersonal())){
			nombre = mapa.get(acceso.getCodigoPersonal());
		}
		
%>	
	<tbody>
		<tr>
			<td><%=cont++%></td>
			<td><a class="btn btn-danger" href="javascript:Borrar('<%=acceso.getCodigoPersonal()%>','<%=acceso.getCarreraId()%>')" title="Borrar"><i class="fas fa-trash-alt"></i></a></td>
			<td><%=acceso.getCodigoPersonal()%></td>
			<td><%=nombre%></td>
		</tr>
	</tbody>
<% 	
	} 
%>
	</table>
</div>
</body>
</html>