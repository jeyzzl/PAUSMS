<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.Maestros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String opcion 	= request.getParameter("Opcion")==null?"T":request.getParameter("Opcion");

	List<Maestros> lisEmpleados				= (List<Maestros>)request.getAttribute("lista");
	HashMap<String,String> mapaImagenes		= (HashMap<String,String>)request.getAttribute("mapaImagenes");
%>
<head>	
</head>
<body>
<div class="container-fluid">
	<h2>Documentos de empleados</h2>
	<form name="frmDocumentos" action="documentos" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" class="btn btn-primary">Regresar</a>&nbsp;&nbsp;
		<select name="Opcion"  class="form-select" style="width:140px" onchange="document.frmDocumentos.submit();">
			<option value="T" <%=opcion.equals("T")?"selected":""%>>Todos</option>
			<option value="C" <%=opcion.equals("C")?"selected":""%>>Con Imagen</option>
		</select>&nbsp;&nbsp;
		<input type="text" class="form-control" style="width:160px;" placeholder="Buscar..." id="buscar">
	</div>
	</form>
	<table class="table  table-striped table-bordered">
	<tr class="table-info">
		<th>#</th>
		<th>Código</th>
		<th>Empleado</th>
		<th class="text-end">Imagenes </th>
	</tr>
<%
	int row=0;
	for (Maestros maestro : lisEmpleados){
		row++;
		String numImg = "0";
		if (mapaImagenes.containsKey(maestro.getCodigoPersonal())){
			numImg = mapaImagenes.get(maestro.getCodigoPersonal());
		}
%>
	<tr>
		<td><%=row%></td>
		<td><%=maestro.getCodigoPersonal()%></td>
		<td><%=maestro.getApellidoPaterno()%> <%=maestro.getApellidoMaterno()%>,<%=maestro.getNombre()%></td>
		<td class="text-end"><%=numImg%></td>
	</tr>
<%	} %>	
	</table>
</div>	
<script type="text/javascript" src="../../js/search.js"></script>
</body>