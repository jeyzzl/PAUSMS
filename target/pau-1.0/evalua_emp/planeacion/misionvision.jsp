<%@page import="aca.proyectos.EvaluacionBase"%>
<%@page import="java.util.List"%>
<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="menu_planeacion.jsp"%>

<%@page import="aca.proyectos.EmpLogro"%>


<%
	
	String periodoId = (String) session.getAttribute("porPeriodo");
		String ejercicio = "001-" + periodoId;
		String codigoPersonal = (String) session.getAttribute("codigoPersonal");

		EmpLogro logros = new EmpLogro(conEnoc);
// 		ArrayList<String> listEmpleados = logros.listLogrosJefe(codigoPersonal);
// 		ArrayList<String> jefedejefe = logros.soyJefeDe(codigoPersonal);
// 		List<EvaluacionBase> jefesList = logros.jefes();

		List<String> lsPuJefe = new ArrayList();
		List<String> lsEmpPue = new ArrayList();

		lsPuJefe.addAll(logros.getJefexDepto(codigoPersonal, ejercicio));
		
		
		
		for(String pue : lsPuJefe){
			
			System.out.println(pue + "\t" );
			lsEmpPue.addAll(logros.getEmpleadosxPuesto(pue, ejercicio, codigoPersonal));
			
		}
		
		
		
%>

<div class="container-fluid">

	<h2>Empleados</h2>

	<table class="table table-condensed table-bordered">
		<tr>
			<th>#</th>
			<th>Código</th>
			<th><spring:message code="aca.Nombre" /></th>
			<th></th>
			<th></th>
		</tr>
		<%
			int cont = 0;
				for (String empleado : lsEmpPue) {
					if(!empleado.equals(codigoPersonal)){
						cont++;
		%>
		<tr>
			<td><%=cont%></td>
			<td><%= empleado %></td>
			<td><a
				href="proyectos_empleado?empleado=<%= empleado %>">
					<%=aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, empleado,
								"NOMBRE")%>
			</a></td>
			<td>
	
				<form name="evaluaempl" method="post"
					action="https://academico.um.edu.mx/msurvey/codeGen">
					<input type="hidden" name="e"
						value="<%=empleado %>"> <input
						type="hidden" name="n"
						value="<%=aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, empleado,"NOMBRE")%>">
					<input type="hidden" name="s" value="4"> <input
						type="submit" name="e" id="e" value="Evaluar"
						class="btn btn-primary">
				</form> 
			</td>
		</tr>
		<%
					}
				}
		%>

	</table>

</div>

<script>
	jQuery('.empleados').addClass('active');
</script>

<%@ include file="../../cierra_enoc.jsp"%>