<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.carga.spring.Carga"%>
<%@ page import="aca.carga.spring.CargaFinanciero"%>
<%@ page import="aca.vista.spring.Inscritos"%>
<%@ page import="aca.internado.spring.IntDormitorio"%>


<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript" >
	
	
	function cambiarCarga(){
		var cargaId 	= document.getElementById("CargaId").value;
		var grado 		= document.getElementById("Grado").value;
		location.href = "inscritos?CargaId="+cargaId+"&Grado="+grado;
	}
	
	
	
</script>

<%

String cargaId 											= (String)request.getAttribute("cargaId");
String grado 											= (String)request.getAttribute("grado");
List<Carga> lisCargas									= (List<Carga>)request.getAttribute("lisCargas");
List<Inscritos> lisTodos								= (List<Inscritos>)request.getAttribute("lisTodos");

HashMap<String, IntDormitorio> mapaDormitorio 		= (HashMap<String, IntDormitorio>)request.getAttribute("mapaDormitorio");
HashMap<String, CargaFinanciero> mapaFinanciero 	= (HashMap<String, CargaFinanciero>)request.getAttribute("mapaFinanciero");


%>
<body>
	<div class="container-fluid">
		<h2>Enrolled Report</h2>
		<div class="alert alert-info d-flex align-items-center">
			<a class="btn btn-primary" href="menu"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
			
			Load:
			<select name="CargaId" id="CargaId" style="width:15rem;" class="form-select mx-2" onchange="javascript:cambiarCarga();">
			<%
				for(Carga carga: lisCargas){
			%>
				<option value="<%=carga.getCargaId()%>" <%=carga.getCargaId().equals(cargaId)?"selected":""%>><%=carga.getNombreCarga() %></option>
			<%
				}
			%>
			</select>
			Year:
			<select name="Grado" id="Grado" style="width:15rem;" class="form-select mx-2" onchange="javascript:cambiarCarga();">
				<option value="0" <%=grado.equals("0")?"selected":""%>>All</option>
				<option value="1" <%=grado.equals("1")?"selected":""%>>1</option>
				<option value="2" <%=grado.equals("2")?"selected":""%>>2</option>
				<option value="3" <%=grado.equals("3")?"selected":""%>>3</option>
				<option value="4" <%=grado.equals("4")?"selected":""%>>4</option>
			</select>
			
		</div>
		<table class="table table-bordered table-sm">
			<thead class="table-dark">
				<tr>
					<th width="2.5%">#</th>
					<th width="2.5%">ID</th>
					<th width="10%">Name</th>
					<th width="10%">Surname</th>
					<th width="10%">Status</th>
					<th width="6%">Date</th>
					<th width="4%">Plan ID</th>
					<th width="6.25%">Financial Clearance</th>
					<th width="6.25%">Boarding Status</th>
				</tr>
			</thead>
			<tbody>
<%	
	int count = 0;
	for(Inscritos insc: lisTodos){
		count++;
		
		
		String depName = "<span class='badge rounded-pill bg-secondary'>n/a</span>";
		
		if(mapaDormitorio.containsKey(insc.getDormitorio())){
			depName = mapaDormitorio.get(insc.getDormitorio()).getNombre();
		}

		String estadoFinanciero = "";
		if(mapaFinanciero.containsKey(insc.getCodigoPersonal())){
			estadoFinanciero =  mapaFinanciero.get(insc.getCodigoPersonal()).getStatus();
		}
		
%>

	

				<tr>
					<td><%=count%></td>
					<td><%=insc.getCodigoPersonal() %></td>
					<td><%=insc.getNombre() %></td>
					<td><%=insc.getApellidoPaterno() %></td>
					<td><%=insc.getResidenciaId().equals("I")?"Boarding":"Day Student"%></td>
					<td><%=insc.getfInscripcion()%></td>
					<td><%=insc.getPlanId()%></td>
					<td><%=estadoFinanciero.equals("A")?"<span class='badge rounded-pill bg-success'>Cleared":"<span class='badge rounded-pill bg-warning text-dark'>Not Cleared"%></span></td>
					<td><%=depName%></td>					
				</tr>
<%
	}
%>
			</tbody>
		</table>
	</div>
</body>