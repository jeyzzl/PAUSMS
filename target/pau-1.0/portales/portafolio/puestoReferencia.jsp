<%@page import="aca.portafolio.PuestoReferencia"%>
<%@page import="aca.portafolio.PorPuesto"%>
<%@page import="aca.portafolio.PuestoEnc"%>
<%@page import="aca.portafolio.InfoEncPuesto"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>

<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
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
<% 
if(request.getParameter("refePue")!=null){
	
	InfoEncPuesto pu = new InfoEncPuesto();
    PuestoReferencia pp = new PuestoReferencia();
    pp = pu.getReferencia(conEnoc, new Integer(request.getParameter("refePue")));
    
%>

<div >
      		
      		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">
			<h3>1. Objetivo del Puesto</h3>
			<div><%= pp.getObjetivopuesto() %></div>
		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>5. Deberes repetitivos</h3>
			<%= pp.getDeberesrepetitivos() %>

		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>6. Deberes periodicos</h3>
			<%= pp.getDeberesperiodicos() %>

		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>7. Deberes irregulares</h3>
			<%= pp.getDeberesirregulares() %>

		</div>

		
		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>9. Relaciones internas</h3>
			<%= pp.getRelacionesinternas() %>

		</div>

		<div style="clear: both;"></div>
		<br>
		
		<div
			style="Box-sizing: Border-box; width: 100%; float: left; background: white;"
			class="alert alert-info ">

			<h3>10. Relaciones externas</h3>
			<%= pp.getRelacionesexternas() %>

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

			<h3>15. Ambiente de trabajo</h3>
			<%= pp.getAmbientetrabajo() %>

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

      </div>
<% } %>
<%@ include file="../../cierra_enoc.jsp"%>