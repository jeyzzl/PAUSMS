<%@page import="java.util.List"%>
<%@page import="aca.ssoc.spring.SsocRequisito"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>
	<link href="../../academico.css" rel="STYLESHEET" type="text/css">
	<STYLE TYPE="text/css">
		.tabbox	{
			background: #eeeeee;
			border-left: 0pt gray solid;
			border-right: 0pt gray solid;
			border-bottom: 1pt gray solid;
		}
		.Estilo1 {font-size: 7pt}
		.Estilo3 {color: #000000}
	</STYLE>
</head>
<body marginwidth="0" marginheight="0" leftmargin="0" topmargin="0" background="../../imagenes/back.gif">
	<table class="tabbox" style="background:white;" width="100%" height="99%" cellspacing="5"  align="center" bordercolor="#CCCCCC">
  	<tr valign="top">
    <td width="107" style="font-size: 8pt;" align="left">
    	<img src='../../imagenes/logo.jpg' width="99" height="105"/> <br>
        <br>
        <br>
        <span class="Estilo1"><strong>Direcci�n de Registro</strong><br>
      Apdo. 16-5 C.P. 67530<br>
      Montemorelos, NL, <br>
      M&eacute;xico<br>
      <br>
      <strong>Tel�fonos:</strong><br>
      Directo(826) 263-0908<br>
      Conmutador 263-0900<br>
      Ext. 1551,1552<br>
      Fax (826) 263-0979<br>
      <br>
      <b><spring:message code="aca.Creada"/></b> por el Gobierno<br>
      del estado de Nuevo<br>
      Le�n, M�xico, mediante<br>
      Resoluci�n Oficial<br>
      publicada el 5 de mayo<br>
      de 1973.<br>
      <br>
      <b>Clave de la Instituci�n</b><br>
      ante la SEP y Direcci�n<br>
      General de Estad�stica<br>
      19MSU1017U </span><br>
    </td>
    <td width="10"><img src='../../imagenes/linea.jpg'/></td>
    <td width="850">
	    <table style='margin:0 auto;'    >
	        <tr>
	          <td align="center" colspan="10"><font size="5"><b>UNIVERSIDAD DE MONTEMORELOS</b></font></td>
	        </tr>
	        <tr>
	          <td align="center" colspan="10"><font size="4"><br>Requisitos para Registrar el Servicio Social<br><br></font></td>
	        </tr>
		</table>
	    <table class="fieldbox" width="100%" >
<%	
	List<SsocRequisito> lisRequisitos 	= (List<SsocRequisito>)request.getAttribute("lisRequisitos");
	for(SsocRequisito requisito : lisRequisitos){
%>
		<tr valign='top'>
			<td width="80"><font face='Verdana' size='3'><b>PASO <%=requisito.getOrden()%>:</b></td>
			<td><font face='Verdana' size='3'><b><%=requisito.getRequisitoNombre()%></b><font><br><br></td>
		</tr>
<%	}%>
    	</table>
	</td>
  	</tr>
	</table>