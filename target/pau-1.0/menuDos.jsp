<%@ page import= "java.util.*"%>
<%@ page import= "aca.menu.spring.*"%>
<%
	String currentColor 	= session.getAttribute("colorTablas").equals("")?"default":(String)session.getAttribute("colorTablas");
	if (currentColor.equals("default")){
		currentColor = "url(imagenes/bg-header.png)";
	}
%>	
<head>
	<link rel="stylesheet" href="bootstrap4/css/bootstrap.min.css" type="text/css" media="screen" />
<!-- 	<link rel="stylesheet" href="fontawesome/css/font-awesome.min.css"> -->
	<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.css" type='text/css'>
	<script src='bootstrap4/js/bootstrap.min.js' type='text/javascript'></script>
	<style>
		#content{
			/*margin: 10px 10px 0 10px;*/
		}
		
		ul {
    		list-style-type: none;
    		margin: 0;
    		padding: 0;
    		width: 240px;
    		background-color: #DFDFDC;
    		/*border: 1px solid black;*/
    		height:100%;
    		position: fixed; /* Make it stick, even on scroll */
    		overflow: auto; /* Enable scrolling if the sidenav has too much content */
    		z-index:-1;
		}
		
		li a {
    		display: block;
    		color: #000;
	    	padding: 4px 16px;
    		text-decoration: none;
    		text-align: left;
		}
		
		/* Change the link color on hover */
		li a:hover {
    		background-color: #A4A4A1;
    		color: white;
    		text-decoration: none;
    		text-align: left;
    		border-bottom: 1px solid #555;
		}
		
		.titulo1 {
 	 		background: #DFDFDC;
    		color: white;   		
		}
		
		.titulo2 {
 	 		background: <%=currentColor%>; 	 		
    		color: white;
    		padding: 8px 16px;
		}
		
		li:last-child {
    		border-bottom: none;
		}
		
	</style>	
</head>
	
<%	
	ArrayList<Modulo> lisModulo		= (ArrayList<Modulo>)session.getAttribute("lisModulo");
	String menuId					= request.getParameter("menu")==null?"0":request.getParameter("menu");
	String menuNombre 				= (String) request.getAttribute("menuNombre");
%>
<div class="container-fluid">
	<ul>	
		<li class="titulo1"><a href="menuUno"><i class="fa fa-arrow-circle-left fa-1x"></i>&nbsp;&nbsp;Menu <small>( <%=menuNombre%> )</small></a>
		<li class="titulo2">Modulos</li>	
<%	
	for (int k = 0; k<lisModulo.size(); k++){
		Modulo modulo = (Modulo) lisModulo.get(k);
		if (modulo.getMenuId().equals(menuId)){
%>		
		<li><a href="menuTres?menu=<%=menuId%>&modulo=<%=modulo.getModuloId()%>" id="menu" target="secondFrame"><%=modulo.getNombreModulo()%></a></li>			
<%
		}
	}	
%>	
	</ul>	
</div>