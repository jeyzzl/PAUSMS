<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.ResRazones"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<html>
	<head>
		<script type="text/javascript">
			function Borrar(razon) {
				if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
					document.location = "borrarRazon?Razon=" +razon;
				}
			}
		</script>
	</head>
	<%
		List<ResRazones> lisRazones 				= (List<ResRazones>) request.getAttribute("lisRazones");
		HashMap<String,String> mapaRazonesUsadas	= (HashMap<String,String>) request.getAttribute("mapaRazonesUsadas");
	%>
	<body>
<div class="container-fluid">
<h1><spring:message code="catalogos.razones.Titulo"/></h1>
<div class="alert alert-info">
	<a class="btn btn-primary" href="editarRazon?Razon="><spring:message code="aca.Anadir"/></a>	
</div>
		<table style="width:40%">
			<tr align="center">
				<td><font size="3"></font></td>
			</tr>
			<tr align="right">
				<td></td>
			</tr>
		</table>
		<table class="table table-sm table-bordered"> 
			<tr>
				<th width="17%"><spring:message code="aca.Operacion"/></th>
				<th width="9%"><spring:message code="aca.Numero"/></th>
				<th width="68%">Reason</th>
				<th width="6%">#Reg.</th>
			</tr>
		<%	
			for (ResRazones razon : lisRazones){
				String total = "0";
				if (mapaRazonesUsadas.containsKey(String.valueOf(razon.getRazon()))){
					total 	= mapaRazonesUsadas.get(String.valueOf(razon.getRazon()));
				}
// 				System.out.println(total);
		%>
				<tr>
					<td style="text-align: left">
						<a href="editarRazon?&Razon=<%=razon.getRazon()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>
						&nbsp;
						<% if(total.equals("0")){ %>
						<a href="javascript:Borrar('<%=razon.getRazon()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash-alt"></i></a>
						<% } %>
					</td>
					<td align="left"><%=razon.getRazon()%></td>
					<td><%=razon.getDescripcion()%></td>
					<td class="text-end"><%=total.equals("0")?"<span class='badge bg-warning rounded-pill'>0</span>":"<span class='badge bg-dark rounded-pill'>"+total+"</span>"%></td>
				</tr>
		<%
			}
		%>
		</table>
		</div>
	</body>
</html>