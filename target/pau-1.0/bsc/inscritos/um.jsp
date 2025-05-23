<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
	<head></head>
	<body>
		<br>
		<table style="width:50%; margin:0 auto;">
			<tr><td style="text-align:center" colspan="2"><font size="5">Histórico de inscritos</font></td></tr>
		</table>
		<br>
		<table style="width:60%; margin:0 auto;" class="table-bordered">
			<tr>
				<td align="center"><img onclick="document.location.href='umEst';" src="estInscritosUm.png" width="200" class="button" title="Inscritos UM"></td>
				<td align="center"><img onclick="document.location.href='detalles';" src="estInscritosCarga.png" width="200" class="button" title="Inscritos por cargas"></td>
				<td align="center"><img onclick="document.location.href='facultad';" src="estInscritosCarrera.png" width="200" class="button" title="Inscritos por carreras"></td>
			</tr>
			<tr>
				<td align="center"><font size="3"><b>Inscritos UM</b></font></td>
				<td align="center"><font size="3"><b>Inscritos por cargas</b></font></td>
				<td align="center"><font size="3"><b>Inscritos por carrera</b></font></td>
			</tr>
		</table>
	</body>
</html>