<%@ include file="../../idioma.jsp"%>

<%@ page import="java.sql.*" %>
<%@ page import="aca.conecta.*" %>
<%@ page import="aca.vmeeting.Grupo" %><%
Connection  conVM	 	= null;
Conectar c 				= new Conectar();
conVM	 				= c.conPostgresVMeeting();

int accion = request.getParameter("accion")!=null?Integer.parseInt(request.getParameter("accion")):0;
switch(accion){
case 3: //Borrar
	Grupo grupo = new Grupo();
	grupo.setId(Integer.parseInt(request.getParameter("id")));
	if(grupo.deleteReg(conVM)){
		%>true<%
	}else{
		%>false<%
	}
	break;
}
c.desPostgres(conVM);
conVM = null;
%>