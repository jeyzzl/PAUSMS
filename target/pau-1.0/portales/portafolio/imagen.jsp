<%@page import="java.io.IOException"%>
<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String codigoEmpleado = (String) session.getAttribute("codigoPersonal");
		String documentoId = request.getParameter("DocumentoId") == null ? "0"
				: request.getParameter("DocumentoId");
		String periodoId = (String) session.getAttribute("porPeriodo");
		String hoja = request.getParameter("Hoja") == null ? "1" : request.getParameter("Hoja");
		String prefijo = aca.portafolio.PorDocumento.getArchivo(conEnoc, documentoId);
		String nomArchivo = codigoEmpleado + periodoId + prefijo + hoja;

		String dir = application.getRealPath("/WEB-INF/portafolio/") + "/";
		byte[] buff = null;
		try {
			java.io.FileInputStream instream = null;
			java.io.File f =null;
			if (!hoja.equals("0")) {
				 f = new java.io.File(dir + nomArchivo + ".jpg");

				if (f.exists()) {
					buff = new byte[(int) f.length()];
					instream = new java.io.FileInputStream(dir + nomArchivo + ".jpg");
				} else {
					f = new java.io.File(dir + "nofoto.png");
					buff = new byte[(int) f.length()];
					instream = new java.io.FileInputStream(dir + "nofoto.png");
				}

			}else{
			f = new java.io.File(dir + request.getParameter("extension") + ".jpg");
				if (f.exists()) {
					buff = new byte[(int) f.length()];
					instream = new java.io.FileInputStream(dir + request.getParameter("extension") + ".jpg");
				} else {
					f = new java.io.File(dir + "nofoto.png");
					buff = new byte[(int) f.length()];
					instream = new java.io.FileInputStream(dir + "nofoto.png");
				} 
			}
			instream.read(buff, 0, (int) f.length());
			response.setContentType("image/jpeg");
			response.getOutputStream().write(buff);
			response.flushBuffer();
			instream.close();
			
			
			
		} catch (IOException ioe) {
			System.err.println("Error en la imagen " + ioe);
		} catch (Exception e) {
			System.err.println("Error en la imagen " + e);
		}
%>
<%@ include file="../../cierra_enoc.jsp"%>