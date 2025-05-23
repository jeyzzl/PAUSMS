<%@page import="aca.proyectos.EvaluacionBase"%>
<%@page import="java.util.List"%>
<%@page import="aca.proyectos.EmpLogro"%>

<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%@ include file="menuPortal.jsp"%>
<%
	String codigoId 		= (String) session.getAttribute("codigoId");
	String periodoId 		= (String) session.getAttribute("porPeriodo");
%>

<div class="container-fluid">
	<h2>Evaluaciones <small>(<%=periodoId%>)</small></h2>
	<hr />
	<div class="container">
		<blockquote>
			<p>
				<strong>Proceso de Evaluación de Desempeño</strong>
			</p>
			<p>Es una herramienta de gestión y aprendizaje organizacional que
				permite conocer y mejorar los sistemas, los procesos y la
				organización del trabajo, brindando información sobre qué y cómo
				hacemos nuestro trabajo, buscando generar mayor motivación y
				compromiso al promover el desarrollo personal, el involucramiento,
				la participación y el trabajo en equipo.</p>
			<footer>
				Ver el documeto completo <cite><a
					href="http://centauro.um.edu.mx/FIMPES-PE-PMC.pdf" target="new">aqui...</a></cite>
			</footer>
		</blockquote>


		<%
			boolean autoevaluacion = false;
				EmpLogro logros = new EmpLogro(conEnoc);
				String ejercicio = "001-" + periodoId;

				List<String> lsPuesto = new ArrayList<String>();
				lsPuesto.addAll(logros.getPuestoEmpleado(codPerUser, ejercicio));
				
				List<String> lsJefes = new ArrayList<String>();
				boolean isJefe = false;
				for (String pu : lsPuesto) {
					lsJefes.addAll(logros.getJefePuesto(pu, ejercicio));
				}
				
				for (String jefe : lsJefes) {
					if (jefe.equals(codPerUser)) {
						isJefe = true;
					}
				}

				int countautoevaluacion = 0;

				for (String jefe : lsJefes) {
					if (!codPerUser.equals(jefe)) {
		%>
		<div class="row">
			<div class="col-md-4">
				<form name="evaluaempl" method="get"
					action="https://academico.um.edu.mx/msurvey/codeGen.jsp">
					<input type="hidden" name="e"
						value="<%=codPerUser%>-<%=aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, jefe, "NOMBRE")%>">
					<input type="hidden" name="n"
						value="<%=aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, codPerUser, "NOMBRE")%>">
					<input type="hidden" name="s" value="6"> <input
						type="submit" name="e" id="e"
						value="Evaluar Jefe <%=aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, jefe, "NOMBRE")%> "
						class="btn btn-primary">
				</form>
			</div>
		</div>



		<%
			}

					if (isJefe) {
						if (countautoevaluacion == 0) {
							countautoevaluacion++;
		%>
		<div class="row">

			<div class="col-md-4">
				<form name="evaluaempl" method="get"
					action="https://academico.um.edu.mx/msurvey/codeGen.jsp">
					<input type="hidden" name="e" value="<%=codPerUser%>"> <input
						type="hidden" name="n"
						value="<%=aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, codPerUser, "NOMBRE")%>">
					<input type="hidden" name="s" value="7"> <input
						type="submit" name="e" id="e"
						value="Autoevaluacion <%=aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, codPerUser, "NOMBRE")%>"
						class="btn btn-primary">
				</form>
			</div>
		</div>
		<%
			
						}
					} 
					
					
				}
		%>
		<div class="row">

			<div class="col-md-4">
				<form name="evaluaempl" method="get"
					action="https://academico.um.edu.mx/msurvey/codeGen.jsp">
					<input type="hidden" name="e" value="<%=codPerUser%>"> <input
						type="hidden" name="n"
						value="<%=aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, codPerUser, "NOMBRE")%>">
					<input type="hidden" name="s" value="5"> <input
						type="submit" name="e" id="e" value="Autoevaluacion"
						class="btn btn-primary">
				</form>
			</div>
		</div>


		<div style="margin-left: 20px; float: left; background: white;"
			class="alert alert-info ">

			<table style="width:100%" class="table"
				style="width: 700px; border-bottom: 1px solid gray">
				<tr>
					<th colspan=4><font size="3">1. Evaluación del Jefe
							(25%)</font></th>
				</tr>
				<tr>
					<td><b><spring:message code="aca.Resultados" /></b></td>
				</tr>
			</table>

		</div>

		<div style="clear: both;"></div>
		<br>

		<div style="margin-left: 20px; float: left; background: white;"
			class="alert alert-info ">

			<table style="width:100%" class="table"
				style="width: 700px; border-bottom: 1px solid gray">
				<tr>
					<th colspan=4><font size="3">2. Evaluación de
							subordinados (15%)</font></th>
				</tr>
				<tr>
					<td><b><spring:message code="aca.Resultados" /></b></td>
				</tr>
			</table>

		</div>
	</div>
</div>

<script>
	jQuery('.evaluacion').addClass('active');
</script>
<%@ include file="../../cierra_enoc.jsp"%>