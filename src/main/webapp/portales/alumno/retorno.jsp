<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.*"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumUbicacion"%>
<%@ page import="aca.alumno.spring.AlumDocumento"%>
<%@ page import="aca.residencia.spring.ResExpediente"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%@ include file="../alumno/menu.jsp"%>

<head>
	<script type="text/javascript">
		function borrarRetorno(documentoId){
			if(confirm("¿Estás seguro de borrar el documento?")){
				document.location.href = "borrarRetorno?DocumentoId="+documentoId;
			}
		}
	</script>
</head>
<%
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");	
	AlumUbicacion alumUbicacion = (AlumUbicacion) request.getAttribute("alumUbicacion");	
	String mensaje 				= (String) request.getAttribute("mensaje");
	String matricula 			= (String) request.getAttribute("matricula");
	String periodoId 			= (String) request.getAttribute("periodoId");

	HashMap<String,String> mapaDocumentos 	= (HashMap<String,String>) request.getAttribute("mapaDocumentos");	
%>
<body>
	<div class="container-fluid mt-1">	
		<div class="alert alert-success">
			<a href="previos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;
			<span class="badge rounded-pill bg-dark">1H</span>
			<spring:message code="portal.alumno.retorno.RetornoSeguro"/><small class="text-muted fs-6"> ( <%=codigoAlumno%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%> )</small>
		</div>	
	</div>
<!-- 	<div class="container-fluid d-flex justify-content-start mt-1"> -->
<!-- 		<div class="card card bg-light" style="max-width: 20rem;"> -->
<%-- 		  <div class="card-header"><span class="badge rounded-pill bg-dark">A</span>&nbsp;<spring:message code="portal.alumno.retorno.CartaResponsiva"/></div> --%>
<!-- 		  <div class="card-body "> -->
<%-- 		  	<h5 class="card-title"><spring:message code="portal.alumno.retorno.Informacion"/></h5> --%>
<%-- 			<p class="card-text"><spring:message code="portal.alumno.retorno.MensajeUno"/></p> --%>
<!-- 		  </div> -->
<!-- 		  <div class="card-footer"> -->
<%-- 		  	<a class="btn btn-dark" href="CartaResponsivaUM.docx" target="_blank"><i class="fas file-alt-o"></i> <spring:message code="portal.alumno.retorno.Formato"/></a>&nbsp; --%>
<%-- <%	if (mapaDocumentos.containsKey("1")){%>			 --%>
<!-- 			<a class="btn btn-success" href="descargarRetorno?DocumentoId=1"><i class="fas fa-download"></i></a>&nbsp; -->
<%-- <%		if(matricula.equals(codigoPersonal)){ %> --%>
<!-- 			<a class="btn btn-danger" href="javascript:borrarRetorno('1')"><i class="fas fa-trash-alt"></i></a>		 -->
<%-- <%		} %>			 --%>
<%-- <%	}else if (codigoPersonal.equals(codigoAlumno)){%> --%>
<%-- 			<a class="btn btn-primary" href="subirRetorno?DocumentoId=1"><spring:message code="portal.alumno.retorno.Subir"/></a> --%>
<%-- <%	}%> --%>
<!-- 		  </div> -->
<!-- 		</div>&nbsp;&nbsp;		 -->
<!-- 		<div class="card card bg-light" style="max-width: 20rem;"> -->
<%-- 		  <div class="card-header"><span class="badge rounded-pill bg-dark">B</span>&nbsp;<spring:message code="portal.alumno.retorno.ComprobanteLlegada"/></div> --%>
<!-- 		  <div class="card-body "> -->
<%-- 		  	<h5 class="card-title"><spring:message code="portal.alumno.retorno.Informacion"/></h5> --%>
<%-- 			<p class="card-text"><spring:message code="portal.alumno.retorno.MensajeDos"/></p> --%>
<!-- 		  </div> -->
<!-- 		  <div class="card-footer">			 -->
<%-- 		  <%if (mapaDocumentos.containsKey("2")){%>			 --%>
<!-- 			<a class="btn btn-success" href="descargarRetorno?DocumentoId=2"><i class="fas fa-download"></i></a>&nbsp; -->
<!-- 			<a class="btn btn-danger" href="javascript:borrarRetorno('2')"><i class="fas fa-trash-alt"></i></a> -->
<%-- 		  <%}else if (codigoPersonal.equals(codigoAlumno)){%> --%>
<%-- 			<a class="btn btn-primary" href="subirRetorno?DocumentoId=2"><spring:message code="portal.alumno.retorno.Subir"/></a> --%>
<%-- 		  <%}%> --%>
<!-- 		  </div> -->
<!-- 		</div>&nbsp;&nbsp; -->
<!-- 		<div class="card card bg-light" style="max-width: 20rem;"> -->
<%-- 		  <div class="card-header"><span class="badge rounded-pill bg-dark">C</span>&nbsp;<spring:message code="portal.alumno.retorno.ComprobanteVacuna"/></div> --%>
<!-- 		  <div class="card-body "> -->
<%-- 		  	<h5 class="card-title"><spring:message code="portal.alumno.retorno.Informacion"/></h5> --%>
<%-- 			<p class="card-text"><spring:message code="portal.alumno.retorno.MensajeTres"/></p>			 --%>
<!-- 		  </div> -->
<!-- 		  <div class="card-footer"> -->
<%-- 		  <%if (mapaDocumentos.containsKey("3")){%>			 --%>
<!-- 			<a class="btn btn-success" href="descargarRetorno?DocumentoId=3"><i class="fas fa-download"></i></a>&nbsp; -->
<!-- 			<a class="btn btn-danger" href="javascript:borrarRetorno('3')"><i class="fas fa-trash-alt"></i></a> -->
<%-- 		  <%}else if (codigoPersonal.equals(codigoAlumno)){%> --%>
<%-- 			<a class="btn btn-primary" href="subirRetorno?DocumentoId=3"><spring:message code="portal.alumno.retorno.Subir"/></a> --%>
<%-- 		  <%}%> --%>
<!-- 		  </div> -->
<!-- 		</div>&nbsp;&nbsp;		 -->
<!-- 		<div class="card card bg-light" style="max-width: 20rem;"> -->
<%-- 		  <div class="card-header"><span class="badge rounded-pill bg-dark">D</span>&nbsp;<spring:message code="portal.alumno.retorno.PruebaPCR"/></div> --%>
<!-- 		  <div class="card-body "> -->
<%-- 		  	<h5 class="card-title"><spring:message code="portal.alumno.retorno.Informacion"/></h5> --%>
<%-- 			<p class="card-text"><spring:message code="portal.alumno.retorno.MensajeCuatro"/></p> --%>
<!-- 		  </div> -->
<!-- 		  <div class="card-footer"> -->
<%-- 		  <%if (mapaDocumentos.containsKey("4")){%>	 --%>
<!-- 			<a class="btn btn-success" href="descargarRetorno?DocumentoId=4"><i class="fas fa-download"></i></a>&nbsp; -->
<!-- 			<a class="btn btn-danger" href="javascript:borrarRetorno('4')"><i class="fas fa-trash-alt"></i></a> -->
<%-- 		  <%}else if (codigoPersonal.equals(codigoAlumno)){%> --%>
<%-- 		    <a class="btn btn-primary" href="subirRetorno?DocumentoId=4"><spring:message code="portal.alumno.retorno.Subir"/></a> --%>
<%-- 		  <%}%>	 --%>
<!-- 		  </div> -->
<!-- 		</div>&nbsp;&nbsp; -->
<!-- 	</div> -->
<!-- 	<br> -->
	<div class="container-fluid mt-1">	
		<form action="grabarVacuna" name="form" method="post">
		<h4>¿Estas vacunado?<br>
		<select name="Vacunado" class="form-select" style="width:100px;">
			<option value="S" <%=alumUbicacion.getVacuna().equals("S")?"selected":""%>>Si</option>
			<option value="N" <%=alumUbicacion.getVacuna().equals("N")?"selected":""%>>No</option>
		</select>
		</h4>
		<h4>
		Si la respuesta anterior es si, coloca el nombre(s) y el número de dósis de la vacuna(s):<br>		
		<textarea id="DescVacuna" name="DescVacuna" class="form-control" cols="50" rows="3"><%=alumUbicacion.getDescripcionVacuna()%></textarea>
		</h4>
		<div class="alert alert-success">
			<button class="btn btn-primary" type="submit">Grabar</button>
			<% if(mensaje.equals("1")){%>		
				&nbsp; &nbsp; <spring:message code="portal.alumno.retorno.ArchivoGuardado"/>
			<%	} %>
		</div>
		</form>
	</div>
</body>