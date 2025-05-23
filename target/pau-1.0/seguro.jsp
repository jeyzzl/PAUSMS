<%		
	String sSesion			= session.getId();
	String sUsuario			= (String) session.getAttribute("usuario");
	String sOpciones		= (String) session.getAttribute("opciones");
	String codPerUser		= (String) session.getAttribute("codigoPersonal");
	String codAlumUser		= (String) session.getAttribute("codigoAlumno");
	String porAlumnoAdmin	= (String) session.getAttribute("portalAlumno");
	String porMaestroAdmin	= (String) session.getAttribute("portalMaestro");
	aca.menu.Sesion sesion	= new aca.menu.Sesion();	
%>