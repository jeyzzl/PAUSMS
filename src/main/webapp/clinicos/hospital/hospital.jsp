<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.rotaciones.spring.RotHospital"%>
<%@page import="aca.rotaciones.spring.RotInstitucion"%>
<%@page import="aca.rotaciones.spring.RotHospitalEspecialidad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	List<RotHospital> lisHospitales 						= (List<RotHospital>)request.getAttribute("lisHospitales");
	HashMap<String, RotInstitucion> mapaInstituciones 		= (HashMap<String, RotInstitucion>)request.getAttribute("mapaInstituciones");
	HashMap<String, String> mapaTotEspecialidades			= (HashMap<String, String>)request.getAttribute("mapaTotEspecialidades");
%>

<style>
	.icon-pencil, .icon-remove, .nombre{
		cursor: pointer;
	}
	.formato{
		margin:0;
		padding:5px;
		display:none;
	}
</style>
<div class="container-fluid">
	<h1>Catálogo de Hospitales</h1>
	<div class="alert alert-info">
		<a href="editar" class="btn btn-primary"><spring:message code="aca.Anadir"/></a>
	</div>
	<table style="width:100%" class="table table-sm table-bordered table-striped">
	<thead class="table-info">	
		<tr>
			<th>Acción</th>
			<th>Id</th>
			<th>Hospital</th>
			<th>Corto</th>
			<th>Institución</th>
			<th>Calle</th>
			<th>Colonia</th>
			<th>MunEdo</th>
			<th>Pais</th>
			<th>Telefono</th>
			<th>Fax</th>
			<th>Medico</th>
			<th>Puesto</th>
			<th>Saludo</th>
		</tr>
	</thead>
	<tbody>	
		<%
		for(RotHospital hospital: lisHospitales){
			
			String institucionNombre = "-";
			if(mapaInstituciones.containsKey(hospital.getInstitucionId())){
				institucionNombre = mapaInstituciones.get(hospital.getInstitucionId()).getInstitucionNombre();
			}
			
			String total = "0";
			if (mapaTotEspecialidades.containsKey(hospital.getInstitucionId())){
				total = mapaTotEspecialidades.get(hospital.getInstitucionId());	
			}
			
		%>
		<tr>
			<td>
				<a href="editar?HospitalId=<%= hospital.getHospitalId() %>"><i class="fas fa-edit"></i></a>
				&nbsp;&nbsp;&nbsp;
<%			if(total.equals("0")){ %>
				<a href="borrar?HospitalId=<%= hospital.getHospitalId() %>"><i class="fas fa-trash-alt"></i></a>
<%			} %>
			</td>
			<td class="id"><%=hospital.getHospitalId() %></td>
			<td class="nombre"><%=hospital.getHospitalNombre() %></td>
			<td class="clave"><%=hospital.getHospitalCorto() %></td>
			<td>
				<%=institucionNombre%>
				<input type="hidden" value="<%=hospital.getInstitucionId()%>" class="institucion">
			</td>
			<td><span class="calle"><%=hospital.getCalle()==null?"":hospital.getCalle() %></span></td>
			<td><span class="colonia"><%=hospital.getColonia()==null?"":hospital.getColonia() %></span></td>
			<td><span class="estado"><%=hospital.getMunEdo()==null?"":hospital.getMunEdo() %></span></td>
			<td><span class="pais"><%=hospital.getPais()==null?"":hospital.getPais() %></span></td>
			<td><span class="telefono"><%=hospital.getTelefono()==null?"":hospital.getTelefono() %></span></td>
			<td><span class="fax"><%=hospital.getFax()==null?"":hospital.getFax() %></span></td>
			<td><span class="medico"><%=hospital.getMedico()==null?"":hospital.getMedico() %></span></td>
			<td><span class="puesto"><%=hospital.getPuesto()==null?"":hospital.getPuesto() %></span></td>
			<td><span class="saludo"><%=hospital.getSaludo()==null?"":hospital.getSaludo() %></span></td>
		</tr>
		<%
			}
		%>
	</tbody>	
	</table>	
</div>