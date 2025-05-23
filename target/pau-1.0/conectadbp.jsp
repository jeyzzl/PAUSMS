<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="aca.conecta.*" %>
<%
	Connection conn2 		= null;
	Conectar conecta		= new Conectar();
	conn2 					= conecta.conPostgresDirecta();
	Statement   stmt2 		= null;
	ResultSet   rset2 		= null;
	PreparedStatement pstmt	= null;
	String COMANDO2 		= "";
%>