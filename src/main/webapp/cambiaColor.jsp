
<%@ page import= "aca.acceso.*"%>
<%@ page import= "aca.util.*"%>
<%@ page import="aca.archivos.ArchivoVO"%>
<%@ include file= "idioma.jsp" %>

<html>
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/fontawesome.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/solid.min.css">			
	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap5.1/css/bootstrap.min.css">
	<script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap5.1/js/bootstrap.min.js"></script>
	
	<style>
	
		#content{
			margin: 10px 10px 0 10px;
		}
			
		.color{
			width:48px;
			height: 35px;
			float:left;
			margin-right: 8px;
			cursor:pointer;
		}
		a{ 
			text-decoration: none;
			color:#00036e;
		}
				
		.cian{background: #26A69A ;margin-left:0px;}
		.gris{background: #4D5656;}
		.azul{background: #34495E}
		.morado{background: #6C3483}
		.verde{background: #1D8348}
		.rosa{background: #E91E63}
		.amarillo{background: #d5ac0d}
		.celeste{background: #3E8CD5}
		.guindo{background: #A32521}	
		.active{border: 2px solid black;}
	</style>	
</head>
<%
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal");
	String currentColor 	= session.getAttribute("colorTablas").equals("")?"#4A76F2":(String)session.getAttribute("colorTablas");
	String accion 			= (String)request.getAttribute("Accion");
	String nombreUsuario	= (String)request.getAttribute("nombreUsuario");
	if (accion.equals("1")){%>
		<script type="text/javascript">top.location.href = "inicio";</script>
	<%}
%>
<body>
<div class="container-fluid">
	<h2>Change the color<small class="text-muted">( <%=codigoPersonal%> - <%=nombreUsuario%>)</small></h2>
	<div class="alert alert-info">
  		<a href="configuracion" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
  	</div>
	<table class="table table-condensed">
	<tr>
		<th colspan="9">Options</th>				
	</tr>
	<tr>
		<td class="text-center color cian">
			<a href="grabarColor?Color=26A69A">
			<div data-color="26A69A" class="color cian <%if(currentColor.equals("#26A69A"))out.print("active");%>" style="text-align:center;"></div>
			</a>
		</td>
		<td class="color gris">
			<a href="grabarColor?Color=4D5656">
			<div data-color="4D5656" class="color gris <%if(currentColor.equals("#4D5656"))out.print("active");%>" style="text-align:center;"></div>
			</a>
		</td>
		<td class="color azul">
			<a href="grabarColor?Color=34495E">
			<div data-color="34495E" class="color azul <%if(currentColor.equals("#34495E"))out.print("active");%>" style="text-align:center;"></div>
			</a>
		</td>
		<td class="color morado">
			<a href="grabarColor?Color=6C3483">
			<div data-color="6C3483" class="color morado <%if(currentColor.equals("#6C3483"))out.print("active");%>" style="text-align:center;"></div>
			</a>
		</td>
		<td class="color verde">
			<a href="grabarColor?Color=1D8348">
			<div data-color="1D8348" class="color verde <%if(currentColor.equals("#1D8348"))out.print("active");%>" style="text-align:center;"></div>
			</a>
		</td>
		<td class="color rosa">
			<a href="grabarColor?Color=E91E63">
			<div data-color="E91E63" class="color rosa <%if(currentColor.equals("#E91E63"))out.print("active");%>" style="text-align:center;"></div>
			</a>
		</td>
		<td class="color amarillo">
			<a href="grabarColor?Color=d5ac0d">
			<div data-color="d5ac0d" class="color amarillo <%if(currentColor.equals("#d5ac0d"))out.print("active");%>" style="text-align:center;"></div>
			</a>
		</td>
		<td class="color celeste">
			<a href="grabarColor?Color=3E8CD5">
			<div data-color="3E8CD5" class="color celeste <%if(currentColor.equals("#3E8CD5"))out.print("active");%>" style="text-align:center;"></div>
			</a>
		</td>
		<td class="color guindo">
			<a href="grabarColor?Color=A32521">
			<div data-color="A32521" class="color guindo <%if(currentColor.equals("#A32521"))out.print("active");%>" style="text-align:center;"></div>
			</a>
		</td>	
	</tr>		
	</table>
</div>
</body>
</html>