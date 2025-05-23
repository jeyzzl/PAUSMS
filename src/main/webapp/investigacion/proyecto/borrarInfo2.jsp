<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="InvBitacora" scope="page" class="aca.investiga.InvBitacora"/>
<jsp:useBean id="InvBitacoraU" scope="page" class="aca.investiga.InvBitacoraUtil"/>

<%
	String codigo			= (String) session.getAttribute("codigoPersonal");
	String proyectoId		= request.getParameter("ProyectoId");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String fechaHora 		= aca.util.Fecha.getFechayHora();
	
	if(accion.equals("1")){
		boolean borrar 			= false;
		
		String dirArchivo		= application.getRealPath("/investigacion/proyecto/archivos/"+proyectoId+"b.pdf");
		java.io.File archivo 	= new java.io.File(dirArchivo);
		int i=0;
		if (archivo.exists()){
			
			// Ciclo para corregir error en sistemas opreativos windows(No es necesario si el servidor de aplicaciones está en linux)
			while(!borrar && i<50000){
				if(archivo.delete()){
					borrar = true;				
				}
				i++;
			}
		}
		if (borrar){
			response.sendRedirect("proyecto");
		}else{
			out.println("No se pudo eliminar el archivo:["+proyectoId+"]");
		}
	
	}else if (accion.equals("2")){
		conEnoc.setAutoCommit(false);
		if (aca.investiga.InvProyectoUtil.updateEstado(conEnoc, proyectoId, "1")){
			InvBitacora.setProyectoId(proyectoId);
			InvBitacora.setFolio(InvBitacoraU.maximoReg(conEnoc, proyectoId));
			InvBitacora.setUsuario(codigo);
			InvBitacora.setFecha(fechaHora);
			InvBitacora.setEstadoOld("0");
			InvBitacora.setEstadoNew("1");
			if (InvBitacoraU.insertReg(conEnoc, InvBitacora)){
				conEnoc.commit();
			}
		}
		conEnoc.setAutoCommit(true);
		response.sendRedirect("proyecto");
	}
%>
<%@ include file="../../cierra_enoc.jsp" %>