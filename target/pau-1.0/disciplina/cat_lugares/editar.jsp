<%@ page import="aca.disciplina.spring.CondLugar"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>


<script type="text/javascript">
	
	function Grabar(){
		if(document.frmLugar.IdLugar.value!="" && document.frmLugar.Nombre.value!="" ){			
			document.frmLugar.submit();			
		}else{
			alert("Fill out the entire form");
		}
	}
	
</script>

<%
	CondLugar lugar = (CondLugar)request.getAttribute("lugar");
%>
<div class="container-fluid">
	<h2>Location Catalog (edit)</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="lugares"><spring:message code="aca.Regresar"/></a>
	</div>
	<form name="frmLugar" method="post" action="grabar">
		<input type="hidden" name="Accion">
		<div class="row">
			<div class="span3">
				<label for="IdLugar">Id:</label>			
				<input name="IdLugar" type="text" class="text form-control" style="width:50px;" id="IdLugar" value="<%=lugar.getIdLugar()%>" size="2" maxlength="2" readonly>
				<br><br>
				<label for="Nombre"><spring:message code="aca.Nombre"/>:</label>			
				<input name="Nombre" type="text" class="text form-control" style="width:500px;" value="<%=lugar.getNombre()%>" size="42" maxlength="50">
			</div>
		</div>
		<br>
		<div class="alert alert-info">
            &nbsp;<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> 
		</div>
</form>
</div>