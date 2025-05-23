<%
	String accion 		= (String) request.getAttribute("accion"); 
	String certCurso	= (String) request.getAttribute("certCurso"); 
	String cursoId 		= (String) request.getAttribute("cursoId"); 
	String curso 		= (String) request.getAttribute("curso"); 
	String planId 		= (String) request.getAttribute("planId"); 
	boolean existe 		= (boolean) request.getAttribute("existe"); 
	boolean inserto 	= (boolean) request.getAttribute("inserto"); 
	boolean borro 		= (boolean) request.getAttribute("borro"); 
	
	if(accion.equals("1")){	//	Guardar
		if(existe){%>
			yaExiste('<%=certCurso %>','<%=cursoId %>','<%=planId %>');
<%		}else{
			if(inserto){%>
				mostrarUnionCompletada('<%=certCurso %>', '<%=curso %>', '<%=planId %>');
<%			}else{%>
				mensajeError('<%=certCurso %>', "Error al grabar. <br /> Int&eacute;ntelo de nuevo");
<%			}
		}
	}else if(accion.equals("2")){	//	Si ya existia y confirmo borrar. Borrar y guardar el nuevo
		if(borro){
			if(inserto){%>
				mostrarUnionCompletada('<%=certCurso %>', '<%=curso %>','<%=planId %>');
<%			}
		}else{%>
			mensajeError('<%=certCurso %>', "Error al grabar. <br /> Int&eacute;ntelo de nuevo");
<%		}
	}
%>