<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="ADatos" class="aca.alerta.AlertaDatos" scope="page" />
<jsp:useBean id="periodoU" class="aca.alerta.AlertaPeriodoUtil" scope="page" />
<jsp:useBean id="ADatosUtil" class="aca.alerta.AlertaDatosUtil" scope="page" />
<jsp:useBean id="historial" class="aca.alerta.AlertaHistorial" scope="page" />
<jsp:useBean id="historialU" class="aca.alerta.AlertaHistorialUtil" scope="page" />
<jsp:useBean id="AntroUtil" class="aca.alerta.AlertaAntroUtil" scope="page" />
<jsp:useBean id="CarreraU" class="aca.catalogo.CatCarreraUtil" scope="page" />

<body>
	<html>
		<div class="container-fluid">
			<h2>Reportes</h2><br>
			<div class="alert alert-info">
				<a type="button" class="btn btn-info" href="empleados">Empleados</a>&nbsp;&nbsp;
				<a type="button" class="btn btn-info" href="alumnos">Alumnos</a>
			</div>
		</div>
	</html>
</body>