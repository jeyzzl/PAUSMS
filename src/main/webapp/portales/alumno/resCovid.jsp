<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.notifica.spring.NotiCovid"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<html>
<head>&nbsp;</head>	
<% 	
	NotiCovid notiCovid		= (NotiCovid)request.getAttribute("notiCovid");
	AlumPersonal alumno		= (AlumPersonal)request.getAttribute("alumno");
	String mensaje			= "debe acudir al área del triage del Hospital la Carlota y seguir sus indicaciones.";
	String mensaje2			= "Alto";
	String color			= "style='color:red; padding-top: 20px'";
%>
<body>
<div class="container" style = "display:flex; flex-direction:column">
<%if(notiCovid.getRespuesta().equals("SI")){
	mensaje 	 = "puede ingresar al plantel después de tomarse la temperatura.";
	mensaje2 	 = "Pase";
	color = "style='color:green; padding-top: 20px'";
	}%>
	<div style = "display:flex; flex-direction:row">
		<div style = "flex-basis: 15%">
			<img class="rounded border border-dark" src="../../foto?Codigo=<%=alumno.getCodigoPersonal()%>&Tipo=O" width="150" lenght="300">
		</div>
		<div style = "text-align:center; flex-basis:80%">
			<div>
				<h3>Universidad de Montemorelos<br>
				Filtro sanitario para el ingreso al plantel<br>
<%
	String[] parts = notiCovid.getFecha().split("-");
	String[] dh = parts[2].split(" ");
	if(parts[1].equals("01")){
		parts[1] = "Ene";
	}else if(parts[1].equals("02")){
		parts[1] = "Feb";
	}else if(parts[1].equals("03")){
		parts[1] = "Mar";
	}else if(parts[1].equals("04")){
		parts[1] = "Abr";
	}else if(parts[1].equals("05")){
		parts[1] = "May";
	}else if(parts[1].equals("06")){
		parts[1] = "Jun";
	}else if(parts[1].equals("07")){
		parts[1] = "Jul";
	}else if(parts[1].equals("08")){
		parts[1] = "Ago";
	}else if(parts[1].equals("09")){
		parts[1] = "Sept";
	}else if(parts[1].equals("10")){
		parts[1] = "Oct";
	}else if(parts[1].equals("11")){
		parts[1] = "Nov";
	}else if(parts[1].equals("12")){
		parts[1] = "Dic";
	}
%>
				<%=dh[0]%>/<%=parts[1]%>/<%=parts[0]%>&nbsp;&nbsp;&nbsp;<%=dh[1] %>
				</h3>
			</div>
			<div <%=color%>><h1><%=mensaje2%></h1></div>
		</div>
	</div>
	<div style = "text-align:center; padding-top: 25px; font-size: 16px ">
		<%=alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()%> [<%=alumno.getCodigoPersonal()%>]  <%=mensaje%>
	</div>
</div>
</body>
</html>