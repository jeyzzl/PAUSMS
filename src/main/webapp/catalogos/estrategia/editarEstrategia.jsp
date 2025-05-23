<%@ page import= "aca.catalogo.spring.CatEstrategia"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">	
	
	function Grabar(){
		if(document.frmestrategia.EstrategiaId.value!="" && document.frmestrategia.NombreEstrategia!=""){			
			document.frmestrategia.submit();			
		}else{
			alert("<spring:message code='aca.JSCompletar'/>");
		}
	}
</script>
<%
	// Declaracion de variables	
	CatEstrategia estrategia 	= (CatEstrategia)request.getAttribute("estrategia");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<div class="container-fluid">
	<h2><spring:message code="catalogos.estrategias.Titulo2"/></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="estrategia"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;<%=mensaje.equals("-")?"":mensaje%>
	</div>
	<form name="frmestrategia" action="grabarEstrategia" method="post">
	<div class="form-group">
    	<label for="Clave"><spring:message code="aca.Clave"/>:</label>
        <input name="EstrategiaId" type="text" class="input input-mini form-control" id="EstrategiaId" size="3" maxlength="3" required value="<%=estrategia.getEstrategiaId()%>" readonly>
		<br>
        <label for="Nombre"><spring:message code="aca.Nombre"/>:</label>
        <input name="NombreEstrategia" type="text" class="input input-xlarge form-control" id="NombreEstrategia" required value="<%=estrategia.getNombreEstrategia()%>" size="40" maxlength="50">
    </div>
    <br>		 				
    <div class="alert alert-info">
        <a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp;  
	</div>		
	</form>
</div>