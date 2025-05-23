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

	function change(){
		document.frmExportar.submit();
	}
	
    function toggleAllCheckboxes() {
        const checkboxes = document.querySelectorAll('input[type="checkbox"]');
        const selectAllBtn = document.getElementById('selectAllBtn');
        let allChecked = true;
        
        // Check if all are currently checked
        checkboxes.forEach(checkbox => {
            if (!checkbox.checked) {
                allChecked = false;
            }
        });
        
        // Toggle all checkboxes
        checkboxes.forEach(checkbox => {
            checkbox.checked = !allChecked;
        });
        
        // Update button text
        selectAllBtn.textContent = allChecked ? 'Select All' : 'Deselect All';
    }
</script>
<%		
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal");
	String admin 			= (String)request.getAttribute("admin");
	String status 			= (String)request.getAttribute("status");
	String plan 			= (String)request.getAttribute("plan");

	List<AlumPersonal> lisAlumnos					= (List<AlumPersonal>)request.getAttribute("lisAlumnos");
	HashMap<String,Acceso> mapaAccesos 				= (HashMap<String,Acceso>)request.getAttribute("mapaAccesos");	
	HashMap<String,String> mapaAlumPlan				= (HashMap<String,String>)request.getAttribute("mapaAlumPlan");	
%>

<div class="container-fluid">
	<h2>Export Users</h2>
	<form name="frmExportar" action="lista" method="post">
    <div class="alert alert-info d-flex align-items-center">
		Status:
		<select name="Status" id="Status" class="form-select me-2" style="width:10rem;" onchange="javascript:change();">
			<option value="N" <%=status.equals("N")?"selected":""%>>Not Synced</option>
			<option value="S" <%=status.equals("S")?"selected":""%>>Synced</option>
		</select>
		Plan:
		<select name="Plan" id="Plan" class="form-select me-2" style="width:10rem;" onchange="javascript:change();">
			<option value="T" <%=plan.equals("T")?"selected":""%>>All</option>
			<option value="A" <%=plan.equals("A")?"selected":""%>>Assigned</option>
			<option value="N" <%=plan.equals("N")?"selected":""%>>Not Assigned</option>
		</select>    	
		<button type="button" onclick="toggleAllCheckboxes();" class="btn btn-secondary me-2" id="selectAllBtn">Select All</button>
		<button type="button" onclick="grabar();" class="btn btn-primary me-2">Export Students</button>
		<a href="syncConfig" class="btn btn-info">Sync Configuration</a>

    </div>
	</form>
	<form name="frmUsuarios" action="exportar" method="post">
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
		String planAlum = "";
		boolean tienePlan = false;
		if(mapaAlumPlan.containsKey(alumno.getCodigoPersonal())){
			planAlum = mapaAlumPlan.get(alumno.getCodigoPersonal());
			tienePlan = true;
		}
%>
			<tr>
				<td><%=row%></td>
				<td>
					<input class="form-check-input" type="checkbox" value="<%=alumno.getCodigoPersonal()%>" id="<%=alumno.getCodigoPersonal()%>" name="<%=alumno.getCodigoPersonal()%>">
				</td>
				<td><%=alumno.getCodigoPersonal()%></td>
				<td><%=alumno.getNombre()+" "+(alumno.getApellidoMaterno().equals("")?"":alumno.getApellidoMaterno())+" "+(alumno.getApellidoPaterno().equals("")?"":alumno.getApellidoPaterno())%></td>
				<td><%=planAlum%></td>
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