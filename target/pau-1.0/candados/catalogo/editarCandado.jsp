<%@ page import="aca.candado.spring.Candado"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">

	function Grabar(){
		if(document.frmcandado.CandadoId != "" && document.frmcandado.NombreCandado.value != ""){
			document.frmcandado.submit();			
		}else{
			alert("Fill out the entire form!");
		}
	}	
</script>

<%
	String candadoId	 		= (String)request.getAttribute("candadoId");
	String tipoId				= (String)request.getAttribute("tipoId");
	String nombreCandado 		= (String)request.getAttribute("nombreCandado");
	String codigoPersonal		= (String)request.getAttribute("codigoPersonal");
	String nombreEmpleado		= (String)request.getAttribute("nombreEmpleado");
	
	Candado candado 	 		= (Candado)request.getAttribute("candado");
%>
<div class="container-fluid">
<h2>Edit Lock <small class="text-muted fs-4">(<%=nombreCandado%>)</small></h2>
<div class="alert alert-info">
	<a class="btn btn-primary" href="alta_candado?TipoId=<%=tipoId %>"><i class="fas fa-arrow-left"></i></a>
</div>
	<form name="frmcandado" method="post" action="grabar">
	<input name="TipoId" type="hidden" value=<%=tipoId%>>
  	<table style="width:48%" class="table table-condensed">
    	<tr>
			<td width="16%"><b>Key:</b></td>
      		<td width="84%"> 
        		<input name="CandadoId" id="CandadoId" type="text" class="text form-control" size="4" maxlength="4" value="<%=candado.getCandadoId()%>" readonly>	
    	</tr>
    	<tr> 
      		<td><b><spring:message code="aca.Nombre"/>:</b></td>
      		<td><input name="NombreCandado" id="NombreCandado" type="text" class="text form-control" value="<%=candado.getNombreCandado()%>" size="60"></td>
       </tr>
       <tr>
	   		<th colspan="2" style="text-align:center;">
          		<input class="btn btn-primary" type="button" onClick="javascript:Grabar()" value="Save">
          	</th>
       </tr>
	</table>
	</form>
</div>