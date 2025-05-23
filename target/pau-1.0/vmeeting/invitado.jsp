<%@ include file= "../../con_enoc.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="aca.conecta.*" %>
<%@ page import="aca.vmeeting.Asistente" %>
<%@ page import="aca.vmeeting.AsistenteUtil" %>
<%@ page import="java.net.*" %>
<%@ page import="java.util.Date" %>

<jsp:useBean id="meeting" scope="page" class="aca.vmeeting.Meeting"/>
<jsp:useBean id="asistente" scope="page" class="aca.vmeeting.Asistente"/>
<%
	Connection  conVM	 	= null;
	Conectar con			= new Conectar();
	conVM	 				= con.conPostgresVMeeting();

	String meetingId = request.getParameter("meeting");
	
	meeting.mapeaRegBbbMeetingId(conVM, meetingId);
			
%>
<html>
<head>
<link href="../main.css" rel="STYLESHEET" type="text/css">
<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
	function revisaCampo(){
		var revision = false;
		
		if($("#nombre").val().length >= 3){
			revision = true;
		}else{
			alert("El Nombre debe ser de al menos 3 caracteres");
		}
		return revision;
	}
</script>
</head>
<body>
<table style="width:100%; margin:0 auto">
	<tr>
		<td align="center"><h2>Bienvenido a la Reuni&oacute;n: <%=meeting.getNombre() %></h2></td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td align="center">
<%
	Date fInicio = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").parse(meeting.getFInicio());
	Date fFinal = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").parse(meeting.getFFinal());
	Date now = new Date();
	if(fInicio.before(now) && fFinal.after(now)){
%>
			<form name="forma" action="iniciarInvitado.jsp" method="POST" onsubmit="return revisaCampo();">
				<input type="hidden" name="id" value="<%=meeting.getId() %>" />
				<input type="hidden" name="bbbMeetingId" value="<%=meeting.getBbbMeetingId() %>" />
				<table>
					<tr>
						<td>Ingrese su nombre:</td>
						<td><input type="text" id="nombre" name="nombre" maxlength="95" value="" /></td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="submit" value="Iniciar" id="next" />
						</td>
					</tr>
				</table>
			</form>
<%
	}else if(fInicio.after(now)){
%>
			<h3>La reuni&oacute;n esta agendada para mas adelante. Por favor consulte la fecha con quien le invit&oacute;</h3>
<%
	}else if(fFinal.before(now)){
%>
			<h3>Lo setimos, pero la reuni&oacute;n ya ha pasado</h3>
<%
	}
%>
		</td>
	</tr>
</table>
</body>
</html>
<%
	con.desPostgres(conVM);
	conVM = null;
%>
<%@ include file= "../../cierra_enoc2.jsf" %>