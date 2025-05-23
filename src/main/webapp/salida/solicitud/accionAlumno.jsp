<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.util.Fecha"%>

<jsp:useBean id="Alumno" scope="page" class="aca.salida.SalidaAlumno"/>

<%
	// Declaracion de variables	
	String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	int nAccion			= Integer.parseInt(accion);
	String usuario 		= (String) session.getAttribute("codigoPersonal");

	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 2: { // Grabar
			Alumno.setCodigoPersonal(request.getParameter("Alumno"));
			Alumno.setSalidaId(request.getParameter("salida"));
			Alumno.setFecha(aca.util.Fecha.getHoy());
			Alumno.setUsuario(usuario);
			if (Alumno.existeReg(conEnoc) == false){
				if (Alumno.insertReg(conEnoc)){
					response.sendRedirect("alumnos?salida="+request.getParameter("salida"));
				}
			}
			break;
		}
		case 4: { // Borrar
			Alumno.setCodigoPersonal(request.getParameter("Alumno"));
			Alumno.setSalidaId(request.getParameter("salida"));
			if (Alumno.existeReg(conEnoc) == true){
				if (Alumno.deleteReg(conEnoc)){
					response.sendRedirect("alumnos?salida="+request.getParameter("salida"));
				}
			}
			break;
		}
	}	
%>
<%@ include file= "../../cierra_enoc.jsp" %>