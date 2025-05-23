<%@ page import="java.util.List"%>
<%@ page import="aca.sep.spring.SepDatos"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String fecha = request.getParameter("Fecha") == null ? "0" : request.getParameter("Fecha");
	String filtro = request.getParameter("Filtro") == null ? "'ISCO2018'" : request.getParameter("Filtro");
	
	List<aca.Mapa> lisFechas = (List<aca.Mapa>) request.getAttribute("lisFechas");
	List<SepDatos> lisDatos = (List<SepDatos>) request.getAttribute("lisDatos");
%>
<body>
	<div class="container-fluid">
		<h1>Estadística 911</h1>
		<form name="frmSep" action="datos" method="post">
			<div class="alert alert-info">
				<select name="Fecha" onchange="javascript:document.frmSep.submit();">
					<%
						for (aca.Mapa mapa : lisFechas) {
					%>
					<option value="<%=mapa.getLlave()%>"
						<%=mapa.getLlave().equals(fecha) ? "selected" : ""%>><%=mapa.getLlave()%>
						[<%=mapa.getValor()%>]
					</option>
					<%
						}
					%>
				</select> 
				&nbsp;&nbsp; 
				<input name="Filtro" size="40" value="<%=filtro%>" />
				&nbsp;&nbsp; <a href="javascript:document.frmSep.submit();" class="btn btn-primary">Buscar</a>
			</div>
		</form>
		<table class="table table-bordered">
		<thead class="table-info">
			<tr>
				<th>N°</th>
				<th>Carrera</th>
				<th>Matricula</th>
				<th>Nombre</th>
				<th>Curp</th>
				<th>Grado</th>
				<th>Nacimiento</th>
				<th>Nuevo</th>
				<th>Antecedente</th>
				<th>Residencia</th>
				<th>Con Disc.</th>
				<th>Disc.</th>
				<th>Lengua</th>
			</tr>
		</thead>
			<%
				int row = 1;
			for (SepDatos sep : lisDatos) {
			%>
			<tr class="tr2">
				<td align="center"><%=row%></td>
				<td align="center"><%=sep.getCarrera()%></td>
				<td align="center"><%=sep.getMatricula()%></td>
				<td align="center"><%=sep.getNombre()%></td>
				<td align="center"><%=sep.getCurp()%></td>
				<td align="center"><%=sep.getGrado()%></td>
				<td align="center"><%=sep.getNacimiento()%></td>
				<td align="center"><%=sep.getNuevo()%></td>
				<td align="center"><%=sep.getAntecedente()%></td>
				<td align="center"><%=sep.getResidencia()%></td>
				<td align="center">NO</td>
				<td align="center"></td>
				<td align="center">NO</td>
			</tr>
			<%
				row++;
			}
			%>
		</table>
	</div>
</body>