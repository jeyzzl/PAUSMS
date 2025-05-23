<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="contacto" scope="page" class="aca.mentores.MentContacto"/>
<jsp:useBean id="contactoU" scope="page" class="aca.mentores.MentContactoUtil"/>

<% 	
	String periodoId 		= request.getParameter("periodoId");
	String mentorId 		= request.getParameter("mentorId"); 	
	String contactoId 		= request.getParameter("contactoId");
	String codigoPersonal	= request.getParameter("codigoAlumno");
	String opcion 			= request.getParameter("Opcion"); 

	contacto.setPeriodoId(periodoId);
  	contacto.setContactoId(contactoId);	
	contacto.setMentorId(mentorId);
	
	if(contactoU.existeReg(conEnoc, periodoId, mentorId, contactoId)==true){
		if(contactoU.deleteReg(conEnoc, periodoId, mentorId, contactoId)==true){
%>
			<center> <b>EL registro fue borrado..</b></center>
<%		}else{%>
			<center> <b>EL registro "No" fue borrado..</b></center>
<%		}
	}else{%>
	<center> <b>EL registro No existe..</b></center>
<%
	}
	
	if (opcion.equals("1")){ 
%>
	<meta http-equiv='REFRESH' content='0;URL=frm_mentor_contacto?CodigoAlumno=<%=codigoPersonal%>&PeriodoId=<%=periodoId%>'>
<%
	}else{
%>
	<meta http-equiv='REFRESH' content='0;URL=entrevistas?PeriodoId=<%=periodoId%>'>
<%		
	}
%>

<%@ include file= "../../cierra_enoc.jsp" %>
