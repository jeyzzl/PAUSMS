<%@ page import= "aca.encuesta.spring.EncPeriodo"%>

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
	String periodoId		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
	String mensaje			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	EncPeriodo periodo 		= (EncPeriodo)request.getAttribute("periodo");
%>
<div class="container-fluid">
	<h2>Editar Periodo</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="lista"><i class="fas fa-arrow-left"></i></a>
			<%=!mensaje.equals("-")?"&nbsp;" + mensaje: "" %>		
	</div>
	<form name="frmperiodo" action="grabarPeriodo" method="post">
	<input name="PeriodoId" type="hidden" id="PeriodoId" value="<%=periodo.getPeriodoId()%>">
  	<div class="row container-fluid">  
    	<div class="span2 col">	
    		<label for="Clave">Nombre:</label>
        	<input name="PeriodoNombre" type="text" class="input input-large form-control" style="width:250px" id="Nombre" size="3" maxlength="30" required value="<%=periodo.getPeriodoNombre() %>">
			<br>
        	<label for="FechaIni">Fecha Inicial:</label>
        	<input name="FechaIni" type="date" min="1950-01-01" max="2050-12-31" style="width:250px" class="form-control center" id="FechaIni" required value="<%=periodo.getFechaIni()%>" style="width:210px;">
			<br>	       
		</div>
		<div class=" span2 col">
        	<label for="FechaFin">Fecha Final:</label>
        	<input name="FechaFin" type="date" min="1950-01-01" max="2050-12-31" style="width:250px" class="form-control center" id="FechaFin" required value="<%=periodo.getFechaFin() %>" style="width:210px;">
			<br>
        	<label for="Estado">Estado:</label>
        	<select class="input-large form-select" id="Estado" name="Estado" style="width:250px">
				<option value="A" <%=periodo.getEstado().equals("A") ? "Selected" : ""%>><spring:message code="aca.Activo"/></option>
				<option value="I" <%=periodo.getEstado().equals("I") ? "Selected" : ""%>><spring:message code="aca.Inactivo"/></option>					
			</select>       
        </div>
    </div>
    &nbsp;
    <div class="alert alert-info">
        <a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp;  
	</div>		
	</form>
</div>