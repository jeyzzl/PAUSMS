<%@page import="aca.puestos.PuestoRH"%>
<%@page import="aca.puestos.PuestoFuncionRH"%>
<%@page import="aca.puestos.Seccion"%>
<%@page import="aca.puestos.CatCategoria"%>
<%@page import="java.util.Iterator"%>
<%@page import="aca.puestos.OpPuestos"%>
<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	OpPuestos op = new OpPuestos(conEnoc);

		String idseccion = request.getParameter("idseccion");

		boolean tieneDatos = false;

		PuestoRH prh = op.getPuestoRh(new Integer(idseccion));

		if (prh != null) {
			tieneDatos = true;
		}

		Iterator<CatCategoria> itCat = op.lsCategorias().iterator();
		Seccion seccion = op.aronPuesto(new Integer(idseccion));

		String strPuesto = "";
		String strCategoria = "";

		if (seccion != null) {
			strPuesto = seccion.getDescripcion_nueva();
			strCategoria = seccion.getCategoria_id() + "";
		}

		String error = "";
		if (request.getParameter("addPuesto") != null) {
			if (!tieneDatos) {
				if (op.insertPuesto(request) != 0) {
					response.sendRedirect("humanos");
				} else {
					error = "No se pudieron guardar los datos";
				}
			}else{
				if(op.actualizaPuesto(request)!=0){
					response.sendRedirect("humanos");
				} else {
					error = "No se pudieron guardar los datos";
				}
			}
		}
%>
<div class="container">
	<div class="alert alert-info">
		<h2>
			Agregar los datos al puesto - <%=strPuesto%>
		</h2>
	</div>

	<form action="" method="post" name="" id="" class="form-horizontal">
		<table class="table" align="center">

			<%
				if (!error.equals("")) {
			%>
			<tr>
				<td colspan="2"><%=error%></td>
			</tr>
			<%
				}
			%>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td><strong>Categoría</strong></td>
				<td><select name="id_categoria" class="form-control"
					id="id_categoria">
						<option disabled="disabled">Seleccione una categoria...</option>
						<%
							while (itCat.hasNext()) {
									CatCategoria cat = itCat.next();
						%>
						<option value="<%=cat.getId()%>"
							<%
							
							if (tieneDatos) {
								
						if (prh.getId_categoria() == cat.getId()) {   %>
							selected="selected"
							<%}
					} else if (seccion.getCategoria_id() == cat.getId()) {%>
							selected="selected" <%}%>><%=cat.getNombre()%></option>
						<%
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td><strong>Objetivo del puesto</strong></td>
				<td><textarea name="objetivo_puesto" id="textarea" cols="45"
						class="form-control"><%	if (tieneDatos) {%><%=prh.getObjetivo_puesto()%><% } %></textarea></td>
			</tr>
			<tr>
				<td colspan="2"><strong>Funciones</strong></td>
			</tr>
			<tr>
				<td colspan="2">

					<table style="width:100%">
						<tr>
							<th><strong>Acción</strong></th>
							<th><strong><spring:message code='aca.Descripcion'/></strong></th>
							<td><div style="text-align: right; vertical-align: central;">
									<a href="accionFunciones?sec=<%=idseccion%>"
										class="btn btn-primary">Agregar Función</a>
								</div></td>
						</tr>
						<%
							Iterator<PuestoFuncionRH> itfun = op.lsFuncionesSeccion(
										new Integer(idseccion)).iterator();
								while (itfun.hasNext()) {
									PuestoFuncionRH pfrh = itfun.next();
						%>
						<tr>
							<td><%=op.getVerbo(pfrh.getVerbo())%></td>
							<td colspan="2"><%=pfrh.getDescripcion()%></td>
						</tr>
						<%
							}
						%>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2"><strong>Escolaridad Requerida</strong></td>
			</tr>
			<tr>
				<td colspan="2">
					<table style="width:100%">
						<tr>
							<td><label>Estudios requeridos</label> <select
								name="estudios" class="form-control" id="estudios">
									<option value="0"
										<%if (tieneDatos) {
					if (prh.getEstudios().equals("0")) {%>
										selected="selected" <%}
				}%>>No aplica</option>
									<option value="1"
										<%if (tieneDatos) {
					if (prh.getEstudios().equals("1")) {%>
										selected="selected" <%}
				}%>>Primaria</option>
									<option value="2"
										<%if (tieneDatos) {
					if (prh.getEstudios().equals("2")) {%>
										selected="selected" <%}
				}%>>Secundaria</option>
									<option value="3"
										<%if (tieneDatos) {
					if (prh.getEstudios().equals("3")) {%>
										selected="selected" <%}
				}%>>Bachillerato</option>
									<option value="4"
										<%if (tieneDatos) {
					if (prh.getEstudios().equals("4")) {%>
										selected="selected" <%}
				}%>>Bachillerato Tecnico</option>
									<option value="5"
										<%if (tieneDatos) {
					if (prh.getEstudios().equals("5")) {%>
										selected="selected" <%}
				}%>>Licenciatura</option>
									<option value="6"
										<%if (tieneDatos) {
					if (prh.getEstudios().equals("6")) {%>
										selected="selected" <%}
				}%>>Licenciatura con
										especialidad</option>
									<option value="7"
										<%if (tieneDatos) {
					if (prh.getEstudios().equals("7")) {%>
										selected="selected" <%}
				}%>>Maestría</option>
									<option value="8"
										<%if (tieneDatos) {
					if (prh.getEstudios().equals("8")) {%>
										selected="selected" <%}
				}%>>Doctorado</option>
							</select></td>
							<td><label>Situación de avance</label> <select
								name="avance_estudios" class="form-control" id="avance_estudios">
									<option value="0"
										<%if (tieneDatos) {
					if (prh.getAvance_estudios().equals("0")) {%>
										selected="selected" <%}
				}%>>No aplica</option>
									<option value="1"
										<%if (tieneDatos) {
					if (prh.getAvance_estudios().equals("1")) {%>
										selected="selected" <%}
				}%>>Terminado con Titulo o
										certificado</option>
									<option value="2"
										<%if (tieneDatos) {
					if (prh.getAvance_estudios().equals("2")) {%>
										selected="selected" <%}
				}%>>Terminado</option>
									<option value="3"
										<%if (tieneDatos) {
					if (prh.getAvance_estudios().equals("3")) {%>
										selected="selected" <%}
				}%>>Servicio social</option>
									<option value="4"
										<%if (tieneDatos) {
					if (prh.getAvance_estudios().equals("4")) {%>
										selected="selected" <%}
				}%>>Trunco o inconclusos</option>
									<option value="5"
										<%if (tieneDatos) {
					if (prh.getAvance_estudios().equals("5")) {%>
										selected="selected" <%}
				}%>>Cursando aun</option>
							</select></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table>
						<tr>
							<td><strong>Experiencia anterior</strong></td>
							<td><strong><spring:message code="aca.Edad"/></strong></td>
							<td><strong>Genero</strong></td>
						</tr>
						<tr>
							<td><select name="experiencia" class="form-control"
								id="experiencia">
									<option value="0"
										<%if (tieneDatos) {
					if (prh.getExperiencia().equals("0")) {%>
										selected="selected" <%}
				}%>>No aplica</option>
									<option value="1"
										<%if (tieneDatos) {
					if (prh.getExperiencia().equals("1")) {%>
										selected="selected" <%}
				}%>>Minima (un año o menos)</option>
									<option value="2"
										<%if (tieneDatos) {
					if (prh.getExperiencia().equals("2")) {%>
										selected="selected" <%}
				}%>>Media (de 1 a 2 años)</option>
									<option value="3"
										<%if (tieneDatos) {
					if (prh.getExperiencia().equals("3")) {%>
										selected="selected" <%}
				}%>>Alta (3 años o más)</option>
							</select></td>
							<td><select name="edad" class="form-control" id="edad">
									<option value="0"
										<%if (tieneDatos) {
					if (prh.getEdad_maxima().equals("0")) {%>
										selected="selected" <%}
				}%>>No aplica</option>
									<option value="1"
										<%if (tieneDatos) {
					if (prh.getEdad_maxima().equals("1")) {%>
										selected="selected" <%}
				}%>>18-25 años</option>
									<option value="2"
										<%if (tieneDatos) {
					if (prh.getEdad_maxima().equals("2")) {%>
										selected="selected" <%}
				}%>>26-35 años</option>
									<option value="3"
										<%if (tieneDatos) {
					if (prh.getEdad_maxima().equals("3")) {%>
										selected="selected" <%}
				}%>>36-45 años</option>
									<option value="4"
										<%if (tieneDatos) {
					if (prh.getEdad_maxima().equals("4")) {%>
										selected="selected" <%}
				}%>>46-56 años</option>
							</select></td>
							<td><select name="sexo" class="form-control" id="sexo">
									<option value="0"
										<%if (tieneDatos) {
					if (prh.getSexo().equals("0")) {%>
										selected="selected" <%}
				}%>>No aplica</option>
									<option value="1"
										<%if (tieneDatos) {
					if (prh.getSexo().equals("1")) {%>
										selected="selected" <%}
				}%>>Masculino</option>
									<option value="2"
										<%if (tieneDatos) {
					if (prh.getSexo().equals("2")) {%>
										selected="selected" <%}
				}%>>Femenino</option>
							</select></td>
						</tr>
					</table>
				</td>

			</tr>
			<tr>
				<td><input type="hidden" name="id_seccion" id="id_seccion"
					value="<%=request.getParameter("idseccion")%>" /></td>
				<td><a href="humanos"
										class="btn btn-primary"><spring:message code="aca.Regresar"/></a> <input type="submit" name="addPuesto" id="add"
					value="Agregar" class="btn btn-primary" /></td>
			</tr>
		</table>
		<p></p>

	</form>

</div>
<%@ include file="../../cierra_enoc.jsp"%>