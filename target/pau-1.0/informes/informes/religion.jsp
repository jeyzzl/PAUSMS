<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.catalogo.spring.CatReligion"%>


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
List<AlumPersonal> lisTodos 					= (List<AlumPersonal>)request.getAttribute("lisTodos");
HashMap<String, CatReligion> mapReligion		= (HashMap<String, CatReligion>)request.getAttribute("mapReligion");
%>
<body>
	<div class="container-fluid">
		<h2>Religion Report</h2>
		<div class="alert alert-info d-flex align-items-center">
			<a class="btn btn-primary" href="menu"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
			<input type="text" class="form-control search-query" placeholder="<spring:message code="aca.Buscar"/>" id="buscar" style="width:200px;">&nbsp;&nbsp;
			
		</div>
		<table id="table" class="table table-bordered table-sm">
			<thead class="table-dark table-info">
				<tr>
					<th width="2.5%">#</th>
					<th width="2.5%">ID</th>
					<th width="10%">Name</th>
					<th width="10%">Surname</th>
					<th width="6.25%">Religion</th>
					<th width="6.25%">Baptized</th>
					<th width="12.5%">Baptism Date</th>
				</tr>
			</thead>
			<tbody>
<%	
	int count = 0;
	for(AlumPersonal alum: lisTodos){
		count++;
		
		
		CatReligion religion = new CatReligion();
		if(mapReligion.containsKey(alum.getReligionId())){
			religion = mapReligion.get(alum.getReligionId());
		}
		
		
		
		
%>

	

				<tr>
					<td><%=count%></td>
					<td><%=alum.getCodigoPersonal() %></td>
					<td><%=alum.getNombre() %></td>
					<td><%=alum.getApellidoPaterno() %></td>
					<td><%=religion.getNombreReligion()%></td>
					<td><%=alum.getBautizado().equals("S")?"Yes":"No" %></td>
					<td><%=alum.getfBautizado()==null?"N/A":alum.getfBautizado() %> </td>
					
				</tr>
<%
	}
%>
			</tbody>
		</table>
	</div>
<script src="../../js/jquery-1.9.1.min.js"></script>
<script src="../../js/search.js"></script>	
<script>	
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>
</body>