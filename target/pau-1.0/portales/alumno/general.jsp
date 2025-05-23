<%@ page import="java.io.*" %>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "../../conectadbp.jsp" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.pg.archivo.ArchGeneral"%>

<jsp:useBean id="archGeneral" scope="page" class="aca.pg.archivo.ArchGeneral"/>
<jsp:useBean id="archGeneralU" scope="page" class="aca.pg.archivo.ArchGeneralUtil"/>
<%
	String codigoPersonal		= (String) session.getAttribute("codigoAlumno");
	String codigoUsuario 		= (String) session.getAttribute("codigoPersonal");
	String folio				= "0";
	
	String COMANDO				= "";
	int numRow 					= 0;
	
	String tmcolorPortal 		= (String)session.getAttribute("colorPortal");
	if(tmcolorPortal==null)tmcolorPortal="";
	
	ArrayList<ArchGeneral> lisDocs	= archGeneralU.getListAlumno(conn2, codigoPersonal, "ORDER BY FOLIO");
%>
<head>
<link href="css/pa<%=tmcolorPortal%>.css" rel="STYLESHEET" type="text/css">
<script type="text/javascript">
	function inicio(){
		$("fotos").style.height = (document.viewport.getHeight()-10)+"px";
	}
	
	function muestraFoto(obj){
		$("visor").innerHTML = '';
		$("visor").insert('<img src="'+obj.src+'" height="'+(document.viewport.getHeight() - 20)+'" />');
	}
</script>
</head>
<body onload="inicio();">
<table style="width:100%; margin:0 auto">
<tr>
    <td align="center" width="180px" style="border: solid 1px black;">
    	<div id="fotos" style="height: 100%; overflow: auto;">
<%
	for(int i = 0; i < lisDocs.size(); i++){
		archGeneral = (ArchGeneral) lisDocs.get(i);
%>      
			<img name="Imagen<%=folio%>" id="Imagen<%=folio%>" src="fotoGeneral?matricula=<%=codigoPersonal %>&folio=<%=archGeneral.getFolio() %>" width="150" nosave border="1" hspace="2" vspace="2" style="cursor: pointer;" onclick="muestraFoto(this);">					
<%
	}
%>
		</div>
	</td>
	<td id="visor" valign="top" align="center" style="border: solid 1px black;">
		&nbsp;
	</td>
  </tr>
</table>

<%@ include file= "../../cierradbp.jsp" %>
<%@ include file= "../../cierra_enoc.jsp" %>
</body>