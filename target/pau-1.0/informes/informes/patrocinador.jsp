<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.catalogo.spring.CatPatrocinador"%>
<%@ page import="aca.carga.spring.Carga"%>
<%@ page import="aca.alumno.spring.AlumPatrocinador"%>


<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript" >
	
	
	function cambiarCarga(){
		var cargaId 	= document.getElementById("CargaId").value;
		console.log(cargaId);
		location.href = "patrocinador?CargaId="+cargaId;
	}
	
	
	
</script>

<%
String cargaId 											= (String)request.getAttribute("cargaId");
List<Carga> lisCargas									= (List<Carga>)request.getAttribute("lisCargas");
List<AlumPersonal> lisAlums								= (List<AlumPersonal>)request.getAttribute("lisAlums");


HashMap<String, AlumPatrocinador> mapAlumPatrocinador 	= (HashMap<String, AlumPatrocinador>)request.getAttribute("mapAlumPatrocinador");
HashMap<String, CatPatrocinador> mapCatPatrocinador 	= (HashMap<String, CatPatrocinador>)request.getAttribute("mapCatPatrocinador");
	
%>
<body>
	<div class="container-fluid">
		<h2>Sponsors Report</h2>
		<div class="alert alert-info d-flex align-items-center">
			<a class="btn btn-primary" href="menu"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
			
			Load:&nbsp;
		<select name="CargaId" id="CargaId" style="width:300px;" class="form-select" onchange="javascript:cambiarCarga();">
		<%
			for(Carga carga: lisCargas){
		%>
					<option value="<%=carga.getCargaId()%>" <%=carga.getCargaId().equals(cargaId)?"selected":""%>><%=carga.getNombreCarga() %></option>
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
					<th width="6.25%">Sponsor Name</th>
					<th width="6.25%">Sponsor Phone Number</th>
					<th width="12.5%">Parent Phone Number</th>
				</tr>
			</thead>
			<tbody>
<%	
	int count = 0;
	for(AlumPersonal alum: lisAlums){
		count++;
		
		String sponsorName = "No Sponsor";
		String sponsorPhone = "No Sponsor";
		String parentPhone = "";
		
		if(mapAlumPatrocinador.get(alum.getCodigoPersonal()) != null){
			AlumPatrocinador alumPat = mapAlumPatrocinador.get(alum.getCodigoPersonal());
			CatPatrocinador  catPatrocinador  = mapCatPatrocinador.get(alumPat.getPatrocinadorId());
			
			sponsorName = catPatrocinador.getNombrePatrocinador();
			sponsorPhone = catPatrocinador.getTelefono();
		}
		
		
		
		
		
		
%>

	

				<tr>
					<td><%=count%></td>
					<td><%=alum.getCodigoPersonal() %></td>
					<td><%=alum.getNombre() %></td>
					<td><%=alum.getApellidoPaterno() %></td>
					<td><%=sponsorName%></td>
					<td><%=sponsorPhone %></td>
					<td><%=parentPhone %> </td>
					
				</tr>
<%
	}
%>
			</tbody>
		</table>
	</div>
</body>