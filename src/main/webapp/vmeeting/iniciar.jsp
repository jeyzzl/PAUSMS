<%@ include file="../con_enoc.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="aca.conecta.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.io.*" %>
<%@page import="aca.alumno.AlumAcademico"%>
<%@page import="aca.vmeeting.Asistente"%>

<jsp:useBean id="meeting" scope="page" class="aca.vmeeting.Meeting"/>
<jsp:useBean id="usuarioU" scope="page" class="aca.vista.UsuariosUtil"/>
<html>
<head>
<link href="../main.css" rel="STYLESHEET" type="text/css">
<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
<%
	Connection  conVM	 	= null;
	Conectar con			= new Conectar();
	conVM	 				= con.conPostgresVMeeting();
	
	meeting.mapeaRegId(conVM, Integer.parseInt(request.getParameter("id")));
	String owner = (String)session.getAttribute("__vmOwner");
	String codigoPersonal = (String)session.getAttribute("codigoPersonal");
	int sistemaId = Integer.parseInt((String)session.getAttribute("__vmSistemaId"));

	String bbbUrl = "http://bbb.um.edu.mx/bigbluebutton/";
	
	/// Se debe crear la conferencia
	String createParameters = "name="+URLEncoder.encode(meeting.getNombre(), "UTF-8")+
							  "&meetingID="+URLEncoder.encode(meeting.getBbbMeetingId(), "UTF-8")+
							  "&attendeePW="+URLEncoder.encode(meeting.getBbbAttendeePw(), "UTF-8")+
							  "&moderatorPW="+URLEncoder.encode(meeting.getBbbModeratorPw(), "UTF-8")+
							  "&welcome="+URLEncoder.encode("Bienvenido a "+meeting.getNombre(), "UTF-8")+
							  "&voiceBridge="+URLEncoder.encode(String.valueOf(meeting.getBbbVoiceBridge()), "UTF-8");
	
	String claveDigest = org.apache.commons.codec.digest.DigestUtils.shaHex("create"+createParameters+"9d857467726e2a49fd2ff78272e68d89");
    
    String url = bbbUrl + "api/create?" + createParameters + "&checksum="+claveDigest;
    //System.out.println(url);
    
    
    URL liga = new URL(url);
    URLConnection yc = liga.openConnection();
    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                            yc.getInputStream()));
    String inputLine, respuesta = "";

    while ((inputLine = in.readLine()) != null) 
        respuesta += inputLine;
    in.close();
    
    //System.out.println(respuesta);
    
    //<response><returncode>SUCCESS</returncode>
    //<meetingID>Prueba001XYepXP44</meetingID>
    //<attendeePW>XYepXP44</attendeePW>
    //<moderatorPW>HAGCqycA</moderatorPW>
    //<createTime>1345155201198</createTime>
    //<hasBeenForciblyEnded>false</hasBeenForciblyEnded>
    //<messageKey></messageKey>
    //<message></message></response>
    
    if(respuesta.indexOf("SUCCESS") > -1){//Conferencia creada
		if(meeting.getIdentificadorOwner().equals(owner)){ // Si es el owner se entra como manager
			/// Se ingresa a la conferencia
	    	createParameters = "fullName="+URLEncoder.encode(aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, owner, "NOMBRE"), "UTF-8")+
								  "&meetingID="+URLEncoder.encode(meeting.getBbbMeetingId(), "UTF-8")+
								  "&password="+URLEncoder.encode(meeting.getBbbModeratorPw(), "UTF-8");
		
			
		}else{ // Si no es el owner se busca que tipo de usuario es
			/// Se ingresa a la conferencia
			//System.out.println(codigoPersonal);
			//System.out.println(usuarioU.getNombreUsuario(conEnoc, codigoPersonal, "NOMBRE"));
	    	createParameters = "fullName="+URLEncoder.encode(aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, codigoPersonal, "NOMBRE"), "UTF-8")+
								  "&meetingID="+URLEncoder.encode(meeting.getBbbMeetingId(), "UTF-8");
			if(Asistente.isModerator(conVM, owner, sistemaId, meeting.getId())){
				createParameters +="&password="+URLEncoder.encode(meeting.getBbbModeratorPw(), "UTF-8");
			}else{
				createParameters +="&password="+URLEncoder.encode(meeting.getBbbAttendeePw(), "UTF-8");
			}
		}
		claveDigest = org.apache.commons.codec.digest.DigestUtils.shaHex("join"+createParameters+"9d857467726e2a49fd2ff78272e68d89");
	    
	    url = bbbUrl + "api/join?" + createParameters + "&checksum="+claveDigest;
	    //System.out.println(url);
%>
		document.location = '<%=url %>';
<%
    }
%>
</script>
</head>
<body>
</body>
</html>
<%
	con.desPostgres(conVM);
%>
<%@ include file="../cierra_enoc.jsp"%>