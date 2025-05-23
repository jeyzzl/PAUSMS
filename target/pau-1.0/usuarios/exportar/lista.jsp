<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.3/css/jquery.dataTables.css">
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.js"></script>
<script type="text/javascript" >
	function grabar(){
		document.frmUsuarios.submit();
	}
</script>
<%		
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal");
	String admin 			= (String)request.getAttribute("admin");

	List<AlumPersonal> lisAlumnos					= (List<AlumPersonal>)request.getAttribute("lisAlumnos");
	HashMap<String,Acceso> mapaAccesos 				= (HashMap<String,Acceso>)request.getAttribute("mapaAccesos");	
	HashMap<String,String> mapaAlumPlan				= (HashMap<String,String>)request.getAttribute("mapaAlumPlan");	
%>

<div class="container-fluid">
	<h2>Export Users</h2>
    <form name="frmUsuarios" action="exportar" method="post">
    <div class="alert alert-info d-flex align-items-center">
		<button type="button" onclick="grabar();" class="btn btn-primary">Export Students</button>
    </div>
	<table id="table" class="table table-bordered table-striped">
		<thead class="table-dark">
			<tr>
				<th>No.</th>
				<th>Select</th>
				<th>Student ID</th>
				<th>Name</th>
				<th>Plan</th>
			</tr>
		</thead>
		<tbody>
<%	
	int row = 0;
	for(AlumPersonal alumno : lisAlumnos){	
		row++;
		String plan = "";
		if(mapaAlumPlan.containsKey(alumno.getCodigoPersonal())){
			plan = mapaAlumPlan.get(alumno.getCodigoPersonal());
		}
%>
			<tr>
				<td><%=row%></td>
				<td>
					<input class="form-check-input" type="checkbox" value="<%=alumno.getCodigoPersonal()%>" id="<%=alumno.getCodigoPersonal()%>" name="<%=alumno.getCodigoPersonal()%>">
				</td>
				<td><%=alumno.getCodigoPersonal()%></td>
				<td><%=alumno.getNombre()+" "+(alumno.getApellidoMaterno().equals("")?"":alumno.getApellidoMaterno())+" "+(alumno.getApellidoPaterno().equals("")?"":alumno.getApellidoPaterno())%></td>
				<td><%=plan%></td>
			</tr>
<%	}	%>	
		</tbody>
	</table>
    </form>
</div>
<script>	
	$('#table').DataTable({
        language: {
            url: 'https://cdn.datatables.net/plug-ins/1.10.21/i18n/English.json',
            search: "Search:"
        },
        paging: false,
        pageLength: 100,
        info: false,
        lengthChange: false,
        layout: {
            topStart: {
                buttons: ['copy', 'csv', 'excel', 'pdf', 'print']
            }
        }
    });
</script>