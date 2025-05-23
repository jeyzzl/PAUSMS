<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.trabajo.spring.TrabAlum"%>
<%@ page import="aca.trabajo.spring.TrabDepartamento"%>
<%@ page import="aca.trabajo.spring.TrabPeriodo"%>


<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript" >
	
function cambiarPeriodo(){
	var periodoId 	= document.getElementById("PeriodoId").value;
	location.href = "trabajo?PeriodoId="+periodoId;
}
	
	
</script>

<%

	String periodoId 											= (String)request.getAttribute("periodoId");
	List<TrabPeriodo> lisPeriodos							= (List<TrabPeriodo>)request.getAttribute("lisPeriodos");
	List<TrabAlum> lisTodos 							= (List<TrabAlum>)request.getAttribute("lisTodos");

	HashMap<String, AlumPersonal> mapAlumTrabajo 		= (HashMap<String, AlumPersonal>)request.getAttribute("mapAlumTrabajo");
	HashMap<String, TrabDepartamento> mapDept 			= (HashMap<String, TrabDepartamento>)request.getAttribute("mapDept");
	HashMap<String, String> mapMaestrosNombre			= (HashMap<String, String>)request.getAttribute("mapMaestrosNombre");
	
%>
<body>
	<div class="container-fluid">
		<h2>Workline Report</h2>
		<div class="alert alert-info d-flex align-items-center">
			<a class="btn btn-primary" href="menu"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
			
			Period:&nbsp;
		<select name="PeriodoId" id="PeriodoId" style="width:300px;" class="form-select" onchange="javascript:cambiarPeriodo();">
		<%
			for(TrabPeriodo periodo: lisPeriodos){
		%>
					<option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"selected":""%>><%=periodo.getNombrePeriodo() %></option>
		<%
			}
		%>
		</select> &nbsp; &nbsp;
			
		</div>
		<table class="table table-bordered table-sm">
			<thead class="table-dark">
				<tr>
					<th width="2.5%">#</th>
					<th width="2.5%">ID</th>
					<th width="10%">Name</th>
					<th width="10%">Surname</th>
					<th width="6.25%">Supervisor Name</th>
					<th width="6.25%">Department Name</th>
					<th width="12.5%">Total Hours</th>
				</tr>
			</thead>
			<tbody>
<%	
	int count = 0;
	for(TrabAlum trabAlum: lisTodos){
		count++;
		
		AlumPersonal alum = new AlumPersonal();
		TrabDepartamento dept = new TrabDepartamento();
		String nombre = "";
		
		if(mapAlumTrabajo.containsKey(trabAlum.getMatricula())){
			alum = mapAlumTrabajo.get(trabAlum.getMatricula());
		}
		
		if(mapDept.containsKey(trabAlum.getDeptId())){
			dept = mapDept.get(trabAlum.getDeptId());
		}
		if(mapMaestrosNombre.containsKey(dept.getCodigo_personal())){
			nombre = mapMaestrosNombre.get(dept.getCodigo_personal());
		}
		
		
		
		
		
		
%>

	

				<tr>
					<td><%=count%></td>
					<td><%=alum.getCodigoPersonal() %></td>
					<td><%=alum.getNombre() %></td>
					<td><%=alum.getApellidoPaterno() %></td>
					<td><%=nombre%></td>
					<td><%=dept.getNombre()%></td>
					<td><%=trabAlum.getHoras() %> </td>
					
				</tr>
<%
	}
%>
			</tbody>
		</table>
	</div>
</body>