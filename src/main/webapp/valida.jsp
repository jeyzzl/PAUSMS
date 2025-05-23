<%@ page buffer= "none" %>
<%@ page import= "java.util.*"%>
<% 
	String saltoPagina 			= (String) request.getAttribute("saltoPagina");	
	boolean validaClave 		= (boolean) request.getAttribute("validaClave");
	boolean avisoPrivacidad		= (boolean) request.getAttribute("avisoPrivacidad");
	String usuario 				= request.getParameter("Usuario")==null?"0":request.getParameter("Usuario");
	
	// Si tiene acceso y es clave generada por default
	if (saltoPagina.equals("entrar") && avisoPrivacidad==false){
		out.print("privacidad();");		
	}else if (saltoPagina.equals("entrar") && validaClave){
		out.print("cambiaClave();");		
	}else if (saltoPagina.equals("entrar")){
		out.print("entrar();");		
	}else if (saltoPagina.equals("errorUsuario")){
		out.print("usuarioIncorrecto();");		
	}else if (saltoPagina.equals("errorClave")){
		out.print("claveIncorrecto();");
		if (usuario.substring(0,1).equals("0") || usuario.substring(0,1).equals("1")){
			out.print("recuperarContra();");			
		}		
	}else if (saltoPagina.equals("cambiarPassword")){
		out.print("cambiarPassword();");		
	}
%>

