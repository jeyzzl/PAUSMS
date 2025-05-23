<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="InvProyecto" scope="page" class="aca.investiga.InvProyecto"/>
<jsp:useBean id="InvProyectoU" scope="page" class="aca.investiga.InvProyectoUtil"/>
<jsp:useBean id="InvBitacora" scope="page" class="aca.investiga.InvBitacora"/>
<jsp:useBean id="InvBitacoraU" scope="page" class="aca.investiga.InvBitacoraUtil"/>
<jsp:useBean id="Usuario" scope="page" class="aca.vista.Usuarios"/>
<jsp:useBean id="UsuarioU" scope="page" class="aca.vista.UsuariosUtil"/>
<jsp:useBean id="CarreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>

<% 
	String codigo			= (String) session.getAttribute("codigoPersonal");	
	String proyectoId 		= request.getParameter("ProyectoId")==null?"0":request.getParameter("ProyectoId");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
	String fechaHora 		= aca.util.Fecha.getFechayHora();
	
	conEnoc.setAutoCommit(false);
	if (accion.equals("1")){
		// Cancelar		
		String estadoOld 	=  aca.investiga.InvProyectoUtil.getEstado(conEnoc, proyectoId);
		String estadoNew 	= String.valueOf(Integer.parseInt(estadoOld)-1).trim();
		
		if ( !estadoOld.equals("0") && aca.investiga.InvProyectoUtil.updateEstado(conEnoc, proyectoId, estadoNew)){
			InvBitacora.setProyectoId(proyectoId);
			InvBitacora.setFolio(InvBitacoraU.maximoReg(conEnoc, proyectoId));
			InvBitacora.setUsuario(codigo);
			InvBitacora.setFecha(fechaHora);
			InvBitacora.setEstadoOld(estadoOld);
			InvBitacora.setEstadoNew(estadoNew);
			if (InvBitacoraU.insertReg(conEnoc, InvBitacora)){
				conEnoc.commit();
			}else{
				conEnoc.rollback();
			}
		}
			
	}else if (accion.equals("2")){
		// Autorizar		
		String estadoOld 	=  aca.investiga.InvProyectoUtil.getEstado(conEnoc, proyectoId);
		String estadoNew 	= String.valueOf(Integer.parseInt(estadoOld)+1).trim();
		
		if ( !estadoOld.equals("4") && aca.investiga.InvProyectoUtil.updateEstado(conEnoc, proyectoId, estadoNew)){
			InvBitacora.setProyectoId(proyectoId);
			InvBitacora.setFolio(InvBitacoraU.maximoReg(conEnoc, proyectoId));
			InvBitacora.setUsuario(codigo);
			InvBitacora.setFecha(fechaHora);
			InvBitacora.setEstadoOld(estadoOld);
			InvBitacora.setEstadoNew(estadoNew);
			if (InvBitacoraU.insertReg(conEnoc, InvBitacora)){
				conEnoc.commit();
			}else{
				conEnoc.rollback();
			}
		}	
	}else if (accion.equals("3")){
		//Concluir
		String estadoOld 	=  aca.investiga.InvProyectoUtil.getEstado(conEnoc, proyectoId);
		if(!estadoOld.equals("0")&& aca.investiga.InvProyectoUtil.updateEstado(conEnoc, proyectoId, "5")){
			
			InvBitacora.setProyectoId(proyectoId);
			InvBitacora.setFolio(InvBitacoraU.maximoReg(conEnoc, proyectoId));
			InvBitacora.setUsuario(codigo);
			InvBitacora.setFecha(fechaHora);
			InvBitacora.setEstadoOld(estadoOld);
			InvBitacora.setEstadoNew("5");
			if (InvBitacoraU.insertReg(conEnoc, InvBitacora)){
				conEnoc.commit();
			}else{
				conEnoc.rollback();
			}
		}
		
	}
	conEnoc.setAutoCommit(true);
%>	
<%@ include file= "../../cierra_enoc.jsp" %>
<% 
	response.sendRedirect("proyecto");
%>