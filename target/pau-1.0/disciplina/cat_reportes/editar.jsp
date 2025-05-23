<%@ page import="aca.disciplina.spring.CondReporte"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	
	function Grabar(){
		if(document.frmReporte.IdReporte.value!="" && document.frmReporte.Nombre.value!=""  && document.frmReporte.Tipo.value!=""){			
			document.frmReporte.submit();			
		}else{
			alert("Fill out the entire form");
		}
	}
	
</script>

<%	
	CondReporte reporte		= (CondReporte)request.getAttribute("reporte");
					
%>
<div class="container-fluid">
	<h2>Report Type Catalog (edit)</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="reportes"><spring:message code="aca.Regresar"/></a>
	</div>
	<form name="frmReporte" method="post" action="grabar">
		<input type="hidden" name="Accion">
		<div class="row">
			<div class="span3">
				<label for="IdReporte">Id:</label>			
				<input name="IdReporte" type="text" class="text form-control" style="width:50px" id="IdReporte" value="<%=reporte.getIdReporte()%>" size="2" maxlength="2" readonly>
				<br>
				<label for="Nombre"><spring:message code="aca.Nombre"/>:</label>			
				<input name="Nombre" type="text" class="text form-control" style="width:500px" value="<%=reporte.getNombre()%>" size="42" maxlength="50">
				<br>
				<label for="Tipo"><spring:message code="aca.Tipo"/></label>	
				<select name="Tipo" id="Tipo" class="form-select" style="width:130px">
					<option value="D" <%=reporte.getTipo().equals("D")?"selected":""%> >Misconduct</option>
					<option value="C" <%=reporte.getTipo().equals("C")?"selected":""%> >Praise</option>
					<option value="N" <%=reporte.getTipo().equals("N")?"selected":""%> >Other</option>
       			</select>		
			</div>
		</div>
		<br>
		<div class="alert alert-info">
            &nbsp;<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a>
		</div>
	</form>
</div>