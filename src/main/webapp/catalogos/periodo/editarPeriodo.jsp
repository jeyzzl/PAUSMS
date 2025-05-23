<%@ page import= "aca.catalogo.spring.CatPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">		
	function Grabar(){
		if(document.frmperiodo.Nombre.value!="" && document.frmperiodo.FechaIni!="" 
			&& document.frmperiodo.FechaFin!="" && document.frmperiodo.Estado!="" && document.frmperiodo.ExceptoPron!=""
		){			
			document.frmperiodo.submit();			
		}else{
			alert("<spring:message code='aca.JSCompletar'/>");
		}
	}
</script>
<%
	// Declaracion de variables	
	CatPeriodo periodo 		= (CatPeriodo)request.getAttribute("periodo");
	String mensaje		= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	if (!mensaje.equals("-")){ 
		if (mensaje.equals("-")) mensaje = "¡Error Saving!"; else mensaje = "¡Data Saved!";
	}	
%>
<div class="container-fluid">
	<h2><spring:message code="catalogos.periodos.Titulo2"/></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="periodo"><i class="fas fa-arrow-left"></i></a>
			<%=!mensaje.equals("-")?"&nbsp;" + mensaje: "" %>		
	</div>
	<form name="frmperiodo" action="grabarPeriodo" method="post">
  <div class="row container-fluid">
  
      <div class="span2 col">
		<label for="PeriodoId"><spring:message code="aca.PeriodoId"/>:</label>
        <input name="PeriodoId" type="text" class="input input-large form-control" style="width:250px" id="PeriodoId" size="3" maxlength="10" required value="<%=periodo.getPeriodoId()%>">
		<br>		
    	<label for="Clave"><spring:message code="aca.Nombre"/>:</label>
        <input name="Nombre" type="text" class="input input-large form-control" style="width:250px" id="Nombre" size="3" maxlength="30" required value="<%=periodo.getNombre()%>">
		<br>
        <label for="FechaIni"><spring:message code="aca.FechaInicio"/>:</label>
        <input name="FechaIni" type="date" min="1950-01-01" max="2050-12-31" style="width:250px" class="form-control center" id="FechaIni" required value="<%=periodo.getFechaIni()%>" style="width:210px;">
		<br>	       
		</div>
		<div class=" span2 col">
        <label for="FechaFin"><spring:message code="aca.FechaFinal"/>:</label>
        <input name="FechaFin" type="date" min="1950-01-01" max="2050-12-31" style="width:250px" class="form-control center" id="FechaFin" required value="<%=periodo.getFechaFin() %>" style="width:210px;">
		<br>
        <label for="Estado"><spring:message code="aca.Status"/>:</label>
        <select class="input-large form-select" id="Estado" name="Estado" style="width:250px">
			<option value="A" <%=periodo.getEstado().equals("A") ? "Selected" : ""%>><spring:message code="aca.Activo"/></option>
			<option value="I" <%=periodo.getEstado().equals("I") ? "Selected" : ""%>><spring:message code="aca.Inactivo"/></option>					
		</select>
        <br> 
        <label for="ExceptoPron"><spring:message code="catalogos.periodos.ExceptoPron"/>:</label>
        <input name="ExceptoPron" type="text" class="input input-large form-control" style="width:250px" id="ExceptoPron" required value="<%=periodo.getExceptoPron() %>" size="40" maxlength="50">
        </div>
        </div>
    &nbsp;
    <div class="alert alert-info">
        <a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp;  
	</div>		
	</form>
</div>