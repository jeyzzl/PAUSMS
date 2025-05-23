<%@page errorPage="paginaerror2.jsp"%>

<%
	String usuario 		= (String) session.getAttribute("codigoPersonal");
%>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />
  
<title><spring:message code='aca.SistemaAcademico'/></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="SHORTCUT ICON" href="imagenes/icoAc.png">
<style type="text/css">
	h6 {
		position: absolute;
		top: -26px;
		left: 190px;	
	
	}
	body{
		padding: 40px;
	}
	.aviso-container{
		max-height: 500px;
		width: 100%;
		overflow: auto;
		border: 1px solid #E6E6E6;
		padding: 20px;
		margin: 20px 0;
		-webkit-box-sizing: border-box; /* Safari/Chrome, other WebKit */
  		-moz-box-sizing: border-box;    /* Firefox, other Gecko */
  		box-sizing: border-box;         /* Opera/IE 8+ */
	}
	
	.texto{
		font-size: 11px;
		color: #777;
	}
</style>

<p style="text-align:center"><span style="font-size:18px"><strong>AVISO DE PRIVACIDAD</strong></span></p>

<div class="aviso-container">
	
	<p>La Universidad de Montemorelos A. C. considera muy importante la privacidad de los Datos Personales, por lo que hacemos de su conocimiento las medidas que se toman en el manejo de los datos que usted nos proporciona en cualquier punto que se lleve la recolecci&oacute;n y almacenamiento de datos en la instituci&oacute;n. Este aviso de privacidad esta apegado a la Ley Federal de Protecci&oacute;n de Datos Personales en Posesi&oacute;n de Particulares publicada en el Diario Oficial de la Federaci&oacute;n el 5 de julio de 2010.</p>
	
	<p><strong><span style="font-size:16px">Responsable de los datos personales</span></strong></p>
	
	<p>La Universidad de Montemorelos A.C. ubicada en calle Libertad 1300 poniente, en el municipio de Montemorelos Nuevo Le&oacute;n, C.P. 67510 es una entidad debidamente constituida de conformidad a las Leyes mexicanas en materia de educaci&oacute;n que dentro de su objeto social es impartir ense&ntilde;anza a nivel inicial, b&aacute;sico, medio &ndash;superior, superior y posgrado, con autorizaci&oacute;n o reconocimientos de validez oficial de estudios, en t&eacute;rminos de la Ley General de Educaci&oacute;n, as&iacute; como otorgar diplomas, certificados, grados acad&eacute;micos y constancias que acrediten los mismos, entre otros.</p>
	
	<p>En el ejercicio de los derechos de acceso, rectificaci&oacute;n, cancelaci&oacute;n, oposici&oacute;n, limitaci&oacute;n de uso o la revocaci&oacute;n del consentimiento, podr&aacute; solicitarse por escrito en el &aacute;rea de Protecci&oacute;n de Datos o al correo electr&oacute;nico privacidad@um.edu.mx</p>
	
	<p>La Pol&iacute;tica de Privacidad y los cambios en el presente aviso se publican en la p&aacute;gina www.um.edu.mx/privacidad.</p>
	
	<p>Al momento que el titular proporciona sus datos personales se espera que ha le&iacute;do, entendido y aceptado los t&eacute;rminos antes expuestos.</p>
	
	<p><strong><span style="font-size:16px">Tratamiento de datos personales</span></strong></p>
	
	<p>Esta pol&iacute;tica tiene como fin asegurar la privacidad de los datos proporcionados por nuestros alumnos, ex alumnos, egresados, proveedores, empleados y dem&aacute;s p&uacute;blicos relacionadas con nuestra gesti&oacute;n acad&eacute;mica y administrativa, con el fin de vincularse con los servicios acad&eacute;micos o administrativos proporcionados por la Universidad de Montemorelos, A.C.</p>
	
	<p>La Universidad de Montemorelos podr&aacute; solicitar y/o recabar a trav&eacute;s del Sitio y otros medios los Datos Personales que sean necesarios para la adecuada prestaci&oacute;n de sus servicios acad&eacute;micos y administrativos. Dichos datos personales y personales sensibles podr&aacute;n incluir los siguientes que se mencionan de manera enunciativa m&aacute;s no limitativa.</p>
	
	<ul>
		<li><spring:message code="aca.Nombre"/> completo</li>
		<li>Datos de contacto tales como direcci&oacute;n, tel&eacute;fonos, correo electr&oacute;nico</li>
		<li>Datos de identificaci&oacute;n personal tales como: edad g&eacute;nero, estado civil y&nbsp;<br />
		nacionalidad</li>
		<li>Datos de informaci&oacute;n gubernamental como numero de inscripci&oacute;n de&nbsp;<br />
		Registro Federal de Causantes, Clave &Uacute;nica de Registro de Poblaci&oacute;n&nbsp;<br />
		(CURP), pasaporte, documento migratorio trat&aacute;ndose de extranjeros</li>
		<li>Datos acad&eacute;micos</li>
		<li>Datos laborales</li>
		<li>informaci&oacute;n de terceros relacionados con usted, como el nombre de&nbsp;<br />
		familiares en relaci&oacute;n a prestaci&oacute;n de servicios que proveen las sociedades</li>
		<li>Datos de contactos en caso de emergencia</li>
		<li>Datos financieros tales como tarjetas de cr&eacute;dito, etc.</li>
	</ul>
	
	<p><strong><span style="font-size:16px">Consentimiento</span></strong></p>
	
	<p>Al usar este sitio o cualquier sitio relacionado con los servicios y/o productos brindados por la Universidad de Montemorelos A.C., usted est&aacute; de acuerdo con la recopilaci&oacute;n, uso, transferencia y almacenamiento de su informaci&oacute;n personal y personal sensible, lo que significa que ha le&iacute;do, entendido y aceptado los t&eacute;rminos a continuaci&oacute;n expuestos, incluyendo cookies y beacons que se generen por el solo hecho de entrar al sitio. En caso de no estar de acuerdo con ellos, el titular NO deber&aacute; proporcionar ninguna informaci&oacute;n personal.</p>
	
	<p><strong><span style="font-size:16px">Finalidad de la informaci&oacute;n</span></strong></p>
	
	<p>La Universidad de Montemorelos quien funge como persona moral responsable de los datos personales y datos personales sensibles, puede recabar para sus procesos acad&eacute;micos, administrativos y/o de operaci&oacute;n sus datos que incluyen datos de identificaci&oacute;n, laborales, familiares, patrimoniales, acad&eacute;micos, migratorios, ideol&oacute;gicos, de salud, f&iacute;sicos y biom&eacute;tricos. Los cuales podr&aacute;n usarse en la operaci&oacute;n de los servicios que se otorgan por la Universidad de Montemorelos A.C. tanto a nivel nacional internacional en sus gestiones de servicios, aportes acad&eacute;micos y administrativos.</p>
	
	<p>Le informamos que los servidores de la instituci&oacute;n utiliza cookies y web beacons para obtener informaci&oacute;n de su equipo y conocer el comportamiento de sus usuarios como en lo siguiente:</p>
	
	<ul>
		<li>Tipo de navegador y sistema operativo</li>
		<li>La direcci&oacute;n IP</li>
		<li>El sitio que visit&oacute; antes de entrar al nuestro</li>
	</ul>
	
	<p>Estas cookies y otras tecnolog&iacute;as pueden ser deshabilitadas. Para conocer como hacerlo, consulte el siguiente vinculo o direcci&oacute;n electr&oacute;nica privacidad@um.edu.mx.</p>
	
	<p><strong><span style="font-size:16px">Tratamientos generales</span></strong></p>
	
	<p>La Universidad de Montemorelos observa los principios de confidencialidad, licitud, consentimiento, informaci&oacute;n, finalidad, lealtad y responsabilidad en la protecci&oacute;n de datos personales contenidos en la Ley Federal de Protecci&oacute;n de Datos Personales en Posesi&oacute;n de Particulares.</p>
	
	<p>Para la prestaci&oacute;n de servicios acad&eacute;micos presenciales y a distancia y en los procesos de Gesti&oacute;n Acad&eacute;mica de la Universidad de Montemorelos integra tareas de gesti&oacute;n escolar, admisiones y promoci&oacute;n de la oferta educativa, inscripciones, bajas, procesos de pasant&iacute;a y obtenci&oacute;n de grados as&iacute; como el involucramiento de profesores, catedr&aacute;ticos y de los padres o tutores en el seguimiento de los ciclos escolares de los alumnos con su desenvolvimiento de preparaci&oacute;n.</p>
	
	<p>En la Gesti&oacute;n Administrativa de la Universidad de Montemorelos A. C. integra procesos y tareas de control interno, creaci&oacute;n y resguardo de expedientes de empleados, docentes, alumnos, egresados y ex alumnos.</p>
	
	<p><strong><span style="font-size:16px">Limitaci&oacute;n de uso y divulgaci&oacute;n de datos personales</span></strong></p>
	
	<p>El uso de sus datos personales ser&aacute; el que resulte necesario, adecuado y relevante en relaci&oacute;n con las finalidades previstas en esta Pol&iacute;tica de Privacidad.</p>
	
	<p>La Universidad de Montemorelos A. C. cumple los principios de protecci&oacute;n de datos personales establecidos por la Ley Federal de Protecci&oacute;n de Datos Personales en Posesi&oacute;n de los Particulares y adopta las medidas necesarias para su aplicaci&oacute;n. Lo anterior aplica a&uacute;n y cuando estos datos fueren tratados por un tercero, a solicitud de Universidad de Montemorelos A. C y con el fin de cumplir con el servicio acad&eacute;mico o administrativo necesario, manteniendo la confidencialidad en todo momento.</p>
	
	<p>La Universidad de Montemorelos A. C. toma las medidas necesarias y suficientes para procurar que esta Pol&iacute;tica de Privacidad sea respetada, por &eacute;l o por terceros con los que guarde alguna relaci&oacute;n, para otorgar los servicios o productos establecidos con el titular.</p>
	
	<p><strong><span style="font-size:16px">Exclusi&oacute;n de responsabilidad del sitio</span></strong></p>
	
	<p>Este sitio web contiene enlaces a otros sitios web de redes asociadas, filiales o terceros, si usted accede a un hiperv&iacute;nculo de esos sitios web, tenga en cuenta que estos pueden tener sus propias pol&iacute;ticas de privacidad y que Universidad de Montemorelos, A. C. no acepta ninguna responsabilidad por esas pol&iacute;ticas, se recomienda que revise esas pol&iacute;ticas antes de enviar cualquier informaci&oacute;n personal a esos sitios web. La inclusi&oacute;n de cualquier v&iacute;nculo a otros sitios web, no implica la aprobaci&oacute;n o adhesi&oacute;n por parte de la Universidad de Montemorelos, A. C. a esas p&aacute;ginas o su contenido.</p>
	
	<p><strong><span style="font-size:16px">Derecho de los titulares de datos personales</span></strong></p>
	
	<p>En todo momento se podr&aacute; revocar el consentimiento que se nos a otorgado para el tratamiento de datos personales a fin de dejemos de hacer uso de los mismos tanto a nivel nacional como internacional con las dependencias en las cuales la Universidad de Montemorelos A. C. tiene relaci&oacute;n como instituci&oacute;n educativa que interact&uacute;a.</p>
	
	<p>El titular o, en su caso, su representante legal podr&aacute;(n) ejercer los derechos de acceso, rectificaci&oacute;n, cancelaci&oacute;n y oposici&oacute;n, y Universidad de Montemorelos A. C. proveer&aacute; los medios que le(s) permita(n) un oportuno ejercicio de sus derechos.</p>
	
	<p>En el ejercicio de los derechos de acceso, rectificaci&oacute;n, cancelaci&oacute;n, oposici&oacute;n de Datos Personales y limitaci&oacute;n de uso o la revocaci&oacute;n del consentimiento, podr&aacute; solicitarse por escrito en el &aacute;rea de Protecci&oacute;n de Datos de la Direcci&oacute;n de Sistemas de Informaci&oacute;n o al correo electr&oacute;nico: privacidad@um.edu.mx.</p>
	
	<p>La Universidad de Montemorelos A.C. podr&aacute; negar el acceso a los datos personales, la rectificaci&oacute;n, cancelaci&oacute;n o concesi&oacute;n de la oposici&oacute;n al tratamiento de los mismos, cuando:</p>
	
	<ul>
		<li>El solicitante no sea el titular de los datos personales, o el representante legal no est&eacute; debidamente acreditado para ello</li>
		<li>En su base de datos no se encuentren los datos personales del solicitante</li>
		<li>Se lesionen los derechos de un tercero</li>
		<li>Exista un impedimento legal, o la resoluci&oacute;n de una autoridad competente que restrinja el acceso a los datos personales o que no permita la rectificaci&oacute;n, cancelaci&oacute;n u oposici&oacute;n de los mismos</li>
		<li>La rectificaci&oacute;n, cancelaci&oacute;n u oposici&oacute;n haya sido previamente realizada</li>
	</ul>
	
	<p>La Universidad de Montemorelos A. C. limitar&aacute; el uso de los datos personales y datos personales sensibles a petici&oacute;n expresa del titular, y no estar&aacute; obligada a cancelar los datos personales cuando:</p>
	
	<ul>
		<li>Deban ser tratados por disposici&oacute;n legal</li>
		<li>Obstaculice actuaciones judiciales o administrativas vinculadas a obligaciones fiscales, la investigaci&oacute;n y persecuci&oacute;n de delitos, o la actualizaci&oacute;n de sanciones administrativas</li>
		<li>Sean necesarios para proteger los intereses jur&iacute;dicamente tutelados del titular</li>
		<li>Sean necesarios para realizar una acci&oacute;n en funci&oacute;n del inter&eacute;s p&uacute;blico</li>
		<li>Sean necesarios para cumplir con una obligaci&oacute;n legalmente adquirida por el titular</li>
		<li>Sean objeto de tratamiento para la prevenci&oacute;n o el diagn&oacute;stico m&eacute;dico o la gesti&oacute;n de servicios de salud; siempre que dicho tratamiento se realice por un profesional de la salud sujeto a un deber de secreto</li>
	</ul>
	
	<p>Nos reservamos el derecho de efectuar en cualquier momento modificaciones o actualizaciones al presente aviso de privacidad. El cambio de la presente Pol&iacute;tica de Privacidad podr&aacute; efectuarse por esta Instituci&oacute;n y estar&aacute; disponible en http://um.edu.mx/privacidad . Si el titular proporciona sus datos personales significa que ha le&iacute;do, entendido y aceptado los t&eacute;rminos antes expuestos.</p>
	
	<p>Creaci&oacute;n 28 de enero de 2013</p>

</div>

<form name="forma" method="post" action="grabarAviso">
	<input type="hidden" name="Usuario" value="<%=usuario%>" />
	<p class="texto">
		Al hacer clic en Aceptar, confirmas que leíste y aceptas nuestros términos y condiciones 
	</p>	
	<div class="alert alert-info">
		<a href="javascript:document.forma.submit();" class="btn btn-primary btn-large"><i class="icon-white icon-ok"></i> Aceptar</a>
	</div>
</form>

<script>
	document.getElementById("acepto").focus();
</script>