<%@page import="java.util.*" %>
<%@page import="aca.opcion.spring.Opcion"%>
<%@page import="aca.opcion.spring.MaestroOpcion"%>
<%@page import="aca.mensaje.spring.Mensaje"%>
<%@page import="aca.acceso.spring.Acceso"%>

<% String idJsp="000"; %>
<%@ include file= "idioma.jsp" %>
<%
	String codigoPersonal		= session.getAttribute("codigoPersonal")==null?"0":(String)session.getAttribute("codigoPersonal");	
	int accion					= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
	String msj      			= request.getParameter("msj")==null?"":request.getParameter("msj");
	
	Mensaje mensaje 			= (Mensaje) request.getAttribute("mensaje");
	Opcion opcion 				= (Opcion) request.getAttribute("opcion");
	MaestroOpcion maestroOpcion = (MaestroOpcion) request.getAttribute("maestroOpcion");
	Acceso acceso 				= (Acceso) request.getAttribute("acceso");	
	
	String respuesta			= "";
	int error					= 0;	
	
	String primeraVez		= request.getParameter("Primera")==null?"0":request.getParameter("Primera");
%>
<html>
<head>
	<title>Academic System</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap4/css/bootstrap.min.css">
	<script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap4/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/fontawesome.min.css">
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/solid.min.css">
	<style>
    	.table-responsive{
    		margin: 10px;
    	}
    	#container{
			margin: 7px 7px 0 7px;
		}
    </style>
</head>
<body>
<div id="container">	
	<h3>Configuration<small class="text-muted">(<%=respuesta.equals("")?"<font size=2><b>&nbsp;</b></font>":respuesta%>)</small></h3>
	<div class="alert alert-info">
		<a class="btn btn-outline-primary" data-toggle="collapse" href="#collapseClave" role="button" aria-expanded="false" aria-controls="collapseClave">
    		Password
  		</a>
  		<a class="btn btn-outline-primary" data-toggle="collapse" href="#collapseSeguridad" role="button" aria-expanded="false" aria-controls="collapseSeguridad">
    		Security
  		</a>
  		<a class="btn btn-outline-primary" data-toggle="collapse" href="#collapseLenguaje" role="button" aria-expanded="false" aria-controls="#collapseLenguaje">
    		Language
  		</a>
  		<a class="btn btn-outline-primary" data-toggle="collapse" href="#collapseColor" role="button" aria-expanded="false" aria-controls="#collapseColor">
    		Color
  		</a>  		
	</div>	
	<div class="collapse" id="collapseClave">
  		<div class="card card-body">
    		<a href="contenido?accion=1&Primera=1"><h4>Change Password</h4></a>
    		<font color="gray" size="4">Change your current login password</font>
  		</div>
	</div>  	
	<div class="collapse" id="collapseSeguridad">
		<form id="forma" name="forma" action="grabarAcceso" method="post">
  		<div class="card card-body">
  			<h4>Security</h4>
  			<h6>This option allows your to either show or hide the multiple session message with your account.</h6>  			
			<table style="width:100%">
			<tr>
				<td><input type="radio" name="alertaSesion" value="S" <%=opcion.getAlertaSesion().equals("S")?"Checked":"" %> /><font size="4"> Show</font></td>				
			</tr>
			<tr>
				<td>
					<small class="text-muted">
						I don't share my session with anyone and I want the system to warn me if someone else is using my session.

					</small>
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>	
			<tr>				
				<td><input type="radio" name="alertaSesion" value="N" <%=opcion.getAlertaSesion().equals("N")?"Checked":"" %> /><font size="4"> Hide</font></td>
			</tr>
			<tr>				
				<td>
					<small class="text-muted">
						I share my session with someone and 
						I don't want the duplicate session 
						message displayed.
					</small>
				</td>
			</tr>				
			</table>
			&nbsp;&nbsp;
			<input type="submit" value="Save Changes" style="width:200px" class="btn btn-primary"/>
  		</div>
  		</form>
	</div>
	<div class="collapse" id="collapseLenguaje">
  		<div class="card card-body">
  			<font size="5">Change Language</font>
  			<br>
    		<form id="frmIdioma" name="frmIdioma" method="post" action="grabarIdioma">
				<input type="hidden" name="Accion" />								
				<select name="IdiomaUsuario" id="IdiomaUsuario">
					<option value="es" <%if(acceso.getIdioma().equals("es")){out.print("selected");} %>><spring:message code="aca.Espanol" /></option>		    					    	
					<option value="en" <%if(acceso.getIdioma().equals("en")){out.print("selected");} %>><spring:message code="aca.Ingles" /></option>					
				</select>			
				<br><br>
				<a class="btn btn-primary" href="javascript:GuardarIdioma();" ><i class="icon-ok icon-white"></i> <spring:message code="aca.Guardar" /></a>
			</form>		
  		</div>
	</div>
	<div class="collapse" id="collapseColor">
  		<div class="card card-body">
			<a href="cambiaColor?"><h4>Change Color</h4></a>
			<font color="gray" size="4">Change the menu bar color.</font>	
  		</div>
	</div>  
</div>	
</body>
<script>
	
	function GuardarIdioma(){
		document.frmIdioma.Accion.value = "3";
		document.frmIdioma.submit();
	}
	
	$(function(){
		$("#collapseClave").collapse('toggle');
	});	
	
</script>