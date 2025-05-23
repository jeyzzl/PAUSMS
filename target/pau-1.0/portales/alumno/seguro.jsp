<%@ page import="java.util.List"%>
<%@ page import="java.text.*"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumDocumento"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="../alumno/menu.jsp"%>

<head>
	<script type="text/javascript">
		function borrar(matricula,folio){
			if(confirm("¿Está seguro que desea borrar este documento?")){
				location.href = "borrarFormato?Matricula="+matricula+"&Folio="+folio;
			}
		}
	</script>
</head>
<%
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");

	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");
	
	String mensaje 		= (String) request.getAttribute("mensaje");
	String matricula 	= (String) request.getAttribute("matricula");	
	
	List<AlumDocumento> lisAlumDocumentoFormato 	= (List<AlumDocumento>) request.getAttribute("lisAlumDocumentoFormato");
%>
<body>
	<div class="container-fluid mt-1">	
		<div class="alert alert-success">
			<a href="previos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;
			<span class="badge rounded-pill bg-dark">1E</span>
			<spring:message code="portal.alumno.seguro.Seguros"/> <small class="text-muted"> ( <%=codigoAlumno%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%>)</small>
		</div>	
	</div>	
<% if(mensaje.equals("1")){%>
	<div class="aler alert-success">
		¡Archivo guardado!
	</div>
<% }%>
	<div style="padding-left: 14px;">
		<a class="btn btn-primary" href="ejemploBecas.pdf" target="_blank"><i class="fas fa-file-alt"></i><spring:message code="portal.alumno.seguro.EjemploDeLlenado"/></a><br><br>
	</div>
	<div class="container-fluid d-flex justify-content-start mt-1">
<!-- 		<div class="card card bg-light" style="max-width: 20rem;"> -->
<!-- 		  <div class="card-header"><span class="badge badge-pill badge-dark">A</span>&nbsp;Servicio Becario</div> -->
<!-- 		  <div class="card-body "> -->
<!-- 		   <a style="text-decoration: none;" href="plazas" target="_blank"><i class="fas fa-search" ></i> <span style="color: black;">Consulta</span></a> -->
<!-- 		  </div> -->
<!-- 		</div> -->
<!-- 		&nbsp; &nbsp; -->
		<div class="card card bg-light" style="max-width: 20rem;">
		  <div class="card-header"><span class="badge rounded-pill bg-dark">A</span>&nbsp;<spring:message code="portal.alumno.seguro.Formato"/> <spring:message code="portal.alumno.seguro.SeguroAccidente"/></div>
		  <div class="card-body ">
			<a style="text-decoration: none;" href="seguroAccidente" target="_blank"><i class="fas fa-file-alt"></i> <span style="color: black;"><spring:message code="portal.alumno.seguro.Prellenado"/></span></a>&nbsp;&nbsp;
			<a href="formatoAccidente.pdf" target="_blank"><i class="fas file-alt-o" > <span style="color: black;"><spring:message code="portal.alumno.seguro.EnBlanco"/></span></i></a>&nbsp;
		  </div>
		</div>
		&nbsp; &nbsp;
		<div class="card card bg-light" style="max-width: 20rem;">
		  <div class="card-header"><span class="badge rounded-pill bg-dark">A</span>&nbsp;<spring:message code="portal.alumno.seguro.Formato"/> <spring:message code="portal.alumno.seguro.EducacionGarantizada"/></div>
		  <div class="card-body ">
			<a style="text-decoration: none;" href="educacionGarantizadaPoliticas.pdf" target="_blank"><i class="fas fa-file-alt"></i> <span style="color: black;"><spring:message code="portal.alumno.seguro.Politicas"/></span></a>
			<a style="text-decoration: none;" href="educacionGarantizada" target="_blank"><i class="fas fa-file-alt"></i> <span style="color: black;"><spring:message code="portal.alumno.seguro.Prellenado"/></span></a>
			<a href="educacionGarantizadaBlanco.pdf" target="_blank"><i class="fas file-alt-o" > <span style="color: black;"><spring:message code="portal.alumno.seguro.EnBlanco"/></span></i></a>&nbsp;
		  </div>
		</div>
		&nbsp; &nbsp;
		<div class="card card bg-light" style="max-width: 20rem;">
		  <div class="card-header"><span class="badge rounded-pill bg-dark">A</span>&nbsp;<spring:message code="portal.alumno.seguro.Formato"/> <spring:message code="portal.alumno.seguro.SeguroDeVida"/></div>
		  <div class="card-body ">
<!-- 			<a style="text-decoration: none;" href="seguroDeVida" target="_blank"><i class="fas fa-file-alt"></i> <span style="color: black;">Prellenado</span></a> -->
			<a href="seguroDeVida.pdf" target="_blank"><i class="fas file-alt-o" > <span style="color: black;"><spring:message code="portal.alumno.seguro.EnBlanco"/></span></i></a>&nbsp;
		  </div>
		</div>
	</div>
	<br>
<%
	if(matricula.equals(codigoPersonal)){
%>
	<form style="padding-left: 20px; padding-right: 20px;" name="frmDocumento" id="frmDocumento" enctype="multipart/form-data" action="subirFormato" method="post">
		<input type="hidden" name="Matricula" value="<%=matricula%>">
		<label><spring:message code="portal.alumno.seguro.MensajeUno"/></label>
		<select class="form-control" name="Tipo" style="width:465px;">
			<option value="2"><spring:message code="portal.alumno.seguro.SeguroAccidente"/></option>
			<option value="3"><spring:message code="portal.alumno.seguro.EducacionGarantizada"/></option>
			<option value="4"><spring:message code="portal.alumno.seguro.SeguroDeVida"/></option>
		</select><br>
	  	<span class="btn btn-file">
		  	<input type="file" id="archivo" name="archivo" required="required"/>
	  	</span>
		<button type="submit" id="btnGuardar" class="btn btn-warning"><i class="glyphicon glyphicon-open"></i> <spring:message code="portal.alumno.seguro.Subir"/></button>
	</form>
<%
	}
%>
	<br>
	<div style="padding-left: 20px; padding-right: 20px;" class="table">
		<table class="table">
		<tr>
			<th><spring:message code="portal.alumno.seguro.Folio"/></th>
			<th><spring:message code="portal.alumno.seguro.Tipo"/></th>
			<th><spring:message code="portal.alumno.seguro.Fecha"/></th>
			<th><spring:message code="portal.alumno.seguro.Documento"/></th>		
		</tr>
<% 		
	for(AlumDocumento documento : lisAlumDocumentoFormato){
		String tipo = "-";
		if (documento.getTipo().equals("2")) tipo = "Seguro accidentes";
		if (documento.getTipo().equals("3")) tipo = "Educación garantizada";
		if (documento.getTipo().equals("4")) tipo = "Seguro de vida";
%>
			<tr>
				<td>
<%		if (codigoPersonal.equals(matricula)){%>			
				<a class="fas fa-trash-alt" href="javascript:borrar('<%=documento.getCodigoPersonal()%>','<%=documento.getFolio()%>');"></a>
<% 		}%>				
				<%=documento.getFolio()%>
				</td>
				<td><%=tipo%></td>
				<td><%=documento.getFecha()%></td>
				<td><%=documento.getNombre()%></td>			
			</tr>
<% 	}%>
		</table>
	</div>
</body>