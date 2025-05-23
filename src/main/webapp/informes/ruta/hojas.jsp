<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="aca.vista.EstadisticaUtil"%>
<%@ page import="aca.vista.HojaRutaUtil"%><head>

<jsp:useBean id="Parametros" scope="page" class="aca.parametros.Parametros"/>

<style TYPE="text/css">
	BODY { background: #ffffff; }
	.para1 { margin-top: -700px; }
	.para2 { margin-top: 200px; }
</style>

</head>

<%
	String sCodigo1					= request.getParameter("inicial");
	String sCodigo2					= request.getParameter("final");
	String institucion 				= (String)session.getAttribute("institucion");
	
	ArrayList	lisEstadistica 			= new ArrayList();
	EstadisticaUtil	estadisticaU 	= new EstadisticaUtil();
	lisEstadistica					= estadisticaU.getListMat(conEnoc, sCodigo1, sCodigo2, "ORDER BY 1");
	
	ArrayList lisHojaRuta				= new ArrayList();
	HojaRutaUtil hojaU				= new HojaRutaUtil();

	String sDatosTutor				= "";
	String sCodigo					= "";
	String sNombre					= "";
	String sEstado					= "";
	String sFecha					= "";
	String sHora					= "";
	String sSexo					= "";
	String sNacimiento				= "";
	String sCivil					= "";
	String sCurp					= "";
	String sReligion				= "";
	String sResidencia				= "";
	String sTipo					= "";
	String sModalidad				= "";
	String sNacion					= "";
	String sPais					= "";
	String sCarrera					= "";
	String sDir						= "";
	String sEmail					= "";
	String sBautizado				= "";
	String sTel						= "";
	String sPlan					= "";
	String sTnombre					= "";
	String sTtel					= "";
	String sTemail					= "";
	String sTdir					= "";
	String sTcolonia				= "";
	String sTciudad					= "";
	String sTestado					= "";
	String sTpais					= "";
	String sTcp						= "";
	String sTap						= "";
	int	i							= 0;
	
	Parametros.mapeaRegId(conEnoc, "1");
	
	for (i=0; i<lisEstadistica.size(); i++){
		java.util.StringTokenizer sToken = new java.util.StringTokenizer((String) lisEstadistica.get(i), "~");
		sCodigo= sToken.nextToken();
		sEstado= sToken.nextToken();
		sNombre= sToken.nextToken();
		
		lisHojaRuta	= hojaU.getListAlum(conEnoc, sCodigo);
		sToken = new java.util.StringTokenizer((String) lisHojaRuta.get(0), "~");
		sFecha		= sToken.nextToken();
		sHora		= sToken.nextToken();
		sSexo		= sToken.nextToken();
		sNacimiento	= sToken.nextToken();
		sCivil		= sToken.nextToken();
		sCurp		= sToken.nextToken();
		sReligion	= sToken.nextToken();
		sResidencia	= sToken.nextToken();
		sTipo		= sToken.nextToken();
		sModalidad	= sToken.nextToken();
		sNacion		= sToken.nextToken();
		sPais		= sToken.nextToken();
		sCarrera	= sToken.nextToken();
%>



<div align="center" class="para2"> <img alt=""  src="../../imagenes/logo_fondo.jpg" width=519 height="485"></div>
<div CLASS="para1" >
<br>
<table   align="CENTER" width="100%">
  <tr> 
    <td colspan="4" align="center"><font size="4" face="Arial Narrow, Arial"><b><%=institucion%>, A.C.</b></font>
    </td>
  </tr>
  <tr>
      <td colspan="4" align="center"> <font size="3" face="Arial Narrow, Arial"><b>Hoja de Ruta</b></font></td>
  </tr>
  <tr>
      <td colspan="4" align="right"><font size="2" face="Arial Narrow, Arial"><b><spring:message code="aca.Fecha"/>:</b><%=sFecha%>&nbsp; 
        <b>Hora:</b> <%=sHora%>&nbsp;</font></td>
  </tr>
  <tr> 
    <th colspan="4" align="center"> <font size="2" face="Arial Narrow, Arial"><b>Alumno: [ <%=sCodigo%> ] [ <%=sNombre%> ]</b></font> 
    </th>
  </tr>
<% 		
		sDatosTutor = hojaU.getTutor(conEnoc, sCodigo);
		sToken = new java.util.StringTokenizer((String) sDatosTutor, "~");
		sDir			= sToken.nextToken();
		sEmail			= sToken.nextToken();
		sBautizado		= sToken.nextToken();
		sTel			= sToken.nextToken();
		sPlan			= sToken.nextToken();
		sTnombre		= sToken.nextToken();
		sTtel			= sToken.nextToken();
		sTemail			= sToken.nextToken();
		sTdir			= sToken.nextToken();
		sTcolonia		= sToken.nextToken();
		sTciudad		= sToken.nextToken();
		sTestado		= sToken.nextToken();
		sTpais			= sToken.nextToken();
		sTcp			= sToken.nextToken();
		sTap			= sToken.nextToken();
%>
  <tr> 
    <td width="12%" align="LEFT"><strong> <font size="2" face="Arial Narrow, Arial">&nbsp;Sexo:</font></strong></td>
    <td width="50%" align="LEFT"> <font size="1" face="Arial Narrow, Arial"><%=sSexo%></font></td>
    <td width="14%" align="LEFT"><font size="1" face="Arial Narrow, Arial"><strong><font size="1" face="Arial, Helvetica, sans-serif"><strong><font size="2" face="Arial Narrow, Arial">Religi&oacute;n:</font></strong></font></strong></font></td>
    <td width="24%" align="LEFT"><%=sReligion%> </td>
  </tr>
  <tr> 
    <td align="LEFT"><strong> <font size="2" face="Arial Narrow, Arial">&nbsp;Fecha Nac.:</font></strong></td>
    <td align="LEFT"><font size="1" face="Arial Narrow, Arial"><%=sNacimiento%></font></td>
    <td align="LEFT"><font size="2" face="Arial Narrow, Arial"><strong><font size="1" face="Arial Narrow, Arial"><strong><font size="2" face="Arial Narrow, Arial"><b><spring:message code="aca.Bautizado"/>:</b></font> 
        </strong></font><font size="2" face="Arial Narrow, Arial"></font></strong></font></td>
      <td align="LEFT"><%=sBautizado%></td>
  </tr>
  <tr> 
      <td align="LEFT"><strong> <font size="2" face="Arial Narrow, Arial">&nbsp;<b><spring:message code="aca.Estado Civil"/>:</b></font></strong></td>  
      <td align="LEFT"> <font size="1" face="Arial Narrow, Arial"><%=sCivil%></font></td>
      <td align="LEFT"> <font size="2" face="Arial Narrow, Arial"><b><spring:message code='aca.Residencia'/>:</b></font></td>
      <td align="LEFT"><%=sResidencia%></td>
  </tr>
    <tr> 
      <td align="LEFT"><strong><font size="2" face="Arial Narrow, Arial">&nbsp;</font><font size="1" face="Arial Narrow, Arial"><strong><font size="2" face="Arial Narrow, Arial">Direcci&oacute;n:<b></b></font></strong></font></strong></td>
      <td align="LEFT"><%=sDir%></td>  
      <td align="LEFT"><font size="2" face="Arial Narrow, Arial"><b>Tipo Alumno:</b></font></td>
    <td align="LEFT"><font size="1" face="Arial Narrow, Arial"><%=sTipo%></font></td>
  </tr>
  <tr> 
      <td height="20" align="LEFT"><strong><font size="1" face="Arial Narrow, Arial">&nbsp;<strong><font size="1" face="Arial, Helvetica, sans-serif"><strong><font size="2" face="Arial Narrow, Arial"><b><spring:message code="aca.Tel"/>&eacute;fono:</b></font></strong></font></strong></font></strong></td>
      <td align="LEFT"><%=sTel%></td>  
      <td align="LEFT"><font size="2" face="Arial Narrow, Arial"><strong><font size="2" face="Arial Narrow, Arial">Modalidad:</font></strong></font></td>
      <td align="LEFT"><%=sModalidad%></td>
  </tr>

  <tr> 
      <td align="LEFT"><strong><font size="1" face="Arial, Helvetica, sans-serif"><strong><font size="2" face="Arial Narrow, Arial">&nbsp;e-mail:</font></strong></font> 
        </strong></td>
      <td align="LEFT"><%=sEmail%></td>
      <td align="LEFT"><font size="2" face="Arial Narrow, Arial"><strong><font size="2" face="Arial Narrow, Arial"><spring:message code="aca.Estado"/>:</font></strong></font></td>
      <td align="LEFT"><%=sEstado%></td>
  </tr>
  <tr> 
      <td align="LEFT"><strong><font size="2" face="Arial Narrow, Arial">&nbsp;<strong><font size="2" face="Arial Narrow, Arial"><b><spring:message code='aca.Curp'/>:</b></font></strong></font></strong></td>
      <td align="LEFT"><%=sCurp%></td>
    <td align="LEFT"><font size="1" face="Arial Narrow, Arial">&nbsp;</font></td>
    <td align="LEFT"><font size="1" face="Arial Narrow, Arial">&nbsp;</font></td>
  </tr>
  <tr> 
      <td colspan="4" align="center"> <font size="1" face="Arial, Helvetica, sans-serif"><b><spring:message code="aca.Plan"/>:</b> 
        [<%=sPlan%>] &nbsp; <b>Estudia:</b> [<%=sCarrera%>]</font></td>
  </tr>
<tr> 
    <td colspan="4" align="CENTER">
	  <font size="1" face="Arial, Helvetica, sans-serif">&nbsp;</font></td>
  </tr>
</table>  
  <table style="width:100%"   >
    <tr> 
      <td colspan="6" align="center"><hr></td>
    </tr>
    <tr> 
      <td colspan="6" align="center"> <font size="3" face="Arial Narrow, Arial"><b><spring:message code="aca.Tutor"/> 
        y Dirección actual para envió de Estado de Cuenta: </b></font></td>
    </tr>
    <tr> 
      <td width="7%" align="LEFT" height="10"><font size="2" face="Arial Narrow, Arial"><strong><spring:message code="aca.Tutor"/>:</strong></font></td>
      <td width="37%" align="LEFT"><%=sTnombre%></td>
      <td width="8%" align="LEFT"><font size="2" face="Arial Narrow, Arial"><strong>Colonia:</strong></font></td>
      <td align="LEFT"><%=sTcolonia%></td>
      <td align="LEFT"><font size="2" face="Arial Narrow, Arial"><strong>Cod. 
        Post.</strong>:</font></td>
      <td width="18%" align="LEFT"><%=sTcp%></td>
    </tr>
    <tr> 
      <td align="LEFT"><font size="2" face="Arial Narrow, Arial"><strong>><spring:message code='aca.Telefono'/>:</strong></font></td>
      <td align="LEFT"><%=sTtel%></td>
      <td align="LEFT"><font size="2" face="Arial Narrow, Arial"><strong>Ciudad:</strong></font></td>
      <td width="18%" align="LEFT"><%=sTciudad%></td>
      <td width="12%" align="LEFT"><font size="2" face="Arial Narrow, Arial"><strong>Apdo. 
        Post.:</strong></font></td>
      <td width="18%" align="LEFT"><%=sTap%></td>
    </tr>
    <tr> 
      <td width="7%" align="LEFT"><font size="2" face="Arial Narrow, Arial"><strong>email:</strong></font></td>
      <td align="LEFT"><%=sTemail%></td>
      <td width="8%" align="LEFT"><font size="2" face="Arial Narrow, Arial"><strong><spring:message code="aca.Estado"/></strong>:</font></td>
      <td align="LEFT"><%=sTestado%></td>
      <td align="LEFT">&nbsp;</td>
      <td align="LEFT">&nbsp;</td>
    </tr>
    <tr> 
      <td align="LEFT"><font size="2" face="Arial Narrow, Arial"><strong>Direcci&oacute;n:</strong></font></td>
      <td align="LEFT"><%=sTdir%></td>
      <td align="LEFT"><font size="2" face="Arial Narrow, Arial"><strong>Pa&iacute;s:</strong></font></td>
      <td align="LEFT" colspan="3"><%=sTpais%></td>
    </tr>
    <tr> 
      <td colspan="6" align="center"><hr></td>
    </tr>
  </table>  
  <table style="width:100%"   >
    <tr> 
      <td colspan="6" align="center"> <font size="3" face="Arial Narrow, Arial"><b>M&oacute;dulos 
        de Admisi&oacute;n (</b> Informaci&oacute;n general -- Mtro. G&eacute;ner 
        Avil&eacute;s -- Ext. 380 <b>)</b></font></td>
    </tr>
    <tr> 
      <td width="9%" align="LEFT"><strong><font size="2" face="Arial Narrow, Arial">Documentos:</font></strong></td>
       <td width="18%" align="LEFT"><strong><font size="2" face="Arial Narrow, Arial">&nbsp;</font></strong></td>
      <td width="18%" align="LEFT"><strong><font size="2" face="Arial Narrow, Arial"><spring:message code="aca.Saldo"/> 
        Actual:</font></strong></td>
      <td width="10%" align="LEFT"><strong></strong></td>
      <td width="21%" align="LEFT"><strong><font size="2" face="Arial Narrow, Arial">Nacionalidad&nbsp;&nbsp;<%=sNacion%></font></strong></td>
      <td width="24%" align="LEFT"><strong><font size="2" face="Arial Narrow, Arial"> 
        (<%=sPais%>)</font></strong></td>
    </tr>
    <tr> 
      <td align="left" colspan="2"><strong> <font size="2" face="Arial Narrow, Arial">&nbsp;&nbsp;M 
        &oacute; d u l o</font></strong></td>
      <td align="left" colspan="2"><strong> <font size="2" face="Arial Narrow, Arial">&nbsp;&nbsp;L 
        u g a r</font></strong></td>
      <td align="left" colspan="2"><strong> <font size="2" face="Arial Narrow, Arial">&nbsp;&nbsp; 
        R e s p o n s a b l e</font></strong></td>
    </tr>
    <tr <%=sColor%>> 
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial">Cambio 
        de datos de la Base</font></td>
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial">Biblioteca 
        -- Sala 3</font></td>
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial"> 
        Betty Montejo ( Ext. 381 ) ________________________</font></td>
    </tr>
    <tr> 
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial">Documentos 
        Legales </font></td>
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial">Biblioteca 
        -- Sala 3</font></td>
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial"> 
        Socorro Arag&oacute;n ( Ext. 384 ) ______________________</font></td>
    </tr>
    <tr <%=sColor%>> 
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial">Documentos 
        Acad&eacute;micos</font></td>
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial">Biblioteca 
        -- Sala 4</font></td>
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial"> 
        Jaime Blanco( Ext. 383 ) _________________________</font></td>
    </tr>
    <tr> 
      <td height="15" colspan="2" align="left"><font size="2" face="Arial Narrow, Arial">Asignaci&oacute;n 
        de residencia</font></td>
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial">Biblioteca 
        -- Sala 4</font></td>
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial"> 
        A. Quintero, C. Cruz ( Ext. 385-386 )________________</font></td>
    </tr>
    <tr <%=sColor%>> 
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial">Deudas 
        en Biblioteca</font></td>
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial">Biblioteca 
        -- Area de circulaci&oacute;n</font></td>
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial"> 
        Ever Torres ( Ext.137 ) __________________________</font></td>
    </tr>
    <tr> 
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial">Deudas 
        de Colegiaturas</font></td>
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial">Capilla 
        de Teolog&iacute;a</font></td>
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial"> 
        Ra&uacute;l Randeles( Ext. 387 - 379 ) ___________________</font></td>
    </tr>
    <tr <%=sColor%>> 
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial"> 
        Trabajo Manual( 4&ordm;,6&ordm; y 8&ordm; Sem.)</font></td>
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial">Biblioteca 
        -- Sala 3</font></td>
      <td align="left" colspan="2"><font size="2" face="Arial Narrow, Arial">Rocio 
        Carpintero ( Ext 382 )</font> ______________________</td>
    </tr>
    <tr> 
      <td height="15" colspan="6" align="center"><hr></td>
    </tr>
  </table>  
  <table style="width:100%"   >
    <tr> 
      <td colspan="3" align="center"> <font size="3" face="Arial Narrow, Arial"><b>Pasos 
        de Matr&iacute;cula</b></font></td>
    </tr>
    <tr> 
      <td width="26%" align="center"><strong><font size="2" face="Arial Narrow, Arial"><spring:message code='aca.Paso'/></font></strong></td>
      <td width="27%" align="center"><strong><font size="2" face="Arial Narrow, Arial">Lugar</font></strong></td>
      <td width="47%" align="left"><strong><font size="2" face="Arial Narrow, Arial">&nbsp;&nbsp;Responsable</font></strong></td>
    </tr>
    <tr <%=sColor%>> 
      <td height="15" align="left"><font size="2" face="Arial Narrow, Arial">1. 
        Encuesta Epidemiologica</font></td>
      <td align="left"><font size="2" face="Arial Narrow, Arial">Biblioteca -- 
        Sala 3</font></td>
      <td align="left"><font size="2" face="Arial Narrow, Arial">Tito Venegas</font></td>
    </tr>
    <tr> 
      <td  align="left"><font size="2" face="Arial Narrow, Arial">2. Carga de Materias</font></td>
      <td align="left"><font size="2" face="Arial Narrow, Arial">Coordinaci&oacute;n 
        de la carrera</font></td>
      <td align="left"><font size="2" face="Arial Narrow, Arial">coordinador de 
        carrera</font></td>
    </tr>
    <tr <%=sColor%>> 
      <td height="15" align="left"><font size="2" face="Arial Narrow, Arial">3. 
        Plan de pago</font></td>
      <td align="left"><font size="2" face="Arial Narrow, Arial">Capilla de Teolog&iacute;a</font></td>
      <td align="left"><font size="2" face="Arial Narrow, Arial">Eliezer Castellanos, 
        Ra&uacute;l Randeles<em>,</em> A.Sebastian, A.Olivas ( Ext. 379, 387 )</font></td>
    </tr>
    <tr> 
      <td height="18" align="left"><font size="2" face="Arial Narrow, Arial">4. 
        Caja</font></td>
      <td align="left"><font size="2" face="Arial Narrow, Arial">Oficina de Rector&iacute;a<br>
        Capilla de Teología</font></td>
      <td align="left"><font size="2" face="Arial Narrow, Arial">Lourdes de la 
        Fuente ( Ext. 514 )<br>
        Marlin Barrios</font></td>
    </tr>
    <tr <%=sColor%>> 
      <td align="left"><font size="2" face="Arial Narrow, Arial">5. Cierre </font></td>
      <td align="left"><font size="2" face="Arial Narrow, Arial">Oficina de Rector&iacute;a</font></td>
      <td align="left"><font size="2" face="Arial Narrow, Arial"><%=Parametros.getCardex()%>, 
        Aida Baltazar ( Ext. 128 )</font></td>
    </tr>
    <tr> 
      <td colspan="3" align="center"><hr></td>
    </tr>
  </table>  
<table style="width:100%"   > 
  <tr><td colspan="4" align="center">&nbsp;</td></tr>
  <tr> 
    <th colspan="4" align="center"> <font size="2" face="Arial Narrow, Arial"><b>Alumno: 
      [ <%=sCodigo%> ] [ <%=sNombre%> ]</b></font> 
    </th>
  </tr>  
  <tr> 
      <td colspan="4" align="center"> <font size="2" face="Arial Narrow, Arial"><b>Actualizar 
        Datos para env&iacute;o de Estado de Cuenta</b></font> </td>
  </tr>
  <tr> 
    <td width="11%" align="LEFT"><font size="2" face="Arial Narrow, Arial">&nbsp;Tutor:</font></td>
    <td width="42%" align="LEFT"><hr></td>
    <td width="10%" align="LEFT"><font size="2" face="Arial Narrow, Arial">&nbsp;Telefono:</font></td>
    <td width="37%" align="LEFT"><hr></td>
  </tr>
  <tr> 
      <td align="LEFT"><font size="2" face="Arial Narrow, Arial">&nbsp;Direcci&oacute;n:</font></td>
    <td align="LEFT"><hr></td>
      <td align="LEFT"><font size="2" face="Arial Narrow, Arial">&nbsp;Pa&iacute;s:</font></td>
    <td align="LEFT"><hr></td>
  </tr>
  <tr> 
      <td align="LEFT"><font size="2" face="Arial Narrow, Arial">&nbsp;Colonia:</font></td>
    <td align="LEFT"><hr></td>
    <td align="LEFT"><font size="2" face="Arial Narrow, Arial">&nbsp;Estado:</font></td>
    <td align="LEFT"><hr></td>
  </tr>
  <tr> 
    <td align="LEFT"><font size="2" face="Arial Narrow, Arial">&nbsp;Cod. Postal:</font></td>
    <td align="LEFT"><hr></td>
    <td align="LEFT"><font size="2" face="Arial Narrow, Arial">&nbsp;Ciudad:</font></td>
    <td align="LEFT"><hr></td>
  </tr>
  <tr> 
    <td height="15" align="LEFT"><font size="2" face="Arial Narrow, Arial">&nbsp;Apdo. Postal:</font></td>
    <td align="LEFT"><hr></td>
    <td align="LEFT"><font size="2" face="Arial Narrow, Arial">&nbsp;e-mail:</font></td>
    <td align="LEFT"><hr></td>
  </tr>
</table>
<table style="width:100%"   >  
  <tr><td colspan="4" align="center">&nbsp;</td></tr>
  <tr> 
    <th colspan="4" align="center"> <font size="2" face="Arial Narrow, Arial"><b>Alumno: 
      [ <%=sCodigo%> ] [ <%=sNombre%> ]</b></font> 
    </th>
  </tr>
  <tr> 
      <td colspan="4" align="center"> <font size="2" face="Arial Narrow, Arial"><b>Actualizar Datos de Residencia</b></font></td>
  </tr>
  <tr> 
      <td width="10%" align="LEFT"><font size="2" face="Arial Narrow, Arial">&nbsp;Tutor:</font></td>
    <td width="43%" align="LEFT"><hr></td>
      <td width="10%" align="LEFT"><font size="2" face="Arial Narrow, Arial">&nbsp;Municipio:</font></td>
    <td width="37%" align="LEFT"><hr></td>
  </tr>
  <tr> 
      <td height="15" align="LEFT"><font size="2" face="Arial Narrow, Arial">&nbsp;Calle/Num.:</font></td>
    <td align="LEFT"><hr></td>
      <td align="LEFT"><font size="2" face="Arial Narrow, Arial">&nbsp;Pa&iacute;s:</font></td>
    <td align="LEFT"><hr></td>
  </tr>
  <tr> 
      <td align="LEFT"><font size="2" face="Arial Narrow, Arial">&nbsp;Colonia:</font></td>
    <td align="LEFT"><hr></td>
      <td align="LEFT"><font size="2" face="Arial Narrow, Arial">&nbsp;Telefono:</font></td>
    <td align="LEFT"><hr></td>
  </tr>  
</table>
</div>
<%	
	} // Fin del for
	lisEstadistica		= null;
	estadisticaU		= null;
	lisHojaRuta			= null;
	hojaU				= null;
%>

<%@ include file= "../../cierra_enoc.jsp" %>