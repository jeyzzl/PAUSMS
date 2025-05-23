<%@page import="aca.portafolio.PorPuesto"%>
<%@page import="aca.portafolio.PuestoEnc"%>
<%@page import="aca.portafolio.InfoEncPuesto"%>
<%@page import="java.util.List"%>
<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>
<style>
.textoRemarcado {
	font-weight: bold;
	font-style: italic;
}

.modal-backdrop {
	background-color: gray  !important;
}

.modal {
	position: fixed; !important;
	width: 700px !important ;
	top: auto !important;
	left: 40% !important;

	
}
.modal-body {
	overflow-y: auto !important;
	max-height: 400px !important;
	padding: 15px !important;
}

.modal-form {
	margin-bottom: 0 !important;
}

</style>

<%@ include file="menuPortal.jsp"%>
<div class="alert alert-info">
	<h3>Puesto(s) Actual(es)</h3>
</div>
<div class="container">

	<%
		String codigoPersonal = (String) session.getAttribute("codigoPersonal");
			String periodoId = (String) session.getAttribute("porPeriodo");
			String fechaHoy = aca.util.Fecha.getHoy();

			InfoEncPuesto encPuesto = new InfoEncPuesto();
			List<PuestoEnc> lsPE = new ArrayList<PuestoEnc>();
			lsPE.addAll(encPuesto.getEncabezado(conEnoc, codigoPersonal, "001-" + periodoId));
	//System.err.println("Puestos " + lsPE.size());
			for (PuestoEnc enc : lsPE) {
				
				List<PorPuesto> lsDescP = new ArrayList<PorPuesto>();
				lsDescP.addAll(encPuesto.getPuesto(conEnoc, codigoPersonal, enc.getCcosto(), enc.getPuesto_id().toString()));
				
				
	%>
<div class="panel panel-default">
  <div class="panel-body">
	<div style="font-size: 14px;">
		<div class="row" style="padding: 5px;">
			<div class="col-lg-3">Titulo del Puesto</div>
			<div class="col-lg-8 textoRemarcado" ><%=enc.getTituloPuesto()%></div>
		</div>
		<div class="row" style="padding: 5px;">
			<div class="col-md-3">Dependencia Administrativa</div>
			<div class="col-md-8 textoRemarcado"><%= enc.getDependenciaAdministrativa() %></div>
		</div>
		<div class="row" style="padding: 5px;">
			<div class="col-md-3">Direccion o departamento</div>
			<div class="col-md-8 textoRemarcado"><%=enc.getDireccionDepartamento()%></div>
		</div>
		<div class="row" style="padding: 5px;">
			<div class="col-md-3">Jefe Inmediato</div>
			<div class="col-md-8 textoRemarcado"><%=enc.getJefeInmediato()%></div>
		</div>
		<div class="row" style="padding: 5px;">
			<div class="col-md-3">Horario regular de trabajo</div>
			<div class="col-md-8 textoRemarcado"></div>
		</div>
		<div class="row" style="padding: 5px;">
			<div class="col-md-3">Grupo Salarial</div>
			<div class="col-md-8 textoRemarcado"><%=enc.getGrupoSalarial()%></div>
		</div>
		<div class="row" style="padding: 5px;">
			<div class="col-md-3">Rango de escalafón</div>
			<div class="col-md-8 textoRemarcado"><%=enc.getRangoEscalafon()%></div>
			
			<% if(!lsDescP.isEmpty()){ %>
			<button type="button" class="pull-right btn btn-primary" data-bs-toggle="modal" data-bs-target="#<%=codigoPersonal %><%= enc.getPuesto_id() %>">Ver Información</button>
			<% }else{ %>
			<a href="formPuesto?puesto_id=<%= enc.getPuesto_id() %>&departamento=<%= enc.getCcosto() %>" class="pull-right btn btn-primary">Agregar Información</a>
			<% } %>
		</div>
	</div>
	</div>
</div>
<!-- Modal -->
<div class="modal fade" id="<%= codigoPersonal %><%= enc.getPuesto_id() %>" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span >&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Descripción de Puesto</h4>
      </div>
      <div class="modal-body">
      		<%
      		//PorPuesto pp = lsDescP.get(0);
      		for(PorPuesto pp : lsDescP){
      		%>
      		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">
			<h3>1. Objetivo del Puesto</h3>
			<div><%= pp.getObjetivo_puesto() %></div>
		</div>

		<div style="clear: both;"></div>
		<br>

		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>2. Juntas a las que pertenece en función de su cargo</h3>
			<div><%= pp.getJuntas_pertenece() %></div>

		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>3. Juntas que preside en función de su cargo</h3>
			<%= pp.getJuntas_preside() %>

		</div>

		<div style="clear: both;"></div>
		<br>
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>4. Puestos dependientes</h3>
			<%= pp.getPersonal_dependiente() %>

		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>5. Deberes repetitivos</h3>
			<%= pp.getDeberes_repe() %>

		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>6. Deberes periodicos</h3>
			<%= pp.getDeberes_peri() %>

		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>7. Deberes irregulares</h3>
			<%= pp.getDeberes_irre() %>

		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>8. Supervision recibida y dada</h3>
			<%= pp.getSupervision() %>

		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>9. Relaciones internas</h3>
			<%= pp.getRelaciones_inte() %>

		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>10. Relaciones externas</h3>
			<%= pp.getRelaciones_exte() %>

		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>11. Escolaridad</h3>
			<%= pp.getEscolaridad() %>

		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>12. Destreza</h3>
			<%= pp.getDestreza() %>

		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>13. Responsabilidad</h3>
			<%= pp.getResponsabilidad() %>

		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>14. Ergonomia</h3>
			<%= pp.getErgonomia() %>

		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>15. Ambiente de trabajo</h3>
			<%= pp.getAmbiente_trabajo() %>

		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>16. Riesgo</h3>
			<%= pp.getRiesgo() %>
		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>17. Esfuerzo</h3>
			<%= pp.getEsfuerzo() %>

		</div>
		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>18. Indicadores de Desempeño</h3>
			<%= pp.getEstandares_dese() %>

		</div>
      		
      <% } %>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
        <a href="formPuesto?puesto_id=<%= enc.getPuesto_id() %>&departamento=<%= enc.getCcosto() %>" class="btn btn-primary">Modificar</a>
      </div>
    </div>
  </div>
</div>



	<%
		}
	%>

</div>


<%@ include file="../../cierra_enoc.jsp"%>