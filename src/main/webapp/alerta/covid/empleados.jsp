<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.HashMap" %>
<%@page import="java.util.List" %>

<%@ page import="aca.covid.spring.Covid"%>
<%@ page import="aca.vista.spring.Maestros"%>

<body>
<%
	HashMap<String, Maestros> mapa 	= (HashMap<String, Maestros>) request.getAttribute("mapa");
	List<Covid> lista 				= (List<Covid>) request.getAttribute("lista");
	
	Maestros empleado = new Maestros();
	
	int cont = 1;
%>
	<html>
		<div class="container-fluid">
			<h2>Reporte empleados</h2>
			<div class="alert alert-info">
				<a type="button" class="btn btn-primary" href="reporte">Regresar</a>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th>#</th>
						<th>Nomina</th>
						<th>Nombre</th>
						<th>Genero</th>
						<th>Edad</th>
						<th>HTA</th>
						<th>DM</th>
						<th>Enf. Cardio.</th>
						<th>Enf. Pul.</th>
						<th>Cancer</th>
						<th>Obe.</th>
						<th>Estres</th>
						<th>Depre.</th>
						<th>IMSS</th>
						<th>Pase</th>
						<th>ISSSTE</th>
						<th>HLC</th>
						<th>Privado</th>
						<th>Otro</th>
						<th>Ninguno</th>
					</tr>
				</thead>
				<tbody>
<% 				for(Covid datos : lista){
				
					if(mapa.containsKey(datos.getCodigoPersonal())){
					empleado = mapa.get(datos.getCodigoPersonal());			
%>
					<tr>
						<td><%=cont++%></td>
						<td><%=empleado.getCodigoPersonal()%></td>
						<td><%=empleado.getNombre()+" "+empleado.getApellidoPaterno()+" "+empleado.getApellidoMaterno()%></td>
						<td><%=empleado.getGenero()%></td>
						<td><%=aca.util.Fecha.edad(empleado.getfNacimiento(), aca.util.Fecha.getHoy())%></td>		
						<td><%=datos.getHipertension()%></td>
						<td><%=datos.getDiabetes()%></td>
						<td><%=datos.getCorazon()%></td>
						<td><%=datos.getPulmon()%></td>
						<td><%=datos.getCancer()%></td>
						<td><%=datos.getObesidad()%></td>
						<td><%=datos.getEstres()%></td>
						<td><%=datos.getDepresion()%></td>
						<td><%=datos.getImss()%></td>
						<td><%=datos.getPase()%></td>
						<td><%=datos.getIsste()%></td>
						<td><%=datos.getHlc()%></td>
						<td><%=datos.getPrivado()%></td>
						<td><%=datos.getOtro()%></td>
						<td><%=datos.getNinguno()%></td>
					</tr>
<% 					}
				}%>
				</tbody>
			</table>
		</div>
	</html>
</body>