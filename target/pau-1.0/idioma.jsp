<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String lenguajeValue =  (String) session.getAttribute("lenguaje")==null?"es": (String) session.getAttribute("lenguaje");
	//System.out.println("Lenguaje:"+lenguajeValue);
%>
<%if(lenguajeValue.equals("es")){%>
	<fmt:setLocale value="es" scope="session"/>
<%}else if(lenguajeValue.equals("en")){%>
	<fmt:setLocale value="en" scope="session"/>
<%}else{%>
	<fmt:setLocale value="es" scope="session"/>
<%} %>
	
<fmt:setBundle basename="aca.idioma.messages"/>