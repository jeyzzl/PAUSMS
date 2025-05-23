<%@ page import= "java.util.List"%>
<%@ page import= "aca.carga.spring.CargaBloque"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">	
	function Grabar(){
		if(document.frmbloque.BloqueId.value!="" && document.frmbloque.BloqueId!=""){
			document.frmbloque.submit();			
		}else{
			alert("<spring:message code='aca.JSCompletar'/>");
		}
	}	
</script>
<%
	// Declaracion de variables	
	String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId"); 
	String bloqueId 		= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
	String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	CargaBloque bloque 		= (CargaBloque)request.getAttribute("bloque");
	String nombreCarga 		= (String)request.getAttribute("nombreCarga");
%>

<div class="container-fluid">
	<h1>Edit Block</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="bloque?cargaId=<%=cargaId%>"><i class="fas fa-arrow-left"></i></a>
	</div>
	<form name="frmbloque" action="grabarBloque" method="post">
	<table class="table table-condensed" style="width:100%" >
   	<tr> 
    	<td width="15%"><strong><spring:message code="aca.Carga"/>:</strong></td>
        <td width="76%">
			<input name="CargaId" type="text" class="text" id="CargaId" size="6" maxlength="6" value="<%=bloque.getCargaId()%>" readonly>
           	<font size="2">&nbsp;[ <%=nombreCarga%> ] </font>
		</td>			
	</tr>
	<tr> 
    	<td><b>Block:</b></td>
        <td><input name="BloqueId" type="text" class="text" id="BloqueId" size="3" maxlength="3" value="<%=bloque.getBloqueId()%>" readonly></td>
        </tr>		  
	<tr> 
        <td><b>Block Name:</b></td>
        <td><input name="NombreBloque" type="text" class="text" id="NombreBloque" size="40" maxlength="40" value="<%=bloque.getNombreBloque()%>"></td>
       </tr>
    <tr> 
        <td><b><spring:message code="aca.Inicio"/> <spring:message code="aca.Fecha"/>:</b></td>
        <td><input name="FechaInicio" type="text" class="text" id="FechaInicio" size="10" maxlength="10" value="<%=bloque.getFInicio()%>" data-date-format="dd/mm/yyyy">
        ( <spring:message code="aca.DDMMAAAA"/> )</td>
	</tr>		  
	<tr> 
        <td><b>Enrollment <spring:message code="aca.Cierre"/> <spring:message code="aca.Fecha"/>:</b></td>
        <td><input name="FechaCierre" type="text" class="text" id="FechaCierre" size="10" maxlength="10" value="<%=bloque.getFCierre()%>" data-date-format="dd/mm/yyyy">
        ( <spring:message code="aca.DDMMAAAA"/> )</td>
	</tr>
	<tr> 
        <td><b><spring:message code="aca.Fin"/> <spring:message code="aca.Fecha"/>:</b></td>
        <td><input name="FechaFinal" type="text" class="text" id="FechaFinal" size="10" maxlength="10" value="<%=bloque.getFFinal()%>" data-date-format="dd/mm/yyyy">
        ( <spring:message code="aca.DDMMAAAA"/> )</td>
	</tr>	
	<tr> 
        <td><b>Beginning of Classes:</b></td>
        <td><input name="InicioClases" type="text" class="text" id="InicioClases" size="10" maxlength="10" value="<%=bloque.getInicioClases()%>" data-date-format="dd/mm/yyyy">
        ( <spring:message code="aca.DDMMAAAA"/> )</td>
	</tr>
	<tr> 
    	<td colspan="2">
			<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar"/></a> &nbsp;
			<%=mensaje.equals("-")?"":mensaje%>
		</td>
	</tr>
	</table>
	</form>
</div>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>
	jQuery('#FechaInicio').datepicker();
	jQuery('#FechaCierre').datepicker();
	jQuery('#FechaFinal').datepicker();	
	jQuery('#InicioClases').datepicker();
</script>
