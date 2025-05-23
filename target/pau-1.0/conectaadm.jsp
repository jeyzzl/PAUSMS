<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="aca.conecta.*" %>
<%
	Connection  conAdm 		= null;
	Conectar conectaAdm		= new Conectar();
	conAdm 					= conectaAdm.conPostgresAdm();
%>