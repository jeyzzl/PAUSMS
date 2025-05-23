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
 	 		background-color: #DFDFDC;
    		color: white;   		
		}
		
		.titulo2 {
 	 		background-color: <%=currentColor%>; 	 		
    		color: black;
    		padding: 8px 16px;
		}
		
		li:last-child {
    		border-bottom: none;
		}
	</style>	
</head>
	
<%
	ArrayList<ModuloOpcion> lisOpcion	= (ArrayList<ModuloOpcion>)session.getAttribute("lisOpcion");

	String menuId					= request.getParameter("menu")==null?"0":request.getParameter("menu");
	String moduloId					= request.getParameter("modulo")==null?"0":request.getParameter("modulo");
	
	String moduloNombre 			= (String) request.getAttribute("moduloNombre");
	
	HashMap<String, aca.menu.spring.Modulo> mapaModulos = 	 (HashMap<String, aca.menu.spring.Modulo>) request.getAttribute("mapaModulos");
%>
<div class="container-fluid">
	
	<ul>
		<li class="titulo1"><a href="menuDos?menu=<%=menuId%>"><i class="fa fa-arrow-circle-left fa-1x"></i>&nbsp;Modulo <small>( <%=moduloNombre%> )</small></a></li>		
		<li class="titulo2">Opciones</li>	
<%	
	for (int k = 0; k<lisOpcion.size(); k++){
		ModuloOpcion opcion = (ModuloOpcion) lisOpcion.get(k);
		if (opcion.getModuloId().equals((moduloId))){
			
			aca.menu.spring.Modulo modulo = new aca.menu.spring.Modulo(); 
			if (mapaModulos.containsKey(opcion.getModuloId()) ){
				modulo = mapaModulos.get(opcion.getModuloId());
			}
%>
		<li>
			<a href="<%=modulo.getUrl() %><%=opcion.getUrl()%>?moduloId=<%=modulo.getModuloId() %>&carpeta=<%=modulo.getUrl() %>" target="secondFrame"><%=opcion.getNombreOpcion() %></a>
		</li>			
<%
		}
	}	
%>	
	</ul>	
</div>