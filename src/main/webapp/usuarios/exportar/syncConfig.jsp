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

<script type="text/javascript" >
	function grabar(){
		document.frmUsuarios.submit();
	}

	function change(){
		document.frmSync.submit();
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
	String sync 			= (String)request.getAttribute("sync");
    String option 			= (String)request.getAttribute("option");

	List<AlumPersonal> lisAlumnos					= (List<AlumPersonal>)request.getAttribute("lisAlumnos");
	HashMap<String,Acceso> mapaAccesos 				= (HashMap<String,Acceso>)request.getAttribute("mapaAccesos");	
	HashMap<String,String> mapaAlumPlan				= (HashMap<String,String>)request.getAttribute("mapaAlumPlan");	
%>
<div class="container-fluid">
	<h2>Student Sync Configuration</h2>
	<form name="frmSync" action="syncConfig" method="post">
    <div class="alert alert-info d-flex align-items-center">
        <a href="lista" class="btn btn-primary me-2">Return</a>
		Status:
		<select name="Sync" id="Sync" class="form-select me-2" style="width:10rem;" onchange="javascript:change();">
        	<option value="S" <%=sync.equals("S")?"selected":""%>>Syncing</option>
			<option value="N" <%=sync.equals("N")?"selected":""%>>Not Syncing</option>
		</select>
    </form>
    <form name="frmUsuarios" action="removeSync" method="post" class="d-flex align-items-center">
		Action:
		<select name="Action" id="Action" class="form-select me-2" style="width:10rem;">
        	<option value="S" <%=option.equals("S")?"selected":""%>>Add sync</option>
			<option value="N" <%=option.equals("N")?"selected":""%>>Remove sync</option>
		</select>
    	<button type="button" onclick="toggleAllCheckboxes();" class="btn btn-secondary me-2" id="selectAllBtn">Select All</button>
        <button type="button" onclick="grabar();" class="btn btn-warning me-2">Apply</button>
    </div>
	
    <%-- <input type="hidden" value="<%=option%>" name="Action" id="Action"> --%>
	<table class="table table-bordered table-striped">
		<thead class="table-dark">
			<tr>
				<th>No.</th>
				<th>Select</th>
				<th>Student ID</th>
				<th>Name</th>
				<th>Plan</th>
                <th>Synchronize</th>
			</tr>
		</thead>
		<tbody>
<%	
	int row = 0;
	for(AlumPersonal alumno : lisAlumnos){	
		row++;
		String planAlum = "";
		if(mapaAlumPlan.containsKey(alumno.getCodigoPersonal())){
			planAlum = mapaAlumPlan.get(alumno.getCodigoPersonal());
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
                <td><%=alumno.getSync().equals("S")?"Yes":"No"%></td>
			</tr>
<%	}	%>	
		</tbody>
	</table>
    </form>
</div>