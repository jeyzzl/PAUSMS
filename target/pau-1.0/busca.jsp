<%@ page import= "java.util.HashMap"%>
<%	
	String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String codigoBusqueda		= request.getParameter("matricula")==null?"":request.getParameter("matricula");
	String mensaje 				= (String) request.getAttribute("mensaje");
	String tipoUsuario			= (String) request.getAttribute("tipoUsuario");
	String codigoUsuario		= (String) request.getAttribute("codigoUsuario");
	String nombreUsuario		= (String) request.getAttribute("nombreUsuario");
	
	if( accion.equals("1") ){
		if (mensaje.equals("NoExiste")){			
%>
			muestraResultado('Este alumno/empleado no existe*');
<%
		}else if (tipoUsuario.equals("Empleado")){
%>
			ocultaResultado();				
			refrescarPaginaPorBusqueda('Employee', '<%=nombreUsuario%>', '<%=codigoUsuario%>');
<%
		}else if (tipoUsuario.equals("Padre")){
%>
			ocultaResultado();				
			refrescarPaginaPorBusqueda('Father', '<%=nombreUsuario%>', '<%=codigoUsuario%>');
<%				
		}else{
%>
			$j("#frmInicio").contents().find("#parametroBusqueda").val("<%=codigoUsuario%>");						
			ocultaResultado();				
			refrescarPaginaPorBusqueda('Student', '<%=nombreUsuario%>', '<%=codigoUsuario%>');
<%			
		}		
	}else if(accion.equals("2")){	//Nombre
		
		String frase = request.getParameter("frase");
		if(frase.trim().equals("")){ %>
			ocultaResultado();
<%		}else{
			/**/
			if(mensaje.equals("Existe")){
				
				if (tipoUsuario.equals("Empleado")){			
%>
						ocultaResultado();						
						refrescarPaginaPorBusqueda('Employee', '<%=nombreUsuario%>', '<%=codigoUsuario%>');
<%
				}else if(tipoUsuario.equals("Padre")){
%>
						ocultaResultado();						
						refrescarPaginaPorBusqueda('Father', '<%=nombreUsuario%>', '<%=codigoUsuario %>');
<%						
				}else if (tipoUsuario.equals("Alumno")){
%>
						ocultaResultado();						
						refrescarPaginaPorBusqueda('Student', '<%=nombreUsuario%>', '<%=codigoUsuario %>');
<%
				}					
			}else if(tipoUsuario.equals("Elegir")){					
%>
					muestraResultado('<%=mensaje%>');
<%
			}else if (mensaje.equals("Lleno")){			
%>
					muestraResultado('<font size="3"><b>Too many</b> correlations were found<br><a class="btn btn-mini" href="javascript:window.frames[0].secondFrame.document.location.href=\'parametros/alumno/buscar?Accion=1&Completo=true&NombreCompleto=<%=frase %>\';ocultaResultado();">B&uacute;squeda completa</a></font>');
<%				
			}else if (mensaje.equals("Vacio")){ %>
				
				muestraResultado('<font size="3"><b>No correlations found</b></font>');
<%			}
		}
		
	}else if(accion.equals("3")){
		
		if(mensaje.equals("NoExiste")){%>
			muestraResultado('Este alumno/empleado no existe+');
<%		}else if (mensaje.equals("Existe")){			
%>
			document.location = document.location;
<%
		}
	}	
%>