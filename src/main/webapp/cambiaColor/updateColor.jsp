<%@ include file= "../con_enoc.jsp" %>

<jsp:useBean id="colorAlum" scope="page" class="aca.portal.Alumno"/>


<%
	String color = request.getParameter("color");

	String sCodigoPersonal 	= (String)session.getAttribute("codigoPersonal");	

	if(color!=null){
		if(!color.equals("default"))color = "#"+color;

			aca.portal.Alumno alumn = new aca.portal.Alumno();
 			alumn.guardaColor(conEnoc, sCodigoPersonal, color);
 			session.setAttribute("colorTablas", color);		
 		%>actualizar();<%
 		
	}
%>	
<%@ include file= "../../cierra_enoc2.jsf" %>