<%@ page import= "java.util.*"%>
<%@ page import= "aca.menu.spring.*"%>
<%
	String currentColor 	= (String)request.getAttribute("colorUsuario");	
	if (currentColor.equals("default")){
		currentColor = "url(imagenes/bg-header.png)";
	}
%>
<head>
	<link rel="stylesheet" href="bootstrap4/css/bootstrap.min.css" type="text/css" media="screen" />
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
 	 		background: white;
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
	ArrayList<Menu> lisMenu		= (ArrayList<Menu>)session.getAttribute("lisMenu");

	String codigoPersonal 			= session.getAttribute("codigoPersonal")==null?"0":session.getAttribute("codigoPersonal").toString();
	boolean esEmpleado 				= (boolean) request.getAttribute("esEmpleado");
	boolean esMaestro 				= (boolean) request.getAttribute("esMaestro");
	boolean esMentor 				= (boolean) request.getAttribute("esMentor");
	int tieneInternado				= (int) request.getAttribute("tieneAcceso");	
	boolean esEditor				= (boolean) request.getAttribute("esEditor");
	String nombreCorto 				= (String) request.getAttribute("nombreCorto");
	
	boolean esPadre 	= false; 
	//aca.padre.PadrePersonal.esPadre(conEnoc, codigoPersonal);
	int row				= 0;
%>
<div class="container-fluid">	
	<ul class="nav flex-column">
		 <li class="titulo2">Menu</li>
<%
	//Verificar si es alumno
	if (codigoPersonal.substring(0,1).equals("0") || codigoPersonal.substring(0,1).equals("1") || codigoPersonal.substring(0,1).equals("2")){		
		//paginaInicial = "portales/portalAlumno/portal";		
		row++;
%>
    	 <li>    	 	
    	 	<a class="alert-info" href="portales/portalAlumno/portal" target="secondFrame">Portal del Alumno</a>
    	 </li>
<%
	}else if(tieneInternado>0 || esMaestro || esMentor || esEmpleado || esEditor || esPadre){
		//Verificar si es preceptor
		if (tieneInternado > 0){ 
			session.setAttribute("dormitorioId",String.valueOf(tieneInternado));
			session.setAttribute("Admin","Preceptor");
			row++;
%>
		 <li>		 			 	
		 	<a class="nav-link" href="portales/preceptor/personal" target="secondFrame">Portal del Preceptor</a>
		 </li>
<%
		}
		//Verifica si es padre, madre o tutor para poner portal del padre						
		if (codigoPersonal.substring(0,1).equals("P")){
			row++;
%>
		 <li>		 	
		 	<a class="submenu" href="portales/padre/hijos.jsp" target="secondFrame">Portal del Padre</a>
		 </li>
<%
		}
		//Verifica si es Profesor
		if (esMaestro){
			row++;
%>
		<li>			
<%-- 			<td class="text-center"><span class="badge badge-pill badge-dark"><%=row%></span></td> --%>
			<a href="portales/maestro/cursos" target="secondFrame" >Portal del Profesor</a>
		</li>
<% 		row++;	%>		
		<li>			
			<a href="portales/porDocente/portafolio" target="secondFrame" >Portafolio Docente</a>
		</li>			
<%
		}
			
		if (esEditor){
			row++;
			//out.print("<tr><td class='text-center'><span class='badge badge-pill badge-dark'>"+row+"</span></td><td><a  href='mapa/diamante/planes' target='secondFrame' >Planes Diamante</a></td></tr>");
			out.print("<li><a  href='mapa/diamante/planes' target='secondFrame' >Planes Diamante</a></li>");
		}
	
		//Verifica si es Mentor
		if (esMentor){						
				// Incluir la carga de materias para mentores.
			String opciones = (String) session.getAttribute("opciones");
			session.setAttribute("opciones", opciones+"130-");
			row++;
%>
		<li class="nav-item">			
			<a href="portales/mentor/portal" target="secondFrame" >Portal del Mentor</a>
		</li>
<%
		}				
		// Verifica si es empleado
		if (esEmpleado || esMaestro){
			row++;
%>
		<li class="nav-item">			
			<a href="portales/portafolio/datos" target="secondFrame" >Portafolio Apoyo</a>
		</li>
<%		
		}	
	}
	
	for (int k = 0; k < lisMenu.size(); k++){
		Menu menu = (Menu) lisMenu.get(k);
		row++;
%>
		<li>	
			<a href="menuDos?menu=<%=menu.getMenuId()%>" class="principal" id="menu" target="secondFrame"><%=menu.getMenuNombre()%></a>
		</li>
<%
	}	
%>	
	
	<ul>	
</div>
