
<%@page import="java.util.Collections"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.portafolio.PorPuesto"%>
<%@page import="aca.portafolio.InfoEncPuesto"%>
<%@page import="java.util.List"%>
<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>


<%@ include file="menuPortal.jsp"%>
<link rel="stylesheet" href="js/chosen.css" type="text/css"	media="screen" />
<%
	String codigoPersonal = (String) session.getAttribute("codigoPersonal");
		String periodoId = (String) session.getAttribute("porPeriodo");

		String accion = request.getParameter("Accion") == null ? "true" : request.getParameter("Accion");
		String msj = "";
		InfoEncPuesto p = new InfoEncPuesto();

		Map<Integer, String> mapPueRefe = new HashMap<Integer, String>();
		mapPueRefe.putAll(p.getPuestosReferencias(conEnoc));

		List<PorPuesto> lsDescP = new ArrayList<PorPuesto>();
		lsDescP.addAll(p.getPuesto(conEnoc, codigoPersonal, request.getParameter("departamento"),
				request.getParameter("puesto_id")));

		boolean tieneDescripcion = false;
		if (!lsDescP.isEmpty()) {
			tieneDescripcion = true;
		}

		if (request.getParameter("enviar") != null) {
			System.out.println("entra " + request.getParameter("enviar"));
			if (!tieneDescripcion) {

				System.out.println("entra 2");

				p.addDescPuesto(conEnoc, request);
				response.sendRedirect("puestoFront");
			} else {
				p.updDescPuesto(conEnoc, request);
				response.sendRedirect("puestoFront");
			}
		}

		PorPuesto pp = new PorPuesto();
		pp.setObjetivo_puesto("");
		pp.setJuntas_pertenece("");
		pp.setJuntas_preside("");
		pp.setPersonal_dependiente("");
		pp.setDeberes_repe("");
		pp.setDeberes_peri("");
		pp.setDeberes_irre("");
		pp.setSupervision("");
		pp.setRelaciones_inte("");
		pp.setRelaciones_exte("");
		pp.setEscolaridad("");
		pp.setDestreza("");
		pp.setResponsabilidad("");
		pp.setErgonomia("");
		pp.setAmbiente_trabajo("");
		pp.setRiesgo("");
		pp.setEsfuerzo("");

		for (PorPuesto pu : lsDescP) {
			pp = pu;
		}
%>
<script>
	function target_popup(form) {
		window.open('', 'formpopup',
				'width=400,height=400,resizeable,scrollbars');
		form.target = 'formpopup';
	}
</script>
<style>
h3 {
	margin-bottom: 10px;
}
</style>

<div class="container-fluid">

	<h2>
		Descripcion del Puesto <small>(<%=periodoId%>)
		</small>
	</h2>

	<%=msj%>

	<hr />

	<div>
		<form action="puestoReferencia" method="post" name="verPuestoBase"
			class="form-inline" onsubmit="target_popup(this)">
			<div class="form-group">
				<label for="Select">Puestos referencia: <select
					name="refePue" class="chosen-select">
					<%
						List<Integer> lsIdPue = new ArrayList(mapPueRefe.keySet());
							Collections.sort(lsIdPue);
							for (Integer idp : lsIdPue) {
					%>
					<option value="<%=idp%>"><%= mapPueRefe.get(idp).toUpperCase() %></option>
					<%
						}
					%>
				</select></label> 
			</div>
			<div class="form-group">
				<input type="submit" name="enviar" value="Cosultar">
			</div>
		</form>
	</div>


	<form action="" method="post" name="forma">
		<input type="hidden" name="Accion" />

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">
			<h3>1. Objetivo del Puesto</h3>
			<textarea name="objetivo_puesto" id="objetivo_puesto" rows="2"
				style="width: 100%;"><%=pp.getObjetivo_puesto()%></textarea>
		</div>

		<div style="clear: both;"></div>
		<br>

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>2. Juntas a las que pertenece en función de su cargo</h3>
			<textarea name="juntas_pertenece" id="juntas_pertenece" rows="2"
				style="width: 100%;"><%=pp.getJuntas_pertenece()%></textarea>

		</div>

		<div style="clear: both;"></div>
		<br>

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>3. Juntas que preside en función de su cargo</h3>
			<textarea name="juntas_preside" id="juntas_preside" rows="2"
				style="width: 100%;"><%=pp.getJuntas_preside()%></textarea>

		</div>

		<div style="clear: both;"></div>
		<br>
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>4. Puestos dependientes</h3>
			<textarea name="personal_dependiente" id="personal_dependiente"
				rows="2" style="width: 100%;"><%=pp.getPersonal_dependiente()%></textarea>

		</div>

		<div style="clear: both;"></div>
		<br>

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>5. Deberes repetitivos</h3>
			<textarea name="deberes_repe" id="deberes_repe" rows="2"
				style="width: 100%;"><%=pp.getDeberes_repe()%></textarea>

		</div>

		<div style="clear: both;"></div>
		<br>

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>6. Deberes periodicos</h3>
			<textarea name="deberes_peri" id="deberes_peri" rows="2"
				style="width: 100%;"><%=pp.getDeberes_peri()%></textarea>

		</div>

		<div style="clear: both;"></div>
		<br>

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>7. Deberes irregulares</h3>
			<textarea name="deberes_irre" id="deberes_irre" rows="2"
				style="width: 100%;"><%=pp.getDeberes_irre()%></textarea>

		</div>

		<div style="clear: both;"></div>
		<br>

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>8. Supervision recibida y dada</h3>
			<textarea name="supervision" id="supervision" rows="2"
				style="width: 100%;"><%=pp.getSupervision()%></textarea>

		</div>

		<div style="clear: both;"></div>
		<br>

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>9. Relaciones internas</h3>
			<textarea name="relaciones_inte" id="relaciones_inte" rows="2"
				style="width: 100%;"><%=pp.getRelaciones_inte()%></textarea>

		</div>

		<div style="clear: both;"></div>
		<br>

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>10. Relaciones externas</h3>
			<textarea name="relaciones_exte" id="relaciones_exte" rows="2"
				style="width: 100%;"><%=pp.getRelaciones_exte()%></textarea>

		</div>

		<div style="clear: both;"></div>
		<br>

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>11. Escolaridad</h3>
			<textarea name="escolaridad" id="escolaridad" rows="2"
				style="width: 100%;"><%=pp.getEscolaridad()%></textarea>

		</div>

		<div style="clear: both;"></div>
		<br>

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>12. Destreza</h3>
			<textarea name="destreza" id="destreza" rows="2" style="width: 100%;"><%=pp.getDestreza()%></textarea>

		</div>

		<div style="clear: both;"></div>
		<br>

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>13. Responsabilidad</h3>
			<textarea name="responsabilidad" id="responsabilidad" rows="2"
				style="width: 100%;"><%=pp.getResponsabilidad()%></textarea>

		</div>

		<div style="clear: both;"></div>
		<br>

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>14. Ergonomia</h3>
			<textarea name="ergonomia" id="ergonomia" rows="2"
				style="width: 100%;"><%=pp.getErgonomia()%></textarea>

		</div>

		<div style="clear: both;"></div>
		<br>

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>15. Ambiente de trabajo</h3>
			<textarea name="ambiente_trabajo" id="ambiente_trabajo" rows="2"
				style="width: 100%;"><%=pp.getAmbiente_trabajo()%></textarea>

		</div>

		<div style="clear: both;"></div>
		<br>

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>16. Riesgo</h3>
			<textarea name="riesgo" id="riesgo" rows="2" style="width: 100%;"><%=pp.getRiesgo()%></textarea>

		</div>

		<div style="clear: both;"></div>
		<br>

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>17. Esfuerzo</h3>
			<textarea name="esfuerzo" id="esfuerzo" rows="2" style="width: 100%;"><%=pp.getEsfuerzo()%></textarea>

		</div>
		
		<div style="clear: both;"></div>
		<br>

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>18. Indicadores de desempeño</h3>
			<textarea name="estandares_dese" id="estandares_dese" rows="2" style="width: 100%;"><%=pp.getEstandares_dese() %></textarea>

		</div>

		<div style="clear: both;"></div>
		<br>
		<%
			if (tieneDescripcion) {
		%>
		<input type="hidden" name="id" value="<%=pp.getId()%>">
		<%
			}
		%>

		<input type="hidden" name="puesto_id"
			value="<%=request.getParameter("puesto_id")%>"> <input
			type="hidden" name="departamento"
			value="<%=request.getParameter("departamento")%>"> <input
			type="hidden" name="numnomina" value="<%=codigoPersonal%>">
		<input type="hidden" name="Accion" value="guardar"> <input
			type="submit" class="btn btn-primary btn-block" name="enviar"
			value="Guardar Informacion"> <br>
		<br>
		<br>
		<br>
		<br>


	</form>
</div>	

<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="js/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript">
	$(".chosen-select").chosen();
</script>

<%@ include file="../../cierra_enoc.jsp"%>