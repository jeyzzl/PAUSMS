<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.emp.spring.EmpCurriculum"%>
<%@page import="aca.catalogo.spring.CatNivel"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../css/style.css" />
<%
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String accesos				= (String)request.getAttribute("accesos");
	boolean esAdmin				= (boolean)request.getAttribute("esAdmin");
	if(!esAdmin && accesos.trim().equals("")){
		out.print("<div class='alert alert-success'><a class='btn btn-primary' href='vitae'>Vitae</a></div>");
	}	
	List<EmpCurriculum> listCurriculum 		= (List<EmpCurriculum>) request.getAttribute("listCurriculum");
	HashMap<String,String> mapaMaestros  	= (HashMap<String,String>) request.getAttribute("mapaMaestros");
	HashMap<String,CatNivel> mapaNiveles  	= (HashMap<String,CatNivel>) request.getAttribute("mapaNiveles");	
%>
<body>
<div class="container-fluid">
	<h2>Lista de maestros con curriculum</h2>			
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	<tr>
		<th width="3%"><H5><spring:message code="aca.Numero"/></H5></th>
		<th width="7%"><H5>N&oacute;mina</H5></th>
		<th width="50%"><h5><spring:message code="aca.Nombre"/></H5></th>
		<th width="10%"><H5>Último Nivel</H5></th>
		<th width="10%" class="text-center"><H5>Revisado</H5></th>
	</tr>			
	</thead>
	<tbody>
<%
	int row = 0;
	for(EmpCurriculum empCurriculum : listCurriculum){
		row++;
		String nivelNombre = "-";
		if (mapaNiveles.containsKey(empCurriculum.getNivelId())){
			nivelNombre = mapaNiveles.get(empCurriculum.getNivelId()).getNombreNivel();
		}
		
		String maestroNombre = "-";
		if (mapaMaestros.containsKey(empCurriculum.getIdEmpleado())){
			maestroNombre = mapaMaestros.get(empCurriculum.getIdEmpleado());
		}		 
%>
	<tr class="button">
		<td><b><%=row%></b></td>
		<td>
			<a href="vitaePdf?codigoPersonal=<%=empCurriculum.getIdEmpleado()%>">
			<%=empCurriculum.getIdEmpleado() %>
			</a>	
		</td>
		<td><%=maestroNombre%></td>
		<td><%=nivelNombre%></td>
		<td align="center"><%=empCurriculum.getRevisado()!=null?empCurriculum.getRevisado():""%></td>
	</tr>
<%	} %>
	</tbody>
	</table>	
</div>
</body>