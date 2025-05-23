<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.emp.spring.Empleado"%>
<%@page import="aca.notifica.spring.NotiCovid"%>

<% String idJsp= "000";%>
<%@include file= "seguro2.jsf" %>
<%@include file= "idioma.jsp" %>

<html>
<head>
	<link rel="stylesheet" href="academico.css" type="text/css">
	<link rel="stylesheet" href="print.css"  type="text/css" media="print">		
	<link rel="stylesheet" href="<%=request.getContextPath()%>/js/popup/general.css" type="text/css" media="screen" />
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap3/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-responsive.min.css" type="text/css" media="screen" />
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/fontawesome.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/solid.min.css">	
	<script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>  
	<script src="<%=request.getContextPath()%>/js/popup/popup.js" type="text/javascript"></script>	
  	<script src='<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js' type='text/javascript'></script>
  	<script type="text/javascript" src="js/prototype-1.6.js"></script>
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap/datepicker/datepicker.js"></script>  	 
</head>		
<% 	
	NotiCovid notiCovid		= (NotiCovid)request.getAttribute("notiCovid");
	Empleado empleado		= (Empleado)request.getAttribute("empleado");
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
			<img class="rounded border border-dark" src="foto?Codigo=<%=empleado.getClave()%>&Tipo=O" width="150" lenght="300">
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
		<%=empleado.getNombre()+" "+empleado.getAppaterno() +" "+empleado.getApmaterno()%> [<%=empleado.getClave()%>]  <%=mensaje%>
	</div>
</div>
</body>
</html>