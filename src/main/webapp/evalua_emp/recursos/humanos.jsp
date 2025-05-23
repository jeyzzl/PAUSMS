

<%@page import="aca.puestos.PuestoRH"%>
<%@page import="java.util.Iterator"%>
<%@page import="aca.puestos.OpPuestos"%>
<%@page import="aca.puestos.Seccion"%>
<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	OpPuestos pue = new OpPuestos(conEnoc);

		Iterator<Seccion> arsec = pue.getAronPuestos().iterator();
%>
<body>
<div class="container">
	<div class="alert alert-info">
		<h1>Puestos UM</h1>
	</div>
	<%
		while (arsec.hasNext()) {
				Seccion s = arsec.next();
	%>
	<p class="bg-info">
		<Strong><%=s.getDescripcion_nueva()%>:</Strong>
		<%
			PuestoRH prh = pue.getPuestoRh(s.getSeccion_id());
					if (prh != null) {
		%>
		<div><%= prh.getObjetivo_puesto() %></div>
	<a href="accionPuestoRH?idseccion=<%= s.getSeccion_id() %>" class="btn btn-primary pull-right">Agregar
		datos</a>
		<%
			} else {
		%>
	
	<div>No hay informacion disponible</div>
	<a href="accionPuestoRH?idseccion=<%= s.getSeccion_id() %>" class="btn btn-primary pull-right">Agregar
		datos</a>
	<%
		}
	%>
	<div><hr></div>
	<%
		}
	%>
</div>
</body>
<%@ include file="../../cierra_enoc.jsp"%>