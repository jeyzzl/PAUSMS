<%@ include file="../../idioma.jsp"%>

<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date" %>
<%@ page import="aca.conecta.*" %>
<%@ page import="aca.vmeeting.Meeting" %>
<%@ page import="aca.vmeeting.MeetingUtil" %>

<jsp:useBean id="meeting" scope="page" class="aca.vmeeting.Meeting"/>
<%
	Connection  conVM	 	= null;
	Conectar c 				= new Conectar();
	conVM	 				= c.conPostgresVMeeting();
	
	String owner 			= request.getParameter("owner");
	String sistemaId 		= request.getParameter("sistemaId");
	
	if(owner != null){
		session.setAttribute("__vmSistemaId", sistemaId);
		session.setAttribute("__vmOwner", owner);
	}else{
		owner = (String)session.getAttribute("__vmOwner");
		sistemaId = (String)session.getAttribute("__vmSistemaId");
	}
%>
<html>
<head>
<link href="main.css" rel="STYLESHEET" type="text/css">
<script type="text/javascript" src="../../js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
	/*function borrar(sistemaId){
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
	}*/
	
	function editar(meetingId){
		document.location = 'meeting/editar.jsp?accion=4&id='+meetingId;
	}
</script>
</head>
<body>
<table style="width:100%">
	<tr><td align="center"><h2>Reuniones Virtuales</h2></td></tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td>
			<table>
				<tr>
					<td><input type="button" value="Crear Reuni&oacute;n" onclick="location.href='meeting/nuevo.jsp';" /></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr><td><h2>Tus reuniones</h2></td></tr>
	<tr>
		<td align="center">
			<table style="width:100%" class="lista">
				<tr>
					<th>ID</th>
					<th><spring:message code="aca.Nombre"/></th>
					<th>Inicia</th>
					<th>Termina</th>
					<th>Grabado?</th>
					<th>Acci&oacute;n</th>
				</tr>
<%
	ArrayList<Meeting> listMeetings = MeetingUtil.getListOwner(conVM, sistemaId, owner, "ORDER BY F_INICIO");
	for(int i = 0; i < listMeetings.size(); i++){
		meeting = (Meeting) listMeetings.get(i);
%>
				<tr>
					<td><%=meeting.getId() %></td>
					<td><%=meeting.getNombre() %></td>
					<td><%=meeting.getFInicio() %></td>
					<td><%=meeting.getFFinal() %></td>
					<td><%=meeting.isBbbRecord()?"Si":"No" %></td>
					<td>
						<!-- img src="../imagenes/borrar3.png" width="20px" onclick="borrar('<%=meeting.getId() %>');" class="button" />&nbsp;&nbsp; -->
						<img src="../imagenes/editar.gif" width="20px" onclick="editar('<%=meeting.getId() %>');" class="button" title="Editar reuni&oacute;n" />&nbsp;&nbsp;
<%
		Date fInicio = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").parse(meeting.getFInicio());
		Date fFinal = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").parse(meeting.getFFinal());
		Date now = new Date();
		
		if(fInicio.before(now) && fFinal.after(now)){
%>
						<img src="../imagenes/flechita.png" width="20px" onclick="window.open('iniciar.jsp?id=<%=meeting.getId() %>');" class="button" title="Iniciar reuni&oacute;n" />
<%		} %>
					</td>
				</tr>
<%	} %>
				<tr><td colspan="6" style="border-top: solid 1px grey;">&nbsp;</td></tr>
				<tr>
					<td colspan="6" align="center"><b>Fin de Listado</b></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr><td><h2>Reuniones a las que has sido invitado</h2></td></tr>
	<tr>
		<td align="center">
			<table style="width:100%" class="lista">
				<tr>
					<th>ID</th>
					<th><spring:message code="aca.Nombre"/></th>
					<th>Inicia</th>
					<th>Termina</th>
					<th>Grabado?</th>
					<th>Acci&oacute;n</th>
				</tr>
<%
	listMeetings = MeetingUtil.getListInvitado(conVM, sistemaId, owner, "ORDER BY F_INICIO");
	for(int i = 0; i < listMeetings.size(); i++){
		meeting = (Meeting) listMeetings.get(i);
		
		Date fInicio = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").parse(meeting.getFInicio());
		Date fFinal = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").parse(meeting.getFFinal());
		Date now = new Date();
%>
				<tr>
					<td><%=meeting.getId() %></td>
					<td><%=meeting.getNombre() %></td>
					<td><%=meeting.getFInicio() %></td>
					<td><%=meeting.getFFinal() %></td>
					<td><%=meeting.isBbbRecord()?"Si":"No" %></td>
					<td>
<%
		if(fInicio.before(now) && fFinal.after(now)){
%>
						<img src="../imagenes/flechita.png" width="20px" onclick="window.open('iniciar.jsp?id=<%=meeting.getId() %>');" class="button" title="Iniciar reuni&oacute;n" />
<%
		}
%>
					</td>
				</tr>
<%
	}
%>
				<tr><td colspan="6" style="border-top: solid 1px grey;">&nbsp;</td></tr>
				<tr>
					<td colspan="6" align="center"><b>Fin de Listado</b></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>
<%
	c.desPostgres(conVM);
	conVM = null;
%>