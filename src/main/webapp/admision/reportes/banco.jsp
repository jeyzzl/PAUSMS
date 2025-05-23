<%@page import="java.util.List" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Date" %>
<%@page import="aca.alumno.spring.AlumPersonal" %>
<%@page import="aca.alumno.spring.AlumBanco" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<head></head>
<%
	List<AlumPersonal> listaAlumnos = (List<AlumPersonal>)request.getAttribute("listaAlumnos");
    HashMap<String,AlumBanco> mapaBanco = (HashMap<String,AlumBanco>)request.getAttribute("mapaBanco");

	int counter = 0;
	String fechaNacimiento = "";
%>
<body>
	<div class="container-fluid">
		<h2>Bank Details for Enrolled Students</h2>
		<div class="alert alert-info" role="alert">
			<a href="menu" class="btn btn-primary">Return</a>
		</div>
	</div>
	<table id="table" class="table" >
		<thead>
			<tr>
                <th>No.</th>
				<th>Student ID.</th>
				<th>Name</th>
				<th>Bank Name</th>
                <th>Branch</th>
				<th>Account Name</th>
				<th>Account #</th>
				<th>Account Type</th>
                <th>BSB #</th>
				<th>Swift Code</th>
			</tr>
		</thead>
		<tbody>
<%		for(AlumPersonal alumno : listaAlumnos){ 
			counter++;
			fechaNacimiento = alumno.getFNacimiento().substring(0,10);

            AlumBanco banco = new AlumBanco();
            if(mapaBanco.containsKey(alumno.getCodigoPersonal())){
                banco = mapaBanco.get(alumno.getCodigoPersonal());
            }
%>
			<tr>
				<td><%=counter%></td>
				<td><%=alumno.getCodigoPersonal()%></td>
				<td><%=alumno.getNombre()+" "+alumno.getApellidoPaterno()%></td>
				<td><%=banco.getBanco()%></td>
				<td><%=banco.getBancoRama()%></td>
				<td><%=banco.getCuentaNombre()%></td>
                <td><%=banco.getCuentaNumero()%></td>
				<td><%=banco.getCuentaTipo()%></td>
				<td><%=banco.getNumeroBBS()%></td>
                <td><%=banco.getCodigoSwift()%></td>
			</tr>
<% }%>
		</tbody>
	</table>
</body>