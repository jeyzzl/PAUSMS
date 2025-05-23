<%@ include file="../../idioma.jsp"%>

<%@ page import="java.sql.*" %>
<%@ page import="aca.conecta.*" %>
<%@ page import="aca.vmeeting.Sistema" %><%
Connection  conVM	 	= null;
Conectar c 				= new Conectar();
conVM	 				= c.conPostgresVMeeting();

int accion = request.getParameter("accion")!=null?Integer.parseInt(request.getParameter("accion")):0;
switch(accion){
case 3: //Borrar
	Sistema sistema = new Sistema();
	sistema.setId(Integer.parseInt(request.getParameter("id")));
	if(sistema.deleteReg(conVM)){
		%>true<%
	}else{
		%>false<%
	}
	break;
}
c.desPostgres(conVM);
conVM = null;
%>