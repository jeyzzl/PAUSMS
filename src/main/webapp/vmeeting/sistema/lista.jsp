<%@ include file="../../idioma.jsp"%>

<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="aca.conecta.*" %>
<%@ page import="aca.vmeeting.Sistema" %>
<%@ page import="aca.vmeeting.SistemaUtil" %>

<jsp:useBean id="sistema" scope="page" class="aca.vmeeting.Sistema"/>
<%
	Connection  conVM	 	= null;
	Conectar c 				= new Conectar();
	conVM	 				= c.conPostgresVMeeting();
%>
<html>
<head>
<link href="../main.css" rel="STYLESHEET" type="text/css">
<script type="text/javascript" src="../../js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
	function borrar(sistemaId){
		if(confirm("Está seguro que desa borrar este sistema?")){
			$.get("listaAccion", {accion: "3", id: sistemaId}, function (data){
				data = eval("("+data+")");
				if(data){
					document.location.reload();
				}else{
					alert("Ocurrió un error al borrar.\nPor favor inténtelo nuevamente");
				}
			});
		}
	}
	
	function editar(sistemaId){
		document.location = 'editar.jsp?accion=4&id='+sistemaId;
	}
</script>
</head>
<body>
<table style="width:100%">
	<tr><td align="center"><h2>Lista de Sistemas que pueden participar en conferencias</h2></td></tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td>
		<input type="button" value="Nuevo" onclick="location.href='nuevo.jsp';" />				
		</td>
	</tr>
	</table>
	<table style="width:100%" class="lista">
	<tr>
		<th>ID</th>
		<th><spring:message code="aca.Nombre"/></th>
		<th><spring:message code="aca.Tipo"/></th>
		<th>Acci&oacute;n</th>
	</tr>
<%
	ArrayList<Sistema> listSistemas = SistemaUtil.getListAll(conVM, "ORDER BY NOMBRE");
	for(int i = 0; i < listSistemas.size(); i++){
		sistema = (Sistema) listSistemas.get(i);
%>
	<tr>
		<td><%=sistema.getId() %></td>
		<td><%=sistema.getNombre() %></td>
		<td><%=sistema.isLocal()?"Interno":"Externo" %></td>
		<td>
			<img src="../../imagenes/borrar3.png" width="20px" onclick="borrar('<%=sistema.getId() %>');" class="button" />&nbsp;&nbsp;
			<img src="../../imagenes/editar.gif" width="20px" onclick="editar('<%=sistema.getId() %>');" class="button" />
		</td>
	</tr>
<%
	}
%>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td colspan="4" align="center"><b>Fin de Listado</b></td>
	</tr>
	</table>	
</body>
</html>
<%
	c.desPostgres(conVM);
	conVM = null;
%>