<%@page import="java.util.List"%>
<%@page import="aca.dherst.spring.DherstArchivo"%>
<%@page import="aca.dherst.spring.DherstAlumno"%>


<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%  
	String folio 			= request.getParameter("folio")==null?"0":request.getParameter("folio");
	String mensaje 			= request.getParameter("mensaje")==null?"0":request.getParameter("mensaje");
	String errores 			= request.getParameter("errores")==null?"0":request.getParameter("errores");
	String traspasos 		= request.getParameter("traspasos")==null?"0":request.getParameter("traspasos");

	DherstArchivo archivo 	= (DherstArchivo) request.getAttribute("archivo");
	boolean existeArchivo 	= (boolean) request.getAttribute("existeArchivo");

	List<DherstAlumno> listaAlumnos = (List<DherstAlumno>) request.getAttribute("listaAlumnos");

	String mensajeTexto = "";
	if(mensaje.equals("1")) mensajeTexto = "Saved";
	if(mensaje.equals("2")) mensajeTexto = "Error";
	if(mensaje.equals("3")) mensajeTexto = "Cannot delete transferred students";
%>

<script type="text/javascript">
	function guardarArchivo(){
		if($("archivo").value != ""){	
			document.frmArchivo.submit();
		}else{
			alert("Select a file");	
        }
	}
		
	function borrarArchivo(folio){
		if(confirm("Do you want to delete this file?")){
			document.location.href = "borrarArchivo?folio="+folio;
		}
	}

	function borrarEstudiantes(folio){
		if(confirm("Do you want to delete the students from this file?")){
			document.location.href = "borrarEstudiantes?folio="+folio;
		}
	}

	function transferirEstudiantes(folio){
		if(confirm("Do you want to create an account for all the students in this file?")){
			document.location.href = "transferirEstudiantes?folio="+folio;
		}
	}

	document.addEventListener("DOMContentLoaded", function() {
		checkTransferEligibility();
	});

	function checkTransferEligibility() {
		const rows = document.querySelectorAll("table.table-bordered tbody tr");
		let hasInvalidRow = false;

		rows.forEach(row => {
			if (row.classList.contains("table-warning") || row.classList.contains("table-danger") || row.classList.contains("table-success")) {
				hasInvalidRow = true;
			}
		});

		const transferButton = document.getElementById("transferButton");
		if (hasInvalidRow) {
			transferButton.setAttribute("disabled", "disabled");
		} else {
			transferButton.removeAttribute("disabled");
		}
	}
</script>
<body>
<div class="container-fluid">
    <h2>Upload file</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="alumnos">Return</a>
		<%=mensajeTexto%>
	</div>
<% if(!existeArchivo){%> <!-- UPLOAD FILE-->
    <h3>Select a file</h3>
    <form name="frmArchivo" id="frmArchivo" enctype="multipart/form-data" action="guardarArchivo?folio=<%=folio%>" method="post">
        <div class="fileupload fileupload-new" data-provides="fileupload">
			<span class="btn  btn-default btn-file">
				<input type="file" id="archivo" name="archivo"/>
			</span><br><br>	
			<input type="text" class="form-control" name="comentario" id="comentario" placeholder="Comment" style="width: 50rem">
			<br>
			<button class="btn btn-primary btn-large" id="btnGuardar" value="Enviar">Upload</button>
        </div>
    </form>
<% }else{%> <!-- FILE AND STUDENT LIST -->
	<div class="card my-3" style="width: 25rem;">
		<h5 class="card-header d-flex justify-content-between">
			File
			<% if(listaAlumnos == null || listaAlumnos.isEmpty()){%>
			<a href="javascript:borrarArchivo('<%=folio%>')" class="btn btn-danger btn-sm"><i class="fas fa-times"></i></a>
			<% } %>
		</h5>
		<div class="card-body">
			<h5 class="card-title"><%=archivo.getNombre()%></h5>
			<p class="card-text"><%=archivo.getComentario()%></p>
		</div>
	</div>
<%		if(listaAlumnos != null && !listaAlumnos.isEmpty()){%>
	<div class="mb-3">
		<a href="javascript:borrarEstudiantes('<%=folio%>')" class="btn btn-danger">Delete Students</a>
		<button onclick="transferirEstudiantes('<%=folio%>')" id="transferButton" class="btn btn-primary">Transfer Students</button>
	</div>
	<table class="table table-bordered"> <!-- STUDENT LIST TABLE-->
		<thead class="table-info">
			<tr>
				<th></th>
				<th>No.</th>
				<th>Slf. No.</th>
				<th>Name</th>
				<th>Surname</th>
				<th>Address</th>
				<th>Email</th>
				<th>Phone</th>
				<th>Mobile</th>
				<th>GPA</th>
				<th>Gender</th>
				<th>Residence</th>
				<th>Course</th>
				<th>Status</th>
			</tr>
		</thead>
		<tbody>
<%			
			int row = 0;
			
			for(DherstAlumno alumno : listaAlumnos){
				row++;	
				String colorRow = "";

				if(alumno.getResEstadoId().equals("0") || alumno.getEstadoId().equals("0") || alumno.getReligionId().equals("0") || alumno.getEstadoCivil().equals("-")){
					colorRow = "table-warning";
				}
				if(alumno.getSlfNo().equals("0") || alumno.getGpa().equals("0") || alumno.getPlanId().trim().equals("-")){
					colorRow = "table-danger";
				}
				if(alumno.getStatus().equals("T") || alumno.getStatus().equals("C")){
					colorRow = "table-success";
				}

				String status = "";
				if(alumno.getStatus().equals("U")) status = "Uploaded";
				if(alumno.getStatus().equals("T")) status = "Transferred";
				if(alumno.getStatus().equals("C")) status = "Letter Generated";
%>
			<tr class="<%=colorRow%>" >
				<td>
					<% if(alumno.getStatus().equals("U")){%><a href="alumno?folio=<%=folio%>&slfNo=<%=alumno.getSlfNo()%>"><i class="fas fa-edit"></i></a><% }%>
					<% if(alumno.getStatus().equals("T")){%><i class="fas fa-check-circle"></i><% }%>
				</td>
				<td><%=row%></td>
				<td><%=alumno.getSlfNo()%></td>
				<td><%=alumno.getNombre()%></td>
				<td><%=alumno.getApellido()%></td>
				<td><%=alumno.getDireccion()%></td>
				<td><%=alumno.getEmail()%></td>
				<td><%=alumno.getTelefono()%></td>
				<td><%=alumno.getCelular()%></td>
				<td><%=alumno.getGpa()%></td>
				<td><%=alumno.getSexo()%></td>
				<td><%=alumno.getResidencia()%></td>
				<td><%=alumno.getPlanId()%></td>
				<td><%=status%></td>
			</tr>
<%			}%>
		</tbody>
	</table>
<%		}else{%>
	<div class="alert alert-warning">
		No students imported from this file
	</div>
<%		}%>
<% }%>
</div>
</body>