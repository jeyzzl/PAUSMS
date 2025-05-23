<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.trabajo.spring.TrabDepartamento"%>
<%@ page import="aca.trabajo.spring.TrabCategoria"%>
<%@ page import="aca.trabajo.spring.TrabPeriodo"%>
<%@ page import="aca.trabajo.spring.TrabAlum"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript" >
	function grabar(){
		if(document.frmPeriodoAlum.PeriodoId.value==""){
			alert("Period Required")
		}else{
			document.frmPeriodoAlum.submit();
		}
	}
	
	function cambioPeriodo(){
		var periodoId = document.getElementById("PeriodoId").value;
		location.href = "periodoAlum?PeriodoId="+periodoId;
	}
	
	function cambioDepartamento(){
		var periodoId 	= document.getElementById("PeriodoId").value;
		var deptId 		= document.getElementById("DeptId").value;
		location.href = "periodoAlum?PeriodoId="+periodoId+"&DeptId="+deptId;
	}
	
	function cambioPlan(){
		var periodoId 	= document.getElementById("PeriodoId").value;
		var deptId 		= document.getElementById("DeptId").value;
		var catId 		= document.getElementById("CatId").value;
		location.href = "periodoAlum?PeriodoId="+periodoId+"&DeptId="+deptId+"&CatId="+catId;
	}
	
	function cambiarDepartamento(){
		var periodoId 	= document.getElementById("PeriodoId").value;
		var deptId 		= document.getElementById("DeptId").value;
		var catId 		= document.getElementById("CatId").value;
		location.href = "cambiarDept?PeriodoId="+periodoId+"&DeptId="+deptId+"&CatId="+catId;
	}
	
	function Borrar(PeriodoId, DeptId, CatId) {
		if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
			document.location = "borrarPeriodo?PeriodoId="+PeriodoId+"&DeptId="+DeptId+"&CatId="+CatId;
		}
	}
	
</script>
<%
	String codigoAlumno 						= (String)session.getAttribute("codigoAlumno");
	String nombreAlumno							= (String)request.getAttribute("nombreAlumno");
	String nombrePlan							= (String)request.getAttribute("nombrePlan");
	String horasAlcanzado						= (String)request.getAttribute("alumnoHorasAlc")==null?"0":(String)request.getAttribute("alumnoHorasAlc");
	String horasTotAlcanzado					= (String)request.getAttribute("alumnoHorasTotAlc")==null?"0":(String)request.getAttribute("alumnoHorasTotAlc");
	String residenciaEstado						= (String)request.getAttribute("residenciaEstado");
	String deptId 								= (String)request.getAttribute("deptId");
	String catId 								= (String)request.getAttribute("catId");
	String periodoId 							= (String)request.getAttribute("periodoId");
	boolean esAlumno 							= (boolean)request.getAttribute("esAlumno");
	boolean esAdmin 							= (boolean)request.getAttribute("esAdmin");
	boolean esDirector 							= (boolean)request.getAttribute("esDirector");
	String mensaje								= (String)request.getParameter("Mensaje")==null?"-":(String)request.getParameter("Mensaje");

	AlumAcademico alumAcademico 				= (AlumAcademico) request.getAttribute("alumAcademico");
	
	List<TrabDepartamento> lisDepartamentos 	= (List<TrabDepartamento>)request.getAttribute("lisDepartamentos");
	List<TrabCategoria> lisCategorias 			= (List<TrabCategoria>)request.getAttribute("lisCategorias");
	List<TrabPeriodo> lisPeriodos 				= (List<TrabPeriodo>)request.getAttribute("lisPeriodos");
	List<TrabAlum> lisAlumPeriodos				= (List<TrabAlum>)request.getAttribute("lisAlumPeriodos");
	
	HashMap<String, String> mapaAlumnoNombre		= (HashMap<String, String>)request.getAttribute("mapaAlumnoNombre");
	HashMap<String, String> mapaDeptNombre			= (HashMap<String, String>)request.getAttribute("mapaDeptNombre");
	HashMap<String, String> mapaCatNombre			= (HashMap<String, String>)request.getAttribute("mapaCatNombre");
	HashMap<String, String> mapaPeriodoNombre		= (HashMap<String, String>)request.getAttribute("mapaPeriodoNombre");
	HashMap<String, String> mapaPeriodoHoras		= (HashMap<String, String>)request.getAttribute("mapaPeriodoHoras");
	HashMap<String, String> mapaPeriodoHorasTotales	= (HashMap<String, String>)request.getAttribute("mapaPeriodoHorasTotales");
	
	String datosAlumno							= esAlumno?codigoAlumno+" - "+nombreAlumno:"Select a Student";
	String datosPlan							= esAlumno?"<b>Plan:</b> " + nombrePlan: "";
	String datosResidencia						= residenciaEstado.equals("I")?" <b>Residence:</b> Boarding Student":" <b>Residence:</b> Day Student";
	
%>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<div class="container-fluid">
	<h2>Assign CTP <small class="text-muted fs-5">( <%=datosAlumno %> <%=datosPlan %> <%=datosResidencia%> )</small></h2>	
	<form action="grabar" name="frmPeriodoAlum" method="post">	
<%
	if(mensaje.equals("1")){
%>
	<div class="alert alert-info">
		Error: The student was previously assigned to a department in this period!
	</div>
<%
	}else if (mensaje.equals("2")){
%>
	<div class="alert alert-info">
		New Department has been assigned for student !
	</div>
<%
	}else if (mensaje.equals("3")){
%>
	<div class="alert alert-info">
		Department has been updated for student !
	</div>
<%
	}else if (mensaje.equals("4")){
%>
	<div class="alert alert-info">
		Error updating work line for student !
	</div>
<%
	}
%>
	<div class="alert alert-info d-flex align-items-center">
		Period:&nbsp;
		<select name="PeriodoId" id="PeriodoId" style="width:300px;" class="form-select">
<%
	for(TrabPeriodo periodo: lisPeriodos){
%>
			<option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"selected":""%>><%=periodo.getNombrePeriodo() %></option>
<%
	}
%>
		</select> &nbsp; &nbsp;
		Department:&nbsp;
		<select name="DeptId" id="DeptId" style="width:280px;" onchange="javascript:cambioDepartamento();" class="form-select">
<%
	for(TrabDepartamento departamento: lisDepartamentos){	
%>
			<option value="<%=departamento.getDeptId() %>" <%=departamento.getDeptId().equals(deptId)?"selected":""%>><%=departamento.getNombre() %></option>
<%
	}
%>
		</select> &nbsp; &nbsp;	
		Category:&nbsp;
		<select name="CatId" id="CatId" style="width:280px;" class="form-select">
<%
	for(TrabCategoria categoria: lisCategorias){
%>
			<option value="<%=categoria.getCategoriaId()%>" <%=categoria.getCategoriaId().equals(catId)?"selected":""%>><%=categoria.getNombreCategoria() %></option>
<%
	}
%>
		</select> &nbsp; &nbsp;
		Hours:&nbsp;
		<input type="text" name="Horas" id="Horas" class="form-control" style="width:100px" value>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="submit" class="btn btn-primary">Assign</button>
	</div>
	</form>
	<table id="table" class="table table-sm table-bordered">
		<thead class="table-info">
		<tr>
			<th width="2%">#</th>
			<th width="5%">Op.</th>
			<th width="15%"><spring:message code="aca.Alumno"/></th>
			<th width="10%">Period</th>
			<th width="10%">Department</th>
			<th width="15%">Category</th>		
			<th width="6%">Assigned</th>	
			<th width="6%">Completed</th>
			<th width="6%">Surplus</th>
			<th width="10%">Residence</th>
			<th width="5%">Payment</th>
			<th width="5%" class="text-center"><i class="fas fa-sync-alt"></i></th>												
		</tr>
		</thead>
<%
	int count = 1;
	for(TrabAlum periodoAlum: lisAlumPeriodos){
		
		String nombreDept 		= "-";
		String nombreCat 		= "-";
		String nombrePeriodo 	= "-";
		String estadoPeriodo	= periodoAlum.getEstado();
		String colorActivo		= "";
		String horasPeriodo 	= "0";
		String horasTotPeriodo =  "0";

		if(estadoPeriodo.equals("A")){ 
			colorActivo = "bg-success"; 
		}
		
		if(mapaDeptNombre.containsKey(periodoAlum.getDeptId())){
			nombreDept = mapaDeptNombre.get(periodoAlum.getDeptId());
		}
		
		if(mapaCatNombre.containsKey(periodoAlum.getCatId())){
			nombreCat = mapaCatNombre.get(periodoAlum.getCatId());
		}
		if(mapaPeriodoNombre.containsKey(periodoAlum.getPeriodoId())){
			nombrePeriodo = mapaPeriodoNombre.get(periodoAlum.getPeriodoId());
		}

		if(mapaPeriodoHoras.containsKey(periodoAlum.getPeriodoId())){
			horasPeriodo = mapaPeriodoHoras.get(periodoAlum.getPeriodoId());
		}

		if(mapaPeriodoHorasTotales.containsKey(periodoAlum.getPeriodoId())){
			horasTotPeriodo = mapaPeriodoHorasTotales.get(periodoAlum.getPeriodoId());
		}

		Double dHorasPeriodo = Double.parseDouble(horasPeriodo);
		Double dHorasTotPeriodo = Double.parseDouble(horasTotPeriodo);
		Double dSurplus = dHorasTotPeriodo - dHorasPeriodo;

		
%>
		<tr class="<%=colorActivo%>" style="--bs-bg-opacity: .5;">
			<td><%=count %></td>
			<td class="text-center">
<% 		if(esAdmin || esDirector){
			if (horasPeriodo.equals("0")){ %>			
				<a class="" href="editarPeriodo?PeriodoId=<%=periodoAlum.getPeriodoId()%>&DeptId=<%=periodoAlum.getDeptId()%>&CatId=<%=periodoAlum.getCatId() %>"><i class="fas fa-edit"></i></a>
<%				if(estadoPeriodo.equals("I")){ %>
				<a class="fas fa-trash-alt text-danger" href="javascript:Borrar('<%=periodoAlum.getPeriodoId()%>','<%=periodoAlum.getDeptId()%>','<%=periodoAlum.getCatId()%>')"></a>	
<%				} %>
<% 			}else{ %>
<%				if(estadoPeriodo.equals("A")){	%>			
				<a class="" href="editarPeriodo?PeriodoId=<%=periodoAlum.getPeriodoId()%>&DeptId=<%=periodoAlum.getDeptId()%>&CatId=<%=periodoAlum.getCatId()%>"><i class="fas fa-edit"></i></a>	
<%				} %>
<% 			} 
			if(estadoPeriodo.equals("A")){
%>
				<a href="acreditarHoras?PeriodoId=<%=periodoAlum.getPeriodoId()%>&DeptId=<%=periodoAlum.getDeptId()%>&CatId=<%=periodoAlum.getCatId()%>" title="Credit Hours"><i class="fas fa-plus-square"></i></a>
<%			}
		} %>
			</td>
			<td><%=nombreAlumno%></td>
			<td><%=nombrePeriodo%></td>
			<td><%=nombreDept%></td>
			<td><%=nombreCat%></td>
			<td><%=periodoAlum.getHoras() %></td>
			<td><%=horasPeriodo%></td>
			<td><%=String.format("%.2f", dSurplus)%></td>
			<td><%=estadoPeriodo.equals("A")?residenciaEstado.equals("I")?"Boarding Student":"Day Student":"" %></td>
			<td><%=periodoAlum.getPago().equals("1")?"Yes":"No" %></td>
			<td class="text-center">
<%
				if(estadoPeriodo.equals("A")){
%>
				<a class="btn btn-primary btn-sm" href="cambiarDept?PeriodoId=<%=periodoAlum.getPeriodoId()%>&DeptId=<%=periodoAlum.getDeptId()%>&CatId=<%=periodoAlum.getCatId()%>&horas=<%=periodoAlum.getHoras()%>">
				  		Change
				</a>
<%
				}
%>
			</td>
		</tr>
<%
		count++;
	}
%>			
	</table>
</div>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
	jQuery(".chosen").chosen();
</script>