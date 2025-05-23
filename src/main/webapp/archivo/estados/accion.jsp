<%@ page import="aca.archivo.spring.ArchStatus"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	
	function Grabar(){
		if(document.frmdocumento.IdStatus.value!="" && document.frmdocumento.Descripcion!=""){			
			document.frmdocumento.submit();			
		}else{
			alert("Fill out the entire form");
		}
	}	
</script>
<% 
	String resultado 	= request.getParameter("Resultado")==null?"-":request.getParameter("Resultado");
	ArchStatus estado	= (ArchStatus) request.getAttribute("estado");
%>
<html>
<head><title><spring:message code='aca.DocumentoSinTitulo'/></title></head>
<body>
<div class="container-fluid">
	<h2>Edit Status</h2>
	<div class="alert alert-info d-flex align-items-center">
		<a href="estados" class="btn btn-primary"> <i class="fas fa-arrow-left"></i></a>
	</div>	
	<form action="grabarEstado" method="post" name="frmdocumento" target="_self">
	<table class="table table-sm" style="width:50%">
    <tr> 
    	<td width="15%"><strong><spring:message code="aca.Clave"/>:</strong></td>
        <td width="76%">
        	<input name="IdStatus" type="text" class="form-control" id="IdStatus" size="3" maxlength="3" value="<%=estado.getIdStatus()%>" style="width:100px;" disabled>
        </td>			
    </tr>
    <tr> 
    	<td><strong>Description:</strong></td>
    	<td><input name="Descripcion" type="text" class="form-control" id="Descripcion" size="40" maxlength="40" value="<%=estado.getDescripcion()%>"></td>
    </tr>
<% 	if (!resultado.equals("-")){%>
    <tr> 
    	<td colspan="2" align="center"><%=resultado%></td>
    </tr>
<%	}%>    
	</table>
	<div class="alert alert-info d-flex align-items-center">  	
		<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a>	
  	</div>
	</form>
</div>	
</body>
</html>