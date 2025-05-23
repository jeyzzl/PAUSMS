<%@ page import="java.util.List"%>
<%@ page import="aca.disciplina.spring.CondJuez"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<script type="text/javascript">
	
	function Grabar(){
		if(document.frmjuez.IdJuez.value!="" && document.frmjuez.Nombre.value!="" ){			
			document.frmjuez.submit();			
		}else{
			alert("Fill out the entire form");
		}
	}
	
</script>

<%	
	String mensaje		= "";
	CondJuez juez		= (CondJuez)request.getAttribute("juez");
%>
<div class="container-fluid">
	<h2>Judges Catalog (edit)</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="juez"><spring:message code="aca.Regresar"/></a>
	</div>
	<form name="frmjuez" method="post" action="grabar">
		<input type="hidden" name="Accion">
		<div class="row">
			<div class="span3">
				<label for="IdJuez">Id:</label>			
				<input name="IdJuez" type="text" class="text form-control" style="width:50px" id="Idjuez" value="<%=juez.getIdJuez()%>" size="2" maxlength="2" readonly>
				<br>
				<label for="Nombre"><spring:message code="aca.Nombre"/>:</label>			
				<input name="Nombre" type="text" class="text form-control" style="width:500px" value="<%=juez.getNombre()%>" size="42" maxlength="50">
			</div>
		</div>
		<br>
		<div class="alert alert-info">
            &nbsp;<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> 
		</div>
	</form>
</div> 