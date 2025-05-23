<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AfeAcuerdos"  class="aca.afe.FesCcAfeAcuerdos" scope="page"/>
<jsp:useBean id="AfeAcuerdosU"  class="aca.afe.FesCcAfeAcuerdosUtil" scope="page"/>
<jsp:useBean id="BecPuestoAlumno"  class="aca.bec.BecPuestoAlumno" scope="page"/>
<jsp:useBean id="BecAcuerdo"  class="aca.bec.BecAcuerdo" scope="page"/>
<jsp:useBean id="BecAcuerdoU"  class="aca.bec.BecAcuerdoUtil" scope="page"/>
<jsp:useBean id="BecTipo"  class="aca.bec.BecTipo" scope="page"/>
<jsp:useBean id="BecCategoria"  class="aca.bec.BecCategoria" scope="page"/>
<jsp:useBean id="BecCategoriaU"  class="aca.bec.BecCategoriaUtil" scope="page"/>
<jsp:useBean id="Inscritos"  class="aca.vista.Inscritos" scope="page"/>
<jsp:useBean id="InscritosU" scope="page" class="aca.vista.InscritosUtil"/>
<head>
</head>
<%
	String idEjercicio 				= (String) session.getAttribute("ejercicioId");
	String codigoPersonal 			= (String) session.getAttribute("codigoAlumno");
	String folio 					= request.getParameter("folio")==null?"0":request.getParameter("folio");
	FesCcAfeAcuerdos afeAcuerdos	= (FesCcAfeAcuerdos)request.getAttribute("afeAcuerdos");
	String alumnoNombre				= (String)request.getAttribute("alumnoNombre");
	if(folio.equals("0")){
		response.sendRedirect("contrato");
	}	
%>
<style>
 	body{
 		background : white;
 	}
 </style>
<body>
<div class="container-fluid">
		
	<h2>Registrar Acuerdo En Mateo <small class="text-muted fs-4">[<%=folio %>] <%=codigoPersonal%> | <%=alumnoNombre%></small></h2>	
	<br />
	<%=msj %>	
	<div class="alert alert-info">
		<a href="contrato" class="btn btn-primary"><i class="icon icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>	
	<form action="" name="forma">
		<input type="hidden" name="Accion" />
		<input type="hidden" name="folio" value="<%=folio%>" />
			
		<p>	
			<label for="matricula"><spring:message code="aca.Matricula"/></label>
			<input type="text" name="matricula" id="matricula" value="<%=afeAcuerdos.getAcuerdoImpMatricula() %>" />
		</p>
		<p>
			<label for="ensenanza">Enseñanza</label>
			<input type="text" name="ensenanza" id="ensenanza" value="<%=afeAcuerdos.getAcuerdoImpEnsenanza() %>" />
		</p>
		<p>
			<label for="internado">Internado</label>
			<input type="text" name="internado" id="internado" value="<%=afeAcuerdos.getAcuerdoImpInternado() %>" />
		</p>
		<p>
			<label for="totalBecAd">Beca Neta</label>
			<input type="text" name="totalBecAd" id="totalBecAd" value="<%=afeAcuerdos.getTotalBecaAdic() %>" />
		</p>
		
		<div class="alert alert-info">
			<a class="btn btn-primary btn-large" onclick="grabar();"><i class="icon icon-ok icon-white"></i> Grabar</a>		
		</div>
	</form>	
</div>
<script>
	function grabar(){
		document.forma.Accion.value = "1";
		document.forma.submit();
	}
</script>