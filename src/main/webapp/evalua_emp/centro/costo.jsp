<%@page import="aca.puestos.Seccion"%>
<%@page import="java.util.Iterator"%>
<%@page import="aca.puestos.OpPuestos"%>
<%@page import="aca.financiero.ContCcosto"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="aca.portafolio.PorJefes"%>
<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<div class="container">
	<div class="alert alert-info">Puestos Por departamento</div>
</div>
<%
	PorJefes jefes = new PorJefes();
		String codigoPersonal = "9800106";
		String idEjercicio = "001-2014";

		jefes.mapeaRegId(conEnoc, codigoPersonal);

		String[] ccostos = jefes.getDepartamentos().split("-");

		java.util.HashMap<String, aca.financiero.ContCcosto> centroCostos = aca.financiero.ContCcostoUtil.getMapCentrosCosto(conEnoc, idEjercicio);

		for (int i = 0; i < ccostos.length; i++) {

			ContCcosto mpccostos = centroCostos.get(ccostos[i]);
%>
<p>
	<strong><%=mpccostos.getNombre()%></strong>
<div>puestos en el depto</div>
<ul>
	<%
		OpPuestos pue = new OpPuestos(conEnoc);
				Iterator<String> ccpue = pue.puestosDepto(mpccostos.getIdCcosto(),mpccostos.getIdEjercicio()).iterator();
						while (ccpue.hasNext()) {
							String idse = ccpue.next();
					Seccion sec = pue.aronPuestob(new Integer(idse));
					if(sec!=null){
	%>
	<li><%=sec.getDescripcion_nueva()%></li> <%
					}
 	}
 %>
		
</ul>

</p>
<%
	}
%>


<%@ include file="../../cierra_enoc.jsp"%>