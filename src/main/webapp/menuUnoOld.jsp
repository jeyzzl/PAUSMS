<%@ page import= "java.util.*"%>
<%@ page import= "aca.menu.*"%>
<head>
	<link rel="stylesheet" href="bootstrap4/css/bootstrap.min.css" type="text/css" media="screen" />
	<script src='bootstrap4/js/bootstrap.min.js' type='text/javascript'></script>
	<style>
		#content{
			margin: 10px 10px 0 10px;
		}
	</style>	
</head>
	
<%	
	ArrayList<Menu> lisMenu		= (ArrayList<Menu>)session.getAttribute("lisMenu");

	String codigoPersonal 			= (String)session.getAttribute("codigoPersonal");
	boolean esEmpleado 				= (boolean) request.getAttribute("esEmpleado");
	boolean esMaestro 				= (boolean) request.getAttribute("esMaestro");
	boolean esMentor 				= (boolean) request.getAttribute("esMentor");
	int tieneInternado				= (int) request.getAttribute("tieneAcceso");	
	boolean esEditor				= (boolean) request.getAttribute("esEditor");
	String nombreCorto 				= (String) request.getAttribute("nombreCorto");
	
	boolean esPadre 	= false;	
	int row				= 0;
%>
<div class="container-fluid">	
	<table class="table table-sm table-bordered" style="width:270px;">
	<thead>
	<tr>
		<th width="10%">&nbsp;</th>
		<th width="90%">Menu</th>
	</tr>
	</thead>
	<tbody>
<%	
	//Verificar si es alumno
	if (codigoPersonal.substring(0,1).equals("0") || codigoPersonal.substring(0,1).equals("1")){		
		//paginaInicial = "portales/portalAlumno/portal";		
		row++;
%>
    	 <tr>
    	 	<td class="text-center"><span class="badge badge-pill badge-dark"><%=row%></span></td>
    	 	<td><a class="alert-info" href="portales/portalAlumno/portal" target="secondFrame">Portal del Alumno</a></td>
    	 </tr>
<%
	}else if(tieneInternado>0 || esMaestro || esMentor || esEmpleado || esEditor || esPadre){
		//Verificar si es preceptor
		if (tieneInternado > 0){ 
			session.setAttribute("dormitorioId",String.valueOf(tieneInternado));
			session.setAttribute("Admin","Preceptor");
			row++;
%>
		 <tr>
		 	<td class="text-center"><span class="badge badge-pill badge-dark"><%=row%></span></td>		 	
		 	<td><a class="nav-link" href="portales/preceptor/personal" target="secondFrame">Portal del Preceptor</a></td>
		 </tr>
<%
		}
		//Verifica si es padre, madre o tutor para poner portal del padre						
		if (codigoPersonal.substring(0,1).equals("P")){
			row++;
%>
		 <tr>		 	
		 	<td class="text-center"><span class="badge badge-pill badge-dark"><%=row%></span></td>
		 	<td><a class="submenu" href="portales/padre/hijos.jsp" target="secondFrame">Portal del Padre</a></td>
		 </tr>
<%
		}
		//Verifica si es Profesor
		if (esMaestro){
			row++;
%>
		<tr>			
			<td class="text-center"><span class="badge badge-pill badge-dark"><%=row%></span></td>
			<td><a href="portales/maestro/cursos" target="secondFrame" >Portal del Profesor</a></td>
		</tr>
<% 		row++;	%>		
		<tr>			
			<td class="text-center"><span class="badge badge-pill badge-dark"><%=row%></span></td>
			<td><a href="portales/porDocente/portafolio" target="secondFrame" >Portafolio Docente</a></td>
		</tr>			
<%
		}
			
		if (esEditor){
			row++;
			out.print("<tr><td class='text-center'><span class='badge badge-pill badge-dark'>"+row+"</span></td><td><a  href='mapa/diamante/planes' target='secondFrame' >Planes Diamante</a></td></tr>");
		}
	
		//Verifica si es Mentor
		if (esMentor){						
				// Incluir la carga de materias para mentores.
			String opciones = (String) session.getAttribute("opciones");
			session.setAttribute("opciones", opciones+"130-");
			row++;
%>
		<tr>			
			<td class="text-center"><span class="badge badge-pill badge-dark"><%=row%></span></td>
			<td><a href="portales/mentor/portal" target="secondFrame" >Portal del Mentor</a></td>
		</tr>
<%
		}				
		// Verifica si es empleado
		if (esEmpleado || esMaestro){
			row++;
%>
		<tr>
			<td class="text-center"><span class="badge badge-pill badge-dark"><%=row%></span></td>
			<td><a class="submenu" href="portales/portafolio/datos" target="secondFrame" >Portafolio Apoyo</a></td>
		</tr>
<%		
		}	
	}
	
	for (int k = 0; k<lisMenu.size(); k++){
		Menu menu = (Menu) lisMenu.get(k);
		row++;
%>
		<tr>
			<td class="text-center"><span class="badge badge-pill badge-dark"><%=row%></span></td>
			<td><a href="menuDos?menu=<%=menu.getMenuId()%>" class="principal" id="menu" target="secondFrame"><%=menu.getMenuNombre()%></a></td>
		</tr>	
<%
	}	
%>	
	</tbody>	
	</table>
</div>
