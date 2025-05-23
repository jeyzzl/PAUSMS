<%@ include file="../../idioma.jsp"%>

<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="aca.conecta.*" %>
<%@ page import="aca.vmeeting.Grupo" %>
<%@ page import="aca.vmeeting.GrupoUtil" %>
<jsp:useBean id="grupo" scope="page" class="aca.vmeeting.Grupo"/>
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
	function borrar(grupoId){
		if(confirm("Está seguro que desa borrar este grupo?")){
			$.get("listaAccion", {accion: "3", id: grupoId}, function (data){
				data = eval("("+data+")");
				if(data){
					document.location.reload();
				}else{
					alert("Ocurrió un error al borrar.\nPor favor inténtelo nuevamente");
				}
			});
		}
	}
	
	function editar(grupoId){
		document.location = 'editar.jsp?accion=4&id='+grupoId;
	}
</script>
</head>
<body>
	<table style="width:100%">
	<tr><td align="center"><h2>Lista de Grupos de sistemas iguales</h2></td></tr>
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
		<th>Acci&oacute;n</th>
	</tr>
<%
	ArrayList<Grupo> listGrupos = GrupoUtil.getListAll(conVM, "ORDER BY NOMBRE");
	for(int i = 0; i < listGrupos.size(); i++){
		grupo = (Grupo) listGrupos.get(i);
%>
	<tr>
		<td><%=grupo.getId() %></td>
		<td><%=grupo.getNombre() %></td>
		<td>
			<img src="../../imagenes/borrar3.png" width="20px" onclick="borrar('<%=grupo.getId() %>');" class="button" />&nbsp;&nbsp;
			<img src="../../imagenes/editar.gif" width="20px" onclick="editar('<%=grupo.getId() %>');" class="button" />
		</td>
	</tr>
<%
	}
%>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td colspan="3" align="center"><b>Fin de Listado</b></td>
	</tr>
	</table>	
</body>
</html>
<%
	c.desPostgres(conVM);
	conVM = null;
%>