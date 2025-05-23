<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	String institucion 		= (String)session.getAttribute("institucion");
%>

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
    <td width="107" style="font-size: 8pt;" align="left"> <img src='../../imagenes/logo.jpg' width="99" height="105"/> 
     <br>
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
    <td width="800">
      <table style='margin:0 auto;'>
        <tr>
          <td align="center" colspan="10">
          	<font size="5"><b><%=institucion.toUpperCase()%></b>
          	</font>
         </td>
        </tr>
        <tr>
          <td align="center" colspan="10">
          	<font size="4"><br>Reglamento General de Servicio Social<br>
                <br>
          	</font>
          </td>
         </tr>         
      </table>
      
      <table class="fieldbox" width="100%">
		<tr valign='top'>
		
		<td width="60" align="justify">
		  <font face='Verdana' size='2'>
			<strong>ARTICULO 1</strong> - Servicio Social es la actividad de car&aacute;cter temporal que ejecutan los estudiantes de las diferentes
			carreras de la UM, como requisito previo a la obtenci&oacute;n del t&iacute;tulo en beneficio e interes de la comunidad, el 
			Estado, la propia Universidad o alguna de las instituciones asociadas con la Universidad. <br>
			<br/><strong>ARTICULO 2</strong> - Objetivos del Servicio Social : <br>
				<ol>
					<li>Estimular en el estudiante el deseo de usar su capacidad profesional para el beneficio de la sociedad.</li>
					<li>Iniciar al estudiante en el ejercicio de su profesi&oacute; como medio de promover su inter&eacute; en la 
					renovaci&oacute;n constante, la investigaci&oacute;n, la conservaci&oacute;n de los bienes culturales y el mejoramiento 
					de los recursos fundamentales de la sociedad.</li>
					<li>Proporcionar profesionistas capacitados al servicio de las necesidades de la comunidad del Estado, la Universidad o
					alguna de las instituciones asociadas con ella, a fin de contribuir a lograr una adecuada distribuci&oacute;n de los 
					bienes culturales adquiridos en la forma profesional.</li>
				</ol>
			<strong>ARTICULO 3</strong> - Las autoridades universitarias responsables del funcionamiento del Servicio Social son:<br>
				<ol>
					<li>El director de Certificaci&oacute;n y Archivo, es el Director General de Servicio Social.</li>
					<li>Los coordinadores de las diferentes unidades acad&eacute;micas de la instituci&oacute;n, son quienes coordinan el 
					&aacute;rea en lo que respecta a la prsesentaci&oacute; del Servicio Social de sus estudiantes.</li>
				</ol>
		  <strong>ARTICULO 4</strong> - Para iniciar el Servicio Social, es necesario: <br> 
		  		<ol>
					<li>Haber completado en forma total las materias correspondientes a un m&iacute;nimo de 70% de los cr&eacute;ditos 
					del plan de estudios ;</li>
					<li>Y recibir autorizaci&oacute;n escrita y asignaci&oacute;n de plaza para prestaci&oacute;n del servicio por parte del 
					director general en consulta con el correspondiente coordinador de &aacute;rea y la dependencia en la cual se prestar&aacute;
					el servicio.</li>
				</ol>
		  <strong>ARTICULO 5</strong> - El Servicio Social debe prestarse en un per&iacute;odo no menor de seis meses ni mayor de 2 anos,
		  tiempo durante el cual se debe rendir un m&iacute;nimo de 500 horas de Servicio.<br>
		  <br/> <strong>ARTICULO 6</strong> - Es responsabilidad del Director General de Servicio Social aprobar la propuesta de las unidades
		  acad&eacute;micas en relaci&oacute;n con la naturaleza de la actividad y las condiciones para dar cumplidos los requisitos del 
		  Servicio Social de los estudiantes.<br>
		  <br/> <strong>ARTICULO 7</strong> - Es responsabilidad y prerrogativa de los consejos t&eacute;cnicos de las unidades acad&eacute;micas
		  , legislar lo relativo a las condiciones y modalidades especificas para el cumplimiento del Servicio Social de sus estudiantes, en 
		  los t&eacute;rminos del presente reglamento.<br>
		  <br/><strong>ARTICULO 8</strong> - Es obligaci&oacute;n de los estudiantes que prestan el Servicio Social rendir informes mensuales
		  de sus actividades, los cuales quedar&aacute;n archivados en la oficina del Director General y del coordinador de &aacute;rea. Dichos 
		  informes, firmados por el responsable de la dependencia donde se presta el servicio, ser&aacute;n evaluados por el coordinador de 
		  &aacute;rea y el Director General para su aprobaci&oacute;n.<br>
		   <br/><strong>ARTICULO 9</strong> - Al terminar la prestaci&oacute;n del servicio, el estudiante poddr&aacute; recibir la correspondiente
		   Carta de Liberaci&oacute;n de Servicio Social expedida por el Director General, esta se entregara cuando a juicio del Director General
		   en consulta con el coordinador de &aacute;rea y con base en los informes enviados por la dependencia en la cual se prest&oacute; el
		   servicio, el estudiante a cumplido satisfactoiamnte con las condiciones establecidas.<br>
		   <br/><strong>ARTICULO 10</strong> - Los asuntos relativos al Servicio Social que no est&eacute;n contemplados en el presente reglamento
		   y los casos espec&iacute;ficos de estudiantes de estudiantes que tengan alguna necesidad, ser&aacute;n considerados por la Comisi&ocute;n
		   de Normas Acad&eacute;micas que tienen autoridad final en la materia.   
		  </font>	
    </table>  
</td>
<td width="50">.</td>
  </tr>
</table>