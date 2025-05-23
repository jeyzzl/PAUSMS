<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String matricula = (String) session.getAttribute("codigoPersonal");
%>
<body>
	<br>
	<table style="margin: 0 auto;">
		<tr>
			<td class="titulo">Pasos para la Inscripción</td>
		</tr>
	</table>
	<br>
	<table style="margin: 0 auto;" class="tabla">
		<tr>
			<td width="15px" valign="top">
				<b>1.</b>
			</td>
			<td>
				Elige las materias que vas a llevar en el siguiente<br>
				semestre dando click <a href="matricula">aqu&iacute;</a>.<br>
				Una vez elegidas las materias tienes que dar click en el<br>
				bot&oacute;n "C&aacute;lculo Estimado" al final de la p&aacute;gina.
			</td>
		</tr>
		<tr>
			<td width="15px" valign="top">
				<b>2.</b>
			</td>
			<td>
				Despu&eacute;s de generar el C&aacute;lculo Estimado de cobro<br>
				se hace el pago mediante el boton "Realizar Pago".
			</td>
		</tr>
		<tr>
			<td width="15px" valign="top">
				<b>3.</b>
			</td>
			<td>
				Una vez hecho el pago, espera un correo con la confirmaci&oacute;n<br>
				de tu inscripci&oacute;n.
			</td>
		</tr>
	</table>
</body>