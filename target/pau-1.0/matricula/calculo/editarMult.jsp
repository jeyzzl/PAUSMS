<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Set"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>



<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">	
	
	function agregarMat(){
		console.log("agregar")
		document.forma.accion.value="0";
		document.forma.submit()
	}
	
	function Grabar(){
		document.forma.accion.value = "1";
		console.log("grabar")
		document.forma.submit();
	}
	
	
	
</script>

<%

String cargaId					= (String)request.getAttribute("CargaId");
String bloqueId					= (String)request.getAttribute("BloqueId");

Set<String> lisMatriculas							= (Set<String>)request.getAttribute("lisMatriculas");
List<AlumPersonal> lisAlumnos						= (List<AlumPersonal>)request.getAttribute("lisAlumnos");

HashMap<String, AlumPersonal> mapAlum					= (HashMap<String, AlumPersonal>)request.getAttribute("mapAlum");
	
%>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<div class="container-fluid">
	<h2>Add Multiple Students</h2>
	<div class="alert alert-info">
	
	<a href="listado" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
		

	</div>
	
	<div class="form-group">
		<form name="forma" method="post" action="editarMult" enctype="multipart/form-data">
		
		
		
			<div class="container-fluid	d-flex">
				<div>
				<label class="form-label">Student ID</label><br>
				<select name="CodigoAlumno" id="CodigoAlumno" class="chosen" onchange="" style="width:250px;" class="form-select">
					<option value="0">Select Student</option>
					<%for(AlumPersonal alum: lisAlumnos){ %>
					<option value="<%=alum.getCodigoPersonal() %>"><%=alum.getCodigoPersonal() %> | <%=alum.getNombre()%></option>
					<%} %>
				</select><br>
				<label class="form-label">Load</label><br>
				<input class="form-control mb-3" type="text" name="CargaId" id ="CargaId" value="<%=cargaId %>" style="width: 15rem" readonly>
				<label class="form-label">Block</label><br>
				<input class="form-control mb-3" type="text" name="BloqueId" id="BloqueId" value="<%=bloqueId %>" style="width: 15rem" readonly>
				<label class="form-label">Status</label><br>
				<select class="form-select mb-3" name="Status" id="Status" style="width:15rem">
					<option value="A">Active</option>
					<option value="I">Inactive</option>
				</select>
				<div class="alert alert-info">
				<a class="btn btn-primary" href="javascript:Grabar()" >Save</button>
				<a class="btn btn-primary" href="javascript:agregarMat()" style="margin-left:10px">Add Student</a>
				</div>
				</div>
				<div style="margin-left:80px;max-height:475px;overflow:auto;display:inline-block">
					<table id="table" class="table table-sm table-bordered" style="width:300px">
						<thead class="table-info">
							<tr>
								
								<th width="30%">ID</th>
								<th width="70%">Name</th>
								
								</th>
							</tr>
						</thead>
			<%int i = 1; %>
			<%for(String matricula:lisMatriculas){ %>
							<tr>
								<td>
								<%=matricula %>
								<input name="matricula<%=i%>" id="matricula<%=i %>" type="hidden" value="<%=matricula%>">
								</td>	
								<td><%=mapAlum.get(matricula).getNombre() %></td>
							</tr>
						<%i++;} %>
						<input name="count" type="hidden" id="count" value="<%=i %>">
						<input name="accion" type="hidden" id="accion" value="<%=0 %>">
					</table>
				</div>	
			</div>	
				
			
		</form>
		
	</div>
	
</div>

<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
	jQuery(".chosen").chosen();
</script>