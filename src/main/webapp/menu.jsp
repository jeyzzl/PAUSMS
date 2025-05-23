<style>
	.radio{
		width: 40px;
       	height: 40px;
       	border-radius: 50%;
	}
	  	
	@media (min-width: 392px){
		.dropdown-menu .dropdown-toggle:after{
			border-top: .3em solid transparent;
		    border-right: 0;
		    border-bottom: .3em solid transparent;
		    border-left: .3em solid;
		}
		.dropdown-menu .dropdown-menu{
			margin-left:0; margin-right: 0;			
		}
		.dropdown-menu li{
			position: relative;			
		}
		.nav-item .submenu{ 
			display: none;
			position: absolute;			
			left:100%; top:-7px;
		}
		.nav-item .submenu-left{ 
			right:100%; left:auto;
		}
		.dropdown-menu > li:hover{ background-color: #cccccc}
		.dropdown-menu > li:hover > .submenu{
			display: block;
		}
	}		
</style>
<%
	boolean esEmpleado 								= (boolean)session.getAttribute("esEmpleado");
	String codigoFotoMenu 							= (String)session.getAttribute("codigoPersonal");
	List<aca.menu.spring.Menu> lisMenu				= (ArrayList<aca.menu.spring.Menu>)session.getAttribute("lisMenu");
	List<aca.menu.spring.Modulo> lisModulo			= (ArrayList<aca.menu.spring.Modulo>)session.getAttribute("lisModulo");
	List<aca.menu.spring.ModuloOpcion> lisOpcion	= (ArrayList<aca.menu.spring.ModuloOpcion>)session.getAttribute("lisOpcion");
%>	
<div id="container">	
	<nav id="navMenu" class="navbar navbar-expand-lg navbar-dark" style="background:gray">	
		<a class="navbar-brand" target="secondFrame" href="<%=esEmpleado?"empleado":"portales/alumno/resumen"%>"><img src="imagenes/logoUni.png" width="30px" style="position:relative;top:-5px;"></a>				
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main_nav">
	    	<span class="navbar-toggler-icon"></span>
		</button>	
		<div class="collapse navbar-collapse" id="main_nav">	
		<ul class="navbar-nav">
<%
	for (int k = 0; k<lisMenu.size(); k++){
		Menu menu = (Menu) lisMenu.get(k);				
%>
	<li class="nav-item dropdown">
	    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown"><%=menu.getMenuNombre()%></a>
	    <ul class="dropdown-menu">			
<%
		for (int i = 0; i<lisModulo.size(); i++){
			Modulo modulo = (Modulo) lisModulo.get(i);
			if (menu.getMenuId().equals(modulo.getMenuId())){
%>	
		<li style="width:177px;"><a class="dropdown-item" href="#"><%=modulo.getNombreModulo()%></a>
			<ul class="submenu dropdown-menu">			
<%
				for (int j=0; j<lisOpcion.size(); j++){
					ModuloOpcion menuOpcion = (ModuloOpcion) lisOpcion.get(j);
					if(menuOpcion.getModuloId().equals(modulo.getModuloId())){
			%>
				<li><a class="dropdown-item cerrarmenu" href="<%=modulo.getUrl()%><%=menuOpcion.getUrl()%>?moduloId=<%=modulo.getModuloId()%>&carpeta=<%=modulo.getUrl()%>" target="secondFrame"><%=menuOpcion.getNombreOpcion()%></a></li>				
			<%
					}
				}
			%>
			</ul>
		</li>
<%
			}
		}
%>
		</ul>
	</li>
<%
	}	
%>	
	</ul>	
	</div> 
	<!-- navbar-collapse.// -->
	<!-- Div para Separar la busqueda y los iconos hacia el lado derecho -->
	<div class="d-flex align-items-center">			
	 <details>
          <summary>
          	<img  class="radio" src="fotoMenu?Codigo=<%=codigoFotoMenu%>">
          </summary>
       <nav class="menu">
          <a href="configuracion" target="secondFrame"><i class="fas fa-cog"></i></a>
          <a href="salir"><i class="fas fa-sign-out-alt"></i></a>
       </nav>
     </details>
	</div>	
	</nav>
</div>	