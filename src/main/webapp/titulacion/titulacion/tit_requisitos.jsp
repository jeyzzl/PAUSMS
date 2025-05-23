<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.tit.RequisitoVO"%>

<jsp:useBean id="bRequisitos"  class="aca.tit.Requisitos" scope="page"/>
<head><link href="../../academico.css" rel="STYLESHEET" type="text/css">
<style type="text/css">
<!--
.Estilo4 {color: #000099}
-->
</style>
</head>
<STYLE TYPE="text/css">
.tabbox
	{
		background: #eeeeee;
		border-left: 0pt gray solid;
		border-right: 0pt gray solid;
		border-bottom: 1pt gray solid;
	}
.Estilo1 {font-size: 7pt}
.Estilo3 {color: #000000}
</STYLE>
<body marginwidth="0" marginheight="0" leftmargin="0" topmargin="0" 

background="../../imagenes/back.gif">
<table class="tabbox" style="background:white; width:100%; height:99%; margin:0 auto;">
  <tr valign="top">
    <td width="107" style="font-size: 8pt;" align="left"> <img 

src='../../imagenes/logo.jpg' width="99" height="105"/> <br>
        <br>
        <br>
        <span class="Estilo1"><strong>Dirección de Registro</strong><br>
      Apdo. 16-5 C.P. 67530<br>
      Montemorelos, NL, <br>
      M&eacute;xico<br>
      <br>
      <strong><spring:message code="aca.Tel"/>efonos:</strong><br>
      Directo(826) 263-0908<br>
      Conmutador 263-0900<br>
      Ext. 119,120,121 <br>
      Fax (826) 263-0979<br>
      <br>
      <b><spring:message code="aca.Creada"/></b> por el Gobierno<br>
      del estado de Nuevo<br>
      León, México, mediante<br>
      Resolución Oficial<br>
      publicada el 5 de mayo<br>
      de 1973.<br>
      <br>
      <b>Clave de la Institución</b><br>
      ante la SEP y Dirección<br>
      General de Estadística<br>
      19MSU1017U </span><br>
    </td>
    <td width="10"><img src='../../imagenes/linea.jpg'/></td>
    <td width="850">
      <table style='margin:0 auto;'>
        <tr>
          <td align="center" colspan="10"><font size="5"><b>UNIVERSIDAD DE 

MONTEMORELOS</b></font></td>
        </tr>
        <tr>
          <td align="center" colspan="10"><font size="4"><br>Requisitos para Titulación<br>
                <br>
          </font></td>
        </tr>
      </table>
      <table class="fieldbox" style="width:100%" >
<%	 
	RequisitoVO requisito = null;
	ArrayList<aca.tit.RequisitoVO> requisitos = bRequisitos.getRequisitos(conEnoc);
	for(int i=0;i<requisitos.size();i++){
	requisito = new RequisitoVO();
	requisito=(RequisitoVO)requisitos.get(i);
%>
	<tr valign='top'>
		<td width="80"><font face='Verdana' size='3'><b>PASO <%=requisito.getOrden()%>:</b></font></td>
		<td><font face='Verdana' size='3'><b><%=requisito.getNombre()%></b></font><br><br></td>
	</tr>
<%}%>
</table>
<%@ include file= "../../cierra_enoc.jsp" %>