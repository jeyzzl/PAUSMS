<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String matricula = request.getParameter("matricula");
%>
<head>
	<script type="text/javascript">
		var numIntentos = 0;
		function cargaDatos(){
			var url = "muestraCalculoAccion?Accion=5"+
				"&matricula=<%=matricula %>";
				
			new Ajax.Request(url, {
				method: 'get',
				onSuccess: function (req){
					$("pagar").innerHTML = req.responseText;
				},
				onFailure: function (req){
					numIntentos++;
					if(numIntentos < 3){
						numIntentos++;
						cargaDatos();
					}else
						alert("Ocurrió un error al solicitar la cantidad a pagar. Inténtelo de nuevo");
				}
			});
		}
	</script>
</head>
<body onload="cargaDatos();">
	<table style="margin: 0 auto;">
		<tr>
			<td class="titulo">C&aacute;lculo Estimado de Cobro</td>
		</tr>
	</table>
	<br>
	<br>
	<table style="margin: 0 auto;">
		<tr>
			<td>
				<iframe src="https://virtual-um.um.edu.mx/financiero/festudiantiles/cc_estimado/calculaCobro.jsp?txtMatricula=<%=matricula %>&sltFormaPago=C" style="width: 800px; height: 300px;" scrolling="auto"></iframe>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center">
				<form id="pagar" action="https://secure.um.edu.mx/info" method="post" target="_blank">
					<img src="../../imagenes/cargando.gif" />
				</form>
			</td>
		</tr>
	</table>
</body>