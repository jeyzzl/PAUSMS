<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="menu_planeacion.jsp"%>

<jsp:useBean id="PorJefes" scope="page" class="aca.portafolio.PorJefes"/>

<%
	
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");	
	String idEjercicio 		= (String) session.getAttribute("ejercicioId");
	
	PorJefes.setCodigoPersonal(codigoPersonal);
	if(PorJefes.existeReg(conEnoc)){
		PorJefes.mapeaRegId(conEnoc, codigoPersonal);
	}
	
	String[] ccostos = PorJefes.getDepartamentos().split("-");
	
	java.util.HashMap<String, aca.financiero.ContCcosto> centroCostos = aca.financiero.ContCcostoUtil.getMapCentrosCosto(conEnoc, idEjercicio);
%>

<div class="container-fluid">
	
	<h2>Centro de costos</h2>
	
	<table class="table table-condensed table-bordered">
		<tr>
			<th>#</th>
			<th>Ccosto</th>
			<th><spring:message code="aca.Nombre"/></th>
		</tr>
		<%
			int cont = 0;
			for(String costo : ccostos){
				if(costo.equals(""))continue;
				
				cont++;
				
				String nombre = "-";
				if(centroCostos.containsKey(costo)){
					nombre = centroCostos.get(costo).getNombre();
				}
		%>
				<tr>
					<td><%=cont %></td>
					<td><%=costo %></td>
					<td>
						<a href="proyectos_depto?ccosto=<%=costo%>">
							<%=nombre %>
						</a>
					</td>
				</tr>
		<%
			} 
		%>
	</table>

</div>

<script>
	jQuery('.ccostos').addClass('active');
</script>

<%@ include file="../../cierra_enoc.jsp"%>