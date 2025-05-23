<%@ page import= "aca.menu.*"%>
<%-- <%@ page errorPage="../../errorPagina.jsp"%> --%>

<jsp:useBean id="colorAlum" scope="page" class="aca.portal.Alumno"/>
<%	
	String colorTablas 		= "#396CB8";	
	String currentColor 	= session.getAttribute("colorTablas").equals("")?"default":(String)session.getAttribute("colorTablas");
	if(!currentColor.equals("default")){
		colorTablas			= currentColor;
	}	
	String colorM 			= colorAlum.modificarColor(colorTablas, 25);
	String colorM2 			= colorAlum.modificarColor(colorTablas, 105);
%>	
<!doctype html>
<html>
<head> 
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/academico.css" type="text/css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/print.css" type="text/css" media="print">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/js/popup/general.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap3/css/bootstrap.min.css">  
  <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap.min.css">  
  <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/css/bootstrap-responsive.min.css" type="text/css" media="screen" />
  <link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/fontawesome.min.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/solid.min.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/regular.min.css">
  
  <script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
  <script src="<%=request.getContextPath()%>/js/popup/popup.js" type="text/javascript"></script>
  <script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>  
  <script type="text/javascript" src="../../js/prototype-1.6.js"></script>  
  <script type="text/javascript">
	jQuery.noConflict();	
  </script>
  
  <% if(!currentColor.equals("default")){%>
  	<script>
		jQuery(document).ready(function(){
			jQuery('.btn').removeClass('btn-primary');
		});
  	</script>
  	
<% }%>
<style>  
/*
  	@font-face {
  		font-family: Baker;
  		src:  url('fonts/Baker/baker_signet_bt-webfont.woff2') format('woff2'),
        url('fonts/Baker/baker_signet_bt-webfont.woff') format('woff');
	}
	
	body {
  		font-family: Baker, Helvetica Neue, Helvetica, Arial, sans-serif;
	}
*/	
  	#clickButton{
		position: absolute;
		right: 13;
		top: 2;
		float:right;
		z-index:100000000000000000000;
	}
	
	.msj{
		
		position:absolute;
		background:white;
		border: 1px solid gray;
		padding: 15px;
		
	 	-moz-box-shadow: 0 1px 6px 0px #6E6E6E;
	  	-webkit-box-shadow: 0 1px 6px 0px #6E6E6E;
		box-shadow: 0 1px 6px 0px #6E6E6E;
		z-index:2000;
	}
	.tinytable th {
			background: <%=colorTablas%>;
			background: -webkit-gradient(linear, left top, left bottom, from(<%=colorM%>), to(<%=colorTablas%>));
			background: -webkit-linear-gradient(<%=colorM%>, <%=colorTablas%> 60%);
			background: -moz-linear-gradient(<%=colorM%>, <%=colorTablas%> 60%);
			background: -o-linear-gradient(<%=colorM%>, <%=colorTablas%> 60%);
			filter:  progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='<%=colorM%>', endColorstr='<%=colorTablas%>'); /* IE6 & IE7 */
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='<%=colorM%>', endColorstr='<%=colorTablas%>')"; /* IE8 */
			
			color:#fff;
	}
	
	.msj .arrow{
		position:absolute;
		right:-15px;
	}
	.popup{
		display:none;
		position:absolute;
		position:fixed;
		background:white;
		border: 1px solid gray;
		padding: 15px;
		
	 		-moz-box-shadow: 0 1px 6px 0px #6E6E6E;
	  		-webkit-box-shadow: 0 1px 6px 0px #6E6E6E;
		box-shadow: 0 1px 6px 0px #6E6E6E;
		z-index:2000;
		display: none;
	}
	
	.bg-popup{
		position:absolute;
		position:fixed;
		top:0;
		width:100%;
		height:100%;
		background:white;
		opacity: .7;
		z-index:1000;
		display: none;
	}
	
	#content{
		margin: 20px 20px 0 20px;
	}
	
	h2{
		margin-bottom: 10px;
	}	
	
  </style>
</head>
<body>
<% 
	String tmpColorTablas = (String)session.getAttribute("colorTablas");
	
	if(tmpColorTablas.equals("default") || tmpColorTablas.equals("")){
		tmpColorTablas = "#683EAD";
	}
	
	
	String sColor 		= "bgcolor = '#eeeeee'";
	String sColorLogo	= "bgcolor = '#FFCC99'";
	String sModulob		= request.getParameter("moduloId");
	if (sModulob==null)sModulob="";
	String noMuestres 		= request.getParameter("noMuestres");
	String sCarpetab		= request.getParameter("carpeta");
	java.util.HashMap<String,aca.menu.spring.Modulo> mapaModulos 			= (java.util.HashMap<String,aca.menu.spring.Modulo>)session.getAttribute("mapaModulos");
	java.util.HashMap<String,aca.menu.spring.ModuloOpcion> mapaOpciones 	= (java.util.HashMap<String,aca.menu.spring.ModuloOpcion>)session.getAttribute("mapaOpciones");
	
	String urlOpcion 	= "";
	String claveModulo	= "";
	String urlModulo	= "";
	if (mapaOpciones.containsKey(idJsp)){
		urlOpcion 	= mapaOpciones.get(idJsp).getUrl();
		claveModulo	= mapaOpciones.get(idJsp).getModuloId();
		if (mapaModulos.containsKey(claveModulo)){
			urlModulo = mapaModulos.get(claveModulo).getUrl();
		}
	}	
%>	
	<div id="popupContact" hidden style="border:3px coral solid; border-color:#4C88D9;">
	<!-- <div id="popupContact" style="border:3px coral solid; border-color:<%=tmpColorTablas %>;"> -->
		<img id="popupContactClose" src="../../imagenes/x.png" width="20"  style="cursor:pointer;"/>
		<p id="contactArea">
			<iframe width="100%" height="100%" src="../../js/popup/loading.html" id="popup" name="popup" marginheight="0" marginwidth="0" frameborder="0"></iframe>
		</p>
	</div>
	<div id="backgroundPopup" style="background:#FFF;"></div>
	<!-- <div id="backgroundPopup" style="background:<%=tmpColorTablas %>;"></div> -->
 	<input type="hidden" value="<%=urlOpcion+"?moduloId="+claveModulo+"&carpeta="+urlModulo%>" id="PaginaModulo">
	<input type="hidden" value="<%=idJsp%>" id="idPagina">
</body>